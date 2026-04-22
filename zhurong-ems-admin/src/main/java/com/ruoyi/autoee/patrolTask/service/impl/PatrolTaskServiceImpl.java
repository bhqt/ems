package com.ruoyi.autoee.patrolTask.service.impl;

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
import com.ruoyi.autoee.patrolTask.mapper.PatrolTaskMapper;
import com.ruoyi.autoee.patrolTask.mapper.PatrolTaskMapperExtend;
import com.ruoyi.autoee.patrolTask.domain.PatrolTask;
import com.ruoyi.autoee.patrolTask.service.IPatrolTaskService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 巡更任务Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class PatrolTaskServiceImpl implements IPatrolTaskService
{
	private static final Logger logger = LoggerFactory.getLogger(PatrolTaskServiceImpl.class);
    @Autowired
    private PatrolTaskMapper patrolTaskMapper;
    @Autowired
    private PatrolTaskMapperExtend patrolTaskMapperExtend;
    @Autowired
    private PatrolTaskServiceExtend patrolTaskServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询巡更任务
     *
     * @param id 巡更任务主键
     * @return 巡更任务
     */
    @Override
    public PatrolTask selectDataByPkPatrolTask(Long id)
    {
        return patrolTaskMapper.selectDataByPkPatrolTask(id);
    }

    /**
     * 通过主键查询巡更任务详细信息
     *
     * @param id 巡更任务主键
     * @return 巡更任务
     */
    @Override
    public PatrolTask selectDetailByPkPatrolTask(Long id)
    {
		PatrolTask patrolTask = patrolTaskMapper.selectDetailByPkPatrolTask(id);
		patrolTaskServiceExtend.selectDetailByPkPatrolTaskEndExtend(patrolTask);
        return patrolTask;
    }

    /**
     * 查询巡更任务列表
     *
     * @param patrolTask 巡更任务
     * @return 巡更任务
     */
    @Override
    public List<PatrolTask> selectDataListByLikePatrolTask(PatrolTask patrolTask)
    {
		patrolTaskServiceExtend.selectListStartExtend(patrolTask);
		List<PatrolTask> list = patrolTaskMapper.selectDataListByLikePatrolTask(patrolTask);
		patrolTaskServiceExtend.selectListEndExtend(patrolTask, list);
        return list;
    }

    /**
     * 精确查询巡更任务列表
     *
     * @param patrolTask 巡更任务
     * @return 巡更任务集合
     */
    public List<PatrolTask> selectDataListByEqPatrolTask(PatrolTask patrolTask)
	{
		patrolTaskServiceExtend.selectListStartExtend(patrolTask);
		List<PatrolTask> list = patrolTaskMapper.selectDataListByEqPatrolTask(patrolTask);
		patrolTaskServiceExtend.selectListEndExtend(patrolTask, list);
        return list;
    }

	/**
     * 查询巡更任务详细列表
     *
     * @param patrolTask 巡更任务
     * @return 巡更任务集合
     */
    public List<PatrolTask> selectDetailListByLikePatrolTask(PatrolTask patrolTask)
	{
		patrolTaskServiceExtend.selectDetailListStartExtend(patrolTask);
		List<PatrolTask> list = patrolTaskMapper.selectDetailListByLikePatrolTask(patrolTask);
		patrolTaskServiceExtend.selectDetailListEndExtend(patrolTask, list);
        return list;
    }

    /**
     * 精确查询巡更任务详细列表
     *
     * @param patrolTask 巡更任务
     * @return 巡更任务集合
     */
    public List<PatrolTask> selectDetailListByEqPatrolTask(PatrolTask patrolTask)
	{
		patrolTaskServiceExtend.selectDetailListByEqPatrolTaskStartExtend(patrolTask);
		List<PatrolTask> list = patrolTaskMapper.selectDetailListByEqPatrolTask(patrolTask);
		patrolTaskServiceExtend.selectDetailListByEqPatrolTaskEndExtend(patrolTask, list);
        return list;
    }

	/**
     * 导出巡更任务详细列表
     *
     * @param patrolTask 巡更任务
     * @return 巡更任务集合
     */
	public List<PatrolTask> selectExportDetailListPatrolTask(PatrolTask patrolTask){
		patrolTaskServiceExtend.selectExportDetailListStartExtend(patrolTask);
		List<PatrolTask> list = patrolTaskMapper.selectDetailListByLikePatrolTask(patrolTask);
		patrolTaskServiceExtend.selectExportDetailListEndExtend(patrolTask, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param patrolTask 巡更任务
     * @return 巡更任务集合
     */
    public int selectCountByLikePatrolTask(PatrolTask patrolTask){
		return patrolTaskMapper.selectCountByLikePatrolTask(patrolTask);
	}

	/**
     * 精确查询记录数
     *
     * @param patrolTask 巡更任务
     * @return 巡更任务集合
     */
    public int selectCountByEqPatrolTask(PatrolTask patrolTask){
		return patrolTaskMapper.selectCountByEqPatrolTask(patrolTask);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(PatrolTask patrolTask){
		patrolTaskServiceExtend.exportDataCheckExtend(patrolTask);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(PatrolTask patrolTask, List<PatrolTask> list){
		 patrolTaskServiceExtend.exportDataDealExtend(patrolTask, list);
	 }

    /**
     * 新增巡更任务
     *
     * @param patrolTask 巡更任务
     * @return 结果
     */
    @Override
	@Transactional
    public int insertPatrolTask(PatrolTask patrolTask)
    {
        patrolTask.setCreateTime(DateUtils.getNowDate());
		patrolTask.setUpdateTime(patrolTask.getCreateTime());
        patrolTaskServiceExtend.insertStartExtend( patrolTask);
		int rows = 0;
 		rows = patrolTaskMapper.insertPatrolTask(patrolTask);
		patrolTaskServiceExtend.insertEndExtend( patrolTask, rows);
        return Integer.parseInt(patrolTask.getId()+"");
    }

	/**
     * 批量新增修改巡更任务
     *
     * @param patrolTask 巡更任务
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByPatrolTask(List<PatrolTask> patrolTasks){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = patrolTasks.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<PatrolTask> batchList = patrolTasks.subList(i, toIndex);
			patrolTaskMapper.batchInsertOrUpdateByPatrolTask(batchList);
		}
	}

    /**
     * 修改巡更任务：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param patrolTask 巡更任务
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByPatrolTask(PatrolTask patrolTask)
    {
		PatrolTask oldPatrolTask = patrolTaskMapper.selectDataByPkPatrolTask(patrolTask.getId());
        patrolTaskServiceExtend.updateStartExtend( patrolTask, oldPatrolTask);
		int rows = 0;
		rows = patrolTaskMapper.updateNullValueByPatrolTask(patrolTask);
		patrolTaskServiceExtend.updateEndExtend( patrolTask,oldPatrolTask, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param patrolTask 巡更任务
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByPatrolTask(PatrolTask patrolTask)
    {
		PatrolTask oldPatrolTask = patrolTaskMapper.selectDataByPkPatrolTask(patrolTask.getId());
        patrolTaskServiceExtend.updateStartExtend( patrolTask, oldPatrolTask);
		int rows = 0;
		rows = patrolTaskMapper.updateNotNullValueByPatrolTask(patrolTask);
		patrolTaskServiceExtend.updateEndExtend( patrolTask,oldPatrolTask, rows);
        return rows;
    }

	/**
     * 删除PatrolTaskById
     *
     * @param id 巡更任务主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deletePatrolTaskById(PatrolTask patrolTask)
    {
		try {
        	return patrolTaskMapper.deletePatrolTaskById(patrolTask);
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
     * 批量删除PatrolTaskByIds
     *
     * @param ids 需要删除的巡更任务主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deletePatrolTaskByIds(PatrolTask patrolTask) {
		try {
			patrolTaskServiceExtend.deleteByIdsStartExtend(patrolTask);
			int rows = patrolTaskMapper.deletePatrolTaskByIds(patrolTask);
			patrolTaskServiceExtend.deleteByIdsEndExtend(patrolTask, rows);
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
     * 批量删除PatrolTaskByEqPatrolTask
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deletePatrolTaskByEqPatrolTask(PatrolTask patrolTask){
		try {
			patrolTaskServiceExtend.deleteByEqPatrolTaskStartExtend(patrolTask);
			int rows = patrolTaskMapper.deletePatrolTaskByEqPatrolTask(patrolTask);
			patrolTaskServiceExtend.deleteByEqPatrolTaskEndExtend(patrolTask, rows);
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
    public int deletePatrolTaskAllData(){
		try {
			int rows = patrolTaskMapper.deletePatrolTaskAllData();
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
    public String importPatrolTaskData(List<PatrolTask> dataList, Boolean isUpdateSupport, String operName, PatrolTask pPatrolTask)
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
		PatrolTask patrolTask = new PatrolTask();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		patrolTaskServiceExtend.importDataStartExtend(dataList, pPatrolTask, operName, nowDate);

		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolPlanIdLableValueMap =  commonService.getDictLableValueMap("a_patrol_plan");
		String  patrolPlanIdAllDictLableStr = commonService.getDictAllLableStr("a_patrol_plan");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolPathIdLableValueMap =  commonService.getDictLableValueMap("a_patrol_path");
		String  patrolPathIdAllDictLableStr = commonService.getDictAllLableStr("a_patrol_path");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolUserIdLableValueMap =  commonService.getDictLableValueMap("sys_user");
		String  patrolUserIdAllDictLableStr = commonService.getDictAllLableStr("sys_user");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolTaskStatusLableValueMap =  commonService.getDictLableValueMap("patrol_task_status");
		String  patrolTaskStatusAllDictLableStr = commonService.getDictAllLableStr("patrol_task_status");
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
				patrolTask =dataList.get(i);
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(patrolTask.getPatrolTaskName())){
					checkMsg += headString + "任务名称字段为必填项。";
				}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolTask.getPatrolPlanIdExtend())){
					checkMsg += headString + "巡更计划字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolPlanId = patrolTask.getPatrolPlanIdExtend();
                if (StrUtil.isNotBlank(patrolPlanId)) {
					// 通过名称取对应的字典值
                    dictValue = patrolPlanIdLableValueMap.get(patrolPlanId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        patrolTask.setPatrolPlanId(Long.parseLong(dictValue));
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
		        if (StrUtil.isBlank(patrolTask.getPatrolPathIdExtend())){
					checkMsg += headString + "巡更路线字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolPathId = patrolTask.getPatrolPathIdExtend();
                if (StrUtil.isNotBlank(patrolPathId)) {
					// 通过名称取对应的字典值
                    dictValue = patrolPathIdLableValueMap.get(patrolPathId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        patrolTask.setPatrolPathId(Long.parseLong(dictValue));
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
		        if (StrUtil.isBlank(patrolTask.getPatrolUserIdExtend())){
					checkMsg += headString + "巡更人员字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolUserId = patrolTask.getPatrolUserIdExtend();
                if (StrUtil.isNotBlank(patrolUserId)) {
					// 通过名称取对应的字典值
                    dictValue = patrolUserIdLableValueMap.get(patrolUserId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        patrolTask.setPatrolUserId(Long.parseLong(dictValue));
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
		        // 检查必填字段：数字类型字段
		        if (null == patrolTask.getPatrolDate()){
		        	checkMsg += headString + "巡更日期字段为必填项。";
	        	}
		        // 检查日期类型
		        if (null != patrolTask.getPatrolDate() && DateUtil.isSameDay(patrolTask.getPatrolDate(), DateUtil.parseDate("9999-09-09"))){
		        	checkMsg += headString + "巡更日期字段为日期类型，格式必须属于以下范围[yyyy-MM-dd,yyyy/MM/dd]。";
	        	}
					        // 检查必填字段：数字类型字段
		        if (null == patrolTask.getStartTime()){
		        	checkMsg += headString + "开始时间字段为必填项。";
	        	}
		        // 检查必填字段：数字类型字段
		        if (null == patrolTask.getEndTime()){
		        	checkMsg += headString + "结束时间字段为必填项。";
	        	}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolTask.getPatrolTaskStatusExtend())){
					checkMsg += headString + "任务状态字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolTaskStatus = patrolTask.getPatrolTaskStatusExtend();
                if (StrUtil.isNotBlank(patrolTaskStatus)) {
					// 通过名称取对应的字典值
                    dictValue = patrolTaskStatusLableValueMap.get(patrolTaskStatus);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						patrolTask.setPatrolTaskStatus(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(patrolTaskStatusLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(patrolTaskStatusAllDictLableStr)) {
                        	checkMsg += headString + "任务状态字段的录入值["+ patrolTaskStatus +"]必须属于以下取值范围["+patrolTaskStatusAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "任务状态字段的录入值["+ patrolTaskStatus +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }

		        // 检查完必填字段和取值范围后，在进行重复记录校验
		        if ("".equals(checkMsg)) {
			        // 进行导入扩展校验
			        checkMsg = patrolTaskServiceExtend.importDataCheckExtend(isUpdateSupport, pPatrolTask, patrolTask, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, patrolTask);
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
				this.batchInsertOrUpdateByPatrolTask(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		patrolTaskServiceExtend.importDataEndExtend(dataList, pPatrolTask, operName, successNum);
        return successMsg.toString();
    }



}
