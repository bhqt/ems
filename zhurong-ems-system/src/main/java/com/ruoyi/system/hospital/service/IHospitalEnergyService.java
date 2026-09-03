package com.ruoyi.system.hospital.service;

import com.ruoyi.system.hospital.energy.HospitalDeviceRankVo;
import com.ruoyi.system.hospital.energy.HospitalEfficiencyVo;
import com.ruoyi.system.hospital.energy.HospitalEnergyOverviewVo;
import com.ruoyi.system.hospital.energy.HospitalEnergyTrendVo;
import com.ruoyi.system.hospital.energy.HospitalSuggestionVo;

import java.util.List;

/**
 * 医院能耗分析与决策支持 Service
 *
 * @author cpems
 */
public interface IHospitalEnergyService {

    /**
     * 全院能耗概览（按院区/科室/设备钻取，附环比）
     *
     * @param level      钻取层级（AREA/DEPT/DEVICE）
     * @param startTime  开始时间（yyyy-MM-dd HH:mm:ss，为空默认近 7 天）
     * @param endTime    结束时间（为空默认当前）
     * @param deviceType 设备类型过滤（可选）
     * @return 分组能耗概览
     */
    List<HospitalEnergyOverviewVo> overview(String level, String startTime, String endTime, String deviceType);

    /**
     * 能耗趋势（按天用电量 / 按小时平均功率）
     *
     * @param deviceId    设备 ID（为空查全部）
     * @param granularity 粒度（DAY/HOUR）
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 趋势点列表
     */
    List<HospitalEnergyTrendVo> trend(Long deviceId, String granularity, String startTime, String endTime);

    /**
     * 设备耗电排名
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param limit     取前 N（默认 10）
     * @return 排名列表
     */
    List<HospitalDeviceRankVo> rank(String startTime, String endTime, Integer limit);

    /**
     * 设备能效评估（待机占比、平均功率、评分等级）
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 评估列表
     */
    List<HospitalEfficiencyVo> efficiency(String startTime, String endTime);

    /**
     * 节能建议清单（待机浪费/高耗能时段/异常设备）
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 建议清单
     */
    List<HospitalSuggestionVo> suggestions(String startTime, String endTime);
}
