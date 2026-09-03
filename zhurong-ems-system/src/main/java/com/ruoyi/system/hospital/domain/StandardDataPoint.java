package com.ruoyi.system.hospital.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 标准化后的设备数据点（跨 MQ 传输载体）
 *
 * @author cpems
 */
@Data
public class StandardDataPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 本系统设备 ID（hospital_device.id） */
    private Long deviceId;

    /** 设备编号 */
    private String deviceCode;

    /** 指标编码 */
    private String metricCode;

    /** 指标值（数值型） */
    private BigDecimal value;

    /** 指标值（状态/字符串型） */
    private String strValue;

    /** 采集时间 */
    private Date ts;

    /** 数据质量（0正常 1异常） */
    private Integer quality;
}
