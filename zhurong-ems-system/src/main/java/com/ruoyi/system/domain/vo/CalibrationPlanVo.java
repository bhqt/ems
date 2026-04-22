package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 校准计划视图对象 ems_calibration_plan
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@Schema(description = "校准计划视图对象")
@ExcelIgnoreUnannotated
public class CalibrationPlanVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 计划编码
     */
    @Schema(description = "计划编码")
    @ExcelProperty(value = "计划编码")
    private String planCode;

    /**
     * 计划名称
     */
    @Schema(description = "计划名称")
    @ExcelProperty(value = "计划名称")
    private String planName;

    /**
     * 计划类型
     */
    @Schema(description = "计划类型")
    @ExcelProperty(value = "计划类型")
    private String planType;

    /**
     * 开始日期
     */
    @Schema(description = "开始日期")
    @ExcelProperty(value = "开始日期")
    private Date startDate;

    /**
     * 结束日期
     */
    @Schema(description = "结束日期")
    @ExcelProperty(value = "结束日期")
    private Date endDate;

    /**
     * 总计量器具数
     */
    @Schema(description = "总计量器具数")
    @ExcelProperty(value = "总计量器具数")
    private Integer totalMeters;

    /**
     * 已完成器具数
     */
    @Schema(description = "已完成器具数")
    @ExcelProperty(value = "已完成器具数")
    private Integer completedMeters;

    /**
     * 计划状态
     */
    @Schema(description = "计划状态")
    @ExcelProperty(value = "计划状态")
    private String status;

    /**
     * 负责人
     */
    @Schema(description = "负责人")
    @ExcelProperty(value = "负责人")
    private String responsiblePerson;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建者
     */
    @Schema(description = "创建者")
    private String createBy;
}
