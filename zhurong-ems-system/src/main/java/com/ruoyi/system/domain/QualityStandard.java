package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 质量标准
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("quality_standard")
public class QualityStandard extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 标准ID */
    @TableId(type = IdType.AUTO)
    private Long standardId;

    /** 标准编号 */
    private String standardCode;

    /** 标准名称 */
    private String standardName;

    /** 标准类型（1-原材料标准，2-过程标准，3-成品标准，4-检验标准） */
    private String standardType;

    /** 版本号 */
    private String version;

    /** 标准内容 */
    private String content;

    /** 生效日期 */
    private String effectiveDate;

    /** 状态（1-草稿，2-审批中，3-已发布，4-已过期） */
    private String status;

}
