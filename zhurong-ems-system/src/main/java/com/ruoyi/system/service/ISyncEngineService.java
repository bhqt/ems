package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.system.domain.SysSyncTask;

public interface ISyncEngineService {

    void executeTask(SysSyncTask task);

    void executeTaskById(Long taskId);

    void executeAllEnabledTasks();

    boolean executeSync(Long taskId, String sourceSystem, String targetSystem, String interfaceCode, Object params);

    boolean executeBatchSync(Long taskId, String sourceSystem, String targetSystem, String interfaceCode, List<Object> paramsList);

    void startScheduler();

    void stopScheduler();
}
