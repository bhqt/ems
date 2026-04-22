package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysSyncTemplate;

import java.util.List;

/**
 * 同步模板服务接口
 * @author cpems
 */
public interface ISysSyncTemplateService extends IService<SysSyncTemplate> {

    /**
     * 查询模板列表
     * @param template 模板信息
     * @return 模板列表
     */
    List<SysSyncTemplate> selectTemplateList(SysSyncTemplate template);

    /**
     * 批量删除模板
     * @param ids 模板ID列表
     * @return 结果
     */
    boolean deleteByIds(Long[] ids);

    /**
     * 根据模板类型查询模板
     * @param templateType 模板类型
     * @return 模板列表
     */
    List<SysSyncTemplate> selectByTemplateType(String templateType);

    /**
     * 复制模板
     * @param id 模板ID
     * @param newTemplateName 新模板名称
     * @param newTemplateCode 新模板编码
     * @return 结果
     */
    boolean copyTemplate(Long id, String newTemplateName, String newTemplateCode);
}
