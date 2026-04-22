package com.cpems.web.controller.dispatch;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.WeatherForecast;
import com.ruoyi.system.service.IWeatherForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dispatch/weatherForecast")
public class WeatherForecastController extends BaseController {

    private final IWeatherForecastService forecastService;

    @GetMapping("/list")
    public R<List<WeatherForecast>> list() {
        return R.ok(forecastService.list());
    }

    @GetMapping("/{weatherId}")
    public R<WeatherForecast> getInfo(@PathVariable Long weatherId) {
        return R.ok(forecastService.getById(weatherId));
    }

    @PostMapping
    public R<Void> add(@RequestBody WeatherForecast forecast) {
        forecastService.save(forecast);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody WeatherForecast forecast) {
        forecastService.updateById(forecast);
        return R.ok();
    }

    @DeleteMapping("/{weatherIds}")
    public R<Void> remove(@PathVariable Long[] weatherIds) {
        forecastService.removeByIds(Arrays.asList(weatherIds));
        return R.ok();
    }

    @PostMapping("/doForecast")
    public R<Double> doForecast() {
        return R.ok(forecastService.doForecast());
    }
}
