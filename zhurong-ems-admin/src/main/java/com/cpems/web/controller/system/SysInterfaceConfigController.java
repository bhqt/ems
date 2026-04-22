package com.cpems.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.SysInterfaceConfig;
import com.ruoyi.system.service.ISysInterfaceConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/integration/interface")
public class SysInterfaceConfigController extends BaseController {

    private final ISysInterfaceConfigService interfaceConfigService;

    @GetMapping("/list")
    public R<List<SysInterfaceConfig>> list(SysInterfaceConfig config) {
        List<SysInterfaceConfig> list = interfaceConfigService.list();
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R<SysInterfaceConfig> getInfo(@PathVariable Long id) {
        return R.ok(interfaceConfigService.getById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody SysInterfaceConfig config) {
        interfaceConfigService.save(config);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody SysInterfaceConfig config) {
        interfaceConfigService.updateById(config);
        return R.ok();
    }

    @DeleteMapping("/{ids}")
    public R<Void> remove(@PathVariable Long[] ids) {
        interfaceConfigService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @GetMapping("/byConfigId/{configId}")
    public R<List<SysInterfaceConfig>> getByConfigId(@PathVariable Long configId) {
        return R.ok(interfaceConfigService.getByConfigId(configId));
    }

    @GetMapping("/test/{id}")
    public R<Boolean> testConnection(@PathVariable Long id) {
        return R.ok(interfaceConfigService.testConnection(id));
    }
}
