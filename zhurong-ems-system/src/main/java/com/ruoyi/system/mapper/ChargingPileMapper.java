package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.ChargingPile;
import com.ruoyi.system.domain.vo.ChargingPileVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 充电桩Mapper接口
 */
public interface ChargingPileMapper extends BaseMapper<ChargingPile> {

    /**
     * 查询充电桩列表
     * 
     * @param chargingPile 充电桩信息
     * @return 充电桩列表
     */
    List<ChargingPileVo> selectChargingPileViewList(ChargingPile chargingPile);

    /**
     * 查询充电桩详情
     * 
     * @param pileId 充电桩ID
     * @return 充电桩详情
     */
    ChargingPileVo selectChargingPileViewById(@Param("pileId") Long pileId);

    /**
     * 获取充电桩统计信息
     * 
     * @return 统计信息
     */
    ChargingPileVo selectChargingPileStatistics();
}
