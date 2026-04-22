package com.cpems.web.controller.newenergy;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.PvStation;
import com.ruoyi.system.service.IPvStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 光伏电站Controller
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/newenergy/pv/station")
public class PvStationController extends BaseController {

    private final IPvStationService pvStationService;

    /**
     * 查询光伏电站列表
     */
    @GetMapping("/list")
    public R<List<PvStation>> list(PvStation pvStation) {
        List<PvStation> list = pvStationService.list();
        return R.ok(list);
    }

    /**
     * 获取光伏电站详细信息
     */
    @GetMapping("/{id}")
    public R<PvStation> getInfo(@PathVariable Long id) {
        return R.ok(pvStationService.getById(id));
    }

    /**
     * 新增光伏电站
     */
    @PostMapping
    public R<Void> add(@RequestBody PvStation pvStation) {
        pvStationService.save(pvStation);
        return R.ok();
    }

    /**
     * 修改光伏电站
     */
    @PutMapping
    public R<Void> edit(@RequestBody PvStation pvStation) {
        pvStationService.updateById(pvStation);
        return R.ok();
    }

    /**
     * 删除光伏电站
     */
    @DeleteMapping("/{stationIds}")
    public R<Void> remove(@PathVariable Long[] stationIds) {
        pvStationService.removeByIds(Arrays.asList(stationIds));
        return R.ok();
    }

    /**
     * 导出光伏电站
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, PvStation pvStation) {
        List<PvStation> list = pvStationService.list();
        ExcelUtil<PvStation> util = new ExcelUtil<PvStation>(PvStation.class);
        util.exportExcel(response, list, "光伏电站数据");
    }

    /**
     * 获取光伏电站统计数据
     */
    @GetMapping("/statistics")
    public R<java.util.Map<String, Object>> getStatistics() {
        return R.ok(pvStationService.getStatistics());
    }
}
