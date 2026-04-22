# Deep-EMS 系统 MQTT使用与架构分析报告

> 文档版本：1.0  
> 创建日期：2026-04-03  
> 适用范围：dev / test / prod 环境

---

## 一、概述

本文档详细分析 Deep-EMS（深能智慧能源管理系统）中 MQTT 协议的使用方式，以及系统的整体技术架构方案。重点阐述设备端如何通过 MQTT 连接到系统、数据如何经过消息队列处理、最终如何存储和分析。

---

## 二、MQTT 相关分析

### 2.1 MQTT 配置文件

**配置文件位置**：`zhurong-ems-admin/src/main/resources/application-{profile}.yml`

**dev 环境配置示例**：

```yaml
## MQTT##
mqtt:
  host: tcp://localhost:1883
  username: admin
  password: 121212Aa
  clientId: devClientID123  # ClientId 必须唯一
  qos: 1
  timeout: 120
  keepalive: 360
```

**参数说明**：

| 参数 | 说明 | 建议值 |
|------|------|--------|
| host | MQTT Broker 地址 | tcp://localhost:1883 |
| username | 连接用户名 | admin |
| password | 连接密码 | 121212Aa |
| clientId | 客户端唯一标识 | 必须全局唯一 |
| qos | 服务质量级别 | 1 (至少一次) |
| timeout | 连接超时时间(秒) | 120 |
| keepalive | 心跳间隔(秒) | 360 |

### 2.2 MQTT 核心配置类

**文件路径**：`zhurong-ems-system/src/main/java/com/ruoyi/system/config/MqttConfiguration.java`

```java
@Configuration
public class MqttConfiguration {
    @Value("${mqtt.host}")
    String host;
    @Value("${mqtt.username}")
    String username;
    @Value("${mqtt.password}")
    String password;
    @Value("${mqtt.clientId}")
    String clientId;
    @Value("${mqtt.timeout}")
    int timeOut;
    @Value("${mqtt.keepalive}")
    int keepAlive;

    @Bean
    public MyMQTTClient myMQTTClient() {
        MyMQTTClient myMQTTClient = new MyMQTTClient(host, username, password, clientId, timeOut, keepAlive);
        // 连接重试机制：最多尝试10次
        for (int i = 1; i <= tryCount; i++) {
            try {
                myMQTTClient.connect();
                return myMQTTClient;
            } catch (MqttException e) {
                log.error("初始化连接MQTT失败，请检查MQTT配置信息。请尝试连接次数 {}/{}", i, tryCount);
                Thread.sleep(2000);  // 重试间隔2秒
            }
        }
        throw new RuntimeException("MQTT连接失败，已达最大重试次数");
    }
}
```

**关键特性**：
- Spring `@Configuration` 自动注入
- 支持连接重试机制（默认10次）
- 重试间隔 2 秒

### 2.3 MQTT 客户端实现

**文件路径**：`zhurong-ems-system/src/main/java/com/ruoyi/system/config/MyMQTTClient.java`

**核心功能**：

| 方法 | 功能说明 |
|------|---------|
| `connect()` | 建立 MQTT 连接 |
| `disconnect()` | 断开连接 |
| `publish(String topic, String message)` | 发布消息到指定主题 |
| `subscribe(String topic)` | 订阅主题 |
| `subscribe(String topic, int qos)` | 订阅主题并指定 QoS |
| `setCallback(MqttCallback callback)` | 设置消息回调 |

**连接选项配置**：

```java
public MqttConnectOptions setMqttConnectOptions(String username, String password, int timeout, int keepalive) {
    MqttConnectOptions options = new MqttConnectOptions();
    options.setUserName(username);
    options.setPassword(password.toCharArray());
    options.setCleanSession(true);
    options.setAutomaticReconnect(true);  // 启用自动重连
    options.setConnectionTimeout(timeout);
    options.setKeepAliveInterval(keepalive);  // 心跳间隔360秒
    return options;
}
```

**技术选型**：使用 Eclipse Paho MQTT 客户端库

### 2.4 MQTT 主题格式 (Topic Pattern)

**文件路径**：`zhurong-ems-system/src/main/java/com/ruoyi/system/domain/enums/TopicType.java`

