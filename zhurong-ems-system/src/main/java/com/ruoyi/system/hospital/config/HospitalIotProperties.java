package com.ruoyi.system.hospital.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 医院智慧能源 - IOT 回调接入配置
 *
 * @author cpems
 */
@Data
@Component
@ConfigurationProperties(prefix = "hospital.iot")
public class HospitalIotProperties {

    /** 回调鉴权 token（IOT 平台请求头 X-IOT-Token 携带） */
    private String authToken = "hospital-iot-2026";

    /** 是否校验签名（预留，默认关闭） */
    private Boolean signEnabled = false;

    /** 签名密钥（signEnabled=true 时生效，为空则不校验签名） */
    private String signSecret = "";

    /** IP 白名单（逗号/分号分隔，为空表示不限制） */
    private String ipWhitelist = "";

    /** RabbitMQ 交换机 */
    private String exchange = "hospital.topic.exchange";

    /** RabbitMQ 设备数据队列 */
    private String queue = "hospital.device.data.queue";

    /** RabbitMQ 路由键 */
    private String routingKey = "hospital.device.data";

    /** 监测页离线判定阈值（分钟）：无数据超过该时长视为离线 */
    private Integer monitorOfflineMinutes = 30;
}
