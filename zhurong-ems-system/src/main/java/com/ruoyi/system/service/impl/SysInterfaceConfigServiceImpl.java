package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.SysInterfaceConfig;
import com.ruoyi.system.mapper.SysInterfaceConfigMapper;
import com.ruoyi.system.service.ISysInterfaceConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysInterfaceConfigServiceImpl extends ServiceImpl<SysInterfaceConfigMapper, SysInterfaceConfig> implements ISysInterfaceConfigService {

    @Override
    public List<SysInterfaceConfig> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public SysInterfaceConfig getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean save(SysInterfaceConfig config) {
        return baseMapper.insert(config) > 0;
    }

    @Override
    public boolean updateById(SysInterfaceConfig config) {
        return baseMapper.updateById(config) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<SysInterfaceConfig> getByConfigId(Long configId) {
        LambdaQueryWrapper<SysInterfaceConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysInterfaceConfig::getConfigId, configId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean testConnection(Long id) {
        SysInterfaceConfig config = baseMapper.selectById(id);
        if (config == null) {
            return false;
        }
        return true;
    }
}
