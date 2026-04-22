package com.ruoyi.system.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;

/**
 * 能源平衡业务对象 ems_energy_balance
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "能源平衡业务对象")
public class EnergyBalanceBo extends BaseEntity {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 平衡日期
     */
    @Schema(description = "平衡日期", required = true)
    @NotNull(message = "平衡日期不能为空", groups = {AddGroup.class, EditGroup.class})
    private Date balanceDate;

    /**
     * 能源介质
     */
    @Schema(description = "能源介质", required = true)
    @NotBlank(message = "能源介质不能为空", groups = {AddGroup.class, EditGroup.class})
    private String energyMedium;

    /**
     * 供应量
     */
    @Schema(description = "供应量", required = true)
    @NotNull(message = "供应量不能为空", groups = {AddGroup.class, EditGroup.class})
    private BigDecimal supplyAmount;

    /**
     * 消耗量
     */
    @Schema(description = "消耗量", required = true)
    @NotNull(message = "消耗量不能为空", groups = {AddGroup.class, EditGroup.class})
    private BigDecimal consumptionAmount;

    /**
     * 损耗量
     */
    @Schema(description = "损耗量")
    private BigDecimal lossAmount;

    /**
     * 平衡率(%)
     */
    @Schema(description = "平衡率(%)")
    private BigDecimal balanceRate;

    /**
     * 平衡状态
     */
    @Schema(description = "平衡状态")
    private String status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 所属用户
     */
    @Schema(description = "所属用户")
    private Long userId;

    /**
     * 所属部门
     */
    @Schema(description = "所属部门")
    private Long deptId;
}
