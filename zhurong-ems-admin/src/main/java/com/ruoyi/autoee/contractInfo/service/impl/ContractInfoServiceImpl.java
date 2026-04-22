package com.ruoyi.autoee.contractInfo.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import javax.validation.Validator;
import com.ruoyi.common.utils.bean.BeanValidators;
import org.springframework.dao.DataIntegrityViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import com.ruoyi.common.exception.ServiceException;
import java.util.List;
import java.util.HashMap;
import java.util.Date;
import com.ruoyi.common.utils.StringUtils;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.service.CommonService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.dao.DuplicateKeyException;
import com.ruoyi.autoee.contractInfo.mapper.ContractInfoMapper;
import com.ruoyi.autoee.contractInfo.mapper.ContractInfoMapperExtend;
import com.ruoyi.autoee.contractInfo.domain.ContractInfo;
import com.ruoyi.autoee.contractInfo.service.IContractInfoService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 合同信息管理Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class ContractInfoServiceImpl implements IContractInfoService
{
	private static final Logger logger = LoggerFactory.getLogger(ContractInfoServiceImpl.class);
    @Autowired
    private ContractInfoMapper contractInfoMapper;
    @Autowired
    private ContractInfoMapperExtend contractInfoMapperExtend;
    @Autowired
    private ContractInfoServiceExtend contractInfoServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询合同信息管理
     *
     * @param id 合同信息管理主键
     * @return 合同信息管理
     */
    @Override
    public ContractInfo selectDataByPkContractInfo(Long id)
    {
        return contractInfoMapper.selectDataByPkContractInfo(id);
    }

    /**
     * 通过主键查询合同信息管理详细信息
     *
     * @param id 合同信息管理主键
     * @return 合同信息管理
     */
    @Override
    public ContractInfo selectDetailByPkContractInfo(Long id)
    {
		ContractInfo contractInfo = contractInfoMapper.selectDetailByPkContractInfo(id);
		contractInfoServiceExtend.selectDetailByPkContractInfoEndExtend(contractInfo);
        return contractInfo;
    }

    /**
     * 查询合同信息管理列表
     *
     * @param contractInfo 合同信息管理
     * @return 合同信息管理
     */
    @Override
    public List<ContractInfo> selectDataListByLikeContractInfo(ContractInfo contractInfo)
    {
		contractInfoServiceExtend.selectListStartExtend(contractInfo);
		List<ContractInfo> list = contractInfoMapper.selectDataListByLikeContractInfo(contractInfo);
		contractInfoServiceExtend.selectListEndExtend(contractInfo, list);
        return list;
    }

    /**
     * 精确查询合同信息管理列表
     *
     * @param contractInfo 合同信息管理
     * @return 合同信息管理集合
     */
    public List<ContractInfo> selectDataListByEqContractInfo(ContractInfo contractInfo)
	{
		contractInfoServiceExtend.selectListStartExtend(contractInfo);
		List<ContractInfo> list = contractInfoMapper.selectDataListByEqContractInfo(contractInfo);
		contractInfoServiceExtend.selectListEndExtend(contractInfo, list);
        return list;
    }

	/**
     * 查询合同信息管理详细列表
     *
     * @param contractInfo 合同信息管理
     * @return 合同信息管理集合
     */
    public List<ContractInfo> selectDetailListByLikeContractInfo(ContractInfo contractInfo)
	{
		contractInfoServiceExtend.selectDetailListStartExtend(contractInfo);
		List<ContractInfo> list = contractInfoMapper.selectDetailListByLikeContractInfo(contractInfo);
		contractInfoServiceExtend.selectDetailListEndExtend(contractInfo, list);
        return list;
    }

    /**
     * 精确查询合同信息管理详细列表
     *
     * @param contractInfo 合同信息管理
     * @return 合同信息管理集合
     */
    public List<ContractInfo> selectDetailListByEqContractInfo(ContractInfo contractInfo)
	{
		contractInfoServiceExtend.selectDetailListByEqContractInfoStartExtend(contractInfo);
		List<ContractInfo> list = contractInfoMapper.selectDetailListByEqContractInfo(contractInfo);
		contractInfoServiceExtend.selectDetailListByEqContractInfoEndExtend(contractInfo, list);
        return list;
    }

	/**
     * 导出合同信息管理详细列表
     *
     * @param contractInfo 合同信息管理
     * @return 合同信息管理集合
     */
	public List<ContractInfo> selectExportDetailListContractInfo(ContractInfo contractInfo){
		contractInfoServiceExtend.selectExportDetailListStartExtend(contractInfo);
		List<ContractInfo> list = contractInfoMapper.selectDetailListByLikeContractInfo(contractInfo);
		contractInfoServiceExtend.selectExportDetailListEndExtend(contractInfo, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param contractInfo 合同信息管理
     * @return 合同信息管理集合
     */
    public int selectCountByLikeContractInfo(ContractInfo contractInfo){
		return contractInfoMapper.selectCountByLikeContractInfo(contractInfo);
	}

	/**
     * 精确查询记录数
     *
     * @param contractInfo 合同信息管理
     * @return 合同信息管理集合
     */
    public int selectCountByEqContractInfo(ContractInfo contractInfo){
		return contractInfoMapper.selectCountByEqContractInfo(contractInfo);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(ContractInfo contractInfo){
		contractInfoServiceExtend.exportDataCheckExtend(contractInfo);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(ContractInfo contractInfo, List<ContractInfo> list){
		 contractInfoServiceExtend.exportDataDealExtend(contractInfo, list);
	 }

    /**
     * 新增合同信息管理
     *
     * @param contractInfo 合同信息管理
     * @return 结果
     */
    @Override
	@Transactional
    public int insertContractInfo(ContractInfo contractInfo)
    {
        contractInfo.setCreateTime(DateUtils.getNowDate());
		contractInfo.setUpdateTime(contractInfo.getCreateTime());
        contractInfoServiceExtend.insertStartExtend( contractInfo);
		int rows = 0;
 		rows = contractInfoMapper.insertContractInfo(contractInfo);
		contractInfoServiceExtend.insertEndExtend( contractInfo, rows);
        return Integer.parseInt(contractInfo.getId()+"");
    }

	/**
     * 批量新增修改合同信息管理
     *
     * @param contractInfo 合同信息管理
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByContractInfo(List<ContractInfo> contractInfos){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = contractInfos.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<ContractInfo> batchList = contractInfos.subList(i, toIndex);
			contractInfoMapper.batchInsertOrUpdateByContractInfo(batchList);
		}
	}

    /**
     * 修改合同信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param contractInfo 合同信息管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByContractInfo(ContractInfo contractInfo)
    {
		ContractInfo oldContractInfo = contractInfoMapper.selectDataByPkContractInfo(contractInfo.getId());
        contractInfoServiceExtend.updateStartExtend( contractInfo, oldContractInfo);
		int rows = 0;
		rows = contractInfoMapper.updateNullValueByContractInfo(contractInfo);
		contractInfoServiceExtend.updateEndExtend( contractInfo,oldContractInfo, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param contractInfo 合同信息管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByContractInfo(ContractInfo contractInfo)
    {
		ContractInfo oldContractInfo = contractInfoMapper.selectDataByPkContractInfo(contractInfo.getId());
        contractInfoServiceExtend.updateStartExtend( contractInfo, oldContractInfo);
		int rows = 0;
		rows = contractInfoMapper.updateNotNullValueByContractInfo(contractInfo);
		contractInfoServiceExtend.updateEndExtend( contractInfo,oldContractInfo, rows);
        return rows;
    }

	/**
     * 删除ContractInfoById
     *
     * @param id 合同信息管理主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteContractInfoById(ContractInfo contractInfo)
    {
		try {
        	return contractInfoMapper.deleteContractInfoById(contractInfo);
		} catch (DataIntegrityViolationException e) {
			// 提取根本原因
			Throwable rootCause = e.getRootCause();
			if (rootCause instanceof SQLIntegrityConstraintViolationException) {
				SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) rootCause;
				// MySQL 错误码 1451 表示外键约束冲突
				if (sqlEx.getErrorCode() == 1451) {
					throw new ServiceException("删除失败：当前删除记录下存在关联信息！请先删除所有关联信息后再进行删除。");
				}
			}
			// 其他类型异常继续抛出
			throw e;
		}
    }

    /**
     * 批量删除ContractInfoByIds
     *
     * @param ids 需要删除的合同信息管理主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteContractInfoByIds(ContractInfo contractInfo) {
		try {
			contractInfoServiceExtend.deleteByIdsStartExtend(contractInfo);
			int rows = contractInfoMapper.deleteContractInfoByIds(contractInfo);
			contractInfoServiceExtend.deleteByIdsEndExtend(contractInfo, rows);
			return rows;
		} catch (DataIntegrityViolationException e) {
			// 提取根本原因
			Throwable rootCause = e.getRootCause();
			if (rootCause instanceof SQLIntegrityConstraintViolationException) {
				SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) rootCause;
				// MySQL 错误码 1451 表示外键约束冲突
				if (sqlEx.getErrorCode() == 1451) {
					throw new ServiceException("删除失败：当前删除记录下存在关联信息！请先删除所有关联信息后再进行删除。");
				}
			}
			// 其他类型异常继续抛出
			throw e;
		}
    }

	/**
     * 批量删除ContractInfoByEqContractInfo
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deleteContractInfoByEqContractInfo(ContractInfo contractInfo){
		try {
			contractInfoServiceExtend.deleteByEqContractInfoStartExtend(contractInfo);
			int rows = contractInfoMapper.deleteContractInfoByEqContractInfo(contractInfo);
			contractInfoServiceExtend.deleteByEqContractInfoEndExtend(contractInfo, rows);
       	 	return rows;
		} catch (DataIntegrityViolationException e) {
			// 提取根本原因
			Throwable rootCause = e.getRootCause();
			if (rootCause instanceof SQLIntegrityConstraintViolationException) {
				SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) rootCause;
				// MySQL 错误码 1451 表示外键约束冲突
				if (sqlEx.getErrorCode() == 1451) {
					throw new ServiceException("删除失败：当前删除记录下存在关联信息！请先删除所有关联信息后再进行删除。");
				}
			}
			// 其他类型异常继续抛出
			throw e;
		}
	}

	/**
     * 删除全部数据
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deleteContractInfoAllData(){
		try {
			int rows = contractInfoMapper.deleteContractInfoAllData();
       	 	return rows;
		} catch (DataIntegrityViolationException e) {
			// 提取根本原因
			Throwable rootCause = e.getRootCause();
			if (rootCause instanceof SQLIntegrityConstraintViolationException) {
				SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) rootCause;
				// MySQL 错误码 1451 表示外键约束冲突
				if (sqlEx.getErrorCode() == 1451) {
					throw new ServiceException("删除失败：当前删除记录下存在关联信息！请先删除所有关联信息后再进行删除。");
				}
			}
			// 其他类型异常继续抛出
			throw e;
		}
	}



   /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
	// @Transactional 导入不开启事务，避免事务过大，每批次写入后即提交事务，数据导入一半，可以重新导入时自动更新
    public String importContractInfoData(List<ContractInfo> dataList, Boolean isUpdateSupport, String operName, ContractInfo pContractInfo)
    {
        if (StringUtils.isNull(dataList) || dataList.size() == 0)
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
		String headString = "<br> - ";
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
		ContractInfo contractInfo = new ContractInfo();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		contractInfoServiceExtend.importDataStartExtend(dataList, pContractInfo, operName, nowDate);

		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> contractTypeLableValueMap =  commonService.getDictLableValueMap("contract_type");
		String  contractTypeAllDictLableStr = commonService.getDictAllLableStr("contract_type");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> contractSubtypeLableValueMap =  commonService.getDictLableValueMap("contract_subtype");
		String  contractSubtypeAllDictLableStr = commonService.getDictAllLableStr("contract_subtype");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> userIdLableValueMap =  commonService.getDictLableValueMap("sys_user");
		String  userIdAllDictLableStr = commonService.getDictAllLableStr("sys_user");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> deptIdLableValueMap =  commonService.getDictLableValueMap("sys_dept");
		String  deptIdAllDictLableStr = commonService.getDictAllLableStr("sys_dept");


		// 将导入的汉字转为对应字典的value后存入数据库
		String dictValue = "";
        for (int i=0;i<dataList.size();i++) {
	        String checkMsg = "";
	        try {
				contractInfo =dataList.get(i);
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(contractInfo.getContractNoNew())){
					checkMsg += headString + "合同编号(新)字段为必填项。";
				}
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(contractInfo.getBelongCustomer())){
					checkMsg += headString + "所属客户字段为必填项。";
				}
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(contractInfo.getCustomerContact())){
					checkMsg += headString + "客户方联系人字段为必填项。";
				}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(contractInfo.getContractTypeExtend())){
					checkMsg += headString + "合同类型字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String contractType = contractInfo.getContractTypeExtend();
                if (StrUtil.isNotBlank(contractType)) {
					// 通过名称取对应的字典值
                    dictValue = contractTypeLableValueMap.get(contractType);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						contractInfo.setContractType(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(contractTypeLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(contractTypeAllDictLableStr)) {
                        	checkMsg += headString + "合同类型字段的录入值["+ contractType +"]必须属于以下取值范围["+contractTypeAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "合同类型字段的录入值["+ contractType +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String contractSubtype = contractInfo.getContractSubtypeExtend();
                if (StrUtil.isNotBlank(contractSubtype)) {
					// 通过名称取对应的字典值
                    dictValue = contractSubtypeLableValueMap.get(contractSubtype);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						contractInfo.setContractSubtype(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(contractSubtypeLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(contractSubtypeAllDictLableStr)) {
                        	checkMsg += headString + "合同子类型字段的录入值["+ contractSubtype +"]必须属于以下取值范围["+contractSubtypeAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "合同子类型字段的录入值["+ contractSubtype +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查日期类型
		        if (null != contractInfo.getSignDate() && DateUtil.isSameDay(contractInfo.getSignDate(), DateUtil.parseDate("9999-09-09"))){
		        	checkMsg += headString + "签约日期字段为日期类型，格式必须属于以下范围[yyyy-MM-dd,yyyy/MM/dd]。";
	        	}
			
		        // 检查完必填字段和取值范围后，在进行重复记录校验
		        if ("".equals(checkMsg)) {
			        // 进行导入扩展校验
			        checkMsg = contractInfoServiceExtend.importDataCheckExtend(isUpdateSupport, pContractInfo, contractInfo, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, contractInfo);
			        }
		        }

	        } catch (Exception e) {
		        checkMsg += headString + "数据导入时出现异常！" + e.getMessage();
		        logger.error("数据导入时出现异常！", e);
	        }
	        if (!"".equals(checkMsg)) {
		        failureNum++;
		        String msg = "<br/>▶第【" + (i + 1) + "】条数据导入失败：" + checkMsg;
		        failureMsg.append(msg);
	        } else{
				successNum++;
	        }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共有 " + failureNum + " 条数据存在问题，请修正后再重新导入。具体如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
			try{
				// 批量插入数据
				this.batchInsertOrUpdateByContractInfo(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		contractInfoServiceExtend.importDataEndExtend(dataList, pContractInfo, operName, successNum);
        return successMsg.toString();
    }



}
