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
@TableName("sys_interface_config")
public class SysInterfaceConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long configId;

    private String interfaceCode;

    private String interfaceName;

    private String url;

    private String method;

    private String requestHeaders;

    private String requestParams;

    private String requestBody;

    private String responseParams;

    private String authType;

    private Integer retryCount;

    private Integer retryInterval;

    private Integer status;
}
