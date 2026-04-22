package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.ForecastModelConfig;
import java.util.List;

public interface IForecastModelConfigService extends IService<ForecastModelConfig> {
    List<ForecastModelConfig> list();
    ForecastModelConfig getById(Long configId);
    boolean save(ForecastModelConfig config);
    boolean updateById(ForecastModelConfig config);
    boolean removeByIds(List<Long> configIds);
    boolean trainModel(Long configId);
}
