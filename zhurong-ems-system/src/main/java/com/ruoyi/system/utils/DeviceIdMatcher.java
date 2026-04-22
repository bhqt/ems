package com.ruoyi.system.utils;

import com.ruoyi.common.utils.StringUtils;

import java.util.Arrays;

/**
 * Title: <br>
 * Desc: <br>
 * Date: 2025/9/14 0014 <br>
 * @author Double
 * @version 1.0.0
 */
public class DeviceIdMatcher {

     /**
     * 检查设备ID是否存在于逗号分隔的设备ID列表中
     * @param deviceIdList 逗号分隔的设备ID列表字符串（如"1,10,100"）
     * @param targetDeviceId 要查找的目标设备ID
     * @return 是否精确匹配
     */
    public static boolean containsDevice(String deviceIdList, String targetDeviceId) {
        if (StringUtils.isEmpty(deviceIdList) || StringUtils.isEmpty(targetDeviceId)) {
            return false;
        }
        return Arrays.asList(deviceIdList.split(",")).contains(targetDeviceId);
    }
}
