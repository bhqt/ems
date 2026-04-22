package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.system.domain.ChargingPile;
import com.ruoyi.system.domain.bo.ChargingPileBo;
import com.ruoyi.system.domain.vo.ChargingPileVo;

import java.util.List;

/**
 * 充电桩Service接口
 */
public interface IChargingPileService extends IService<ChargingPile> {

    /**
     * 查询充电桩列表
     * 
     * @param chargingPile 充电桩信息
     * @param pageQuery 分页参数
     * @return 充电桩分页列表
     */
    IPage<ChargingPileVo> selectChargingPilePage(ChargingPile chargingPile, PageQuery pageQuery);

    /**
     * 查询充电桩详情
     * 
     * @param pileId 充电桩ID
     * @return 充电桩详情
     */
    ChargingPileVo selectChargingPileById(Long pileId);

    /**
     * 新增充电桩
     * 
     * @param bo 充电桩信息
     * @return 结果
     */
    int insertChargingPile(ChargingPileBo bo);

    /**
     * 修改充电桩
     * 
     * @param bo 充电桩信息
     * @return 结果
     */
    int updateChargingPile(ChargingPileBo bo);

    /**
     * 删除充电桩
     * 
     * @param pileIds 充电桩ID列表
     * @return 结果
     */
    int deleteChargingPileByIds(Long[] pileIds);

    /**
     * 启用/停用充电桩
     * 
     * @param pileId 充电桩ID
     * @param status 状态
     * @return 结果
     */
    int openOrClosePile(Long pileId, String status);

    /**
     * 获取充电桩统计信息
     * 
     * @return 统计信息
     */
    ChargingPileVo getChargingPileStatistics();

    /**
     * 导出充电桩列表
     * 
     * @param chargingPile 充电桩信息
     * @return 充电桩列表
     */
    List<ChargingPileVo> exportChargingPileList(ChargingPile chargingPile);
}
