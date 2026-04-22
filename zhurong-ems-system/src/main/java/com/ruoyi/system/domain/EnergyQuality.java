package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 能源质量对象 ems_energy_quality
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ems_energy_quality")
public class EnergyQuality extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 检测日期
     */
    private Date qualityDate;

    /**
     * 能源介质(electricity:电力, water:水, gas:煤气, steam:蒸汽)
     */
    private String energyMedium;

    /**
     * 参数名称
     */
    private String parameterName;

    /**
     * 标准值
     */
    private String standardValue;

    /**
     * 实际值
     */
    private String actualValue;

    /**
     * 偏差(%)
     */
    private BigDecimal deviation;

    /**
     * 质量状态(normal:正常, warning:警告, error:异常)
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
