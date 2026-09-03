package com.ruoyi.system.hospital.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.system.hospital.config.HospitalIotProperties;
import com.ruoyi.system.hospital.constant.HospitalConstants;
import com.ruoyi.system.hospital.domain.HospitalAlarmRecord;
import com.ruoyi.system.hospital.domain.HospitalDevice;
import com.ruoyi.system.hospital.energy.HospitalDeviceRankVo;
import com.ruoyi.system.hospital.energy.HospitalEfficiencyVo;
import com.ruoyi.system.hospital.energy.HospitalEnergyCategoryVo;
import com.ruoyi.system.hospital.energy.HospitalEnergyOverviewVo;
import com.ruoyi.system.hospital.energy.HospitalEnergyTrendVo;
import com.ruoyi.system.hospital.energy.HospitalSuggestionVo;
import com.ruoyi.system.hospital.mapper.HospitalAlarmRecordMapper;
import com.ruoyi.system.hospital.mapper.HospitalDeviceMapper;
import com.ruoyi.system.hospital.mapper.HospitalDeviceWorkloadMapper;
import com.ruoyi.system.hospital.mapper.HospitalEnergyMapper;
import com.ruoyi.system.hospital.service.IHospitalDataScopeService;
import com.ruoyi.system.hospital.service.IHospitalEnergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 医院能耗分析与决策支持 Service 实现
 * <p>
 * 数据口径：用电量取累计电量（electricity）周期内 max-min；功率取实时功率（power）均值；
 * 运行/待机取运行状态（run_status）点数占比；环比取上一等长周期。
 *
 * @author cpems
 */
@RequiredArgsConstructor
@Service
public class HospitalEnergyServiceImpl implements IHospitalEnergyService {

    private final HospitalEnergyMapper energyMapper;
    private final HospitalDeviceMapper deviceMapper;
    private final HospitalDeviceWorkloadMapper workloadMapper;
    private final HospitalAlarmRecordMapper alarmRecordMapper;
    private final HospitalIotProperties iotProperties;
    private final IHospitalDataScopeService dataScopeService;

    /**
     * 待机浪费建议阈值：待机占比超过 30%
     */
    private static final BigDecimal STANDBY_SUGGEST_LINE = new BigDecimal("30");

    @Override
    public List<HospitalEnergyOverviewVo> overview(String level, String startTime, String endTime, String deviceType) {
        Date[] range = parseRange(startTime, endTime);
        Date start = range[0];
        Date end = range[1];
        long len = end.getTime() - start.getTime();
        Date prevStart = new Date(start.getTime() - len);
        Date prevEnd = start;

        List<Long> ids = deviceIdsByType(deviceType);
        List<Map<String, Object>> cur = energyMapper.selectDeviceStats(ids, start, end);
        List<Map<String, Object>> prev = energyMapper.selectDeviceStats(ids, prevStart, prevEnd);
        Map<String, BigDecimal> prevKwh = new HashMap<>(64);
        for (Map<String, Object> row : prev) {
            prevKwh.put(dimKey(level, row), toDecimal(row.get("kwh")));
        }

        Map<String, HospitalEnergyOverviewVo> grouped = new HashMap<>(32);
        for (Map<String, Object> row : cur) {
            String key = dimKey(level, row);
            HospitalEnergyOverviewVo vo = grouped.get(key);
            if (vo == null) {
                vo = new HospitalEnergyOverviewVo();
                vo.setDimKey(key);
                vo.setDimName(dimName(level, row));
                vo.setKwh(BigDecimal.ZERO);
                vo.setAvgPower(BigDecimal.ZERO);
                vo.setDeviceCount(0);
                grouped.put(key, vo);
            }
            BigDecimal kwh = toDecimal(row.get("kwh"));
            BigDecimal avg = toDecimal(row.get("avgPower"));
            vo.setKwh(vo.getKwh().add(kwh));
            // 平均功率取设备均值再平均（设备量纲一致，可比）
            int n = vo.getDeviceCount();
            vo.setAvgPower(vo.getAvgPower().multiply(BigDecimal.valueOf(n)).add(avg)
                .divide(BigDecimal.valueOf(n + 1), 4, RoundingMode.HALF_UP));
            vo.setDeviceCount(n + 1);
        }
        List<HospitalEnergyOverviewVo> result = new ArrayList<>(grouped.values());
        for (HospitalEnergyOverviewVo vo : result) {
            BigDecimal p = prevKwh.get(vo.getDimKey());
            if (p != null && p.compareTo(BigDecimal.ZERO) > 0 && vo.getKwh() != null) {
                vo.setChainRatio(vo.getKwh().subtract(p).multiply(BigDecimal.valueOf(100))
                    .divide(p, 2, RoundingMode.HALF_UP));
            }
        }
        result.sort(Comparator.comparing(HospitalEnergyOverviewVo::getKwh,
            Comparator.nullsLast(BigDecimal::compareTo)).reversed());
        return result;
    }

