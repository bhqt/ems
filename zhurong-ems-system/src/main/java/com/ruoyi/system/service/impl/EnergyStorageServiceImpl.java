package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.EnergyStorage;
import com.ruoyi.system.domain.bo.EnergyStorageBo;
import com.ruoyi.system.domain.vo.EnergyStorageVo;
import com.ruoyi.system.mapper.EnergyStorageMapper;
import com.ruoyi.system.mapper.StorageBatteryMapper;
import com.ruoyi.system.service.IEnergyStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 储能系统Service业务层处理
 * 
 * @author cpems
 * @date 2026-03-27
 */
@RequiredArgsConstructor
@Service
public class EnergyStorageServiceImpl implements IEnergyStorageService {

    private final EnergyStorageMapper baseMapper;
    private final StorageBatteryMapper storageBatteryMapper;

    /**
     * 查询储能系统
     * 
     * @param id 主键
     * @return 储能系统
     */
    @Override
    public EnergyStorageVo queryById(Long id) {
        EnergyStorageVo storage = baseMapper.selectEnergyStorageById(id);
        if (storage != null) {
            // 查询电池组数量
            int batteryCount = baseMapper.countBatteriesByStorageId(id);
            storage.setBatteryCount(batteryCount);
        }
        return storage;
    }

    /**
     * 查询储能系统列表
     * 
     * @param bo 业务对象
     * @param pageQuery 分页参数
     * @return 储能系统集合
     */
    @Override
    public TableDataInfo<EnergyStorageVo> queryPageList(EnergyStorageBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<EnergyStorage> lqw = buildQueryWrapper(bo);
        Page<EnergyStorageVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询储能系统列表（不分页）
     * 
     * @param bo 业务对象
     * @return 储能系统集合
     */
    @Override
    public List<EnergyStorageVo> queryList(EnergyStorageBo bo) {
        LambdaQueryWrapper<EnergyStorage> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<EnergyStorage> buildQueryWrapper(EnergyStorageBo bo) {
        LambdaQueryWrapper<EnergyStorage> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, EnergyStorage::getId, bo.getId());
        lqw.like(bo.getStorageName() != null, EnergyStorage::getStorageName, bo.getStorageName());
        lqw.eq(bo.getStorageCode() != null, EnergyStorage::getStorageCode, bo.getStorageCode());
        lqw.eq(bo.getStorageType() != null, EnergyStorage::getStorageType, bo.getStorageType());
        lqw.eq(bo.getAreaId() != null, EnergyStorage::getAreaId, bo.getAreaId());
        lqw.eq(bo.getStatus() != null, EnergyStorage::getStatus, bo.getStatus());
        lqw.eq(EnergyStorage::getDelFlag, "0");
        lqw.orderByDesc(EnergyStorage::getCreateTime);
        return lqw;
    }

    /**
     * 新增储能系统
     * 
     * @param bo 业务对象
     * @return 结果
     */
    @Override
    public Boolean insertByBo(EnergyStorageBo bo) {
        EnergyStorage add = BeanUtil.toBean(bo, EnergyStorage.class);
        validEntityBeforeSave(add);
        add.setDelFlag("0");
        add.setStatus("1"); // 默认正常状态
        return baseMapper.insert(add) > 0;
    }

    /**
     * 修改储能系统
     * 
     * @param bo 业务对象
     * @return 结果
     */
    @Override
    public Boolean updateByBo(EnergyStorageBo bo) {
        EnergyStorage update = BeanUtil.toBean(bo, EnergyStorage.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     * 
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(EnergyStorage entity) {
        // TODO 做一些数据校验,如唯一校验
    }

    /**
     * 批量删除储能系统
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
     * 更新储能系统状态
     * 
     * @param id 主键
     * @param status 状态
     * @return 结果
     */
    @Override
    public Boolean updateStatus(Long id, String status) {
        return baseMapper.updateStorageStatus(id, status) > 0;
    }

    /**
     * 获取储能系统统计数据
     * 
     * @return 统计数据
     */
    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> result = new HashMap<>();

        // 查询所有储能系统
        List<EnergyStorageVo> storageList = baseMapper.selectVoList(
            Wrappers.<EnergyStorage>lambdaQuery()
                .eq(EnergyStorage::getDelFlag, "0")
        );

        // 统计信息
        int totalStorage = storageList.size();
        int normalStorage = 0;
        int faultStorage = 0;
        int maintenanceStorage = 0;
        BigDecimal totalCapacity = BigDecimal.ZERO;

        for (EnergyStorageVo storage : storageList) {
            totalCapacity = totalCapacity.add(storage.getCapacity() != null ? storage.getCapacity() : BigDecimal.ZERO);
            switch (storage.getStatus()) {
                case "1":
                    normalStorage++;
                    break;
                case "2":
                    faultStorage++;
                    break;
                case "3":
                    maintenanceStorage++;
                    break;
            }
        }

        result.put("totalStorage", totalStorage);
        result.put("normalStorage", normalStorage);
        result.put("faultStorage", faultStorage);
        result.put("maintenanceStorage", maintenanceStorage);
        result.put("totalCapacity", totalCapacity);

        // TODO: 从实时数据表获取今日充放电量、累计充放电量等数据
        result.put("todayCharge", new BigDecimal("0"));
        result.put("todayDischarge", new BigDecimal("0"));
        result.put("totalCharge", new BigDecimal("0"));
        result.put("totalDischarge", new BigDecimal("0"));
        result.put("currentPower", new BigDecimal("0"));

        return result;
    }

    /**
     * 获取储能系统实时数据
     * 
     * @param storageId 储能系统ID
     * @return 实时数据
     */
    @Override
    public Map<String, Object> getRealTimeData(Long storageId) {
        Map<String, Object> result = new HashMap<>();

        EnergyStorageVo storage = baseMapper.selectEnergyStorageById(storageId);
        if (storage == null) {
            return result;
        }

        result.put("storageId", storage.getId());
        result.put("storageName", storage.getStorageName());
        result.put("capacity", storage.getCapacity());
        result.put("power", storage.getPower());

        // TODO: 从实时数据表获取实时数据
        result.put("currentPower", new BigDecimal("0"));
        result.put("currentSoc", new BigDecimal("0"));
        result.put("todayCharge", new BigDecimal("0"));
        result.put("todayDischarge", new BigDecimal("0"));
        result.put("totalCharge", new BigDecimal("0"));
        result.put("totalDischarge", new BigDecimal("0"));
        result.put("temperature", new BigDecimal("0"));
        result.put("voltage", new BigDecimal("0"));
        result.put("current", new BigDecimal("0"));

        return result;
    }

    /**
     * 获取充放电统计
     * 
     * @param storageId 储能系统ID
     * @param dateType 日期类型（day/month/year）
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 充放电统计
     */
    @Override
    public List<Map<String, Object>> getChargeDischargeStatistics(Long storageId, String dateType, String startTime, String endTime) {
        List<Map<String, Object>> result = new ArrayList<>();

        // TODO: 从数据库查询充放电统计数据
        // 这里返回模拟数据
        Map<String, Object> data = new HashMap<>();
        data.put("time", "2026-03-27");
        data.put("charge", new BigDecimal("500.5"));
        data.put("discharge", new BigDecimal("450.2"));
        data.put("netEnergy", new BigDecimal("50.3"));
        result.add(data);

        return result;
    }

    /**
     * 获取电池组状态统计
     * 
     * @param storageId 储能系统ID
     * @return 电池组状态统计
     */
    @Override
    public Map<String, Object> getBatteryStatusStatistics(Long storageId) {
        Map<String, Object> result = new HashMap<>();

        int totalBatteries = baseMapper.countBatteriesByStorageId(storageId);
        int normalBatteries = storageBatteryMapper.countBatteriesByStatus(storageId, "1");
        int faultBatteries = storageBatteryMapper.countBatteriesByStatus(storageId, "2");
        int maintenanceBatteries = storageBatteryMapper.countBatteriesByStatus(storageId, "3");

        result.put("totalBatteries", totalBatteries);
        result.put("normalBatteries", normalBatteries);
        result.put("faultBatteries", faultBatteries);
        result.put("maintenanceBatteries", maintenanceBatteries);

        return result;
    }
}
