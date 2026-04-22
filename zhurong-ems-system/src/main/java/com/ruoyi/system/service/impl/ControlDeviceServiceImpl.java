package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.ControlDevice;
import com.ruoyi.system.domain.vo.ControlDeviceVo;
import com.ruoyi.system.domain.bo.ControlDeviceBo;
import com.ruoyi.system.mapper.ControlDeviceMapper;
import com.ruoyi.system.service.IControlDeviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 控制设备Service业务层处理
 *
 * @author cpems
 * @date 2026-03-28
 */
@Service
public class ControlDeviceServiceImpl extends ServiceImpl<ControlDeviceMapper, ControlDevice> implements IControlDeviceService {

    @Override
    public ControlDeviceVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    @Override
    public List<ControlDeviceVo> queryList(ControlDeviceBo bo) {
        Map<String, Object> params = BeanUtil.beanToMap(bo, false, true);
        return baseMapper.selectVoByMap(params);
    }

    @Override
    public TableDataInfo<ControlDeviceVo> queryPageList(ControlDeviceBo bo, PageQuery pageQuery) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<ControlDevice> wrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        Map<String, Object> params = BeanUtil.beanToMap(bo, false, true);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                wrapper.eq(entry.getKey(), entry.getValue());
            }
        }
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<ControlDevice> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        com.baomidou.mybatisplus.core.metadata.IPage<ControlDeviceVo> voPage = baseMapper.selectVoPage(page, wrapper);
        return com.ruoyi.common.core.page.TableDataInfo.build(voPage);
    }

    @Override
    public Boolean insertByBo(ControlDeviceBo bo) {
        ControlDevice add = BeanUtil.toBean(bo, ControlDevice.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(ControlDeviceBo bo) {
        ControlDevice update = BeanUtil.toBean(bo, ControlDevice.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(ControlDevice entity) {
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
    @Transactional(rollbackFor = Exception.class)
    public Boolean controlDevice(Long deviceId, String action) {
        // 查询设备信息
        ControlDevice device = baseMapper.selectById(deviceId);
        if (ObjectUtil.isNull(device)) {
            throw new RuntimeException("设备不存在");
        }

        // 检查设备状态
        if ("start".equals(action) && "online".equals(device.getStatus())) {
            throw new RuntimeException("设备已在运行中");
        }
        if ("stop".equals(action) && "offline".equals(device.getStatus())) {
            throw new RuntimeException("设备已停止");
        }

        // 更新设备状态
        ControlDevice updateDevice = new ControlDevice();
        updateDevice.setId(deviceId);
        updateDevice.setStatus("start".equals(action) ? "online" : "offline");
        baseMapper.updateById(updateDevice);

        // TODO 记录控制日志和控制指令

        return true;
    }

    @Override
    public List<Map<String, Object>> getDeviceStatistics() {
        // 查询所有设备
        List<ControlDevice> devices = baseMapper.selectList(null);
        
        // 按设备类型分组统计
        Map<String, Map<String, Long>> statistics = devices.stream()
            .collect(Collectors.groupingBy(
                ControlDevice::getDeviceType,
                Collectors.groupingBy(
                    device -> device.getStatus() == null ? "offline" : device.getStatus(),
                    Collectors.counting()
                )
            ));

        // 转换为前端需要的格式
        List<Map<String, Object>> result = new ArrayList<>();
        statistics.forEach((deviceType, statusMap) -> {
            Map<String, Object> item = new HashMap<>();
            item.put("deviceType", deviceType);
            item.put("total", statusMap.values().stream().mapToLong(Long::longValue).sum());
            item.put("online", statusMap.getOrDefault("online", 0L));
            item.put("offline", statusMap.getOrDefault("offline", 0L));
            item.put("standby", statusMap.getOrDefault("standby", 0L));
            item.put("maintenance", statusMap.getOrDefault("maintenance", 0L));
            result.add(item);
        });

        return result;
    }
}
