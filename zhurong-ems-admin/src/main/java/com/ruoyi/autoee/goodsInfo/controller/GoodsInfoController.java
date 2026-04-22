// 用于ems代码生成
package com.ruoyi.autoee.goodsInfo.controller;

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
import com.ruoyi.autoee.goodsInfo.domain.GoodsInfo;
import com.ruoyi.autoee.goodsInfo.service.IGoodsInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物品信息管理Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/goodsInfo")
public class GoodsInfoController extends BaseController
{
    @Autowired
    private IGoodsInfoService goodsInfoService;

    /**
     * 查询物品信息管理分页列表
     */
    @SaCheckPermission("autoee:goodsInfo:list")
    //@PreAuthorize("@ss.hasPermi('autoee:goodsInfo:list')")
    @GetMapping("/selectPageListGoodsInfo")
    public TableDataInfo selectPageListGoodsInfo(GoodsInfo goodsInfo)
    {
        startPage();
        List<GoodsInfo> list = goodsInfoService.selectDataListByLikeGoodsInfo(goodsInfo);
        return getDataTable(list);
    }

	/**
     * 查询物品信息管理数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsInfo:list")
        @GetMapping("/selectDataListByLikeGoodsInfo")
    public TableDataInfo selectDataListByLikeGoodsInfo(GoodsInfo goodsInfo)
    {
        List<GoodsInfo> list = goodsInfoService.selectDataListByLikeGoodsInfo(goodsInfo);
        return getDataTable(list);
    }

	/**
     * 精确查询物品信息管理数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsInfo:list")
        @GetMapping("/selectDataListByEqGoodsInfo")
    public TableDataInfo selectDataListByEqGoodsInfo(GoodsInfo goodsInfo)
    {
        List<GoodsInfo> list = goodsInfoService.selectDataListByEqGoodsInfo(goodsInfo);
        return getDataTable(list);
    }

	/**
     * 查询物品信息管理详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsInfo:list")
        @GetMapping("/selectDetailListByLikeGoodsInfo")
    public TableDataInfo selectDetailListByLikeGoodsInfo(GoodsInfo goodsInfo)
    {
        List<GoodsInfo> list = goodsInfoService.selectDetailListByLikeGoodsInfo(goodsInfo);
        return getDataTable(list);
    }

	/**
     * 精确查询物品信息管理详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:goodsInfo:list")
        @GetMapping("/selectDetailListByEqGoodsInfo")
    public TableDataInfo selectDetailListByEqGoodsInfo(GoodsInfo goodsInfo)
    {
        List<GoodsInfo> list = goodsInfoService.selectDetailListByEqGoodsInfo(goodsInfo);
        return getDataTable(list);
    }

	/**
     * 导入物品信息管理数据
     */
	@Log(title = "物品信息管理", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:goodsInfo:import")
        @PostMapping("/importGoodsInfoData")
    public AjaxResult importGoodsInfoData(MultipartFile file, boolean updateSupport, GoodsInfo goodsInfo) throws Exception
    {
        ExcelUtil<GoodsInfo> util = new ExcelUtil<GoodsInfo>(GoodsInfo.class);
        List<GoodsInfo> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = goodsInfoService.importGoodsInfoData(dataList, updateSupport, operName, goodsInfo);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplateGoodsInfo")
    public void downLoadImportTemplateGoodsInfo(HttpServletResponse response)
    {
        ExcelUtil<GoodsInfo> util = new ExcelUtil<GoodsInfo>(GoodsInfo.class);
        util.importTemplateExcel(response, "物品信息管理数据");
    }

    /**
     * 导出物品信息管理列表
     */
	@SaCheckPermission("autoee:goodsInfo:export")
        @Log(title = "物品信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/exportGoodsInfo")
    public void exportGoodsInfo(HttpServletResponse response, GoodsInfo goodsInfo)
    {
		goodsInfoService.exportDataCheck(goodsInfo);
        List<GoodsInfo> list = goodsInfoService.selectExportDetailListGoodsInfo(goodsInfo);
		goodsInfoService.exportDataDeal(goodsInfo, list);
        ExcelUtil<GoodsInfo> util = new ExcelUtil<GoodsInfo>(GoodsInfo.class);
        util.exportExcel(response, list, "物品信息管理数据");
    }

    /**
     * 获取物品信息管理数据信息
     */
	@SaCheckPermission("autoee:goodsInfo:query")
        @GetMapping(value = "/selectDataByPkGoodsInfo/{id}")
    public AjaxResult selectDataByPkGoodsInfo(@PathVariable("id") Long id)
    {
        return success(goodsInfoService.selectDataByPkGoodsInfo(id));
    }

    /**
     * 获取物品信息管理详细信息，已转码
     */
	@SaCheckPermission("autoee:goodsInfo:query")
        @GetMapping(value = "/selectDetailByPkGoodsInfo/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(goodsInfoService.selectDetailByPkGoodsInfo(id));
    }

    /**
     * 新增物品信息管理
     */
	@SaCheckPermission("autoee:goodsInfo:add")
        @Log(title = "物品信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsInfo goodsInfo)
    {
		goodsInfo.setUserId(getUserId());
		goodsInfo.setDeptId(getDeptId());
		goodsInfo.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    goodsInfo.setCreateTime(date);
		goodsInfo.setUpdateBy(getUsernameAndNickName());
        goodsInfo.setUpdateTime(date);
        return AjaxResult.success(goodsInfoService.insertGoodsInfo(goodsInfo));
    }

    /**
     * 修改物品信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:goodsInfo:edit")
        @Log(title = "物品信息管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByGoodsInfo")
    public AjaxResult updateNullValueByGoodsInfo(@RequestBody GoodsInfo goodsInfo)
    {
		goodsInfo.setUpdateBy(getUsernameAndNickName());
        goodsInfo.setUpdateTime(new Date());
        return toAjaxResult(goodsInfoService.updateNullValueByGoodsInfo(goodsInfo));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:goodsInfo:edit")
        @Log(title = "物品信息管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByGoodsInfo")
    public AjaxResult updateNotNullValueByGoodsInfo(@RequestBody GoodsInfo goodsInfo)
    {
		goodsInfo.setUpdateBy(getUsernameAndNickName());
        goodsInfo.setUpdateTime(new Date());
        return toAjaxResult(goodsInfoService.updateNotNullValueByGoodsInfo(goodsInfo));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:goodsInfo:edit")
	    @Log(title = "物品信息管理", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditGoodsInfo")
	public AjaxResult submitTableEditGoodsInfo(@RequestBody List<GoodsInfo> goodsInfoList) {
		String errMsg = "";
		for (int i = 0; i < goodsInfoList.size(); i++) {
			try {
				GoodsInfo goodsInfo = goodsInfoList.get(i);
				goodsInfo.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// goodsInfo.setUpdateTime(new Date());
				goodsInfoService.updateNullValueByGoodsInfo(goodsInfo);
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
     * 删除物品信息管理
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:goodsInfo:remove")
	    @Log(title = "物品信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteGoodsInfoByIds/{ids}")
    public AjaxResult deleteGoodsInfoByIds(@PathVariable String[] ids)
    {
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setIds(ids);
        goodsInfo.setDelBy(getUsernameAndNickName());
        goodsInfo.setDelTime(new Date());
        return toAjaxResult(goodsInfoService.deleteGoodsInfoByIds(goodsInfo));
            }

	/**
	 * 一个个删除物品信息管理
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:goodsInfo:remove")
		@Log(title = "物品信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteGoodsInfoOneByOne/{ids}")
	public AjaxResult deleteGoodsInfoOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				GoodsInfo goodsInfo = new GoodsInfo();
				String[] idOneArr = {ids[i]};
				goodsInfo.setIds(idOneArr);
				goodsInfo.setDelBy(getUsernameAndNickName());
				goodsInfo.setDelTime(new Date());
				goodsInfoService.deleteGoodsInfoByIds(goodsInfo);
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
	@SaCheckPermission("autoee:goodsInfo:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteGoodsInfoAllData")
    public AjaxResult deleteGoodsInfoAllData() {
		return toAjaxResult(goodsInfoService.deleteGoodsInfoAllData()>=0);
	}




}
