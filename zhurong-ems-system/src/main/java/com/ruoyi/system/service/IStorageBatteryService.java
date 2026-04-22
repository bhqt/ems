package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.bo.StorageBatteryBo;
import com.ruoyi.system.domain.vo.StorageBatteryVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 储能电池组Service接口
 * 
 * @author cpems
 * @date 2026-03-27
 */
public interface IStorageBatteryService {

    /**
     * 查询储能电池组
     * 
     * @param id 主键
     * @return 储能电池组
     */
    StorageBatteryVo queryById(Long id);

    /**
     * 查询储能电池组列表
     * 
     * @param bo 业务对象
     * @param pageQuery 分页参数
     * @return 储能电池组集合
     */
    TableDataInfo<StorageBatteryVo> queryPageList(StorageBatteryBo bo, PageQuery pageQuery);

    /**
     * 查询储能电池组列表（不分页）
     * 
     * @param bo 业务对象
     * @return 储能电池组集合
     */
    List<StorageBatteryVo> queryList(StorageBatteryBo bo);

    /**
     * 新增储能电池组
     * 
     * @param bo 业务对象
     * @return 结果
     */
    Boolean insertByBo(StorageBatteryBo bo);

    /**
     * 修改储能电池组
     * 
     * @param bo 业务对象
     * @return 结果
     */
    Boolean updateByBo(StorageBatteryBo bo);

    /**
     * 批量删除储能电池组
     * 
     * @param ids 主键集合
     * @param isValid 是否校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 更新电池组状态
     * 
     * @param id 主键
     * @param status 状态
     * @return 结果
     */
    Boolean updateStatus(Long id, String status);

    /**
     * 根据储能系统ID查询电池组列表
     * 
     * @param storageId 储能系统ID
     * @return 电池组列表
     */
    List<StorageBatteryVo> queryBatteriesByStorageId(Long storageId);

    /**
     * 获取电池组实时数据
     * 
     * @param batteryId 电池组ID
     * @return 实时数据
     */
    Map<String, Object> getRealTimeData(Long batteryId);

    /**
     * 获取电池组历史数据
     * 
     * @param batteryId 电池组ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param dataType 数据类型（voltage/current/temperature/soc）
     * @return 历史数据
     */
    List<Map<String, Object>> getHistoryData(Long batteryId, String startTime, String endTime, String dataType);

    /**
     * 获取电池组健康状态统计
     * 
     * @param storageId 储能系统ID
     * @return 健康状态统计
     */
    Map<String, Object> getHealthStatistics(Long storageId);
}
