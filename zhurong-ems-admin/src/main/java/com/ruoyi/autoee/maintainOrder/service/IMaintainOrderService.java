package com.ruoyi.autoee.maintainOrder.service;

import com.ruoyi.common.core.domain.AjaxResult;
import java.util.List;
import com.ruoyi.autoee.maintainOrder.domain.MaintainOrder;

/**
 * 维修工单Service接口
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
public interface IMaintainOrderService
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
     * 精确查询维修工单列表
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
     * 导出维修工单详细列表
     *
     * @param maintainOrder 维修工单
     * @return 维修工单集合
     */
	public List<MaintainOrder> selectExportDetailListMaintainOrder(MaintainOrder maintainOrder);

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
     * 导出前校验
     */
    public void exportDataCheck(MaintainOrder maintainOrder);

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(MaintainOrder maintainOrder, List<MaintainOrder> list);

    /**
     * 新增维修工单
     *
     * @param maintainOrder 维修工单
     * @return 结果
     */
    public int insertMaintainOrder(MaintainOrder maintainOrder);

    /**
     * 批量新增修改维修工单
     *
     * @param maintainOrder 维修工单
     * @return 结果
     */
    public void batchInsertOrUpdateByMaintainOrder(List<MaintainOrder> maintainOrders);

    /**
     * 修改维修工单：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param maintainOrder 维修工单
     * @return 结果
     */
    public int updateNullValueByMaintainOrder(MaintainOrder maintainOrder);

    /**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param maintainOrder 维修工单
     * @return 结果
     */
    public int updateNotNullValueByMaintainOrder(MaintainOrder maintainOrder);

    /**
     * 删除维修工单ById
     *
     * @param id 维修工单主键
     * @return 结果
     */
    public int deleteMaintainOrderById(MaintainOrder maintainOrder);

    /**
     * 批量删除MaintainOrderByIds
     *
     * @param ids 需要删除的维修工单主键集合
     * @return 结果
     */
	public int deleteMaintainOrderByIds(MaintainOrder maintainOrder);
    
    /**
     * 批量删除MaintainOrderByEqMaintainOrder
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

	   /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importMaintainOrderData(List<MaintainOrder> dataList, Boolean isUpdateSupport, String operName, MaintainOrder maintainOrder);


}
