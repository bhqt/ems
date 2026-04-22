package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysApiInterface;

import java.util.List;

public interface ISysApiInterfaceService extends IService<SysApiInterface> {

    List<SysApiInterface> list();

    SysApiInterface getById(Long id);

    boolean save(SysApiInterface api);

    boolean updateById(SysApiInterface api);

    boolean removeByIds(List<Long> ids);

    List<SysApiInterface> getByCategory(String category);

    SysApiInterface getByApiCode(String apiCode);
}
