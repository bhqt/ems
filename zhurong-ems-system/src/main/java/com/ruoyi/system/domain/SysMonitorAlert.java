package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 监控告警记录
 * @author cpems
 */
@Data
@TableName("sys_monitor_alert")
public class SysMonitorAlert {

    /**
     * 告警ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 告警类型
     */
    private String alertType;

    /**
     * 告警级别
     */
    private String alertLevel;

    /**
     * 告警标题
     */
    private String alertTitle;

    /**
     * 告警内容
     */
    private String alertContent;

    /**
     * 关联系统
     */
    private String relatedSystem;

    /**
     * 关联任务ID
     */
    private Long relatedTaskId;

    /**
     * 告警状态
     */
    private Integer status;

    /**
     * 处理人
     */
    private String handler;

    /**
     * 处理时间
     */
    private Date handleTime;

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
