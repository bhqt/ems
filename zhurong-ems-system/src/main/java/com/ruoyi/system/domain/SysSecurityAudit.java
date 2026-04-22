package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 安全审计日志
 * @author cpems
 */
@Data
@TableName("sys_security_audit")
public class SysSecurityAudit {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 操作内容
     */
    private String operationContent;

    /**
     * 是否成功 1-成功 0-失败
     */
    private Integer success;

    /**
     * 操作IP
     */
    private String ipAddress;

    /**
     * 操作时间
     */
    private Date operationTime;
}
