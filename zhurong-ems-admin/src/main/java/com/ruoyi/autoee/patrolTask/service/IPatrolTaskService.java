package com.ruoyi.autoee.patrolTask.service;

import com.ruoyi.common.core.domain.AjaxResult;
import java.util.List;
import com.ruoyi.autoee.patrolTask.domain.PatrolTask;

/**
 * 巡更任务Service接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface IPatrolTaskService
{
    /**
     * 查询巡更任务
     *
     * @param id 巡更任务主键
     * @return 巡更任务
     */
    public PatrolTask selectDataByPkPatrolTask(Long id);

    /**
     * 查询巡更任务详细信息
     *
     * @param id 巡更任务主键
     * @return 巡更任务
     */
    public PatrolTask selectDetailByPkPatrolTask(Long id);

    /**
     * 查询巡更任务列表
     *
     * @param patrolTask 巡更任务
     * @return 巡更任务集合
     */
    public List<PatrolTask> selectDataListByLikePatrolTask(PatrolTask patrolTask);

    /**
     * 精确查询巡更任务列表
     *
     * @param patrolTask 巡更任务
     * @return 巡更任务集合
     */
    public List<PatrolTask> selectDataListByEqPatrolTask(PatrolTask patrolTask);

	/**
     * 查询巡更任务详细列表
     *
     * @param patrolTask 巡更任务
     * @return 巡更任务集合
     */
    public List<PatrolTask> selectDetailListByLikePatrolTask(PatrolTask patrolTask);

    /**
     * 精确查询巡更任务详细列表
     *
     * @param patrolTask 巡更任务
     * @return 巡更任务集合
     */
    public List<PatrolTask> selectDetailListByEqPatrolTask(PatrolTask patrolTask);

	/**
     * 导出巡更任务详细列表
     *
     * @param patrolTask 巡更任务
     * @return 巡更任务集合
     */
	public List<PatrolTask> selectExportDetailListPatrolTask(PatrolTask patrolTask);

	/**
     * 模糊查询记录数
     *
     * @param patrolTask 巡更任务
     * @return 巡更任务集合
     */
    public int selectCountByLikePatrolTask(PatrolTask patrolTask);

	/**
     * 精确查询记录数
     *
     * @param patrolTask 巡更任务
     * @return 巡更任务集合
     */
    public int selectCountByEqPatrolTask(PatrolTask patrolTask);

	/**
     * 导出前校验
     */
    public void exportDataCheck(PatrolTask patrolTask);

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(PatrolTask patrolTask, List<PatrolTask> list);

    /**
     * 新增巡更任务
     *
     * @param patrolTask 巡更任务
     * @return 结果
     */
    public int insertPatrolTask(PatrolTask patrolTask);

    /**
     * 批量新增修改巡更任务
     *
     * @param patrolTask 巡更任务
     * @return 结果
     */
    public void batchInsertOrUpdateByPatrolTask(List<PatrolTask> patrolTasks);

    /**
     * 修改巡更任务：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param patrolTask 巡更任务
     * @return 结果
     */
    public int updateNullValueByPatrolTask(PatrolTask patrolTask);

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param patrolTask 巡更任务
     * @return 结果
     */
    public int updateNotNullValueByPatrolTask(PatrolTask patrolTask);

    /**
     * 删除巡更任务ById
     *
     * @param id 巡更任务主键
     * @return 结果
     */
    public int deletePatrolTaskById(PatrolTask patrolTask);

    /**
     * 批量删除PatrolTaskByIds
     *
     * @param ids 需要删除的巡更任务主键集合
     * @return 结果
     */
	public int deletePatrolTaskByIds(PatrolTask patrolTask);
    
    /**
     * 批量删除PatrolTaskByEqPatrolTask
     *
     * @return 结果
     */
    public int deletePatrolTaskByEqPatrolTask(PatrolTask patrolTask);

	/**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deletePatrolTaskAllData();

	   /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importPatrolTaskData(List<PatrolTask> dataList, Boolean isUpdateSupport, String operName, PatrolTask patrolTask);


}
