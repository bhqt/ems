package com.ruoyi.autoee.goodsInfo.service.impl;

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
import com.ruoyi.autoee.goodsInfo.mapper.GoodsInfoMapper;
import com.ruoyi.autoee.goodsInfo.mapper.GoodsInfoMapperExtend;
import com.ruoyi.autoee.goodsInfo.domain.GoodsInfo;
import com.ruoyi.autoee.goodsInfo.service.IGoodsInfoService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 物品信息管理Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class GoodsInfoServiceImpl implements IGoodsInfoService
{
	private static final Logger logger = LoggerFactory.getLogger(GoodsInfoServiceImpl.class);
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired
    private GoodsInfoMapperExtend goodsInfoMapperExtend;
    @Autowired
    private GoodsInfoServiceExtend goodsInfoServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询物品信息管理
     *
     * @param id 物品信息管理主键
     * @return 物品信息管理
     */
    @Override
    public GoodsInfo selectDataByPkGoodsInfo(Long id)
    {
        return goodsInfoMapper.selectDataByPkGoodsInfo(id);
    }

    /**
     * 通过主键查询物品信息管理详细信息
     *
     * @param id 物品信息管理主键
     * @return 物品信息管理
     */
    @Override
    public GoodsInfo selectDetailByPkGoodsInfo(Long id)
    {
		GoodsInfo goodsInfo = goodsInfoMapper.selectDetailByPkGoodsInfo(id);
		goodsInfoServiceExtend.selectDetailByPkGoodsInfoEndExtend(goodsInfo);
        return goodsInfo;
    }

    /**
     * 查询物品信息管理列表
     *
     * @param goodsInfo 物品信息管理
     * @return 物品信息管理
     */
    @Override
    public List<GoodsInfo> selectDataListByLikeGoodsInfo(GoodsInfo goodsInfo)
    {
		goodsInfoServiceExtend.selectListStartExtend(goodsInfo);
		List<GoodsInfo> list = goodsInfoMapper.selectDataListByLikeGoodsInfo(goodsInfo);
		goodsInfoServiceExtend.selectListEndExtend(goodsInfo, list);
        return list;
    }

    /**
     * 精确查询物品信息管理列表
     *
     * @param goodsInfo 物品信息管理
     * @return 物品信息管理集合
     */
    public List<GoodsInfo> selectDataListByEqGoodsInfo(GoodsInfo goodsInfo)
	{
		goodsInfoServiceExtend.selectListStartExtend(goodsInfo);
		List<GoodsInfo> list = goodsInfoMapper.selectDataListByEqGoodsInfo(goodsInfo);
		goodsInfoServiceExtend.selectListEndExtend(goodsInfo, list);
        return list;
    }

	/**
     * 查询物品信息管理详细列表
     *
     * @param goodsInfo 物品信息管理
     * @return 物品信息管理集合
     */
    public List<GoodsInfo> selectDetailListByLikeGoodsInfo(GoodsInfo goodsInfo)
	{
		goodsInfoServiceExtend.selectDetailListStartExtend(goodsInfo);
		List<GoodsInfo> list = goodsInfoMapper.selectDetailListByLikeGoodsInfo(goodsInfo);
		goodsInfoServiceExtend.selectDetailListEndExtend(goodsInfo, list);
        return list;
    }

    /**
     * 精确查询物品信息管理详细列表
     *
     * @param goodsInfo 物品信息管理
     * @return 物品信息管理集合
     */
    public List<GoodsInfo> selectDetailListByEqGoodsInfo(GoodsInfo goodsInfo)
	{
		goodsInfoServiceExtend.selectDetailListByEqGoodsInfoStartExtend(goodsInfo);
		List<GoodsInfo> list = goodsInfoMapper.selectDetailListByEqGoodsInfo(goodsInfo);
		goodsInfoServiceExtend.selectDetailListByEqGoodsInfoEndExtend(goodsInfo, list);
        return list;
    }

	/**
     * 导出物品信息管理详细列表
     *
     * @param goodsInfo 物品信息管理
     * @return 物品信息管理集合
     */
	public List<GoodsInfo> selectExportDetailListGoodsInfo(GoodsInfo goodsInfo){
		goodsInfoServiceExtend.selectExportDetailListStartExtend(goodsInfo);
		List<GoodsInfo> list = goodsInfoMapper.selectDetailListByLikeGoodsInfo(goodsInfo);
		goodsInfoServiceExtend.selectExportDetailListEndExtend(goodsInfo, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param goodsInfo 物品信息管理
     * @return 物品信息管理集合
     */
    public int selectCountByLikeGoodsInfo(GoodsInfo goodsInfo){
		return goodsInfoMapper.selectCountByLikeGoodsInfo(goodsInfo);
	}

	/**
     * 精确查询记录数
     *
     * @param goodsInfo 物品信息管理
     * @return 物品信息管理集合
     */
    public int selectCountByEqGoodsInfo(GoodsInfo goodsInfo){
		return goodsInfoMapper.selectCountByEqGoodsInfo(goodsInfo);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(GoodsInfo goodsInfo){
		goodsInfoServiceExtend.exportDataCheckExtend(goodsInfo);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(GoodsInfo goodsInfo, List<GoodsInfo> list){
		 goodsInfoServiceExtend.exportDataDealExtend(goodsInfo, list);
	 }

    /**
     * 新增物品信息管理
     *
     * @param goodsInfo 物品信息管理
     * @return 结果
     */
    @Override
	@Transactional
    public int insertGoodsInfo(GoodsInfo goodsInfo)
    {
        goodsInfo.setCreateTime(DateUtils.getNowDate());
		goodsInfo.setUpdateTime(goodsInfo.getCreateTime());
        goodsInfoServiceExtend.insertStartExtend( goodsInfo);
		int rows = 0;
 		rows = goodsInfoMapper.insertGoodsInfo(goodsInfo);
		goodsInfoServiceExtend.insertEndExtend( goodsInfo, rows);
        return Integer.parseInt(goodsInfo.getId()+"");
    }

	/**
     * 批量新增修改物品信息管理
     *
     * @param goodsInfo 物品信息管理
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByGoodsInfo(List<GoodsInfo> goodsInfos){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = goodsInfos.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<GoodsInfo> batchList = goodsInfos.subList(i, toIndex);
			goodsInfoMapper.batchInsertOrUpdateByGoodsInfo(batchList);
		}
	}

    /**
     * 修改物品信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param goodsInfo 物品信息管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByGoodsInfo(GoodsInfo goodsInfo)
    {
		GoodsInfo oldGoodsInfo = goodsInfoMapper.selectDataByPkGoodsInfo(goodsInfo.getId());
        goodsInfoServiceExtend.updateStartExtend( goodsInfo, oldGoodsInfo);
		int rows = 0;
		rows = goodsInfoMapper.updateNullValueByGoodsInfo(goodsInfo);
		goodsInfoServiceExtend.updateEndExtend( goodsInfo,oldGoodsInfo, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param goodsInfo 物品信息管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByGoodsInfo(GoodsInfo goodsInfo)
    {
		GoodsInfo oldGoodsInfo = goodsInfoMapper.selectDataByPkGoodsInfo(goodsInfo.getId());
        goodsInfoServiceExtend.updateStartExtend( goodsInfo, oldGoodsInfo);
		int rows = 0;
		rows = goodsInfoMapper.updateNotNullValueByGoodsInfo(goodsInfo);
		goodsInfoServiceExtend.updateEndExtend( goodsInfo,oldGoodsInfo, rows);
        return rows;
    }

	/**
     * 删除GoodsInfoById
     *
     * @param id 物品信息管理主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGoodsInfoById(GoodsInfo goodsInfo)
    {
		try {
        	return goodsInfoMapper.deleteGoodsInfoById(goodsInfo);
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
     * 批量删除GoodsInfoByIds
     *
     * @param ids 需要删除的物品信息管理主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGoodsInfoByIds(GoodsInfo goodsInfo) {
		try {
			goodsInfoServiceExtend.deleteByIdsStartExtend(goodsInfo);
			int rows = goodsInfoMapper.deleteGoodsInfoByIds(goodsInfo);
			goodsInfoServiceExtend.deleteByIdsEndExtend(goodsInfo, rows);
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
     * 批量删除GoodsInfoByEqGoodsInfo
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deleteGoodsInfoByEqGoodsInfo(GoodsInfo goodsInfo){
		try {
			goodsInfoServiceExtend.deleteByEqGoodsInfoStartExtend(goodsInfo);
			int rows = goodsInfoMapper.deleteGoodsInfoByEqGoodsInfo(goodsInfo);
			goodsInfoServiceExtend.deleteByEqGoodsInfoEndExtend(goodsInfo, rows);
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
    public int deleteGoodsInfoAllData(){
		try {
			int rows = goodsInfoMapper.deleteGoodsInfoAllData();
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
    public String importGoodsInfoData(List<GoodsInfo> dataList, Boolean isUpdateSupport, String operName, GoodsInfo pGoodsInfo)
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
		GoodsInfo goodsInfo = new GoodsInfo();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		goodsInfoServiceExtend.importDataStartExtend(dataList, pGoodsInfo, operName, nowDate);

		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> goodsTypeLableValueMap =  commonService.getDictLableValueMap("goods_type");
		String  goodsTypeAllDictLableStr = commonService.getDictAllLableStr("goods_type");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> goodsStatusLableValueMap =  commonService.getDictLableValueMap("goods_status");
		String  goodsStatusAllDictLableStr = commonService.getDictAllLableStr("goods_status");
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
				goodsInfo =dataList.get(i);
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(goodsInfo.getGoodsName())){
					checkMsg += headString + "物品名称字段为必填项。";
				}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(goodsInfo.getGoodsTypeExtend())){
					checkMsg += headString + "物品类型字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String goodsType = goodsInfo.getGoodsTypeExtend();
                if (StrUtil.isNotBlank(goodsType)) {
					// 通过名称取对应的字典值
                    dictValue = goodsTypeLableValueMap.get(goodsType);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						goodsInfo.setGoodsType(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(goodsTypeLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(goodsTypeAllDictLableStr)) {
                        	checkMsg += headString + "物品类型字段的录入值["+ goodsType +"]必须属于以下取值范围["+goodsTypeAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "物品类型字段的录入值["+ goodsType +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(goodsInfo.getGoodsStatusExtend())){
					checkMsg += headString + "物品状态字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String goodsStatus = goodsInfo.getGoodsStatusExtend();
                if (StrUtil.isNotBlank(goodsStatus)) {
					// 通过名称取对应的字典值
                    dictValue = goodsStatusLableValueMap.get(goodsStatus);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						goodsInfo.setGoodsStatus(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(goodsStatusLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(goodsStatusAllDictLableStr)) {
                        	checkMsg += headString + "物品状态字段的录入值["+ goodsStatus +"]必须属于以下取值范围["+goodsStatusAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "物品状态字段的录入值["+ goodsStatus +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(goodsInfo.getSpecification())){
					checkMsg += headString + "规格型号字段为必填项。";
				}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(goodsInfo.getGoodsUnitExtend())){
					checkMsg += headString + "单位字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String goodsUnit = goodsInfo.getGoodsUnitExtend();
                if (StrUtil.isNotBlank(goodsUnit)) {
					// 通过名称取对应的字典值
                    dictValue = goodsUnitLableValueMap.get(goodsUnit);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						goodsInfo.setGoodsUnit(dictValue);
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
			        checkMsg = goodsInfoServiceExtend.importDataCheckExtend(isUpdateSupport, pGoodsInfo, goodsInfo, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, goodsInfo);
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
				this.batchInsertOrUpdateByGoodsInfo(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		goodsInfoServiceExtend.importDataEndExtend(dataList, pGoodsInfo, operName, successNum);
        return successMsg.toString();
    }



}
