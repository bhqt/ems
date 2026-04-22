package com.ruoyi.autoee.dangerGoodsStockOut.service.impl;

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
import com.ruoyi.autoee.dangerGoodsStockOut.mapper.DangerGoodsStockOutMapper;
import com.ruoyi.autoee.dangerGoodsStockOut.mapper.DangerGoodsStockOutMapperExtend;
import com.ruoyi.autoee.dangerGoodsStockOut.domain.DangerGoodsStockOut;
import com.ruoyi.autoee.dangerGoodsStockOut.service.IDangerGoodsStockOutService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 危化品出库记录Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class DangerGoodsStockOutServiceImpl implements IDangerGoodsStockOutService
{
	private static final Logger logger = LoggerFactory.getLogger(DangerGoodsStockOutServiceImpl.class);
    @Autowired
    private DangerGoodsStockOutMapper dangerGoodsStockOutMapper;
    @Autowired
    private DangerGoodsStockOutMapperExtend dangerGoodsStockOutMapperExtend;
    @Autowired
    private DangerGoodsStockOutServiceExtend dangerGoodsStockOutServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询危化品出库记录
     *
     * @param id 危化品出库记录主键
     * @return 危化品出库记录
     */
    @Override
    public DangerGoodsStockOut selectDataByPkDangerGoodsStockOut(Long id)
    {
        return dangerGoodsStockOutMapper.selectDataByPkDangerGoodsStockOut(id);
    }

    /**
     * 通过主键查询危化品出库记录详细信息
     *
     * @param id 危化品出库记录主键
     * @return 危化品出库记录
     */
    @Override
    public DangerGoodsStockOut selectDetailByPkDangerGoodsStockOut(Long id)
    {
		DangerGoodsStockOut dangerGoodsStockOut = dangerGoodsStockOutMapper.selectDetailByPkDangerGoodsStockOut(id);
		dangerGoodsStockOutServiceExtend.selectDetailByPkDangerGoodsStockOutEndExtend(dangerGoodsStockOut);
        return dangerGoodsStockOut;
    }

    /**
     * 查询危化品出库记录列表
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 危化品出库记录
     */
    @Override
    public List<DangerGoodsStockOut> selectDataListByLikeDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut)
    {
		dangerGoodsStockOutServiceExtend.selectListStartExtend(dangerGoodsStockOut);
		List<DangerGoodsStockOut> list = dangerGoodsStockOutMapper.selectDataListByLikeDangerGoodsStockOut(dangerGoodsStockOut);
		dangerGoodsStockOutServiceExtend.selectListEndExtend(dangerGoodsStockOut, list);
        return list;
    }

    /**
     * 精确查询危化品出库记录列表
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 危化品出库记录集合
     */
    public List<DangerGoodsStockOut> selectDataListByEqDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut)
	{
		dangerGoodsStockOutServiceExtend.selectListStartExtend(dangerGoodsStockOut);
		List<DangerGoodsStockOut> list = dangerGoodsStockOutMapper.selectDataListByEqDangerGoodsStockOut(dangerGoodsStockOut);
		dangerGoodsStockOutServiceExtend.selectListEndExtend(dangerGoodsStockOut, list);
        return list;
    }

	/**
     * 查询危化品出库记录详细列表
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 危化品出库记录集合
     */
    public List<DangerGoodsStockOut> selectDetailListByLikeDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut)
	{
		dangerGoodsStockOutServiceExtend.selectDetailListStartExtend(dangerGoodsStockOut);
		List<DangerGoodsStockOut> list = dangerGoodsStockOutMapper.selectDetailListByLikeDangerGoodsStockOut(dangerGoodsStockOut);
		dangerGoodsStockOutServiceExtend.selectDetailListEndExtend(dangerGoodsStockOut, list);
        return list;
    }

    /**
     * 精确查询危化品出库记录详细列表
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 危化品出库记录集合
     */
    public List<DangerGoodsStockOut> selectDetailListByEqDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut)
	{
		dangerGoodsStockOutServiceExtend.selectDetailListByEqDangerGoodsStockOutStartExtend(dangerGoodsStockOut);
		List<DangerGoodsStockOut> list = dangerGoodsStockOutMapper.selectDetailListByEqDangerGoodsStockOut(dangerGoodsStockOut);
		dangerGoodsStockOutServiceExtend.selectDetailListByEqDangerGoodsStockOutEndExtend(dangerGoodsStockOut, list);
        return list;
    }

	/**
     * 导出危化品出库记录详细列表
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 危化品出库记录集合
     */
	public List<DangerGoodsStockOut> selectExportDetailListDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut){
		dangerGoodsStockOutServiceExtend.selectExportDetailListStartExtend(dangerGoodsStockOut);
		List<DangerGoodsStockOut> list = dangerGoodsStockOutMapper.selectDetailListByLikeDangerGoodsStockOut(dangerGoodsStockOut);
		dangerGoodsStockOutServiceExtend.selectExportDetailListEndExtend(dangerGoodsStockOut, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 危化品出库记录集合
     */
    public int selectCountByLikeDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut){
		return dangerGoodsStockOutMapper.selectCountByLikeDangerGoodsStockOut(dangerGoodsStockOut);
	}

	/**
     * 精确查询记录数
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 危化品出库记录集合
     */
    public int selectCountByEqDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut){
		return dangerGoodsStockOutMapper.selectCountByEqDangerGoodsStockOut(dangerGoodsStockOut);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(DangerGoodsStockOut dangerGoodsStockOut){
		dangerGoodsStockOutServiceExtend.exportDataCheckExtend(dangerGoodsStockOut);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(DangerGoodsStockOut dangerGoodsStockOut, List<DangerGoodsStockOut> list){
		 dangerGoodsStockOutServiceExtend.exportDataDealExtend(dangerGoodsStockOut, list);
	 }

    /**
     * 新增危化品出库记录
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 结果
     */
    @Override
	@Transactional
    public int insertDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut)
    {
        dangerGoodsStockOut.setCreateTime(DateUtils.getNowDate());
		dangerGoodsStockOut.setUpdateTime(dangerGoodsStockOut.getCreateTime());
        dangerGoodsStockOutServiceExtend.insertStartExtend( dangerGoodsStockOut);
		int rows = 0;
 		rows = dangerGoodsStockOutMapper.insertDangerGoodsStockOut(dangerGoodsStockOut);
		dangerGoodsStockOutServiceExtend.insertEndExtend( dangerGoodsStockOut, rows);
        return Integer.parseInt(dangerGoodsStockOut.getId()+"");
    }

	/**
     * 批量新增修改危化品出库记录
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByDangerGoodsStockOut(List<DangerGoodsStockOut> dangerGoodsStockOuts){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = dangerGoodsStockOuts.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<DangerGoodsStockOut> batchList = dangerGoodsStockOuts.subList(i, toIndex);
			dangerGoodsStockOutMapper.batchInsertOrUpdateByDangerGoodsStockOut(batchList);
		}
	}

    /**
     * 修改危化品出库记录：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut)
    {
		DangerGoodsStockOut oldDangerGoodsStockOut = dangerGoodsStockOutMapper.selectDataByPkDangerGoodsStockOut(dangerGoodsStockOut.getId());
        dangerGoodsStockOutServiceExtend.updateStartExtend( dangerGoodsStockOut, oldDangerGoodsStockOut);
		int rows = 0;
		rows = dangerGoodsStockOutMapper.updateNullValueByDangerGoodsStockOut(dangerGoodsStockOut);
		dangerGoodsStockOutServiceExtend.updateEndExtend( dangerGoodsStockOut,oldDangerGoodsStockOut, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut)
    {
		DangerGoodsStockOut oldDangerGoodsStockOut = dangerGoodsStockOutMapper.selectDataByPkDangerGoodsStockOut(dangerGoodsStockOut.getId());
        dangerGoodsStockOutServiceExtend.updateStartExtend( dangerGoodsStockOut, oldDangerGoodsStockOut);
		int rows = 0;
		rows = dangerGoodsStockOutMapper.updateNotNullValueByDangerGoodsStockOut(dangerGoodsStockOut);
		dangerGoodsStockOutServiceExtend.updateEndExtend( dangerGoodsStockOut,oldDangerGoodsStockOut, rows);
        return rows;
    }

	/**
     * 删除DangerGoodsStockOutById
     *
     * @param id 危化品出库记录主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteDangerGoodsStockOutById(DangerGoodsStockOut dangerGoodsStockOut)
    {
		try {
        	return dangerGoodsStockOutMapper.deleteDangerGoodsStockOutById(dangerGoodsStockOut);
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
     * 批量删除DangerGoodsStockOutByIds
     *
     * @param ids 需要删除的危化品出库记录主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteDangerGoodsStockOutByIds(DangerGoodsStockOut dangerGoodsStockOut) {
		try {
			dangerGoodsStockOutServiceExtend.deleteByIdsStartExtend(dangerGoodsStockOut);
			int rows = dangerGoodsStockOutMapper.deleteDangerGoodsStockOutByIds(dangerGoodsStockOut);
			dangerGoodsStockOutServiceExtend.deleteByIdsEndExtend(dangerGoodsStockOut, rows);
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
     * 批量删除DangerGoodsStockOutByEqDangerGoodsStockOut
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deleteDangerGoodsStockOutByEqDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut){
		try {
			dangerGoodsStockOutServiceExtend.deleteByEqDangerGoodsStockOutStartExtend(dangerGoodsStockOut);
			int rows = dangerGoodsStockOutMapper.deleteDangerGoodsStockOutByEqDangerGoodsStockOut(dangerGoodsStockOut);
			dangerGoodsStockOutServiceExtend.deleteByEqDangerGoodsStockOutEndExtend(dangerGoodsStockOut, rows);
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
    public int deleteDangerGoodsStockOutAllData(){
		try {
			int rows = dangerGoodsStockOutMapper.deleteDangerGoodsStockOutAllData();
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
    public String importDangerGoodsStockOutData(List<DangerGoodsStockOut> dataList, Boolean isUpdateSupport, String operName, DangerGoodsStockOut pDangerGoodsStockOut)
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
		DangerGoodsStockOut dangerGoodsStockOut = new DangerGoodsStockOut();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		dangerGoodsStockOutServiceExtend.importDataStartExtend(dataList, pDangerGoodsStockOut, operName, nowDate);

		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> dangerGoodsStockInIdLableValueMap =  commonService.getDictLableValueMap("a_danger_goods_stock_in");
		String  dangerGoodsStockInIdAllDictLableStr = commonService.getDictAllLableStr("a_danger_goods_stock_in");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> dangerGoodsIdLableValueMap =  commonService.getDictLableValueMap("a_danger_goods_info");
		String  dangerGoodsIdAllDictLableStr = commonService.getDictAllLableStr("a_danger_goods_info");
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
				dangerGoodsStockOut =dataList.get(i);
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(dangerGoodsStockOut.getDangerGoodsStockInIdExtend())){
					checkMsg += headString + "入库编号字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String dangerGoodsStockInId = dangerGoodsStockOut.getDangerGoodsStockInIdExtend();
                if (StrUtil.isNotBlank(dangerGoodsStockInId)) {
					// 通过名称取对应的字典值
                    dictValue = dangerGoodsStockInIdLableValueMap.get(dangerGoodsStockInId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        dangerGoodsStockOut.setDangerGoodsStockInId(Long.parseLong(dictValue));
                    } else {
						//allDictLableStr = CollUtil.join(dangerGoodsStockInIdLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(dangerGoodsStockInIdAllDictLableStr)) {
                        	checkMsg += headString + "入库编号字段的录入值["+ dangerGoodsStockInId +"]必须属于以下取值范围["+dangerGoodsStockInIdAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "入库编号字段的录入值["+ dangerGoodsStockInId +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(dangerGoodsStockOut.getDangerGoodsIdExtend())){
					checkMsg += headString + "危化品名称字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String dangerGoodsId = dangerGoodsStockOut.getDangerGoodsIdExtend();
                if (StrUtil.isNotBlank(dangerGoodsId)) {
					// 通过名称取对应的字典值
                    dictValue = dangerGoodsIdLableValueMap.get(dangerGoodsId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        dangerGoodsStockOut.setDangerGoodsId(Long.parseLong(dictValue));
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
		        // 检查必填字段：数字类型字段
		        if (null == dangerGoodsStockOut.getQuantity()){
		        	checkMsg += headString + "出库数量字段为必填项。";
	        	}

		        // 检查完必填字段和取值范围后，在进行重复记录校验
		        if ("".equals(checkMsg)) {
			        // 进行导入扩展校验
			        checkMsg = dangerGoodsStockOutServiceExtend.importDataCheckExtend(isUpdateSupport, pDangerGoodsStockOut, dangerGoodsStockOut, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, dangerGoodsStockOut);
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
				this.batchInsertOrUpdateByDangerGoodsStockOut(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		dangerGoodsStockOutServiceExtend.importDataEndExtend(dataList, pDangerGoodsStockOut, operName, successNum);
        return successMsg.toString();
    }



}
