package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.EnergyPlan;

import java.util.List;

public interface IEnergyPlanService extends IService<EnergyPlan> {

    List<EnergyPlan> list();

    EnergyPlan getById(Long planId);

    boolean save(EnergyPlan energyPlan);

    boolean updateById(EnergyPlan energyPlan);

    boolean removeByIds(List<Long> planIds);
}
