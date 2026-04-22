package com.cpems.web.controller.chargingStation;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.ChargingBrand;
import com.ruoyi.system.domain.bo.ChargingBrandBo;
import com.ruoyi.system.domain.vo.ChargingBrandVo;
import com.ruoyi.system.service.IChargingBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 充电站品牌管理Controller
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/chargingStation/brand")
public class ChargingBrandController extends BaseController {

    private final IChargingBrandService chargingBrandService;

    /**
     * 查询品牌列表
     */
    @SaCheckPermission("chargingStation:brand:list")
    @GetMapping("/list")
    public TableDataInfo list(ChargingBrand chargingBrand, PageQuery pageQuery) {
        IPage<ChargingBrandVo> page = chargingBrandService.selectChargingBrandPage(chargingBrand, pageQuery);
        return TableDataInfo.build(page);
    }

    /**
     * 获取品牌详情
     */
    @SaCheckPermission("chargingStation:brand:query")
    @GetMapping("/info/{id}")
    public R<ChargingBrandVo> info(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(chargingBrandService.selectChargingBrandById(id));
    }

    /**
     * 新增品牌
     */
    @SaCheckPermission("chargingStation:brand:add")
    @PostMapping("/add")
    public R<Integer> add(@RequestBody ChargingBrandBo bo) {
        return R.ok(chargingBrandService.insertChargingBrand(bo));
    }

    /**
     * 修改品牌
     */
    @SaCheckPermission("chargingStation:brand:edit")
    @PutMapping("/edit")
    public R<Integer> edit(@RequestBody ChargingBrandBo bo) {
        return R.ok(chargingBrandService.updateChargingBrand(bo));
    }

    /**
     * 删除品牌
     */
    @SaCheckPermission("chargingStation:brand:remove")
    @DeleteMapping("/remove/{ids}")
    public R<Integer> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return R.ok(chargingBrandService.deleteChargingBrandByIds(ids));
    }

    /**
     * 导出品牌列表
     */
    @SaCheckPermission("chargingStation:brand:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChargingBrand chargingBrand) {
        List<ChargingBrandVo> list = chargingBrandService.exportChargingBrandList(chargingBrand);
        ExcelUtil<ChargingBrandVo> util = new ExcelUtil<ChargingBrandVo>(ChargingBrandVo.class);
        util.exportExcel(response, list, "品牌信息");
    }

    /**
     * 获取品牌统计信息
     */
    @SaCheckPermission("chargingStation:brand:query")
    @GetMapping("/statistics")
    public R<ChargingBrandVo> statistics() {
        return R.ok(chargingBrandService.getChargingBrandStatistics());
    }

}
