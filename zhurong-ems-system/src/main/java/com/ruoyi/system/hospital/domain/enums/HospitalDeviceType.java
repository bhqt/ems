package com.ruoyi.system.hospital.domain.enums;

import com.ruoyi.common.utils.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * 医院检查检验设备类型
 *
 * @author cpems
 */
public enum HospitalDeviceType {

    /** CT 计算机断层扫描 */
    CT("CT", "CT"),
    /** MRI 磁共振成像 */
    MRI("MRI", "MRI"),
    /** DR 数字化 X 射线摄影 */
    DR("DR", "DR"),
    /** US 超声诊断设备 */
    US("US", "超声"),
    /** LAB 检验流水线/检验设备 */
    LAB("LAB", "检验"),
    /** DSA 数字减影血管造影 */
    DSA("DSA", "DSA"),
    /** OTHER 其他 */
    OTHER("OTHER", "其他");

    private final String code;
    private final String info;

    HospitalDeviceType(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static Optional<HospitalDeviceType> of(String code) {
        if (StringUtils.isBlank(code)) {
            return Optional.empty();
        }
        return Arrays.stream(values())
            .filter(t -> t.code.equalsIgnoreCase(code))
            .findFirst();
    }
}
