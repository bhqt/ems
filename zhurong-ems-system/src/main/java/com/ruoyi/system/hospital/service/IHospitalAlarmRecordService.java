package com.ruoyi.system.hospital.service;

import com.ruoyi.system.hospital.bo.HospitalAlarmRecordBo;
import com.ruoyi.system.hospital.vo.HospitalAlarmRecordVo;

import java.util.List;

/**
 * 医院设备报警记录 Service
 *
 * @author cpems
 */
public interface IHospitalAlarmRecordService {

    /**
     * 查询报警记录列表（关联设备与规则，不分页，取最近 500 条）
     */
    List<HospitalAlarmRecordVo> queryList(HospitalAlarmRecordBo bo);

    /**
     * 处理/关闭报警记录
     *
     * @param id           记录 ID
     * @param handleRemark 处理说明
     * @param handleBy     处理人
     * @return 是否成功
     */
    Boolean handle(Long id, String handleRemark, String handleBy);
}
