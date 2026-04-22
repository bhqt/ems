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
import com.ruoyi.system.domain.vo.BenchmarkStandardVo;
import com.ruoyi.system.domain.bo.BenchmarkStandardBo;
import com.ruoyi.system.service.IBenchmarkStandardService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 标杆标准Controller
 *
 * @author cpems
 * @date 2026-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/energy/benchmark")
public class BenchmarkStandardController extends BaseController {

    private final IBenchmarkStandardService iBenchmarkStandardService;

    /**
     * 查询标杆标准列表
     */
    @SaCheckPermission("energy:benchmark:list")
    @GetMapping("/list")
    public TableDataInfo<BenchmarkStandardVo> list(BenchmarkStandardBo bo, PageQuery pageQuery) {
        return iBenchmarkStandardService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出标杆标准列表
     */
    @SaCheckPermission("energy:benchmark:export")
    @Log(title = "标杆标准", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(BenchmarkStandardBo bo, HttpServletResponse response) {
        List<BenchmarkStandardVo> list = iBenchmarkStandardService.queryList(bo);
        ExcelUtil.exportExcel(list, "标杆标准", BenchmarkStandardVo.class, response);
    }

    /**
     * 获取标杆标准详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("energy:benchmark:query")
    @GetMapping("/info/{id}")
    public R<BenchmarkStandardVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathVariable Long id) {
        return R.ok(iBenchmarkStandardService.queryById(id));
    }

    /**
     * 新增标杆标准
     */
    @SaCheckPermission("energy:benchmark:add")
    @Log(title = "标杆标准", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody BenchmarkStandardBo bo) {
        return toAjax(iBenchmarkStandardService.insertByBo(bo));
    }

    /**
     * 修改标杆标准
     */
    @SaCheckPermission("energy:benchmark:edit")
    @Log(title = "标杆标准", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BenchmarkStandardBo bo) {
        return toAjax(iBenchmarkStandardService.updateByBo(bo));
    }

    /**
     * 删除标杆标准
     *
     * @param ids 主键串
     */
    @SaCheckPermission("energy:benchmark:remove")
    @Log(title = "标杆标准", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iBenchmarkStandardService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 查询标准类型统计
     */
    @SaCheckPermission("energy:benchmark:list")
    @GetMapping("/statistics")
    public R<List<Map<String, Object>>> getStatistics() {
        return R.ok(iBenchmarkStandardService.getStandardStatistics());
    }

    /**
     * 启用标准
     *
     * @param standardId 标准ID
     */
    @SaCheckPermission("energy:benchmark:edit")
    @Log(title = "标杆标准", businessType = BusinessType.UPDATE)
    @PostMapping("/activate")
    public R<Void> activateStandard(@NotNull(message = "标准ID不能为空") @RequestParam Long standardId) {
        return toAjax(iBenchmarkStandardService.activateStandard(standardId));
    }

    /**
     * 停用标准
     *
     * @param standardId 标准ID
     */
    @SaCheckPermission("energy:benchmark:edit")
    @Log(title = "标杆标准", businessType = BusinessType.UPDATE)
    @PostMapping("/deactivate")
    public R<Void> deactivateStandard(@NotNull(message = "标准ID不能为空") @RequestParam Long standardId) {
        return toAjax(iBenchmarkStandardService.deactivateStandard(standardId));
    }

    /**
     * 获取有效标准
     */
    @SaCheckPermission("energy:benchmark:list")
    @GetMapping("/active")
    public R<List<BenchmarkStandardVo>> getActiveStandards() {
        return R.ok(iBenchmarkStandardService.getActiveStandards());
    }
}
