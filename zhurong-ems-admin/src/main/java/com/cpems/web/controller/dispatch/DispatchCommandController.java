package com.cpems.web.controller.dispatch;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.DispatchCommand;
import com.ruoyi.system.service.IDispatchCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dispatch/command")
public class DispatchCommandController extends BaseController {

    private final IDispatchCommandService commandService;

    @GetMapping("/list")
    public R<List<DispatchCommand>> list(DispatchCommand command) {
        List<DispatchCommand> list = commandService.list();
        return R.ok(list);
    }

    @GetMapping("/{commandId}")
    public R<DispatchCommand> getInfo(@PathVariable Long commandId) {
        return R.ok(commandService.getById(commandId));
    }

    @PostMapping
    public R<Void> add(@RequestBody DispatchCommand command) {
        commandService.save(command);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody DispatchCommand command) {
        commandService.updateById(command);
        return R.ok();
    }

    @DeleteMapping("/{commandIds}")
    public R<Void> remove(@PathVariable Long[] commandIds) {
        commandService.removeByIds(Arrays.asList(commandIds));
        return R.ok();
    }

    @PutMapping("/execute/{commandId}")
    public R<Void> execute(@PathVariable Long commandId) {
        commandService.executeCommand(commandId);
        return R.ok();
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, DispatchCommand command) {
        List<DispatchCommand> list = commandService.list();
        ExcelUtil<DispatchCommand> util = new ExcelUtil<>(DispatchCommand.class);
        util.exportExcel(response, list, "调度指令数据");
    }
}
