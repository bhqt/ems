package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.CostSavingRecord;
import java.util.List;
import java.util.Map;

public interface IEvaluationService extends IService<CostSavingRecord> {
    List<CostSavingRecord> listCostSaving();
    List<Map<String, Object>> getCostSavingTrend(String startDate, String endDate);
    List<Map<String, Object>> getEfficiencyTrend(String startDate, String endDate);
    List<Map<String, Object>> getEmissionTrend(String startDate, String endDate);
    Map<String, Object> getEvaluationSummary();
}
