package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.ReportTemplate;
import com.ruoyi.system.mapper.ReportTemplateMapper;
import com.ruoyi.system.service.IReportTemplateService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 报表模板Service业务层处理
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Service
public class ReportTemplateServiceImpl extends ServiceImpl<ReportTemplateMapper, ReportTemplate> implements IReportTemplateService {

    @Override
    public List<ReportTemplate> selectReportTemplateList(ReportTemplate reportTemplate) {
        return baseMapper.selectList(null);
    }

    @Override
    public int insertReportTemplate(ReportTemplate reportTemplate) {
        return baseMapper.insert(reportTemplate);
    }

    @Override
    public int updateReportTemplate(ReportTemplate reportTemplate) {
        return baseMapper.updateById(reportTemplate);
    }

    @Override
    public int deleteReportTemplateById(Long templateId) {
        return baseMapper.deleteById(templateId);
    }

    @Override
    public int deleteReportTemplateByIds(Long[] templateIds) {
        return baseMapper.deleteBatchIds(Arrays.asList(templateIds));
    }

}
