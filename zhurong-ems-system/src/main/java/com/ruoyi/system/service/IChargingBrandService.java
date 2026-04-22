package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.system.domain.ChargingBrand;
import com.ruoyi.system.domain.bo.ChargingBrandBo;
import com.ruoyi.system.domain.vo.ChargingBrandVo;

import java.util.List;

/**
 * 充电站品牌Service接口
 */
public interface IChargingBrandService extends IService<ChargingBrand> {

    /**
     * 查询品牌列表
     * 
     * @param chargingBrand 品牌信息
     * @param pageQuery 分页参数
     * @return 品牌分页列表
     */
    IPage<ChargingBrandVo> selectChargingBrandPage(ChargingBrand chargingBrand, PageQuery pageQuery);

    /**
     * 查询品牌详情
     * 
     * @param id 品牌ID
     * @return 品牌详情
     */
    ChargingBrandVo selectChargingBrandById(Long id);

    /**
     * 新增品牌
     * 
     * @param bo 品牌信息
     * @return 结果
     */
    int insertChargingBrand(ChargingBrandBo bo);

    /**
     * 修改品牌
     * 
     * @param bo 品牌信息
     * @return 结果
     */
    int updateChargingBrand(ChargingBrandBo bo);

    /**
     * 删除品牌
     * 
     * @param ids 品牌ID列表
     * @return 结果
     */
    int deleteChargingBrandByIds(Long[] ids);

    /**
     * 获取品牌统计信息
     * 
     * @return 统计信息
     */
    ChargingBrandVo getChargingBrandStatistics();

    /**
     * 导出品牌列表
     * 
     * @param chargingBrand 品牌信息
     * @return 品牌列表
     */
    List<ChargingBrandVo> exportChargingBrandList(ChargingBrand chargingBrand);

}
