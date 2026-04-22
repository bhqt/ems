package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.CalibrationPlan;
import com.ruoyi.system.domain.vo.CalibrationPlanVo;
import com.ruoyi.system.domain.bo.CalibrationPlanBo;
import com.ruoyi.system.mapper.CalibrationPlanMapper;
import com.ruoyi.system.service.ICalibrationPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 校准计划Service业务层处理
 *
 * @author cpems
 * @date 2026-03-28
 */
@Service
public class CalibrationPlanServiceImpl extends ServiceImpl<CalibrationPlanMapper, CalibrationPlan> implements ICalibrationPlanService {

    @Override
    public CalibrationPlanVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    @Override
    public List<CalibrationPlanVo> queryList(CalibrationPlanBo bo) {
        Map<String, Object> params = BeanUtil.beanToMap(bo, false, true);
        return baseMapper.selectVoByMap(params);
    }

    @Override
    public TableDataInfo<CalibrationPlanVo> queryPageList(CalibrationPlanBo bo, PageQuery pageQuery) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<CalibrationPlan> wrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        Map<String, Object> params = BeanUtil.beanToMap(bo, false, true);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                wrapper.eq(entry.getKey(), entry.getValue());
            }
        }
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<CalibrationPlan> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        com.baomidou.mybatisplus.core.metadata.IPage<CalibrationPlanVo> voPage = baseMapper.selectVoPage(page, wrapper);
        return com.ruoyi.common.core.page.TableDataInfo.build(voPage);
    }

    @Override
    public Boolean insertByBo(CalibrationPlanBo bo) {
        CalibrationPlan add = BeanUtil.toBean(bo, CalibrationPlan.class);
        validEntityBeforeSave(add);
        // 设置默认状态为待开始
        add.setStatus("pending");
        add.setCompletedMeters(0);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(CalibrationPlanBo bo) {
        CalibrationPlan update = BeanUtil.toBean(bo, CalibrationPlan.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(CalibrationPlan entity) {
        // TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<Map<String, Object>> getPlanStatistics() {
        // 查询所有计划
        List<CalibrationPlan> plans = baseMapper.selectList(null);
        
        // 按计划状态分组统计
        Map<String, Long> statusMap = plans.stream()
            .collect(Collectors.groupingBy(
                plan -> plan.getStatus() == null ? "pending" : plan.getStatus(),
                Collectors.counting()
            ));

        // 转换为前端需要的格式
        List<Map<String, Object>> result = new ArrayList<>();
        statusMap.forEach((status, count) -> {
            Map<String, Object> item = new HashMap<>();
            item.put("status", status);
            item.put("count", count);
            result.add(item);
        });

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean startPlan(Long planId) {
        CalibrationPlan plan = baseMapper.selectById(planId);
        if (ObjectUtil.isNull(plan)) {
            throw new RuntimeException("计划不存在");
        }
        if (!"pending".equals(plan.getStatus())) {
            throw new RuntimeException("计划状态错误，只能启动待开始的计划");
        }

        CalibrationPlan updatePlan = new CalibrationPlan();
        updatePlan.setId(planId);
        updatePlan.setStatus("in_progress");
        return baseMapper.updateById(updatePlan) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean completePlan(Long planId) {
        CalibrationPlan plan = baseMapper.selectById(planId);
        if (ObjectUtil.isNull(plan)) {
            throw new RuntimeException("计划不存在");
        }
        if (!"in_progress".equals(plan.getStatus())) {
            throw new RuntimeException("计划状态错误，只能完成进行中的计划");
        }

        CalibrationPlan updatePlan = new CalibrationPlan();
        updatePlan.setId(planId);
        updatePlan.setStatus("completed");
        return baseMapper.updateById(updatePlan) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelPlan(Long planId) {
        CalibrationPlan plan = baseMapper.selectById(planId);
        if (ObjectUtil.isNull(plan)) {
            throw new RuntimeException("计划不存在");
        }
        if ("completed".equals(plan.getStatus())) {
            throw new RuntimeException("已完成的计划不能取消");
        }

        CalibrationPlan updatePlan = new CalibrationPlan();
        updatePlan.setId(planId);
        updatePlan.setStatus("cancelled");
        return baseMapper.updateById(updatePlan) > 0;
    }
}
