# Deep-EMS 54 服务器环境升级说明

## 一、升级概述

### 1.1 升级目标

将 `docker-compose.full.yml` 中的配置同步到 54 服务器环境，重点升级：
- **前端服务** (zhurong-ems-frontend)
- **涛思数据库** (zhurong-ems-tdengine)
- **后端 TDengine 配置**

### 1.2 升级原则

- ✅ MySQL、Redis 利旧（使用外部服务）
- ✅ 保持服务依赖关系
- ✅ 添加健康检查
- ✅ 配置数据持久化

---

## 二、升级内容详解

### 2.1 前端服务升级

**变更项**:

| 项目 | 原配置 | 新配置 |
|------|--------|--------|
| 镜像名 | `zhurong-admin-ui:latest` | `zhurong-ems/frontend:latest` |
| 重启策略 | 无 | `unless-stopped` |
| 时区 | 无 | `Asia/Shanghai` |
| 日志卷 | 无 | `zhurong-ems-frontend-logs` |
| 网络 | 无 | `zhurong-ems-network` |
| 健康检查 | 无 | `curl health check` |

**新增配置**:
```yaml
restart: unless-stopped
TZ: Asia/Shanghai
volumes:
  - zhurong-ems-frontend-logs:/var/log/nginx
networks:
  - zhurong-ems-network
healthcheck:
  test: ["CMD-SHELL", "curl -f http://localhost || exit 1"]
```

---

### 2.2 涛思数据库升级

**变更项**:

| 项目 | 原配置 | 新配置 |
|------|--------|--------|
| 重启策略 | 无 | `unless-stopped` |
| 时区 | 无 | `Asia/Shanghai` |
| 数据卷 | 无 | `zhurong-ems-tdengine-data` |
| 日志卷 | 无 | `zhurong-ems-tdengine-log` |
| 网络 | 无 | `zhurong-ems-network` |
| 健康检查 | 无 | `taos ping` |

**新增配置**:
```yaml
restart: unless-stopped
TZ: Asia/Shanghai
volumes:
  - zhurong-ems-tdengine-data:/var/lib/taos
  - zhurong-ems-tdengine-log:/var/log/taos
networks:
  - zhurong-ems-network
healthcheck:
  test: ["CMD-SHELL", "taos -u root -s 'show databases;' > /dev/null 2>&1"]
```

---

### 2.3 后端服务升级

**关键变更**: 启用 TDengine

**原配置**:
```yaml
SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_ENABLED: "false"  # 禁用
# SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_URL: jdbc:TAOS-RS://...
```

**新配置**:
```yaml
# TDengine 时序数据库配置（已启用）
SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_ENABLED: "true"
SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_DRIVERCLASSNAME: com.taosdata.jdbc.rs.RestfulDriver
SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_URL: jdbc:TAOS-RS://zhurong-ems-tdengine:6041/energy?user=energy&password=difyai123456&timezone=Asia/Shanghai
SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_USERNAME: energy
SPRING_DATASOURCE_DYNAMIC_DATASOURCE_TD_PASSWORD: difyai123456
```

---

## 三、升级步骤

### 3.1 升级前准备

**步骤 1**: 备份当前配置

```bash
# 在 54 服务器上执行
cp docker-compose-54.yml docker-compose-54.yml.backup.$(date +%Y%m%d)
```

**步骤 2**: 检查当前服务状态

```bash
docker-compose -f docker-compose-54.yml ps
```

**步骤 3**: 查看当前日志

```bash
docker-compose -f docker-compose-54.yml logs --tail=50
```

### 3.2 执行升级

**方式一: 使用升级脚本**

```bash
# 1. 上传新配置文件
scp docker-compose-54.yml user@192.168.8.54:/path/to/

# 2. 执行升级脚本
chmod +x upgrade-54.sh
./upgrade-54.sh
```

**方式二: 手动升级**

