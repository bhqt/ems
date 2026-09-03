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
import com.ruoyi.system.hospital.bo.HospitalAreaBo;
import com.ruoyi.system.hospital.service.IHospitalAreaService;
import com.ruoyi.system.hospital.vo.HospitalAreaVo;
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
import java.util.List;

/**
 * 医院院区
 *
 * @author cpems
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/area")
public class HospitalAreaController extends BaseController {

    private final IHospitalAreaService hospitalAreaService;

    /**
     * 查询院区列表
     */
    @SaCheckPermission("hospital:area:list")
    @GetMapping("/list")
    public TableDataInfo<HospitalAreaVo> list(HospitalAreaBo bo, PageQuery pageQuery) {
        return hospitalAreaService.queryPageList(bo, pageQuery);
    }

    /**
     * 院区下拉选项（不分页）
     */
    @SaCheckPermission("hospital:area:query")
    @GetMapping("/options")
    public R<List<HospitalAreaVo>> options(HospitalAreaBo bo) {
        return R.ok(hospitalAreaService.queryList(bo));
    }

    /**
     * 获取院区详细信息
     */
    @SaCheckPermission("hospital:area:query")
    @GetMapping("/info/{id}")
    public R<HospitalAreaVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(hospitalAreaService.queryById(id));
    }

    /**
     * 新增院区
     */
    @SaCheckPermission("hospital:area:add")
    @Log(title = "医院院区", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody HospitalAreaBo bo) {
        return toAjax(hospitalAreaService.insertByBo(bo));
    }

    /**
     * 修改院区
     */
    @SaCheckPermission("hospital:area:edit")
    @Log(title = "医院院区", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody HospitalAreaBo bo) {
        return toAjax(hospitalAreaService.updateByBo(bo));
    }

    /**
     * 删除院区
     */
    @SaCheckPermission("hospital:area:remove")
    @Log(title = "医院院区", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(hospitalAreaService.deleteWithValidByIds(Arrays.asList(ids)));
    }
}
