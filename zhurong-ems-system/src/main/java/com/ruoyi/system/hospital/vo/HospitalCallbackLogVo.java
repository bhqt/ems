package com.ruoyi.system.hospital.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 医院 IOT 回调日志视图对象
 *
 * @author cpems
 */
@Data
@ExcelIgnoreUnannotated
public class HospitalCallbackLogVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ExcelProperty(value = "主键")
    private Long id;

    /** 请求 ID（IOT 平台消息 ID） */
    @ExcelProperty(value = "请求ID")
    private String requestId;

    /** 来源 IP */
    @ExcelProperty(value = "来源IP")
    private String sourceIp;

    /** IOT 消息时间戳 */
    @ExcelProperty(value = "IOT时间戳")
    private String iotTimestamp;

    /** 设备数量 */
    @ExcelProperty(value = "设备数量")
    private Integer deviceCount;

    /** 数据点数量 */
    @ExcelProperty(value = "数据点数量")
    private Integer pointCount;

    /** 处理状态（success/auth_fail/parse_fail/fail） */
    @ExcelProperty(value = "处理状态")
    private String status;

    /** 错误信息 */
    private String errorMsg;

    /** 请求耗时（ms） */
    @ExcelProperty(value = "耗时ms")
    private Long costTime;

    /** 接收时间 */
    @ExcelProperty(value = "接收时间")
    private Date receiveTime;
}
