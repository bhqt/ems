# Deep-EMS WSL 部署容器清单

> 部署时间: 2026-04-28  
> 配置文件: `docker-compose.full.yml`  
> 部署环境: WSL (Windows Subsystem for Linux)

---

## 一、容器服务概览

| 序号 | 服务名称 | 镜像版本 | 容器名称 | 状态 | 用途 |
|:---:|:---|:---|:---|:---:|:---|
| 1 | MySQL | mysql:8.0 | zhurong-ems-mysql | ✅ | 关系型数据库 |
| 2 | Redis | redis:7 | zhurong-ems-redis | ✅ | 缓存服务 |
| 3 | RabbitMQ | rabbitmq:3.12-management-alpine | zhurong-ems-rabbitmq | ✅ | 消息队列 |
| 4 | EMQX | emqx/emqx:5.3.2 | zhurong-ems-emqx | ✅ | MQTT Broker |
| 5 | TDengine | tdengine/tdengine:3.2.0.0 | zhurong-ems-tdengine | ⚠️ | 时序数据库(已禁用) |
| 6 | 后端服务 | zhurong-ems/backend:latest | zhurong-ems-backend | ✅ | Spring Boot应用 |
| 7 | 前端服务 | zhurong-ems/frontend:latest | zhurong-ems-frontend | ✅ | Vue前端应用 |

---

## 二、详细配置信息

### 1. MySQL 数据库

| 配置项 | 值 |
|:---|:---|
| **镜像** | mysql:8.0 |
| **容器名称** | zhurong-ems-mysql |
| **主机端口** | 3306 |
| **容器端口** | 3306 |
| **用户名** | root |
| **密码** | root |
| **默认数据库** | autoee_ems |
| **时区** | Asia/Shanghai |
| **字符集** | utf8mb4 |
| **身份验证插件** | mysql_native_password |

**连接信息:**
```
Host: localhost
Port: 3306
Database: autoee_ems
Username: root
Password: root
JDBC URL: jdbc:mysql://localhost:3306/autoee_ems?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true
```

---

### 2. Redis 缓存

| 配置项 | 值 |
|:---|:---|
| **镜像** | redis:7 |
| **容器名称** | zhurong-ems-redis |
| **主机端口** | 6379 |
| **容器端口** | 6379 |
| **密码** | difyai123456 |
| **数据库编号** | 13 (应用使用) |
| **持久化** | AOF (appendonly yes) |

**连接信息:**
```
Host: localhost
Port: 6379
Password: difyai123456
CLI命令: redis-cli -h localhost -p 6379 -a difyai123456
```

---

### 3. RabbitMQ 消息队列

| 配置项 | 值 |
|:---|:---|
| **镜像** | rabbitmq:3.12-management-alpine |
| **容器名称** | zhurong-ems-rabbitmq |
| **AMQP端口** | 5672 |
| **管理界面端口** | 15672 |
| **用户名** | guest |
| **密码** | guest |
| **时区** | Asia/Shanghai |

**访问地址:**
- AMQP连接: `amqp://guest:guest@localhost:5672`
- 管理界面: http://localhost:15672
- 登录账号: guest / guest

---

### 4. EMQX MQTT Broker

| 配置项 | 值 |
|:---|:---|
| **镜像** | emqx/emqx:5.3.2 |
| **容器名称** | zhurong-ems-emqx |
| **MQTT端口** | 1883 |
| **MQTT/SSL端口** | 8883 |
| **WebSocket端口** | 8081 |
| **WebSocket/SSL端口** | 8083 |
| **Dashboard端口** | 18083 |
| **Dashboard用户名** | admin |
| **Dashboard密码** | public |
| **MQTT用户名** | admin |
| **MQTT密码** | public |

**访问地址:**
- Dashboard: http://localhost:18083
- Dashboard登录: admin / public
- MQTT连接: tcp://localhost:1883
- MQTT over TLS: ssl://localhost:8883

**MQTT配置参数:**
```
Host: tcp://zhurong-ems-emqx:1883
Port: 1883
Username: admin
Password: public
Client ID: zhurong-ems-server-${HOSTNAME}
Timeout: 120s
Keepalive: 360s
Max Packet Size: 1MB
Max Client ID Length: 128
```

---

### 5. TDengine 时序数据库 (已禁用)

