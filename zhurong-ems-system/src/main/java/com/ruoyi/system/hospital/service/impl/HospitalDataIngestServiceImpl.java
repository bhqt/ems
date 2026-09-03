package com.ruoyi.system.hospital.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ruoyi.system.hospital.config.HospitalIotProperties;
import com.ruoyi.system.hospital.constant.HospitalConstants;
import com.ruoyi.system.hospital.domain.HospitalCallbackLog;
import com.ruoyi.system.hospital.domain.StandardDataPoint;
import com.ruoyi.system.hospital.dto.IotCallbackRequest;
import com.ruoyi.system.hospital.service.IHospitalCallbackLogService;
import com.ruoyi.system.hospital.service.IHospitalDataIngestService;
import com.ruoyi.system.hospital.service.IotCallbackAuthService;
import com.ruoyi.system.hospital.service.IotDataParser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 医院 IOT 回调数据接入 Service 实现
 * 流程：鉴权 -> 解析标准化 -> 投递 MQ -> 记录回调日志
 *
 * @author cpems
 */
@RequiredArgsConstructor
@Service
public class HospitalDataIngestServiceImpl implements IHospitalDataIngestService {

    private static final Logger log = LoggerFactory.getLogger(HospitalDataIngestServiceImpl.class);

    private final IotCallbackAuthService authService;
    private final IotDataParser iotDataParser;
    private final IHospitalCallbackLogService callbackLogService;
    private final HospitalIotProperties iotProperties;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public String handleCallback(String token, String sourceIp, IotCallbackRequest request, String requestId) {
        return handleCallback(token, sourceIp, null, request, requestId);
    }

    @Override
    public String handleCallback(String token, String sourceIp, String sign,
                                 IotCallbackRequest request, String requestId) {
        long start = System.currentTimeMillis();
        String msgId = request == null ? requestId : (StrUtil.isNotBlank(request.getMsgId()) ? request.getMsgId() : requestId);

        HospitalCallbackLog logEntity = new HospitalCallbackLog();
        logEntity.setRequestId(msgId);
        logEntity.setSourceIp(sourceIp);
        logEntity.setIotTimestamp(request == null ? null : request.getTimestamp());
        logEntity.setReceiveTime(new Date());

        Long logId = callbackLogService.insertLog(logEntity);

        try {
            // 1. 鉴权（Token + IP 白名单 + 可选签名）
            String failReason = authService.checkFailReason(token, sourceIp,
                request == null ? null : request.getTimestamp(), msgId, sign);
            if (failReason != null) {
                logEntity.setId(logId);
                logEntity.setStatus(HospitalConstants.CALLBACK_STATUS_AUTH_FAIL);
                logEntity.setErrorMsg("鉴权失败：" + failReason);
                logEntity.setCostTime(System.currentTimeMillis() - start);
                callbackLogService.updateLog(logEntity);
                log.warn("[IOT回调] 鉴权失败 requestId={} sourceIp={} reason={}", msgId, sourceIp, failReason);
                return "鉴权失败";
            }

            // 2. 解析标准化
            List<StandardDataPoint> points = iotDataParser.parse(request);
            if (CollUtil.isEmpty(points)) {
                logEntity.setId(logId);
                logEntity.setStatus(HospitalConstants.CALLBACK_STATUS_SUCCESS);
                logEntity.setDeviceCount(countDevices(request));
                logEntity.setPointCount(0);
                logEntity.setCostTime(System.currentTimeMillis() - start);
                callbackLogService.updateLog(logEntity);
                return "OK";
            }

            // 3. 投递 MQ（异步落库）
            String payload = JSON.toJSONString(points);
            rabbitTemplate.convertAndSend(iotProperties.getExchange(), iotProperties.getRoutingKey(), payload);

            logEntity.setId(logId);
            logEntity.setStatus(HospitalConstants.CALLBACK_STATUS_SUCCESS);
            logEntity.setDeviceCount(countDevices(request));
            logEntity.setPointCount(points.size());
            logEntity.setCostTime(System.currentTimeMillis() - start);
            callbackLogService.updateLog(logEntity);
            log.info("[IOT回调] 处理成功 requestId={} points={}", msgId, points.size());
            return "OK";
        } catch (Exception e) {
            log.error("[IOT回调] 处理异常 requestId={}", msgId, e);
            logEntity.setId(logId);
            logEntity.setStatus(HospitalConstants.CALLBACK_STATUS_FAIL);
            logEntity.setErrorMsg(e.getMessage());
            logEntity.setCostTime(System.currentTimeMillis() - start);
            callbackLogService.updateLog(logEntity);
            return "处理失败：" + e.getMessage();
        }
    }

    private int countDevices(IotCallbackRequest request) {
        if (request == null || CollUtil.isEmpty(request.getDevices())) {
            return 0;
        }
        return request.getDevices().size();
    }
}
