package com.ruoyi.system.hospital.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 医院设备实时监测视图对象（聚合最新数据点）
 *
 * @author cpems
 */
@Data
public class HospitalDeviceRealtimeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long deviceId;

    /** 设备名称 */
    private String deviceName;

    /** 设备编号 */
    private String deviceCode;

    /** 设备类型 */
    private String deviceType;

    /** IOT 平台设备 ID */
    private String iotDeviceId;

    /** 台账状态（0正常 1停用 2离线） */
    private String status;

    /** 是否在线（有近期数据且台账正常） */
    private Boolean online;

    /** 运行状态（1运行/0待机，未知为 null） */
    private String runStatus;

    /** 实时功率（kW，最新 power 点） */
    private BigDecimal power;

    /** 累计电量（kWh，最新 electricity 点） */
    private BigDecimal electricity;

    /** 最近数据时间 */
    private Date lastTs;

    /** 未处理报警数 */
    private Integer openAlarmCount;
}
