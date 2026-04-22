package com.ruoyi.system.service;

import com.ruoyi.system.domain.ControlDevice;
import com.ruoyi.system.domain.vo.ControlDeviceVo;
import com.ruoyi.system.domain.bo.ControlDeviceBo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 控制设备Service接口
 *
 * @author cpems
 * @date 2026-03-28
 */
public interface IControlDeviceService extends IService<ControlDevice> {

    /**
     * 查询控制设备
     *
     * @param id 主键
     * @return 控制设备
     */
    ControlDeviceVo queryById(Long id);

    /**
     * 查询控制设备列表
     *
     * @param bo 控制设备
     * @return 控制设备集合
     */
    List<ControlDeviceVo> queryList(ControlDeviceBo bo);

    /**
     * 查询控制设备列表（分页）
     *
     * @param bo 控制设备
     * @return 控制设备分页集合
     */
    TableDataInfo<ControlDeviceVo> queryPageList(ControlDeviceBo bo, PageQuery pageQuery);

    /**
     * 新增控制设备
     *
     * @param bo 控制设备
     * @return 结果
     */
    Boolean insertByBo(ControlDeviceBo bo);

    /**
     * 修改控制设备
     *
     * @param bo 控制设备
     * @return 结果
     */
    Boolean updateByBo(ControlDeviceBo bo);

    /**
     * 校验并批量删除控制设备信息
     *
     * @param ids 主键集合
     * @param isValid 是否校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 控制设备
     *
     * @param deviceId 设备ID
     * @param action 操作类型(start:启动, stop:停止)
     * @return 结果
     */
    Boolean controlDevice(Long deviceId, String action);

    /**
     * 查询设备状态统计
     *
     * @return 设备状态统计列表
     */
    List<Map<String, Object>> getDeviceStatistics();
}
