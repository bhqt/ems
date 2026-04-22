package com.cpems.web.controller.dispatch;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.EnergyPlan;
import com.ruoyi.system.service.IEnergyPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dispatch/energyPlan")
public class EnergyPlanController extends BaseController {

    private final IEnergyPlanService energyPlanService;

    @GetMapping("/list")
    public R<List<EnergyPlan>> list(EnergyPlan energyPlan) {
        List<EnergyPlan> list = energyPlanService.list();
        return R.ok(list);
    }

    @GetMapping("/{planId}")
    public R<EnergyPlan> getInfo(@PathVariable Long planId) {
        return R.ok(energyPlanService.getById(planId));
    }

    @PostMapping
    public R<Void> add(@RequestBody EnergyPlan energyPlan) {
        energyPlanService.save(energyPlan);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody EnergyPlan energyPlan) {
        energyPlanService.updateById(energyPlan);
        return R.ok();
    }

    @DeleteMapping("/{planIds}")
    public R<Void> remove(@PathVariable Long[] planIds) {
        energyPlanService.removeByIds(Arrays.asList(planIds));
        return R.ok();
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, EnergyPlan energyPlan) {
        List<EnergyPlan> list = energyPlanService.list();
        ExcelUtil<EnergyPlan> util = new ExcelUtil<>(EnergyPlan.class);
        util.exportExcel(response, list, "能源计划数据");
    }
}
