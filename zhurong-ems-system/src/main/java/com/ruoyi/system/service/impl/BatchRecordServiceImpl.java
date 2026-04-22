package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.BatchRecord;
import com.ruoyi.system.domain.vo.BatchRecordVo;
import com.ruoyi.system.domain.bo.BatchRecordBo;
import com.ruoyi.system.mapper.BatchRecordMapper;
import com.ruoyi.system.service.IBatchRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 批次实绩Service业务层处理
 *
 * @author cpems
 * @date 2026-03-28
 */
@Service
public class BatchRecordServiceImpl extends ServiceImpl<BatchRecordMapper, BatchRecord> implements IBatchRecordService {

    @Override
    public BatchRecordVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    @Override
    public List<BatchRecordVo> queryList(BatchRecordBo bo) {
        Map<String, Object> params = BeanUtil.beanToMap(bo, false, true);
        return baseMapper.selectVoByMap(params);
    }

    @Override
    public TableDataInfo<BatchRecordVo> queryPageList(BatchRecordBo bo, PageQuery pageQuery) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<BatchRecord> wrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        Map<String, Object> params = BeanUtil.beanToMap(bo, false, true);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                wrapper.eq(entry.getKey(), entry.getValue());
            }
        }
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<BatchRecord> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        com.baomidou.mybatisplus.core.metadata.IPage<BatchRecordVo> voPage = baseMapper.selectVoPage(page, wrapper);
        return com.ruoyi.common.core.page.TableDataInfo.build(voPage);
    }

    @Override
    public Boolean insertByBo(BatchRecordBo bo) {
        BatchRecord add = BeanUtil.toBean(bo, BatchRecord.class);
        validEntityBeforeSave(add);
        // 设置默认状态为进行中
        add.setStatus("in_progress");
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(BatchRecordBo bo) {
        BatchRecord update = BeanUtil.toBean(bo, BatchRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(BatchRecord entity) {
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
    public List<Map<String, Object>> getBatchStatistics() {
        // 查询所有批次
        List<BatchRecord> batches = baseMapper.selectList(null);
        
        // 按批次状态分组统计
        Map<String, Long> statusMap = batches.stream()
            .collect(Collectors.groupingBy(
                batch -> batch.getStatus() == null ? "in_progress" : batch.getStatus(),
                Collectors.counting()
            ));

        // 转换为前端需要的格式
        List<Map<String, Object>> result = new ArrayList<>();
        statusMap.forEach((status, count) -> {
            Map<String, Object> item = new HashMap<>();
            item.put("status", status);
            item.put("count", count);
            result.add(item);
        });

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean completeBatch(Long batchId) {
        BatchRecord batch = baseMapper.selectById(batchId);
        if (ObjectUtil.isNull(batch)) {
            throw new RuntimeException("批次不存在");
        }
        if (!"in_progress".equals(batch.getStatus())) {
            throw new RuntimeException("批次状态错误，只能完成进行中的批次");
        }

        BatchRecord updateBatch = new BatchRecord();
        updateBatch.setId(batchId);
        updateBatch.setStatus("completed");
        updateBatch.setEndTime(new Date());
        return baseMapper.updateById(updateBatch) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelBatch(Long batchId) {
        BatchRecord batch = baseMapper.selectById(batchId);
        if (ObjectUtil.isNull(batch)) {
            throw new RuntimeException("批次不存在");
        }
        if ("completed".equals(batch.getStatus())) {
            throw new RuntimeException("已完成的批次不能取消");
        }

        BatchRecord updateBatch = new BatchRecord();
        updateBatch.setId(batchId);
        updateBatch.setStatus("cancelled");
        return baseMapper.updateById(updateBatch) > 0;
    }

    @Override
    public Map<String, Object> calculateEnergyIndicators(Long batchId) {
        BatchRecord batch = baseMapper.selectById(batchId);
        if (ObjectUtil.isNull(batch)) {
            throw new RuntimeException("批次不存在");
        }

        Map<String, Object> indicators = new HashMap<>();
        BigDecimal steelWeight = batch.getSteelWeight();

        if (steelWeight != null && steelWeight.compareTo(BigDecimal.ZERO) > 0) {
            // 计算吨钢电耗
            if (batch.getElectricity() != null) {
                BigDecimal electricityPerTon = batch.getElectricity().divide(steelWeight, 2, BigDecimal.ROUND_HALF_UP);
                indicators.put("electricityPerTon", electricityPerTon);
            }

            // 计算吨钢水耗
            if (batch.getWater() != null) {
                BigDecimal waterPerTon = batch.getWater().divide(steelWeight, 2, BigDecimal.ROUND_HALF_UP);
                indicators.put("waterPerTon", waterPerTon);
            }

            // 计算吨钢煤气消耗
            if (batch.getGas() != null) {
                BigDecimal gasPerTon = batch.getGas().divide(steelWeight, 2, BigDecimal.ROUND_HALF_UP);
                indicators.put("gasPerTon", gasPerTon);
            }

            // 计算吨钢煤炭消耗
            if (batch.getCoal() != null) {
                BigDecimal coalPerTon = batch.getCoal().divide(steelWeight, 2, BigDecimal.ROUND_HALF_UP);
                indicators.put("coalPerTon", coalPerTon);
            }

            // 计算吨钢能源成本
            if (batch.getEnergyCost() != null) {
                BigDecimal costPerTon = batch.getEnergyCost().divide(steelWeight, 2, BigDecimal.ROUND_HALF_UP);
                indicators.put("costPerTon", costPerTon);
            }
        }

        return indicators;
    }
}
