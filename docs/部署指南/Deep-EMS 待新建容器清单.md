# Deep-EMS 待新建容器清单

> 基于现有服务器容器情况，对比 Deep-EMS 系统中间件需求，列出需要新建的容器。

---

## 一、对比分析

### 已有容器（复用）

| # | 容器名 | 镜像版本 | 说明 |
| - | ------ | -------- | ---- |
| 1 | legal-mysql | mysql:8.0.42 | 可复用于 Deep-EMS（版本接近8.0.35），需创建独立数据库 |
| 2 | shared-redis | redis:6-alpine | 可复用于 Deep-EMS（版本接近7.2.3），需配置独立DB |
| 3 | legal-minio | minio/minio:RELEASE.2024-11-07T00-52-20Z | 可复用于 Deep-EMS OSS模块 |
| 4 | legal-nginx | nginx:latest | 可作为 Deep-EMS 反向代理入口 |

### 缺失容器（需新建）

| # | 容器名 | 用途 | 优先级 |
| - | ------ | ---- | ------ |
| 1 | zhurong-ems-rabbitmq | 消息队列 | 必需 |
| 2 | zhurong-ems-emqx | MQTT消息代理 | 必需 |
| 3 | zhurong-ems-xxl-job-executor | 分布式定时任务 | 推荐 |
| 4 | zhurong-ems-sba | Spring Boot Admin监控 | 推荐 |
| 5 | zhurong-ems-tdengine | 时序数据库（可选） | 按需 |

---

## 二、需新建容器详情

### 1. RabbitMQ（必需）

| 项目 | 配置值 |
| ---- | ------ |
| 容器名 | zhurong-ems-rabbitmq |
| 镜像 | rabbitmq:3.12-management-alpine |
| 端口映射 | 5672:5672, 15672:15672 |
| 环境变量 | RABBITMQ_DEFAULT_USER=zhurong / RABBITMQ_DEFAULT_PASS=zhurong123 |
| 数据卷 | /opt/zhurong-ems/rabbitmq/data:/var/lib/rabbitmq |
| 说明 | 业务异步消息通信，管理界面：http://localhost:15672 |

### 2. EMQX（必需）

| 项目 | 配置值 |
| ---- | ------ |
| 容器名 | zhurong-ems-emqx |
| 镜像 | emqx/emqx:5.3.0 |
| 端口映射 | 1883:1883, 8083:8083, 18083:18083 |
| 环境变量 | EMQX_NAME=zhurong-ems-emqx / EMQX_HOST=127.0.0.1 |
| 数据卷 | /opt/zhurong-ems/emqx/data:/opt/emqx/data /opt/zhurong-ems/emqx/log:/opt/emqx/log |
| 说明 | IoT设备消息代理，Dashboard：http://localhost:18083（admin/public） |

### 3. XXL-Job 执行器（推荐）

| 项目 | 配置值 |
| ---- | ------ |
| 容器名 | zhurong-ems-xxl-job-executor |
| 镜像 | xuxueli/xxl-job-core:2.3.1 |
| 端口映射 | 9101:9101 |
| 环境变量 | PARAMS="--spring.datasource.url=jdbc:mysql://legal-mysql:3306/zhurong_ems_xxl?useUnicode=true&characterEncoding=UTF-8" |
| 依赖 | 依赖已有 MySQL，需创建 xxl_job 数据库 |
| 说明 | 需配合调度中心使用，或使用社区版调度中心 |

### 4. Spring Boot Admin（推荐）

| 项目 | 配置值 |
| ---- | ------ |
| 容器名 | zhurong-ems-sba |
| 镜像 | codecentric/spring-boot-admin:2.7.10 |
| 端口映射 | 9090:9090 |
| 环境变量 | SPRING_BOOT_ADMIN_ADMIN_USERNAME=admin / SPRING_BOOT_ADMIN_ADMIN_PASSWORD=zhurong123 |
| 说明 | 应用健康监控与管理，访问地址：http://localhost:9090 |

### 5. TDengine（可选/按需）

