package com.cpems.web.controller.newenergy;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.MicroGrid;
import com.ruoyi.system.service.IMicroGridService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

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
    public R<List<MicroGrid>> list(MicroGrid microGrid) {
        List<MicroGrid> list = microGridService.list();
        return R.ok(list);
    }

    /**
     * 获取微电网详细信息
     */
    @GetMapping("/{gridId}")
    public R<MicroGrid> getInfo(@PathVariable Long gridId) {
        return R.ok(microGridService.getById(gridId));
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
    @DeleteMapping("/{gridIds}")
    public R<Void> remove(@PathVariable Long[] gridIds) {
        microGridService.removeByIds(Arrays.asList(gridIds));
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
}
