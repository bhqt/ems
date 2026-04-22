package com.ruoyi.autoee.goodsStockIn.service.impl;

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
import com.ruoyi.autoee.goodsStockIn.mapper.GoodsStockInMapper;
import com.ruoyi.autoee.goodsStockIn.mapper.GoodsStockInMapperExtend;
import com.ruoyi.autoee.goodsStockIn.domain.GoodsStockIn;
import com.ruoyi.autoee.goodsStockIn.service.IGoodsStockInService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 物品入库记录Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class GoodsStockInServiceImpl implements IGoodsStockInService
{
	private static final Logger logger = LoggerFactory.getLogger(GoodsStockInServiceImpl.class);
    @Autowired
    private GoodsStockInMapper goodsStockInMapper;
    @Autowired
    private GoodsStockInMapperExtend goodsStockInMapperExtend;
    @Autowired
    private GoodsStockInServiceExtend goodsStockInServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询物品入库记录
     *
     * @param id 物品入库记录主键
     * @return 物品入库记录
     */
    @Override
    public GoodsStockIn selectDataByPkGoodsStockIn(Long id)
    {
        return goodsStockInMapper.selectDataByPkGoodsStockIn(id);
    }

    /**
     * 通过主键查询物品入库记录详细信息
     *
     * @param id 物品入库记录主键
     * @return 物品入库记录
     */
    @Override
    public GoodsStockIn selectDetailByPkGoodsStockIn(Long id)
    {
		GoodsStockIn goodsStockIn = goodsStockInMapper.selectDetailByPkGoodsStockIn(id);
		goodsStockInServiceExtend.selectDetailByPkGoodsStockInEndExtend(goodsStockIn);
        return goodsStockIn;
    }

    /**
     * 查询物品入库记录列表
     *
     * @param goodsStockIn 物品入库记录
     * @return 物品入库记录
     */
    @Override
    public List<GoodsStockIn> selectDataListByLikeGoodsStockIn(GoodsStockIn goodsStockIn)
    {
		goodsStockInServiceExtend.selectListStartExtend(goodsStockIn);
		List<GoodsStockIn> list = goodsStockInMapper.selectDataListByLikeGoodsStockIn(goodsStockIn);
		goodsStockInServiceExtend.selectListEndExtend(goodsStockIn, list);
        return list;
    }

    /**
     * 精确查询物品入库记录列表
     *
     * @param goodsStockIn 物品入库记录
     * @return 物品入库记录集合
     */
    public List<GoodsStockIn> selectDataListByEqGoodsStockIn(GoodsStockIn goodsStockIn)
	{
		goodsStockInServiceExtend.selectListStartExtend(goodsStockIn);
		List<GoodsStockIn> list = goodsStockInMapper.selectDataListByEqGoodsStockIn(goodsStockIn);
		goodsStockInServiceExtend.selectListEndExtend(goodsStockIn, list);
        return list;
    }

	/**
     * 查询物品入库记录详细列表
     *
     * @param goodsStockIn 物品入库记录
     * @return 物品入库记录集合
     */
    public List<GoodsStockIn> selectDetailListByLikeGoodsStockIn(GoodsStockIn goodsStockIn)
	{
		goodsStockInServiceExtend.selectDetailListStartExtend(goodsStockIn);
		List<GoodsStockIn> list = goodsStockInMapper.selectDetailListByLikeGoodsStockIn(goodsStockIn);
		goodsStockInServiceExtend.selectDetailListEndExtend(goodsStockIn, list);
        return list;
    }

    /**
     * 精确查询物品入库记录详细列表
     *
     * @param goodsStockIn 物品入库记录
     * @return 物品入库记录集合
     */
    public List<GoodsStockIn> selectDetailListByEqGoodsStockIn(GoodsStockIn goodsStockIn)
	{
		goodsStockInServiceExtend.selectDetailListByEqGoodsStockInStartExtend(goodsStockIn);
		List<GoodsStockIn> list = goodsStockInMapper.selectDetailListByEqGoodsStockIn(goodsStockIn);
		goodsStockInServiceExtend.selectDetailListByEqGoodsStockInEndExtend(goodsStockIn, list);
        return list;
    }

	/**
     * 导出物品入库记录详细列表
     *
     * @param goodsStockIn 物品入库记录
     * @return 物品入库记录集合
     */
	public List<GoodsStockIn> selectExportDetailListGoodsStockIn(GoodsStockIn goodsStockIn){
		goodsStockInServiceExtend.selectExportDetailListStartExtend(goodsStockIn);
		List<GoodsStockIn> list = goodsStockInMapper.selectDetailListByLikeGoodsStockIn(goodsStockIn);
		goodsStockInServiceExtend.selectExportDetailListEndExtend(goodsStockIn, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param goodsStockIn 物品入库记录
     * @return 物品入库记录集合
     */
    public int selectCountByLikeGoodsStockIn(GoodsStockIn goodsStockIn){
		return goodsStockInMapper.selectCountByLikeGoodsStockIn(goodsStockIn);
	}

	/**
     * 精确查询记录数
     *
     * @param goodsStockIn 物品入库记录
     * @return 物品入库记录集合
     */
    public int selectCountByEqGoodsStockIn(GoodsStockIn goodsStockIn){
		return goodsStockInMapper.selectCountByEqGoodsStockIn(goodsStockIn);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(GoodsStockIn goodsStockIn){
		goodsStockInServiceExtend.exportDataCheckExtend(goodsStockIn);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(GoodsStockIn goodsStockIn, List<GoodsStockIn> list){
		 goodsStockInServiceExtend.exportDataDealExtend(goodsStockIn, list);
	 }

    /**
     * 新增物品入库记录
     *
     * @param goodsStockIn 物品入库记录
     * @return 结果
     */
    @Override
	@Transactional
    public int insertGoodsStockIn(GoodsStockIn goodsStockIn)
    {
        goodsStockIn.setCreateTime(DateUtils.getNowDate());
		goodsStockIn.setUpdateTime(goodsStockIn.getCreateTime());
        goodsStockInServiceExtend.insertStartExtend( goodsStockIn);
		int rows = 0;
 		rows = goodsStockInMapper.insertGoodsStockIn(goodsStockIn);
		goodsStockInServiceExtend.insertEndExtend( goodsStockIn, rows);
        return Integer.parseInt(goodsStockIn.getId()+"");
    }

	/**
     * 批量新增修改物品入库记录
     *
     * @param goodsStockIn 物品入库记录
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByGoodsStockIn(List<GoodsStockIn> goodsStockIns){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = goodsStockIns.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<GoodsStockIn> batchList = goodsStockIns.subList(i, toIndex);
			goodsStockInMapper.batchInsertOrUpdateByGoodsStockIn(batchList);
		}
	}

    /**
     * 修改物品入库记录：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param goodsStockIn 物品入库记录
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByGoodsStockIn(GoodsStockIn goodsStockIn)
    {
		GoodsStockIn oldGoodsStockIn = goodsStockInMapper.selectDataByPkGoodsStockIn(goodsStockIn.getId());
        goodsStockInServiceExtend.updateStartExtend( goodsStockIn, oldGoodsStockIn);
		int rows = 0;
		rows = goodsStockInMapper.updateNullValueByGoodsStockIn(goodsStockIn);
		goodsStockInServiceExtend.updateEndExtend( goodsStockIn,oldGoodsStockIn, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param goodsStockIn 物品入库记录
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByGoodsStockIn(GoodsStockIn goodsStockIn)
    {
		GoodsStockIn oldGoodsStockIn = goodsStockInMapper.selectDataByPkGoodsStockIn(goodsStockIn.getId());
        goodsStockInServiceExtend.updateStartExtend( goodsStockIn, oldGoodsStockIn);
		int rows = 0;
		rows = goodsStockInMapper.updateNotNullValueByGoodsStockIn(goodsStockIn);
		goodsStockInServiceExtend.updateEndExtend( goodsStockIn,oldGoodsStockIn, rows);
        return rows;
    }

	/**
     * 删除GoodsStockInById
     *
     * @param id 物品入库记录主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGoodsStockInById(GoodsStockIn goodsStockIn)
    {
		try {
        	return goodsStockInMapper.deleteGoodsStockInById(goodsStockIn);
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
     * 批量删除GoodsStockInByIds
     *
     * @param ids 需要删除的物品入库记录主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGoodsStockInByIds(GoodsStockIn goodsStockIn) {
		try {
			goodsStockInServiceExtend.deleteByIdsStartExtend(goodsStockIn);
			int rows = goodsStockInMapper.deleteGoodsStockInByIds(goodsStockIn);
			goodsStockInServiceExtend.deleteByIdsEndExtend(goodsStockIn, rows);
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
     * 批量删除GoodsStockInByEqGoodsStockIn
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deleteGoodsStockInByEqGoodsStockIn(GoodsStockIn goodsStockIn){
		try {
			goodsStockInServiceExtend.deleteByEqGoodsStockInStartExtend(goodsStockIn);
			int rows = goodsStockInMapper.deleteGoodsStockInByEqGoodsStockIn(goodsStockIn);
			goodsStockInServiceExtend.deleteByEqGoodsStockInEndExtend(goodsStockIn, rows);
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
    public int deleteGoodsStockInAllData(){
		try {
			int rows = goodsStockInMapper.deleteGoodsStockInAllData();
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
    public String importGoodsStockInData(List<GoodsStockIn> dataList, Boolean isUpdateSupport, String operName, GoodsStockIn pGoodsStockIn)
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
		GoodsStockIn goodsStockIn = new GoodsStockIn();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		goodsStockInServiceExtend.importDataStartExtend(dataList, pGoodsStockIn, operName, nowDate);

		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> goodsIdLableValueMap =  commonService.getDictLableValueMap("a_goods_info");
		String  goodsIdAllDictLableStr = commonService.getDictAllLableStr("a_goods_info");
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
				goodsStockIn =dataList.get(i);
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(goodsStockIn.getGoodsIdExtend())){
					checkMsg += headString + "物品名称字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String goodsId = goodsStockIn.getGoodsIdExtend();
                if (StrUtil.isNotBlank(goodsId)) {
					// 通过名称取对应的字典值
                    dictValue = goodsIdLableValueMap.get(goodsId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        goodsStockIn.setGoodsId(Long.parseLong(dictValue));
                    } else {
						//allDictLableStr = CollUtil.join(goodsIdLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(goodsIdAllDictLableStr)) {
                        	checkMsg += headString + "物品名称字段的录入值["+ goodsId +"]必须属于以下取值范围["+goodsIdAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "物品名称字段的录入值["+ goodsId +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：数字类型字段
		        if (null == goodsStockIn.getQuantity()){
		        	checkMsg += headString + "入库数量字段为必填项。";
	        	}

		        // 检查完必填字段和取值范围后，在进行重复记录校验
		        if ("".equals(checkMsg)) {
			        // 进行导入扩展校验
			        checkMsg = goodsStockInServiceExtend.importDataCheckExtend(isUpdateSupport, pGoodsStockIn, goodsStockIn, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, goodsStockIn);
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
				this.batchInsertOrUpdateByGoodsStockIn(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		goodsStockInServiceExtend.importDataEndExtend(dataList, pGoodsStockIn, operName, successNum);
        return successMsg.toString();
    }



}
