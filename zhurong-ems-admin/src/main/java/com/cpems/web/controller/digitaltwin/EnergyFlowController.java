package com.cpems.web.controller.digitaltwin;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.EnergyFlow;
import com.ruoyi.system.service.IEnergyFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/system/digitaltwin/flow")
public class EnergyFlowController extends BaseController {

    private final IEnergyFlowService flowService;

    @GetMapping("/list")
    public R<List<EnergyFlow>> list() {
        return R.ok(flowService.list());
    }

    @GetMapping("/byType")
    public R<List<Map<String, Object>>> getByType(@RequestParam Integer flowType) {
        return R.ok(flowService.getFlowByType(flowType));
    }

    @GetMapping("/balance")
    public R<List<Map<String, Object>>> getBalance() {
        return R.ok(flowService.getEnergyBalance());
    }

    @PostMapping
    public R<Void> add(@RequestBody EnergyFlow flow) {
        flowService.save(flow);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody EnergyFlow flow) {
        flowService.updateById(flow);
        return R.ok();
    }

    @DeleteMapping("/{flowIds}")
    public R<Void> remove(@PathVariable Long[] flowIds) {
        flowService.removeByIds(Arrays.asList(flowIds));
        return R.ok();
    }
}
