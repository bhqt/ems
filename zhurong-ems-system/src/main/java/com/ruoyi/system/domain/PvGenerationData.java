package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 光伏发电数据
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("pv_generation_data")
public class PvGenerationData extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 数据ID */
    @TableId(type = IdType.AUTO)
    private Long dataId;

    /** 电站ID */
    private Long stationId;

    /** 时间戳 */
    private String timestamp;

    /** 功率（kW） */
    private Double power;

    /** 发电量（kWh） */
    private Double energy;

    /** 效率（%） */
    private Double efficiency;

}
