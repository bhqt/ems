package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.ReportTemplate;

import java.util.List;

/**
 * 报表模板Service接口
 * 
 * @author cpems
 * @date 2026-03-27
 */
public interface IReportTemplateService extends IService<ReportTemplate> {

    /**
     * 查询报表模板列表
     * 
     * @param reportTemplate 报表模板
     * @return 报表模板集合
     */
    List<ReportTemplate> selectReportTemplateList(ReportTemplate reportTemplate);

    /**
     * 新增报表模板
     * 
     * @param reportTemplate 报表模板
     * @return 结果
     */
    int insertReportTemplate(ReportTemplate reportTemplate);

    /**
     * 修改报表模板
     * 
     * @param reportTemplate 报表模板
     * @return 结果
     */
    int updateReportTemplate(ReportTemplate reportTemplate);

    /**
     * 删除报表模板
     * 
     * @param templateId 报表模板ID
     * @return 结果
     */
    int deleteReportTemplateById(Long templateId);

    /**
     * 批量删除报表模板
     * 
     * @param templateIds 需要删除的报表模板ID
     * @return 结果
     */
    int deleteReportTemplateByIds(Long[] templateIds);

}
