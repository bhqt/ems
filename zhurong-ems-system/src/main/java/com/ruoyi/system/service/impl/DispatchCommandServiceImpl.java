package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.DispatchCommand;
import com.ruoyi.system.mapper.DispatchCommandMapper;
import com.ruoyi.system.service.IDispatchCommandService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DispatchCommandServiceImpl extends ServiceImpl<DispatchCommandMapper, DispatchCommand> implements IDispatchCommandService {

    @Override
    public List<DispatchCommand> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public DispatchCommand getById(Long commandId) {
        return baseMapper.selectById(commandId);
    }

    @Override
    public boolean save(DispatchCommand command) {
        return baseMapper.insert(command) > 0;
    }

    @Override
    public boolean updateById(DispatchCommand command) {
        return baseMapper.updateById(command) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> commandIds) {
        return baseMapper.deleteBatchIds(commandIds) > 0;
    }

    @Override
    public boolean executeCommand(Long commandId) {
        DispatchCommand command = baseMapper.selectById(commandId);
        if (command != null && command.getStatus() == 1) {
            command.setStatus(2);
            command.setActualTime(new Date());
            return baseMapper.updateById(command) > 0;
        }
        return false;
    }
}
