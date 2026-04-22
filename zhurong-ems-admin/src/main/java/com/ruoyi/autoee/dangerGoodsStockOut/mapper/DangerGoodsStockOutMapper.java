package com.ruoyi.autoee.dangerGoodsStockOut.mapper;

import java.util.List;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.autoee.dangerGoodsStockOut.domain.DangerGoodsStockOut;

/**
 * 危化品出库记录Mapper接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface DangerGoodsStockOutMapper extends BaseMapperPlus<DangerGoodsStockOutMapper, DangerGoodsStockOut, DangerGoodsStockOut>
{
    /**
     * 查询危化品出库记录
     *
     * @param id 危化品出库记录主键
     * @return 危化品出库记录
     */
    public DangerGoodsStockOut selectDataByPkDangerGoodsStockOut(Long id);

	/**
     * 查询危化品出库记录详细信息
     *
     * @param id 危化品出库记录主键
     * @return 危化品出库记录
     */
    public DangerGoodsStockOut selectDetailByPkDangerGoodsStockOut(Long id);

    /**
     * 查询危化品出库记录列表
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 危化品出库记录集合
     */
    public List<DangerGoodsStockOut> selectDataListByLikeDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut);

	/**
     * 精确查询危化品出库记录列表：前主要用于校验，只能进行精确查询
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 危化品出库记录集合
     */
    public List<DangerGoodsStockOut> selectDataListByEqDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut);

	    /**
     * 查询危化品出库记录详细列表
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 危化品出库记录集合
     */
    public List<DangerGoodsStockOut> selectDetailListByLikeDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut);

	/**
     * 精确查询危化品出库记录详细列表
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 危化品出库记录集合
     */
    public List<DangerGoodsStockOut> selectDetailListByEqDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut);

	/**
     * 模糊查询记录数
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 危化品出库记录集合
     */
    public int selectCountByLikeDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut);

	/**
     * 精确查询记录数
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 危化品出库记录集合
     */
    public int selectCountByEqDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut);

    /**
     * 批量新增修改危化品出库记录
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 结果
     */
    public void batchInsertOrUpdateByDangerGoodsStockOut(List<DangerGoodsStockOut> dangerGoodsStockOuts);

    /**
     * 新增危化品出库记录
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 结果
     */
    public int insertDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut);

    /**
     * 修改危化品出库记录：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 结果
     */
    public int updateNullValueByDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut);

	/**
     *  更新设置值的字段，未设置值的字段不进行更新
     *
     * @param dangerGoodsStockOut 危化品出库记录
     * @return 结果
     */
    public int updateNotNullValueByDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut);

    /**
     * 删除危化品出库记录
     *
     * @param id 危化品出库记录主键
     * @return 结果
     */
    public int deleteDangerGoodsStockOutById(DangerGoodsStockOut dangerGoodsStockOut);

    /**
     * 批量删除危化品出库记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDangerGoodsStockOutByIds(DangerGoodsStockOut dangerGoodsStockOut);

    /**
     * 批量删除ByEqDangerGoodsStockOut
     *
     * @return 结果
     */
    public int deleteDangerGoodsStockOutByEqDangerGoodsStockOut(DangerGoodsStockOut dangerGoodsStockOut);

    /**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deleteDangerGoodsStockOutAllData();


}
