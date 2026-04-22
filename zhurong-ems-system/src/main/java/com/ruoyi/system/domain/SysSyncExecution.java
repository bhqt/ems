package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_sync_execution")
public class SysSyncExecution extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long taskId;

    private String taskCode;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private Integer totalCount;

    private Integer successCount;

    private Integer failCount;

    private String sourceData;

    private String targetData;

    private String errorMessage;

    private String executionLog;
}
