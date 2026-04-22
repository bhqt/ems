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
import com.ruoyi.system.domain.vo.CalibrationRecordVo;
import com.ruoyi.system.domain.bo.CalibrationRecordBo;
import com.ruoyi.system.service.ICalibrationRecordService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 校准记录Controller
 *
 * @author cpems
 * @date 2026-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/metering/record")
public class CalibrationRecordController extends BaseController {

    private final ICalibrationRecordService iCalibrationRecordService;

    /**
     * 查询校准记录列表
     */
    @SaCheckPermission("metering:record:list")
    @GetMapping("/list")
    public TableDataInfo<CalibrationRecordVo> list(CalibrationRecordBo bo, PageQuery pageQuery) {
        return iCalibrationRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出校准记录列表
     */
    @SaCheckPermission("metering:record:export")
    @Log(title = "校准记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CalibrationRecordBo bo, HttpServletResponse response) {
        List<CalibrationRecordVo> list = iCalibrationRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "校准记录", CalibrationRecordVo.class, response);
    }

    /**
     * 获取校准记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("metering:record:query")
    @GetMapping("/info/{id}")
    public R<CalibrationRecordVo> getInfo(@NotNull(message = "主键不能为空")
                                          @PathVariable Long id) {
        return R.ok(iCalibrationRecordService.queryById(id));
    }

    /**
     * 新增校准记录
     */
    @SaCheckPermission("metering:record:add")
    @Log(title = "校准记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CalibrationRecordBo bo) {
        return toAjax(iCalibrationRecordService.insertByBo(bo));
    }

    /**
     * 修改校准记录
     */
    @SaCheckPermission("metering:record:edit")
    @Log(title = "校准记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CalibrationRecordBo bo) {
        return toAjax(iCalibrationRecordService.updateByBo(bo));
    }

    /**
     * 删除校准记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("metering:record:remove")
    @Log(title = "校准记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iCalibrationRecordService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 查询校准结果统计
     */
    @SaCheckPermission("metering:record:list")
    @GetMapping("/statistics")
    public R<List<Map<String, Object>>> getStatistics() {
        return R.ok(iCalibrationRecordService.getCalibrationStatistics());
    }

    /**
     * 查询器具的校准历史
     *
     * @param meterId 器具ID
     */
    @SaCheckPermission("metering:record:query")
    @GetMapping("/history/{meterId}")
    public R<List<CalibrationRecordVo>> getMeterCalibrationHistory(@NotNull(message = "器具ID不能为空")
                                                                 @PathVariable Long meterId) {
        return R.ok(iCalibrationRecordService.getMeterCalibrationHistory(meterId));
    }
}
