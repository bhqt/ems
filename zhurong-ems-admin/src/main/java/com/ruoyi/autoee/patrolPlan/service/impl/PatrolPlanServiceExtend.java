package com.ruoyi.autoee.patrolPlan.service.impl;

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
import com.ruoyi.autoee.patrolPlan.mapper.PatrolPlanMapper;
import com.ruoyi.autoee.patrolPlan.mapper.PatrolPlanMapperExtend;
import com.ruoyi.autoee.patrolPlan.domain.PatrolPlan;
import com.ruoyi.autoee.patrolPlan.service.IPatrolPlanService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 巡更计划Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class PatrolPlanServiceExtend {
	private static final Logger logger = LoggerFactory.getLogger(PatrolPlanServiceExtend.class);
	@Autowired
	private PatrolPlanMapper patrolPlanMapper;
	@Autowired
	private PatrolPlanMapperExtend patrolPlanMapperExtend;
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
     * 通过唯一字段查询巡更计划一条详细信息
     */
    public PatrolPlan selectOneDataByCache(PatrolPlan patrolPlan)
    {
		PatrolPlan patrolPlanR = new PatrolPlan();
	    if (null != patrolPlanR) {
			return patrolPlanR;
	    } else {
			List<PatrolPlan> patrolPlanList = patrolPlanMapper.selectDetailListByLikePatrolPlan(new PatrolPlan());
			if (patrolPlanList.size() == 1) {
				patrolPlanR = patrolPlanList.get(0);
				RedisCacheUtils.setMapValue("DataCache_Map_patrolPlan", String.valueOf(patrolPlan.getId()), JSONUtil.toJsonStr(patrolPlanR));
				return patrolPlanR;
			} else {
				throw new ServiceException("查询信息结果不唯一！");
			}
	    }
    }

	/**
	 * 查询列表Start扩展
	 */
	public void selectListStartExtend(PatrolPlan patrolPlan) {

	}

	/**
	 * 查询列表End扩展
	 */
	public void selectListEndExtend(PatrolPlan patrolPlan, List<PatrolPlan> list) {

	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListStartExtend(PatrolPlan patrolPlan) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListEndExtend(PatrolPlan patrolPlan, List<PatrolPlan> list) {
		changSelfDefineDict(patrolPlan, list);
	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListByEqPatrolPlanStartExtend(PatrolPlan patrolPlan) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListByEqPatrolPlanEndExtend(PatrolPlan patrolPlan, List<PatrolPlan> list) {
		changSelfDefineDict(patrolPlan, list);
	}

	/**
	 * 查询明细End扩展
	 */
	public void selectDetailByPkPatrolPlanEndExtend(PatrolPlan patrolPlan) {
		List<PatrolPlan> list = new ArrayList();
		list.add(patrolPlan);
		changSelfDefineDict(patrolPlan, list);
		patrolPlan =list.get(0);
	}

	/**
	 * 查询导出列表Start扩展
	 */
	public void selectExportDetailListStartExtend(PatrolPlan patrolPlan) {

	}

	/**
	 * 查询导出列表End扩展
	 */
	public void selectExportDetailListEndExtend(PatrolPlan patrolPlan, List<PatrolPlan> list) {
		changSelfDefineDict(patrolPlan, list);
	}

	/**
	 * 处理不能通过sql进行转码的下拉框
	 */
	public void changSelfDefineDict(PatrolPlan patrolPlan, List<PatrolPlan> list) {
		//HashMap<String, String> patrolPathIdMap = patrolPathIdMap(patrolPlan);
		//HashMap<String, String> patrolUserIdMap = patrolUserIdMap(patrolPlan);
		//HashMap<String, String> userIdMap = userIdMap(patrolPlan);
		//HashMap<String, String> deptIdMap = deptIdMap(patrolPlan);
		//list.forEach(e -> {
		//e.setPatrolPathIdExtend(patrolPathIdMap.get(e.getPatrolPathId() + ""));
		//e.setPatrolUserIdExtend(patrolUserIdMap.get(e.getPatrolUserId() + ""));
		//e.setUserIdExtend(userIdMap.get(e.getUserId() + ""));
		//e.setDeptIdExtend(deptIdMap.get(e.getDeptId() + ""));
		//});
	}

	/**
	 * 校验数据唯一性
	 */
	public void checkDataUnique(PatrolPlan patrolPlan, String flag) {
	PatrolPlan qPatrolPlan = new PatrolPlan();
					qPatrolPlan.setPatrolPlanName(patrolPlan.getPatrolPlanName());
																			
		List<PatrolPlan> list = patrolPlanMapper.selectDataListByEqPatrolPlan(qPatrolPlan);
		if (("insert".equals(flag) && list.size() > 0) || "update".equals(flag) && list.size() > 0 && !list.get(0).getId().equals(patrolPlan.getId())){
		String msg = "";
					msg += "巡更计划名称[" + patrolPlan.getPatrolPlanName() + "]，";
																					msg += "在系统中已经存在！请直接在系统中进行维护。";
		logger.error("【失败】-" + msg);
		throw new ServiceException(msg);
	}
	}

	/**
	 * 新增Start扩展
	 */
	public void insertStartExtend(PatrolPlan patrolPlan) {
		checkDataUnique(patrolPlan, "insert");
	}

	/**
	 * 新增End扩展
	 */
	public void insertEndExtend(PatrolPlan patrolPlan, int rows) {

	}

	/**
	 * 修改Start扩展
	 */
	public void updateStartExtend(PatrolPlan patrolPlan,PatrolPlan oldPatrolPlan) {
		checkDataUnique(patrolPlan, "update");
	}

	/**
	 * 修改End扩展
	 */
	public void updateEndExtend(PatrolPlan patrolPlan, PatrolPlan oldPatrolPlan, int rows) {

	}

	/**
	 * 批量删除Start扩展
	 */
	public void deleteByIdsStartExtend(PatrolPlan patrolPlan) {
		for (String id : patrolPlan.getIds()){
			//PatrolPlan tPatrolPlan = patrolPlanMapper.selectDataByPkPatrolPlan(id);
		}
	}

	/**
	 * 批量删除End扩展
	 */
	public void deleteByIdsEndExtend(PatrolPlan patrolPlan, int rows) {

	}

	/**
	 * 批量删除ByEqPatrolPlanStart扩展
	 */
	public void deleteByEqPatrolPlanStartExtend(PatrolPlan patrolPlan) {
	}

	/**
	 * 批量删除ByEqPatrolPlanEnd扩展
	 */
	public void deleteByEqPatrolPlanEndExtend(PatrolPlan patrolPlan, int rows) {

	}


	/**
     * 导出前校验扩展
     */
	public void exportDataCheckExtend(PatrolPlan patrolPlan) {
		int count = patrolPlanMapper.selectCountByLikePatrolPlan(patrolPlan);
		// 导出数据超过5万条，报错提示，各模块可以根据需要进行设置
		int limitCount = 50000;
		if (count > limitCount) {
			throw new ServiceException("当前导出的数据超过" + limitCount + "条！请修改查询条件后进行查询，确认数据条数不大于" + limitCount + "条后，再进行导出。");
		}
	}

	/**
     * 导出数据预处理扩展
     */
	public void exportDataDealExtend(PatrolPlan patrolPlan, List<PatrolPlan> list) {

	}

	/**
	 * 导入开始扩展
	 */
	public void importDataStartExtend(List<PatrolPlan> dataList, PatrolPlan pPatrolPlan, String operName, Date operDate) {
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
	public String importDataCheckExtend(boolean isUpdateSupport, PatrolPlan pPatrolPlan, PatrolPlan patrolPlan, String operName) {
		String checkMsg = "";
		if (isUpdateSupport) {

		} else {
			//  如果当前不进行更新数据，当前数据不存在，则直接新增
			try {
				checkDataUnique(patrolPlan, "insert");
			} catch (Exception e) {
				checkMsg = e.getMessage();
			}
		}

															return checkMsg;
	}

	/**
	 * 导入完成扩展
	 */
	public void importDataEndExtend(List<PatrolPlan> dataList, PatrolPlan pPatrolPlan, String operName, int successNum) {

	}

	/**
	 * 检查是否可以修改
	 */
	public void checkEditExtend(PatrolPlan pPatrolPlan) {
		//        if(pPatrolPlan.getXXX().equals("")) {
		//            throw new ServiceException("当前选中数据不可修改！");
		//        }
	}


}
