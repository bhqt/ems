# Docker 容器化配置说明

## 配置概述

项目已配置完整的 Docker 容器化方案，包括：

1. **中间件服务**：MySQL、Redis、RabbitMQ、EMQX、TDengine
2. **后端服务**：Spring Boot 应用
3. **前端服务**：Vue.js 应用（通过 Nginx 提供静态文件服务和 API 代理）

## Docker 编排文件全景

| 文件 | 场景 | 中间件来源 | 网络 | 适用环境 |
|------|------|-----------|------|----------|
| `docker-compose.yml` | 早期本地全量 | 自建全部中间件 | `zhurong-ems-network` | 本地（已较少使用） |
| `docker-compose.full.yml` | 本地 WSL 全量 | 自建全部中间件 | `zhurong-ems-network` + `legal-network` | 本地 WSL 开发 |
| `docker-compose-54.yml` | 192.168.8.54 服务器 | **MySQL/Redis 利旧外部服务**，其余自建 | `legal-network` | 生产服务器 |
| `docker-compose-simple.yml` | 精简版 | 自建（不含 TDengine/XXL-Job/SBA） | `zhurong-ems-network` | 快速验证 |

### 各文件关键差异

| 配置项 | `docker-compose.full.yml` | `docker-compose-54.yml` |
|--------|--------------------------|------------------------|
| **MySQL** | 内部容器 `zhurong-ems-mysql` | 外部服务 `legal-mysql:3306` |
| **Redis** | 内部容器 `zhurong-ems-redis` | 外部服务 `shared-redis:6379` |
| **网络** | `zhurong-ems-network` | `legal-network` |
| **depends_on** | 有（等待中间件健康） | 无（外部服务不可控） |
| **前端镜像** | `zhurong-ems/frontend:latest` | `zhurong-ems/frontend:latest` |

## shared-infra 共享中间件整合

> 详细接入经验见 [EMS接入共享中间件本地研发联调经验.md](EMS接入共享中间件本地研发联调经验.md)

### 概述

`D:\code\shared-infra` 是 WSL Docker 环境下的**跨项目共用中间件层**，通过 Docker Compose Profile 机制分层管理多个项目共享的基础服务。各业务项目只启动自己的应用，基础服务在此统一维护。

### Profile 分层

| Profile | 服务 | 适用场景 |
|---------|------|----------|
| `core` | MariaDB、MySQL、Redis | 大多数 Java/Web 项目 |
| `coldchain` | MongoDB、RabbitMQ、MinIO | 冷链项目 |
| `iot` | EMQX(MQTT)、TDengine(时序库) | zhurong-ems 等 IoT 项目 |
| `postgres` | PostgreSQL | 部分 AI / 开源项目 |

### shared-infra 连接参数

| 组件 | 地址 | 账号 / 口令 | 库 / 说明 |
|------|------|------------|----------|
| MySQL | `localhost:3306` | `root` / `123456` | 库 `autoee_ems`、`xxl_job` |
| Redis | `localhost:6379` db `13` | **有密码** `difyai123456` | 共享 Redis |
| RabbitMQ | `localhost:5672` | `rabbitmq` / `rabbitmqpassword` | vhost `admin_vhost` |
| EMQX(MQTT) | `tcp://localhost:1883` | `admin` / `public` | Dashboard `18083` |
| TDengine | `localhost:6041`(REST) | `root` / `taosdata` | 库 `energy` |

### 两种部署模式

**本地研发模式**：后端通过 `application-local.yml` 指向 `localhost` 映射端口连接 shared-infra，启动参数 `--spring.profiles.active=local`。xxl-job 和 SBA 本地关闭。

**生产部署模式**：`docker-compose-54.yml` 复用服务器已有的 `legal-mysql` 和 `shared-redis`，RabbitMQ/EMQX/TDengine/Backend/Frontend/XXL-Job 仍以容器部署，通过 Docker 服务名通信。

### shared-infra 启动命令

```bash
cd /mnt/d/code/shared-infra
cp .env.example .env    # 首次使用

# 启动 core + iot（MySQL + Redis + EMQX + TDengine）
docker compose --profile core --profile iot up -d

# 仅 core（MySQL + Redis）
docker compose --profile core up -d
```

## 核心配置说明

