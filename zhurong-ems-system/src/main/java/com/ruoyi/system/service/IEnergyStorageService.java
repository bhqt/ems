package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.bo.EnergyStorageBo;
import com.ruoyi.system.domain.vo.EnergyStorageVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 储能系统Service接口
 * 
 * @author cpems
 * @date 2026-03-27
 */
public interface IEnergyStorageService {

    /**
     * 查询储能系统
     * 
     * @param id 主键
     * @return 储能系统
     */
    EnergyStorageVo queryById(Long id);

    /**
     * 查询储能系统列表
     * 
     * @param bo 业务对象
     * @param pageQuery 分页参数
     * @return 储能系统集合
     */
    TableDataInfo<EnergyStorageVo> queryPageList(EnergyStorageBo bo, PageQuery pageQuery);

    /**
     * 查询储能系统列表（不分页）
     * 
     * @param bo 业务对象
     * @return 储能系统集合
     */
    List<EnergyStorageVo> queryList(EnergyStorageBo bo);

    /**
     * 新增储能系统
     * 
     * @param bo 业务对象
     * @return 结果
     */
    Boolean insertByBo(EnergyStorageBo bo);

    /**
     * 修改储能系统
     * 
     * @param bo 业务对象
     * @return 结果
     */
    Boolean updateByBo(EnergyStorageBo bo);

    /**
     * 批量删除储能系统
     * 
     * @param ids 主键集合
     * @param isValid 是否校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 更新储能系统状态
     * 
     * @param id 主键
     * @param status 状态
     * @return 结果
     */
    Boolean updateStatus(Long id, String status);

    /**
     * 获取储能系统统计数据
     * 
     * @return 统计数据
     */
    Map<String, Object> getStatistics();

    /**
     * 获取储能系统实时数据
     * 
     * @param storageId 储能系统ID
     * @return 实时数据
     */
    Map<String, Object> getRealTimeData(Long storageId);

    /**
     * 获取充放电统计
     * 
     * @param storageId 储能系统ID
     * @param dateType 日期类型（day/month/year）
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 充放电统计
     */
    List<Map<String, Object>> getChargeDischargeStatistics(Long storageId, String dateType, String startTime, String endTime);

    /**
     * 获取电池组状态统计
     * 
     * @param storageId 储能系统ID
     * @return 电池组状态统计
     */
    Map<String, Object> getBatteryStatusStatistics(Long storageId);
}
