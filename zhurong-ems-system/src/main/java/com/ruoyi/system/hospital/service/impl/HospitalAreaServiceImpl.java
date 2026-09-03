package com.ruoyi.system.hospital.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.hospital.bo.HospitalAreaBo;
import com.ruoyi.system.hospital.domain.HospitalArea;
import com.ruoyi.system.hospital.mapper.HospitalAreaMapper;
import com.ruoyi.system.hospital.service.IHospitalAreaService;
import com.ruoyi.system.hospital.vo.HospitalAreaVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 医院院区 Service 实现
 *
 * @author cpems
 */
@RequiredArgsConstructor
@Service
public class HospitalAreaServiceImpl implements IHospitalAreaService {

    private final HospitalAreaMapper baseMapper;

    @Override
    public HospitalAreaVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    @Override
    public TableDataInfo<HospitalAreaVo> queryPageList(HospitalAreaBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<HospitalArea> lqw = buildQueryWrapper(bo);
        lqw.orderByAsc(HospitalArea::getSort).orderByAsc(HospitalArea::getId);
        Page<HospitalAreaVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public List<HospitalAreaVo> queryList(HospitalAreaBo bo) {
        LambdaQueryWrapper<HospitalArea> lqw = buildQueryWrapper(bo);
        lqw.orderByAsc(HospitalArea::getSort).orderByAsc(HospitalArea::getId);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<HospitalArea> buildQueryWrapper(HospitalAreaBo bo) {
        LambdaQueryWrapper<HospitalArea> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getAreaName()), HospitalArea::getAreaName, bo.getAreaName());
        lqw.eq(StringUtils.isNotBlank(bo.getAreaCode()), HospitalArea::getAreaCode, bo.getAreaCode());
        lqw.eq(StringUtils.isNotBlank(bo.getAreaType()), HospitalArea::getAreaType, bo.getAreaType());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), HospitalArea::getStatus, bo.getStatus());
        return lqw;
    }

    @Override
    public Boolean insertByBo(HospitalAreaBo bo) {
        HospitalArea add = BeanUtil.toBean(bo, HospitalArea.class);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(HospitalAreaBo bo) {
        HospitalArea update = BeanUtil.toBean(bo, HospitalArea.class);
        return baseMapper.updateById(update) > 0;
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
