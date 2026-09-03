package com.ruoyi.system.hospital.energy;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 医院能耗概览视图对象（按院区/科室/设备分组聚合）
 *
 * @author cpems
 */
@Data
public class HospitalEnergyOverviewVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 维度键（院区/科室 ID 或设备 ID） */
    private String dimKey;

    /** 维度名称 */
    private String dimName;

    /** 本期用电量（kWh，累计电量 max-min 汇总） */
    private BigDecimal kwh;

    /** 平均功率（kW） */
    private BigDecimal avgPower;

    /** 设备数量 */
    private Integer deviceCount;

    /** 环比（%，相对上一等长周期，可为 null） */
    private BigDecimal chainRatio;
}
