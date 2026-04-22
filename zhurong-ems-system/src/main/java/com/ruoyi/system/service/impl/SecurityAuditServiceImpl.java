package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.system.domain.SysSecurityAudit;
import com.ruoyi.system.mapper.SysSecurityAuditMapper;
import com.ruoyi.system.service.ISecurityAuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 安全审计服务实现
 * @author cpems
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityAuditServiceImpl implements ISecurityAuditService {

    private final SysSecurityAuditMapper securityAuditMapper;

    @Override
    public void recordAuditLog(String operationType, String operationContent, boolean success, String ipAddress) {
        try {
            SysSecurityAudit audit = new SysSecurityAudit();
            audit.setOperationType(operationType);
            audit.setOperationContent(operationContent);
            audit.setSuccess(success ? 1 : 0);
            audit.setIpAddress(ipAddress);
            audit.setOperationTime(new Date());

            // 获取当前用户信息
            LoginUser loginUser = LoginHelper.getLoginUser();
            if (loginUser != null) {
                audit.setUserId(loginUser.getUserId());
                audit.setUserName(loginUser.getUsername());
            }

            securityAuditMapper.insert(audit);
        } catch (Exception e) {
            log.error("Failed to record audit log", e);
        }
    }

    @Override
    public void recordSyncOperation(Long taskId, String taskName, String operation, boolean success, String details) {
        String content = String.format("同步任务[%s]执行操作: %s, 结果: %s, 详情: %s", 
            taskName, operation, success ? "成功" : "失败", details);
        recordAuditLog("SYNC_OPERATION", content, success, ServletUtils.getClientIP());
    }

    @Override
    public void recordConfigOperation(String configName, String operation, boolean success, String details) {
        String content = String.format("系统配置[%s]执行操作: %s, 结果: %s, 详情: %s", 
            configName, operation, success ? "成功" : "失败", details);
        recordAuditLog("CONFIG_OPERATION", content, success, ServletUtils.getClientIP());
    }
}
