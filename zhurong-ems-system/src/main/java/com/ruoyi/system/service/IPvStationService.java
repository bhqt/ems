package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.PvStation;
import java.util.Map;

/**
 * 光伏电站Service接口
 * 
 * @author cpems
 * @date 2026-03-27
 */
public interface IPvStationService extends IService<PvStation> {

    /**
     * 获取光伏电站统计数据
     * @return 统计数据
     */
    Map<String, Object> getStatistics();

}