    @Override
    public List<HospitalEnergyTrendVo> trend(Long deviceId, String granularity, String startTime, String endTime) {
        Date[] range = parseRange(startTime, endTime);
        List<Long> ids = deviceId == null ? deviceIdsByType(null) : Collections.singletonList(deviceId);
        List<HospitalEnergyTrendVo> result = new ArrayList<>();
        if ("HOUR".equalsIgnoreCase(granularity)) {
            List<Map<String, Object>> rows = energyMapper.selectHourlyPower(ids, range[0], range[1]);
            for (Map<String, Object> row : rows) {
                HospitalEnergyTrendVo vo = new HospitalEnergyTrendVo();
                vo.setLabel(String.format("%02d:00", toInt(row.get("hour"))));
                vo.setAvgPower(toDecimal(row.get("avgPower")));
                result.add(vo);
            }
        } else {
            List<Map<String, Object>> rows = energyMapper.selectDailyTrend(ids, range[0], range[1]);
            for (Map<String, Object> row : rows) {
                HospitalEnergyTrendVo vo = new HospitalEnergyTrendVo();
                Object day = row.get("day");
                vo.setLabel(day == null ? "" : day.toString());
                vo.setKwh(toDecimal(row.get("kwh")));
                vo.setAvgPower(toDecimal(row.get("avgPower")));
                result.add(vo);
            }
        }
        return result;
    }

