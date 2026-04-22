package com.ruoyi.system.domain.vo;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 充电站品牌视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChargingBrandVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 品牌ID
     */
    private Long id;
    
    /**
     * 品牌名称
     */
    private String brandName;
    
    /**
     * 状态（0正常 1停用）
     */
    private String status;
    
    /**
     * 状态名称
     */
    private String statusName;
    
    /**
     * 备注
     */
    private String remark;

}
