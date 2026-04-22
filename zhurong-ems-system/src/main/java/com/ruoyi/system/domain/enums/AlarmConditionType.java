package com.ruoyi.system.domain.enums;

import lombok.Getter;

/**
 * 报警条件查询
 * @Author cpems
 * @Date 2023/7/6 16:15
 */
@Getter
public enum AlarmConditionType {
    /**
     * 大于
     */
    G(0, ">"),

    /**
     * 等于
     */
    E(1, "="),
    /**
     * 小于
     */
    L(2, "<"),

    /**
     * 大于等于
     */
    GE(3, ">="),

    /**
     * 小于等于
     */
    LE(4, "<=");

    private final Integer code;
    private final String info;

    AlarmConditionType(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    /**
     * 根据 info 描述获取枚举值
     */
    public static AlarmConditionType fromInfo(String info) {
        for (AlarmConditionType type : values()) {
            if (type.info.equals(info)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的条件类型: " + info);
    }

    /**
     * 根据 code 编码获取枚举值
     */
    public static AlarmConditionType fromCode(String code) {
        for (AlarmConditionType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的条件编码: " + code);
    }
}
