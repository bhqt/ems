package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 光伏电站信息业务对象
 *
 * @author cpems
 * @date 2026-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PvStationBo extends BaseEntity {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 电站名称
     */
    @NotBlank(message = "电站名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String stationName;

    /**
     * 电站编号
     */
    @NotBlank(message = "电站编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String stationCode;

    /**
     * 电站类型（1-屋顶光伏 2-地面光伏 3-农光互补 4-渔光互补）
     */
    @NotBlank(message = "电站类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String stationType;

    /**
     * 装机容量(kW)
     */
    @NotNull(message = "装机容量不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal capacity;

    /**
     * 所属区域ID
     */
    @NotNull(message = "所属区域不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long areaId;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 安装日期
     */
    private Date installDate;

    /**
     * 并网日期
     */
    private Date gridDate;

    /**
     * 设计寿命(年)
     */
    private Integer designLife;

    /**
     * 组件类型（1-单晶硅 2-多晶硅 3-薄膜）
     */
    private String componentType;

    /**
     * 逆变器型号
     */
    private String inverterModel;

    /**
     * 逆变器数量
     */
    private Integer inverterCount;

    /**
     * 组件数量
     */
    private Integer componentCount;

    /**
     * 电站状态（0-停用 1-正常 2-故障 3-维护）
     */
    private String status;

    /**
     * 负责人
     */
    private String manager;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 备注
     */
    private String remark;
}
