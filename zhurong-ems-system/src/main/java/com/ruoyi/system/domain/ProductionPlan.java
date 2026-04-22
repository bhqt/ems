package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 生产计划
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("production_plan")
public class ProductionPlan extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 计划ID */
    @TableId(type = IdType.AUTO)
    private Long planId;

    /** 计划编号 */
    private String planCode;

    /** 计划类型（1-年度，2-月度，3-周，4-日） */
    private String planType;

    /** 计划名称 */
    private String planName;

    /** 开始日期 */
    private String startDate;

    /** 结束日期 */
    private String endDate;

    /** 状态（1-草稿，2-审批中，3-已批准，4-执行中，5-已完成） */
    private String status;

}
