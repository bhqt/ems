package com.ruoyi.autoee.patrolPlan.service;

import com.ruoyi.common.core.domain.AjaxResult;
import java.util.List;
import com.ruoyi.autoee.patrolPlan.domain.PatrolPlan;

/**
 * 巡更计划Service接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface IPatrolPlanService
{
    /**
     * 查询巡更计划
     *
     * @param id 巡更计划主键
     * @return 巡更计划
     */
    public PatrolPlan selectDataByPkPatrolPlan(Long id);

    /**
     * 查询巡更计划详细信息
     *
     * @param id 巡更计划主键
     * @return 巡更计划
     */
    public PatrolPlan selectDetailByPkPatrolPlan(Long id);

    /**
     * 查询巡更计划列表
     *
     * @param patrolPlan 巡更计划
     * @return 巡更计划集合
     */
    public List<PatrolPlan> selectDataListByLikePatrolPlan(PatrolPlan patrolPlan);

    /**
     * 精确查询巡更计划列表
     *
     * @param patrolPlan 巡更计划
     * @return 巡更计划集合
     */
    public List<PatrolPlan> selectDataListByEqPatrolPlan(PatrolPlan patrolPlan);

	/**
     * 查询巡更计划详细列表
     *
     * @param patrolPlan 巡更计划
     * @return 巡更计划集合
     */
    public List<PatrolPlan> selectDetailListByLikePatrolPlan(PatrolPlan patrolPlan);

    /**
     * 精确查询巡更计划详细列表
     *
     * @param patrolPlan 巡更计划
     * @return 巡更计划集合
     */
    public List<PatrolPlan> selectDetailListByEqPatrolPlan(PatrolPlan patrolPlan);

	/**
     * 导出巡更计划详细列表
     *
     * @param patrolPlan 巡更计划
     * @return 巡更计划集合
     */
	public List<PatrolPlan> selectExportDetailListPatrolPlan(PatrolPlan patrolPlan);

	/**
     * 模糊查询记录数
     *
     * @param patrolPlan 巡更计划
     * @return 巡更计划集合
     */
    public int selectCountByLikePatrolPlan(PatrolPlan patrolPlan);

	/**
     * 精确查询记录数
     *
     * @param patrolPlan 巡更计划
     * @return 巡更计划集合
     */
    public int selectCountByEqPatrolPlan(PatrolPlan patrolPlan);

	/**
     * 导出前校验
     */
    public void exportDataCheck(PatrolPlan patrolPlan);

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(PatrolPlan patrolPlan, List<PatrolPlan> list);

    /**
     * 新增巡更计划
     *
     * @param patrolPlan 巡更计划
     * @return 结果
     */
    public int insertPatrolPlan(PatrolPlan patrolPlan);

    /**
     * 批量新增修改巡更计划
     *
     * @param patrolPlan 巡更计划
     * @return 结果
     */
    public void batchInsertOrUpdateByPatrolPlan(List<PatrolPlan> patrolPlans);

    /**
     * 修改巡更计划：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param patrolPlan 巡更计划
     * @return 结果
     */
    public int updateNullValueByPatrolPlan(PatrolPlan patrolPlan);

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param patrolPlan 巡更计划
     * @return 结果
     */
    public int updateNotNullValueByPatrolPlan(PatrolPlan patrolPlan);

    /**
     * 删除巡更计划ById
     *
     * @param id 巡更计划主键
     * @return 结果
     */
    public int deletePatrolPlanById(PatrolPlan patrolPlan);

    /**
     * 批量删除PatrolPlanByIds
     *
     * @param ids 需要删除的巡更计划主键集合
     * @return 结果
     */
	public int deletePatrolPlanByIds(PatrolPlan patrolPlan);
    
    /**
     * 批量删除PatrolPlanByEqPatrolPlan
     *
     * @return 结果
     */
    public int deletePatrolPlanByEqPatrolPlan(PatrolPlan patrolPlan);

	/**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deletePatrolPlanAllData();

	   /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importPatrolPlanData(List<PatrolPlan> dataList, Boolean isUpdateSupport, String operName, PatrolPlan patrolPlan);


}
