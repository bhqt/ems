package com.ruoyi.system.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;

/**
 * 批次实绩业务对象 ems_batch_record
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "批次实绩业务对象")
public class BatchRecordBo extends com.ruoyi.common.core.domain.BaseEntity {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 批次号
     */
    @Schema(description = "批次号", required = true)
    @NotBlank(message = "批次号不能为空", groups = {AddGroup.class, EditGroup.class})
    private String batchNo;

    /**
     * 产品ID
     */
    @Schema(description = "产品ID")
    private Long productId;

    /**
     * 产品名称
     */
    @Schema(description = "产品名称", required = true)
    @NotBlank(message = "产品名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String productName;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间", required = true)
    @NotNull(message = "开始时间不能为空", groups = {AddGroup.class, EditGroup.class})
    private Date startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private Date endTime;

    /**
     * 钢水重量(吨)
     */
    @Schema(description = "钢水重量(吨)", required = true)
    @NotNull(message = "钢水重量不能为空", groups = {AddGroup.class, EditGroup.class})
    private BigDecimal steelWeight;

    /**
     * 电能消耗(kWh)
     */
    @Schema(description = "电能消耗(kWh)")
    private BigDecimal electricity;

    /**
     * 水消耗(m³)
     */
    @Schema(description = "水消耗(m³)")
    private BigDecimal water;

    /**
     * 煤气消耗(m³)
     */
    @Schema(description = "煤气消耗(m³)")
    private BigDecimal gas;

    /**
     * 煤炭消耗(吨)
     */
    @Schema(description = "煤炭消耗(吨)")
    private BigDecimal coal;

    /**
     * 能源成本(元)
     */
    @Schema(description = "能源成本(元)")
    private BigDecimal energyCost;

    /**
     * 批次状态
     */
    @Schema(description = "批次状态")
    private String status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 所属用户
     */
    @Schema(description = "所属用户")
    private Long userId;

    /**
     * 所属部门
     */
    @Schema(description = "所属部门")
    private Long deptId;
}
