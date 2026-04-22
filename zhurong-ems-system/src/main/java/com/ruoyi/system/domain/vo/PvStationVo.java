package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 光伏电站信息视图对象
 *
 * @author cpems
 * @date 2026-03-27
 */
@Data
@ExcelIgnoreUnannotated
public class PvStationVo {

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long id;

    /**
     * 电站名称
     */
    @ExcelProperty(value = "电站名称")
    private String stationName;

    /**
     * 电站编号
     */
    @ExcelProperty(value = "电站编号")
    private String stationCode;

    /**
     * 电站类型（1-屋顶光伏 2-地面光伏 3-农光互补 4-渔光互补）
     */
    @ExcelProperty(value = "电站类型")
    private String stationType;

    /**
     * 装机容量(kW)
     */
    @ExcelProperty(value = "装机容量(kW)")
    private BigDecimal capacity;

    /**
     * 所属区域ID
     */
    private Long areaId;

    /**
     * 区域名称
     */
    @ExcelProperty(value = "所属区域")
    private String areaName;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 安装日期
     */
    @ExcelProperty(value = "安装日期")
    private Date installDate;

    /**
     * 并网日期
     */
    @ExcelProperty(value = "并网日期")
    private Date gridDate;

    /**
     * 设计寿命(年)
     */
    private Integer designLife;

    /**
     * 组件类型（1-单晶硅 2-多晶硅 3-薄膜）
     */
    private String componentType;

    /**
     * 逆变器数量
     */
    private Integer inverterCount;

    /**
     * 组件数量
     */
    private Integer componentCount;

    /**
     * 电站状态（0-停用 1-正常 2-故障 3-维护）
     */
    @ExcelProperty(value = "电站状态")
    private String status;

    /**
     * 负责人
     */
    @ExcelProperty(value = "负责人")
    private String manager;

    /**
     * 联系电话
     */
    @ExcelProperty(value = "联系电话")
    private String contactPhone;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    // 扩展字段 - 实时数据
    /**
     * 今日发电量(kWh)
     */
    private BigDecimal todayEnergy;

    /**
     * 累计发电量(kWh)
     */
    private BigDecimal totalEnergy;

    /**
     * 当前功率(kW)
     */
    private BigDecimal currentPower;

    /**
     * 逆变器在线数量
     */
    private Integer onlineInverterCount;

    /**
     * 逆变器总数
     */
    private Integer totalInverterCount;
}
