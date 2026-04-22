package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.ReportDataVo;

/**
 * 质量统计分析Service接口
 * 
 * @author cpems
 * @date 2026-03-27
 */
public interface IQualityStatisticsService {

    /**
     * 获取质量统计
     */
    ReportDataVo getQualityStatistics(String startTime, String endTime, String productId);

    /**
     * 获取质量分析
     */
    ReportDataVo getQualityAnalysis(String startTime, String endTime, String productId);

    /**
     * 获取质量报表
     */
    ReportDataVo getQualityReport(String startTime, String endTime, String productId);
}
