package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 充电站品牌业务对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChargingBrandBo extends BaseEntity {

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
     * 备注
     */
    private String remark;

}
