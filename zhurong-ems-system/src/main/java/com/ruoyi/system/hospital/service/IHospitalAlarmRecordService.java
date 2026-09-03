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
     * 确认报警（待处理 → 已确认）
     */
    Boolean confirm(Long id, String handleBy);

    /**
     * 标记处理中（已确认 → 处理中）
     */
    Boolean process(Long id, String handleBy);

    /**
     * 处理/关闭报警记录（→ 已处理，status 置为已结束）
     *
     * @param id           记录 ID
     * @param handleRemark 处理说明
     * @param handleBy     处理人
     * @return 是否成功
     */
    Boolean handle(Long id, String handleRemark, String handleBy);

    /**
     * 处理阶段闭环（确认/处理中/处理完成统一入口）
     *
     * @param action       confirm/process/done
     * @param id           记录 ID
     * @param handleRemark 处理说明
     * @param handleBy     处理人
     */
    Boolean doAction(String action, Long id, String handleRemark, String handleBy);
}
