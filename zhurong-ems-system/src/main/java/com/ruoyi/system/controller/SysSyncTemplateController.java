package com.ruoyi.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.SysSyncTemplate;
import com.ruoyi.system.service.ISysSyncTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 同步模板控制器
 * @author cpems
 */
@Slf4j
@RestController
@RequestMapping("/system/integration/template")
@RequiredArgsConstructor
public class SysSyncTemplateController {

    private final ISysSyncTemplateService syncTemplateService;

    /**
     * 获取模板列表
     */
    @SaCheckPermission("system:template:list")
    @GetMapping("/list")
    public AjaxResult list(SysSyncTemplate template) {
        List<SysSyncTemplate> list = syncTemplateService.selectTemplateList(template);
        return AjaxResult.success(list);
    }

    /**
     * 获取模板详情
     */
    @SaCheckPermission("system:template:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(syncTemplateService.getById(id));
    }

    /**
     * 新增模板
     */
    @SaCheckPermission("system:template:add")
    @PostMapping
    public AjaxResult add(@RequestBody SysSyncTemplate template) {
        return AjaxResult.success(syncTemplateService.save(template));
    }

    /**
     * 修改模板
     */
    @SaCheckPermission("system:template:edit")
    @PutMapping
    public AjaxResult edit(@RequestBody SysSyncTemplate template) {
        return AjaxResult.success(syncTemplateService.updateById(template));
    }

    /**
     * 删除模板
     */
    @SaCheckPermission("system:template:remove")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(syncTemplateService.deleteByIds(ids));
    }

    /**
     * 根据模板类型查询模板
     */
    @SaCheckPermission("system:template:list")
    @GetMapping("/byType/{templateType}")
    public AjaxResult getByType(@PathVariable String templateType) {
        List<SysSyncTemplate> list = syncTemplateService.selectByTemplateType(templateType);
        return AjaxResult.success(list);
    }

    /**
     * 复制模板
     */
    @SaCheckPermission("system:template:add")
    @PostMapping("/copy")
    public AjaxResult copyTemplate(@RequestParam Long id, @RequestParam String newTemplateName, @RequestParam String newTemplateCode) {
        boolean result = syncTemplateService.copyTemplate(id, newTemplateName, newTemplateCode);
        return AjaxResult.success(result);
    }
}
