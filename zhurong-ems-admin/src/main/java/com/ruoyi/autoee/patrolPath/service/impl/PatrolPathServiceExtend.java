package com.ruoyi.autoee.patrolPath.service.impl;

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
import com.ruoyi.autoee.patrolPath.mapper.PatrolPathMapper;
import com.ruoyi.autoee.patrolPath.mapper.PatrolPathMapperExtend;
import com.ruoyi.autoee.patrolPath.domain.PatrolPath;
import com.ruoyi.autoee.patrolPath.service.IPatrolPathService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 巡更路线Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class PatrolPathServiceExtend {
	private static final Logger logger = LoggerFactory.getLogger(PatrolPathServiceExtend.class);
	@Autowired
	private PatrolPathMapper patrolPathMapper;
	@Autowired
	private PatrolPathMapperExtend patrolPathMapperExtend;
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
     * 通过唯一字段查询巡更路线一条详细信息
     */
    public PatrolPath selectOneDataByCache(PatrolPath patrolPath)
    {
		PatrolPath patrolPathR = new PatrolPath();
	    if (null != patrolPathR) {
			return patrolPathR;
	    } else {
			List<PatrolPath> patrolPathList = patrolPathMapper.selectDetailListByLikePatrolPath(new PatrolPath());
			if (patrolPathList.size() == 1) {
				patrolPathR = patrolPathList.get(0);
				RedisCacheUtils.setMapValue("DataCache_Map_patrolPath", String.valueOf(patrolPath.getId()), JSONUtil.toJsonStr(patrolPathR));
				return patrolPathR;
			} else {
				throw new ServiceException("查询信息结果不唯一！");
			}
	    }
    }

	/**
	 * 查询列表Start扩展
	 */
	public void selectListStartExtend(PatrolPath patrolPath) {

	}

	/**
	 * 查询列表End扩展
	 */
	public void selectListEndExtend(PatrolPath patrolPath, List<PatrolPath> list) {

	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListStartExtend(PatrolPath patrolPath) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListEndExtend(PatrolPath patrolPath, List<PatrolPath> list) {
		changSelfDefineDict(patrolPath, list);
	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListByEqPatrolPathStartExtend(PatrolPath patrolPath) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListByEqPatrolPathEndExtend(PatrolPath patrolPath, List<PatrolPath> list) {
		changSelfDefineDict(patrolPath, list);
	}

	/**
	 * 查询明细End扩展
	 */
	public void selectDetailByPkPatrolPathEndExtend(PatrolPath patrolPath) {
		List<PatrolPath> list = new ArrayList();
		list.add(patrolPath);
		changSelfDefineDict(patrolPath, list);
		patrolPath =list.get(0);
	}

	/**
	 * 查询导出列表Start扩展
	 */
	public void selectExportDetailListStartExtend(PatrolPath patrolPath) {

	}

	/**
	 * 查询导出列表End扩展
	 */
	public void selectExportDetailListEndExtend(PatrolPath patrolPath, List<PatrolPath> list) {
		changSelfDefineDict(patrolPath, list);
	}

	/**
	 * 处理不能通过sql进行转码的下拉框
	 */
	public void changSelfDefineDict(PatrolPath patrolPath, List<PatrolPath> list) {
		//HashMap<String, String> pointListMap = pointListMap(patrolPath);
		//HashMap<String, String> userIdMap = userIdMap(patrolPath);
		//HashMap<String, String> deptIdMap = deptIdMap(patrolPath);
		//list.forEach(e -> {
		//e.setPointListExtend(pointListMap.get(e.getPointList() + ""));
		//e.setUserIdExtend(userIdMap.get(e.getUserId() + ""));
		//e.setDeptIdExtend(deptIdMap.get(e.getDeptId() + ""));
		//});
	}

	/**
	 * 校验数据唯一性
	 */
	public void checkDataUnique(PatrolPath patrolPath, String flag) {
	PatrolPath qPatrolPath = new PatrolPath();
					qPatrolPath.setRouteCode(patrolPath.getRouteCode());
													
		List<PatrolPath> list = patrolPathMapper.selectDataListByEqPatrolPath(qPatrolPath);
		if (("insert".equals(flag) && list.size() > 0) || "update".equals(flag) && list.size() > 0 && !list.get(0).getId().equals(patrolPath.getId())){
		String msg = "";
					msg += "路线编号[" + patrolPath.getRouteCode() + "]，";
															msg += "在系统中已经存在！请直接在系统中进行维护。";
		logger.error("【失败】-" + msg);
		throw new ServiceException(msg);
	}
	}

	/**
	 * 新增Start扩展
	 */
	public void insertStartExtend(PatrolPath patrolPath) {
		checkDataUnique(patrolPath, "insert");
	}

	/**
	 * 新增End扩展
	 */
	public void insertEndExtend(PatrolPath patrolPath, int rows) {

	}

	/**
	 * 修改Start扩展
	 */
	public void updateStartExtend(PatrolPath patrolPath,PatrolPath oldPatrolPath) {
		checkDataUnique(patrolPath, "update");
	}

	/**
	 * 修改End扩展
	 */
	public void updateEndExtend(PatrolPath patrolPath, PatrolPath oldPatrolPath, int rows) {

	}

	/**
	 * 批量删除Start扩展
	 */
	public void deleteByIdsStartExtend(PatrolPath patrolPath) {
		for (String id : patrolPath.getIds()){
			//PatrolPath tPatrolPath = patrolPathMapper.selectDataByPkPatrolPath(id);
		}
	}

	/**
	 * 批量删除End扩展
	 */
	public void deleteByIdsEndExtend(PatrolPath patrolPath, int rows) {

	}

	/**
	 * 批量删除ByEqPatrolPathStart扩展
	 */
	public void deleteByEqPatrolPathStartExtend(PatrolPath patrolPath) {
	}

	/**
	 * 批量删除ByEqPatrolPathEnd扩展
	 */
	public void deleteByEqPatrolPathEndExtend(PatrolPath patrolPath, int rows) {

	}


	/**
     * 导出前校验扩展
     */
	public void exportDataCheckExtend(PatrolPath patrolPath) {
		int count = patrolPathMapper.selectCountByLikePatrolPath(patrolPath);
		// 导出数据超过5万条，报错提示，各模块可以根据需要进行设置
		int limitCount = 50000;
		if (count > limitCount) {
			throw new ServiceException("当前导出的数据超过" + limitCount + "条！请修改查询条件后进行查询，确认数据条数不大于" + limitCount + "条后，再进行导出。");
		}
	}

	/**
     * 导出数据预处理扩展
     */
	public void exportDataDealExtend(PatrolPath patrolPath, List<PatrolPath> list) {

	}

	/**
	 * 导入开始扩展
	 */
	public void importDataStartExtend(List<PatrolPath> dataList, PatrolPath pPatrolPath, String operName, Date operDate) {
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
	public String importDataCheckExtend(boolean isUpdateSupport, PatrolPath pPatrolPath, PatrolPath patrolPath, String operName) {
		String checkMsg = "";
		if (isUpdateSupport) {

		} else {
			//  如果当前不进行更新数据，当前数据不存在，则直接新增
			try {
				checkDataUnique(patrolPath, "insert");
			} catch (Exception e) {
				checkMsg = e.getMessage();
			}
		}

															return checkMsg;
	}

	/**
	 * 导入完成扩展
	 */
	public void importDataEndExtend(List<PatrolPath> dataList, PatrolPath pPatrolPath, String operName, int successNum) {

	}

	/**
	 * 检查是否可以修改
	 */
	public void checkEditExtend(PatrolPath pPatrolPath) {
		//        if(pPatrolPath.getXXX().equals("")) {
		//            throw new ServiceException("当前选中数据不可修改！");
		//        }
	}


}
