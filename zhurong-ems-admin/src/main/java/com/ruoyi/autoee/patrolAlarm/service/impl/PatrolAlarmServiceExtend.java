package com.ruoyi.autoee.patrolAlarm.service.impl;

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
import com.ruoyi.autoee.patrolAlarm.mapper.PatrolAlarmMapper;
import com.ruoyi.autoee.patrolAlarm.mapper.PatrolAlarmMapperExtend;
import com.ruoyi.autoee.patrolAlarm.domain.PatrolAlarm;
import com.ruoyi.autoee.patrolAlarm.service.IPatrolAlarmService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 巡更报警Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class PatrolAlarmServiceExtend {
	private static final Logger logger = LoggerFactory.getLogger(PatrolAlarmServiceExtend.class);
	@Autowired
	private PatrolAlarmMapper patrolAlarmMapper;
	@Autowired
	private PatrolAlarmMapperExtend patrolAlarmMapperExtend;
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
     * 通过唯一字段查询巡更报警一条详细信息
     */
    public PatrolAlarm selectOneDataByCache(PatrolAlarm patrolAlarm)
    {
		PatrolAlarm patrolAlarmR = new PatrolAlarm();
	    if (null != patrolAlarmR) {
			return patrolAlarmR;
	    } else {
			List<PatrolAlarm> patrolAlarmList = patrolAlarmMapper.selectDetailListByLikePatrolAlarm(new PatrolAlarm());
			if (patrolAlarmList.size() == 1) {
				patrolAlarmR = patrolAlarmList.get(0);
				RedisCacheUtils.setMapValue("DataCache_Map_patrolAlarm", String.valueOf(patrolAlarm.getId()), JSONUtil.toJsonStr(patrolAlarmR));
				return patrolAlarmR;
			} else {
				throw new ServiceException("查询信息结果不唯一！");
			}
	    }
    }

	/**
	 * 查询列表Start扩展
	 */
	public void selectListStartExtend(PatrolAlarm patrolAlarm) {

	}

	/**
	 * 查询列表End扩展
	 */
	public void selectListEndExtend(PatrolAlarm patrolAlarm, List<PatrolAlarm> list) {

	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListStartExtend(PatrolAlarm patrolAlarm) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListEndExtend(PatrolAlarm patrolAlarm, List<PatrolAlarm> list) {
		changSelfDefineDict(patrolAlarm, list);
	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListByEqPatrolAlarmStartExtend(PatrolAlarm patrolAlarm) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListByEqPatrolAlarmEndExtend(PatrolAlarm patrolAlarm, List<PatrolAlarm> list) {
		changSelfDefineDict(patrolAlarm, list);
	}

	/**
	 * 查询明细End扩展
	 */
	public void selectDetailByPkPatrolAlarmEndExtend(PatrolAlarm patrolAlarm) {
		List<PatrolAlarm> list = new ArrayList();
		list.add(patrolAlarm);
		changSelfDefineDict(patrolAlarm, list);
		patrolAlarm =list.get(0);
	}

	/**
	 * 查询导出列表Start扩展
	 */
	public void selectExportDetailListStartExtend(PatrolAlarm patrolAlarm) {

	}

	/**
	 * 查询导出列表End扩展
	 */
	public void selectExportDetailListEndExtend(PatrolAlarm patrolAlarm, List<PatrolAlarm> list) {
		changSelfDefineDict(patrolAlarm, list);
	}

	/**
	 * 处理不能通过sql进行转码的下拉框
	 */
	public void changSelfDefineDict(PatrolAlarm patrolAlarm, List<PatrolAlarm> list) {
		//HashMap<String, String> patrolPlanIdMap = patrolPlanIdMap(patrolAlarm);
		//HashMap<String, String> patrolTaskIdMap = patrolTaskIdMap(patrolAlarm);
		//HashMap<String, String> patrolUserIdMap = patrolUserIdMap(patrolAlarm);
		//HashMap<String, String> handleUserIdMap = handleUserIdMap(patrolAlarm);
		//HashMap<String, String> userIdMap = userIdMap(patrolAlarm);
		//HashMap<String, String> deptIdMap = deptIdMap(patrolAlarm);
		//list.forEach(e -> {
		//e.setPatrolPlanIdExtend(patrolPlanIdMap.get(e.getPatrolPlanId() + ""));
		//e.setPatrolTaskIdExtend(patrolTaskIdMap.get(e.getPatrolTaskId() + ""));
		//e.setPatrolUserIdExtend(patrolUserIdMap.get(e.getPatrolUserId() + ""));
		//e.setHandleUserIdExtend(handleUserIdMap.get(e.getHandleUserId() + ""));
		//e.setUserIdExtend(userIdMap.get(e.getUserId() + ""));
		//e.setDeptIdExtend(deptIdMap.get(e.getDeptId() + ""));
		//});
	}

	/**
	 * 校验数据唯一性
	 */
	public void checkDataUnique(PatrolAlarm patrolAlarm, String flag) {
	PatrolAlarm qPatrolAlarm = new PatrolAlarm();
					qPatrolAlarm.setAlarmNo(patrolAlarm.getAlarmNo());
																				
		List<PatrolAlarm> list = patrolAlarmMapper.selectDataListByEqPatrolAlarm(qPatrolAlarm);
		if (("insert".equals(flag) && list.size() > 0) || "update".equals(flag) && list.size() > 0 && !list.get(0).getId().equals(patrolAlarm.getId())){
		String msg = "";
					msg += "报警编号[" + patrolAlarm.getAlarmNo() + "]，";
																						msg += "在系统中已经存在！请直接在系统中进行维护。";
		logger.error("【失败】-" + msg);
		throw new ServiceException(msg);
	}
	}

	/**
	 * 新增Start扩展
	 */
	public void insertStartExtend(PatrolAlarm patrolAlarm) {
		checkDataUnique(patrolAlarm, "insert");
	}

	/**
	 * 新增End扩展
	 */
	public void insertEndExtend(PatrolAlarm patrolAlarm, int rows) {

	}

	/**
	 * 修改Start扩展
	 */
	public void updateStartExtend(PatrolAlarm patrolAlarm,PatrolAlarm oldPatrolAlarm) {
		checkDataUnique(patrolAlarm, "update");
	}

	/**
	 * 修改End扩展
	 */
	public void updateEndExtend(PatrolAlarm patrolAlarm, PatrolAlarm oldPatrolAlarm, int rows) {

	}

	/**
	 * 批量删除Start扩展
	 */
	public void deleteByIdsStartExtend(PatrolAlarm patrolAlarm) {
		for (String id : patrolAlarm.getIds()){
			//PatrolAlarm tPatrolAlarm = patrolAlarmMapper.selectDataByPkPatrolAlarm(id);
		}
	}

	/**
	 * 批量删除End扩展
	 */
	public void deleteByIdsEndExtend(PatrolAlarm patrolAlarm, int rows) {

	}

	/**
	 * 批量删除ByEqPatrolAlarmStart扩展
	 */
	public void deleteByEqPatrolAlarmStartExtend(PatrolAlarm patrolAlarm) {
	}

	/**
	 * 批量删除ByEqPatrolAlarmEnd扩展
	 */
	public void deleteByEqPatrolAlarmEndExtend(PatrolAlarm patrolAlarm, int rows) {

	}


	/**
     * 导出前校验扩展
     */
	public void exportDataCheckExtend(PatrolAlarm patrolAlarm) {
		int count = patrolAlarmMapper.selectCountByLikePatrolAlarm(patrolAlarm);
		// 导出数据超过5万条，报错提示，各模块可以根据需要进行设置
		int limitCount = 50000;
		if (count > limitCount) {
			throw new ServiceException("当前导出的数据超过" + limitCount + "条！请修改查询条件后进行查询，确认数据条数不大于" + limitCount + "条后，再进行导出。");
		}
	}

	/**
     * 导出数据预处理扩展
     */
	public void exportDataDealExtend(PatrolAlarm patrolAlarm, List<PatrolAlarm> list) {

	}

	/**
	 * 导入开始扩展
	 */
	public void importDataStartExtend(List<PatrolAlarm> dataList, PatrolAlarm pPatrolAlarm, String operName, Date operDate) {
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
	public String importDataCheckExtend(boolean isUpdateSupport, PatrolAlarm pPatrolAlarm, PatrolAlarm patrolAlarm, String operName) {
		String checkMsg = "";
		if (isUpdateSupport) {

		} else {
			//  如果当前不进行更新数据，当前数据不存在，则直接新增
			try {
				checkDataUnique(patrolAlarm, "insert");
			} catch (Exception e) {
				checkMsg = e.getMessage();
			}
		}

															return checkMsg;
	}

	/**
	 * 导入完成扩展
	 */
	public void importDataEndExtend(List<PatrolAlarm> dataList, PatrolAlarm pPatrolAlarm, String operName, int successNum) {

	}

	/**
	 * 检查是否可以修改
	 */
	public void checkEditExtend(PatrolAlarm pPatrolAlarm) {
		//        if(pPatrolAlarm.getXXX().equals("")) {
		//            throw new ServiceException("当前选中数据不可修改！");
		//        }
	}


}
