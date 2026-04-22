package com.ruoyi.autoee.maintainOrder.service.impl;

import java.util.*;
import javax.annotation.PostConstruct;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.utils.RedisCacheUtils;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.ISysDeptService;

import com.ruoyi.common.core.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.service.CommonService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.autoee.maintainOrder.mapper.MaintainOrderMapper;
import com.ruoyi.autoee.maintainOrder.mapper.MaintainOrderMapperExtend;
import com.ruoyi.autoee.maintainOrder.domain.MaintainOrder;
import com.ruoyi.autoee.maintainOrder.service.IMaintainOrderService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 维修工单Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class MaintainOrderServiceExtend {
	private static final Logger logger = LoggerFactory.getLogger(MaintainOrderServiceExtend.class);
	@Autowired
	private MaintainOrderMapper maintainOrderMapper;
	@Autowired
	private MaintainOrderMapperExtend maintainOrderMapperExtend;
	@Autowired
	private ISysDictDataService iSysDictDataService;
	@Autowired
	private ISysUserService iSysUserService;
	@Autowired
	private ISysDeptService iSysDeptService;

	/**
 	* 项目启动时，初始化参数到缓存
	 */
	@PostConstruct
	public void init() {
	}



  /**
     * 通过唯一字段查询维修工单一条详细信息
     */
    public MaintainOrder selectOneDataByCache(MaintainOrder maintainOrder)
    {
		MaintainOrder maintainOrderR = new MaintainOrder();
	    if (null != maintainOrderR) {
			return maintainOrderR;
	    } else {
			List<MaintainOrder> maintainOrderList = maintainOrderMapper.selectDetailListByLikeMaintainOrder(new MaintainOrder());
			if (maintainOrderList.size() == 1) {
				maintainOrderR = maintainOrderList.get(0);
				RedisCacheUtils.setMapValue("DataCache_Map_maintainOrder", String.valueOf(maintainOrder.getId()), JSONUtil.toJsonStr(maintainOrderR));
				return maintainOrderR;
			} else {
				throw new ServiceException("查询信息结果不唯一！");
			}
	    }
    }

	/**
	 * 查询列表Start扩展
	 */
	public void selectListStartExtend(MaintainOrder maintainOrder) {

	}

	/**
	 * 查询列表End扩展
	 */
	public void selectListEndExtend(MaintainOrder maintainOrder, List<MaintainOrder> list) {

	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListStartExtend(MaintainOrder maintainOrder) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListEndExtend(MaintainOrder maintainOrder, List<MaintainOrder> list) {
		changSelfDefineDict(maintainOrder, list);
	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListByEqMaintainOrderStartExtend(MaintainOrder maintainOrder) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListByEqMaintainOrderEndExtend(MaintainOrder maintainOrder, List<MaintainOrder> list) {
		changSelfDefineDict(maintainOrder, list);
	}

	/**
	 * 查询明细End扩展
	 */
	public void selectDetailByPkMaintainOrderEndExtend(MaintainOrder maintainOrder) {
		List<MaintainOrder> list = new ArrayList();
		list.add(maintainOrder);
		changSelfDefineDict(maintainOrder, list);
		maintainOrder =list.get(0);
	}

	/**
	 * 查询导出列表Start扩展
	 */
	public void selectExportDetailListStartExtend(MaintainOrder maintainOrder) {

	}

	/**
	 * 查询导出列表End扩展
	 */
	public void selectExportDetailListEndExtend(MaintainOrder maintainOrder, List<MaintainOrder> list) {
		changSelfDefineDict(maintainOrder, list);
	}

	/**
	 * 处理不能通过sql进行转码的下拉框
	 */
	public void changSelfDefineDict(MaintainOrder maintainOrder, List<MaintainOrder> list) {
		//HashMap<String, String> reporterIdMap = reporterIdMap(maintainOrder);
		//HashMap<String, String> assigneeIdMap = assigneeIdMap(maintainOrder);
		//HashMap<String, String> userIdMap = userIdMap(maintainOrder);
		//HashMap<String, String> deptIdMap = deptIdMap(maintainOrder);
		//list.forEach(e -> {
		//e.setReporterIdExtend(reporterIdMap.get(e.getReporterId() + ""));
		//e.setAssigneeIdExtend(assigneeIdMap.get(e.getAssigneeId() + ""));
		//e.setUserIdExtend(userIdMap.get(e.getUserId() + ""));
		//e.setDeptIdExtend(deptIdMap.get(e.getDeptId() + ""));
		//});
	}

	/**
	 * 校验数据唯一性
	 */
	public void checkDataUnique(MaintainOrder maintainOrder, String flag) {
	MaintainOrder qMaintainOrder = new MaintainOrder();
					qMaintainOrder.setOrderNo(maintainOrder.getOrderNo());
																						
		List<MaintainOrder> list = maintainOrderMapper.selectDataListByEqMaintainOrder(qMaintainOrder);
		if (("insert".equals(flag) && list.size() > 0) || "update".equals(flag) && list.size() > 0 && !list.get(0).getId().equals(maintainOrder.getId())){
		String msg = "";
					msg += "工单编号[" + maintainOrder.getOrderNo() + "]，";
																								msg += "在系统中已经存在！请直接在系统中进行维护。";
		logger.error("【失败】-" + msg);
		throw new ServiceException(msg);
	}
	}

	/**
	 * 新增Start扩展
	 */
	public void insertStartExtend(MaintainOrder maintainOrder) {
		checkDataUnique(maintainOrder, "insert");
	}

	/**
	 * 新增End扩展
	 */
	public void insertEndExtend(MaintainOrder maintainOrder, int rows) {

	}

	/**
	 * 修改Start扩展
	 */
	public void updateStartExtend(MaintainOrder maintainOrder,MaintainOrder oldMaintainOrder) {
		checkDataUnique(maintainOrder, "update");
	}

	/**
	 * 修改End扩展
	 */
	public void updateEndExtend(MaintainOrder maintainOrder, MaintainOrder oldMaintainOrder, int rows) {

	}

	/**
	 * 批量删除Start扩展
	 */
	public void deleteByIdsStartExtend(MaintainOrder maintainOrder) {
		for (String id : maintainOrder.getIds()){
			//MaintainOrder tMaintainOrder = maintainOrderMapper.selectDataByPkMaintainOrder(id);
		}
	}

	/**
	 * 批量删除End扩展
	 */
	public void deleteByIdsEndExtend(MaintainOrder maintainOrder, int rows) {

	}

	/**
	 * 批量删除ByEqMaintainOrderStart扩展
	 */
	public void deleteByEqMaintainOrderStartExtend(MaintainOrder maintainOrder) {
	}

	/**
	 * 批量删除ByEqMaintainOrderEnd扩展
	 */
	public void deleteByEqMaintainOrderEndExtend(MaintainOrder maintainOrder, int rows) {

	}


	/**
     * 导出前校验扩展
     */
	public void exportDataCheckExtend(MaintainOrder maintainOrder) {
		int count = maintainOrderMapper.selectCountByLikeMaintainOrder(maintainOrder);
		// 导出数据超过5万条，报错提示，各模块可以根据需要进行设置
		int limitCount = 50000;
		if (count > limitCount) {
			throw new ServiceException("当前导出的数据超过" + limitCount + "条！请修改查询条件后进行查询，确认数据条数不大于" + limitCount + "条后，再进行导出。");
		}
	}

	/**
     * 导出数据预处理扩展
     */
	public void exportDataDealExtend(MaintainOrder maintainOrder, List<MaintainOrder> list) {

	}

	/**
	 * 导入开始扩展
	 */
	public void importDataStartExtend(List<MaintainOrder> dataList, MaintainOrder pMaintainOrder, String operName, Date operDate) {
		dataList.forEach(data -> {
			data.setCreateTime(operDate);
			data.setCreateBy(operName);
			data.setUpdateTime(operDate);
			data.setUpdateBy(operName);
		});
	}

	/**
	 * 导入校验数据扩展
	 */
	public String importDataCheckExtend(boolean isUpdateSupport, MaintainOrder pMaintainOrder, MaintainOrder maintainOrder, String operName) {
		String checkMsg = "";
		if (isUpdateSupport) {

		} else {
			//  如果当前不进行更新数据，当前数据不存在，则直接新增
			try {
				checkDataUnique(maintainOrder, "insert");
			} catch (Exception e) {
				checkMsg = e.getMessage();
			}
		}

															return checkMsg;
	}

	/**
	 * 导入完成扩展
	 */
	public void importDataEndExtend(List<MaintainOrder> dataList, MaintainOrder pMaintainOrder, String operName, int successNum) {

	}

	/**
	 * 检查是否可以修改
	 */
	public void checkEditExtend(MaintainOrder pMaintainOrder) {
		//        if(pMaintainOrder.getXXX().equals("")) {
		//            throw new ServiceException("当前选中数据不可修改！");
		//        }
	}


}
