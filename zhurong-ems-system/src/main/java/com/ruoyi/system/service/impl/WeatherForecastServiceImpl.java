package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.WeatherForecast;
import com.ruoyi.system.mapper.WeatherForecastMapper;
import com.ruoyi.system.service.IWeatherForecastService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WeatherForecastServiceImpl extends ServiceImpl<WeatherForecastMapper, WeatherForecast> implements IWeatherForecastService {

    @Override
    public List<WeatherForecast> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public WeatherForecast getById(Long weatherId) {
        return baseMapper.selectById(weatherId);
    }

    @Override
    public boolean save(WeatherForecast forecast) {
        return baseMapper.insert(forecast) > 0;
    }

    @Override
    public boolean updateById(WeatherForecast forecast) {
        return baseMapper.updateById(forecast) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> weatherIds) {
        return baseMapper.deleteBatchIds(weatherIds) > 0;
    }

    @Override
    public Double doForecast() {
        return Math.random() * 15 + 20.0;
    }
}
