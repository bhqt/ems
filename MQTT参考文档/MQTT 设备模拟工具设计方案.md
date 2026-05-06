# MQTT 设备模拟工具设计方案

## 一、项目背景

基于祝融能源管理系统（Deep-EMS）的 MQTT 消息链路分析，设计一款用于测试和压力验证的 MQTT 设备模拟工具。

### 1.1 系统 MQTT 消息链路

```
设备 → MQTT Broker (EMQX) → 后端服务 → RabbitMQ → 数据存储 → 后端 API → 前端展示
```

### 1.2 支持的 MQTT 主题

| 序号 | 主题 | 说明 | 数据格式 |
|------|------|------|----------|
| 1 | `electric/emsCarson` | 电表综合数据 | JSON 对象 |
| 2 | `electric/all/{clientId}` | 电表所有数据（数组） | JSON 数组 |
| 3 | `electric/voltage/{clientId}` | 电压数据 | JSON 对象 |
| 4 | `electric/current/{clientId}` | 电流数据 | JSON 对象 |
| 5 | `electric/power/{clientId}` | 功率数据 | JSON 对象 |
| 6 | `electric/consumption/{clientId}` | 电能数据 | JSON 对象 |
| 7 | `water/consumption/{clientId}` | 水表数据（数组） | JSON 数组 |

## 二、工具架构设计

### 2.1 整体架构

```
MQTT 设备模拟工具 (mqtt-device-simulator)
├── 核心模块
│   ├── MQTT 连接管理器 (MqttConnectionManager)
│   ├── 消息生成器 (MessageGenerator)
│   ├── 单消息模拟器 (SingleMessageSimulator)
│   └── 并发压力测试器 (ConcurrencyStressTester)
├── 配置模块
│   ├── 连接配置 (ConnectionConfig)
│   ├── 消息模板配置 (MessageTemplateConfig)
│   └── 压力测试配置 (StressTestConfig)
├── 数据模块
│   ├── 消息模板库 (Template Library)
│   ├── 测试数据生成器 (TestDataGenerator)
│   └── 结果记录器 (ResultRecorder)
└── 工具模块
    ├── 日志管理器 (Logger)
    ├── 报告生成器 (ReportGenerator)
    └── 命令行接口 (CLI)
```

### 2.2 技术选型

**推荐方案：Python 实现**
- **MQTT 客户端库**: `paho-mqtt` (与后端 Java 端使用的 Eclipse Paho 同源)
- **并发模型**: `asyncio` + `aio-mqtt` 或 `concurrent.futures`
- **配置管理**: `pyyaml` (YAML 配置文件)
- **数据生成**: `faker` (生成逼真的设备数据)
- **命令行**: `argparse` 或 `click`
- **报告生成**: `jinja2` + `markdown`

**选择 Python 的理由**：
1. 快速开发和原型验证
2. 丰富的 MQTT 库支持
3. 优秀的并发处理能力
4. 便于后续集成到自动化测试流程

## 三、核心功能设计

### 3.1 单条消息模拟功能

**功能特性**：
- ✅ 支持所有 MQTT 主题类型
- ✅ 自定义设备 ID (clientId)
- ✅ 自定义数据值
- ✅ 自动/手动时间戳
- ✅ 消息发送验证
- ✅ 详细日志记录

**支持的主题类型**：
```python
SUPPORTED_TOPICS = {
    "electric/emsCarson": "电表综合数据",
    "electric/all/{clientId}": "电表所有数据 (数组格式)",
    "electric/voltage/{clientId}": "电压数据",
    "electric/current/{clientId}": "电流数据",
    "electric/power/{clientId}": "功率数据",
    "electric/consumption/{clientId}": "电能数据",
    "water/consumption/{clientId}": "水表数据 (数组格式)"
}
```

