package com.cpems.web.controller.energy;

import java.util.List;
import java.util.Arrays;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.vo.EnergyBalanceVo;
import com.ruoyi.system.domain.bo.EnergyBalanceBo;
import com.ruoyi.system.service.IEnergyBalanceService;
import com.ruoyi.common.core.page.TableDataInfo;
import cn.hutool.core.bean.BeanUtil;

/**
 * 能源平衡Controller
 *
 * @author cpems
 * @date 2026-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/energy/balance")
public class EnergyBalanceController extends BaseController {

    private final IEnergyBalanceService iEnergyBalanceService;

    /**
     * 查询能源平衡列表
     */
    @SaCheckPermission("energy:balance:list")
    @GetMapping("/list")
    public TableDataInfo<EnergyBalanceVo> list(EnergyBalanceBo bo, PageQuery pageQuery) {
        return iEnergyBalanceService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出能源平衡列表
     */
    @SaCheckPermission("energy:balance:export")
    @Log(title = "能源平衡", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(EnergyBalanceBo bo, HttpServletResponse response) {
        List<EnergyBalanceVo> list = iEnergyBalanceService.queryList(bo);
        ExcelUtil.exportExcel(list, "能源平衡", EnergyBalanceVo.class, response);
    }

    /**
     * 获取能源平衡详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("energy:balance:query")
    @GetMapping("/info/{id}")
    public R<EnergyBalanceVo> getInfo(@NotNull(message = "主键不能为空")
                                      @PathVariable Long id) {
        return R.ok(iEnergyBalanceService.queryById(id));
    }

    /**
     * 新增能源平衡
     */
    @SaCheckPermission("energy:balance:add")
    @Log(title = "能源平衡", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody EnergyBalanceBo bo) {
        return toAjax(iEnergyBalanceService.insertByBo(bo));
    }

    /**
     * 修改能源平衡
     */
    @SaCheckPermission("energy:balance:edit")
    @Log(title = "能源平衡", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody EnergyBalanceBo bo) {
        return toAjax(iEnergyBalanceService.updateByBo(bo));
    }

    /**
     * 删除能源平衡
     *
     * @param ids 主键串
     */
    @SaCheckPermission("energy:balance:remove")
    @Log(title = "能源平衡", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iEnergyBalanceService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 计算能源平衡
     *
     * @param bo 能源平衡
     */
    @SaCheckPermission("energy:balance:query")
    @PostMapping("/calculate")
    public R<EnergyBalanceVo> calculateBalance(@Validated @RequestBody EnergyBalanceBo bo) {
        return R.ok(BeanUtil.toBean(iEnergyBalanceService.calculateBalance(bo), EnergyBalanceVo.class));
    }

    /**
     * 查询能源介质统计
     */
    @SaCheckPermission("energy:balance:list")
    @GetMapping("/mediumStatistics")
    public R<List<Map<String, Object>>> getMediumStatistics() {
        return R.ok(iEnergyBalanceService.getMediumStatistics());
    }

    /**
     * 查询平衡状态统计
     */
    @SaCheckPermission("energy:balance:list")
    @GetMapping("/statusStatistics")
    public R<List<Map<String, Object>>> getStatusStatistics() {
        return R.ok(iEnergyBalanceService.getStatusStatistics());
    }
}
