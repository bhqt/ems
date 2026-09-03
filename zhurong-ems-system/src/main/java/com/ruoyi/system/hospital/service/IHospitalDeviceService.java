package com.ruoyi.system.hospital.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.hospital.bo.HospitalDeviceBo;
import com.ruoyi.system.hospital.vo.HospitalDeviceVo;

import java.util.Collection;
import java.util.List;

/**
 * 医院检查检验设备台账 Service
 *
 * @author cpems
 */
public interface IHospitalDeviceService {

    /**
     * 查询设备信息
     *
     * @param id 主键
     * @return 设备信息
     */
    HospitalDeviceVo queryById(Long id);

    /**
     * 分页查询设备信息
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 分页结果
     */
    TableDataInfo<HospitalDeviceVo> queryPageList(HospitalDeviceBo bo, PageQuery pageQuery);

    /**
     * 查询设备信息列表
     *
     * @param bo 查询条件
     * @return 设备列表
     */
    List<HospitalDeviceVo> queryList(HospitalDeviceBo bo);

    /**
     * 新增设备
     *
     * @param bo 设备信息
     * @return 是否成功
     */
    Boolean insertByBo(HospitalDeviceBo bo);

    /**
     * 修改设备
     *
     * @param bo 设备信息
     * @return 是否成功
     */
    Boolean updateByBo(HospitalDeviceBo bo);

    /**
     * 批量删除设备
     *
     * @param ids 主键集合
     * @return 是否成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids);

    /**
     * 绑定/解绑 IOT 平台设备
     *
     * @param id          设备主键
     * @param iotDeviceId IOT 设备 ID（空则解绑）
     * @return 是否成功
     */
    Boolean bindIotDevice(Long id, String iotDeviceId);

    /**
     * 根据 IOT 设备 ID 查询本系统设备 ID
     *
     * @param iotDeviceId IOT 设备 ID
     * @return 本系统设备 ID
     */
    Long queryDeviceIdByIotDeviceId(String iotDeviceId);
}
