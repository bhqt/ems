package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.system.domain.ChargingPile;
import com.ruoyi.system.domain.bo.ChargingPileBo;
import com.ruoyi.system.domain.vo.ChargingPileVo;
import com.ruoyi.system.mapper.ChargingPileMapper;
import com.ruoyi.system.service.IChargingPileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 充电桩Service实现类
 */
@Service
public class ChargingPileServiceImpl extends ServiceImpl<ChargingPileMapper, ChargingPile> implements IChargingPileService {

    @Override
    public IPage<ChargingPileVo> selectChargingPilePage(ChargingPile chargingPile, PageQuery pageQuery) {
        LambdaQueryWrapper<ChargingPile> lqw = new LambdaQueryWrapper<ChargingPile>()
                .like(chargingPile.getName() != null, ChargingPile::getName, chargingPile.getName())
                .like(chargingPile.getEncoding() != null, ChargingPile::getEncoding, chargingPile.getEncoding())
                .eq(chargingPile.getBrand() != null, ChargingPile::getBrand, chargingPile.getBrand())
                .eq(chargingPile.getModel() != null, ChargingPile::getModel, chargingPile.getModel())
                .eq(chargingPile.getStatus() != null, ChargingPile::getStatus, chargingPile.getStatus())
                .eq(chargingPile.getWorkStatus() != null, ChargingPile::getWorkStatus, chargingPile.getWorkStatus());
        Page<ChargingPile> page = pageQuery.build();
        Page<ChargingPile> result = page(page, lqw);
        return result.convert(item -> {
            ChargingPileVo vo = new ChargingPileVo();
            BeanUtils.copyProperties(item, vo);
            return vo;
        });
    }

    @Override
    public ChargingPileVo selectChargingPileById(Long pileId) {
        return getBaseMapper().selectChargingPileViewById(pileId);
    }

    @Override
    public int insertChargingPile(ChargingPileBo bo) {
        ChargingPile chargingPile = new ChargingPile();
        BeanUtils.copyProperties(bo, chargingPile);
        return save(chargingPile) ? 1 : 0;
    }

    @Override
    public int updateChargingPile(ChargingPileBo bo) {
        ChargingPile chargingPile = new ChargingPile();
        BeanUtils.copyProperties(bo, chargingPile);
        return updateById(chargingPile) ? 1 : 0;
    }

    @Override
    public int deleteChargingPileByIds(Long[] pileIds) {
        return removeByIds(Arrays.asList(pileIds)) ? pileIds.length : 0;
    }

    @Override
    public int openOrClosePile(Long pileId, String status) {
        ChargingPile chargingPile = new ChargingPile();
        chargingPile.setPileId(pileId);
        chargingPile.setStatus(status);
        return updateById(chargingPile) ? 1 : 0;
    }

    @Override
    public ChargingPileVo getChargingPileStatistics() {
        return getBaseMapper().selectChargingPileStatistics();
    }

    @Override
    public List<ChargingPileVo> exportChargingPileList(ChargingPile chargingPile) {
        return getBaseMapper().selectChargingPileViewList(chargingPile);
    }
}
