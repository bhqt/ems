package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.LoadForecast;
import com.ruoyi.system.mapper.LoadForecastMapper;
import com.ruoyi.system.service.ILoadForecastService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LoadForecastServiceImpl extends ServiceImpl<LoadForecastMapper, LoadForecast> implements ILoadForecastService {

    @Override
    public List<LoadForecast> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public LoadForecast getById(Long loadId) {
        return baseMapper.selectById(loadId);
    }

    @Override
    public boolean save(LoadForecast forecast) {
        return baseMapper.insert(forecast) > 0;
    }

    @Override
    public boolean updateById(LoadForecast forecast) {
        return baseMapper.updateById(forecast) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> loadIds) {
        return baseMapper.deleteBatchIds(loadIds) > 0;
    }

    @Override
    public Double doForecast(Integer forecastType, Integer loadType) {
        return Math.random() * 10000 + 40000.0;
    }
}
