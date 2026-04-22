// 用于ems代码生成
package com.ruoyi.autoee.patrolPoint.controller;

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
import com.ruoyi.autoee.patrolPoint.domain.PatrolPoint;
import com.ruoyi.autoee.patrolPoint.service.IPatrolPointService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡更点位Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/patrolPoint")
public class PatrolPointController extends BaseController
{
    @Autowired
    private IPatrolPointService patrolPointService;

    /**
     * 查询巡更点位分页列表
     */
    @SaCheckPermission("autoee:patrolPoint:list")
    //@PreAuthorize("@ss.hasPermi('autoee:patrolPoint:list')")
    @GetMapping("/selectPageListPatrolPoint")
    public TableDataInfo selectPageListPatrolPoint(PatrolPoint patrolPoint)
    {
        startPage();
        List<PatrolPoint> list = patrolPointService.selectDataListByLikePatrolPoint(patrolPoint);
        return getDataTable(list);
    }

	/**
     * 查询巡更点位数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolPoint:list")
        @GetMapping("/selectDataListByLikePatrolPoint")
    public TableDataInfo selectDataListByLikePatrolPoint(PatrolPoint patrolPoint)
    {
        List<PatrolPoint> list = patrolPointService.selectDataListByLikePatrolPoint(patrolPoint);
        return getDataTable(list);
    }

	/**
     * 精确查询巡更点位数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolPoint:list")
        @GetMapping("/selectDataListByEqPatrolPoint")
    public TableDataInfo selectDataListByEqPatrolPoint(PatrolPoint patrolPoint)
    {
        List<PatrolPoint> list = patrolPointService.selectDataListByEqPatrolPoint(patrolPoint);
        return getDataTable(list);
    }

	/**
     * 查询巡更点位详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolPoint:list")
        @GetMapping("/selectDetailListByLikePatrolPoint")
    public TableDataInfo selectDetailListByLikePatrolPoint(PatrolPoint patrolPoint)
    {
        List<PatrolPoint> list = patrolPointService.selectDetailListByLikePatrolPoint(patrolPoint);
        return getDataTable(list);
    }

	/**
     * 精确查询巡更点位详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolPoint:list")
        @GetMapping("/selectDetailListByEqPatrolPoint")
    public TableDataInfo selectDetailListByEqPatrolPoint(PatrolPoint patrolPoint)
    {
        List<PatrolPoint> list = patrolPointService.selectDetailListByEqPatrolPoint(patrolPoint);
        return getDataTable(list);
    }

	/**
     * 导入巡更点位数据
     */
	@Log(title = "巡更点位", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:patrolPoint:import")
        @PostMapping("/importPatrolPointData")
    public AjaxResult importPatrolPointData(MultipartFile file, boolean updateSupport, PatrolPoint patrolPoint) throws Exception
    {
        ExcelUtil<PatrolPoint> util = new ExcelUtil<PatrolPoint>(PatrolPoint.class);
        List<PatrolPoint> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = patrolPointService.importPatrolPointData(dataList, updateSupport, operName, patrolPoint);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplatePatrolPoint")
    public void downLoadImportTemplatePatrolPoint(HttpServletResponse response)
    {
        ExcelUtil<PatrolPoint> util = new ExcelUtil<PatrolPoint>(PatrolPoint.class);
        util.importTemplateExcel(response, "巡更点位数据");
    }

    /**
     * 导出巡更点位列表
     */
	@SaCheckPermission("autoee:patrolPoint:export")
        @Log(title = "巡更点位", businessType = BusinessType.EXPORT)
    @PostMapping("/exportPatrolPoint")
    public void exportPatrolPoint(HttpServletResponse response, PatrolPoint patrolPoint)
    {
		patrolPointService.exportDataCheck(patrolPoint);
        List<PatrolPoint> list = patrolPointService.selectExportDetailListPatrolPoint(patrolPoint);
		patrolPointService.exportDataDeal(patrolPoint, list);
        ExcelUtil<PatrolPoint> util = new ExcelUtil<PatrolPoint>(PatrolPoint.class);
        util.exportExcel(response, list, "巡更点位数据");
    }

    /**
     * 获取巡更点位数据信息
     */
	@SaCheckPermission("autoee:patrolPoint:query")
        @GetMapping(value = "/selectDataByPkPatrolPoint/{id}")
    public AjaxResult selectDataByPkPatrolPoint(@PathVariable("id") Long id)
    {
        return success(patrolPointService.selectDataByPkPatrolPoint(id));
    }

    /**
     * 获取巡更点位详细信息，已转码
     */
	@SaCheckPermission("autoee:patrolPoint:query")
        @GetMapping(value = "/selectDetailByPkPatrolPoint/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(patrolPointService.selectDetailByPkPatrolPoint(id));
    }

    /**
     * 新增巡更点位
     */
	@SaCheckPermission("autoee:patrolPoint:add")
        @Log(title = "巡更点位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PatrolPoint patrolPoint)
    {
		patrolPoint.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    patrolPoint.setCreateTime(date);
		patrolPoint.setUpdateBy(getUsernameAndNickName());
        patrolPoint.setUpdateTime(date);
        return AjaxResult.success(patrolPointService.insertPatrolPoint(patrolPoint));
    }

    /**
     * 修改巡更点位：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:patrolPoint:edit")
        @Log(title = "巡更点位", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByPatrolPoint")
    public AjaxResult updateNullValueByPatrolPoint(@RequestBody PatrolPoint patrolPoint)
    {
		patrolPoint.setUpdateBy(getUsernameAndNickName());
        patrolPoint.setUpdateTime(new Date());
        return toAjaxResult(patrolPointService.updateNullValueByPatrolPoint(patrolPoint));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:patrolPoint:edit")
        @Log(title = "巡更点位", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByPatrolPoint")
    public AjaxResult updateNotNullValueByPatrolPoint(@RequestBody PatrolPoint patrolPoint)
    {
		patrolPoint.setUpdateBy(getUsernameAndNickName());
        patrolPoint.setUpdateTime(new Date());
        return toAjaxResult(patrolPointService.updateNotNullValueByPatrolPoint(patrolPoint));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:patrolPoint:edit")
	    @Log(title = "巡更点位", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditPatrolPoint")
	public AjaxResult submitTableEditPatrolPoint(@RequestBody List<PatrolPoint> patrolPointList) {
		String errMsg = "";
		for (int i = 0; i < patrolPointList.size(); i++) {
			try {
				PatrolPoint patrolPoint = patrolPointList.get(i);
				patrolPoint.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// patrolPoint.setUpdateTime(new Date());
				patrolPointService.updateNullValueByPatrolPoint(patrolPoint);
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
     * 删除巡更点位
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:patrolPoint:remove")
	    @Log(title = "巡更点位", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolPointByIds/{ids}")
    public AjaxResult deletePatrolPointByIds(@PathVariable String[] ids)
    {
        PatrolPoint patrolPoint = new PatrolPoint();
        patrolPoint.setIds(ids);
        patrolPoint.setDelBy(getUsernameAndNickName());
        patrolPoint.setDelTime(new Date());
        return toAjaxResult(patrolPointService.deletePatrolPointByIds(patrolPoint));
            }

	/**
	 * 一个个删除巡更点位
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:patrolPoint:remove")
		@Log(title = "巡更点位", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolPointOneByOne/{ids}")
	public AjaxResult deletePatrolPointOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				PatrolPoint patrolPoint = new PatrolPoint();
				String[] idOneArr = {ids[i]};
				patrolPoint.setIds(idOneArr);
				patrolPoint.setDelBy(getUsernameAndNickName());
				patrolPoint.setDelTime(new Date());
				patrolPointService.deletePatrolPointByIds(patrolPoint);
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
	@SaCheckPermission("autoee:patrolPoint:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolPointAllData")
    public AjaxResult deletePatrolPointAllData() {
		return toAjaxResult(patrolPointService.deletePatrolPointAllData()>=0);
	}




}
