package com.ruoyi.autoee.dangerGoodsStockIn.service.impl;

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
import com.ruoyi.autoee.dangerGoodsStockIn.mapper.DangerGoodsStockInMapper;
import com.ruoyi.autoee.dangerGoodsStockIn.mapper.DangerGoodsStockInMapperExtend;
import com.ruoyi.autoee.dangerGoodsStockIn.domain.DangerGoodsStockIn;
import com.ruoyi.autoee.dangerGoodsStockIn.service.IDangerGoodsStockInService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.autoee.dangerGoodsInfo.domain.DangerGoodsInfo;
import com.ruoyi.autoee.dangerGoodsInfo.mapper.DangerGoodsInfoMapper;
import com.ruoyi.autoee.dangerGoodsInventory.domain.DangerGoodsInventory;
import com.ruoyi.autoee.dangerGoodsInventory.mapper.DangerGoodsInventoryMapper;

/**
 * 危化品入库记录Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class DangerGoodsStockInServiceExtend {
	private static final Logger logger = LoggerFactory.getLogger(DangerGoodsStockInServiceExtend.class);
	@Autowired
	private DangerGoodsStockInMapper dangerGoodsStockInMapper;
	@Autowired
	private DangerGoodsStockInMapperExtend dangerGoodsStockInMapperExtend;
	@Autowired
	private ISysDictDataService iSysDictDataService;
	@Autowired
	private ISysUserService iSysUserService;
	@Autowired
	private ISysDeptService iSysDeptService;
	@Autowired
	private DangerGoodsInventoryMapper dangerGoodsInventoryMapper;
	@Autowired
	private DangerGoodsInfoMapper dangerGoodsInfoMapper;

	/**
 	* 项目启动时，初始化参数到缓存
	 */
	@PostConstruct
	public void init() {
	}



  /**
     * 通过唯一字段查询危化品入库记录一条详细信息
     */
    public DangerGoodsStockIn selectOneDataByCache(DangerGoodsStockIn dangerGoodsStockIn)
    {
		DangerGoodsStockIn dangerGoodsStockInR = new DangerGoodsStockIn();
	    if (null != dangerGoodsStockInR) {
			return dangerGoodsStockInR;
	    } else {
			List<DangerGoodsStockIn> dangerGoodsStockInList = dangerGoodsStockInMapper.selectDetailListByLikeDangerGoodsStockIn(new DangerGoodsStockIn());
			if (dangerGoodsStockInList.size() == 1) {
				dangerGoodsStockInR = dangerGoodsStockInList.get(0);
				RedisCacheUtils.setMapValue("DataCache_Map_dangerGoodsStockIn", String.valueOf(dangerGoodsStockIn.getId()), JSONUtil.toJsonStr(dangerGoodsStockInR));
				return dangerGoodsStockInR;
			} else {
				throw new ServiceException("查询信息结果不唯一！");
			}
	    }
    }

	/**
	 * 查询列表Start扩展
	 */
	public void selectListStartExtend(DangerGoodsStockIn dangerGoodsStockIn) {

	}

	/**
	 * 查询列表End扩展
	 */
	public void selectListEndExtend(DangerGoodsStockIn dangerGoodsStockIn, List<DangerGoodsStockIn> list) {

	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListStartExtend(DangerGoodsStockIn dangerGoodsStockIn) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListEndExtend(DangerGoodsStockIn dangerGoodsStockIn, List<DangerGoodsStockIn> list) {
		changSelfDefineDict(dangerGoodsStockIn, list);
	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListByEqDangerGoodsStockInStartExtend(DangerGoodsStockIn dangerGoodsStockIn) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListByEqDangerGoodsStockInEndExtend(DangerGoodsStockIn dangerGoodsStockIn, List<DangerGoodsStockIn> list) {
		changSelfDefineDict(dangerGoodsStockIn, list);
	}

	/**
	 * 查询明细End扩展
	 */
	public void selectDetailByPkDangerGoodsStockInEndExtend(DangerGoodsStockIn dangerGoodsStockIn) {
		List<DangerGoodsStockIn> list = new ArrayList();
		list.add(dangerGoodsStockIn);
		changSelfDefineDict(dangerGoodsStockIn, list);
		dangerGoodsStockIn =list.get(0);
	}

	/**
	 * 查询导出列表Start扩展
	 */
	public void selectExportDetailListStartExtend(DangerGoodsStockIn dangerGoodsStockIn) {

	}

	/**
	 * 查询导出列表End扩展
	 */
	public void selectExportDetailListEndExtend(DangerGoodsStockIn dangerGoodsStockIn, List<DangerGoodsStockIn> list) {
		changSelfDefineDict(dangerGoodsStockIn, list);
	}

	/**
	 * 处理不能通过sql进行转码的下拉框
	 */
	public void changSelfDefineDict(DangerGoodsStockIn dangerGoodsStockIn, List<DangerGoodsStockIn> list) {
		//HashMap<String, String> dangerGoodsIdMap = dangerGoodsIdMap(dangerGoodsStockIn);
		//HashMap<String, String> userIdMap = userIdMap(dangerGoodsStockIn);
		//HashMap<String, String> deptIdMap = deptIdMap(dangerGoodsStockIn);
		//list.forEach(e -> {
		//e.setDangerGoodsIdExtend(dangerGoodsIdMap.get(e.getDangerGoodsId() + ""));
		//e.setUserIdExtend(userIdMap.get(e.getUserId() + ""));
		//e.setDeptIdExtend(deptIdMap.get(e.getDeptId() + ""));
		//});
	}

	/**
	 * 校验数据唯一性
	 */
	public void checkDataUnique(DangerGoodsStockIn dangerGoodsStockIn, String flag) {
	DangerGoodsStockIn qDangerGoodsStockIn = new DangerGoodsStockIn();

	}

	/**
	 * 新增Start扩展
	 */
	public void insertStartExtend(DangerGoodsStockIn dangerGoodsStockIn) {
		checkDataUnique(dangerGoodsStockIn, "insert");
	}

	/**
	 * 新增End扩展
	 * 入库新增时如果没有对应的库存信息，则新增一条库存，如果存在对应的物品库存则修改库存数量
	 */
	public void insertEndExtend(DangerGoodsStockIn dangerGoodsStockIn, int rows) {
		// 根据危化品ID查询库存信息
		DangerGoodsInventory queryInventory = new DangerGoodsInventory();
		queryInventory.setDangerGoodsId(dangerGoodsStockIn.getDangerGoodsId());
		List<DangerGoodsInventory> inventoryList = dangerGoodsInventoryMapper.selectDataListByEqDangerGoodsInventory(queryInventory);

		if (inventoryList != null && !inventoryList.isEmpty()) {
			// 库存已存在，更新库存数量
			DangerGoodsInventory updateInventory = inventoryList.get(0);
			Integer currentStock = updateInventory.getCurrentStock() != null ? updateInventory.getCurrentStock() : 0;
			updateInventory.setCurrentStock(currentStock + dangerGoodsStockIn.getQuantity());
			updateInventory.setUpdateBy(dangerGoodsStockIn.getUpdateBy());
			updateInventory.setUpdateTime(new Date());
			dangerGoodsInventoryMapper.updateNotNullValueByDangerGoodsInventory(updateInventory);
		} else {
			DangerGoodsInfo dangerGoodsInfo = dangerGoodsInfoMapper.selectDetailByPkDangerGoodsInfo(dangerGoodsStockIn.getDangerGoodsId());
			if (null == dangerGoodsInfo) {
				throw new ServiceException("当前入库的危化品，没有对应的物品信息！请确认后再进行操作。");
			}

			// 库存不存在，新增库存记录
			DangerGoodsInventory newInventory = new DangerGoodsInventory();
			newInventory.setDangerGoodsId(dangerGoodsStockIn.getDangerGoodsId());
			newInventory.setCurrentStock(dangerGoodsStockIn.getQuantity());
			newInventory.setDangerGoodsType(dangerGoodsInfo.getDangerGoodsType());
			newInventory.setGoodsUnit(dangerGoodsInfo.getGoodsUnit());
			newInventory.setSpecification(dangerGoodsInfo.getSpecification());
			newInventory.setUserId(dangerGoodsStockIn.getUserId());
			newInventory.setDeptId(dangerGoodsStockIn.getDeptId());
			newInventory.setCreateBy(dangerGoodsStockIn.getCreateBy());
			newInventory.setCreateTime(new Date());
			newInventory.setUpdateBy(dangerGoodsStockIn.getUpdateBy());
			newInventory.setUpdateTime(new Date());
			dangerGoodsInventoryMapper.insert(newInventory);
		}
	}

	/**
	 * 修改Start扩展
	 */
	public void updateStartExtend(DangerGoodsStockIn dangerGoodsStockIn,DangerGoodsStockIn oldDangerGoodsStockIn) {
		checkDataUnique(dangerGoodsStockIn, "update");
	}

	/**
	 * 修改End扩展
	 * 修改入库记录时，调整库存数量
	 */
	public void updateEndExtend(DangerGoodsStockIn dangerGoodsStockIn, DangerGoodsStockIn oldDangerGoodsStockIn, int rows) {
		// 查询库存信息
		DangerGoodsInventory queryInventory = new DangerGoodsInventory();
		queryInventory.setDangerGoodsId(dangerGoodsStockIn.getDangerGoodsId());
		List<DangerGoodsInventory> inventoryList = dangerGoodsInventoryMapper.selectDataListByEqDangerGoodsInventory(queryInventory);

		if (inventoryList != null && !inventoryList.isEmpty()) {
			DangerGoodsInventory updateInventory = inventoryList.get(0);
			// 计算库存变化量
			Integer oldQuantity = oldDangerGoodsStockIn.getQuantity() != null ? oldDangerGoodsStockIn.getQuantity() : 0;
			Integer newQuantity = dangerGoodsStockIn.getQuantity() != null ? dangerGoodsStockIn.getQuantity() : 0;
			Integer changeQuantity = newQuantity - oldQuantity;

			// 更新库存数量
			Integer currentStock = updateInventory.getCurrentStock() != null ? updateInventory.getCurrentStock() : 0;
			updateInventory.setCurrentStock(currentStock + changeQuantity);
			updateInventory.setUpdateBy(dangerGoodsStockIn.getUpdateBy());
			updateInventory.setUpdateTime(new Date());
			dangerGoodsInventoryMapper.updateNotNullValueByDangerGoodsInventory(updateInventory);
		}
	}

	/**
	 * 批量删除Start扩展
	 */
	public void deleteByIdsStartExtend(DangerGoodsStockIn dangerGoodsStockIn) {
		for (String id : dangerGoodsStockIn.getIds()){
			//DangerGoodsStockIn tDangerGoodsStockIn = dangerGoodsStockInMapper.selectDataByPkDangerGoodsStockIn(id);
		}
	}

	/**
	 * 批量删除End扩展
	 * 删除入库记录时，扣减库存数量
	 */
	public void deleteByIdsEndExtend(DangerGoodsStockIn dangerGoodsStockIn, int rows) {
		// 遍历所有要删除的入库记录
		for (String id : dangerGoodsStockIn.getIds()) {
			// 查询入库记录
			DangerGoodsStockIn stockIn = dangerGoodsStockInMapper.selectDataByPkDangerGoodsStockIn(Long.parseLong(id));
			if (stockIn != null) {
				// 查询对应的库存信息
				DangerGoodsInventory queryInventory = new DangerGoodsInventory();
				queryInventory.setDangerGoodsId(stockIn.getDangerGoodsId());
				List<DangerGoodsInventory> inventoryList = dangerGoodsInventoryMapper.selectDataListByEqDangerGoodsInventory(queryInventory);

				if (inventoryList != null && !inventoryList.isEmpty()) {
					// 扣减库存数量
					DangerGoodsInventory updateInventory = inventoryList.get(0);
					Integer currentStock = updateInventory.getCurrentStock() != null ? updateInventory.getCurrentStock() : 0;
					Integer newStock = currentStock - stockIn.getQuantity();
					// 确保库存不会变为负数
					updateInventory.setCurrentStock(Math.max(0, newStock));
					updateInventory.setUpdateBy(dangerGoodsStockIn.getUpdateBy());
					updateInventory.setUpdateTime(new Date());
					dangerGoodsInventoryMapper.updateNotNullValueByDangerGoodsInventory(updateInventory);
				}
			}
		}
	}

	/**
	 * 批量删除ByEqDangerGoodsStockInStart扩展
	 */
	public void deleteByEqDangerGoodsStockInStartExtend(DangerGoodsStockIn dangerGoodsStockIn) {
	}

	/**
	 * 批量删除ByEqDangerGoodsStockInEnd扩展
	 * 通过条件删除入库记录时，扣减库存数量
	 */
	public void deleteByEqDangerGoodsStockInEndExtend(DangerGoodsStockIn dangerGoodsStockIn, int rows) {
		// 根据条件查询要删除的入库记录
		List<DangerGoodsStockIn> stockInList = dangerGoodsStockInMapper.selectDataListByEqDangerGoodsStockIn(dangerGoodsStockIn);

		// 遍历所有要删除的入库记录
		for (DangerGoodsStockIn stockIn : stockInList) {
			// 查询对应的库存信息
			DangerGoodsInventory queryInventory = new DangerGoodsInventory();
			queryInventory.setDangerGoodsId(stockIn.getDangerGoodsId());
			List<DangerGoodsInventory> inventoryList = dangerGoodsInventoryMapper.selectDataListByEqDangerGoodsInventory(queryInventory);

			if (inventoryList != null && !inventoryList.isEmpty()) {
				// 扣减库存数量
				DangerGoodsInventory updateInventory = inventoryList.get(0);
				Integer currentStock = updateInventory.getCurrentStock() != null ? updateInventory.getCurrentStock() : 0;
				Integer newStock = currentStock - stockIn.getQuantity();
				// 确保库存不会变为负数
				updateInventory.setCurrentStock(Math.max(0, newStock));
				updateInventory.setUpdateBy(dangerGoodsStockIn.getUpdateBy());
				updateInventory.setUpdateTime(new Date());
				dangerGoodsInventoryMapper.updateNotNullValueByDangerGoodsInventory(updateInventory);
			}
		}
	}


	/**
     * 导出前校验扩展
     */
	public void exportDataCheckExtend(DangerGoodsStockIn dangerGoodsStockIn) {
		int count = dangerGoodsStockInMapper.selectCountByLikeDangerGoodsStockIn(dangerGoodsStockIn);
		// 导出数据超过5万条，报错提示，各模块可以根据需要进行设置
		int limitCount = 50000;
		if (count > limitCount) {
			throw new ServiceException("当前导出的数据超过" + limitCount + "条！请修改查询条件后进行查询，确认数据条数不大于" + limitCount + "条后，再进行导出。");
		}
	}

	/**
     * 导出数据预处理扩展
     */
	public void exportDataDealExtend(DangerGoodsStockIn dangerGoodsStockIn, List<DangerGoodsStockIn> list) {

	}

	/**
	 * 导入开始扩展
	 */
	public void importDataStartExtend(List<DangerGoodsStockIn> dataList, DangerGoodsStockIn pDangerGoodsStockIn, String operName, Date operDate) {
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
	public String importDataCheckExtend(boolean isUpdateSupport, DangerGoodsStockIn pDangerGoodsStockIn, DangerGoodsStockIn dangerGoodsStockIn, String operName) {
		String checkMsg = "";
		if (isUpdateSupport) {

		} else {
			//  如果当前不进行更新数据，当前数据不存在，则直接新增
			try {
				checkDataUnique(dangerGoodsStockIn, "insert");
			} catch (Exception e) {
				checkMsg = e.getMessage();
			}
		}

															return checkMsg;
	}

	/**
	 * 导入完成扩展
	 */
	public void importDataEndExtend(List<DangerGoodsStockIn> dataList, DangerGoodsStockIn pDangerGoodsStockIn, String operName, int successNum) {

	}

	/**
	 * 检查是否可以修改
	 */
	public void checkEditExtend(DangerGoodsStockIn pDangerGoodsStockIn) {
		//        if(pDangerGoodsStockIn.getXXX().equals("")) {
		//            throw new ServiceException("当前选中数据不可修改！");
		//        }
	}


}
