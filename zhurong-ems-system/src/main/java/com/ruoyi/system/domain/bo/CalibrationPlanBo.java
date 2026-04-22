package com.ruoyi.system.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;

/**
 * 校准计划业务对象 ems_calibration_plan
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "校准计划业务对象")
public class CalibrationPlanBo extends BaseEntity {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 计划编码
     */
    @Schema(description = "计划编码", required = true)
    @NotBlank(message = "计划编码不能为空", groups = {AddGroup.class, EditGroup.class})
    private String planCode;

    /**
     * 计划名称
     */
    @Schema(description = "计划名称", required = true)
    @NotBlank(message = "计划名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String planName;

    /**
     * 计划类型
     */
    @Schema(description = "计划类型", required = true)
    @NotBlank(message = "计划类型不能为空", groups = {AddGroup.class, EditGroup.class})
    private String planType;

    /**
     * 开始日期
     */
    @Schema(description = "开始日期", required = true)
    private Date startDate;

    /**
     * 结束日期
     */
    @Schema(description = "结束日期", required = true)
    private Date endDate;

    /**
     * 总计量器具数
     */
    @Schema(description = "总计量器具数")
    private Integer totalMeters;

    /**
     * 已完成器具数
     */
    @Schema(description = "已完成器具数")
    private Integer completedMeters;

    /**
     * 计划状态
     */
    @Schema(description = "计划状态")
    private String status;

    /**
     * 负责人
     */
    @Schema(description = "负责人", required = true)
    @NotBlank(message = "负责人不能为空", groups = {AddGroup.class, EditGroup.class})
    private String responsiblePerson;

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
