// 用于ems代码生成
package com.ruoyi.autoee.patrolRecord.controller;

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
import com.ruoyi.autoee.patrolRecord.domain.PatrolRecord;
import com.ruoyi.autoee.patrolRecord.service.IPatrolRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡更记录Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/patrolRecord")
public class PatrolRecordController extends BaseController
{
    @Autowired
    private IPatrolRecordService patrolRecordService;

    /**
     * 查询巡更记录分页列表
     */
    @SaCheckPermission("autoee:patrolRecord:list")
    //@PreAuthorize("@ss.hasPermi('autoee:patrolRecord:list')")
    @GetMapping("/selectPageListPatrolRecord")
    public TableDataInfo selectPageListPatrolRecord(PatrolRecord patrolRecord)
    {
        startPage();
        List<PatrolRecord> list = patrolRecordService.selectDataListByLikePatrolRecord(patrolRecord);
        return getDataTable(list);
    }

	/**
     * 查询巡更记录数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolRecord:list")
        @GetMapping("/selectDataListByLikePatrolRecord")
    public TableDataInfo selectDataListByLikePatrolRecord(PatrolRecord patrolRecord)
    {
        List<PatrolRecord> list = patrolRecordService.selectDataListByLikePatrolRecord(patrolRecord);
        return getDataTable(list);
    }

	/**
     * 精确查询巡更记录数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolRecord:list")
        @GetMapping("/selectDataListByEqPatrolRecord")
    public TableDataInfo selectDataListByEqPatrolRecord(PatrolRecord patrolRecord)
    {
        List<PatrolRecord> list = patrolRecordService.selectDataListByEqPatrolRecord(patrolRecord);
        return getDataTable(list);
    }

	/**
     * 查询巡更记录详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolRecord:list")
        @GetMapping("/selectDetailListByLikePatrolRecord")
    public TableDataInfo selectDetailListByLikePatrolRecord(PatrolRecord patrolRecord)
    {
        List<PatrolRecord> list = patrolRecordService.selectDetailListByLikePatrolRecord(patrolRecord);
        return getDataTable(list);
    }

	/**
     * 精确查询巡更记录详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:patrolRecord:list")
        @GetMapping("/selectDetailListByEqPatrolRecord")
    public TableDataInfo selectDetailListByEqPatrolRecord(PatrolRecord patrolRecord)
    {
        List<PatrolRecord> list = patrolRecordService.selectDetailListByEqPatrolRecord(patrolRecord);
        return getDataTable(list);
    }

	/**
     * 导入巡更记录数据
     */
	@Log(title = "巡更记录", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:patrolRecord:import")
        @PostMapping("/importPatrolRecordData")
    public AjaxResult importPatrolRecordData(MultipartFile file, boolean updateSupport, PatrolRecord patrolRecord) throws Exception
    {
        ExcelUtil<PatrolRecord> util = new ExcelUtil<PatrolRecord>(PatrolRecord.class);
        List<PatrolRecord> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = patrolRecordService.importPatrolRecordData(dataList, updateSupport, operName, patrolRecord);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplatePatrolRecord")
    public void downLoadImportTemplatePatrolRecord(HttpServletResponse response)
    {
        ExcelUtil<PatrolRecord> util = new ExcelUtil<PatrolRecord>(PatrolRecord.class);
        util.importTemplateExcel(response, "巡更记录数据");
    }

    /**
     * 导出巡更记录列表
     */
	@SaCheckPermission("autoee:patrolRecord:export")
        @Log(title = "巡更记录", businessType = BusinessType.EXPORT)
    @PostMapping("/exportPatrolRecord")
    public void exportPatrolRecord(HttpServletResponse response, PatrolRecord patrolRecord)
    {
		patrolRecordService.exportDataCheck(patrolRecord);
        List<PatrolRecord> list = patrolRecordService.selectExportDetailListPatrolRecord(patrolRecord);
		patrolRecordService.exportDataDeal(patrolRecord, list);
        ExcelUtil<PatrolRecord> util = new ExcelUtil<PatrolRecord>(PatrolRecord.class);
        util.exportExcel(response, list, "巡更记录数据");
    }

    /**
     * 获取巡更记录数据信息
     */
	@SaCheckPermission("autoee:patrolRecord:query")
        @GetMapping(value = "/selectDataByPkPatrolRecord/{id}")
    public AjaxResult selectDataByPkPatrolRecord(@PathVariable("id") Long id)
    {
        return success(patrolRecordService.selectDataByPkPatrolRecord(id));
    }

    /**
     * 获取巡更记录详细信息，已转码
     */
	@SaCheckPermission("autoee:patrolRecord:query")
        @GetMapping(value = "/selectDetailByPkPatrolRecord/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(patrolRecordService.selectDetailByPkPatrolRecord(id));
    }

    /**
     * 新增巡更记录
     */
	@SaCheckPermission("autoee:patrolRecord:add")
        @Log(title = "巡更记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PatrolRecord patrolRecord)
    {
		patrolRecord.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    patrolRecord.setCreateTime(date);
		patrolRecord.setUpdateBy(getUsernameAndNickName());
        patrolRecord.setUpdateTime(date);
        return AjaxResult.success(patrolRecordService.insertPatrolRecord(patrolRecord));
    }

    /**
     * 修改巡更记录：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:patrolRecord:edit")
        @Log(title = "巡更记录", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByPatrolRecord")
    public AjaxResult updateNullValueByPatrolRecord(@RequestBody PatrolRecord patrolRecord)
    {
		patrolRecord.setUpdateBy(getUsernameAndNickName());
        patrolRecord.setUpdateTime(new Date());
        return toAjaxResult(patrolRecordService.updateNullValueByPatrolRecord(patrolRecord));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:patrolRecord:edit")
        @Log(title = "巡更记录", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByPatrolRecord")
    public AjaxResult updateNotNullValueByPatrolRecord(@RequestBody PatrolRecord patrolRecord)
    {
		patrolRecord.setUpdateBy(getUsernameAndNickName());
        patrolRecord.setUpdateTime(new Date());
        return toAjaxResult(patrolRecordService.updateNotNullValueByPatrolRecord(patrolRecord));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:patrolRecord:edit")
	    @Log(title = "巡更记录", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditPatrolRecord")
	public AjaxResult submitTableEditPatrolRecord(@RequestBody List<PatrolRecord> patrolRecordList) {
		String errMsg = "";
		for (int i = 0; i < patrolRecordList.size(); i++) {
			try {
				PatrolRecord patrolRecord = patrolRecordList.get(i);
				patrolRecord.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// patrolRecord.setUpdateTime(new Date());
				patrolRecordService.updateNullValueByPatrolRecord(patrolRecord);
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
     * 删除巡更记录
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:patrolRecord:remove")
	    @Log(title = "巡更记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolRecordByIds/{ids}")
    public AjaxResult deletePatrolRecordByIds(@PathVariable String[] ids)
    {
        PatrolRecord patrolRecord = new PatrolRecord();
        patrolRecord.setIds(ids);
        patrolRecord.setDelBy(getUsernameAndNickName());
        patrolRecord.setDelTime(new Date());
        return toAjaxResult(patrolRecordService.deletePatrolRecordByIds(patrolRecord));
            }

	/**
	 * 一个个删除巡更记录
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:patrolRecord:remove")
		@Log(title = "巡更记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolRecordOneByOne/{ids}")
	public AjaxResult deletePatrolRecordOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				PatrolRecord patrolRecord = new PatrolRecord();
				String[] idOneArr = {ids[i]};
				patrolRecord.setIds(idOneArr);
				patrolRecord.setDelBy(getUsernameAndNickName());
				patrolRecord.setDelTime(new Date());
				patrolRecordService.deletePatrolRecordByIds(patrolRecord);
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
	@SaCheckPermission("autoee:patrolRecord:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deletePatrolRecordAllData")
    public AjaxResult deletePatrolRecordAllData() {
		return toAjaxResult(patrolRecordService.deletePatrolRecordAllData()>=0);
	}




}
