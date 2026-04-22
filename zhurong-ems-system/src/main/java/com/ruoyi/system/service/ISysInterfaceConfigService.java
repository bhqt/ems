package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysInterfaceConfig;

import java.util.List;

public interface ISysInterfaceConfigService extends IService<SysInterfaceConfig> {

    List<SysInterfaceConfig> list();

    SysInterfaceConfig getById(Long id);

    boolean save(SysInterfaceConfig config);

    boolean updateById(SysInterfaceConfig config);

    boolean removeByIds(List<Long> ids);

    List<SysInterfaceConfig> getByConfigId(Long configId);

    boolean testConnection(Long id);
}
