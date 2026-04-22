package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验结果
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("inspection_result")
public class InspectionResult extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 结果ID */
    @TableId(type = IdType.AUTO)
    private Long resultId;

    /** 任务ID */
    private Long taskId;

    /** 检验项目 */
    private String inspectionItem;

    /** 标准值 */
    private String standardValue;

    /** 实际值 */
    private String actualValue;

    /** 结果（1-合格，2-不合格，3-让步接收） */
    private String result;

    /** 备注 */
    private String remark;

}
