# MQTT 设备模拟工具 - 快速入门指南

## 5 分钟快速开始

本指南帮助您在 5 分钟内快速上手 MQTT 设备模拟工具。

---

## 一、安装（2 分钟）

### 步骤 1：确认 Python 环境

```bash
# 检查 Python 版本（需要 3.8+）
python --version
```

如果未安装 Python，请访问：https://www.python.org/downloads/

### 步骤 2：安装依赖

```bash
# 进入项目目录
cd D:\code\gitcp\inspur-ems\deep-ems0\simulator

# 安装依赖包
pip install -r requirements.txt
```

### 步骤 3：验证安装

```bash
# 显示帮助信息
python mqtt_simulator.py --help
```

---

## 二、配置（1 分钟）

编辑 `config/config.yaml` 文件：

```yaml
mqtt:
  host: "tcp://192.168.1.100:1883"  # 修改为您的 MQTT Broker 地址
  username: "admin"                  # 修改为用户名
  password: "public"                 # 修改为密码
```

**测试连接**：

```bash
python mqtt_simulator.py ping
```

看到 `✓ 连接成功` 表示配置正确。

---

## 三、发送第一条消息（1 分钟）

### 方式 1：使用模板（推荐新手）

```bash
# 发送电压数据
python mqtt_simulator.py send \
  --topic "electric/voltage" \
  --client-id "device_001" \
  --template "voltage"
```

### 方式 2：自定义数据

```bash
# 发送自定义电压数据
python mqtt_simulator.py send \
  --topic "electric/voltage" \
  --client-id "device_001" \
  --data '{"value": 220.5}'
```

**查看结果**：

```
✓ 消息发送成功！
消息 ID: msg_20260430_100000_001
主题：electric/voltage/device_001
耗时：45ms
```

---

## 四、运行压力测试（1 分钟）

### 轻量测试

```bash
# 10 个设备，每秒 1 条消息，持续 60 秒
python mqtt_simulator.py stress --scenario light
```

### 查看实时监控

测试运行时会显示实时数据：

```
运行时间：00:00:30 / 00:01:00
发送进度：████████████░░░░░░░░░░  50%
已发送：300 / 600
成功率：100%
平均延迟：42ms
```

### 查看测试报告

测试完成后，报告保存在 `output/` 目录。

---

## 五、常用命令速查

### 单条消息

```bash
# 电压数据
python mqtt_simulator.py send -t electric/voltage -cid device_001 --template voltage

# 电流数据
python mqtt_simulator.py send -t electric/current -cid device_002 --template current

# 功率数据
python mqtt_simulator.py send -t electric/power -cid device_003 --template power

# 电表综合数据
python mqtt_simulator.py send -t electric/emsCarson --template emsCarson
```

### 压力测试

```bash
# 轻量测试
python mqtt_simulator.py stress -s light

# 中等压力
python mqtt_simulator.py stress -s medium

# 自定义测试
python mqtt_simulator.py stress -d 50 -f 2 -dur 300
```

### 其他命令

```bash
# 查看帮助
python mqtt_simulator.py --help

# 查看支持的主题
python mqtt_simulator.py topics

# 查看消息模板
python mqtt_simulator.py templates

# 验证配置
python mqtt_simulator.py validate
```

---

## 六、交互式界面（可选）

```bash
# 启动交互式界面
python mqtt_simulator.py
```

按照菜单提示操作，适合不熟悉命令行的用户。

---

## 七、下一步

完成快速入门后，您可以：

1. **查看详细文档**：`MQTT 设备模拟工具 - 使用说明文档.md`
2. **学习模板使用**：第 5 章 - 消息模板
3. **了解配置管理**：第 6 章 - 配置管理
4. **查看故障排查**：第 8 章 - 故障排查

---

## 八、常见问题

### Q1: 连接失败怎么办？

**A**: 检查以下几点：
1. MQTT Broker 是否运行
2. 配置文件的地址、端口、用户名、密码是否正确
3. 网络是否连通：`telnet 192.168.1.100 1883`

### Q2: 如何停止压力测试？

**A**: 按 `Ctrl+C` 即可停止测试。

### Q3: 测试报告在哪里？

**A**: 报告保存在 `output/` 目录，文件名格式：`stress_test_YYYYMMDD_HHMMSS.md`

### Q4: 如何获取帮助？

**A**: 
- 命令行帮助：`python mqtt_simulator.py --help`
- 查看文档：`MQTT 设备模拟工具 - 使用说明文档.md`
- 查看日志：`logs/mqtt_simulator.log`

---

## 九、获取更多信息

- **完整文档**：`MQTT 设备模拟工具 - 使用说明文档.md`
- **UI 设计**：`MQTT 设备模拟工具 - UI 设计文档.md`
- **设计方案**：`MQTT 设备模拟工具设计方案.md`
- **项目目录**：`D:\code\gitcp\inspur-ems\deep-ems0\simulator`

---

**提示**：这是快速入门指南，仅包含最常用的功能。详细功能请参考完整文档。

**文档版本**: v1.0  
**更新时间**: 2026-04-30  
**项目名称**: 祝融能源管理系统 MQTT 设备模拟工具
