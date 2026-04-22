package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("network_node")
public class NetworkNode extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long nodeId;

    private Long networkId;

    private String nodeCode;

    private String nodeName;

    private Integer nodeType;

    private String location;

    private Double capacity;

    private Integer status;

}
