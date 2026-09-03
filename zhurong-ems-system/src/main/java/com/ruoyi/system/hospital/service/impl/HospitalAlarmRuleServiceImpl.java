package com.ruoyi.system.hospital.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.hospital.bo.HospitalAlarmRuleBo;
import com.ruoyi.system.hospital.domain.HospitalAlarmRule;
import com.ruoyi.system.hospital.domain.HospitalDevice;
import com.ruoyi.system.hospital.mapper.HospitalAlarmRuleMapper;
import com.ruoyi.system.hospital.mapper.HospitalDeviceMapper;
import com.ruoyi.system.hospital.service.IHospitalAlarmRuleService;
import com.ruoyi.system.hospital.vo.HospitalAlarmRuleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医院设备报警规则 Service 实现
 *
 * @author cpems
 */
@RequiredArgsConstructor
@Service
public class HospitalAlarmRuleServiceImpl implements IHospitalAlarmRuleService {

    private final HospitalAlarmRuleMapper baseMapper;
    private final HospitalDeviceMapper deviceMapper;

    @Override
    public TableDataInfo<HospitalAlarmRuleVo> queryPageList(HospitalAlarmRuleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<HospitalAlarmRule> lqw = buildQueryWrapper(bo);
        lqw.orderByDesc(HospitalAlarmRule::getCreateTime).orderByDesc(HospitalAlarmRule::getId);
        Page<HospitalAlarmRuleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        fillDeviceName(result.getRecords());
        return TableDataInfo.build(result);
    }

    @Override
    public List<HospitalAlarmRuleVo> queryList(HospitalAlarmRuleBo bo) {
        LambdaQueryWrapper<HospitalAlarmRule> lqw = buildQueryWrapper(bo);
        lqw.orderByDesc(HospitalAlarmRule::getCreateTime);
        List<HospitalAlarmRuleVo> list = baseMapper.selectVoList(lqw);
        fillDeviceName(list);
        return list;
    }

    private void fillDeviceName(List<HospitalAlarmRuleVo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, String> nameMap = new HashMap<>(list.size() * 2);
        for (HospitalAlarmRuleVo vo : list) {
            if (vo.getDeviceId() != null && !nameMap.containsKey(vo.getDeviceId())) {
                HospitalDevice device = deviceMapper.selectById(vo.getDeviceId());
                nameMap.put(vo.getDeviceId(), device == null ? null : device.getDeviceName());
            }
            vo.setDeviceName(nameMap.get(vo.getDeviceId()));
        }
    }

    private LambdaQueryWrapper<HospitalAlarmRule> buildQueryWrapper(HospitalAlarmRuleBo bo) {
        LambdaQueryWrapper<HospitalAlarmRule> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getRuleName()), HospitalAlarmRule::getRuleName, bo.getRuleName());
        lqw.eq(bo.getDeviceId() != null, HospitalAlarmRule::getDeviceId, bo.getDeviceId());
        lqw.eq(StringUtils.isNotBlank(bo.getRuleType()), HospitalAlarmRule::getRuleType, bo.getRuleType());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), HospitalAlarmRule::getStatus, bo.getStatus());
        return lqw;
    }

    @Override
    public HospitalAlarmRuleVo queryById(Long id) {
        HospitalAlarmRuleVo vo = baseMapper.selectVoById(id);
        if (vo != null) {
            fillDeviceName(java.util.Collections.singletonList(vo));
        }
        return vo;
    }

    @Override
    public Boolean insertByBo(HospitalAlarmRuleBo bo) {
        checkRule(bo, null);
        HospitalAlarmRule add = BeanUtil.toBean(bo, HospitalAlarmRule.class);
        if (StringUtils.isBlank(add.getStatus())) {
            add.setStatus("0");
        }
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(HospitalAlarmRuleBo bo) {
        checkRule(bo, bo.getId());
        HospitalAlarmRule update = BeanUtil.toBean(bo, HospitalAlarmRule.class);
        return baseMapper.updateById(update) > 0;
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 校验规则配置合法性
     */
    private void checkRule(HospitalAlarmRuleBo bo, Long excludeId) {
        if ("THRESHOLD".equals(bo.getRuleType())) {
            if (StringUtils.isBlank(bo.getMetricCode())) {
                throw new ServiceException("阈值规则必须配置指标编码");
            }
            if (StringUtils.isBlank(bo.getCondition())) {
                throw new ServiceException("阈值规则必须配置比较条件");
            }
            if (bo.getThresholdValue() == null) {
                throw new ServiceException("阈值规则必须配置阈值");
            }
        } else if ("OFFLINE".equals(bo.getRuleType())) {
            if (bo.getOfflineTimeoutMin() == null || bo.getOfflineTimeoutMin() <= 0) {
                throw new ServiceException("离线规则必须配置超时分钟数");
            }
        } else {
            throw new ServiceException("不支持的规则类型：" + bo.getRuleType());
        }
        if (bo.getDeviceId() != null && deviceMapper.selectById(bo.getDeviceId()) == null) {
            throw new ServiceException("绑定的设备不存在：" + bo.getDeviceId());
        }
    }
}
