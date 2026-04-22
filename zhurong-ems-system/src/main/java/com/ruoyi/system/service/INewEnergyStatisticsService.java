package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.ReportDataVo;

/**
 * 新能源统计报表Service接口
 * 
 * @author cpems
 * @date 2026-03-27
 */
public interface INewEnergyStatisticsService {

    /**
     * 获取发电量统计
     */
    ReportDataVo getGenerationStatistics(String startTime, String endTime, String stationId);

    /**
     * 获取储能统计
     */
    ReportDataVo getStorageStatistics(String startTime, String endTime, String systemId);

    /**
     * 获取微电网统计
     */
    ReportDataVo getMicroGridStatistics(String startTime, String endTime, String gridId);

    /**
     * 获取虚拟电厂统计
     */
    ReportDataVo getVirtualPlantStatistics(String startTime, String endTime, String plantId);
}
