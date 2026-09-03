package com.cpems.web.controller.hospital;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.hospital.bo.HospitalDeviceBo;
import com.ruoyi.system.hospital.mapper.HospitalDeviceDataMapper;
import com.ruoyi.system.hospital.service.IHospitalDeviceService;
import com.ruoyi.system.hospital.vo.HospitalDeviceDataVo;
import com.ruoyi.system.hospital.vo.HospitalDeviceVo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 医院检查检验设备台账
 *
 * @author cpems
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/device")
public class HospitalDeviceController extends BaseController {

    private final IHospitalDeviceService hospitalDeviceService;
    private final HospitalDeviceDataMapper hospitalDeviceDataMapper;

    /**
     * 查询设备列表
     */
    @SaCheckPermission("hospital:device:list")
    @GetMapping("/list")
    public TableDataInfo<HospitalDeviceVo> list(HospitalDeviceBo bo, PageQuery pageQuery) {
        return hospitalDeviceService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取设备详细信息
     */
    @SaCheckPermission("hospital:device:query")
    @GetMapping("/info/{id}")
    public R<HospitalDeviceVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(hospitalDeviceService.queryById(id));
    }

    /**
     * 新增设备
     */
    @SaCheckPermission("hospital:device:add")
    @Log(title = "医院设备", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody HospitalDeviceBo bo) {
        return toAjax(hospitalDeviceService.insertByBo(bo));
    }

    /**
     * 修改设备
     */
    @SaCheckPermission("hospital:device:edit")
    @Log(title = "医院设备", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody HospitalDeviceBo bo) {
        return toAjax(hospitalDeviceService.updateByBo(bo));
    }

    /**
     * 删除设备
     */
    @SaCheckPermission("hospital:device:remove")
    @Log(title = "医院设备", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(hospitalDeviceService.deleteWithValidByIds(Arrays.asList(ids)));
    }

    /**
     * 绑定/解绑 IOT 平台设备
     */
    @SaCheckPermission("hospital:device:bind")
    @Log(title = "医院设备", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/bind")
    public R<Void> bind(@NotNull(message = "设备ID不能为空") @RequestParam Long id,
                        @RequestParam(required = false) String iotDeviceId) {
        return toAjax(hospitalDeviceService.bindIotDevice(id, iotDeviceId));
    }

    /**
     * 查询设备数据点（最近 N 条）
     */
    @SaCheckPermission("hospital:device:query")
    @GetMapping("/dataList")
    public R<List<HospitalDeviceDataVo>> dataList(@RequestParam(required = false) Long deviceId,
                                                  @RequestParam(required = false) String metricCode,
                                                  @RequestParam(defaultValue = "100") Integer limit) {
        return R.ok(hospitalDeviceDataMapper.selectHospitalDeviceDataList(deviceId, metricCode, limit));
    }
}
