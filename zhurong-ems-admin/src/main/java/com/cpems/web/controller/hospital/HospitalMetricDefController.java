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
import com.ruoyi.system.hospital.bo.HospitalMetricDefBo;
import com.ruoyi.system.hospital.service.IHospitalMetricDefService;
import com.ruoyi.system.hospital.vo.HospitalMetricDefVo;
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
 * 医院设备指标定义
 *
 * @author cpems
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/metric")
public class HospitalMetricDefController extends BaseController {

    private final IHospitalMetricDefService hospitalMetricDefService;

    /**
     * 查询指标列表
     */
    @SaCheckPermission("hospital:metric:list")
    @GetMapping("/list")
    public TableDataInfo<HospitalMetricDefVo> list(HospitalMetricDefBo bo, PageQuery pageQuery) {
        return hospitalMetricDefService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取指标详细信息
     */
    @SaCheckPermission("hospital:metric:query")
    @GetMapping("/info/{id}")
    public R<HospitalMetricDefVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(hospitalMetricDefService.queryById(id));
    }

    /**
     * 新增指标
     */
    @SaCheckPermission("hospital:metric:add")
    @Log(title = "医院指标", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody HospitalMetricDefBo bo) {
        return toAjax(hospitalMetricDefService.insertByBo(bo));
    }

    /**
     * 修改指标
     */
    @SaCheckPermission("hospital:metric:edit")
    @Log(title = "医院指标", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody HospitalMetricDefBo bo) {
        return toAjax(hospitalMetricDefService.updateByBo(bo));
    }

    /**
     * 删除指标
     */
    @SaCheckPermission("hospital:metric:remove")
    @Log(title = "医院指标", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(hospitalMetricDefService.deleteWithValidByIds(Arrays.asList(ids)));
    }
}
