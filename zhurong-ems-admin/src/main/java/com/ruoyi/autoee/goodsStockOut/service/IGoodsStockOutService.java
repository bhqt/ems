package com.ruoyi.autoee.goodsStockOut.service;

import com.ruoyi.common.core.domain.AjaxResult;
import java.util.List;
import com.ruoyi.autoee.goodsStockOut.domain.GoodsStockOut;

/**
 * 物品出库记录Service接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface IGoodsStockOutService
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
     * 精确查询物品出库记录列表
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
     * 导出物品出库记录详细列表
     *
     * @param goodsStockOut 物品出库记录
     * @return 物品出库记录集合
     */
	public List<GoodsStockOut> selectExportDetailListGoodsStockOut(GoodsStockOut goodsStockOut);

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
     * 导出前校验
     */
    public void exportDataCheck(GoodsStockOut goodsStockOut);

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(GoodsStockOut goodsStockOut, List<GoodsStockOut> list);

    /**
     * 新增物品出库记录
     *
     * @param goodsStockOut 物品出库记录
     * @return 结果
     */
    public int insertGoodsStockOut(GoodsStockOut goodsStockOut);

    /**
     * 批量新增修改物品出库记录
     *
     * @param goodsStockOut 物品出库记录
     * @return 结果
     */
    public void batchInsertOrUpdateByGoodsStockOut(List<GoodsStockOut> goodsStockOuts);

    /**
     * 修改物品出库记录：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param goodsStockOut 物品出库记录
     * @return 结果
     */
    public int updateNullValueByGoodsStockOut(GoodsStockOut goodsStockOut);

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param goodsStockOut 物品出库记录
     * @return 结果
     */
    public int updateNotNullValueByGoodsStockOut(GoodsStockOut goodsStockOut);

    /**
     * 删除物品出库记录ById
     *
     * @param id 物品出库记录主键
     * @return 结果
     */
    public int deleteGoodsStockOutById(GoodsStockOut goodsStockOut);

    /**
     * 批量删除GoodsStockOutByIds
     *
     * @param ids 需要删除的物品出库记录主键集合
     * @return 结果
     */
	public int deleteGoodsStockOutByIds(GoodsStockOut goodsStockOut);
    
    /**
     * 批量删除GoodsStockOutByEqGoodsStockOut
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

	   /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importGoodsStockOutData(List<GoodsStockOut> dataList, Boolean isUpdateSupport, String operName, GoodsStockOut goodsStockOut);


}
