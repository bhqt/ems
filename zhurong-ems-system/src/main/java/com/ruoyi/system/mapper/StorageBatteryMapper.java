package com.ruoyi.system.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.system.domain.StorageBattery;
import com.ruoyi.system.domain.vo.StorageBatteryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 储能电池组Mapper接口
 * 
 * @author cpems
 * @date 2026-03-27
 */
public interface StorageBatteryMapper extends BaseMapperPlus<StorageBatteryMapper, StorageBattery, StorageBatteryVo> {

    /**
     * 查询储能电池组列表
     * 
     * @param storageBattery 储能电池组信息
     * @return 储能电池组信息集合
     */
    List<StorageBatteryVo> selectStorageBatteryList(StorageBattery storageBattery);

    /**
     * 根据ID查询储能电池组详情
     * 
     * @param id 主键ID
     * @return 储能电池组信息
     */
    StorageBatteryVo selectStorageBatteryById(Long id);

    /**
     * 更新电池组状态
     * 
     * @param id 主键ID
     * @param status 状态
     * @return 结果
     */
    int updateBatteryStatus(@Param("id") Long id, @Param("status") String status);

    /**
     * 根据储能系统ID查询电池组列表
     * 
     * @param storageId 储能系统ID
     * @return 电池组列表
     */
    List<StorageBatteryVo> selectBatteriesByStorageId(Long storageId);

    /**
     * 统计储能系统下的电池组状态
     * 
     * @param storageId 储能系统ID
     * @param status 状态
     * @return 数量
     */
    int countBatteriesByStatus(@Param("storageId") Long storageId, @Param("status") String status);
}
