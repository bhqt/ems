package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 批次实绩对象 ems_batch_record
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ems_batch_record")
public class BatchRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 钢水重量(吨)
     */
    private BigDecimal steelWeight;

    /**
     * 电能消耗(kWh)
     */
    private BigDecimal electricity;

    /**
     * 水消耗(m³)
     */
    private BigDecimal water;

    /**
     * 煤气消耗(m³)
     */
    private BigDecimal gas;

    /**
     * 煤炭消耗(吨)
     */
    private BigDecimal coal;

    /**
     * 能源成本(元)
     */
    private BigDecimal energyCost;

    /**
     * 批次状态(in_progress:进行中, completed:已完成, cancelled:已取消)
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 所属用户
     */
    private Long userId;

    /**
     * 所属部门
     */
    private Long deptId;

    /**
     * 删除标志(0:正常, 1:删除)
     */
    @TableLogic
    private String delFlag;

    /**
     * 删除者
     */
    private String delBy;

    /**
     * 删除时间
     */
    private Date delTime;
}
