package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.SysMonitorAlert;
import com.ruoyi.system.mapper.SysMonitorAlertMapper;
import com.ruoyi.system.service.IMonitorAlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 监控告警服务实现
 * @author cpems
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MonitorAlertServiceImpl extends ServiceImpl<SysMonitorAlertMapper, SysMonitorAlert> implements IMonitorAlertService {

    private final SysMonitorAlertMapper alertMapper;

    @Override
    public boolean createAlert(SysMonitorAlert alert) {
        alert.setStatus(1); // 1-未处理
        alert.setCreateTime(new Date());
        alert.setUpdateTime(new Date());
        return save(alert);
    }

    @Override
    public List<SysMonitorAlert> selectAlertList(SysMonitorAlert alert) {
        LambdaQueryWrapper<SysMonitorAlert> queryWrapper = new LambdaQueryWrapper<>();
        if (alert.getAlertType() != null) {
            queryWrapper.eq(SysMonitorAlert::getAlertType, alert.getAlertType());
        }
        if (alert.getAlertLevel() != null) {
            queryWrapper.eq(SysMonitorAlert::getAlertLevel, alert.getAlertLevel());
        }
        if (alert.getRelatedSystem() != null) {
            queryWrapper.eq(SysMonitorAlert::getRelatedSystem, alert.getRelatedSystem());
        }
        if (alert.getStatus() != null) {
            queryWrapper.eq(SysMonitorAlert::getStatus, alert.getStatus());
        }
        queryWrapper.orderByDesc(SysMonitorAlert::getCreateTime);
        return list(queryWrapper);
    }

    @Override
    public SysMonitorAlert getById(Long id) {
        return super.getById(id);
    }

    @Override
    public boolean handleAlert(Long id, String handler, String handleResult) {
        SysMonitorAlert alert = getById(id);
        if (alert != null) {
            alert.setStatus(2); // 2-已处理
            alert.setHandler(handler);
            alert.setHandleTime(new Date());
            alert.setHandleResult(handleResult);
            alert.setUpdateTime(new Date());
            return updateById(alert);
        }
        return false;
    }

    @Override
    public boolean deleteByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids));
    }

    @Override
    public void sendIntegrationAlert(String alertLevel, String alertTitle, String alertContent, String relatedSystem, Long relatedTaskId) {
        SysMonitorAlert alert = new SysMonitorAlert();
        alert.setAlertType("SYSTEM_INTEGRATION");
        alert.setAlertLevel(alertLevel);
        alert.setAlertTitle(alertTitle);
        alert.setAlertContent(alertContent);
        alert.setRelatedSystem(relatedSystem);
        alert.setRelatedTaskId(relatedTaskId);
        createAlert(alert);
        log.warn("Integration alert sent: {} - {}", alertLevel, alertTitle);
    }

    @Override
    public boolean checkSystemHealth() {
        // 这里可以实现系统健康检查逻辑
        // 例如检查数据库连接、外部系统连接等
        try {
            // 简单的健康检查
            return true;
        } catch (Exception e) {
            log.error("System health check failed", e);
            return false;
        }
    }
}
