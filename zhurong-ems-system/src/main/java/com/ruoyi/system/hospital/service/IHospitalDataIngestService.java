package com.ruoyi.system.hospital.service;

import com.ruoyi.system.hospital.dto.IotCallbackRequest;

/**
 * 医院 IOT 回调数据接入 Service
 *
 * @author cpems
 */
public interface IHospitalDataIngestService {

    /**
     * 处理 IOT 平台回调数据
     *
     * @param token     请求头 X-IOT-Token
     * @param sourceIp  来源 IP
     * @param request   回调报文
     * @param requestId 请求 ID（msgId）
     * @return 处理结果信息
     */
    String handleCallback(String token, String sourceIp, IotCallbackRequest request, String requestId);

    /**
     * 处理 IOT 平台回调数据（带签名）
     *
     * @param token     请求头 X-IOT-Token
     * @param sourceIp  来源 IP
     * @param sign      请求头 X-IOT-Sign（可为空）
     * @param request   回调报文
     * @param requestId 请求 ID（msgId）
     * @return 处理结果信息
     */
    default String handleCallback(String token, String sourceIp, String sign,
                                  IotCallbackRequest request, String requestId) {
        return handleCallback(token, sourceIp, request, requestId);
    }
}
