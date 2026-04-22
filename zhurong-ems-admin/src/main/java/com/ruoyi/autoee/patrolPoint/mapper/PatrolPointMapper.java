package com.ruoyi.autoee.patrolPoint.mapper;

import java.util.List;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.autoee.patrolPoint.domain.PatrolPoint;

/**
 * 巡更点位Mapper接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface PatrolPointMapper extends BaseMapperPlus<PatrolPointMapper, PatrolPoint, PatrolPoint>
{
    /**
     * 查询巡更点位
     *
     * @param id 巡更点位主键
     * @return 巡更点位
     */
    public PatrolPoint selectDataByPkPatrolPoint(Long id);

	/**
     * 查询巡更点位详细信息
     *
     * @param id 巡更点位主键
     * @return 巡更点位
     */
    public PatrolPoint selectDetailByPkPatrolPoint(Long id);

    /**
     * 查询巡更点位列表
     *
     * @param patrolPoint 巡更点位
     * @return 巡更点位集合
     */
    public List<PatrolPoint> selectDataListByLikePatrolPoint(PatrolPoint patrolPoint);

	/**
     * 精确查询巡更点位列表：前主要用于校验，只能进行精确查询
     *
     * @param patrolPoint 巡更点位
     * @return 巡更点位集合
     */
    public List<PatrolPoint> selectDataListByEqPatrolPoint(PatrolPoint patrolPoint);

	    /**
     * 查询巡更点位详细列表
     *
     * @param patrolPoint 巡更点位
     * @return 巡更点位集合
     */
    public List<PatrolPoint> selectDetailListByLikePatrolPoint(PatrolPoint patrolPoint);

	/**
     * 精确查询巡更点位详细列表
     *
     * @param patrolPoint 巡更点位
     * @return 巡更点位集合
     */
    public List<PatrolPoint> selectDetailListByEqPatrolPoint(PatrolPoint patrolPoint);

	/**
     * 模糊查询记录数
     *
     * @param patrolPoint 巡更点位
     * @return 巡更点位集合
     */
    public int selectCountByLikePatrolPoint(PatrolPoint patrolPoint);

	/**
     * 精确查询记录数
     *
     * @param patrolPoint 巡更点位
     * @return 巡更点位集合
     */
    public int selectCountByEqPatrolPoint(PatrolPoint patrolPoint);

    /**
     * 批量新增修改巡更点位
     *
     * @param patrolPoint 巡更点位
     * @return 结果
     */
    public void batchInsertOrUpdateByPatrolPoint(List<PatrolPoint> patrolPoints);

    /**
     * 新增巡更点位
     *
     * @param patrolPoint 巡更点位
     * @return 结果
     */
    public int insertPatrolPoint(PatrolPoint patrolPoint);

    /**
     * 修改巡更点位：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param patrolPoint 巡更点位
     * @return 结果
     */
    public int updateNullValueByPatrolPoint(PatrolPoint patrolPoint);

	/**
     *  更新设置值的字段，未设置值的字段不进行更新
     *
     * @param patrolPoint 巡更点位
     * @return 结果
     */
    public int updateNotNullValueByPatrolPoint(PatrolPoint patrolPoint);

    /**
     * 删除巡更点位
     *
     * @param id 巡更点位主键
     * @return 结果
     */
    public int deletePatrolPointById(PatrolPoint patrolPoint);

    /**
     * 批量删除巡更点位
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePatrolPointByIds(PatrolPoint patrolPoint);

    /**
     * 批量删除ByEqPatrolPoint
     *
     * @return 结果
     */
    public int deletePatrolPointByEqPatrolPoint(PatrolPoint patrolPoint);

    /**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deletePatrolPointAllData();


}
