package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.event.OperLogEvent;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysOperLog;

import java.util.List;

/**
 * 操作日志 服务层
 *
 * @author cpems
 */
public interface ISysOperLogService {

    /**
     * 操作日志记录
     *
     * @param operLogEvent 操作日志事件
     */
    void recordOper(OperLogEvent operLogEvent);

    TableDataInfo<SysOperLog> selectPageOperLogList(SysOperLog operLog, PageQuery pageQuery);

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    void insertOperlog(SysOperLog operLog);

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    List<SysOperLog> selectOperLogList(SysOperLog operLog);

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    int deleteOperLogByIds(Long[] operIds);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    SysOperLog selectOperLogById(Long operId);

    /**
     * 清空操作日志
     */
    void cleanOperLog();
}
