package com.ruoyi.system.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.system.adapter.AdapterFactory;
import com.ruoyi.system.adapter.SystemAdapter;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.SysSyncExecutionMapper;
import com.ruoyi.system.service.ISyncEngineService;
import com.ruoyi.system.service.ISysIntegrationConfigService;
import com.ruoyi.system.service.ISysSyncExecutionService;
import com.ruoyi.system.service.ISysSyncTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncEngineServiceImpl implements ISyncEngineService {

    private final ISysSyncTaskService syncTaskService;
    private final ISysSyncExecutionService syncExecutionService;
    private final ISysIntegrationConfigService integrationConfigService;
    private final AdapterFactory adapterFactory;
    private final ObjectMapper objectMapper;

    @Value("${system.integration.thread-count:10}")
    private int threadCount;

    @Value("${system.integration.batch-size:100}")
    private int batchSize;

    @Value("${system.integration.timeout:30000}")
    private int timeout;

    private boolean schedulerRunning = false;
    
    // 线程池缓存
    private final Map<String, ExecutorService> executorServiceCache = new ConcurrentHashMap<>();

    @Override
    @Async
    public void executeTask(SysSyncTask task) {
        log.info("Starting sync task: {}", task.getTaskName());
        
        SysSyncExecution execution = new SysSyncExecution();
        execution.setTaskId(task.getId());
        execution.setTaskCode(task.getTaskCode());
        execution.setStartTime(new Date());
        execution.setStatus(1);
        
        try {
            Object sourceData = executeSync(
                task.getId(),
                task.getSourceSystem(),
                task.getTargetSystem(),
                null,
                null
            );
            
            execution.setSourceData(sourceData != null ? objectMapper.writeValueAsString(sourceData) : null);
            execution.setStatus(2);
            execution.setSuccessCount(1);
            execution.setTotalCount(1);
            execution.setEndTime(new Date());
            
            log.info("Sync task completed successfully: {}", task.getTaskName());
        } catch (Exception e) {
            log.error("Sync task failed: {}", task.getTaskName(), e);
            execution.setStatus(3);
            execution.setFailCount(1);
            execution.setErrorMessage(e.getMessage());
            execution.setEndTime(new Date());
        }
        
        syncExecutionService.save(execution);
    }

    @Override
    public void executeTaskById(Long taskId) {
        SysSyncTask task = syncTaskService.getById(taskId);
        if (task != null) {
            executeTask(task);
        }
    }

    @Override
    @Scheduled(cron = "0 */5 * * * ?")
    public void executeAllEnabledTasks() {
        if (!schedulerRunning) {
            return;
        }
        
        log.info("Executing all enabled sync tasks");
        List<SysSyncTask> enabledTasks = syncTaskService.getEnabledTasks();
        
        for (SysSyncTask task : enabledTasks) {
            try {
                executeTask(task);
            } catch (Exception e) {
                log.error("Failed to execute task: {}", task.getTaskName(), e);
            }
        }
    }

    @Override
    public boolean executeSync(Long taskId, String sourceSystem, String targetSystem, String interfaceCode, Object params) {
        try {
            SysIntegrationConfig sourceConfig = integrationConfigService.getBySystemCode(sourceSystem);
            if (sourceConfig == null) {
                log.error("Source system config not found: {}", sourceSystem);
                return false;
            }
            
            SystemAdapter sourceAdapter = adapterFactory.createAdapter(sourceConfig);
            if (sourceAdapter == null) {
                log.error("Failed to create adapter for source system: {}", sourceSystem);
                return false;
            }
            
            Object data = sourceAdapter.fetchData(interfaceCode, params);
            
            if (data != null && targetSystem != null && !targetSystem.equals(sourceSystem)) {
                SysIntegrationConfig targetConfig = integrationConfigService.getBySystemCode(targetSystem);
                if (targetConfig != null) {
                    SystemAdapter targetAdapter = adapterFactory.createAdapter(targetConfig);
                    if (targetAdapter != null) {
                        return targetAdapter.sendData(interfaceCode, data);
                    }
                }
            }
            
            return data != null;
        } catch (Exception e) {
            log.error("Sync execution failed", e);
            return false;
        }
    }

    @Override
    public void startScheduler() {
        schedulerRunning = true;
        log.info("Sync scheduler started");
    }

    @Override
    public void stopScheduler() {
        schedulerRunning = false;
        log.info("Sync scheduler stopped");
    }

    @Override
    @Async
    public boolean executeBatchSync(Long taskId, String sourceSystem, String targetSystem, String interfaceCode, List<Object> paramsList) {
        if (paramsList == null || paramsList.isEmpty()) {
            return true;
        }

        log.info("Starting batch sync task with {} items", paramsList.size());

        // 获取或创建线程池
        String poolKey = sourceSystem + "_" + targetSystem;
        ExecutorService executorService = getExecutorService(poolKey);
        
        // 计算批次大小
        int totalSize = paramsList.size();
        int batchCount = (totalSize + batchSize - 1) / batchSize;
        
        CountDownLatch latch = new CountDownLatch(batchCount);
        final boolean[] allSuccess = {true};

        // 分批处理
        for (int i = 0; i < totalSize; i += batchSize) {
            final int start = i;
            final int end = Math.min(i + batchSize, totalSize);
            final List<Object> batchParams = paramsList.subList(start, end);
            
            executorService.submit(() -> {
                try {
                    for (Object params : batchParams) {
                        boolean success = executeSync(taskId, sourceSystem, targetSystem, interfaceCode, params);
                        if (!success) {
                            allSuccess[0] = false;
                        }
                    }
                } catch (Exception e) {
                    log.error("Batch sync item failed", e);
                    allSuccess[0] = false;
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            // 设置超时时间
            if (!latch.await(timeout, TimeUnit.MILLISECONDS)) {
                log.error("Batch sync timeout after {}ms", timeout);
                allSuccess[0] = false;
            }
        } catch (InterruptedException e) {
            log.error("Batch sync interrupted", e);
            allSuccess[0] = false;
        }

        log.info("Batch sync completed, all success: {}", allSuccess[0]);
        return allSuccess[0];
    }
    
    /**
     * 获取或创建线程池
     */
    private ExecutorService getExecutorService(String key) {
        return executorServiceCache.computeIfAbsent(key, k -> {
            int poolSize = Math.min(Runtime.getRuntime().availableProcessors(), threadCount);
            ExecutorService executor = Executors.newFixedThreadPool(poolSize, r -> {
                Thread thread = new Thread(r);
                thread.setName("sync-worker-" + k + "-" + threadCount);
                return thread;
            });
            log.info("Created executor service for {} with {} threads", k, poolSize);
            return executor;
        });
    }
}
