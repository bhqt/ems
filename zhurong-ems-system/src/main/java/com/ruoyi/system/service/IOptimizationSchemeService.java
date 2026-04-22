package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.OptimizationScheme;

import java.util.List;

public interface IOptimizationSchemeService extends IService<OptimizationScheme> {

    List<OptimizationScheme> list();

    OptimizationScheme getById(Long schemeId);

    boolean save(OptimizationScheme scheme);

    boolean updateById(OptimizationScheme scheme);

    boolean removeByIds(List<Long> schemeIds);

    boolean executeOptimization(Long schemeId);
}
