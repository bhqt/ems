package com.cpems.web.controller.dispatch;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.OptimizationScheme;
import com.ruoyi.system.service.IOptimizationSchemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dispatch/optimization")
public class OptimizationSchemeController extends BaseController {

    private final IOptimizationSchemeService schemeService;

    @GetMapping("/list")
    public R<List<OptimizationScheme>> list(OptimizationScheme scheme) {
        List<OptimizationScheme> list = schemeService.list();
        return R.ok(list);
    }

    @GetMapping("/{schemeId}")
    public R<OptimizationScheme> getInfo(@PathVariable Long schemeId) {
        return R.ok(schemeService.getById(schemeId));
    }

    @PostMapping
    public R<Void> add(@RequestBody OptimizationScheme scheme) {
        schemeService.save(scheme);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody OptimizationScheme scheme) {
        schemeService.updateById(scheme);
        return R.ok();
    }

    @DeleteMapping("/{schemeIds}")
    public R<Void> remove(@PathVariable Long[] schemeIds) {
        schemeService.removeByIds(Arrays.asList(schemeIds));
        return R.ok();
    }

    @PutMapping("/execute/{schemeId}")
    public R<Void> execute(@PathVariable Long schemeId) {
        schemeService.executeOptimization(schemeId);
        return R.ok();
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, OptimizationScheme scheme) {
        List<OptimizationScheme> list = schemeService.list();
        ExcelUtil<OptimizationScheme> util = new ExcelUtil<>(OptimizationScheme.class);
        util.exportExcel(response, list, "优化方案数据");
    }
}
