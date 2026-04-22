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
@TableName("sys_sync_task")
public class SysSyncTask extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String taskCode;

    private String taskName;

    private String taskType;

    private String sourceSystem;

    private String targetSystem;

    private Long sourceInterfaceId;

    private Long targetInterfaceId;

    private Integer syncType;

    private String syncFrequency;

    private String cronExpression;

    private String fieldMapping;

    private String transformRules;

    private Integer enabled;

    private String description;
}
