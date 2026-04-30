# Deep-EMS WSL Docker 部署清单

**文档版本**: V1.0  
**创建日期**: 2026-04-30  
**适用环境**: WSL2 + Docker Desktop  
**部署方式**: Docker Compose  

---

## 一、部署环境要求

| 项目 | 要求 |
|------|------|
| 操作系统 | Windows 10/11 + WSL2 |
| Docker 版本 | Docker Desktop 4.x+ |
| Docker Compose | 2.x+ |
| 内存 | 建议 8GB 以上 |
| 磁盘空间 | 建议 20GB 以上可用空间 |
| 网络 | 需要开放相应端口 |

---

## 二、容器部署清单

### 2.1 MySQL 数据库

| 配置项 | 值 |
|:---|:---|
| **容器名称** | zhurong-ems-mysql |
| **镜像版本** | mysql:8.0 |
| **主机端口** | 3306 |
| **容器端口** | 3306 |
| **数据库名** | autoee_ems |
| **用户名** | root |
| **密码** | root |
| **字符集** | utf8mb4 |
| **最大连接数** | 1000 |
| **时区** | Asia/Shanghai |
| **数据卷** | zhurong-ems-mysql-data |
| **健康检查** | mysqladmin ping -uroot -proot |

**访问地址**: `localhost:3306`  
**JDBC URL**: `jdbc:mysql://localhost:3306/autoee_ems?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8`

---

### 2.2 Redis 缓存

| 配置项 | 值 |
|:---|:---|
| **容器名称** | zhurong-ems-redis |
| **镜像版本** | redis:7 |
| **主机端口** | 6379 |
| **容器端口** | 6379 |
| **密码** | difyai123456 |
| **持久化** | AOF 开启 |
| **数据卷** | zhurong-ems-redis-data |
| **健康检查** | redis-cli -a difyai123456 ping |

**访问地址**: `localhost:6379`  
**连接命令**: `redis-cli -a difyai123456`

---

### 2.3 RabbitMQ 消息队列

| 配置项 | 值 |
|:---|:---|
| **容器名称** | zhurong-ems-rabbitmq |
| **镜像版本** | rabbitmq:3.12-management-alpine |
| **AMQP 端口** | 5672 |
| **管理界面端口** | 15672 |
| **用户名** | guest |
| **密码** | guest |
| **数据卷** | zhurong-ems-rabbitmq-data |
| **健康检查** | rabbitmq-diagnostics ping |

**访问地址**:
- AMQP: `localhost:5672`
- 管理界面：`http://localhost:15672`

**管理界面登录**: guest / guest

---

### 2.4 EMQX MQTT Broker

| 配置项 | 值 |
|:---|:---|
| **容器名称** | zhurong-ems-emqx |
| **镜像版本** | emqx/emqx:5.3.2 |
| **MQTT 端口** | 1883 |
| **MQTT/SSL 端口** | 8883 |
| **Dashboard 端口** | 18083 |
| **WebSocket 端口** | 8081 |
| **WebSocket/SSL 端口** | 8083 |
| **Dashboard 用户名** | admin |
| **Dashboard 密码** | public |
| **MQTT 用户名** | admin |
| **MQTT 密码** | public |
| **最大报文大小** | 1MB |
| **最大 Keepalive** | 360 秒 |
| **数据卷** | zhurong-ems-emqx-data, zhurong-ems-emqx-log, zhurong-ems-emqx-plugins |
| **健康检查** | /opt/emqx/bin/emqx_ctl status |

**访问地址**:
- MQTT 连接：`tcp://localhost:1883`
- MQTT/SSL 连接：`ssl://localhost:8883`
- Dashboard: `http://localhost:18083`
- WebSocket: `ws://localhost:8081/mqtt`

**Dashboard 登录**: admin / public

**MQTT 连接参数**:
```
Host: tcp://localhost:1883
Port: 1883
Username: admin
Password: public
Client ID: zhurong-ems-server-{hostname}
Timeout: 120s
Keepalive: 360s
```

