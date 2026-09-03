package com.ruoyi.system.hospital.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 医院设备报警规则对象 hospital_alarm_rule
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hospital_alarm_rule")
public class HospitalAlarmRule extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id")
    private Long id;

    /** 规则名称 */
    private String ruleName;

    /** 设备ID（为空表示全部设备） */
    private Long deviceId;

    /** 设备类型（deviceId为空时可按类型过滤） */
    private String deviceType;

    /** 指标编码（THRESHOLD规则必填，如power） */
    private String metricCode;

    /** 规则类型（THRESHOLD阈值/OFFLINE离线） */
    private String ruleType;

    /** 比较条件（G大于/E等于/L小于/GE大于等于/LE小于等于） */
    private String condition;

    /** 阈值（THRESHOLD规则必填） */
    private BigDecimal thresholdValue;

    /** 离线超时分钟数（OFFLINE规则必填） */
    private Integer offlineTimeoutMin;

    /** 报警级别（0一般 1严重 2紧急） */
    private String level;

    /** 是否启用（0启用 1停用） */
    private String status;

    /** 通知邮箱（多个逗号分隔，为空仅记录不通知） */
    private String notifyEmail;

    /** 备注 */
    private String remark;
}
