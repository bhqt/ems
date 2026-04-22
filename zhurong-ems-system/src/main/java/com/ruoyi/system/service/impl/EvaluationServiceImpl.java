package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.CostSavingRecord;
import com.ruoyi.system.domain.EfficiencyImprovementRecord;
import com.ruoyi.system.domain.EmissionReductionRecord;
import com.ruoyi.system.mapper.CostSavingRecordMapper;
import com.ruoyi.system.mapper.EfficiencyImprovementRecordMapper;
import com.ruoyi.system.mapper.EmissionReductionRecordMapper;
import com.ruoyi.system.service.IEvaluationService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class EvaluationServiceImpl extends ServiceImpl<CostSavingRecordMapper, CostSavingRecord> implements IEvaluationService {

    @Override
    public List<CostSavingRecord> listCostSaving() {
        return baseMapper.selectList(null);
    }

    @Override
    public List<Map<String, Object>> getCostSavingTrend(String startDate, String endDate) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("date", "2026-07");
        data.put("saving", 45000.0);
        result.add(data);
        return result;
    }

    @Override
    public List<Map<String, Object>> getEfficiencyTrend(String startDate, String endDate) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("date", "2026-07");
        data.put("improvement", 6.9);
        result.add(data);
        return result;
    }

    @Override
    public List<Map<String, Object>> getEmissionTrend(String startDate, String endDate) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("date", "2026-07");
        data.put("reduction", 95.0);
        result.add(data);
        return result;
    }

    @Override
    public Map<String, Object> getEvaluationSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalCostSaving", 90000.0);
        summary.put("avgSavingRate", 9.15);
        summary.put("totalEfficiencyImprovement", 6.9);
        summary.put("totalEmissionReduction", 95.0);
        summary.put("schemeCount", 1);
        return summary;
    }
}