| 枚举名称 | Topic 路径 | 描述 | 数据类型 |
|----------|-----------|------|----------|
| `ELECTRIC_emsCarson` | `electric/emsCarson` | 电表数据打包上传 | Map |
| `ELECTRIC_S` | `electric/all/+` | 电表所有数据(带通配符) | List |
| `ELECTRIC_U` | `electric/voltage` | 电压数据 | 单一值 |
| `ELECTRIC_I` | `electric/current` | 电流数据 | 单一值 |
| `ELECTRIC_P` | `electric/power` | 电功率数据 | 单一值 |
| `ELECTRIC_W` | `electric/consumption` | 用电量数据 | 单一值 |
| `WATER_CONSUMPTION` | `water/consumption/+` | 用水量(带通配符) | List |

**Topic 命名规范**：
- 层级使用 `/` 分隔
- 支持单层通配符 `+`（匹配任意字符串）
- 支持多层通配符 `#`（匹配多层路径）

### 2.5 MQTT 消息数据格式

#### 消息基类：MqttMsg

**文件路径**：`zhurong-ems-system/src/main/java/com/ruoyi/system/config/MqttMsg.java`

```java
@Data
@NoArgsConstructor
public class MqttMsg {
    private String clientId;      // 设备SN/ID
    private BigDecimal value;      // 参数值
    private String createTime;    // 创建时间
}
```

#### 电表打包数据格式：ElectricEmsCarsonData

**文件路径**：`zhurong-ems-system/src/main/java/com/ruoyi/system/domain/vo/ElectricEmsCarsonData.java`

```java
@Data
public class ElectricEmsCarsonData {
    private String id;           // 消息ID
    private String version;      // 协议版本
    private Integer ack;         // 响应标识
    private List<Param> params;   // 参数列表
    
    @Data
    public static class Param {
        private String clientID;          // 设备SN
        private List<Property> properties; // 属性列表
    }
    
    @Data
    public static class Property {
        private String name;      // 属性名 (如: voltage, current)
        private Object value;     // 属性值
        private Long timestamp;   // 时间戳
    }
}
```

#### JSON 示例

**电表打包数据**：

```json
{
  "id": "meter001",
  "version": "1.0",
  "ack": 1,
  "params": [
    {
      "clientID": "SN123456",
      "properties": [
        {"name": "voltage", "value": 220.5, "timestamp": 1699999999000},
        {"name": "current", "value": 10.2, "timestamp": 1699999999000},
        {"name": "power", "value": 2200.0, "timestamp": 1699999999000},
        {"name": "consumption", "value": 1500.5, "timestamp": 1699999999000}
      ]
    }
  ]
}
```

**单一参数数据**：

```json
{
  "clientId": "SN123456",
  "value": 220.5,
  "createTime": "2026-04-03 12:00:00"
}
```

---

## 三、系统模块结构

### 3.1 项目模块架构

**根 pom.xml 位置**：`zhurong-ems/pom.xml`

```xml
<modules>
    <module>zhurong-ems-admin</module>      <!-- 主应用入口 -->
    <module>zhurong-ems-framework</module>  <!-- 核心框架模块 -->
    <module>zhurong-ems-system</module>      <!-- 系统管理模块 -->
    <module>zhurong-ems-job</module>         <!-- 定时任务模块 -->
    <module>zhurong-ems-common</module>     <!-- 通用工具模块 -->
    <module>zhurong-ems-extend</module>      <!-- 扩展功能模块 -->
    <module>zhurong-ems-oss</module>         <!-- 对象存储模块 -->
    <module>zhurong-ems-sms</module>        <!-- 短信服务模块 -->
</modules>
```

**模块说明**：

| 模块 | 说明 |
|------|------|
| zhurong-ems-admin | 主应用模块，包含启动类和 Web 配置 |
| zhurong-ems-framework | 核心框架，封装通用工具和基础类 |
| zhurong-ems-system | 系统管理模块，包含业务逻辑和 MQTT/RabbitMQ 处理 |
| zhurong-ems-job | 定时任务模块（XXL-Job） |
| zhurong-ems-common | 通用工具类模块 |
| zhurong-ems-extend | 扩展功能模块 |
| zhurong-ems-oss | 对象存储模块（文件上传） |
| zhurong-ems-sms | 短信服务模块 |

### 3.2 技术栈

