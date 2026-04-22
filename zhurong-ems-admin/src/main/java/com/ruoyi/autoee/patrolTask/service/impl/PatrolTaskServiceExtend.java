package com.ruoyi.autoee.patrolTask.service.impl;

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
import com.ruoyi.autoee.patrolTask.mapper.PatrolTaskMapper;
import com.ruoyi.autoee.patrolTask.mapper.PatrolTaskMapperExtend;
import com.ruoyi.autoee.patrolTask.domain.PatrolTask;
import com.ruoyi.autoee.patrolTask.service.IPatrolTaskService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 巡更任务Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class PatrolTaskServiceExtend {
	private static final Logger logger = LoggerFactory.getLogger(PatrolTaskServiceExtend.class);
	@Autowired
	private PatrolTaskMapper patrolTaskMapper;
	@Autowired
	private PatrolTaskMapperExtend patrolTaskMapperExtend;
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
     * 通过唯一字段查询巡更任务一条详细信息
     */
    public PatrolTask selectOneDataByCache(PatrolTask patrolTask)
    {
		PatrolTask patrolTaskR = new PatrolTask();
	    if (null != patrolTaskR) {
			return patrolTaskR;
	    } else {
			List<PatrolTask> patrolTaskList = patrolTaskMapper.selectDetailListByLikePatrolTask(new PatrolTask());
			if (patrolTaskList.size() == 1) {
				patrolTaskR = patrolTaskList.get(0);
				RedisCacheUtils.setMapValue("DataCache_Map_patrolTask", String.valueOf(patrolTask.getId()), JSONUtil.toJsonStr(patrolTaskR));
				return patrolTaskR;
			} else {
				throw new ServiceException("查询信息结果不唯一！");
			}
	    }
    }

	/**
	 * 查询列表Start扩展
	 */
	public void selectListStartExtend(PatrolTask patrolTask) {

	}

	/**
	 * 查询列表End扩展
	 */
	public void selectListEndExtend(PatrolTask patrolTask, List<PatrolTask> list) {

	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListStartExtend(PatrolTask patrolTask) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListEndExtend(PatrolTask patrolTask, List<PatrolTask> list) {
		changSelfDefineDict(patrolTask, list);
	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListByEqPatrolTaskStartExtend(PatrolTask patrolTask) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListByEqPatrolTaskEndExtend(PatrolTask patrolTask, List<PatrolTask> list) {
		changSelfDefineDict(patrolTask, list);
	}

	/**
	 * 查询明细End扩展
	 */
	public void selectDetailByPkPatrolTaskEndExtend(PatrolTask patrolTask) {
		List<PatrolTask> list = new ArrayList();
		list.add(patrolTask);
		changSelfDefineDict(patrolTask, list);
		patrolTask =list.get(0);
	}

	/**
	 * 查询导出列表Start扩展
	 */
	public void selectExportDetailListStartExtend(PatrolTask patrolTask) {

	}

	/**
	 * 查询导出列表End扩展
	 */
	public void selectExportDetailListEndExtend(PatrolTask patrolTask, List<PatrolTask> list) {
		changSelfDefineDict(patrolTask, list);
	}

	/**
	 * 处理不能通过sql进行转码的下拉框
	 */
	public void changSelfDefineDict(PatrolTask patrolTask, List<PatrolTask> list) {
		//HashMap<String, String> patrolPlanIdMap = patrolPlanIdMap(patrolTask);
		//HashMap<String, String> patrolPathIdMap = patrolPathIdMap(patrolTask);
		//HashMap<String, String> patrolUserIdMap = patrolUserIdMap(patrolTask);
		//HashMap<String, String> userIdMap = userIdMap(patrolTask);
		//HashMap<String, String> deptIdMap = deptIdMap(patrolTask);
		//list.forEach(e -> {
		//e.setPatrolPlanIdExtend(patrolPlanIdMap.get(e.getPatrolPlanId() + ""));
		//e.setPatrolPathIdExtend(patrolPathIdMap.get(e.getPatrolPathId() + ""));
		//e.setPatrolUserIdExtend(patrolUserIdMap.get(e.getPatrolUserId() + ""));
		//e.setUserIdExtend(userIdMap.get(e.getUserId() + ""));
		//e.setDeptIdExtend(deptIdMap.get(e.getDeptId() + ""));
		//});
	}

	/**
	 * 校验数据唯一性
	 */
	public void checkDataUnique(PatrolTask patrolTask, String flag) {
	PatrolTask qPatrolTask = new PatrolTask();
																			
	}

	/**
	 * 新增Start扩展
	 */
	public void insertStartExtend(PatrolTask patrolTask) {
		checkDataUnique(patrolTask, "insert");
	}

	/**
	 * 新增End扩展
	 */
	public void insertEndExtend(PatrolTask patrolTask, int rows) {

	}

	/**
	 * 修改Start扩展
	 */
	public void updateStartExtend(PatrolTask patrolTask,PatrolTask oldPatrolTask) {
		checkDataUnique(patrolTask, "update");
	}

	/**
	 * 修改End扩展
	 */
	public void updateEndExtend(PatrolTask patrolTask, PatrolTask oldPatrolTask, int rows) {

	}

	/**
	 * 批量删除Start扩展
	 */
	public void deleteByIdsStartExtend(PatrolTask patrolTask) {
		for (String id : patrolTask.getIds()){
			//PatrolTask tPatrolTask = patrolTaskMapper.selectDataByPkPatrolTask(id);
		}
	}

	/**
	 * 批量删除End扩展
	 */
	public void deleteByIdsEndExtend(PatrolTask patrolTask, int rows) {

	}

	/**
	 * 批量删除ByEqPatrolTaskStart扩展
	 */
	public void deleteByEqPatrolTaskStartExtend(PatrolTask patrolTask) {
	}

	/**
	 * 批量删除ByEqPatrolTaskEnd扩展
	 */
	public void deleteByEqPatrolTaskEndExtend(PatrolTask patrolTask, int rows) {

	}


	/**
     * 导出前校验扩展
     */
	public void exportDataCheckExtend(PatrolTask patrolTask) {
		int count = patrolTaskMapper.selectCountByLikePatrolTask(patrolTask);
		// 导出数据超过5万条，报错提示，各模块可以根据需要进行设置
		int limitCount = 50000;
		if (count > limitCount) {
			throw new ServiceException("当前导出的数据超过" + limitCount + "条！请修改查询条件后进行查询，确认数据条数不大于" + limitCount + "条后，再进行导出。");
		}
	}

	/**
     * 导出数据预处理扩展
     */
	public void exportDataDealExtend(PatrolTask patrolTask, List<PatrolTask> list) {

	}

	/**
	 * 导入开始扩展
	 */
	public void importDataStartExtend(List<PatrolTask> dataList, PatrolTask pPatrolTask, String operName, Date operDate) {
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
	public String importDataCheckExtend(boolean isUpdateSupport, PatrolTask pPatrolTask, PatrolTask patrolTask, String operName) {
		String checkMsg = "";
		if (isUpdateSupport) {

		} else {
			//  如果当前不进行更新数据，当前数据不存在，则直接新增
			try {
				checkDataUnique(patrolTask, "insert");
			} catch (Exception e) {
				checkMsg = e.getMessage();
			}
		}

															return checkMsg;
	}

	/**
	 * 导入完成扩展
	 */
	public void importDataEndExtend(List<PatrolTask> dataList, PatrolTask pPatrolTask, String operName, int successNum) {

	}

	/**
	 * 检查是否可以修改
	 */
	public void checkEditExtend(PatrolTask pPatrolTask) {
		//        if(pPatrolTask.getXXX().equals("")) {
		//            throw new ServiceException("当前选中数据不可修改！");
		//        }
	}


}
