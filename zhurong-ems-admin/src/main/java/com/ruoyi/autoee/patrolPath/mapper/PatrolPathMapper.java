package com.ruoyi.autoee.patrolPath.mapper;

import java.util.List;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.autoee.patrolPath.domain.PatrolPath;

/**
 * 巡更路线Mapper接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface PatrolPathMapper extends BaseMapperPlus<PatrolPathMapper, PatrolPath, PatrolPath>
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
     * 精确查询巡更路线列表：前主要用于校验，只能进行精确查询
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
     * 批量新增修改巡更路线
     *
     * @param patrolPath 巡更路线
     * @return 结果
     */
    public void batchInsertOrUpdateByPatrolPath(List<PatrolPath> patrolPaths);

    /**
     * 新增巡更路线
     *
     * @param patrolPath 巡更路线
     * @return 结果
     */
    public int insertPatrolPath(PatrolPath patrolPath);

    /**
     * 修改巡更路线：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param patrolPath 巡更路线
     * @return 结果
     */
    public int updateNullValueByPatrolPath(PatrolPath patrolPath);

	/**
     *  更新设置值的字段，未设置值的字段不进行更新
     *
     * @param patrolPath 巡更路线
     * @return 结果
     */
    public int updateNotNullValueByPatrolPath(PatrolPath patrolPath);

    /**
     * 删除巡更路线
     *
     * @param id 巡更路线主键
     * @return 结果
     */
    public int deletePatrolPathById(PatrolPath patrolPath);

    /**
     * 批量删除巡更路线
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePatrolPathByIds(PatrolPath patrolPath);

    /**
     * 批量删除ByEqPatrolPath
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


}
