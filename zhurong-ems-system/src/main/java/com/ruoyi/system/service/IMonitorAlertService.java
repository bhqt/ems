package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysMonitorAlert;

import java.util.List;

/**
 * 监控告警服务接口
 * @author cpems
 */
public interface IMonitorAlertService {

    /**
     * 创建告警
     * @param alert 告警信息
     * @return 结果
     */
    boolean createAlert(SysMonitorAlert alert);

    /**
     * 查询告警列表
     * @param alert 告警信息
     * @return 告警列表
     */
    List<SysMonitorAlert> selectAlertList(SysMonitorAlert alert);

    /**
     * 根据ID查询告警
     * @param id 告警ID
     * @return 告警信息
     */
    SysMonitorAlert getById(Long id);

    /**
     * 处理告警
     * @param id 告警ID
     * @param handler 处理人
     * @param handleResult 处理结果
     * @return 结果
     */
    boolean handleAlert(Long id, String handler, String handleResult);

    /**
     * 批量删除告警
     * @param ids 告警ID列表
     * @return 结果
     */
    boolean deleteByIds(Long[] ids);

    /**
     * 发送系统集成告警
     * @param alertLevel 告警级别
     * @param alertTitle 告警标题
     * @param alertContent 告警内容
     * @param relatedSystem 关联系统
     * @param relatedTaskId 关联任务ID
     */
    void sendIntegrationAlert(String alertLevel, String alertTitle, String alertContent, String relatedSystem, Long relatedTaskId);

    /**
     * 检查系统健康状态
     * @return 健康状态
     */
    boolean checkSystemHealth();
}
