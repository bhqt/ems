// 用于ems代码生成
package com.ruoyi.autoee.goodsStockIn.controller;

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
import com.ruoyi.autoee.goodsStockIn.domain.GoodsStockIn;
import com.ruoyi.autoee.goodsStockIn.service.IGoodsStockInService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物品入库记录Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/goodsStockIn")
public class GoodsStockInController extends BaseController
{
    @Autowired
    private IGoodsStockInService goodsStockInService;

    /**
     * 查询物品入库记录分页列表
     */
    @SaCheckPermission("autoee:goodsStockIn:list")
    //@PreAuthorize("@ss.hasPermi('autoee:goodsStockIn:list')")
    @GetMapping("/selectPageListGoodsStockIn")
    public TableDataInfo selectPageListGoodsStockIn(GoodsStockIn goodsStockIn)
    {
        startPage();
        List<GoodsStockIn> list = goodsStockInService.selectDataListByLikeGoodsStockIn(goodsStockIn);
        return getDataTable(list);
    }

	/**
     * 查询物品入库记录数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsStockIn:list")
        @GetMapping("/selectDataListByLikeGoodsStockIn")
    public TableDataInfo selectDataListByLikeGoodsStockIn(GoodsStockIn goodsStockIn)
    {
        List<GoodsStockIn> list = goodsStockInService.selectDataListByLikeGoodsStockIn(goodsStockIn);
        return getDataTable(list);
    }

	/**
     * 精确查询物品入库记录数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsStockIn:list")
        @GetMapping("/selectDataListByEqGoodsStockIn")
    public TableDataInfo selectDataListByEqGoodsStockIn(GoodsStockIn goodsStockIn)
    {
        List<GoodsStockIn> list = goodsStockInService.selectDataListByEqGoodsStockIn(goodsStockIn);
        return getDataTable(list);
    }

	/**
     * 查询物品入库记录详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsStockIn:list")
        @GetMapping("/selectDetailListByLikeGoodsStockIn")
    public TableDataInfo selectDetailListByLikeGoodsStockIn(GoodsStockIn goodsStockIn)
    {
        List<GoodsStockIn> list = goodsStockInService.selectDetailListByLikeGoodsStockIn(goodsStockIn);
        return getDataTable(list);
    }

	/**
     * 精确查询物品入库记录详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsStockIn:list")
        @GetMapping("/selectDetailListByEqGoodsStockIn")
    public TableDataInfo selectDetailListByEqGoodsStockIn(GoodsStockIn goodsStockIn)
    {
        List<GoodsStockIn> list = goodsStockInService.selectDetailListByEqGoodsStockIn(goodsStockIn);
        return getDataTable(list);
    }

	/**
     * 导入物品入库记录数据
     */
	@Log(title = "物品入库记录", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:goodsStockIn:import")
        @PostMapping("/importGoodsStockInData")
    public AjaxResult importGoodsStockInData(MultipartFile file, boolean updateSupport, GoodsStockIn goodsStockIn) throws Exception
    {
        ExcelUtil<GoodsStockIn> util = new ExcelUtil<GoodsStockIn>(GoodsStockIn.class);
        List<GoodsStockIn> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = goodsStockInService.importGoodsStockInData(dataList, updateSupport, operName, goodsStockIn);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplateGoodsStockIn")
    public void downLoadImportTemplateGoodsStockIn(HttpServletResponse response)
    {
        ExcelUtil<GoodsStockIn> util = new ExcelUtil<GoodsStockIn>(GoodsStockIn.class);
        util.importTemplateExcel(response, "物品入库记录数据");
    }

    /**
     * 导出物品入库记录列表
     */
	@SaCheckPermission("autoee:goodsStockIn:export")
        @Log(title = "物品入库记录", businessType = BusinessType.EXPORT)
    @PostMapping("/exportGoodsStockIn")
    public void exportGoodsStockIn(HttpServletResponse response, GoodsStockIn goodsStockIn)
    {
		goodsStockInService.exportDataCheck(goodsStockIn);
        List<GoodsStockIn> list = goodsStockInService.selectExportDetailListGoodsStockIn(goodsStockIn);
		goodsStockInService.exportDataDeal(goodsStockIn, list);
        ExcelUtil<GoodsStockIn> util = new ExcelUtil<GoodsStockIn>(GoodsStockIn.class);
        util.exportExcel(response, list, "物品入库记录数据");
    }

    /**
     * 获取物品入库记录数据信息
     */
	@SaCheckPermission("autoee:goodsStockIn:query")
        @GetMapping(value = "/selectDataByPkGoodsStockIn/{id}")
    public AjaxResult selectDataByPkGoodsStockIn(@PathVariable("id") Long id)
    {
        return success(goodsStockInService.selectDataByPkGoodsStockIn(id));
    }

    /**
     * 获取物品入库记录详细信息，已转码
     */
	@SaCheckPermission("autoee:goodsStockIn:query")
        @GetMapping(value = "/selectDetailByPkGoodsStockIn/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(goodsStockInService.selectDetailByPkGoodsStockIn(id));
    }

    /**
     * 新增物品入库记录
     */
	@SaCheckPermission("autoee:goodsStockIn:add")
        @Log(title = "物品入库记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsStockIn goodsStockIn)
    {
		goodsStockIn.setUserId(getUserId());
		goodsStockIn.setDeptId(getDeptId());
		goodsStockIn.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    goodsStockIn.setCreateTime(date);
		goodsStockIn.setUpdateBy(getUsernameAndNickName());
        goodsStockIn.setUpdateTime(date);
        return AjaxResult.success(goodsStockInService.insertGoodsStockIn(goodsStockIn));
    }

    /**
     * 修改物品入库记录：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:goodsStockIn:edit")
        @Log(title = "物品入库记录", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByGoodsStockIn")
    public AjaxResult updateNullValueByGoodsStockIn(@RequestBody GoodsStockIn goodsStockIn)
    {
		goodsStockIn.setUpdateBy(getUsernameAndNickName());
        goodsStockIn.setUpdateTime(new Date());
        return toAjaxResult(goodsStockInService.updateNullValueByGoodsStockIn(goodsStockIn));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:goodsStockIn:edit")
        @Log(title = "物品入库记录", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByGoodsStockIn")
    public AjaxResult updateNotNullValueByGoodsStockIn(@RequestBody GoodsStockIn goodsStockIn)
    {
		goodsStockIn.setUpdateBy(getUsernameAndNickName());
        goodsStockIn.setUpdateTime(new Date());
        return toAjaxResult(goodsStockInService.updateNotNullValueByGoodsStockIn(goodsStockIn));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:goodsStockIn:edit")
	    @Log(title = "物品入库记录", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditGoodsStockIn")
	public AjaxResult submitTableEditGoodsStockIn(@RequestBody List<GoodsStockIn> goodsStockInList) {
		String errMsg = "";
		for (int i = 0; i < goodsStockInList.size(); i++) {
			try {
				GoodsStockIn goodsStockIn = goodsStockInList.get(i);
				goodsStockIn.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// goodsStockIn.setUpdateTime(new Date());
				goodsStockInService.updateNullValueByGoodsStockIn(goodsStockIn);
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
     * 删除物品入库记录
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:goodsStockIn:remove")
	    @Log(title = "物品入库记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteGoodsStockInByIds/{ids}")
    public AjaxResult deleteGoodsStockInByIds(@PathVariable String[] ids)
    {
        GoodsStockIn goodsStockIn = new GoodsStockIn();
        goodsStockIn.setIds(ids);
        goodsStockIn.setDelBy(getUsernameAndNickName());
        goodsStockIn.setDelTime(new Date());
        return toAjaxResult(goodsStockInService.deleteGoodsStockInByIds(goodsStockIn));
            }

	/**
	 * 一个个删除物品入库记录
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:goodsStockIn:remove")
		@Log(title = "物品入库记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteGoodsStockInOneByOne/{ids}")
	public AjaxResult deleteGoodsStockInOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				GoodsStockIn goodsStockIn = new GoodsStockIn();
				String[] idOneArr = {ids[i]};
				goodsStockIn.setIds(idOneArr);
				goodsStockIn.setDelBy(getUsernameAndNickName());
				goodsStockIn.setDelTime(new Date());
				goodsStockInService.deleteGoodsStockInByIds(goodsStockIn);
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
	@SaCheckPermission("autoee:goodsStockIn:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteGoodsStockInAllData")
    public AjaxResult deleteGoodsStockInAllData() {
		return toAjaxResult(goodsStockInService.deleteGoodsStockInAllData()>=0);
	}




}
