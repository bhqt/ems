package com.ruoyi.system.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 光伏逆变器信息对象 pv_inverter
 *
 * @author cpems
 * @date 2026-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pv_inverter")
@ExcelIgnoreUnannotated
public class PvInverter extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 逆变器名称
     */
    private String inverterName;

    /**
     * 逆变器编号
     */
    private String inverterCode;

    /**
     * 所属电站ID
     */
    private Long stationId;

    /**
     * 电站名称
     */
    private String stationName;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 额定功率(kW)
     */
    private BigDecimal ratedPower;

    /**
     * 设备序列号
     */
    private String serialNumber;

    /**
     * 安装位置
     */
    private String installLocation;

    /**
     * 安装日期
     */
    private Date installDate;

    /**
     * 设备状态（0-停用 1-正常 2-故障 3-离线）
     */
    private String status;

    /**
     * 通讯协议
     */
    private String protocol;

    /**
     * 设备IP地址
     */
    private String ipAddress;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 从机地址
     */
    private String slaveId;

    /**
     * 最后在线时间
     */
    private Date lastOnlineTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
}
