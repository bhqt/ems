package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.mapper.PvComponentMapper;
import com.ruoyi.system.domain.PvComponent;
import com.ruoyi.system.service.IPvComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 光伏组件Service业务层处理
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Service
@RequiredArgsConstructor
public class PvComponentServiceImpl extends ServiceImpl<PvComponentMapper, PvComponent> implements IPvComponentService {

}
