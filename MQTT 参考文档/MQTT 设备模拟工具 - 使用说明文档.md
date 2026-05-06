# MQTT 设备模拟工具 - 使用说明文档

## 一、工具简介

MQTT 设备模拟工具是专为祝融能源管理系统（Deep-EMS）开发的测试工具，用于模拟 IoT 设备向 MQTT Broker 发送消息，支持单条消息模拟和并发压力测试两种模式。

### 1.1 主要功能

- ✅ **单条消息模拟** - 发送单条 MQTT 消息，用于链路打通和协议解析测试
- ✅ **并发压力测试** - 多设备并发发送消息，用于系统性能测试
- ✅ **消息模板** - 预定义消息模板，快速生成符合后端格式的消息
- ✅ **实时监控** - 实时显示消息发送状态和性能指标
- ✅ **测试报告** - 自动生成详细的测试报告

### 1.2 适用场景

| 场景 | 描述 | 推荐模式 |
|------|------|----------|
| 开发测试 | 验证 MQTT 消息链路是否打通 | 单条消息 |
| 集成测试 | 验证后端消息解析逻辑 | 单条消息 + 模板 |
| 性能测试 | 测试系统并发处理能力 | 压力测试 |
| 稳定性测试 | 长时间运行测试系统稳定性 | 压力测试（长时） |
| 演示展示 | 向客户展示系统功能 | 单条消息 + 监控 |

---

## 二、快速开始

### 2.1 环境要求

- **操作系统**：Windows 10/11, Linux, macOS
- **Python 版本**：Python 3.8+
- **网络连接**：可访问 MQTT Broker（EMQX）

### 2.2 安装步骤

**步骤 1：安装 Python**

下载安装 Python 3.8+：https://www.python.org/downloads/

**步骤 2：克隆或下载工具**

```bash
# 进入项目目录
cd D:\code\gitcp\inspur-ems\deep-ems0\simulator
```

**步骤 3：安装依赖**

```bash
# 安装 Python 依赖包
pip install -r requirements.txt
```

**步骤 4：验证安装**

```bash
# 显示帮助信息
python mqtt_simulator.py --help
```

### 2.3 配置文件

编辑 `config/config.yaml` 文件，配置 MQTT 连接信息：

```yaml
mqtt:
  host: "tcp://192.168.1.100:1883"  # MQTT Broker 地址
  username: "admin"                  # 用户名
  password: "public"                 # 密码
  client_id: "mqtt-simulator-001"    # 客户端 ID
  timeout: 120                       # 连接超时（秒）
  keepalive: 360                     # 心跳间隔（秒）
  qos: 0                             # 默认 QoS 级别
  retain: false                      # 是否保留消息
```

**测试连接**：

```bash
# 测试 MQTT 连接
python mqtt_simulator.py ping
```

---

## 三、单条消息模拟

### 3.1 交互式模式

**启动交互式界面**：

```bash
python mqtt_simulator.py
```

按照菜单提示操作：

```
╔══════════════════════════════════════════════════════════╗
║       MQTT 设备模拟工具 v1.0 - 祝融能源管理系统          ║
╠══════════════════════════════════════════════════════════╣
║  请选择操作模式：                                         ║
║                                                          ║
║  [1] 单条消息模拟                                         ║
║  [2] 并发压力测试                                         ║
║  [3] 查看支持的主题                                       ║
║  [0] 退出                                                 ║
╚══════════════════════════════════════════════════════════╝
请输入选项 [0-3]: 1
```

### 3.2 命令行模式

#### 3.2.1 发送电压数据

```bash
# 方式 1：直接指定 JSON 数据
python mqtt_simulator.py send \
  --topic "electric/voltage" \
  --client-id "device_001" \
  --data '{"value": 220.5}'

# 方式 2：使用模板
python mqtt_simulator.py send \
  --topic "electric/voltage" \
  --client-id "device_001" \
  --template "voltage"
```

#### 3.2.2 发送电表综合数据

