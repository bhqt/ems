package com.cpems.web.controller.dispatch;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.EnergyDemandForecast;
import com.ruoyi.system.service.IEnergyDemandForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dispatch/forecast")
public class EnergyDemandForecastController extends BaseController {

    private final IEnergyDemandForecastService forecastService;

    @GetMapping("/list")
    public R<List<EnergyDemandForecast>> list(EnergyDemandForecast forecast) {
        List<EnergyDemandForecast> list = forecastService.list();
        return R.ok(list);
    }

    @GetMapping("/{forecastId}")
    public R<EnergyDemandForecast> getInfo(@PathVariable Long forecastId) {
        return R.ok(forecastService.getById(forecastId));
    }

    @PostMapping
    public R<Void> add(@RequestBody EnergyDemandForecast forecast) {
        forecastService.save(forecast);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody EnergyDemandForecast forecast) {
        forecastService.updateById(forecast);
        return R.ok();
    }

    @DeleteMapping("/{forecastIds}")
    public R<Void> remove(@PathVariable Long[] forecastIds) {
        forecastService.removeByIds(Arrays.asList(forecastIds));
        return R.ok();
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, EnergyDemandForecast forecast) {
        List<EnergyDemandForecast> list = forecastService.list();
        ExcelUtil<EnergyDemandForecast> util = new ExcelUtil<>(EnergyDemandForecast.class);
        util.exportExcel(response, list, "能源需求预测数据");
    }
}
