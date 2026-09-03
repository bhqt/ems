package com.ruoyi.system.hospital.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 医院设备数据点视图对象（用于查询展示）
 *
 * @author cpems
 */
@Data
public class HospitalDeviceDataVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 本系统设备 ID */
    private Long deviceId;

    /** 设备名称 */
    private String deviceName;

    /** 设备编号 */
    private String deviceCode;

    /** 指标编码 */
    private String metricCode;

    /** 指标名称 */
    private String metricName;

    /** 指标单位 */
    private String unit;

    /** 指标值（数值型） */
    private BigDecimal metricValue;

    /** 指标值（状态/字符串型） */
    private String metricStr;

    /** 采集时间 */
    private Date ts;

    /** 数据质量（0正常 1异常） */
    private Integer quality;
}
