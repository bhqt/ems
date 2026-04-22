// 用于ems代码生成
package com.ruoyi.autoee.patrolPlan.controller;

import com.ruoyi.common.utils.DateUtils;
import java.util.Date;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
//import org.springframework.security.access.prepost.PreAuthorize;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.autoee.patrolPlan.domain.PatrolPlan;
import com.ruoyi.autoee.patrolPlan.service.IPatrolPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡更计划Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/patrolPlan")
public class PatrolPlanController extends BaseController
{
    @Autowired
    private IPatrolPlanService patrolPlanService;

    /**
     * 查询巡更计划分页列表
     */
    @SaCheckPermission("autoee:patrolPlan:list")
    //@PreAuthorize("@ss.hasPermi('autoee:patrolPlan:list')")
    @GetMapping("/selectPageListPatrolPlan")
    public TableDataInfo selectPageListPatrolPlan(PatrolPlan patrolPlan)
    {
        startPage();
        List<PatrolPlan> list = patrolPlanService.selectDataListByLikePatrolPlan(patrolPlan);
        return getDataTable(list);
    }

	/**
     * 查询巡更计划数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolPlan:list")
        @GetMapping("/selectDataListByLikePatrolPlan")
    public TableDataInfo selectDataListByLikePatrolPlan(PatrolPlan patrolPlan)
    {
        List<PatrolPlan> list = patrolPlanService.selectDataListByLikePatrolPlan(patrolPlan);
        return getDataTable(list);
    }

	/**
     * 精确查询巡更计划数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolPlan:list")
        @GetMapping("/selectDataListByEqPatrolPlan")
    public TableDataInfo selectDataListByEqPatrolPlan(PatrolPlan patrolPlan)
    {
        List<PatrolPlan> list = patrolPlanService.selectDataListByEqPatrolPlan(patrolPlan);
        return getDataTable(list);
    }

	/**
     * 查询巡更计划详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolPlan:list")
        @GetMapping("/selectDetailListByLikePatrolPlan")
    public TableDataInfo selectDetailListByLikePatrolPlan(PatrolPlan patrolPlan)
    {
        List<PatrolPlan> list = patrolPlanService.selectDetailListByLikePatrolPlan(patrolPlan);
        return getDataTable(list);
    }

	/**
     * 精确查询巡更计划详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolPlan:list")
        @GetMapping("/selectDetailListByEqPatrolPlan")
    public TableDataInfo selectDetailListByEqPatrolPlan(PatrolPlan patrolPlan)
    {
        List<PatrolPlan> list = patrolPlanService.selectDetailListByEqPatrolPlan(patrolPlan);
        return getDataTable(list);
    }

	/**
     * 导入巡更计划数据
     */
	@Log(title = "巡更计划", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:patrolPlan:import")
        @PostMapping("/importPatrolPlanData")
    public AjaxResult importPatrolPlanData(MultipartFile file, boolean updateSupport, PatrolPlan patrolPlan) throws Exception
    {
        ExcelUtil<PatrolPlan> util = new ExcelUtil<PatrolPlan>(PatrolPlan.class);
        List<PatrolPlan> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = patrolPlanService.importPatrolPlanData(dataList, updateSupport, operName, patrolPlan);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplatePatrolPlan")
    public void downLoadImportTemplatePatrolPlan(HttpServletResponse response)
    {
        ExcelUtil<PatrolPlan> util = new ExcelUtil<PatrolPlan>(PatrolPlan.class);
        util.importTemplateExcel(response, "巡更计划数据");
    }

    /**
     * 导出巡更计划列表
     */
	@SaCheckPermission("autoee:patrolPlan:export")
        @Log(title = "巡更计划", businessType = BusinessType.EXPORT)
    @PostMapping("/exportPatrolPlan")
    public void exportPatrolPlan(HttpServletResponse response, PatrolPlan patrolPlan)
    {
		patrolPlanService.exportDataCheck(patrolPlan);
        List<PatrolPlan> list = patrolPlanService.selectExportDetailListPatrolPlan(patrolPlan);
		patrolPlanService.exportDataDeal(patrolPlan, list);
        ExcelUtil<PatrolPlan> util = new ExcelUtil<PatrolPlan>(PatrolPlan.class);
        util.exportExcel(response, list, "巡更计划数据");
    }

    /**
     * 获取巡更计划数据信息
     */
	@SaCheckPermission("autoee:patrolPlan:query")
        @GetMapping(value = "/selectDataByPkPatrolPlan/{id}")
    public AjaxResult selectDataByPkPatrolPlan(@PathVariable("id") Long id)
    {
        return success(patrolPlanService.selectDataByPkPatrolPlan(id));
    }

    /**
     * 获取巡更计划详细信息，已转码
     */
	@SaCheckPermission("autoee:patrolPlan:query")
        @GetMapping(value = "/selectDetailByPkPatrolPlan/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(patrolPlanService.selectDetailByPkPatrolPlan(id));
    }

    /**
     * 新增巡更计划
     */
	@SaCheckPermission("autoee:patrolPlan:add")
        @Log(title = "巡更计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PatrolPlan patrolPlan)
    {
		patrolPlan.setUserId(getUserId());
		patrolPlan.setDeptId(getDeptId());
		patrolPlan.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    patrolPlan.setCreateTime(date);
		patrolPlan.setUpdateBy(getUsernameAndNickName());
        patrolPlan.setUpdateTime(date);
        return AjaxResult.success(patrolPlanService.insertPatrolPlan(patrolPlan));
    }

    /**
     * 修改巡更计划：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:patrolPlan:edit")
        @Log(title = "巡更计划", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByPatrolPlan")
    public AjaxResult updateNullValueByPatrolPlan(@RequestBody PatrolPlan patrolPlan)
    {
		patrolPlan.setUpdateBy(getUsernameAndNickName());
        patrolPlan.setUpdateTime(new Date());
        return toAjaxResult(patrolPlanService.updateNullValueByPatrolPlan(patrolPlan));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:patrolPlan:edit")
        @Log(title = "巡更计划", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByPatrolPlan")
    public AjaxResult updateNotNullValueByPatrolPlan(@RequestBody PatrolPlan patrolPlan)
    {
		patrolPlan.setUpdateBy(getUsernameAndNickName());
        patrolPlan.setUpdateTime(new Date());
        return toAjaxResult(patrolPlanService.updateNotNullValueByPatrolPlan(patrolPlan));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:patrolPlan:edit")
	    @Log(title = "巡更计划", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditPatrolPlan")
	public AjaxResult submitTableEditPatrolPlan(@RequestBody List<PatrolPlan> patrolPlanList) {
		String errMsg = "";
		for (int i = 0; i < patrolPlanList.size(); i++) {
			try {
				PatrolPlan patrolPlan = patrolPlanList.get(i);
				patrolPlan.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// patrolPlan.setUpdateTime(new Date());
				patrolPlanService.updateNullValueByPatrolPlan(patrolPlan);
			} catch (Exception e) {
				errMsg = errMsg + "（" + (i + 1) + "）" + e.getMessage();
			}
		}
		if ("".equals(errMsg)) {
			return AjaxResult.success();
		} else {
			return AjaxResult.error(errMsg);
		}
	}



    /**
     * 删除巡更计划
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:patrolPlan:remove")
	    @Log(title = "巡更计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolPlanByIds/{ids}")
    public AjaxResult deletePatrolPlanByIds(@PathVariable String[] ids)
    {
        PatrolPlan patrolPlan = new PatrolPlan();
        patrolPlan.setIds(ids);
        patrolPlan.setDelBy(getUsernameAndNickName());
        patrolPlan.setDelTime(new Date());
        return toAjaxResult(patrolPlanService.deletePatrolPlanByIds(patrolPlan));
            }

	/**
	 * 一个个删除巡更计划
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:patrolPlan:remove")
    @Log(title = "巡更计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolPlanOneByOne/{ids}")
	public AjaxResult deletePatrolPlanOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				PatrolPlan patrolPlan = new PatrolPlan();
				String[] idOneArr = {ids[i]};
				patrolPlan.setIds(idOneArr);
				patrolPlan.setDelBy(getUsernameAndNickName());
				patrolPlan.setDelTime(new Date());
				patrolPlanService.deletePatrolPlanByIds(patrolPlan);
			} catch (Exception e) {
				errMsg = errMsg + "（" + (i + 1) + "）" + e.getMessage();
			}
		}
		if ("".equals(errMsg)) {
			return AjaxResult.success();
		} else {
			return AjaxResult.error(errMsg);
		}
	}

	/**
     * 删除全部数据
     */
	@SaCheckPermission("autoee:patrolPlan:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolPlanAllData")
    public AjaxResult deletePatrolPlanAllData() {
        return toAjaxResult(patrolPlanService.deletePatrolPlanAllData()>=0);
	}




}
