# MQTT 设备模拟工具

祝融能源管理系统（Deep-EMS）的 MQTT 设备模拟测试工具。

## 功能特性

- ✅ **单条消息模拟** - 发送单条 MQTT 消息，用于链路打通和协议解析测试
- ✅ **并发压力测试** - 多设备并发发送消息，用于系统性能测试
- ✅ **消息模板** - 预定义消息模板，快速生成符合后端格式的消息
- ✅ **实时监控** - 实时显示消息发送状态和性能指标
- ✅ **测试报告** - 自动生成详细的测试报告（Markdown/JSON 格式）

## 快速开始

### 1. 环境要求

- Python 3.8+
- Windows 10/11, Linux, macOS
- 可访问 MQTT Broker（EMQX）

### 2. 安装依赖

```bash
pip install -r requirements.txt
```

### 3. 配置 MQTT 连接

编辑 `config/config.yaml` 文件：

```yaml
mqtt:
  host: "tcp://192.168.1.100:1883"  # MQTT Broker 地址
  username: "admin"                  # 用户名
  password: "public"                 # 密码
```

### 4. 测试连接

```bash
python main.py ping
```

### 5. 发送第一条消息

```bash
# 使用模板发送电压数据
python main.py send -t electric/voltage -cid device_001 -tpl voltage

# 或自定义数据
python main.py send -t electric/voltage -cid device_001 -d '{"value": 220.5}'
```

### 6. 运行压力测试

```bash
# 轻量测试
python main.py stress -s light

# 自定义测试
python main.py stress -d 50 -f 2 -dur 300
```

## 使用帮助

### 显示帮助信息

```bash
python main.py --help
```

### 查看支持的主题

```bash
python main.py topics
```

### 查看消息模板

```bash
python main.py templates
```

### 验证配置

```bash
python main.py validate
```

## 支持的 MQTT 主题

| 主题 | 说明 | 数据格式 |
|------|------|----------|
| `electric/emsCarson` | 电表综合数据 | JSON 对象 |
| `electric/all/{clientId}` | 电表所有数据（数组） | JSON 数组 |
| `electric/voltage/{clientId}` | 电压数据 | JSON 对象 |
| `electric/current/{clientId}` | 电流数据 | JSON 对象 |
| `electric/power/{clientId}` | 功率数据 | JSON 对象 |
| `electric/consumption/{clientId}` | 电能数据 | JSON 对象 |
| `water/consumption/{clientId}` | 水表数据（数组） | JSON 数组 |

## 压力测试场景

| 场景 | 设备数 | 频率 | 持续时间 | 说明 |
|------|--------|------|----------|------|
| light | 10 | 1 msg/s | 60s | 轻量测试 |
| medium | 100 | 5 msg/s | 300s | 中等压力 |
| heavy | 1000 | 10 msg/s | 600s | 重度压力 |

## 项目结构

```
simulator/
├── main.py                 # 主入口和命令行接口
├── requirements.txt        # Python 依赖
├── run.bat                 # Windows 快速启动脚本
├── README.md               # 使用说明
├── config/
│   ├── config.yaml         # 主配置文件
│   └── templates.yaml      # 消息模板配置
├── src/
│   ├── mqtt_client.py      # MQTT 客户端封装
│   ├── config.py           # 配置加载模块
│   ├── message_generator.py # 消息生成器
│   ├── single_simulator.py # 单消息模拟器
│   ├── stress_tester.py    # 压力测试器
│   └── reporters.py        # 报告生成器
├── logs/                   # 日志目录
└── output/                 # 测试报告输出目录
```

## 常用命令

### 发送消息

```bash
# 发送电压数据
python main.py send -t electric/voltage -cid device_001 -tpl voltage

# 发送电流数据
python main.py send -t electric/current -cid device_002 -tpl current

# 发送功率数据
python main.py send -t electric/power -cid device_003 -tpl power

# 发送电表综合数据
python main.py send -t electric/emsCarson -tpl emsCarson

# 空运行（验证数据但不发送）
python main.py send -t electric/voltage -cid device_001 -tpl voltage -n
```

### 压力测试

```bash
# 轻量测试
python main.py stress -s light

# 中等压力
python main.py stress -s medium

# 重度压力
python main.py stress -s heavy

# 自定义测试
python main.py stress -d 50 -f 2 -dur 300 -mt voltage

# 生成 JSON 格式报告
python main.py stress -s medium -o report.json --format json
```

## 配置说明

### MQTT 配置

```yaml
mqtt:
  host: "tcp://192.168.1.100:1883"    # Broker 地址
  username: "admin"                    # 用户名
  password: "public"                   # 密码
  client_id: "mqtt-simulator-001"      # 客户端 ID
  timeout: 120                         # 连接超时（秒）
  keepalive: 360                       # 心跳间隔（秒）
  qos: 0                               # QoS 级别
  retain: false                        # 是否保留消息
```

### 日志配置

```yaml
logging:
  level: "INFO"                        # 日志级别
  file: "logs/mqtt_simulator.log"      # 日志文件
  format: "%(asctime)s - %(levelname)s - %(message)s"
```

## 测试报告

测试完成后，报告会自动保存到 `output/` 目录：

- **Markdown 格式** (.md) - 可读性强，适合查看
- **JSON 格式** (.json) - 结构化数据，适合程序处理

## 故障排查

### 连接失败

```
✗ 错误：MQTT 连接失败
```

**解决方法**：
1. 检查 MQTT Broker 是否运行
2. 验证配置文件的地址、端口、用户名、密码
3. 测试网络连通性：`telnet 192.168.1.100 1883`

### 消息发送失败

```
✗ 错误：消息发送失败
```

**解决方法**：
1. 检查 MQTT 连接状态：`python main.py ping`
2. 检查数据格式是否正确
3. 查看详细日志：`logs/mqtt_simulator.log`

## 文档

- **设计方案**: `../MQTT 参考文档/MQTT 设备模拟工具设计方案.md`
- **UI 设计**: `../MQTT 参考文档/MQTT 设备模拟工具 - UI 设计文档.md`
- **使用说明**: `../MQTT 参考文档/MQTT 设备模拟工具 - 使用说明文档.md`
- **快速入门**: `../MQTT 参考文档/MQTT 设备模拟工具 - 快速入门指南.md`

## 版本信息

- **版本**: v1.0
- **创建日期**: 2026-04-30
- **项目**: 祝融能源管理系统（Deep-EMS）

## 许可证

本项目为祝融能源管理系统内部工具。

---

**祝融能源管理系统 MQTT 设备模拟工具**
