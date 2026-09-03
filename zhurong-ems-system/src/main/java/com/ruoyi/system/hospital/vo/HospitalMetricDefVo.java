package com.ruoyi.system.hospital.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 医院设备指标定义视图对象
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
public class HospitalMetricDefVo extends BaseEntity {

    /** 主键 */
    @ExcelProperty(value = "主键")
    private Long id;

    /** 指标编码（如 power/current/temperature） */
    @ExcelProperty(value = "指标编码")
    private String metricCode;

    /** 指标名称 */
    @ExcelProperty(value = "指标名称")
    private String metricName;

    /** 指标单位 */
    @ExcelProperty(value = "指标单位")
    private String unit;

    /** 数据类型（number/status/string） */
    @ExcelProperty(value = "数据类型")
    private String dataType;

    /** 是否高频数据（1是 0否，高频走时序库） */
    @ExcelProperty(value = "是否高频")
    private String highFreq;

    /** 是否启用（0正常 1停用） */
    @ExcelProperty(value = "状态")
    private String status;

    /** 备注 */
    private String remark;
}
