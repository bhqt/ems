// 用于ems代码生成
package com.ruoyi.autoee.dangerGoodsStockIn.controller;

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
import com.ruoyi.autoee.dangerGoodsStockIn.domain.DangerGoodsStockIn;
import com.ruoyi.autoee.dangerGoodsStockIn.service.IDangerGoodsStockInService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 危化品入库记录Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/dangerGoodsStockIn")
public class DangerGoodsStockInController extends BaseController
{
    @Autowired
    private IDangerGoodsStockInService dangerGoodsStockInService;

    /**
     * 查询危化品入库记录分页列表
     */
    @SaCheckPermission("autoee:dangerGoodsStockIn:list")
    //@PreAuthorize("@ss.hasPermi('autoee:dangerGoodsStockIn:list')")
    @GetMapping("/selectPageListDangerGoodsStockIn")
    public TableDataInfo selectPageListDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn)
    {
        startPage();
        List<DangerGoodsStockIn> list = dangerGoodsStockInService.selectDataListByLikeDangerGoodsStockIn(dangerGoodsStockIn);
        return getDataTable(list);
    }

	/**
     * 查询危化品入库记录数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsStockIn:list")
        @GetMapping("/selectDataListByLikeDangerGoodsStockIn")
    public TableDataInfo selectDataListByLikeDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn)
    {
        List<DangerGoodsStockIn> list = dangerGoodsStockInService.selectDataListByLikeDangerGoodsStockIn(dangerGoodsStockIn);
        return getDataTable(list);
    }

	/**
     * 精确查询危化品入库记录数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsStockIn:list")
        @GetMapping("/selectDataListByEqDangerGoodsStockIn")
    public TableDataInfo selectDataListByEqDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn)
    {
        List<DangerGoodsStockIn> list = dangerGoodsStockInService.selectDataListByEqDangerGoodsStockIn(dangerGoodsStockIn);
        return getDataTable(list);
    }

	/**
     * 查询危化品入库记录详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsStockIn:list")
        @GetMapping("/selectDetailListByLikeDangerGoodsStockIn")
    public TableDataInfo selectDetailListByLikeDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn)
    {
        List<DangerGoodsStockIn> list = dangerGoodsStockInService.selectDetailListByLikeDangerGoodsStockIn(dangerGoodsStockIn);
        return getDataTable(list);
    }

	/**
     * 精确查询危化品入库记录详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsStockIn:list")
        @GetMapping("/selectDetailListByEqDangerGoodsStockIn")
    public TableDataInfo selectDetailListByEqDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn)
    {
        List<DangerGoodsStockIn> list = dangerGoodsStockInService.selectDetailListByEqDangerGoodsStockIn(dangerGoodsStockIn);
        return getDataTable(list);
    }

	/**
     * 导入危化品入库记录数据
     */
	@Log(title = "危化品入库记录", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:dangerGoodsStockIn:import")
        @PostMapping("/importDangerGoodsStockInData")
    public AjaxResult importDangerGoodsStockInData(MultipartFile file, boolean updateSupport, DangerGoodsStockIn dangerGoodsStockIn) throws Exception
    {
        ExcelUtil<DangerGoodsStockIn> util = new ExcelUtil<DangerGoodsStockIn>(DangerGoodsStockIn.class);
        List<DangerGoodsStockIn> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = dangerGoodsStockInService.importDangerGoodsStockInData(dataList, updateSupport, operName, dangerGoodsStockIn);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplateDangerGoodsStockIn")
    public void downLoadImportTemplateDangerGoodsStockIn(HttpServletResponse response)
    {
        ExcelUtil<DangerGoodsStockIn> util = new ExcelUtil<DangerGoodsStockIn>(DangerGoodsStockIn.class);
        util.importTemplateExcel(response, "危化品入库记录数据");
    }

    /**
     * 导出危化品入库记录列表
     */
	@SaCheckPermission("autoee:dangerGoodsStockIn:export")
        @Log(title = "危化品入库记录", businessType = BusinessType.EXPORT)
    @PostMapping("/exportDangerGoodsStockIn")
    public void exportDangerGoodsStockIn(HttpServletResponse response, DangerGoodsStockIn dangerGoodsStockIn)
    {
		dangerGoodsStockInService.exportDataCheck(dangerGoodsStockIn);
        List<DangerGoodsStockIn> list = dangerGoodsStockInService.selectExportDetailListDangerGoodsStockIn(dangerGoodsStockIn);
		dangerGoodsStockInService.exportDataDeal(dangerGoodsStockIn, list);
        ExcelUtil<DangerGoodsStockIn> util = new ExcelUtil<DangerGoodsStockIn>(DangerGoodsStockIn.class);
        util.exportExcel(response, list, "危化品入库记录数据");
    }

    /**
     * 获取危化品入库记录数据信息
     */
	@SaCheckPermission("autoee:dangerGoodsStockIn:query")
        @GetMapping(value = "/selectDataByPkDangerGoodsStockIn/{id}")
    public AjaxResult selectDataByPkDangerGoodsStockIn(@PathVariable("id") Long id)
    {
        return success(dangerGoodsStockInService.selectDataByPkDangerGoodsStockIn(id));
    }

    /**
     * 获取危化品入库记录详细信息，已转码
     */
	@SaCheckPermission("autoee:dangerGoodsStockIn:query")
        @GetMapping(value = "/selectDetailByPkDangerGoodsStockIn/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(dangerGoodsStockInService.selectDetailByPkDangerGoodsStockIn(id));
    }

    /**
     * 新增危化品入库记录
     */
	@SaCheckPermission("autoee:dangerGoodsStockIn:add")
        @Log(title = "危化品入库记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DangerGoodsStockIn dangerGoodsStockIn)
    {
		dangerGoodsStockIn.setUserId(getUserId());
		dangerGoodsStockIn.setDeptId(getDeptId());
		dangerGoodsStockIn.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    dangerGoodsStockIn.setCreateTime(date);
		dangerGoodsStockIn.setUpdateBy(getUsernameAndNickName());
        dangerGoodsStockIn.setUpdateTime(date);
        return AjaxResult.success(dangerGoodsStockInService.insertDangerGoodsStockIn(dangerGoodsStockIn));
    }

    /**
     * 修改危化品入库记录：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:dangerGoodsStockIn:edit")
        @Log(title = "危化品入库记录", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByDangerGoodsStockIn")
    public AjaxResult updateNullValueByDangerGoodsStockIn(@RequestBody DangerGoodsStockIn dangerGoodsStockIn)
    {
		dangerGoodsStockIn.setUpdateBy(getUsernameAndNickName());
        dangerGoodsStockIn.setUpdateTime(new Date());
        return toAjaxResult(dangerGoodsStockInService.updateNullValueByDangerGoodsStockIn(dangerGoodsStockIn));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:dangerGoodsStockIn:edit")
        @Log(title = "危化品入库记录", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByDangerGoodsStockIn")
    public AjaxResult updateNotNullValueByDangerGoodsStockIn(@RequestBody DangerGoodsStockIn dangerGoodsStockIn)
    {
		dangerGoodsStockIn.setUpdateBy(getUsernameAndNickName());
        dangerGoodsStockIn.setUpdateTime(new Date());
        return toAjaxResult(dangerGoodsStockInService.updateNotNullValueByDangerGoodsStockIn(dangerGoodsStockIn));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:dangerGoodsStockIn:edit")
	    @Log(title = "危化品入库记录", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditDangerGoodsStockIn")
	public AjaxResult submitTableEditDangerGoodsStockIn(@RequestBody List<DangerGoodsStockIn> dangerGoodsStockInList) {
		String errMsg = "";
		for (int i = 0; i < dangerGoodsStockInList.size(); i++) {
			try {
				DangerGoodsStockIn dangerGoodsStockIn = dangerGoodsStockInList.get(i);
				dangerGoodsStockIn.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// dangerGoodsStockIn.setUpdateTime(new Date());
				dangerGoodsStockInService.updateNullValueByDangerGoodsStockIn(dangerGoodsStockIn);
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
     * 删除危化品入库记录
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:dangerGoodsStockIn:remove")
	    @Log(title = "危化品入库记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteDangerGoodsStockInByIds/{ids}")
    public AjaxResult deleteDangerGoodsStockInByIds(@PathVariable String[] ids)
    {
        DangerGoodsStockIn dangerGoodsStockIn = new DangerGoodsStockIn();
        dangerGoodsStockIn.setIds(ids);
        dangerGoodsStockIn.setDelBy(getUsernameAndNickName());
        dangerGoodsStockIn.setDelTime(new Date());
        return toAjaxResult(dangerGoodsStockInService.deleteDangerGoodsStockInByIds(dangerGoodsStockIn));
            }

	/**
	 * 一个个删除危化品入库记录
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:dangerGoodsStockIn:remove")
		@Log(title = "危化品入库记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteDangerGoodsStockInOneByOne/{ids}")
	public AjaxResult deleteDangerGoodsStockInOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				DangerGoodsStockIn dangerGoodsStockIn = new DangerGoodsStockIn();
				String[] idOneArr = {ids[i]};
				dangerGoodsStockIn.setIds(idOneArr);
				dangerGoodsStockIn.setDelBy(getUsernameAndNickName());
				dangerGoodsStockIn.setDelTime(new Date());
				dangerGoodsStockInService.deleteDangerGoodsStockInByIds(dangerGoodsStockIn);
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
	@SaCheckPermission("autoee:dangerGoodsStockIn:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteDangerGoodsStockInAllData")
    public AjaxResult deleteDangerGoodsStockInAllData() {
		return toAjaxResult(dangerGoodsStockInService.deleteDangerGoodsStockInAllData()>=0);
	}




}
