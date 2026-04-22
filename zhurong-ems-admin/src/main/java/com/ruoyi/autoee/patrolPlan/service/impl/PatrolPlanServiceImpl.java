package com.ruoyi.autoee.patrolPlan.service.impl;

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
import com.ruoyi.autoee.patrolPlan.mapper.PatrolPlanMapper;
import com.ruoyi.autoee.patrolPlan.mapper.PatrolPlanMapperExtend;
import com.ruoyi.autoee.patrolPlan.domain.PatrolPlan;
import com.ruoyi.autoee.patrolPlan.service.IPatrolPlanService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 巡更计划Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class PatrolPlanServiceImpl implements IPatrolPlanService
{
	private static final Logger logger = LoggerFactory.getLogger(PatrolPlanServiceImpl.class);
    @Autowired
    private PatrolPlanMapper patrolPlanMapper;
    @Autowired
    private PatrolPlanMapperExtend patrolPlanMapperExtend;
    @Autowired
    private PatrolPlanServiceExtend patrolPlanServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询巡更计划
     *
     * @param id 巡更计划主键
     * @return 巡更计划
     */
    @Override
    public PatrolPlan selectDataByPkPatrolPlan(Long id)
    {
        return patrolPlanMapper.selectDataByPkPatrolPlan(id);
    }

    /**
     * 通过主键查询巡更计划详细信息
     *
     * @param id 巡更计划主键
     * @return 巡更计划
     */
    @Override
    public PatrolPlan selectDetailByPkPatrolPlan(Long id)
    {
		PatrolPlan patrolPlan = patrolPlanMapper.selectDetailByPkPatrolPlan(id);
		patrolPlanServiceExtend.selectDetailByPkPatrolPlanEndExtend(patrolPlan);
        return patrolPlan;
    }

    /**
     * 查询巡更计划列表
     *
     * @param patrolPlan 巡更计划
     * @return 巡更计划
     */
    @Override
    public List<PatrolPlan> selectDataListByLikePatrolPlan(PatrolPlan patrolPlan)
    {
		patrolPlanServiceExtend.selectListStartExtend(patrolPlan);
		List<PatrolPlan> list = patrolPlanMapper.selectDataListByLikePatrolPlan(patrolPlan);
		patrolPlanServiceExtend.selectListEndExtend(patrolPlan, list);
        return list;
    }

    /**
     * 精确查询巡更计划列表
     *
     * @param patrolPlan 巡更计划
     * @return 巡更计划集合
     */
    public List<PatrolPlan> selectDataListByEqPatrolPlan(PatrolPlan patrolPlan)
	{
		patrolPlanServiceExtend.selectListStartExtend(patrolPlan);
		List<PatrolPlan> list = patrolPlanMapper.selectDataListByEqPatrolPlan(patrolPlan);
		patrolPlanServiceExtend.selectListEndExtend(patrolPlan, list);
        return list;
    }

	/**
     * 查询巡更计划详细列表
     *
     * @param patrolPlan 巡更计划
     * @return 巡更计划集合
     */
    public List<PatrolPlan> selectDetailListByLikePatrolPlan(PatrolPlan patrolPlan)
	{
		patrolPlanServiceExtend.selectDetailListStartExtend(patrolPlan);
		List<PatrolPlan> list = patrolPlanMapper.selectDetailListByLikePatrolPlan(patrolPlan);
		patrolPlanServiceExtend.selectDetailListEndExtend(patrolPlan, list);
        return list;
    }

    /**
     * 精确查询巡更计划详细列表
     *
     * @param patrolPlan 巡更计划
     * @return 巡更计划集合
     */
    public List<PatrolPlan> selectDetailListByEqPatrolPlan(PatrolPlan patrolPlan)
	{
		patrolPlanServiceExtend.selectDetailListByEqPatrolPlanStartExtend(patrolPlan);
		List<PatrolPlan> list = patrolPlanMapper.selectDetailListByEqPatrolPlan(patrolPlan);
		patrolPlanServiceExtend.selectDetailListByEqPatrolPlanEndExtend(patrolPlan, list);
        return list;
    }

	/**
     * 导出巡更计划详细列表
     *
     * @param patrolPlan 巡更计划
     * @return 巡更计划集合
     */
	public List<PatrolPlan> selectExportDetailListPatrolPlan(PatrolPlan patrolPlan){
		patrolPlanServiceExtend.selectExportDetailListStartExtend(patrolPlan);
		List<PatrolPlan> list = patrolPlanMapper.selectDetailListByLikePatrolPlan(patrolPlan);
		patrolPlanServiceExtend.selectExportDetailListEndExtend(patrolPlan, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param patrolPlan 巡更计划
     * @return 巡更计划集合
     */
    public int selectCountByLikePatrolPlan(PatrolPlan patrolPlan){
		return patrolPlanMapper.selectCountByLikePatrolPlan(patrolPlan);
	}

	/**
     * 精确查询记录数
     *
     * @param patrolPlan 巡更计划
     * @return 巡更计划集合
     */
    public int selectCountByEqPatrolPlan(PatrolPlan patrolPlan){
		return patrolPlanMapper.selectCountByEqPatrolPlan(patrolPlan);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(PatrolPlan patrolPlan){
		patrolPlanServiceExtend.exportDataCheckExtend(patrolPlan);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(PatrolPlan patrolPlan, List<PatrolPlan> list){
		 patrolPlanServiceExtend.exportDataDealExtend(patrolPlan, list);
	 }

    /**
     * 新增巡更计划
     *
     * @param patrolPlan 巡更计划
     * @return 结果
     */
    @Override
	@Transactional
    public int insertPatrolPlan(PatrolPlan patrolPlan)
    {
        patrolPlan.setCreateTime(DateUtils.getNowDate());
		patrolPlan.setUpdateTime(patrolPlan.getCreateTime());
        patrolPlanServiceExtend.insertStartExtend( patrolPlan);
		int rows = 0;
 		rows = patrolPlanMapper.insertPatrolPlan(patrolPlan);
		patrolPlanServiceExtend.insertEndExtend( patrolPlan, rows);
        return Integer.parseInt(patrolPlan.getId()+"");
    }

	/**
     * 批量新增修改巡更计划
     *
     * @param patrolPlan 巡更计划
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByPatrolPlan(List<PatrolPlan> patrolPlans){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = patrolPlans.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<PatrolPlan> batchList = patrolPlans.subList(i, toIndex);
			patrolPlanMapper.batchInsertOrUpdateByPatrolPlan(batchList);
		}
	}

    /**
     * 修改巡更计划：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param patrolPlan 巡更计划
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByPatrolPlan(PatrolPlan patrolPlan)
    {
		PatrolPlan oldPatrolPlan = patrolPlanMapper.selectDataByPkPatrolPlan(patrolPlan.getId());
        patrolPlanServiceExtend.updateStartExtend( patrolPlan, oldPatrolPlan);
		int rows = 0;
		rows = patrolPlanMapper.updateNullValueByPatrolPlan(patrolPlan);
		patrolPlanServiceExtend.updateEndExtend( patrolPlan,oldPatrolPlan, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param patrolPlan 巡更计划
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByPatrolPlan(PatrolPlan patrolPlan)
    {
		PatrolPlan oldPatrolPlan = patrolPlanMapper.selectDataByPkPatrolPlan(patrolPlan.getId());
        patrolPlanServiceExtend.updateStartExtend( patrolPlan, oldPatrolPlan);
		int rows = 0;
		rows = patrolPlanMapper.updateNotNullValueByPatrolPlan(patrolPlan);
		patrolPlanServiceExtend.updateEndExtend( patrolPlan,oldPatrolPlan, rows);
        return rows;
    }

	/**
     * 删除PatrolPlanById
     *
     * @param id 巡更计划主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deletePatrolPlanById(PatrolPlan patrolPlan)
    {
		try {
        	return patrolPlanMapper.deletePatrolPlanById(patrolPlan);
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
     * 批量删除PatrolPlanByIds
     *
     * @param ids 需要删除的巡更计划主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deletePatrolPlanByIds(PatrolPlan patrolPlan) {
		try {
			patrolPlanServiceExtend.deleteByIdsStartExtend(patrolPlan);
			int rows = patrolPlanMapper.deletePatrolPlanByIds(patrolPlan);
			patrolPlanServiceExtend.deleteByIdsEndExtend(patrolPlan, rows);
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
     * 批量删除PatrolPlanByEqPatrolPlan
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deletePatrolPlanByEqPatrolPlan(PatrolPlan patrolPlan){
		try {
			patrolPlanServiceExtend.deleteByEqPatrolPlanStartExtend(patrolPlan);
			int rows = patrolPlanMapper.deletePatrolPlanByEqPatrolPlan(patrolPlan);
			patrolPlanServiceExtend.deleteByEqPatrolPlanEndExtend(patrolPlan, rows);
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
    public int deletePatrolPlanAllData(){
		try {
			int rows = patrolPlanMapper.deletePatrolPlanAllData();
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
    public String importPatrolPlanData(List<PatrolPlan> dataList, Boolean isUpdateSupport, String operName, PatrolPlan pPatrolPlan)
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
		PatrolPlan patrolPlan = new PatrolPlan();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		patrolPlanServiceExtend.importDataStartExtend(dataList, pPatrolPlan, operName, nowDate);

		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolPathIdLableValueMap =  commonService.getDictLableValueMap("a_patrol_path");
		String  patrolPathIdAllDictLableStr = commonService.getDictAllLableStr("a_patrol_path");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolUserIdLableValueMap =  commonService.getDictLableValueMap("sys_user");
		String  patrolUserIdAllDictLableStr = commonService.getDictAllLableStr("sys_user");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolCycleTypeLableValueMap =  commonService.getDictLableValueMap("patrol_cycle_type");
		String  patrolCycleTypeAllDictLableStr = commonService.getDictAllLableStr("patrol_cycle_type");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolPlanStatusLableValueMap =  commonService.getDictLableValueMap("patrol_plan_status");
		String  patrolPlanStatusAllDictLableStr = commonService.getDictAllLableStr("patrol_plan_status");
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
				patrolPlan =dataList.get(i);
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(patrolPlan.getPatrolPlanName())){
					checkMsg += headString + "巡更计划名称字段为必填项。";
				}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolPlan.getPatrolPathIdExtend())){
					checkMsg += headString + "巡更路线字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolPathId = patrolPlan.getPatrolPathIdExtend();
                if (StrUtil.isNotBlank(patrolPathId)) {
					// 通过名称取对应的字典值
                    dictValue = patrolPathIdLableValueMap.get(patrolPathId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        patrolPlan.setPatrolPathId(Long.parseLong(dictValue));
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
		        if (StrUtil.isBlank(patrolPlan.getPatrolUserIdExtend())){
					checkMsg += headString + "巡更人员字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolUserId = patrolPlan.getPatrolUserIdExtend();
                if (StrUtil.isNotBlank(patrolUserId)) {
					// 通过名称取对应的字典值
                    dictValue = patrolUserIdLableValueMap.get(patrolUserId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        patrolPlan.setPatrolUserId(Long.parseLong(dictValue));
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
		        if (null == patrolPlan.getStartTime()){
		        	checkMsg += headString + "开始时间字段为必填项。";
	        	}
		        // 检查必填字段：数字类型字段
		        if (null == patrolPlan.getEndTime()){
		        	checkMsg += headString + "结束时间字段为必填项。";
	        	}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolPlan.getPatrolCycleTypeExtend())){
					checkMsg += headString + "巡更周期字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolCycleType = patrolPlan.getPatrolCycleTypeExtend();
                if (StrUtil.isNotBlank(patrolCycleType)) {
					// 通过名称取对应的字典值
                    dictValue = patrolCycleTypeLableValueMap.get(patrolCycleType);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						patrolPlan.setPatrolCycleType(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(patrolCycleTypeLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(patrolCycleTypeAllDictLableStr)) {
                        	checkMsg += headString + "巡更周期字段的录入值["+ patrolCycleType +"]必须属于以下取值范围["+patrolCycleTypeAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "巡更周期字段的录入值["+ patrolCycleType +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：数字类型字段
		        if (null == patrolPlan.getStartDate()){
		        	checkMsg += headString + "计划开始日期字段为必填项。";
	        	}
		        // 检查日期类型
		        if (null != patrolPlan.getStartDate() && DateUtil.isSameDay(patrolPlan.getStartDate(), DateUtil.parseDate("9999-09-09"))){
		        	checkMsg += headString + "计划开始日期字段为日期类型，格式必须属于以下范围[yyyy-MM-dd,yyyy/MM/dd]。";
	        	}
					        // 检查必填字段：数字类型字段
		        if (null == patrolPlan.getEndDate()){
		        	checkMsg += headString + "计划结束日期字段为必填项。";
	        	}
		        // 检查日期类型
		        if (null != patrolPlan.getEndDate() && DateUtil.isSameDay(patrolPlan.getEndDate(), DateUtil.parseDate("9999-09-09"))){
		        	checkMsg += headString + "计划结束日期字段为日期类型，格式必须属于以下范围[yyyy-MM-dd,yyyy/MM/dd]。";
	        	}
					        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolPlan.getPatrolPlanStatusExtend())){
					checkMsg += headString + "计划状态字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolPlanStatus = patrolPlan.getPatrolPlanStatusExtend();
                if (StrUtil.isNotBlank(patrolPlanStatus)) {
					// 通过名称取对应的字典值
                    dictValue = patrolPlanStatusLableValueMap.get(patrolPlanStatus);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						patrolPlan.setPatrolPlanStatus(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(patrolPlanStatusLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(patrolPlanStatusAllDictLableStr)) {
                        	checkMsg += headString + "计划状态字段的录入值["+ patrolPlanStatus +"]必须属于以下取值范围["+patrolPlanStatusAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "计划状态字段的录入值["+ patrolPlanStatus +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }

		        // 检查完必填字段和取值范围后，在进行重复记录校验
		        if ("".equals(checkMsg)) {
			        // 进行导入扩展校验
			        checkMsg = patrolPlanServiceExtend.importDataCheckExtend(isUpdateSupport, pPatrolPlan, patrolPlan, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, patrolPlan);
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
				this.batchInsertOrUpdateByPatrolPlan(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		patrolPlanServiceExtend.importDataEndExtend(dataList, pPatrolPlan, operName, successNum);
        return successMsg.toString();
    }



}
