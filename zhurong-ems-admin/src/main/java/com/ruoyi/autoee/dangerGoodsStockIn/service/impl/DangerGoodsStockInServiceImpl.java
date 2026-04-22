package com.ruoyi.autoee.dangerGoodsStockIn.service.impl;

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
import com.ruoyi.autoee.dangerGoodsStockIn.mapper.DangerGoodsStockInMapper;
import com.ruoyi.autoee.dangerGoodsStockIn.mapper.DangerGoodsStockInMapperExtend;
import com.ruoyi.autoee.dangerGoodsStockIn.domain.DangerGoodsStockIn;
import com.ruoyi.autoee.dangerGoodsStockIn.service.IDangerGoodsStockInService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 危化品入库记录Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class DangerGoodsStockInServiceImpl implements IDangerGoodsStockInService
{
	private static final Logger logger = LoggerFactory.getLogger(DangerGoodsStockInServiceImpl.class);
    @Autowired
    private DangerGoodsStockInMapper dangerGoodsStockInMapper;
    @Autowired
    private DangerGoodsStockInMapperExtend dangerGoodsStockInMapperExtend;
    @Autowired
    private DangerGoodsStockInServiceExtend dangerGoodsStockInServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询危化品入库记录
     *
     * @param id 危化品入库记录主键
     * @return 危化品入库记录
     */
    @Override
    public DangerGoodsStockIn selectDataByPkDangerGoodsStockIn(Long id)
    {
        return dangerGoodsStockInMapper.selectDataByPkDangerGoodsStockIn(id);
    }

    /**
     * 通过主键查询危化品入库记录详细信息
     *
     * @param id 危化品入库记录主键
     * @return 危化品入库记录
     */
    @Override
    public DangerGoodsStockIn selectDetailByPkDangerGoodsStockIn(Long id)
    {
		DangerGoodsStockIn dangerGoodsStockIn = dangerGoodsStockInMapper.selectDetailByPkDangerGoodsStockIn(id);
		dangerGoodsStockInServiceExtend.selectDetailByPkDangerGoodsStockInEndExtend(dangerGoodsStockIn);
        return dangerGoodsStockIn;
    }

    /**
     * 查询危化品入库记录列表
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 危化品入库记录
     */
    @Override
    public List<DangerGoodsStockIn> selectDataListByLikeDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn)
    {
		dangerGoodsStockInServiceExtend.selectListStartExtend(dangerGoodsStockIn);
		List<DangerGoodsStockIn> list = dangerGoodsStockInMapper.selectDataListByLikeDangerGoodsStockIn(dangerGoodsStockIn);
		dangerGoodsStockInServiceExtend.selectListEndExtend(dangerGoodsStockIn, list);
        return list;
    }

    /**
     * 精确查询危化品入库记录列表
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 危化品入库记录集合
     */
    public List<DangerGoodsStockIn> selectDataListByEqDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn)
	{
		dangerGoodsStockInServiceExtend.selectListStartExtend(dangerGoodsStockIn);
		List<DangerGoodsStockIn> list = dangerGoodsStockInMapper.selectDataListByEqDangerGoodsStockIn(dangerGoodsStockIn);
		dangerGoodsStockInServiceExtend.selectListEndExtend(dangerGoodsStockIn, list);
        return list;
    }

	/**
     * 查询危化品入库记录详细列表
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 危化品入库记录集合
     */
    public List<DangerGoodsStockIn> selectDetailListByLikeDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn)
	{
		dangerGoodsStockInServiceExtend.selectDetailListStartExtend(dangerGoodsStockIn);
		List<DangerGoodsStockIn> list = dangerGoodsStockInMapper.selectDetailListByLikeDangerGoodsStockIn(dangerGoodsStockIn);
		dangerGoodsStockInServiceExtend.selectDetailListEndExtend(dangerGoodsStockIn, list);
        return list;
    }

    /**
     * 精确查询危化品入库记录详细列表
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 危化品入库记录集合
     */
    public List<DangerGoodsStockIn> selectDetailListByEqDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn)
	{
		dangerGoodsStockInServiceExtend.selectDetailListByEqDangerGoodsStockInStartExtend(dangerGoodsStockIn);
		List<DangerGoodsStockIn> list = dangerGoodsStockInMapper.selectDetailListByEqDangerGoodsStockIn(dangerGoodsStockIn);
		dangerGoodsStockInServiceExtend.selectDetailListByEqDangerGoodsStockInEndExtend(dangerGoodsStockIn, list);
        return list;
    }

	/**
     * 导出危化品入库记录详细列表
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 危化品入库记录集合
     */
	public List<DangerGoodsStockIn> selectExportDetailListDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn){
		dangerGoodsStockInServiceExtend.selectExportDetailListStartExtend(dangerGoodsStockIn);
		List<DangerGoodsStockIn> list = dangerGoodsStockInMapper.selectDetailListByLikeDangerGoodsStockIn(dangerGoodsStockIn);
		dangerGoodsStockInServiceExtend.selectExportDetailListEndExtend(dangerGoodsStockIn, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 危化品入库记录集合
     */
    public int selectCountByLikeDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn){
		return dangerGoodsStockInMapper.selectCountByLikeDangerGoodsStockIn(dangerGoodsStockIn);
	}

	/**
     * 精确查询记录数
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 危化品入库记录集合
     */
    public int selectCountByEqDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn){
		return dangerGoodsStockInMapper.selectCountByEqDangerGoodsStockIn(dangerGoodsStockIn);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(DangerGoodsStockIn dangerGoodsStockIn){
		dangerGoodsStockInServiceExtend.exportDataCheckExtend(dangerGoodsStockIn);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(DangerGoodsStockIn dangerGoodsStockIn, List<DangerGoodsStockIn> list){
		 dangerGoodsStockInServiceExtend.exportDataDealExtend(dangerGoodsStockIn, list);
	 }

    /**
     * 新增危化品入库记录
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 结果
     */
    @Override
	@Transactional
    public int insertDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn)
    {
        dangerGoodsStockIn.setCreateTime(DateUtils.getNowDate());
		dangerGoodsStockIn.setUpdateTime(dangerGoodsStockIn.getCreateTime());
        dangerGoodsStockInServiceExtend.insertStartExtend( dangerGoodsStockIn);
		int rows = 0;
 		rows = dangerGoodsStockInMapper.insertDangerGoodsStockIn(dangerGoodsStockIn);
		dangerGoodsStockInServiceExtend.insertEndExtend( dangerGoodsStockIn, rows);
        return Integer.parseInt(dangerGoodsStockIn.getId()+"");
    }

	/**
     * 批量新增修改危化品入库记录
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByDangerGoodsStockIn(List<DangerGoodsStockIn> dangerGoodsStockIns){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = dangerGoodsStockIns.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<DangerGoodsStockIn> batchList = dangerGoodsStockIns.subList(i, toIndex);
			dangerGoodsStockInMapper.batchInsertOrUpdateByDangerGoodsStockIn(batchList);
		}
	}

    /**
     * 修改危化品入库记录：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn)
    {
		DangerGoodsStockIn oldDangerGoodsStockIn = dangerGoodsStockInMapper.selectDataByPkDangerGoodsStockIn(dangerGoodsStockIn.getId());
        dangerGoodsStockInServiceExtend.updateStartExtend( dangerGoodsStockIn, oldDangerGoodsStockIn);
		int rows = 0;
		rows = dangerGoodsStockInMapper.updateNullValueByDangerGoodsStockIn(dangerGoodsStockIn);
		dangerGoodsStockInServiceExtend.updateEndExtend( dangerGoodsStockIn,oldDangerGoodsStockIn, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn)
    {
		DangerGoodsStockIn oldDangerGoodsStockIn = dangerGoodsStockInMapper.selectDataByPkDangerGoodsStockIn(dangerGoodsStockIn.getId());
        dangerGoodsStockInServiceExtend.updateStartExtend( dangerGoodsStockIn, oldDangerGoodsStockIn);
		int rows = 0;
		rows = dangerGoodsStockInMapper.updateNotNullValueByDangerGoodsStockIn(dangerGoodsStockIn);
		dangerGoodsStockInServiceExtend.updateEndExtend( dangerGoodsStockIn,oldDangerGoodsStockIn, rows);
        return rows;
    }

	/**
     * 删除DangerGoodsStockInById
     *
     * @param id 危化品入库记录主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteDangerGoodsStockInById(DangerGoodsStockIn dangerGoodsStockIn)
    {
		try {
        	return dangerGoodsStockInMapper.deleteDangerGoodsStockInById(dangerGoodsStockIn);
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
     * 批量删除DangerGoodsStockInByIds
     *
     * @param ids 需要删除的危化品入库记录主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteDangerGoodsStockInByIds(DangerGoodsStockIn dangerGoodsStockIn) {
		try {
			dangerGoodsStockInServiceExtend.deleteByIdsStartExtend(dangerGoodsStockIn);
			int rows = dangerGoodsStockInMapper.deleteDangerGoodsStockInByIds(dangerGoodsStockIn);
			dangerGoodsStockInServiceExtend.deleteByIdsEndExtend(dangerGoodsStockIn, rows);
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
     * 批量删除DangerGoodsStockInByEqDangerGoodsStockIn
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deleteDangerGoodsStockInByEqDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn){
		try {
			dangerGoodsStockInServiceExtend.deleteByEqDangerGoodsStockInStartExtend(dangerGoodsStockIn);
			int rows = dangerGoodsStockInMapper.deleteDangerGoodsStockInByEqDangerGoodsStockIn(dangerGoodsStockIn);
			dangerGoodsStockInServiceExtend.deleteByEqDangerGoodsStockInEndExtend(dangerGoodsStockIn, rows);
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
    public int deleteDangerGoodsStockInAllData(){
		try {
			int rows = dangerGoodsStockInMapper.deleteDangerGoodsStockInAllData();
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
    public String importDangerGoodsStockInData(List<DangerGoodsStockIn> dataList, Boolean isUpdateSupport, String operName, DangerGoodsStockIn pDangerGoodsStockIn)
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
		DangerGoodsStockIn dangerGoodsStockIn = new DangerGoodsStockIn();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		dangerGoodsStockInServiceExtend.importDataStartExtend(dataList, pDangerGoodsStockIn, operName, nowDate);

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
				dangerGoodsStockIn =dataList.get(i);
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(dangerGoodsStockIn.getDangerGoodsIdExtend())){
					checkMsg += headString + "危化品名称字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String dangerGoodsId = dangerGoodsStockIn.getDangerGoodsIdExtend();
                if (StrUtil.isNotBlank(dangerGoodsId)) {
					// 通过名称取对应的字典值
                    dictValue = dangerGoodsIdLableValueMap.get(dangerGoodsId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        dangerGoodsStockIn.setDangerGoodsId(Long.parseLong(dictValue));
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
		        if (null == dangerGoodsStockIn.getQuantity()){
		        	checkMsg += headString + "入库数量字段为必填项。";
	        	}

		        // 检查完必填字段和取值范围后，在进行重复记录校验
		        if ("".equals(checkMsg)) {
			        // 进行导入扩展校验
			        checkMsg = dangerGoodsStockInServiceExtend.importDataCheckExtend(isUpdateSupport, pDangerGoodsStockIn, dangerGoodsStockIn, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, dangerGoodsStockIn);
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
				this.batchInsertOrUpdateByDangerGoodsStockIn(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		dangerGoodsStockInServiceExtend.importDataEndExtend(dataList, pDangerGoodsStockIn, operName, successNum);
        return successMsg.toString();
    }



}