| 层级 | 技术 | 版本 |
|------|------|------|
| **基础框架** | | |
| 后端框架 | Spring Boot | 2.7.9 |
| 编程语言 | Java | 1.8 |
| Web 容器 | Undertow | 内嵌 |
| **数据层** | | |
| ORM | MyBatis Plus | 3.5.3.1 |
| 数据库 | MySQL | 8.0 |
| 缓存 | Redis | 7.2 |
| 时序数据库 | TDengine (可选) | 3.2.x |
| **消息层** | | |
| 消息队列 | RabbitMQ | 3.12 |
| 物联网协议 | MQTT (Eclipse Paho) | - |
| **安全认证** | | |
| 权限框架 | Sa-Token | 1.34.0 |
| **任务调度** | | |
| 任务调度 | XXL-Job | 2.3.1 |
| **前端** | | |
| 前端框架 | Vue | 2.6.12 |
| UI 组件库 | Element UI | 2.15.12 |
| **基础设施** | | |
| 反向代理 | Nginx | - |
| 容器化 | Docker | - |

---

## 四、设备数据流转完整路径

### 4.1 数据流转架构图

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                设备层                                        │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐                    │
│  │  电表1   │  │  电表2   │  │  水表1   │  │  网关1   │                    │
│  │ SN001    │  │ SN002    │  │ SN003    │  │ 批量上报 │                    │
│  └────┬─────┘  └────┬─────┘  └────┬─────┘  └────┬─────┘                    │
└───────┼─────────────┼─────────────┼─────────────┼────────────────────────┘
        │ MQTT        │ MQTT        │ MQTT        │ MQTT
        │ (直连)      │ (直连)      │ (直连)      │ (批量)
        ▼
┌───────────────────────────────────────────────────────────────────────────────┐
│                           MQTT Broker (EMQX)                                   │
│                                                                               │
│   订阅主题:                                                                   │
│   - electric/emsCarson                                                        │
│   - electric/all/+                                                            │
│   - electric/voltage                                                          │
│   - electric/current                                                          │
│   - water/consumption/+                                                       │
│                                                                               │
└─────────────────────────────────┬───────────────────────────────────────────┘
                                  │
                                  │ 消息转发
                                  ▼
