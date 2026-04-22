package com.ruoyi.autoee.goodsInventory.service.impl;

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
import com.ruoyi.autoee.goodsInventory.mapper.GoodsInventoryMapper;
import com.ruoyi.autoee.goodsInventory.mapper.GoodsInventoryMapperExtend;
import com.ruoyi.autoee.goodsInventory.domain.GoodsInventory;
import com.ruoyi.autoee.goodsInventory.service.IGoodsInventoryService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 物品库存Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 */
@Service
public class GoodsInventoryServiceExtend {
	private static final Logger logger = LoggerFactory.getLogger(GoodsInventoryServiceExtend.class);
	@Autowired
	private GoodsInventoryMapper goodsInventoryMapper;
	@Autowired
	private GoodsInventoryMapperExtend goodsInventoryMapperExtend;
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
     * 通过唯一字段查询物品库存一条详细信息
     */
    public GoodsInventory selectOneDataByCache(GoodsInventory goodsInventory)
    {
		GoodsInventory goodsInventoryR = new GoodsInventory();
	    if (null != goodsInventoryR) {
			return goodsInventoryR;
	    } else {
			List<GoodsInventory> goodsInventoryList = goodsInventoryMapper.selectDetailListByLikeGoodsInventory(new GoodsInventory());
			if (goodsInventoryList.size() == 1) {
				goodsInventoryR = goodsInventoryList.get(0);
				RedisCacheUtils.setMapValue("DataCache_Map_goodsInventory", String.valueOf(goodsInventory.getId()), JSONUtil.toJsonStr(goodsInventoryR));
				return goodsInventoryR;
			} else {
				throw new ServiceException("查询信息结果不唯一！");
			}
	    }
    }

	/**
	 * 查询列表Start扩展
	 */
	public void selectListStartExtend(GoodsInventory goodsInventory) {

	}

