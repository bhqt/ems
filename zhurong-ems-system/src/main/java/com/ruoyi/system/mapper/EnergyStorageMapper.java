package com.ruoyi.system.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.system.domain.EnergyStorage;
import com.ruoyi.system.domain.vo.EnergyStorageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 储能系统Mapper接口
 * 
 * @author cpems
 * @date 2026-03-27
 */
public interface EnergyStorageMapper extends BaseMapperPlus<EnergyStorageMapper, EnergyStorage, EnergyStorageVo> {

    /**
     * 查询储能系统列表
     * 
     * @param energyStorage 储能系统信息
     * @return 储能系统信息集合
     */
    List<EnergyStorageVo> selectEnergyStorageList(EnergyStorage energyStorage);

    /**
     * 根据ID查询储能系统详情
     * 
     * @param id 主键ID
     * @return 储能系统信息
     */
    EnergyStorageVo selectEnergyStorageById(Long id);

    /**
     * 更新系统状态
     * 
     * @param id 主键ID
     * @param status 状态
     * @return 结果
     */
    int updateStorageStatus(@Param("id") Long id, @Param("status") String status);

    /**
     * 根据储能系统ID查询电池组数量
     * 
     * @param storageId 储能系统ID
     * @return 电池组数量
     */
    int countBatteriesByStorageId(Long storageId);
}
