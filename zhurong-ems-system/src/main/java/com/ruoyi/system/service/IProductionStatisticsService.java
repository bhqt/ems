package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.ReportDataVo;

/**
 * 生产统计分析Service接口
 * 
 * @author cpems
 * @date 2026-03-27
 */
public interface IProductionStatisticsService {

    /**
     * 获取产量统计
     */
    ReportDataVo getProductionStatistics(String startTime, String endTime, String productId);

    /**
     * 获取质量统计
     */
    ReportDataVo getQualityStatistics(String startTime, String endTime, String productId);

    /**
     * 获取效率统计
     */
    ReportDataVo getEfficiencyStatistics(String startTime, String endTime, String productId);

    /**
     * 获取成本统计
     */
    ReportDataVo getCostStatistics(String startTime, String endTime, String productId);
}
