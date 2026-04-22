package com.ruoyi.system.domain.bo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 储能电池组业务对象
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
public class StorageBatteryBo {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 储能系统ID
     */
    private Long storageId;

    /**
     * 电池组名称
     */
    private String batteryName;

    /**
     * 电池组编号
     */
    private String batteryCode;

    /**
     * 电池型号
     */
    private String batteryModel;

    /**
     * 额定容量(Ah)
     */
    private BigDecimal ratedCapacity;

    /**
     * 额定电压(V)
     */
    private BigDecimal ratedVoltage;

    /**
     * 串数
     */
    private Integer seriesCount;

    /**
     * 并数
     */
    private Integer parallelCount;

    /**
     * 总容量(kWh)
     */
    private BigDecimal totalCapacity;

    /**
     * 状态（1-正常 2-故障 3-维护）
     */
    private String status;

    /**
     * 投运日期
     */
    private Date commissioningDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志（0-正常 1-删除）
     */
    private String delFlag;
}
