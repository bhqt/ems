package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.TwinPipeline;
import com.ruoyi.system.mapper.TwinPipelineMapper;
import com.ruoyi.system.service.ITwinPipelineService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TwinPipelineServiceImpl extends ServiceImpl<TwinPipelineMapper, TwinPipeline> implements ITwinPipelineService {

    @Override
    public List<TwinPipeline> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public TwinPipeline getById(Long pipelineId) {
        return baseMapper.selectById(pipelineId);
    }

    @Override
    public boolean save(TwinPipeline pipeline) {
        return baseMapper.insert(pipeline) > 0;
    }

    @Override
    public boolean updateById(TwinPipeline pipeline) {
        return baseMapper.updateById(pipeline) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> pipelineIds) {
        return baseMapper.deleteBatchIds(pipelineIds) > 0;
    }
}
