package com.ruoyi.autoee.goodsInventory.handler;

import cn.hutool.json.JSONUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.handler.dictData.DictDataHandler;
import org.springframework.stereotype.Component;
import com.ruoyi.common.core.domain.entity.SysDictData;

import com.ruoyi.autoee.goodsInventory.domain.GoodsInventory;
import com.ruoyi.autoee.goodsInventory.mapper.GoodsInventoryMapper;
import com.ruoyi.autoee.goodsInventory.mapper.GoodsInventoryMapperExtend;


/**
 * 物品库存字典处理类
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Component
public class GoodsInventoryDictHandler implements DictDataHandler
{
	private static final Logger logger = LoggerFactory.getLogger(GoodsInventoryDictHandler.class);

	// 要使用Mapper，不能使用Service，否则会出现循环依赖问题
	private final GoodsInventoryMapper goodsInventoryMapper;
	private static final String dictType = "a_goods_inventory";


	// 构造函数注入
	public GoodsInventoryDictHandler(GoodsInventoryMapper goodsInventoryMapper) {
		this.goodsInventoryMapper = goodsInventoryMapper;
	}

	@Override
	public boolean supports(String dictType) {
		return this.dictType.equals(dictType);
	}

    
    
	@Override
	public List<SysDictData> handleGetDictData(Object params) {
		List<SysDictData> resList = new ArrayList<SysDictData>();
		GoodsInventory queryData = JSONUtil.toBean(JSONUtil.toJsonStr(params), GoodsInventory.class);
		List<GoodsInventory> queryList = goodsInventoryMapper.selectDataListByEqGoodsInventory(queryData);
		for (int i = 0; i < queryList.size(); i++) {
			SysDictData data = new SysDictData();
			data.setDictType(this.dictType);
			// 默认代码值取id
			// 如果存在_code的列，则字典value取该字段
			data.setDictValue(queryList.get(i).getId() + "");
			// 如果存在_name的列，则字典lable取该字段，否则默认取第二列
			data.setDictLabel(queryList.get(i).getGoodsId() + "");
			data.setDictSort((int) (i + 1));
			data.setStatus("0");
			resList.add(data);
		}
		return resList;
	}

	@Override
	public HashMap<String, String> handleGetLableValueMap(Object params) {
		HashMap<String, String> map = new HashMap<>();
		GoodsInventory queryData = JSONUtil.toBean(JSONUtil.toJsonStr(params), GoodsInventory.class);
		List<GoodsInventory> list = goodsInventoryMapper.selectDataListByEqGoodsInventory(queryData);
		for (GoodsInventory data : list) {
			map.put(data.getGoodsId()+"", data.getId()+"");
		}
		return map;
	}

	@Override
	public String handleGetDictAllLableStr(Object params) {
		StringBuilder res = new StringBuilder();
		GoodsInventory queryData = JSONUtil.toBean(JSONUtil.toJsonStr(params), GoodsInventory.class);
		List<GoodsInventory> queryList = goodsInventoryMapper.selectDataListByEqGoodsInventory(queryData);

		if (!queryList.isEmpty()) {
			for (GoodsInventory data : queryList) {
				res.append(data.getGoodsId()).append(",");
			}
			// 去掉最后一个逗号（如果字符串非空）
			if (res.length() > 0) {
				res.deleteCharAt(res.length() - 1);
			}
		}

		return res.toString();
	}



}
