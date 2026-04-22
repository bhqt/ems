package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.ProductionEnergyCollaboration;

import java.util.List;

public interface IProductionEnergyCollaborationService extends IService<ProductionEnergyCollaboration> {

    List<ProductionEnergyCollaboration> list();

    ProductionEnergyCollaboration getById(Long collabId);

    boolean save(ProductionEnergyCollaboration collaboration);

    boolean updateById(ProductionEnergyCollaboration collaboration);

    boolean removeByIds(List<Long> collabIds);

    boolean executeCollaboration(Long collabId);
}