    @Override
    public List<HospitalDeviceRankVo> rank(String startTime, String endTime, Integer limit) {
        Date[] range = parseRange(startTime, endTime);
        List<Map<String, Object>> rows = energyMapper.selectDeviceStats(deviceIdsByType(null), range[0], range[1]);
        List<HospitalDeviceRankVo> result = new ArrayList<>(rows.size());
        for (Map<String, Object> row : rows) {
            BigDecimal kwh = toDecimal(row.get("kwh"));
            if (kwh == null || kwh.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            HospitalDeviceRankVo vo = new HospitalDeviceRankVo();
            vo.setDeviceId(toLong(row.get("deviceId")));
            vo.setDeviceName(str(row.get("deviceName")));
            vo.setDeviceCode(str(row.get("deviceCode")));
            vo.setDeviceType(str(row.get("deviceType")));
            vo.setKwh(kwh);
            vo.setAvgPower(toDecimal(row.get("avgPower")));
            result.add(vo);
        }
        result.sort(Comparator.comparing(HospitalDeviceRankVo::getKwh).reversed());
        int n = (limit == null || limit <= 0) ? 10 : limit;
        return result.size() > n ? result.subList(0, n) : result;
    }

    @Override
    public List<HospitalEfficiencyVo> efficiency(String startTime, String endTime) {
        Date[] range = parseRange(startTime, endTime);
        List<Map<String, Object>> rows = energyMapper.selectDeviceStats(deviceIdsByType(null), range[0], range[1]);
        // 同类设备平均功率（横向对标）
        Map<String, BigDecimal> typeAvg = new HashMap<>(16);
        Map<String, Integer> typeCnt = new HashMap<>(16);
        for (Map<String, Object> row : rows) {
            BigDecimal avg = toDecimal(row.get("avgPower"));
            if (avg == null) {
                continue;
            }
            String type = str(row.get("deviceType"));
            typeAvg.put(type, typeAvg.getOrDefault(type, BigDecimal.ZERO).add(avg));
            typeCnt.put(type, typeCnt.getOrDefault(type, 0) + 1);
        }
        for (Map.Entry<String, BigDecimal> e : typeAvg.entrySet()) {
            e.setValue(e.getValue().divide(BigDecimal.valueOf(typeCnt.get(e.getKey())), 4, RoundingMode.HALF_UP));
        }

        List<HospitalEfficiencyVo> result = new ArrayList<>(rows.size());
        // 周期工作量（单位工作量能效）
        Map<Long, BigDecimal> workloadMap = new HashMap<>(64);
        List<Map<String, Object>> wl = workloadMapper.sumWorkloadByDevice(deviceIdsByType(null), range[0], range[1]);
        if (CollUtil.isNotEmpty(wl)) {
            for (Map<String, Object> row : wl) {
                workloadMap.put(toLong(row.get("deviceId")), toDecimal(row.get("workload")));
            }
        }
        for (Map<String, Object> row : rows) {
            HospitalEfficiencyVo vo = new HospitalEfficiencyVo();
            vo.setDeviceId(toLong(row.get("deviceId")));
            vo.setDeviceName(str(row.get("deviceName")));
            vo.setDeviceCode(str(row.get("deviceCode")));
            vo.setDeviceType(str(row.get("deviceType")));
            vo.setKwh(toDecimal(row.get("kwh")));
            vo.setAvgPower(toDecimal(row.get("avgPower")));
            Long devId = vo.getDeviceId();
            if (devId != null && workloadMap.containsKey(devId)) {
                BigDecimal workload = workloadMap.get(devId);
                vo.setWorkload(workload);
                BigDecimal kwh = vo.getKwh();
                if (workload != null && workload.compareTo(BigDecimal.ZERO) > 0 && kwh != null) {
                    vo.setUnitEnergy(kwh.divide(workload, 2, RoundingMode.HALF_UP));
                }
            }
            long run = toLong(row.get("runPoints"), 0L);
            long standby = toLong(row.get("standbyPoints"), 0L);
            if (run + standby > 0) {
                vo.setStandbyRatio(BigDecimal.valueOf(standby * 100.0 / (run + standby))
                    .setScale(2, RoundingMode.HALF_UP));
            }
            if (toLong(row.get("totalPoints"), 0L) <= 0) {
                vo.setLevel("未评估");
                result.add(vo);
                continue;
            }
            // 评分：100 起扣，待机占比>40% 扣30、>20% 扣15；平均功率超同类均值50% 扣20
            int score = 100;
            if (vo.getStandbyRatio() != null) {
                if (vo.getStandbyRatio().compareTo(new BigDecimal("40")) > 0) {
                    score -= 30;
                } else if (vo.getStandbyRatio().compareTo(new BigDecimal("20")) > 0) {
                    score -= 15;
                }
            }
            BigDecimal peer = typeAvg.get(vo.getDeviceType());
            if (vo.getAvgPower() != null && peer != null && peer.compareTo(BigDecimal.ZERO) > 0
                && vo.getAvgPower().compareTo(peer.multiply(new BigDecimal("1.5"))) > 0) {
                score -= 20;
            }
            vo.setScore(BigDecimal.valueOf(Math.max(score, 0)));
            if (score >= 80) {
                vo.setLevel("优");
            } else if (score >= 60) {
                vo.setLevel("良");
            } else {
                vo.setLevel("待改进");
            }
            result.add(vo);
        }
        result.sort(Comparator.comparing(HospitalEfficiencyVo::getScore, Comparator.nullsLast(BigDecimal::compareTo)));
        return result;
    }

    @Override
    public List<HospitalSuggestionVo> suggestions(String startTime, String endTime) {
        Date[] range = parseRange(startTime, endTime);
        List<HospitalSuggestionVo> result = new ArrayList<>();

        // 1. 待机浪费：待机占比超过阈值
        for (HospitalEfficiencyVo e : efficiency(startTime, endTime)) {
            if (e.getStandbyRatio() != null && e.getStandbyRatio().compareTo(STANDBY_SUGGEST_LINE) >= 0) {
                HospitalSuggestionVo s = new HospitalSuggestionVo();
                s.setType("STANDBY");
                s.setTypeName("待机浪费");
                s.setDeviceId(e.getDeviceId());
                s.setDeviceName(e.getDeviceName());
                s.setContent("设备[" + e.getDeviceName() + "]周期内待机占比达 " + e.getStandbyRatio()
                    + "%，存在待机耗电浪费");
                s.setAction("建议非工作时段关机断电，或配置待机自动断电管理");
                result.add(s);
            }
        }

        // 2. 高耗能时段：小时平均功率 Top3 中超过日均 1.3 倍的时段
        List<Map<String, Object>> hourly = energyMapper.selectHourlyPower(deviceIdsByType(null), range[0], range[1]);
        if (CollUtil.isNotEmpty(hourly)) {
            BigDecimal total = BigDecimal.ZERO;
            int cnt = 0;
            List<HourPower> list = new ArrayList<>(hourly.size());
            for (Map<String, Object> row : hourly) {
                BigDecimal avg = toDecimal(row.get("avgPower"));
                if (avg == null) {
                    continue;
                }
                list.add(new HourPower(toInt(row.get("hour")), avg));
                total = total.add(avg);
                cnt++;
            }
            if (cnt > 0) {
                BigDecimal dayAvg = total.divide(BigDecimal.valueOf(cnt), 4, RoundingMode.HALF_UP);
                list.sort(Comparator.comparing(HourPower::getAvg).reversed());
                int top = Math.min(3, list.size());
                for (int i = 0; i < top; i++) {
                    HourPower h = list.get(i);
                    if (h.getAvg().compareTo(dayAvg.multiply(new BigDecimal("1.3"))) > 0) {
                        HospitalSuggestionVo s = new HospitalSuggestionVo();
                        s.setType("PEAK");
                        s.setTypeName("高耗能时段");
                        s.setContent(String.format("%02d:00 时段平均功率 %s kW，为全天均值 %s kW 的 %s 倍",
                            h.getHour(), h.getAvg(), dayAvg,
                            h.getAvg().divide(dayAvg, 2, RoundingMode.HALF_UP)));
                        s.setAction("建议核查该时段设备排班，评估错峰运行可行性");
                        result.add(s);
                    }
                }
            }
        }

        // 3. 异常设备：存在未处理报警或当前离线
        List<HospitalAlarmRecord> openList = alarmRecordMapper.selectList(
            new LambdaQueryWrapper<HospitalAlarmRecord>()
                .eq(HospitalAlarmRecord::getStatus, HospitalConstants.ALARM_STATUS_OPEN));
        Map<Long, String> alarmDevices = new HashMap<>(32);
        if (CollUtil.isNotEmpty(openList)) {
            for (HospitalAlarmRecord r : openList) {
                alarmDevices.put(r.getDeviceId(), r.getContent());
            }
        }
        if (!alarmDevices.isEmpty()) {
            List<HospitalDevice> devices = deviceMapper.selectBatchIds(alarmDevices.keySet());
            for (HospitalDevice d : devices) {
                HospitalSuggestionVo s = new HospitalSuggestionVo();
                s.setType("ABNORMAL");
                s.setTypeName("异常设备");
                s.setDeviceId(d.getId());
                s.setDeviceName(d.getDeviceName());
                s.setContent("设备[" + d.getDeviceName() + "]存在未处理报警：" + alarmDevices.get(d.getId()));
                s.setAction("建议优先排查处理，避免异常耗能与诊疗中断");
                result.add(s);
            }
        }
        return result;
    }

    // ---------- helpers ----------

    @Override
    public List<HospitalEnergyCategoryVo> categorySummary(String startTime, String endTime) {
        Date[] range = parseRange(startTime, endTime);
        List<Map<String, Object>> rows = energyMapper.selectCategoryStats(range[0], range[1]);
        List<HospitalEnergyCategoryVo> result = new ArrayList<>(rows.size());
        for (Map<String, Object> row : rows) {
            HospitalEnergyCategoryVo vo = new HospitalEnergyCategoryVo();
            vo.setCategory(str(row.get("category")));
            vo.setCategoryName(categoryName(str(row.get("category"))));
            vo.setKwh(toDecimal(row.get("kwh")));
            vo.setAvgPower(toDecimal(row.get("avgPower")));
            vo.setDeviceCount(toInt(row.get("deviceCount")));
            result.add(vo);
        }
        return result;
    }

    @Override
    public Map<String, Map<String, BigDecimal>> categoryTrend(String startTime, String endTime) {
        Date[] range = parseRange(startTime, endTime);
        List<Map<String, Object>> rows = energyMapper.selectCategoryDailyTrend(range[0], range[1]);
        Map<String, Map<String, BigDecimal>> result = new LinkedHashMap<>();
        for (Map<String, Object> row : rows) {
            String day = str(row.get("day"));
            String category = str(row.get("category"));
            result.computeIfAbsent(day, k -> new HashMap<>())
                .put(category, toDecimal(row.get("kwh")));
        }
        return result;
    }

    private String categoryName(String category) {
        if (StrUtil.isBlank(category)) {
            return "其他";
        }
        switch (category) {
            case "LIGHTING":
                return "照明";
            case "AIRCOND":
                return "空调";
            case "MEDICAL":
                return "医疗设备";
            case "POWER":
                return "动力";
            default:
                return "其他";
        }
    }

    private Date[] parseRange(String startTime, String endTime) {
        Date end = StrUtil.isBlank(endTime) ? new Date() : DateUtil.parse(endTime);
        Date start = StrUtil.isBlank(startTime) ? DateUtil.offsetDay(end, -7) : DateUtil.parse(startTime);
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        return new Date[]{start, end};
    }

    private List<Long> deviceIdsByType(String deviceType) {
        // 多院区数据权限：解析当前用户可访问院区，过滤设备
        Set<String> areas = dataScopeService.resolveAccessibleAreas();
        if (StrUtil.isBlank(deviceType) && CollUtil.isEmpty(areas)) {
            return null;
        }
        LambdaQueryWrapper<HospitalDevice> lqw = new LambdaQueryWrapper<HospitalDevice>()
            .eq(StrUtil.isNotBlank(deviceType), HospitalDevice::getDeviceType, deviceType)
            .in(CollUtil.isNotEmpty(areas), HospitalDevice::getAreaId, areas);
        List<HospitalDevice> list = deviceMapper.selectList(lqw);
        List<Long> ids = new ArrayList<>(list.size());
        for (HospitalDevice d : list) {
            ids.add(d.getId());
        }
        return ids;
    }

    private String dimKey(String level, Map<String, Object> row) {
        if ("DEPT".equalsIgnoreCase(level)) {
            return "DEPT:" + str(row.get("deptId"));
        } else if ("DEVICE".equalsIgnoreCase(level)) {
            return "DEVICE:" + str(row.get("deviceId"));
        }
        return "AREA:" + str(row.get("areaId"));
    }

    private String dimName(String level, Map<String, Object> row) {
        if ("DEPT".equalsIgnoreCase(level)) {
            String dept = str(row.get("deptId"));
            return StrUtil.isBlank(dept) ? "未分区科室" : dept;
        } else if ("DEVICE".equalsIgnoreCase(level)) {
            return str(row.get("deviceName"));
        }
        String area = str(row.get("areaId"));
        return StrUtil.isBlank(area) ? "未分区院区" : area;
    }

    private BigDecimal toDecimal(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof BigDecimal) {
            return (BigDecimal) o;
        }
        if (o instanceof Number) {
            return BigDecimal.valueOf(((Number) o).doubleValue());
        }
        try {
            return new BigDecimal(String.valueOf(o));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Long toLong(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).longValue();
        }
        return Long.valueOf(String.valueOf(o));
    }

    private long toLong(Object o, long def) {
        Long v = null;
        try {
            v = toLong(o);
        } catch (NumberFormatException ignore) {
            // 忽略
        }
        return v == null ? def : v;
    }

    private int toInt(Object o) {
        if (o instanceof Number) {
            return ((Number) o).intValue();
        }
        return Integer.parseInt(String.valueOf(o));
    }

    private String str(Object o) {
        return o == null ? "" : String.valueOf(o);
    }

    /**
     * 小时功率载体
     */
    private static class HourPower {
        private final int hour;
        private final BigDecimal avg;

        HourPower(int hour, BigDecimal avg) {
            this.hour = hour;
            this.avg = avg;
        }

        int getHour() {
            return hour;
        }

        BigDecimal getAvg() {
            return avg;
        }
    }
}
