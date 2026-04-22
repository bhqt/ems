package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 校准记录视图对象 ems_calibration_record
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@Schema(description = "校准记录视图对象")
@ExcelIgnoreUnannotated
public class CalibrationRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 记录编码
     */
    @Schema(description = "记录编码")
    @ExcelProperty(value = "记录编码")
    private String recordCode;

    /**
     * 校准计划ID
     */
    @Schema(description = "校准计划ID")
    private Long planId;

    /**
     * 计量器具ID
     */
    @Schema(description = "计量器具ID")
    private Long meterId;

    /**
     * 器具名称
     */
    @Schema(description = "器具名称")
    @ExcelProperty(value = "器具名称")
    private String meterName;

    /**
     * 校准日期
     */
    @Schema(description = "校准日期")
    @ExcelProperty(value = "校准日期")
    private Date calibrationDate;

    /**
     * 校准人员姓名
     */
    @Schema(description = "校准人员姓名")
    @ExcelProperty(value = "校准人员")
    private String calibratorName;

    /**
     * 校准结果
     */
    @Schema(description = "校准结果")
    @ExcelProperty(value = "校准结果")
    private String calibrationResult;

    /**
     * 偏差值(%)
     */
    @Schema(description = "偏差值(%)")
    @ExcelProperty(value = "偏差值")
    private BigDecimal deviation;

    /**
     * 证书编号
     */
    @Schema(description = "证书编号")
    @ExcelProperty(value = "证书编号")
    private String certificateNo;

    /**
     * 下次校准日期
     */
    @Schema(description = "下次校准日期")
    @ExcelProperty(value = "下次校准日期")
    private Date nextCalibrationDate;

    /**
     * 校准费用
     */
    @Schema(description = "校准费用")
    @ExcelProperty(value = "校准费用")
    private BigDecimal calibrationCost;

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
