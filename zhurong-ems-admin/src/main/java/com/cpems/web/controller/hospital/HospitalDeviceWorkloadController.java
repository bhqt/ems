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
import com.ruoyi.system.hospital.bo.HospitalDeviceWorkloadBo;
import com.ruoyi.system.hospital.service.IHospitalDeviceWorkloadService;
import com.ruoyi.system.hospital.vo.HospitalDeviceWorkloadVo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * 医院设备工作量（检查量）
 *
 * @author cpems
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/workload")
public class HospitalDeviceWorkloadController extends BaseController {

    private final IHospitalDeviceWorkloadService hospitalDeviceWorkloadService;

    /**
     * 查询工作量列表
     */
    @SaCheckPermission("hospital:workload:list")
    @GetMapping("/list")
    public TableDataInfo<HospitalDeviceWorkloadVo> list(HospitalDeviceWorkloadBo bo, PageQuery pageQuery) {
        return hospitalDeviceWorkloadService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取工作量详细信息
     */
    @SaCheckPermission("hospital:workload:query")
    @GetMapping("/info/{id}")
    public R<HospitalDeviceWorkloadVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(hospitalDeviceWorkloadService.queryById(id));
    }

    /**
     * 新增工作量
     */
    @SaCheckPermission("hospital:workload:add")
    @Log(title = "医院工作量", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody HospitalDeviceWorkloadBo bo) {
        return toAjax(hospitalDeviceWorkloadService.insertByBo(bo));
    }

    /**
     * 修改工作量
     */
    @SaCheckPermission("hospital:workload:edit")
    @Log(title = "医院工作量", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody HospitalDeviceWorkloadBo bo) {
        return toAjax(hospitalDeviceWorkloadService.updateByBo(bo));
    }

    /**
     * 删除工作量
     */
    @SaCheckPermission("hospital:workload:remove")
    @Log(title = "医院工作量", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(hospitalDeviceWorkloadService.deleteWithValidByIds(Arrays.asList(ids)));
    }
}
