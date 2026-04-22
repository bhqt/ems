package com.ruoyi.autoee.patrolPath.handler;

import cn.hutool.json.JSONUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.handler.dictData.DictDataHandler;
import org.springframework.stereotype.Component;
import com.ruoyi.common.core.domain.entity.SysDictData;

import com.ruoyi.autoee.patrolPath.domain.PatrolPath;
import com.ruoyi.autoee.patrolPath.mapper.PatrolPathMapper;
import com.ruoyi.autoee.patrolPath.mapper.PatrolPathMapperExtend;


/**
 * 巡更路线字典处理类
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Component
public class PatrolPathDictHandler implements DictDataHandler
{
	private static final Logger logger = LoggerFactory.getLogger(PatrolPathDictHandler.class);

	// 要使用Mapper，不能使用Service，否则会出现循环依赖问题
	private final PatrolPathMapper patrolPathMapper;
	private static final String dictType = "a_patrol_path";


	// 构造函数注入
	public PatrolPathDictHandler(PatrolPathMapper patrolPathMapper) {
		this.patrolPathMapper = patrolPathMapper;
	}

	@Override
	public boolean supports(String dictType) {
		return this.dictType.equals(dictType);
	}

    

	@Override
	public List<SysDictData> handleGetDictData(Object params) {
		List<SysDictData> resList = new ArrayList<SysDictData>();
		PatrolPath queryData = JSONUtil.toBean(JSONUtil.toJsonStr(params), PatrolPath.class);
		List<PatrolPath> queryList = patrolPathMapper.selectDataListByEqPatrolPath(queryData);
		for (int i = 0; i < queryList.size(); i++) {
			SysDictData data = new SysDictData();
			data.setDictType(this.dictType);
			// 默认代码值取id
			// 如果存在_code的列，则字典value取该字段
			data.setDictValue(queryList.get(i).getId() + "");
			// 如果存在_name的列，则字典lable取该字段，否则默认取第二列
			data.setDictLabel(queryList.get(i).getRouteName() + "");
			data.setDictSort((int) (i + 1));
			data.setStatus("0");
			resList.add(data);
		}
		return resList;
	}

	@Override
	public HashMap<String, String> handleGetLableValueMap(Object params) {
		HashMap<String, String> map = new HashMap<>();
		PatrolPath queryData = JSONUtil.toBean(JSONUtil.toJsonStr(params), PatrolPath.class);
		List<PatrolPath> list = patrolPathMapper.selectDataListByEqPatrolPath(queryData);
		for (PatrolPath data : list) {
			map.put(data.getRouteName()+"", data.getId()+"");
		}
		return map;
	}

	@Override
	public String handleGetDictAllLableStr(Object params) {
		StringBuilder res = new StringBuilder();
		PatrolPath queryData = JSONUtil.toBean(JSONUtil.toJsonStr(params), PatrolPath.class);
		List<PatrolPath> queryList = patrolPathMapper.selectDataListByEqPatrolPath(queryData);

		if (!queryList.isEmpty()) {
			for (PatrolPath data : queryList) {
				res.append(data.getRouteName()).append(",");
			}
			// 去掉最后一个逗号（如果字符串非空）
			if (res.length() > 0) {
				res.deleteCharAt(res.length() - 1);
			}
		}

		return res.toString();
	}



}
