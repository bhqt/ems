package com.ruoyi.autoee.goodsStockOut.mapper;

import java.util.List;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.autoee.goodsStockOut.domain.GoodsStockOut;

/**
 * 物品出库记录Mapper接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface GoodsStockOutMapper extends BaseMapperPlus<GoodsStockOutMapper, GoodsStockOut, GoodsStockOut>
{
    /**
     * 查询物品出库记录
     *
     * @param id 物品出库记录主键
     * @return 物品出库记录
     */
    public GoodsStockOut selectDataByPkGoodsStockOut(Long id);

	/**
     * 查询物品出库记录详细信息
     *
     * @param id 物品出库记录主键
     * @return 物品出库记录
     */
    public GoodsStockOut selectDetailByPkGoodsStockOut(Long id);

    /**
     * 查询物品出库记录列表
     *
     * @param goodsStockOut 物品出库记录
     * @return 物品出库记录集合
     */
    public List<GoodsStockOut> selectDataListByLikeGoodsStockOut(GoodsStockOut goodsStockOut);

	/**
     * 精确查询物品出库记录列表：前主要用于校验，只能进行精确查询
     *
     * @param goodsStockOut 物品出库记录
     * @return 物品出库记录集合
     */
    public List<GoodsStockOut> selectDataListByEqGoodsStockOut(GoodsStockOut goodsStockOut);

	    /**
     * 查询物品出库记录详细列表
     *
     * @param goodsStockOut 物品出库记录
     * @return 物品出库记录集合
     */
    public List<GoodsStockOut> selectDetailListByLikeGoodsStockOut(GoodsStockOut goodsStockOut);

	/**
     * 精确查询物品出库记录详细列表
     *
     * @param goodsStockOut 物品出库记录
     * @return 物品出库记录集合
     */
    public List<GoodsStockOut> selectDetailListByEqGoodsStockOut(GoodsStockOut goodsStockOut);

	/**
     * 模糊查询记录数
     *
     * @param goodsStockOut 物品出库记录
     * @return 物品出库记录集合
     */
    public int selectCountByLikeGoodsStockOut(GoodsStockOut goodsStockOut);

	/**
     * 精确查询记录数
     *
     * @param goodsStockOut 物品出库记录
     * @return 物品出库记录集合
     */
    public int selectCountByEqGoodsStockOut(GoodsStockOut goodsStockOut);

    /**
     * 批量新增修改物品出库记录
     *
     * @param goodsStockOut 物品出库记录
     * @return 结果
     */
    public void batchInsertOrUpdateByGoodsStockOut(List<GoodsStockOut> goodsStockOuts);

    /**
     * 新增物品出库记录
     *
     * @param goodsStockOut 物品出库记录
     * @return 结果
     */
    public int insertGoodsStockOut(GoodsStockOut goodsStockOut);

    /**
     * 修改物品出库记录：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param goodsStockOut 物品出库记录
     * @return 结果
     */
    public int updateNullValueByGoodsStockOut(GoodsStockOut goodsStockOut);

	/**
     *  更新设置值的字段，未设置值的字段不进行更新
     *
     * @param goodsStockOut 物品出库记录
     * @return 结果
     */
    public int updateNotNullValueByGoodsStockOut(GoodsStockOut goodsStockOut);

    /**
     * 删除物品出库记录
     *
     * @param id 物品出库记录主键
     * @return 结果
     */
    public int deleteGoodsStockOutById(GoodsStockOut goodsStockOut);

    /**
     * 批量删除物品出库记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsStockOutByIds(GoodsStockOut goodsStockOut);

    /**
     * 批量删除ByEqGoodsStockOut
     *
     * @return 结果
     */
    public int deleteGoodsStockOutByEqGoodsStockOut(GoodsStockOut goodsStockOut);

    /**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deleteGoodsStockOutAllData();


}
