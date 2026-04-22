package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.EnergyPriceForecast;
import java.util.List;

public interface IEnergyPriceForecastService extends IService<EnergyPriceForecast> {
    List<EnergyPriceForecast> list();
    EnergyPriceForecast getById(Long priceId);
    boolean save(EnergyPriceForecast forecast);
    boolean updateById(EnergyPriceForecast forecast);
    boolean removeByIds(List<Long> priceIds);
    Double doForecast(Integer energyType, String priceType);
}
