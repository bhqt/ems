package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.DistributionNetwork;

import java.util.List;

public interface IDistributionNetworkService extends IService<DistributionNetwork> {

    List<DistributionNetwork> list();

    DistributionNetwork getById(Long networkId);

    boolean save(DistributionNetwork network);

    boolean updateById(DistributionNetwork network);

    boolean removeByIds(List<Long> networkIds);
}
