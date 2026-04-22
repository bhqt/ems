package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("optimization_scheme")
public class OptimizationScheme extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long schemeId;

    private String schemeCode;

    private String schemeName;

    private Integer optimizationType;

    private String algorithm;

    private Double objectiveValue;

    private String constraintsText;

    private String resultText;

    private Integer status;

}
