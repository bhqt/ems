package com.ruoyi.system.hospital.service;

import com.ruoyi.system.hospital.dto.IotCallbackRequest;

/**
 * IOT 回调鉴权 Service
 *
 * @author cpems
 */
public interface IotCallbackAuthService {

    /**
     * 校验回调请求是否合法
     *
     * @param token 请求头 X-IOT-Token
     * @return 是否通过
     */
    boolean verify(String token);

    /**
     * 校验回调请求是否合法（Token + IP 白名单 + 可选签名）
     * <p>
     * 签名算法（signEnabled=true 且 signSecret 非空时生效）：
     * hex(HMAC_SHA256(secret, timestamp + "\n" + msgId))，由 IOT 平台放在请求头 X-IOT-Sign。
     *
     * @param token     请求头 X-IOT-Token
     * @param sourceIp  来源 IP
     * @param timestamp 报文时间戳（body.timestamp）
     * @param msgId     报文消息 ID（body.msgId）
     * @param sign      请求头 X-IOT-Sign（可为空）
     * @return 通过返回 null，否则返回失败原因
     */
    default String checkFailReason(String token, String sourceIp, String timestamp, String msgId, String sign) {
        return verify(token) ? null : "X-IOT-Token 无效";
    }
}
