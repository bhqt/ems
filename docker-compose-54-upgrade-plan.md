# Docker Compose 配置升级方案

## 一、升级目标

将 `docker-compose.full.yml` 中的配置同步到 `docker-compose-54.yml`，保持 54 服务器的利旧策略。

## 二、现有环境分析（docker-compose-54.yml）

### 2.1 利旧服务（保持不变）

| 服务名 | 说明 | 状态 |
|--------|------|------|
| legal-mysql | MySQL 数据库（外部） | ✅ 利旧 |
| shared-redis | Redis 缓存（外部） | ✅ 利旧 |

### 2.2 已配置服务

| 服务名 | 说明 | 需要升级 |
|--------|------|----------|
| zhurong-ems-rabbitmq | RabbitMQ | ⚠️ 检查 |
| zhurong-ems-emqx | EMQX MQTT | ⚠️ 检查 |
| zhurong-ems-tdengine | TDengine | ⚠️ **重点升级** |
| zhurong-ems-backend | 后端服务 | ⚠️ 检查 |
| zhurong-ems-frontend | 前端服务 | ⚠️ **重点升级** |
| zhurong-ems-xxl-job | XXL-Job | ✅ |
| zhurong-ems-monitor | Monitor | ✅ |

## 三、重点升级内容

### 3.1 前端配置（zhurong-ems-frontend）

**当前配置（docker-compose-54.yml）**:
```yaml
zhurong-ems-frontend:
  image: zhurong-admin-ui:latest
  ports:
    - "3080:80"
  environment:
    VUE_APP_API_BASE_URL: http://zhurong-ems-backend:8088/autoee-iot-ems
```

**建议升级为（来自 docker-compose.full.yml）**:
```yaml
zhurong-ems-frontend:
  image: zhurong-ems/frontend:latest  # 使用完整版镜像名
  container_name: zhurong-ems-frontend
  restart: unless-stopped
  ports:
    - "3080:80"
  environment:
    VUE_APP_API_BASE_URL: http://zhurong-ems-backend:8088/autoee-iot-ems
    TZ: Asia/Shanghai
  volumes:
    - zhurong-ems-frontend-logs:/var/log/nginx
  networks:
    - zhurong-ems-network
  depends_on:
    zhurong-ems-backend:
      condition: service_healthy
  healthcheck:
    test: ["CMD-SHELL", "curl -f http://localhost || exit 1"]
    interval: 30s
    timeout: 10s
    retries: 3
```

**变更点**:
- ✅ 镜像名称更新为 `zhurong-ems/frontend:latest`
- ✅ 添加 `restart: unless-stopped`
- ✅ 添加 `TZ: Asia/Shanghai`
- ✅ 添加日志卷挂载
- ✅ 添加网络配置
- ✅ 添加健康检查

---

### 3.2 涛思数据库配置（zhurong-ems-tdengine）

**当前配置（docker-compose-54.yml）**:
```yaml
zhurong-ems-tdengine:
  image: tdengine/tdengine:3.2.0.0
  environment:
    TAOS_FQDN: zhurong-ems-tdengine
    TAOS_USER: root
    TAOS_PASS: difyai123456
  ports:
    - "6030:6030"
    - "6041:6041"
```

**建议升级为（来自 docker-compose.full.yml）**:
```yaml
zhurong-ems-tdengine:
  image: tdengine/tdengine:3.2.0.0
  container_name: zhurong-ems-tdengine
  restart: unless-stopped
  environment:
    TAOS_FQDN: zhurong-ems-tdengine
    TAOS_USER: root
    TAOS_PASS: difyai123456
    TZ: Asia/Shanghai
  ports:
    - "6030:6030"
    - "6041:6041"
  volumes:
    - zhurong-ems-tdengine-data:/var/lib/taos
    - zhurong-ems-tdengine-log:/var/log/taos
  networks:
    - zhurong-ems-network
  healthcheck:
    test: ["CMD-SHELL", "taos -u root -s 'show databases;' > /dev/null 2>&1"]
    interval: 10s
    timeout: 5s
    retries: 5
```

**变更点**:
- ✅ 添加 `restart: unless-stopped`
- ✅ 添加 `TZ: Asia/Shanghai`
- ✅ 添加数据卷挂载（持久化）
- ✅ 添加网络配置
- ✅ 添加健康检查

