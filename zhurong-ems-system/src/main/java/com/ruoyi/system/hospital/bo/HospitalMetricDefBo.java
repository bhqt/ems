package com.ruoyi.system.hospital.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 医院设备指标定义业务对象
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HospitalMetricDefBo extends BaseEntity {

    /** 主键 */
    @NotNull(message = "主键不能为空", groups = {EditGroup.class})
    private Long id;

    /** 指标编码（如 power/current/temperature） */
    @NotBlank(message = "指标编码不能为空", groups = {AddGroup.class, EditGroup.class})
    private String metricCode;

    /** 指标名称 */
    @NotBlank(message = "指标名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String metricName;

    /** 指标单位 */
    private String unit;

    /** 数据类型（number/status/string） */
    @NotBlank(message = "数据类型不能为空", groups = {AddGroup.class, EditGroup.class})
    private String dataType;

    /** 是否高频数据（1是 0否，高频走时序库） */
    private String highFreq;

    /** 是否启用（0正常 1停用） */
    private String status;

    /** 备注 */
    private String remark;
}
