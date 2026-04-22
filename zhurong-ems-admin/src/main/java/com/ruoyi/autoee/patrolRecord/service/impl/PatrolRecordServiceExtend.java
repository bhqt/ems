package com.ruoyi.autoee.patrolRecord.service.impl;

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
import com.ruoyi.autoee.patrolRecord.mapper.PatrolRecordMapper;
import com.ruoyi.autoee.patrolRecord.mapper.PatrolRecordMapperExtend;
import com.ruoyi.autoee.patrolRecord.domain.PatrolRecord;
import com.ruoyi.autoee.patrolRecord.service.IPatrolRecordService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 巡更记录Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class PatrolRecordServiceExtend {
	private static final Logger logger = LoggerFactory.getLogger(PatrolRecordServiceExtend.class);
	@Autowired
	private PatrolRecordMapper patrolRecordMapper;
	@Autowired
	private PatrolRecordMapperExtend patrolRecordMapperExtend;
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
     * 通过唯一字段查询巡更记录一条详细信息
     */
    public PatrolRecord selectOneDataByCache(PatrolRecord patrolRecord)
    {
		PatrolRecord patrolRecordR = new PatrolRecord();
	    if (null != patrolRecordR) {
			return patrolRecordR;
	    } else {
			List<PatrolRecord> patrolRecordList = patrolRecordMapper.selectDetailListByLikePatrolRecord(new PatrolRecord());
			if (patrolRecordList.size() == 1) {
				patrolRecordR = patrolRecordList.get(0);
				RedisCacheUtils.setMapValue("DataCache_Map_patrolRecord", String.valueOf(patrolRecord.getId()), JSONUtil.toJsonStr(patrolRecordR));
				return patrolRecordR;
			} else {
				throw new ServiceException("查询信息结果不唯一！");
			}
	    }
    }

	/**
	 * 查询列表Start扩展
	 */
	public void selectListStartExtend(PatrolRecord patrolRecord) {

	}

	/**
	 * 查询列表End扩展
	 */
	public void selectListEndExtend(PatrolRecord patrolRecord, List<PatrolRecord> list) {

	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListStartExtend(PatrolRecord patrolRecord) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListEndExtend(PatrolRecord patrolRecord, List<PatrolRecord> list) {
		changSelfDefineDict(patrolRecord, list);
	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListByEqPatrolRecordStartExtend(PatrolRecord patrolRecord) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListByEqPatrolRecordEndExtend(PatrolRecord patrolRecord, List<PatrolRecord> list) {
		changSelfDefineDict(patrolRecord, list);
	}

	/**
	 * 查询明细End扩展
	 */
	public void selectDetailByPkPatrolRecordEndExtend(PatrolRecord patrolRecord) {
		List<PatrolRecord> list = new ArrayList();
		list.add(patrolRecord);
		changSelfDefineDict(patrolRecord, list);
		patrolRecord =list.get(0);
	}

	/**
	 * 查询导出列表Start扩展
	 */
	public void selectExportDetailListStartExtend(PatrolRecord patrolRecord) {

	}

	/**
	 * 查询导出列表End扩展
	 */
	public void selectExportDetailListEndExtend(PatrolRecord patrolRecord, List<PatrolRecord> list) {
		changSelfDefineDict(patrolRecord, list);
	}

	/**
	 * 处理不能通过sql进行转码的下拉框
	 */
	public void changSelfDefineDict(PatrolRecord patrolRecord, List<PatrolRecord> list) {
		//HashMap<String, String> patrolPlanIdMap = patrolPlanIdMap(patrolRecord);
		//HashMap<String, String> patrolPathIdMap = patrolPathIdMap(patrolRecord);
		//HashMap<String, String> patrolPointIdMap = patrolPointIdMap(patrolRecord);
		//HashMap<String, String> patrolUserIdMap = patrolUserIdMap(patrolRecord);
		//HashMap<String, String> patrolTaskIdMap = patrolTaskIdMap(patrolRecord);
		//HashMap<String, String> userIdMap = userIdMap(patrolRecord);
		//HashMap<String, String> deptIdMap = deptIdMap(patrolRecord);
		//list.forEach(e -> {
		//e.setPatrolPlanIdExtend(patrolPlanIdMap.get(e.getPatrolPlanId() + ""));
		//e.setPatrolPathIdExtend(patrolPathIdMap.get(e.getPatrolPathId() + ""));
		//e.setPatrolPointIdExtend(patrolPointIdMap.get(e.getPatrolPointId() + ""));
		//e.setPatrolUserIdExtend(patrolUserIdMap.get(e.getPatrolUserId() + ""));
		//e.setPatrolTaskIdExtend(patrolTaskIdMap.get(e.getPatrolTaskId() + ""));
		//e.setUserIdExtend(userIdMap.get(e.getUserId() + ""));
		//e.setDeptIdExtend(deptIdMap.get(e.getDeptId() + ""));
		//});
	}

	/**
	 * 校验数据唯一性
	 */
	public void checkDataUnique(PatrolRecord patrolRecord, String flag) {
	PatrolRecord qPatrolRecord = new PatrolRecord();
																				
	}

	/**
	 * 新增Start扩展
	 */
	public void insertStartExtend(PatrolRecord patrolRecord) {
		checkDataUnique(patrolRecord, "insert");
	}

	/**
	 * 新增End扩展
	 */
	public void insertEndExtend(PatrolRecord patrolRecord, int rows) {

	}

	/**
	 * 修改Start扩展
	 */
	public void updateStartExtend(PatrolRecord patrolRecord,PatrolRecord oldPatrolRecord) {
		checkDataUnique(patrolRecord, "update");
	}

	/**
	 * 修改End扩展
	 */
	public void updateEndExtend(PatrolRecord patrolRecord, PatrolRecord oldPatrolRecord, int rows) {

	}

	/**
	 * 批量删除Start扩展
	 */
	public void deleteByIdsStartExtend(PatrolRecord patrolRecord) {
		for (String id : patrolRecord.getIds()){
			//PatrolRecord tPatrolRecord = patrolRecordMapper.selectDataByPkPatrolRecord(id);
		}
	}

	/**
	 * 批量删除End扩展
	 */
	public void deleteByIdsEndExtend(PatrolRecord patrolRecord, int rows) {

	}

	/**
	 * 批量删除ByEqPatrolRecordStart扩展
	 */
	public void deleteByEqPatrolRecordStartExtend(PatrolRecord patrolRecord) {
	}

	/**
	 * 批量删除ByEqPatrolRecordEnd扩展
	 */
	public void deleteByEqPatrolRecordEndExtend(PatrolRecord patrolRecord, int rows) {

	}


	/**
     * 导出前校验扩展
     */
	public void exportDataCheckExtend(PatrolRecord patrolRecord) {
		int count = patrolRecordMapper.selectCountByLikePatrolRecord(patrolRecord);
		// 导出数据超过5万条，报错提示，各模块可以根据需要进行设置
		int limitCount = 50000;
		if (count > limitCount) {
			throw new ServiceException("当前导出的数据超过" + limitCount + "条！请修改查询条件后进行查询，确认数据条数不大于" + limitCount + "条后，再进行导出。");
		}
	}

	/**
     * 导出数据预处理扩展
     */
	public void exportDataDealExtend(PatrolRecord patrolRecord, List<PatrolRecord> list) {

	}

	/**
	 * 导入开始扩展
	 */
	public void importDataStartExtend(List<PatrolRecord> dataList, PatrolRecord pPatrolRecord, String operName, Date operDate) {
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
	public String importDataCheckExtend(boolean isUpdateSupport, PatrolRecord pPatrolRecord, PatrolRecord patrolRecord, String operName) {
		String checkMsg = "";
		if (isUpdateSupport) {

		} else {
			//  如果当前不进行更新数据，当前数据不存在，则直接新增
			try {
				checkDataUnique(patrolRecord, "insert");
			} catch (Exception e) {
				checkMsg = e.getMessage();
			}
		}

															return checkMsg;
	}

	/**
	 * 导入完成扩展
	 */
	public void importDataEndExtend(List<PatrolRecord> dataList, PatrolRecord pPatrolRecord, String operName, int successNum) {

	}

	/**
	 * 检查是否可以修改
	 */
	public void checkEditExtend(PatrolRecord pPatrolRecord) {
		//        if(pPatrolRecord.getXXX().equals("")) {
		//            throw new ServiceException("当前选中数据不可修改！");
		//        }
	}


}