---

### 3.3 后端服务配置（zhurong-ems-backend）

**关键升级点 - TDengine 连接**:

当前配置（docker-compose-54.yml）:
```yaml
# TDengine 时序数据库（可选，按需启用）
SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_ENABLED: "false"  # 禁用
# SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_URL: jdbc:TAOS-RS://...
```

建议升级为:
```yaml
# TDengine 时序数据库配置（启用）
SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_ENABLED: "true"
SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_DRIVERCLASSNAME: com.taosdata.jdbc.rs.RestfulDriver
SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_URL: jdbc:TAOS-RS://zhurong-ems-tdengine:6041/energy?user=energy&password=difyai123456&timezone=Asia/Shanghai
SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_USERNAME: energy
SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_PASSWORD: difyai123456
```

---

### 3.4 EMQX MQTT Broker 配置

当前配置已经比较完善，无需大改。建议检查：
- 端口映射是否完整
- Dashboard 访问是否正常
- 用户名密码是否正确

## 四、完整升级配置

### 4.1 docker-compose-54.yml 完整配置

```yaml
# =============================================
# Deep-EMS 部署配置 (192.168.8.54 服务器)
# 复用已有的 MySQL (legal-mysql) 和 Redis (shared-redis)
# 新增/升级 RabbitMQ、Backend、Frontend、XXL-Job、Monitor、TDengine
# =============================================

services:

  # ========== RabbitMQ 消息队列 ==========

  zhurong-ems-rabbitmq:
    image: rabbitmq:3.12-management-alpine
    container_name: zhurong-ems-rabbitmq
    restart: unless-stopped
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
      TZ: Asia/Shanghai
    ports:
      - "5672:5672"
      - "15672:15672"
    volumes:
      - zhurong-ems-rabbitmq-data:/var/lib/rabbitmq
    networks:
      - zhurong-ems-network
    healthcheck:
      test: ["CMD", "rabbitmq-diagnostics", "ping"]
      interval: 10s
      timeout: 5s
      retries: 5

  # ========== EMQ X MQTT Broker ==========

  zhurong-ems-emqx:
    image: emqx/emqx:5.3.2
    container_name: zhurong-ems-emqx
    restart: unless-stopped
    environment:
      TZ: Asia/Shanghai
      EMQX_NAME: emqx
      EMQX_HOST: 127.0.0.1
      EMQX_Dashboard__default_username: admin
      EMQX_Dashboard__default_password: public
      EMQX_MQTT__DEFAULT__LISTENER: 1883
      EMQX_MQTT__DEFAULT__MAX_PACKET_SIZE: 1MB
      EMQX_MQTT__DEFAULT__MAX_CLIENTID_LEN: 128
      EMQX_MQTT__DEFAULT__MAX_KEEPALIVE: 360
      EMQX_MQTT__DEFAULT__KEEPALIVE_BACKOFF: 0.75
    ports:
      - "1883:1883"     # MQTT 协议端口
      - "8883:8883"     # MQTT/SSL 端口
      - "18083:18083"   # Dashboard HTTP API 端口
      - "8081:8081"     # WebSocket 端口
      - "8083:8083"     # WebSocket/SSL 端口
    volumes:
      - zhurong-ems-emqx-data:/opt/emqx/data
      - zhurong-ems-emqx-log:/opt/emqx/log
      - zhurong-ems-emqx-plugins:/opt/emqx/plugins
    networks:
      - zhurong-ems-network
    healthcheck:
      test: ["CMD", "/opt/emqx/bin/emqx_ctl", "status"]
      interval: 10s
      timeout: 5s
      retries: 5

  # ========== TDengine 时序数据库（已升级）==========

  zhurong-ems-tdengine:
    image: tdengine/tdengine:3.2.0.0
    container_name: zhurong-ems-tdengine
    restart: unless-stopped
    environment:
      TAOS_FQDN: zhurong-ems-tdengine
      TAOS_USER: root
      TAOS_PASS: difyai123456
      TZ: Asia/Shanghai
    ports:
      - "6030:6030"   # TDengine 原生客户端连接端口
      - "6041:6041"   # RESTful API 端口
    volumes:
      - zhurong-ems-tdengine-data:/var/lib/taos
      - zhurong-ems-tdengine-log:/var/log/taos
    networks:
      - zhurong-ems-network
    healthcheck:
      test: ["CMD-SHELL", "taos -u root -s 'show databases;' > /dev/null 2>&1"]
      interval: 10s
      timeout: 5s
      retries: 5

  # ========== 后端服务 ==========

  zhurong-ems-backend:
    image: zhurong-ems/backend:latest
    container_name: zhurong-ems-backend
    restart: unless-stopped
    ports:
      - "1088:8088"
    environment:
      SPRING_PROFILES_ACTIVE: docker
      # 复用服务器已有的 MySQL (legal-mysql:3306)
      SPRING_DATASOURCE_DYNAMIC_DATASOURCE_MASTER_URL: jdbc:mysql://legal-mysql:3306/autoee_ems?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8&autoReconnect=true&rewriteBatchedStatements=true
      SPRING_DATASOURCE_DYNAMIC_DATASOURCE_MASTER_USERNAME: root
      SPRING_DATASOURCE_DYNAMIC_DATASOURCE_MASTER_PASSWORD: root
      # 复用服务器已有的 Redis (shared-redis:6379)
      SPRING_REDIS_HOST: shared-redis
      SPRING_REDIS_PORT: 6379
      SPRING_REDIS_PASSWORD: difyai123456
      SPRING_REDIS_DATABASE: 13
      # RabbitMQ 配置
      SPRING_RABBITMQ_HOST: zhurong-ems-rabbitmq
      SPRING_RABBITMQ_PORT: 5672
      SPRING_RABBITMQ_USERNAME: guest
      SPRING_RABBITMQ_PASSWORD: guest
      # MQTT EMQ X 配置
      MQTT_HOST: tcp://zhurong-ems-emqx:1883
      MQTT_PORT: 1883
      MQTT_USERNAME: admin
      MQTT_PASSWORD: public
      MQTT_CLIENT_ID: zhurong-ems-server-${HOSTNAME:-default}
      MQTT_TIMEOUT: 120
      MQTT_KEEPALIVE: 360
      # TDengine 时序数据库配置（已启用）
      SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_ENABLED: "true"
      SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_DRIVERCLASSNAME: com.taosdata.jdbc.rs.RestfulDriver
      SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_URL: jdbc:TAOS-RS://zhurong-ems-tdengine:6041/energy?user=energy&password=difyai123456&timezone=Asia/Shanghai
      SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_USERNAME: energy
      SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_PASSWORD: difyai123456
      # JVM 参数
      JAVA_OPTS: -Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.lang.ClassLoader=ALL-UNNAMED --add-opens=java.base/java.security=ALL-UNNAMED
      TZ: Asia/Shanghai
    volumes:
      - zhurong-ems-backend-logs:/zhurong-ems/server/logs
      - zhurong-ems-backend-temp:/zhurong-ems/server/temp
    networks:
      - zhurong-ems-network
    depends_on:
      zhurong-ems-rabbitmq:
        condition: service_healthy
      zhurong-ems-emqx:
        condition: service_healthy
      zhurong-ems-tdengine:
        condition: service_healthy
    healthcheck:
      test: ["CMD-SHELL", "curl -f http://localhost:8088/autoee-iot-ems/actuator/health || exit 1"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 60s

  # ========== 前端服务（已升级）==========

  zhurong-ems-frontend:
    image: zhurong-ems/frontend:latest
    container_name: zhurong-ems-frontend
    restart: unless-stopped
    ports:
      - "3080:80"
    environment:
      VUE_APP_API_BASE_URL: http://zhurong-ems-backend:8088/autoee-iot-ems
      TZ: Asia/Shanghai
    volumes:
      - zhurong-ems-frontend-logs:/var/log/nginx
    networks:
      - zhurong-ems-network
    depends_on:
      zhurong-ems-backend:
        condition: service_healthy
    healthcheck:
      test: ["CMD-SHELL", "curl -f http://localhost || exit 1"]
      interval: 30s
      timeout: 10s
      retries: 3

  # ========== XXL-Job 调度中心 ==========

  zhurong-ems-xxl-job:
    image: zhurong-ems/xxl-job:4.6.0
    container_name: zhurong-ems-xxl-job-admin
    restart: unless-stopped
    ports:
      - "9110:9100"
    environment:
      TZ: Asia/Shanghai
      SPRING_DATASOURCE_URL: jdbc:mysql://legal-mysql:3306/xxl_job?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
    volumes:
      - ./logs/xxljob:/zhurong-ems/xxljob/logs
      - zhurong-ems-xxljob-logs:/data/applogs
    networks:
      - zhurong-ems-network
    healthcheck:
      test: ["CMD-SHELL", "wget -qO- http://localhost:9100/xxl-job-admin/actuator/health || exit 1"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 60s

  # ========== Spring Boot Admin 监控 ==========

  zhurong-ems-monitor:
    image: zhurong-ems/monitor:4.6.0
    container_name: zhurong-ems-monitor
    restart: unless-stopped
    ports:
      - "8690:9090"
    environment:
      SPRING_PROFILES_ACTIVE: admin
      SPRING_BOOT_ADMIN_ADMIN_USERNAME: admin
      SPRING_BOOT_ADMIN_ADMIN_PASSWORD: zhurong123
      TZ: Asia/Shanghai
    volumes:
      - zhurong-ems-monitor-logs:/cp-portal/monitor/logs
    networks:
      - zhurong-ems-network

# ================================
# 数据卷
# ================================
volumes:
  zhurong-ems-emqx-data:
    driver: local
  zhurong-ems-emqx-log:
    driver: local
  zhurong-ems-emqx-plugins:
    driver: local
  zhurong-ems-rabbitmq-data:
    driver: local
  zhurong-ems-tdengine-data:
    driver: local
  zhurong-ems-tdengine-log:
    driver: local
  zhurong-ems-backend-logs:
    driver: local
  zhurong-ems-backend-temp:
    driver: local
  zhurong-ems-frontend-logs:
    driver: local
  zhurong-ems-xxljob-logs:
    driver: local
  zhurong-ems-monitor-logs:
    driver: local

# ================================
# 网络
# ================================
networks:
  zhurong-ems-network:
    name: zhurong-ems-network
    driver: bridge
```

