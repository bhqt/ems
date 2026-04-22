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
import com.ruoyi.system.domain.vo.MeterInfoVo;
import com.ruoyi.system.domain.bo.MeterInfoBo;
import com.ruoyi.system.service.IMeterInfoService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 计量器具信息Controller
 *
 * @author cpems
 * @date 2026-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/metering/info")
public class MeterInfoController extends BaseController {

    private final IMeterInfoService iMeterInfoService;

    /**
     * 查询计量器具信息列表
     */
    @SaCheckPermission("metering:info:list")
    @GetMapping("/list")
    public TableDataInfo<MeterInfoVo> list(MeterInfoBo bo, PageQuery pageQuery) {
        return iMeterInfoService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出计量器具信息列表
     */
    @SaCheckPermission("metering:info:export")
    @Log(title = "计量器具信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MeterInfoBo bo, HttpServletResponse response) {
        List<MeterInfoVo> list = iMeterInfoService.queryList(bo);
        ExcelUtil.exportExcel(list, "计量器具信息", MeterInfoVo.class, response);
    }

    /**
     * 获取计量器具信息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("metering:info:query")
    @GetMapping("/info/{id}")
    public R<MeterInfoVo> getInfo(@NotNull(message = "主键不能为空")
                                  @PathVariable Long id) {
        return R.ok(iMeterInfoService.queryById(id));
    }

    /**
     * 新增计量器具信息
     */
    @SaCheckPermission("metering:info:add")
    @Log(title = "计量器具信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MeterInfoBo bo) {
        return toAjax(iMeterInfoService.insertByBo(bo));
    }

    /**
     * 修改计量器具信息
     */
    @SaCheckPermission("metering:info:edit")
    @Log(title = "计量器具信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MeterInfoBo bo) {
        return toAjax(iMeterInfoService.updateByBo(bo));
    }

    /**
     * 删除计量器具信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("metering:info:remove")
    @Log(title = "计量器具信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iMeterInfoService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 查询器具状态统计
     */
    @SaCheckPermission("metering:info:list")
    @GetMapping("/statistics")
    public R<List<Map<String, Object>>> getStatistics() {
        return R.ok(iMeterInfoService.getMeterStatistics());
    }

    /**
     * 查询需要校准的器具列表
     */
    @SaCheckPermission("metering:info:list")
    @GetMapping("/needCalibration")
    public R<List<MeterInfoVo>> getNeedCalibrationList() {
        return R.ok(iMeterInfoService.getNeedCalibrationList());
    }
}
