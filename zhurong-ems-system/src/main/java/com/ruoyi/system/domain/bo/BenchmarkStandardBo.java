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
 * 标杆标准业务对象 ems_benchmark_standard
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "标杆标准业务对象")
public class BenchmarkStandardBo extends BaseEntity {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 标准编码
     */
    @Schema(description = "标准编码", required = true)
    @NotBlank(message = "标准编码不能为空", groups = {AddGroup.class, EditGroup.class})
    private String standardCode;

    /**
     * 标准名称
     */
    @Schema(description = "标准名称", required = true)
    @NotBlank(message = "标准名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String standardName;

    /**
     * 标准类型
     */
    @Schema(description = "标准类型", required = true)
    @NotBlank(message = "标准类型不能为空", groups = {AddGroup.class, EditGroup.class})
    private String standardType;

    /**
     * 指标名称
     */
    @Schema(description = "指标名称", required = true)
    @NotBlank(message = "指标名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String indicatorName;

    /**
     * 指标单位
     */
    @Schema(description = "指标单位", required = true)
    @NotBlank(message = "指标单位不能为空", groups = {AddGroup.class, EditGroup.class})
    private String indicatorUnit;

    /**
     * 标准值
     */
    @Schema(description = "标准值", required = true)
    @NotNull(message = "标准值不能为空", groups = {AddGroup.class, EditGroup.class})
    private BigDecimal standardValue;

    /**
     * 生效日期
     */
    @Schema(description = "生效日期", required = true)
    @NotNull(message = "生效日期不能为空", groups = {AddGroup.class, EditGroup.class})
    private Date effectiveDate;

    /**
     * 失效日期
     */
    @Schema(description = "失效日期")
    private Date expiryDate;

    /**
     * 标准状态
     */
    @Schema(description = "标准状态")
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
