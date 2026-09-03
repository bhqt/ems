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
import com.ruoyi.system.hospital.bo.HospitalAlarmRuleBo;
import com.ruoyi.system.hospital.service.IHospitalAlarmRuleService;
import com.ruoyi.system.hospital.vo.HospitalAlarmRuleVo;
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
 * 医院设备报警规则
 *
 * @author cpems
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/alarmRule")
public class HospitalAlarmRuleController extends BaseController {

    private final IHospitalAlarmRuleService alarmRuleService;

    /**
     * 查询报警规则列表
     */
    @SaCheckPermission("hospital:alarmRule:list")
    @GetMapping("/list")
    public TableDataInfo<HospitalAlarmRuleVo> list(HospitalAlarmRuleBo bo, PageQuery pageQuery) {
        return alarmRuleService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取报警规则详细信息
     */
    @SaCheckPermission("hospital:alarmRule:query")
    @GetMapping("/info/{id}")
    public R<HospitalAlarmRuleVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(alarmRuleService.queryById(id));
    }

    /**
     * 新增报警规则
     */
    @SaCheckPermission("hospital:alarmRule:add")
    @Log(title = "医院报警规则", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody HospitalAlarmRuleBo bo) {
        return toAjax(alarmRuleService.insertByBo(bo));
    }

    /**
     * 修改报警规则
     */
    @SaCheckPermission("hospital:alarmRule:edit")
    @Log(title = "医院报警规则", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody HospitalAlarmRuleBo bo) {
        return toAjax(alarmRuleService.updateByBo(bo));
    }

    /**
     * 删除报警规则
     */
    @SaCheckPermission("hospital:alarmRule:remove")
    @Log(title = "医院报警规则", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(alarmRuleService.deleteWithValidByIds(Arrays.asList(ids)));
    }
}
