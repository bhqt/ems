// 用于ems代码生成
package com.ruoyi.autoee.dangerGoodsInfo.controller;

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
import com.ruoyi.autoee.dangerGoodsInfo.domain.DangerGoodsInfo;
import com.ruoyi.autoee.dangerGoodsInfo.service.IDangerGoodsInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 危化品信息管理Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/dangerGoodsInfo")
public class DangerGoodsInfoController extends BaseController
{
    @Autowired
    private IDangerGoodsInfoService dangerGoodsInfoService;

    /**
     * 查询危化品信息管理分页列表
     */
    @SaCheckPermission("autoee:dangerGoodsInfo:list")
    //@PreAuthorize("@ss.hasPermi('autoee:dangerGoodsInfo:list')")
    @GetMapping("/selectPageListDangerGoodsInfo")
    public TableDataInfo selectPageListDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo)
    {
        startPage();
        List<DangerGoodsInfo> list = dangerGoodsInfoService.selectDataListByLikeDangerGoodsInfo(dangerGoodsInfo);
        return getDataTable(list);
    }

	/**
     * 查询危化品信息管理数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsInfo:list")
        @GetMapping("/selectDataListByLikeDangerGoodsInfo")
    public TableDataInfo selectDataListByLikeDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo)
    {
        List<DangerGoodsInfo> list = dangerGoodsInfoService.selectDataListByLikeDangerGoodsInfo(dangerGoodsInfo);
        return getDataTable(list);
    }

	/**
     * 精确查询危化品信息管理数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsInfo:list")
        @GetMapping("/selectDataListByEqDangerGoodsInfo")
    public TableDataInfo selectDataListByEqDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo)
    {
        List<DangerGoodsInfo> list = dangerGoodsInfoService.selectDataListByEqDangerGoodsInfo(dangerGoodsInfo);
        return getDataTable(list);
    }

	/**
     * 查询危化品信息管理详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsInfo:list")
        @GetMapping("/selectDetailListByLikeDangerGoodsInfo")
    public TableDataInfo selectDetailListByLikeDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo)
    {
        List<DangerGoodsInfo> list = dangerGoodsInfoService.selectDetailListByLikeDangerGoodsInfo(dangerGoodsInfo);
        return getDataTable(list);
    }

	/**
     * 精确查询危化品信息管理详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:dangerGoodsInfo:list")
        @GetMapping("/selectDetailListByEqDangerGoodsInfo")
    public TableDataInfo selectDetailListByEqDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo)
    {
        List<DangerGoodsInfo> list = dangerGoodsInfoService.selectDetailListByEqDangerGoodsInfo(dangerGoodsInfo);
        return getDataTable(list);
    }

	/**
     * 导入危化品信息管理数据
     */
	@Log(title = "危化品信息管理", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:dangerGoodsInfo:import")
        @PostMapping("/importDangerGoodsInfoData")
    public AjaxResult importDangerGoodsInfoData(MultipartFile file, boolean updateSupport, DangerGoodsInfo dangerGoodsInfo) throws Exception
    {
        ExcelUtil<DangerGoodsInfo> util = new ExcelUtil<DangerGoodsInfo>(DangerGoodsInfo.class);
        List<DangerGoodsInfo> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = dangerGoodsInfoService.importDangerGoodsInfoData(dataList, updateSupport, operName, dangerGoodsInfo);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplateDangerGoodsInfo")
    public void downLoadImportTemplateDangerGoodsInfo(HttpServletResponse response)
    {
        ExcelUtil<DangerGoodsInfo> util = new ExcelUtil<DangerGoodsInfo>(DangerGoodsInfo.class);
        util.importTemplateExcel(response, "危化品信息管理数据");
    }

    /**
     * 导出危化品信息管理列表
     */
	@SaCheckPermission("autoee:dangerGoodsInfo:export")
        @Log(title = "危化品信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/exportDangerGoodsInfo")
    public void exportDangerGoodsInfo(HttpServletResponse response, DangerGoodsInfo dangerGoodsInfo)
    {
		dangerGoodsInfoService.exportDataCheck(dangerGoodsInfo);
        List<DangerGoodsInfo> list = dangerGoodsInfoService.selectExportDetailListDangerGoodsInfo(dangerGoodsInfo);
		dangerGoodsInfoService.exportDataDeal(dangerGoodsInfo, list);
        ExcelUtil<DangerGoodsInfo> util = new ExcelUtil<DangerGoodsInfo>(DangerGoodsInfo.class);
        util.exportExcel(response, list, "危化品信息管理数据");
    }

    /**
     * 获取危化品信息管理数据信息
     */
	@SaCheckPermission("autoee:dangerGoodsInfo:query")
        @GetMapping(value = "/selectDataByPkDangerGoodsInfo/{id}")
    public AjaxResult selectDataByPkDangerGoodsInfo(@PathVariable("id") Long id)
    {
        return success(dangerGoodsInfoService.selectDataByPkDangerGoodsInfo(id));
    }

    /**
     * 获取危化品信息管理详细信息，已转码
     */
	@SaCheckPermission("autoee:dangerGoodsInfo:query")
        @GetMapping(value = "/selectDetailByPkDangerGoodsInfo/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(dangerGoodsInfoService.selectDetailByPkDangerGoodsInfo(id));
    }

    /**
     * 新增危化品信息管理
     */
	@SaCheckPermission("autoee:dangerGoodsInfo:add")
        @Log(title = "危化品信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DangerGoodsInfo dangerGoodsInfo)
    {
		dangerGoodsInfo.setUserId(getUserId());
		dangerGoodsInfo.setDeptId(getDeptId());
		dangerGoodsInfo.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    dangerGoodsInfo.setCreateTime(date);
		dangerGoodsInfo.setUpdateBy(getUsernameAndNickName());
        dangerGoodsInfo.setUpdateTime(date);
        return AjaxResult.success(dangerGoodsInfoService.insertDangerGoodsInfo(dangerGoodsInfo));
    }

    /**
     * 修改危化品信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:dangerGoodsInfo:edit")
        @Log(title = "危化品信息管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByDangerGoodsInfo")
    public AjaxResult updateNullValueByDangerGoodsInfo(@RequestBody DangerGoodsInfo dangerGoodsInfo)
    {
		dangerGoodsInfo.setUpdateBy(getUsernameAndNickName());
        dangerGoodsInfo.setUpdateTime(new Date());
        return toAjaxResult(dangerGoodsInfoService.updateNullValueByDangerGoodsInfo(dangerGoodsInfo));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:dangerGoodsInfo:edit")
        @Log(title = "危化品信息管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByDangerGoodsInfo")
    public AjaxResult updateNotNullValueByDangerGoodsInfo(@RequestBody DangerGoodsInfo dangerGoodsInfo)
    {
		dangerGoodsInfo.setUpdateBy(getUsernameAndNickName());
        dangerGoodsInfo.setUpdateTime(new Date());
        return toAjaxResult(dangerGoodsInfoService.updateNotNullValueByDangerGoodsInfo(dangerGoodsInfo));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:dangerGoodsInfo:edit")
	    @Log(title = "危化品信息管理", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditDangerGoodsInfo")
	public AjaxResult submitTableEditDangerGoodsInfo(@RequestBody List<DangerGoodsInfo> dangerGoodsInfoList) {
		String errMsg = "";
		for (int i = 0; i < dangerGoodsInfoList.size(); i++) {
			try {
				DangerGoodsInfo dangerGoodsInfo = dangerGoodsInfoList.get(i);
				dangerGoodsInfo.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// dangerGoodsInfo.setUpdateTime(new Date());
				dangerGoodsInfoService.updateNullValueByDangerGoodsInfo(dangerGoodsInfo);
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
     * 删除危化品信息管理
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:dangerGoodsInfo:remove")
	    @Log(title = "危化品信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteDangerGoodsInfoByIds/{ids}")
    public AjaxResult deleteDangerGoodsInfoByIds(@PathVariable String[] ids)
    {
        DangerGoodsInfo dangerGoodsInfo = new DangerGoodsInfo();
        dangerGoodsInfo.setIds(ids);
        dangerGoodsInfo.setDelBy(getUsernameAndNickName());
        dangerGoodsInfo.setDelTime(new Date());
        return toAjaxResult(dangerGoodsInfoService.deleteDangerGoodsInfoByIds(dangerGoodsInfo));
            }

	/**
	 * 一个个删除危化品信息管理
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:dangerGoodsInfo:remove")
		@Log(title = "危化品信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteDangerGoodsInfoOneByOne/{ids}")
	public AjaxResult deleteDangerGoodsInfoOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				DangerGoodsInfo dangerGoodsInfo = new DangerGoodsInfo();
				String[] idOneArr = {ids[i]};
				dangerGoodsInfo.setIds(idOneArr);
				dangerGoodsInfo.setDelBy(getUsernameAndNickName());
				dangerGoodsInfo.setDelTime(new Date());
				dangerGoodsInfoService.deleteDangerGoodsInfoByIds(dangerGoodsInfo);
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
	@SaCheckPermission("autoee:dangerGoodsInfo:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteDangerGoodsInfoAllData")
    public AjaxResult deleteDangerGoodsInfoAllData() {
		return toAjaxResult(dangerGoodsInfoService.deleteDangerGoodsInfoAllData()>=0);
	}




}
