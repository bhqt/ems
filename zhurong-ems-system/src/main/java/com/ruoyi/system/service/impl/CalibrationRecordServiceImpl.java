package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.CalibrationRecord;
import com.ruoyi.system.domain.vo.CalibrationRecordVo;
import com.ruoyi.system.domain.bo.CalibrationRecordBo;
import com.ruoyi.system.mapper.CalibrationRecordMapper;
import com.ruoyi.system.service.ICalibrationRecordService;
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
 * 校准记录Service业务层处理
 *
 * @author cpems
 * @date 2026-03-28
 */
@Service
public class CalibrationRecordServiceImpl extends ServiceImpl<CalibrationRecordMapper, CalibrationRecord> implements ICalibrationRecordService {

    @Override
    public CalibrationRecordVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    @Override
    public List<CalibrationRecordVo> queryList(CalibrationRecordBo bo) {
        Map<String, Object> params = BeanUtil.beanToMap(bo, false, true);
        return baseMapper.selectVoByMap(params);
    }

    @Override
    public TableDataInfo<CalibrationRecordVo> queryPageList(CalibrationRecordBo bo, PageQuery pageQuery) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<CalibrationRecord> wrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        Map<String, Object> params = BeanUtil.beanToMap(bo, false, true);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                wrapper.eq(entry.getKey(), entry.getValue());
            }
        }
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<CalibrationRecord> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        com.baomidou.mybatisplus.core.metadata.IPage<CalibrationRecordVo> voPage = baseMapper.selectVoPage(page, wrapper);
        return com.ruoyi.common.core.page.TableDataInfo.build(voPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(CalibrationRecordBo bo) {
        CalibrationRecord add = BeanUtil.toBean(bo, CalibrationRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(CalibrationRecordBo bo) {
        CalibrationRecord update = BeanUtil.toBean(bo, CalibrationRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(CalibrationRecord entity) {
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
    public List<Map<String, Object>> getCalibrationStatistics() {
        // 查询所有校准记录
        List<CalibrationRecord> records = baseMapper.selectList(null);
        
        // 按校准结果分组统计
        Map<String, Long> resultMap = records.stream()
            .collect(Collectors.groupingBy(
                record -> record.getCalibrationResult() == null ? "pass" : record.getCalibrationResult(),
                Collectors.counting()
            ));

        // 转换为前端需要的格式
        List<Map<String, Object>> result = new ArrayList<>();
        resultMap.forEach((resultType, count) -> {
            Map<String, Object> item = new HashMap<>();
            item.put("result", resultType);
            item.put("count", count);
            result.add(item);
        });

        return result;
    }

    @Override
    public List<CalibrationRecordVo> getMeterCalibrationHistory(Long meterId) {
        // 构建查询条件
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<CalibrationRecord> wrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        wrapper.eq("meter_id", meterId);
        
        // 查询该器具的校准历史
        List<CalibrationRecord> records = baseMapper.selectList(wrapper);
        
        // 转换为VO并按日期排序
        return records.stream()
            .map(record -> BeanUtil.toBean(record, CalibrationRecordVo.class))
            .sorted((a, b) -> b.getCalibrationDate().compareTo(a.getCalibrationDate()))
            .collect(Collectors.toList());
    }
}
