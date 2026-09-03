package com.cpems.web.controller.hospital;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.hospital.bo.HospitalCallbackLogBo;
import com.ruoyi.system.hospital.service.IHospitalCallbackLogService;
import com.ruoyi.system.hospital.vo.HospitalCallbackLogVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 医院 IOT 回调日志
 *
 * @author cpems
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/callbackLog")
public class HospitalCallbackLogController extends BaseController {

    private final IHospitalCallbackLogService callbackLogService;

    /**
     * 查询回调日志列表
     */
    @SaCheckPermission("hospital:callbackLog:list")
    @GetMapping("/list")
    public TableDataInfo<HospitalCallbackLogVo> list(HospitalCallbackLogBo bo, PageQuery pageQuery) {
        return callbackLogService.queryPageList(bo, pageQuery);
    }
}
