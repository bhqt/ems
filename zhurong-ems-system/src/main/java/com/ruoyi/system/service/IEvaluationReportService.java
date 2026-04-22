package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.EvaluationReport;

import java.util.List;

public interface IEvaluationReportService extends IService<EvaluationReport> {

    List<EvaluationReport> list();

    EvaluationReport getById(Long reportId);

    boolean save(EvaluationReport report);

    boolean updateById(EvaluationReport report);

    boolean removeByIds(List<Long> reportIds);
}
