package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("emission_reduction_record")
public class EmissionReductionRecord extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long recordId;
    private Long schemeId;
    private Date recordDate;
    private Double beforeEmission;
    private Double afterEmission;
    private Double reductionAmount;
    private Double reductionRate;
    private String emissionType;
    private String description;
}
