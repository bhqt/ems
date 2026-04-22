package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.NetworkNode;
import com.ruoyi.system.mapper.NetworkNodeMapper;
import com.ruoyi.system.service.INetworkNodeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetworkNodeServiceImpl extends ServiceImpl<NetworkNodeMapper, NetworkNode> implements INetworkNodeService {

    @Override
    public List<NetworkNode> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public List<NetworkNode> listByNetworkId(Long networkId) {
        LambdaQueryWrapper<NetworkNode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NetworkNode::getNetworkId, networkId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public NetworkNode getById(Long nodeId) {
        return baseMapper.selectById(nodeId);
    }

    @Override
    public boolean save(NetworkNode node) {
        return baseMapper.insert(node) > 0;
    }

    @Override
    public boolean updateById(NetworkNode node) {
        return baseMapper.updateById(node) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> nodeIds) {
        return baseMapper.deleteBatchIds(nodeIds) > 0;
    }
}
