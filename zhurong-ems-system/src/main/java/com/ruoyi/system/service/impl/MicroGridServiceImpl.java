package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.mapper.MicroGridMapper;
import com.ruoyi.system.domain.MicroGrid;
import com.ruoyi.system.service.IMicroGridService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 微电网Service业务层处理
 *
 * @author cpems
 * @date 2026-03-27
 */
@Service
@RequiredArgsConstructor
public class MicroGridServiceImpl extends ServiceImpl<MicroGridMapper, MicroGrid> implements IMicroGridService {

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> statistics = new HashMap<>();

        // 微电网总数
        statistics.put("totalCount", count());

        // 并网数量（运行模式 1-并网）
        statistics.put("gridConnectedCount", lambdaQuery().eq(MicroGrid::getRunMode, "1").count());

        // 离网数量（运行模式 2-离网）
        statistics.put("offGridCount", lambdaQuery().eq(MicroGrid::getRunMode, "2").count());

        // 总容量(kW) = 光伏装机容量 + 负荷容量
        double totalCapacity = lambdaQuery().list().stream()
                .mapToDouble(grid -> {
                    double pv = grid.getPvCapacity() == null ? 0d : grid.getPvCapacity().doubleValue();
                    double load = grid.getLoadCapacity() == null ? 0d : grid.getLoadCapacity().doubleValue();
                    return pv + load;
                })
                .sum();
        statistics.put("totalCapacity", totalCapacity);

        return statistics;
    }

}
