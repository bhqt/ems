package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 物料需求
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("material_requirement")
public class MaterialRequirement extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 需求ID */
    @TableId(type = IdType.AUTO)
    private Long requirementId;

    /** 计划ID */
    private Long planId;

    /** 物料ID */
    private Long materialId;

    /** 需求数量 */
    private Double requirementQuantity;

    /** 需求日期 */
    private String requiredDate;

    /** 状态（1-待采购，2-已采购，3-已入库） */
    private String status;

}
