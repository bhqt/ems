package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("energy_flow")
public class EnergyFlow extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long flowId;
    private Integer flowType;
    private Long sourceId;
    private String sourceName;
    private Long targetId;
    private String targetName;
    private Double flowValue;
    private Double flowRate;
    private Double lossRate;
    private Double efficiency;
    private Date timestamp;
}
