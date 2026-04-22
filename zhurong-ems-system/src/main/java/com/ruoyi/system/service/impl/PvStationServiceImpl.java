package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.mapper.PvStationMapper;
import com.ruoyi.system.domain.PvStation;
import com.ruoyi.system.service.IPvStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 光伏电站Service业务层处理
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Service
@RequiredArgsConstructor
public class PvStationServiceImpl extends ServiceImpl<PvStationMapper, PvStation> implements IPvStationService {

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 统计电站总数
        long totalStation = count();
        statistics.put("totalStation", totalStation);
        
        // 统计正常电站数量
        long normalStation = lambdaQuery().eq(PvStation::getStatus, "1").count();
        statistics.put("normalStation", normalStation);
        
        // 统计故障电站数量
        long faultStation = lambdaQuery().eq(PvStation::getStatus, "2").count();
        statistics.put("faultStation", faultStation);
        
        // 统计维护电站数量
        long maintenanceStation = lambdaQuery().eq(PvStation::getStatus, "3").count();
        statistics.put("maintenanceStation", maintenanceStation);
        
        // 统计总装机容量
        Double totalCapacity = lambdaQuery().list().stream()
                .map(PvStation::getCapacity)
                .filter(capacity -> capacity != null)
                .mapToDouble(capacity -> capacity.doubleValue())
                .sum();
        statistics.put("totalCapacity", totalCapacity);
        
        // 今日发电量（模拟数据）
        statistics.put("todayEnergy", 0);
        
        return statistics;
    }

}