```bash
python mqtt_simulator.py send \
  --topic "electric/emsCarson" \
  --data '{
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
    "F": 50.0
  }'
```

#### 3.2.3 发送电表所有数据（数组格式）

```bash
python mqtt_simulator.py send \
  --topic "electric/all" \
  --client-id "gateway_001" \
  --data '[
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
    }
  ]'
```

#### 3.2.4 发送水表数据（数组格式）

```bash
python mqtt_simulator.py send \
  --topic "water/consumption" \
  --client-id "water_meter_001" \
  --data '[
    {
      "clientId": "water_meter_001",
      "value": 125.5
    },
    {
      "clientId": "water_meter_002",
      "value": 98.3
    }
  ]'
```

### 3.3 使用模板

工具预定义了常用消息模板：

```bash
# 查看所有模板
python mqtt_simulator.py templates

# 使用电压模板
python mqtt_simulator.py send \
  --topic "electric/voltage" \
  --client-id "device_001" \
  --template "voltage"

# 使用电流模板
python mqtt_simulator.py send \
  --topic "electric/current" \
  --client-id "device_002" \
  --template "current"

# 使用功率模板
python mqtt_simulator.py send \
  --topic "electric/power" \
  --client-id "device_003" \
  --template "power"
```

### 3.4 高级选项

```bash
# 设置 QoS 级别（0, 1, 2）
python mqtt_simulator.py send \
  --topic "electric/voltage" \
  --client-id "device_001" \
  --data '{"value": 220.5}' \
  --qos 1

# 启用消息保留
python mqtt_simulator.py send \
  --topic "electric/voltage" \
  --client-id "device_001" \
  --data '{"value": 220.5}' \
  --retain

# 空运行（验证数据但不发送）
python mqtt_simulator.py send \
  --topic "electric/voltage" \
  --client-id "device_001" \
  --data '{"value": 220.5}' \
  --dry-run

# 详细日志输出
python mqtt_simulator.py send \
  --topic "electric/voltage" \
  --client-id "device_001" \
  --data '{"value": 220.5}' \
  --log-level DEBUG
```

---

## 四、并发压力测试

### 4.1 使用预设场景

```bash
# 轻量测试：10 个设备，1 消息/秒，持续 60 秒
python mqtt_simulator.py stress --scenario light

# 中等压力：100 个设备，5 消息/秒，持续 300 秒
python mqtt_simulator.py stress --scenario medium

# 重度压力：1000 个设备，10 消息/秒，持续 600 秒
python mqtt_simulator.py stress --scenario heavy
```

### 4.2 自定义测试配置

```bash
# 自定义：50 个设备，每秒 2 条消息，持续 5 分钟
python mqtt_simulator.py stress \
  --devices 50 \
  --frequency 2 \
  --duration 300 \
  --message-type voltage

# 混合消息类型（随机发送不同类型的消息）
python mqtt_simulator.py stress \
  --devices 100 \
  --frequency 5 \
  --duration 600 \
  --message-type mixed
```

### 4.3 实时监控

压力测试运行时会显示实时监控界面：

```
╔══════════════════════════════════════════════════════════╗
║  压力测试进行中...                                        ║
╠══════════════════════════════════════════════════════════╣
║  运行时间：00:02:35 / 00:05:00                           ║
║                                                          ║
║  发送统计：                                               ║
║  ████████████████████░░░░░░░░  52%                       ║
║  已发送：78,000 / 150,000                                ║
║  成功：77,856  失败：144  成功率：99.82%                 ║
║                                                          ║
║  性能指标：                                               ║
║  平均延迟：45ms    最大延迟：230ms   最小延迟：12ms      ║
║  当前吞吐量：502 消息/秒                                  ║
║                                                          ║
║  活跃连接：100 / 100                                      ║
║  内存使用：125.6 MB                                       ║
╠══════════════════════════════════════════════════════════╣
║  [Ctrl+C] 停止测试                                        ║
╚══════════════════════════════════════════════════════════╝
```

