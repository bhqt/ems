package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 计量器具信息对象 ems_meter_info
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ems_meter_info")
public class MeterInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 器具编码
     */
    private String meterCode;

    /**
     * 器具名称
     */
    private String meterName;

    /**
     * 器具类型(electric:电能表, water:水表, gas:煤气表, steam:蒸汽表)
     */
    private String meterType;

    /**
     * 型号
     */
    private String meterModel;

    /**
     * 精度等级
     */
    private String accuracyLevel;

    /**
     * 安装位置
     */
    private String installLocation;

    /**
     * 安装日期
     */
    private Date installDate;

    /**
     * 生产厂家
     */
    private String manufacturer;

    /**
     * 出厂日期
     */
    private Date manufacturerDate;

    /**
     * 器具状态(normal:正常, warning:需校准, error:故障, maintenance:维护中)
     */
    private String status;

    /**
     * 上次校准日期
     */
    private Date lastCalibrationDate;

    /**
     * 下次校准日期
     */
    private Date nextCalibrationDate;

    /**
     * 校准周期(月)
     */
    private Integer calibrationCycle;

    /**
     * 证书编号
     */
    private String certificateNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 所属用户
     */
    private Long userId;

    /**
     * 所属部门
     */
    private Long deptId;

    /**
     * 删除标志(0:正常, 1:删除)
     */
    @TableLogic
    private String delFlag;

    /**
     * 删除者
     */
    private String delBy;

    /**
     * 删除时间
     */
    private Date delTime;
}
