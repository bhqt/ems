package com.ruoyi.system.domain.bo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 储能系统业务对象
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
public class EnergyStorageBo {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 储能系统名称
     */
    private String storageName;

    /**
     * 储能系统编号
     */
    private String storageCode;

    /**
     * 系统类型
     */
    private String storageType;

    /**
     * 装机容量(kWh)
     */
    private BigDecimal capacity;

    /**
     * 额定功率(kW)
     */
    private BigDecimal power;

    /**
     * 电压等级
     */
    private String voltageLevel;

    /**
     * 电池类型
     */
    private String batteryType;

    /**
     * 所在区域ID
     */
    private Long areaId;

    /**
     * 地址
     */
    private String address;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 投运日期
     */
    private Date commissioningDate;

    /**
     * 状态（1-正常 2-故障 3-维护）
     */
    private String status;

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
