package com.ruoyi.autoee.dangerGoodsInventory.service.impl;

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
import com.ruoyi.autoee.dangerGoodsInventory.mapper.DangerGoodsInventoryMapper;
import com.ruoyi.autoee.dangerGoodsInventory.mapper.DangerGoodsInventoryMapperExtend;
import com.ruoyi.autoee.dangerGoodsInventory.domain.DangerGoodsInventory;
import com.ruoyi.autoee.dangerGoodsInventory.service.IDangerGoodsInventoryService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 危化品库存Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class DangerGoodsInventoryServiceExtend {
	private static final Logger logger = LoggerFactory.getLogger(DangerGoodsInventoryServiceExtend.class);
	@Autowired
	private DangerGoodsInventoryMapper dangerGoodsInventoryMapper;
	@Autowired
	private DangerGoodsInventoryMapperExtend dangerGoodsInventoryMapperExtend;
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
     * 通过唯一字段查询危化品库存一条详细信息
     */
    public DangerGoodsInventory selectOneDataByCache(DangerGoodsInventory dangerGoodsInventory)
    {
		DangerGoodsInventory dangerGoodsInventoryR = new DangerGoodsInventory();
	    if (null != dangerGoodsInventoryR) {
			return dangerGoodsInventoryR;
	    } else {
			List<DangerGoodsInventory> dangerGoodsInventoryList = dangerGoodsInventoryMapper.selectDetailListByLikeDangerGoodsInventory(new DangerGoodsInventory());
			if (dangerGoodsInventoryList.size() == 1) {
				dangerGoodsInventoryR = dangerGoodsInventoryList.get(0);
				RedisCacheUtils.setMapValue("DataCache_Map_dangerGoodsInventory", String.valueOf(dangerGoodsInventory.getId()), JSONUtil.toJsonStr(dangerGoodsInventoryR));
				return dangerGoodsInventoryR;
			} else {
				throw new ServiceException("查询信息结果不唯一！");
			}
	    }
    }

	/**
	 * 查询列表Start扩展
	 */
	public void selectListStartExtend(DangerGoodsInventory dangerGoodsInventory) {

	}

	/**
	 * 查询列表End扩展
	 */
	public void selectListEndExtend(DangerGoodsInventory dangerGoodsInventory, List<DangerGoodsInventory> list) {

	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListStartExtend(DangerGoodsInventory dangerGoodsInventory) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListEndExtend(DangerGoodsInventory dangerGoodsInventory, List<DangerGoodsInventory> list) {
		changSelfDefineDict(dangerGoodsInventory, list);
	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListByEqDangerGoodsInventoryStartExtend(DangerGoodsInventory dangerGoodsInventory) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListByEqDangerGoodsInventoryEndExtend(DangerGoodsInventory dangerGoodsInventory, List<DangerGoodsInventory> list) {
		changSelfDefineDict(dangerGoodsInventory, list);
	}

	/**
	 * 查询明细End扩展
	 */
	public void selectDetailByPkDangerGoodsInventoryEndExtend(DangerGoodsInventory dangerGoodsInventory) {
		List<DangerGoodsInventory> list = new ArrayList();
		list.add(dangerGoodsInventory);
		changSelfDefineDict(dangerGoodsInventory, list);
		dangerGoodsInventory =list.get(0);
	}

	/**
	 * 查询导出列表Start扩展
	 */
	public void selectExportDetailListStartExtend(DangerGoodsInventory dangerGoodsInventory) {

	}

	/**
	 * 查询导出列表End扩展
	 */
	public void selectExportDetailListEndExtend(DangerGoodsInventory dangerGoodsInventory, List<DangerGoodsInventory> list) {
		changSelfDefineDict(dangerGoodsInventory, list);
	}

	/**
	 * 处理不能通过sql进行转码的下拉框
	 */
	public void changSelfDefineDict(DangerGoodsInventory dangerGoodsInventory, List<DangerGoodsInventory> list) {
		//HashMap<String, String> dangerGoodsIdMap = dangerGoodsIdMap(dangerGoodsInventory);
		//HashMap<String, String> userIdMap = userIdMap(dangerGoodsInventory);
		//HashMap<String, String> deptIdMap = deptIdMap(dangerGoodsInventory);
		//list.forEach(e -> {
		//e.setDangerGoodsIdExtend(dangerGoodsIdMap.get(e.getDangerGoodsId() + ""));
		//e.setUserIdExtend(userIdMap.get(e.getUserId() + ""));
		//e.setDeptIdExtend(deptIdMap.get(e.getDeptId() + ""));
		//});
	}

	/**
	 * 校验数据唯一性
	 */
	public void checkDataUnique(DangerGoodsInventory dangerGoodsInventory, String flag) {
	DangerGoodsInventory qDangerGoodsInventory = new DangerGoodsInventory();
															
	}

	/**
	 * 新增Start扩展
	 */
	public void insertStartExtend(DangerGoodsInventory dangerGoodsInventory) {
		checkDataUnique(dangerGoodsInventory, "insert");
	}

	/**
	 * 新增End扩展
	 */
	public void insertEndExtend(DangerGoodsInventory dangerGoodsInventory, int rows) {

	}

	/**
	 * 修改Start扩展
	 */
	public void updateStartExtend(DangerGoodsInventory dangerGoodsInventory,DangerGoodsInventory oldDangerGoodsInventory) {
		checkDataUnique(dangerGoodsInventory, "update");
	}

	/**
	 * 修改End扩展
	 */
	public void updateEndExtend(DangerGoodsInventory dangerGoodsInventory, DangerGoodsInventory oldDangerGoodsInventory, int rows) {

	}

	/**
	 * 批量删除Start扩展
	 */
	public void deleteByIdsStartExtend(DangerGoodsInventory dangerGoodsInventory) {
		for (String id : dangerGoodsInventory.getIds()){
			//DangerGoodsInventory tDangerGoodsInventory = dangerGoodsInventoryMapper.selectDataByPkDangerGoodsInventory(id);
		}
	}

	/**
	 * 批量删除End扩展
	 */
	public void deleteByIdsEndExtend(DangerGoodsInventory dangerGoodsInventory, int rows) {

	}

	/**
	 * 批量删除ByEqDangerGoodsInventoryStart扩展
	 */
	public void deleteByEqDangerGoodsInventoryStartExtend(DangerGoodsInventory dangerGoodsInventory) {
	}

	/**
	 * 批量删除ByEqDangerGoodsInventoryEnd扩展
	 */
	public void deleteByEqDangerGoodsInventoryEndExtend(DangerGoodsInventory dangerGoodsInventory, int rows) {

	}


	/**
     * 导出前校验扩展
     */
	public void exportDataCheckExtend(DangerGoodsInventory dangerGoodsInventory) {
		int count = dangerGoodsInventoryMapper.selectCountByLikeDangerGoodsInventory(dangerGoodsInventory);
		// 导出数据超过5万条，报错提示，各模块可以根据需要进行设置
		int limitCount = 50000;
		if (count > limitCount) {
			throw new ServiceException("当前导出的数据超过" + limitCount + "条！请修改查询条件后进行查询，确认数据条数不大于" + limitCount + "条后，再进行导出。");
		}
	}

	/**
     * 导出数据预处理扩展
     */
	public void exportDataDealExtend(DangerGoodsInventory dangerGoodsInventory, List<DangerGoodsInventory> list) {

	}

	/**
	 * 导入开始扩展
	 */
	public void importDataStartExtend(List<DangerGoodsInventory> dataList, DangerGoodsInventory pDangerGoodsInventory, String operName, Date operDate) {
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
	public String importDataCheckExtend(boolean isUpdateSupport, DangerGoodsInventory pDangerGoodsInventory, DangerGoodsInventory dangerGoodsInventory, String operName) {
		String checkMsg = "";
		if (isUpdateSupport) {

		} else {
			//  如果当前不进行更新数据，当前数据不存在，则直接新增
			try {
				checkDataUnique(dangerGoodsInventory, "insert");
			} catch (Exception e) {
				checkMsg = e.getMessage();
			}
		}

															return checkMsg;
	}

	/**
	 * 导入完成扩展
	 */
	public void importDataEndExtend(List<DangerGoodsInventory> dataList, DangerGoodsInventory pDangerGoodsInventory, String operName, int successNum) {

	}

	/**
	 * 检查是否可以修改
	 */
	public void checkEditExtend(DangerGoodsInventory pDangerGoodsInventory) {
		//        if(pDangerGoodsInventory.getXXX().equals("")) {
		//            throw new ServiceException("当前选中数据不可修改！");
		//        }
	}


}
