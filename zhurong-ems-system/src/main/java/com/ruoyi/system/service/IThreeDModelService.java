package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.ThreeDModel;
import java.util.List;
import java.util.Map;

public interface IThreeDModelService extends IService<ThreeDModel> {
    List<ThreeDModel> list();
    ThreeDModel getById(Long modelId);
    boolean save(ThreeDModel model);
    boolean updateById(ThreeDModel model);
    boolean removeByIds(List<Long> modelIds);
    Map<String, Object> getModelTree();
}
