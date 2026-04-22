package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.NetworkNode;

import java.util.List;

public interface INetworkNodeService extends IService<NetworkNode> {

    List<NetworkNode> list();

    List<NetworkNode> listByNetworkId(Long networkId);

    NetworkNode getById(Long nodeId);

    boolean save(NetworkNode node);

    boolean updateById(NetworkNode node);

    boolean removeByIds(List<Long> nodeIds);
}
