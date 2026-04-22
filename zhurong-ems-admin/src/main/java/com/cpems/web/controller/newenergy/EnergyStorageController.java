package com.cpems.web.controller.newenergy;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.bo.EnergyStorageBo;
import com.ruoyi.system.domain.vo.EnergyStorageVo;
import com.ruoyi.system.service.IEnergyStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 储能系统Controller
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/newenergy/energyStorage")
public class EnergyStorageController extends BaseController {

    private final IEnergyStorageService energyStorageService;

    /**
     * 查询储能系统列表
     */
    @SaCheckPermission("newenergy:energyStorage:list")
    @GetMapping("/list")
    public TableDataInfo<EnergyStorageVo> list(EnergyStorageBo bo, PageQuery pageQuery) {
        return energyStorageService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出储能系统列表
     */
    @SaCheckPermission("newenergy:energyStorage:export")
    @PostMapping("/export")
    public void export(EnergyStorageBo bo, HttpServletResponse response) {
        List<EnergyStorageVo> list = energyStorageService.queryList(bo);
        ExcelUtil<EnergyStorageVo> util = new ExcelUtil<EnergyStorageVo>(EnergyStorageVo.class);
        util.exportExcel(response, list, "储能系统信息");
    }

    /**
     * 获取储能系统详细信息
     * 
     * @param id 主键
     */
    @SaCheckPermission("newenergy:energyStorage:query")
    @GetMapping("/{id}")
    public R<EnergyStorageVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(energyStorageService.queryById(id));
    }

    /**
     * 新增储能系统
     */
    @SaCheckPermission("newenergy:energyStorage:add")
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody EnergyStorageBo bo) {
        return toAjax(energyStorageService.insertByBo(bo));
    }

    /**
     * 修改储能系统
     */
    @SaCheckPermission("newenergy:energyStorage:edit")
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody EnergyStorageBo bo) {
        return toAjax(energyStorageService.updateByBo(bo));
    }

    /**
     * 删除储能系统
     * 
     * @param ids 主键串
     */
    @SaCheckPermission("newenergy:energyStorage:remove")
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(energyStorageService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 更新储能系统状态
     * 
     * @param id 主键
     * @param status 状态
     */
    @SaCheckPermission("newenergy:energyStorage:edit")
    @PutMapping("/updateStatus/{id}/{status}")
    public R<Void> updateStatus(@NotNull(message = "主键不能为空") @PathVariable Long id,
                                @NotNull(message = "状态不能为空") @PathVariable String status) {
        return toAjax(energyStorageService.updateStatus(id, status));
    }

    /**
     * 获取储能系统统计数据
     */
    @SaCheckPermission("newenergy:energyStorage:query")
    @GetMapping("/statistics")
    public R<Map<String, Object>> getStatistics() {
        return R.ok(energyStorageService.getStatistics());
    }

    /**
     * 获取储能系统实时数据
     * 
     * @param storageId 储能系统ID
     */
    @SaCheckPermission("newenergy:energyStorage:query")
    @GetMapping("/realTimeData/{storageId}")
    public R<Map<String, Object>> getRealTimeData(@NotNull(message = "储能系统ID不能为空") @PathVariable Long storageId) {
        return R.ok(energyStorageService.getRealTimeData(storageId));
    }

    /**
     * 获取充放电统计
     * 
     * @param storageId 储能系统ID
     * @param dateType 日期类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    @SaCheckPermission("newenergy:energyStorage:query")
    @GetMapping("/chargeDischargeStatistics")
    public R<List<Map<String, Object>>> getChargeDischargeStatistics(
            @NotNull(message = "储能系统ID不能为空") Long storageId,
            @NotNull(message = "日期类型不能为空") String dateType,
            @NotNull(message = "开始时间不能为空") String startTime,
            @NotNull(message = "结束时间不能为空") String endTime) {
        return R.ok(energyStorageService.getChargeDischargeStatistics(storageId, dateType, startTime, endTime));
    }

    /**
     * 获取电池组状态统计
     * 
     * @param storageId 储能系统ID
     */
    @SaCheckPermission("newenergy:energyStorage:query")
    @GetMapping("/batteryStatusStatistics/{storageId}")
    public R<Map<String, Object>> getBatteryStatusStatistics(@NotNull(message = "储能系统ID不能为空") @PathVariable Long storageId) {
        return R.ok(energyStorageService.getBatteryStatusStatistics(storageId));
    }
}
