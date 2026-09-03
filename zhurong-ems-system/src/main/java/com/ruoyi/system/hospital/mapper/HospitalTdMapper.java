package com.ruoyi.system.hospital.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 医院设备时序数据 Mapper（TDengine，可开关）
 *
 * @author cpems
 */
@DS("td")
public interface HospitalTdMapper {

    /**
     * 写入设备数据点到 TDengine 超表 hospital_device_data（自动创建子表）
     * <p>
     * 语法：insert into 子表名 using 超表名 tags (tag...) (列...) values (...)
     *
     * @param tbName     子表名（由 deviceCode + metricCode 拼接并清洗）
     * @param ts         采集时间
     * @param deviceCode 设备编号（TAG）
     * @param metricCode 指标编码（TAG）
     * @param value      数值
     * @param quality    数据质量
     */
    @Insert("insert into ${tbName} using hospital_device_data tags (#{deviceCode}, #{metricCode}) " +
        "(ts, metric_value, quality) values (#{ts}, #{value}, #{quality})")
    void insertPoint(@Param("tbName") String tbName,
                     @Param("ts") Date ts,
                     @Param("deviceCode") String deviceCode,
                     @Param("metricCode") String metricCode,
                     @Param("value") Double value,
                     @Param("quality") Integer quality);
}
