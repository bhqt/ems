package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 能源质量视图对象 ems_energy_quality
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@Schema(description = "能源质量视图对象")
@ExcelIgnoreUnannotated
public class EnergyQualityVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 检测日期
     */
    @Schema(description = "检测日期")
    @ExcelProperty(value = "检测日期")
    private Date qualityDate;

    /**
     * 能源介质
     */
    @Schema(description = "能源介质")
    @ExcelProperty(value = "能源介质")
    private String energyMedium;

    /**
     * 参数名称
     */
    @Schema(description = "参数名称")
    @ExcelProperty(value = "参数名称")
    private String parameterName;

    /**
     * 标准值
     */
    @Schema(description = "标准值")
    @ExcelProperty(value = "标准值")
    private String standardValue;

    /**
     * 实际值
     */
    @Schema(description = "实际值")
    @ExcelProperty(value = "实际值")
    private String actualValue;

    /**
     * 偏差(%)
     */
    @Schema(description = "偏差(%)")
    @ExcelProperty(value = "偏差")
    private BigDecimal deviation;

    /**
     * 质量状态
     */
    @Schema(description = "质量状态")
    @ExcelProperty(value = "质量状态")
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
