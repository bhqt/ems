# MQTT 设备模拟工具 - 使用示例

本文档提供详细的使用示例，帮助您快速上手 MQTT 设备模拟工具。

## 示例 1：发送单条电压消息

### 方式 1：使用模板

```bash
python main.py send -t electric/voltage -cid device_001 -tpl voltage
```

**输出**:
```
✓ 消息发送成功
消息 ID: 1
主题：electric/voltage/device_001
耗时：45.23ms
大小：56 bytes
```

### 方式 2：自定义数据

```bash
python main.py send -t electric/voltage -cid device_001 -d '{"value": 220.5}'
```

### 方式 3：完整参数

```bash
python main.py send \
  -t electric/voltage \
  -cid device_001 \
  -d '{"value": 220.5, "unit": "V"}' \
  -q 1 \
  -r
```

---

## 示例 2：发送电表综合数据

```bash
python main.py send -t electric/emsCarson -tpl emsCarson
```

**生成的消息**:
```json
{
  "clientId": "emsCarson_001",
  "Ua": 220.5,
  "Ub": 219.8,
  "Uc": 221.2,
  "Ia": 5.23,
  "Ib": 5.18,
  "Ic": 5.31,
  "P": 1150.5,
  "Q": 230.2,
  "S": 1173.8,
  "PF": 0.98,
  "F": 50.0,
  "createTime": "2026-04-30 10:00:00"
}
```

---

## 示例 3：发送电表所有数据（数组格式）

```bash
python main.py send -t electric/all -cid gateway_001 -tpl all_devices
```

**生成的消息** (数组格式):
```json
[
  {
    "clientId": "device_001",
    "deviceType": "electric/voltage",
    "value": 220.5,
    "createTime": "2026-04-30 10:00:00"
  },
  {
    "clientId": "device_001",
    "deviceType": "electric/current",
    "value": 5.2,
    "createTime": "2026-04-30 10:00:00"
  },
  {
    "clientId": "device_001",
    "deviceType": "electric/power",
    "value": 1150.5,
    "createTime": "2026-04-30 10:00:00"
  }
]
```

---

## 示例 4：发送水表数据（数组格式）

```bash
python main.py send -t water/consumption -cid water_001 -tpl water
```

**生成的消息**:
```json
[
  {
    "clientId": "water_001",
    "value": 125.5,
    "createTime": "2026-04-30 10:00:00"
  },
  {
    "clientId": "water_002",
    "value": 98.3,
    "createTime": "2026-04-30 10:00:00"
  }
]
```

---

## 示例 5：轻量级压力测试

```bash
python main.py stress -s light
```

**输出**:
```
开始压力测试 - 设备数：10, 频率：1.0Hz, 持续时间：60s
============================================================
发送中... 300 条，成功率：100.0% (5.0 msg/s)
============================================================
✓ 压力测试完成
总消息数：600
成功：598
失败：2
成功率：99.67%
平均吞吐量：10.0 msg/s
平均延迟：42.35ms
最大延迟：125.67ms
最小延迟：18.23ms
✓ 报告已保存：output/stress_test_20260430_100000.md
```

---

## 示例 6：自定义压力测试

```bash
python main.py stress -d 50 -f 2 -dur 300 -mt voltage -o my_test_report.md
```

**参数说明**:
- `-d 50`: 50 个设备
- `-f 2`: 每秒 2 条消息
- `-dur 300`: 持续 300 秒（5 分钟）
- `-mt voltage`: 发送电压数据
- `-o my_test_report.md`: 输出报告文件名

---

## 示例 7：批量发送不同类型的消息

创建脚本 `batch_send.py`:

