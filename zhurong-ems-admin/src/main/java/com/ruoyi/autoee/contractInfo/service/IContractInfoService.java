package com.ruoyi.autoee.contractInfo.service;

import com.ruoyi.common.core.domain.AjaxResult;
import java.util.List;
import com.ruoyi.autoee.contractInfo.domain.ContractInfo;

/**
 * 合同信息管理Service接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface IContractInfoService
{
    /**
     * 查询合同信息管理
     *
     * @param id 合同信息管理主键
     * @return 合同信息管理
     */
    public ContractInfo selectDataByPkContractInfo(Long id);

    /**
     * 查询合同信息管理详细信息
     *
     * @param id 合同信息管理主键
     * @return 合同信息管理
     */
    public ContractInfo selectDetailByPkContractInfo(Long id);

    /**
     * 查询合同信息管理列表
     *
     * @param contractInfo 合同信息管理
     * @return 合同信息管理集合
     */
    public List<ContractInfo> selectDataListByLikeContractInfo(ContractInfo contractInfo);

    /**
     * 精确查询合同信息管理列表
     *
     * @param contractInfo 合同信息管理
     * @return 合同信息管理集合
     */
    public List<ContractInfo> selectDataListByEqContractInfo(ContractInfo contractInfo);

	/**
     * 查询合同信息管理详细列表
     *
     * @param contractInfo 合同信息管理
     * @return 合同信息管理集合
     */
    public List<ContractInfo> selectDetailListByLikeContractInfo(ContractInfo contractInfo);

    /**
     * 精确查询合同信息管理详细列表
     *
     * @param contractInfo 合同信息管理
     * @return 合同信息管理集合
     */
    public List<ContractInfo> selectDetailListByEqContractInfo(ContractInfo contractInfo);

	/**
     * 导出合同信息管理详细列表
     *
     * @param contractInfo 合同信息管理
     * @return 合同信息管理集合
     */
	public List<ContractInfo> selectExportDetailListContractInfo(ContractInfo contractInfo);

	/**
     * 模糊查询记录数
     *
     * @param contractInfo 合同信息管理
     * @return 合同信息管理集合
     */
    public int selectCountByLikeContractInfo(ContractInfo contractInfo);

	/**
     * 精确查询记录数
     *
     * @param contractInfo 合同信息管理
     * @return 合同信息管理集合
     */
    public int selectCountByEqContractInfo(ContractInfo contractInfo);

	/**
     * 导出前校验
     */
    public void exportDataCheck(ContractInfo contractInfo);

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(ContractInfo contractInfo, List<ContractInfo> list);

    /**
     * 新增合同信息管理
     *
     * @param contractInfo 合同信息管理
     * @return 结果
     */
    public int insertContractInfo(ContractInfo contractInfo);

    /**
     * 批量新增修改合同信息管理
     *
     * @param contractInfo 合同信息管理
     * @return 结果
     */
    public void batchInsertOrUpdateByContractInfo(List<ContractInfo> contractInfos);

    /**
     * 修改合同信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param contractInfo 合同信息管理
     * @return 结果
     */
    public int updateNullValueByContractInfo(ContractInfo contractInfo);

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param contractInfo 合同信息管理
     * @return 结果
     */
    public int updateNotNullValueByContractInfo(ContractInfo contractInfo);

    /**
     * 删除合同信息管理ById
     *
     * @param id 合同信息管理主键
     * @return 结果
     */
    public int deleteContractInfoById(ContractInfo contractInfo);

    /**
     * 批量删除ContractInfoByIds
     *
     * @param ids 需要删除的合同信息管理主键集合
     * @return 结果
     */
	public int deleteContractInfoByIds(ContractInfo contractInfo);
    
    /**
     * 批量删除ContractInfoByEqContractInfo
     *
     * @return 结果
     */
    public int deleteContractInfoByEqContractInfo(ContractInfo contractInfo);

	/**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deleteContractInfoAllData();

	   /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importContractInfoData(List<ContractInfo> dataList, Boolean isUpdateSupport, String operName, ContractInfo contractInfo);


}
