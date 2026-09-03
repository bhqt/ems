package com.ruoyi.system.hospital.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.hospital.bo.HospitalCallbackLogBo;
import com.ruoyi.system.hospital.domain.HospitalCallbackLog;
import com.ruoyi.system.hospital.vo.HospitalCallbackLogVo;

/**
 * 医院 IOT 回调日志 Service
 *
 * @author cpems
 */
public interface IHospitalCallbackLogService {

    /**
     * 分页查询回调日志
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 分页结果
     */
    TableDataInfo<HospitalCallbackLogVo> queryPageList(HospitalCallbackLogBo bo, PageQuery pageQuery);

    /**
     * 新增回调日志
     *
     * @param log 回调日志
     * @return 主键
     */
    Long insertLog(HospitalCallbackLog log);

    /**
     * 更新回调日志
     *
     * @param log 回调日志
     */
    void updateLog(HospitalCallbackLog log);
}