### 1. 前端 Nginx 配置

**文件位置**：`zhurong-admin-ui/nginx.conf`

**主要功能**：
- 提供前端静态文件服务
- 配置反向代理，将 API 请求转发到后端服务
- 支持 Vue Router History 模式

**API代理配置**：
```nginx
location /autoee-iot-ems/ {
    proxy_pass http://zhurong-ems-backend:8088;
    # ... 其他配置
}
```

**重要说明**：
- 前端构建时的 `VUE_APP_BASE_API` 环境变量必须与 nginx 代理路径一致
- 默认配置为 `/autoee-iot-ems/`，与后端 context-path 一致
- 如需修改，需要同时调整 Dockerfile 构建参数和 nginx.conf

### 2. 后端环境变量配置

**文件位置**：`docker-compose.full.yml` / `docker-compose-54.yml`

后端通过环境变量连接中间件服务：

- **MySQL**：`zhurong-ems-mysql:3306`（本地）或 `legal-mysql:3306`（生产）
- **Redis**：`zhurong-ems-redis:6379`（本地）或 `shared-redis:6379`（生产）
- **RabbitMQ**：`zhurong-ems-rabbitmq:5672`
- **EMQX**：`zhurong-ems-emqx:1883`
- **TDengine**：`zhurong-ems-tdengine:6041`

**环境变量格式**：
Spring Boot 使用大写+下划线的格式，例如：
- `SPRING_DATASOURCE_DYNAMIC_DATASOURCE_MASTER_URL`
- `SPRING_REDIS_HOST`
- `SPRING_RABBITMQ_HOST`
- `MQTT_HOST`

### 3. 前端构建配置

**文件位置**：`zhurong-admin-ui/Dockerfile`

前端构建时通过 ARG 参数设置环境变量：

```dockerfile
ARG VUE_APP_BASE_API=/autoee-iot-ems/
ARG VUE_APP_CONTEXT_PATH=/
ENV VUE_APP_BASE_API=${VUE_APP_BASE_API}
ENV VUE_APP_CONTEXT_PATH=${VUE_APP_CONTEXT_PATH}
```

在 `docker-compose.full.yml` 中通过 build args 传入。

### 4. 本地研发配置（application-local.yml）

**文件位置**：`zhurong-ems-admin/src/main/resources/application-local.yml`

自包含全部中间件连接，指向本机 shared-infra 映射端口，不依赖 docker-compose 服务名。

```yaml
spring:
  datasource:
    master:
      url: jdbc:mysql://localhost:3306/autoee_ems  # → shared-mysql
      username: root
      password: 123456
    td:
      enabled: true
      url: jdbc:TAOS-RS://localhost:6041/energy    # → shared-tdengine
      username: root
      password: taosdata
  redis:
    host: localhost                                # → shared-redis
    port: 6379
    database: 13
    password: difyai123456
  rabbitmq:
    host: localhost                                # → shared-rabbitmq
    port: 5672
    username: rabbitmq
    password: rabbitmqpassword
    virtual-host: admin_vhost
mqtt:
  host: tcp://localhost:1883                       # → shared-emqx
  username: admin
  password: public
```

## 部署方式

### 方式1：仅部署中间件

```bash
# 启动 shared-infra（core + iot）
cd /mnt/d/code/shared-infra
docker compose --profile core --profile iot up -d
```

### 方式2：本地 WSL 全量部署

```bash
# 中间件 + 后端 + 前端 + XXL-Job
docker compose -f docker-compose.full.yml up -d
```

### 方式3：生产服务器部署

```bash
# 复用服务器已有 MySQL/Redis，其余容器化部署
docker compose -f docker-compose-54.yml up -d
```

### 方式4：精简版部署

```bash
docker compose -f docker-compose-simple.yml up -d
```

## 服务访问地址

部署后，各服务访问地址：

| 环境 | 服务 | 地址 |
|------|------|------|
| 本地 WSL | 前端页面 | http://localhost:3080 |
| 本地 WSL | 后端API | http://localhost:1088/autoee-iot-ems |
| 本地 WSL | MySQL | localhost:3306 |
| 本地 WSL | Redis | localhost:6379 (db13) |
| 本地 WSL | RabbitMQ 管理界面 | http://localhost:15672 |
| 本地 WSL | EMQX Dashboard | http://localhost:18083 |
| 本地 WSL | 后端健康检查 | http://localhost:1088/autoee-iot-ems/actuator/health |
| 生产服务器 | 后端API | http://localhost:1088/autoee-iot-ems |
| 生产服务器 | XXL-Job 管理 | http://localhost:9110/xxl-job-admin |
| 生产服务器 | 监控中心 | http://localhost:8690 |

