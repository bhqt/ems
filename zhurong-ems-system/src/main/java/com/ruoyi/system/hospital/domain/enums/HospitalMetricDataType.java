package com.ruoyi.system.hospital.domain.enums;

/**
 * 医院设备指标数据类型
 *
 * @author cpems
 */
public enum HospitalMetricDataType {

    /** 数值型（能耗、功率、电流等） */
    NUMBER("number"),

    /** 状态型（开机/关机/待机/故障等状态码） */
    STATUS("status"),

    /** 字符串型（文本描述） */
    STRING("string");

    private final String code;

    HospitalMetricDataType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
