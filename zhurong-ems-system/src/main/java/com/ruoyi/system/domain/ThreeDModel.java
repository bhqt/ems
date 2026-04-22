package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("three_d_model")
public class ThreeDModel extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long modelId;
    private String modelCode;
    private String modelName;
    private Integer modelType;
    private Long parentId;
    private Integer levelOfDetail;
    private String filePath;
    private String thumbnailPath;
    private Integer status;
    private String description;
}
