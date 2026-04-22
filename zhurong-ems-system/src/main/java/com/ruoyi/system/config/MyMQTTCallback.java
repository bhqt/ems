package com.ruoyi.system.config;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.enums.TopicType;
import com.ruoyi.system.domain.vo.ElectricEmsCarsonData;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * MQTT回调相关
 *
 * @Author cpems
 * @Date 2025/9/7 14:03
 */
public class MyMQTTCallback implements MqttCallbackExtended {

    private RabbitTemplate rabbitTemplate = SpringUtils.getBean(RabbitTemplate.class);

    private static final Logger log = LoggerFactory.getLogger(MyMQTTCallback.class);

    private MyMQTTClient myMQTTClient;

    // 时间格式化器：yyyy-MM-dd HH:mm:ss
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneId.systemDefault());

    public MyMQTTCallback(MyMQTTClient myMQTTClient) {
        this.myMQTTClient = myMQTTClient;
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.info("MQTT 连接成功，连接方式：{}", reconnect ? "重连" : "直连");
        // 订阅主题（包含新增主题）
        myMQTTClient.subscribe(TopicType.ELECTRIC_emsCarson.getInfo(), 1);
        myMQTTClient.subscribe(TopicType.ELECTRIC_S.getInfo(), 1);
        myMQTTClient.subscribe(TopicType.ELECTRIC_U.getInfo(), 1);
        myMQTTClient.subscribe(TopicType.ELECTRIC_I.getInfo(), 1);
        myMQTTClient.subscribe(TopicType.ELECTRIC_W.getInfo(), 1);
        myMQTTClient.subscribe(TopicType.ELECTRIC_P.getInfo(), 1);
        myMQTTClient.subscribe(TopicType.WATER_CONSUMPTION.getInfo(), 1);
    }

    @Override
    public void connectionLost(Throwable throwable) {
        log.error("mqtt connectionLost 连接断开，5S之后尝试重连: {}", throwable.getMessage());
        long reconnectTimes = 1;
        while (true) {
            try {
                if (MyMQTTClient.getClient().isConnected()) {
                    log.warn("mqtt reconnect success end  重新连接  重新订阅成功");
                    return;
                }
                reconnectTimes += 1;
                log.warn("mqtt reconnect times = {} try again...  mqtt重新连接时间 {}", reconnectTimes, reconnectTimes);
                MyMQTTClient.getClient().reconnect();
            } catch (MqttException e) {
                log.error("mqtt断连异常", e);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
            }
        }
    }

    /**
     * 根据deviceType匹配对应的主题枚举
     */
    private TopicType getTargetTopicByDeviceType(String deviceType) {
        if (StrUtil.isEmpty(deviceType)) {
            return null;
        }
        switch (deviceType) {
            case "electric/voltage":
                return TopicType.ELECTRIC_U;
            case "electric/current":
                return TopicType.ELECTRIC_I;
            case "electric/power":
                return TopicType.ELECTRIC_P;
            case "electric/consumption":
                return TopicType.ELECTRIC_W;
            case "water/consumption":
                return TopicType.WATER_CONSUMPTION;
            default:
                return null;
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws UnsupportedEncodingException {
        log.info("接收消息主题 : {}，接收消息内容 : {}", topic, new String(mqttMessage.getPayload()));

        // 处理电表所有数据打包上传(emsCarson)
        if (topic.equals(TopicType.ELECTRIC_emsCarson.getInfo())) {
            Map<String, Object> maps = JSON.parseObject(
                new String(mqttMessage.getPayload(), CharsetUtil.UTF_8),
                new TypeReference<Map<String, Object>>() {}
            );
            ensureCreateTime(maps); // 确保createTime在最后
            rabbitTemplate.convertAndSend("EquipmentTopicExchange", TopicType.ELECTRIC_emsCarson.getInfo(), maps);
        }
        // 处理电表所有数据打包上传(all) - 新增数组解析逻辑
        else if (topic.startsWith("electric/all/")) {
            try {
                // 解析JSON数组为设备数据列表
                String payloadStr = new String(mqttMessage.getPayload(), CharsetUtil.UTF_8);
                List<Map<String, Object>> dataList = JSON.parseObject(
                    payloadStr,
                    new TypeReference<List<Map<String, Object>>>() {}
                );

                if (dataList != null && !dataList.isEmpty()) {
                    for (Map<String, Object> item : dataList) {
                        // 提取设备类型，匹配目标主题
                        String deviceType = MapUtil.getStr(item, "deviceType");
                        TopicType targetTopic = getTargetTopicByDeviceType(deviceType);

                        if (targetTopic != null) {
                            // 构建转发数据（包含clientId、value，并补充createTime）
                            Map<String, Object> forwardData = new HashMap<>();
                            forwardData.put("clientId", MapUtil.getStr(item, "clientId"));
                            forwardData.put("value", item.get("value"));

                            ensureCreateTime(forwardData); // 确保createTime在最后
                            // 转发到对应的主题（如electric/consumption、electric/voltage等）
                            rabbitTemplate.convertAndSend(
                                "EquipmentTopicExchange",
                                targetTopic.getInfo(),
                                forwardData
                            );
                            log.info("[{}] 已转发数据到{}: {}", topic, targetTopic.getInfo(), forwardData);
                        } else {
                            log.warn("[{}] 跳过未知deviceType: {}", topic, deviceType);
                        }
                    }
                } else {
                    log.info("[{}] 接收到空数组或非数组格式数据", topic);
                }
            } catch (Exception e) {
                log.error("[{}] 数组数据解析失败", topic, e);
            }
        }
        // 处理电压主题
        else if (topic.equals(TopicType.ELECTRIC_U.getInfo())) {
            Map<String, Object> maps = JSON.parseObject(
                new String(mqttMessage.getPayload(), CharsetUtil.UTF_8),
                new TypeReference<Map<String, Object>>() {}
            );
            ensureCreateTime(maps); // 确保createTime在最后
            rabbitTemplate.convertAndSend("EquipmentTopicExchange", TopicType.ELECTRIC_U.getInfo(), maps);
        }
        // 处理电流主题
        else if (topic.equals(TopicType.ELECTRIC_I.getInfo())) {
            Map<String, Object> maps = JSON.parseObject(
                new String(mqttMessage.getPayload(), CharsetUtil.UTF_8),
                new TypeReference<Map<String, Object>>() {}
            );
            ensureCreateTime(maps); // 确保createTime在最后
            rabbitTemplate.convertAndSend("EquipmentTopicExchange", TopicType.ELECTRIC_I.getInfo(), maps);
        }
        // 处理电能主题
        else if (topic.equals(TopicType.ELECTRIC_W.getInfo())) {
            Map<String, Object> maps = JSON.parseObject(
                new String(mqttMessage.getPayload(), CharsetUtil.UTF_8),
                new TypeReference<Map<String, Object>>() {}
            );
            ensureCreateTime(maps); // 确保createTime在最后
            rabbitTemplate.convertAndSend("EquipmentTopicExchange", TopicType.ELECTRIC_W.getInfo(), maps);
        }
        // 处理功率主题
        else if (topic.equals(TopicType.ELECTRIC_P.getInfo())) {
            Map<String, Object> maps = JSON.parseObject(
                new String(mqttMessage.getPayload(), CharsetUtil.UTF_8),
                new TypeReference<Map<String, Object>>() {}
            );
            ensureCreateTime(maps); // 确保createTime在最后
            rabbitTemplate.convertAndSend("EquipmentTopicExchange", TopicType.ELECTRIC_P.getInfo(), maps);
        }
// 处理所有water/consumption/xxx子主题（通过通配符订阅匹配）
        else if (topic.startsWith("water/consumption/")) {
            try {
                // 解析JSON数组为List<Map<String, Object>>
                String payloadStr = new String(mqttMessage.getPayload(), CharsetUtil.UTF_8);
                List<Map<String, Object>> dataList = JSON.parseObject(
                    payloadStr,
                    new TypeReference<List<Map<String, Object>>>() {}
                );

                if (dataList != null && !dataList.isEmpty()) {
                    // 声明目标主题（提前定义，确保作用域覆盖日志语句）
                    String targetTopic = TopicType.WATER_CONSUMPTION.getInfo();
                    for (Map<String, Object> map : dataList) {
                        ensureCreateTime(map); // 确保createTime在最后
                        // 转发到水表消费主题
                        rabbitTemplate.convertAndSend(
                            "EquipmentTopicExchange",
                            targetTopic,  // 使用声明的目标主题
                            map
                        );
                        // 统一日志格式：使用已声明的targetTopic
                        log.info("[{}] 已转发数据到{}: {}", topic, targetTopic, map);
                    }
                } else {
                    log.info("[{}] 接收到空消息列表或非数组格式", topic);
                }
            } catch (Exception e) {
                log.error("[{}] 消息解析或处理失败", topic, e);
            }
        }
    }



    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("==========deliveryComplete={}==========", iMqttDeliveryToken.isComplete());
    }

    /**
     * 确保Map中存在createTime字段，若不存在则添加当前时间（放在最后）
     *
     * @param dataMap 数据Map
     */
    private void ensureCreateTime(Map<String, Object> dataMap) {
        if (dataMap == null) {
            return;
        }
        // 检查createTime是否存在且非空
        Object createTime = dataMap.get("createTime");
        if (createTime == null || StrUtil.isEmpty(createTime.toString().trim())) {
            // 添加当前时间（yyyy-MM-dd HH:mm:ss）
            String currentTime = DATE_TIME_FORMATTER.format(Instant.now());

            // 使用LinkedHashMap保持插入顺序，确保createTime在最后
            LinkedHashMap<String, Object> orderedMap = new LinkedHashMap<>(dataMap);
            // 为了避免重复先移除可能存在的空值createTime
            orderedMap.remove("createTime");
            // 最后添加createTime
            orderedMap.put("createTime", currentTime);

            // 替换原Map内容
            dataMap.clear();
            dataMap.putAll(orderedMap);

            log.debug("自动补充createTime: {}", currentTime);
        }
    }
}
