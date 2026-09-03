package com.ruoyi.system.hospital.mapper;

import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.system.hospital.domain.HospitalAlarmRecord;
import com.ruoyi.system.hospital.vo.HospitalAlarmRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 医院设备报警记录 Mapper
 *
 * @author cpems
 */
public interface HospitalAlarmRecordMapper extends BaseMapperPlus<HospitalAlarmRecordMapper, HospitalAlarmRecord, HospitalAlarmRecordVo> {

    /**
     * 查询报警记录列表（关联设备与规则）
     *
     * @param deviceId     设备 ID
     * @param alarmType    报警类型
     * @param status       处理状态
     * @param handleStatus 处理阶段
     * @param level        报警级别
     * @return 报警记录列表
     */
    List<HospitalAlarmRecordVo> selectHospitalAlarmRecordList(@Param("deviceId") Long deviceId,
                                                             @Param("alarmType") String alarmType,
                                                             @Param("status") String status,
                                                             @Param("handleStatus") String handleStatus,
                                                             @Param("level") String level);
}
