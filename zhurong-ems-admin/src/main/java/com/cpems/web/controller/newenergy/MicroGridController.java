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
import com.ruoyi.system.domain.MicroGrid;
import com.ruoyi.system.service.IMicroGridService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 微电网Controller
 *
 * @author cpems
 * @date 2026-03-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/newenergy/microgrid")
public class MicroGridController extends BaseController {

    private final IMicroGridService microGridService;

    /**
     * 查询微电网列表
     */
    @GetMapping("/list")
    public TableDataInfo<MicroGrid> list(MicroGrid microGrid, PageQuery pageQuery) {
        LambdaQueryWrapper<MicroGrid> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(microGrid.getGridName()), MicroGrid::getGridName, microGrid.getGridName());
        lqw.like(StringUtils.isNotBlank(microGrid.getGridCode()), MicroGrid::getGridCode, microGrid.getGridCode());
        lqw.eq(StringUtils.isNotBlank(microGrid.getGridType()), MicroGrid::getGridType, microGrid.getGridType());
        lqw.eq(microGrid.getAreaId() != null, MicroGrid::getAreaId, microGrid.getAreaId());
        lqw.eq(StringUtils.isNotBlank(microGrid.getStatus()), MicroGrid::getStatus, microGrid.getStatus());
        lqw.eq(StringUtils.isNotBlank(microGrid.getRunMode()), MicroGrid::getRunMode, microGrid.getRunMode());
        lqw.orderByDesc(MicroGrid::getId);
        Page<MicroGrid> page = microGridService.page(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    /**
     * 获取微电网详细信息
     */
    @GetMapping("/{id}")
    public R<MicroGrid> getInfo(@PathVariable Long id) {
        return R.ok(microGridService.getById(id));
    }

    /**
     * 新增微电网
     */
    @PostMapping
    public R<Void> add(@RequestBody MicroGrid microGrid) {
        microGridService.save(microGrid);
        return R.ok();
    }

    /**
     * 修改微电网
     */
    @PutMapping
    public R<Void> edit(@RequestBody MicroGrid microGrid) {
        microGridService.updateById(microGrid);
        return R.ok();
    }

    /**
     * 删除微电网
     */
    @DeleteMapping("/{ids}")
    public R<Void> remove(@PathVariable Long[] ids) {
        microGridService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 导出微电网
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, MicroGrid microGrid) {
        List<MicroGrid> list = microGridService.list();
        ExcelUtil<MicroGrid> util = new ExcelUtil<MicroGrid>(MicroGrid.class);
        util.exportExcel(response, list, "微电网数据");
    }

    /**
     * 获取微电网统计数据
     */
    @GetMapping("/statistics")
    public R<Map<String, Object>> getStatistics() {
        return R.ok(microGridService.getStatistics());
    }

}
