package com.ruoyi.autoee.goodsStockIn.mapper;

import java.util.List;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.autoee.goodsStockIn.domain.GoodsStockIn;

/**
 * 物品入库记录Mapper接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface GoodsStockInMapper extends BaseMapperPlus<GoodsStockInMapper, GoodsStockIn, GoodsStockIn>
{
    /**
     * 查询物品入库记录
     *
     * @param id 物品入库记录主键
     * @return 物品入库记录
     */
    public GoodsStockIn selectDataByPkGoodsStockIn(Long id);

	/**
     * 查询物品入库记录详细信息
     *
     * @param id 物品入库记录主键
     * @return 物品入库记录
     */
    public GoodsStockIn selectDetailByPkGoodsStockIn(Long id);

    /**
     * 查询物品入库记录列表
     *
     * @param goodsStockIn 物品入库记录
     * @return 物品入库记录集合
     */
    public List<GoodsStockIn> selectDataListByLikeGoodsStockIn(GoodsStockIn goodsStockIn);

	/**
     * 精确查询物品入库记录列表：前主要用于校验，只能进行精确查询
     *
     * @param goodsStockIn 物品入库记录
     * @return 物品入库记录集合
     */
    public List<GoodsStockIn> selectDataListByEqGoodsStockIn(GoodsStockIn goodsStockIn);

	    /**
     * 查询物品入库记录详细列表
     *
     * @param goodsStockIn 物品入库记录
     * @return 物品入库记录集合
     */
    public List<GoodsStockIn> selectDetailListByLikeGoodsStockIn(GoodsStockIn goodsStockIn);

	/**
     * 精确查询物品入库记录详细列表
     *
     * @param goodsStockIn 物品入库记录
     * @return 物品入库记录集合
     */
    public List<GoodsStockIn> selectDetailListByEqGoodsStockIn(GoodsStockIn goodsStockIn);

	/**
     * 模糊查询记录数
     *
     * @param goodsStockIn 物品入库记录
     * @return 物品入库记录集合
     */
    public int selectCountByLikeGoodsStockIn(GoodsStockIn goodsStockIn);

	/**
     * 精确查询记录数
     *
     * @param goodsStockIn 物品入库记录
     * @return 物品入库记录集合
     */
    public int selectCountByEqGoodsStockIn(GoodsStockIn goodsStockIn);

    /**
     * 批量新增修改物品入库记录
     *
     * @param goodsStockIn 物品入库记录
     * @return 结果
     */
    public void batchInsertOrUpdateByGoodsStockIn(List<GoodsStockIn> goodsStockIns);

    /**
     * 新增物品入库记录
     *
     * @param goodsStockIn 物品入库记录
     * @return 结果
     */
    public int insertGoodsStockIn(GoodsStockIn goodsStockIn);

    /**
     * 修改物品入库记录：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param goodsStockIn 物品入库记录
     * @return 结果
     */
    public int updateNullValueByGoodsStockIn(GoodsStockIn goodsStockIn);

	/**
     *  更新设置值的字段，未设置值的字段不进行更新
     *
     * @param goodsStockIn 物品入库记录
     * @return 结果
     */
    public int updateNotNullValueByGoodsStockIn(GoodsStockIn goodsStockIn);

    /**
     * 删除物品入库记录
     *
     * @param id 物品入库记录主键
     * @return 结果
     */
    public int deleteGoodsStockInById(GoodsStockIn goodsStockIn);

    /**
     * 批量删除物品入库记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsStockInByIds(GoodsStockIn goodsStockIn);

    /**
     * 批量删除ByEqGoodsStockIn
     *
     * @return 结果
     */
    public int deleteGoodsStockInByEqGoodsStockIn(GoodsStockIn goodsStockIn);

    /**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deleteGoodsStockInAllData();


}
