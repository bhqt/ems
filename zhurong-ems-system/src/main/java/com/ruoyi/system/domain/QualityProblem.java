package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 质量问题
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("quality_problem")
public class QualityProblem extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 问题ID */
    @TableId(type = IdType.AUTO)
    private Long problemId;

    /** 问题编号 */
    private String problemCode;

    /** 问题描述 */
    private String problemDescription;

    /** 问题类型（1-原材料问题，2-工艺问题，3-设备问题，4-人员问题） */
    private String problemType;

    /** 严重程度（1-轻微，2-一般，3-严重，4-致命） */
    private String severity;

    /** 发生时间 */
    private String occurrenceTime;

    /** 发生地点 */
    private String location;

    /** 状态（1-待处理，2-处理中，3-已解决，4-已关闭） */
    private String status;

}