| 配置项 | 值 |
|:---|:---|
| **镜像** | tdengine/tdengine:3.2.0.0 |
| **容器名称** | zhurong-ems-tdengine |
| **原生客户端端口** | 6030 |
| **RESTful API端口** | 6041 |
| **用户名** | root |
| **密码** | difyai123456 |
| **FQDN** | zhurong-ems-tdengine |
| **状态** | ⚠️ 未启用 |

> **注意**: TDengine 已在后端配置中禁用，不会连接到此服务。

---

### 6. 后端服务 (Spring Boot)

| 配置项 | 值 |
|:---|:---|
| **镜像** | zhurong-ems/backend:latest |
| **容器名称** | zhurong-ems-backend |
| **主机端口** | 1088 |
| **容器端口** | 8088 |
| **Java版本** | Java 17 |
| **Spring Boot版本** | 2.7.9 |
| **应用版本** | 4.6.0 |
| **内存配置** | -Xms512m -Xmx1024m |
| **配置文件** | application.yml (外部挂载) |
| **健康检查** | http://localhost:8088/autoee-iot-ems/actuator/health |

**访问地址:**
- API Base URL: http://localhost:1088/autoee-iot-ems
- 健康检查: http://localhost:1088/autoee-iot-ems/actuator/health

**数据源配置:**
```yaml
主数据源 (MySQL):
  URL: jdbc:mysql://zhurong-ems-mysql:3306/autoee_ems
  Username: root
  Password: root
  
Redis:
  Host: zhurong-ems-redis
  Port: 6379
  Password: difyai123456
  Database: 13
  
RabbitMQ:
  Host: zhurong-ems-rabbitmq
  Port: 5672
  Username: guest
  Password: guest
  
MQTT:
  Host: tcp://zhurong-ems-emqx:1883
  Username: admin
  Password: public
```

---

### 7. 前端服务 (Vue.js)

| 配置项 | 值 |
|:---|:---|
| **镜像** | zhurong-ems/frontend:latest |
| **容器名称** | zhurong-ems-frontend |
| **主机端口** | 3080 |
| **容器端口** | 80 (Nginx) |
| **Web服务器** | Nginx |
| **API基础URL** | http://localhost:1088/autoee-iot-ems |
| **时区** | Asia/Shanghai |

**访问地址:**
- 前端页面: http://localhost:3080
- 登录接口: http://localhost:1088/autoee-iot-ems/login

---

## 三、网络配置

### Docker 网络

| 配置项 | 值 |
|:---|:---|
| **网络名称** | zhurong-ems-network |
| **驱动类型** | bridge |
| **网络模式** | 桥接模式 |

### 服务间通信地址

在容器内部，各服务通过以下地址相互访问：

| 源服务 | 目标服务 | 连接地址 |
|:---|:---|:---|
| Backend | MySQL | zhurong-ems-mysql:3306 |
| Backend | Redis | zhurong-ems-redis:6379 |
| Backend | RabbitMQ | zhurong-ems-rabbitmq:5672 |
| Backend | EMQX | zhurong-ems-emqx:1883 |
| Frontend | Backend | zhurong-ems-backend:8088 |

---

## 四、数据卷配置

| 卷名称 | 用途 | 挂载路径 |
|:---|:---|:---|
| zhurong-ems-mysql-data | MySQL数据持久化 | /var/lib/mysql |
| zhurong-ems-redis-data | Redis数据持久化 | /data |
| zhurong-ems-rabbitmq-data | RabbitMQ数据持久化 | /var/lib/rabbitmq |
| zhurong-ems-emqx-data | EMQX数据持久化 | /opt/emqx/data |
| zhurong-ems-emqx-log | EMQX日志 | /opt/emqx/log |
| zhurong-ems-emqx-plugins | EMQX插件 | /opt/emqx/plugins |
| zhurong-ems-tdengine-data | TDengine数据持久化 | /var/lib/taos |
| zhurong-ems-tdengine-log | TDengine日志 | /var/log/taos |
| zhurong-ems-backend-logs | 后端日志 | /zhurong-ems/server/logs |
| zhurong-ems-backend-temp | 后端临时文件 | /zhurong-ems/server/temp |
| zhurong-ems-frontend-logs | 前端Nginx日志 | /var/log/nginx |

---

## 五、快速访问指南

### 5.1 Web界面访问

| 服务 | 访问地址 | 用户名 | 密码 | 说明 |
|:---|:---|:---|:---|:---|
| **前端应用** | http://localhost:3080 | - | - | 主应用界面 |
| **后端API** | http://localhost:1088/autoee-iot-ems | - | - | REST API接口 |
| **RabbitMQ管理** | http://localhost:15672 | guest | guest | 消息队列管理 |
| **EMQX Dashboard** | http://localhost:18083 | admin | public | MQTT Broker管理 |

