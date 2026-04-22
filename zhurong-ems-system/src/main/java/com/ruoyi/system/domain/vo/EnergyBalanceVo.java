package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 能源平衡视图对象 ems_energy_balance
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@Schema(description = "能源平衡视图对象")
@ExcelIgnoreUnannotated
public class EnergyBalanceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 平衡日期
     */
    @Schema(description = "平衡日期")
    @ExcelProperty(value = "平衡日期")
    private Date balanceDate;

    /**
     * 能源介质
     */
    @Schema(description = "能源介质")
    @ExcelProperty(value = "能源介质")
    private String energyMedium;

    /**
     * 供应量
     */
    @Schema(description = "供应量")
    @ExcelProperty(value = "供应量")
    private BigDecimal supplyAmount;

    /**
     * 消耗量
     */
    @Schema(description = "消耗量")
    @ExcelProperty(value = "消耗量")
    private BigDecimal consumptionAmount;

    /**
     * 损耗量
     */
    @Schema(description = "损耗量")
    @ExcelProperty(value = "损耗量")
    private BigDecimal lossAmount;

    /**
     * 平衡率(%)
     */
    @Schema(description = "平衡率(%)")
    @ExcelProperty(value = "平衡率")
    private BigDecimal balanceRate;

    /**
     * 平衡状态
     */
    @Schema(description = "平衡状态")
    @ExcelProperty(value = "平衡状态")
    private String status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建者
     */
    @Schema(description = "创建者")
    private String createBy;
}
