package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 校准记录对象 ems_calibration_record
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ems_calibration_record")
public class CalibrationRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 记录编码
     */
    private String recordCode;

    /**
     * 校准计划ID
     */
    private Long planId;

    /**
     * 计量器具ID
     */
    private Long meterId;

    /**
     * 器具名称
     */
    private String meterName;

    /**
     * 校准日期
     */
    private Date calibrationDate;

    /**
     * 校准人员ID
     */
    private Long calibratorId;

    /**
     * 校准人员姓名
     */
    private String calibratorName;

    /**
     * 校准结果(pass:合格, fail:不合格)
     */
    private String calibrationResult;

    /**
     * 偏差值(%)
     */
    private BigDecimal deviation;

    /**
     * 证书编号
     */
    private String certificateNo;

    /**
     * 证书文件
     */
    private String certificateFile;

    /**
     * 下次校准日期
     */
    private Date nextCalibrationDate;

    /**
     * 校准费用
     */
    private BigDecimal calibrationCost;

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
