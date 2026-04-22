package com.cpems.web.controller.dispatch;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.CostSavingRecord;
import com.ruoyi.system.service.IEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dispatch/evaluation")
public class EvaluationController extends BaseController {

    private final IEvaluationService evaluationService;

    @GetMapping("/costSaving/list")
    public R<List<CostSavingRecord>> listCostSaving() {
        return R.ok(evaluationService.listCostSaving());
    }

    @GetMapping("/trend/costSaving")
    public R<List<Map<String, Object>>> getCostSavingTrend(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return R.ok(evaluationService.getCostSavingTrend(startDate, endDate));
    }

    @GetMapping("/trend/efficiency")
    public R<List<Map<String, Object>>> getEfficiencyTrend(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return R.ok(evaluationService.getEfficiencyTrend(startDate, endDate));
    }

    @GetMapping("/trend/emission")
    public R<List<Map<String, Object>>> getEmissionTrend(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return R.ok(evaluationService.getEmissionTrend(startDate, endDate));
    }

    @GetMapping("/summary")
    public R<Map<String, Object>> getSummary() {
        return R.ok(evaluationService.getEvaluationSummary());
    }
}
