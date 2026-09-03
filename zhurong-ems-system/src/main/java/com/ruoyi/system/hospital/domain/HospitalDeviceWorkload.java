package com.ruoyi.system.hospital.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 医院设备工作量（检查量）对象 hospital_device_workload
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hospital_device_workload")
public class HospitalDeviceWorkload extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id")
    private Long id;

    /** 设备ID（hospital_device.id） */
    private Long deviceId;

    /** 工作量（检查台次） */
    private BigDecimal workloadCount;

    /** 统计日期 */
    private Date statDate;
}
