package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("energy_demand_forecast")
public class EnergyDemandForecast extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long forecastId;

    private Integer forecastType;

    private Integer energyType;

    private Date predictedTime;

    private Double predictedValue;

    private Double actualValue;

    private Double error;

    private String modelType;

}
