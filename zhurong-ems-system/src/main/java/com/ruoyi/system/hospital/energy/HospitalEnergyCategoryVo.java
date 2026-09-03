package com.ruoyi.system.hospital.energy;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 医院分项能耗汇总视图对象
 *
 * @author cpems
 */
@Data
@ExcelIgnoreUnannotated
public class HospitalEnergyCategoryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 分项编码（LIGHTING/AIRCOND/MEDICAL/POWER/OTHER） */
    private String category;

    /** 分项名称 */
    @ExcelProperty(value = "分项")
    private String categoryName;

    /** 周期用电量（kWh） */
    @ExcelProperty(value = "用电量(kWh)")
    private BigDecimal kwh;

    /** 平均功率（kW） */
    @ExcelProperty(value = "平均功率(kW)")
    private BigDecimal avgPower;

    /** 设备数量 */
    @ExcelProperty(value = "设备数量")
    private Integer deviceCount;
}
