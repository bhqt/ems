// 用于ems代码生成
package com.ruoyi.autoee.goodsInventory.controller;

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
import com.ruoyi.autoee.goodsInventory.domain.GoodsInventory;
import com.ruoyi.autoee.goodsInventory.service.IGoodsInventoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物品库存Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/goodsInventory")
public class GoodsInventoryController extends BaseController
{
    @Autowired
    private IGoodsInventoryService goodsInventoryService;

    /**
     * 查询物品库存分页列表
     */
    @SaCheckPermission("autoee:goodsInventory:list")
    //@PreAuthorize("@ss.hasPermi('autoee:goodsInventory:list')")
    @GetMapping("/selectPageListGoodsInventory")
    public TableDataInfo selectPageListGoodsInventory(GoodsInventory goodsInventory)
    {
        startPage();
        List<GoodsInventory> list = goodsInventoryService.selectDataListByLikeGoodsInventory(goodsInventory);
        return getDataTable(list);
    }

	/**
     * 查询物品库存数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsInventory:list")
        @GetMapping("/selectDataListByLikeGoodsInventory")
    public TableDataInfo selectDataListByLikeGoodsInventory(GoodsInventory goodsInventory)
    {
        List<GoodsInventory> list = goodsInventoryService.selectDataListByLikeGoodsInventory(goodsInventory);
        return getDataTable(list);
    }

	/**
     * 精确查询物品库存数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsInventory:list")
        @GetMapping("/selectDataListByEqGoodsInventory")
    public TableDataInfo selectDataListByEqGoodsInventory(GoodsInventory goodsInventory)
    {
        List<GoodsInventory> list = goodsInventoryService.selectDataListByEqGoodsInventory(goodsInventory);
        return getDataTable(list);
    }

	/**
     * 查询物品库存详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsInventory:list")
        @GetMapping("/selectDetailListByLikeGoodsInventory")
    public TableDataInfo selectDetailListByLikeGoodsInventory(GoodsInventory goodsInventory)
    {
        List<GoodsInventory> list = goodsInventoryService.selectDetailListByLikeGoodsInventory(goodsInventory);
        return getDataTable(list);
    }

	/**
     * 精确查询物品库存详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsInventory:list")
        @GetMapping("/selectDetailListByEqGoodsInventory")
    public TableDataInfo selectDetailListByEqGoodsInventory(GoodsInventory goodsInventory)
    {
        List<GoodsInventory> list = goodsInventoryService.selectDetailListByEqGoodsInventory(goodsInventory);
        return getDataTable(list);
    }

	/**
     * 导入物品库存数据
     */
	@Log(title = "物品库存", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:goodsInventory:import")
        @PostMapping("/importGoodsInventoryData")
    public AjaxResult importGoodsInventoryData(MultipartFile file, boolean updateSupport, GoodsInventory goodsInventory) throws Exception
    {
        ExcelUtil<GoodsInventory> util = new ExcelUtil<GoodsInventory>(GoodsInventory.class);
        List<GoodsInventory> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = goodsInventoryService.importGoodsInventoryData(dataList, updateSupport, operName, goodsInventory);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplateGoodsInventory")
    public void downLoadImportTemplateGoodsInventory(HttpServletResponse response)
    {
        ExcelUtil<GoodsInventory> util = new ExcelUtil<GoodsInventory>(GoodsInventory.class);
        util.importTemplateExcel(response, "物品库存数据");
    }

    /**
     * 导出物品库存列表
     */
	@SaCheckPermission("autoee:goodsInventory:export")
        @Log(title = "物品库存", businessType = BusinessType.EXPORT)
    @PostMapping("/exportGoodsInventory")
    public void exportGoodsInventory(HttpServletResponse response, GoodsInventory goodsInventory)
    {
		goodsInventoryService.exportDataCheck(goodsInventory);
        List<GoodsInventory> list = goodsInventoryService.selectExportDetailListGoodsInventory(goodsInventory);
		goodsInventoryService.exportDataDeal(goodsInventory, list);
        ExcelUtil<GoodsInventory> util = new ExcelUtil<GoodsInventory>(GoodsInventory.class);
        util.exportExcel(response, list, "物品库存数据");
    }

    /**
     * 获取物品库存数据信息
     */
	@SaCheckPermission("autoee:goodsInventory:query")
        @GetMapping(value = "/selectDataByPkGoodsInventory/{id}")
    public AjaxResult selectDataByPkGoodsInventory(@PathVariable("id") Long id)
    {
        return success(goodsInventoryService.selectDataByPkGoodsInventory(id));
    }

    /**
     * 获取物品库存详细信息，已转码
     */
	@SaCheckPermission("autoee:goodsInventory:query")
        @GetMapping(value = "/selectDetailByPkGoodsInventory/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(goodsInventoryService.selectDetailByPkGoodsInventory(id));
    }

    /**
     * 新增物品库存
     */
	@SaCheckPermission("autoee:goodsInventory:add")
        @Log(title = "物品库存", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsInventory goodsInventory)
    {
		goodsInventory.setUserId(getUserId());
		goodsInventory.setDeptId(getDeptId());
		goodsInventory.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    goodsInventory.setCreateTime(date);
		goodsInventory.setUpdateBy(getUsernameAndNickName());
        goodsInventory.setUpdateTime(date);
        return AjaxResult.success(goodsInventoryService.insertGoodsInventory(goodsInventory));
    }

    /**
     * 修改物品库存：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:goodsInventory:edit")
        @Log(title = "物品库存", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByGoodsInventory")
    public AjaxResult updateNullValueByGoodsInventory(@RequestBody GoodsInventory goodsInventory)
    {
		goodsInventory.setUpdateBy(getUsernameAndNickName());
        goodsInventory.setUpdateTime(new Date());
        return toAjaxResult(goodsInventoryService.updateNullValueByGoodsInventory(goodsInventory));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:goodsInventory:edit")
        @Log(title = "物品库存", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByGoodsInventory")
    public AjaxResult updateNotNullValueByGoodsInventory(@RequestBody GoodsInventory goodsInventory)
    {
		goodsInventory.setUpdateBy(getUsernameAndNickName());
        goodsInventory.setUpdateTime(new Date());
        return toAjaxResult(goodsInventoryService.updateNotNullValueByGoodsInventory(goodsInventory));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:goodsInventory:edit")
	    @Log(title = "物品库存", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditGoodsInventory")
	public AjaxResult submitTableEditGoodsInventory(@RequestBody List<GoodsInventory> goodsInventoryList) {
		String errMsg = "";
		for (int i = 0; i < goodsInventoryList.size(); i++) {
			try {
				GoodsInventory goodsInventory = goodsInventoryList.get(i);
				goodsInventory.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// goodsInventory.setUpdateTime(new Date());
				goodsInventoryService.updateNullValueByGoodsInventory(goodsInventory);
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
     * 删除物品库存
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:goodsInventory:remove")
	    @Log(title = "物品库存", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteGoodsInventoryByIds/{ids}")
    public AjaxResult deleteGoodsInventoryByIds(@PathVariable String[] ids)
    {
        GoodsInventory goodsInventory = new GoodsInventory();
        goodsInventory.setIds(ids);
        goodsInventory.setDelBy(getUsernameAndNickName());
        goodsInventory.setDelTime(new Date());
        return toAjaxResult(goodsInventoryService.deleteGoodsInventoryByIds(goodsInventory));
            }

	/**
	 * 一个个删除物品库存
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:goodsInventory:remove")
		@Log(title = "物品库存", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteGoodsInventoryOneByOne/{ids}")
	public AjaxResult deleteGoodsInventoryOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				GoodsInventory goodsInventory = new GoodsInventory();
				String[] idOneArr = {ids[i]};
				goodsInventory.setIds(idOneArr);
				goodsInventory.setDelBy(getUsernameAndNickName());
				goodsInventory.setDelTime(new Date());
				goodsInventoryService.deleteGoodsInventoryByIds(goodsInventory);
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
	@SaCheckPermission("autoee:goodsInventory:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteGoodsInventoryAllData")
    public AjaxResult deleteGoodsInventoryAllData() {
		return toAjaxResult(goodsInventoryService.deleteGoodsInventoryAllData()>=0);
	}




}
