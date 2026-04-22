// 用于ems代码生成
package com.ruoyi.autoee.goodsStockOut.controller;

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
import com.ruoyi.autoee.goodsStockOut.domain.GoodsStockOut;
import com.ruoyi.autoee.goodsStockOut.service.IGoodsStockOutService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物品出库记录Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/goodsStockOut")
public class GoodsStockOutController extends BaseController
{
    @Autowired
    private IGoodsStockOutService goodsStockOutService;

    /**
     * 查询物品出库记录分页列表
     */
    @SaCheckPermission("autoee:goodsStockOut:list")
    //@PreAuthorize("@ss.hasPermi('autoee:goodsStockOut:list')")
    @GetMapping("/selectPageListGoodsStockOut")
    public TableDataInfo selectPageListGoodsStockOut(GoodsStockOut goodsStockOut)
    {
        startPage();
        List<GoodsStockOut> list = goodsStockOutService.selectDataListByLikeGoodsStockOut(goodsStockOut);
        return getDataTable(list);
    }

	/**
     * 查询物品出库记录数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsStockOut:list")
        @GetMapping("/selectDataListByLikeGoodsStockOut")
    public TableDataInfo selectDataListByLikeGoodsStockOut(GoodsStockOut goodsStockOut)
    {
        List<GoodsStockOut> list = goodsStockOutService.selectDataListByLikeGoodsStockOut(goodsStockOut);
        return getDataTable(list);
    }

	/**
     * 精确查询物品出库记录数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsStockOut:list")
        @GetMapping("/selectDataListByEqGoodsStockOut")
    public TableDataInfo selectDataListByEqGoodsStockOut(GoodsStockOut goodsStockOut)
    {
        List<GoodsStockOut> list = goodsStockOutService.selectDataListByEqGoodsStockOut(goodsStockOut);
        return getDataTable(list);
    }

	/**
     * 查询物品出库记录详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsStockOut:list")
        @GetMapping("/selectDetailListByLikeGoodsStockOut")
    public TableDataInfo selectDetailListByLikeGoodsStockOut(GoodsStockOut goodsStockOut)
    {
        List<GoodsStockOut> list = goodsStockOutService.selectDetailListByLikeGoodsStockOut(goodsStockOut);
        return getDataTable(list);
    }

	/**
     * 精确查询物品出库记录详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsStockOut:list")
        @GetMapping("/selectDetailListByEqGoodsStockOut")
    public TableDataInfo selectDetailListByEqGoodsStockOut(GoodsStockOut goodsStockOut)
    {
        List<GoodsStockOut> list = goodsStockOutService.selectDetailListByEqGoodsStockOut(goodsStockOut);
        return getDataTable(list);
    }

	/**
     * 导入物品出库记录数据
     */
	@Log(title = "物品出库记录", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:goodsStockOut:import")
        @PostMapping("/importGoodsStockOutData")
    public AjaxResult importGoodsStockOutData(MultipartFile file, boolean updateSupport, GoodsStockOut goodsStockOut) throws Exception
    {
        ExcelUtil<GoodsStockOut> util = new ExcelUtil<GoodsStockOut>(GoodsStockOut.class);
        List<GoodsStockOut> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = goodsStockOutService.importGoodsStockOutData(dataList, updateSupport, operName, goodsStockOut);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplateGoodsStockOut")
    public void downLoadImportTemplateGoodsStockOut(HttpServletResponse response)
    {
        ExcelUtil<GoodsStockOut> util = new ExcelUtil<GoodsStockOut>(GoodsStockOut.class);
        util.importTemplateExcel(response, "物品出库记录数据");
    }

    /**
     * 导出物品出库记录列表
     */
	@SaCheckPermission("autoee:goodsStockOut:export")
        @Log(title = "物品出库记录", businessType = BusinessType.EXPORT)
    @PostMapping("/exportGoodsStockOut")
    public void exportGoodsStockOut(HttpServletResponse response, GoodsStockOut goodsStockOut)
    {
		goodsStockOutService.exportDataCheck(goodsStockOut);
        List<GoodsStockOut> list = goodsStockOutService.selectExportDetailListGoodsStockOut(goodsStockOut);
		goodsStockOutService.exportDataDeal(goodsStockOut, list);
        ExcelUtil<GoodsStockOut> util = new ExcelUtil<GoodsStockOut>(GoodsStockOut.class);
        util.exportExcel(response, list, "物品出库记录数据");
    }

    /**
     * 获取物品出库记录数据信息
     */
	@SaCheckPermission("autoee:goodsStockOut:query")
        @GetMapping(value = "/selectDataByPkGoodsStockOut/{id}")
    public AjaxResult selectDataByPkGoodsStockOut(@PathVariable("id") Long id)
    {
        return success(goodsStockOutService.selectDataByPkGoodsStockOut(id));
    }

    /**
     * 获取物品出库记录详细信息，已转码
     */
	@SaCheckPermission("autoee:goodsStockOut:query")
        @GetMapping(value = "/selectDetailByPkGoodsStockOut/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(goodsStockOutService.selectDetailByPkGoodsStockOut(id));
    }

    /**
     * 新增物品出库记录
     */
	@SaCheckPermission("autoee:goodsStockOut:add")
        @Log(title = "物品出库记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsStockOut goodsStockOut)
    {
		goodsStockOut.setUserId(getUserId());
		goodsStockOut.setDeptId(getDeptId());
		goodsStockOut.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    goodsStockOut.setCreateTime(date);
		goodsStockOut.setUpdateBy(getUsernameAndNickName());
        goodsStockOut.setUpdateTime(date);
        return AjaxResult.success(goodsStockOutService.insertGoodsStockOut(goodsStockOut));
    }

    /**
     * 修改物品出库记录：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:goodsStockOut:edit")
        @Log(title = "物品出库记录", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByGoodsStockOut")
    public AjaxResult updateNullValueByGoodsStockOut(@RequestBody GoodsStockOut goodsStockOut)
    {
		goodsStockOut.setUpdateBy(getUsernameAndNickName());
        goodsStockOut.setUpdateTime(new Date());
        return toAjaxResult(goodsStockOutService.updateNullValueByGoodsStockOut(goodsStockOut));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:goodsStockOut:edit")
        @Log(title = "物品出库记录", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByGoodsStockOut")
    public AjaxResult updateNotNullValueByGoodsStockOut(@RequestBody GoodsStockOut goodsStockOut)
    {
		goodsStockOut.setUpdateBy(getUsernameAndNickName());
        goodsStockOut.setUpdateTime(new Date());
        return toAjaxResult(goodsStockOutService.updateNotNullValueByGoodsStockOut(goodsStockOut));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:goodsStockOut:edit")
	    @Log(title = "物品出库记录", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditGoodsStockOut")
	public AjaxResult submitTableEditGoodsStockOut(@RequestBody List<GoodsStockOut> goodsStockOutList) {
		String errMsg = "";
		for (int i = 0; i < goodsStockOutList.size(); i++) {
			try {
				GoodsStockOut goodsStockOut = goodsStockOutList.get(i);
				goodsStockOut.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// goodsStockOut.setUpdateTime(new Date());
				goodsStockOutService.updateNullValueByGoodsStockOut(goodsStockOut);
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
     * 删除物品出库记录
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:goodsStockOut:remove")
	    @Log(title = "物品出库记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteGoodsStockOutByIds/{ids}")
    public AjaxResult deleteGoodsStockOutByIds(@PathVariable String[] ids)
    {
        GoodsStockOut goodsStockOut = new GoodsStockOut();
        goodsStockOut.setIds(ids);
        goodsStockOut.setDelBy(getUsernameAndNickName());
        goodsStockOut.setDelTime(new Date());
        return toAjaxResult(goodsStockOutService.deleteGoodsStockOutByIds(goodsStockOut));
            }

	/**
	 * 一个个删除物品出库记录
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:goodsStockOut:remove")
		@Log(title = "物品出库记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteGoodsStockOutOneByOne/{ids}")
	public AjaxResult deleteGoodsStockOutOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				GoodsStockOut goodsStockOut = new GoodsStockOut();
				String[] idOneArr = {ids[i]};
				goodsStockOut.setIds(idOneArr);
				goodsStockOut.setDelBy(getUsernameAndNickName());
				goodsStockOut.setDelTime(new Date());
				goodsStockOutService.deleteGoodsStockOutByIds(goodsStockOut);
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
	@SaCheckPermission("autoee:goodsStockOut:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteGoodsStockOutAllData")
    public AjaxResult deleteGoodsStockOutAllData() {
		return toAjaxResult(goodsStockOutService.deleteGoodsStockOutAllData()>=0);
	}




}
