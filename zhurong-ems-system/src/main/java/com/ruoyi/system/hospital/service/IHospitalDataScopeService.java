package com.ruoyi.system.hospital.service;

import java.util.Set;

/**
 * 医院数据权限（多院区隔离）Service 接口
 * <p>
 * 依据当前登录用户的角色数据范围（全部/自定/本部门/本部门及以下/仅本人），
 * 解析其可访问的院区（area id = 部门 id）集合，用于医院设备/能耗/监测/报警等查询的按院区过滤。
 *
 * @author cpems
 */
public interface IHospitalDataScopeService {

    /**
     * 当前用户可访问的院区 id 集合。
     * 返回 null 表示不限（超级管理员 / 全部数据范围角色）。
     */
    Set<String> resolveAccessibleAreas();

    /**
     * 是否为不限制院区（超级管理员 / 全部数据范围）
     */
    boolean isUnrestricted();
}
