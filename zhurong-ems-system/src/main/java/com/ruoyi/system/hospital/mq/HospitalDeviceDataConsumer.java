package com.ruoyi.system.hospital.mq;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.ruoyi.system.hospital.domain.HospitalDeviceData;
import com.ruoyi.system.hospital.domain.StandardDataPoint;
import com.ruoyi.system.hospital.mapper.HospitalDeviceDataMapper;
import com.ruoyi.system.hospital.mapper.HospitalTdMapper;
import com.ruoyi.system.hospital.service.IHospitalAlarmEvalService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 医院设备数据 MQ 消费者
 * 监听 hospital.device.data.queue，异步落库（MySQL + TDengine 可开关）
 *
 * @author cpems
 */
@RequiredArgsConstructor
@Component
public class HospitalDeviceDataConsumer {

    private static final Logger log = LoggerFactory.getLogger(HospitalDeviceDataConsumer.class);

    private final HospitalDeviceDataMapper deviceDataMapper;
    private final HospitalTdMapper hospitalTdMapper;
    private final IHospitalAlarmEvalService alarmEvalService;

    @Value("${spring.datasource.dynamic.datasource.td.enabled:false}")
    private boolean tdDbEnabled;

    @RabbitListener(queues = "${hospital.iot.queue}", ackMode = "MANUAL")
    public void onMessage(String payload, Channel channel,
                          @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            List<StandardDataPoint> points = JSON.parseArray(payload, StandardDataPoint.class);
            if (CollUtil.isNotEmpty(points)) {
                List<HospitalDeviceData> list = new ArrayList<>(points.size());
                for (StandardDataPoint p : points) {
                    list.add(toEntity(p));
                    // TDengine 时序落库（可开关，失败不影响主链路）
                    if (tdDbEnabled) {
                        saveToTdengine(p);
                    }
                }
                deviceDataMapper.insertBatch(list);
                // 报警触发引擎（阈值评估，失败仅日志不影响 ack）
                alarmEvalService.evalPoints(points);
            }
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("[IOT回调] 设备数据落库失败，requeue=false，需人工排查", e);
            try {
                channel.basicNack(deliveryTag, false, false);
            } catch (Exception nackEx) {
                log.error("[IOT回调] basicNack 失败", nackEx);
            }
        }
    }

    private HospitalDeviceData toEntity(StandardDataPoint p) {
        HospitalDeviceData data = new HospitalDeviceData();
        data.setDeviceId(p.getDeviceId());
        data.setMetricCode(p.getMetricCode());
        data.setMetricValue(p.getValue());
        data.setMetricStr(p.getStrValue());
        data.setTs(p.getTs());
        data.setQuality(p.getQuality() == null ? 0 : p.getQuality());
        data.setReceiveTime(new Date());
        return data;
    }

    private void saveToTdengine(StandardDataPoint p) {
        try {
            if (p.getValue() != null) {
                String tbName = generateTbName(p.getDeviceCode(), p.getMetricCode());
                hospitalTdMapper.insertPoint(tbName, p.getTs(), p.getDeviceCode(), p.getMetricCode(),
                    p.getValue().doubleValue(), p.getQuality() == null ? 0 : p.getQuality());
            }
        } catch (Exception e) {
            // TDengine 写入失败不阻断主链路，仅记录日志
            log.error("[IOT回调] TDengine 写入失败(不影响主链路) device={} metric={}", p.getDeviceCode(), p.getMetricCode(), e);
        }
    }

    /**
     * 生成 TDengine 子表名：deviceCode_metricCode，并清洗为合法表名字符
     */
    private String generateTbName(String deviceCode, String metricCode) {
        String cleanDevice = (deviceCode == null ? "dev" : deviceCode).replaceAll("[^a-zA-Z0-9_]", "_");
        String cleanMetric = (metricCode == null ? "metric" : metricCode).replaceAll("[^a-zA-Z0-9_]", "_");
        return cleanDevice + "_" + cleanMetric;
    }
}
