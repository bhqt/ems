package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.ReportTask;
import com.ruoyi.system.mapper.ReportTaskMapper;
import com.ruoyi.system.service.IReportTaskService;
import com.ruoyi.system.service.ReportEngineService;
import com.ruoyi.system.domain.bo.ReportGenerateBo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 报表定时任务Service业务层处理
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Service
@RequiredArgsConstructor
public class ReportTaskServiceImpl extends ServiceImpl<ReportTaskMapper, ReportTask> implements IReportTaskService {

    private final ReportEngineService reportEngineService;

    @Override
    public List<ReportTask> selectReportTaskList(ReportTask reportTask) {
        return baseMapper.selectList(null);
    }

    @Override
    public int insertReportTask(ReportTask reportTask) {
        int result = baseMapper.insert(reportTask);
        // 如果任务状态为正常，启动定时任务
        if ("0".equals(reportTask.getStatus())) {
            startTask(reportTask.getTaskId());
        }
        return result;
    }

    @Override
    public int updateReportTask(ReportTask reportTask) {
        int result = baseMapper.updateById(reportTask);
        // 根据状态更新定时任务状态
        if ("0".equals(reportTask.getStatus())) {
            startTask(reportTask.getTaskId());
        } else {
            stopTask(reportTask.getTaskId());
        }
        return result;
    }

    @Override
    public int deleteReportTaskById(Long taskId) {
        // 先停止定时任务
        stopTask(taskId);
        return baseMapper.deleteById(taskId);
    }

    @Override
    public int deleteReportTaskByIds(Long[] taskIds) {
        for (Long taskId : taskIds) {
            stopTask(taskId);
        }
        return baseMapper.deleteBatchIds(Arrays.asList(taskIds));
    }

    @Override
    public void executeTask(Long taskId) {
        ReportTask task = baseMapper.selectById(taskId);
        if (task != null) {
            // 构建报表生成参数
            ReportGenerateBo bo = new ReportGenerateBo();
            bo.setTemplateId(task.getTemplateId());
            bo.setStartTime(task.getStartTime());
            bo.setEndTime(task.getEndTime());
            bo.setEnergyType(task.getEnergyType());
            bo.setDateType(task.getDateType());
            bo.setExportFormat(task.getExportFormat());
            
            // 执行报表生成
            reportEngineService.generateReport(bo);
            
            // 这里可以添加发送邮件的逻辑
            if (task.getEmail() != null && !task.getEmail().isEmpty()) {
                // 发送邮件逻辑
            }
        }
    }

    @Override
    public void startTask(Long taskId) {
        // 启动定时任务的逻辑
        // 这里需要集成XXL-Job或其他定时任务框架
    }

    @Override
    public void stopTask(Long taskId) {
        // 停止定时任务的逻辑
        // 这里需要集成XXL-Job或其他定时任务框架
    }

    /**
     * XXL-Job执行器
     */
    @XxlJob("reportTaskJobHandler")
    public ReturnT<String> execute(String param) throws Exception {
        System.out.println("XXL-Job执行报表定时任务: " + param);
        
        try {
            // 解析任务ID
            Long taskId = Long.parseLong(param);
            // 执行任务
            executeTask(taskId);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            System.err.println("执行报表定时任务失败: " + e.getMessage());
            return ReturnT.FAIL;
        }
    }

}
