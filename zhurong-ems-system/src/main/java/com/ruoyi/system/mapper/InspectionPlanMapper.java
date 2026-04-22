package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.InspectionPlan;
import com.ruoyi.system.domain.vo.InspectionPlanVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 巡检计划Mapper接口
 *
 * @author ruoyi
 * @date 2023-04-04
 */
@Mapper
public interface InspectionPlanMapper extends BaseMapperPlus<InspectionPlanMapper, InspectionPlan, InspectionPlanVo> {

}
