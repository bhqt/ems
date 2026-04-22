package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 预测数据
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("forecast_data")
public class ForecastData extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 数据ID */
    @TableId(type = IdType.AUTO)
    private Long dataId;

    /** 预测类型（1-光伏发电，2-负荷） */
    private String forecastType;

    /** 预测时间类型（1-短期，2-中期，3-长期） */
    private String timeType;

    /** 预测时间 */
    private String forecastTime;

    /** 预测值 */
    private Double forecastValue;

    /** 实际值 */
    private Double actualValue;

    /** 预测精度（%） */
    private Double accuracy;

}
