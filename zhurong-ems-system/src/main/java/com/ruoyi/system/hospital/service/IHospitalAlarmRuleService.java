package com.ruoyi.system.hospital.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.hospital.bo.HospitalAlarmRuleBo;
import com.ruoyi.system.hospital.vo.HospitalAlarmRuleVo;

import java.util.Collection;
import java.util.List;

/**
 * 医院设备报警规则 Service
 *
 * @author cpems
 */
public interface IHospitalAlarmRuleService {

    TableDataInfo<HospitalAlarmRuleVo> queryPageList(HospitalAlarmRuleBo bo, PageQuery pageQuery);

    List<HospitalAlarmRuleVo> queryList(HospitalAlarmRuleBo bo);

    HospitalAlarmRuleVo queryById(Long id);

    Boolean insertByBo(HospitalAlarmRuleBo bo);

    Boolean updateByBo(HospitalAlarmRuleBo bo);

    Boolean deleteWithValidByIds(Collection<Long> ids);
}
