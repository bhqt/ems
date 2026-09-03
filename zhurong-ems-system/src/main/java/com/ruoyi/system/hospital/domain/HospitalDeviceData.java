package com.ruoyi.system.hospital.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 医院设备数据点对象 hospital_device_data
 *
 * @author cpems
 */
@Data
@TableName("hospital_device_data")
public class HospitalDeviceData implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id")
    private Long id;

    /** 本系统设备 ID（hospital_device.id） */
    private Long deviceId;

    /** 指标编码 */
    private String metricCode;

    /** 指标值（数值型） */
    private BigDecimal metricValue;

    /** 指标值（状态/字符串型） */
    private String metricStr;

    /** 采集时间 */
    private Date ts;

    /** 数据质量（0正常 1异常） */
    private Integer quality;

    /** 接收时间 */
    private Date receiveTime;
}
