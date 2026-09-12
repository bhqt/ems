# docker-compose 部署配置总览

> 本文档全面梳理 Deep-EMS 所有 Docker 编排文件、与 `D:\code\shared-infra` 共享中间件的关系，以及各部署场景的差异。

***

## 一、docker-compose 文件全景

| 文件 | 场景 | 中间件来源 | 网络 | 适用环境 |
|------|------|-----------|------|----------|
| `docker-compose.yml` | 早期本地全量 | 自建全部中间件 | `zhurong-ems-network` | 本地（已较少使用） |
| `docker-compose.full.yml` | 本地 WSL 全量 | 自建全部中间件 | `zhurong-ems-network` + `legal-network` | 本地 WSL 开发 |
| `docker-compose-54.yml` | 192.168.8.54 服务器 | **MySQL/Redis 利旧外部服务**，其余自建 | `legal-network` | 生产服务器 |
| `docker-compose-simple.yml` | 精简版 | 自建（不含 TDengine/XXL-Job/SBA） | `zhurong-ems-network` | 快速验证 |
| `docs/部署指南/docker-compose.yml` | 部署指南专用 | 同 `docker-compose.full.yml` | 同 `docker-compose.full.yml` | 文档示例 |

### 关键差异速查

| 配置项 | `docker-compose.full.yml` | `docker-compose-54.yml` |
|--------|--------------------------|------------------------|
| **MySQL** | 内部容器 `zhurong-ems-mysql` | 外部服务 `legal-mysql:3306` |
| **Redis** | 内部容器 `zhurong-ems-redis` | 外部服务 `shared-redis:6379` |
| **网络** | `zhurong-ems-network` | `legal-network` |
| **depends_on** | 有（等待中间件健康） | 无（外部服务不可控） |
| **TDengine** | 启用 | 启用 |
| **XXL-Job** | 包含 | 包含 |
| **SBA 监控** | 包含（注释） | 包含（注释） |
| **前端镜像** | `zhurong-ems/frontend:latest` | `zhurong-ems/frontend:latest` |

***

## 二、shared-infra 共享中间件层

### 2.1 概述

`D:\code\shared-infra` 是 WSL Docker 环境下的**跨项目共用中间件层**，通过 Docker Compose Profile 机制分层管理多个项目共享的基础服务。各业务项目只启动自己的应用，基础服务在此统一维护。

### 2.2 Profile 分层

| Profile | 服务 | 适用场景 |
|---------|------|----------|
| `core` | MariaDB、MySQL、Redis | 大多数 Java/Web 项目 |
| `coldchain` | MongoDB、RabbitMQ、MinIO | 冷链项目 |
| `postgres` | PostgreSQL | 部分 AI / 开源项目 |
| `iot` | EMQX(MQTT)、TDengine(时序库) | zhurong-ems 等 IoT 项目 |
| `tools` | redis-commander / mongo-express | 管理 UI |

### 2.3 shared-infra 连接参数

| 组件 | 地址 | 账号 / 口令 | 库 / 说明 |
|------|------|------------|----------|
| MySQL | `localhost:3306` | `root` / `123456` | 库 `autoee_ems`、`xxl_job` |
| Redis | `localhost:6379` db `13` | `difyai123456` | 共享 Redis，带密码 |
| RabbitMQ | `localhost:5672` | `rabbitmq` / `rabbitmqpassword` | vhost `admin_vhost` |
| EMQX(MQTT) | `tcp://localhost:1883` | `admin` / `public` | Dashboard `18083` |
| TDengine | `localhost:6041`(REST) | `root` / `taosdata` | 库 `energy` |

### 2.4 数据持久化方式

| 存储方式 | 服务 | WSL 路径 / Volume 名 |
|----------|------|---------------------|
| **named volume**（WSL ext4） | MariaDB、MySQL、MongoDB、RabbitMQ、PostgreSQL、EMQX、TDengine | `shared-mariadb-data` 等 |
| **bind mount**（Windows D 盘） | Redis、MinIO | `/mnt/d/docker-data/shared-infra/` |

> **注意**：数据库类服务在 WSL + Docker 环境下不能使用 NTFS bind mount，会出现文件权限错误导致容器反复重启。Redis 和 MinIO 可以使用 bind mount。

***

## 三、Deep-EMS 与 shared-infra 的整合关系

### 3.1 整合架构图