### 4.4 停止测试

- **正常停止**：等待测试完成自动停止
- **手动停止**：按 `Ctrl+C` 立即停止测试

### 4.5 测试报告

测试完成后自动生成报告：

```bash
# 指定报告输出文件
python mqtt_simulator.py stress \
  --scenario medium \
  --output "reports/stress_test_20260430.md"

# 同时生成 Markdown 和 JSON 格式
python mqtt_simulator.py stress \
  --scenario medium \
  --output "reports/stress_test" \
  --format md json
```

---

## 五、消息模板

### 5.1 查看模板

```bash
# 查看所有可用模板
python mqtt_simulator.py templates

# 查看特定模板详情
python mqtt_simulator.py templates --name voltage
```

### 5.2 预定义模板

#### 电压数据模板（voltage）

```json
{
  "clientId": "device_{id}",
  "value": "random(215.0, 225.0, 2)",
  "createTime": "auto"
}
```

#### 电流数据模板（current）

```json
{
  "clientId": "device_{id}",
  "value": "random(0.0, 10.0, 2)",
  "createTime": "auto"
}
```

#### 功率数据模板（power）

```json
{
  "clientId": "device_{id}",
  "value": "random(100.0, 5000.0, 2)",
  "createTime": "auto"
}
```

#### 电能数据模板（consumption）

```json
{
  "clientId": "device_{id}",
  "value": "random(0.0, 1000.0, 2)",
  "createTime": "auto"
}
```

#### 电表综合数据模板（emsCarson）

```json
{
  "clientId": "emsCarson_{id}",
  "Ua": "random(215.0, 225.0, 1)",
  "Ub": "random(215.0, 225.0, 1)",
  "Uc": "random(215.0, 225.0, 1)",
  "Ia": "random(0.0, 10.0, 2)",
  "Ib": "random(0.0, 10.0, 2)",
  "Ic": "random(0.0, 10.0, 2)",
  "P": "random(100.0, 5000.0, 2)",
  "Q": "random(50.0, 500.0, 2)",
  "S": "random(150.0, 5500.0, 2)",
  "PF": "random(0.85, 0.99, 2)",
  "F": "random(49.5, 50.5, 2)"
}
```

### 5.3 自定义模板

在 `config/templates.yaml` 中添加自定义模板：

```yaml
templates:
  my_custom_template:
    topic: "electric/voltage"
    description: "我的自定义电压模板"
    data_pattern:
      clientId: "my_device_{id}"
      value: "random(220.0, 230.0, 2)"
      unit: "V"
      createTime: "auto"
```

使用自定义模板：

```bash
python mqtt_simulator.py send \
  --template "my_custom_template"
```

---

## 六、配置管理

### 6.1 配置文件位置

```
simulator/
├── config/
│   ├── config.yaml        # 主配置文件
│   ├── templates.yaml     # 消息模板配置
│   └── scenarios.yaml     # 压力测试场景配置
```

### 6.2 MQTT 连接配置

```yaml
mqtt:
  host: "tcp://192.168.1.100:1883"
  username: "admin"
  password: "public"
  client_id: "mqtt-simulator-${HOSTNAME}"  # 支持环境变量
  timeout: 120
  keepalive: 360
  qos: 0
  retain: false
```

### 6.3 日志配置

```yaml
logging:
  level: "INFO"  # DEBUG, INFO, WARNING, ERROR
  file: "logs/mqtt_simulator.log"
  format: "%(asctime)s - %(levelname)s - %(message)s"
  max_size: "10MB"  # 单个日志文件最大大小
  backup_count: 5   # 保留的日志文件数量
```

### 6.4 压力测试场景配置

```yaml
stress_test:
  scenarios:
    light:
      devices: 10
      frequency: 1
      duration: 60
      message_type: voltage
    medium:
      devices: 100
      frequency: 5
      duration: 300
      message_type: voltage
    heavy:
      devices: 1000
      frequency: 10
      duration: 600
      message_type: mixed
```