| 项目 | 配置值 |
| ---- | ------ |
| 容器名 | zhurong-ems-tdengine |
| 镜像 | tdengine/tdengine:3.2.0.0 |
| 端口映射 | 6030:6030, 6041:6041 |
| 环境变量 | TZ=Asia/Shanghai |
| 数据卷 | /opt/zhurong-ems/tdengine/data:/var/lib/taos |
| 说明 | 时序数据库，用于能源数据存储，需创建 root/taosdata 用户 |

---

## 三、端口规划

| 服务 | 宿主机端口 | 容器端口 | 状态 |
| ---- | --------- | -------- | ---- |
| RabbitMQ | 5672 | 5672 | 新建 |
| RabbitMQ Management | 15672 | 15672 | 新建 |
| EMQX MQTT | 1883 | 1883 | 新建 |
| EMQX WebSocket | 8083 | 8083 | 新建 |
| EMQX Dashboard | 18083 | 18083 | 新建 |
| XXL-Job Executor | 9101 | 9101 | 新建 |
| Spring Boot Admin | 9090 | 9090 | 新建 |
| TDengine | 6030 | 6030 | 新建 |
| TDengine RESTful | 6041 | 6041 | 新建 |

---

## 四、数据库创建（如复用已有MySQL）

```sql
-- Deep-EMS 业务数据库
CREATE DATABASE zhurong_ems DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
GRANT ALL PRIVILEGES ON zhurong_ems.* TO 'zhurong'@'%' IDENTIFIED BY 'zhurong123';

-- XXL-Job 调度数据库
CREATE DATABASE xxl_job DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
GRANT ALL PRIVILEGES ON xxl_job.* TO 'zhurong'@'%' IDENTIFIED BY 'zhurong123';
```

---

## 五、Docker Compose 片段

```yaml
# 新增容器 - 可合并到现有 docker-compose.yml

  # RabbitMQ
  zhurong-ems-rabbitmq:
    image: rabbitmq:3.12-management-alpine
    container_name: zhurong-ems-rabbitmq
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: zhurong
      RABBITMQ_DEFAULT_PASS: zhurong123
    volumes:
      - /opt/zhurong-ems/rabbitmq/data:/var/lib/rabbitmq
    restart: unless-stopped

  # EMQX
  zhurong-ems-emqx:
    image: emqx/emqx:5.3.0
    container_name: zhurong-ems-emqx
    ports:
      - "1883:1883"
      - "8083:8083"
      - "18083:18083"
    environment:
      EMQX_NAME: zhurong-ems-emqx
      EMQX_HOST: 127.0.0.1
    volumes:
      - /opt/zhurong-ems/emqx/data:/opt/emqx/data
      - /opt/zhurong-ems/emqx/log:/opt/emqx/log
    restart: unless-stopped

  # Spring Boot Admin
  zhurong-ems-sba:
    image: codecentric/spring-boot-admin:2.7.10
    container_name: zhurong-ems-sba
    ports:
      - "9090:9090"
    environment:
      SPRING_BOOT_ADMIN_ADMIN_USERNAME: admin
      SPRING_BOOT_ADMIN_ADMIN_PASSWORD: zhurong123
    restart: unless-stopped

  # TDengine (可选)
  zhurong-ems-tdengine:
    image: tdengine/tdengine:3.2.0.0
    container_name: zhurong-ems-tdengine
    ports:
      - "6030:6030"
      - "6041:6041"
    environment:
      TZ: Asia/Shanghai
    volumes:
      - /opt/zhurong-ems/tdengine/data:/var/lib/taos
    restart: unless-stopped
```

---

## 六、执行顺序

1. **第一步**：创建 MySQL 数据库（zhurong_ems, xxl_job）
2. **第二步**：启动 RabbitMQ
3. **第三步**：启动 EMQX
4. **第四步**：启动 TDengine（如需要）
5. **第五步**：启动 Spring Boot Admin
6. **第六步**：启动 Deep-EMS 后端服务
7. **最后**：配置 Nginx 反向代理

---

## 七、总结

| 类别 | 数量 | 说明 |
| ---- | ---- | ---- |
| 必需新建 | 2 | RabbitMQ, EMQX |
| 推荐新建 | 2 | XXL-Job Executor, Spring Boot Admin |
| 可选新建 | 1 | TDengine |
| 可复用 | 4 | MySQL, Redis, MinIO, Nginx |

**建议优先部署必需组件（RabbitMQ + EMQX），后再根据需求添加推荐和可选组件。**