## 五、升级步骤

### 5.1 升级前准备

```bash
# 1. 备份当前配置
cp docker-compose-54.yml docker-compose-54.yml.backup

# 2. 查看当前运行状态
docker-compose -f docker-compose-54.yml ps

# 3. 停止服务
docker-compose -f docker-compose-54.yml down
```

### 5.2 应用升级配置

```bash
# 1. 复制新配置
cp docker-compose-54-upgraded.yml docker-compose-54.yml

# 2. 重新启动服务
docker-compose -f docker-compose-54.yml up -d

# 3. 查看服务状态
docker-compose -f docker-compose-54.yml ps

# 4. 查看日志
docker-compose -f docker-compose-54.yml logs -f
```

### 5.3 验证升级

```bash
# 1. 验证前端
curl http://localhost:3080

# 2. 验证后端
curl http://localhost:1088/autoee-iot-ems/actuator/health

# 3. 验证 EMQX Dashboard
curl http://localhost:18083

# 4. 验证 TDengine
docker exec zhurong-ems-tdengine taos -u root -s 'show databases;'
```

## 六、注意事项

### 6.1 数据持久化

升级后需要注意：
- TDengine 数据卷已配置，确保数据不会丢失
- MySQL 和 Redis 使用外部服务，数据不受影响

### 6.2 服务依赖

启动顺序：
1. RabbitMQ → 2. EMQX → 3. TDengine → 4. Backend → 5. Frontend

使用 `depends_on` 和 `condition: service_healthy` 确保健康检查通过后再启动依赖服务。

### 6.3 镜像更新

确保以下镜像已构建并推送到镜像仓库：
- `zhurong-ems/backend:latest`
- `zhurong-ems/frontend:latest`
- `zhurong-ems/xxl-job:4.6.0`
- `zhurong-ems/monitor:4.6.0`

## 七、配置文件位置

升级后的配置文件：
- `D:\code\gitcp\inspur-ems\deep-ems0\docker-compose-54.yml`

---

**文档版本**: v1.0
**创建日期**: 2026-04-30
**项目名称**: Deep-EMS 54 服务器环境升级
