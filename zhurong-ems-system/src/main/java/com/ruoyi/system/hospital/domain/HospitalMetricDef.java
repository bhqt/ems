package com.ruoyi.system.hospital.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 医院设备指标定义对象 hospital_metric_def
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hospital_metric_def")
public class HospitalMetricDef extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id")
    private Long id;

    /** 指标编码（如 power/current/temperature） */
    private String metricCode;

    /** 指标名称 */
    private String metricName;

    /** 指标单位 */
    private String unit;

    /** 数据类型（number/status/string） */
    private String dataType;

    /** 是否高频数据（1是 0否，高频走时序库） */
    private String highFreq;

    /** 是否启用（0正常 1停用） */
    private String status;

    /** 备注 */
    private String remark;
}