┌───────────────────────────────────────────────────────────────────────────────┐
│                      RabbitMQ Topic Exchange                                   │
│                        (EquipmentTopicExchange)                                │
│                                                                               │
│   路由键:                                                                     │
│   - electric/emsCarson                                                        │
│   - electric/voltage                                                         │
│   - electric/current                                                         │
│   - water/consumption/*                                                      │
│                                                                               │
└───────┬───────────────┬───────────────┬───────────────┬───────────────────┘
        │               │               │               │
        ▼               ▼               ▼               ▼
┌───────────────┐ ┌───────────────┐ ┌───────────────┐ ┌───────────────┐
│  UQueue       │ │  IQueue       │ │  WQueue       │ │  CarsonQueue  │
│ electric/     │ │ electric/     │ │ water/        │ │ electric/     │
│ voltage       │ │ current       │ │ consumption   │ │ emsCarson     │
└───────┬───────┘ └───────┬───────┘ └───────┬───────┘ └───────┬───────┘
        │                 │                 │                 │
        └─────────────────┴────────┬────────┴─────────────────┘
                                   │
                                   ▼
┌───────────────────────────────────────────────────────────────────────────────┐
│                         MyAckReceiver (消费者)                                  │
│                                                                               │
│   1. 消息反序列化                                                              │
│   2. 数据校验                                                                  │
│   3. 保存到 MySQL (历史数据)                                                   │
│   4. 保存到 TDengine (时序数据，可选)                                           │
│   5. 报警规则判断                                                              │
│   6. 发送通知 (邮件/工单)                                                       │
│                                                                               │
└─────────────────────────────────┬───────────────────────────────────────────┘
                                  │
        ┌─────────────┬───────────┴───────────┬─────────────┐
        ▼             ▼                       ▼             ▼
┌──────────────┐ ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│    MySQL     │ │   TDengine   │ │    Redis     │ │    报警      │
│  (历史数据)   │ │ (时序数据)    │ │   (缓存)     │ │  (邮件/工单)  │
└──────────────┘ └──────────────┘ └──────────────┘ └──────────────┘
```

### 4.2 关键代码路径

#### Step 1: MQTT 消息接收

**文件路径**：`zhurong-ems-system/src/main/java/com/ruoyi/system/config/MyMQTTCallback.java`

```java
@Override
public void messageArrived(String topic, MqttMessage mqttMessage) {
    log.info("接收消息主题 : {}，接收消息内容 : {}", topic, new String(mqttMessage.getPayload()));
    
    // 处理电表所有数据打包上传(emsCarson)
    if (topic.equals(TopicType.ELECTRIC_emsCarson.getInfo())) {
        Map<String, Object> maps = JSON.parseObject(...);
        ensureCreateTime(maps);
        rabbitTemplate.convertAndSend(
            "EquipmentTopicExchange",
            TopicType.ELECTRIC_emsCarson.getInfo(),
            maps
        );
    }
    // 处理 electric/all/+ - 数组解析逻辑
    else if (topic.startsWith("electric/all/")) {
        List<Map<String, Object>> dataList = JSON.parseObject(...);
        for (Map<String, Object> item : dataList) {
            String deviceType = MapUtil.getStr(item, "deviceType");
            TopicType targetTopic = getTargetTopicByDeviceType(deviceType);
            String clientId = MapUtil.getStr(item, "clientId");
            String value = MapUtil.getStr(item, "value");
            String createTime = MapUtil.getStr(item, "createTime");
            
            Map<String, Object> forwardData = new HashMap<>();
            forwardData.put("clientId", clientId);
            forwardData.put("value", value);
            forwardData.put("createTime", createTime);
            
            rabbitTemplate.convertAndSend(
                "EquipmentTopicExchange",
                targetTopic.getInfo(),
                forwardData
            );
        }
    }
}
```

#### Step 2: RabbitMQ 交换机配置

**文件路径**：`zhurong-ems-system/src/main/java/com/ruoyi/system/config/RabbitExChangeConfig.java`

```java
@Configuration
public class RabbitExChangeConfig {
    
    /**
     * Topic 交换机 - 用于设备数据路由
     */
    @Bean
    public TopicExchange EquipmentTopicExchange() {
        return new TopicExchange("EquipmentTopicExchange", true, false);
    }
    
    /**
     * 电压数据队列
     */
    @Bean
    public Queue UQueue() {
        return new Queue(TopicType.ELECTRIC_U.getInfo(), true);
    }
    
    /**
     * 电流数据队列
     */
    @Bean
    public Queue IQueue() {
        return new Queue(TopicType.ELECTRIC_I.getInfo(), true);
    }
    
    /**
     * 功率数据队列
     */
    @Bean
    public Queue PQueue() {
        return new Queue(TopicType.ELECTRIC_P.getInfo(), true);
    }
    
    /**
     * 用电量数据队列
     */
    @Bean
    public Queue WQueue() {
        return new Queue(TopicType.ELECTRIC_W.getInfo(), true);
    }
    
    /**
     * 电表打包数据队列
     */
    @Bean
    public Queue CarsonQueue() {
        return new Queue(TopicType.ELECTRIC_emsCarson.getInfo(), true);
    }
    
    // ========== 绑定关系 ==========
    
    @Bean
    public Binding UExchangeMessage() {
        return BindingBuilder.bind(UQueue())
            .to(EquipmentTopicExchange())
            .with(TopicType.ELECTRIC_U.getInfo());
    }
    
    @Bean
    public Binding IExchangeMessage() {
        return BindingBuilder.bind(IQueue())
            .to(EquipmentTopicExchange())
            .with(TopicType.ELECTRIC_I.getInfo());
    }
    
    @Bean
    public Binding PExchangeMessage() {
        return BindingBuilder.bind(PQueue())
            .to(EquipmentTopicExchange())
            .with(TopicType.ELECTRIC_P.getInfo());
    }
    
    @Bean
    public Binding WExchangeMessage() {
        return BindingBuilder.bind(WQueue())
            .to(EquipmentTopicExchange())
            .with(TopicType.ELECTRIC_W.getInfo());
    }
    
    @Bean
    public Binding CarsonExchangeMessage() {
        return BindingBuilder.bind(CarsonQueue())
            .to(EquipmentTopicExchange())
            .with(TopicType.ELECTRIC_emsCarson.getInfo());
    }
}
```

#### Step 3: RabbitMQ 消费者处理

**文件路径**：`zhurong-ems-system/src/main/java/com/ruoyi/system/config/MyAckReceiver.java`

```java
@Override
public void onMessage(Message message, Channel channel) {
    long deliveryTag = message.getMessageProperties().getDeliveryTag();
    try {
        // 1. 反序列化消息
        Map<String, Object> msgMap = (Map<String, Object>) ois.readObject();
        BigDecimal value = new BigDecimal(msgMap.get("value").toString());
        String clientId = msgMap.get("clientId").toString();
        String createTime = msgMap.get("createTime").toString();
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        
        // 2. 保存到 MySQL 数据库
        saveDataToDB(clientId, routingKey, createTime, value);
        
        // 3. 保存到 TDengine 时序数据库 (可选)
        if (tdDbEnabled) {
            saveDataToTDengine(clientId, routingKey, createTime, value);
        }
        
        // 4. 报警规则判断
        checkAlarmRules(routingKey, value, clientId, createTime);
        
        // 5. 确认消息
        channel.basicAck(deliveryTag, true);
    } catch (Exception e) {
        log.error("消息处理失败", e);
        channel.basicReject(deliveryTag, false);  // 拒绝消息，不重新入队
    }
}

