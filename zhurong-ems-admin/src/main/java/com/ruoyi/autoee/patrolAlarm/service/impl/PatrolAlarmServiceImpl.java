package com.ruoyi.autoee.patrolAlarm.service.impl;

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
import com.ruoyi.autoee.patrolAlarm.mapper.PatrolAlarmMapper;
import com.ruoyi.autoee.patrolAlarm.mapper.PatrolAlarmMapperExtend;
import com.ruoyi.autoee.patrolAlarm.domain.PatrolAlarm;
import com.ruoyi.autoee.patrolAlarm.service.IPatrolAlarmService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 巡更报警Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class PatrolAlarmServiceImpl implements IPatrolAlarmService
{
	private static final Logger logger = LoggerFactory.getLogger(PatrolAlarmServiceImpl.class);
    @Autowired
    private PatrolAlarmMapper patrolAlarmMapper;
    @Autowired
    private PatrolAlarmMapperExtend patrolAlarmMapperExtend;
    @Autowired
    private PatrolAlarmServiceExtend patrolAlarmServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询巡更报警
     *
     * @param id 巡更报警主键
     * @return 巡更报警
     */
    @Override
    public PatrolAlarm selectDataByPkPatrolAlarm(Long id)
    {
        return patrolAlarmMapper.selectDataByPkPatrolAlarm(id);
    }

    /**
     * 通过主键查询巡更报警详细信息
     *
     * @param id 巡更报警主键
     * @return 巡更报警
     */
    @Override
    public PatrolAlarm selectDetailByPkPatrolAlarm(Long id)
    {
		PatrolAlarm patrolAlarm = patrolAlarmMapper.selectDetailByPkPatrolAlarm(id);
		patrolAlarmServiceExtend.selectDetailByPkPatrolAlarmEndExtend(patrolAlarm);
        return patrolAlarm;
    }

    /**
     * 查询巡更报警列表
     *
     * @param patrolAlarm 巡更报警
     * @return 巡更报警
     */
    @Override
    public List<PatrolAlarm> selectDataListByLikePatrolAlarm(PatrolAlarm patrolAlarm)
    {
		patrolAlarmServiceExtend.selectListStartExtend(patrolAlarm);
		List<PatrolAlarm> list = patrolAlarmMapper.selectDataListByLikePatrolAlarm(patrolAlarm);
		patrolAlarmServiceExtend.selectListEndExtend(patrolAlarm, list);
        return list;
    }

    /**
     * 精确查询巡更报警列表
     *
     * @param patrolAlarm 巡更报警
     * @return 巡更报警集合
     */
    public List<PatrolAlarm> selectDataListByEqPatrolAlarm(PatrolAlarm patrolAlarm)
	{
		patrolAlarmServiceExtend.selectListStartExtend(patrolAlarm);
		List<PatrolAlarm> list = patrolAlarmMapper.selectDataListByEqPatrolAlarm(patrolAlarm);
		patrolAlarmServiceExtend.selectListEndExtend(patrolAlarm, list);
        return list;
    }

	/**
     * 查询巡更报警详细列表
     *
     * @param patrolAlarm 巡更报警
     * @return 巡更报警集合
     */
    public List<PatrolAlarm> selectDetailListByLikePatrolAlarm(PatrolAlarm patrolAlarm)
	{
		patrolAlarmServiceExtend.selectDetailListStartExtend(patrolAlarm);
		List<PatrolAlarm> list = patrolAlarmMapper.selectDetailListByLikePatrolAlarm(patrolAlarm);
		patrolAlarmServiceExtend.selectDetailListEndExtend(patrolAlarm, list);
        return list;
    }

    /**
     * 精确查询巡更报警详细列表
     *
     * @param patrolAlarm 巡更报警
     * @return 巡更报警集合
     */
    public List<PatrolAlarm> selectDetailListByEqPatrolAlarm(PatrolAlarm patrolAlarm)
	{
		patrolAlarmServiceExtend.selectDetailListByEqPatrolAlarmStartExtend(patrolAlarm);
		List<PatrolAlarm> list = patrolAlarmMapper.selectDetailListByEqPatrolAlarm(patrolAlarm);
		patrolAlarmServiceExtend.selectDetailListByEqPatrolAlarmEndExtend(patrolAlarm, list);
        return list;
    }

	/**
     * 导出巡更报警详细列表
     *
     * @param patrolAlarm 巡更报警
     * @return 巡更报警集合
     */
	public List<PatrolAlarm> selectExportDetailListPatrolAlarm(PatrolAlarm patrolAlarm){
		patrolAlarmServiceExtend.selectExportDetailListStartExtend(patrolAlarm);
		List<PatrolAlarm> list = patrolAlarmMapper.selectDetailListByLikePatrolAlarm(patrolAlarm);
		patrolAlarmServiceExtend.selectExportDetailListEndExtend(patrolAlarm, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param patrolAlarm 巡更报警
     * @return 巡更报警集合
     */
    public int selectCountByLikePatrolAlarm(PatrolAlarm patrolAlarm){
		return patrolAlarmMapper.selectCountByLikePatrolAlarm(patrolAlarm);
	}

	/**
     * 精确查询记录数
     *
     * @param patrolAlarm 巡更报警
     * @return 巡更报警集合
     */
    public int selectCountByEqPatrolAlarm(PatrolAlarm patrolAlarm){
		return patrolAlarmMapper.selectCountByEqPatrolAlarm(patrolAlarm);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(PatrolAlarm patrolAlarm){
		patrolAlarmServiceExtend.exportDataCheckExtend(patrolAlarm);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(PatrolAlarm patrolAlarm, List<PatrolAlarm> list){
		 patrolAlarmServiceExtend.exportDataDealExtend(patrolAlarm, list);
	 }

    /**
     * 新增巡更报警
     *
     * @param patrolAlarm 巡更报警
     * @return 结果
     */
    @Override
	@Transactional
    public int insertPatrolAlarm(PatrolAlarm patrolAlarm)
    {
        patrolAlarm.setCreateTime(DateUtils.getNowDate());
		patrolAlarm.setUpdateTime(patrolAlarm.getCreateTime());
        patrolAlarmServiceExtend.insertStartExtend( patrolAlarm);
		int rows = 0;
 		rows = patrolAlarmMapper.insertPatrolAlarm(patrolAlarm);
		patrolAlarmServiceExtend.insertEndExtend( patrolAlarm, rows);
        return Integer.parseInt(patrolAlarm.getId()+"");
    }

	/**
     * 批量新增修改巡更报警
     *
     * @param patrolAlarm 巡更报警
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByPatrolAlarm(List<PatrolAlarm> patrolAlarms){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = patrolAlarms.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<PatrolAlarm> batchList = patrolAlarms.subList(i, toIndex);
			patrolAlarmMapper.batchInsertOrUpdateByPatrolAlarm(batchList);
		}
	}

    /**
     * 修改巡更报警：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param patrolAlarm 巡更报警
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByPatrolAlarm(PatrolAlarm patrolAlarm)
    {
		PatrolAlarm oldPatrolAlarm = patrolAlarmMapper.selectDataByPkPatrolAlarm(patrolAlarm.getId());
        patrolAlarmServiceExtend.updateStartExtend( patrolAlarm, oldPatrolAlarm);
		int rows = 0;
		rows = patrolAlarmMapper.updateNullValueByPatrolAlarm(patrolAlarm);
		patrolAlarmServiceExtend.updateEndExtend( patrolAlarm,oldPatrolAlarm, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param patrolAlarm 巡更报警
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByPatrolAlarm(PatrolAlarm patrolAlarm)
    {
		PatrolAlarm oldPatrolAlarm = patrolAlarmMapper.selectDataByPkPatrolAlarm(patrolAlarm.getId());
        patrolAlarmServiceExtend.updateStartExtend( patrolAlarm, oldPatrolAlarm);
		int rows = 0;
		rows = patrolAlarmMapper.updateNotNullValueByPatrolAlarm(patrolAlarm);
		patrolAlarmServiceExtend.updateEndExtend( patrolAlarm,oldPatrolAlarm, rows);
        return rows;
    }

	/**
     * 删除PatrolAlarmById
     *
     * @param id 巡更报警主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deletePatrolAlarmById(PatrolAlarm patrolAlarm)
    {
		try {
        	return patrolAlarmMapper.deletePatrolAlarmById(patrolAlarm);
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
     * 批量删除PatrolAlarmByIds
     *
     * @param ids 需要删除的巡更报警主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deletePatrolAlarmByIds(PatrolAlarm patrolAlarm) {
		try {
			patrolAlarmServiceExtend.deleteByIdsStartExtend(patrolAlarm);
			int rows = patrolAlarmMapper.deletePatrolAlarmByIds(patrolAlarm);
			patrolAlarmServiceExtend.deleteByIdsEndExtend(patrolAlarm, rows);
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
     * 批量删除PatrolAlarmByEqPatrolAlarm
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deletePatrolAlarmByEqPatrolAlarm(PatrolAlarm patrolAlarm){
		try {
			patrolAlarmServiceExtend.deleteByEqPatrolAlarmStartExtend(patrolAlarm);
			int rows = patrolAlarmMapper.deletePatrolAlarmByEqPatrolAlarm(patrolAlarm);
			patrolAlarmServiceExtend.deleteByEqPatrolAlarmEndExtend(patrolAlarm, rows);
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
    public int deletePatrolAlarmAllData(){
		try {
			int rows = patrolAlarmMapper.deletePatrolAlarmAllData();
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
    public String importPatrolAlarmData(List<PatrolAlarm> dataList, Boolean isUpdateSupport, String operName, PatrolAlarm pPatrolAlarm)
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
		PatrolAlarm patrolAlarm = new PatrolAlarm();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		patrolAlarmServiceExtend.importDataStartExtend(dataList, pPatrolAlarm, operName, nowDate);

		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolPlanIdLableValueMap =  commonService.getDictLableValueMap("a_patrol_plan");
		String  patrolPlanIdAllDictLableStr = commonService.getDictAllLableStr("a_patrol_plan");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolTaskIdLableValueMap =  commonService.getDictLableValueMap("a_patrol_task");
		String  patrolTaskIdAllDictLableStr = commonService.getDictAllLableStr("a_patrol_task");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolUserIdLableValueMap =  commonService.getDictLableValueMap("sys_user");
		String  patrolUserIdAllDictLableStr = commonService.getDictAllLableStr("sys_user");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolAlarmTypeLableValueMap =  commonService.getDictLableValueMap("patrol_alarm_type");
		String  patrolAlarmTypeAllDictLableStr = commonService.getDictAllLableStr("patrol_alarm_type");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolAlarmStatusLableValueMap =  commonService.getDictLableValueMap("patrol_alarm_status");
		String  patrolAlarmStatusAllDictLableStr = commonService.getDictAllLableStr("patrol_alarm_status");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> handleUserIdLableValueMap =  commonService.getDictLableValueMap("sys_user");
		String  handleUserIdAllDictLableStr = commonService.getDictAllLableStr("sys_user");
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
				patrolAlarm =dataList.get(i);
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(patrolAlarm.getAlarmNo())){
					checkMsg += headString + "报警编号字段为必填项。";
				}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolAlarm.getPatrolPlanIdExtend())){
					checkMsg += headString + "巡更计划字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolPlanId = patrolAlarm.getPatrolPlanIdExtend();
                if (StrUtil.isNotBlank(patrolPlanId)) {
					// 通过名称取对应的字典值
                    dictValue = patrolPlanIdLableValueMap.get(patrolPlanId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        patrolAlarm.setPatrolPlanId(Long.parseLong(dictValue));
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
		        if (StrUtil.isBlank(patrolAlarm.getPatrolTaskIdExtend())){
					checkMsg += headString + "巡更任务字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolTaskId = patrolAlarm.getPatrolTaskIdExtend();
                if (StrUtil.isNotBlank(patrolTaskId)) {
					// 通过名称取对应的字典值
                    dictValue = patrolTaskIdLableValueMap.get(patrolTaskId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        patrolAlarm.setPatrolTaskId(Long.parseLong(dictValue));
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
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolAlarm.getPatrolUserIdExtend())){
					checkMsg += headString + "巡更人员字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolUserId = patrolAlarm.getPatrolUserIdExtend();
                if (StrUtil.isNotBlank(patrolUserId)) {
					// 通过名称取对应的字典值
                    dictValue = patrolUserIdLableValueMap.get(patrolUserId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        patrolAlarm.setPatrolUserId(Long.parseLong(dictValue));
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
		        if (StrUtil.isBlank(patrolAlarm.getPatrolAlarmTypeExtend())){
					checkMsg += headString + "报警类型字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolAlarmType = patrolAlarm.getPatrolAlarmTypeExtend();
                if (StrUtil.isNotBlank(patrolAlarmType)) {
					// 通过名称取对应的字典值
                    dictValue = patrolAlarmTypeLableValueMap.get(patrolAlarmType);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						patrolAlarm.setPatrolAlarmType(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(patrolAlarmTypeLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(patrolAlarmTypeAllDictLableStr)) {
                        	checkMsg += headString + "报警类型字段的录入值["+ patrolAlarmType +"]必须属于以下取值范围["+patrolAlarmTypeAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "报警类型字段的录入值["+ patrolAlarmType +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：数字类型字段
		        if (null == patrolAlarm.getPatrolAlarmTime()){
		        	checkMsg += headString + "报警时间字段为必填项。";
	        	}
		        // 检查日期时间类型
		        if (null != patrolAlarm.getPatrolAlarmTime() && DateUtil.isSameDay(patrolAlarm.getPatrolAlarmTime(), DateUtil.parseDate("9999-09-09"))){
		        	checkMsg += headString + "报警时间字段为日期时间类型，格式必须属于以下范围[yyyy-MM-dd HH:mm:ss,yyyy/MM/dd HH:mm:ss]。";
	        	}
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(patrolAlarm.getPatrolAlarmContent())){
					checkMsg += headString + "报警内容字段为必填项。";
				}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolAlarm.getPatrolAlarmStatusExtend())){
					checkMsg += headString + "报警状态字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolAlarmStatus = patrolAlarm.getPatrolAlarmStatusExtend();
                if (StrUtil.isNotBlank(patrolAlarmStatus)) {
					// 通过名称取对应的字典值
                    dictValue = patrolAlarmStatusLableValueMap.get(patrolAlarmStatus);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						patrolAlarm.setPatrolAlarmStatus(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(patrolAlarmStatusLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(patrolAlarmStatusAllDictLableStr)) {
                        	checkMsg += headString + "报警状态字段的录入值["+ patrolAlarmStatus +"]必须属于以下取值范围["+patrolAlarmStatusAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "报警状态字段的录入值["+ patrolAlarmStatus +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String handleUserId = patrolAlarm.getHandleUserIdExtend();
                if (StrUtil.isNotBlank(handleUserId)) {
					// 通过名称取对应的字典值
                    dictValue = handleUserIdLableValueMap.get(handleUserId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        patrolAlarm.setHandleUserId(Long.parseLong(dictValue));
                    } else {
						//allDictLableStr = CollUtil.join(handleUserIdLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(handleUserIdAllDictLableStr)) {
                        	checkMsg += headString + "处理人字段的录入值["+ handleUserId +"]必须属于以下取值范围["+handleUserIdAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "处理人字段的录入值["+ handleUserId +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查日期时间类型
		        if (null != patrolAlarm.getHandleTime() && DateUtil.isSameDay(patrolAlarm.getHandleTime(), DateUtil.parseDate("9999-09-09"))){
		        	checkMsg += headString + "处理时间字段为日期时间类型，格式必须属于以下范围[yyyy-MM-dd HH:mm:ss,yyyy/MM/dd HH:mm:ss]。";
	        	}

		        // 检查完必填字段和取值范围后，在进行重复记录校验
		        if ("".equals(checkMsg)) {
			        // 进行导入扩展校验
			        checkMsg = patrolAlarmServiceExtend.importDataCheckExtend(isUpdateSupport, pPatrolAlarm, patrolAlarm, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, patrolAlarm);
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
				this.batchInsertOrUpdateByPatrolAlarm(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		patrolAlarmServiceExtend.importDataEndExtend(dataList, pPatrolAlarm, operName, successNum);
        return successMsg.toString();
    }



}
