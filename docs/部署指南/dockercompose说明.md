# docker-compose 配置说明

## 1. docker-compose.full.yml（本地 WSL 环境）

本地开发全量部署，包含所有中间件和业务服务：

- ✅ **中间件**：MySQL (`zhurong-ems-mysql`)、Redis (`zhurong-ems-redis`)、RabbitMQ (`zhurong-ems-rabbitmq`)、EMQX (`zhurong-ems-emqx`)、TDengine (`zhurong-ems-tdengine`)
- ✅ **后端**：`zhurong-ems/backend:latest`，端口 `1088:8088`
- ✅ **前端**：`zhurong-ems/frontend:latest`，端口 `3080:80`
- ✅ **XXL-Job**：`zhurong-ems/xxl-job:4.6.0`，端口 `9110:9100`
- ✅ **SBA 监控**：`codecentric/spring-boot-admin:3.0.0`（注释状态），端口 `9090:8080`
- ✅ **网络**：`zhurong-ems-network` + `legal-network`
- ✅ **依赖**：后端通过 `depends_on` + `condition: service_healthy` 等待 RabbitMQ、EMQX、TDengine
- ✅ **健康检查**：30 秒间隔，60 秒启动等待期
- ✅ **日志卷**：`zhurong-ems-backend-logs`、`zhurong-ems-xxljob-logs` 等

### 启动命令

```bash
docker compose -f docker-compose.full.yml up -d
```

---

## 2. docker-compose-54.yml（192.168.8.54 服务器）

生产服务器部署，**复用服务器已有中间件**：

- ✅ **MySQL**：外部服务 `legal-mysql:3306`（不复用容器）
- ✅ **Redis**：外部服务 `shared-redis:6379`（不复用容器）
- ✅ **RabbitMQ / EMQX / TDengine**：自建容器
- ✅ **后端**：`zhurong-ems/backend:latest`，端口 `1088:8088`
- ✅ **前端**：`zhurong-ems/frontend:latest`，端口 `3080:80`
- ✅ **XXL-Job**：`zhurong-ems/xxl-job:4.6.0`，端口 `9110:9100`
- ✅ **监控**：`zhurong-ems/monitor:4.6.0`，端口 `8690:9090`
- ✅ **网络**：`legal-network`
- ⚠️ **无 depends_on**：外部服务不可控，不配置启动依赖

### 启动命令

```bash
docker compose -f docker-compose-54.yml up -d
```

---

## 3. docker-compose-simple.yml（精简版）

快速验证使用，不含 TDengine、XXL-Job、SBA 监控：

- ✅ MySQL、Redis、RabbitMQ、EMQX
- ✅ 后端 + 前端
- ✅ 网络：`zhurong-ems-network`

---

## 4. 与 shared-infra 的关系

| 场景 | MySQL 来源 | Redis 来源 | 说明 |
|------|-----------|-----------|------|
| **本地 WSL**（docker-compose.full.yml） | 自建容器 `zhurong-ems-mysql` | 自建容器 `zhurong-ems-redis` | 完全自包含 |
| **本地研发调试**（application-local.yml） | shared-infra 的 `shared-mysql` | shared-infra 的 `shared-redis` | 通过 localhost 映射端口连接 |
| **生产服务器**（docker-compose-54.yml） | 外部 `legal-mysql` | 外部 `shared-redis` | 复用服务器已有服务 |
| **shared-infra 本身**（profile: core + iot） | `mysql` / `mariadb` | `redis` | 跨项目共用中间件层 |

### local profile 连接方式

本地研发调试时，后端通过 `application-local.yml` 指向 shared-infra 的 localhost 映射端口：

```yaml
spring:
  datasource:
    master:
      url: jdbc:mysql://localhost:3306/autoee_ems  # shared-mysql
  redis:
    host: localhost                                # shared-redis
    password: difyai123456
  rabbitmq:
    host: localhost                                # shared-rabbitmq
mqtt:
  host: tcp://localhost:1883                       # shared-emqx
```

---

## 5. 配置差异汇总

| 配置项 | docker-compose.full.yml | docker-compose-54.yml | 使用场景 |
|--------|-------------------------|----------------------|----------|
| MySQL 地址 | `zhurong-ems-mysql:3306` | `legal-mysql:3306` | 本地 WSL / 生产服务器 |
| Redis 地址 | `zhurong-ems-redis:6379` | `shared-redis:6379` | 本地 WSL / 生产服务器 |
| 网络 | `zhurong-ems-network` | `legal-network` | 自建 / 外部 |
| 依赖配置 | 有 `depends_on` | 无 | 内部可控 / 外部不可控 |
| 启动命令 | `docker compose -f docker-compose.full.yml up -d` | `docker compose -f docker-compose-54.yml up -d` | — |

---

## 6. 端口映射总览

| 服务 | 容器名 | full.yml 端口 | 54.yml 端口 | 说明 |
|------|--------|--------------|------------|------|
| MySQL | zhurong-ems-mysql | 3306:3306 | — (外部) | 数据库 |
| Redis | zhurong-ems-redis | 6379:6379 | — (外部) | 缓存 |
| RabbitMQ | zhurong-ems-rabbitmq | 5672/15672 | 5672/15672 | 消息队列 |
| EMQX | zhurong-ems-emqx | 1883/8883/18083/8081/8083 | 1883/8883/18083/8081/8083 | MQTT Broker |
| TDengine | zhurong-ems-tdengine | 6030/6041 | 6030/6041 | 时序数据库 |
| 后端 | zhurong-ems-backend | 1088:8088 | 1088:8088 | Spring Boot 应用 |
| 前端 | zhurong-ems-frontend | 3080:80 | 3080:80 | Nginx 静态资源 |
| XXL-Job | zhurong-ems-xxl-job-admin | 9110:9100 | 9110:9100 | 调度中心 |
| Monitor | zhurong-ems-monitor | — (注释) | 8690:9090 | 监控中心 |

---

## 7. 验证结果

- ✅ docker-compose.full.yml 配置验证通过
- ✅ XXL-Job 服务已添加到服务列表
- ✅ 所有配置语法正确

### 访问地址

| 服务 | 地址 | 默认账号 |
|------|------|---------|
| XXL-Job 管理 | http://localhost:9110/xxl-job-admin | admin / 123456 |
| RabbitMQ 管理 | http://localhost:15672 | guest / guest |
| EMQX Dashboard | http://localhost:18083 | admin / public |
| 后端健康检查 | http://localhost:1088/autoee-iot-ems/actuator/health | — |
