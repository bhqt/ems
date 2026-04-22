package com.ruoyi.system.handler;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.handler.dictData.DictDataHandler;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Title: <br>
 * Desc: <br>
 * Date: 2025/6/10 <br>
 * @author Double
 * @version 1.0.0
 */
@Component  // 必须添加
public class DeptDictHandler implements DictDataHandler {
	private final ISysDeptService deptService;
	private static final String dictType = "sys_dept";

	// 构造函数注入
	public DeptDictHandler(ISysDeptService deptService) {
		this.deptService = deptService;
	}

	@Override
	public boolean supports(String dictType) {
		return this.dictType.equals(dictType);
	}

	@Override
	public List<SysDictData> handleGetDictData(Object params) {
		List<SysDictData> resList = null;
		resList = new ArrayList<SysDictData>();
		List<SysDept> queryList = deptService.selectDeptList(new SysDept());
		for (int i = 0; i < queryList.size(); i++) {
			SysDictData data = new SysDictData();
			data.setDictType(this.dictType);
			data.setDictValue(queryList.get(i).getDeptId() + "");
			data.setDictLabel(queryList.get(i).getDeptName());
			data.setDictSort((int) (i + 1));
			data.setStatus("0");
			resList.add(data);
		}
		return resList;
	}

	@Override
	public HashMap<String, String> handleGetLableValueMap(Object params) {
		HashMap<String, String> map = new HashMap<>();
		SysDept dept = new SysDept();
		List<SysDept> list = deptService.selectDeptList(dept);
		for (SysDept data : list) {
			map.put(data.getDeptName(), data.getDeptId()+"");
		}
		return map;
	}

	@Override
	public String handleGetDictAllLableStr(Object params) {
		StringBuilder res = new StringBuilder();
		SysDept queryData = new SysDept();
		List<SysDept> queryList = deptService.selectDeptList(queryData);

		if (!queryList.isEmpty()) {
			for (SysDept data : queryList) {
				res.append(data.getDeptName()).append(",");
			}
			// 去掉最后一个逗号（如果字符串非空）
			if (res.length() > 0) {
				res.deleteCharAt(res.length() - 1);
			}
		}

		return res.toString();
	}

}
