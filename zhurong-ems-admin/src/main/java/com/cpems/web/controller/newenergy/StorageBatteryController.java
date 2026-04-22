package com.cpems.web.controller.newenergy;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.bo.StorageBatteryBo;
import com.ruoyi.system.domain.vo.StorageBatteryVo;
import com.ruoyi.system.service.IStorageBatteryService;
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
 * 储能电池组Controller
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/newenergy/storageBattery")
public class StorageBatteryController extends BaseController {

    private final IStorageBatteryService storageBatteryService;

    /**
     * 查询储能电池组列表
     */
    @SaCheckPermission("newenergy:storageBattery:list")
    @GetMapping("/list")
    public TableDataInfo<StorageBatteryVo> list(StorageBatteryBo bo, PageQuery pageQuery) {
        return storageBatteryService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出储能电池组列表
     */
    @SaCheckPermission("newenergy:storageBattery:export")
    @PostMapping("/export")
    public void export(StorageBatteryBo bo, HttpServletResponse response) {
        List<StorageBatteryVo> list = storageBatteryService.queryList(bo);
        ExcelUtil<StorageBatteryVo> util = new ExcelUtil<StorageBatteryVo>(StorageBatteryVo.class);
        util.exportExcel(response, list, "储能电池组信息");
    }

    /**
     * 获取储能电池组详细信息
     * 
     * @param id 主键
     */
    @SaCheckPermission("newenergy:storageBattery:query")
    @GetMapping("/{id}")
    public R<StorageBatteryVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(storageBatteryService.queryById(id));
    }

    /**
     * 新增储能电池组
     */
    @SaCheckPermission("newenergy:storageBattery:add")
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody StorageBatteryBo bo) {
        return toAjax(storageBatteryService.insertByBo(bo));
    }

    /**
     * 修改储能电池组
     */
    @SaCheckPermission("newenergy:storageBattery:edit")
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody StorageBatteryBo bo) {
        return toAjax(storageBatteryService.updateByBo(bo));
    }

    /**
     * 删除储能电池组
     * 
     * @param ids 主键串
     */
    @SaCheckPermission("newenergy:storageBattery:remove")
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(storageBatteryService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 更新电池组状态
     * 
     * @param id 主键
     * @param status 状态
     */
    @SaCheckPermission("newenergy:storageBattery:edit")
    @PutMapping("/updateStatus/{id}/{status}")
    public R<Void> updateStatus(@NotNull(message = "主键不能为空") @PathVariable Long id,
                                @NotNull(message = "状态不能为空") @PathVariable String status) {
        return toAjax(storageBatteryService.updateStatus(id, status));
    }

    /**
     * 根据储能系统ID查询电池组列表
     * 
     * @param storageId 储能系统ID
     */
    @SaCheckPermission("newenergy:storageBattery:query")
    @GetMapping("/byStorageId/{storageId}")
    public R<List<StorageBatteryVo>> getBatteriesByStorageId(@NotNull(message = "储能系统ID不能为空") @PathVariable Long storageId) {
        return R.ok(storageBatteryService.queryBatteriesByStorageId(storageId));
    }

    /**
     * 获取电池组实时数据
     * 
     * @param batteryId 电池组ID
     */
    @SaCheckPermission("newenergy:storageBattery:query")
    @GetMapping("/realTimeData/{batteryId}")
    public R<Map<String, Object>> getRealTimeData(@NotNull(message = "电池组ID不能为空") @PathVariable Long batteryId) {
        return R.ok(storageBatteryService.getRealTimeData(batteryId));
    }

    /**
     * 获取电池组历史数据
     * 
     * @param batteryId 电池组ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param dataType 数据类型
     */
    @SaCheckPermission("newenergy:storageBattery:query")
    @GetMapping("/historyData")
    public R<List<Map<String, Object>>> getHistoryData(
            @NotNull(message = "电池组ID不能为空") Long batteryId,
            @NotNull(message = "开始时间不能为空") String startTime,
            @NotNull(message = "结束时间不能为空") String endTime,
            @NotNull(message = "数据类型不能为空") String dataType) {
        return R.ok(storageBatteryService.getHistoryData(batteryId, startTime, endTime, dataType));
    }

    /**
     * 获取电池组健康状态统计
     * 
     * @param storageId 储能系统ID
     */
    @SaCheckPermission("newenergy:storageBattery:query")
    @GetMapping("/healthStatistics/{storageId}")
    public R<Map<String, Object>> getHealthStatistics(@NotNull(message = "储能系统ID不能为空") @PathVariable Long storageId) {
        return R.ok(storageBatteryService.getHealthStatistics(storageId));
    }
}
