package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("dispatch_command")
public class DispatchCommand extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long commandId;

    private String commandCode;

    private Integer commandType;

    private String targetDevice;

    private Long deviceId;

    private String parameter;

    private Date scheduledTime;

    private Date actualTime;

    private Long operatorId;

    private String operatorName;

    private Integer status;

    private String result;

}