/**
 * 保存数据到 MySQL
 */
private void saveDataToDB(String clientId, String routingKey, String createTime, BigDecimal value) {
    if ("electric/voltage".equals(routingKey)) {
        ElectricityU electricityU = new ElectricityU();
        electricityU.setClientId(clientId);
        electricityU.setValue(value);
        electricityU.setCreateTime(parseTime(createTime));
        electricityUMapper.insert(electricityU);
    }
    // ... 其他数据类型处理
}

/**
 * 保存数据到 TDengine
 */
private void saveDataToTDengine(String clientId, String routingKey, String createTime, BigDecimal value) {
    // TDengine 超表创建和写入逻辑
    // ...
}

/**
 * 检查报警规则
 */
private void checkAlarmRules(String routingKey, BigDecimal value, String clientId, String createTime) {
    // 1. 查询报警规则
    // 2. 条件判断
    // 3. 状态变化检测
    // 4. 触发通知
}
```

#### Step 4: 数据存储

**数据表结构示例 - 电压数据表**：

```java
@Data
@TableName("electricity_u")
public class ElectricityU {
    @TableId(value = "id")
    private Long id;
    
    private String clientId;      // 设备SN
    private BigDecimal value;     // 电压值
    private Date createTime;      // 创建时间
}
```

**数据库表**：

| 表名 | 说明 | 主要字段 |
|------|------|---------|
| electricity_u | 电压数据 | id, client_id, value, create_time |
| electricity_i | 电流数据 | id, client_id, value, create_time |
| electricity_p | 功率数据 | id, client_id, value, create_time |
| electricity_w | 用电量数据 | id, client_id, value, create_time |
| equipment_info | 设备信息 | id, name, sn, model, type, status |
| realtime_alarm | 实时报警 | id, client_id, alarm_type, value, status |

### 4.3 报警处理流程

**报警处理逻辑**：

```
┌─────────────────────────────────────────────────────────────────┐
│                        报警处理流程                               │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  1. 查询报警规则                                                  │
│     └── 根据设备类型和参数名查询启用的报警规则                       │
│                                                                 │
│  2. 条件判断 (6种条件类型)                                        │
│     ├── G  (value > threshold)     大于阈值                      │
│     ├── L  (value < threshold)     小于阈值                      │
│     ├── E  (value == threshold)    等于阈值                      │
│     ├── GE (value >= threshold)    大于等于                       │
│     ├── LE (value <= threshold)    小于等于                       │
│     └── 双阈值 (low < value < high)  范围外                       │
│                                                                 │
│  3. 防重复报警                                                    │
│     └── 仅当状态发生跃变时触发新报警                               │
│         - 正常 → 报警                                             │
│         - 报警 → 恢复正常                                         │
│                                                                 │
│  4. 通知处理                                                      │
│     ├── 插入实时报警记录 (realtime_alarm)                         │
│     ├── 发送邮件通知 (可选)                                       │
│     └── 自动创建维修工单 (可选)                                    │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 五、设备端连接方案

### 5.1 设备连接方式

系统支持 **两种设备连接方式**：

#### 方式一：设备直连 MQTT Broker

适用于单个设备直接上报数据的场景：

```
┌─────────────┐                           ┌──────────────┐
│   设备端    │  ────── MQTT ──────>      │  MQTT Broker │
│  (电表/水表) │                            │    (EMQX)    │
└─────────────┘                            └──────────────┘
```

