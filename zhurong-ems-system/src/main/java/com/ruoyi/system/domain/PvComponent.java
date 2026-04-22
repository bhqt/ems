package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 光伏组件
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("pv_component")
public class PvComponent extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 组件ID */
    @TableId(type = IdType.AUTO)
    private Long componentId;

    /** 电站ID */
    private Long stationId;

    /** 型号 */
    private String model;

    /** 数量 */
    private Integer quantity;

    /** 单块容量（W） */
    private Double capacityPerUnit;

    /** 安装日期 */
    private String installationDate;

    /** 状态（1-正常，2-异常，3-故障） */
    private String status;

}
