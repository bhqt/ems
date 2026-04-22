package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.SysSyncTemplate;
import com.ruoyi.system.mapper.SysSyncTemplateMapper;
import com.ruoyi.system.service.ISysSyncTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 同步模板服务实现
 * @author cpems
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysSyncTemplateServiceImpl extends ServiceImpl<SysSyncTemplateMapper, SysSyncTemplate> implements ISysSyncTemplateService {

    private final SysSyncTemplateMapper templateMapper;

    @Override
    public List<SysSyncTemplate> selectTemplateList(SysSyncTemplate template) {
        LambdaQueryWrapper<SysSyncTemplate> queryWrapper = new LambdaQueryWrapper<>();
        if (template.getTemplateName() != null) {
            queryWrapper.like(SysSyncTemplate::getTemplateName, template.getTemplateName());
        }
        if (template.getTemplateCode() != null) {
            queryWrapper.like(SysSyncTemplate::getTemplateCode, template.getTemplateCode());
        }
        if (template.getTemplateType() != null) {
            queryWrapper.eq(SysSyncTemplate::getTemplateType, template.getTemplateType());
        }
        if (template.getSourceSystem() != null) {
            queryWrapper.eq(SysSyncTemplate::getSourceSystem, template.getSourceSystem());
        }
        if (template.getTargetSystem() != null) {
            queryWrapper.eq(SysSyncTemplate::getTargetSystem, template.getTargetSystem());
        }
        if (template.getStatus() != null) {
            queryWrapper.eq(SysSyncTemplate::getStatus, template.getStatus());
        }
        queryWrapper.orderByDesc(SysSyncTemplate::getCreateTime);
        return list(queryWrapper);
    }

    @Override
    public boolean deleteByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids));
    }

    @Override
    public List<SysSyncTemplate> selectByTemplateType(String templateType) {
        LambdaQueryWrapper<SysSyncTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysSyncTemplate::getTemplateType, templateType);
        queryWrapper.eq(SysSyncTemplate::getStatus, 1);
        queryWrapper.orderByAsc(SysSyncTemplate::getTemplateName);
        return list(queryWrapper);
    }

    @Override
    public boolean copyTemplate(Long id, String newTemplateName, String newTemplateCode) {
        SysSyncTemplate originalTemplate = getById(id);
        if (originalTemplate != null) {
            SysSyncTemplate newTemplate = new SysSyncTemplate();
            newTemplate.setTemplateName(newTemplateName);
            newTemplate.setTemplateCode(newTemplateCode);
            newTemplate.setTemplateType(originalTemplate.getTemplateType());
            newTemplate.setSourceSystem(originalTemplate.getSourceSystem());
            newTemplate.setTargetSystem(originalTemplate.getTargetSystem());
            newTemplate.setFieldMapping(originalTemplate.getFieldMapping());
            newTemplate.setTransformRules(originalTemplate.getTransformRules());
            newTemplate.setDescription("复制自: " + originalTemplate.getTemplateName());
            newTemplate.setStatus(1);
            newTemplate.setCreateTime(new Date());
            newTemplate.setUpdateTime(new Date());
            return save(newTemplate);
        }
        return false;
    }
}
