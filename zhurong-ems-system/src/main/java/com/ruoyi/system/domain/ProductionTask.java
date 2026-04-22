package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 生产任务
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("production_task")
public class ProductionTask extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 任务ID */
    @TableId(type = IdType.AUTO)
    private Long taskId;

    /** 任务编号 */
    private String taskCode;

    /** 任务名称 */
    private String taskName;

    /** 计划ID */
    private Long planId;

    /** 产品ID */
    private Long productId;

    /** 任务数量 */
    private Double quantity;

    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 状态（1-待执行，2-执行中，3-已完成，4-延期） */
    private String status;

}
