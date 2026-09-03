package com.ruoyi.system.hospital.constant;

/**
 * 医院智慧能源 - 常量定义
 *
 * @author cpems
 */
public class HospitalConstants {

    private HospitalConstants() {
    }

    /** 回调接口请求头：IOT 平台 Token */
    public static final String IOT_TOKEN_HEADER = "X-IOT-Token";

    /** 回调接口路径前缀 */
    public static final String CALLBACK_PATH = "/hospital/callback";

    /** RabbitMQ 交换机 */
    public static final String MQ_EXCHANGE = "hospital.topic.exchange";

    /** RabbitMQ 设备数据队列 */
    public static final String MQ_QUEUE_DEVICE_DATA = "hospital.device.data.queue";

    /** RabbitMQ 路由键 */
    public static final String MQ_ROUTING_DEVICE_DATA = "hospital.device.data";

    /** 设备状态：正常 */
    public static final String DEVICE_STATUS_NORMAL = "0";

    /** 设备状态：停用 */
    public static final String DEVICE_STATUS_DISABLED = "1";

    /** 设备状态：离线 */
    public static final String DEVICE_STATUS_OFFLINE = "2";

    /** 回调处理状态：成功 */
    public static final String CALLBACK_STATUS_SUCCESS = "success";

    /** 回调处理状态：鉴权失败 */
    public static final String CALLBACK_STATUS_AUTH_FAIL = "auth_fail";

    /** 回调处理状态：解析失败 */
    public static final String CALLBACK_STATUS_PARSE_FAIL = "parse_fail";

    /** 回调处理状态：失败 */
    public static final String CALLBACK_STATUS_FAIL = "fail";

    /** 消息质量：正常 */
    public static final Integer DATA_QUALITY_GOOD = 0;

    /** 消息质量：异常 */
    public static final Integer DATA_QUALITY_BAD = 1;

    /** 报警规则类型：阈值 */
    public static final String ALARM_RULE_THRESHOLD = "THRESHOLD";

    /** 报警规则类型：离线 */
    public static final String ALARM_RULE_OFFLINE = "OFFLINE";

    /** 报警类型：过载 */
    public static final String ALARM_TYPE_OVERLOAD = "OVERLOAD";

    /** 报警类型：离线 */
    public static final String ALARM_TYPE_OFFLINE = "OFFLINE";

    /** 报警记录状态：待处理 */
    public static final String ALARM_STATUS_OPEN = "0";

    /** 报警记录状态：已结束 */
    public static final String ALARM_STATUS_CLOSED = "1";
}
