package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验任务
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("inspection_task")
public class InspectionTask extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 任务ID */
    @TableId(type = IdType.AUTO)
    private Long taskId;

    /** 任务编号 */
    private String taskCode;

    /** 计划ID */
    private Long planId;

    /** 检验类型（1-来料检验，2-过程检验，3-成品检验） */
    private String inspectionType;

    /** 检验对象ID */
    private Long objectId;

    /** 检验员ID */
    private Long inspectorId;

    /** 计划检验时间 */
    private String scheduledTime;

    /** 实际检验时间 */
    private String actualTime;

    /** 状态（1-待检验，2-检验中，3-已完成） */
    private String status;

}
