package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.system.domain.ChargingBrand;
import com.ruoyi.system.domain.bo.ChargingBrandBo;
import com.ruoyi.system.domain.vo.ChargingBrandVo;
import com.ruoyi.system.mapper.ChargingBrandMapper;
import com.ruoyi.system.service.IChargingBrandService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 充电站品牌Service实现类
 */
@Service
public class ChargingBrandServiceImpl extends ServiceImpl<ChargingBrandMapper, ChargingBrand> implements IChargingBrandService {

    @Override
    public IPage<ChargingBrandVo> selectChargingBrandPage(ChargingBrand chargingBrand, PageQuery pageQuery) {
        LambdaQueryWrapper<ChargingBrand> lqw = new LambdaQueryWrapper<ChargingBrand>()
                .like(chargingBrand.getBrandName() != null, ChargingBrand::getBrandName, chargingBrand.getBrandName())
                .eq(chargingBrand.getStatus() != null, ChargingBrand::getStatus, chargingBrand.getStatus());
        Page<ChargingBrand> page = pageQuery.build();
        Page<ChargingBrand> result = page(page, lqw);
        return result.convert(item -> {
            ChargingBrandVo vo = new ChargingBrandVo();
            BeanUtils.copyProperties(item, vo);
            vo.setStatusName(item.getStatus().equals("0") ? "正常" : "停用");
            return vo;
        });
    }

    @Override
    public ChargingBrandVo selectChargingBrandById(Long id) {
        return getBaseMapper().selectChargingBrandViewById(id);
    }

    @Override
    public int insertChargingBrand(ChargingBrandBo bo) {
        ChargingBrand chargingBrand = new ChargingBrand();
        BeanUtils.copyProperties(bo, chargingBrand);
        return save(chargingBrand) ? 1 : 0;
    }

    @Override
    public int updateChargingBrand(ChargingBrandBo bo) {
        ChargingBrand chargingBrand = new ChargingBrand();
        BeanUtils.copyProperties(bo, chargingBrand);
        return updateById(chargingBrand) ? 1 : 0;
    }

    @Override
    public int deleteChargingBrandByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? ids.length : 0;
    }

    @Override
    public ChargingBrandVo getChargingBrandStatistics() {
        return getBaseMapper().selectChargingBrandStatistics();
    }

    @Override
    public List<ChargingBrandVo> exportChargingBrandList(ChargingBrand chargingBrand) {
        return getBaseMapper().selectChargingBrandViewList(chargingBrand);
    }

}
