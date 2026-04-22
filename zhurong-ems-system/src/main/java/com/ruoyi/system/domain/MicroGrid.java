package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 微电网
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("micro_grid")
public class MicroGrid extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 微电网ID */
    @TableId(type = IdType.AUTO)
    private Long gridId;

    /** 微电网名称 */
    private String name;

    /** 描述 */
    private String description;

    /** 状态（1-运行中，2-停机，3-维护） */
    private String status;

    /** 运行模式（1-并网，2-孤岛） */
    private String operationMode;

}
