// 用于ems代码生成
package com.ruoyi.autoee.dangerGoodsStockOut.controller;

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
import com.ruoyi.autoee.dangerGoodsStockOut.domain.DangerGoodsStockOut;
import com.ruoyi.autoee.dangerGoodsStockOut.service.IDangerGoodsStockOutService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 危化品出库记录Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/dangerGoodsStockOut")
public class DangerGoodsStockOutController extends BaseController
{
    @Autowired
    private IDangerGoodsStockOutService dangerGoodsStockOutService;

    /**
     * 查询危化品出库记录分页列表
     */
    @SaCheckPermission("autoee:dangerGoodsStockOut:list")
    //@PreAuthorize("@ss.hasPermi('autoee:dangerGoodsStockOut:list')")
    @GetMapping("/selectPageListDangerGoodsStockOut")
    public TableDataInfo selectPageListDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut)
    {
        startPage();
        List<DangerGoodsStockOut> list = dangerGoodsStockOutService.selectDataListByLikeDangerGoodsStockOut(dangerGoodsStockOut);
        return getDataTable(list);
    }

	/**
     * 查询危化品出库记录数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsStockOut:list")
        @GetMapping("/selectDataListByLikeDangerGoodsStockOut")
    public TableDataInfo selectDataListByLikeDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut)
    {
        List<DangerGoodsStockOut> list = dangerGoodsStockOutService.selectDataListByLikeDangerGoodsStockOut(dangerGoodsStockOut);
        return getDataTable(list);
    }

	/**
     * 精确查询危化品出库记录数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsStockOut:list")
        @GetMapping("/selectDataListByEqDangerGoodsStockOut")
    public TableDataInfo selectDataListByEqDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut)
    {
        List<DangerGoodsStockOut> list = dangerGoodsStockOutService.selectDataListByEqDangerGoodsStockOut(dangerGoodsStockOut);
        return getDataTable(list);
    }

	/**
     * 查询危化品出库记录详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsStockOut:list")
        @GetMapping("/selectDetailListByLikeDangerGoodsStockOut")
    public TableDataInfo selectDetailListByLikeDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut)
    {
        List<DangerGoodsStockOut> list = dangerGoodsStockOutService.selectDetailListByLikeDangerGoodsStockOut(dangerGoodsStockOut);
        return getDataTable(list);
    }

	/**
     * 精确查询危化品出库记录详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsStockOut:list")
        @GetMapping("/selectDetailListByEqDangerGoodsStockOut")
    public TableDataInfo selectDetailListByEqDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut)
    {
        List<DangerGoodsStockOut> list = dangerGoodsStockOutService.selectDetailListByEqDangerGoodsStockOut(dangerGoodsStockOut);
        return getDataTable(list);
    }

	/**
     * 导入危化品出库记录数据
     */
	@Log(title = "危化品出库记录", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:dangerGoodsStockOut:import")
        @PostMapping("/importDangerGoodsStockOutData")
    public AjaxResult importDangerGoodsStockOutData(MultipartFile file, boolean updateSupport, DangerGoodsStockOut dangerGoodsStockOut) throws Exception
    {
        ExcelUtil<DangerGoodsStockOut> util = new ExcelUtil<DangerGoodsStockOut>(DangerGoodsStockOut.class);
        List<DangerGoodsStockOut> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = dangerGoodsStockOutService.importDangerGoodsStockOutData(dataList, updateSupport, operName, dangerGoodsStockOut);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplateDangerGoodsStockOut")
    public void downLoadImportTemplateDangerGoodsStockOut(HttpServletResponse response)
    {
        ExcelUtil<DangerGoodsStockOut> util = new ExcelUtil<DangerGoodsStockOut>(DangerGoodsStockOut.class);
        util.importTemplateExcel(response, "危化品出库记录数据");
    }

    /**
     * 导出危化品出库记录列表
     */
	@SaCheckPermission("autoee:dangerGoodsStockOut:export")
        @Log(title = "危化品出库记录", businessType = BusinessType.EXPORT)
    @PostMapping("/exportDangerGoodsStockOut")
    public void exportDangerGoodsStockOut(HttpServletResponse response, DangerGoodsStockOut dangerGoodsStockOut)
    {
		dangerGoodsStockOutService.exportDataCheck(dangerGoodsStockOut);
        List<DangerGoodsStockOut> list = dangerGoodsStockOutService.selectExportDetailListDangerGoodsStockOut(dangerGoodsStockOut);
		dangerGoodsStockOutService.exportDataDeal(dangerGoodsStockOut, list);
        ExcelUtil<DangerGoodsStockOut> util = new ExcelUtil<DangerGoodsStockOut>(DangerGoodsStockOut.class);
        util.exportExcel(response, list, "危化品出库记录数据");
    }

    /**
     * 获取危化品出库记录数据信息
     */
	@SaCheckPermission("autoee:dangerGoodsStockOut:query")
        @GetMapping(value = "/selectDataByPkDangerGoodsStockOut/{id}")
    public AjaxResult selectDataByPkDangerGoodsStockOut(@PathVariable("id") Long id)
    {
        return success(dangerGoodsStockOutService.selectDataByPkDangerGoodsStockOut(id));
    }

    /**
     * 获取危化品出库记录详细信息，已转码
     */
	@SaCheckPermission("autoee:dangerGoodsStockOut:query")
        @GetMapping(value = "/selectDetailByPkDangerGoodsStockOut/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(dangerGoodsStockOutService.selectDetailByPkDangerGoodsStockOut(id));
    }

    /**
     * 新增危化品出库记录
     */
	@SaCheckPermission("autoee:dangerGoodsStockOut:add")
        @Log(title = "危化品出库记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DangerGoodsStockOut dangerGoodsStockOut)
    {
		dangerGoodsStockOut.setUserId(getUserId());
		dangerGoodsStockOut.setDeptId(getDeptId());
		dangerGoodsStockOut.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    dangerGoodsStockOut.setCreateTime(date);
		dangerGoodsStockOut.setUpdateBy(getUsernameAndNickName());
        dangerGoodsStockOut.setUpdateTime(date);
        return AjaxResult.success(dangerGoodsStockOutService.insertDangerGoodsStockOut(dangerGoodsStockOut));
    }

    /**
     * 修改危化品出库记录：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:dangerGoodsStockOut:edit")
        @Log(title = "危化品出库记录", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByDangerGoodsStockOut")
    public AjaxResult updateNullValueByDangerGoodsStockOut(@RequestBody DangerGoodsStockOut dangerGoodsStockOut)
    {
		dangerGoodsStockOut.setUpdateBy(getUsernameAndNickName());
        dangerGoodsStockOut.setUpdateTime(new Date());
        return toAjaxResult(dangerGoodsStockOutService.updateNullValueByDangerGoodsStockOut(dangerGoodsStockOut));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:dangerGoodsStockOut:edit")
        @Log(title = "危化品出库记录", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByDangerGoodsStockOut")
    public AjaxResult updateNotNullValueByDangerGoodsStockOut(@RequestBody DangerGoodsStockOut dangerGoodsStockOut)
    {
		dangerGoodsStockOut.setUpdateBy(getUsernameAndNickName());
        dangerGoodsStockOut.setUpdateTime(new Date());
        return toAjaxResult(dangerGoodsStockOutService.updateNotNullValueByDangerGoodsStockOut(dangerGoodsStockOut));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:dangerGoodsStockOut:edit")
	    @Log(title = "危化品出库记录", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditDangerGoodsStockOut")
	public AjaxResult submitTableEditDangerGoodsStockOut(@RequestBody List<DangerGoodsStockOut> dangerGoodsStockOutList) {
		String errMsg = "";
		for (int i = 0; i < dangerGoodsStockOutList.size(); i++) {
			try {
				DangerGoodsStockOut dangerGoodsStockOut = dangerGoodsStockOutList.get(i);
				dangerGoodsStockOut.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// dangerGoodsStockOut.setUpdateTime(new Date());
				dangerGoodsStockOutService.updateNullValueByDangerGoodsStockOut(dangerGoodsStockOut);
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
     * 删除危化品出库记录
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:dangerGoodsStockOut:remove")
	    @Log(title = "危化品出库记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteDangerGoodsStockOutByIds/{ids}")
    public AjaxResult deleteDangerGoodsStockOutByIds(@PathVariable String[] ids)
    {
        DangerGoodsStockOut dangerGoodsStockOut = new DangerGoodsStockOut();
        dangerGoodsStockOut.setIds(ids);
        dangerGoodsStockOut.setDelBy(getUsernameAndNickName());
        dangerGoodsStockOut.setDelTime(new Date());
        return toAjaxResult(dangerGoodsStockOutService.deleteDangerGoodsStockOutByIds(dangerGoodsStockOut));
            }

	/**
	 * 一个个删除危化品出库记录
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:dangerGoodsStockOut:remove")
		@Log(title = "危化品出库记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteDangerGoodsStockOutOneByOne/{ids}")
	public AjaxResult deleteDangerGoodsStockOutOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				DangerGoodsStockOut dangerGoodsStockOut = new DangerGoodsStockOut();
				String[] idOneArr = {ids[i]};
				dangerGoodsStockOut.setIds(idOneArr);
				dangerGoodsStockOut.setDelBy(getUsernameAndNickName());
				dangerGoodsStockOut.setDelTime(new Date());
				dangerGoodsStockOutService.deleteDangerGoodsStockOutByIds(dangerGoodsStockOut);
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
	@SaCheckPermission("autoee:dangerGoodsStockOut:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteDangerGoodsStockOutAllData")
    public AjaxResult deleteDangerGoodsStockOutAllData() {
		return toAjaxResult(dangerGoodsStockOutService.deleteDangerGoodsStockOutAllData()>=0);
	}




}