## 配置修改说明

### 修改前端API路径

如果需要修改前端API路径（例如改为 `/dev-api/`），需要修改以下文件：

1. **docker-compose.full.yml**：
    ```yaml
    build:
      args:
        VUE_APP_BASE_API: /dev-api/  # 修改这里
    ```

2. **zhurong-admin-ui/nginx.conf**：
    ```nginx
    location /dev-api/ {  # 添加或修改这个location
        proxy_pass http://zhurong-ems-backend:8088/autoee-iot-ems/;
    }
    ```

3. **重新构建前端镜像**：
    ```bash
    docker compose -f docker-compose.full.yml build zhurong-ems-frontend
    ```

### 修改后端中间件连接

修改对应 docker-compose 文件中后端服务的环境变量。生产环境修改 `docker-compose-54.yml`，本地环境修改 `docker-compose.full.yml` 或 `application-local.yml`。

```yaml
environment:
  - SPRING_REDIS_HOST=新的redis服务地址
  - SPRING_DATASOURCE_DYNAMIC_DATASOURCE_MASTER_URL=jdbc:mysql://新的mysql地址:3306/...
```

### 切换本地/生产数据源

- **本地开发**：编辑 `application-local.yml`，连接 `localhost` 映射端口
- **容器部署**：编辑 `docker-compose.full.yml` 或 `docker-compose-54.yml` 中的环境变量
- **Spring Profiles**：`--spring.profiles.active=local`（本地）或 `docker`（容器）

## 注意事项

1. **端口冲突**：确保本地端口 80、8088、3306、6379、5672、15672 未被占用
2. **数据持久化**：所有中间件数据都通过 Docker Volume 持久化
3. **网络配置**：本地使用 `zhurong-ems-network`，生产使用 `legal-network`
4. **健康检查**：后端服务会等待中间件服务健康检查通过后才启动
5. **前端构建**：前端构建时会自动使用 Dockerfile 中设置的环境变量
6. **shared-infra 数据库类服务**：WSL + Docker 环境下不能使用 NTFS bind mount，会出现文件权限错误导致容器反复重启
7. **Redis 有密码**：shared-infra 的 Redis 密码为 `difyai123456`，本地和容器配置都必须带密码
8. **xxl-job / SBA 本地调试**：不在 shared-infra 中，local 模式下 `enabled: false` 关闭

## 故障排查

### 前端无法访问后端

1. 检查 nginx.conf 中的 proxy_pass 地址是否正确
2. 检查前端构建时的 VUE_APP_BASE_API 是否与 nginx 配置一致
3. 检查后端服务是否正常运行：`docker compose logs zhurong-ems-backend`
4. 检查网络连接：`docker compose exec zhurong-ems-frontend ping zhurong-ems-backend`

### 后端无法连接中间件

1. 检查环境变量配置是否正确
2. 检查中间件服务是否正常运行：`docker compose ps`
3. 检查网络连接：`docker compose exec zhurong-ems-backend ping zhurong-ems-mysql`
4. 查看后端日志：`docker compose logs zhurong-ems-backend`

### 本地连接 shared-infra 失败

1. 确认 shared-infra 已启动：`cd /mnt/d/code/shared-infra && docker compose ps`
2. 确认端口映射正确：`docker compose port redis 6379`
3. 检查 Redis 密码：`docker exec shared-redis redis-cli -a difyai123456 ping`
4. 检查 MySQL 连接：`docker exec shared-mysql mysql -uroot -p123456 -e "SHOW DATABASES;"`

### 容器网络不通

1. 检查容器是否在同一个网络：`docker inspect <容器名> --format '{{json .NetworkSettings.Networks}}'`
2. 确认 Docker 网络名称正确（本地 `zhurong-ems-network`，生产 `legal-network`）
3. 尝试手动连接网络：`docker network connect zhurong-ems-network <容器名>`
