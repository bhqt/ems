package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 控制设备对象 ems_control_device
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ems_control_device")
public class ControlDevice extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 设备编码
     */
    private String deviceCode;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型(water_source:水源井, intermediate_station:中间站, high_level_tank:高位水池, well_area:水井区域)
     */
    private String deviceType;

    /**
     * 区域编码
     */
    private String areaCode;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 安装位置
     */
    private String location;

    /**
     * 设备状态(online:在线, offline:离线, standby:备用, maintenance:维护中)
     */
    private String status;

    /**
     * 压力(MPa)
     */
    private BigDecimal pressure;

    /**
     * 流量(m³/h)
     */
    private BigDecimal flow;

    /**
     * 电压(V)
     */
    private BigDecimal voltage;

    /**
     * 电流(A)
     */
    private BigDecimal current;

    /**
     * 功率(kW)
     */
    private BigDecimal power;

    /**
     * 水位(%)
     */
    private BigDecimal level;

    /**
     * 容量(m³)
     */
    private BigDecimal capacity;

    /**
     * 信号强度(strong:强, medium:中, weak:弱)
     */
    private String signalStrength;

    /**
     * 最后维护日期
     */
    private Date lastMaintenanceDate;

    /**
     * 下次维护日期
     */
    private Date nextMaintenanceDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 所属用户
     */
    private Long userId;

    /**
     * 所属部门
     */
    private Long deptId;

    /**
     * 删除标志(0:正常, 1:删除)
     */
    @TableLogic
    private String delFlag;

    /**
     * 删除者
     */
    private String delBy;

    /**
     * 删除时间
     */
    private Date delTime;
}
