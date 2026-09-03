package com.ruoyi.system.hospital.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 医院设备报警记录对象 hospital_alarm_record
 *
 * @author cpems
 */
@Data
@TableName("hospital_alarm_record")
public class HospitalAlarmRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id")
    private Long id;

    /** 触发规则ID */
    private Long ruleId;

    /** 设备ID */
    private Long deviceId;

    /** 指标编码 */
    private String metricCode;

    /** 报警类型（OVERLOAD过载/OFFLINE离线） */
    private String alarmType;

    /** 报警级别（0一般 1严重 2紧急） */
    private String level;

    /** 触发时的指标值 */
    private BigDecimal alarmVal;

    /** 报警内容描述 */
    private String content;

    /** 处理状态（0待处理 1已结束） */
    private String status;

    /** 报警开始时间 */
    private Date startTime;

    /** 报警结束时间 */
    private Date endTime;

    /** 处理人 */
    private String handleBy;

    /** 处理时间 */
    private Date handleTime;

    /** 处理说明 */
    private String handleRemark;

    /** 创建时间 */
    private Date createTime;
}
