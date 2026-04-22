package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.BenchmarkStandard;
import com.ruoyi.system.domain.vo.BenchmarkStandardVo;
import com.ruoyi.system.domain.bo.BenchmarkStandardBo;
import com.ruoyi.system.mapper.BenchmarkStandardMapper;
import com.ruoyi.system.service.IBenchmarkStandardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 标杆标准Service业务层处理
 *
 * @author cpems
 * @date 2026-03-28
 */
@Service
public class BenchmarkStandardServiceImpl extends ServiceImpl<BenchmarkStandardMapper, BenchmarkStandard> implements IBenchmarkStandardService {

    @Override
    public BenchmarkStandardVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    @Override
    public List<BenchmarkStandardVo> queryList(BenchmarkStandardBo bo) {
        Map<String, Object> params = BeanUtil.beanToMap(bo, false, true);
        return baseMapper.selectVoByMap(params);
    }

    @Override
    public TableDataInfo<BenchmarkStandardVo> queryPageList(BenchmarkStandardBo bo, PageQuery pageQuery) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<BenchmarkStandard> wrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        Map<String, Object> params = BeanUtil.beanToMap(bo, false, true);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                wrapper.eq(entry.getKey(), entry.getValue());
            }
        }
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<BenchmarkStandard> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        com.baomidou.mybatisplus.core.metadata.IPage<BenchmarkStandardVo> voPage = baseMapper.selectVoPage(page, wrapper);
        return com.ruoyi.common.core.page.TableDataInfo.build(voPage);
    }

    @Override
    public Boolean insertByBo(BenchmarkStandardBo bo) {
        BenchmarkStandard add = BeanUtil.toBean(bo, BenchmarkStandard.class);
        validEntityBeforeSave(add);
        // 设置默认状态为有效
        add.setStatus("active");
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(BenchmarkStandardBo bo) {
        BenchmarkStandard update = BeanUtil.toBean(bo, BenchmarkStandard.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(BenchmarkStandard entity) {
        // TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<Map<String, Object>> getStandardStatistics() {
        // 查询所有标准
        List<BenchmarkStandard> standards = baseMapper.selectList(null);
        
        // 按标准类型分组统计
        Map<String, Long> typeMap = standards.stream()
            .collect(Collectors.groupingBy(
                standard -> standard.getStandardType() == null ? "enterprise" : standard.getStandardType(),
                Collectors.counting()
            ));

        // 转换为前端需要的格式
        List<Map<String, Object>> result = new ArrayList<>();
        typeMap.forEach((type, count) -> {
            Map<String, Object> item = new HashMap<>();
            item.put("type", type);
            item.put("count", count);
            result.add(item);
        });

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean activateStandard(Long standardId) {
        BenchmarkStandard standard = baseMapper.selectById(standardId);
        if (ObjectUtil.isNull(standard)) {
            throw new RuntimeException("标准不存在");
        }

        BenchmarkStandard updateStandard = new BenchmarkStandard();
        updateStandard.setId(standardId);
        updateStandard.setStatus("active");
        return baseMapper.updateById(updateStandard) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deactivateStandard(Long standardId) {
        BenchmarkStandard standard = baseMapper.selectById(standardId);
        if (ObjectUtil.isNull(standard)) {
            throw new RuntimeException("标准不存在");
        }

        BenchmarkStandard updateStandard = new BenchmarkStandard();
        updateStandard.setId(standardId);
        updateStandard.setStatus("inactive");
        return baseMapper.updateById(updateStandard) > 0;
    }

    @Override
    public List<BenchmarkStandardVo> getActiveStandards() {
        // 构建查询条件
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<BenchmarkStandard> wrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        wrapper.eq("status", "active");
        
        // 查询有效标准
        List<BenchmarkStandard> standards = baseMapper.selectList(wrapper);
        
        // 转换为VO
        return standards.stream()
            .map(standard -> BeanUtil.toBean(standard, BenchmarkStandardVo.class))
            .collect(Collectors.toList());
    }
}