	/**
	 * 查询列表End扩展
	 */
	public void selectListEndExtend(GoodsInventory goodsInventory, List<GoodsInventory> list) {

	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListStartExtend(GoodsInventory goodsInventory) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListEndExtend(GoodsInventory goodsInventory, List<GoodsInventory> list) {
		changSelfDefineDict(goodsInventory, list);
	}

	/**
	 * 查询详细列表Start扩展
	 */
	public void selectDetailListByEqGoodsInventoryStartExtend(GoodsInventory goodsInventory) {

	}

	/**
	 * 查询详细列表End扩展
	 */
	public void selectDetailListByEqGoodsInventoryEndExtend(GoodsInventory goodsInventory, List<GoodsInventory> list) {
		changSelfDefineDict(goodsInventory, list);
	}

	/**
	 * 查询明细End扩展
	 */
	public void selectDetailByPkGoodsInventoryEndExtend(GoodsInventory goodsInventory) {
		List<GoodsInventory> list = new ArrayList();
		list.add(goodsInventory);
		changSelfDefineDict(goodsInventory, list);
		goodsInventory =list.get(0);
	}

	/**
	 * 查询导出列表Start扩展
	 */
	public void selectExportDetailListStartExtend(GoodsInventory goodsInventory) {

	}

	/**
	 * 查询导出列表End扩展
	 */
	public void selectExportDetailListEndExtend(GoodsInventory goodsInventory, List<GoodsInventory> list) {
		changSelfDefineDict(goodsInventory, list);
	}

	/**
	 * 处理不能通过sql进行转码的下拉框
	 */
	public void changSelfDefineDict(GoodsInventory goodsInventory, List<GoodsInventory> list) {
		//HashMap<String, String> goodsIdMap = goodsIdMap(goodsInventory);
		//HashMap<String, String> userIdMap = userIdMap(goodsInventory);
		//HashMap<String, String> deptIdMap = deptIdMap(goodsInventory);
		//list.forEach(e -> {
		//e.setGoodsIdExtend(goodsIdMap.get(e.getGoodsId() + ""));
		//e.setUserIdExtend(userIdMap.get(e.getUserId() + ""));
		//e.setDeptIdExtend(deptIdMap.get(e.getDeptId() + ""));
		//});
	}

	/**
	 * 校验数据唯一性
	 */
	public void checkDataUnique(GoodsInventory goodsInventory, String flag) {
	GoodsInventory qGoodsInventory = new GoodsInventory();
															
	}

	/**
	 * 新增Start扩展
	 */
	public void insertStartExtend(GoodsInventory goodsInventory) {
		checkDataUnique(goodsInventory, "insert");
	}

	/**
	 * 新增End扩展
	 */
	public void insertEndExtend(GoodsInventory goodsInventory, int rows) {

	}

	/**
	 * 修改Start扩展
	 */
	public void updateStartExtend(GoodsInventory goodsInventory,GoodsInventory oldGoodsInventory) {
		checkDataUnique(goodsInventory, "update");
	}

	/**
	 * 修改End扩展
	 */
	public void updateEndExtend(GoodsInventory goodsInventory, GoodsInventory oldGoodsInventory, int rows) {

	}

	/**
	 * 批量删除Start扩展
	 */
	public void deleteByIdsStartExtend(GoodsInventory goodsInventory) {
		for (String id : goodsInventory.getIds()){
			//GoodsInventory tGoodsInventory = goodsInventoryMapper.selectDataByPkGoodsInventory(id);
		}
	}

	/**
	 * 批量删除End扩展
	 */
	public void deleteByIdsEndExtend(GoodsInventory goodsInventory, int rows) {

	}

	/**
	 * 批量删除ByEqGoodsInventoryStart扩展
	 */
	public void deleteByEqGoodsInventoryStartExtend(GoodsInventory goodsInventory) {
	}

	/**
	 * 批量删除ByEqGoodsInventoryEnd扩展
	 */
	public void deleteByEqGoodsInventoryEndExtend(GoodsInventory goodsInventory, int rows) {

	}


	/**
     * 导出前校验扩展
     */
	public void exportDataCheckExtend(GoodsInventory goodsInventory) {
		int count = goodsInventoryMapper.selectCountByLikeGoodsInventory(goodsInventory);
		// 导出数据超过5万条，报错提示，各模块可以根据需要进行设置
		int limitCount = 50000;
		if (count > limitCount) {
			throw new ServiceException("当前导出的数据超过" + limitCount + "条！请修改查询条件后进行查询，确认数据条数不大于" + limitCount + "条后，再进行导出。");
		}
	}

	/**
     * 导出数据预处理扩展
     */
	public void exportDataDealExtend(GoodsInventory goodsInventory, List<GoodsInventory> list) {

	}

	/**
	 * 导入开始扩展
	 */
	public void importDataStartExtend(List<GoodsInventory> dataList, GoodsInventory pGoodsInventory, String operName, Date operDate) {
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
	public String importDataCheckExtend(boolean isUpdateSupport, GoodsInventory pGoodsInventory, GoodsInventory goodsInventory, String operName) {
		String checkMsg = "";
		if (isUpdateSupport) {

		} else {
			//  如果当前不进行更新数据，当前数据不存在，则直接新增
			try {
				checkDataUnique(goodsInventory, "insert");
			} catch (Exception e) {
				checkMsg = e.getMessage();
			}
		}

															return checkMsg;
	}

	/**
	 * 导入完成扩展
	 */
	public void importDataEndExtend(List<GoodsInventory> dataList, GoodsInventory pGoodsInventory, String operName, int successNum) {

	}

	/**
	 * 检查是否可以修改
	 */
	public void checkEditExtend(GoodsInventory pGoodsInventory) {
		//        if(pGoodsInventory.getXXX().equals("")) {
		//            throw new ServiceException("当前选中数据不可修改！");
		//        }
	}


}
