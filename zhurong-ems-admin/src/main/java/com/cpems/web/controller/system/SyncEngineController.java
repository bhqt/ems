package com.cpems.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.SysSyncTask;
import com.ruoyi.system.service.ISyncEngineService;
import com.ruoyi.system.service.ISysSyncTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/integration/sync/engine")
public class SyncEngineController extends BaseController {

    private final ISyncEngineService syncEngineService;
    private final ISysSyncTaskService syncTaskService;

    @PostMapping("/execute/{taskId}")
    public R<Void> executeTask(@PathVariable Long taskId) {
        syncEngineService.executeTaskById(taskId);
        return R.ok();
    }

    @PostMapping("/executeAll")
    public R<Void> executeAllTasks() {
        syncEngineService.executeAllEnabledTasks();
        return R.ok();
    }

    @PostMapping("/start")
    public R<Void> startScheduler() {
        syncEngineService.startScheduler();
        return R.ok();
    }

    @PostMapping("/stop")
    public R<Void> stopScheduler() {
        syncEngineService.stopScheduler();
        return R.ok();
    }

    @GetMapping("/status")
    public R<String> getSchedulerStatus() {
        return R.ok("running");
    }
}
