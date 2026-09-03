package com.ruoyi.system.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.hospital.domain.HospitalCallbackLog;
import com.ruoyi.system.hospital.mapper.HospitalCallbackLogMapper;
import com.ruoyi.system.hospital.service.IHospitalCallbackLogService;
import com.ruoyi.system.hospital.bo.HospitalCallbackLogBo;
import com.ruoyi.system.hospital.vo.HospitalCallbackLogVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 医院 IOT 回调日志 Service 实现
 *
 * @author cpems
 */
@RequiredArgsConstructor
@Service
public class HospitalCallbackLogServiceImpl implements IHospitalCallbackLogService {

    private final HospitalCallbackLogMapper baseMapper;

    @Override
    public TableDataInfo<HospitalCallbackLogVo> queryPageList(HospitalCallbackLogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<HospitalCallbackLog> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getRequestId()), HospitalCallbackLog::getRequestId, bo.getRequestId());
        lqw.eq(StringUtils.isNotBlank(bo.getSourceIp()), HospitalCallbackLog::getSourceIp, bo.getSourceIp());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), HospitalCallbackLog::getStatus, bo.getStatus());
        lqw.orderByDesc(HospitalCallbackLog::getReceiveTime).orderByDesc(HospitalCallbackLog::getId);
        Page<HospitalCallbackLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public Long insertLog(HospitalCallbackLog log) {
        baseMapper.insert(log);
        return log.getId();
    }

    @Override
    public void updateLog(HospitalCallbackLog log) {
        baseMapper.updateById(log);
    }
}
