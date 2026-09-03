package com.ruoyi.system.hospital.energy;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 医院设备耗电排名视图对象
 *
 * @author cpems
 */
@Data
@ExcelIgnoreUnannotated
public class HospitalDeviceRankVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long deviceId;

    /** 设备名称 */
    @ExcelProperty(value = "设备名称")
    private String deviceName;

    /** 设备编号 */
    @ExcelProperty(value = "设备编号")
    private String deviceCode;

    /** 设备类型 */
    @ExcelProperty(value = "设备类型")
    private String deviceType;

    /** 用电量（kWh） */
    @ExcelProperty(value = "用电量(kWh)")
    private BigDecimal kwh;

    /** 平均功率（kW） */
    @ExcelProperty(value = "平均功率(kW)")
    private BigDecimal avgPower;
}
