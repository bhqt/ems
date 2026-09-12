package com.cpems.web.controller.newenergy;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
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
     * 查询光伏电站列表（分页，与前端 rows/total 约定对齐）
     */
    @GetMapping("/list")
    public TableDataInfo<PvStation> list(PvStation pvStation, PageQuery pageQuery) {
        LambdaQueryWrapper<PvStation> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(pvStation.getStationName()), PvStation::getStationName, pvStation.getStationName());
        lqw.like(StringUtils.isNotBlank(pvStation.getStationCode()), PvStation::getStationCode, pvStation.getStationCode());
        lqw.eq(StringUtils.isNotBlank(pvStation.getStationType()), PvStation::getStationType, pvStation.getStationType());
        lqw.eq(pvStation.getAreaId() != null, PvStation::getAreaId, pvStation.getAreaId());
        lqw.eq(StringUtils.isNotBlank(pvStation.getStatus()), PvStation::getStatus, pvStation.getStatus());
        lqw.orderByDesc(PvStation::getId);
        Page<PvStation> page = pvStationService.page(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
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
