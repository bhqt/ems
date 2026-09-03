package com.ruoyi.system.hospital.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.hospital.domain.HospitalDevice;
import com.ruoyi.system.hospital.mapper.HospitalDeviceMapper;
import com.ruoyi.system.hospital.service.IHospitalDataScopeService;
import com.ruoyi.system.hospital.service.IHospitalDeviceService;
import com.ruoyi.system.hospital.bo.HospitalDeviceBo;
import com.ruoyi.system.hospital.vo.HospitalDeviceVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 医院检查检验设备台账 Service 实现
 *
 * @author cpems
 */
@RequiredArgsConstructor
@Service
public class HospitalDeviceServiceImpl implements IHospitalDeviceService {

    private final HospitalDeviceMapper baseMapper;
    private final IHospitalDataScopeService dataScopeService;

    @Override
    public HospitalDeviceVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    @Override
    public TableDataInfo<HospitalDeviceVo> queryPageList(HospitalDeviceBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<HospitalDevice> lqw = buildQueryWrapper(bo);
        lqw.orderByDesc(HospitalDevice::getCreateTime).orderByDesc(HospitalDevice::getId);
        Page<HospitalDeviceVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public List<HospitalDeviceVo> queryList(HospitalDeviceBo bo) {
        LambdaQueryWrapper<HospitalDevice> lqw = buildQueryWrapper(bo);
        lqw.orderByDesc(HospitalDevice::getCreateTime);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<HospitalDevice> buildQueryWrapper(HospitalDeviceBo bo) {
        LambdaQueryWrapper<HospitalDevice> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getDeviceName()), HospitalDevice::getDeviceName, bo.getDeviceName());
        lqw.like(StringUtils.isNotBlank(bo.getDeviceCode()), HospitalDevice::getDeviceCode, bo.getDeviceCode());
        lqw.eq(StringUtils.isNotBlank(bo.getDeviceType()), HospitalDevice::getDeviceType, bo.getDeviceType());
        lqw.eq(StringUtils.isNotBlank(bo.getProjectCategory()), HospitalDevice::getProjectCategory, bo.getProjectCategory());
        lqw.eq(StringUtils.isNotBlank(bo.getAreaId()), HospitalDevice::getAreaId, bo.getAreaId());
        lqw.eq(StringUtils.isNotBlank(bo.getDeptId()), HospitalDevice::getDeptId, bo.getDeptId());
        lqw.eq(StringUtils.isNotBlank(bo.getIotDeviceId()), HospitalDevice::getIotDeviceId, bo.getIotDeviceId());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), HospitalDevice::getStatus, bo.getStatus());
        // 多院区数据权限：按当前用户可访问院区过滤
        Set<String> areas = dataScopeService.resolveAccessibleAreas();
        lqw.in(areas != null && !areas.isEmpty(), HospitalDevice::getAreaId, areas);
        return lqw;
    }

    @Override
    public Boolean insertByBo(HospitalDeviceBo bo) {
        checkDeviceCodeUnique(bo.getDeviceCode(), null);
        checkIotDeviceIdUnique(bo.getIotDeviceId(), null);
        HospitalDevice add = BeanUtil.toBean(bo, HospitalDevice.class);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(HospitalDeviceBo bo) {
        checkDeviceCodeUnique(bo.getDeviceCode(), bo.getId());
        checkIotDeviceIdUnique(bo.getIotDeviceId(), bo.getId());
        HospitalDevice update = BeanUtil.toBean(bo, HospitalDevice.class);
        return baseMapper.updateById(update) > 0;
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean bindIotDevice(Long id, String iotDeviceId) {
        if (StringUtils.isBlank(iotDeviceId)) {
            HospitalDevice update = new HospitalDevice();
            update.setId(id);
            update.setIotDeviceId(null);
            return baseMapper.updateById(update) > 0;
        }
        checkIotDeviceIdUnique(iotDeviceId, id);
        HospitalDevice update = new HospitalDevice();
        update.setId(id);
        update.setIotDeviceId(iotDeviceId);
        return baseMapper.updateById(update) > 0;
    }

    @Override
    public Long queryDeviceIdByIotDeviceId(String iotDeviceId) {
        if (StringUtils.isBlank(iotDeviceId)) {
            return null;
        }
        HospitalDevice device = baseMapper.selectOne(new LambdaQueryWrapper<HospitalDevice>()
            .eq(HospitalDevice::getIotDeviceId, iotDeviceId)
            .last("limit 1"));
        return device == null ? null : device.getId();
    }

    /**
     * 校验设备编号唯一（对应 uk_device_code）
     */
    private void checkDeviceCodeUnique(String deviceCode, Long excludeId) {
        if (StringUtils.isBlank(deviceCode)) {
            return;
        }
        LambdaQueryWrapper<HospitalDevice> lqw = new LambdaQueryWrapper<HospitalDevice>()
            .eq(HospitalDevice::getDeviceCode, deviceCode);
        if (excludeId != null) {
            lqw.ne(HospitalDevice::getId, excludeId);
        }
        if (baseMapper.exists(lqw)) {
            throw new ServiceException("设备编号已存在：" + deviceCode);
        }
    }

    /**
     * 校验 IOT 设备 ID 唯一（一个 IOT 设备只允许绑定一台台账设备，解绑传空跳过）
     */
    private void checkIotDeviceIdUnique(String iotDeviceId, Long excludeId) {
        if (StringUtils.isBlank(iotDeviceId)) {
            return;
        }
        LambdaQueryWrapper<HospitalDevice> lqw = new LambdaQueryWrapper<HospitalDevice>()
            .eq(HospitalDevice::getIotDeviceId, iotDeviceId);
        if (excludeId != null) {
            lqw.ne(HospitalDevice::getId, excludeId);
        }
        if (baseMapper.exists(lqw)) {
            throw new ServiceException("IOT 设备已被其他台账绑定：" + iotDeviceId);
        }
    }
}
