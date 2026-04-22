package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.SysSyncTask;
import com.ruoyi.system.mapper.SysSyncTaskMapper;
import com.ruoyi.system.service.ISysSyncTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysSyncTaskServiceImpl extends ServiceImpl<SysSyncTaskMapper, SysSyncTask> implements ISysSyncTaskService {

    @Override
    public List<SysSyncTask> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public SysSyncTask getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean save(SysSyncTask task) {
        return baseMapper.insert(task) > 0;
    }

    @Override
    public boolean updateById(SysSyncTask task) {
        return baseMapper.updateById(task) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public SysSyncTask getByTaskCode(String taskCode) {
        LambdaQueryWrapper<SysSyncTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysSyncTask::getTaskCode, taskCode);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<SysSyncTask> getEnabledTasks() {
        LambdaQueryWrapper<SysSyncTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysSyncTask::getEnabled, 1);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean enable(Long id) {
        SysSyncTask task = new SysSyncTask();
        task.setId(id);
        task.setEnabled(1);
        return baseMapper.updateById(task) > 0;
    }

    @Override
    public boolean disable(Long id) {
        SysSyncTask task = new SysSyncTask();
        task.setId(id);
        task.setEnabled(2);
        return baseMapper.updateById(task) > 0;
    }
}
