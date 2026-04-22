package com.cpems.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.SysSyncExecution;
import com.ruoyi.system.service.ISysSyncExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/integration/sync/execution")
public class SysSyncExecutionController extends BaseController {

    private final ISysSyncExecutionService syncExecutionService;

    @GetMapping("/list")
    public R<List<SysSyncExecution>> list(SysSyncExecution execution) {
        List<SysSyncExecution> list = syncExecutionService.list();
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R<SysSyncExecution> getInfo(@PathVariable Long id) {
        return R.ok(syncExecutionService.getById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody SysSyncExecution execution) {
        syncExecutionService.save(execution);
        return R.ok();
    }

    @GetMapping("/byTaskId/{taskId}")
    public R<List<SysSyncExecution>> getByTaskId(@PathVariable Long taskId) {
        return R.ok(syncExecutionService.getByTaskId(taskId));
    }

    @GetMapping("/recent/{limit}")
    public R<List<SysSyncExecution>> getRecentExecutions(@PathVariable int limit) {
        return R.ok(syncExecutionService.getRecentExecutions(limit));
    }

    @GetMapping("/statistics")
    public R<Map<String, Object>> getStatistics(@RequestParam(required = false) Long taskId) {
        return R.ok(syncExecutionService.getStatistics(taskId));
    }
}
