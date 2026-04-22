package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.StorageBattery;
import com.ruoyi.system.domain.bo.StorageBatteryBo;
import com.ruoyi.system.domain.vo.StorageBatteryVo;
import com.ruoyi.system.mapper.StorageBatteryMapper;
import com.ruoyi.system.service.IStorageBatteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 储能电池组Service业务层处理
 * 
 * @author cpems
 * @date 2026-03-27
 */
@RequiredArgsConstructor
@Service
public class StorageBatteryServiceImpl implements IStorageBatteryService {

    private final StorageBatteryMapper baseMapper;

    /**
     * 查询储能电池组
     * 
     * @param id 主键
     * @return 储能电池组
     */
    @Override
    public StorageBatteryVo queryById(Long id) {
        return baseMapper.selectStorageBatteryById(id);
    }

    /**
     * 查询储能电池组列表
     * 
     * @param bo 业务对象
     * @param pageQuery 分页参数
     * @return 储能电池组集合
     */
    @Override
    public TableDataInfo<StorageBatteryVo> queryPageList(StorageBatteryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<StorageBattery> lqw = buildQueryWrapper(bo);
        Page<StorageBatteryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询储能电池组列表（不分页）
     * 
     * @param bo 业务对象
     * @return 储能电池组集合
     */
    @Override
    public List<StorageBatteryVo> queryList(StorageBatteryBo bo) {
        LambdaQueryWrapper<StorageBattery> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<StorageBattery> buildQueryWrapper(StorageBatteryBo bo) {
        LambdaQueryWrapper<StorageBattery> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, StorageBattery::getId, bo.getId());
        lqw.eq(bo.getStorageId() != null, StorageBattery::getStorageId, bo.getStorageId());
        lqw.like(bo.getBatteryName() != null, StorageBattery::getBatteryName, bo.getBatteryName());
        lqw.eq(bo.getBatteryCode() != null, StorageBattery::getBatteryCode, bo.getBatteryCode());
        lqw.eq(bo.getStatus() != null, StorageBattery::getStatus, bo.getStatus());
        lqw.eq(StorageBattery::getDelFlag, "0");
        lqw.orderByDesc(StorageBattery::getCreateTime);
        return lqw;
    }

    /**
     * 新增储能电池组
     * 
     * @param bo 业务对象
     * @return 结果
     */
    @Override
    public Boolean insertByBo(StorageBatteryBo bo) {
        StorageBattery add = BeanUtil.toBean(bo, StorageBattery.class);
        validEntityBeforeSave(add);
        add.setDelFlag("0");
        add.setStatus("1"); // 默认正常状态
        return baseMapper.insert(add) > 0;
    }

    /**
     * 修改储能电池组
     * 
     * @param bo 业务对象
     * @return 结果
     */
    @Override
    public Boolean updateByBo(StorageBatteryBo bo) {
        StorageBattery update = BeanUtil.toBean(bo, StorageBattery.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     * 
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(StorageBattery entity) {
        // TODO 做一些数据校验,如唯一校验
    }

    /**
     * 批量删除储能电池组
     * 
     * @param ids 主键集合
     * @param isValid 是否校验
     * @return 结果
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 更新电池组状态
     * 
     * @param id 主键
     * @param status 状态
     * @return 结果
     */
    @Override
    public Boolean updateStatus(Long id, String status) {
        return baseMapper.updateBatteryStatus(id, status) > 0;
    }

    /**
     * 根据储能系统ID查询电池组列表
     * 
     * @param storageId 储能系统ID
     * @return 电池组列表
     */
    @Override
    public List<StorageBatteryVo> queryBatteriesByStorageId(Long storageId) {
        return baseMapper.selectBatteriesByStorageId(storageId);
    }

    /**
     * 获取电池组实时数据
     * 
     * @param batteryId 电池组ID
     * @return 实时数据
     */
    @Override
    public Map<String, Object> getRealTimeData(Long batteryId) {
        Map<String, Object> result = new HashMap<>();

        StorageBatteryVo battery = baseMapper.selectStorageBatteryById(batteryId);
        if (battery == null) {
            return result;
        }

        result.put("batteryId", battery.getId());
        result.put("batteryName", battery.getBatteryName());
        result.put("ratedCapacity", battery.getRatedCapacity());
        result.put("ratedVoltage", battery.getRatedVoltage());

        // TODO: 从实时数据表获取实时数据
        result.put("currentSoc", new BigDecimal("0"));
        result.put("currentVoltage", new BigDecimal("0"));
        result.put("currentCurrent", new BigDecimal("0"));
        result.put("currentTemperature", new BigDecimal("0"));
        result.put("cycleCount", 0);
        result.put("soh", new BigDecimal("0"));

        return result;
    }

    /**
     * 获取电池组历史数据
     * 
     * @param batteryId 电池组ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param dataType 数据类型（voltage/current/temperature/soc）
     * @return 历史数据
     */
    @Override
    public List<Map<String, Object>> getHistoryData(Long batteryId, String startTime, String endTime, String dataType) {
        List<Map<String, Object>> result = new ArrayList<>();

        // TODO: 从数据库查询历史数据
        // 这里返回模拟数据
        for (int i = 0; i < 24; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("time", "2026-03-27 " + String.format("%02d:00", i));
            data.put("value", new BigDecimal(Math.random() * 100));
            result.add(data);
        }

        return result;
    }

    /**
     * 获取电池组健康状态统计
     * 
     * @param storageId 储能系统ID
     * @return 健康状态统计
     */
    @Override
    public Map<String, Object> getHealthStatistics(Long storageId) {
        Map<String, Object> result = new HashMap<>();

        List<StorageBatteryVo> batteries = baseMapper.selectBatteriesByStorageId(storageId);

        int totalBatteries = batteries.size();
        int healthyBatteries = 0;
        int warningBatteries = 0;
        int faultBatteries = 0;
        BigDecimal avgSoh = BigDecimal.ZERO;
        BigDecimal totalSoh = BigDecimal.ZERO;

        for (StorageBatteryVo battery : batteries) {
            // 模拟SOH数据
            BigDecimal soh = new BigDecimal("85");
            totalSoh = totalSoh.add(soh);
            
            if (soh.compareTo(new BigDecimal("90")) >= 0) {
                healthyBatteries++;
            } else if (soh.compareTo(new BigDecimal("70")) >= 0) {
                warningBatteries++;
            } else {
                faultBatteries++;
            }
        }

        if (totalBatteries > 0) {
            avgSoh = totalSoh.divide(new BigDecimal(totalBatteries), 2, BigDecimal.ROUND_HALF_UP);
        }

        result.put("totalBatteries", totalBatteries);
        result.put("healthyBatteries", healthyBatteries);
        result.put("warningBatteries", warningBatteries);
        result.put("faultBatteries", faultBatteries);
        result.put("avgSoh", avgSoh);

        return result;
    }
}
