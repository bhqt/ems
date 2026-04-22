package com.cpems.web.controller.metering;

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
import com.ruoyi.system.domain.vo.CalibrationPlanVo;
import com.ruoyi.system.domain.bo.CalibrationPlanBo;
import com.ruoyi.system.service.ICalibrationPlanService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 校准计划Controller
 *
 * @author cpems
 * @date 2026-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/metering/plan")
public class CalibrationPlanController extends BaseController {

    private final ICalibrationPlanService iCalibrationPlanService;

    /**
     * 查询校准计划列表
     */
    @SaCheckPermission("metering:plan:list")
    @GetMapping("/list")
    public TableDataInfo<CalibrationPlanVo> list(CalibrationPlanBo bo, PageQuery pageQuery) {
        return iCalibrationPlanService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出校准计划列表
     */
    @SaCheckPermission("metering:plan:export")
    @Log(title = "校准计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CalibrationPlanBo bo, HttpServletResponse response) {
        List<CalibrationPlanVo> list = iCalibrationPlanService.queryList(bo);
        ExcelUtil.exportExcel(list, "校准计划", CalibrationPlanVo.class, response);
    }

    /**
     * 获取校准计划详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("metering:plan:query")
    @GetMapping("/info/{id}")
    public R<CalibrationPlanVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathVariable Long id) {
        return R.ok(iCalibrationPlanService.queryById(id));
    }

    /**
     * 新增校准计划
     */
    @SaCheckPermission("metering:plan:add")
    @Log(title = "校准计划", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CalibrationPlanBo bo) {
        return toAjax(iCalibrationPlanService.insertByBo(bo));
    }

    /**
     * 修改校准计划
     */
    @SaCheckPermission("metering:plan:edit")
    @Log(title = "校准计划", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CalibrationPlanBo bo) {
        return toAjax(iCalibrationPlanService.updateByBo(bo));
    }

    /**
     * 删除校准计划
     *
     * @param ids 主键串
     */
    @SaCheckPermission("metering:plan:remove")
    @Log(title = "校准计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                           @PathVariable Long[] ids) {
        return toAjax(iCalibrationPlanService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 查询计划状态统计
     */
    @SaCheckPermission("metering:plan:list")
    @GetMapping("/statistics")
    public R<List<Map<String, Object>>> getStatistics() {
        return R.ok(iCalibrationPlanService.getPlanStatistics());
    }

    /**
     * 启动校准计划
     *
     * @param planId 计划ID
     */
    @SaCheckPermission("metering:plan:edit")
    @Log(title = "校准计划", businessType = BusinessType.UPDATE)
    @PostMapping("/start")
    public R<Void> startPlan(@NotNull(message = "计划ID不能为空") @RequestParam Long planId) {
        return toAjax(iCalibrationPlanService.startPlan(planId));
    }

    /**
     * 完成校准计划
     *
     * @param planId 计划ID
     */
    @SaCheckPermission("metering:plan:edit")
    @Log(title = "校准计划", businessType = BusinessType.UPDATE)
    @PostMapping("/complete")
    public R<Void> completePlan(@NotNull(message = "计划ID不能为空") @RequestParam Long planId) {
        return toAjax(iCalibrationPlanService.completePlan(planId));
    }

    /**
     * 取消校准计划
     *
     * @param planId 计划ID
     */
    @SaCheckPermission("metering:plan:edit")
    @Log(title = "校准计划", businessType = BusinessType.UPDATE)
    @PostMapping("/cancel")
    public R<Void> cancelPlan(@NotNull(message = "计划ID不能为空") @RequestParam Long planId) {
        return toAjax(iCalibrationPlanService.cancelPlan(planId));
    }
}
