package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.OptimizationScheme;
import com.ruoyi.system.mapper.OptimizationSchemeMapper;
import com.ruoyi.system.service.IOptimizationSchemeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptimizationSchemeServiceImpl extends ServiceImpl<OptimizationSchemeMapper, OptimizationScheme> implements IOptimizationSchemeService {

    @Override
    public List<OptimizationScheme> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public OptimizationScheme getById(Long schemeId) {
        return baseMapper.selectById(schemeId);
    }

    @Override
    public boolean save(OptimizationScheme scheme) {
        return baseMapper.insert(scheme) > 0;
    }

    @Override
    public boolean updateById(OptimizationScheme scheme) {
        return baseMapper.updateById(scheme) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> schemeIds) {
        return baseMapper.deleteBatchIds(schemeIds) > 0;
    }

    @Override
    public boolean executeOptimization(Long schemeId) {
        OptimizationScheme scheme = baseMapper.selectById(schemeId);
        if (scheme != null && scheme.getStatus() == 3) {
            scheme.setStatus(4);
            return baseMapper.updateById(scheme) > 0;
        }
        return false;
    }
}
