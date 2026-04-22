package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验计划
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("inspection_plan")
public class InspectionPlan extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 计划ID */
    @TableId(type = IdType.AUTO)
    private Long planId;

    /** 计划编号 */
    private String planCode;

    /** 计划名称 */
    private String planName;

    /** 检验类型（1-来料检验，2-过程检验，3-成品检验） */
    private String inspectionType;

    /** 开始日期 */
    private String startDate;

    /** 结束日期 */
    private String endDate;

    /** 状态（1-草稿，2-审批中，3-已批准，4-执行中，5-已完成） */
    private String status;

    /** 计划内容 */
    private String planContent;

    /** 项目名称 */
    private String projectName;

    /** 巡检周期（日、周、月） */
    private String inspectionCycle;

    /** 巡检人员 */
    private String inspectionPerson;

    /** 用户ID */
    private String userId;

    /** 设置日期 */
    private String setDate;

    /** 设置时间 */
    private String setTime;

    /** 计划ID（兼容旧代码） */
    public Long getId() {
        return planId;
    }

    /** 计划ID（兼容旧代码） */
    public void setId(Long id) {
        this.planId = id;
    }

}
