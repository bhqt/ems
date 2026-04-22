package com.ruoyi.common.service.impl;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.service.CommonService;
import com.ruoyi.handler.dictData.DictDataFactory;
import com.ruoyi.system.mapper.SysDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {
	@Autowired
	private DictDataFactory dictDataFactory;
	@Autowired
	private SysDictDataMapper dictDataMapper;

    @Override
	public HashMap<String, String> getDictLableValueMap(String dictType) {

		HashMap<String, String> dictLableValueMap = dictDataFactory.getAllLableValueMapByDictType(dictType, new HashMap<>());
		if (null == dictLableValueMap) {
			List<SysDictData> sysDictDataList = dictDataMapper.selectDictDataByType(dictType);
			for (SysDictData sysDictData : sysDictDataList) {
				dictLableValueMap.put(sysDictData.getDictLabel(), sysDictData.getDictValue());
			}
		}
		return dictLableValueMap;
	}

	@Override
	public HashMap<String, String> getDictLableValueMap(String dictType, Object params) {
		HashMap<String, String> dictLableValueMap = dictDataFactory.getAllLableValueMapByDictType(dictType, params);
		if (null == dictLableValueMap) {
			// 如果自定义字典处理器未处理，则使用默认方式查询
			// 注意：这里可以根据实际需求扩展默认处理逻辑，支持带参数的查询
			List<SysDictData> sysDictDataList = dictDataMapper.selectDictDataByType(dictType);
			for (SysDictData sysDictData : sysDictDataList) {
				dictLableValueMap.put(sysDictData.getDictLabel(), sysDictData.getDictValue());
			}
		}
		return dictLableValueMap;
	}

    @Override
	public String getDictAllLableStr(String dictType) {
		String res =   dictDataFactory.getDictAllLableStr(dictType, new HashMap<>());
		if (null ==res) {
			res = dictDataMapper.selectAllDictLableStrByDictType(dictType);
		}
		return res;
	}

	@Override
	public String getDictAllLableStr(String dictType, Object params) {
		String res = dictDataFactory.getDictAllLableStr(dictType, params);
		if (null == res) {
			// 如果自定义字典处理器未处理，则使用默认方式查询
			// 注意：这里可以根据实际需求扩展默认处理逻辑，支持带参数的查询
			res = dictDataMapper.selectAllDictLableStrByDictType(dictType);
		}
		return res;
	}

}
