package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 同步模板管理
 * @author cpems
 */
@Data
@TableName("sys_sync_template")
public class SysSyncTemplate {

    /**
     * 模板ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板类型
     */
    private String templateType;

    /**
     * 源系统
     */
    private String sourceSystem;

    /**
     * 目标系统
     */
    private String targetSystem;

    /**
     * 字段映射
     */
    private String fieldMapping;

    /**
     * 转换规则
     */
    private String transformRules;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;
}
