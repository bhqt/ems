package com.ruoyi.system.service;

import com.ruoyi.system.domain.CalibrationPlan;
import com.ruoyi.system.domain.vo.CalibrationPlanVo;
import com.ruoyi.system.domain.bo.CalibrationPlanBo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 校准计划Service接口
 *
 * @author cpems
 * @date 2026-03-28
 */
public interface ICalibrationPlanService extends IService<CalibrationPlan> {

    /**
     * 查询校准计划
     *
     * @param id 主键
     * @return 校准计划
     */
    CalibrationPlanVo queryById(Long id);

    /**
     * 查询校准计划列表
     *
     * @param bo 校准计划
     * @return 校准计划集合
     */
    List<CalibrationPlanVo> queryList(CalibrationPlanBo bo);

    /**
     * 查询校准计划列表（分页）
     *
     * @param bo 校准计划
     * @return 校准计划分页集合
     */
    TableDataInfo<CalibrationPlanVo> queryPageList(CalibrationPlanBo bo, PageQuery pageQuery);

    /**
     * 新增校准计划
     *
     * @param bo 校准计划
     * @return 结果
     */
    Boolean insertByBo(CalibrationPlanBo bo);

    /**
     * 修改校准计划
     *
     * @param bo 校准计划
     * @return 结果
     */
    Boolean updateByBo(CalibrationPlanBo bo);

    /**
     * 校验并批量删除校准计划信息
     *
     * @param ids 主键集合
     * @param isValid 是否校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 查询计划状态统计
     *
     * @return 计划状态统计列表
     */
    List<Map<String, Object>> getPlanStatistics();

    /**
     * 启动校准计划
     *
     * @param planId 计划ID
     * @return 结果
     */
    Boolean startPlan(Long planId);

    /**
     * 完成校准计划
     *
     * @param planId 计划ID
     * @return 结果
     */
    Boolean completePlan(Long planId);

    /**
     * 取消校准计划
     *
     * @param planId 计划ID
     * @return 结果
     */
    Boolean cancelPlan(Long planId);
}
