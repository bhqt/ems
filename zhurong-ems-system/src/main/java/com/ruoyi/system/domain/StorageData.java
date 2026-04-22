package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 储能数据
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("storage_data")
public class StorageData extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 数据ID */
    @TableId(type = IdType.AUTO)
    private Long dataId;

    /** 储能系统ID */
    private Long systemId;

    /** 时间戳 */
    private String timestamp;

    /** 荷电状态（%） */
    private Double soc;

    /** 功率（kW） */
    private Double power;

    /** 能量（kWh） */
    private Double energy;

    /** 状态（1-充电，2-放电，3-待机） */
    private String status;

}
