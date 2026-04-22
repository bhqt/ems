package com.cpems.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.SysSyncTask;
import com.ruoyi.system.service.ISysSyncTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/integration/sync/task")
public class SysSyncTaskController extends BaseController {

    private final ISysSyncTaskService syncTaskService;

    @GetMapping("/list")
    public R<List<SysSyncTask>> list(SysSyncTask task) {
        List<SysSyncTask> list = syncTaskService.list();
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R<SysSyncTask> getInfo(@PathVariable Long id) {
        return R.ok(syncTaskService.getById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody SysSyncTask task) {
        syncTaskService.save(task);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody SysSyncTask task) {
        syncTaskService.updateById(task);
        return R.ok();
    }

    @DeleteMapping("/{ids}")
    public R<Void> remove(@PathVariable Long[] ids) {
        syncTaskService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @GetMapping("/enabled")
    public R<List<SysSyncTask>> getEnabledTasks() {
        return R.ok(syncTaskService.getEnabledTasks());
    }

    @PutMapping("/enable/{id}")
    public R<Void> enable(@PathVariable Long id) {
        syncTaskService.enable(id);
        return R.ok();
    }

    @PutMapping("/disable/{id}")
    public R<Void> disable(@PathVariable Long id) {
        syncTaskService.disable(id);
        return R.ok();
    }
}