**使用示例**：
```bash
# 发送单条电压数据
python mqtt_simulator.py send ^
  --topic "electric/voltage" ^
  --client-id "device_001" ^
  --data '{"value": 220.5}'

# 发送电表综合数据
python mqtt_simulator.py send ^
  --topic "electric/emsCarson" ^
  --data '{"Ua":220,"Ia":5,"P":1100}'

# 发送电表所有数据（数组格式）
python mqtt_simulator.py send ^
  --topic "electric/all" ^
  --client-id "gateway_001" ^
  --template "all_devices"
```

### 3.2 并发消息模拟功能（压力测试）

**功能特性**：
- ✅ 可配置并发设备数量 (1-N 个设备)
- ✅ 可配置发送频率 (消息/秒)
- ✅ 可配置测试持续时间
- ✅ 随机/顺序数据生成
- ✅ 实时统计和监控
- ✅ 性能指标收集
- ✅ 错误处理和重试机制

**压力测试场景**：
```python
STRESS_TEST_SCENARIOS = {
    "light": {"devices": 10, "frequency": 1, "duration": 60},      # 轻量测试
    "medium": {"devices": 100, "frequency": 5, "duration": 300},   # 中等压力
    "heavy": {"devices": 1000, "frequency": 10, "duration": 600},  # 重度压力
    "custom": {"devices": "N", "frequency": "X", "duration": "T"}  # 自定义
}
```

**使用示例**：
```bash
# 启动轻量压力测试
python mqtt_simulator.py stress ^
  --scenario light ^
  --report "test_report.md"

# 自定义压力测试：50 个设备，每秒 2 条消息，持续 5 分钟
python mqtt_simulator.py stress ^
  --devices 50 ^
  --frequency 2 ^
  --duration 300 ^
  --output "stress_test_result.json"
```

## 四、消息模板设计

根据后端 `MyMQTTCallback.java` 的解析逻辑，设计以下消息模板：

### 4.1 电表综合数据模板 (electric/emsCarson)
```json
{
  "clientId": "emsCarson_001",
  "Ua": 220.5,
  "Ub": 220.3,
  "Uc": 220.4,
  "Ia": 5.2,
  "Ib": 5.1,
  "Ic": 5.3,
  "P": 1150.5,
  "Q": 230.2,
  "S": 1173.8,
  "PF": 0.98,
  "F": 50.0,
  "createTime": "2026-04-30 10:00:00"
}
```

### 4.2 电表所有数据模板 (electric/all/{clientId})
```json
[
  {
    "clientId": "device_001",
    "deviceType": "electric/voltage",
    "value": 220.5
  },
  {
    "clientId": "device_001",
    "deviceType": "electric/current",
    "value": 5.2
  },
  {
    "clientId": "device_001",
    "deviceType": "electric/power",
    "value": 1150.5
  },
  {
    "clientId": "device_001",
    "deviceType": "electric/consumption",
    "value": 125.8
  }
]
```

### 4.3 电压/电流/功率/电能数据模板
```json
{
  "clientId": "device_001",
  "value": 220.5,
  "createTime": "2026-04-30 10:00:00"
}
```

### 4.4 水表数据模板 (water/consumption/{clientId})
```json
[
  {
    "clientId": "water_meter_001",
    "value": 125.5,
    "createTime": "2026-04-30 10:00:00"
  },
  {
    "clientId": "water_meter_002",
    "value": 98.3,
    "createTime": "2026-04-30 10:00:00"
  }
]
```

## 五、配置文件设计

### 5.1 连接配置 (config.yaml)
```yaml
mqtt:
  host: "tcp://localhost:1883"
  username: "admin"
  password: "public"
  client_id: "mqtt-simulator-${HOSTNAME}"
  timeout: 120
  keepalive: 360
  qos: 0
  retain: false

logging:
  level: "INFO"  # DEBUG, INFO, WARNING, ERROR
  file: "logs/mqtt_simulator.log"
  format: "%(asctime)s - %(levelname)s - %(message)s"

stress_test:
  default_scenario: "light"
  scenarios:
    light:
      devices: 10
      frequency: 1  # messages per second
      duration: 60  # seconds
    medium:
      devices: 100
      frequency: 5
      duration: 300
    heavy:
      devices: 1000
      frequency: 10
      duration: 600
```

