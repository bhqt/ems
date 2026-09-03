package com.ruoyi.system.hospital.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.system.hospital.domain.StandardDataPoint;
import com.ruoyi.system.hospital.dto.IotCallbackRequest;
import com.ruoyi.system.hospital.service.IHospitalDeviceService;
import com.ruoyi.system.hospital.service.IotDataParser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * IOT 回调报文解析标准化 Service 实现
 *
 * @author cpems
 */
@RequiredArgsConstructor
@Service
public class IotDataParserImpl implements IotDataParser {

    private static final Logger log = LoggerFactory.getLogger(IotDataParserImpl.class);

    private final IHospitalDeviceService hospitalDeviceService;

    @Override
    public List<StandardDataPoint> parse(IotCallbackRequest request) {
        List<StandardDataPoint> points = new ArrayList<>();
        if (request == null || CollUtil.isEmpty(request.getDevices())) {
            return points;
        }
        for (IotCallbackRequest.Device device : request.getDevices()) {
            if (device == null || StrUtil.isBlank(device.getDeviceId())
                || CollUtil.isEmpty(device.getPoints())) {
                continue;
            }
            // IOT 设备 ID -> 本系统设备 ID（未绑定则跳过并告警）
            Long deviceId = hospitalDeviceService.queryDeviceIdByIotDeviceId(device.getDeviceId());
            if (deviceId == null) {
                log.warn("[IOT回调] IOT设备未绑定本系统台账，跳过 deviceId={}", device.getDeviceId());
                continue;
            }
            for (IotCallbackRequest.Point point : device.getPoints()) {
                if (point == null || StrUtil.isBlank(point.getMetric())) {
                    continue;
                }
                StandardDataPoint sp = new StandardDataPoint();
                sp.setDeviceId(deviceId);
                sp.setDeviceCode(device.getDeviceId());
                sp.setMetricCode(point.getMetric());
                sp.setTs(parseTime(point.getTs()));
                sp.setQuality(point.getQuality() == null ? 0 : point.getQuality());
                fillValue(sp, point.getValue());
                points.add(sp);
            }
        }
        return points;
    }

    /**
     * 解析采集时间，兼容 yyyy-MM-dd HH:mm:ss 与 ISO8601
     */
    private Date parseTime(String ts) {
        if (StrUtil.isBlank(ts)) {
            return new Date();
        }
        try {
            return DateUtil.parse(ts);
        } catch (Exception e) {
            return new Date();
        }
    }

    /**
     * 数值可转 BigDecimal 则存数值字段，否则存字符串字段
     */
    private void fillValue(StandardDataPoint sp, Object value) {
        if (value == null) {
            return;
        }
        try {
            sp.setValue(new BigDecimal(String.valueOf(value)));
        } catch (NumberFormatException e) {
            sp.setStrValue(String.valueOf(value));
        }
    }
}
