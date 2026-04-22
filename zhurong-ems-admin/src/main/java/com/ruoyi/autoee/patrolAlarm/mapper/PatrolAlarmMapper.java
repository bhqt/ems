package com.ruoyi.autoee.patrolAlarm.mapper;

import java.util.List;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.autoee.patrolAlarm.domain.PatrolAlarm;

/**
 * 巡更报警Mapper接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface PatrolAlarmMapper extends BaseMapperPlus<PatrolAlarmMapper, PatrolAlarm, PatrolAlarm>
{
    /**
     * 查询巡更报警
     *
     * @param id 巡更报警主键
     * @return 巡更报警
     */
    public PatrolAlarm selectDataByPkPatrolAlarm(Long id);

	/**
     * 查询巡更报警详细信息
     *
     * @param id 巡更报警主键
     * @return 巡更报警
     */
    public PatrolAlarm selectDetailByPkPatrolAlarm(Long id);

    /**
     * 查询巡更报警列表
     *
     * @param patrolAlarm 巡更报警
     * @return 巡更报警集合
     */
    public List<PatrolAlarm> selectDataListByLikePatrolAlarm(PatrolAlarm patrolAlarm);

	/**
     * 精确查询巡更报警列表：前主要用于校验，只能进行精确查询
     *
     * @param patrolAlarm 巡更报警
     * @return 巡更报警集合
     */
    public List<PatrolAlarm> selectDataListByEqPatrolAlarm(PatrolAlarm patrolAlarm);

	    /**
     * 查询巡更报警详细列表
     *
     * @param patrolAlarm 巡更报警
     * @return 巡更报警集合
     */
    public List<PatrolAlarm> selectDetailListByLikePatrolAlarm(PatrolAlarm patrolAlarm);

	/**
     * 精确查询巡更报警详细列表
     *
     * @param patrolAlarm 巡更报警
     * @return 巡更报警集合
     */
    public List<PatrolAlarm> selectDetailListByEqPatrolAlarm(PatrolAlarm patrolAlarm);

	/**
     * 模糊查询记录数
     *
     * @param patrolAlarm 巡更报警
     * @return 巡更报警集合
     */
    public int selectCountByLikePatrolAlarm(PatrolAlarm patrolAlarm);

	/**
     * 精确查询记录数
     *
     * @param patrolAlarm 巡更报警
     * @return 巡更报警集合
     */
    public int selectCountByEqPatrolAlarm(PatrolAlarm patrolAlarm);

    /**
     * 批量新增修改巡更报警
     *
     * @param patrolAlarm 巡更报警
     * @return 结果
     */
    public void batchInsertOrUpdateByPatrolAlarm(List<PatrolAlarm> patrolAlarms);

    /**
     * 新增巡更报警
     *
     * @param patrolAlarm 巡更报警
     * @return 结果
     */
    public int insertPatrolAlarm(PatrolAlarm patrolAlarm);

    /**
     * 修改巡更报警：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param patrolAlarm 巡更报警
     * @return 结果
     */
    public int updateNullValueByPatrolAlarm(PatrolAlarm patrolAlarm);

	/**
     *  更新设置值的字段，未设置值的字段不进行更新
     *
     * @param patrolAlarm 巡更报警
     * @return 结果
     */
    public int updateNotNullValueByPatrolAlarm(PatrolAlarm patrolAlarm);

    /**
     * 删除巡更报警
     *
     * @param id 巡更报警主键
     * @return 结果
     */
    public int deletePatrolAlarmById(PatrolAlarm patrolAlarm);

    /**
     * 批量删除巡更报警
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePatrolAlarmByIds(PatrolAlarm patrolAlarm);

    /**
     * 批量删除ByEqPatrolAlarm
     *
     * @return 结果
     */
    public int deletePatrolAlarmByEqPatrolAlarm(PatrolAlarm patrolAlarm);

    /**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deletePatrolAlarmAllData();


}
