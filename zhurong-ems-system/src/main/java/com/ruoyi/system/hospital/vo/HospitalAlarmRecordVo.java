package com.ruoyi.system.hospital.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 医院设备报警记录视图对象
 *
 * @author cpems
 */
@Data
@ExcelIgnoreUnannotated
public class HospitalAlarmRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ExcelProperty(value = "主键")
    private Long id;

    /** 触发规则ID */
    private Long ruleId;

    /** 规则名称 */
    private String ruleName;

    /** 设备ID */
    private Long deviceId;

    /** 设备名称 */
    private String deviceName;

    /** 设备编号 */
    private String deviceCode;

    /** 指标编码 */
    private String metricCode;

    /** 报警类型（OVERLOAD/OFFLINE） */
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
}
