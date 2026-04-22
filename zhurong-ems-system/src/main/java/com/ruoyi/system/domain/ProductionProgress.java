package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 生产进度
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("production_progress")
public class ProductionProgress extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 进度ID */
    @TableId(type = IdType.AUTO)
    private Long progressId;

    /** 任务ID */
    private Long taskId;

    /** 工序ID */
    private Long processId;

    /** 实际开始时间 */
    private String actualStartTime;

    /** 实际结束时间 */
    private String actualEndTime;

    /** 完成数量 */
    private Double completedQuantity;

    /** 状态（1-待执行，2-执行中，3-已完成） */
    private String status;

}
