package com.cpems.web.controller.report;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.ReportTemplate;
import com.ruoyi.system.service.IReportTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 报表模板
 *
 * @Author cpems
 * @Date 2026/3/27 10:26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/report/template")
public class ReportTemplateController extends BaseController {

    private final IReportTemplateService reportTemplateService;

    /**
     * 查询报表模板列表
     */
    @GetMapping("/list")
    public R<List<ReportTemplate>> list(ReportTemplate reportTemplate) {
        List<ReportTemplate> list = reportTemplateService.selectReportTemplateList(reportTemplate);
        return R.ok(list);
    }

    /**
     * 获取报表模板详细信息
     */
    @GetMapping("/info/{templateId}")
    public R<ReportTemplate> info(@PathVariable("templateId") Long templateId) {
        return R.ok(reportTemplateService.getById(templateId));
    }

    /**
     * 新增报表模板
     */
    @PostMapping("/add")
    public R<Void> add(@RequestBody ReportTemplate reportTemplate) {
        return toAjax(reportTemplateService.insertReportTemplate(reportTemplate));
    }

    /**
     * 修改报表模板
     */
    @PutMapping("/edit")
    public R<Void> edit(@RequestBody ReportTemplate reportTemplate) {
        return toAjax(reportTemplateService.updateReportTemplate(reportTemplate));
    }

    /**
     * 删除报表模板
     */
    @DeleteMapping("/remove/{templateId}")
    public R<Void> remove(@PathVariable Long templateId) {
        return toAjax(reportTemplateService.deleteReportTemplateById(templateId));
    }

    /**
     * 批量删除报表模板
     */
    @DeleteMapping("/remove")
    public R<Void> remove(@RequestBody Long[] templateIds) {
        return toAjax(reportTemplateService.deleteReportTemplateByIds(templateIds));
    }

    /**
     * 修改报表模板状态
     */
    @PutMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody ReportTemplate reportTemplate) {
        return toAjax(reportTemplateService.updateById(reportTemplate) ? 1 : 0);
    }

    /**
     * 导出报表模板
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response, ReportTemplate reportTemplate) {
        List<ReportTemplate> list = reportTemplateService.selectReportTemplateList(reportTemplate);
        ExcelUtil<ReportTemplate> util = new ExcelUtil<ReportTemplate>(ReportTemplate.class);
        util.exportExcel(response, list, "报表模板数据");
    }

}
