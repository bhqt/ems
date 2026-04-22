package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.WeatherForecast;
import java.util.List;

public interface IWeatherForecastService extends IService<WeatherForecast> {
    List<WeatherForecast> list();
    WeatherForecast getById(Long weatherId);
    boolean save(WeatherForecast forecast);
    boolean updateById(WeatherForecast forecast);
    boolean removeByIds(List<Long> weatherIds);
    Double doForecast();
}
