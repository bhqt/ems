package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.MicroGrid;

import java.util.Map;

/**
 * 微电网Service接口
 *
 * @author cpems
 * @date 2026-03-27
 */
public interface IMicroGridService extends IService<MicroGrid> {

    /**
     * 获取微电网统计数据
     */
    Map<String, Object> getStatistics();

}
