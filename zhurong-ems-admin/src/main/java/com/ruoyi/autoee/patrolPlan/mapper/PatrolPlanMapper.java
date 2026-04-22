package com.ruoyi.autoee.patrolPlan.mapper;

import java.util.List;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.autoee.patrolPlan.domain.PatrolPlan;

/**
 * 巡更计划Mapper接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface PatrolPlanMapper extends BaseMapperPlus<PatrolPlanMapper, PatrolPlan, PatrolPlan>
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
     * 精确查询巡更计划列表：前主要用于校验，只能进行精确查询
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
     * 批量新增修改巡更计划
     *
     * @param patrolPlan 巡更计划
     * @return 结果
     */
    public void batchInsertOrUpdateByPatrolPlan(List<PatrolPlan> patrolPlans);

    /**
     * 新增巡更计划
     *
     * @param patrolPlan 巡更计划
     * @return 结果
     */
    public int insertPatrolPlan(PatrolPlan patrolPlan);

    /**
     * 修改巡更计划：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param patrolPlan 巡更计划
     * @return 结果
     */
    public int updateNullValueByPatrolPlan(PatrolPlan patrolPlan);

	/**
     *  更新设置值的字段，未设置值的字段不进行更新
     *
     * @param patrolPlan 巡更计划
     * @return 结果
     */
    public int updateNotNullValueByPatrolPlan(PatrolPlan patrolPlan);

    /**
     * 删除巡更计划
     *
     * @param id 巡更计划主键
     * @return 结果
     */
    public int deletePatrolPlanById(PatrolPlan patrolPlan);

    /**
     * 批量删除巡更计划
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePatrolPlanByIds(PatrolPlan patrolPlan);

    /**
     * 批量删除ByEqPatrolPlan
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


}
