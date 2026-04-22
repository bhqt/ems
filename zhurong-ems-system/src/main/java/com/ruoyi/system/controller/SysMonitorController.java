package com.ruoyi.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.system.domain.SysMonitorAlert;
import com.ruoyi.system.service.IMonitorAlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 监控告警控制器
 * @author cpems
 */
@Slf4j
@RestController
@RequestMapping("/system/monitor")
@RequiredArgsConstructor
public class SysMonitorController {

    private final IMonitorAlertService monitorAlertService;

    /**
     * 获取告警列表
     */
    @SaCheckPermission("system:monitor:list")
    @GetMapping("/alert/list")
    public AjaxResult list(SysMonitorAlert alert) {
        List<SysMonitorAlert> list = monitorAlertService.selectAlertList(alert);
        return AjaxResult.success(list);
    }

    /**
     * 获取告警详情
     */
    @SaCheckPermission("system:monitor:query")
    @GetMapping("/alert/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(monitorAlertService.getById(id));
    }

    /**
     * 处理告警
     */
    @SaCheckPermission("system:monitor:handle")
    @PutMapping("/alert/handle/{id}")
    public AjaxResult handle(@PathVariable Long id, @RequestBody String handleResult) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        boolean result = monitorAlertService.handleAlert(id, loginUser.getUsername(), handleResult);
        return AjaxResult.success(result);
    }

    /**
     * 删除告警
     */
    @SaCheckPermission("system:monitor:remove")
    @DeleteMapping("/alert/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(monitorAlertService.deleteByIds(ids));
    }

    /**
     * 检查系统健康状态
     */
    @SaCheckPermission("system:monitor:health")
    @GetMapping("/health")
    public AjaxResult checkHealth() {
        boolean healthy = monitorAlertService.checkSystemHealth();
        return AjaxResult.success(healthy ? "系统运行正常" : "系统运行异常");
    }
}
