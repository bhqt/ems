package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysApiLog;

import java.util.List;
import java.util.Map;

public interface ISysApiLogService extends IService<SysApiLog> {

    List<SysApiLog> list();

    SysApiLog getById(Long id);

    boolean save(SysApiLog log);

    List<SysApiLog> getByApiId(Long apiId);

    List<SysApiLog> getByStatus(Integer status);

    Map<String, Object> getStatistics();

    boolean clearOldLogs(int days);
}
