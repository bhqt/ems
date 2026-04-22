package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 充电站品牌信息对象 charging_brand
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("charging_brand")
public class ChargingBrand extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 品牌ID
     */
    @TableId(value = "id")
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
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;
    
    /**
     * 备注
     */
    private String remark;

}
