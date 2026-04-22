package com.ruoyi.autoee.patrolRecord.service;

import com.ruoyi.common.core.domain.AjaxResult;
import java.util.List;
import com.ruoyi.autoee.patrolRecord.domain.PatrolRecord;

/**
 * 巡更记录Service接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface IPatrolRecordService
{
    /**
     * 查询巡更记录
     *
     * @param id 巡更记录主键
     * @return 巡更记录
     */
    public PatrolRecord selectDataByPkPatrolRecord(Long id);

    /**
     * 查询巡更记录详细信息
     *
     * @param id 巡更记录主键
     * @return 巡更记录
     */
    public PatrolRecord selectDetailByPkPatrolRecord(Long id);

    /**
     * 查询巡更记录列表
     *
     * @param patrolRecord 巡更记录
     * @return 巡更记录集合
     */
    public List<PatrolRecord> selectDataListByLikePatrolRecord(PatrolRecord patrolRecord);

    /**
     * 精确查询巡更记录列表
     *
     * @param patrolRecord 巡更记录
     * @return 巡更记录集合
     */
    public List<PatrolRecord> selectDataListByEqPatrolRecord(PatrolRecord patrolRecord);

	/**
     * 查询巡更记录详细列表
     *
     * @param patrolRecord 巡更记录
     * @return 巡更记录集合
     */
    public List<PatrolRecord> selectDetailListByLikePatrolRecord(PatrolRecord patrolRecord);

    /**
     * 精确查询巡更记录详细列表
     *
     * @param patrolRecord 巡更记录
     * @return 巡更记录集合
     */
    public List<PatrolRecord> selectDetailListByEqPatrolRecord(PatrolRecord patrolRecord);

	/**
     * 导出巡更记录详细列表
     *
     * @param patrolRecord 巡更记录
     * @return 巡更记录集合
     */
	public List<PatrolRecord> selectExportDetailListPatrolRecord(PatrolRecord patrolRecord);

	/**
     * 模糊查询记录数
     *
     * @param patrolRecord 巡更记录
     * @return 巡更记录集合
     */
    public int selectCountByLikePatrolRecord(PatrolRecord patrolRecord);

	/**
     * 精确查询记录数
     *
     * @param patrolRecord 巡更记录
     * @return 巡更记录集合
     */
    public int selectCountByEqPatrolRecord(PatrolRecord patrolRecord);

	/**
     * 导出前校验
     */
    public void exportDataCheck(PatrolRecord patrolRecord);

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(PatrolRecord patrolRecord, List<PatrolRecord> list);

    /**
     * 新增巡更记录
     *
     * @param patrolRecord 巡更记录
     * @return 结果
     */
    public int insertPatrolRecord(PatrolRecord patrolRecord);

    /**
     * 批量新增修改巡更记录
     *
     * @param patrolRecord 巡更记录
     * @return 结果
     */
    public void batchInsertOrUpdateByPatrolRecord(List<PatrolRecord> patrolRecords);

    /**
     * 修改巡更记录：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param patrolRecord 巡更记录
     * @return 结果
     */
    public int updateNullValueByPatrolRecord(PatrolRecord patrolRecord);

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param patrolRecord 巡更记录
     * @return 结果
     */
    public int updateNotNullValueByPatrolRecord(PatrolRecord patrolRecord);

    /**
     * 删除巡更记录ById
     *
     * @param id 巡更记录主键
     * @return 结果
     */
    public int deletePatrolRecordById(PatrolRecord patrolRecord);

    /**
     * 批量删除PatrolRecordByIds
     *
     * @param ids 需要删除的巡更记录主键集合
     * @return 结果
     */
	public int deletePatrolRecordByIds(PatrolRecord patrolRecord);
    
    /**
     * 批量删除PatrolRecordByEqPatrolRecord
     *
     * @return 结果
     */
    public int deletePatrolRecordByEqPatrolRecord(PatrolRecord patrolRecord);

	/**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deletePatrolRecordAllData();

	   /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importPatrolRecordData(List<PatrolRecord> dataList, Boolean isUpdateSupport, String operName, PatrolRecord patrolRecord);


}
