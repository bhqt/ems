package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.system.domain.EnergyQuality;
import com.ruoyi.system.domain.bo.EnergyQualityBo;
import com.ruoyi.system.domain.vo.EnergyQualityVo;
import com.ruoyi.system.mapper.EnergyQualityMapper;
import com.ruoyi.system.service.IEnergyQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 能源质量管理服务实现
 *
 * @author ruoyi
 */
@Service
public class EnergyQualityServiceImpl implements IEnergyQualityService {

    @Autowired
    private EnergyQualityMapper energyQualityMapper;

    @Override
    public List<EnergyQualityVo> queryList(EnergyQualityBo bo) {
        return energyQualityMapper.selectEnergyQualityList(bo);
    }

    @Override
    public TableDataInfo<EnergyQualityVo> queryPageList(EnergyQualityBo bo, Integer pageNum, Integer pageSize) {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(pageNum);
        pageDomain.setPageSize(pageSize);
        PageUtils.startPage(pageDomain);
        List<EnergyQualityVo> list = energyQualityMapper.selectEnergyQualityList(bo);
        return TableDataInfo.build(list);
    }

    @Override
    public EnergyQualityVo queryById(Long qualityId) {
        return energyQualityMapper.selectEnergyQualityByQualityId(qualityId);
    }

    @Override
    public boolean insertByBo(EnergyQualityBo bo) {
        EnergyQuality energyQuality = new EnergyQuality();
        energyQuality.setEnergyMedium(bo.getEnergyMedium());
        energyQuality.setParameterName(bo.getParameterName());
        energyQuality.setStandardValue(bo.getStandardValue());
        energyQuality.setActualValue(bo.getActualValue());
        energyQuality.setDeviation(bo.getDeviation());
        energyQuality.setStatus(bo.getStatus());
        energyQuality.setQualityDate(bo.getQualityDate());
        energyQuality.setRemark(bo.getRemark());
        energyQuality.setUserId(bo.getUserId());
        energyQuality.setDeptId(bo.getDeptId());
        return energyQualityMapper.insertEnergyQuality(energyQuality) > 0;
    }

    @Override
    public boolean updateByBo(EnergyQualityBo bo) {
        EnergyQuality energyQuality = new EnergyQuality();
        energyQuality.setId(bo.getId());
        energyQuality.setEnergyMedium(bo.getEnergyMedium());
        energyQuality.setParameterName(bo.getParameterName());
        energyQuality.setStandardValue(bo.getStandardValue());
        energyQuality.setActualValue(bo.getActualValue());
        energyQuality.setDeviation(bo.getDeviation());
        energyQuality.setStatus(bo.getStatus());
        energyQuality.setQualityDate(bo.getQualityDate());
        energyQuality.setRemark(bo.getRemark());
        energyQuality.setUserId(bo.getUserId());
        energyQuality.setDeptId(bo.getDeptId());
        return energyQualityMapper.updateEnergyQuality(energyQuality) > 0;
    }

    @Override
    public boolean deleteWithValidByIds(List<Long> ids, boolean isValid) {
        Long[] idArray = ids.toArray(new Long[0]);
        return energyQualityMapper.deleteEnergyQualityByQualityIds(idArray) > 0;
    }
}
