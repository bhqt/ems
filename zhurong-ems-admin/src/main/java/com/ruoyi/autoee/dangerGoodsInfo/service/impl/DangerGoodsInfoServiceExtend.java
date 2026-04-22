package com.ruoyi.autoee.dangerGoodsInfo.service.impl;

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
import com.ruoyi.autoee.dangerGoodsInfo.mapper.DangerGoodsInfoMapper;
import com.ruoyi.autoee.dangerGoodsInfo.mapper.DangerGoodsInfoMapperExtend;
import com.ruoyi.autoee.dangerGoodsInfo.domain.DangerGoodsInfo;
import com.ruoyi.autoee.dangerGoodsInfo.service.IDangerGoodsInfoService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 危化品信息管理Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class DangerGoodsInfoServiceExtend {
	private static final Logger logger = LoggerFactory.getLogger(DangerGoodsInfoServiceExtend.class);
	@Autowired
	private DangerGoodsInfoMapper dangerGoodsInfoMapper;
	@Autowired
	private DangerGoodsInfoMapperExtend dangerGoodsInfoMapperExtend;
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
     * 通过唯一字段查询危化品信息管理一条详细信息
     */
    public DangerGoodsInfo selectOneDataByCache(DangerGoodsInfo dangerGoodsInfo)
    {
		DangerGoodsInfo dangerGoodsInfoR = new DangerGoodsInfo();
	    if (null != dangerGoodsInfoR) {
			return dangerGoodsInfoR;
	    } else {
			List<DangerGoodsInfo> dangerGoodsInfoList = dangerGoodsInfoMapper.selectDetailListByLikeDangerGoodsInfo(new DangerGoodsInfo());
			if (dangerGoodsInfoList.size() == 1) {
				dangerGoodsInfoR = dangerGoodsInfoList.get(0);
				RedisCacheUtils.setMapValue("DataCache_Map_dangerGoodsInfo", String.valueOf(dangerGoodsInfo.getId()), JSONUtil.toJsonStr(dangerGoodsInfoR));
				return dangerGoodsInfoR;
			} else {
				throw new ServiceException("查询信息结果不唯一！");
			}
	    }
    }

	/**
	 * 查询列表Start扩展
	 */
	public void selectListStartExtend(DangerGoodsInfo dangerGoodsInfo) {

	}

	/**
	 * 查询列表End扩展
	 */
	public void selectListEndExtend(DangerGoodsInfo dangerGoodsInfo, List<DangerGoodsInfo> list) {

	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListStartExtend(DangerGoodsInfo dangerGoodsInfo) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListEndExtend(DangerGoodsInfo dangerGoodsInfo, List<DangerGoodsInfo> list) {
		changSelfDefineDict(dangerGoodsInfo, list);
	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListByEqDangerGoodsInfoStartExtend(DangerGoodsInfo dangerGoodsInfo) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListByEqDangerGoodsInfoEndExtend(DangerGoodsInfo dangerGoodsInfo, List<DangerGoodsInfo> list) {
		changSelfDefineDict(dangerGoodsInfo, list);
	}

	/**
	 * 查询明细End扩展
	 */
	public void selectDetailByPkDangerGoodsInfoEndExtend(DangerGoodsInfo dangerGoodsInfo) {
		List<DangerGoodsInfo> list = new ArrayList();
		list.add(dangerGoodsInfo);
		changSelfDefineDict(dangerGoodsInfo, list);
		dangerGoodsInfo =list.get(0);
	}

	/**
	 * 查询导出列表Start扩展
	 */
	public void selectExportDetailListStartExtend(DangerGoodsInfo dangerGoodsInfo) {

	}

	/**
	 * 查询导出列表End扩展
	 */
	public void selectExportDetailListEndExtend(DangerGoodsInfo dangerGoodsInfo, List<DangerGoodsInfo> list) {
		changSelfDefineDict(dangerGoodsInfo, list);
	}

	/**
	 * 处理不能通过sql进行转码的下拉框
	 */
	public void changSelfDefineDict(DangerGoodsInfo dangerGoodsInfo, List<DangerGoodsInfo> list) {
		//HashMap<String, String> userIdMap = userIdMap(dangerGoodsInfo);
		//HashMap<String, String> deptIdMap = deptIdMap(dangerGoodsInfo);
		//list.forEach(e -> {
		//e.setUserIdExtend(userIdMap.get(e.getUserId() + ""));
		//e.setDeptIdExtend(deptIdMap.get(e.getDeptId() + ""));
		//});
	}

	/**
	 * 校验数据唯一性
	 */
	public void checkDataUnique(DangerGoodsInfo dangerGoodsInfo, String flag) {
	DangerGoodsInfo qDangerGoodsInfo = new DangerGoodsInfo();
					qDangerGoodsInfo.setDangerGoodsName(dangerGoodsInfo.getDangerGoodsName());
														
		List<DangerGoodsInfo> list = dangerGoodsInfoMapper.selectDataListByEqDangerGoodsInfo(qDangerGoodsInfo);
		if (("insert".equals(flag) && list.size() > 0) || "update".equals(flag) && list.size() > 0 && !list.get(0).getId().equals(dangerGoodsInfo.getId())){
		String msg = "";
					msg += "危化品名称[" + dangerGoodsInfo.getDangerGoodsName() + "]，";
																msg += "在系统中已经存在！请直接在系统中进行维护。";
		logger.error("【失败】-" + msg);
		throw new ServiceException(msg);
	}
	}

	/**
	 * 新增Start扩展
	 */
	public void insertStartExtend(DangerGoodsInfo dangerGoodsInfo) {
		checkDataUnique(dangerGoodsInfo, "insert");
	}

	/**
	 * 新增End扩展
	 */
	public void insertEndExtend(DangerGoodsInfo dangerGoodsInfo, int rows) {

	}

	/**
	 * 修改Start扩展
	 */
	public void updateStartExtend(DangerGoodsInfo dangerGoodsInfo,DangerGoodsInfo oldDangerGoodsInfo) {
		checkDataUnique(dangerGoodsInfo, "update");
	}

	/**
	 * 修改End扩展
	 */
	public void updateEndExtend(DangerGoodsInfo dangerGoodsInfo, DangerGoodsInfo oldDangerGoodsInfo, int rows) {

	}

	/**
	 * 批量删除Start扩展
	 */
	public void deleteByIdsStartExtend(DangerGoodsInfo dangerGoodsInfo) {
		for (String id : dangerGoodsInfo.getIds()){
			//DangerGoodsInfo tDangerGoodsInfo = dangerGoodsInfoMapper.selectDataByPkDangerGoodsInfo(id);
		}
	}

	/**
	 * 批量删除End扩展
	 */
	public void deleteByIdsEndExtend(DangerGoodsInfo dangerGoodsInfo, int rows) {

	}

	/**
	 * 批量删除ByEqDangerGoodsInfoStart扩展
	 */
	public void deleteByEqDangerGoodsInfoStartExtend(DangerGoodsInfo dangerGoodsInfo) {
	}

	/**
	 * 批量删除ByEqDangerGoodsInfoEnd扩展
	 */
	public void deleteByEqDangerGoodsInfoEndExtend(DangerGoodsInfo dangerGoodsInfo, int rows) {

	}


	/**
     * 导出前校验扩展
     */
	public void exportDataCheckExtend(DangerGoodsInfo dangerGoodsInfo) {
		int count = dangerGoodsInfoMapper.selectCountByLikeDangerGoodsInfo(dangerGoodsInfo);
		// 导出数据超过5万条，报错提示，各模块可以根据需要进行设置
		int limitCount = 50000;
		if (count > limitCount) {
			throw new ServiceException("当前导出的数据超过" + limitCount + "条！请修改查询条件后进行查询，确认数据条数不大于" + limitCount + "条后，再进行导出。");
		}
	}

	/**
     * 导出数据预处理扩展
     */
	public void exportDataDealExtend(DangerGoodsInfo dangerGoodsInfo, List<DangerGoodsInfo> list) {

	}

	/**
	 * 导入开始扩展
	 */
	public void importDataStartExtend(List<DangerGoodsInfo> dataList, DangerGoodsInfo pDangerGoodsInfo, String operName, Date operDate) {
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
	public String importDataCheckExtend(boolean isUpdateSupport, DangerGoodsInfo pDangerGoodsInfo, DangerGoodsInfo dangerGoodsInfo, String operName) {
		String checkMsg = "";
		if (isUpdateSupport) {

		} else {
			//  如果当前不进行更新数据，当前数据不存在，则直接新增
			try {
				checkDataUnique(dangerGoodsInfo, "insert");
			} catch (Exception e) {
				checkMsg = e.getMessage();
			}
		}

															return checkMsg;
	}

	/**
	 * 导入完成扩展
	 */
	public void importDataEndExtend(List<DangerGoodsInfo> dataList, DangerGoodsInfo pDangerGoodsInfo, String operName, int successNum) {

	}

	/**
	 * 检查是否可以修改
	 */
	public void checkEditExtend(DangerGoodsInfo pDangerGoodsInfo) {
		//        if(pDangerGoodsInfo.getXXX().equals("")) {
		//            throw new ServiceException("当前选中数据不可修改！");
		//        }
	}


}
