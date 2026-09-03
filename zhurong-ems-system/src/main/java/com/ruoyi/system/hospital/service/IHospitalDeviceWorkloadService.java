package com.ruoyi.system.hospital.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.hospital.bo.HospitalDeviceWorkloadBo;
import com.ruoyi.system.hospital.vo.HospitalDeviceWorkloadVo;

import java.util.Collection;

/**
 * 医院设备工作量（检查量） Service 接口
 *
 * @author cpems
 */
public interface IHospitalDeviceWorkloadService {

    HospitalDeviceWorkloadVo queryById(Long id);

    TableDataInfo<HospitalDeviceWorkloadVo> queryPageList(HospitalDeviceWorkloadBo bo, PageQuery pageQuery);

    Boolean insertByBo(HospitalDeviceWorkloadBo bo);

    Boolean updateByBo(HospitalDeviceWorkloadBo bo);

    Boolean deleteWithValidByIds(Collection<Long> ids);
}