```
┌─────────────────────────────────────────────────────────────────────┐
│                        deep-ems0 项目                               │
│                                                                     │
│  ┌──────────────────┐    ┌──────────────────┐                     │
│  │ docker-compose   │    │ docker-compose   │                     │
│  │ -54.yml          │    │ -full.yml        │                     │
│  │ (生产服务器)      │    │ (本地 WSL)       │                     │
│  │                  │    │                  │                     │
│  │ MySQL → legal-   │    │ MySQL →          │                     │
│  │ mysql(外部)      │    │ zhurong-ems-     │                     │
│  │                  │    │ mysql(自建)       │                     │
│  │ Redis → shared-  │    │ Redis →          │                     │
│  │ redis(外部)      │    │ zhurong-ems-     │                     │
│  │                  │    │ redis(自建)       │                     │
│  │ RabbitMQ/EMQX/   │    │ RabbitMQ/EMQX/   │                     │
│  │ TDengine/XXL-Job │    │ TDengine/XXL-Job │                     │
│  │ /Backend/Frontend│    │ /Backend/Frontend │                     │
│  └───────┬──────────┘    └───────┬──────────┘                     │
│          │                      │                                │
└──────────┼──────────────────────┼────────────────────────────────┘
           │                      │
           │    ┌─────────────────▼─────────────────┐               │
           │    │     D:\code\shared-infra           │               │
           │    │  (WSL Docker, Profile 机制)         │               │
           │    │                                     │               │
           │    │  Profile: core + iot               │               │
           │    │  ┌─────────┐  ┌─────────┐          │               │
           │    │  │ MySQL   │  │ Redis   │          │               │
           │    │  │ mariadb │  │         │          │               │
           │    │  │ mysql   │  │         │          │               │
           │    │  ├─────────┤  ├─────────┤          │               │
           │    │  │ EMQX    │  │ TDengine│          │               │
           │    │  │ rabbitmq│  │         │          │               │
           │    │  └─────────┘  └─────────┘          │               │
           │    │  Network: shared-infra              │               │
           │    └─────────────────┬──────────────────┘               │
           │                      │                                  │
           └──────────────────────┼──────────────────────────────────┘
                                  │  本地开发时通过 localhost 映射端口连接
                                  │  生产部署时通过 Docker 服务名连接
                                  │
                                  ▼
                          ┌──────────────┐
                          │  应用层      │
                          │  - Backend   │
                          │  - Frontend  │
                          │  - XXL-Job   │
                          └──────────────┘
```

### 3.2 本地开发模式

- **后端**通过 `application-local.yml` 指向 `localhost` 映射端口连接 shared-infra
- 启动参数：`--spring.profiles.active=local`
- **xxl-job 调度中心、SBA 监控不在 shared-infra**，local 模式下 `enabled: false` 关闭
- 前端 `.env.development` 中 `VUE_APP_BASE_API = http://localhost:8088/autoee-iot-ems`

`application-local.yml` 连接配置：
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

### 3.3 生产部署模式

- **`docker-compose-54.yml`** 部署到 192.168.8.54 服务器
- MySQL、Redis 利旧服务器已有服务（`legal-mysql`、`shared-redis`）
- RabbitMQ、EMQX、TDengine、Backend、Frontend、XXL-Job 仍以容器部署
- 通过 Docker 服务名互相通信，不映射宿主机端口

***

## 四、各 compose 文件服务清单

### 4.1 docker-compose.full.yml 服务列表

| 服务名 | 镜像 | 端口 | 依赖 | 说明 |
|--------|------|------|------|------|
| zhurong-ems-mysql | mysql:8.0 | 3306 | — | 内部 MySQL |
| zhurong-ems-redis | redis:7 | 6379 | — | 内部 Redis |
| zhurong-ems-rabbitmq | rabbitmq:3.12-management-alpine | 5672/15672 | — | 消息队列 |
| zhurong-ems-emqx | emqx/emqx:5.3.2 | 1883/8883/18083/8081/8083 | — | MQTT Broker |
| zhurong-ems-tdengine | tdengine/tdengine:3.2.0.0 | 6030/6041 | — | 时序数据库 |
| zhurong-ems-xxl-job | zhurong-ems/xxl-job:4.6.0 | 9110:9100 | zhurong-ems-mysql | 调度中心 |
| zhurong-ems-backend | zhurong-ems/backend:latest | 1088:8088 | rabbitmq/emqx/tdengine | 后端应用 |
| zhurong-ems-frontend | zhurong-ems/frontend:latest | 3080:80 | zhurong-ems-backend | 前端 Nginx |
| zhurong-ems-sba | codecentric/spring-boot-admin:3.0.0 | 9090:8080 | — | 监控中心（注释） |

### 4.2 docker-compose-54.yml 服务列表

