package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("energy_price_forecast")
public class EnergyPriceForecast extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long priceId;

    private Integer energyType;

    private Date predictedTime;

    private Double predictedPrice;

    private Double actualPrice;

    private Double errorRate;

    private String priceType;

    private String modelType;

}
