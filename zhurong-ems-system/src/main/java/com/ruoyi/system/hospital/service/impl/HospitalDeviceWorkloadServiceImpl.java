package com.ruoyi.system.hospital.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.hospital.bo.HospitalDeviceWorkloadBo;
import com.ruoyi.system.hospital.domain.HospitalDeviceWorkload;
import com.ruoyi.system.hospital.mapper.HospitalDeviceWorkloadMapper;
import com.ruoyi.system.hospital.service.IHospitalDeviceWorkloadService;
import com.ruoyi.system.hospital.vo.HospitalDeviceWorkloadVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 医院设备工作量（检查量） Service 实现
 *
 * @author cpems
 */
@RequiredArgsConstructor
@Service
public class HospitalDeviceWorkloadServiceImpl implements IHospitalDeviceWorkloadService {

    private final HospitalDeviceWorkloadMapper baseMapper;

    @Override
    public HospitalDeviceWorkloadVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    @Override
    public TableDataInfo<HospitalDeviceWorkloadVo> queryPageList(HospitalDeviceWorkloadBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<HospitalDeviceWorkload> lqw = Wrappers.<HospitalDeviceWorkload>lambdaQuery()
            .eq(bo.getDeviceId() != null, HospitalDeviceWorkload::getDeviceId, bo.getDeviceId())
            .select(HospitalDeviceWorkload::getId, HospitalDeviceWorkload::getDeviceId,
                HospitalDeviceWorkload::getWorkloadCount, HospitalDeviceWorkload::getStatDate,
                HospitalDeviceWorkload::getCreateTime)
            .orderByDesc(HospitalDeviceWorkload::getStatDate).orderByDesc(HospitalDeviceWorkload::getId);
        Page<HospitalDeviceWorkloadVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public Boolean insertByBo(HospitalDeviceWorkloadBo bo) {
        // 同一设备同一日期唯一，存在则累加
        HospitalDeviceWorkload exist = baseMapper.selectOne(Wrappers.<HospitalDeviceWorkload>lambdaQuery()
            .eq(HospitalDeviceWorkload::getDeviceId, bo.getDeviceId())
            .eq(HospitalDeviceWorkload::getStatDate, bo.getStatDate())
            .last("limit 1"));
        if (exist != null) {
            HospitalDeviceWorkload update = new HospitalDeviceWorkload();
            update.setId(exist.getId());
            update.setWorkloadCount(exist.getWorkloadCount().add(bo.getWorkloadCount()));
            return baseMapper.updateById(update) > 0;
        }
        HospitalDeviceWorkload add = BeanUtil.toBean(bo, HospitalDeviceWorkload.class);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(HospitalDeviceWorkloadBo bo) {
        HospitalDeviceWorkload update = BeanUtil.toBean(bo, HospitalDeviceWorkload.class);
        return baseMapper.updateById(update) > 0;
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
