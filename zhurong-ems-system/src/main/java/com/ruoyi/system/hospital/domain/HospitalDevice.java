package com.ruoyi.system.hospital.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 医院检查检验设备台账对象 hospital_device
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hospital_device")
public class HospitalDevice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id")
    private Long id;

    /** 设备名称 */
    private String deviceName;

    /** 设备编号 */
    private String deviceCode;

    /** 设备类型（CT/MRI/DR/US/LAB/DSA/OTHER） */
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
