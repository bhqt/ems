package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.TwinPipeline;
import java.util.List;

public interface ITwinPipelineService extends IService<TwinPipeline> {
    List<TwinPipeline> list();
    TwinPipeline getById(Long pipelineId);
    boolean save(TwinPipeline pipeline);
    boolean updateById(TwinPipeline pipeline);
    boolean removeByIds(List<Long> pipelineIds);
}
