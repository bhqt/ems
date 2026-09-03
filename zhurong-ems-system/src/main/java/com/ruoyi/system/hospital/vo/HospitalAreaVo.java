package com.ruoyi.system.hospital.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 医院院区视图对象
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelIgnoreUnannotated
public class HospitalAreaVo extends BaseEntity {

    /** 主键 */
    @ExcelProperty(value = "主键")
    private Long id;

    /** 院区编码 */
    @ExcelProperty(value = "院区编码")
    private String areaCode;

    /** 院区名称 */
    @ExcelProperty(value = "院区名称")
    private String areaName;

    /** 类型（0院区 1楼宇） */
    @ExcelProperty(value = "类型")
    private String areaType;

    /** 上级ID（0为顶级院区） */
    @ExcelProperty(value = "上级院区")
    private Long parentId;

    /** 状态（0正常 1停用） */
    @ExcelProperty(value = "状态")
    private String status;

    /** 排序 */
    @ExcelProperty(value = "排序")
    private Integer sort;

    /** 备注 */
    private String remark;
}
