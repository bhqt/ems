package com.ruoyi.autoee.maintainOrder.mapper;

import java.util.List;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import com.ruoyi.autoee.maintainOrder.domain.MaintainOrder;

/**
 * 维修工单Mapper接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface MaintainOrderMapper extends BaseMapperPlus<MaintainOrderMapper, MaintainOrder, MaintainOrder>
{
    /**
     * 查询维修工单
     *
     * @param id 维修工单主键
     * @return 维修工单
     */
    public MaintainOrder selectDataByPkMaintainOrder(Long id);

	/**
     * 查询维修工单详细信息
     *
     * @param id 维修工单主键
     * @return 维修工单
     */
    public MaintainOrder selectDetailByPkMaintainOrder(Long id);

    /**
     * 查询维修工单列表
     *
     * @param maintainOrder 维修工单
     * @return 维修工单集合
     */
    public List<MaintainOrder> selectDataListByLikeMaintainOrder(MaintainOrder maintainOrder);

	/**
     * 精确查询维修工单列表：前主要用于校验，只能进行精确查询
     *
     * @param maintainOrder 维修工单
     * @return 维修工单集合
     */
    public List<MaintainOrder> selectDataListByEqMaintainOrder(MaintainOrder maintainOrder);

	    /**
     * 查询维修工单详细列表
     *
     * @param maintainOrder 维修工单
     * @return 维修工单集合
     */
    public List<MaintainOrder> selectDetailListByLikeMaintainOrder(MaintainOrder maintainOrder);

	/**
     * 精确查询维修工单详细列表
     *
     * @param maintainOrder 维修工单
     * @return 维修工单集合
     */
    public List<MaintainOrder> selectDetailListByEqMaintainOrder(MaintainOrder maintainOrder);

	/**
     * 模糊查询记录数
     *
     * @param maintainOrder 维修工单
     * @return 维修工单集合
     */
    public int selectCountByLikeMaintainOrder(MaintainOrder maintainOrder);

	/**
     * 精确查询记录数
     *
     * @param maintainOrder 维修工单
     * @return 维修工单集合
     */
    public int selectCountByEqMaintainOrder(MaintainOrder maintainOrder);

    /**
     * 批量新增修改维修工单
     *
     * @param maintainOrder 维修工单
     * @return 结果
     */
    public void batchInsertOrUpdateByMaintainOrder(List<MaintainOrder> maintainOrders);

    /**
     * 新增维修工单
     *
     * @param maintainOrder 维修工单
     * @return 结果
     */
    public int insertMaintainOrder(MaintainOrder maintainOrder);

    /**
     * 修改维修工单：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param maintainOrder 维修工单
     * @return 结果
     */
    public int updateNullValueByMaintainOrder(MaintainOrder maintainOrder);

	/**
     *  更新设置值的字段，未设置值的字段不进行更新
     *
     * @param maintainOrder 维修工单
     * @return 结果
     */
    public int updateNotNullValueByMaintainOrder(MaintainOrder maintainOrder);

    /**
     * 删除维修工单
     *
     * @param id 维修工单主键
     * @return 结果
     */
    public int deleteMaintainOrderById(MaintainOrder maintainOrder);

    /**
     * 批量删除维修工单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMaintainOrderByIds(MaintainOrder maintainOrder);

    /**
     * 批量删除ByEqMaintainOrder
     *
     * @return 结果
     */
    public int deleteMaintainOrderByEqMaintainOrder(MaintainOrder maintainOrder);

    /**
     * 删除全部数据
     *
     * @return 结果
     */
    public int deleteMaintainOrderAllData();


}
