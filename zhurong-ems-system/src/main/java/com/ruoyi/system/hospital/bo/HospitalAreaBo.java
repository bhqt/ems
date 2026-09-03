package com.ruoyi.system.hospital.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 医院院区业务对象
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HospitalAreaBo extends BaseEntity {

    /** 主键 */
    @NotNull(message = "主键不能为空", groups = {EditGroup.class})
    private Long id;

    /** 院区编码 */
    @NotBlank(message = "院区编码不能为空", groups = {AddGroup.class, EditGroup.class})
    private String areaCode;

    /** 院区名称 */
    @NotBlank(message = "院区名称不能为空", groups = {AddGroup.class, EditGroup.class})
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
