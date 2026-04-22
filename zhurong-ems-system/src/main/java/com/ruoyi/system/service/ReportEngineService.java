package com.ruoyi.system.service;

import com.ruoyi.system.domain.ReportTemplate;
import com.ruoyi.system.domain.bo.ReportGenerateBo;
import com.ruoyi.system.domain.vo.ReportDataVo;

import java.util.Map;

/**
 * 报表生成引擎服务
 * 
 * @author cpems
 * @date 2026-03-27
 */
public interface ReportEngineService {

    /**
     * 生成报表数据
     * 
     * @param bo 报表生成参数
     * @return 报表数据
     */
    ReportDataVo generateReport(ReportGenerateBo bo);

    /**
     * 根据模板生成报表
     * 
     * @param template 报表模板
     * @param params 报表参数
     * @return 报表数据
     */
    ReportDataVo generateReportByTemplate(ReportTemplate template, Map<String, Object> params);

    /**
     * 预览报表
     * 
     * @param bo 报表生成参数
     * @return 报表预览数据
     */
    ReportDataVo previewReport(ReportGenerateBo bo);

    /**
     * 生成能耗报表
     * 
     * @param params 报表参数
     * @return 报表数据
     */
    ReportDataVo generateEnergyReport(Map<String, Object> params);

    /**
     * 生成费用报表
     * 
     * @param params 报表参数
     * @return 报表数据
     */
    ReportDataVo generateExpenseReport(Map<String, Object> params);

    /**
     * 生成损耗报表
     * 
     * @param params 报表参数
     * @return 报表数据
     */
    ReportDataVo generateLossReport(Map<String, Object> params);

    /**
     * 生成碳排放报表
     * 
     * @param params 报表参数
     * @return 报表数据
     */
    ReportDataVo generateCarbonReport(Map<String, Object> params);

}
