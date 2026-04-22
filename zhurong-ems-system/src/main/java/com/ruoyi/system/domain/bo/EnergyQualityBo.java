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
 * 能源质量业务对象 ems_energy_quality
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "能源质量业务对象")
public class EnergyQualityBo extends BaseEntity {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 检测日期
     */
    @Schema(description = "检测日期", required = true)
    @NotNull(message = "检测日期不能为空", groups = {AddGroup.class, EditGroup.class})
    private Date qualityDate;

    /**
     * 能源介质
     */
    @Schema(description = "能源介质", required = true)
    @NotBlank(message = "能源介质不能为空", groups = {AddGroup.class, EditGroup.class})
    private String energyMedium;

    /**
     * 参数名称
     */
    @Schema(description = "参数名称", required = true)
    @NotBlank(message = "参数名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String parameterName;

    /**
     * 标准值
     */
    @Schema(description = "标准值", required = true)
    @NotBlank(message = "标准值不能为空", groups = {AddGroup.class, EditGroup.class})
    private String standardValue;

    /**
     * 实际值
     */
    @Schema(description = "实际值", required = true)
    @NotBlank(message = "实际值不能为空", groups = {AddGroup.class, EditGroup.class})
    private String actualValue;

    /**
     * 偏差(%)
     */
    @Schema(description = "偏差(%)")
    private BigDecimal deviation;

    /**
     * 质量状态
     */
    @Schema(description = "质量状态")
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
