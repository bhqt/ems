package com.ruoyi.system.hospital.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 医院 IOT 回调日志对象 hospital_callback_log
 *
 * @author cpems
 */
@Data
@TableName("hospital_callback_log")
public class HospitalCallbackLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id")
    private Long id;

    /** 请求 ID（IOT 平台消息 ID） */
    private String requestId;

    /** 来源 IP */
    private String sourceIp;

    /** IOT 消息时间戳 */
    private String iotTimestamp;

    /** 设备数量 */
    private Integer deviceCount;

    /** 数据点数量 */
    private Integer pointCount;

    /** 处理状态（success/auth_fail/parse_fail/fail） */
    private String status;

    /** 错误信息 */
    private String errorMsg;

    /** 请求耗时（ms） */
    private Long costTime;

    /** 接收时间 */
    private Date receiveTime;
}
