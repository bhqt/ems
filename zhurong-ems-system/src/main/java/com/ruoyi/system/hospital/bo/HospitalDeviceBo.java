package com.ruoyi.system.hospital.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 医院检查检验设备台账业务对象
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HospitalDeviceBo extends BaseEntity {

    /** 主键 */
    @NotNull(message = "主键不能为空", groups = {EditGroup.class})
    private Long id;

    /** 设备名称 */
    @NotBlank(message = "设备名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String deviceName;

    /** 设备编号 */
    @NotBlank(message = "设备编号不能为空", groups = {AddGroup.class, EditGroup.class})
    private String deviceCode;

    /** 设备类型（CT/MRI/DR/US/LAB/DSA/OTHER） */
    @NotBlank(message = "设备类型不能为空", groups = {AddGroup.class, EditGroup.class})
    private String deviceType;

    /** 设备型号 */
    private String model;

    /** 生产厂商 */
    private String manufacturer;

    /** 所属院区 */
    private String areaId;

    /** 所属科室 */
    private String deptId;

    /** IOT 平台设备 ID（用于回调数据关联） */
    private String iotDeviceId;

    /** 设备状态（0正常 1停用 2离线） */
    private String status;

    /** 备注 */
    private String remark;
}
