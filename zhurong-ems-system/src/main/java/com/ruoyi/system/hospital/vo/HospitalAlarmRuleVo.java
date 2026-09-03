package com.ruoyi.system.hospital.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 医院设备报警规则视图对象
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
public class HospitalAlarmRuleVo extends BaseEntity {

    /** 主键 */
    @ExcelProperty(value = "主键")
    private Long id;

    /** 规则名称 */
    @ExcelProperty(value = "规则名称")
    private String ruleName;

    /** 设备ID（为空表示全部设备） */
    private Long deviceId;

    /** 设备名称（deviceId 非空时关联展示） */
    private String deviceName;

    /** 设备类型 */
    private String deviceType;

    /** 指标编码 */
    private String metricCode;

    /** 规则类型（THRESHOLD/OFFLINE） */
    private String ruleType;

    /** 比较条件 */
    private String condition;

    /** 阈值 */
    private BigDecimal thresholdValue;

    /** 离线超时分钟数 */
    private Integer offlineTimeoutMin;

    /** 报警级别（0一般 1严重 2紧急） */
    private String level;

    /** 是否启用（0启用 1停用） */
    private String status;

    /** 通知邮箱 */
    private String notifyEmail;

    /** 升级超时分钟数（该级别仍未处理则升级） */
    private Integer escalateMin;

    /** 升级目标级别（0一般 1严重 2紧急） */
    private String escalateLevel;

    /** 备注 */
    private String remark;
}
