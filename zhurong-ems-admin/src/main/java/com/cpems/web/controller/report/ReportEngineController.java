package com.cpems.web.controller.report;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.bo.ReportGenerateBo;
import com.ruoyi.system.domain.vo.ReportDataVo;
import com.ruoyi.system.service.ReportEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 报表生成引擎
 *
 * @Author cpems
 * @Date 2026/3/27 10:26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/report/engine")
public class ReportEngineController extends BaseController {

    private final ReportEngineService reportEngineService;

    /**
     * 生成报表
     */
    @PostMapping("/generate")
    public R<ReportDataVo> generate(@RequestBody ReportGenerateBo bo) {
        ReportDataVo reportData = reportEngineService.generateReport(bo);
        return R.ok(reportData);
    }

    /**
     * 预览报表
     */
    @PostMapping("/preview")
    public R<ReportDataVo> preview(@RequestBody ReportGenerateBo bo) {
        ReportDataVo reportData = reportEngineService.previewReport(bo);
        return R.ok(reportData);
    }

    /**
     * 生成能耗报表
     */
    @PostMapping("/energy")
    public R<ReportDataVo> generateEnergyReport(@RequestBody ReportGenerateBo bo) {
        ReportDataVo reportData = reportEngineService.generateEnergyReport(null);
        return R.ok(reportData);
    }

    /**
     * 生成费用报表
     */
    @PostMapping("/expense")
    public R<ReportDataVo> generateExpenseReport(@RequestBody ReportGenerateBo bo) {
        ReportDataVo reportData = reportEngineService.generateExpenseReport(null);
        return R.ok(reportData);
    }

    /**
     * 生成损耗报表
     */
    @PostMapping("/loss")
    public R<ReportDataVo> generateLossReport(@RequestBody ReportGenerateBo bo) {
        ReportDataVo reportData = reportEngineService.generateLossReport(null);
        return R.ok(reportData);
    }

    /**
     * 生成碳排放报表
     */
    @PostMapping("/carbon")
    public R<ReportDataVo> generateCarbonReport(@RequestBody ReportGenerateBo bo) {
        ReportDataVo reportData = reportEngineService.generateCarbonReport(null);
        return R.ok(reportData);
    }

    /**
     * 导出Excel报表 - 使用简单的CSV格式
     */
    @PostMapping("/export/excel")
    public void exportExcel(HttpServletResponse response, @RequestBody ReportGenerateBo bo) {
        ReportDataVo reportData = reportEngineService.generateReport(bo);
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + reportData.getTitle() + ".xls");

            List<Map<String, Object>> dataList = reportData.getData();
            if (dataList != null && !dataList.isEmpty()) {
                Map<String, Object> firstRow = dataList.get(0);
                StringBuilder sb = new StringBuilder();

                // 写入表头
                for (String key : firstRow.keySet()) {
                    sb.append(key).append("\t");
                }
                sb.append("\n");

                // 写入数据
                for (Map<String, Object> row : dataList) {
                    for (Object value : row.values()) {
                        sb.append(value != null ? value.toString() : "").append("\t");
                    }
                    sb.append("\n");
                }

                response.getWriter().write(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出PDF报表 (使用 OpenPDF)
     */
    @PostMapping("/export/pdf")
    public void exportPdf(HttpServletResponse response, @RequestBody ReportGenerateBo bo) {
        ReportDataVo reportData = reportEngineService.generateReport(bo);

        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + reportData.getTitle() + ".pdf");

            OutputStream out = response.getOutputStream();
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, out);
            document.open();

            // 添加中文字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font titleFont = new Font(bfChinese, 20, Font.BOLD);
            Font headerFont = new Font(bfChinese, 12, Font.BOLD);
            Font contentFont = new Font(bfChinese, 10, Font.NORMAL);

            // 添加标题
            Paragraph title = new Paragraph(reportData.getTitle(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(30);
            document.add(title);

            // 添加表格
            if (reportData.getData() != null && !reportData.getData().isEmpty()) {
                List<Map<String, Object>> data = reportData.getData();
                Map<String, Object> firstRow = data.get(0);
                int columnCount = firstRow.size();

                PdfPTable table = new PdfPTable(columnCount);
                table.setWidthPercentage(100);

                // 添加表头
                for (String key : firstRow.keySet()) {
                    PdfPCell cell = new PdfPCell(new Paragraph(key, headerFont));
                    cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(5);
                    table.addCell(cell);
                }

                // 添加数据行
                for (Map<String, Object> row : data) {
                    for (Object value : row.values()) {
                        PdfPCell cell = new PdfPCell(new Paragraph(value != null ? value.toString() : "", contentFont));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setPadding(3);
                        table.addCell(cell);
                    }
                }

                document.add(table);
            }

            document.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
