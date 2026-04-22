package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysSyncTask;

import java.util.List;

public interface ISysSyncTaskService extends IService<SysSyncTask> {

    List<SysSyncTask> list();

    SysSyncTask getById(Long id);

    boolean save(SysSyncTask task);

    boolean updateById(SysSyncTask task);

    boolean removeByIds(List<Long> ids);

    SysSyncTask getByTaskCode(String taskCode);

    List<SysSyncTask> getEnabledTasks();

    boolean enable(Long id);

    boolean disable(Long id);
}
