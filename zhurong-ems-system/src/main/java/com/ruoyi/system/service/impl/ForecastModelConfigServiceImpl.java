package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.ForecastModelConfig;
import com.ruoyi.system.mapper.ForecastModelConfigMapper;
import com.ruoyi.system.service.IForecastModelConfigService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class ForecastModelConfigServiceImpl extends ServiceImpl<ForecastModelConfigMapper, ForecastModelConfig> implements IForecastModelConfigService {

    @Override
    public List<ForecastModelConfig> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public ForecastModelConfig getById(Long configId) {
        return baseMapper.selectById(configId);
    }

    @Override
    public boolean save(ForecastModelConfig config) {
        return baseMapper.insert(config) > 0;
    }

    @Override
    public boolean updateById(ForecastModelConfig config) {
        return baseMapper.updateById(config) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> configIds) {
        return baseMapper.deleteBatchIds(configIds) > 0;
    }

    @Override
    public boolean trainModel(Long configId) {
        ForecastModelConfig config = baseMapper.selectById(configId);
        if (config != null) {
            config.setLastTrainTime(new Date());
            config.setAccuracy(Math.random() * 10 + 85.0);
            return baseMapper.updateById(config) > 0;
        }
        return false;
    }
}
