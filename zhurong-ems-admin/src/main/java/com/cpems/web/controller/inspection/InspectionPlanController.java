package com.cpems.web.controller.inspection;

import java.util.List;
import java.util.Arrays;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.convert.Convert;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.AppletsSignUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.vo.InspectionRecordVo;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.utils.WeChatUtil;
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
import com.ruoyi.system.domain.vo.InspectionPlanVo;
import com.ruoyi.system.domain.bo.InspectionPlanBo;
import com.ruoyi.system.service.IInspectionPlanService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡检计划
 *
 * @author cpems
 * @date 2023-04-04
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/plan")
public class InspectionPlanController extends BaseController {

    private final IInspectionPlanService iInspectionPlanService;
    private final ISysUserService userService;

    /**
     * 查询巡检计划列表
     */
    @SaCheckPermission("system:plan:list")
    @GetMapping("/list")
    public TableDataInfo<InspectionPlanVo> list(InspectionPlanBo bo, PageQuery pageQuery) {
        return iInspectionPlanService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出巡检计划列表
     */
    @SaCheckPermission("system:plan:export")
    @Log(title = "巡检计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(InspectionPlanBo bo, HttpServletResponse response) {
        List<InspectionPlanVo> list = iInspectionPlanService.queryList(bo);
        ExcelUtil.exportExcel(list, "巡检计划", InspectionPlanVo.class, response);
    }

    /**
     * 获取巡检计划详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:plan:query")
    @GetMapping("/{id}")
    public R<InspectionPlanVo> getInfo(@NotNull(message = "主键不能为空")
                                       @PathVariable Long id) {
        return R.ok(iInspectionPlanService.queryById(id));
    }

    /**
     * 新增巡检计划
     */
    @SaCheckPermission("system:plan:add")
    @Log(title = "巡检计划", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody InspectionPlanBo bo) {
        return toAjax(iInspectionPlanService.insertByBo(bo));
    }

    /**
     * 修改巡检计划
     */
    @SaCheckPermission("system:plan:edit")
    @Log(title = "巡检计划", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody InspectionPlanBo bo) {
        return toAjax(iInspectionPlanService.updateByBo(bo));
    }

    /**
     * 删除巡检计划
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:plan:remove")
    @Log(title = "巡检计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iInspectionPlanService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 测试微信消息订阅
     */
    @SaIgnore
    @GetMapping("/pushMessage")
    public void pushMessage() {
        SysUser user = userService.selectUserById(Long.valueOf("1646813492114718721"));
        String openId = user.getOpenId();
        // System.out.println(openId);
        InspectionRecordVo vo = new InspectionRecordVo();
        vo.setId(Convert.toLong("1646813492114718721"));
        vo.setPlanContent("测试消息订阅1");
        vo.setInspectionPerson("wj1");
        vo.setPlanTime(DateUtils.parseDate(DateUtils.getTime()));
        // System.out.println(vo);
        WeChatUtil.pushInspectionMessage(openId, vo);
    }

    /**
     * 微信服务号绑定验证接口
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @SaIgnore
    @GetMapping(value = "/verification/interface")
    public String wechatVerificationInterface(String signature, String timestamp, String nonce, String echostr) {

        //验证微信签名，判断是否来自配置微信端
        if (AppletsSignUtil.checkSignature(signature, timestamp, nonce)){
            return echostr;
        }
        return null;
    }
}
