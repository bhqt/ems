// 用于ems代码生成
package com.ruoyi.autoee.contractInfo.controller;

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
import com.ruoyi.autoee.contractInfo.domain.ContractInfo;
import com.ruoyi.autoee.contractInfo.service.IContractInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 合同信息管理Controller
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@RestController
@RequestMapping("/autoee/contractInfo")
public class ContractInfoController extends BaseController
{
    @Autowired
    private IContractInfoService contractInfoService;

    /**
     * 查询合同信息管理分页列表
     */
    @SaCheckPermission("autoee:contractInfo:list")
    //@PreAuthorize("@ss.hasPermi('autoee:contractInfo:list')")
    @GetMapping("/selectPageListContractInfo")
    public TableDataInfo selectPageListContractInfo(ContractInfo contractInfo)
    {
        startPage();
        List<ContractInfo> list = contractInfoService.selectDataListByLikeContractInfo(contractInfo);
        return getDataTable(list);
    }

	/**
     * 查询合同信息管理数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:contractInfo:list")
        @GetMapping("/selectDataListByLikeContractInfo")
    public TableDataInfo selectDataListByLikeContractInfo(ContractInfo contractInfo)
    {
        List<ContractInfo> list = contractInfoService.selectDataListByLikeContractInfo(contractInfo);
        return getDataTable(list);
    }

	/**
     * 精确查询合同信息管理数据列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:contractInfo:list")
        @GetMapping("/selectDataListByEqContractInfo")
    public TableDataInfo selectDataListByEqContractInfo(ContractInfo contractInfo)
    {
        List<ContractInfo> list = contractInfoService.selectDataListByEqContractInfo(contractInfo);
        return getDataTable(list);
    }

	/**
     * 查询合同信息管理详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:contractInfo:list")
        @GetMapping("/selectDetailListByLikeContractInfo")
    public TableDataInfo selectDetailListByLikeContractInfo(ContractInfo contractInfo)
    {
        List<ContractInfo> list = contractInfoService.selectDetailListByLikeContractInfo(contractInfo);
        return getDataTable(list);
    }

	/**
     * 精确查询合同信息管理详细列表：不分页，返回全部查询结果数据
     */
	@SaCheckPermission("autoee:contractInfo:list")
        @GetMapping("/selectDetailListByEqContractInfo")
    public TableDataInfo selectDetailListByEqContractInfo(ContractInfo contractInfo)
    {
        List<ContractInfo> list = contractInfoService.selectDetailListByEqContractInfo(contractInfo);
        return getDataTable(list);
    }

	/**
     * 导入合同信息管理数据
     */
	@Log(title = "合同信息管理", businessType = BusinessType.IMPORT)
	@SaCheckPermission("autoee:contractInfo:import")
        @PostMapping("/importContractInfoData")
    public AjaxResult importContractInfoData(MultipartFile file, boolean updateSupport, ContractInfo contractInfo) throws Exception
    {
        ExcelUtil<ContractInfo> util = new ExcelUtil<ContractInfo>(ContractInfo.class);
        List<ContractInfo> dataList = util.importExcel(file.getInputStream());
        String operName = getUsernameAndNickName();
        String message = contractInfoService.importContractInfoData(dataList, updateSupport, operName, contractInfo);
        return success(message);
    }

    @PostMapping("/downLoadImportTemplateContractInfo")
    public void downLoadImportTemplateContractInfo(HttpServletResponse response)
    {
        ExcelUtil<ContractInfo> util = new ExcelUtil<ContractInfo>(ContractInfo.class);
        util.importTemplateExcel(response, "合同信息管理数据");
    }

    /**
     * 导出合同信息管理列表
     */
	@SaCheckPermission("autoee:contractInfo:export")
        @Log(title = "合同信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/exportContractInfo")
    public void exportContractInfo(HttpServletResponse response, ContractInfo contractInfo)
    {
		contractInfoService.exportDataCheck(contractInfo);
        List<ContractInfo> list = contractInfoService.selectExportDetailListContractInfo(contractInfo);
		contractInfoService.exportDataDeal(contractInfo, list);
        ExcelUtil<ContractInfo> util = new ExcelUtil<ContractInfo>(ContractInfo.class);
        util.exportExcel(response, list, "合同信息管理数据");
    }

    /**
     * 获取合同信息管理数据信息
     */
	@SaCheckPermission("autoee:contractInfo:query")
        @GetMapping(value = "/selectDataByPkContractInfo/{id}")
    public AjaxResult selectDataByPkContractInfo(@PathVariable("id") Long id)
    {
        return success(contractInfoService.selectDataByPkContractInfo(id));
    }

