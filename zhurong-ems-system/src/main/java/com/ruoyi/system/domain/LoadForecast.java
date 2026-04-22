package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("load_forecast")
public class LoadForecast extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long loadId;

    private Integer forecastType;

    private Integer loadType;

    private Date predictedTime;

    private Double predictedLoad;

    private Double actualLoad;

    private Double errorRate;

    private String modelType;

}
