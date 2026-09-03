package com.ruoyi.system.hospital.service;

import com.ruoyi.system.hospital.domain.StandardDataPoint;
import com.ruoyi.system.hospital.dto.IotCallbackRequest;

import java.util.List;

/**
 * IOT 回调报文解析标准化 Service
 *
 * @author cpems
 */
public interface IotDataParser {

    /**
     * 将 IOT 原始报文解析为标准数据点列表
     *
     * @param request IOT 回调报文
     * @return 标准数据点列表
     */
    List<StandardDataPoint> parse(IotCallbackRequest request);
}
