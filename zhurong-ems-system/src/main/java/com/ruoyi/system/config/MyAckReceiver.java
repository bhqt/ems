package com.ruoyi.system.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.email.MailUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.enums.AlarmConditionType;
import com.ruoyi.system.domain.enums.EnergyType;
import com.ruoyi.system.domain.enums.TopicType;
import com.ruoyi.system.domain.vo.AlarmRuleVo;
import com.ruoyi.system.domain.vo.EquipmentInfoVo;
import com.ruoyi.system.domain.vo.RealtimeAlarmVo;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ISysUserService;
import com.rabbitmq.client.Channel;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 创建ACK回调
 * 功能：作为 MQ 消费者，监听设备上报的电力数据（如电压、电流、功率），
 * 根据预设的报警规则判断是否触发报警，生成报警记录、发送邮件通知并自动创建维修工单。
 * @Author cpems
 * @Date 2025/9/11 17:31
 */
@Component
public class MyAckReceiver implements ChannelAwareMessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MyAckReceiver.class);

    //TODO:这里后续需要做一个util类来定义Mongo用的方法
    private AlarmRuleMapper alarmRuleMapper = SpringUtils.getBean(AlarmRuleMapper.class);
    private EquipmentInfoMapper equipmentInfoMapper = SpringUtils.getBean(EquipmentInfoMapper.class);
    private RealtimeAlarmMapper realtimeAlarmMapper = SpringUtils.getBean(RealtimeAlarmMapper.class);
    private ISysUserService userService = SpringUtils.getBean(ISysUserService.class);
    private RepairOrderMapper repairOrderMapper = SpringUtils.getBean(RepairOrderMapper.class);

    @Value("${spring.datasource.dynamic.datasource.td.enabled:false}")
    private boolean tdDbEnabled;
    // TD数据库回写
    private EnergyMapper energyMapper = SpringUtils.getBean(EnergyMapper.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        logger.info("[开始]-接收到RabbitMQ的消息-进行业务处理");
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            byte[] body = message.getBody();
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body));
            Map<String, Object> msgMap = (Map<String, Object>) ois.readObject();
            BigDecimal value = new BigDecimal(msgMap.get("value").toString());
            String clientId = msgMap.get("clientId").toString();
            //String topic=msgMap.get("topic");
            String createTime = msgMap.get("createTime").toString();
            ois.close();

            String routingKey = message.getMessageProperties().getReceivedRoutingKey();

            // 保存数据到数据库
            saveDataToDB(clientId, routingKey, createTime, value);

            // 保存数据到TDengine数据库
            // 可用于如@XxlJob("statisticsElectricityEnergy")之类的定时任务统计数据
            // 启用TDengine数据库
            if (tdDbEnabled) {
                saveDataToTDengine(clientId, routingKey, createTime, value);
            }

            // TODO 保存数据到redis
            // RedisUtils.setCacheMap(message.getMessageProperties().getConsumerQueue(), msgMap);
            // TODO 保存数据到mongo
            //mongoTemplate.save(msgMap,"test");

            // 报警规则查询
            // 根据路由键类型（如 ELECTRIC_U），查询对应参数类型的启用中的报警规则
            // 触发条件判断
            // 报警条件类型：AlarmConditionType 枚举（大于 G、小于 L、等于 E 等）。
            // 双阈值检查：每条规则包含两个条件（condition1 和 condition2）及其阈值（thresholdValue1 和 thresholdValue2）。
            // 防重复报警：通过 getLast() 获取设备最新的同参数报警记录，仅当当前值与上次状态发生跃变时插入新报警。

            // 以下只对电相关数据进行了处理
            if (routingKey.equals(TopicType.ELECTRIC_U.getInfo())) {
                List<AlarmRuleVo> alarmRuleVoList =
                    alarmRuleMapper.selectVoList(new LambdaQueryWrapper<AlarmRule>().eq(AlarmRule::getAlarmSwitch, "0").eq(AlarmRule::getParamName, TopicType.ELECTRIC_U.getCode().toString()));
                for (AlarmRuleVo alarmRuleVo : alarmRuleVoList) {
                    if (alarmRuleVo.getCondition1().equals(AlarmConditionType.G.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue1()) > 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_U.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue1()) > 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_U.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition1().equals(AlarmConditionType.L.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue1()) < 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_U.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue1()) < 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_U.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition1().equals(AlarmConditionType.E.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue1()) == 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_U.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue1()) == 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_U.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition1().equals(AlarmConditionType.GE.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue1()) >= 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_U.getCode().toString());
                            // 如果上一次记录不存在，或上一次未达到阈值（避免重复报警）
                            // 上次已经报警过，下一次不在报警，系统中删除当前报警信息后，才能再次插入新的报警信息
                            // dongbei 为方便测试，暂时注释禁止重复报警
                            // if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue1()) >= 0)) {
                            insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_U.getCode().toString());
                            // }
                        }
                    }
                    if (alarmRuleVo.getCondition1().equals(AlarmConditionType.LE.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue1()) <= 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_U.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue1()) <= 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_U.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition2().equals(AlarmConditionType.G.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue2()) > 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_U.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue2()) > 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_U.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition2().equals(AlarmConditionType.L.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue2()) < 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_U.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue2()) < 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_U.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition2().equals(AlarmConditionType.E.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue2()) == 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_U.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue2()) == 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_U.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition2().equals(AlarmConditionType.GE.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue2()) >= 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_U.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue2()) >= 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_U.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition2().equals(AlarmConditionType.LE.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue2()) <= 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_U.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue2()) <= 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_U.getCode().toString());
                            }
                        }
                    }
                }
            }
            if (routingKey.equals(TopicType.ELECTRIC_I.getInfo())) {
                List<AlarmRuleVo> alarmRuleVoList =
                    alarmRuleMapper.selectVoList(new LambdaQueryWrapper<AlarmRule>().eq(AlarmRule::getAlarmSwitch, "0").eq(AlarmRule::getParamName, TopicType.ELECTRIC_I.getCode().toString()));
                for (AlarmRuleVo alarmRuleVo : alarmRuleVoList) {
                    if (alarmRuleVo.getCondition1().equals(AlarmConditionType.G.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue1()) > 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_I.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue1()) > 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_I.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition1().equals(AlarmConditionType.L.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue1()) < 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_I.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue1()) < 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_I.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition1().equals(AlarmConditionType.E.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue1()) == 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_I.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue1()) == 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_I.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition1().equals(AlarmConditionType.GE.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue1()) >= 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_I.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue1()) >= 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_I.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition1().equals(AlarmConditionType.LE.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue1()) <= 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_I.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue1()) <= 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_I.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition2().equals(AlarmConditionType.G.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue2()) > 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_I.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue2()) > 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_I.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition2().equals(AlarmConditionType.L.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue2()) < 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_I.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue2()) < 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_I.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition2().equals(AlarmConditionType.E.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue2()) == 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_I.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue2()) == 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_I.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition2().equals(AlarmConditionType.GE.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue2()) >= 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_I.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue2()) >= 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_I.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition2().equals(AlarmConditionType.LE.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue2()) <= 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_I.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue2()) <= 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_I.getCode().toString());
                            }
                        }
                    }
                }
            }
            /*if (message.getMessageProperties().getReceivedRoutingKey().equals(TopicType.ELECTRIC_W.getInfo())) {
                // 需要计算差值，目前传入的是累计值
            }*/
            if (routingKey.equals(TopicType.ELECTRIC_P.getInfo())) {
                List<AlarmRuleVo> alarmRuleVoList =
                    alarmRuleMapper.selectVoList(new LambdaQueryWrapper<AlarmRule>().eq(AlarmRule::getAlarmSwitch, "0").eq(AlarmRule::getParamName, TopicType.ELECTRIC_P.getCode().toString()));
                for (AlarmRuleVo alarmRuleVo : alarmRuleVoList) {
                    if (alarmRuleVo.getCondition1().equals(AlarmConditionType.G.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue1()) > 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_P.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue1()) > 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_P.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition1().equals(AlarmConditionType.L.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue1()) < 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_P.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue1()) < 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_P.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition1().equals(AlarmConditionType.E.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue1()) == 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_P.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue1()) == 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_P.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition1().equals(AlarmConditionType.GE.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue1()) >= 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_P.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue1()) >= 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_P.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition1().equals(AlarmConditionType.LE.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue1()) <= 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_P.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue1()) <= 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_P.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition2().equals(AlarmConditionType.G.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue2()) > 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_P.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue2()) > 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_P.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition2().equals(AlarmConditionType.L.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue2()) < 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_P.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue2()) < 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_P.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition2().equals(AlarmConditionType.E.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue2()) == 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_P.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue2()) == 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_P.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition2().equals(AlarmConditionType.GE.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue2()) >= 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_P.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue2()) >= 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_P.getCode().toString());
                            }
                        }
                    }
                    if (alarmRuleVo.getCondition2().equals(AlarmConditionType.LE.getInfo())) {
                        if (value.compareTo(alarmRuleVo.getThresholdValue2()) <= 0) {
                            BigDecimal last = getLast(clientId, TopicType.ELECTRIC_P.getCode().toString());
                            if (last == null || !(last.compareTo(alarmRuleVo.getThresholdValue2()) <= 0)) {
                                insertAlarm(value, createTime, clientId, alarmRuleVo, TopicType.ELECTRIC_P.getCode().toString());
                            }
                        }
                    }
                }

            }

            // 处理用水量报警规则：需要计算差值，目前传入的是累计值，可以改为监控水压等其他信息
            // if (routingKey.equals(TopicType.WATER_CONSUMPTION.getInfo())) {
            //     List<AlarmRuleVo> alarmRuleVoList = alarmRuleMapper.selectVoList(
            //         new LambdaQueryWrapper<AlarmRule>()
            //             .eq(AlarmRule::getAlarmSwitch, "0")
            //             .eq(AlarmRule::getParamName, TopicType.WATER_CONSUMPTION.getCode().toString()) // 参数名对应枚举的code
            //                                                                     );
            //     for (AlarmRuleVo alarmRuleVo : alarmRuleVoList) {
            //         // 条件1判断
            //         checkAndTriggerAlarm(alarmRuleVo.getCondition1(), alarmRuleVo.getThresholdValue1(), value, clientId, alarmRuleVo, TopicType.WATER_CONSUMPTION, createTime);
            //         // 条件2判断
            //         checkAndTriggerAlarm(alarmRuleVo.getCondition2(), alarmRuleVo.getThresholdValue2(), value, clientId, alarmRuleVo, TopicType.WATER_CONSUMPTION, createTime);
            //     }
            // }

            //// System.out.println("  MyAckReceiver  name:"+name+"  content:"+content+"  time:"+time);
            /*String messageId = msgMap.get("messageId");
            String messageData = msgMap.get("messageData");
            String createTime = msgMap.get("createTime");
            ois.close();
            // System.out.println("  MyAckReceiver  messageId:"+messageId+"  messageData:"+messageData+"  createTime:"+createTime);*/
            // // System.out.println("消费的主题消息来自：" + message.getMessageProperties().getConsumerQueue());

            channel.basicAck(deliveryTag, true); //第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
            // channel.basicReject(deliveryTag, true);//第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
        } catch (Exception e) {
            channel.basicReject(deliveryTag, false);
            logger.info("【异常】-接收到RabbitMQ的消息-进行业务处理时出现异常！", e);
        }
        logger.info("[完成]-接收到RabbitMQ的消息-进行业务处理");
    }

    //------------------ 公共方法：封装条件判断逻辑 ------------------
    private void checkAndTriggerAlarm(String condition, BigDecimal threshold, BigDecimal currentValue,
                                      String clientId, AlarmRuleVo rule, TopicType type, String createTime) {  // 添加 createTime 参数
        if (threshold == null) return; // 忽略无效阈值

        // 获取该类型最后一次报警记录
        BigDecimal last = getLast(clientId, type.getCode().toString());

        switch (AlarmConditionType.fromInfo(condition)) { // 将条件转为枚举
            case G:
                if (currentValue.compareTo(threshold) > 0 && (last == null || last.compareTo(threshold) <= 0)) {
                    insertAlarm(currentValue, createTime, clientId, rule, type.getCode().toString());
                }
                break;
            case L:
                if (currentValue.compareTo(threshold) < 0 && (last == null || last.compareTo(threshold) >= 0)) {
                    insertAlarm(currentValue, createTime, clientId, rule, type.getCode().toString());
                }
                break;
            case E:
                if (currentValue.compareTo(threshold) == 0 && (last == null || last.compareTo(threshold) != 0)) {
                    insertAlarm(currentValue, createTime, clientId, rule, type.getCode().toString());
                }
                break;
            case GE:
                if (currentValue.compareTo(threshold) >= 0 && (last == null || last.compareTo(threshold) < 0)) {
                    insertAlarm(currentValue, createTime, clientId, rule, type.getCode().toString());
                }
                break;
            case LE:
                if (currentValue.compareTo(threshold) <= 0 && (last == null || last.compareTo(threshold) > 0)) {
                    insertAlarm(currentValue, createTime, clientId, rule, type.getCode().toString());
                }
                break;
            default:
                break; // 忽略未知条件
        }
    }

    private void insertAlarm(BigDecimal value, String createTime, String clientId, AlarmRuleVo vo, String electricType) {
        logger.info("[开始]-生成报警信息");
        RealtimeAlarm insert = new RealtimeAlarm();
        insert.setParamName(electricType);
        insert.setAlarmTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, createTime));
        insert.setAlarmVal(value);
        insert.setAlarmInfo(vo.getAlarmInfo());
        insert.setEquipment(clientId);
        EquipmentInfoVo equipmentInfoVo = equipmentInfoMapper.selectVoOne(new LambdaQueryWrapper<EquipmentInfo>().eq(EquipmentInfo::getSn, clientId));
        insert.setArea(equipmentInfoVo.getName());
        insert.setAlarmLevel(vo.getAlarmLevel());
        realtimeAlarmMapper.insert(insert);
        logger.info("[完成]-生成报警信息：{}", JSONUtil.toJsonStr(insert));
        SysUser user = userService.selectUserById(Long.valueOf(vo.getUserId()));
        if (null != user) {
            //        realtimeAlarmService.insertByBo(insert);
            if (ObjectUtil.isNotEmpty(user.getEmail())) {
                try {
                    MailUtils.sendText(user.getEmail(), "报警提醒", "区域:" + insert.getArea() + "设备:" + insert.getEquipment() + vo.getAlarmInfo() + insert.getAlarmVal().toString());
                } catch (Exception e) {
                    logger.error("【异常】-发送报警邮件时出现异常！", e);
                }
            }
            if ("0".equals(vo.getCreateOrderSwitch())) {
                String orderNo = "WX" + DateUtils.dateTimeNow("yyyyMMddHHmmssSSS");
                logger.info("[开始]-报警后自动生成维修工单：" + orderNo);
                RepairOrder repairOrder = new RepairOrder();
                repairOrder.setOrderNo(orderNo);
                repairOrder.setOrderContent(vo.getAlarmInfo());
                repairOrder.setProjectName(equipmentInfoVo.getName());
                repairOrderMapper.insert(repairOrder);
                logger.info("[完成]-报警后自动生成维修工单：" + orderNo + "-" + JSONUtil.toJsonStr(repairOrder));
            }

        } else {
            logger.error("【失败】-当前报警规则[" + vo.getParamName() + "]上没有配置有效的提醒人，不能进行邮件发送和自动生成维修工单！");
        }
    }

    private BigDecimal getLast(String clientId, String electricType) {
        RealtimeAlarmVo realtimeAlarmVo =
            realtimeAlarmMapper.selectVoOne(new LambdaQueryWrapper<RealtimeAlarm>().eq(RealtimeAlarm::getParamName, electricType).eq(RealtimeAlarm::getEquipment, clientId)
                .orderByDesc(RealtimeAlarm::getAlarmTime).last("limit 1"));
        if (ObjectUtil.isNotEmpty(realtimeAlarmVo)) {
            return realtimeAlarmVo.getAlarmVal();
        }
        return null;
    }

    /**
     * 保存数据到TDengine数据库
     * @param clientId
     * @param routingKey
     * @param createTime
     * @param value
     * @return
     */
    private void saveDataToTDengine(String clientId, String routingKey, String createTime, BigDecimal value) {
        try {
            Energy energy = new Energy();
            energy.setTs(DateUtil.date(DateUtil.parse(createTime)).toTimestamp());
            energy.setClientId(clientId);
            energy.setVal(value.floatValue());

            EnergyType energyType = null;
            if (routingKey.equals(TopicType.ELECTRIC_U.getInfo())) {
                energyType = EnergyType.ELECTRICITYU;
            } else if (routingKey.equals(TopicType.ELECTRIC_I.getInfo())) {
                energyType = EnergyType.ELECTRICITYI;
            } else if (routingKey.equals(TopicType.ELECTRIC_P.getInfo())) {
                energyType = EnergyType.ELECTRICITYP;
            } else if (routingKey.equals(TopicType.ELECTRIC_W.getInfo())) {
                energyType = EnergyType.ELECTRICITY;
            } else if (routingKey.equals(TopicType.WATER_CONSUMPTION.getInfo())) {
                energyType = EnergyType.WATER;
            }

            // 设置 Energy 的 type 字段（假设使用 EnergyType 的 info 作为表名）
            if (energyType != null) {
                energy.setType(energyType.getInfo());
            } else {
                // 处理未知类型的兜底逻辑（如日志告警）
                logger.warn("Unknown routing key: {}", routingKey);
            }

            energyMapper.insertEnergy(energy);
        } catch (Exception e) {
            logger.error("【异常】-进行业务处理时出现异常！", e);
        }

    }

    private ElectricityUMapper electricityUMapper = SpringUtils.getBean(ElectricityUMapper.class);
    private ElectricityIMapper electricityIMapper = SpringUtils.getBean(ElectricityIMapper.class);
    private ElectricityPMapper electricityPMapper = SpringUtils.getBean(ElectricityPMapper.class);
    private ElectricityWMapper electricityWMapper = SpringUtils.getBean(ElectricityWMapper.class);
    private WaterConsumptionMapper waterConsumptionapper = SpringUtils.getBean(WaterConsumptionMapper.class);

    /**
     * 保存数据到数据库
     * @param clientId
     * @param routingKey
     * @param createTime
     * @param value
     * @return
     */
    private void saveDataToDB(String clientId, String routingKey, String createTime, BigDecimal value) {
        try {

            ElectricityU insert = new ElectricityU();
            insert.setClientId(clientId);
            insert.setCreateTime(DateUtil.parse(createTime));
            insert.setValue(value);

            if (routingKey.equals(TopicType.ELECTRIC_U.getInfo())) {
                electricityUMapper.insert(insert);
            } else if (routingKey.equals(TopicType.ELECTRIC_I.getInfo())) {
                electricityIMapper.insert(BeanUtil.toBean(insert, ElectricityI.class));
            } else if (routingKey.equals(TopicType.ELECTRIC_P.getInfo())) {
                electricityPMapper.insert(BeanUtil.toBean(insert, ElectricityP.class));
            } else if (routingKey.equals(TopicType.ELECTRIC_W.getInfo())) {
                electricityWMapper.insert(BeanUtil.toBean(insert, ElectricityW.class));
            } else if (routingKey.equals(TopicType.WATER_CONSUMPTION.getInfo())) {
                waterConsumptionapper.insert(BeanUtil.toBean(insert, WaterConsumption.class));
            }

        } catch (Exception e) {
            logger.error("【异常】-进行业务处理时出现异常！", e);
        }

    }

}
