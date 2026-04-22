package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.ReportTask;

import java.util.List;

/**
 * 报表定时任务Service接口
 * 
 * @author cpems
 * @date 2026-03-27
 */
public interface IReportTaskService extends IService<ReportTask> {

    /**
     * 查询报表定时任务列表
     * 
     * @param reportTask 报表定时任务
     * @return 报表定时任务集合
     */
    List<ReportTask> selectReportTaskList(ReportTask reportTask);

    /**
     * 新增报表定时任务
     * 
     * @param reportTask 报表定时任务
     * @return 结果
     */
    int insertReportTask(ReportTask reportTask);

    /**
     * 修改报表定时任务
     * 
     * @param reportTask 报表定时任务
     * @return 结果
     */
    int updateReportTask(ReportTask reportTask);

    /**
     * 删除报表定时任务
     * 
     * @param taskId 报表定时任务ID
     * @return 结果
     */
    int deleteReportTaskById(Long taskId);

    /**
     * 批量删除报表定时任务
     * 
     * @param taskIds 需要删除的报表定时任务ID
     * @return 结果
     */
    int deleteReportTaskByIds(Long[] taskIds);

    /**
     * 执行报表定时任务
     * 
     * @param taskId 任务ID
     */
    void executeTask(Long taskId);

    /**
     * 启动报表定时任务
     * 
     * @param taskId 任务ID
     */
    void startTask(Long taskId);

    /**
     * 停止报表定时任务
     * 
     * @param taskId 任务ID
     */
    void stopTask(Long taskId);

}
