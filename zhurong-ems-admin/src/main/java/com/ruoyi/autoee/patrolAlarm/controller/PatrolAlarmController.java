// 用于ems代码生成
package com.ruoyi.autoee.patrolAlarm.controller;

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
import com.ruoyi.autoee.patrolAlarm.domain.PatrolAlarm;
import com.ruoyi.autoee.patrolAlarm.service.IPatrolAlarmService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡更报警Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/patrolAlarm")
public class PatrolAlarmController extends BaseController
{
    @Autowired
    private IPatrolAlarmService patrolAlarmService;

    /**
     * 查询巡更报警分页列表
     */
    @SaCheckPermission("autoee:patrolAlarm:list")
    //@PreAuthorize("@ss.hasPermi('autoee:patrolAlarm:list')")
    @GetMapping("/selectPageListPatrolAlarm")
    public TableDataInfo selectPageListPatrolAlarm(PatrolAlarm patrolAlarm)
    {
        startPage();
        List<PatrolAlarm> list = patrolAlarmService.selectDataListByLikePatrolAlarm(patrolAlarm);
        return getDataTable(list);
    }

	/**
     * 查询巡更报警数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolAlarm:list")
        @GetMapping("/selectDataListByLikePatrolAlarm")
    public TableDataInfo selectDataListByLikePatrolAlarm(PatrolAlarm patrolAlarm)
    {
        List<PatrolAlarm> list = patrolAlarmService.selectDataListByLikePatrolAlarm(patrolAlarm);
        return getDataTable(list);
    }

	/**
     * 精确查询巡更报警数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolAlarm:list")
        @GetMapping("/selectDataListByEqPatrolAlarm")
    public TableDataInfo selectDataListByEqPatrolAlarm(PatrolAlarm patrolAlarm)
    {
        List<PatrolAlarm> list = patrolAlarmService.selectDataListByEqPatrolAlarm(patrolAlarm);
        return getDataTable(list);
    }

	/**
     * 查询巡更报警详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolAlarm:list")
        @GetMapping("/selectDetailListByLikePatrolAlarm")
    public TableDataInfo selectDetailListByLikePatrolAlarm(PatrolAlarm patrolAlarm)
    {
        List<PatrolAlarm> list = patrolAlarmService.selectDetailListByLikePatrolAlarm(patrolAlarm);
        return getDataTable(list);
    }

	/**
     * 精确查询巡更报警详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolAlarm:list")
        @GetMapping("/selectDetailListByEqPatrolAlarm")
    public TableDataInfo selectDetailListByEqPatrolAlarm(PatrolAlarm patrolAlarm)
    {
        List<PatrolAlarm> list = patrolAlarmService.selectDetailListByEqPatrolAlarm(patrolAlarm);
        return getDataTable(list);
    }

	/**
     * 导入巡更报警数据
     */
	@Log(title = "巡更报警", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:patrolAlarm:import")
        @PostMapping("/importPatrolAlarmData")
    public AjaxResult importPatrolAlarmData(MultipartFile file, boolean updateSupport, PatrolAlarm patrolAlarm) throws Exception
    {
        ExcelUtil<PatrolAlarm> util = new ExcelUtil<PatrolAlarm>(PatrolAlarm.class);
        List<PatrolAlarm> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = patrolAlarmService.importPatrolAlarmData(dataList, updateSupport, operName, patrolAlarm);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplatePatrolAlarm")
    public void downLoadImportTemplatePatrolAlarm(HttpServletResponse response)
    {
        ExcelUtil<PatrolAlarm> util = new ExcelUtil<PatrolAlarm>(PatrolAlarm.class);
        util.importTemplateExcel(response, "巡更报警数据");
    }

    /**
     * 导出巡更报警列表
     */
	@SaCheckPermission("autoee:patrolAlarm:export")
        @Log(title = "巡更报警", businessType = BusinessType.EXPORT)
    @PostMapping("/exportPatrolAlarm")
    public void exportPatrolAlarm(HttpServletResponse response, PatrolAlarm patrolAlarm)
    {
		patrolAlarmService.exportDataCheck(patrolAlarm);
        List<PatrolAlarm> list = patrolAlarmService.selectExportDetailListPatrolAlarm(patrolAlarm);
		patrolAlarmService.exportDataDeal(patrolAlarm, list);
        ExcelUtil<PatrolAlarm> util = new ExcelUtil<PatrolAlarm>(PatrolAlarm.class);
        util.exportExcel(response, list, "巡更报警数据");
    }

    /**
     * 获取巡更报警数据信息
     */
	@SaCheckPermission("autoee:patrolAlarm:query")
        @GetMapping(value = "/selectDataByPkPatrolAlarm/{id}")
    public AjaxResult selectDataByPkPatrolAlarm(@PathVariable("id") Long id)
    {
        return success(patrolAlarmService.selectDataByPkPatrolAlarm(id));
    }

    /**
     * 获取巡更报警详细信息，已转码
     */
	@SaCheckPermission("autoee:patrolAlarm:query")
        @GetMapping(value = "/selectDetailByPkPatrolAlarm/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(patrolAlarmService.selectDetailByPkPatrolAlarm(id));
    }

    /**
     * 新增巡更报警
     */
	@SaCheckPermission("autoee:patrolAlarm:add")
        @Log(title = "巡更报警", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PatrolAlarm patrolAlarm)
    {
		patrolAlarm.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    patrolAlarm.setCreateTime(date);
		patrolAlarm.setUpdateBy(getUsernameAndNickName());
        patrolAlarm.setUpdateTime(date);
        return AjaxResult.success(patrolAlarmService.insertPatrolAlarm(patrolAlarm));
    }

    /**
     * 修改巡更报警：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:patrolAlarm:edit")
        @Log(title = "巡更报警", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByPatrolAlarm")
    public AjaxResult updateNullValueByPatrolAlarm(@RequestBody PatrolAlarm patrolAlarm)
    {
		patrolAlarm.setUpdateBy(getUsernameAndNickName());
        patrolAlarm.setUpdateTime(new Date());
        return toAjaxResult(patrolAlarmService.updateNullValueByPatrolAlarm(patrolAlarm));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:patrolAlarm:edit")
        @Log(title = "巡更报警", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByPatrolAlarm")
    public AjaxResult updateNotNullValueByPatrolAlarm(@RequestBody PatrolAlarm patrolAlarm)
    {
		patrolAlarm.setUpdateBy(getUsernameAndNickName());
        patrolAlarm.setUpdateTime(new Date());
        return toAjaxResult(patrolAlarmService.updateNotNullValueByPatrolAlarm(patrolAlarm));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:patrolAlarm:edit")
	    @Log(title = "巡更报警", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditPatrolAlarm")
	public AjaxResult submitTableEditPatrolAlarm(@RequestBody List<PatrolAlarm> patrolAlarmList) {
		String errMsg = "";
		for (int i = 0; i < patrolAlarmList.size(); i++) {
			try {
				PatrolAlarm patrolAlarm = patrolAlarmList.get(i);
				patrolAlarm.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// patrolAlarm.setUpdateTime(new Date());
				patrolAlarmService.updateNullValueByPatrolAlarm(patrolAlarm);
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
     * 删除巡更报警
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:patrolAlarm:remove")
	    @Log(title = "巡更报警", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolAlarmByIds/{ids}")
    public AjaxResult deletePatrolAlarmByIds(@PathVariable String[] ids)
    {
        PatrolAlarm patrolAlarm = new PatrolAlarm();
        patrolAlarm.setIds(ids);
        patrolAlarm.setDelBy(getUsernameAndNickName());
        patrolAlarm.setDelTime(new Date());
        return toAjaxResult(patrolAlarmService.deletePatrolAlarmByIds(patrolAlarm));
            }

	/**
	 * 一个个删除巡更报警
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:patrolAlarm:remove")
		@Log(title = "巡更报警", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolAlarmOneByOne/{ids}")
	public AjaxResult deletePatrolAlarmOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				PatrolAlarm patrolAlarm = new PatrolAlarm();
				String[] idOneArr = {ids[i]};
				patrolAlarm.setIds(idOneArr);
				patrolAlarm.setDelBy(getUsernameAndNickName());
				patrolAlarm.setDelTime(new Date());
				patrolAlarmService.deletePatrolAlarmByIds(patrolAlarm);
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
	@SaCheckPermission("autoee:patrolAlarm:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolAlarmAllData")
    public AjaxResult deletePatrolAlarmAllData() {
		return toAjaxResult(patrolAlarmService.deletePatrolAlarmAllData()>=0);
	}




}
