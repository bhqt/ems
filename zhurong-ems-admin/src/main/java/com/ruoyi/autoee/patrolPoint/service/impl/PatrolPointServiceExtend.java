package com.ruoyi.autoee.patrolPoint.service.impl;

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
import com.ruoyi.autoee.patrolPoint.mapper.PatrolPointMapper;
import com.ruoyi.autoee.patrolPoint.mapper.PatrolPointMapperExtend;
import com.ruoyi.autoee.patrolPoint.domain.PatrolPoint;
import com.ruoyi.autoee.patrolPoint.service.IPatrolPointService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 巡更点位Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class PatrolPointServiceExtend {
	private static final Logger logger = LoggerFactory.getLogger(PatrolPointServiceExtend.class);
	@Autowired
	private PatrolPointMapper patrolPointMapper;
	@Autowired
	private PatrolPointMapperExtend patrolPointMapperExtend;
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
     * 通过唯一字段查询巡更点位一条详细信息
     */
    public PatrolPoint selectOneDataByCache(PatrolPoint patrolPoint)
    {
		PatrolPoint patrolPointR = new PatrolPoint();
	    if (null != patrolPointR) {
			return patrolPointR;
	    } else {
			List<PatrolPoint> patrolPointList = patrolPointMapper.selectDetailListByLikePatrolPoint(new PatrolPoint());
			if (patrolPointList.size() == 1) {
				patrolPointR = patrolPointList.get(0);
				RedisCacheUtils.setMapValue("DataCache_Map_patrolPoint", String.valueOf(patrolPoint.getId()), JSONUtil.toJsonStr(patrolPointR));
				return patrolPointR;
			} else {
				throw new ServiceException("查询信息结果不唯一！");
			}
	    }
    }

	/**
	 * 查询列表Start扩展
	 */
	public void selectListStartExtend(PatrolPoint patrolPoint) {

	}

	/**
	 * 查询列表End扩展
	 */
	public void selectListEndExtend(PatrolPoint patrolPoint, List<PatrolPoint> list) {

	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListStartExtend(PatrolPoint patrolPoint) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListEndExtend(PatrolPoint patrolPoint, List<PatrolPoint> list) {
		changSelfDefineDict(patrolPoint, list);
	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListByEqPatrolPointStartExtend(PatrolPoint patrolPoint) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListByEqPatrolPointEndExtend(PatrolPoint patrolPoint, List<PatrolPoint> list) {
		changSelfDefineDict(patrolPoint, list);
	}

	/**
	 * 查询明细End扩展
	 */
	public void selectDetailByPkPatrolPointEndExtend(PatrolPoint patrolPoint) {
		List<PatrolPoint> list = new ArrayList();
		list.add(patrolPoint);
		changSelfDefineDict(patrolPoint, list);
		patrolPoint =list.get(0);
	}

	/**
	 * 查询导出列表Start扩展
	 */
	public void selectExportDetailListStartExtend(PatrolPoint patrolPoint) {

	}

	/**
	 * 查询导出列表End扩展
	 */
	public void selectExportDetailListEndExtend(PatrolPoint patrolPoint, List<PatrolPoint> list) {
		changSelfDefineDict(patrolPoint, list);
	}

	/**
	 * 处理不能通过sql进行转码的下拉框
	 */
	public void changSelfDefineDict(PatrolPoint patrolPoint, List<PatrolPoint> list) {
		//HashMap<String, String> userIdMap = userIdMap(patrolPoint);
		//HashMap<String, String> deptIdMap = deptIdMap(patrolPoint);
		//list.forEach(e -> {
		//e.setUserIdExtend(userIdMap.get(e.getUserId() + ""));
		//e.setDeptIdExtend(deptIdMap.get(e.getDeptId() + ""));
		//});
	}

	/**
	 * 校验数据唯一性
	 */
	public void checkDataUnique(PatrolPoint patrolPoint, String flag) {
	PatrolPoint qPatrolPoint = new PatrolPoint();
													
	}

	/**
	 * 新增Start扩展
	 */
	public void insertStartExtend(PatrolPoint patrolPoint) {
		checkDataUnique(patrolPoint, "insert");
	}

	/**
	 * 新增End扩展
	 */
	public void insertEndExtend(PatrolPoint patrolPoint, int rows) {

	}

	/**
	 * 修改Start扩展
	 */
	public void updateStartExtend(PatrolPoint patrolPoint,PatrolPoint oldPatrolPoint) {
		checkDataUnique(patrolPoint, "update");
	}

	/**
	 * 修改End扩展
	 */
	public void updateEndExtend(PatrolPoint patrolPoint, PatrolPoint oldPatrolPoint, int rows) {

	}

	/**
	 * 批量删除Start扩展
	 */
	public void deleteByIdsStartExtend(PatrolPoint patrolPoint) {
		for (String id : patrolPoint.getIds()){
			//PatrolPoint tPatrolPoint = patrolPointMapper.selectDataByPkPatrolPoint(id);
		}
	}

	/**
	 * 批量删除End扩展
	 */
	public void deleteByIdsEndExtend(PatrolPoint patrolPoint, int rows) {

	}

	/**
	 * 批量删除ByEqPatrolPointStart扩展
	 */
	public void deleteByEqPatrolPointStartExtend(PatrolPoint patrolPoint) {
	}

	/**
	 * 批量删除ByEqPatrolPointEnd扩展
	 */
	public void deleteByEqPatrolPointEndExtend(PatrolPoint patrolPoint, int rows) {

	}


	/**
     * 导出前校验扩展
     */
	public void exportDataCheckExtend(PatrolPoint patrolPoint) {
		int count = patrolPointMapper.selectCountByLikePatrolPoint(patrolPoint);
		// 导出数据超过5万条，报错提示，各模块可以根据需要进行设置
		int limitCount = 50000;
		if (count > limitCount) {
			throw new ServiceException("当前导出的数据超过" + limitCount + "条！请修改查询条件后进行查询，确认数据条数不大于" + limitCount + "条后，再进行导出。");
		}
	}

	/**
     * 导出数据预处理扩展
     */
	public void exportDataDealExtend(PatrolPoint patrolPoint, List<PatrolPoint> list) {

	}

	/**
	 * 导入开始扩展
	 */
	public void importDataStartExtend(List<PatrolPoint> dataList, PatrolPoint pPatrolPoint, String operName, Date operDate) {
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
	public String importDataCheckExtend(boolean isUpdateSupport, PatrolPoint pPatrolPoint, PatrolPoint patrolPoint, String operName) {
		String checkMsg = "";
		if (isUpdateSupport) {

		} else {
			//  如果当前不进行更新数据，当前数据不存在，则直接新增
			try {
				checkDataUnique(patrolPoint, "insert");
			} catch (Exception e) {
				checkMsg = e.getMessage();
			}
		}

															return checkMsg;
	}

	/**
	 * 导入完成扩展
	 */
	public void importDataEndExtend(List<PatrolPoint> dataList, PatrolPoint pPatrolPoint, String operName, int successNum) {

	}

	/**
	 * 检查是否可以修改
	 */
	public void checkEditExtend(PatrolPoint pPatrolPoint) {
		//        if(pPatrolPoint.getXXX().equals("")) {
		//            throw new ServiceException("当前选中数据不可修改！");
		//        }
	}


}
