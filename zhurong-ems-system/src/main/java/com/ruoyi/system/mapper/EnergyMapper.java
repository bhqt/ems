package com.ruoyi.system.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.system.domain.Energy;
import com.ruoyi.system.domain.vo.EnergyVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * 能源值Mapper接口
 * @author cpems
 * @date 2023-04-21
 */
@DS("td")
public interface EnergyMapper extends BaseMapperPlus<EnergyMapper, Energy, EnergyVo> {

    @Insert("INSERT INTO #{energy.type} (ts, client_id, val) VALUES (#{energy.ts}, #{energy.clientId}, #{energy.val})")
    void insertEnergy(@Param("energy") Energy energy);
}
