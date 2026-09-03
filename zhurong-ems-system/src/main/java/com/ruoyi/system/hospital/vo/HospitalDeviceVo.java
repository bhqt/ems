package com.ruoyi.system.hospital.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 医院检查检验设备台账视图对象
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
public class HospitalDeviceVo extends BaseEntity {

    /** 主键 */
    @ExcelProperty(value = "主键")
    private Long id;

    /** 设备名称 */
    @ExcelProperty(value = "设备名称")
    private String deviceName;

    /** 设备编号 */
    @ExcelProperty(value = "设备编号")
    private String deviceCode;

    /** 设备类型（CT/MRI/DR/US/LAB/DSA/OTHER） */
    @ExcelProperty(value = "设备类型")
    private String deviceType;

    /** 设备型号 */
    @ExcelProperty(value = "设备型号")
    private String model;

    /** 生产厂商 */
    @ExcelProperty(value = "生产厂商")
    private String manufacturer;

    /** 所属院区 */
    @ExcelProperty(value = "所属院区")
    private String areaId;

    /** 所属院区名称 */
    private String areaName;

    /** 所属科室 */
    @ExcelProperty(value = "所属科室")
    private String deptId;

    /** 所属科室名称 */
    private String deptName;

    /** IOT 平台设备 ID（用于回调数据关联） */
    @ExcelProperty(value = "IOT设备ID")
    private String iotDeviceId;

    /** 设备状态（0正常 1停用 2离线） */
    @ExcelProperty(value = "设备状态")
    private String status;

    /** 备注 */
    private String remark;
}
