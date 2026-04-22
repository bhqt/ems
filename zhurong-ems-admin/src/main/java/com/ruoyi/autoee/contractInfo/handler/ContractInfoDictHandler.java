package com.ruoyi.autoee.contractInfo.handler;

import cn.hutool.json.JSONUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.handler.dictData.DictDataHandler;
import org.springframework.stereotype.Component;
import com.ruoyi.common.core.domain.entity.SysDictData;

import com.ruoyi.autoee.contractInfo.domain.ContractInfo;
import com.ruoyi.autoee.contractInfo.mapper.ContractInfoMapper;
import com.ruoyi.autoee.contractInfo.mapper.ContractInfoMapperExtend;


/**
 * 合同信息管理字典处理类
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Component
public class ContractInfoDictHandler implements DictDataHandler
{
	private static final Logger logger = LoggerFactory.getLogger(ContractInfoDictHandler.class);

	// 要使用Mapper，不能使用Service，否则会出现循环依赖问题
	private final ContractInfoMapper contractInfoMapper;
	private static final String dictType = "a_contract_info";


	// 构造函数注入
	public ContractInfoDictHandler(ContractInfoMapper contractInfoMapper) {
		this.contractInfoMapper = contractInfoMapper;
	}

	@Override
	public boolean supports(String dictType) {
		return this.dictType.equals(dictType);
	}

    
    
	@Override
	public List<SysDictData> handleGetDictData(Object params) {
		List<SysDictData> resList = new ArrayList<SysDictData>();
		ContractInfo queryData = JSONUtil.toBean(JSONUtil.toJsonStr(params), ContractInfo.class);
		List<ContractInfo> queryList = contractInfoMapper.selectDataListByEqContractInfo(queryData);
		for (int i = 0; i < queryList.size(); i++) {
			SysDictData data = new SysDictData();
			data.setDictType(this.dictType);
			// 默认代码值取id
			// 如果存在_code的列，则字典value取该字段
			data.setDictValue(queryList.get(i).getId() + "");
			// 如果存在_name的列，则字典lable取该字段，否则默认取第二列
			data.setDictLabel(queryList.get(i).getContractNoNew() + "");
			data.setDictSort((int) (i + 1));
			data.setStatus("0");
			resList.add(data);
		}
		return resList;
	}

	@Override
	public HashMap<String, String> handleGetLableValueMap(Object params) {
		HashMap<String, String> map = new HashMap<>();
		ContractInfo queryData = JSONUtil.toBean(JSONUtil.toJsonStr(params), ContractInfo.class);
		List<ContractInfo> list = contractInfoMapper.selectDataListByEqContractInfo(queryData);
		for (ContractInfo data : list) {
			map.put(data.getContractNoNew()+"", data.getId()+"");
		}
		return map;
	}

	@Override
	public String handleGetDictAllLableStr(Object params) {
		StringBuilder res = new StringBuilder();
		ContractInfo queryData = JSONUtil.toBean(JSONUtil.toJsonStr(params), ContractInfo.class);
		List<ContractInfo> queryList = contractInfoMapper.selectDataListByEqContractInfo(queryData);

		if (!queryList.isEmpty()) {
			for (ContractInfo data : queryList) {
				res.append(data.getContractNoNew()).append(",");
			}
			// 去掉最后一个逗号（如果字符串非空）
			if (res.length() > 0) {
				res.deleteCharAt(res.length() - 1);
			}
		}

		return res.toString();
	}



}
