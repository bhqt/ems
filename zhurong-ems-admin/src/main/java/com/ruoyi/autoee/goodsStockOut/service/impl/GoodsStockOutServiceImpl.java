package com.ruoyi.autoee.goodsStockOut.service.impl;

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
import com.ruoyi.autoee.goodsStockOut.mapper.GoodsStockOutMapper;
import com.ruoyi.autoee.goodsStockOut.mapper.GoodsStockOutMapperExtend;
import com.ruoyi.autoee.goodsStockOut.domain.GoodsStockOut;
import com.ruoyi.autoee.goodsStockOut.service.IGoodsStockOutService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 物品出库记录Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class GoodsStockOutServiceImpl implements IGoodsStockOutService
{
	private static final Logger logger = LoggerFactory.getLogger(GoodsStockOutServiceImpl.class);
    @Autowired
    private GoodsStockOutMapper goodsStockOutMapper;
    @Autowired
    private GoodsStockOutMapperExtend goodsStockOutMapperExtend;
    @Autowired
    private GoodsStockOutServiceExtend goodsStockOutServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询物品出库记录
     *
     * @param id 物品出库记录主键
     * @return 物品出库记录
     */
    @Override
    public GoodsStockOut selectDataByPkGoodsStockOut(Long id)
    {
        return goodsStockOutMapper.selectDataByPkGoodsStockOut(id);
    }

    /**
     * 通过主键查询物品出库记录详细信息
     *
     * @param id 物品出库记录主键
     * @return 物品出库记录
     */
    @Override
    public GoodsStockOut selectDetailByPkGoodsStockOut(Long id)
    {
		GoodsStockOut goodsStockOut = goodsStockOutMapper.selectDetailByPkGoodsStockOut(id);
		goodsStockOutServiceExtend.selectDetailByPkGoodsStockOutEndExtend(goodsStockOut);
        return goodsStockOut;
    }

    /**
     * 查询物品出库记录列表
     *
     * @param goodsStockOut 物品出库记录
     * @return 物品出库记录
     */
    @Override
    public List<GoodsStockOut> selectDataListByLikeGoodsStockOut(GoodsStockOut goodsStockOut)
    {
		goodsStockOutServiceExtend.selectListStartExtend(goodsStockOut);
		List<GoodsStockOut> list = goodsStockOutMapper.selectDataListByLikeGoodsStockOut(goodsStockOut);
		goodsStockOutServiceExtend.selectListEndExtend(goodsStockOut, list);
        return list;
    }

    /**
     * 精确查询物品出库记录列表
     *
     * @param goodsStockOut 物品出库记录
     * @return 物品出库记录集合
     */
    public List<GoodsStockOut> selectDataListByEqGoodsStockOut(GoodsStockOut goodsStockOut)
	{
		goodsStockOutServiceExtend.selectListStartExtend(goodsStockOut);
		List<GoodsStockOut> list = goodsStockOutMapper.selectDataListByEqGoodsStockOut(goodsStockOut);
		goodsStockOutServiceExtend.selectListEndExtend(goodsStockOut, list);
        return list;
    }

	/**
     * 查询物品出库记录详细列表
     *
     * @param goodsStockOut 物品出库记录
     * @return 物品出库记录集合
     */
    public List<GoodsStockOut> selectDetailListByLikeGoodsStockOut(GoodsStockOut goodsStockOut)
	{
		goodsStockOutServiceExtend.selectDetailListStartExtend(goodsStockOut);
		List<GoodsStockOut> list = goodsStockOutMapper.selectDetailListByLikeGoodsStockOut(goodsStockOut);
		goodsStockOutServiceExtend.selectDetailListEndExtend(goodsStockOut, list);
        return list;
    }

    /**
     * 精确查询物品出库记录详细列表
     *
     * @param goodsStockOut 物品出库记录
     * @return 物品出库记录集合
     */
    public List<GoodsStockOut> selectDetailListByEqGoodsStockOut(GoodsStockOut goodsStockOut)
	{
		goodsStockOutServiceExtend.selectDetailListByEqGoodsStockOutStartExtend(goodsStockOut);
		List<GoodsStockOut> list = goodsStockOutMapper.selectDetailListByEqGoodsStockOut(goodsStockOut);
		goodsStockOutServiceExtend.selectDetailListByEqGoodsStockOutEndExtend(goodsStockOut, list);
        return list;
    }

	/**
     * 导出物品出库记录详细列表
     *
     * @param goodsStockOut 物品出库记录
     * @return 物品出库记录集合
     */
	public List<GoodsStockOut> selectExportDetailListGoodsStockOut(GoodsStockOut goodsStockOut){
		goodsStockOutServiceExtend.selectExportDetailListStartExtend(goodsStockOut);
		List<GoodsStockOut> list = goodsStockOutMapper.selectDetailListByLikeGoodsStockOut(goodsStockOut);
		goodsStockOutServiceExtend.selectExportDetailListEndExtend(goodsStockOut, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param goodsStockOut 物品出库记录
     * @return 物品出库记录集合
     */
    public int selectCountByLikeGoodsStockOut(GoodsStockOut goodsStockOut){
		return goodsStockOutMapper.selectCountByLikeGoodsStockOut(goodsStockOut);
	}

	/**
     * 精确查询记录数
     *
     * @param goodsStockOut 物品出库记录
     * @return 物品出库记录集合
     */
    public int selectCountByEqGoodsStockOut(GoodsStockOut goodsStockOut){
		return goodsStockOutMapper.selectCountByEqGoodsStockOut(goodsStockOut);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(GoodsStockOut goodsStockOut){
		goodsStockOutServiceExtend.exportDataCheckExtend(goodsStockOut);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(GoodsStockOut goodsStockOut, List<GoodsStockOut> list){
		 goodsStockOutServiceExtend.exportDataDealExtend(goodsStockOut, list);
	 }

    /**
     * 新增物品出库记录
     *
     * @param goodsStockOut 物品出库记录
     * @return 结果
     */
    @Override
	@Transactional
    public int insertGoodsStockOut(GoodsStockOut goodsStockOut)
    {
        goodsStockOut.setCreateTime(DateUtils.getNowDate());
		goodsStockOut.setUpdateTime(goodsStockOut.getCreateTime());
        goodsStockOutServiceExtend.insertStartExtend( goodsStockOut);
		int rows = 0;
 		rows = goodsStockOutMapper.insertGoodsStockOut(goodsStockOut);
		goodsStockOutServiceExtend.insertEndExtend( goodsStockOut, rows);
        return Integer.parseInt(goodsStockOut.getId()+"");
    }

	/**
     * 批量新增修改物品出库记录
     *
     * @param goodsStockOut 物品出库记录
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByGoodsStockOut(List<GoodsStockOut> goodsStockOuts){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = goodsStockOuts.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<GoodsStockOut> batchList = goodsStockOuts.subList(i, toIndex);
			goodsStockOutMapper.batchInsertOrUpdateByGoodsStockOut(batchList);
		}
	}

    /**
     * 修改物品出库记录：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param goodsStockOut 物品出库记录
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByGoodsStockOut(GoodsStockOut goodsStockOut)
    {
		GoodsStockOut oldGoodsStockOut = goodsStockOutMapper.selectDataByPkGoodsStockOut(goodsStockOut.getId());
        goodsStockOutServiceExtend.updateStartExtend( goodsStockOut, oldGoodsStockOut);
		int rows = 0;
		rows = goodsStockOutMapper.updateNullValueByGoodsStockOut(goodsStockOut);
		goodsStockOutServiceExtend.updateEndExtend( goodsStockOut,oldGoodsStockOut, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param goodsStockOut 物品出库记录
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByGoodsStockOut(GoodsStockOut goodsStockOut)
    {
		GoodsStockOut oldGoodsStockOut = goodsStockOutMapper.selectDataByPkGoodsStockOut(goodsStockOut.getId());
        goodsStockOutServiceExtend.updateStartExtend( goodsStockOut, oldGoodsStockOut);
		int rows = 0;
		rows = goodsStockOutMapper.updateNotNullValueByGoodsStockOut(goodsStockOut);
		goodsStockOutServiceExtend.updateEndExtend( goodsStockOut,oldGoodsStockOut, rows);
        return rows;
    }

	/**
     * 删除GoodsStockOutById
     *
     * @param id 物品出库记录主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGoodsStockOutById(GoodsStockOut goodsStockOut)
    {
		try {
        	return goodsStockOutMapper.deleteGoodsStockOutById(goodsStockOut);
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
     * 批量删除GoodsStockOutByIds
     *
     * @param ids 需要删除的物品出库记录主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGoodsStockOutByIds(GoodsStockOut goodsStockOut) {
		try {
			goodsStockOutServiceExtend.deleteByIdsStartExtend(goodsStockOut);
			int rows = goodsStockOutMapper.deleteGoodsStockOutByIds(goodsStockOut);
			goodsStockOutServiceExtend.deleteByIdsEndExtend(goodsStockOut, rows);
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
     * 批量删除GoodsStockOutByEqGoodsStockOut
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deleteGoodsStockOutByEqGoodsStockOut(GoodsStockOut goodsStockOut){
		try {
			goodsStockOutServiceExtend.deleteByEqGoodsStockOutStartExtend(goodsStockOut);
			int rows = goodsStockOutMapper.deleteGoodsStockOutByEqGoodsStockOut(goodsStockOut);
			goodsStockOutServiceExtend.deleteByEqGoodsStockOutEndExtend(goodsStockOut, rows);
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
    public int deleteGoodsStockOutAllData(){
		try {
			int rows = goodsStockOutMapper.deleteGoodsStockOutAllData();
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
    public String importGoodsStockOutData(List<GoodsStockOut> dataList, Boolean isUpdateSupport, String operName, GoodsStockOut pGoodsStockOut)
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
		GoodsStockOut goodsStockOut = new GoodsStockOut();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		goodsStockOutServiceExtend.importDataStartExtend(dataList, pGoodsStockOut, operName, nowDate);

		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> goodsStockInIdLableValueMap =  commonService.getDictLableValueMap("a_goods_stock_in");
		String  goodsStockInIdAllDictLableStr = commonService.getDictAllLableStr("a_goods_stock_in");
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
				goodsStockOut =dataList.get(i);
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(goodsStockOut.getGoodsStockInIdExtend())){
					checkMsg += headString + "入库编号字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String goodsStockInId = goodsStockOut.getGoodsStockInIdExtend();
                if (StrUtil.isNotBlank(goodsStockInId)) {
					// 通过名称取对应的字典值
                    dictValue = goodsStockInIdLableValueMap.get(goodsStockInId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        goodsStockOut.setGoodsStockInId(Long.parseLong(dictValue));
                    } else {
						//allDictLableStr = CollUtil.join(goodsStockInIdLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(goodsStockInIdAllDictLableStr)) {
                        	checkMsg += headString + "入库编号字段的录入值["+ goodsStockInId +"]必须属于以下取值范围["+goodsStockInIdAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "入库编号字段的录入值["+ goodsStockInId +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(goodsStockOut.getGoodsIdExtend())){
					checkMsg += headString + "物品名称字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String goodsId = goodsStockOut.getGoodsIdExtend();
                if (StrUtil.isNotBlank(goodsId)) {
					// 通过名称取对应的字典值
                    dictValue = goodsIdLableValueMap.get(goodsId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        goodsStockOut.setGoodsId(Long.parseLong(dictValue));
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
		        if (null == goodsStockOut.getQuantity()){
		        	checkMsg += headString + "出库数量字段为必填项。";
	        	}

		        // 检查完必填字段和取值范围后，在进行重复记录校验
		        if ("".equals(checkMsg)) {
			        // 进行导入扩展校验
			        checkMsg = goodsStockOutServiceExtend.importDataCheckExtend(isUpdateSupport, pGoodsStockOut, goodsStockOut, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, goodsStockOut);
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
				this.batchInsertOrUpdateByGoodsStockOut(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		goodsStockOutServiceExtend.importDataEndExtend(dataList, pGoodsStockOut, operName, successNum);
        return successMsg.toString();
    }



}