---

### 2.5 TDengine 时序数据库

| 配置项 | 值 |
|:---|:---|
| **容器名称** | zhurong-ems-tdengine |
| **镜像版本** | tdengine/tdengine:3.2.0.0 |
| **原生客户端端口** | 6030 |
| **RESTful API 端口** | 6041 |
| **Root 用户名** | root |
| **Root 密码** | difyai123456 |
| **应用用户名** | energy |
| **应用用户密码** | difyai123456 |
| **数据库名** | energy |
| **FQDN** | zhurong-ems-tdengine |
| **数据卷** | zhurong-ems-tdengine-data, zhurong-ems-tdengine-log |
| **健康检查** | taos -u root -s 'show databases;' |

**访问地址**:
- 原生客户端：`localhost:6030`
- RESTful API: `http://localhost:6041`

**JDBC 连接 URL**:
```
jdbc:TAOS-RS://localhost:6041/energy?user=energy&password=difyai123456&timezone=Asia/Shanghai
```

**数据库表结构**:
- 超级表：`energy`
- 子表：`electricityw`, `electricityi`, `electricityu`, `electricityp`, `water`

---

### 2.6 后端服务 (Spring Boot)

| 配置项 | 值 |
|:---|:---|
| **容器名称** | zhurong-ems-backend |
| **镜像版本** | zhurong-ems/backend:latest |
| **主机端口** | 1088 |
| **容器端口** | 8088 |
| **Java 版本** | Java 17 |
| **Spring Boot 版本** | 2.7.9 |
| **应用版本** | 4.6.0 |
| **内存配置** | -Xms512m -Xmx1024m |
| **配置文件** | application.yml (外部挂载) |
| **健康检查** | http://localhost:8088/autoee-iot-ems/actuator/health |
| **启动依赖** | RabbitMQ, EMQX, TDengine (均需 healthy) |

**访问地址**:
- API Base URL: `http://localhost:1088/autoee-iot-ems`
- Actuator: `http://localhost:1088/autoee-iot-ems/actuator`
- Swagger: `http://localhost:1088/autoee-iot-ems/swagger-ui/` (如启用)

**环境配置**:
```yaml
MySQL:
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
  Port: 1883
  Username: admin
  Password: public

TDengine:
  Enabled: true
  URL: jdbc:TAOS-RS://zhurong-ems-tdengine:6041/energy
  Username: energy
  Password: difyai123456
```

---

### 2.7 前端服务 (Nginx)

| 配置项 | 值 |
|:---|:---|
| **容器名称** | zhurong-ems-frontend |
| **镜像版本** | zhurong-ems/frontend:latest |
| **主机端口** | 3080 |
| **容器端口** | 80 |
| **后端 API 地址** | http://zhurong-ems-backend:8088/autoee-iot-ems |
| **数据卷** | zhurong-ems-frontend-logs |
| **健康检查** | curl -f http://localhost |
| **启动依赖** | zhurong-ems-backend (healthy) |

**访问地址**: `http://localhost:3080`

---

## 三、网络配置

### 3.1 Docker 网络

| 网络名称 | 驱动 | 用途 |
|:---|:---|:---|
| zhurong-ems-network | bridge | 内部服务通信网络 |
| legal-network | bridge | 外部服务通信网络（预留） |

### 3.2 容器间通信

所有中间件和后端服务在 `zhurong-ems-network` 网络中，可以通过容器名互相访问：

```
zhurong-ems-backend → zhurong-ems-mysql:3306
zhurong-ems-backend → zhurong-ems-redis:6379
zhurong-ems-backend → zhurong-ems-rabbitmq:5672
zhurong-ems-backend → zhurong-ems-emqx:1883
zhurong-ems-backend → zhurong-ems-tdengine:6041
zhurong-ems-frontend → zhurong-ems-backend:8088
```

---

## 四、数据卷管理

