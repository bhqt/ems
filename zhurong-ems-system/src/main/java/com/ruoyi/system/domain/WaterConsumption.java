package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用水量值对象
 *
 */
@Data
@TableName("water_consumption")
public class WaterConsumption {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 设备id
     */
    private String clientId;
    /**
     * 用水量
     */
    private BigDecimal value;

    /**
     * 创建时间
     */
    private Date createTime;

}
