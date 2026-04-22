package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.EnergyFlow;
import java.util.List;
import java.util.Map;

public interface IEnergyFlowService extends IService<EnergyFlow> {
    List<EnergyFlow> list();
    List<Map<String, Object>> getFlowByType(Integer flowType);
    boolean save(EnergyFlow flow);
    boolean updateById(EnergyFlow flow);
    boolean removeByIds(List<Long> flowIds);
    List<Map<String, Object>> getEnergyBalance();
}
