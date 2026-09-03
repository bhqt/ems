package com.ruoyi.system.hospital.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.system.hospital.domain.HospitalDeviceData;
import com.ruoyi.system.hospital.vo.HospitalDeviceDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 医院设备数据点 Mapper
 *
 * @author cpems
 */
public interface HospitalDeviceDataMapper extends BaseMapperPlus<HospitalDeviceDataMapper, HospitalDeviceData, HospitalDeviceDataVo> {

    /**
     * 查询设备数据点列表（关联设备与指标定义）
     *
     * @param deviceId   设备 ID
     * @param metricCode 指标编码
     * @param limit      条数限制
     * @return 数据点列表
     */
    List<HospitalDeviceDataVo> selectHospitalDeviceDataList(@Param("deviceId") Long deviceId,
                                                             @Param("metricCode") String metricCode,
                                                             @Param("limit") Integer limit);

    /**
     * 批量查询各设备各指标的最新数据点（监测页/离线扫描用）
     *
     * @param deviceIds 设备 ID 列表（为空查全部）
     * @return 最新数据点列表
     */
    List<HospitalDeviceData> selectLatestByDeviceIds(@Param("deviceIds") List<Long> deviceIds);

    /**
     * 批量查询各设备的最近数据时间（离线判定用）
     *
     * @param deviceIds 设备 ID 列表（为空查全部）
     * @return 每行含 deviceId/maxTs 的 Map 列表
     */
    List<Map<String, Object>> selectMaxTsByDeviceIds(@Param("deviceIds") List<Long> deviceIds);
}