```python
#!/usr/bin/env python3
import sys
sys.path.insert(0, 'src')

from mqtt_client import MqttClientWrapper
from config import ConfigLoader, TemplateLoader
from message_generator import MessageGenerator
from single_simulator import SingleMessageSimulator

# 加载配置
config_loader = ConfigLoader()
config = config_loader.load()

# 加载模板
template_loader = TemplateLoader()
templates = template_loader.load()

# 创建客户端
mqtt_client = MqttClientWrapper(config.get('mqtt', {}))
mqtt_client.connect()

# 创建模拟器
generator = MessageGenerator(templates)
simulator = SingleMessageSimulator(mqtt_client, generator)

# 批量发送
templates_to_send = ['voltage', 'current', 'power', 'consumption']

for tpl_name in templates_to_send:
    print(f"\n发送 {tpl_name} 数据...")
    result = simulator.send_with_template(
        template_name=tpl_name,
        client_id=f"device_{len(templates_to_send):03d}"
    )
    
    if result['success']:
        print(f"  ✓ 成功 - {result['topic']}")
    else:
        print(f"  ✗ 失败 - {result.get('error')}")

# 断开连接
mqtt_client.disconnect()
print("\n批量发送完成！")
```

运行:
```bash
python batch_send.py
```

---

## 示例 8：周期性发送测试

创建脚本 `periodic_send.py`:

```python
#!/usr/bin/env python3
import sys
import time
sys.path.insert(0, 'src')

from mqtt_client import MqttClientWrapper
from config import ConfigLoader, TemplateLoader
from message_generator import MessageGenerator
from single_simulator import SingleMessageSimulator

# 配置
INTERVAL = 5  # 每 5 秒发送一次
COUNT = 12    # 发送 12 次

# 初始化和连接
config_loader = ConfigLoader()
config = config_loader.load()

template_loader = TemplateLoader()
templates = template_loader.load()

mqtt_client = MqttClientWrapper(config.get('mqtt', {}))
mqtt_client.connect()

generator = MessageGenerator(templates)
simulator = SingleMessageSimulator(mqtt_client, generator)

print(f"开始周期性发送测试，每{INTERVAL}秒发送一次，共{COUNT}次...")

try:
    for i in range(COUNT):
        result = simulator.send_with_template(
            template_name='voltage',
            client_id=f"device_{i:03d}"
        )
        
        if result['success']:
            print(f"[{i+1}/{COUNT}] ✓ 发送成功")
        else:
            print(f"[{i+1}/{COUNT}] ✗ 发送失败：{result.get('error')}")
        
        time.sleep(INTERVAL)
    
    print("\n周期性发送测试完成！")
    
except KeyboardInterrupt:
    print("\n测试被中断")
finally:
    mqtt_client.disconnect()
```

运行:
```bash
python periodic_send.py
```

---

## 示例 9：验证消息格式

```bash
# 空运行模式（只验证，不发送）
python main.py send -t electric/voltage -cid device_001 -tpl voltage -n
```

**输出**:
```
ℹ 空运行模式 - 不实际发送消息
✓ 消息验证通过
主题：electric/voltage/device_001
数据：{"clientId": "device_001", "value": 220.5, "createTime": "2026-04-30 10:00:00"}
```

---

## 示例 10：查看和验证配置

```bash
# 验证配置文件
python main.py validate
```

**输出**:
```
验证配置...
============================================================
✓ MQTT 主机：tcp://192.168.1.100:1883
✓ MQTT 用户名：admin
✓ MQTT 密码：已配置
✓ 日志级别：INFO
✓ 日志文件：logs/mqtt_simulator.log
✓ 压力测试场景：3 个
  - light
  - medium
  - heavy
============================================================
✓ 配置验证通过
```

---

## 示例 11：查看支持的主题和模板

```bash
# 查看所有支持的主题
python main.py topics
```

**输出**:
```
支持的 MQTT 主题:
============================================================
主题                                  说明                       
------------------------------------------------------------
electric/emsCarson                    电表综合数据               
electric/all/{clientId}               电表所有数据（数组）        
electric/voltage/{clientId}           电压数据                   
electric/current/{clientId}           电流数据                   
electric/power/{clientId}             功率数据                   
electric/consumption/{clientId}       电能数据                   
water/consumption/{clientId}          水表数据（数组）           
============================================================
```