| 数据卷名称 | 用途 | 挂载路径 |
|:---|:---|:---|
| zhurong-ems-mysql-data | MySQL 数据持久化 | /var/lib/mysql |
| zhurong-ems-redis-data | Redis 数据持久化 | /data |
| zhurong-ems-rabbitmq-data | RabbitMQ 数据持久化 | /var/lib/rabbitmq |
| zhurong-ems-emqx-data | EMQX 数据持久化 | /opt/emqx/data |
| zhurong-ems-emqx-log | EMQX 日志 | /opt/emqx/log |
| zhurong-ems-emqx-plugins | EMQX 插件 | /opt/emqx/plugins |
| zhurong-ems-tdengine-data | TDengine 数据持久化 | /var/lib/taos |
| zhurong-ems-tdengine-log | TDengine 日志 | /var/log/taos |
| zhurong-ems-backend-logs | 后端日志 | /zhurong-ems/server/logs |
| zhurong-ems-backend-temp | 后端临时文件 | /zhurong-ems/server/temp |
| zhurong-ems-frontend-logs | Nginx 日志 | /var/log/nginx |

---

## 五、部署命令

### 5.1 启动所有服务

```bash
# 进入项目目录
cd /mnt/d/code/gitcp/inspur-ems/deep-ems0

# 启动所有服务
docker-compose -f docker-compose.full.yml up -d

# 查看服务状态
docker-compose -f docker-compose.full.yml ps
```

### 5.2 启动单个服务

```bash
# 启动 MySQL
docker-compose -f docker-compose.full.yml up -d zhurong-ems-mysql

# 启动 TDengine
docker-compose -f docker-compose.full.yml up -d zhurong-ems-tdengine

# 启动后端服务
docker-compose -f docker-compose.full.yml up -d zhurong-ems-backend
```

### 5.3 重启服务

```bash
# 重启后端服务
docker-compose -f docker-compose.full.yml restart zhurong-ems-backend

# 强制重新创建容器
docker-compose -f docker-compose.full.yml up -d --force-recreate zhurong-ems-backend
```

### 5.4 停止服务

```bash
# 停止所有服务
docker-compose -f docker-compose.full.yml down

# 停止单个服务
docker-compose -f docker-compose.full.yml stop zhurong-ems-backend
```

### 5.5 查看日志

```bash
# 查看所有服务日志
docker-compose -f docker-compose.full.yml logs -f

# 查看后端服务日志
docker-compose -f docker-compose.full.yml logs -f zhurong-ems-backend

# 查看 TDengine 日志
docker-compose -f docker-compose.full.yml logs -f zhurong-ems-tdengine
```

---

## 六、健康检查状态

| 服务 | 健康检查命令 | 检查间隔 | 超时时间 | 重试次数 |
|:---|:---|:---|:---|:---|
| MySQL | mysqladmin ping -uroot -proot | 10s | 5s | 5 |
| Redis | redis-cli -a difyai123456 ping | 10s | 3s | 5 |
| RabbitMQ | rabbitmq-diagnostics ping | 10s | 5s | 5 |
| EMQX | /opt/emqx/bin/emqx_ctl status | 10s | 5s | 5 |
| TDengine | taos -u root -s 'show databases;' | 10s | 5s | 5 |
| Backend | curl -f http://localhost:8088/autoee-iot-ems/actuator/health | 30s | 10s | 3 |
| Frontend | curl -f http://localhost | 30s | 10s | 3 |

---

## 七、端口映射总览

