package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.SysSyncExecution;
import com.ruoyi.system.mapper.SysSyncExecutionMapper;
import com.ruoyi.system.service.ISysSyncExecutionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysSyncExecutionServiceImpl extends ServiceImpl<SysSyncExecutionMapper, SysSyncExecution> implements ISysSyncExecutionService {

    @Override
    public List<SysSyncExecution> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public SysSyncExecution getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean save(SysSyncExecution execution) {
        return baseMapper.insert(execution) > 0;
    }

    @Override
    public List<SysSyncExecution> getByTaskId(Long taskId) {
        LambdaQueryWrapper<SysSyncExecution> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysSyncExecution::getTaskId, taskId);
        wrapper.orderByDesc(SysSyncExecution::getStartTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<SysSyncExecution> getByStatus(Integer status) {
        LambdaQueryWrapper<SysSyncExecution> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysSyncExecution::getStatus, status);
        wrapper.orderByDesc(SysSyncExecution::getStartTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public Map<String, Object> getStatistics(Long taskId) {
        Map<String, Object> result = new HashMap<>();
        LambdaQueryWrapper<SysSyncExecution> wrapper = new LambdaQueryWrapper<>();
        if (taskId != null) {
            wrapper.eq(SysSyncExecution::getTaskId, taskId);
        }
        Long total = baseMapper.selectCount(wrapper);
        wrapper.eq(SysSyncExecution::getStatus, 2);
        Long success = baseMapper.selectCount(wrapper);
        wrapper.eq(SysSyncExecution::getStatus, 3);
        Long fail = baseMapper.selectCount(wrapper);
        wrapper.eq(SysSyncExecution::getStatus, 4);
        Long partial = baseMapper.selectCount(wrapper);
        result.put("total", total);
        result.put("success", success);
        result.put("fail", fail);
        result.put("partial", partial);
        result.put("successRate", total > 0 ? (success * 100.0 / total) : 0);
        return result;
    }

    @Override
    public List<SysSyncExecution> getRecentExecutions(int limit) {
        LambdaQueryWrapper<SysSyncExecution> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysSyncExecution::getStartTime);
        wrapper.last("LIMIT " + limit);
        return baseMapper.selectList(wrapper);
    }
}