**设备端配置要求**：

| 配置项 | 值 |
|--------|-----|
| Broker 地址 | tcp://192.168.8.54:1883 |
| 用户名 | admin |
| 密码 | admin (test环境) |
| 客户端ID | 唯一设备标识 (如 SN123456) |
| QoS 级别 | 1 |
| 心跳间隔 | 60秒 |

**设备端发布消息示例**：

```json
// Topic: electric/voltage
{
  "clientId": "SN123456",
  "value": 220.5,
  "createTime": "2026-04-03 12:00:00"
}
```

#### 方式二：网关转发模式

适用于多个设备通过网关批量上报的场景：

```
┌─────────────┐     ┌─────────────┐
│   设备1    │     │   设备2    │
│   设备3    │ ──> │   网关     │ ──> MQTT ──> Broker
│   ...      │     │  (数据聚合)  │
└─────────────┘     └─────────────┘
```

**网关配置**：

| 配置项 | 值 |
|--------|-----|
| Broker 地址 | tcp://192.168.8.54:1883 |
| Topic | electric/all/{gatewayId} |
| 数据格式 | 数组 |

**网关发布消息示例**：

```json
// Topic: electric/all/gateway001
[
  {"clientId": "SN001", "value": 220.5, "createTime": "2026-04-03 12:00:00"},
  {"clientId": "SN002", "value": 221.0, "createTime": "2026-04-03 12:00:00"},
  {"clientId": "SN003", "value": 219.8, "createTime": "2026-04-03 12:00:00"}
]
```

### 5.2 设备数据模型

**设备信息表 - EquipmentInfo**：

```java
@Data
@TableName("equipment_info")
public class EquipmentInfo extends BaseEntity {
    private Long id;
    private String name;       // 设备名称
    private String sn;        // 设备SN (与MQTT clientId对应)
    private String model;     // 设备型号
    private String type;      // 设备类型 (electric/water)
    private String status;    // 设备状态 (online/offline)
    private String factory;   // 所属工厂
    private String location;  // 安装位置
    private Date registerTime; // 注册时间
    private Date lastOnlineTime; // 最后上线时间
}
```

### 5.3 设备上线/下线流程

```
┌─────────────────────────────────────────────────────────────────┐
│                      设备生命周期管理                              │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  1. 设备注册                                                     │
│     └── 设备首次连接时，自动创建 equipment_info 记录               │
│                                                                 │
│  2. 设备上线                                                     │
│     └── MQTT 连接建立 → 更新 last_online_time → status=online    │
│                                                                 │
│  3. 心跳保活                                                     │
│     └── MQTT keepalive (360秒) → 维持连接状态                     │
│                                                                 │
│  4. 设备离线                                                     │
│     └── MQTT 连接断开 → 检测离线 → status=offline                │
│                                                                 │
│  5. 数据采集                                                     │
│     └── 设备上报数据 → 系统存储 → 报警检测                        │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 六、中间件依赖关系

### 6.1 部署架构图

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              Nginx (反向代理)                                 │
│                           http://192.168.8.54:80                             │
└─────────────────────────────────┬───────────────────────────────────────────┘
                                  │
                                  ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                       Deep-EMS 应用集群                                      │
│                                                                              │
│   ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐              │
│   │  zhurong-ems-  │  │  zhurong-ems-  │  │  zhurong-ems-  │              │
│   │    admin-1     │  │    admin-2     │  │    job-1       │              │
│   │  (Undertow)    │  │  (Undertow)    │  │  (XXL-Job)     │              │
│   │   port:8088    │  │   port:8088    │  │   port:8081    │              │
│   └────────┬────────┘  └────────┬────────┘  └────────┬────────┘              │
└────────────┼─────────────────────┼─────────────────────┼──────────────────────┘
             │                     │                     │
    ┌────────┴────────┬─────────────┴──────────┬──────────┴────────┐
    │                 │                       │                    │
    ▼                 ▼                       ▼                    ▼
┌─────────┐    ┌─────────┐    ┌─────────────┐    ┌─────────┐    ┌─────────────┐
│  MySQL  │    │  Redis  │    │  RabbitMQ   │    │  EMQX   │    │  TDengine   │
│ :3326   │    │  :6379  │    │    :5672    │    │  :1883  │    │   :6041     │
│         │    │ db:13   │    │             │    │ (Web:   │    │  (可选)     │
│         │    │         │    │             │    │  18083) │    │             │
└─────────┘    └─────────┘    └─────────────┘    └─────────┘    └─────────────┘
   数据          缓存/          消息队列          MQTT           时序数据
   存储          会话           异步处理          Broker         存储
```

