package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("evaluation_report")
public class EvaluationReport extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long reportId;

    private String reportCode;

    private String reportName;

    private Integer reportType;

    private Date periodStart;

    private Date periodEnd;

    private Double costSaving;

    private Double efficiencyImprovement;

    private Double emissionReduction;

    private Double reliabilityImprovement;

    private String content;

}
