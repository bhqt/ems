package com.cpems.web.controller.chargingStation;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.ChargingPile;
import com.ruoyi.system.domain.bo.ChargingPileBo;
import com.ruoyi.system.domain.vo.ChargingPileVo;
import com.ruoyi.system.service.IChargingPileService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 充电桩管理Controller
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/chargingStation/pile")
public class ChargingPileController extends BaseController {

    private final IChargingPileService chargingPileService;

    /**
     * 查询充电桩列表
     */
    @SaCheckPermission("chargingStation:pile:list")
    @GetMapping("/list")
    public TableDataInfo list(ChargingPile chargingPile, PageQuery pageQuery) {
        IPage<ChargingPileVo> page = chargingPileService.selectChargingPilePage(chargingPile, pageQuery);
        return TableDataInfo.build(page);
    }

    /**
     * 获取充电桩详情
     */
    @SaCheckPermission("chargingStation:pile:query")
    @GetMapping("/info/{pileId}")
    public R<ChargingPileVo> info(@NotNull(message = "主键不能为空") @PathVariable Long pileId) {
        return R.ok(chargingPileService.selectChargingPileById(pileId));
    }

    /**
     * 新增充电桩
     */
    @SaCheckPermission("chargingStation:pile:add")
    @PostMapping("/add")
    public R<Integer> add(@RequestBody ChargingPileBo bo) {
        return R.ok(chargingPileService.insertChargingPile(bo));
    }

    /**
     * 修改充电桩
     */
    @SaCheckPermission("chargingStation:pile:edit")
    @PutMapping("/edit")
    public R<Integer> edit(@RequestBody ChargingPileBo bo) {
        return R.ok(chargingPileService.updateChargingPile(bo));
    }

    /**
     * 删除充电桩
     */
    @SaCheckPermission("chargingStation:pile:remove")
    @DeleteMapping("/remove/{pileIds}")
    public R<Integer> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] pileIds) {
        return R.ok(chargingPileService.deleteChargingPileByIds(pileIds));
    }

    /**
     * 启用/停用充电桩
     */
    @SaCheckPermission("chargingStation:pile:edit")
    @PutMapping("/openOrClose")
    public R<Integer> openOrClose(@NotNull(message = "主键不能为空") @RequestParam Long pileId, @NotNull(message = "状态不能为空") @RequestParam String status) {
        return R.ok(chargingPileService.openOrClosePile(pileId, status));
    }

    /**
     * 导出充电桩列表
     */
    @SaCheckPermission("chargingStation:pile:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChargingPile chargingPile) {
        List<ChargingPileVo> list = chargingPileService.exportChargingPileList(chargingPile);
        ExcelUtil<ChargingPileVo> util = new ExcelUtil<ChargingPileVo>(ChargingPileVo.class);
        util.exportExcel(response, list, "充电桩信息");
    }

    /**
     * 获取充电桩统计信息
     */
    @SaCheckPermission("chargingStation:pile:query")
    @GetMapping("/statistics")
    public R<ChargingPileVo> statistics() {
        return R.ok(chargingPileService.getChargingPileStatistics());
    }
}
