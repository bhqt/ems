package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.EnergyPlan;
import com.ruoyi.system.mapper.EnergyPlanMapper;
import com.ruoyi.system.service.IEnergyPlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyPlanServiceImpl extends ServiceImpl<EnergyPlanMapper, EnergyPlan> implements IEnergyPlanService {

    @Override
    public List<EnergyPlan> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public EnergyPlan getById(Long planId) {
        return baseMapper.selectById(planId);
    }

    @Override
    public boolean save(EnergyPlan energyPlan) {
        return baseMapper.insert(energyPlan) > 0;
    }

    @Override
    public boolean updateById(EnergyPlan energyPlan) {
        return baseMapper.updateById(energyPlan) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> planIds) {
        return baseMapper.deleteBatchIds(planIds) > 0;
    }
}
