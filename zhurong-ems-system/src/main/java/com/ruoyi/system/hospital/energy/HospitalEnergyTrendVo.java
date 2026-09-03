package com.ruoyi.system.hospital.energy;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 医院能耗趋势视图对象（按天/按小时）
 *
 * @author cpems
 */
@Data
public class HospitalEnergyTrendVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 时间标签（yyyy-MM-dd 或 HH:00） */
    private String label;

    /** 用电量（kWh，日粒度有效） */
    private BigDecimal kwh;

    /** 平均功率（kW） */
    private BigDecimal avgPower;
}
