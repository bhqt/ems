package com.ruoyi.system.hospital.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.ruoyi.system.hospital.config.HospitalIotProperties;
import com.ruoyi.system.hospital.service.IotCallbackAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * IOT 回调鉴权 Service 实现（Token 比对，签名校验预留）
 *
 * @author cpems
 */
@RequiredArgsConstructor
@Service
public class IotCallbackAuthServiceImpl implements IotCallbackAuthService {

    private final HospitalIotProperties iotProperties;

    @Override
    public boolean verify(String token) {
        if (token == null) {
            return false;
        }
        // 简单 Token 比对；后续可扩展签名校验（sign-enabled）
        return Objects.equals(token, iotProperties.getAuthToken());
    }

    @Override
    public String checkFailReason(String token, String sourceIp, String timestamp, String msgId, String sign) {
        // 1. Token 比对
        if (!verify(token)) {
            return "X-IOT-Token 无效";
        }
        // 2. IP 白名单（为空表示不限制）
        String whitelist = iotProperties.getIpWhitelist();
        if (StrUtil.isNotBlank(whitelist) && StrUtil.isNotBlank(sourceIp)) {
            boolean matched = false;
            for (String item : whitelist.split("[,;]")) {
                if (sourceIp.trim().equals(item.trim())) {
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                return "来源 IP 不在白名单：" + sourceIp;
            }
        }
        // 3. 签名校验（开关 + 密钥均配置时才生效）
        if (Boolean.TRUE.equals(iotProperties.getSignEnabled())
            && StrUtil.isNotBlank(iotProperties.getSignSecret())) {
            if (StrUtil.isBlank(sign)) {
                return "缺少签名 X-IOT-Sign";
            }
            String plain = (timestamp == null ? "" : timestamp) + "\n" + (msgId == null ? "" : msgId);
            HMac hmac = new HMac(HmacAlgorithm.HmacSHA256,
                iotProperties.getSignSecret().getBytes(StandardCharsets.UTF_8));
            String expected = hmac.digestHex(plain.getBytes(StandardCharsets.UTF_8));
            if (!expected.equalsIgnoreCase(sign.trim())) {
                return "签名校验失败";
            }
        }
        return null;
    }
}
