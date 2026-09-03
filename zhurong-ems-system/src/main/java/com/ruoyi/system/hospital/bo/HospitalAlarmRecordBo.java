package com.ruoyi.system.hospital.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 医院设备报警记录业务对象（查询 + 处理）
 *
 * @author cpems
 */
@Data
public class HospitalAlarmRecordBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long deviceId;

    /** 报警类型（OVERLOAD/OFFLINE） */
    private String alarmType;

    /** 处理状态（0待处理 1已结束） */
    private String status;

    /** 处理阶段（0待处理 1已确认 2处理中 3已处理） */
    private String handleStatus;

    /** 报警级别（0一般 1严重 2紧急） */
    private String level;

    /** 处理说明（处理/关闭时填写） */
    private String handleRemark;
}