```bash
# 1. 停止服务
docker-compose -f docker-compose-54.yml down

# 2. 更新配置文件
# 将新的 docker-compose-54.yml 上传到服务器

# 3. 启动服务
docker-compose -f docker-compose-54.yml up -d

# 4. 等待服务启动
sleep 30

# 5. 查看状态
docker-compose -f docker-compose-54.yml ps
```

### 3.3 验证升级

**使用验证脚本**:

```bash
chmod +x verify-upgrade-54.sh
./verify-upgrade-54.sh
```

**手动验证**:

```bash
# 1. 检查容器状态
docker-compose -f docker-compose-54.yml ps

# 2. 检查健康状态
curl http://localhost:1088/autoee-iot-ems/actuator/health

# 3. 检查前端
curl http://localhost:3080

# 4. 检查 EMQX
curl http://localhost:18083

# 5. 检查 TDengine
docker exec zhurong-ems-tdengine taos -u root -s 'show databases;'
```

### 3.4 查看日志

```bash
# 查看所有服务日志
docker-compose -f docker-compose-54.yml logs -f

# 查看特定服务
docker-compose -f docker-compose-54.yml logs -f zhurong-ems-backend

# 查看最近 100 行
docker-compose -f docker-compose-54.yml logs --tail=100
```

---

## 四、升级后验证清单

### 4.1 服务状态检查

- [ ] RabbitMQ 容器运行正常 (端口 5672)
- [ ] EMQX 容器运行正常 (端口 1883)
- [ ] TDengine 容器运行正常 (端口 6041)
- [ ] 后端容器运行正常 (端口 1088)
- [ ] 前端容器运行正常 (端口 3080)
- [ ] XXL-Job 容器运行正常 (端口 9110)
- [ ] Monitor 容器运行正常 (端口 8690)

### 4.2 功能验证

- [ ] 后端健康检查通过: `curl http://localhost:1088/autoee-iot-ems/actuator/health`
- [ ] 前端访问正常: `curl http://localhost:3080`
- [ ] EMQX Dashboard 可访问: `http://192.168.8.54:18083`
- [ ] RabbitMQ Dashboard 可访问: `http://192.168.8.54:15672`
- [ ] TDengine 连接正常: `docker exec zhurong-ems-tdengine taos -u root -s 'show databases;'`

### 4.3 日志检查

- [ ] 后端启动日志无错误
- [ ] TDengine 连接成功日志
- [ ] EMQX 连接成功日志
- [ ] 前端无错误日志

---

## 五、常见问题处理

### 5.1 服务启动失败

**问题**: 容器启动后立即退出

**排查步骤**:
```bash
# 查看容器日志
docker-compose -f docker-compose-54.yml logs <service-name>

# 检查容器退出原因
docker ps -a | grep <service-name>

# 检查配置语法
docker-compose -f docker-compose-54.yml config
```

### 5.2 端口冲突

**问题**: 端口已被占用

**排查步骤**:
```bash
# 检查端口占用
netstat -tuln | grep <port>

# 或使用 ss
ss -tuln | grep <port>

# 解决方案：修改 docker-compose-54.yml 中的端口映射
```

### 5.3 TDengine 连接失败

**问题**: 后端无法连接 TDengine

**排查步骤**:
```bash
# 1. 检查 TDengine 容器状态
docker ps | grep tdengine

# 2. 检查 TDengine 日志
docker-compose -f docker-compose-54.yml logs zhurong-ems-tdengine

# 3. 测试 TDengine 连接
docker exec zhurong-ems-tdengine taos -u root -s 'show databases;'

# 4. 检查后端日志中的 TDengine 连接错误
docker-compose -f docker-compose-54.yml logs zhurong-ems-backend | grep -i tdengine
```

### 5.4 EMQX 连接失败

**问题**: 后端无法连接 EMQX