### 5.2 数据模板配置 (templates.yaml)
```yaml
templates:
  voltage:
    topic: "electric/voltage"
    data_pattern:
      clientId: "device_{id:03d}"
      value: "random(215.0, 225.0, 2)"  # 随机值，保留 2 位小数
      createTime: "auto"  # 自动生成
    
  current:
    topic: "electric/current"
    data_pattern:
      clientId: "device_{id:03d}"
      value: "random(0.0, 10.0, 2)"
      
  power:
    topic: "electric/power"
    data_pattern:
      clientId: "device_{id:03d}"
      value: "random(100.0, 5000.0, 2)"
      
  consumption:
    topic: "electric/consumption"
    data_pattern:
      clientId: "device_{id:03d}"
      value: "random(0.0, 1000.0, 2)"
      
  water:
    topic: "water/consumption"
    data_pattern:
      clientId: "water_{id:03d}"
      value: "random(0.0, 500.0, 2)"
```

## 六、项目文件结构

```
D:\code\gitcp\inspur-ems\deep-ems0\simulator\
├── src/
│   ├── __init__.py
│   ├── main.py                  # 主入口
│   ├── config.py                # 配置加载
│   ├── mqtt_client.py           # MQTT 客户端封装
│   ├── message_generator.py     # 消息生成器
│   ├── single_simulator.py      # 单消息模拟
│   ├── stress_tester.py         # 压力测试
│   ├── templates.py             # 消息模板
│   ├── reporters.py             # 报告生成
│   └── utils.py                 # 工具函数
├── config/
│   ├── config.yaml              # 主配置文件
│   ├── templates.yaml           # 消息模板配置
│   └── scenarios.yaml           # 压力测试场景
├── templates/
│   ├── report_template.md.j2    # 报告模板
│   └── data_templates.json      # 数据模板 JSON
├── logs/
│   └── .gitkeep
├── output/
│   └── .gitkeep                 # 测试报告输出
├── tests/
│   ├── test_single_message.py
│   ├── test_stress_test.py
│   └── test_templates.py
├── requirements.txt             # Python 依赖
├── README.md                    # 使用说明
└── run.bat                      # Windows 快速启动脚本
```

## 七、核心代码实现要点

### 7.1 MQTT 客户端封装
```python
class MqttClientWrapper:
    def __init__(self, config):
        self.config = config
        self.client = mqtt.Client(client_id=config.client_id)
        self.client.username_pw_set(config.username, config.password)
        self.connected = False
        
    def connect(self):
        """连接到 MQTT Broker"""
        self.client.connect(self.config.host, keepalive=self.config.keepalive)
        self.client.loop_start()
        
    def publish(self, topic, payload, qos=0, retained=False):
        """发布消息"""
        result = self.client.publish(topic, json.dumps(payload), qos, retained)
        return result.is_published()
        
    def disconnect(self):
        """断开连接"""
        self.client.loop_stop()
        self.client.disconnect()
```

### 7.2 并发压力测试器
```python
class StressTester:
    def __init__(self, mqtt_client, config):
        self.mqtt_client = mqtt_client
        self.config = config
        self.stats = {
            'sent': 0,
            'failed': 0,
            'start_time': None,
            'end_time': None
        }
        
    async def run_test(self):
        """运行压力测试"""
        tasks = []
        for i in range(self.config.devices):
            task = asyncio.create_task(
                self.device_worker(device_id=i)
            )
            tasks.append(task)
        
        await asyncio.gather(*tasks)
        
    async def device_worker(self, device_id):
        """单个设备的消息发送协程"""
        interval = 1.0 / self.config.frequency
        for _ in range(int(self.config.duration * self.config.frequency)):
            try:
                topic, payload = generate_message(device_id)
                await self.mqtt_client.publish_async(topic, payload)
                self.stats['sent'] += 1
            except Exception as e:
                self.stats['failed'] += 1
            await asyncio.sleep(interval)
```

