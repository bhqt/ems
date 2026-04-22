package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.EnergyDemandForecast;

import java.util.List;

public interface IEnergyDemandForecastService extends IService<EnergyDemandForecast> {

    List<EnergyDemandForecast> list();

    EnergyDemandForecast getById(Long forecastId);

    boolean save(EnergyDemandForecast forecast);

    boolean updateById(EnergyDemandForecast forecast);

    boolean removeByIds(List<Long> forecastIds);
}
