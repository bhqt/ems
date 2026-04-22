package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysIntegrationConfig;

import java.util.List;

public interface ISysIntegrationConfigService extends IService<SysIntegrationConfig> {

    List<SysIntegrationConfig> list();

    SysIntegrationConfig getById(Long id);

    boolean save(SysIntegrationConfig config);

    boolean updateById(SysIntegrationConfig config);

    boolean removeByIds(List<Long> ids);

    SysIntegrationConfig getBySystemCode(String systemCode);

    boolean enable(Long id);

    boolean disable(Long id);
}
