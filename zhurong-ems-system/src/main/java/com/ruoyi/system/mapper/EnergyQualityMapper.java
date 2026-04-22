package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.EnergyQuality;
import com.ruoyi.system.domain.bo.EnergyQualityBo;
import com.ruoyi.system.domain.vo.EnergyQualityVo;

import java.util.List;

/**
 * 能源质量Mapper接口
 *
 * @author ruoyi
 */
public interface EnergyQualityMapper {

    /**
     * 查询能源质量
     */
    EnergyQualityVo selectEnergyQualityByQualityId(Long qualityId);

    /**
     * 查询能源质量列表
     */
    List<EnergyQualityVo> selectEnergyQualityList(EnergyQualityBo bo);

    /**
     * 新增能源质量
     */
    int insertEnergyQuality(EnergyQuality energyQuality);

    /**
     * 修改能源质量
     */
    int updateEnergyQuality(EnergyQuality energyQuality);

    /**
     * 删除能源质量
     */
    int deleteEnergyQualityByQualityId(Long qualityId);

    /**
     * 批量删除能源质量
     */
    int deleteEnergyQualityByQualityIds(Long[] qualityIds);
}
