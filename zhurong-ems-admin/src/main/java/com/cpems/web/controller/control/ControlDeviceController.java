package com.cpems.web.controller.control;

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
import com.ruoyi.system.domain.vo.ControlDeviceVo;
import com.ruoyi.system.domain.bo.ControlDeviceBo;
import com.ruoyi.system.service.IControlDeviceService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 控制设备Controller
 *
 * @author cpems
 * @date 2026-03-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/control/device")
public class ControlDeviceController extends BaseController {

    private final IControlDeviceService iControlDeviceService;

    /**
     * 查询控制设备列表
     */
    @SaCheckPermission("control:device:list")
    @GetMapping("/list")
    public TableDataInfo<ControlDeviceVo> list(ControlDeviceBo bo, PageQuery pageQuery) {
        return iControlDeviceService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出控制设备列表
     */
    @SaCheckPermission("control:device:export")
    @Log(title = "控制设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ControlDeviceBo bo, HttpServletResponse response) {
        List<ControlDeviceVo> list = iControlDeviceService.queryList(bo);
        ExcelUtil.exportExcel(list, "控制设备", ControlDeviceVo.class, response);
    }

    /**
     * 获取控制设备详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("control:device:query")
    @GetMapping("/info/{id}")
    public R<ControlDeviceVo> getInfo(@NotNull(message = "主键不能为空")
                                      @PathVariable Long id) {
        return R.ok(iControlDeviceService.queryById(id));
    }

    /**
     * 新增控制设备
     */
    @SaCheckPermission("control:device:add")
    @Log(title = "控制设备", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ControlDeviceBo bo) {
        return toAjax(iControlDeviceService.insertByBo(bo));
    }

    /**
     * 修改控制设备
     */
    @SaCheckPermission("control:device:edit")
    @Log(title = "控制设备", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ControlDeviceBo bo) {
        return toAjax(iControlDeviceService.updateByBo(bo));
    }

    /**
     * 删除控制设备
     *
     * @param ids 主键串
     */
    @SaCheckPermission("control:device:remove")
    @Log(title = "控制设备", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iControlDeviceService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 控制设备
     *
     * @param deviceId 设备ID
     * @param action 操作类型(start:启动, stop:停止)
     */
    @SaCheckPermission("control:device:control")
    @Log(title = "控制设备", businessType = BusinessType.UPDATE)
    @PostMapping("/control")
    public R<Void> control(@NotNull(message = "设备ID不能为空") @RequestParam Long deviceId,
                           @NotBlank(message = "操作类型不能为空") @RequestParam String action) {
        return toAjax(iControlDeviceService.controlDevice(deviceId, action));
    }

    /**
     * 查询设备状态统计
     */
    @SaCheckPermission("control:device:list")
    @GetMapping("/statistics")
    public R<List<Map<String, Object>>> getStatistics() {
        return R.ok(iControlDeviceService.getDeviceStatistics());
    }
}
