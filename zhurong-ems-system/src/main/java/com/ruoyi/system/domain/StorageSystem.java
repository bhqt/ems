package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 储能系统
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
@TableName("storage_system")
public class StorageSystem extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 系统ID */
    @TableId(type = IdType.AUTO)
    private Long systemId;

    /** 系统名称 */
    private String name;

    /** 容量（kWh） */
    private Double capacity;

    /** 类型（锂电池、铅酸电池等） */
    private String type;

    /** 厂家 */
    private String manufacturer;

    /** 投运日期 */
    private String commissionDate;

    /** 状态（1-运行中，2-停机，3-维护） */
    private String status;

}
