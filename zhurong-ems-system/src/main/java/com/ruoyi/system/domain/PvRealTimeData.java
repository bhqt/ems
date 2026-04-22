package com.ruoyi.system.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 光伏实时数据对象 pv_realtime_data
 *
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("pv_realtime_data")
@ExcelIgnoreUnannotated
public class PvRealTimeData {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 电站ID
     */
    private Long stationId;

    /**
     * 逆变器ID
     */
    private Long inverterId;

    /**
     * 数据时间
     */
    private Date dataTime;

    /**
     * 直流电压(V)
     */
    private BigDecimal dcVoltage;

    /**
     * 直流电流(A)
     */
    private BigDecimal dcCurrent;

    /**
     * 直流功率(kW)
     */
    private BigDecimal dcPower;

    /**
     * 交流电压(V)
     */
    private BigDecimal acVoltage;

    /**
     * 交流电流(A)
     */
    private BigDecimal acCurrent;

    /**
     * 交流功率(kW)
     */
    private BigDecimal acPower;

    /**
     * 有功功率(kW)
     */
    private BigDecimal activePower;

    /**
     * 无功功率(kVar)
     */
    private BigDecimal reactivePower;

    /**
     * 功率因数
     */
    private BigDecimal powerFactor;

    /**
     * 电网频率(Hz)
     */
    private BigDecimal gridFrequency;

    /**
     * 日发电量(kWh)
     */
    private BigDecimal dailyEnergy;

    /**
     * 累计发电量(kWh)
     */
    private BigDecimal totalEnergy;

    /**
     * 逆变器温度(℃)
     */
    private BigDecimal inverterTemp;

    /**
     * 转换效率(%)
     */
    private BigDecimal efficiency;

    /**
     * 辐照度(W/㎡)
     */
    private BigDecimal irradiance;

    /**
     * 环境温度(℃)
     */
    private BigDecimal ambientTemp;

    /**
     * 组件温度(℃)
     */
    private BigDecimal componentTemp;

    /**
     * 运行状态（0-停机 1-正常 2-故障 3-待机）
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;
}
