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
 * 储能系统信息对象 energy_storage
 *
 * @author cpems
 * @date 2026-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("energy_storage")
@ExcelIgnoreUnannotated
public class EnergyStorage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 储能系统名称
     */
    private String storageName;

    /**
     * 储能系统编号
     */
    private String storageCode;

    /**
     * 储能类型（1-锂电池 2-铅酸电池 3-液流电池 4-超级电容）
     */
    private String storageType;

    /**
     * 额定容量(kWh)
     */
    private BigDecimal capacity;

    /**
     * 额定功率(kW)
     */
    private BigDecimal power;

    /**
     * 电压等级
     */
    private String voltageLevel;

    /**
     * 所属区域ID
     */
    private Long areaId;

    /**
     * 区域名称
     */
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
    private Date installDate;

    /**
     * 并网日期
     */
    private Date gridDate;

    /**
     * 设计寿命(年)
     */
    private Integer designLife;

    /**
     * 制造商
     */
    private String manufacturer;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 系统状态（0-停用 1-正常 2-故障 3-维护 4-充电中 5-放电中）
     */
    private String status;

    /**
     * 负责人
     */
    private String manager;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
}
