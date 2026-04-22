package com.ruoyi.autoee.contractInfo.mapper;

import java.util.List;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.autoee.contractInfo.domain.ContractInfo;

/**
 * 合同信息管理Mapper接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface ContractInfoMapper extends BaseMapperPlus<ContractInfoMapper, ContractInfo, ContractInfo>
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
     * 精确查询合同信息管理列表：前主要用于校验，只能进行精确查询
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
     * 批量新增修改合同信息管理
     *
     * @param contractInfo 合同信息管理
     * @return 结果
     */
    public void batchInsertOrUpdateByContractInfo(List<ContractInfo> contractInfos);

    /**
     * 新增合同信息管理
     *
     * @param contractInfo 合同信息管理
     * @return 结果
     */
    public int insertContractInfo(ContractInfo contractInfo);

    /**
     * 修改合同信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param contractInfo 合同信息管理
     * @return 结果
     */
    public int updateNullValueByContractInfo(ContractInfo contractInfo);

	/**
     *  更新设置值的字段，未设置值的字段不进行更新
     *
     * @param contractInfo 合同信息管理
     * @return 结果
     */
    public int updateNotNullValueByContractInfo(ContractInfo contractInfo);

    /**
     * 删除合同信息管理
     *
     * @param id 合同信息管理主键
     * @return 结果
     */
    public int deleteContractInfoById(ContractInfo contractInfo);

    /**
     * 批量删除合同信息管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteContractInfoByIds(ContractInfo contractInfo);

    /**
     * 批量删除ByEqContractInfo
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


}
