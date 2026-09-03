package com.ruoyi.system.hospital.energy;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 医院设备能效评估视图对象
 *
 * @author cpems
 */
@Data
@ExcelIgnoreUnannotated
public class HospitalEfficiencyVo implements Serializable {

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

    /** 周期用电量（kWh） */
    @ExcelProperty(value = "用电量(kWh)")
    private BigDecimal kwh;

    /** 平均功率（kW） */
    @ExcelProperty(value = "平均功率(kW)")
    private BigDecimal avgPower;

    /** 待机占比（%，无运行状态数据为 null） */
    @ExcelProperty(value = "待机占比(%)")
    private BigDecimal standbyRatio;

    /** 能效评分（0-100，无数据为 null） */
    @ExcelProperty(value = "能效评分")
    private BigDecimal score;

    /** 能效等级（优/良/待改进/未评估） */
    @ExcelProperty(value = "能效等级")
    private String level;
}
