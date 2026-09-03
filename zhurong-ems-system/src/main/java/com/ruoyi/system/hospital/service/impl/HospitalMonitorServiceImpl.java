package com.ruoyi.system.hospital.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.system.hospital.config.HospitalIotProperties;
import com.ruoyi.system.hospital.constant.HospitalConstants;
import com.ruoyi.system.hospital.domain.HospitalAlarmRecord;
import com.ruoyi.system.hospital.domain.HospitalDevice;
import com.ruoyi.system.hospital.domain.HospitalDeviceData;
import com.ruoyi.system.hospital.mapper.HospitalAlarmRecordMapper;
import com.ruoyi.system.hospital.mapper.HospitalDeviceDataMapper;
import com.ruoyi.system.hospital.mapper.HospitalDeviceMapper;
import com.ruoyi.system.hospital.service.IHospitalDataScopeService;
import com.ruoyi.system.hospital.service.IHospitalMonitorService;
import com.ruoyi.system.hospital.vo.HospitalDeviceDataVo;
import com.ruoyi.system.hospital.vo.HospitalDeviceRealtimeVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 医院设备实时监测 Service 实现
 *
 * @author cpems
 */
@RequiredArgsConstructor
@Service
public class HospitalMonitorServiceImpl implements IHospitalMonitorService {

    private final HospitalDeviceMapper deviceMapper;
    private final HospitalDeviceDataMapper deviceDataMapper;
    private final HospitalAlarmRecordMapper alarmRecordMapper;
    private final HospitalIotProperties iotProperties;
    private final IHospitalDataScopeService dataScopeService;

    @Override
    public List<HospitalDeviceRealtimeVo> queryOverview(String deviceType, String keyword) {
        Set<String> areas = dataScopeService.resolveAccessibleAreas();
        LambdaQueryWrapper<HospitalDevice> lqw = new LambdaQueryWrapper<HospitalDevice>()
            .eq(deviceType != null && !deviceType.isEmpty(), HospitalDevice::getDeviceType, deviceType)
            .in(CollUtil.isNotEmpty(areas), HospitalDevice::getAreaId, areas)
            .and(keyword != null && !keyword.isEmpty(),
                w -> w.like(HospitalDevice::getDeviceName, keyword).or().like(HospitalDevice::getDeviceCode, keyword))
            .orderByDesc(HospitalDevice::getCreateTime);
        List<HospitalDevice> devices = deviceMapper.selectList(lqw);

        List<HospitalDeviceRealtimeVo> result = new ArrayList<>(devices.size());
        if (devices.isEmpty()) {
            return result;
        }
        List<Long> ids = new ArrayList<>(devices.size());
        for (HospitalDevice d : devices) {
            ids.add(d.getId());
        }
        // 各设备各指标最新点
        Map<String, HospitalDeviceData> latest = new HashMap<>(64);
        List<HospitalDeviceData> latestList = deviceDataMapper.selectLatestByDeviceIds(ids);
        if (latestList != null) {
            for (HospitalDeviceData data : latestList) {
                latest.put(data.getDeviceId() + "|" + data.getMetricCode(), data);
            }
        }
        // 各设备未处理报警数
        Map<Long, Integer> openAlarms = new HashMap<>(32);
        List<HospitalAlarmRecord> openList = alarmRecordMapper.selectList(
            new LambdaQueryWrapper<HospitalAlarmRecord>()
                .eq(HospitalAlarmRecord::getStatus, HospitalConstants.ALARM_STATUS_OPEN)
                .in(HospitalAlarmRecord::getDeviceId, ids));
        if (openList != null) {
            for (HospitalAlarmRecord r : openList) {
                openAlarms.put(r.getDeviceId(), openAlarms.getOrDefault(r.getDeviceId(), 0) + 1);
            }
        }

        long offlineMillis = (iotProperties.getMonitorOfflineMinutes() == null
            ? 30 : iotProperties.getMonitorOfflineMinutes()) * 60_000L;
        long now = System.currentTimeMillis();
        for (HospitalDevice d : devices) {
            HospitalDeviceRealtimeVo vo = new HospitalDeviceRealtimeVo();
            vo.setDeviceId(d.getId());
            vo.setDeviceName(d.getDeviceName());
            vo.setDeviceCode(d.getDeviceCode());
            vo.setDeviceType(d.getDeviceType());
            vo.setIotDeviceId(d.getIotDeviceId());
            vo.setStatus(d.getStatus());
            vo.setOpenAlarmCount(openAlarms.getOrDefault(d.getId(), 0));

            HospitalDeviceData power = latest.get(d.getId() + "|power");
            HospitalDeviceData elec = latest.get(d.getId() + "|electricity");
            HospitalDeviceData run = latest.get(d.getId() + "|run_status");
            if (power != null && power.getMetricValue() != null) {
                vo.setPower(power.getMetricValue());
            }
            if (elec != null && elec.getMetricValue() != null) {
                vo.setElectricity(elec.getMetricValue());
            }
            if (run != null) {
                vo.setRunStatus(run.getMetricValue() != null
                    ? run.getMetricValue().stripTrailingZeros().toPlainString() : run.getMetricStr());
            }
            Date lastTs = maxTs(power, elec, run, latest, d.getId());
            vo.setLastTs(lastTs);
            boolean hasRecentData = lastTs != null && (now - lastTs.getTime()) <= offlineMillis;
            vo.setOnline(HospitalConstants.DEVICE_STATUS_NORMAL.equals(d.getStatus()) && hasRecentData);
            result.add(vo);
        }
        return result;
    }

    private Date maxTs(HospitalDeviceData power, HospitalDeviceData elec, HospitalDeviceData run,
                       Map<String, HospitalDeviceData> latest, Long deviceId) {
        Date max = null;
        HospitalDeviceData[] known = new HospitalDeviceData[]{power, elec, run};
        for (HospitalDeviceData data : known) {
            if (data != null && data.getTs() != null && (max == null || data.getTs().after(max))) {
                max = data.getTs();
            }
        }
        if (max == null) {
            // 兜底：任一指标的最新时间
            for (Map.Entry<String, HospitalDeviceData> e : latest.entrySet()) {
                HospitalDeviceData data = e.getValue();
                if (data.getDeviceId() != null && data.getDeviceId().equals(deviceId)
                    && data.getTs() != null && (max == null || data.getTs().after(max))) {
                    max = data.getTs();
                }
            }
        }
        return max;
    }

    @Override
    public List<HospitalDeviceDataVo> queryTrend(Long deviceId, String metricCode, Integer limit) {
        int n = (limit == null || limit <= 0 || limit > 1000) ? 100 : limit;
        List<HospitalDeviceDataVo> list = deviceDataMapper.selectHospitalDeviceDataList(deviceId, metricCode, n);
        return CollUtil.isEmpty(list) ? new ArrayList<HospitalDeviceDataVo>(0) : list;
    }
}
