package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.DispatchCommand;

import java.util.List;

public interface IDispatchCommandService extends IService<DispatchCommand> {

    List<DispatchCommand> list();

    DispatchCommand getById(Long commandId);

    boolean save(DispatchCommand command);

    boolean updateById(DispatchCommand command);

    boolean removeByIds(List<Long> commandIds);

    boolean executeCommand(Long commandId);
}
