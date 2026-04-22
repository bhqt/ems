package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 虚拟电厂
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("virtual_power_plant")
public class VirtualPowerPlant extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 虚拟电厂ID */
    @TableId(type = IdType.AUTO)
    private Long plantId;

    /** 名称 */
    private String name;

    /** 总容量（kW） */
    private Double totalCapacity;

    /** 状态（1-运行中，2-停机） */
    private String status;

}
