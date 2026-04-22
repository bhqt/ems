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
 * 储能电池组信息对象 storage_battery
 *
 * @author cpems
 * @date 2026-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("storage_battery")
@ExcelIgnoreUnannotated
public class StorageBattery extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 电池组名称
     */
    private String batteryName;

    /**
     * 电池组编号
     */
    private String batteryCode;

    /**
     * 所属储能系统ID
     */
    private Long storageId;

    /**
     * 储能系统名称
     */
    private String storageName;

    /**
     * 额定容量(kWh)
     */
    private BigDecimal capacity;

    /**
     * 额定电压(V)
     */
    private BigDecimal voltage;

    /**
     * 额定电流(A)
     */
    private BigDecimal current;

    /**
     * 电芯数量
     */
    private Integer cellCount;

    /**
     * 制造商
     */
    private String manufacturer;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 安装日期
     */
    private Date installDate;

    /**
     * 电池状态（0-停用 1-正常 2-故障 3-维护）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
}
