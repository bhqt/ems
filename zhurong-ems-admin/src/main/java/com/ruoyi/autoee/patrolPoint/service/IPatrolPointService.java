package com.ruoyi.autoee.patrolPoint.service;

import com.ruoyi.common.core.domain.AjaxResult;
import java.util.List;
import com.ruoyi.autoee.patrolPoint.domain.PatrolPoint;

/**
 * 巡更点位Service接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface IPatrolPointService
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
     * 精确查询巡更点位列表
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
     * 导出巡更点位详细列表
     *
     * @param patrolPoint 巡更点位
     * @return 巡更点位集合
     */
	public List<PatrolPoint> selectExportDetailListPatrolPoint(PatrolPoint patrolPoint);

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
     * 导出前校验
     */
    public void exportDataCheck(PatrolPoint patrolPoint);

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(PatrolPoint patrolPoint, List<PatrolPoint> list);

    /**
     * 新增巡更点位
     *
     * @param patrolPoint 巡更点位
     * @return 结果
     */
    public int insertPatrolPoint(PatrolPoint patrolPoint);

    /**
     * 批量新增修改巡更点位
     *
     * @param patrolPoint 巡更点位
     * @return 结果
     */
    public void batchInsertOrUpdateByPatrolPoint(List<PatrolPoint> patrolPoints);

    /**
     * 修改巡更点位：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param patrolPoint 巡更点位
     * @return 结果
     */
    public int updateNullValueByPatrolPoint(PatrolPoint patrolPoint);

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param patrolPoint 巡更点位
     * @return 结果
     */
    public int updateNotNullValueByPatrolPoint(PatrolPoint patrolPoint);

    /**
     * 删除巡更点位ById
     *
     * @param id 巡更点位主键
     * @return 结果
     */
    public int deletePatrolPointById(PatrolPoint patrolPoint);

    /**
     * 批量删除PatrolPointByIds
     *
     * @param ids 需要删除的巡更点位主键集合
     * @return 结果
     */
	public int deletePatrolPointByIds(PatrolPoint patrolPoint);
    
    /**
     * 批量删除PatrolPointByEqPatrolPoint
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

	   /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importPatrolPointData(List<PatrolPoint> dataList, Boolean isUpdateSupport, String operName, PatrolPoint patrolPoint);


}
