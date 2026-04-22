package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("forecast_model_config")
public class ForecastModelConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long configId;

    private String modelName;

    private String modelType;

    private String targetVariable;

    private String features;

    private String parameters;

    private Double accuracy;

    private Integer status;

    private Date lastTrainTime;

}
