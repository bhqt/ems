package com.ruoyi.system.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.bo.EnergyQualityBo;
import com.ruoyi.system.domain.vo.EnergyQualityVo;

import java.util.List;

/**
 * 能源质量管理服务接口
 *
 * @author ruoyi
 */
public interface IEnergyQualityService {

    /**
     * 查询能源质量列表
     */
    List<EnergyQualityVo> queryList(EnergyQualityBo bo);

    /**
     * 分页查询能源质量列表
     */
    TableDataInfo<EnergyQualityVo> queryPageList(EnergyQualityBo bo, Integer pageNum, Integer pageSize);

    /**
     * 根据id查询能源质量
     */
    EnergyQualityVo queryById(Long qualityId);

    /**
     * 新增能源质量
     */
    boolean insertByBo(EnergyQualityBo bo);

    /**
     * 修改能源质量
     */
    boolean updateByBo(EnergyQualityBo bo);

    /**
     * 批量删除能源质量
     */
    boolean deleteWithValidByIds(List<Long> ids, boolean isValid);
}
