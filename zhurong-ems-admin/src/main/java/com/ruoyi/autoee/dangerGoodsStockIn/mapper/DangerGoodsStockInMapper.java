package com.ruoyi.autoee.dangerGoodsStockIn.mapper;

import java.util.List;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.autoee.dangerGoodsStockIn.domain.DangerGoodsStockIn;

/**
 * 危化品入库记录Mapper接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface DangerGoodsStockInMapper extends BaseMapperPlus<DangerGoodsStockInMapper, DangerGoodsStockIn, DangerGoodsStockIn>
{
    /**
     * 查询危化品入库记录
     *
     * @param id 危化品入库记录主键
     * @return 危化品入库记录
     */
    public DangerGoodsStockIn selectDataByPkDangerGoodsStockIn(Long id);

	/**
     * 查询危化品入库记录详细信息
     *
     * @param id 危化品入库记录主键
     * @return 危化品入库记录
     */
    public DangerGoodsStockIn selectDetailByPkDangerGoodsStockIn(Long id);

    /**
     * 查询危化品入库记录列表
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 危化品入库记录集合
     */
    public List<DangerGoodsStockIn> selectDataListByLikeDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn);

	/**
     * 精确查询危化品入库记录列表：前主要用于校验，只能进行精确查询
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 危化品入库记录集合
     */
    public List<DangerGoodsStockIn> selectDataListByEqDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn);

	    /**
     * 查询危化品入库记录详细列表
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 危化品入库记录集合
     */
    public List<DangerGoodsStockIn> selectDetailListByLikeDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn);

	/**
     * 精确查询危化品入库记录详细列表
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 危化品入库记录集合
     */
    public List<DangerGoodsStockIn> selectDetailListByEqDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn);

	/**
     * 模糊查询记录数
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 危化品入库记录集合
     */
    public int selectCountByLikeDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn);

	/**
     * 精确查询记录数
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 危化品入库记录集合
     */
    public int selectCountByEqDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn);

    /**
     * 批量新增修改危化品入库记录
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 结果
     */
    public void batchInsertOrUpdateByDangerGoodsStockIn(List<DangerGoodsStockIn> dangerGoodsStockIns);

    /**
     * 新增危化品入库记录
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 结果
     */
    public int insertDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn);

    /**
     * 修改危化品入库记录：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 结果
     */
    public int updateNullValueByDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn);

	/**
     *  更新设置值的字段，未设置值的字段不进行更新
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 结果
     */
    public int updateNotNullValueByDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn);

    /**
     * 删除危化品入库记录
     *
     * @param id 危化品入库记录主键
     * @return 结果
     */
    public int deleteDangerGoodsStockInById(DangerGoodsStockIn dangerGoodsStockIn);

    /**
     * 批量删除危化品入库记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDangerGoodsStockInByIds(DangerGoodsStockIn dangerGoodsStockIn);

    /**
     * 批量删除ByEqDangerGoodsStockIn
     *
     * @return 结果
     */
    public int deleteDangerGoodsStockInByEqDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn);

    /**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deleteDangerGoodsStockInAllData();


}
