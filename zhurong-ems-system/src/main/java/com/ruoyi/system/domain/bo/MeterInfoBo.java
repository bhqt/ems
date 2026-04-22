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
 * 计量器具信息业务对象 ems_meter_info
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "计量器具信息业务对象")
public class MeterInfoBo extends BaseEntity {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 器具编码
     */
    @Schema(description = "器具编码", required = true)
    @NotBlank(message = "器具编码不能为空", groups = {AddGroup.class, EditGroup.class})
    private String meterCode;

    /**
     * 器具名称
     */
    @Schema(description = "器具名称", required = true)
    @NotBlank(message = "器具名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String meterName;

    /**
     * 器具类型
     */
    @Schema(description = "器具类型", required = true)
    @NotBlank(message = "器具类型不能为空", groups = {AddGroup.class, EditGroup.class})
    private String meterType;

    /**
     * 型号
     */
    @Schema(description = "型号")
    private String meterModel;

    /**
     * 精度等级
     */
    @Schema(description = "精度等级")
    private String accuracyLevel;

    /**
     * 安装位置
     */
    @Schema(description = "安装位置")
    private String installLocation;

    /**
     * 安装日期
     */
    @Schema(description = "安装日期")
    private Date installDate;

    /**
     * 生产厂家
     */
    @Schema(description = "生产厂家")
    private String manufacturer;

    /**
     * 出厂日期
     */
    @Schema(description = "出厂日期")
    private Date manufacturerDate;

    /**
     * 器具状态
     */
    @Schema(description = "器具状态")
    private String status;

    /**
     * 上次校准日期
     */
    @Schema(description = "上次校准日期")
    private Date lastCalibrationDate;

    /**
     * 下次校准日期
     */
    @Schema(description = "下次校准日期")
    private Date nextCalibrationDate;

    /**
     * 校准周期(月)
     */
    @Schema(description = "校准周期(月)")
    private Integer calibrationCycle;

    /**
     * 证书编号
     */
    @Schema(description = "证书编号")
    private String certificateNo;

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
