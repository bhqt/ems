package com.ruoyi.autoee.goodsInventory.mapper;

import java.util.List;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.autoee.goodsInventory.domain.GoodsInventory;

/**
 * 物品库存Mapper接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface GoodsInventoryMapper extends BaseMapperPlus<GoodsInventoryMapper, GoodsInventory, GoodsInventory>
{
    /**
     * 查询物品库存
     *
     * @param id 物品库存主键
     * @return 物品库存
     */
    public GoodsInventory selectDataByPkGoodsInventory(Long id);

	/**
     * 查询物品库存详细信息
     *
     * @param id 物品库存主键
     * @return 物品库存
     */
    public GoodsInventory selectDetailByPkGoodsInventory(Long id);

    /**
     * 查询物品库存列表
     *
     * @param goodsInventory 物品库存
     * @return 物品库存集合
     */
    public List<GoodsInventory> selectDataListByLikeGoodsInventory(GoodsInventory goodsInventory);

	/**
     * 精确查询物品库存列表：前主要用于校验，只能进行精确查询
     *
     * @param goodsInventory 物品库存
     * @return 物品库存集合
     */
    public List<GoodsInventory> selectDataListByEqGoodsInventory(GoodsInventory goodsInventory);

	    /**
     * 查询物品库存详细列表
     *
     * @param goodsInventory 物品库存
     * @return 物品库存集合
     */
    public List<GoodsInventory> selectDetailListByLikeGoodsInventory(GoodsInventory goodsInventory);

	/**
     * 精确查询物品库存详细列表
     *
     * @param goodsInventory 物品库存
     * @return 物品库存集合
     */
    public List<GoodsInventory> selectDetailListByEqGoodsInventory(GoodsInventory goodsInventory);

	/**
     * 模糊查询记录数
     *
     * @param goodsInventory 物品库存
     * @return 物品库存集合
     */
    public int selectCountByLikeGoodsInventory(GoodsInventory goodsInventory);

	/**
     * 精确查询记录数
     *
     * @param goodsInventory 物品库存
     * @return 物品库存集合
     */
    public int selectCountByEqGoodsInventory(GoodsInventory goodsInventory);

    /**
     * 批量新增修改物品库存
     *
     * @param goodsInventory 物品库存
     * @return 结果
     */
    public void batchInsertOrUpdateByGoodsInventory(List<GoodsInventory> goodsInventorys);

    /**
     * 新增物品库存
     *
     * @param goodsInventory 物品库存
     * @return 结果
     */
    public int insertGoodsInventory(GoodsInventory goodsInventory);

    /**
     * 修改物品库存：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param goodsInventory 物品库存
     * @return 结果
     */
    public int updateNullValueByGoodsInventory(GoodsInventory goodsInventory);

	/**
     *  更新设置值的字段，未设置值的字段不进行更新
     *
     * @param goodsInventory 物品库存
     * @return 结果
     */
    public int updateNotNullValueByGoodsInventory(GoodsInventory goodsInventory);

    /**
     * 删除物品库存
     *
     * @param id 物品库存主键
     * @return 结果
     */
    public int deleteGoodsInventoryById(GoodsInventory goodsInventory);

    /**
     * 批量删除物品库存
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsInventoryByIds(GoodsInventory goodsInventory);

    /**
     * 批量删除ByEqGoodsInventory
     *
     * @return 结果
     */
    public int deleteGoodsInventoryByEqGoodsInventory(GoodsInventory goodsInventory);

    /**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deleteGoodsInventoryAllData();


}
