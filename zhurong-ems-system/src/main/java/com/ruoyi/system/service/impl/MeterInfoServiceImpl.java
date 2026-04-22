package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.MeterInfo;
import com.ruoyi.system.domain.vo.MeterInfoVo;
import com.ruoyi.system.domain.bo.MeterInfoBo;
import com.ruoyi.system.mapper.MeterInfoMapper;
import com.ruoyi.system.service.IMeterInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import org.springframework.stereotype.Service;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 计量器具信息Service业务层处理
 *
 * @author cpems
 * @date 2026-03-28
 */
@Service
public class MeterInfoServiceImpl extends ServiceImpl<MeterInfoMapper, MeterInfo> implements IMeterInfoService {

    @Override
    public MeterInfoVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    @Override
    public List<MeterInfoVo> queryList(MeterInfoBo bo) {
        Map<String, Object> params = BeanUtil.beanToMap(bo, false, true);
        return baseMapper.selectVoByMap(params);
    }

    @Override
    public TableDataInfo<MeterInfoVo> queryPageList(MeterInfoBo bo, PageQuery pageQuery) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<MeterInfo> wrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        Map<String, Object> params = BeanUtil.beanToMap(bo, false, true);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                wrapper.eq(entry.getKey(), entry.getValue());
            }
        }
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<MeterInfo> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        com.baomidou.mybatisplus.core.metadata.IPage<MeterInfoVo> voPage = baseMapper.selectVoPage(page, wrapper);
        return com.ruoyi.common.core.page.TableDataInfo.build(voPage);
    }

    @Override
    public Boolean insertByBo(MeterInfoBo bo) {
        MeterInfo add = BeanUtil.toBean(bo, MeterInfo.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(MeterInfoBo bo) {
        MeterInfo update = BeanUtil.toBean(bo, MeterInfo.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(MeterInfo entity) {
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
    public List<Map<String, Object>> getMeterStatistics() {
        // 查询所有器具
        List<MeterInfo> meters = baseMapper.selectList(null);
        
        // 按器具类型分组统计
        Map<String, Map<String, Long>> statistics = meters.stream()
            .collect(Collectors.groupingBy(
                MeterInfo::getMeterType,
                Collectors.groupingBy(
                    meter -> meter.getStatus() == null ? "normal" : meter.getStatus(),
                    Collectors.counting()
                )
            ));

        // 转换为前端需要的格式
        List<Map<String, Object>> result = new ArrayList<>();
        statistics.forEach((meterType, statusMap) -> {
            Map<String, Object> item = new HashMap<>();
            item.put("meterType", meterType);
            item.put("total", statusMap.values().stream().mapToLong(Long::longValue).sum());
            item.put("normal", statusMap.getOrDefault("normal", 0L));
            item.put("warning", statusMap.getOrDefault("warning", 0L));
            item.put("error", statusMap.getOrDefault("error", 0L));
            item.put("maintenance", statusMap.getOrDefault("maintenance", 0L));
            result.add(item);
        });

        return result;
    }

    @Override
    public List<MeterInfoVo> getNeedCalibrationList() {
        // 查询所有器具
        List<MeterInfo> meters = baseMapper.selectList(null);
        
        // 过滤出需要校准的器具（下次校准日期在30天内或已过期）
        LocalDate today = LocalDate.now();
        List<MeterInfo> needCalibration = meters.stream()
            .filter(meter -> {
                if (meter.getNextCalibrationDate() == null) {
                    return false;
                }
                LocalDate nextCalibrationDate = meter.getNextCalibrationDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
                long days = ChronoUnit.DAYS.between(today, nextCalibrationDate);
                return days <= 30;
            })
            .collect(Collectors.toList());

        // 转换为VO
        return needCalibration.stream()
            .map(meter -> BeanUtil.toBean(meter, MeterInfoVo.class))
            .collect(Collectors.toList());
    }
}
