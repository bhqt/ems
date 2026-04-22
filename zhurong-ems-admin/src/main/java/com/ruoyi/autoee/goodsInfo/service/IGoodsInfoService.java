package com.ruoyi.autoee.goodsInfo.service;

import com.ruoyi.common.core.domain.AjaxResult;
import java.util.List;
import com.ruoyi.autoee.goodsInfo.domain.GoodsInfo;

/**
 * 物品信息管理Service接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface IGoodsInfoService
{
    /**
     * 查询物品信息管理
     *
     * @param id 物品信息管理主键
     * @return 物品信息管理
     */
    public GoodsInfo selectDataByPkGoodsInfo(Long id);

    /**
     * 查询物品信息管理详细信息
     *
     * @param id 物品信息管理主键
     * @return 物品信息管理
     */
    public GoodsInfo selectDetailByPkGoodsInfo(Long id);

    /**
     * 查询物品信息管理列表
     *
     * @param goodsInfo 物品信息管理
     * @return 物品信息管理集合
     */
    public List<GoodsInfo> selectDataListByLikeGoodsInfo(GoodsInfo goodsInfo);

    /**
     * 精确查询物品信息管理列表
     *
     * @param goodsInfo 物品信息管理
     * @return 物品信息管理集合
     */
    public List<GoodsInfo> selectDataListByEqGoodsInfo(GoodsInfo goodsInfo);

	/**
     * 查询物品信息管理详细列表
     *
     * @param goodsInfo 物品信息管理
     * @return 物品信息管理集合
     */
    public List<GoodsInfo> selectDetailListByLikeGoodsInfo(GoodsInfo goodsInfo);

    /**
     * 精确查询物品信息管理详细列表
     *
     * @param goodsInfo 物品信息管理
     * @return 物品信息管理集合
     */
    public List<GoodsInfo> selectDetailListByEqGoodsInfo(GoodsInfo goodsInfo);

	/**
     * 导出物品信息管理详细列表
     *
     * @param goodsInfo 物品信息管理
     * @return 物品信息管理集合
     */
	public List<GoodsInfo> selectExportDetailListGoodsInfo(GoodsInfo goodsInfo);

	/**
     * 模糊查询记录数
     *
     * @param goodsInfo 物品信息管理
     * @return 物品信息管理集合
     */
    public int selectCountByLikeGoodsInfo(GoodsInfo goodsInfo);

	/**
     * 精确查询记录数
     *
     * @param goodsInfo 物品信息管理
     * @return 物品信息管理集合
     */
    public int selectCountByEqGoodsInfo(GoodsInfo goodsInfo);

	/**
     * 导出前校验
     */
    public void exportDataCheck(GoodsInfo goodsInfo);

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(GoodsInfo goodsInfo, List<GoodsInfo> list);

    /**
     * 新增物品信息管理
     *
     * @param goodsInfo 物品信息管理
     * @return 结果
     */
    public int insertGoodsInfo(GoodsInfo goodsInfo);

    /**
     * 批量新增修改物品信息管理
     *
     * @param goodsInfo 物品信息管理
     * @return 结果
     */
    public void batchInsertOrUpdateByGoodsInfo(List<GoodsInfo> goodsInfos);

    /**
     * 修改物品信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param goodsInfo 物品信息管理
     * @return 结果
     */
    public int updateNullValueByGoodsInfo(GoodsInfo goodsInfo);

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param goodsInfo 物品信息管理
     * @return 结果
     */
    public int updateNotNullValueByGoodsInfo(GoodsInfo goodsInfo);

    /**
     * 删除物品信息管理ById
     *
     * @param id 物品信息管理主键
     * @return 结果
     */
    public int deleteGoodsInfoById(GoodsInfo goodsInfo);

    /**
     * 批量删除GoodsInfoByIds
     *
     * @param ids 需要删除的物品信息管理主键集合
     * @return 结果
     */
	public int deleteGoodsInfoByIds(GoodsInfo goodsInfo);
    
    /**
     * 批量删除GoodsInfoByEqGoodsInfo
     *
     * @return 结果
     */
    public int deleteGoodsInfoByEqGoodsInfo(GoodsInfo goodsInfo);

	/**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deleteGoodsInfoAllData();

	   /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importGoodsInfoData(List<GoodsInfo> dataList, Boolean isUpdateSupport, String operName, GoodsInfo goodsInfo);


}
