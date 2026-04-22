package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 光伏电站
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("pv_station")
public class PvStation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 电站名称 */
    private String stationName;

    /** 电站编号 */
    private String stationCode;

    /** 电站类型（1-屋顶光伏 2-地面光伏 3-农光互补 4-渔光互补） */
    private String stationType;

    /** 装机容量(kW) */
    private BigDecimal capacity;

    /** 所属区域ID */
    private Long areaId;

    /** 经度 */
    private BigDecimal longitude;

    /** 纬度 */
    private BigDecimal latitude;

    /** 安装日期 */
    private Date installDate;

    /** 并网日期 */
    private Date gridDate;

    /** 设计寿命(年) */
    private Integer designLife;

    /** 组件类型（1-单晶硅 2-多晶硅 3-薄膜） */
    private String componentType;

    /** 逆变器型号 */
    private String inverterModel;

    /** 逆变器数量 */
    private Integer inverterCount;

    /** 组件数量 */
    private Integer componentCount;

    /** 电站状态（0-停用 1-正常 2-故障 3-维护） */
    private String status;

    /** 负责人 */
    private String manager;

    /** 联系电话 */
    private String contactPhone;

    /** 备注 */
    private String remark;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