---

## 七、测试报告

### 7.1 报告格式

工具支持生成以下格式的测试报告：

- **Markdown** (.md) - 可读性强，适合查看和分享
- **JSON** (.json) - 结构化数据，适合程序处理
- **HTML** (.html) - 可视化报告，适合演示（可选）

### 7.2 报告内容

#### 单次发送报告

```markdown
# MQTT 消息发送报告

## 发送信息
- 消息 ID: msg_20260430_100000_001
- 发送时间：2026-04-30 10:00:00.123
- 主题：electric/voltage/device_001
- QoS: 0
- 保留：false

## 消息内容
```json
{
  "clientId": "device_001",
  "value": 220.5,
  "createTime": "2026-04-30 10:00:00"
}
```

## 发送结果
- 状态：✓ 成功
- 耗时：45ms
- 消息大小：56 bytes
```

#### 压力测试报告

```markdown
# MQTT 压力测试报告

## 测试概览
- 测试 ID: stress_20260430_100000
- 测试时间：2026-04-30 10:00:00 - 10:05:00
- 测试场景：medium

## 测试配置
| 配置项 | 值 |
|--------|-----|
| 设备数量 | 100 |
| 发送频率 | 5 消息/秒 |
| 测试持续时间 | 300 秒 |
| 消息类型 | voltage |

## 测试结果
| 指标 | 值 |
|------|-----|
| 总消息数 | 150,000 |
| 成功发送 | 149,856 |
| 失败消息 | 144 |
| 成功率 | 99.904% |

## 性能指标
| 指标 | 值 |
|------|-----|
| 平均延迟 | 45ms |
| 最大延迟 | 230ms |
| 最小延迟 | 12ms |
| 平均吞吐量 | 500 消息/秒 |
| 峰值吞吐量 | 520 消息/秒 |
```

### 7.3 导出报告

```bash
# 导出为 Markdown
python mqtt_simulator.py stress \
  --scenario medium \
  --output "report.md"

# 导出为 JSON
python mqtt_simulator.py stress \
  --scenario medium \
  --output "report.json"

# 导出多种格式
python mqtt_simulator.py stress \
  --scenario medium \
  --output "report" \
  --format md json
```

---

## 八、故障排查

### 8.1 常见问题

#### 问题 1：连接失败

```
✗ 错误：MQTT 连接失败
错误代码：CONN_REFUSED
```

**原因**：
- MQTT Broker 未启动
- 主机地址或端口错误
- 用户名或密码错误
- 防火墙阻止连接

**解决方法**：
1. 检查 MQTT Broker 是否运行：`docker ps | grep emqx`
2. 验证配置：`python mqtt_simulator.py validate`
3. 测试网络连接：`telnet 192.168.1.100 1883`
4. 检查防火墙设置

#### 问题 2：消息发送失败

```
✗ 错误：消息发送失败
错误信息：Broker not connected
```

**原因**：
- MQTT 连接已断开
- 网络不稳定

**解决方法**：
1. 重新连接：`python mqtt_simulator.py ping`
2. 检查网络稳定性
3. 增加超时时间配置

#### 问题 3：数据格式错误

```
✗ 错误：数据格式验证失败
错误信息：缺少必需字段 'clientId'
```

**原因**：
- JSON 数据格式不正确
- 缺少必需字段

**解决方法**：
1. 使用模板：`--template voltage`
2. 验证 JSON 格式：https://jsonlint.com/
3. 查看必需字段说明

#### 问题 4：压力测试性能低

```
警告：吞吐量低于预期
预期：500 消息/秒，实际：200 消息/秒
```

**原因**：
- 系统资源不足
- 网络带宽限制
- MQTT Broker 性能瓶颈

**解决方法**：
1. 减少设备数量或频率
2. 检查系统资源使用：任务管理器
3. 优化 MQTT Broker 配置
4. 检查网络带宽

### 8.2 日志分析

查看日志文件：

