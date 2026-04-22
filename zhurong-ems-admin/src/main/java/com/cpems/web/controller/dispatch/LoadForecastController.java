package com.cpems.web.controller.dispatch;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.LoadForecast;
import com.ruoyi.system.service.ILoadForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dispatch/loadForecast")
public class LoadForecastController extends BaseController {

    private final ILoadForecastService forecastService;

    @GetMapping("/list")
    public R<List<LoadForecast>> list() {
        return R.ok(forecastService.list());
    }

    @GetMapping("/{loadId}")
    public R<LoadForecast> getInfo(@PathVariable Long loadId) {
        return R.ok(forecastService.getById(loadId));
    }

    @PostMapping
    public R<Void> add(@RequestBody LoadForecast forecast) {
        forecastService.save(forecast);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody LoadForecast forecast) {
        forecastService.updateById(forecast);
        return R.ok();
    }

    @DeleteMapping("/{loadIds}")
    public R<Void> remove(@PathVariable Long[] loadIds) {
        forecastService.removeByIds(Arrays.asList(loadIds));
        return R.ok();
    }

    @PostMapping("/doForecast")
    public R<Double> doForecast(@RequestParam Integer forecastType, @RequestParam Integer loadType) {
        return R.ok(forecastService.doForecast(forecastType, loadType));
    }
}
