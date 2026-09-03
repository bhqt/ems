package com.cpems.web.controller.hospital;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.hospital.energy.HospitalDeviceRankVo;
import com.ruoyi.system.hospital.energy.HospitalEfficiencyVo;
import com.ruoyi.system.hospital.energy.HospitalEnergyOverviewVo;
import com.ruoyi.system.hospital.energy.HospitalEnergyTrendVo;
import com.ruoyi.system.hospital.energy.HospitalSuggestionVo;
import com.ruoyi.system.hospital.service.IHospitalEnergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 医院能耗分析与决策支持
 *
 * @author cpems
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/energy")
public class HospitalEnergyController extends BaseController {

    private final IHospitalEnergyService energyService;

    /**
     * 全院能耗概览（院区/科室/设备钻取，附环比）
     */
    @SaCheckPermission("hospital:energy:list")
    @GetMapping("/overview")
    public R<List<HospitalEnergyOverviewVo>> overview(
        @RequestParam(defaultValue = "AREA") String level,
        @RequestParam(required = false) String startTime,
        @RequestParam(required = false) String endTime,
        @RequestParam(required = false) String deviceType) {
        return R.ok(energyService.overview(level, startTime, endTime, deviceType));
    }

    /**
     * 能耗趋势（DAY 按天用电量 / HOUR 按小时平均功率）
     */
    @SaCheckPermission("hospital:energy:list")
    @GetMapping("/trend")
    public R<List<HospitalEnergyTrendVo>> trend(
        @RequestParam(required = false) Long deviceId,
        @RequestParam(defaultValue = "DAY") String granularity,
        @RequestParam(required = false) String startTime,
        @RequestParam(required = false) String endTime) {
        return R.ok(energyService.trend(deviceId, granularity, startTime, endTime));
    }

    /**
     * 设备耗电排名
     */
    @SaCheckPermission("hospital:energy:list")
    @GetMapping("/rank")
    public R<List<HospitalDeviceRankVo>> rank(
        @RequestParam(required = false) String startTime,
        @RequestParam(required = false) String endTime,
        @RequestParam(defaultValue = "10") Integer limit) {
        return R.ok(energyService.rank(startTime, endTime, limit));
    }

    /**
     * 设备能效评估
     */
    @SaCheckPermission("hospital:energy:list")
    @GetMapping("/efficiency")
    public R<List<HospitalEfficiencyVo>> efficiency(
        @RequestParam(required = false) String startTime,
        @RequestParam(required = false) String endTime) {
        return R.ok(energyService.efficiency(startTime, endTime));
    }

    /**
     * 节能建议清单
     */
    @SaCheckPermission("hospital:energy:list")
    @GetMapping("/suggestions")
    public R<List<HospitalSuggestionVo>> suggestions(
        @RequestParam(required = false) String startTime,
        @RequestParam(required = false) String endTime) {
        return R.ok(energyService.suggestions(startTime, endTime));
    }

    /**
     * 导出节能建议清单（分析报告）
     */
    @SaCheckPermission("hospital:energy:export")
    @Log(title = "医院节能建议", businessType = BusinessType.EXPORT)
    @GetMapping("/suggestions/export")
    public void exportSuggestions(@RequestParam(required = false) String startTime,
                                  @RequestParam(required = false) String endTime,
                                  HttpServletResponse response) {
        List<HospitalSuggestionVo> list = energyService.suggestions(startTime, endTime);
        ExcelUtil.exportExcel(list, "节能建议清单", HospitalSuggestionVo.class, response);
    }

    /**
     * 导出设备能效评估（分析报告）
     */
    @SaCheckPermission("hospital:energy:export")
    @Log(title = "医院能效评估", businessType = BusinessType.EXPORT)
    @GetMapping("/efficiency/export")
    public void exportEfficiency(@RequestParam(required = false) String startTime,
                                 @RequestParam(required = false) String endTime,
                                 HttpServletResponse response) {
        List<HospitalEfficiencyVo> list = energyService.efficiency(startTime, endTime);
        ExcelUtil.exportExcel(list, "设备能效评估", HospitalEfficiencyVo.class, response);
    }
}
