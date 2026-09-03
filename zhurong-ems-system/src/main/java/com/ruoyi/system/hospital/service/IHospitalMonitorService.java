package com.ruoyi.system.hospital.service;

import com.ruoyi.system.hospital.vo.HospitalDeviceDataVo;
import com.ruoyi.system.hospital.vo.HospitalDeviceRealtimeVo;

import java.util.List;

/**
 * 医院设备实时监测 Service
 *
 * @author cpems
 */
public interface IHospitalMonitorService {

    /**
     * 查询设备实时监测总览（聚合各设备最新数据点 + 在离线判定）
     *
     * @param deviceType 设备类型（可选）
     * @param keyword    设备名称/编号关键字（可选）
     * @return 设备实时状态列表
     */
    List<HospitalDeviceRealtimeVo> queryOverview(String deviceType, String keyword);

    /**
     * 查询单设备近期趋势数据点
     *
     * @param deviceId   设备 ID
     * @param metricCode 指标编码（可选）
     * @param limit      条数（默认 100）
     * @return 数据点列表（按时间倒序）
     */
    List<HospitalDeviceDataVo> queryTrend(Long deviceId, String metricCode, Integer limit);
}
