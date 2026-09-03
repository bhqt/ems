package com.ruoyi.system.hospital.service;

import com.ruoyi.system.hospital.domain.StandardDataPoint;

import java.util.List;

/**
 * 医院设备报警触发引擎 Service
 * <p>
 * THRESHOLD 规则：数据落库后同步评估；OFFLINE 规则：定时扫描触发。
 *
 * @author cpems
 */
public interface IHospitalAlarmEvalService {

    /**
     * 评估刚落库的数据点并触发阈值报警（失败仅日志，不抛异常）
     *
     * @param points 本次落库的数据点
     */
    void evalPoints(List<StandardDataPoint> points);

    /**
     * 扫描离线设备并触发/恢复离线报警（定时任务调用）
     */
    void scanOffline();
}