| 服务名 | 镜像 | 端口 | 依赖 | 说明 |
|--------|------|------|------|------|
| zhurong-ems-rabbitmq | rabbitmq:3.12-management-alpine | 5672/15672 | — | 消息队列 |
| zhurong-ems-emqx | emqx/emqx:5.3.2 | 1883/8883/18083/8081/8083 | — | MQTT Broker |
| zhurong-ems-tdengine | tdengine/tdengine:3.2.0.0 | 6030/6041 | — | 时序数据库 |
| zhurong-ems-backend | zhurong-ems/backend:latest | 1088:8088 | rabbitmq/emqx/tdengine | 后端应用 |
| zhurong-ems-frontend | zhurong-ems/frontend:latest | 3080:80 | zhurong-ems-backend | 前端 Nginx |
| zhurong-ems-xxl-job | zhurong-ems/xxl-job:4.6.0 | 9110:9100 | — | 调度中心 |
| zhurong-ems-monitor | zhurong-ems/monitor:4.6.0 | 8690:9090 | — | 监控中心 |
| **legal-mysql** | *(外部服务)* | 3306 | — | 服务器已有 |
| **shared-redis** | *(外部服务)* | 6379 | — | 服务器已有 |

### 4.3 shared-infra 服务列表（`docker compose --profile core --profile iot up`）

| 服务名 | 镜像 | 端口 | Profile | 说明 |
|--------|------|------|---------|------|
| mysql | mysql:8.0 | 3306 | core | 共享 MySQL |
| mariadb | mariadb:11.8 | 23306 | core | 共享 MariaDB |
| redis | redis:7-alpine | 6379 | core | 共享 Redis |
| emqx | emqx/emqx:5.3.2 | 1883/18083/8081/8083 | iot | 共享 EMQX |
| tdengine | tdengine/tdengine:3.3.6.13 | 6030/6041 | iot | 共享 TDengine |

***

## 五、启动命令速查

### 5.1 Deep-EMS 项目

```bash
# 本地 WSL 全量部署
cd /mnt/d/code/gitcp/inspur-ems/deep-ems0
docker compose -f docker-compose.full.yml up -d

# 生产服务器部署
docker compose -f docker-compose-54.yml up -d

# 精简版部署
docker compose -f docker-compose-simple.yml up -d

# 查看状态
docker compose -f docker-compose.full.yml ps

# 停止
docker compose -f docker-compose.full.yml down
```

### 5.2 shared-infra 共享中间件

```bash
cd /mnt/d/code/shared-infra
cp .env.example .env    # 首次使用

# 启动 core + iot（MySQL + Redis + EMQX + TDengine）
docker compose --profile core --profile iot up -d

# 仅 core（MySQL + Redis）
docker compose --profile core up -d

# 查看状态
docker compose ps
```

***

## 六、网络拓扑

| 网络名 | 所属 compose | 驱动 | 用途 |
|--------|-------------|------|------|
| `zhurong-ems-network` | deep-ems0 全量文件 | bridge | Deep-EMS 内部服务通信 |
| `legal-network` | docker-compose-54.yml | bridge | 生产服务器外部服务通信 |
| `shared-infra` | D:\code\shared-infra | bridge | 共享中间件层，跨项目共用 |

本地开发时，`zhurong-ems-network` 与 `shared-infra` 通过 localhost 端口映射桥接。生产部署时，`legal-network` 直接与 `shared-infra` 在同一 Docker 网络中通过服务名通信。

***

## 七、相关文档索引

| 文档 | 内容 |
|------|------|
| [EMS接入共享中间件本地研发联调经验.md](./EMS接入共享中间件本地研发联调经验.md) | 接入 shared-infra 的详细步骤和避坑指南 |
| [dockercompose说明.md](./dockercompose说明.md) | docker-compose.full.yml 与 docker-compose-54.yml 差异对比 |
| [WSL-Docker 部署清单.md](./WSL-Docker 部署清单.md) | 各容器详细配置、端口、健康检查汇总 |
| [Deep-EMS 系统中间件清单.md](./Deep-EMS 系统中间件清单.md) | 全部中间件版本、用途、依赖关系 |
| [TDengine 手动初始化操作手册.md](./TDengine 手动初始化操作手册.md) | TDengine 数据库初始化步骤 |
| [打包部署常见问题.md](./打包部署常见问题.md) | 部署过程中的常见问题及解决方案 |
| [打包操作说明.md](./打包操作说明.md) | 镜像构建与打包流程 |
| [生产环境部署配置.md](./生产环境部署配置.md) | 生产环境配置详情 |
| [Docker配置说明.md](../Docker配置说明.md) | Docker 容器化配置通用说明 |

***

**文档版本**: V1.0  
**创建日期**: 2026-09-05  
**最后更新**: 2026-09-05
