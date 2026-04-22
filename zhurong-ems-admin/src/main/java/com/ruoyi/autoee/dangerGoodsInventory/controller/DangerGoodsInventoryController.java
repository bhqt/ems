// 用于ems代码生成
package com.ruoyi.autoee.dangerGoodsInventory.controller;

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
import com.ruoyi.autoee.dangerGoodsInventory.domain.DangerGoodsInventory;
import com.ruoyi.autoee.dangerGoodsInventory.service.IDangerGoodsInventoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 危化品库存Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/dangerGoodsInventory")
public class DangerGoodsInventoryController extends BaseController
{
    @Autowired
    private IDangerGoodsInventoryService dangerGoodsInventoryService;

    /**
     * 查询危化品库存分页列表
     */
    @SaCheckPermission("autoee:dangerGoodsInventory:list")
    //@PreAuthorize("@ss.hasPermi('autoee:dangerGoodsInventory:list')")
    @GetMapping("/selectPageListDangerGoodsInventory")
    public TableDataInfo selectPageListDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory)
    {
        startPage();
        List<DangerGoodsInventory> list = dangerGoodsInventoryService.selectDataListByLikeDangerGoodsInventory(dangerGoodsInventory);
        return getDataTable(list);
    }

	/**
     * 查询危化品库存数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsInventory:list")
        @GetMapping("/selectDataListByLikeDangerGoodsInventory")
    public TableDataInfo selectDataListByLikeDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory)
    {
        List<DangerGoodsInventory> list = dangerGoodsInventoryService.selectDataListByLikeDangerGoodsInventory(dangerGoodsInventory);
        return getDataTable(list);
    }

	/**
     * 精确查询危化品库存数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsInventory:list")
        @GetMapping("/selectDataListByEqDangerGoodsInventory")
    public TableDataInfo selectDataListByEqDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory)
    {
        List<DangerGoodsInventory> list = dangerGoodsInventoryService.selectDataListByEqDangerGoodsInventory(dangerGoodsInventory);
        return getDataTable(list);
    }

	/**
     * 查询危化品库存详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsInventory:list")
        @GetMapping("/selectDetailListByLikeDangerGoodsInventory")
    public TableDataInfo selectDetailListByLikeDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory)
    {
        List<DangerGoodsInventory> list = dangerGoodsInventoryService.selectDetailListByLikeDangerGoodsInventory(dangerGoodsInventory);
        return getDataTable(list);
    }

	/**
     * 精确查询危化品库存详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsInventory:list")
        @GetMapping("/selectDetailListByEqDangerGoodsInventory")
    public TableDataInfo selectDetailListByEqDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory)
    {
        List<DangerGoodsInventory> list = dangerGoodsInventoryService.selectDetailListByEqDangerGoodsInventory(dangerGoodsInventory);
        return getDataTable(list);
    }

	/**
     * 导入危化品库存数据
     */
	@Log(title = "危化品库存", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:dangerGoodsInventory:import")
        @PostMapping("/importDangerGoodsInventoryData")
    public AjaxResult importDangerGoodsInventoryData(MultipartFile file, boolean updateSupport, DangerGoodsInventory dangerGoodsInventory) throws Exception
    {
        ExcelUtil<DangerGoodsInventory> util = new ExcelUtil<DangerGoodsInventory>(DangerGoodsInventory.class);
        List<DangerGoodsInventory> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = dangerGoodsInventoryService.importDangerGoodsInventoryData(dataList, updateSupport, operName, dangerGoodsInventory);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplateDangerGoodsInventory")
    public void downLoadImportTemplateDangerGoodsInventory(HttpServletResponse response)
    {
        ExcelUtil<DangerGoodsInventory> util = new ExcelUtil<DangerGoodsInventory>(DangerGoodsInventory.class);
        util.importTemplateExcel(response, "危化品库存数据");
    }

    /**
     * 导出危化品库存列表
     */
	@SaCheckPermission("autoee:dangerGoodsInventory:export")
        @Log(title = "危化品库存", businessType = BusinessType.EXPORT)
    @PostMapping("/exportDangerGoodsInventory")
    public void exportDangerGoodsInventory(HttpServletResponse response, DangerGoodsInventory dangerGoodsInventory)
    {
		dangerGoodsInventoryService.exportDataCheck(dangerGoodsInventory);
        List<DangerGoodsInventory> list = dangerGoodsInventoryService.selectExportDetailListDangerGoodsInventory(dangerGoodsInventory);
		dangerGoodsInventoryService.exportDataDeal(dangerGoodsInventory, list);
        ExcelUtil<DangerGoodsInventory> util = new ExcelUtil<DangerGoodsInventory>(DangerGoodsInventory.class);
        util.exportExcel(response, list, "危化品库存数据");
    }

    /**
     * 获取危化品库存数据信息
     */
	@SaCheckPermission("autoee:dangerGoodsInventory:query")
        @GetMapping(value = "/selectDataByPkDangerGoodsInventory/{id}")
    public AjaxResult selectDataByPkDangerGoodsInventory(@PathVariable("id") Long id)
    {
        return success(dangerGoodsInventoryService.selectDataByPkDangerGoodsInventory(id));
    }

    /**
     * 获取危化品库存详细信息，已转码
     */
	@SaCheckPermission("autoee:dangerGoodsInventory:query")
        @GetMapping(value = "/selectDetailByPkDangerGoodsInventory/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(dangerGoodsInventoryService.selectDetailByPkDangerGoodsInventory(id));
    }

    /**
     * 新增危化品库存
     */
	@SaCheckPermission("autoee:dangerGoodsInventory:add")
        @Log(title = "危化品库存", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DangerGoodsInventory dangerGoodsInventory)
    {
		dangerGoodsInventory.setUserId(getUserId());
		dangerGoodsInventory.setDeptId(getDeptId());
		dangerGoodsInventory.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    dangerGoodsInventory.setCreateTime(date);
		dangerGoodsInventory.setUpdateBy(getUsernameAndNickName());
        dangerGoodsInventory.setUpdateTime(date);
        return AjaxResult.success(dangerGoodsInventoryService.insertDangerGoodsInventory(dangerGoodsInventory));
    }

    /**
     * 修改危化品库存：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:dangerGoodsInventory:edit")
        @Log(title = "危化品库存", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByDangerGoodsInventory")
    public AjaxResult updateNullValueByDangerGoodsInventory(@RequestBody DangerGoodsInventory dangerGoodsInventory)
    {
		dangerGoodsInventory.setUpdateBy(getUsernameAndNickName());
        dangerGoodsInventory.setUpdateTime(new Date());
        return toAjaxResult(dangerGoodsInventoryService.updateNullValueByDangerGoodsInventory(dangerGoodsInventory));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:dangerGoodsInventory:edit")
        @Log(title = "危化品库存", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByDangerGoodsInventory")
    public AjaxResult updateNotNullValueByDangerGoodsInventory(@RequestBody DangerGoodsInventory dangerGoodsInventory)
    {
		dangerGoodsInventory.setUpdateBy(getUsernameAndNickName());
        dangerGoodsInventory.setUpdateTime(new Date());
        return toAjaxResult(dangerGoodsInventoryService.updateNotNullValueByDangerGoodsInventory(dangerGoodsInventory));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:dangerGoodsInventory:edit")
	    @Log(title = "危化品库存", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditDangerGoodsInventory")
	public AjaxResult submitTableEditDangerGoodsInventory(@RequestBody List<DangerGoodsInventory> dangerGoodsInventoryList) {
		String errMsg = "";
		for (int i = 0; i < dangerGoodsInventoryList.size(); i++) {
			try {
				DangerGoodsInventory dangerGoodsInventory = dangerGoodsInventoryList.get(i);
				dangerGoodsInventory.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// dangerGoodsInventory.setUpdateTime(new Date());
				dangerGoodsInventoryService.updateNullValueByDangerGoodsInventory(dangerGoodsInventory);
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
     * 删除危化品库存
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:dangerGoodsInventory:remove")
	    @Log(title = "危化品库存", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteDangerGoodsInventoryByIds/{ids}")
    public AjaxResult deleteDangerGoodsInventoryByIds(@PathVariable String[] ids)
    {
        DangerGoodsInventory dangerGoodsInventory = new DangerGoodsInventory();
        dangerGoodsInventory.setIds(ids);
        dangerGoodsInventory.setDelBy(getUsernameAndNickName());
        dangerGoodsInventory.setDelTime(new Date());
        return toAjaxResult(dangerGoodsInventoryService.deleteDangerGoodsInventoryByIds(dangerGoodsInventory));
            }

	/**
	 * 一个个删除危化品库存
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:dangerGoodsInventory:remove")
		@Log(title = "危化品库存", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteDangerGoodsInventoryOneByOne/{ids}")
	public AjaxResult deleteDangerGoodsInventoryOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				DangerGoodsInventory dangerGoodsInventory = new DangerGoodsInventory();
				String[] idOneArr = {ids[i]};
				dangerGoodsInventory.setIds(idOneArr);
				dangerGoodsInventory.setDelBy(getUsernameAndNickName());
				dangerGoodsInventory.setDelTime(new Date());
				dangerGoodsInventoryService.deleteDangerGoodsInventoryByIds(dangerGoodsInventory);
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
	@SaCheckPermission("autoee:dangerGoodsInventory:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteDangerGoodsInventoryAllData")
    public AjaxResult deleteDangerGoodsInventoryAllData() {
		return toAjaxResult(dangerGoodsInventoryService.deleteDangerGoodsInventoryAllData()>=0);
	}




}
