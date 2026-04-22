package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.TwinDevice;
import com.ruoyi.system.mapper.TwinDeviceMapper;
import com.ruoyi.system.service.ITwinDeviceService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TwinDeviceServiceImpl extends ServiceImpl<TwinDeviceMapper, TwinDevice> implements ITwinDeviceService {

    @Override
    public List<TwinDevice> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public TwinDevice getById(Long deviceId) {
        return baseMapper.selectById(deviceId);
    }

    @Override
    public boolean save(TwinDevice device) {
        return baseMapper.insert(device) > 0;
    }

    @Override
    public boolean updateById(TwinDevice device) {
        return baseMapper.updateById(device) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> deviceIds) {
        return baseMapper.deleteBatchIds(deviceIds) > 0;
    }
}
