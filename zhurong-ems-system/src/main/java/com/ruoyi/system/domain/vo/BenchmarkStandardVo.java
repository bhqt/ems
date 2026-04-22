package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 标杆标准视图对象 ems_benchmark_standard
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@Schema(description = "标杆标准视图对象")
@ExcelIgnoreUnannotated
public class BenchmarkStandardVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 标准编码
     */
    @Schema(description = "标准编码")
    @ExcelProperty(value = "标准编码")
    private String standardCode;

    /**
     * 标准名称
     */
    @Schema(description = "标准名称")
    @ExcelProperty(value = "标准名称")
    private String standardName;

    /**
     * 标准类型
     */
    @Schema(description = "标准类型")
    @ExcelProperty(value = "标准类型")
    private String standardType;

    /**
     * 指标名称
     */
    @Schema(description = "指标名称")
    @ExcelProperty(value = "指标名称")
    private String indicatorName;

    /**
     * 指标单位
     */
    @Schema(description = "指标单位")
    @ExcelProperty(value = "指标单位")
    private String indicatorUnit;

    /**
     * 标准值
     */
    @Schema(description = "标准值")
    @ExcelProperty(value = "标准值")
    private BigDecimal standardValue;

    /**
     * 生效日期
     */
    @Schema(description = "生效日期")
    @ExcelProperty(value = "生效日期")
    private Date effectiveDate;

    /**
     * 失效日期
     */
    @Schema(description = "失效日期")
    @ExcelProperty(value = "失效日期")
    private Date expiryDate;

    /**
     * 标准状态
     */
    @Schema(description = "标准状态")
    @ExcelProperty(value = "标准状态")
    private String status;

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
