package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 报表定时任务
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("report_task")
public class ReportTask extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 任务ID */
    @TableId(type = IdType.AUTO)
    private Long taskId;

    /** 任务名称 */
    private String taskName;

    /** 模板ID */
    private Long templateId;

    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 区域ID */
    private String areaIds;

    /** 能源类型 */
    private String energyType;

    /** 日期类型 */
    private String dateType;

    /** 执行周期 */
    private String cronExpression;

    /** 导出格式 */
    private String exportFormat;

    /** 发送邮箱 */
    private String email;

    /** 状态（0正常 1停用） */
    private String status;

    /** 排序 */
    private Integer orderNum;

    /** 备注 */
    private String remark;

}
