package com.ruoyi.autoee.patrolRecord.service.impl;

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
import com.ruoyi.autoee.patrolRecord.mapper.PatrolRecordMapper;
import com.ruoyi.autoee.patrolRecord.mapper.PatrolRecordMapperExtend;
import com.ruoyi.autoee.patrolRecord.domain.PatrolRecord;
import com.ruoyi.autoee.patrolRecord.service.IPatrolRecordService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 巡更记录Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class PatrolRecordServiceImpl implements IPatrolRecordService
{
	private static final Logger logger = LoggerFactory.getLogger(PatrolRecordServiceImpl.class);
    @Autowired
    private PatrolRecordMapper patrolRecordMapper;
    @Autowired
    private PatrolRecordMapperExtend patrolRecordMapperExtend;
    @Autowired
    private PatrolRecordServiceExtend patrolRecordServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询巡更记录
     *
     * @param id 巡更记录主键
     * @return 巡更记录
     */
    @Override
    public PatrolRecord selectDataByPkPatrolRecord(Long id)
    {
        return patrolRecordMapper.selectDataByPkPatrolRecord(id);
    }

    /**
     * 通过主键查询巡更记录详细信息
     *
     * @param id 巡更记录主键
     * @return 巡更记录
     */
    @Override
    public PatrolRecord selectDetailByPkPatrolRecord(Long id)
    {
		PatrolRecord patrolRecord = patrolRecordMapper.selectDetailByPkPatrolRecord(id);
		patrolRecordServiceExtend.selectDetailByPkPatrolRecordEndExtend(patrolRecord);
        return patrolRecord;
    }

    /**
     * 查询巡更记录列表
     *
     * @param patrolRecord 巡更记录
     * @return 巡更记录
     */
    @Override
    public List<PatrolRecord> selectDataListByLikePatrolRecord(PatrolRecord patrolRecord)
    {
		patrolRecordServiceExtend.selectListStartExtend(patrolRecord);
		List<PatrolRecord> list = patrolRecordMapper.selectDataListByLikePatrolRecord(patrolRecord);
		patrolRecordServiceExtend.selectListEndExtend(patrolRecord, list);
        return list;
    }

    /**
     * 精确查询巡更记录列表
     *
     * @param patrolRecord 巡更记录
     * @return 巡更记录集合
     */
    public List<PatrolRecord> selectDataListByEqPatrolRecord(PatrolRecord patrolRecord)
	{
		patrolRecordServiceExtend.selectListStartExtend(patrolRecord);
		List<PatrolRecord> list = patrolRecordMapper.selectDataListByEqPatrolRecord(patrolRecord);
		patrolRecordServiceExtend.selectListEndExtend(patrolRecord, list);
        return list;
    }

	/**
     * 查询巡更记录详细列表
     *
     * @param patrolRecord 巡更记录
     * @return 巡更记录集合
     */
    public List<PatrolRecord> selectDetailListByLikePatrolRecord(PatrolRecord patrolRecord)
	{
		patrolRecordServiceExtend.selectDetailListStartExtend(patrolRecord);
		List<PatrolRecord> list = patrolRecordMapper.selectDetailListByLikePatrolRecord(patrolRecord);
		patrolRecordServiceExtend.selectDetailListEndExtend(patrolRecord, list);
        return list;
    }

    /**
     * 精确查询巡更记录详细列表
     *
     * @param patrolRecord 巡更记录
     * @return 巡更记录集合
     */
    public List<PatrolRecord> selectDetailListByEqPatrolRecord(PatrolRecord patrolRecord)
	{
		patrolRecordServiceExtend.selectDetailListByEqPatrolRecordStartExtend(patrolRecord);
		List<PatrolRecord> list = patrolRecordMapper.selectDetailListByEqPatrolRecord(patrolRecord);
		patrolRecordServiceExtend.selectDetailListByEqPatrolRecordEndExtend(patrolRecord, list);
        return list;
    }

	/**
     * 导出巡更记录详细列表
     *
     * @param patrolRecord 巡更记录
     * @return 巡更记录集合
     */
	public List<PatrolRecord> selectExportDetailListPatrolRecord(PatrolRecord patrolRecord){
		patrolRecordServiceExtend.selectExportDetailListStartExtend(patrolRecord);
		List<PatrolRecord> list = patrolRecordMapper.selectDetailListByLikePatrolRecord(patrolRecord);
		patrolRecordServiceExtend.selectExportDetailListEndExtend(patrolRecord, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param patrolRecord 巡更记录
     * @return 巡更记录集合
     */
    public int selectCountByLikePatrolRecord(PatrolRecord patrolRecord){
		return patrolRecordMapper.selectCountByLikePatrolRecord(patrolRecord);
	}

	/**
     * 精确查询记录数
     *
     * @param patrolRecord 巡更记录
     * @return 巡更记录集合
     */
    public int selectCountByEqPatrolRecord(PatrolRecord patrolRecord){
		return patrolRecordMapper.selectCountByEqPatrolRecord(patrolRecord);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(PatrolRecord patrolRecord){
		patrolRecordServiceExtend.exportDataCheckExtend(patrolRecord);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(PatrolRecord patrolRecord, List<PatrolRecord> list){
		 patrolRecordServiceExtend.exportDataDealExtend(patrolRecord, list);
	 }

    /**
     * 新增巡更记录
     *
     * @param patrolRecord 巡更记录
     * @return 结果
     */
    @Override
	@Transactional
    public int insertPatrolRecord(PatrolRecord patrolRecord)
    {
        patrolRecord.setCreateTime(DateUtils.getNowDate());
		patrolRecord.setUpdateTime(patrolRecord.getCreateTime());
        patrolRecordServiceExtend.insertStartExtend( patrolRecord);
		int rows = 0;
 		rows = patrolRecordMapper.insertPatrolRecord(patrolRecord);
		patrolRecordServiceExtend.insertEndExtend( patrolRecord, rows);
        return Integer.parseInt(patrolRecord.getId()+"");
    }

	/**
     * 批量新增修改巡更记录
     *
     * @param patrolRecord 巡更记录
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByPatrolRecord(List<PatrolRecord> patrolRecords){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = patrolRecords.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<PatrolRecord> batchList = patrolRecords.subList(i, toIndex);
			patrolRecordMapper.batchInsertOrUpdateByPatrolRecord(batchList);
		}
	}

    /**
     * 修改巡更记录：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param patrolRecord 巡更记录
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByPatrolRecord(PatrolRecord patrolRecord)
    {
		PatrolRecord oldPatrolRecord = patrolRecordMapper.selectDataByPkPatrolRecord(patrolRecord.getId());
        patrolRecordServiceExtend.updateStartExtend( patrolRecord, oldPatrolRecord);
		int rows = 0;
		rows = patrolRecordMapper.updateNullValueByPatrolRecord(patrolRecord);
		patrolRecordServiceExtend.updateEndExtend( patrolRecord,oldPatrolRecord, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param patrolRecord 巡更记录
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByPatrolRecord(PatrolRecord patrolRecord)
    {
		PatrolRecord oldPatrolRecord = patrolRecordMapper.selectDataByPkPatrolRecord(patrolRecord.getId());
        patrolRecordServiceExtend.updateStartExtend( patrolRecord, oldPatrolRecord);
		int rows = 0;
		rows = patrolRecordMapper.updateNotNullValueByPatrolRecord(patrolRecord);
		patrolRecordServiceExtend.updateEndExtend( patrolRecord,oldPatrolRecord, rows);
        return rows;
    }

	/**
     * 删除PatrolRecordById
     *
     * @param id 巡更记录主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deletePatrolRecordById(PatrolRecord patrolRecord)
    {
		try {
        	return patrolRecordMapper.deletePatrolRecordById(patrolRecord);
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
     * 批量删除PatrolRecordByIds
     *
     * @param ids 需要删除的巡更记录主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deletePatrolRecordByIds(PatrolRecord patrolRecord) {
		try {
			patrolRecordServiceExtend.deleteByIdsStartExtend(patrolRecord);
			int rows = patrolRecordMapper.deletePatrolRecordByIds(patrolRecord);
			patrolRecordServiceExtend.deleteByIdsEndExtend(patrolRecord, rows);
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
     * 批量删除PatrolRecordByEqPatrolRecord
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deletePatrolRecordByEqPatrolRecord(PatrolRecord patrolRecord){
		try {
			patrolRecordServiceExtend.deleteByEqPatrolRecordStartExtend(patrolRecord);
			int rows = patrolRecordMapper.deletePatrolRecordByEqPatrolRecord(patrolRecord);
			patrolRecordServiceExtend.deleteByEqPatrolRecordEndExtend(patrolRecord, rows);
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
    public int deletePatrolRecordAllData(){
		try {
			int rows = patrolRecordMapper.deletePatrolRecordAllData();
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
    public String importPatrolRecordData(List<PatrolRecord> dataList, Boolean isUpdateSupport, String operName, PatrolRecord pPatrolRecord)
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
		PatrolRecord patrolRecord = new PatrolRecord();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		patrolRecordServiceExtend.importDataStartExtend(dataList, pPatrolRecord, operName, nowDate);

		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolPlanIdLableValueMap =  commonService.getDictLableValueMap("a_patrol_plan");
		String  patrolPlanIdAllDictLableStr = commonService.getDictAllLableStr("a_patrol_plan");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolPathIdLableValueMap =  commonService.getDictLableValueMap("a_patrol_path");
		String  patrolPathIdAllDictLableStr = commonService.getDictAllLableStr("a_patrol_path");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolPointIdLableValueMap =  commonService.getDictLableValueMap("a_patrol_point");
		String  patrolPointIdAllDictLableStr = commonService.getDictAllLableStr("a_patrol_point");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolUserIdLableValueMap =  commonService.getDictLableValueMap("sys_user");
		String  patrolUserIdAllDictLableStr = commonService.getDictAllLableStr("sys_user");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolTaskIdLableValueMap =  commonService.getDictLableValueMap("a_patrol_task");
		String  patrolTaskIdAllDictLableStr = commonService.getDictAllLableStr("a_patrol_task");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolResultLableValueMap =  commonService.getDictLableValueMap("patrol_result");
		String  patrolResultAllDictLableStr = commonService.getDictAllLableStr("patrol_result");
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
				patrolRecord =dataList.get(i);
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolRecord.getPatrolPlanIdExtend())){
					checkMsg += headString + "巡更计划字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolPlanId = patrolRecord.getPatrolPlanIdExtend();
                if (StrUtil.isNotBlank(patrolPlanId)) {
					// 通过名称取对应的字典值
                    dictValue = patrolPlanIdLableValueMap.get(patrolPlanId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        patrolRecord.setPatrolPlanId(Long.parseLong(dictValue));
                    } else {
						//allDictLableStr = CollUtil.join(patrolPlanIdLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(patrolPlanIdAllDictLableStr)) {
                        	checkMsg += headString + "巡更计划字段的录入值["+ patrolPlanId +"]必须属于以下取值范围["+patrolPlanIdAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "巡更计划字段的录入值["+ patrolPlanId +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolRecord.getPatrolPathIdExtend())){
					checkMsg += headString + "巡更路线字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolPathId = patrolRecord.getPatrolPathIdExtend();
                if (StrUtil.isNotBlank(patrolPathId)) {
					// 通过名称取对应的字典值
                    dictValue = patrolPathIdLableValueMap.get(patrolPathId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        patrolRecord.setPatrolPathId(Long.parseLong(dictValue));
                    } else {
						//allDictLableStr = CollUtil.join(patrolPathIdLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(patrolPathIdAllDictLableStr)) {
                        	checkMsg += headString + "巡更路线字段的录入值["+ patrolPathId +"]必须属于以下取值范围["+patrolPathIdAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "巡更路线字段的录入值["+ patrolPathId +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolRecord.getPatrolPointIdExtend())){
					checkMsg += headString + "巡更点位字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolPointId = patrolRecord.getPatrolPointIdExtend();
                if (StrUtil.isNotBlank(patrolPointId)) {
					// 通过名称取对应的字典值
                    dictValue = patrolPointIdLableValueMap.get(patrolPointId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        patrolRecord.setPatrolPointId(Long.parseLong(dictValue));
                    } else {
						//allDictLableStr = CollUtil.join(patrolPointIdLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(patrolPointIdAllDictLableStr)) {
                        	checkMsg += headString + "巡更点位字段的录入值["+ patrolPointId +"]必须属于以下取值范围["+patrolPointIdAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "巡更点位字段的录入值["+ patrolPointId +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolRecord.getPatrolUserIdExtend())){
					checkMsg += headString + "巡更人员字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolUserId = patrolRecord.getPatrolUserIdExtend();
                if (StrUtil.isNotBlank(patrolUserId)) {
					// 通过名称取对应的字典值
                    dictValue = patrolUserIdLableValueMap.get(patrolUserId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        patrolRecord.setPatrolUserId(Long.parseLong(dictValue));
                    } else {
						//allDictLableStr = CollUtil.join(patrolUserIdLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(patrolUserIdAllDictLableStr)) {
                        	checkMsg += headString + "巡更人员字段的录入值["+ patrolUserId +"]必须属于以下取值范围["+patrolUserIdAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "巡更人员字段的录入值["+ patrolUserId +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolRecord.getPatrolTaskIdExtend())){
					checkMsg += headString + "巡更任务字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolTaskId = patrolRecord.getPatrolTaskIdExtend();
                if (StrUtil.isNotBlank(patrolTaskId)) {
					// 通过名称取对应的字典值
                    dictValue = patrolTaskIdLableValueMap.get(patrolTaskId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        patrolRecord.setPatrolTaskId(Long.parseLong(dictValue));
                    } else {
						//allDictLableStr = CollUtil.join(patrolTaskIdLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(patrolTaskIdAllDictLableStr)) {
                        	checkMsg += headString + "巡更任务字段的录入值["+ patrolTaskId +"]必须属于以下取值范围["+patrolTaskIdAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "巡更任务字段的录入值["+ patrolTaskId +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：数字类型字段
		        if (null == patrolRecord.getPointOrder()){
		        	checkMsg += headString + "点位顺序字段为必填项。";
	        	}
		        // 检查必填字段：数字类型字段
		        if (null == patrolRecord.getArriveTime()){
		        	checkMsg += headString + "点位巡更时间字段为必填项。";
	        	}
		        // 检查日期时间类型
		        if (null != patrolRecord.getArriveTime() && DateUtil.isSameDay(patrolRecord.getArriveTime(), DateUtil.parseDate("9999-09-09"))){
		        	checkMsg += headString + "点位巡更时间字段为日期时间类型，格式必须属于以下范围[yyyy-MM-dd HH:mm:ss,yyyy/MM/dd HH:mm:ss]。";
	        	}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolRecord.getPatrolResultExtend())){
					checkMsg += headString + "巡更结果字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolResult = patrolRecord.getPatrolResultExtend();
                if (StrUtil.isNotBlank(patrolResult)) {
					// 通过名称取对应的字典值
                    dictValue = patrolResultLableValueMap.get(patrolResult);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						patrolRecord.setPatrolResult(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(patrolResultLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(patrolResultAllDictLableStr)) {
                        	checkMsg += headString + "巡更结果字段的录入值["+ patrolResult +"]必须属于以下取值范围["+patrolResultAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "巡更结果字段的录入值["+ patrolResult +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }

		        // 检查完必填字段和取值范围后，在进行重复记录校验
		        if ("".equals(checkMsg)) {
			        // 进行导入扩展校验
			        checkMsg = patrolRecordServiceExtend.importDataCheckExtend(isUpdateSupport, pPatrolRecord, patrolRecord, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, patrolRecord);
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
				this.batchInsertOrUpdateByPatrolRecord(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		patrolRecordServiceExtend.importDataEndExtend(dataList, pPatrolRecord, operName, successNum);
        return successMsg.toString();
    }



}
