package com.ruoyi.system.handler;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.handler.dictData.DictDataHandler;
import com.ruoyi.system.service.ISysUserService;
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
public class UserDictHandler implements DictDataHandler {
	private final ISysUserService userService;
	private static final String dictType = "sys_user";

	// 构造函数注入
	public UserDictHandler(ISysUserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(String dictType) {
		return this.dictType.equals(dictType);
	}

	@Override
	public List<SysDictData> handleGetDictData(Object params) {
		List<SysDictData> data = null;
		data = new ArrayList<SysDictData>();
		List<SysUser> sysUsers = userService.selectUserList(new SysUser());
		SysUser user = null;
		for (int i = 0; i < sysUsers.size(); i++) {
			user = sysUsers.get(i);
			SysDictData sysDictData = new SysDictData();
			sysDictData.setDictType(this.dictType);
			sysDictData.setDictValue(user.getUserId() + "");
			sysDictData.setDictLabel(user.getUserName() + "/" + user.getNickName());
			sysDictData.setDictSort((int) (i + 1));
			sysDictData.setStatus("0");
			data.add(sysDictData);
		}
		return data;
	}

	@Override
	public HashMap<String, String> handleGetLableValueMap(Object params) {
		HashMap<String, String> dictLableValueMap = new HashMap<>();
		SysUser user = new SysUser();
		List<SysUser> sysUserList = userService.selectUserList(user);
		for (SysUser sysDictData : sysUserList) {
			dictLableValueMap.put(sysDictData.getNickName(), sysDictData.getUserId() + "");
		}
		return dictLableValueMap;
	}

	@Override
	public String handleGetDictAllLableStr(Object params) {
		StringBuilder res = new StringBuilder();
		SysUser user = new SysUser();
		List<SysUser> sysUserList = userService.selectUserList(user);

		if (!sysUserList.isEmpty()) {
			for (SysUser sysUser : sysUserList) {
				res.append(sysUser.getNickName()).append(",");
			}
			// 去掉最后一个逗号（如果字符串非空）
			if (res.length() > 0) {
				res.deleteCharAt(res.length() - 1);
			}
		}

		return res.toString();
	}

}
