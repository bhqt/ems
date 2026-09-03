package com.ruoyi.system.hospital.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.email.MailUtils;
import com.ruoyi.system.hospital.constant.HospitalConstants;
import com.ruoyi.system.hospital.domain.HospitalAlarmRecord;
import com.ruoyi.system.hospital.domain.HospitalAlarmRule;
import com.ruoyi.system.hospital.domain.HospitalDevice;
import com.ruoyi.system.hospital.domain.StandardDataPoint;
import com.ruoyi.system.hospital.mapper.HospitalAlarmRecordMapper;
import com.ruoyi.system.hospital.mapper.HospitalAlarmRuleMapper;
import com.ruoyi.system.hospital.mapper.HospitalDeviceDataMapper;
import com.ruoyi.system.hospital.mapper.HospitalDeviceMapper;
import com.ruoyi.system.hospital.service.IHospitalAlarmEvalService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医院设备报警触发引擎 Service 实现
 * <p>
 * THRESHOLD 规则：数据落库后同步评估，命中且无同规则未处理记录时建单；
 * OFFLINE 规则：每 5 分钟扫描一次，无数据超时建单、数据恢复自动结束。
 *
 * @author cpems
 */
@RequiredArgsConstructor
@Service
public class HospitalAlarmEvalServiceImpl implements IHospitalAlarmEvalService {

    private static final Logger log = LoggerFactory.getLogger(HospitalAlarmEvalServiceImpl.class);

    private final HospitalAlarmRuleMapper ruleMapper;
    private final HospitalAlarmRecordMapper recordMapper;
    private final HospitalDeviceMapper deviceMapper;
    private final HospitalDeviceDataMapper deviceDataMapper;

    @Override
    public void evalPoints(List<StandardDataPoint> points) {
        try {
            if (CollUtil.isEmpty(points)) {
                return;
            }
            List<HospitalAlarmRule> rules = ruleMapper.selectList(
                new LambdaQueryWrapper<HospitalAlarmRule>()
                    .eq(HospitalAlarmRule::getRuleType, HospitalConstants.ALARM_RULE_THRESHOLD)
                    .eq(HospitalAlarmRule::getStatus, "0"));
            if (CollUtil.isEmpty(rules)) {
                return;
            }
            for (StandardDataPoint p : points) {
                if (p == null || p.getValue() == null || p.getDeviceId() == null) {
                    continue;
                }
                for (HospitalAlarmRule rule : rules) {
                    if (!matchDevice(rule, p.getDeviceId(), null)) {
                        continue;
                    }
                    if (!StrUtil.equals(rule.getMetricCode(), p.getMetricCode())) {
                        continue;
                    }
                    if (hitCondition(rule.getCondition(), p.getValue(), rule.getThresholdValue())) {
                        fireThreshold(rule, p);
                    }
                }
            }
        } catch (Exception e) {
            // 报警评估失败不影响主链路
            log.error("[医院报警] 阈值评估异常", e);
        }
    }

    /**
     * 离线扫描：每 5 分钟执行一次
     */
    @Scheduled(fixedDelay = 300000)
    @Override
    public void scanOffline() {
        try {
            List<HospitalAlarmRule> rules = ruleMapper.selectList(
                new LambdaQueryWrapper<HospitalAlarmRule>()
                    .eq(HospitalAlarmRule::getRuleType, HospitalConstants.ALARM_RULE_OFFLINE)
                    .eq(HospitalAlarmRule::getStatus, "0"));
            if (CollUtil.isEmpty(rules)) {
                return;
            }
            List<Map<String, Object>> maxTsList = deviceDataMapper.selectMaxTsByDeviceIds(null);
            Map<Long, Date> lastTs = new HashMap<>(64);
            if (maxTsList != null) {
                for (Map<String, Object> row : maxTsList) {
                    Object id = row.get("deviceId");
                    Object ts = row.get("maxTs");
                    if (id != null && ts instanceof Date) {
                        lastTs.put(Long.valueOf(String.valueOf(id)), (Date) ts);
                    }
                }
            }
            long now = System.currentTimeMillis();
            for (HospitalAlarmRule rule : rules) {
                List<HospitalDevice> targets = targetDevices(rule);
                int timeoutMin = rule.getOfflineTimeoutMin() == null ? 30 : rule.getOfflineTimeoutMin();
                for (HospitalDevice device : targets) {
                    Date ts = lastTs.get(device.getId());
                    boolean offline = ts == null || (now - ts.getTime()) > timeoutMin * 60_000L;
                    if (offline) {
                        fireOffline(rule, device, ts);
                    } else {
                        closeOffline(rule.getId(), device.getId());
                    }
                }
            }
        } catch (Exception e) {
            log.error("[医院报警] 离线扫描异常", e);
        }
    }

    private List<HospitalDevice> targetDevices(HospitalAlarmRule rule) {
        LambdaQueryWrapper<HospitalDevice> lqw = new LambdaQueryWrapper<HospitalDevice>()
            .eq(rule.getDeviceId() != null, HospitalDevice::getId, rule.getDeviceId())
            .eq(StrUtil.isNotBlank(rule.getDeviceType()), HospitalDevice::getDeviceType, rule.getDeviceType())
            .eq(HospitalDevice::getStatus, HospitalConstants.DEVICE_STATUS_NORMAL);
        return deviceMapper.selectList(lqw);
    }

