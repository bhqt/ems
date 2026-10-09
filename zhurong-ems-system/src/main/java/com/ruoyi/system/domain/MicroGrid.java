package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 微电网 microgrid
 *
 * @author cpems
 * @date 2026-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("microgrid")
public class MicroGrid extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 微电网名称 */
    private String gridName;

    /** 微电网编号 */
    private String gridCode;

    /** 微电网类型（1-独立型 2-并网型） */
    private String gridType;

    /** 所属区域ID */
    private Long areaId;

    /** 经度 */
    private BigDecimal longitude;

    /** 纬度 */
    private BigDecimal latitude;

    /** 光伏装机容量(kW) */
    private BigDecimal pvCapacity;

    /** 储能容量(kWh) */
    private BigDecimal storageCapacity;

    /** 负荷容量(kW) */
    private BigDecimal loadCapacity;

    /** 安装日期 */
    private Date installDate;

    /** 并网日期 */
    private Date gridDate;

    /** 系统状态（0-停用 1-正常 2-故障 3-维护） */
    private String status;

    /** 运行模式（1-并网 2-离网 3-并离网切换） */
    private String runMode;

    /** 负责人 */
    private String manager;

    /** 联系电话 */
    private String contactPhone;

    /** 备注 */
    private String remark;

    /** 删除标志（0代表存在 2代表删除） */
    @TableLogic
    private String delFlag;

}
