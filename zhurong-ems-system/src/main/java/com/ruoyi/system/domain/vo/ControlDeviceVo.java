package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 控制设备视图对象 ems_control_device
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@Schema(description = "控制设备视图对象")
@ExcelIgnoreUnannotated
public class ControlDeviceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 设备编码
     */
    @Schema(description = "设备编码")
    @ExcelProperty(value = "设备编码")
    private String deviceCode;

    /**
     * 设备名称
     */
    @Schema(description = "设备名称")
    @ExcelProperty(value = "设备名称")
    private String deviceName;

    /**
     * 设备类型
     */
    @Schema(description = "设备类型")
    @ExcelProperty(value = "设备类型")
    private String deviceType;

    /**
     * 区域编码
     */
    @Schema(description = "区域编码")
    private String areaCode;

    /**
     * 区域名称
     */
    @Schema(description = "区域名称")
    @ExcelProperty(value = "区域名称")
    private String areaName;

    /**
     * 安装位置
     */
    @Schema(description = "安装位置")
    @ExcelProperty(value = "安装位置")
    private String location;

    /**
     * 设备状态
     */
    @Schema(description = "设备状态")
    @ExcelProperty(value = "设备状态")
    private String status;

    /**
     * 压力(MPa)
     */
    @Schema(description = "压力(MPa)")
    @ExcelProperty(value = "压力")
    private BigDecimal pressure;

    /**
     * 流量(m³/h)
     */
    @Schema(description = "流量(m³/h)")
    @ExcelProperty(value = "流量")
    private BigDecimal flow;

    /**
     * 电压(V)
     */
    @Schema(description = "电压(V)")
    @ExcelProperty(value = "电压")
    private BigDecimal voltage;

    /**
     * 电流(A)
     */
    @Schema(description = "电流(A)")
    @ExcelProperty(value = "电流")
    private BigDecimal current;

    /**
     * 功率(kW)
     */
    @Schema(description = "功率(kW)")
    @ExcelProperty(value = "功率")
    private BigDecimal power;

    /**
     * 水位(%)
     */
    @Schema(description = "水位(%)")
    @ExcelProperty(value = "水位")
    private BigDecimal level;

    /**
     * 容量(m³)
     */
    @Schema(description = "容量(m³)")
    @ExcelProperty(value = "容量")
    private BigDecimal capacity;

    /**
     * 信号强度
     */
    @Schema(description = "信号强度")
    private String signalStrength;

    /**
     * 最后维护日期
     */
    @Schema(description = "最后维护日期")
    @ExcelProperty(value = "最后维护日期")
    private Date lastMaintenanceDate;

    /**
     * 下次维护日期
     */
    @Schema(description = "下次维护日期")
    @ExcelProperty(value = "下次维护日期")
    private Date nextMaintenanceDate;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建者
     */
    @Schema(description = "创建者")
    private String createBy;
}
