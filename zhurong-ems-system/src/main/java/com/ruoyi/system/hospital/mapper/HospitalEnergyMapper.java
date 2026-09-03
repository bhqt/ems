package com.ruoyi.system.hospital.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 医院能耗分析 Mapper（聚合 hospital_device_data）
 *
 * @author cpems
 */
public interface HospitalEnergyMapper {

    /**
     * 按设备聚合周期统计（用电量=累计电量 max-min，平均/最大功率，运行/待机点数）
     *
     * @param deviceIds  设备 ID 列表（为空查全部）
     * @param start      开始时间
     * @param end        结束时间
     * @return 每行 deviceId/deviceName/deviceCode/deviceType/areaId/deptId/kwh/avgPower/maxPower/runPoints/standbyPoints
     */
    List<Map<String, Object>> selectDeviceStats(@Param("deviceIds") List<Long> deviceIds,
                                                @Param("start") Date start,
                                                @Param("end") Date end);

    /**
     * 按天聚合趋势（多设备日电量汇总 + 平均功率）
     */
    List<Map<String, Object>> selectDailyTrend(@Param("deviceIds") List<Long> deviceIds,
                                               @Param("start") Date start,
                                               @Param("end") Date end);

    /**
     * 按小时聚合平均功率（高耗能时段识别）
     */
    List<Map<String, Object>> selectHourlyPower(@Param("deviceIds") List<Long> deviceIds,
                                                @Param("start") Date start,
                                                @Param("end") Date end);
}
