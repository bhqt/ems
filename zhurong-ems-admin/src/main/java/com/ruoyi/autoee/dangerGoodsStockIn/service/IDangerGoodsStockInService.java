package com.ruoyi.autoee.dangerGoodsStockIn.service;

import com.ruoyi.common.core.domain.AjaxResult;
import java.util.List;
import com.ruoyi.autoee.dangerGoodsStockIn.domain.DangerGoodsStockIn;

/**
 * 危化品入库记录Service接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface IDangerGoodsStockInService
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
     * 精确查询危化品入库记录列表
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
     * 导出危化品入库记录详细列表
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 危化品入库记录集合
     */
	public List<DangerGoodsStockIn> selectExportDetailListDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn);

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
     * 导出前校验
     */
    public void exportDataCheck(DangerGoodsStockIn dangerGoodsStockIn);

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(DangerGoodsStockIn dangerGoodsStockIn, List<DangerGoodsStockIn> list);

    /**
     * 新增危化品入库记录
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 结果
     */
    public int insertDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn);

    /**
     * 批量新增修改危化品入库记录
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 结果
     */
    public void batchInsertOrUpdateByDangerGoodsStockIn(List<DangerGoodsStockIn> dangerGoodsStockIns);

    /**
     * 修改危化品入库记录：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 结果
     */
    public int updateNullValueByDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn);

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param dangerGoodsStockIn 危化品入库记录
     * @return 结果
     */
    public int updateNotNullValueByDangerGoodsStockIn(DangerGoodsStockIn dangerGoodsStockIn);

    /**
     * 删除危化品入库记录ById
     *
     * @param id 危化品入库记录主键
     * @return 结果
     */
    public int deleteDangerGoodsStockInById(DangerGoodsStockIn dangerGoodsStockIn);

    /**
     * 批量删除DangerGoodsStockInByIds
     *
     * @param ids 需要删除的危化品入库记录主键集合
     * @return 结果
     */
	public int deleteDangerGoodsStockInByIds(DangerGoodsStockIn dangerGoodsStockIn);
    
    /**
     * 批量删除DangerGoodsStockInByEqDangerGoodsStockIn
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

	   /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importDangerGoodsStockInData(List<DangerGoodsStockIn> dataList, Boolean isUpdateSupport, String operName, DangerGoodsStockIn dangerGoodsStockIn);


}
