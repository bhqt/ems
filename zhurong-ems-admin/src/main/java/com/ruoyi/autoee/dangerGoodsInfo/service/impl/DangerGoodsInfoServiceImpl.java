package com.ruoyi.autoee.dangerGoodsInfo.service.impl;

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
import com.ruoyi.autoee.dangerGoodsInfo.mapper.DangerGoodsInfoMapper;
import com.ruoyi.autoee.dangerGoodsInfo.mapper.DangerGoodsInfoMapperExtend;
import com.ruoyi.autoee.dangerGoodsInfo.domain.DangerGoodsInfo;
import com.ruoyi.autoee.dangerGoodsInfo.service.IDangerGoodsInfoService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 危化品信息管理Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class DangerGoodsInfoServiceImpl implements IDangerGoodsInfoService
{
	private static final Logger logger = LoggerFactory.getLogger(DangerGoodsInfoServiceImpl.class);
    @Autowired
    private DangerGoodsInfoMapper dangerGoodsInfoMapper;
    @Autowired
    private DangerGoodsInfoMapperExtend dangerGoodsInfoMapperExtend;
    @Autowired
    private DangerGoodsInfoServiceExtend dangerGoodsInfoServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询危化品信息管理
     *
     * @param id 危化品信息管理主键
     * @return 危化品信息管理
     */
    @Override
    public DangerGoodsInfo selectDataByPkDangerGoodsInfo(Long id)
    {
        return dangerGoodsInfoMapper.selectDataByPkDangerGoodsInfo(id);
    }

    /**
     * 通过主键查询危化品信息管理详细信息
     *
     * @param id 危化品信息管理主键
     * @return 危化品信息管理
     */
    @Override
    public DangerGoodsInfo selectDetailByPkDangerGoodsInfo(Long id)
    {
		DangerGoodsInfo dangerGoodsInfo = dangerGoodsInfoMapper.selectDetailByPkDangerGoodsInfo(id);
		dangerGoodsInfoServiceExtend.selectDetailByPkDangerGoodsInfoEndExtend(dangerGoodsInfo);
        return dangerGoodsInfo;
    }

    /**
     * 查询危化品信息管理列表
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 危化品信息管理
     */
    @Override
    public List<DangerGoodsInfo> selectDataListByLikeDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo)
    {
		dangerGoodsInfoServiceExtend.selectListStartExtend(dangerGoodsInfo);
		List<DangerGoodsInfo> list = dangerGoodsInfoMapper.selectDataListByLikeDangerGoodsInfo(dangerGoodsInfo);
		dangerGoodsInfoServiceExtend.selectListEndExtend(dangerGoodsInfo, list);
        return list;
    }

    /**
     * 精确查询危化品信息管理列表
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 危化品信息管理集合
     */
    public List<DangerGoodsInfo> selectDataListByEqDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo)
	{
		dangerGoodsInfoServiceExtend.selectListStartExtend(dangerGoodsInfo);
		List<DangerGoodsInfo> list = dangerGoodsInfoMapper.selectDataListByEqDangerGoodsInfo(dangerGoodsInfo);
		dangerGoodsInfoServiceExtend.selectListEndExtend(dangerGoodsInfo, list);
        return list;
    }

	/**
     * 查询危化品信息管理详细列表
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 危化品信息管理集合
     */
    public List<DangerGoodsInfo> selectDetailListByLikeDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo)
	{
		dangerGoodsInfoServiceExtend.selectDetailListStartExtend(dangerGoodsInfo);
		List<DangerGoodsInfo> list = dangerGoodsInfoMapper.selectDetailListByLikeDangerGoodsInfo(dangerGoodsInfo);
		dangerGoodsInfoServiceExtend.selectDetailListEndExtend(dangerGoodsInfo, list);
        return list;
    }

    /**
     * 精确查询危化品信息管理详细列表
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 危化品信息管理集合
     */
    public List<DangerGoodsInfo> selectDetailListByEqDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo)
	{
		dangerGoodsInfoServiceExtend.selectDetailListByEqDangerGoodsInfoStartExtend(dangerGoodsInfo);
		List<DangerGoodsInfo> list = dangerGoodsInfoMapper.selectDetailListByEqDangerGoodsInfo(dangerGoodsInfo);
		dangerGoodsInfoServiceExtend.selectDetailListByEqDangerGoodsInfoEndExtend(dangerGoodsInfo, list);
        return list;
    }

	/**
     * 导出危化品信息管理详细列表
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 危化品信息管理集合
     */
	public List<DangerGoodsInfo> selectExportDetailListDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo){
		dangerGoodsInfoServiceExtend.selectExportDetailListStartExtend(dangerGoodsInfo);
		List<DangerGoodsInfo> list = dangerGoodsInfoMapper.selectDetailListByLikeDangerGoodsInfo(dangerGoodsInfo);
		dangerGoodsInfoServiceExtend.selectExportDetailListEndExtend(dangerGoodsInfo, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 危化品信息管理集合
     */
    public int selectCountByLikeDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo){
		return dangerGoodsInfoMapper.selectCountByLikeDangerGoodsInfo(dangerGoodsInfo);
	}

	/**
     * 精确查询记录数
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 危化品信息管理集合
     */
    public int selectCountByEqDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo){
		return dangerGoodsInfoMapper.selectCountByEqDangerGoodsInfo(dangerGoodsInfo);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(DangerGoodsInfo dangerGoodsInfo){
		dangerGoodsInfoServiceExtend.exportDataCheckExtend(dangerGoodsInfo);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(DangerGoodsInfo dangerGoodsInfo, List<DangerGoodsInfo> list){
		 dangerGoodsInfoServiceExtend.exportDataDealExtend(dangerGoodsInfo, list);
	 }

    /**
     * 新增危化品信息管理
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 结果
     */
    @Override
	@Transactional
    public int insertDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo)
    {
        dangerGoodsInfo.setCreateTime(DateUtils.getNowDate());
		dangerGoodsInfo.setUpdateTime(dangerGoodsInfo.getCreateTime());
        dangerGoodsInfoServiceExtend.insertStartExtend( dangerGoodsInfo);
		int rows = 0;
 		rows = dangerGoodsInfoMapper.insertDangerGoodsInfo(dangerGoodsInfo);
		dangerGoodsInfoServiceExtend.insertEndExtend( dangerGoodsInfo, rows);
        return Integer.parseInt(dangerGoodsInfo.getId()+"");
    }

	/**
     * 批量新增修改危化品信息管理
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByDangerGoodsInfo(List<DangerGoodsInfo> dangerGoodsInfos){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = dangerGoodsInfos.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<DangerGoodsInfo> batchList = dangerGoodsInfos.subList(i, toIndex);
			dangerGoodsInfoMapper.batchInsertOrUpdateByDangerGoodsInfo(batchList);
		}
	}

    /**
     * 修改危化品信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo)
    {
		DangerGoodsInfo oldDangerGoodsInfo = dangerGoodsInfoMapper.selectDataByPkDangerGoodsInfo(dangerGoodsInfo.getId());
        dangerGoodsInfoServiceExtend.updateStartExtend( dangerGoodsInfo, oldDangerGoodsInfo);
		int rows = 0;
		rows = dangerGoodsInfoMapper.updateNullValueByDangerGoodsInfo(dangerGoodsInfo);
		dangerGoodsInfoServiceExtend.updateEndExtend( dangerGoodsInfo,oldDangerGoodsInfo, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo)
    {
		DangerGoodsInfo oldDangerGoodsInfo = dangerGoodsInfoMapper.selectDataByPkDangerGoodsInfo(dangerGoodsInfo.getId());
        dangerGoodsInfoServiceExtend.updateStartExtend( dangerGoodsInfo, oldDangerGoodsInfo);
		int rows = 0;
		rows = dangerGoodsInfoMapper.updateNotNullValueByDangerGoodsInfo(dangerGoodsInfo);
		dangerGoodsInfoServiceExtend.updateEndExtend( dangerGoodsInfo,oldDangerGoodsInfo, rows);
        return rows;
    }

	/**
     * 删除DangerGoodsInfoById
     *
     * @param id 危化品信息管理主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteDangerGoodsInfoById(DangerGoodsInfo dangerGoodsInfo)
    {
		try {
        	return dangerGoodsInfoMapper.deleteDangerGoodsInfoById(dangerGoodsInfo);
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
     * 批量删除DangerGoodsInfoByIds
     *
     * @param ids 需要删除的危化品信息管理主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteDangerGoodsInfoByIds(DangerGoodsInfo dangerGoodsInfo) {
		try {
			dangerGoodsInfoServiceExtend.deleteByIdsStartExtend(dangerGoodsInfo);
			int rows = dangerGoodsInfoMapper.deleteDangerGoodsInfoByIds(dangerGoodsInfo);
			dangerGoodsInfoServiceExtend.deleteByIdsEndExtend(dangerGoodsInfo, rows);
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
     * 批量删除DangerGoodsInfoByEqDangerGoodsInfo
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deleteDangerGoodsInfoByEqDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo){
		try {
			dangerGoodsInfoServiceExtend.deleteByEqDangerGoodsInfoStartExtend(dangerGoodsInfo);
			int rows = dangerGoodsInfoMapper.deleteDangerGoodsInfoByEqDangerGoodsInfo(dangerGoodsInfo);
			dangerGoodsInfoServiceExtend.deleteByEqDangerGoodsInfoEndExtend(dangerGoodsInfo, rows);
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
    public int deleteDangerGoodsInfoAllData(){
		try {
			int rows = dangerGoodsInfoMapper.deleteDangerGoodsInfoAllData();
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
    public String importDangerGoodsInfoData(List<DangerGoodsInfo> dataList, Boolean isUpdateSupport, String operName, DangerGoodsInfo pDangerGoodsInfo)
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
		DangerGoodsInfo dangerGoodsInfo = new DangerGoodsInfo();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		dangerGoodsInfoServiceExtend.importDataStartExtend(dataList, pDangerGoodsInfo, operName, nowDate);

		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> dangerGoodsTypeLableValueMap =  commonService.getDictLableValueMap("danger_goods_type");
		String  dangerGoodsTypeAllDictLableStr = commonService.getDictAllLableStr("danger_goods_type");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> dangerGoodsStatusLableValueMap =  commonService.getDictLableValueMap("danger_goods_status");
		String  dangerGoodsStatusAllDictLableStr = commonService.getDictAllLableStr("danger_goods_status");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> goodsUnitLableValueMap =  commonService.getDictLableValueMap("goods_unit");
		String  goodsUnitAllDictLableStr = commonService.getDictAllLableStr("goods_unit");
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
				dangerGoodsInfo =dataList.get(i);
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(dangerGoodsInfo.getDangerGoodsName())){
					checkMsg += headString + "危化品名称字段为必填项。";
				}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(dangerGoodsInfo.getDangerGoodsTypeExtend())){
					checkMsg += headString + "危化品类型字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String dangerGoodsType = dangerGoodsInfo.getDangerGoodsTypeExtend();
                if (StrUtil.isNotBlank(dangerGoodsType)) {
					// 通过名称取对应的字典值
                    dictValue = dangerGoodsTypeLableValueMap.get(dangerGoodsType);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						dangerGoodsInfo.setDangerGoodsType(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(dangerGoodsTypeLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(dangerGoodsTypeAllDictLableStr)) {
                        	checkMsg += headString + "危化品类型字段的录入值["+ dangerGoodsType +"]必须属于以下取值范围["+dangerGoodsTypeAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "危化品类型字段的录入值["+ dangerGoodsType +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(dangerGoodsInfo.getDangerGoodsStatusExtend())){
					checkMsg += headString + "危化品状态字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String dangerGoodsStatus = dangerGoodsInfo.getDangerGoodsStatusExtend();
                if (StrUtil.isNotBlank(dangerGoodsStatus)) {
					// 通过名称取对应的字典值
                    dictValue = dangerGoodsStatusLableValueMap.get(dangerGoodsStatus);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						dangerGoodsInfo.setDangerGoodsStatus(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(dangerGoodsStatusLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(dangerGoodsStatusAllDictLableStr)) {
                        	checkMsg += headString + "危化品状态字段的录入值["+ dangerGoodsStatus +"]必须属于以下取值范围["+dangerGoodsStatusAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "危化品状态字段的录入值["+ dangerGoodsStatus +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(dangerGoodsInfo.getSpecification())){
					checkMsg += headString + "规格型号字段为必填项。";
				}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(dangerGoodsInfo.getGoodsUnitExtend())){
					checkMsg += headString + "单位字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String goodsUnit = dangerGoodsInfo.getGoodsUnitExtend();
                if (StrUtil.isNotBlank(goodsUnit)) {
					// 通过名称取对应的字典值
                    dictValue = goodsUnitLableValueMap.get(goodsUnit);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						dangerGoodsInfo.setGoodsUnit(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(goodsUnitLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(goodsUnitAllDictLableStr)) {
                        	checkMsg += headString + "单位字段的录入值["+ goodsUnit +"]必须属于以下取值范围["+goodsUnitAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "单位字段的录入值["+ goodsUnit +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }

		        // 检查完必填字段和取值范围后，在进行重复记录校验
		        if ("".equals(checkMsg)) {
			        // 进行导入扩展校验
			        checkMsg = dangerGoodsInfoServiceExtend.importDataCheckExtend(isUpdateSupport, pDangerGoodsInfo, dangerGoodsInfo, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, dangerGoodsInfo);
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
				this.batchInsertOrUpdateByDangerGoodsInfo(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		dangerGoodsInfoServiceExtend.importDataEndExtend(dataList, pDangerGoodsInfo, operName, successNum);
        return successMsg.toString();
    }



}
