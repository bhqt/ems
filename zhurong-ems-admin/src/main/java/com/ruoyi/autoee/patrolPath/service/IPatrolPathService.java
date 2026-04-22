package com.ruoyi.autoee.patrolPath.service;

import com.ruoyi.common.core.domain.AjaxResult;
import java.util.List;
import com.ruoyi.autoee.patrolPath.domain.PatrolPath;

/**
 * 巡更路线Service接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface IPatrolPathService
{
    /**
     * 查询巡更路线
     *
     * @param id 巡更路线主键
     * @return 巡更路线
     */
    public PatrolPath selectDataByPkPatrolPath(Long id);

    /**
     * 查询巡更路线详细信息
     *
     * @param id 巡更路线主键
     * @return 巡更路线
     */
    public PatrolPath selectDetailByPkPatrolPath(Long id);

    /**
     * 查询巡更路线列表
     *
     * @param patrolPath 巡更路线
     * @return 巡更路线集合
     */
    public List<PatrolPath> selectDataListByLikePatrolPath(PatrolPath patrolPath);

    /**
     * 精确查询巡更路线列表
     *
     * @param patrolPath 巡更路线
     * @return 巡更路线集合
     */
    public List<PatrolPath> selectDataListByEqPatrolPath(PatrolPath patrolPath);

	/**
     * 查询巡更路线详细列表
     *
     * @param patrolPath 巡更路线
     * @return 巡更路线集合
     */
    public List<PatrolPath> selectDetailListByLikePatrolPath(PatrolPath patrolPath);

    /**
     * 精确查询巡更路线详细列表
     *
     * @param patrolPath 巡更路线
     * @return 巡更路线集合
     */
    public List<PatrolPath> selectDetailListByEqPatrolPath(PatrolPath patrolPath);

	/**
     * 导出巡更路线详细列表
     *
     * @param patrolPath 巡更路线
     * @return 巡更路线集合
     */
	public List<PatrolPath> selectExportDetailListPatrolPath(PatrolPath patrolPath);

	/**
     * 模糊查询记录数
     *
     * @param patrolPath 巡更路线
     * @return 巡更路线集合
     */
    public int selectCountByLikePatrolPath(PatrolPath patrolPath);

	/**
     * 精确查询记录数
     *
     * @param patrolPath 巡更路线
     * @return 巡更路线集合
     */
    public int selectCountByEqPatrolPath(PatrolPath patrolPath);

	/**
     * 导出前校验
     */
    public void exportDataCheck(PatrolPath patrolPath);

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(PatrolPath patrolPath, List<PatrolPath> list);

    /**
     * 新增巡更路线
     *
     * @param patrolPath 巡更路线
     * @return 结果
     */
    public int insertPatrolPath(PatrolPath patrolPath);

    /**
     * 批量新增修改巡更路线
     *
     * @param patrolPath 巡更路线
     * @return 结果
     */
    public void batchInsertOrUpdateByPatrolPath(List<PatrolPath> patrolPaths);

    /**
     * 修改巡更路线：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param patrolPath 巡更路线
     * @return 结果
     */
    public int updateNullValueByPatrolPath(PatrolPath patrolPath);

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param patrolPath 巡更路线
     * @return 结果
     */
    public int updateNotNullValueByPatrolPath(PatrolPath patrolPath);

    /**
     * 删除巡更路线ById
     *
     * @param id 巡更路线主键
     * @return 结果
     */
    public int deletePatrolPathById(PatrolPath patrolPath);

    /**
     * 批量删除PatrolPathByIds
     *
     * @param ids 需要删除的巡更路线主键集合
     * @return 结果
     */
	public int deletePatrolPathByIds(PatrolPath patrolPath);
    
    /**
     * 批量删除PatrolPathByEqPatrolPath
     *
     * @return 结果
     */
    public int deletePatrolPathByEqPatrolPath(PatrolPath patrolPath);

	/**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deletePatrolPathAllData();

	   /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importPatrolPathData(List<PatrolPath> dataList, Boolean isUpdateSupport, String operName, PatrolPath patrolPath);


}
