package com.cpems.web.controller.hospital;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.hospital.dto.IotCallbackRequest;
import com.ruoyi.system.hospital.service.IHospitalDataIngestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 医院 IOT 回调接入（外部 IOT 平台调用，无需登录）
 *
 * @author cpems
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/callback")
public class HospitalCallbackController {

    private final IHospitalDataIngestService dataIngestService;

    /**
     * 接收 IOT 平台推送的设备数据
     *
     * @param token  请求头 X-IOT-Token
     * @param sign   请求头 X-IOT-Sign（签名开启时必填，hex(HMAC_SHA256(secret, timestamp + "\n" + msgId))）
     * @param body   回调报文
     * @param request HTTP 请求
     * @return 处理结果
     */
    @PostMapping("/data")
    public R<String> receive(@RequestHeader(value = "X-IOT-Token", required = false) String token,
                             @RequestHeader(value = "X-IOT-Sign", required = false) String sign,
                             @RequestBody IotCallbackRequest body,
                             HttpServletRequest request) {
        String msgId = body == null ? null : body.getMsgId();
        String result = dataIngestService.handleCallback(token, getClientIp(request), sign, body, msgId);
        return "OK".equals(result) ? R.ok("OK") : R.fail(result);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多级代理取第一个
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
