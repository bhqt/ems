package com.ruoyi.autoee.goodsInventory.service.impl;

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
import com.ruoyi.autoee.goodsInventory.mapper.GoodsInventoryMapper;
import com.ruoyi.autoee.goodsInventory.mapper.GoodsInventoryMapperExtend;
import com.ruoyi.autoee.goodsInventory.domain.GoodsInventory;
import com.ruoyi.autoee.goodsInventory.service.IGoodsInventoryService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 物品库存Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class GoodsInventoryServiceImpl implements IGoodsInventoryService
{
	private static final Logger logger = LoggerFactory.getLogger(GoodsInventoryServiceImpl.class);
    @Autowired
    private GoodsInventoryMapper goodsInventoryMapper;
    @Autowired
    private GoodsInventoryMapperExtend goodsInventoryMapperExtend;
    @Autowired
    private GoodsInventoryServiceExtend goodsInventoryServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询物品库存
     *
     * @param id 物品库存主键
     * @return 物品库存
     */
    @Override
    public GoodsInventory selectDataByPkGoodsInventory(Long id)
    {
        return goodsInventoryMapper.selectDataByPkGoodsInventory(id);
    }

    /**
     * 通过主键查询物品库存详细信息
     *
     * @param id 物品库存主键
     * @return 物品库存
     */
    @Override
    public GoodsInventory selectDetailByPkGoodsInventory(Long id)
    {
		GoodsInventory goodsInventory = goodsInventoryMapper.selectDetailByPkGoodsInventory(id);
		goodsInventoryServiceExtend.selectDetailByPkGoodsInventoryEndExtend(goodsInventory);
        return goodsInventory;
    }

    /**
     * 查询物品库存列表
     *
     * @param goodsInventory 物品库存
     * @return 物品库存
     */
    @Override
    public List<GoodsInventory> selectDataListByLikeGoodsInventory(GoodsInventory goodsInventory)
    {
		goodsInventoryServiceExtend.selectListStartExtend(goodsInventory);
		List<GoodsInventory> list = goodsInventoryMapper.selectDataListByLikeGoodsInventory(goodsInventory);
		goodsInventoryServiceExtend.selectListEndExtend(goodsInventory, list);
        return list;
    }

    /**
     * 精确查询物品库存列表
     *
     * @param goodsInventory 物品库存
     * @return 物品库存集合
     */
    public List<GoodsInventory> selectDataListByEqGoodsInventory(GoodsInventory goodsInventory)
	{
		goodsInventoryServiceExtend.selectListStartExtend(goodsInventory);
		List<GoodsInventory> list = goodsInventoryMapper.selectDataListByEqGoodsInventory(goodsInventory);
		goodsInventoryServiceExtend.selectListEndExtend(goodsInventory, list);
        return list;
    }

	/**
     * 查询物品库存详细列表
     *
     * @param goodsInventory 物品库存
     * @return 物品库存集合
     */
    public List<GoodsInventory> selectDetailListByLikeGoodsInventory(GoodsInventory goodsInventory)
	{
		goodsInventoryServiceExtend.selectDetailListStartExtend(goodsInventory);
		List<GoodsInventory> list = goodsInventoryMapper.selectDetailListByLikeGoodsInventory(goodsInventory);
		goodsInventoryServiceExtend.selectDetailListEndExtend(goodsInventory, list);
        return list;
    }

    /**
     * 精确查询物品库存详细列表
     *
     * @param goodsInventory 物品库存
     * @return 物品库存集合
     */
    public List<GoodsInventory> selectDetailListByEqGoodsInventory(GoodsInventory goodsInventory)
	{
		goodsInventoryServiceExtend.selectDetailListByEqGoodsInventoryStartExtend(goodsInventory);
		List<GoodsInventory> list = goodsInventoryMapper.selectDetailListByEqGoodsInventory(goodsInventory);
		goodsInventoryServiceExtend.selectDetailListByEqGoodsInventoryEndExtend(goodsInventory, list);
        return list;
    }

	/**
     * 导出物品库存详细列表
     *
     * @param goodsInventory 物品库存
     * @return 物品库存集合
     */
	public List<GoodsInventory> selectExportDetailListGoodsInventory(GoodsInventory goodsInventory){
		goodsInventoryServiceExtend.selectExportDetailListStartExtend(goodsInventory);
		List<GoodsInventory> list = goodsInventoryMapper.selectDetailListByLikeGoodsInventory(goodsInventory);
		goodsInventoryServiceExtend.selectExportDetailListEndExtend(goodsInventory, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param goodsInventory 物品库存
     * @return 物品库存集合
     */
    public int selectCountByLikeGoodsInventory(GoodsInventory goodsInventory){
		return goodsInventoryMapper.selectCountByLikeGoodsInventory(goodsInventory);
	}

	/**
     * 精确查询记录数
     *
     * @param goodsInventory 物品库存
     * @return 物品库存集合
     */
    public int selectCountByEqGoodsInventory(GoodsInventory goodsInventory){
		return goodsInventoryMapper.selectCountByEqGoodsInventory(goodsInventory);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(GoodsInventory goodsInventory){
		goodsInventoryServiceExtend.exportDataCheckExtend(goodsInventory);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(GoodsInventory goodsInventory, List<GoodsInventory> list){
		 goodsInventoryServiceExtend.exportDataDealExtend(goodsInventory, list);
	 }

    /**
     * 新增物品库存
     *
     * @param goodsInventory 物品库存
     * @return 结果
     */
    @Override
	@Transactional
    public int insertGoodsInventory(GoodsInventory goodsInventory)
    {
        goodsInventory.setCreateTime(DateUtils.getNowDate());
		goodsInventory.setUpdateTime(goodsInventory.getCreateTime());
        goodsInventoryServiceExtend.insertStartExtend( goodsInventory);
		int rows = 0;
 		rows = goodsInventoryMapper.insertGoodsInventory(goodsInventory);
		goodsInventoryServiceExtend.insertEndExtend( goodsInventory, rows);
        return Integer.parseInt(goodsInventory.getId()+"");
    }

	/**
     * 批量新增修改物品库存
     *
     * @param goodsInventory 物品库存
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByGoodsInventory(List<GoodsInventory> goodsInventorys){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = goodsInventorys.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<GoodsInventory> batchList = goodsInventorys.subList(i, toIndex);
			goodsInventoryMapper.batchInsertOrUpdateByGoodsInventory(batchList);
		}
	}

    /**
     * 修改物品库存：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param goodsInventory 物品库存
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByGoodsInventory(GoodsInventory goodsInventory)
    {
		GoodsInventory oldGoodsInventory = goodsInventoryMapper.selectDataByPkGoodsInventory(goodsInventory.getId());
        goodsInventoryServiceExtend.updateStartExtend( goodsInventory, oldGoodsInventory);
		int rows = 0;
		rows = goodsInventoryMapper.updateNullValueByGoodsInventory(goodsInventory);
		goodsInventoryServiceExtend.updateEndExtend( goodsInventory,oldGoodsInventory, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param goodsInventory 物品库存
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByGoodsInventory(GoodsInventory goodsInventory)
    {
		GoodsInventory oldGoodsInventory = goodsInventoryMapper.selectDataByPkGoodsInventory(goodsInventory.getId());
        goodsInventoryServiceExtend.updateStartExtend( goodsInventory, oldGoodsInventory);
		int rows = 0;
		rows = goodsInventoryMapper.updateNotNullValueByGoodsInventory(goodsInventory);
		goodsInventoryServiceExtend.updateEndExtend( goodsInventory,oldGoodsInventory, rows);
        return rows;
    }

	/**
     * 删除GoodsInventoryById
     *
     * @param id 物品库存主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGoodsInventoryById(GoodsInventory goodsInventory)
    {
		try {
        	return goodsInventoryMapper.deleteGoodsInventoryById(goodsInventory);
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
     * 批量删除GoodsInventoryByIds
     *
     * @param ids 需要删除的物品库存主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGoodsInventoryByIds(GoodsInventory goodsInventory) {
		try {
			goodsInventoryServiceExtend.deleteByIdsStartExtend(goodsInventory);
			int rows = goodsInventoryMapper.deleteGoodsInventoryByIds(goodsInventory);
			goodsInventoryServiceExtend.deleteByIdsEndExtend(goodsInventory, rows);
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
     * 批量删除GoodsInventoryByEqGoodsInventory
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deleteGoodsInventoryByEqGoodsInventory(GoodsInventory goodsInventory){
		try {
			goodsInventoryServiceExtend.deleteByEqGoodsInventoryStartExtend(goodsInventory);
			int rows = goodsInventoryMapper.deleteGoodsInventoryByEqGoodsInventory(goodsInventory);
			goodsInventoryServiceExtend.deleteByEqGoodsInventoryEndExtend(goodsInventory, rows);
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
    public int deleteGoodsInventoryAllData(){
		try {
			int rows = goodsInventoryMapper.deleteGoodsInventoryAllData();
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
    public String importGoodsInventoryData(List<GoodsInventory> dataList, Boolean isUpdateSupport, String operName, GoodsInventory pGoodsInventory)
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
		GoodsInventory goodsInventory = new GoodsInventory();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		goodsInventoryServiceExtend.importDataStartExtend(dataList, pGoodsInventory, operName, nowDate);

		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> goodsIdLableValueMap =  commonService.getDictLableValueMap("a_goods_info");
		String  goodsIdAllDictLableStr = commonService.getDictAllLableStr("a_goods_info");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> goodsTypeLableValueMap =  commonService.getDictLableValueMap("goods_type");
		String  goodsTypeAllDictLableStr = commonService.getDictAllLableStr("goods_type");
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
				goodsInventory =dataList.get(i);
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(goodsInventory.getGoodsIdExtend())){
					checkMsg += headString + "物品名称字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String goodsId = goodsInventory.getGoodsIdExtend();
                if (StrUtil.isNotBlank(goodsId)) {
					// 通过名称取对应的字典值
                    dictValue = goodsIdLableValueMap.get(goodsId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        goodsInventory.setGoodsId(Long.parseLong(dictValue));
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
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(goodsInventory.getGoodsTypeExtend())){
					checkMsg += headString + "物品类型字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String goodsType = goodsInventory.getGoodsTypeExtend();
                if (StrUtil.isNotBlank(goodsType)) {
					// 通过名称取对应的字典值
                    dictValue = goodsTypeLableValueMap.get(goodsType);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						goodsInventory.setGoodsType(dictValue);
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
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(goodsInventory.getSpecification())){
					checkMsg += headString + "规格型号字段为必填项。";
				}
		        // 检查必填字段：数字类型字段
		        if (null == goodsInventory.getCurrentStock()){
		        	checkMsg += headString + "当前库存数量字段为必填项。";
	        	}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(goodsInventory.getGoodsUnitExtend())){
					checkMsg += headString + "单位字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String goodsUnit = goodsInventory.getGoodsUnitExtend();
                if (StrUtil.isNotBlank(goodsUnit)) {
					// 通过名称取对应的字典值
                    dictValue = goodsUnitLableValueMap.get(goodsUnit);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						goodsInventory.setGoodsUnit(dictValue);
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
			        checkMsg = goodsInventoryServiceExtend.importDataCheckExtend(isUpdateSupport, pGoodsInventory, goodsInventory, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, goodsInventory);
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
				this.batchInsertOrUpdateByGoodsInventory(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		goodsInventoryServiceExtend.importDataEndExtend(dataList, pGoodsInventory, operName, successNum);
        return successMsg.toString();
    }



}
