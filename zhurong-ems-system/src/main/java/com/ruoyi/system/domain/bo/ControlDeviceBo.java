package com.ruoyi.system.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;

/**
 * 控制设备业务对象 ems_control_device
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "控制设备业务对象")
public class ControlDeviceBo extends BaseEntity {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 设备编码
     */
    @Schema(description = "设备编码", required = true)
    @NotBlank(message = "设备编码不能为空", groups = {AddGroup.class, EditGroup.class})
    private String deviceCode;

    /**
     * 设备名称
     */
    @Schema(description = "设备名称", required = true)
    @NotBlank(message = "设备名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String deviceName;

    /**
     * 设备类型
     */
    @Schema(description = "设备类型", required = true)
    @NotBlank(message = "设备类型不能为空", groups = {AddGroup.class, EditGroup.class})
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
    private String areaName;

    /**
     * 安装位置
     */
    @Schema(description = "安装位置")
    private String location;

    /**
     * 设备状态
     */
    @Schema(description = "设备状态")
    private String status;

    /**
     * 压力(MPa)
     */
    @Schema(description = "压力(MPa)")
    private BigDecimal pressure;

    /**
     * 流量(m³/h)
     */
    @Schema(description = "流量(m³/h)")
    private BigDecimal flow;

    /**
     * 电压(V)
     */
    @Schema(description = "电压(V)")
    private BigDecimal voltage;

    /**
     * 电流(A)
     */
    @Schema(description = "电流(A)")
    private BigDecimal current;

    /**
     * 功率(kW)
     */
    @Schema(description = "功率(kW)")
    private BigDecimal power;

    /**
     * 水位(%)
     */
    @Schema(description = "水位(%)")
    private BigDecimal level;

    /**
     * 容量(m³)
     */
    @Schema(description = "容量(m³)")
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
    private Date lastMaintenanceDate;

    /**
     * 下次维护日期
     */
    @Schema(description = "下次维护日期")
    private Date nextMaintenanceDate;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 所属用户
     */
    @Schema(description = "所属用户")
    private Long userId;

    /**
     * 所属部门
     */
    @Schema(description = "所属部门")
    private Long deptId;
}
