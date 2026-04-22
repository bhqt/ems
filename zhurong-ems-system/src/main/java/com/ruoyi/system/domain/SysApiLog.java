package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_api_log")
public class SysApiLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long apiId;

    private String apiCode;

    private String requestUrl;

    private String method;

    private String requestParams;

    private String requestBody;

    private String responseData;

    private Integer responseTime;

    private Integer statusCode;

    private Integer status;

    private String errorMessage;

    private String ipAddress;

    private String userAgent;
}
