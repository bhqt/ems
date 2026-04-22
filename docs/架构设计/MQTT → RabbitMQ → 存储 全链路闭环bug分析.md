MQTT → RabbitMQ → 存储 全链路闭环分析
经过对全部源码的逐一核实，我发现整个数据链路 主体通联，但存在 4 处严重 Bug 和若干缺口，并非完全闭环。

一、数据流转全貌
设备 ──MQTT──> EMQX ──> MyMQTTCallback.messageArrived()
                              │
                              ▼
                        RabbitMQ Exchange
                        (EquipmentTopicExchange)
                              │
              ┌───────────────┼───────────────┐
              ▼               ▼               ▼
           UQueue         WQueue          WaterQueue
         (电压)          (用电量)          (水量)
              │               │               │
              ▼               ▼               ▼
        MyAckReceiver ← ← ← ← ← ← ← ← ← ← ← ←
        (消费者)   注意：PQueue/Carson/Water 未在此监听！
              │
              ├─> MySQL (✅ 闭合)
              ├─> TDengine (✅ 闭合，需开启)
              ├─> 报警规则 (⚠️ 仅电压/电流/功率)
              ├─> 邮件通知 (✅ 闭合)
              └─> 维修工单 (✅ 闭合)
二、已 ✅ 闭合的功能
环节	说明	状态
MQTT 消息接收	MyMQTTCallback 正确解析 7 个主题	✅
MQTT → RabbitMQ 转发	rabbitTemplate.convertAndSend() 成功发送	✅
交换机/队列基础绑定	UQueue / IQueue / PQueue / WQueue / WaterQueue 绑定到 Exchange	✅
MySQL 存储	saveDataToDB() 写入 electricity_u/i/p/w 和 water_consumption	✅
TDengine 存储	saveDataToTDengine() 写入 Energy 表（需开启 td.enabled）	✅
报警规则 — 电压	6种条件 + 双阈值 + 防重复	✅
报警规则 — 电流	同上	✅
报警规则 — 功率	同上	✅
邮件通知	MailUtils.sendText() 发送报警邮件	✅
自动创建维修工单	RepairOrder 插入数据库	✅
消息手动确认	channel.basicAck() / basicReject()	✅
自动补充 createTime	ensureCreateTime() 兜底空时间	✅
设备在线/离线重连	MqttConnectOptions.setAutomaticReconnect(true)	✅
三、❌ 未闭合 / 严重 Bug
Bug 1：PQueue 和 WQueue 的 binding 交叉 — 最严重

RabbitExChangeConfig.java
Lines 38-74
    @Bean
    Binding PExchangeMessage() {
        return BindingBuilder.bind(WQueue()).to(EquipmentDataExchange()).with(TopicType.ELECTRIC_W.getInfo());
    }
    @Bean
    Binding WExchangeMessage() {
        return BindingBuilder.bind(PQueue()).to(EquipmentDataExchange()).with(TopicType.ELECTRIC_P.getInfo());
    }
队列和 binding 颠倒了：

WQueue（用电量队列）被绑定到了 electric/consumption（功率路由键）
PQueue（功率队列）被绑定到了 electric/power（用电量路由键）
结果：功率和用电量的消息互相跑到对方队列——数据错乱。

Bug 2：electric/emsCarson 主题数据完全丢失
MessageListenerConfig.java 第 38 行监听队列列表中 没有 CarsonQueue：


MessageListenerConfig.java
Lines 38-38
container.setQueueNames(
    TopicType.ELECTRIC_U.getInfo(),        // ✅
    TopicType.ELECTRIC_I.getInfo(),        // ✅
    TopicType.ELECTRIC_W.getInfo(),        // ✅
    TopicType.ELECTRIC_P.getInfo(),        // ✅
    TopicType.WATER_CONSUMPTION.getInfo()  // ✅
); // ← 没有 electric/emsCarson 的 CarsonQueue
同时 MyAckReceiver.saveDataToDB() 中也没有处理 ELECTRIC_emsCarson 的分支。

结果：electric/emsCarson 主题的数据被 MQTT 接收并转发到 RabbitMQ，但从未被消费，数据直接丢弃。

Bug 3：water/consumption/+ 主题数据未监听
WaterQueue 已在 RabbitMQ 中声明和绑定（第 77 行），但同样 未加入监听队列列表（第 38 行）。

结果：水表数据在 MQTT 回调中正确转发到 RabbitMQ，但 MyAckReceiver 从未消费，数据丢弃。

Bug 4：报警规则 — 水表数据完全缺失
MyAckReceiver.onMessage() 中有 U / I / P 的完整报警逻辑（各 10 个条件分支，共 30 个 if），但 water consumption 的整个报警块被注释掉了（第 365-378 行）：


MyAckReceiver.java
Lines 365-378
            // 处理用水量报警规则：需要计算差值，目前传入的是累计值...
            // if (routingKey.equals(TopicType.WATER_CONSUMPTION.getInfo())) {
            //     List<AlarmRuleVo> alarmRuleVoList = alarmRuleMapper.selectVoList(...);
            //     ...
            // }
结果：即使修复了 Bug 3 水表数据能消费进来，也不会触发任何水表报警。

四、⚠️ 部分闭合 / 待完善
环节	现状	建议
Redis 缓存	saveDataToDB() 后有 TODO 注释但未实现	设备实时数据缓存到 Redis
MongoDB 归档	有 TODO 注释但未实现	历史数据冷存储
Carson 数据结构	ElectricEmsCarsonData 定义了 params/properties 嵌套结构，但消费者只取 clientId + value，嵌套属性完全未解析	展开 properties 列表，分别存储各属性字段
getLast() 防重复报警	当前查的是 realtime_alarm 表（报警记录），不是最近一次设备值——这意味着只有第一次触发报警后才防重，设备持续超阈值期间不会重复报警	需确认这是否为预期行为
Carson 报警逻辑	完全缺失	应遍历 params[].properties[] 逐项判断
五、修复优先级建议
优先级	问题	影响
P0	Bug 1 — PQueue/WQueue binding 颠倒	功率/用电量数据错乱
P0	Bug 2 — Carson 队列无消费者	打包数据全部丢失
P1	Bug 3 — Water 队列无消费者	水表数据全部丢失
P1	Bug 4 — 水表无报警规则	水表数据无告警
P2	Redis/Mongo 未实现	实时数据无法缓存/归档
P2	Carson 嵌套数据未解析	打包格式优势未利用
总结
整个 MQTT → RabbitMQ → MySQL → TDengine → 报警 → 邮件/工单的链路骨架完整，代码量也扎实，但 4 个 binding/监听配置错误导致部分数据（功率/用电量错乱、Carson 丢失、水表丢失）实际无法正确落地。修复 Bug 1~3 后，核心闭环即可成立；Bug 4 属于功能补全。需要我帮你修复这些问题吗？