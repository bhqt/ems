package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.EnergyPriceForecast;
import com.ruoyi.system.mapper.EnergyPriceForecastMapper;
import com.ruoyi.system.service.IEnergyPriceForecastService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EnergyPriceForecastServiceImpl extends ServiceImpl<EnergyPriceForecastMapper, EnergyPriceForecast> implements IEnergyPriceForecastService {

    @Override
    public List<EnergyPriceForecast> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public EnergyPriceForecast getById(Long priceId) {
        return baseMapper.selectById(priceId);
    }

    @Override
    public boolean save(EnergyPriceForecast forecast) {
        return baseMapper.insert(forecast) > 0;
    }

    @Override
    public boolean updateById(EnergyPriceForecast forecast) {
        return baseMapper.updateById(forecast) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> priceIds) {
        return baseMapper.deleteBatchIds(priceIds) > 0;
    }

    @Override
    public Double doForecast(Integer energyType, String priceType) {
        return Math.random() * 0.5 + 0.8;
    }
}