### 6.2 端口清单

| 中间件 | 端口 | 用途 |
|--------|------|------|
| **应用服务** | | |
| Spring Boot Admin | 9101 (dev) / 1088 (test) | HTTP 服务端口 |
| XXL-Job Admin | 8080 | 任务调度管理 |
| **数据库** | | |
| MySQL | 3326 | 主数据库 |
| **缓存** | | |
| Redis | 6379 | 缓存、Session、分布式锁 |
| **消息队列** | | |
| RabbitMQ | 5672 | AMQP 协议端口 |
| RabbitMQ Management | 15672 | Web 管理界面 |
| **物联网** | | |
| EMQX | 1883 | MQTT 协议端口 |
| EMQX Dashboard | 18083 | Web 管理界面 |
| **时序数据库** | | |
| TDengine | 6041 | REST API 端口 |
| TDengine | 6030 | TAOS Shell 端口 |

### 6.3 中间件版本要求

| 中间件 | 最低版本 | 推荐版本 |
|--------|----------|----------|
| MySQL | 5.7+ | 8.0 |
| Redis | 3.0+ | 7.2 |
| RabbitMQ | 3.8+ | 3.12 |
| EMQ X | 4.0+ | 5.0 |
| TDengine | 3.0+ | 3.2.x |
| Nginx | 1.18+ | 1.24 |

---

## 七、关键技术点总结

| 功能 | 实现方式 | 文件位置 |
|------|----------|----------|
| MQTT 连接 | Eclipse Paho 库 | `MyMQTTClient.java` |
| MQTT 配置注入 | Spring `@Configuration` + `@Value` | `MqttConfiguration.java` |
| 消息接收处理 | `MqttCallbackExtended` | `MyMQTTCallback.java` |
| 消息持久化转发 | RabbitMQ Topic Exchange | `RabbitExChangeConfig.java` |
| 消息消费处理 | `ChannelAwareMessageListener` | `MyAckReceiver.java` |
| 数据存储 | MyBatis Plus + TDengine | 各 `*Mapper.java` |
| 报警检测 | 规则匹配 + 邮件通知 | `MyAckReceiver.java` |
| 自动重连 | `MqttConnectOptions.setAutomaticReconnect()` | `MyMQTTClient.java` |
| 定时任务 | XXL-Job | `zhurong-ems-job` 模块 |
| 权限认证 | Sa-Token | `zhurong-ems-framework` 模块 |

---

## 八、关键文件路径汇总

| 分类 | 文件路径 |
|------|----------|
| **MQTT 相关** | |
| MQTT 配置类 | `zhurong-ems-system/src/main/java/com/ruoyi/system/config/MqttConfiguration.java` |
| MQTT 客户端 | `zhurong-ems-system/src/main/java/com/ruoyi/system/config/MyMQTTClient.java` |
| MQTT 回调处理 | `zhurong-ems-system/src/main/java/com/ruoyi/system/config/MyMQTTCallback.java` |
| MQTT 消息模型 | `zhurong-ems-system/src/main/java/com/ruoyi/system/config/MqttMsg.java` |
| MQTT 主题枚举 | `zhurong-ems-system/src/main/java/com/ruoyi/system/domain/enums/TopicType.java` |
| 电表打包数据 | `zhurong-ems-system/src/main/java/com/ruoyi/system/domain/vo/ElectricEmsCarsonData.java` |
| **RabbitMQ 相关** | |
| 交换机配置 | `zhurong-ems-system/src/main/java/com/ruoyi/system/config/RabbitExChangeConfig.java` |
| 消息消费者 | `zhurong-ems-system/src/main/java/com/ruoyi/system/config/MyAckReceiver.java` |
| **数据模型** | |
| 设备信息 | `zhurong-ems-system/src/main/java/com/ruoyi/system/domain/EquipmentInfo.java` |
| 电压数据 | `zhurong-ems-system/src/main/java/com/ruoyi/system/domain/ElectricityU.java` |
| 电流数据 | `zhurong-ems-system/src/main/java/com/ruoyi/system/domain/ElectricityI.java` |
| 功率数据 | `zhurong-ems-system/src/main/java/com/ruoyi/system/domain/ElectricityP.java` |
| 用电数据 | `zhurong-ems-system/src/main/java/com/ruoyi/system/domain/ElectricityW.java` |
| **配置文件** | |
| 主配置文件 | `zhurong-ems-admin/src/main/resources/application.yml` |
| 开发环境配置 | `zhurong-ems-admin/src/main/resources/application-dev.yml` |
| 测试环境配置 | `zhurong-ems-admin/src/main/resources/application-test.yml` |
| 生产环境配置 | `zhurong-ems-admin/src/main/resources/application-prod.yml` |

