package com.ruoyi.system.hospital.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.hospital.bo.HospitalAreaBo;
import com.ruoyi.system.hospital.vo.HospitalAreaVo;

import java.util.Collection;
import java.util.List;

/**
 * 医院院区 Service 接口
 *
 * @author cpems
 */
public interface IHospitalAreaService {

    HospitalAreaVo queryById(Long id);

    TableDataInfo<HospitalAreaVo> queryPageList(HospitalAreaBo bo, PageQuery pageQuery);

    List<HospitalAreaVo> queryList(HospitalAreaBo bo);

    Boolean insertByBo(HospitalAreaBo bo);

    Boolean updateByBo(HospitalAreaBo bo);

    Boolean deleteWithValidByIds(Collection<Long> ids);
}
