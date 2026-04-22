package com.ruoyi.autoee.dangerGoodsInventory.mapper;

import java.util.List;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.autoee.dangerGoodsInventory.domain.DangerGoodsInventory;

/**
 * 危化品库存Mapper接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface DangerGoodsInventoryMapper extends BaseMapperPlus<DangerGoodsInventoryMapper, DangerGoodsInventory, DangerGoodsInventory>
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
     * 精确查询危化品库存列表：前主要用于校验，只能进行精确查询
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
     * 批量新增修改危化品库存
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 结果
     */
    public void batchInsertOrUpdateByDangerGoodsInventory(List<DangerGoodsInventory> dangerGoodsInventorys);

    /**
     * 新增危化品库存
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 结果
     */
    public int insertDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory);

    /**
     * 修改危化品库存：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 结果
     */
    public int updateNullValueByDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory);

	/**
     *  更新设置值的字段，未设置值的字段不进行更新
     *
     * @param dangerGoodsInventory 危化品库存
     * @return 结果
     */
    public int updateNotNullValueByDangerGoodsInventory(DangerGoodsInventory dangerGoodsInventory);

    /**
     * 删除危化品库存
     *
     * @param id 危化品库存主键
     * @return 结果
     */
    public int deleteDangerGoodsInventoryById(DangerGoodsInventory dangerGoodsInventory);

    /**
     * 批量删除危化品库存
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDangerGoodsInventoryByIds(DangerGoodsInventory dangerGoodsInventory);

    /**
     * 批量删除ByEqDangerGoodsInventory
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


}
