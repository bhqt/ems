package com.ruoyi.autoee.dangerGoodsInventory.service.impl;

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
import com.ruoyi.autoee.dangerGoodsInventory.mapper.DangerGoodsInventoryMapper;
import com.ruoyi.autoee.dangerGoodsInventory.mapper.DangerGoodsInventoryMapperExtend;
import com.ruoyi.autoee.dangerGoodsInventory.domain.DangerGoodsInventory;
import com.ruoyi.autoee.dangerGoodsInventory.service.IDangerGoodsInventoryService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 危化品库存Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class DangerGoodsInventoryServiceImpl implements IDangerGoodsInventoryService
{
	private static final Logger logger = LoggerFactory.getLogger(DangerGoodsInventoryServiceImpl.class);
    @Autowired
    private DangerGoodsInventoryMapper dangerGoodsInventoryMapper;
    @Autowired
    private DangerGoodsInventoryMapperExtend dangerGoodsInventoryMapperExtend;
    @Autowired
    private DangerGoodsInventoryServiceExtend dangerGoodsInventoryServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询危化品库存
     *
     * @param id 危化品库存主键
     * @return 危化品库存
     */
    @Override
    public DangerGoodsInventory selectDataByPkDangerGoodsInventory(Long id)
    {
        return dangerGoodsInventoryMapper.selectDataByPkDangerGoodsInventory(id);
    }

    /**
     * 通过主键查询危化品库存详细信息
     *
     * @param id 危化品库存主键
     * @return 危化品库存
     */
    @Override
    public DangerGoodsInventory selectDetailByPkDangerGoodsInventory(Long id)
    {
		DangerGoodsInventory dangerGoodsInventory = dangerGoodsInventoryMapper.selectDetailByPkDangerGoodsInventory(id);
		dangerGoodsInventoryServiceExtend.selectDetailByPkDangerGoodsInventoryEndExtend(dangerGoodsInventory);
        return dangerGoodsInventory;
    }

    /**
     * 查询危化品库存列表
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 危化品库存
     */
    @Override
    public List<DangerGoodsInventory> selectDataListByLikeDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory)
    {
		dangerGoodsInventoryServiceExtend.selectListStartExtend(dangerGoodsInventory);
		List<DangerGoodsInventory> list = dangerGoodsInventoryMapper.selectDataListByLikeDangerGoodsInventory(dangerGoodsInventory);
		dangerGoodsInventoryServiceExtend.selectListEndExtend(dangerGoodsInventory, list);
        return list;
    }

    /**
     * 精确查询危化品库存列表
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 危化品库存集合
     */
    public List<DangerGoodsInventory> selectDataListByEqDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory)
	{
		dangerGoodsInventoryServiceExtend.selectListStartExtend(dangerGoodsInventory);
		List<DangerGoodsInventory> list = dangerGoodsInventoryMapper.selectDataListByEqDangerGoodsInventory(dangerGoodsInventory);
		dangerGoodsInventoryServiceExtend.selectListEndExtend(dangerGoodsInventory, list);
        return list;
    }

	/**
     * 查询危化品库存详细列表
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 危化品库存集合
     */
    public List<DangerGoodsInventory> selectDetailListByLikeDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory)
	{
		dangerGoodsInventoryServiceExtend.selectDetailListStartExtend(dangerGoodsInventory);
		List<DangerGoodsInventory> list = dangerGoodsInventoryMapper.selectDetailListByLikeDangerGoodsInventory(dangerGoodsInventory);
		dangerGoodsInventoryServiceExtend.selectDetailListEndExtend(dangerGoodsInventory, list);
        return list;
    }

    /**
     * 精确查询危化品库存详细列表
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 危化品库存集合
     */
    public List<DangerGoodsInventory> selectDetailListByEqDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory)
	{
		dangerGoodsInventoryServiceExtend.selectDetailListByEqDangerGoodsInventoryStartExtend(dangerGoodsInventory);
		List<DangerGoodsInventory> list = dangerGoodsInventoryMapper.selectDetailListByEqDangerGoodsInventory(dangerGoodsInventory);
		dangerGoodsInventoryServiceExtend.selectDetailListByEqDangerGoodsInventoryEndExtend(dangerGoodsInventory, list);
        return list;
    }

	/**
     * 导出危化品库存详细列表
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 危化品库存集合
     */
	public List<DangerGoodsInventory> selectExportDetailListDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory){
		dangerGoodsInventoryServiceExtend.selectExportDetailListStartExtend(dangerGoodsInventory);
		List<DangerGoodsInventory> list = dangerGoodsInventoryMapper.selectDetailListByLikeDangerGoodsInventory(dangerGoodsInventory);
		dangerGoodsInventoryServiceExtend.selectExportDetailListEndExtend(dangerGoodsInventory, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 危化品库存集合
     */
    public int selectCountByLikeDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory){
		return dangerGoodsInventoryMapper.selectCountByLikeDangerGoodsInventory(dangerGoodsInventory);
	}

	/**
     * 精确查询记录数
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 危化品库存集合
     */
    public int selectCountByEqDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory){
		return dangerGoodsInventoryMapper.selectCountByEqDangerGoodsInventory(dangerGoodsInventory);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(DangerGoodsInventory dangerGoodsInventory){
		dangerGoodsInventoryServiceExtend.exportDataCheckExtend(dangerGoodsInventory);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(DangerGoodsInventory dangerGoodsInventory, List<DangerGoodsInventory> list){
		 dangerGoodsInventoryServiceExtend.exportDataDealExtend(dangerGoodsInventory, list);
	 }

    /**
     * 新增危化品库存
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 结果
     */
    @Override
	@Transactional
    public int insertDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory)
    {
        dangerGoodsInventory.setCreateTime(DateUtils.getNowDate());
		dangerGoodsInventory.setUpdateTime(dangerGoodsInventory.getCreateTime());
        dangerGoodsInventoryServiceExtend.insertStartExtend( dangerGoodsInventory);
		int rows = 0;
 		rows = dangerGoodsInventoryMapper.insertDangerGoodsInventory(dangerGoodsInventory);
		dangerGoodsInventoryServiceExtend.insertEndExtend( dangerGoodsInventory, rows);
        return Integer.parseInt(dangerGoodsInventory.getId()+"");
    }

	/**
     * 批量新增修改危化品库存
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByDangerGoodsInventory(List<DangerGoodsInventory> dangerGoodsInventorys){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = dangerGoodsInventorys.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<DangerGoodsInventory> batchList = dangerGoodsInventorys.subList(i, toIndex);
			dangerGoodsInventoryMapper.batchInsertOrUpdateByDangerGoodsInventory(batchList);
		}
	}

    /**
     * 修改危化品库存：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory)
    {
		DangerGoodsInventory oldDangerGoodsInventory = dangerGoodsInventoryMapper.selectDataByPkDangerGoodsInventory(dangerGoodsInventory.getId());
        dangerGoodsInventoryServiceExtend.updateStartExtend( dangerGoodsInventory, oldDangerGoodsInventory);
		int rows = 0;
		rows = dangerGoodsInventoryMapper.updateNullValueByDangerGoodsInventory(dangerGoodsInventory);
		dangerGoodsInventoryServiceExtend.updateEndExtend( dangerGoodsInventory,oldDangerGoodsInventory, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory)
    {
		DangerGoodsInventory oldDangerGoodsInventory = dangerGoodsInventoryMapper.selectDataByPkDangerGoodsInventory(dangerGoodsInventory.getId());
        dangerGoodsInventoryServiceExtend.updateStartExtend( dangerGoodsInventory, oldDangerGoodsInventory);
		int rows = 0;
		rows = dangerGoodsInventoryMapper.updateNotNullValueByDangerGoodsInventory(dangerGoodsInventory);
		dangerGoodsInventoryServiceExtend.updateEndExtend( dangerGoodsInventory,oldDangerGoodsInventory, rows);
        return rows;
    }

	/**
     * 删除DangerGoodsInventoryById
     *
     * @param id 危化品库存主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteDangerGoodsInventoryById(DangerGoodsInventory dangerGoodsInventory)
    {
		try {
        	return dangerGoodsInventoryMapper.deleteDangerGoodsInventoryById(dangerGoodsInventory);
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
     * 批量删除DangerGoodsInventoryByIds
     *
     * @param ids 需要删除的危化品库存主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteDangerGoodsInventoryByIds(DangerGoodsInventory dangerGoodsInventory) {
		try {
			dangerGoodsInventoryServiceExtend.deleteByIdsStartExtend(dangerGoodsInventory);
			int rows = dangerGoodsInventoryMapper.deleteDangerGoodsInventoryByIds(dangerGoodsInventory);
			dangerGoodsInventoryServiceExtend.deleteByIdsEndExtend(dangerGoodsInventory, rows);
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
     * 批量删除DangerGoodsInventoryByEqDangerGoodsInventory
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deleteDangerGoodsInventoryByEqDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory){
		try {
			dangerGoodsInventoryServiceExtend.deleteByEqDangerGoodsInventoryStartExtend(dangerGoodsInventory);
			int rows = dangerGoodsInventoryMapper.deleteDangerGoodsInventoryByEqDangerGoodsInventory(dangerGoodsInventory);
			dangerGoodsInventoryServiceExtend.deleteByEqDangerGoodsInventoryEndExtend(dangerGoodsInventory, rows);
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
    public int deleteDangerGoodsInventoryAllData(){
		try {
			int rows = dangerGoodsInventoryMapper.deleteDangerGoodsInventoryAllData();
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
    public String importDangerGoodsInventoryData(List<DangerGoodsInventory> dataList, Boolean isUpdateSupport, String operName, DangerGoodsInventory pDangerGoodsInventory)
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
		DangerGoodsInventory dangerGoodsInventory = new DangerGoodsInventory();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		dangerGoodsInventoryServiceExtend.importDataStartExtend(dataList, pDangerGoodsInventory, operName, nowDate);

		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> dangerGoodsIdLableValueMap =  commonService.getDictLableValueMap("a_danger_goods_info");
		String  dangerGoodsIdAllDictLableStr = commonService.getDictAllLableStr("a_danger_goods_info");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> dangerGoodsTypeLableValueMap =  commonService.getDictLableValueMap("danger_goods_type");
		String  dangerGoodsTypeAllDictLableStr = commonService.getDictAllLableStr("danger_goods_type");
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
				dangerGoodsInventory =dataList.get(i);
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(dangerGoodsInventory.getDangerGoodsIdExtend())){
					checkMsg += headString + "危化品名称字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String dangerGoodsId = dangerGoodsInventory.getDangerGoodsIdExtend();
                if (StrUtil.isNotBlank(dangerGoodsId)) {
					// 通过名称取对应的字典值
                    dictValue = dangerGoodsIdLableValueMap.get(dangerGoodsId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        dangerGoodsInventory.setDangerGoodsId(Long.parseLong(dictValue));
                    } else {
						//allDictLableStr = CollUtil.join(dangerGoodsIdLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(dangerGoodsIdAllDictLableStr)) {
                        	checkMsg += headString + "危化品名称字段的录入值["+ dangerGoodsId +"]必须属于以下取值范围["+dangerGoodsIdAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "危化品名称字段的录入值["+ dangerGoodsId +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(dangerGoodsInventory.getDangerGoodsTypeExtend())){
					checkMsg += headString + "危化品类型字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String dangerGoodsType = dangerGoodsInventory.getDangerGoodsTypeExtend();
                if (StrUtil.isNotBlank(dangerGoodsType)) {
					// 通过名称取对应的字典值
                    dictValue = dangerGoodsTypeLableValueMap.get(dangerGoodsType);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						dangerGoodsInventory.setDangerGoodsType(dictValue);
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
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(dangerGoodsInventory.getSpecification())){
					checkMsg += headString + "规格型号字段为必填项。";
				}
		        // 检查必填字段：数字类型字段
		        if (null == dangerGoodsInventory.getCurrentStock()){
		        	checkMsg += headString + "当前库存数量字段为必填项。";
	        	}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(dangerGoodsInventory.getGoodsUnitExtend())){
					checkMsg += headString + "单位字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String goodsUnit = dangerGoodsInventory.getGoodsUnitExtend();
                if (StrUtil.isNotBlank(goodsUnit)) {
					// 通过名称取对应的字典值
                    dictValue = goodsUnitLableValueMap.get(goodsUnit);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						dangerGoodsInventory.setGoodsUnit(dictValue);
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
			        checkMsg = dangerGoodsInventoryServiceExtend.importDataCheckExtend(isUpdateSupport, pDangerGoodsInventory, dangerGoodsInventory, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, dangerGoodsInventory);
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
				this.batchInsertOrUpdateByDangerGoodsInventory(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		dangerGoodsInventoryServiceExtend.importDataEndExtend(dataList, pDangerGoodsInventory, operName, successNum);
        return successMsg.toString();
    }



}
