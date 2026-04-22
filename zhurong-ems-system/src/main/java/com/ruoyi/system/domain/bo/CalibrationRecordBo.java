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
 * 校准记录业务对象 ems_calibration_record
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "校准记录业务对象")
public class CalibrationRecordBo extends BaseEntity {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 记录编码
     */
    @Schema(description = "记录编码", required = true)
    @NotBlank(message = "记录编码不能为空", groups = {AddGroup.class, EditGroup.class})
    private String recordCode;

    /**
     * 校准计划ID
     */
    @Schema(description = "校准计划ID")
    private Long planId;

    /**
     * 计量器具ID
     */
    @Schema(description = "计量器具ID", required = true)
    @NotNull(message = "计量器具ID不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long meterId;

    /**
     * 器具名称
     */
    @Schema(description = "器具名称")
    private String meterName;

    /**
     * 校准日期
     */
    @Schema(description = "校准日期", required = true)
    @NotNull(message = "校准日期不能为空", groups = {AddGroup.class, EditGroup.class})
    private Date calibrationDate;

    /**
     * 校准人员ID
     */
    @Schema(description = "校准人员ID")
    private Long calibratorId;

    /**
     * 校准人员姓名
     */
    @Schema(description = "校准人员姓名", required = true)
    @NotBlank(message = "校准人员姓名不能为空", groups = {AddGroup.class, EditGroup.class})
    private String calibratorName;

    /**
     * 校准结果
     */
    @Schema(description = "校准结果", required = true)
    @NotBlank(message = "校准结果不能为空", groups = {AddGroup.class, EditGroup.class})
    private String calibrationResult;

    /**
     * 偏差值(%)
     */
    @Schema(description = "偏差值(%)")
    private BigDecimal deviation;

    /**
     * 证书编号
     */
    @Schema(description = "证书编号")
    private String certificateNo;

    /**
     * 证书文件
     */
    @Schema(description = "证书文件")
    private String certificateFile;

    /**
     * 下次校准日期
     */
    @Schema(description = "下次校准日期", required = true)
    @NotNull(message = "下次校准日期不能为空", groups = {AddGroup.class, EditGroup.class})
    private Date nextCalibrationDate;

    /**
     * 校准费用
     */
    @Schema(description = "校准费用")
    private BigDecimal calibrationCost;

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
