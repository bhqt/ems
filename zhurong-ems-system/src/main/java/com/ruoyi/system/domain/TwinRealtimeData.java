package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("twin_realtime_data")
public class TwinRealtimeData extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long dataId;
    private Long objectId;
    private Integer objectType;
    private String parameterName;
    private Double parameterValue;
    private String unit;
    private Date timestamp;
    private Integer status;
}