    /**
     * 获取合同信息管理详细信息，已转码
     */
	@SaCheckPermission("autoee:contractInfo:query")
        @GetMapping(value = "/selectDetailByPkContractInfo/{id}")
    public AjaxResult getInfoDetail(@PathVariable("id") Long id)
    {
        return success(contractInfoService.selectDetailByPkContractInfo(id));
    }

    /**
     * 新增合同信息管理
     */
	@SaCheckPermission("autoee:contractInfo:add")
        @Log(title = "合同信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ContractInfo contractInfo)
    {
		contractInfo.setUserId(getUserId());
		contractInfo.setDeptId(getDeptId());
		contractInfo.setCreateBy(getUsernameAndNickName());
		Date date = new Date();
	    contractInfo.setCreateTime(date);
		contractInfo.setUpdateBy(getUsernameAndNickName());
        contractInfo.setUpdateTime(date);
        return AjaxResult.success(contractInfoService.insertContractInfo(contractInfo));
    }

    /**
     * 修改合同信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
     */
	@SaCheckPermission("autoee:contractInfo:edit")
        @Log(title = "合同信息管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNullValueByContractInfo")
    public AjaxResult updateNullValueByContractInfo(@RequestBody ContractInfo contractInfo)
    {
		contractInfo.setUpdateBy(getUsernameAndNickName());
        contractInfo.setUpdateTime(new Date());
        return toAjaxResult(contractInfoService.updateNullValueByContractInfo(contractInfo));
    }

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     */
	@SaCheckPermission("autoee:contractInfo:edit")
        @Log(title = "合同信息管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateNotNullValueByContractInfo")
    public AjaxResult updateNotNullValueByContractInfo(@RequestBody ContractInfo contractInfo)
    {
		contractInfo.setUpdateBy(getUsernameAndNickName());
        contractInfo.setUpdateTime(new Date());
        return toAjaxResult(contractInfoService.updateNotNullValueByContractInfo(contractInfo));
    }

	/**
	 * 提交列表编辑
	 */
	@SaCheckPermission("autoee:contractInfo:edit")
	    @Log(title = "合同信息管理", businessType = BusinessType.UPDATE)
	@PutMapping(value = "/submitTableEditContractInfo")
	public AjaxResult submitTableEditContractInfo(@RequestBody List<ContractInfo> contractInfoList) {
		String errMsg = "";
		for (int i = 0; i < contractInfoList.size(); i++) {
			try {
				ContractInfo contractInfo = contractInfoList.get(i);
				contractInfo.setUpdateBy(getUsernameAndNickName());
				//提交列表编辑，不修改更新时间，否则都修改成相同的时间，顺序会变乱
				// contractInfo.setUpdateTime(new Date());
				contractInfoService.updateNullValueByContractInfo(contractInfo);
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
     * 删除合同信息管理
	 * 在同一个事务中
     */
    @SaCheckPermission("autoee:contractInfo:remove")
	    @Log(title = "合同信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteContractInfoByIds/{ids}")
    public AjaxResult deleteContractInfoByIds(@PathVariable String[] ids)
    {
        ContractInfo contractInfo = new ContractInfo();
        contractInfo.setIds(ids);
        contractInfo.setDelBy(getUsernameAndNickName());
        contractInfo.setDelTime(new Date());
        return toAjaxResult(contractInfoService.deleteContractInfoByIds(contractInfo));
            }

	/**
	 * 一个个删除合同信息管理
	 * 不在同一个事务中，当删除时需要对接第三方接口时，可以使用该方法
	 */

	@SaCheckPermission("autoee:contractInfo:remove")
		@Log(title = "合同信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteContractInfoOneByOne/{ids}")
	public AjaxResult deleteContractInfoOneByOne(@PathVariable String[] ids) {
		String errMsg = "";
		// 一个个进行删除，出现问题后，提示出现问题的信息到前端
		for (int i = 0; i < ids.length; i++) {
			try {
				ContractInfo contractInfo = new ContractInfo();
				String[] idOneArr = {ids[i]};
				contractInfo.setIds(idOneArr);
				contractInfo.setDelBy(getUsernameAndNickName());
				contractInfo.setDelTime(new Date());
				contractInfoService.deleteContractInfoByIds(contractInfo);
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
	@SaCheckPermission("autoee:contractInfo:remove")
		@Log(title = "删除全部数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteContractInfoAllData")
    public AjaxResult deleteContractInfoAllData() {
		return toAjaxResult(contractInfoService.deleteContractInfoAllData()>=0);
	}




}
