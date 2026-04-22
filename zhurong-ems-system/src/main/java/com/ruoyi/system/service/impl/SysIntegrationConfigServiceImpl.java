package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.SysIntegrationConfig;
import com.ruoyi.system.mapper.SysIntegrationConfigMapper;
import com.ruoyi.system.service.ISysIntegrationConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysIntegrationConfigServiceImpl extends ServiceImpl<SysIntegrationConfigMapper, SysIntegrationConfig> implements ISysIntegrationConfigService {

    @Override
    public List<SysIntegrationConfig> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public SysIntegrationConfig getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean save(SysIntegrationConfig config) {
        return baseMapper.insert(config) > 0;
    }

    @Override
    public boolean updateById(SysIntegrationConfig config) {
        return baseMapper.updateById(config) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public SysIntegrationConfig getBySystemCode(String systemCode) {
        LambdaQueryWrapper<SysIntegrationConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysIntegrationConfig::getSystemCode, systemCode);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public boolean enable(Long id) {
        SysIntegrationConfig config = new SysIntegrationConfig();
        config.setId(id);
        config.setStatus(1);
        return baseMapper.updateById(config) > 0;
    }

    @Override
    public boolean disable(Long id) {
        SysIntegrationConfig config = new SysIntegrationConfig();
        config.setId(id);
        config.setStatus(2);
        return baseMapper.updateById(config) > 0;
    }
}
