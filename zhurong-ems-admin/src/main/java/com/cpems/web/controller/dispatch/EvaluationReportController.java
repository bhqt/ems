package com.cpems.web.controller.dispatch;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.EvaluationReport;
import com.ruoyi.system.service.IEvaluationReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dispatch/evaluation")
public class EvaluationReportController extends BaseController {

    private final IEvaluationReportService reportService;

    @GetMapping("/list")
    public R<List<EvaluationReport>> list(EvaluationReport report) {
        List<EvaluationReport> list = reportService.list();
        return R.ok(list);
    }

    @GetMapping("/{reportId}")
    public R<EvaluationReport> getInfo(@PathVariable Long reportId) {
        return R.ok(reportService.getById(reportId));
    }

    @PostMapping
    public R<Void> add(@RequestBody EvaluationReport report) {
        reportService.save(report);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody EvaluationReport report) {
        reportService.updateById(report);
        return R.ok();
    }

    @DeleteMapping("/{reportIds}")
    public R<Void> remove(@PathVariable Long[] reportIds) {
        reportService.removeByIds(Arrays.asList(reportIds));
        return R.ok();
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, EvaluationReport report) {
        List<EvaluationReport> list = reportService.list();
        ExcelUtil<EvaluationReport> util = new ExcelUtil<>(EvaluationReport.class);
        util.exportExcel(response, list, "评估报告数据");
    }
}
