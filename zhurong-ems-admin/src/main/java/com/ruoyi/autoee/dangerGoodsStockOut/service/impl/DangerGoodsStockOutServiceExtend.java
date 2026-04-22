package com.ruoyi.autoee.dangerGoodsStockOut.service.impl;

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
import com.ruoyi.autoee.dangerGoodsStockOut.mapper.DangerGoodsStockOutMapper;
import com.ruoyi.autoee.dangerGoodsStockOut.mapper.DangerGoodsStockOutMapperExtend;
import com.ruoyi.autoee.dangerGoodsStockOut.domain.DangerGoodsStockOut;
import com.ruoyi.autoee.dangerGoodsStockOut.service.IDangerGoodsStockOutService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.autoee.dangerGoodsInventory.domain.DangerGoodsInventory;
import com.ruoyi.autoee.dangerGoodsInventory.mapper.DangerGoodsInventoryMapper;

/**
 * 危化品出库记录Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class DangerGoodsStockOutServiceExtend {
	private static final Logger logger = LoggerFactory.getLogger(DangerGoodsStockOutServiceExtend.class);
	@Autowired
	private DangerGoodsStockOutMapper dangerGoodsStockOutMapper;
	@Autowired
	private DangerGoodsStockOutMapperExtend dangerGoodsStockOutMapperExtend;
	@Autowired
	private ISysDictDataService iSysDictDataService;
	@Autowired
	private ISysUserService iSysUserService;
	@Autowired
	private ISysDeptService iSysDeptService;
	@Autowired
	private DangerGoodsInventoryMapper dangerGoodsInventoryMapper;

	/**
 	* 项目启动时，初始化参数到缓存
	 */
	@PostConstruct
	public void init() {
	}



  /**
     * 通过唯一字段查询危化品出库记录一条详细信息
     */
    public DangerGoodsStockOut selectOneDataByCache(DangerGoodsStockOut dangerGoodsStockOut)
    {
		DangerGoodsStockOut dangerGoodsStockOutR = new DangerGoodsStockOut();
	    if (null != dangerGoodsStockOutR) {
			return dangerGoodsStockOutR;
	    } else {
			List<DangerGoodsStockOut> dangerGoodsStockOutList = dangerGoodsStockOutMapper.selectDetailListByLikeDangerGoodsStockOut(new DangerGoodsStockOut());
			if (dangerGoodsStockOutList.size() == 1) {
				dangerGoodsStockOutR = dangerGoodsStockOutList.get(0);
				RedisCacheUtils.setMapValue("DataCache_Map_dangerGoodsStockOut", String.valueOf(dangerGoodsStockOut.getId()), JSONUtil.toJsonStr(dangerGoodsStockOutR));
				return dangerGoodsStockOutR;
			} else {
				throw new ServiceException("查询信息结果不唯一！");
			}
	    }
    }

	/**
	 * 查询列表Start扩展
	 */
	public void selectListStartExtend(DangerGoodsStockOut dangerGoodsStockOut) {

	}

	/**
	 * 查询列表End扩展
	 */
	public void selectListEndExtend(DangerGoodsStockOut dangerGoodsStockOut, List<DangerGoodsStockOut> list) {

	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListStartExtend(DangerGoodsStockOut dangerGoodsStockOut) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListEndExtend(DangerGoodsStockOut dangerGoodsStockOut, List<DangerGoodsStockOut> list) {
		changSelfDefineDict(dangerGoodsStockOut, list);
	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListByEqDangerGoodsStockOutStartExtend(DangerGoodsStockOut dangerGoodsStockOut) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListByEqDangerGoodsStockOutEndExtend(DangerGoodsStockOut dangerGoodsStockOut, List<DangerGoodsStockOut> list) {
		changSelfDefineDict(dangerGoodsStockOut, list);
	}

	/**
	 * 查询明细End扩展
	 */
	public void selectDetailByPkDangerGoodsStockOutEndExtend(DangerGoodsStockOut dangerGoodsStockOut) {
		List<DangerGoodsStockOut> list = new ArrayList();
		list.add(dangerGoodsStockOut);
		changSelfDefineDict(dangerGoodsStockOut, list);
		dangerGoodsStockOut =list.get(0);
	}

	/**
	 * 查询导出列表Start扩展
	 */
	public void selectExportDetailListStartExtend(DangerGoodsStockOut dangerGoodsStockOut) {

	}

	/**
	 * 查询导出列表End扩展
	 */
	public void selectExportDetailListEndExtend(DangerGoodsStockOut dangerGoodsStockOut, List<DangerGoodsStockOut> list) {
		changSelfDefineDict(dangerGoodsStockOut, list);
	}

	/**
	 * 处理不能通过sql进行转码的下拉框
	 */
	public void changSelfDefineDict(DangerGoodsStockOut dangerGoodsStockOut, List<DangerGoodsStockOut> list) {
		//HashMap<String, String> dangerGoodsStockInIdMap = dangerGoodsStockInIdMap(dangerGoodsStockOut);
		//HashMap<String, String> dangerGoodsIdMap = dangerGoodsIdMap(dangerGoodsStockOut);
		//HashMap<String, String> userIdMap = userIdMap(dangerGoodsStockOut);
		//HashMap<String, String> deptIdMap = deptIdMap(dangerGoodsStockOut);
		//list.forEach(e -> {
		//e.setDangerGoodsStockInIdExtend(dangerGoodsStockInIdMap.get(e.getDangerGoodsStockInId() + ""));
		//e.setDangerGoodsIdExtend(dangerGoodsIdMap.get(e.getDangerGoodsId() + ""));
		//e.setUserIdExtend(userIdMap.get(e.getUserId() + ""));
		//e.setDeptIdExtend(deptIdMap.get(e.getDeptId() + ""));
		//});
	}

	/**
	 * 校验数据唯一性
	 */
	public void checkDataUnique(DangerGoodsStockOut dangerGoodsStockOut, String flag) {
	DangerGoodsStockOut qDangerGoodsStockOut = new DangerGoodsStockOut();
															
	}

	/**
	 * 新增Start扩展
	 */
	public void insertStartExtend(DangerGoodsStockOut dangerGoodsStockOut) {
		checkDataUnique(dangerGoodsStockOut, "insert");
	}

	/**
	 * 新增End扩展
	 * 出库新增时扣减库存数量
	 */
	public void insertEndExtend(DangerGoodsStockOut dangerGoodsStockOut, int rows) {
		// 根据危化品ID查询库存信息
		DangerGoodsInventory queryInventory = new DangerGoodsInventory();
		queryInventory.setDangerGoodsId(dangerGoodsStockOut.getDangerGoodsId());
		List<DangerGoodsInventory> inventoryList = dangerGoodsInventoryMapper.selectDataListByEqDangerGoodsInventory(queryInventory);

		if (inventoryList != null && !inventoryList.isEmpty()) {
			// 库存已存在，扣减库存数量
			DangerGoodsInventory updateInventory = inventoryList.get(0);
			Integer currentStock = updateInventory.getCurrentStock() != null ? updateInventory.getCurrentStock() : 0;
			
			// 检查库存是否充足
			if (currentStock < dangerGoodsStockOut.getQuantity()) {
				throw new ServiceException("库存不足，当前库存数量：" + currentStock + "，出库数量：" + dangerGoodsStockOut.getQuantity());
			}
			
			updateInventory.setCurrentStock(currentStock - dangerGoodsStockOut.getQuantity());
			updateInventory.setUpdateBy(dangerGoodsStockOut.getUpdateBy());
			updateInventory.setUpdateTime(new Date());
			dangerGoodsInventoryMapper.updateNotNullValueByDangerGoodsInventory(updateInventory);
		} else {
			throw new ServiceException("当前出库的危化品没有对应的库存信息！");
		}
	}

	/**
	 * 修改Start扩展
	 */
	public void updateStartExtend(DangerGoodsStockOut dangerGoodsStockOut,DangerGoodsStockOut oldDangerGoodsStockOut) {
		checkDataUnique(dangerGoodsStockOut, "update");
	}

	/**
	 * 修改End扩展
	 * 修改出库记录时，调整库存数量
	 */
	public void updateEndExtend(DangerGoodsStockOut dangerGoodsStockOut, DangerGoodsStockOut oldDangerGoodsStockOut, int rows) {
		// 查询库存信息
		DangerGoodsInventory queryInventory = new DangerGoodsInventory();
		queryInventory.setDangerGoodsId(dangerGoodsStockOut.getDangerGoodsId());
		List<DangerGoodsInventory> inventoryList = dangerGoodsInventoryMapper.selectDataListByEqDangerGoodsInventory(queryInventory);

		if (inventoryList != null && !inventoryList.isEmpty()) {
			DangerGoodsInventory updateInventory = inventoryList.get(0);
			// 计算库存变化量
			Integer oldQuantity = oldDangerGoodsStockOut.getQuantity() != null ? oldDangerGoodsStockOut.getQuantity() : 0;
			Integer newQuantity = dangerGoodsStockOut.getQuantity() != null ? dangerGoodsStockOut.getQuantity() : 0;
			Integer changeQuantity = newQuantity - oldQuantity;

			// 更新库存数量
			Integer currentStock = updateInventory.getCurrentStock() != null ? updateInventory.getCurrentStock() : 0;
			// 如果变化量为正数，表示增加了出库数量，需要扣减更多库存
			// 如果变化量为负数，表示减少了出库数量，需要恢复部分库存
			Integer newStock = currentStock - changeQuantity;
			
			// 确保库存不会变为负数
			if (newStock < 0) {
				throw new ServiceException("库存不足，调整后库存数量：" + newStock);
			}
			
			updateInventory.setCurrentStock(newStock);
			updateInventory.setUpdateBy(dangerGoodsStockOut.getUpdateBy());
			updateInventory.setUpdateTime(new Date());
			dangerGoodsInventoryMapper.updateNotNullValueByDangerGoodsInventory(updateInventory);
		} else {
			throw new ServiceException("当前修改的出库记录对应的危化品没有库存信息！");
		}
	}

	/**
	 * 批量删除Start扩展
	 */
	public void deleteByIdsStartExtend(DangerGoodsStockOut dangerGoodsStockOut) {
		for (String id : dangerGoodsStockOut.getIds()){
			//DangerGoodsStockOut tDangerGoodsStockOut = dangerGoodsStockOutMapper.selectDataByPkDangerGoodsStockOut(id);
		}
	}

	/**
	 * 批量删除End扩展
	 * 删除出库记录时，恢复对应的库存数量
	 */
	public void deleteByIdsEndExtend(DangerGoodsStockOut dangerGoodsStockOut, int rows) {

		String[] ids = dangerGoodsStockOut.getIds();
		if (ids != null && ids.length > 0) {
			// 遍历所有要删除的出库记录ID
			for (String id : ids) {
				try {
					// 将String类型的id转换为Long类型
					Long idLong = Long.parseLong(id);
					// 查询要删除的出库记录
					DangerGoodsStockOut tDangerGoodsStockOut = dangerGoodsStockOutMapper.selectDataByPkDangerGoodsStockOut(idLong);
					if (tDangerGoodsStockOut != null) {
						// 查询对应的库存信息
						DangerGoodsInventory queryInventory = new DangerGoodsInventory();
						queryInventory.setDangerGoodsId(tDangerGoodsStockOut.getDangerGoodsId());
						List<DangerGoodsInventory> inventoryList = dangerGoodsInventoryMapper.selectDataListByEqDangerGoodsInventory(queryInventory);
						
						if (inventoryList != null && !inventoryList.isEmpty()) {
							// 恢复库存数量（将出库的数量加回库存）
							DangerGoodsInventory updateInventory = inventoryList.get(0);
							Integer currentStock = updateInventory.getCurrentStock() != null ? updateInventory.getCurrentStock() : 0;
							Integer quantity = tDangerGoodsStockOut.getQuantity() != null ? tDangerGoodsStockOut.getQuantity() : 0;
							
							updateInventory.setCurrentStock(currentStock + quantity);
							updateInventory.setUpdateTime(new Date());
							dangerGoodsInventoryMapper.updateNotNullValueByDangerGoodsInventory(updateInventory);
						}
					}
				} catch (NumberFormatException e) {
					logger.error("ID转换失败: {}", id, e);
					throw new ServiceException("ID格式不正确: " + id);
				}
			}
		}
	}

	/**
	 * 批量删除ByEqDangerGoodsStockOutStart扩展
	 */
	public void deleteByEqDangerGoodsStockOutStartExtend(DangerGoodsStockOut dangerGoodsStockOut) {
	}

	/**
	 * 批量删除ByEqDangerGoodsStockOutEnd扩展
	 * 根据条件删除出库记录后，恢复对应的库存数量
	 */
	public void deleteByEqDangerGoodsStockOutEndExtend(DangerGoodsStockOut dangerGoodsStockOut, int rows) {
		// 为了实现库存的恢复，我们需要知道哪些记录被删除了
		// 但由于这是删除后的扩展方法，我们无法直接获取被删除的记录
		// 因此，这里我们可以考虑在deleteByEqDangerGoodsStockOutStartExtend方法中缓存被删除的记录
		// 但根据现有代码结构，我们无法直接实现这种缓存
		
		// 注意：由于当前的方法结构限制，我们无法在删除后扩展方法中准确恢复库存
		// 推荐的做法是修改服务层实现，在执行删除操作前先查询并缓存需要删除的记录
		// 然后在deleteByEqDangerGoodsStockOutEndExtend方法中根据缓存的记录恢复库存
		
		// 目前，我们只能在日志中记录此操作，提示需要进一步的实现来支持库存恢复
		logger.info("条件删除出库记录后，无法自动恢复库存，请手动调整库存数量");
	}


	/**
     * 导出前校验扩展
     */
	public void exportDataCheckExtend(DangerGoodsStockOut dangerGoodsStockOut) {
		int count = dangerGoodsStockOutMapper.selectCountByLikeDangerGoodsStockOut(dangerGoodsStockOut);
		// 导出数据超过5万条，报错提示，各模块可以根据需要进行设置
		int limitCount = 50000;
		if (count > limitCount) {
			throw new ServiceException("当前导出的数据超过" + limitCount + "条！请修改查询条件后进行查询，确认数据条数不大于" + limitCount + "条后，再进行导出。");
		}
	}

	/**
     * 导出数据预处理扩展
     */
	public void exportDataDealExtend(DangerGoodsStockOut dangerGoodsStockOut, List<DangerGoodsStockOut> list) {

	}

	/**
	 * 导入开始扩展
	 */
	public void importDataStartExtend(List<DangerGoodsStockOut> dataList, DangerGoodsStockOut pDangerGoodsStockOut, String operName, Date operDate) {
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
	public String importDataCheckExtend(boolean isUpdateSupport, DangerGoodsStockOut pDangerGoodsStockOut, DangerGoodsStockOut dangerGoodsStockOut, String operName) {
		String checkMsg = "";
		if (isUpdateSupport) {

		} else {
			//  如果当前不进行更新数据，当前数据不存在，则直接新增
			try {
				checkDataUnique(dangerGoodsStockOut, "insert");
			} catch (Exception e) {
				checkMsg = e.getMessage();
			}
		}

															return checkMsg;
	}

	/**
	 * 导入完成扩展
	 */
	public void importDataEndExtend(List<DangerGoodsStockOut> dataList, DangerGoodsStockOut pDangerGoodsStockOut, String operName, int successNum) {

	}

	/**
	 * 检查是否可以修改
	 */
	public void checkEditExtend(DangerGoodsStockOut pDangerGoodsStockOut) {
		//        if(pDangerGoodsStockOut.getXXX().equals("")) {
		//            throw new ServiceException("当前选中数据不可修改！");
		//        }
	}


}
