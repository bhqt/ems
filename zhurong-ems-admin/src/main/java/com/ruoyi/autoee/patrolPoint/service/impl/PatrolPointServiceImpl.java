package com.ruoyi.autoee.patrolPoint.service.impl;

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
import com.ruoyi.autoee.patrolPoint.mapper.PatrolPointMapper;
import com.ruoyi.autoee.patrolPoint.mapper.PatrolPointMapperExtend;
import com.ruoyi.autoee.patrolPoint.domain.PatrolPoint;
import com.ruoyi.autoee.patrolPoint.service.IPatrolPointService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 巡更点位Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class PatrolPointServiceImpl implements IPatrolPointService
{
	private static final Logger logger = LoggerFactory.getLogger(PatrolPointServiceImpl.class);
    @Autowired
    private PatrolPointMapper patrolPointMapper;
    @Autowired
    private PatrolPointMapperExtend patrolPointMapperExtend;
    @Autowired
    private PatrolPointServiceExtend patrolPointServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询巡更点位
     *
     * @param id 巡更点位主键
     * @return 巡更点位
     */
    @Override
    public PatrolPoint selectDataByPkPatrolPoint(Long id)
    {
        return patrolPointMapper.selectDataByPkPatrolPoint(id);
    }

    /**
     * 通过主键查询巡更点位详细信息
     *
     * @param id 巡更点位主键
     * @return 巡更点位
     */
    @Override
    public PatrolPoint selectDetailByPkPatrolPoint(Long id)
    {
		PatrolPoint patrolPoint = patrolPointMapper.selectDetailByPkPatrolPoint(id);
		patrolPointServiceExtend.selectDetailByPkPatrolPointEndExtend(patrolPoint);
        return patrolPoint;
    }

    /**
     * 查询巡更点位列表
     *
     * @param patrolPoint 巡更点位
     * @return 巡更点位
     */
    @Override
    public List<PatrolPoint> selectDataListByLikePatrolPoint(PatrolPoint patrolPoint)
    {
		patrolPointServiceExtend.selectListStartExtend(patrolPoint);
		List<PatrolPoint> list = patrolPointMapper.selectDataListByLikePatrolPoint(patrolPoint);
		patrolPointServiceExtend.selectListEndExtend(patrolPoint, list);
        return list;
    }

    /**
     * 精确查询巡更点位列表
     *
     * @param patrolPoint 巡更点位
     * @return 巡更点位集合
     */
    public List<PatrolPoint> selectDataListByEqPatrolPoint(PatrolPoint patrolPoint)
	{
		patrolPointServiceExtend.selectListStartExtend(patrolPoint);
		List<PatrolPoint> list = patrolPointMapper.selectDataListByEqPatrolPoint(patrolPoint);
		patrolPointServiceExtend.selectListEndExtend(patrolPoint, list);
        return list;
    }

	/**
     * 查询巡更点位详细列表
     *
     * @param patrolPoint 巡更点位
     * @return 巡更点位集合
     */
    public List<PatrolPoint> selectDetailListByLikePatrolPoint(PatrolPoint patrolPoint)
	{
		patrolPointServiceExtend.selectDetailListStartExtend(patrolPoint);
		List<PatrolPoint> list = patrolPointMapper.selectDetailListByLikePatrolPoint(patrolPoint);
		patrolPointServiceExtend.selectDetailListEndExtend(patrolPoint, list);
        return list;
    }

    /**
     * 精确查询巡更点位详细列表
     *
     * @param patrolPoint 巡更点位
     * @return 巡更点位集合
     */
    public List<PatrolPoint> selectDetailListByEqPatrolPoint(PatrolPoint patrolPoint)
	{
		patrolPointServiceExtend.selectDetailListByEqPatrolPointStartExtend(patrolPoint);
		List<PatrolPoint> list = patrolPointMapper.selectDetailListByEqPatrolPoint(patrolPoint);
		patrolPointServiceExtend.selectDetailListByEqPatrolPointEndExtend(patrolPoint, list);
        return list;
    }

	/**
     * 导出巡更点位详细列表
     *
     * @param patrolPoint 巡更点位
     * @return 巡更点位集合
     */
	public List<PatrolPoint> selectExportDetailListPatrolPoint(PatrolPoint patrolPoint){
		patrolPointServiceExtend.selectExportDetailListStartExtend(patrolPoint);
		List<PatrolPoint> list = patrolPointMapper.selectDetailListByLikePatrolPoint(patrolPoint);
		patrolPointServiceExtend.selectExportDetailListEndExtend(patrolPoint, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param patrolPoint 巡更点位
     * @return 巡更点位集合
     */
    public int selectCountByLikePatrolPoint(PatrolPoint patrolPoint){
		return patrolPointMapper.selectCountByLikePatrolPoint(patrolPoint);
	}

	/**
     * 精确查询记录数
     *
     * @param patrolPoint 巡更点位
     * @return 巡更点位集合
     */
    public int selectCountByEqPatrolPoint(PatrolPoint patrolPoint){
		return patrolPointMapper.selectCountByEqPatrolPoint(patrolPoint);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(PatrolPoint patrolPoint){
		patrolPointServiceExtend.exportDataCheckExtend(patrolPoint);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(PatrolPoint patrolPoint, List<PatrolPoint> list){
		 patrolPointServiceExtend.exportDataDealExtend(patrolPoint, list);
	 }

    /**
     * 新增巡更点位
     *
     * @param patrolPoint 巡更点位
     * @return 结果
     */
    @Override
	@Transactional
    public int insertPatrolPoint(PatrolPoint patrolPoint)
    {
        patrolPoint.setCreateTime(DateUtils.getNowDate());
		patrolPoint.setUpdateTime(patrolPoint.getCreateTime());
        patrolPointServiceExtend.insertStartExtend( patrolPoint);
		int rows = 0;
 		rows = patrolPointMapper.insertPatrolPoint(patrolPoint);
		patrolPointServiceExtend.insertEndExtend( patrolPoint, rows);
        return Integer.parseInt(patrolPoint.getId()+"");
    }

	/**
     * 批量新增修改巡更点位
     *
     * @param patrolPoint 巡更点位
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByPatrolPoint(List<PatrolPoint> patrolPoints){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = patrolPoints.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<PatrolPoint> batchList = patrolPoints.subList(i, toIndex);
			patrolPointMapper.batchInsertOrUpdateByPatrolPoint(batchList);
		}
	}

    /**
     * 修改巡更点位：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param patrolPoint 巡更点位
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByPatrolPoint(PatrolPoint patrolPoint)
    {
		PatrolPoint oldPatrolPoint = patrolPointMapper.selectDataByPkPatrolPoint(patrolPoint.getId());
        patrolPointServiceExtend.updateStartExtend( patrolPoint, oldPatrolPoint);
		int rows = 0;
		rows = patrolPointMapper.updateNullValueByPatrolPoint(patrolPoint);
		patrolPointServiceExtend.updateEndExtend( patrolPoint,oldPatrolPoint, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param patrolPoint 巡更点位
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByPatrolPoint(PatrolPoint patrolPoint)
    {
		PatrolPoint oldPatrolPoint = patrolPointMapper.selectDataByPkPatrolPoint(patrolPoint.getId());
        patrolPointServiceExtend.updateStartExtend( patrolPoint, oldPatrolPoint);
		int rows = 0;
		rows = patrolPointMapper.updateNotNullValueByPatrolPoint(patrolPoint);
		patrolPointServiceExtend.updateEndExtend( patrolPoint,oldPatrolPoint, rows);
        return rows;
    }

	/**
     * 删除PatrolPointById
     *
     * @param id 巡更点位主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deletePatrolPointById(PatrolPoint patrolPoint)
    {
		try {
        	return patrolPointMapper.deletePatrolPointById(patrolPoint);
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
     * 批量删除PatrolPointByIds
     *
     * @param ids 需要删除的巡更点位主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deletePatrolPointByIds(PatrolPoint patrolPoint) {
		try {
			patrolPointServiceExtend.deleteByIdsStartExtend(patrolPoint);
			int rows = patrolPointMapper.deletePatrolPointByIds(patrolPoint);
			patrolPointServiceExtend.deleteByIdsEndExtend(patrolPoint, rows);
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
     * 批量删除PatrolPointByEqPatrolPoint
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deletePatrolPointByEqPatrolPoint(PatrolPoint patrolPoint){
		try {
			patrolPointServiceExtend.deleteByEqPatrolPointStartExtend(patrolPoint);
			int rows = patrolPointMapper.deletePatrolPointByEqPatrolPoint(patrolPoint);
			patrolPointServiceExtend.deleteByEqPatrolPointEndExtend(patrolPoint, rows);
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
    public int deletePatrolPointAllData(){
		try {
			int rows = patrolPointMapper.deletePatrolPointAllData();
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
    public String importPatrolPointData(List<PatrolPoint> dataList, Boolean isUpdateSupport, String operName, PatrolPoint pPatrolPoint)
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
		PatrolPoint patrolPoint = new PatrolPoint();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		patrolPointServiceExtend.importDataStartExtend(dataList, pPatrolPoint, operName, nowDate);

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
				patrolPoint =dataList.get(i);
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(patrolPoint.getPointName())){
					checkMsg += headString + "点位名称字段为必填项。";
				}
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(patrolPoint.getPointLocation())){
					checkMsg += headString + "点位地点字段为必填项。";
				}

		        // 检查完必填字段和取值范围后，在进行重复记录校验
		        if ("".equals(checkMsg)) {
			        // 进行导入扩展校验
			        checkMsg = patrolPointServiceExtend.importDataCheckExtend(isUpdateSupport, pPatrolPoint, patrolPoint, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, patrolPoint);
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
				this.batchInsertOrUpdateByPatrolPoint(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		patrolPointServiceExtend.importDataEndExtend(dataList, pPatrolPoint, operName, successNum);
        return successMsg.toString();
    }



}
