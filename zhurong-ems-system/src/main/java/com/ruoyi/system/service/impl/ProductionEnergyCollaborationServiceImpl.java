package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.ProductionEnergyCollaboration;
import com.ruoyi.system.mapper.ProductionEnergyCollaborationMapper;
import com.ruoyi.system.service.IProductionEnergyCollaborationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionEnergyCollaborationServiceImpl extends ServiceImpl<ProductionEnergyCollaborationMapper, ProductionEnergyCollaboration> implements IProductionEnergyCollaborationService {

    @Override
    public List<ProductionEnergyCollaboration> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public ProductionEnergyCollaboration getById(Long collabId) {
        return baseMapper.selectById(collabId);
    }

    @Override
    public boolean save(ProductionEnergyCollaboration collaboration) {
        return baseMapper.insert(collaboration) > 0;
    }

    @Override
    public boolean updateById(ProductionEnergyCollaboration collaboration) {
        return baseMapper.updateById(collaboration) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> collabIds) {
        return baseMapper.deleteBatchIds(collabIds) > 0;
    }

    @Override
    public boolean executeCollaboration(Long collabId) {
        ProductionEnergyCollaboration collaboration = baseMapper.selectById(collabId);
        if (collaboration != null && collaboration.getStatus() == 1) {
            collaboration.setStatus(2);
            return baseMapper.updateById(collaboration) > 0;
        }
        return false;
    }
}
