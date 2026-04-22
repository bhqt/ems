package com.ruoyi.system.service;

/**
 * 安全审计服务接口
 * @author cpems
 */
public interface ISecurityAuditService {

    /**
     * 记录审计日志
     * @param operationType 操作类型
     * @param operationContent 操作内容
     * @param success 是否成功
     * @param ipAddress IP地址
     */
    void recordAuditLog(String operationType, String operationContent, boolean success, String ipAddress);

    /**
     * 记录同步操作审计日志
     * @param taskId 任务ID
     * @param taskName 任务名称
     * @param operation 操作类型
     * @param success 是否成功
     * @param details 详细信息
     */
    void recordSyncOperation(Long taskId, String taskName, String operation, boolean success, String details);

    /**
     * 记录配置操作审计日志
     * @param configName 配置名称
     * @param operation 操作类型
     * @param success 是否成功
     * @param details 详细信息
     */
    void recordConfigOperation(String configName, String operation, boolean success, String details);
}
