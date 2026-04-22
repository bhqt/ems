package com.cpems.web.controller.dispatch;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.ForecastModelConfig;
import com.ruoyi.system.service.IForecastModelConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dispatch/modelConfig")
public class ForecastModelConfigController extends BaseController {

    private final IForecastModelConfigService configService;

    @GetMapping("/list")
    public R<List<ForecastModelConfig>> list() {
        return R.ok(configService.list());
    }

    @GetMapping("/{configId}")
    public R<ForecastModelConfig> getInfo(@PathVariable Long configId) {
        return R.ok(configService.getById(configId));
    }

    @PostMapping
    public R<Void> add(@RequestBody ForecastModelConfig config) {
        configService.save(config);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody ForecastModelConfig config) {
        configService.updateById(config);
        return R.ok();
    }

    @DeleteMapping("/{configIds}")
    public R<Void> remove(@PathVariable Long[] configIds) {
        configService.removeByIds(Arrays.asList(configIds));
        return R.ok();
    }

    @PutMapping("/train/{configId}")
    public R<Void> train(@PathVariable Long configId) {
        configService.trainModel(configId);
        return R.ok();
    }
}