### 5.2 数据库连接

| 数据库 | 连接命令/工具 | 地址 | 端口 | 用户名 | 密码 |
|:---|:---|:---|:---:|:---|:---|
| **MySQL** | mysql -h localhost -P 3306 -u root -p | localhost | 3306 | root | root |
| **Redis** | redis-cli -h localhost -p 6379 -a difyai123456 | localhost | 6379 | - | difyai123456 |

### 5.3 MQTT连接测试

```bash
# 订阅主题
mosquitto_sub -h localhost -p 1883 -u admin -P public -t "test/topic"

# 发布消息
mosquitto_pub -h localhost -p 1883 -u admin -P public -t "test/topic" -m "Hello EMQX"
```

---

## 六、常用操作命令

### 6.1 启动/停止服务

```bash
# 进入项目目录
cd /mnt/d/code/gitcp/inspur-ems/deep-ems0

# 启动所有服务
docker-compose -f docker-compose.full.yml up -d

# 停止所有服务
docker-compose -f docker-compose.full.yml down

# 停止并删除数据卷（彻底重置）
docker-compose -f docker-compose.full.yml down -v

# 查看服务状态
docker-compose -f docker-compose.full.yml ps

# 查看服务日志
docker-compose -f docker-compose.full.yml logs -f [服务名]
```

### 6.2 查看容器日志

```bash
# MySQL日志
docker logs -f zhurong-ems-mysql

# 后端服务日志
docker logs -f zhurong-ems-backend

# 前端服务日志
docker logs -f zhurong-ems-frontend
```

### 6.3 进入容器内部

```bash
# 进入MySQL容器
docker exec -it zhurong-ems-mysql bash

# 进入后端容器
docker exec -it zhurong-ems-backend sh

# 进入前端容器
docker exec -it zhurong-ems-frontend sh
```

### 6.4 数据库操作

```bash
# 连接MySQL数据库
docker exec -it zhurong-ems-mysql mysql -uroot -proot

# 查看所有表
docker exec -it zhurong-ems-mysql mysql -uroot -proot -e "USE autoee_ems; SHOW TABLES;"

# Redis操作
docker exec -it zhurong-ems-redis redis-cli -a difyai123456
```

---

## 七、故障排查

### 7.1 服务健康检查

```bash
# 检查所有容器状态
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

# 检查MySQL健康状态
docker exec zhurong-ems-mysql mysqladmin ping -uroot -proot

# 检查Redis健康状态
docker exec zhurong-ems-redis redis-cli -a difyai123456 ping

# 检查后端健康状态
curl http://localhost:1088/autoee-iot-ems/actuator/health
```

### 7.2 常见问题

| 问题 | 解决方案 |
|:---|:---|
| 端口被占用 | 修改 docker-compose.full.yml 中的端口映射 |
| 数据库连接失败 | 检查 MySQL 容器是否健康，确认密码配置 |
| 后端启动失败 | 查看日志: `docker logs zhurong-ems-backend` |
| 前端502错误 | 确认后端服务已正常启动 |
| 数据丢失 | 检查数据卷是否正确挂载 |

---

## 八、安全注意事项

⚠️ **重要提示**:

1. **生产环境部署前，请务必修改默认密码**
   - MySQL root 密码: root → 修改为强密码
   - Redis 密码: difyai123456 → 修改为强密码
   - RabbitMQ guest 密码: guest → 修改为强密码
   - EMQX admin 密码: public → 修改为强密码

2. **端口暴露**
   - 当前配置将所有服务端口映射到主机，仅在开发环境使用
   - 生产环境应使用防火墙限制访问，仅暴露必要的端口

3. **数据备份**
   - 定期备份数据卷: `docker volume backup`
   - MySQL数据位于: zhurong-ems-mysql-data

4. **TDengine 禁用说明**
   - 当前配置已禁用TDengine时序数据库
   - 如需启用，请修改后端配置并重新部署

---

## 九、配置参考文件

| 文件 | 用途 |
|:---|:---|
| `docker-compose.full.yml` | Docker Compose主配置文件 |
| `application.yml` | 后端应用外部配置文件 |
| `docker/mysql/init.sql` | MySQL数据库初始化脚本 |

---

**文档生成时间**: 2026-04-28  
**版本**: v1.0  
**适用环境**: WSL2 + Docker Desktop