**排查步骤**:
```bash
# 1. 检查 EMQX 容器状态
docker ps | grep emqx

# 2. 检查 EMQX 日志
docker-compose -f docker-compose-54.yml logs zhurong-ems-emqx

# 3. 测试 MQTT 端口
telnet localhost 1883

# 4. 检查 EMQX Dashboard
curl http://localhost:18083
```

---

## 六、回滚方案

### 6.1 回滚步骤

如果升级失败，执行回滚：

```bash
# 1. 停止当前服务
docker-compose -f docker-compose-54.yml down

# 2. 恢复备份配置
cp docker-compose-54.yml.backup.$(date +%Y%m%d) docker-compose-54.yml

# 3. 重启服务
docker-compose -f docker-compose-54.yml up -d

# 4. 验证回滚
docker-compose -f docker-compose-54.yml ps
```

### 6.2 数据保护

**重要数据卷**:
- `zhurong-ems-tdengine-data` - TDengine 数据
- `zhurong-ems-emqx-data` - EMQX 数据
- `zhurong-ems-rabbitmq-data` - RabbitMQ 数据

这些数据卷在回滚后不会丢失，因为使用了 Docker Volume 持久化。

---

## 七、升级文件清单

### 7.1 需要上传的文件

| 文件名 | 说明 | 位置 |
|--------|------|------|
| `docker-compose-54.yml` | 升级后的配置文件 | 本地 → 服务器 |
| `upgrade-54.sh` | 升级脚本 | 本地 → 服务器 |
| `verify-upgrade-54.sh` | 验证脚本 | 本地 → 服务器 |

### 7.2 上传到服务器的路径

建议上传到 54 服务器的 `/opt/deep-ems/` 目录：

```bash
scp docker-compose-54.yml user@192.168.8.54:/opt/deep-ems/
scp upgrade-54.sh user@192.168.8.54:/opt/deep-ems/
scp verify-upgrade-54.sh user@192.168.8.54:/opt/deep-ems/
```

---

## 八、升级后访问信息

### 8.1 服务访问地址

| 服务 | 地址 | 默认账号 |
|------|------|----------|
| 前端 | http://192.168.8.54:3080 | - |
| 后端 API | http://192.168.8.54:1088 | - |
| EMQX Dashboard | http://192.168.8.54:18083 | admin/public |
| RabbitMQ | http://192.168.8.54:15672 | guest/guest |
| XXL-Job | http://192.168.8.54:9110 | admin/123456 |
| Monitor | http://192.168.8.54:8690 | admin/zhurong123 |

### 8.2 Docker 网络

- 网络名: `zhurong-ems-network`
- 网络类型: `bridge`

### 8.3 数据卷

| 数据卷名 | 用途 |
|----------|------|
| `zhurong-ems-emqx-data` | EMQX 数据 |
| `zhurong-ems-rabbitmq-data` | RabbitMQ 数据 |
| `zhurong-ems-tdengine-data` | TDengine 数据 |
| `zhurong-ems-backend-logs` | 后端日志 |

---

## 九、技术支持

### 9.1 日志位置

```bash
# 所有服务日志
docker-compose -f docker-compose-54.yml logs -f

# 特定服务日志
docker logs zhurong-ems-backend
docker logs zhurong-ems-frontend
docker logs zhurong-ems-tdengine
```

### 9.2 常用命令

```bash
# 查看所有服务状态
docker-compose -f docker-compose-54.yml ps

# 停止所有服务
docker-compose -f docker-compose-54.yml down

# 启动所有服务
docker-compose -f docker-compose-54.yml up -d

# 重启特定服务
docker-compose -f docker-compose-54.yml restart zhurong-ems-backend

# 进入容器
docker exec -it zhurong-ems-backend /bin/bash

# 查看资源使用
docker stats
```

---

**文档版本**: v1.0
**创建日期**: 2026-04-30
**升级日期**: 待执行
**项目名称**: Deep-EMS 54 服务器环境升级
