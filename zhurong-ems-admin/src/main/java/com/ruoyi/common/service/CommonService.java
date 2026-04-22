package com.ruoyi.common.service;

import java.util.HashMap;

public interface CommonService {

	public HashMap<String, String> getDictLableValueMap(String dictType);

	public HashMap<String, String> getDictLableValueMap(String dictType, Object params);

	public String getDictAllLableStr(String dictType);

	public String getDictAllLableStr(String dictType, Object params);
}
