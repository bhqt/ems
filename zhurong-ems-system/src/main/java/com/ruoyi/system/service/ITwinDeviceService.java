package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.TwinDevice;
import java.util.List;

public interface ITwinDeviceService extends IService<TwinDevice> {
    List<TwinDevice> list();
    TwinDevice getById(Long deviceId);
    boolean save(TwinDevice device);
    boolean updateById(TwinDevice device);
    boolean removeByIds(List<Long> deviceIds);
}
