package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 校准计划对象 ems_calibration_plan
 *
 * @author cpems
 * @date 2026-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ems_calibration_plan")
public class CalibrationPlan extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 计划编码
     */
    private String planCode;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 计划类型(regular:定期校准, temporary:临时校准)
     */
    private String planType;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 总计量器具数
     */
    private Integer totalMeters;

    /**
     * 已完成器具数
     */
    private Integer completedMeters;

    /**
     * 计划状态(pending:待开始, in_progress:进行中, completed:已完成, cancelled:已取消)
     */
    private String status;

    /**
     * 负责人
     */
    private String responsiblePerson;

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
