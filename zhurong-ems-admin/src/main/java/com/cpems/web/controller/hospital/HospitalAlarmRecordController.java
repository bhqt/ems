package com.cpems.web.controller.hospital;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.hospital.bo.HospitalAlarmRecordBo;
import com.ruoyi.system.hospital.service.IHospitalAlarmRecordService;
import com.ruoyi.system.hospital.vo.HospitalAlarmRecordVo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 医院设备报警记录
 *
 * @author cpems
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/alarmRecord")
public class HospitalAlarmRecordController extends BaseController {

    private final IHospitalAlarmRecordService alarmRecordService;

    /**
     * 查询报警记录列表（最近 500 条）
     */
    @SaCheckPermission("hospital:alarmRecord:list")
    @GetMapping("/list")
    public R<List<HospitalAlarmRecordVo>> list(HospitalAlarmRecordBo bo) {
        return R.ok(alarmRecordService.queryList(bo));
    }

    /**
     * 处理/关闭报警记录
     */
    @SaCheckPermission("hospital:alarmRecord:handle")
    @Log(title = "医院报警记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/handle/{id}")
    public R<Void> handle(@NotNull(message = "主键不能为空") @PathVariable Long id,
                          @RequestParam(required = false) String handleRemark) {
        return toAjax(alarmRecordService.handle(id, handleRemark, getUsername()));
    }
}
