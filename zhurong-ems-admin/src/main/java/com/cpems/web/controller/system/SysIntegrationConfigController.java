package com.cpems.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.SysIntegrationConfig;
import com.ruoyi.system.service.ISysIntegrationConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/integration/config")
public class SysIntegrationConfigController extends BaseController {

    private final ISysIntegrationConfigService integrationConfigService;

    @GetMapping("/list")
    public R<List<SysIntegrationConfig>> list(SysIntegrationConfig config) {
        List<SysIntegrationConfig> list = integrationConfigService.list();
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R<SysIntegrationConfig> getInfo(@PathVariable Long id) {
        return R.ok(integrationConfigService.getById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody SysIntegrationConfig config) {
        integrationConfigService.save(config);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody SysIntegrationConfig config) {
        integrationConfigService.updateById(config);
        return R.ok();
    }

    @DeleteMapping("/{ids}")
    public R<Void> remove(@PathVariable Long[] ids) {
        integrationConfigService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @PutMapping("/enable/{id}")
    public R<Void> enable(@PathVariable Long id) {
        integrationConfigService.enable(id);
        return R.ok();
    }

    @PutMapping("/disable/{id}")
    public R<Void> disable(@PathVariable Long id) {
        integrationConfigService.disable(id);
        return R.ok();
    }

    @GetMapping("/byCode/{systemCode}")
    public R<SysIntegrationConfig> getByCode(@PathVariable String systemCode) {
        return R.ok(integrationConfigService.getBySystemCode(systemCode));
    }
}
