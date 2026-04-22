package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.EnergyDemandForecast;
import com.ruoyi.system.mapper.EnergyDemandForecastMapper;
import com.ruoyi.system.service.IEnergyDemandForecastService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyDemandForecastServiceImpl extends ServiceImpl<EnergyDemandForecastMapper, EnergyDemandForecast> implements IEnergyDemandForecastService {

    @Override
    public List<EnergyDemandForecast> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public EnergyDemandForecast getById(Long forecastId) {
        return baseMapper.selectById(forecastId);
    }

    @Override
    public boolean save(EnergyDemandForecast forecast) {
        return baseMapper.insert(forecast) > 0;
    }

    @Override
    public boolean updateById(EnergyDemandForecast forecast) {
        return baseMapper.updateById(forecast) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> forecastIds) {
        return baseMapper.deleteBatchIds(forecastIds) > 0;
    }
}
