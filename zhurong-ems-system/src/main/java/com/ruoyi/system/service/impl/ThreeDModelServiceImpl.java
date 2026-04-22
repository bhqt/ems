package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.ThreeDModel;
import com.ruoyi.system.mapper.ThreeDModelMapper;
import com.ruoyi.system.service.IThreeDModelService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ThreeDModelServiceImpl extends ServiceImpl<ThreeDModelMapper, ThreeDModel> implements IThreeDModelService {

    @Override
    public List<ThreeDModel> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public ThreeDModel getById(Long modelId) {
        return baseMapper.selectById(modelId);
    }

    @Override
    public boolean save(ThreeDModel model) {
        return baseMapper.insert(model) > 0;
    }

    @Override
    public boolean updateById(ThreeDModel model) {
        return baseMapper.updateById(model) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> modelIds) {
        return baseMapper.deleteBatchIds(modelIds) > 0;
    }

    @Override
    public Map<String, Object> getModelTree() {
        Map<String, Object> tree = new HashMap<>();
        tree.put("id", 0);
        tree.put("label", "三维工厂模型");
        List<Map<String, Object>> children = new ArrayList<>();
        
        List<ThreeDModel> models = baseMapper.selectList(null);
        for (ThreeDModel model : models) {
            Map<String, Object> node = new HashMap<>();
            node.put("id", model.getModelId());
            node.put("label", model.getModelName());
            node.put("type", model.getModelType());
            children.add(node);
        }
        tree.put("children", children);
        return tree;
    }
}
