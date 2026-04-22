package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 储能电池组视图对象
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Data
public class StorageBatteryVo {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 储能系统ID
     */
    private Long storageId;

    /**
     * 储能系统名称
     */
    private String storageName;

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
     * 状态名称
     */
    private String statusName;

    /**
     * 投运日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 当前SOC
     */
    private BigDecimal currentSoc;

    /**
     * 当前电压
     */
    private BigDecimal currentVoltage;

    /**
     * 当前电流
     */
    private BigDecimal currentCurrent;

    /**
     * 当前温度
     */
    private BigDecimal currentTemperature;

    /**
     * 循环次数
     */
    private Integer cycleCount;

    /**
     * 健康状态
     */
    private BigDecimal soh;
}
