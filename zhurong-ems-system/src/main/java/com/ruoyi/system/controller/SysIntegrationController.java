package com.ruoyi.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.system.domain.SysIntegrationConfig;
import com.ruoyi.system.domain.SysSyncTask;
import com.ruoyi.system.service.ISysIntegrationConfigService;
import com.ruoyi.system.service.ISysSyncTaskService;
import com.ruoyi.system.service.ISyncEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统集成控制器
 * @author cpems
 */
@Slf4j
@RestController
@RequestMapping("/system/integration")
@RequiredArgsConstructor
public class SysIntegrationController {

    private final ISysIntegrationConfigService integrationConfigService;
    private final ISysSyncTaskService syncTaskService;
    private final ISyncEngineService syncEngineService;

    /**
     * 获取系统集成配置列表
     */
    @SaCheckPermission("system:integration:list")
    @GetMapping("/sys-config/list")
    public AjaxResult list(SysIntegrationConfig config) {
        List<SysIntegrationConfig> list = integrationConfigService.list();
        return AjaxResult.success(list);
    }

    /**
     * 获取系统集成配置详情
     */
    @SaCheckPermission("system:integration:query")
    @GetMapping("/sys-config/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(integrationConfigService.getById(id));
    }

    /**
     * 新增系统集成配置
     */
    @SaCheckPermission("system:integration:add")
    @PostMapping("/sys-config")
    public AjaxResult add(@RequestBody SysIntegrationConfig config) {
        return AjaxResult.success(integrationConfigService.save(config));
    }

    /**
     * 修改系统集成配置
     */
    @SaCheckPermission("system:integration:edit")
    @PutMapping("/sys-config")
    public AjaxResult edit(@RequestBody SysIntegrationConfig config) {
        return AjaxResult.success(integrationConfigService.updateById(config));
    }

    /**
     * 删除系统集成配置
     */
    @SaCheckPermission("system:integration:remove")
    @DeleteMapping("/sys-config/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(integrationConfigService.removeByIds(java.util.Arrays.asList(ids)));
    }

    /**
     * 获取同步任务列表
     */
    @SaCheckPermission("system:sync:list")
    @GetMapping("/task/list")
    public AjaxResult taskList(SysSyncTask task) {
        List<SysSyncTask> list = syncTaskService.list();
        return AjaxResult.success(list);
    }

    /**
     * 执行同步任务
     */
    @SaCheckPermission("system:sync:execute")
    @PostMapping("/task/execute/{taskId}")
    public AjaxResult executeTask(@PathVariable Long taskId, HttpServletRequest request) {
        // 记录操作日志
        LoginUser loginUser = LoginHelper.getLoginUser();
        log.info("User {} executed sync task {}", loginUser.getUsername(), taskId);
        
        syncEngineService.executeTaskById(taskId);
        return AjaxResult.success("任务已开始执行");
    }

    /**
     * 启动同步调度器
     */
    @SaCheckPermission("system:sync:start")
    @PostMapping("/scheduler/start")
    public AjaxResult startScheduler() {
        syncEngineService.startScheduler();
        return AjaxResult.success("调度器已启动");
    }

    /**
     * 停止同步调度器
     */
    @SaCheckPermission("system:sync:stop")
    @PostMapping("/scheduler/stop")
    public AjaxResult stopScheduler() {
        syncEngineService.stopScheduler();
        return AjaxResult.success("调度器已停止");
    }
}
