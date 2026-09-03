package com.ruoyi.system.hospital.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.system.hospital.domain.HospitalDeviceWorkload;
import com.ruoyi.system.hospital.vo.HospitalDeviceWorkloadVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 医院设备工作量（检查量） Mapper
 *
 * @author cpems
 */
public interface HospitalDeviceWorkloadMapper extends BaseMapperPlus<HospitalDeviceWorkloadMapper, HospitalDeviceWorkload, HospitalDeviceWorkloadVo> {

    /**
     * 查询工作量列表（关联设备）
     *
     * @param deviceId 设备 ID
     * @param begin    开始日期
     * @param end      结束日期
     */
    List<HospitalDeviceWorkloadVo> selectHospitalDeviceWorkloadList(@Param("deviceId") Long deviceId,
                                                                   @Param("begin") Date begin,
                                                                   @Param("end") Date end);

    /**
     * 按设备汇总周期内工作量
     *
     * @param deviceIds 设备 ID 集合（为空表示全部）
     * @param begin     开始日期
     * @param end       结束日期
     * @return deviceId / workload
     */
    List<Map<String, Object>> sumWorkloadByDevice(@Param("deviceIds") List<Long> deviceIds,
                                                  @Param("begin") Date begin,
                                                  @Param("end") Date end);
}
