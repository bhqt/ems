package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("twin_device")
public class TwinDevice extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long deviceId;
    private String deviceCode;
    private String deviceName;
    private Integer deviceType;
    private Long modelId;
    private String location;
    private Double positionX;
    private Double positionY;
    private Double positionZ;
    private Double rotationX;
    private Double rotationY;
    private Double rotationZ;
    private Double scaleX;
    private Double scaleY;
    private Double scaleZ;
    private Integer status;
}
