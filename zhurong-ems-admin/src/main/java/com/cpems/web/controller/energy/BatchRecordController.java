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
import com.ruoyi.system.domain.vo.BatchRecordVo;
import com.ruoyi.system.domain.bo.BatchRecordBo;
import com.ruoyi.system.service.IBatchRecordService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 批次实绩Controller
 *
 * @author cpems
 * @date 2026-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/energy/batch")
public class BatchRecordController extends BaseController {

    private final IBatchRecordService iBatchRecordService;

    /**
     * 查询批次实绩列表
     */
    @SaCheckPermission("energy:batch:list")
    @GetMapping("/list")
    public TableDataInfo<BatchRecordVo> list(BatchRecordBo bo, PageQuery pageQuery) {
        return iBatchRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出批次实绩列表
     */
    @SaCheckPermission("energy:batch:export")
    @Log(title = "批次实绩", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(BatchRecordBo bo, HttpServletResponse response) {
        List<BatchRecordVo> list = iBatchRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "批次实绩", BatchRecordVo.class, response);
    }

    /**
     * 获取批次实绩详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("energy:batch:query")
    @GetMapping("/info/{id}")
    public R<BatchRecordVo> getInfo(@NotNull(message = "主键不能为空")
                                    @PathVariable Long id) {
        return R.ok(iBatchRecordService.queryById(id));
    }

    /**
     * 新增批次实绩
     */
    @SaCheckPermission("energy:batch:add")
    @Log(title = "批次实绩", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody BatchRecordBo bo) {
        return toAjax(iBatchRecordService.insertByBo(bo));
    }

    /**
     * 修改批次实绩
     */
    @SaCheckPermission("energy:batch:edit")
    @Log(title = "批次实绩", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BatchRecordBo bo) {
        return toAjax(iBatchRecordService.updateByBo(bo));
    }

    /**
     * 删除批次实绩
     *
     * @param ids 主键串
     */
    @SaCheckPermission("energy:batch:remove")
    @Log(title = "批次实绩", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iBatchRecordService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 查询批次状态统计
     */
    @SaCheckPermission("energy:batch:list")
    @GetMapping("/statistics")
    public R<List<Map<String, Object>>> getStatistics() {
        return R.ok(iBatchRecordService.getBatchStatistics());
    }

    /**
     * 完成批次
     *
     * @param batchId 批次ID
     */
    @SaCheckPermission("energy:batch:edit")
    @Log(title = "批次实绩", businessType = BusinessType.UPDATE)
    @PostMapping("/complete")
    public R<Void> completeBatch(@NotNull(message = "批次ID不能为空") @RequestParam Long batchId) {
        return toAjax(iBatchRecordService.completeBatch(batchId));
    }

    /**
     * 取消批次
     *
     * @param batchId 批次ID
     */
    @SaCheckPermission("energy:batch:edit")
    @Log(title = "批次实绩", businessType = BusinessType.UPDATE)
    @PostMapping("/cancel")
    public R<Void> cancelBatch(@NotNull(message = "批次ID不能为空") @RequestParam Long batchId) {
        return toAjax(iBatchRecordService.cancelBatch(batchId));
    }

    /**
     * 计算批次能耗指标
     *
     * @param batchId 批次ID
     */
    @SaCheckPermission("energy:batch:query")
    @GetMapping("/indicators/{batchId}")
    public R<Map<String, Object>> calculateEnergyIndicators(@NotNull(message = "批次ID不能为空")
                                                          @PathVariable Long batchId) {
        return R.ok(iBatchRecordService.calculateEnergyIndicators(batchId));
    }
}
