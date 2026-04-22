package com.ruoyi.system.domain.vo;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 微电网视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MicroGridVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 微电网ID
     */
    private Long id;

    /**
     * 微电网名称
     */
    private String name;

    /**
     * 微电网编码
     */
    private String code;

    /**
     * 所在区域
     */
    private String region;

    /**
     * 电压等级(kV)
     */
    private BigDecimal voltageLevel;

    /**
     * 总容量(kW)
     */
    private BigDecimal totalCapacity;

    /**
     * 最大负荷(kW)
     */
    private BigDecimal maxLoad;

    /**
     * 并网状态：0-离网，1-并网
     */
    private Integer gridStatus;

    /**
     * 并网状态名称
     */
    private String gridStatusName;

    /**
     * 运行状态：0-停用，1-运行
     */
    private Integer status;

    /**
     * 运行状态名称
     */
    private String statusName;

    /**
     * 描述
     */
    private String description;

    /**
     * 备注
     */
    private String remark;
}
