package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.EnergyFlow;
import com.ruoyi.system.mapper.EnergyFlowMapper;
import com.ruoyi.system.service.IEnergyFlowService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class EnergyFlowServiceImpl extends ServiceImpl<EnergyFlowMapper, EnergyFlow> implements IEnergyFlowService {

    @Override
    public List<EnergyFlow> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public List<Map<String, Object>> getFlowByType(Integer flowType) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("flowType", flowType);
        data.put("flowValue", Math.random() * 1000 + 500);
        data.put("lossRate", Math.random() * 5 + 2);
        data.put("efficiency", 95.0 - Math.random() * 5);
        result.add(data);
        return result;
    }

    @Override
    public boolean save(EnergyFlow flow) {
        return baseMapper.insert(flow) > 0;
    }

    @Override
    public boolean updateById(EnergyFlow flow) {
        return baseMapper.updateById(flow) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> flowIds) {
        return baseMapper.deleteBatchIds(flowIds) > 0;
    }

    @Override
    public List<Map<String, Object>> getEnergyBalance() {
        List<Map<String, Object>> result = new ArrayList<>();
        String[] types = {"电力", "热力", "燃料", "水"};
        for (int i = 0; i < types.length; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("type", types[i]);
            data.put("input", Math.random() * 10000 + 5000);
            data.put("output", Math.random() * 9000 + 4500);
            data.put("loss", Math.random() * 500 + 100);
            data.put("efficiency", 85.0 + Math.random() * 10);
            result.add(data);
        }
        return result;
    }
}
