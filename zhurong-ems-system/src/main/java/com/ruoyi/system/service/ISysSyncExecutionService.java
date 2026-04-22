package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysSyncExecution;

import java.util.List;
import java.util.Map;

public interface ISysSyncExecutionService extends IService<SysSyncExecution> {

    List<SysSyncExecution> list();

    SysSyncExecution getById(Long id);

    boolean save(SysSyncExecution execution);

    List<SysSyncExecution> getByTaskId(Long taskId);

    List<SysSyncExecution> getByStatus(Integer status);

    Map<String, Object> getStatistics(Long taskId);

    List<SysSyncExecution> getRecentExecutions(int limit);
}
