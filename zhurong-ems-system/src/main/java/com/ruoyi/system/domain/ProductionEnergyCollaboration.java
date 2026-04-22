package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("production_energy_collaboration")
public class ProductionEnergyCollaboration extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long collabId;

    private Long productionPlanId;

    private Long energyPlanId;

    private Double productionDemand;

    private Double energySupply;

    private Double matchRate;

    private String optimizationSuggestion;

    private Integer status;

}
