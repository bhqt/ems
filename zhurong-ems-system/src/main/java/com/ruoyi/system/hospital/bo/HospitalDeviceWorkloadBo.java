package com.ruoyi.system.hospital.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 医院设备工作量（检查量）业务对象
 *
 * @author cpems
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HospitalDeviceWorkloadBo extends BaseEntity {

    /** 主键 */
    @NotNull(message = "主键不能为空", groups = {EditGroup.class})
    private Long id;

    /** 设备ID（hospital_device.id） */
    @NotNull(message = "设备ID不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long deviceId;

    /** 工作量（检查台次） */
    private BigDecimal workloadCount;

    /** 统计日期 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date statDate;

    /** 开始日期（查询条件） */
    private Date beginStatDate;

    /** 结束日期（查询条件） */
    private Date endStatDate;
}