## 八、测试报告输出

压力测试完成后自动生成 Markdown 报告：

```markdown
# MQTT 压力测试报告

## 测试配置
- 设备数量：100
- 发送频率：5 消息/秒
- 测试持续时间：300 秒
- 测试场景：medium

## 测试结果
- 总发送消息数：150,000
- 成功发送：149,856
- 失败消息：144
- 成功率：99.904%

## 性能指标
- 平均延迟：45ms
- 最大延迟：230ms
- 最小延迟：12ms
- 吞吐量：500 消息/秒

## 时间线
- 开始时间：2026-04-30 10:00:00
- 结束时间：2026-04-30 10:05:00
```

## 九、开发计划

### 第一阶段：基础框架（1-2 天）
- [ ] 创建项目目录结构
- [ ] 实现 MQTT 客户端封装
- [ ] 实现配置文件加载
- [ ] 实现命令行接口

### 第二阶段：单消息模拟（1 天）
- [ ] 实现消息生成器
- [ ] 实现单消息发送功能
- [ ] 实现消息模板系统
- [ ] 添加日志记录

### 第三阶段：并发压力测试（2-3 天）
- [ ] 实现异步并发框架
- [ ] 实现设备工作协程
- [ ] 实现实时统计和监控
- [ ] 实现错误处理和重试

### 第四阶段：报告和测试（1-2 天）
- [ ] 实现测试报告生成
- [ ] 编写单元测试
- [ ] 编写使用文档
- [ ] 集成测试和优化

## 十、关键技术实现

### 10.1 MQTT 连接管理
- 自动重连机制，确保连接稳定性
- 心跳保活，防止连接超时
- 连接状态监控和日志记录

### 10.2 数据格式转换
- JSON 数据解析和序列化
- 时间戳格式统一（yyyy-MM-dd HH:mm:ss）
- 数据类型转换和校验

### 10.3 数据生成逻辑
- 随机数据生成（支持范围控制）
- 设备 ID 自动编号
- 时间戳自动补充

### 10.4 并发控制
- 异步协程调度
- 限流和背压机制
- 资源管理和清理

## 十一、潜在问题和解决方案

### 11.1 MQTT 连接数限制
**问题**：大量设备模拟可能导致 Broker 连接数超限

**解决方案**：
- 支持连接池管理
- 实现连接复用
- 可配置最大连接数

### 11.2 消息积压
**问题**：发送速度过快可能导致消息积压

**解决方案**：
- 实现背压机制
- 支持 QoS 流量控制
- 添加发送速率限制

### 11.3 内存占用
**问题**：大量并发协程可能占用较多内存

**解决方案**：
- 协程批处理
- 内存池优化
- 资源使用监控

## 十二、总结

### 12.1 功能清单
- ✅ 单条消息模拟（链路打通测试）
- ✅ 并发消息模拟（压力测试）
- ✅ 灵活的消息模板系统
- ✅ 详细的测试报告生成
- ✅ 完善的日志记录
- ✅ 命令行友好交互

### 12.2 技术优势
- 基于 Python 快速开发
- 异步并发高效处理
- 配置驱动灵活部署
- 易于扩展和维护

### 12.3 后续扩展
- 支持 WebSocket 协议
- 集成到 CI/CD 流程
- 图形化界面支持
- 分布式压力测试

---

**报告版本**: v1.0  
**设计时间**: 2026-04-30  
**设计人员**: AI 代码助手  
**项目名称**: 祝融能源管理系统 MQTT 设备模拟工具
