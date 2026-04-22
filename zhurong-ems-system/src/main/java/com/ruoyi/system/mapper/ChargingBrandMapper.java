package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.ChargingBrand;
import com.ruoyi.system.domain.vo.ChargingBrandVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 充电站品牌Mapper接口
 */
public interface ChargingBrandMapper extends BaseMapper<ChargingBrand> {

    /**
     * 查询品牌列表
     * 
     * @param chargingBrand 品牌信息
     * @return 品牌列表
     */
    List<ChargingBrandVo> selectChargingBrandViewList(ChargingBrand chargingBrand);

    /**
     * 查询品牌详情
     * 
     * @param id 品牌ID
     * @return 品牌详情
     */
    ChargingBrandVo selectChargingBrandViewById(@Param("id") Long id);

    /**
     * 获取品牌统计信息
     * 
     * @return 统计信息
     */
    ChargingBrandVo selectChargingBrandStatistics();

}
