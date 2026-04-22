package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 报表模板
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("report_template")
public class ReportTemplate extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 模板ID */
    @TableId(type = IdType.AUTO)
    private Long templateId;

    /** 模板名称 */
    private String templateName;

    /** 模板类型 */
    private String templateType;

    /** 模板描述 */
    private String templateDesc;

    /** 模板内容 */
    private String templateContent;

    /** 状态（0正常 1停用） */
    private String status;

    /** 排序 */
    private Integer orderNum;

}
