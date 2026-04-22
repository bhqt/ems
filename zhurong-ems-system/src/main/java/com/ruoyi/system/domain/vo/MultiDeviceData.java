package com.ruoyi.system.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 多设备数据项
 *
 * @Author cpems
 */
@Data
public class MultiDeviceData {
    private String deviceType;
    private String clientId;
    private BigDecimal value;
}
