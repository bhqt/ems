package com.cpems.web.controller.dispatch;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.EnergyPriceForecast;
import com.ruoyi.system.service.IEnergyPriceForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dispatch/priceForecast")
public class EnergyPriceForecastController extends BaseController {

    private final IEnergyPriceForecastService forecastService;

    @GetMapping("/list")
    public R<List<EnergyPriceForecast>> list() {
        return R.ok(forecastService.list());
    }

    @GetMapping("/{priceId}")
    public R<EnergyPriceForecast> getInfo(@PathVariable Long priceId) {
        return R.ok(forecastService.getById(priceId));
    }

    @PostMapping
    public R<Void> add(@RequestBody EnergyPriceForecast forecast) {
        forecastService.save(forecast);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody EnergyPriceForecast forecast) {
        forecastService.updateById(forecast);
        return R.ok();
    }

    @DeleteMapping("/{priceIds}")
    public R<Void> remove(@PathVariable Long[] priceIds) {
        forecastService.removeByIds(Arrays.asList(priceIds));
        return R.ok();
    }

    @PostMapping("/doForecast")
    public R<Double> doForecast(@RequestParam Integer energyType, @RequestParam String priceType) {
        return R.ok(forecastService.doForecast(energyType, priceType));
    }
}
