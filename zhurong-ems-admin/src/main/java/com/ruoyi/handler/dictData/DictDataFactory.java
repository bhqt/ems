package com.ruoyi.handler.dictData;

import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.domain.entity.SysDictData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Title: <br>
 * Desc: <br>
 * Date: 2025/6/10 <br>
 * @author Double
 * @version 1.0.0
 */
@Component  // 添加此注解，让 Spring 管理该 Bean
public class DictDataFactory {
	private static final Logger logger = LoggerFactory.getLogger(DictDataFactory.class);
	private final List<DictDataHandler> handlers;

	// 显式构造函数
	// 构造函数注入（Spring 会自动传入 handlers）
	//Spring 会自动收集所有 DictDataHandler 的实现类（如 StudentNoDictHandler），并通过构造函数注入。
	public DictDataFactory(List<DictDataHandler> handlers) {
		this.handlers = handlers;
	}

	public List<SysDictData> getDictData(String dictType, Object params) {
		return handlers.stream()
				.filter(handler -> handler.supports(dictType))
				.findFirst()
				.map(handler -> {
					List<SysDictData> data = handler.handleGetDictData(params);
					logger.info("[获取]自定义字典数据-类型=[{]]-数据={}", dictType, JSONUtil.toJsonStr(data));
					return data;
				})
				.orElse(null);
	}



	public HashMap<String, String> getAllLableValueMapByDictType(String dictType, Object params) {
		return handlers.stream()
				.filter(handler -> handler.supports(dictType))
				.findFirst()
				.map(handler -> {
					HashMap<String, String> dictLableValueMap = handler.handleGetLableValueMap(params);
					logger.info("[获取]自定义字典数据-类型=[{]]-数据={}", dictType, JSONUtil.toJsonStr(dictLableValueMap));
					return dictLableValueMap;
				})
				.orElse(null);
	}


	public String getDictAllLableStr(String dictType, Object params) {
		return handlers.stream()
				.filter(handler -> handler.supports(dictType))
				.findFirst()
				.map(handler -> {
					String allLableStr= handler.handleGetDictAllLableStr(params);
					logger.info("[获取]自定义字典数据-类型=[{]]-数据={}", dictType, allLableStr);
					return allLableStr;
				})
				.orElse(null);
	}



}
