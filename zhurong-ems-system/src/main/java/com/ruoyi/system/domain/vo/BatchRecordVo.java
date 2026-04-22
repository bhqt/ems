package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 批次实绩视图对象 ems_batch_record
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@Schema(description = "批次实绩视图对象")
@ExcelIgnoreUnannotated
public class BatchRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 批次号
     */
    @Schema(description = "批次号")
    @ExcelProperty(value = "批次号")
    private String batchNo;

    /**
     * 产品ID
     */
    @Schema(description = "产品ID")
    private Long productId;

    /**
     * 产品名称
     */
    @Schema(description = "产品名称")
    @ExcelProperty(value = "产品名称")
    private String productName;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @ExcelProperty(value = "开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @ExcelProperty(value = "结束时间")
    private Date endTime;

    /**
     * 钢水重量(吨)
     */
    @Schema(description = "钢水重量(吨)")
    @ExcelProperty(value = "钢水重量")
    private BigDecimal steelWeight;

    /**
     * 电能消耗(kWh)
     */
    @Schema(description = "电能消耗(kWh)")
    @ExcelProperty(value = "电能消耗")
    private BigDecimal electricity;

    /**
     * 水消耗(m³)
     */
    @Schema(description = "水消耗(m³)")
    @ExcelProperty(value = "水消耗")
    private BigDecimal water;

    /**
     * 煤气消耗(m³)
     */
    @Schema(description = "煤气消耗(m³)")
    @ExcelProperty(value = "煤气消耗")
    private BigDecimal gas;

    /**
     * 煤炭消耗(吨)
     */
    @Schema(description = "煤炭消耗(吨)")
    @ExcelProperty(value = "煤炭消耗")
    private BigDecimal coal;

    /**
     * 能源成本(元)
     */
    @Schema(description = "能源成本(元)")
    @ExcelProperty(value = "能源成本")
    private BigDecimal energyCost;

    /**
     * 批次状态
     */
    @Schema(description = "批次状态")
    @ExcelProperty(value = "批次状态")
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
