package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.EnergyBalance;
import com.ruoyi.system.domain.vo.EnergyBalanceVo;
import com.ruoyi.system.domain.bo.EnergyBalanceBo;
import com.ruoyi.system.mapper.EnergyBalanceMapper;
import com.ruoyi.system.service.IEnergyBalanceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * 能源平衡Service业务层处理
 *
 * @author cpems
 * @date 2026-03-28
 */
@Service
public class EnergyBalanceServiceImpl extends ServiceImpl<EnergyBalanceMapper, EnergyBalance> implements IEnergyBalanceService {

    @Override
    public EnergyBalanceVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    @Override
    public List<EnergyBalanceVo> queryList(EnergyBalanceBo bo) {
        QueryWrapper<EnergyBalance> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(bo)) {
            queryWrapper.eq(ObjectUtil.isNotNull(bo.getEnergyMedium()), "energy_medium", bo.getEnergyMedium());
            queryWrapper.eq(ObjectUtil.isNotNull(bo.getBalanceDate()), "balance_date", bo.getBalanceDate());
            queryWrapper.eq(ObjectUtil.isNotNull(bo.getStatus()), "status", bo.getStatus());
        }
        return baseMapper.selectVoList(queryWrapper);
    }

    @Override
    public TableDataInfo<EnergyBalanceVo> queryPageList(EnergyBalanceBo bo, PageQuery pageQuery) {
        QueryWrapper<EnergyBalance> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(bo)) {
            queryWrapper.eq(ObjectUtil.isNotNull(bo.getEnergyMedium()), "energy_medium", bo.getEnergyMedium());
            queryWrapper.eq(ObjectUtil.isNotNull(bo.getBalanceDate()), "balance_date", bo.getBalanceDate());
            queryWrapper.eq(ObjectUtil.isNotNull(bo.getStatus()), "status", bo.getStatus());
        }
        Page<EnergyBalance> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        page = baseMapper.selectPage(page, queryWrapper);
        List<EnergyBalanceVo> list = page.getRecords().stream().map(item -> BeanUtil.toBean(item, EnergyBalanceVo.class)).collect(Collectors.toList());
        return TableDataInfo.build(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(EnergyBalanceBo bo) {
        // 计算平衡率和状态
        EnergyBalance balance = calculateBalance(bo);
        validEntityBeforeSave(balance);
        boolean flag = baseMapper.insert(balance) > 0;
        if (flag) {
            bo.setId(balance.getId());
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByBo(EnergyBalanceBo bo) {
        // 重新计算平衡率和状态
        EnergyBalance balance = calculateBalance(bo);
        validEntityBeforeSave(balance);
        return baseMapper.updateById(balance) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(EnergyBalance entity) {
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
    public EnergyBalance calculateBalance(EnergyBalanceBo bo) {
        EnergyBalance balance = BeanUtil.toBean(bo, EnergyBalance.class);
        
        // 计算损耗量
        BigDecimal supplyAmount = balance.getSupplyAmount();
        BigDecimal consumptionAmount = balance.getConsumptionAmount();
        BigDecimal lossAmount = supplyAmount.subtract(consumptionAmount);
        balance.setLossAmount(lossAmount);
        
        // 计算平衡率
        BigDecimal balanceRate;
        if (supplyAmount.compareTo(BigDecimal.ZERO) > 0) {
            balanceRate = consumptionAmount.divide(supplyAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        } else {
            balanceRate = BigDecimal.ZERO;
        }
        balance.setBalanceRate(balanceRate);
        
        // 确定平衡状态
        String status;
        BigDecimal rate = balanceRate.abs();
        if (rate.compareTo(new BigDecimal(95)) >= 0 && rate.compareTo(new BigDecimal(105)) <= 0) {
            status = "normal";
        } else {
            status = "warning";
        }
        balance.setStatus(status);
        
        return balance;
    }

    @Override
    public List<Map<String, Object>> getMediumStatistics() {
        // 查询所有能源平衡记录
        List<EnergyBalance> balances = baseMapper.selectList(null);
        
        // 按能源介质分组统计
        Map<String, BigDecimal> mediumMap = balances.stream()
            .collect(Collectors.groupingBy(
                balance -> balance.getEnergyMedium() == null ? "other" : balance.getEnergyMedium(),
                Collectors.reducing(
                    BigDecimal.ZERO,
                    EnergyBalance::getConsumptionAmount,
                    BigDecimal::add
                )
            ));

        // 转换为前端需要的格式
        List<Map<String, Object>> result = new ArrayList<>();
        mediumMap.forEach((medium, amount) -> {
            Map<String, Object> item = new HashMap<>();
            item.put("medium", medium);
            item.put("amount", amount);
            result.add(item);
        });

        return result;
    }

    @Override
    public List<Map<String, Object>> getStatusStatistics() {
        // 查询所有能源平衡记录
        List<EnergyBalance> balances = baseMapper.selectList(null);
        
        // 按平衡状态分组统计
        Map<String, Long> statusMap = balances.stream()
            .collect(Collectors.groupingBy(
                balance -> balance.getStatus() == null ? "normal" : balance.getStatus(),
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
}
