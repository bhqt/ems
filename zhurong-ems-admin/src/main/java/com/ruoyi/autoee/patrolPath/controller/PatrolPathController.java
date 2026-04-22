// 用于ems代码生成
package com.ruoyi.autoee.patrolPath.controller;

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
import com.ruoyi.autoee.patrolPath.domain.PatrolPath;
import com.ruoyi.autoee.patrolPath.service.IPatrolPathService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡更路线Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/patrolPath")
public class PatrolPathController extends BaseController
{
    @Autowired
    private IPatrolPathService patrolPathService;

    /**
     * 查询巡更路线分页列表
     */
    @SaCheckPermission("autoee:patrolPath:list")
    //@PreAuthorize("@ss.hasPermi('autoee:patrolPath:list')")
    @GetMapping("/selectPageListPatrolPath")
    public TableDataInfo selectPageListPatrolPath(PatrolPath patrolPath)
    {
        startPage();
        List<PatrolPath> list = patrolPathService.selectDataListByLikePatrolPath(patrolPath);
        return getDataTable(list);
    }

	/**
     * 查询巡更路线数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolPath:list")
        @GetMapping("/selectDataListByLikePatrolPath")
    public TableDataInfo selectDataListByLikePatrolPath(PatrolPath patrolPath)
    {
        List<PatrolPath> list = patrolPathService.selectDataListByLikePatrolPath(patrolPath);
        return getDataTable(list);
    }

	/**
     * 精确查询巡更路线数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolPath:list")
        @GetMapping("/selectDataListByEqPatrolPath")
    public TableDataInfo selectDataListByEqPatrolPath(PatrolPath patrolPath)
    {
        List<PatrolPath> list = patrolPathService.selectDataListByEqPatrolPath(patrolPath);
        return getDataTable(list);
    }

	/**
     * 查询巡更路线详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolPath:list")
        @GetMapping("/selectDetailListByLikePatrolPath")
    public TableDataInfo selectDetailListByLikePatrolPath(PatrolPath patrolPath)
    {
        List<PatrolPath> list = patrolPathService.selectDetailListByLikePatrolPath(patrolPath);
        return getDataTable(list);
    }

	/**
     * 精确查询巡更路线详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolPath:list")
        @GetMapping("/selectDetailListByEqPatrolPath")
    public TableDataInfo selectDetailListByEqPatrolPath(PatrolPath patrolPath)
    {
        List<PatrolPath> list = patrolPathService.selectDetailListByEqPatrolPath(patrolPath);
        return getDataTable(list);
    }

	/**
     * 导入巡更路线数据
     */
	@Log(title = "巡更路线", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:patrolPath:import")
        @PostMapping("/importPatrolPathData")
    public AjaxResult importPatrolPathData(MultipartFile file, boolean updateSupport, PatrolPath patrolPath) throws Exception
    {
        ExcelUtil<PatrolPath> util = new ExcelUtil<PatrolPath>(PatrolPath.class);
        List<PatrolPath> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = patrolPathService.importPatrolPathData(dataList, updateSupport, operName, patrolPath);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplatePatrolPath")
    public void downLoadImportTemplatePatrolPath(HttpServletResponse response)
    {
        ExcelUtil<PatrolPath> util = new ExcelUtil<PatrolPath>(PatrolPath.class);
        util.importTemplateExcel(response, "巡更路线数据");
    }

    /**
     * 导出巡更路线列表
     */
	@SaCheckPermission("autoee:patrolPath:export")
        @Log(title = "巡更路线", businessType = BusinessType.EXPORT)
    @PostMapping("/exportPatrolPath")
    public void exportPatrolPath(HttpServletResponse response, PatrolPath patrolPath)
    {
		patrolPathService.exportDataCheck(patrolPath);
        List<PatrolPath> list = patrolPathService.selectExportDetailListPatrolPath(patrolPath);
		patrolPathService.exportDataDeal(patrolPath, list);
        ExcelUtil<PatrolPath> util = new ExcelUtil<PatrolPath>(PatrolPath.class);
        util.exportExcel(response, list, "巡更路线数据");
    }

    /**
     * 获取巡更路线数据信息
     */
	@SaCheckPermission("autoee:patrolPath:query")
        @GetMapping(value = "/selectDataByPkPatrolPath/{id}")
    public AjaxResult selectDataByPkPatrolPath(@PathVariable("id") Long id)
    {
        return success(patrolPathService.selectDataByPkPatrolPath(id));
    }

    /**
     * 获取巡更路线详细信息，已转码
     */
	@SaCheckPermission("autoee:patrolPath:query")
        @GetMapping(value = "/selectDetailByPkPatrolPath/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(patrolPathService.selectDetailByPkPatrolPath(id));
    }

    /**
     * 新增巡更路线
     */
	@SaCheckPermission("autoee:patrolPath:add")
        @Log(title = "巡更路线", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PatrolPath patrolPath)
    {
		patrolPath.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    patrolPath.setCreateTime(date);
		patrolPath.setUpdateBy(getUsernameAndNickName());
        patrolPath.setUpdateTime(date);
        return AjaxResult.success(patrolPathService.insertPatrolPath(patrolPath));
    }

    /**
     * 修改巡更路线：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:patrolPath:edit")
        @Log(title = "巡更路线", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByPatrolPath")
    public AjaxResult updateNullValueByPatrolPath(@RequestBody PatrolPath patrolPath)
    {
		patrolPath.setUpdateBy(getUsernameAndNickName());
        patrolPath.setUpdateTime(new Date());
        return toAjaxResult(patrolPathService.updateNullValueByPatrolPath(patrolPath));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:patrolPath:edit")
        @Log(title = "巡更路线", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByPatrolPath")
    public AjaxResult updateNotNullValueByPatrolPath(@RequestBody PatrolPath patrolPath)
    {
		patrolPath.setUpdateBy(getUsernameAndNickName());
        patrolPath.setUpdateTime(new Date());
        return toAjaxResult(patrolPathService.updateNotNullValueByPatrolPath(patrolPath));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:patrolPath:edit")
	    @Log(title = "巡更路线", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditPatrolPath")
	public AjaxResult submitTableEditPatrolPath(@RequestBody List<PatrolPath> patrolPathList) {
		String errMsg = "";
		for (int i = 0; i < patrolPathList.size(); i++) {
			try {
				PatrolPath patrolPath = patrolPathList.get(i);
				patrolPath.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// patrolPath.setUpdateTime(new Date());
				patrolPathService.updateNullValueByPatrolPath(patrolPath);
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
     * 删除巡更路线
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:patrolPath:remove")
	    @Log(title = "巡更路线", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolPathByIds/{ids}")
    public AjaxResult deletePatrolPathByIds(@PathVariable String[] ids)
    {
        PatrolPath patrolPath = new PatrolPath();
        patrolPath.setIds(ids);
        patrolPath.setDelBy(getUsernameAndNickName());
        patrolPath.setDelTime(new Date());
        return toAjaxResult(patrolPathService.deletePatrolPathByIds(patrolPath));
            }

	/**
	 * 一个个删除巡更路线
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:patrolPath:remove")
		@Log(title = "巡更路线", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolPathOneByOne/{ids}")
	public AjaxResult deletePatrolPathOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				PatrolPath patrolPath = new PatrolPath();
				String[] idOneArr = {ids[i]};
				patrolPath.setIds(idOneArr);
				patrolPath.setDelBy(getUsernameAndNickName());
				patrolPath.setDelTime(new Date());
				patrolPathService.deletePatrolPathByIds(patrolPath);
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
	@SaCheckPermission("autoee:patrolPath:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolPathAllData")
    public AjaxResult deletePatrolPathAllData() {
		return toAjaxResult(patrolPathService.deletePatrolPathAllData()>=0);
	}




}
