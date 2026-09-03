package com.ruoyi.system.hospital.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * IOT 平台回调请求报文
 *
 * <pre>
 * {
 *   "msgId": "msg-001",
 *   "timestamp": "2026-09-03 10:00:00",
 *   "devices": [
 *     {
 *       "deviceId": "iot-device-001",
 *       "points": [
 *         {"metric": "power", "value": 12.34, "ts": "2026-09-03 10:00:00", "quality": 0}
 *       ]
 *     }
 *   ]
 * }
 * </pre>
 *
 * @author cpems
 */
@Data
public class IotCallbackRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** IOT 平台消息 ID（用于日志与去重） */
    private String msgId;

    /** IOT 平台消息时间戳 */
    private String timestamp;

    /** 设备数据列表 */
    private List<Device> devices;

    /**
     * IOT 平台设备数据
     */
    @Data
    public static class Device implements Serializable {

        private static final long serialVersionUID = 1L;

        /** IOT 平台设备 ID（需与 hospital_device.iot_device_id 绑定） */
        private String deviceId;

        /** 数据点列表 */
        private List<Point> points;
    }

    /**
     * 设备数据点
     */
    @Data
    public static class Point implements Serializable {

        private static final long serialVersionUID = 1L;

        /** 指标编码（对应 hospital_metric_def.metric_code） */
        private String metric;

        /** 指标值（数值或状态码） */
        private Object value;

        /** 采集时间 */
        private String ts;

        /** 数据质量（0正常 1异常） */
        private Integer quality;
    }
}