```bash
# 查看所有消息模板
python main.py templates
```

**输出**:
```
可用的消息模板:
============================================================
模板名称               说明                          
------------------------------------------------------------
voltage                电压数据模板                  
current                电流数据模板                  
power                  功率数据模板                  
consumption            电能数据模板                  
water                  水表数据模板                  
emsCarson              电表综合数据模板              
all_devices            电表所有数据模板（数组）        
============================================================
```

---

## 示例 12：生成不同格式的报告

```bash
# 生成 Markdown 格式报告
python main.py stress -s medium -o report.md --format md

# 生成 JSON 格式报告
python main.py stress -s medium -o report.json --format json

# 同时生成两种格式
python main.py stress -s medium -o report --format md,json
```

**报告文件**:
- `report.md` - Markdown 格式，适合人工阅读
- `report.json` - JSON 格式，适合程序处理

---

## 示例 13：使用不同的 QoS 级别

```bash
# QoS 0 (最多一次)
python main.py send -t electric/voltage -cid device_001 -tpl voltage -q 0

# QoS 1 (至少一次)
python main.py send -t electric/voltage -cid device_001 -tpl voltage -q 1

# QoS 2 (只有一次)
python main.py send -t electric/voltage -cid device_001 -tpl voltage -q 2
```

---

## 示例 14：保留消息

```bash
# 发送保留消息（新订阅者会立即收到）
python main.py send -t electric/voltage -cid device_001 -tpl voltage -r

# 清除保留消息（发送空消息并设置 retain）
python main.py send -t electric/voltage -cid device_001 -d '{}' -r
```

---

## 示例 15：测试不同场景

```bash
# 场景 1：链路打通测试
python main.py send -t electric/voltage -cid test_device -tpl voltage

# 场景 2：协议解析验证
python main.py send -t electric/emsCarson -tpl emsCarson

# 场景 3：性能基准测试
python main.py stress -s light

# 场景 4：压力测试
python main.py stress -s medium

# 场景 5：极限测试
python main.py stress -s heavy
```

---

## 故障排查示例

### 问题 1：连接失败

```bash
# 1. 检查配置
python main.py validate

# 2. 测试连接
python main.py ping

# 3. 查看详细日志
python main.py ping --log-level DEBUG
```

### 问题 2：消息发送失败

```bash
# 1. 验证数据格式（空运行）
python main.py send -t electric/voltage -cid device_001 -tpl voltage -n

# 2. 查看详细错误
python main.py send -t electric/voltage -cid device_001 -tpl voltage --log-level DEBUG
```

### 问题 3：压力测试性能低

```bash
# 1. 减小规模测试
python main.py stress -d 10 -f 1 -dur 60

# 2. 查看系统资源
# Windows: 任务管理器
# Linux: top 或 htop

# 3. 查看日志
cat logs/mqtt_simulator.log
```

---

## 最佳实践

### 1. 开发环境测试

```bash
# 使用轻量配置
python main.py stress -s light

# 或使用自定义小規模测试
python main.py stress -d 5 -f 0.5 -dur 30
```

### 2. 生产环境测试

```bash
# 先验证配置
python main.py validate

# 使用中等压力测试
python main.py stress -s medium

# 生成详细报告
python main.py stress -s medium -o production_test.md
```

### 3. 自动化测试

创建 CI/CD 脚本:

```bash
#!/bin/bash
# 验证配置
python main.py validate

# 发送测试消息
python main.py send -t electric/voltage -cid ci_test -tpl voltage

# 运行压力测试
python main.py stress -s light -o ci_report.md
```

---

**提示**: 更多示例请参考完整文档和使用说明。

**文档版本**: v1.0  
**更新日期**: 2026-04-30