```bash
# Windows
notepad logs\mqtt_simulator.log

# Linux
tail -f logs/mqtt_simulator.log
```

日志级别说明：

| 级别 | 说明 | 使用场景 |
|------|------|----------|
| DEBUG | 调试信息 | 开发调试 |
| INFO | 一般信息 | 正常使用 |
| WARNING | 警告信息 | 需要注意 |
| ERROR | 错误信息 | 需要处理 |

---

## 九、最佳实践

### 9.1 开发测试

**场景**：验证 MQTT 消息链路

```bash
# 1. 发送单条消息测试链路
python mqtt_simulator.py send \
  --topic "electric/voltage" \
  --client-id "test_device" \
  --data '{"value": 220.5}'

# 2. 查看后端日志确认接收
# 3. 检查数据库是否存储
```

### 9.2 集成测试

**场景**：验证后端消息解析

```bash
# 使用模板发送各种类型的消息
for topic in voltage current power consumption; do
  python mqtt_simulator.py send \
    --topic "electric/$topic" \
    --client-id "test_001" \
    --template "$topic"
done
```

### 9.3 性能测试

**场景**：测试系统并发能力

```bash
# 逐步增加压力
python mqtt_simulator.py stress --scenario light    # 10 设备
python mqtt_simulator.py stress --scenario medium   # 100 设备
python mqtt_simulator.py stress --scenario heavy    # 1000 设备

# 分析性能瓶颈
# 查看生成的报告
```

### 9.4 稳定性测试

**场景**：长时间运行测试

```bash
# 运行 1 小时
python mqtt_simulator.py stress \
  --devices 50 \
  --frequency 2 \
  --duration 3600 \
  --output "stability_test.md"
```

---

## 十、附录

### 10.1 支持的主题列表

| 主题 | 说明 | 数据格式 |
|------|------|----------|
| `electric/emsCarson` | 电表综合数据 | JSON 对象 |
| `electric/all/{clientId}` | 电表所有数据（数组） | JSON 数组 |
| `electric/voltage/{clientId}` | 电压数据 | JSON 对象 |
| `electric/current/{clientId}` | 电流数据 | JSON 对象 |
| `electric/power/{clientId}` | 功率数据 | JSON 对象 |
| `electric/consumption/{clientId}` | 电能数据 | JSON 对象 |
| `water/consumption/{clientId}` | 水表数据（数组） | JSON 数组 |

### 10.2 命令行参数速查

```bash
# 全局参数
--config, -c      配置文件路径
--log-level       日志级别
--help, -h        帮助信息
--version, -v     版本号

# send 命令
--topic, -t       MQTT 主题
--client-id, -cid 设备 ID
--data, -d        JSON 数据
--template, -tpl  使用模板
--qos, -q         QoS 级别
--retain, -r      保留消息
--dry-run, -n     空运行

# stress 命令
--scenario, -s    测试场景
--devices, -d     设备数量
--frequency, -f   发送频率
--duration, -dur  持续时间
--message-type, -mt 消息类型
--output, -o      输出文件
--realtime, -rt   实时监控
```

### 10.3 快捷键

| 快捷键 | 功能 |
|--------|------|
| `Ctrl+C` | 停止测试/退出 |
| `Ctrl+Q` | 快速退出 |
| `H` | 显示帮助 |
| `B` | 返回上一级 |

### 10.4 资源链接

- **项目目录**：`D:\code\gitcp\inspur-ems\deep-ems0\simulator`
- **配置目录**：`D:\code\gitcp\inspur-ems\deep-ems0\simulator\config`
- **日志目录**：`D:\code\gitcp\inspur-ems\deep-ems0\simulator\logs`
- **报告目录**：`D:\code\gitcp\inspur-ems\deep-ems0\simulator\output`

---

**文档版本**: v1.0  
**更新时间**: 2026-04-30  
**维护人员**: AI 代码助手  
**项目名称**: 祝融能源管理系统 MQTT 设备模拟工具
