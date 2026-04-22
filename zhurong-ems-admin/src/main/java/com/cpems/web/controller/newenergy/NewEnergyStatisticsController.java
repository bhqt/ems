package com.cpems.web.controller.newenergy;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.vo.ReportDataVo;
import com.ruoyi.system.service.INewEnergyStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 新能源统计报表Controller
 * 
 * @author cpems
 * @date 2026-03-27
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/newenergy/statistics")
public class NewEnergyStatisticsController extends BaseController {

    private final INewEnergyStatisticsService newEnergyStatisticsService;

    /**
     * 获取发电量统计
     */
    @GetMapping("/generation")
    public R<ReportDataVo> getGenerationStatistics(@RequestParam String startTime, 
                                                   @RequestParam String endTime, 
                                                   @RequestParam(required = false) String stationId) {
        ReportDataVo reportData = newEnergyStatisticsService.getGenerationStatistics(startTime, endTime, stationId);
        return R.ok(reportData);
    }

    /**
     * 获取储能统计
     */
    @GetMapping("/storage")
    public R<ReportDataVo> getStorageStatistics(@RequestParam String startTime, 
                                                @RequestParam String endTime, 
                                                @RequestParam(required = false) String systemId) {
        ReportDataVo reportData = newEnergyStatisticsService.getStorageStatistics(startTime, endTime, systemId);
        return R.ok(reportData);
    }

    /**
     * 获取微电网统计
     */
    @GetMapping("/microgrid")
    public R<ReportDataVo> getMicroGridStatistics(@RequestParam String startTime, 
                                                  @RequestParam String endTime, 
                                                  @RequestParam(required = false) String gridId) {
        ReportDataVo reportData = newEnergyStatisticsService.getMicroGridStatistics(startTime, endTime, gridId);
        return R.ok(reportData);
    }

    /**
     * 获取虚拟电厂统计
     */
    @GetMapping("/virtualplant")
    public R<ReportDataVo> getVirtualPlantStatistics(@RequestParam String startTime, 
                                                     @RequestParam String endTime, 
                                                     @RequestParam(required = false) String plantId) {
        ReportDataVo reportData = newEnergyStatisticsService.getVirtualPlantStatistics(startTime, endTime, plantId);
        return R.ok(reportData);
    }
}
