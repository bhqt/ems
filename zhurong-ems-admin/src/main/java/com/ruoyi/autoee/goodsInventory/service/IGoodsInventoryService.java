package com.ruoyi.autoee.goodsInventory.service;

import com.ruoyi.common.core.domain.AjaxResult;
import java.util.List;
import com.ruoyi.autoee.goodsInventory.domain.GoodsInventory;

/**
 * 物品库存Service接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface IGoodsInventoryService
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
     * 精确查询物品库存列表
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
     * 导出物品库存详细列表
     *
     * @param goodsInventory 物品库存
     * @return 物品库存集合
     */
	public List<GoodsInventory> selectExportDetailListGoodsInventory(GoodsInventory goodsInventory);

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
     * 导出前校验
     */
    public void exportDataCheck(GoodsInventory goodsInventory);

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(GoodsInventory goodsInventory, List<GoodsInventory> list);

    /**
     * 新增物品库存
     *
     * @param goodsInventory 物品库存
     * @return 结果
     */
    public int insertGoodsInventory(GoodsInventory goodsInventory);

    /**
     * 批量新增修改物品库存
     *
     * @param goodsInventory 物品库存
     * @return 结果
     */
    public void batchInsertOrUpdateByGoodsInventory(List<GoodsInventory> goodsInventorys);

    /**
     * 修改物品库存：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param goodsInventory 物品库存
     * @return 结果
     */
    public int updateNullValueByGoodsInventory(GoodsInventory goodsInventory);

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param goodsInventory 物品库存
     * @return 结果
     */
    public int updateNotNullValueByGoodsInventory(GoodsInventory goodsInventory);

    /**
     * 删除物品库存ById
     *
     * @param id 物品库存主键
     * @return 结果
     */
    public int deleteGoodsInventoryById(GoodsInventory goodsInventory);

    /**
     * 批量删除GoodsInventoryByIds
     *
     * @param ids 需要删除的物品库存主键集合
     * @return 结果
     */
	public int deleteGoodsInventoryByIds(GoodsInventory goodsInventory);
    
    /**
     * 批量删除GoodsInventoryByEqGoodsInventory
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

	   /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importGoodsInventoryData(List<GoodsInventory> dataList, Boolean isUpdateSupport, String operName, GoodsInventory goodsInventory);


}
