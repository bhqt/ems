package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.SysApiLog;
import com.ruoyi.system.mapper.SysApiLogMapper;
import com.ruoyi.system.service.ISysApiLogService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysApiLogServiceImpl extends ServiceImpl<SysApiLogMapper, SysApiLog> implements ISysApiLogService {

    @Override
    public List<SysApiLog> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public SysApiLog getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean save(SysApiLog log) {
        return baseMapper.insert(log) > 0;
    }

    @Override
    public List<SysApiLog> getByApiId(Long apiId) {
        LambdaQueryWrapper<SysApiLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysApiLog::getApiId, apiId);
        wrapper.orderByDesc(SysApiLog::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<SysApiLog> getByStatus(Integer status) {
        LambdaQueryWrapper<SysApiLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysApiLog::getStatus, status);
        wrapper.orderByDesc(SysApiLog::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> result = new HashMap<>();
        LambdaQueryWrapper<SysApiLog> wrapper = new LambdaQueryWrapper<>();
        Long total = baseMapper.selectCount(wrapper);
        wrapper.eq(SysApiLog::getStatus, 1);
        Long success = baseMapper.selectCount(wrapper);
        wrapper.eq(SysApiLog::getStatus, 2);
        Long fail = baseMapper.selectCount(wrapper);
        result.put("total", total);
        result.put("success", success);
        result.put("fail", fail);
        result.put("successRate", total > 0 ? (success * 100.0 / total) : 0);
        return result;
    }

    @Override
    public boolean clearOldLogs(int days) {
        return true;
    }
}
