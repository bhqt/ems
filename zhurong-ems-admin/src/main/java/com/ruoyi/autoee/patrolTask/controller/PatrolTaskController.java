// 用于ems代码生成
package com.ruoyi.autoee.patrolTask.controller;

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
import com.ruoyi.autoee.patrolTask.domain.PatrolTask;
import com.ruoyi.autoee.patrolTask.service.IPatrolTaskService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡更任务Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/patrolTask")
public class PatrolTaskController extends BaseController
{
    @Autowired
    private IPatrolTaskService patrolTaskService;

    /**
     * 查询巡更任务分页列表
     */
    @SaCheckPermission("autoee:patrolTask:list")
    //@PreAuthorize("@ss.hasPermi('autoee:patrolTask:list')")
    @GetMapping("/selectPageListPatrolTask")
    public TableDataInfo selectPageListPatrolTask(PatrolTask patrolTask)
    {
        startPage();
        List<PatrolTask> list = patrolTaskService.selectDataListByLikePatrolTask(patrolTask);
        return getDataTable(list);
    }

	/**
     * 查询巡更任务数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolTask:list")
        @GetMapping("/selectDataListByLikePatrolTask")
    public TableDataInfo selectDataListByLikePatrolTask(PatrolTask patrolTask)
    {
        List<PatrolTask> list = patrolTaskService.selectDataListByLikePatrolTask(patrolTask);
        return getDataTable(list);
    }

	/**
     * 精确查询巡更任务数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolTask:list")
        @GetMapping("/selectDataListByEqPatrolTask")
    public TableDataInfo selectDataListByEqPatrolTask(PatrolTask patrolTask)
    {
        List<PatrolTask> list = patrolTaskService.selectDataListByEqPatrolTask(patrolTask);
        return getDataTable(list);
    }

	/**
     * 查询巡更任务详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolTask:list")
        @GetMapping("/selectDetailListByLikePatrolTask")
    public TableDataInfo selectDetailListByLikePatrolTask(PatrolTask patrolTask)
    {
        List<PatrolTask> list = patrolTaskService.selectDetailListByLikePatrolTask(patrolTask);
        return getDataTable(list);
    }

	/**
     * 精确查询巡更任务详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolTask:list")
        @GetMapping("/selectDetailListByEqPatrolTask")
    public TableDataInfo selectDetailListByEqPatrolTask(PatrolTask patrolTask)
    {
        List<PatrolTask> list = patrolTaskService.selectDetailListByEqPatrolTask(patrolTask);
        return getDataTable(list);
    }

	/**
     * 导入巡更任务数据
     */
	@Log(title = "巡更任务", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:patrolTask:import")
        @PostMapping("/importPatrolTaskData")
    public AjaxResult importPatrolTaskData(MultipartFile file, boolean updateSupport, PatrolTask patrolTask) throws Exception
    {
        ExcelUtil<PatrolTask> util = new ExcelUtil<PatrolTask>(PatrolTask.class);
        List<PatrolTask> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = patrolTaskService.importPatrolTaskData(dataList, updateSupport, operName, patrolTask);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplatePatrolTask")
    public void downLoadImportTemplatePatrolTask(HttpServletResponse response)
    {
        ExcelUtil<PatrolTask> util = new ExcelUtil<PatrolTask>(PatrolTask.class);
        util.importTemplateExcel(response, "巡更任务数据");
    }

    /**
     * 导出巡更任务列表
     */
	@SaCheckPermission("autoee:patrolTask:export")
        @Log(title = "巡更任务", businessType = BusinessType.EXPORT)
    @PostMapping("/exportPatrolTask")
    public void exportPatrolTask(HttpServletResponse response, PatrolTask patrolTask)
    {
		patrolTaskService.exportDataCheck(patrolTask);
        List<PatrolTask> list = patrolTaskService.selectExportDetailListPatrolTask(patrolTask);
		patrolTaskService.exportDataDeal(patrolTask, list);
        ExcelUtil<PatrolTask> util = new ExcelUtil<PatrolTask>(PatrolTask.class);
        util.exportExcel(response, list, "巡更任务数据");
    }

    /**
     * 获取巡更任务数据信息
     */
	@SaCheckPermission("autoee:patrolTask:query")
        @GetMapping(value = "/selectDataByPkPatrolTask/{id}")
    public AjaxResult selectDataByPkPatrolTask(@PathVariable("id") Long id)
    {
        return success(patrolTaskService.selectDataByPkPatrolTask(id));
    }

    /**
     * 获取巡更任务详细信息，已转码
     */
	@SaCheckPermission("autoee:patrolTask:query")
        @GetMapping(value = "/selectDetailByPkPatrolTask/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(patrolTaskService.selectDetailByPkPatrolTask(id));
    }

    /**
     * 新增巡更任务
     */
	@SaCheckPermission("autoee:patrolTask:add")
        @Log(title = "巡更任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PatrolTask patrolTask)
    {
		patrolTask.setUserId(getUserId());
		patrolTask.setDeptId(getDeptId());
		patrolTask.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    patrolTask.setCreateTime(date);
		patrolTask.setUpdateBy(getUsernameAndNickName());
        patrolTask.setUpdateTime(date);
        return AjaxResult.success(patrolTaskService.insertPatrolTask(patrolTask));
    }

    /**
     * 修改巡更任务：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:patrolTask:edit")
        @Log(title = "巡更任务", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByPatrolTask")
    public AjaxResult updateNullValueByPatrolTask(@RequestBody PatrolTask patrolTask)
    {
		patrolTask.setUpdateBy(getUsernameAndNickName());
        patrolTask.setUpdateTime(new Date());
        return toAjaxResult(patrolTaskService.updateNullValueByPatrolTask(patrolTask));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:patrolTask:edit")
        @Log(title = "巡更任务", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByPatrolTask")
    public AjaxResult updateNotNullValueByPatrolTask(@RequestBody PatrolTask patrolTask)
    {
		patrolTask.setUpdateBy(getUsernameAndNickName());
        patrolTask.setUpdateTime(new Date());
        return toAjaxResult(patrolTaskService.updateNotNullValueByPatrolTask(patrolTask));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:patrolTask:edit")
	    @Log(title = "巡更任务", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditPatrolTask")
	public AjaxResult submitTableEditPatrolTask(@RequestBody List<PatrolTask> patrolTaskList) {
		String errMsg = "";
		for (int i = 0; i < patrolTaskList.size(); i++) {
			try {
				PatrolTask patrolTask = patrolTaskList.get(i);
				patrolTask.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// patrolTask.setUpdateTime(new Date());
				patrolTaskService.updateNullValueByPatrolTask(patrolTask);
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
     * 删除巡更任务
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:patrolTask:remove")
	    @Log(title = "巡更任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolTaskByIds/{ids}")
    public AjaxResult deletePatrolTaskByIds(@PathVariable String[] ids)
    {
        PatrolTask patrolTask = new PatrolTask();
        patrolTask.setIds(ids);
        patrolTask.setDelBy(getUsernameAndNickName());
        patrolTask.setDelTime(new Date());
        return toAjaxResult(patrolTaskService.deletePatrolTaskByIds(patrolTask));
            }

	/**
	 * 一个个删除巡更任务
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:patrolTask:remove")
		@Log(title = "巡更任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolTaskOneByOne/{ids}")
	public AjaxResult deletePatrolTaskOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				PatrolTask patrolTask = new PatrolTask();
				String[] idOneArr = {ids[i]};
				patrolTask.setIds(idOneArr);
				patrolTask.setDelBy(getUsernameAndNickName());
				patrolTask.setDelTime(new Date());
				patrolTaskService.deletePatrolTaskByIds(patrolTask);
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
	@SaCheckPermission("autoee:patrolTask:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolTaskAllData")
    public AjaxResult deletePatrolTaskAllData() {
		return toAjaxResult(patrolTaskService.deletePatrolTaskAllData()>=0);
	}




}
