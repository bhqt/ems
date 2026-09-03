package com.ruoyi.system.hospital.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.hospital.domain.HospitalMetricDef;
import com.ruoyi.system.hospital.mapper.HospitalMetricDefMapper;
import com.ruoyi.system.hospital.service.IHospitalMetricDefService;
import com.ruoyi.system.hospital.bo.HospitalMetricDefBo;
import com.ruoyi.system.hospital.vo.HospitalMetricDefVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 医院设备指标定义 Service 实现
 *
 * @author cpems
 */
@RequiredArgsConstructor
@Service
public class HospitalMetricDefServiceImpl implements IHospitalMetricDefService {

    private final HospitalMetricDefMapper baseMapper;

    @Override
    public HospitalMetricDefVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    @Override
    public TableDataInfo<HospitalMetricDefVo> queryPageList(HospitalMetricDefBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<HospitalMetricDef> lqw = buildQueryWrapper(bo);
        lqw.orderByDesc(HospitalMetricDef::getCreateTime).orderByDesc(HospitalMetricDef::getId);
        Page<HospitalMetricDefVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public List<HospitalMetricDefVo> queryList(HospitalMetricDefBo bo) {
        LambdaQueryWrapper<HospitalMetricDef> lqw = buildQueryWrapper(bo);
        lqw.orderByDesc(HospitalMetricDef::getCreateTime);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<HospitalMetricDef> buildQueryWrapper(HospitalMetricDefBo bo) {
        LambdaQueryWrapper<HospitalMetricDef> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMetricCode()), HospitalMetricDef::getMetricCode, bo.getMetricCode());
        lqw.like(StringUtils.isNotBlank(bo.getMetricName()), HospitalMetricDef::getMetricName, bo.getMetricName());
        lqw.eq(StringUtils.isNotBlank(bo.getDataType()), HospitalMetricDef::getDataType, bo.getDataType());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), HospitalMetricDef::getStatus, bo.getStatus());
        return lqw;
    }

    @Override
    public Boolean insertByBo(HospitalMetricDefBo bo) {
        HospitalMetricDef add = BeanUtil.toBean(bo, HospitalMetricDef.class);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(HospitalMetricDefBo bo) {
        HospitalMetricDef update = BeanUtil.toBean(bo, HospitalMetricDef.class);
        return baseMapper.updateById(update) > 0;
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
