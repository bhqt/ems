package com.ruoyi.system.hospital.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 医院设备工作量（检查量）视图对象
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
public class HospitalDeviceWorkloadVo extends BaseEntity {

    /** 主键 */
    @ExcelProperty(value = "主键")
    private Long id;

    /** 设备ID（hospital_device.id） */
    private Long deviceId;

    /** 设备名称 */
    private String deviceName;

    /** 设备编号 */
    private String deviceCode;

    /** 工作量（检查台次） */
    @ExcelProperty(value = "工作量")
    private BigDecimal workloadCount;

    /** 统计日期 */
    @ExcelProperty(value = "统计日期")
    private Date statDate;
}
