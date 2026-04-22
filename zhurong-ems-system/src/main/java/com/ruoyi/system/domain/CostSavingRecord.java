package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("cost_saving_record")
public class CostSavingRecord extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long recordId;
    private Long schemeId;
    private Date recordDate;
    private Double beforeCost;
    private Double afterCost;
    private Double savingAmount;
    private Double savingRate;
    private String costType;
    private String description;
}
