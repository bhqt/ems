package com.ruoyi.system.hospital.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 医院院区对象 hospital_area
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hospital_area")
public class HospitalArea extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id")
    private Long id;

    /** 院区编码 */
    private String areaCode;

    /** 院区名称 */
    private String areaName;

    /** 类型（0院区 1楼宇） */
    private String areaType;

    /** 上级ID（0为顶级院区） */
    private Long parentId;

    /** 状态（0正常 1停用） */
    private String status;

    /** 排序 */
    private Integer sort;

    /** 备注 */
    private String remark;
}
