package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.DistributionNetwork;
import com.ruoyi.system.mapper.DistributionNetworkMapper;
import com.ruoyi.system.service.IDistributionNetworkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributionNetworkServiceImpl extends ServiceImpl<DistributionNetworkMapper, DistributionNetwork> implements IDistributionNetworkService {

    @Override
    public List<DistributionNetwork> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public DistributionNetwork getById(Long networkId) {
        return baseMapper.selectById(networkId);
    }

    @Override
    public boolean save(DistributionNetwork network) {
        return baseMapper.insert(network) > 0;
    }

    @Override
    public boolean updateById(DistributionNetwork network) {
        return baseMapper.updateById(network) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> networkIds) {
        return baseMapper.deleteBatchIds(networkIds) > 0;
    }
}