---

## 九、环境配置差异

### 9.1 多环境配置对比

| 配置项 | dev 环境 | test 环境 | prod 环境 |
|--------|----------|-----------|-----------|
| HTTP 端口 | 8088 | 1088 | 1088 |
| MySQL | localhost:3306 | 192.168.8.54:3326 | 192.168.8.54:3326 |
| Redis | localhost:6379 | 192.168.8.54:6379 | 192.168.8.54:6379 |
| RabbitMQ | localhost:5672 | 192.168.8.54:5672 | 192.168.8.54:5672 |
| MQTT Broker | localhost:1883 | 192.168.8.54:1883 | 192.168.8.54:1883 |
| 文件上传路径 | D:/ruoyi/uploadPath | D:/zhurong-ems/uploads | /data/ems/uploads |

### 9.2 启动命令

```bash
# 开发环境
java -jar zhurong-ems-admin.jar --spring.profiles.active=dev

# 测试环境
java -jar zhurong-ems-admin.jar --spring.profiles.active=test

# 生产环境
java -jar zhurong-ems-admin.jar --spring.profiles.active=prod
```

---

## 十、故障排查指南

### 10.1 MQTT 连接失败

**症状**：
```
ERROR - 初始化连接MQTT失败，请检查MQTT配置信息。请尝试连接次数 1/10
```

**排查步骤**：

1. **检查 MQTT Broker 是否运行**
   ```bash
   # 检查 EMQX 容器状态
   docker ps | grep emqx
   
   # 检查端口是否监听
   netstat -an | grep 1883
   ```

2. **检查网络连通性**
   ```bash
   telnet 192.168.8.54 1883
   ```

3. **检查用户名密码**
   ```yaml
   # 确认 application-dev.yml 中配置正确
   mqtt:
     username: admin
     password: 121212Aa  # 确认密码
   ```

4. **检查客户端ID是否唯一**
   ```yaml
   # 确保 clientId 不与其他客户端冲突
   clientId: devClientID123
   ```

### 10.2 消息无法消费

**症状**：MQTT 消息到达 Broker，但数据库无数据

**排查步骤**：

1. **检查 RabbitMQ 连接**
   ```bash
   # 检查 RabbitMQ 管理界面
   http://192.168.8.54:15672
   ```

2. **检查队列状态**
   - 确认队列存在
   - 确认队列有消费者
   - 检查消息积压情况

3. **检查应用日志**
   ```bash
   # 查看消息消费日志
   tail -f app.log | grep "MyAckReceiver"
   ```

### 10.3 数据未写入数据库

**排查步骤**：

1. **检查 MySQL 连接**
2. **检查设备 SN 是否在设备表中存在**
3. **检查时间格式是否正确**
4. **检查数值范围是否合理**

---

## 十一、附录

### 11.1 EMQ X 管理界面

| 环境 | 地址 | 默认账号 |
|------|------|----------|
| 本地 | http://localhost:18083 | admin / public |
| 测试 | http://192.168.8.54:18083 | admin / public |

### 11.2 RabbitMQ 管理界面

| 环境 | 地址 | 默认账号 |
|------|------|----------|
| 本地 | http://localhost:15672 | guest / guest |
| 测试 | http://192.168.8.54:15672 | guest / guest |

### 11.3 相关资源

- Eclipse Paho Java Client: https://www.eclipse.org/paho/
- EMQ X 文档: https://www.emqx.io/docs/zh/
- RabbitMQ 文档: https://www.rabbitmq.com/documentation.html
- TDengine 文档: https://docs.taosdata.com/

---

*文档结束*
