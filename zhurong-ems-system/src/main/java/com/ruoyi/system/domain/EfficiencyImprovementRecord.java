package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("efficiency_improvement_record")
public class EfficiencyImprovementRecord extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long recordId;
    private Long schemeId;
    private Date recordDate;
    private Double beforeEfficiency;
    private Double afterEfficiency;
    private Double improvement;
    private String efficiencyType;
    private String description;
}
