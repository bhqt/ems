package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.SysApiInterface;
import com.ruoyi.system.mapper.SysApiInterfaceMapper;
import com.ruoyi.system.service.ISysApiInterfaceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysApiInterfaceServiceImpl extends ServiceImpl<SysApiInterfaceMapper, SysApiInterface> implements ISysApiInterfaceService {

    @Override
    public List<SysApiInterface> list() {
        return baseMapper.selectList(null);
    }

    @Override
    public SysApiInterface getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean save(SysApiInterface api) {
        return baseMapper.insert(api) > 0;
    }

    @Override
    public boolean updateById(SysApiInterface api) {
        return baseMapper.updateById(api) > 0;
    }

    @Override
    public boolean removeByIds(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<SysApiInterface> getByCategory(String category) {
        LambdaQueryWrapper<SysApiInterface> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysApiInterface::getCategory, category);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public SysApiInterface getByApiCode(String apiCode) {
        LambdaQueryWrapper<SysApiInterface> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysApiInterface::getApiCode, apiCode);
        return baseMapper.selectOne(wrapper);
    }
}