| 服务 | 主机端口 | 容器端口 | 协议 | 用途 |
|:---|:---|:---|:---|:---|
| MySQL | 3306 | 3306 | TCP | 数据库连接 |
| Redis | 6379 | 6379 | TCP | 缓存连接 |
| RabbitMQ | 5672 | 5672 | TCP | AMQP 连接 |
| RabbitMQ | 15672 | 15672 | TCP | 管理界面 |
| EMQX | 1883 | 1883 | TCP | MQTT 连接 |
| EMQX | 8883 | 8883 | TCP | MQTT/SSL 连接 |
| EMQX | 18083 | 18083 | TCP | Dashboard |
| EMQX | 8081 | 8081 | TCP | WebSocket |
| EMQX | 8083 | 8083 | TCP | WebSocket/SSL |
| TDengine | 6030 | 6030 | TCP | 原生客户端 |
| TDengine | 6041 | 6041 | TCP | RESTful API |
| Backend | 1088 | 8088 | TCP | API 服务 |
| Frontend | 3080 | 80 | TCP | Web 界面 |

---

## 八、常见问题排查

### 8.1 容器无法启动

```bash
# 查看容器日志
docker logs zhurong-ems-{service-name}

# 查看容器详细信息
docker inspect zhurong-ems-{service-name}
```

### 8.2 健康检查失败

```bash
# 查看健康检查日志
docker inspect --format='{{json .State.Health}}' zhurong-ems-{service-name} | jq
```

### 8.3 端口冲突

```bash
# 查看端口占用
netstat -ano | findstr "端口号"

# 修改 docker-compose.yml 中的端口映射
```

### 8.4 数据库连接失败

```bash
# 进入容器测试连接
docker exec -it zhurong-ems-mysql mysql -uroot -proot
docker exec -it zhurong-ems-redis redis-cli -a difyai123456
docker exec -it zhurong-ems-tdengine taos -u energy -pdifyai123456
```

---

## 九、安全建议

1. **修改默认密码**: 生产环境务必修改所有默认密码
2. **限制端口暴露**: 仅开放必要的端口到宿主机
3. **使用网络隔离**: 将敏感服务限制在内部网络
4. **启用 SSL/TLS**: MQTT、数据库连接建议启用加密
5. **定期备份**: 定期备份数据卷中的重要数据
6. **日志审计**: 开启并定期审查各服务日志

---

## 十、性能优化建议

### 10.1 MySQL 优化
- 调整 `max_connections` 根据实际并发量
- 配置合适的 InnoDB 缓冲池大小
- 启用慢查询日志

### 10.2 Redis 优化
- 根据内存情况调整最大内存限制
- 配置合适的淘汰策略
- 开启持久化保证数据安全

### 10.3 后端服务优化
- 调整 JVM 堆内存参数 `-Xms` 和 `-Xmx`
- 配置数据库连接池大小
- 启用 G1 垃圾收集器

---

## 十一、监控与维护

### 11.1 监控指标

- **CPU 使用率**: 各容器 CPU 占用
- **内存使用率**: 各容器内存占用
- **磁盘使用率**: 数据卷磁盘占用
- **网络流量**: 容器间网络通信
- **服务健康状态**: 健康检查结果

### 11.2 日志管理

```bash
# 查看后端服务最近 100 行日志
docker logs --tail 100 zhurong-ems-backend

# 实时查看日志
docker logs -f zhurong-ems-backend

# 导出日志到文件
docker logs zhurong-ems-backend > backend.log 2>&1
```

### 11.3 数据备份

```bash
# 备份 MySQL 数据库
docker exec zhurong-ems-mysql mysqldump -uroot -proot autoee_ems > backup.sql

# 备份 Redis 数据
docker exec zhurong-ems-redis redis-cli -a difyai123456 BGSAVE

# 备份 TDengine 数据
docker exec zhurong-ems-tdengine taosdump -u root -p difyai123456 energy
```

---

## 十二、版本信息

| 组件 | 版本 | 发布日期 |
|:---|:---|:---|
| MySQL | 8.0 | - |
| Redis | 7 | - |
| RabbitMQ | 3.12 | - |
| EMQX | 5.3.2 | - |
| TDengine | 3.2.0.0 | - |
| Spring Boot | 2.7.9 | - |
| Java | 17 | - |
| Deep-EMS | 4.6.0 | - |

---

**文档维护**: Deep-EMS 团队  
**最后更新**: 2026-04-30
