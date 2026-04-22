package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.ChargingStation;
import com.ruoyi.system.domain.bo.ChargingStationBo;
import com.ruoyi.system.service.IChargingStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.vo.ChargingStationVo;
import com.ruoyi.system.mapper.ChargingStationMapper;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 充电站信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-10-08
 */
@RequiredArgsConstructor
@Service
public class ChargingStationServiceImpl implements IChargingStationService {

    private final ChargingStationMapper baseMapper;

    /**
     * 查询充电站信息
     */
    @Override
    public ChargingStationVo queryById(Long stationId){
        return baseMapper.selectVoById(stationId);
    }

    /**
     * 查询充电站信息列表
     */
    @Override
    public TableDataInfo<ChargingStationVo> queryPageList(ChargingStationBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ChargingStation> lqw = buildQueryWrapper(bo);
        lqw.orderByDesc(ChargingStation::getUpdateTime);
        Page<ChargingStationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询充电站信息列表
     */
    @Override
    public List<ChargingStationVo> queryList(ChargingStationBo bo) {
        LambdaQueryWrapper<ChargingStation> lqw = buildQueryWrapper(bo);
        lqw.orderByDesc(ChargingStation::getUpdateTime);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ChargingStation> buildQueryWrapper(ChargingStationBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ChargingStation> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMerchantId()), ChargingStation::getMerchantId, bo.getMerchantId());
        lqw.like(StringUtils.isNotBlank(bo.getMerchantName()), ChargingStation::getMerchantName, bo.getMerchantName());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), ChargingStation::getType, bo.getType());
        lqw.like(StringUtils.isNotBlank(bo.getName()), ChargingStation::getName, bo.getName());
        lqw.eq(bo.getPrice() != null, ChargingStation::getPrice, bo.getPrice());
        lqw.eq(StringUtils.isNotBlank(bo.getAddress()), ChargingStation::getAddress, bo.getAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getActivity()), ChargingStation::getActivity, bo.getActivity());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), ChargingStation::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增充电站信息
     */
    @Override
    public Boolean insertByBo(ChargingStationBo bo) {
        ChargingStation add = BeanUtil.toBean(bo, ChargingStation.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setStationId(add.getStationId());
        }
        return flag;
    }

    /**
     * 修改充电站信息
     */
    @Override
    public Boolean updateByBo(ChargingStationBo bo) {
        ChargingStation update = BeanUtil.toBean(bo, ChargingStation.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ChargingStation entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除充电站信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
