package com.ruoyi.system.hospital.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 医院设备报警规则业务对象
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HospitalAlarmRuleBo extends BaseEntity {

    /** 主键 */
    @NotNull(message = "主键不能为空", groups = {EditGroup.class})
    private Long id;

    /** 规则名称 */
    @NotBlank(message = "规则名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String ruleName;

    /** 设备ID（为空表示全部设备） */
    private Long deviceId;

    /** 设备类型（deviceId为空时可按类型过滤） */
    private String deviceType;

    /** 指标编码（THRESHOLD规则必填） */
    private String metricCode;

    /** 规则类型（THRESHOLD阈值/OFFLINE离线） */
    @NotBlank(message = "规则类型不能为空", groups = {AddGroup.class, EditGroup.class})
    private String ruleType;

    /** 比较条件（G/E/L/GE/LE） */
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
