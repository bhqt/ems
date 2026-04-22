package com.ruoyi.autoee.dangerGoodsInfo.mapper;

import java.util.List;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.autoee.dangerGoodsInfo.domain.DangerGoodsInfo;

/**
 * 危化品信息管理Mapper接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface DangerGoodsInfoMapper extends BaseMapperPlus<DangerGoodsInfoMapper, DangerGoodsInfo, DangerGoodsInfo>
{
    /**
     * 查询危化品信息管理
     *
     * @param id 危化品信息管理主键
     * @return 危化品信息管理
     */
    public DangerGoodsInfo selectDataByPkDangerGoodsInfo(Long id);

	/**
     * 查询危化品信息管理详细信息
     *
     * @param id 危化品信息管理主键
     * @return 危化品信息管理
     */
    public DangerGoodsInfo selectDetailByPkDangerGoodsInfo(Long id);

    /**
     * 查询危化品信息管理列表
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 危化品信息管理集合
     */
    public List<DangerGoodsInfo> selectDataListByLikeDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo);

	/**
     * 精确查询危化品信息管理列表：前主要用于校验，只能进行精确查询
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 危化品信息管理集合
     */
    public List<DangerGoodsInfo> selectDataListByEqDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo);

	    /**
     * 查询危化品信息管理详细列表
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 危化品信息管理集合
     */
    public List<DangerGoodsInfo> selectDetailListByLikeDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo);

	/**
     * 精确查询危化品信息管理详细列表
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 危化品信息管理集合
     */
    public List<DangerGoodsInfo> selectDetailListByEqDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo);

	/**
     * 模糊查询记录数
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 危化品信息管理集合
     */
    public int selectCountByLikeDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo);

	/**
     * 精确查询记录数
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 危化品信息管理集合
     */
    public int selectCountByEqDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo);

    /**
     * 批量新增修改危化品信息管理
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 结果
     */
    public void batchInsertOrUpdateByDangerGoodsInfo(List<DangerGoodsInfo> dangerGoodsInfos);

    /**
     * 新增危化品信息管理
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 结果
     */
    public int insertDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo);

    /**
     * 修改危化品信息管理：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 结果
     */
    public int updateNullValueByDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo);

	/**
     *  更新设置值的字段，未设置值的字段不进行更新
     *
     * @param dangerGoodsInfo 危化品信息管理
     * @return 结果
     */
    public int updateNotNullValueByDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo);

    /**
     * 删除危化品信息管理
     *
     * @param id 危化品信息管理主键
     * @return 结果
     */
    public int deleteDangerGoodsInfoById(DangerGoodsInfo dangerGoodsInfo);

    /**
     * 批量删除危化品信息管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDangerGoodsInfoByIds(DangerGoodsInfo dangerGoodsInfo);

    /**
     * 批量删除ByEqDangerGoodsInfo
     *
     * @return 结果
     */
    public int deleteDangerGoodsInfoByEqDangerGoodsInfo(DangerGoodsInfo dangerGoodsInfo);

    /**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deleteDangerGoodsInfoAllData();


}
