package com.ruoyi.system.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.hospital.bo.HospitalAlarmRecordBo;
import com.ruoyi.system.hospital.constant.HospitalConstants;
import com.ruoyi.system.hospital.domain.HospitalAlarmRecord;
import com.ruoyi.system.hospital.mapper.HospitalAlarmRecordMapper;
import com.ruoyi.system.hospital.service.IHospitalAlarmRecordService;
import com.ruoyi.system.hospital.vo.HospitalAlarmRecordVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 医院设备报警记录 Service 实现
 *
 * @author cpems
 */
@RequiredArgsConstructor
@Service
public class HospitalAlarmRecordServiceImpl implements IHospitalAlarmRecordService {

    private final HospitalAlarmRecordMapper baseMapper;

    @Override
    public List<HospitalAlarmRecordVo> queryList(HospitalAlarmRecordBo bo) {
        List<HospitalAlarmRecordVo> list = baseMapper.selectHospitalAlarmRecordList(
            bo.getDeviceId(), bo.getAlarmType(), bo.getStatus(), bo.getLevel());
        // 兜底截断，避免无条件查询返回过大
        if (list != null && list.size() > 500) {
            return list.subList(0, 500);
        }
        return list;
    }

    @Override
    public Boolean handle(Long id, String handleRemark, String handleBy) {
        HospitalAlarmRecord record = baseMapper.selectById(id);
        if (record == null) {
            return false;
        }
        if (HospitalConstants.ALARM_STATUS_CLOSED.equals(record.getStatus())) {
            return true;
        }
        HospitalAlarmRecord update = new HospitalAlarmRecord();
        update.setId(id);
        update.setStatus(HospitalConstants.ALARM_STATUS_CLOSED);
        update.setEndTime(new Date());
        update.setHandleBy(StringUtils.isBlank(handleBy) ? "admin" : handleBy);
        update.setHandleTime(new Date());
        update.setHandleRemark(handleRemark);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 查询某规则下某设备的未处理记录数（触发引擎防抖用）
     */
    public long countOpen(Long ruleId, Long deviceId) {
        return baseMapper.selectCount(new LambdaQueryWrapper<HospitalAlarmRecord>()
            .eq(HospitalAlarmRecord::getRuleId, ruleId)
            .eq(HospitalAlarmRecord::getDeviceId, deviceId)
            .eq(HospitalAlarmRecord::getStatus, HospitalConstants.ALARM_STATUS_OPEN));
    }
}
