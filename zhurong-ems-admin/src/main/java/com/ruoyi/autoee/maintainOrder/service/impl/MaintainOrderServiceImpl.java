package com.ruoyi.autoee.maintainOrder.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import javax.validation.Validator;
import com.ruoyi.common.utils.bean.BeanValidators;
import org.springframework.dao.DataIntegrityViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import com.ruoyi.common.exception.ServiceException;
import java.util.List;
import java.util.HashMap;
import java.util.Date;
import com.ruoyi.common.utils.StringUtils;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.service.CommonService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.dao.DuplicateKeyException;
import com.ruoyi.autoee.maintainOrder.mapper.MaintainOrderMapper;
import com.ruoyi.autoee.maintainOrder.mapper.MaintainOrderMapperExtend;
import com.ruoyi.autoee.maintainOrder.domain.MaintainOrder;
import com.ruoyi.autoee.maintainOrder.service.IMaintainOrderService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 维修工单Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class MaintainOrderServiceImpl implements IMaintainOrderService
{
	private static final Logger logger = LoggerFactory.getLogger(MaintainOrderServiceImpl.class);
    @Autowired
    private MaintainOrderMapper maintainOrderMapper;
    @Autowired
    private MaintainOrderMapperExtend maintainOrderMapperExtend;
    @Autowired
    private MaintainOrderServiceExtend maintainOrderServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询维修工单
     *
     * @param id 维修工单主键
     * @return 维修工单
     */
    @Override
    public MaintainOrder selectDataByPkMaintainOrder(Long id)
    {
        return maintainOrderMapper.selectDataByPkMaintainOrder(id);
    }

    /**
     * 通过主键查询维修工单详细信息
     *
     * @param id 维修工单主键
     * @return 维修工单
     */
    @Override
    public MaintainOrder selectDetailByPkMaintainOrder(Long id)
    {
		MaintainOrder maintainOrder = maintainOrderMapper.selectDetailByPkMaintainOrder(id);
		maintainOrderServiceExtend.selectDetailByPkMaintainOrderEndExtend(maintainOrder);
        return maintainOrder;
    }

    /**
     * 查询维修工单列表
     *
     * @param maintainOrder 维修工单
     * @return 维修工单
     */
    @Override
    public List<MaintainOrder> selectDataListByLikeMaintainOrder(MaintainOrder maintainOrder)
    {
		maintainOrderServiceExtend.selectListStartExtend(maintainOrder);
		List<MaintainOrder> list = maintainOrderMapper.selectDataListByLikeMaintainOrder(maintainOrder);
		maintainOrderServiceExtend.selectListEndExtend(maintainOrder, list);
        return list;
    }

    /**
     * 精确查询维修工单列表
     *
     * @param maintainOrder 维修工单
     * @return 维修工单集合
     */
    public List<MaintainOrder> selectDataListByEqMaintainOrder(MaintainOrder maintainOrder)
	{
		maintainOrderServiceExtend.selectListStartExtend(maintainOrder);
		List<MaintainOrder> list = maintainOrderMapper.selectDataListByEqMaintainOrder(maintainOrder);
		maintainOrderServiceExtend.selectListEndExtend(maintainOrder, list);
        return list;
    }

	/**
     * 查询维修工单详细列表
     *
     * @param maintainOrder 维修工单
     * @return 维修工单集合
     */
    public List<MaintainOrder> selectDetailListByLikeMaintainOrder(MaintainOrder maintainOrder)
	{
		maintainOrderServiceExtend.selectDetailListStartExtend(maintainOrder);
		List<MaintainOrder> list = maintainOrderMapper.selectDetailListByLikeMaintainOrder(maintainOrder);
		maintainOrderServiceExtend.selectDetailListEndExtend(maintainOrder, list);
        return list;
    }

    /**
     * 精确查询维修工单详细列表
     *
     * @param maintainOrder 维修工单
     * @return 维修工单集合
     */
    public List<MaintainOrder> selectDetailListByEqMaintainOrder(MaintainOrder maintainOrder)
	{
		maintainOrderServiceExtend.selectDetailListByEqMaintainOrderStartExtend(maintainOrder);
		List<MaintainOrder> list = maintainOrderMapper.selectDetailListByEqMaintainOrder(maintainOrder);
		maintainOrderServiceExtend.selectDetailListByEqMaintainOrderEndExtend(maintainOrder, list);
        return list;
    }

	/**
     * 导出维修工单详细列表
     *
     * @param maintainOrder 维修工单
     * @return 维修工单集合
     */
	public List<MaintainOrder> selectExportDetailListMaintainOrder(MaintainOrder maintainOrder){
		maintainOrderServiceExtend.selectExportDetailListStartExtend(maintainOrder);
		List<MaintainOrder> list = maintainOrderMapper.selectDetailListByLikeMaintainOrder(maintainOrder);
		maintainOrderServiceExtend.selectExportDetailListEndExtend(maintainOrder, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param maintainOrder 维修工单
     * @return 维修工单集合
     */
    public int selectCountByLikeMaintainOrder(MaintainOrder maintainOrder){
		return maintainOrderMapper.selectCountByLikeMaintainOrder(maintainOrder);
	}

	/**
     * 精确查询记录数
     *
     * @param maintainOrder 维修工单
     * @return 维修工单集合
     */
    public int selectCountByEqMaintainOrder(MaintainOrder maintainOrder){
		return maintainOrderMapper.selectCountByEqMaintainOrder(maintainOrder);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(MaintainOrder maintainOrder){
		maintainOrderServiceExtend.exportDataCheckExtend(maintainOrder);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(MaintainOrder maintainOrder, List<MaintainOrder> list){
		 maintainOrderServiceExtend.exportDataDealExtend(maintainOrder, list);
	 }

    /**
     * 新增维修工单
     *
     * @param maintainOrder 维修工单
     * @return 结果
     */
    @Override
	@Transactional
    public int insertMaintainOrder(MaintainOrder maintainOrder)
    {
        maintainOrder.setCreateTime(DateUtils.getNowDate());
		maintainOrder.setUpdateTime(maintainOrder.getCreateTime());
        maintainOrderServiceExtend.insertStartExtend( maintainOrder);
		int rows = 0;
 		rows = maintainOrderMapper.insertMaintainOrder(maintainOrder);
		maintainOrderServiceExtend.insertEndExtend( maintainOrder, rows);
        return Integer.parseInt(maintainOrder.getId()+"");
    }

	/**
     * 批量新增修改维修工单
     *
     * @param maintainOrder 维修工单
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByMaintainOrder(List<MaintainOrder> maintainOrders){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = maintainOrders.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<MaintainOrder> batchList = maintainOrders.subList(i, toIndex);
			maintainOrderMapper.batchInsertOrUpdateByMaintainOrder(batchList);
		}
	}

    /**
     * 修改维修工单：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param maintainOrder 维修工单
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByMaintainOrder(MaintainOrder maintainOrder)
    {
		MaintainOrder oldMaintainOrder = maintainOrderMapper.selectDataByPkMaintainOrder(maintainOrder.getId());
        maintainOrderServiceExtend.updateStartExtend( maintainOrder, oldMaintainOrder);
		int rows = 0;
		rows = maintainOrderMapper.updateNullValueByMaintainOrder(maintainOrder);
		maintainOrderServiceExtend.updateEndExtend( maintainOrder,oldMaintainOrder, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param maintainOrder 维修工单
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByMaintainOrder(MaintainOrder maintainOrder)
    {
		MaintainOrder oldMaintainOrder = maintainOrderMapper.selectDataByPkMaintainOrder(maintainOrder.getId());
        maintainOrderServiceExtend.updateStartExtend( maintainOrder, oldMaintainOrder);
		int rows = 0;
		rows = maintainOrderMapper.updateNotNullValueByMaintainOrder(maintainOrder);
		maintainOrderServiceExtend.updateEndExtend( maintainOrder,oldMaintainOrder, rows);
        return rows;
    }

	/**
     * 删除MaintainOrderById
     *
     * @param id 维修工单主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteMaintainOrderById(MaintainOrder maintainOrder)
    {
		try {
        	return maintainOrderMapper.deleteMaintainOrderById(maintainOrder);
		} catch (DataIntegrityViolationException e) {
			// 提取根本原因
			Throwable rootCause = e.getRootCause();
			if (rootCause instanceof SQLIntegrityConstraintViolationException) {
				SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) rootCause;
				// MySQL 错误码 1451 表示外键约束冲突
				if (sqlEx.getErrorCode() == 1451) {
					throw new ServiceException("删除失败：当前删除记录下存在关联信息！请先删除所有关联信息后再进行删除。");
				}
			}
			// 其他类型异常继续抛出
			throw e;
		}
    }

    /**
     * 批量删除MaintainOrderByIds
     *
     * @param ids 需要删除的维修工单主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteMaintainOrderByIds(MaintainOrder maintainOrder) {
		try {
			maintainOrderServiceExtend.deleteByIdsStartExtend(maintainOrder);
			int rows = maintainOrderMapper.deleteMaintainOrderByIds(maintainOrder);
			maintainOrderServiceExtend.deleteByIdsEndExtend(maintainOrder, rows);
			return rows;
		} catch (DataIntegrityViolationException e) {
			// 提取根本原因
			Throwable rootCause = e.getRootCause();
			if (rootCause instanceof SQLIntegrityConstraintViolationException) {
				SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) rootCause;
				// MySQL 错误码 1451 表示外键约束冲突
				if (sqlEx.getErrorCode() == 1451) {
					throw new ServiceException("删除失败：当前删除记录下存在关联信息！请先删除所有关联信息后再进行删除。");
				}
			}
			// 其他类型异常继续抛出
			throw e;
		}
    }

	/**
     * 批量删除MaintainOrderByEqMaintainOrder
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deleteMaintainOrderByEqMaintainOrder(MaintainOrder maintainOrder){
		try {
			maintainOrderServiceExtend.deleteByEqMaintainOrderStartExtend(maintainOrder);
			int rows = maintainOrderMapper.deleteMaintainOrderByEqMaintainOrder(maintainOrder);
			maintainOrderServiceExtend.deleteByEqMaintainOrderEndExtend(maintainOrder, rows);
       	 	return rows;
		} catch (DataIntegrityViolationException e) {
			// 提取根本原因
			Throwable rootCause = e.getRootCause();
			if (rootCause instanceof SQLIntegrityConstraintViolationException) {
				SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) rootCause;
				// MySQL 错误码 1451 表示外键约束冲突
				if (sqlEx.getErrorCode() == 1451) {
					throw new ServiceException("删除失败：当前删除记录下存在关联信息！请先删除所有关联信息后再进行删除。");
				}
			}
			// 其他类型异常继续抛出
			throw e;
		}
	}

	/**
     * 删除全部数据
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deleteMaintainOrderAllData(){
		try {
			int rows = maintainOrderMapper.deleteMaintainOrderAllData();
       	 	return rows;
		} catch (DataIntegrityViolationException e) {
			// 提取根本原因
			Throwable rootCause = e.getRootCause();
			if (rootCause instanceof SQLIntegrityConstraintViolationException) {
				SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) rootCause;
				// MySQL 错误码 1451 表示外键约束冲突
				if (sqlEx.getErrorCode() == 1451) {
					throw new ServiceException("删除失败：当前删除记录下存在关联信息！请先删除所有关联信息后再进行删除。");
				}
			}
			// 其他类型异常继续抛出
			throw e;
		}
	}



   /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
	// @Transactional 导入不开启事务，避免事务过大，每批次写入后即提交事务，数据导入一半，可以重新导入时自动更新
    public String importMaintainOrderData(List<MaintainOrder> dataList, Boolean isUpdateSupport, String operName, MaintainOrder pMaintainOrder)
    {
        if (StringUtils.isNull(dataList) || dataList.size() == 0)
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
		String headString = "<br> - ";
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
		MaintainOrder maintainOrder = new MaintainOrder();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		maintainOrderServiceExtend.importDataStartExtend(dataList, pMaintainOrder, operName, nowDate);

		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> orderFaultTypeLableValueMap =  commonService.getDictLableValueMap("order_fault_type");
		String  orderFaultTypeAllDictLableStr = commonService.getDictAllLableStr("order_fault_type");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> orderPriorityLableValueMap =  commonService.getDictLableValueMap("order_priority");
		String  orderPriorityAllDictLableStr = commonService.getDictAllLableStr("order_priority");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> repairOrderStatusLableValueMap =  commonService.getDictLableValueMap("repair_order_status");
		String  repairOrderStatusAllDictLableStr = commonService.getDictAllLableStr("repair_order_status");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> reporterIdLableValueMap =  commonService.getDictLableValueMap("sys_user");
		String  reporterIdAllDictLableStr = commonService.getDictAllLableStr("sys_user");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> assigneeIdLableValueMap =  commonService.getDictLableValueMap("sys_user");
		String  assigneeIdAllDictLableStr = commonService.getDictAllLableStr("sys_user");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> userIdLableValueMap =  commonService.getDictLableValueMap("sys_user");
		String  userIdAllDictLableStr = commonService.getDictAllLableStr("sys_user");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> deptIdLableValueMap =  commonService.getDictLableValueMap("sys_dept");
		String  deptIdAllDictLableStr = commonService.getDictAllLableStr("sys_dept");


		// 将导入的汉字转为对应字典的value后存入数据库
		String dictValue = "";
        for (int i=0;i<dataList.size();i++) {
	        String checkMsg = "";
	        try {
				maintainOrder =dataList.get(i);
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(maintainOrder.getOrderNo())){
					checkMsg += headString + "工单编号字段为必填项。";
				}
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(maintainOrder.getDescription())){
					checkMsg += headString + "问题描述字段为必填项。";
				}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(maintainOrder.getOrderFaultTypeExtend())){
					checkMsg += headString + "故障类型字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String orderFaultType = maintainOrder.getOrderFaultTypeExtend();
                if (StrUtil.isNotBlank(orderFaultType)) {
					// 通过名称取对应的字典值
                    dictValue = orderFaultTypeLableValueMap.get(orderFaultType);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						maintainOrder.setOrderFaultType(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(orderFaultTypeLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(orderFaultTypeAllDictLableStr)) {
                        	checkMsg += headString + "故障类型字段的录入值["+ orderFaultType +"]必须属于以下取值范围["+orderFaultTypeAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "故障类型字段的录入值["+ orderFaultType +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(maintainOrder.getOrderPriorityExtend())){
					checkMsg += headString + "优先级字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String orderPriority = maintainOrder.getOrderPriorityExtend();
                if (StrUtil.isNotBlank(orderPriority)) {
					// 通过名称取对应的字典值
                    dictValue = orderPriorityLableValueMap.get(orderPriority);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						maintainOrder.setOrderPriority(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(orderPriorityLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(orderPriorityAllDictLableStr)) {
                        	checkMsg += headString + "优先级字段的录入值["+ orderPriority +"]必须属于以下取值范围["+orderPriorityAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "优先级字段的录入值["+ orderPriority +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(maintainOrder.getRepairOrderStatusExtend())){
					checkMsg += headString + "工单状态字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String repairOrderStatus = maintainOrder.getRepairOrderStatusExtend();
                if (StrUtil.isNotBlank(repairOrderStatus)) {
					// 通过名称取对应的字典值
                    dictValue = repairOrderStatusLableValueMap.get(repairOrderStatus);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						maintainOrder.setRepairOrderStatus(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(repairOrderStatusLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(repairOrderStatusAllDictLableStr)) {
                        	checkMsg += headString + "工单状态字段的录入值["+ repairOrderStatus +"]必须属于以下取值范围["+repairOrderStatusAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "工单状态字段的录入值["+ repairOrderStatus +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String reporterId = maintainOrder.getReporterIdExtend();
                if (StrUtil.isNotBlank(reporterId)) {
					// 通过名称取对应的字典值
                    dictValue = reporterIdLableValueMap.get(reporterId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        maintainOrder.setReporterId(Long.parseLong(dictValue));
                    } else {
						//allDictLableStr = CollUtil.join(reporterIdLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(reporterIdAllDictLableStr)) {
                        	checkMsg += headString + "报修人字段的录入值["+ reporterId +"]必须属于以下取值范围["+reporterIdAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "报修人字段的录入值["+ reporterId +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查日期时间类型
		        if (null != maintainOrder.getReportTime() && DateUtil.isSameDay(maintainOrder.getReportTime(), DateUtil.parseDate("9999-09-09"))){
		        	checkMsg += headString + "报修时间字段为日期时间类型，格式必须属于以下范围[yyyy-MM-dd HH:mm:ss,yyyy/MM/dd HH:mm:ss]。";
	        	}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String assigneeId = maintainOrder.getAssigneeIdExtend();
                if (StrUtil.isNotBlank(assigneeId)) {
					// 通过名称取对应的字典值
                    dictValue = assigneeIdLableValueMap.get(assigneeId);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
                        maintainOrder.setAssigneeId(Long.parseLong(dictValue));
                    } else {
						//allDictLableStr = CollUtil.join(assigneeIdLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(assigneeIdAllDictLableStr)) {
                        	checkMsg += headString + "维修人字段的录入值["+ assigneeId +"]必须属于以下取值范围["+assigneeIdAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "维修人字段的录入值["+ assigneeId +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查日期时间类型
		        if (null != maintainOrder.getCompletionTime() && DateUtil.isSameDay(maintainOrder.getCompletionTime(), DateUtil.parseDate("9999-09-09"))){
		        	checkMsg += headString + "完成时间字段为日期时间类型，格式必须属于以下范围[yyyy-MM-dd HH:mm:ss,yyyy/MM/dd HH:mm:ss]。";
	        	}

		        // 检查完必填字段和取值范围后，在进行重复记录校验
		        if ("".equals(checkMsg)) {
			        // 进行导入扩展校验
			        checkMsg = maintainOrderServiceExtend.importDataCheckExtend(isUpdateSupport, pMaintainOrder, maintainOrder, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, maintainOrder);
			        }
		        }

	        } catch (Exception e) {
		        checkMsg += headString + "数据导入时出现异常！" + e.getMessage();
		        logger.error("数据导入时出现异常！", e);
	        }
	        if (!"".equals(checkMsg)) {
		        failureNum++;
		        String msg = "<br/>▶第【" + (i + 1) + "】条数据导入失败：" + checkMsg;
		        failureMsg.append(msg);
	        } else{
				successNum++;
	        }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共有 " + failureNum + " 条数据存在问题，请修正后再重新导入。具体如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
			try{
				// 批量插入数据
				this.batchInsertOrUpdateByMaintainOrder(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		maintainOrderServiceExtend.importDataEndExtend(dataList, pMaintainOrder, operName, successNum);
        return successMsg.toString();
    }



}
