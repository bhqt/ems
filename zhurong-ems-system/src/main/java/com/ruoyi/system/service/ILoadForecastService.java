package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.LoadForecast;
import java.util.List;

public interface ILoadForecastService extends IService<LoadForecast> {
    List<LoadForecast> list();
    LoadForecast getById(Long loadId);
    boolean save(LoadForecast forecast);
    boolean updateById(LoadForecast forecast);
    boolean removeByIds(List<Long> loadIds);
    Double doForecast(Integer forecastType, Integer loadType);
}