    private boolean matchDevice(HospitalAlarmRule rule, Long deviceId, HospitalDevice device) {
        if (rule.getDeviceId() != null) {
            return rule.getDeviceId().equals(deviceId);
        }
        if (StrUtil.isBlank(rule.getDeviceType())) {
            return true;
        }
        HospitalDevice d = device;
        if (d == null) {
            d = deviceMapper.selectById(deviceId);
        }
        return d != null && StrUtil.equals(rule.getDeviceType(), d.getDeviceType());
    }

    private boolean hitCondition(String condition, BigDecimal value, BigDecimal threshold) {
        if (StrUtil.isBlank(condition) || value == null || threshold == null) {
            return false;
        }
        int cmp = value.compareTo(threshold);
        if ("G".equalsIgnoreCase(condition)) {
            return cmp > 0;
        } else if ("GE".equalsIgnoreCase(condition)) {
            return cmp >= 0;
        } else if ("L".equalsIgnoreCase(condition)) {
            return cmp < 0;
        } else if ("LE".equalsIgnoreCase(condition)) {
            return cmp <= 0;
        } else if ("E".equalsIgnoreCase(condition)) {
            return cmp == 0;
        }
        return false;
    }

    private void fireThreshold(HospitalAlarmRule rule, StandardDataPoint p) {
        long open = recordMapper.selectCount(new LambdaQueryWrapper<HospitalAlarmRecord>()
            .eq(HospitalAlarmRecord::getRuleId, rule.getId())
            .eq(HospitalAlarmRecord::getDeviceId, p.getDeviceId())
            .eq(HospitalAlarmRecord::getStatus, HospitalConstants.ALARM_STATUS_OPEN));
        if (open > 0) {
            return;
        }
        HospitalAlarmRecord record = new HospitalAlarmRecord();
        record.setRuleId(rule.getId());
        record.setDeviceId(p.getDeviceId());
        record.setMetricCode(p.getMetricCode());
        record.setAlarmType(HospitalConstants.ALARM_TYPE_OVERLOAD);
        record.setLevel(rule.getLevel());
        record.setAlarmVal(p.getValue());
        record.setContent("设备指标 " + p.getMetricCode() + " 当前值 " + p.getValue()
            + " 触发规则[" + rule.getRuleName() + "]（" + rule.getCondition() + " " + rule.getThresholdValue() + "）");
        record.setStatus(HospitalConstants.ALARM_STATUS_OPEN);
        record.setStartTime(new Date());
        record.setCreateTime(new Date());
        recordMapper.insert(record);
        log.warn("[医院报警] 阈值报警 device={} metric={} value={} rule={}",
            p.getDeviceId(), p.getMetricCode(), p.getValue(), rule.getRuleName());
        notifyMail(rule, record.getContent());
    }

    private void fireOffline(HospitalAlarmRule rule, HospitalDevice device, Date lastTs) {
        long open = recordMapper.selectCount(new LambdaQueryWrapper<HospitalAlarmRecord>()
            .eq(HospitalAlarmRecord::getRuleId, rule.getId())
            .eq(HospitalAlarmRecord::getDeviceId, device.getId())
            .eq(HospitalAlarmRecord::getStatus, HospitalConstants.ALARM_STATUS_OPEN));
        if (open > 0) {
            return;
        }
        HospitalAlarmRecord record = new HospitalAlarmRecord();
        record.setRuleId(rule.getId());
        record.setDeviceId(device.getId());
        record.setAlarmType(HospitalConstants.ALARM_TYPE_OFFLINE);
        record.setLevel(rule.getLevel());
        record.setContent("设备[" + device.getDeviceName() + "]超过 " + rule.getOfflineTimeoutMin()
            + " 分钟无数据（最近：" + (lastTs == null ? "无" : lastTs) + "），判定离线");
        record.setStatus(HospitalConstants.ALARM_STATUS_OPEN);
        record.setStartTime(new Date());
        record.setCreateTime(new Date());
        recordMapper.insert(record);
        log.warn("[医院报警] 离线报警 device={} rule={}", device.getDeviceName(), rule.getRuleName());
        notifyMail(rule, record.getContent());
    }

    /**
     * 数据恢复：自动结束该规则下该设备的离线报警
     */
    private void closeOffline(Long ruleId, Long deviceId) {
        List<HospitalAlarmRecord> openList = recordMapper.selectList(
            new LambdaQueryWrapper<HospitalAlarmRecord>()
                .eq(HospitalAlarmRecord::getRuleId, ruleId)
                .eq(HospitalAlarmRecord::getDeviceId, deviceId)
                .eq(HospitalAlarmRecord::getAlarmType, HospitalConstants.ALARM_TYPE_OFFLINE)
                .eq(HospitalAlarmRecord::getStatus, HospitalConstants.ALARM_STATUS_OPEN));
        if (CollUtil.isEmpty(openList)) {
            return;
        }
        for (HospitalAlarmRecord r : openList) {
            HospitalAlarmRecord update = new HospitalAlarmRecord();
            update.setId(r.getId());
            update.setStatus(HospitalConstants.ALARM_STATUS_CLOSED);
            update.setEndTime(new Date());
            update.setHandleBy("system");
            update.setHandleRemark("数据恢复，自动结束");
            recordMapper.updateById(update);
        }
    }

    private void notifyMail(HospitalAlarmRule rule, String content) {
        if (StringUtils.isBlank(rule.getNotifyEmail())) {
            return;
        }
        try {
            for (String to : rule.getNotifyEmail().split("[,;]")) {
                if (StrUtil.isNotBlank(to)) {
                    MailUtils.sendText(to.trim(), "医院设备报警提醒", content);
                }
            }
        } catch (Exception e) {
            log.error("[医院报警] 邮件通知失败 rule={}", rule.getRuleName(), e);
        }
    }
}
