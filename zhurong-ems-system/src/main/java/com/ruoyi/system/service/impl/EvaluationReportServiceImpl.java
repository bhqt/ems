package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.EvaluationReport;
import com.ruoyi.system.mapper.EvaluationReportMapper;
import com.ruoyi.system.service.IEvaluationReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationReportServiceImpl extends ServiceImpl<EvaluationReportMapper, EvaluationReport> implements IEvaluationReportService {

    @Override
    public List<EvaluationReport> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public EvaluationReport getById(Long reportId) {
        return baseMapper.selectById(reportId);
    }

    @Override
    public boolean save(EvaluationReport report) {
        return baseMapper.insert(report) > 0;
    }

    @Override
    public boolean updateById(EvaluationReport report) {
        return baseMapper.updateById(report) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> reportIds) {
        return baseMapper.deleteBatchIds(reportIds) > 0;
    }
}
