package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("twin_pipeline")
public class TwinPipeline extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long pipelineId;
    private String pipelineCode;
    private String pipelineName;
    private Integer pipelineType;
    private Long modelId;
    private String startPoint;
    private String endPoint;
    private Double startX;
    private Double startY;
    private Double startZ;
    private Double endX;
    private Double endY;
    private Double endZ;
    private Double length;
    private Double diameter;
    private Double flowRate;
    private Double pressure;
    private Double temperature;
    private Integer status;
}
