package com.ruoyi.handler.dictData;

import com.ruoyi.common.core.domain.entity.SysDictData;

import java.util.HashMap;
import java.util.List;

/**
 * Title: <br>
 * Desc: <br>
 * Date: 2025/6/10 <br>
 * @author Double
 * @version 1.0.0
 */
public interface DictDataHandler {
	boolean supports(String dictType);

	List<SysDictData> handleGetDictData(Object params);


	HashMap<String, String> handleGetLableValueMap(Object params);


	public String handleGetDictAllLableStr(Object params);

}
