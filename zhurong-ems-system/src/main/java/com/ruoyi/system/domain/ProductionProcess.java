package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 工序
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("production_process")
public class ProductionProcess extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 工序ID */
    @TableId(type = IdType.AUTO)
    private Long processId;

    /** 工序编号 */
    private String processCode;

    /** 工序名称 */
    private String processName;

    /** 工序描述 */
    private String description;

    /** 标准时间（分钟） */
    private Double standardTime;

    /** 工序顺序 */
    private Integer sequence;

}
