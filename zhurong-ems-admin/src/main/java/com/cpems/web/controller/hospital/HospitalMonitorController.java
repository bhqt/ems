package com.cpems.web.controller.hospital;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.hospital.service.IHospitalMonitorService;
import com.ruoyi.system.hospital.vo.HospitalDeviceDataVo;
import com.ruoyi.system.hospital.vo.HospitalDeviceRealtimeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 医院设备实时监测
 *
 * @author cpems
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/monitor")
public class HospitalMonitorController extends BaseController {

    private final IHospitalMonitorService monitorService;

    /**
     * 设备实时监测总览
     */
    @SaCheckPermission("hospital:monitor:list")
    @GetMapping("/overview")
    public R<List<HospitalDeviceRealtimeVo>> overview(
        @RequestParam(required = false) String deviceType,
        @RequestParam(required = false) String keyword) {
        return R.ok(monitorService.queryOverview(deviceType, keyword));
    }

    /**
     * 单设备近期趋势数据点
     */
    @SaCheckPermission("hospital:monitor:list")
    @GetMapping("/trend")
    public R<List<HospitalDeviceDataVo>> trend(
        @RequestParam Long deviceId,
        @RequestParam(required = false) String metricCode,
        @RequestParam(defaultValue = "100") Integer limit) {
        return R.ok(monitorService.queryTrend(deviceId, metricCode, limit));
    }
}
