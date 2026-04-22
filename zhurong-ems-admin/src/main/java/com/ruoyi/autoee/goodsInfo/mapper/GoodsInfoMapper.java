package com.ruoyi.autoee.goodsInfo.mapper;

import java.util.List;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.autoee.goodsInfo.domain.GoodsInfo;

/**
 * 物品信息管理Mapper接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface GoodsInfoMapper extends BaseMapperPlus<GoodsInfoMapper, GoodsInfo, GoodsInfo>
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
     * 精确查询物品信息管理列表：前主要用于校验，只能进行精确查询
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
     * 批量新增修改物品信息管理
     *
     * @param goodsInfo 物品信息管理
     * @return 结果
     */
    public void batchInsertOrUpdateByGoodsInfo(List<GoodsInfo> goodsInfos);

    /**
     * 新增物品信息管理
     *
     * @param goodsInfo 物品信息管理
     * @return 结果
     */
    public int insertGoodsInfo(GoodsInfo goodsInfo);

    /**
     * 修改物品信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param goodsInfo 物品信息管理
     * @return 结果
     */
    public int updateNullValueByGoodsInfo(GoodsInfo goodsInfo);

	/**
     *  更新设置值的字段，未设置值的字段不进行更新
     *
     * @param goodsInfo 物品信息管理
     * @return 结果
     */
    public int updateNotNullValueByGoodsInfo(GoodsInfo goodsInfo);

    /**
     * 删除物品信息管理
     *
     * @param id 物品信息管理主键
     * @return 结果
     */
    public int deleteGoodsInfoById(GoodsInfo goodsInfo);

    /**
     * 批量删除物品信息管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsInfoByIds(GoodsInfo goodsInfo);

    /**
     * 批量删除ByEqGoodsInfo
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


}
