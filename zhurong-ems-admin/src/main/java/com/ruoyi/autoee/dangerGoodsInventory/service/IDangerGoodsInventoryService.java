package com.ruoyi.autoee.dangerGoodsInventory.service;

import com.ruoyi.common.core.domain.AjaxResult;
import java.util.List;
import com.ruoyi.autoee.dangerGoodsInventory.domain.DangerGoodsInventory;

/**
 * 危化品库存Service接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface IDangerGoodsInventoryService
{
    /**
     * 查询危化品库存
     *
     * @param id 危化品库存主键
     * @return 危化品库存
     */
    public DangerGoodsInventory selectDataByPkDangerGoodsInventory(Long id);

    /**
     * 查询危化品库存详细信息
     *
     * @param id 危化品库存主键
     * @return 危化品库存
     */
    public DangerGoodsInventory selectDetailByPkDangerGoodsInventory(Long id);

    /**
     * 查询危化品库存列表
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 危化品库存集合
     */
    public List<DangerGoodsInventory> selectDataListByLikeDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory);

    /**
     * 精确查询危化品库存列表
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 危化品库存集合
     */
    public List<DangerGoodsInventory> selectDataListByEqDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory);

	/**
     * 查询危化品库存详细列表
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 危化品库存集合
     */
    public List<DangerGoodsInventory> selectDetailListByLikeDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory);

    /**
     * 精确查询危化品库存详细列表
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 危化品库存集合
     */
    public List<DangerGoodsInventory> selectDetailListByEqDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory);

	/**
     * 导出危化品库存详细列表
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 危化品库存集合
     */
	public List<DangerGoodsInventory> selectExportDetailListDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory);

	/**
     * 模糊查询记录数
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 危化品库存集合
     */
    public int selectCountByLikeDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory);

	/**
     * 精确查询记录数
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 危化品库存集合
     */
    public int selectCountByEqDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory);

	/**
     * 导出前校验
     */
    public void exportDataCheck(DangerGoodsInventory dangerGoodsInventory);

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(DangerGoodsInventory dangerGoodsInventory, List<DangerGoodsInventory> list);

    /**
     * 新增危化品库存
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 结果
     */
    public int insertDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory);

    /**
     * 批量新增修改危化品库存
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 结果
     */
    public void batchInsertOrUpdateByDangerGoodsInventory(List<DangerGoodsInventory> dangerGoodsInventorys);

    /**
     * 修改危化品库存：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 结果
     */
    public int updateNullValueByDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory);

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 结果
     */
    public int updateNotNullValueByDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory);

    /**
     * 删除危化品库存ById
     *
     * @param id 危化品库存主键
     * @return 结果
     */
    public int deleteDangerGoodsInventoryById(DangerGoodsInventory dangerGoodsInventory);

    /**
     * 批量删除DangerGoodsInventoryByIds
     *
     * @param ids 需要删除的危化品库存主键集合
     * @return 结果
     */
	public int deleteDangerGoodsInventoryByIds(DangerGoodsInventory dangerGoodsInventory);
    
    /**
     * 批量删除DangerGoodsInventoryByEqDangerGoodsInventory
     *
     * @return 结果
     */
    public int deleteDangerGoodsInventoryByEqDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory);

	/**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deleteDangerGoodsInventoryAllData();

	   /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importDangerGoodsInventoryData(List<DangerGoodsInventory> dataList, Boolean isUpdateSupport, String operName, DangerGoodsInventory dangerGoodsInventory);


}
