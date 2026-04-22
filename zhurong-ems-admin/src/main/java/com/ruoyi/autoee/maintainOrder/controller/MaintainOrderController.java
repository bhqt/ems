// 用于ems代码生成
package com.ruoyi.autoee.maintainOrder.controller;

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
import com.ruoyi.autoee.maintainOrder.domain.MaintainOrder;
import com.ruoyi.autoee.maintainOrder.service.IMaintainOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 维修工单Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/maintainOrder")
public class MaintainOrderController extends BaseController
{
    @Autowired
    private IMaintainOrderService maintainOrderService;

    /**
     * 查询维修工单分页列表
     */
    @SaCheckPermission("autoee:maintainOrder:list")
    //@PreAuthorize("@ss.hasPermi('autoee:maintainOrder:list')")
    @GetMapping("/selectPageListMaintainOrder")
    public TableDataInfo selectPageListMaintainOrder(MaintainOrder maintainOrder)
    {
        startPage();
        List<MaintainOrder> list = maintainOrderService.selectDataListByLikeMaintainOrder(maintainOrder);
        return getDataTable(list);
    }

	/**
     * 查询维修工单数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:maintainOrder:list")
        @GetMapping("/selectDataListByLikeMaintainOrder")
    public TableDataInfo selectDataListByLikeMaintainOrder(MaintainOrder maintainOrder)
    {
        List<MaintainOrder> list = maintainOrderService.selectDataListByLikeMaintainOrder(maintainOrder);
        return getDataTable(list);
    }

	/**
     * 精确查询维修工单数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:maintainOrder:list")
        @GetMapping("/selectDataListByEqMaintainOrder")
    public TableDataInfo selectDataListByEqMaintainOrder(MaintainOrder maintainOrder)
    {
        List<MaintainOrder> list = maintainOrderService.selectDataListByEqMaintainOrder(maintainOrder);
        return getDataTable(list);
    }

	/**
     * 查询维修工单详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:maintainOrder:list")
        @GetMapping("/selectDetailListByLikeMaintainOrder")
    public TableDataInfo selectDetailListByLikeMaintainOrder(MaintainOrder maintainOrder)
    {
        List<MaintainOrder> list = maintainOrderService.selectDetailListByLikeMaintainOrder(maintainOrder);
        return getDataTable(list);
    }

	/**
     * 精确查询维修工单详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:maintainOrder:list")
        @GetMapping("/selectDetailListByEqMaintainOrder")
    public TableDataInfo selectDetailListByEqMaintainOrder(MaintainOrder maintainOrder)
    {
        List<MaintainOrder> list = maintainOrderService.selectDetailListByEqMaintainOrder(maintainOrder);
        return getDataTable(list);
    }

	/**
     * 导入维修工单数据
     */
	@Log(title = "维修工单", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:maintainOrder:import")
        @PostMapping("/importMaintainOrderData")
    public AjaxResult importMaintainOrderData(MultipartFile file, boolean updateSupport, MaintainOrder maintainOrder) throws Exception
    {
        ExcelUtil<MaintainOrder> util = new ExcelUtil<MaintainOrder>(MaintainOrder.class);
        List<MaintainOrder> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = maintainOrderService.importMaintainOrderData(dataList, updateSupport, operName, maintainOrder);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplateMaintainOrder")
    public void downLoadImportTemplateMaintainOrder(HttpServletResponse response)
    {
        ExcelUtil<MaintainOrder> util = new ExcelUtil<MaintainOrder>(MaintainOrder.class);
        util.importTemplateExcel(response, "维修工单数据");
    }

    /**
     * 导出维修工单列表
     */
	@SaCheckPermission("autoee:maintainOrder:export")
        @Log(title = "维修工单", businessType = BusinessType.EXPORT)
    @PostMapping("/exportMaintainOrder")
    public void exportMaintainOrder(HttpServletResponse response, MaintainOrder maintainOrder)
    {
		maintainOrderService.exportDataCheck(maintainOrder);
        List<MaintainOrder> list = maintainOrderService.selectExportDetailListMaintainOrder(maintainOrder);
		maintainOrderService.exportDataDeal(maintainOrder, list);
        ExcelUtil<MaintainOrder> util = new ExcelUtil<MaintainOrder>(MaintainOrder.class);
        util.exportExcel(response, list, "维修工单数据");
    }

    /**
     * 获取维修工单数据信息
     */
	@SaCheckPermission("autoee:maintainOrder:query")
        @GetMapping(value = "/selectDataByPkMaintainOrder/{id}")
    public AjaxResult selectDataByPkMaintainOrder(@PathVariable("id") Long id)
    {
        return success(maintainOrderService.selectDataByPkMaintainOrder(id));
    }

    /**
     * 获取维修工单详细信息，已转码
     */
	@SaCheckPermission("autoee:maintainOrder:query")
        @GetMapping(value = "/selectDetailByPkMaintainOrder/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(maintainOrderService.selectDetailByPkMaintainOrder(id));
    }

    /**
     * 新增维修工单
     */
	@SaCheckPermission("autoee:maintainOrder:add")
        @Log(title = "维修工单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaintainOrder maintainOrder)
    {
		maintainOrder.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    maintainOrder.setCreateTime(date);
		maintainOrder.setUpdateBy(getUsernameAndNickName());
        maintainOrder.setUpdateTime(date);
        return AjaxResult.success(maintainOrderService.insertMaintainOrder(maintainOrder));
    }

    /**
     * 修改维修工单：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:maintainOrder:edit")
        @Log(title = "维修工单", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByMaintainOrder")
    public AjaxResult updateNullValueByMaintainOrder(@RequestBody MaintainOrder maintainOrder)
    {
		maintainOrder.setUpdateBy(getUsernameAndNickName());
        maintainOrder.setUpdateTime(new Date());
        return toAjaxResult(maintainOrderService.updateNullValueByMaintainOrder(maintainOrder));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:maintainOrder:edit")
        @Log(title = "维修工单", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByMaintainOrder")
    public AjaxResult updateNotNullValueByMaintainOrder(@RequestBody MaintainOrder maintainOrder)
    {
		maintainOrder.setUpdateBy(getUsernameAndNickName());
        maintainOrder.setUpdateTime(new Date());
        return toAjaxResult(maintainOrderService.updateNotNullValueByMaintainOrder(maintainOrder));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:maintainOrder:edit")
	    @Log(title = "维修工单", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditMaintainOrder")
	public AjaxResult submitTableEditMaintainOrder(@RequestBody List<MaintainOrder> maintainOrderList) {
		String errMsg = "";
		for (int i = 0; i < maintainOrderList.size(); i++) {
			try {
				MaintainOrder maintainOrder = maintainOrderList.get(i);
				maintainOrder.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// maintainOrder.setUpdateTime(new Date());
				maintainOrderService.updateNullValueByMaintainOrder(maintainOrder);
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
     * 删除维修工单
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:maintainOrder:remove")
	    @Log(title = "维修工单", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteMaintainOrderByIds/{ids}")
    public AjaxResult deleteMaintainOrderByIds(@PathVariable String[] ids)
    {
        MaintainOrder maintainOrder = new MaintainOrder();
        maintainOrder.setIds(ids);
        maintainOrder.setDelBy(getUsernameAndNickName());
        maintainOrder.setDelTime(new Date());
        return toAjaxResult(maintainOrderService.deleteMaintainOrderByIds(maintainOrder));
            }

	/**
	 * 一个个删除维修工单
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:maintainOrder:remove")
		@Log(title = "维修工单", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteMaintainOrderOneByOne/{ids}")
	public AjaxResult deleteMaintainOrderOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				MaintainOrder maintainOrder = new MaintainOrder();
				String[] idOneArr = {ids[i]};
				maintainOrder.setIds(idOneArr);
				maintainOrder.setDelBy(getUsernameAndNickName());
				maintainOrder.setDelTime(new Date());
				maintainOrderService.deleteMaintainOrderByIds(maintainOrder);
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
	@SaCheckPermission("autoee:maintainOrder:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteMaintainOrderAllData")
    public AjaxResult deleteMaintainOrderAllData() {
		return toAjaxResult(maintainOrderService.deleteMaintainOrderAllData()>=0);
	}




}
