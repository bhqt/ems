package com.cpems.web.controller.system;

import cn.dev33.satoken.annotation.SaIgnore;
import com.ruoyi.system.config.MqttMsg;
import com.ruoyi.system.config.MyMQTTClient;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MQTT测试控制器
 * @Author cpems
 * @Date 2023/4/7 14:06
 */
@RestController
@RequestMapping("/system/mqtt")
public class MqttController {
    private static final Logger logger = LoggerFactory.getLogger(MqttController.class);

    @Autowired
    private MyMQTTClient myMQTTClient;

    /**
     * mqtt测试发送消息到rabbitmq消费到mysql、redis、mongodb
     * @param mqttMsg
     */
    @PostMapping("/sendEquipmentData")
    @ResponseBody
    @SaIgnore
    public synchronized void mqttMsg(MqttMsg mqttMsg) {

        //时间格式化
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = df.format(new Date());
        mqttMsg.setCreateTime(time);

        //map转json
        JSONObject json = JSONObject.fromObject(mqttMsg);
        // 定义的主题直接传进来更方便
        String topicType = (String) json.get("topicType");
        json.remove("topicType");

        String sendMsg = json.toString();
        logger.info("【获取】-系统接收到向MQTT发送的数据：{}-对应主题：{}", sendMsg, topicType);

        //发布消息  自定义发送消息到对应的主题
        logger.info("[开始]-向MQTT发布消息");
        myMQTTClient.publish(sendMsg, topicType);
        logger.info("[完成]-向MQTT发布消息");
    }
}

