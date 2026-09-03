好的，我来为您创建一个详细的 TDengine 手动初始化操作手册：

```markdown
# TDengine 手动初始化操作手册

## 一、概述

本文档描述如何在 Docker 环境中手动初始化 TDengine 时序数据库，包括创建数据库、用户和授权操作。

**适用场景**:
- 首次部署 TDengine 时需要手动初始化
- 初始化脚本执行失败时进行手动修复
- 需要重置数据库时重新初始化

---

## 二、前置条件

### 2.1 环境要求

| 项目 | 说明 |
|------|------|
| Docker | 已安装并运行 |
| TDengine 容器 | 已启动并运行 |
| 容器名称 | `zhurong-ems-tdengine` |
| 网络 | 容器可通过 `docker exec` 访问 |

### 2.2 检查容器状态

```bash
# 查看 TDengine 容器状态
docker ps | grep zhurong-ems-tdengine
```

**预期输出**:
```
CONTAINER ID   IMAGE                      COMMAND                  CREATED        STATUS        PORTS                              NAMES
abc123456789   tdengine/tdengine:3.2.0.0   "/usr/bin/taosd"         10 minutes ago Up 10 minutes 0.0.0.0:6030->6030/tcp, 0.0.0.0:6041->6041/tcp   zhurong-ems-tdengine
```

---

## 三、初始化步骤

### 3.1 创建数据库

```bash
# 进入 TDengine 容器执行 SQL
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "CREATE DATABASE IF NOT EXISTS energy;"
```

**参数说明**:
- `-u root`: 使用 root 用户
- `-pdifyai123456`: 密码（**注意：-p 和密码之间无空格**）
- `-s "SQL"`: 执行单条 SQL 语句

**预期输出**:
```
Welcome to the TDengine Command Line Interface, Client Version:3.2.0.0
Copyright (c) 2022 by TDengine, all rights reserved.

Query OK, 0 row(s) in set (0.003123s)
```

### 3.2 创建用户

```bash
# 创建 energy 用户（TDengine 3.x 语法）
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "CREATE USER energy PASS 'difyai123456';"
```

**注意**: TDengine 3.x 不支持 `IF NOT EXISTS` 和 `WITH PASSWORD` 语法

**预期输出**:
```
Welcome to the TDengine Command Line Interface, Client Version:3.2.0.0
Copyright (c) 2022 by TDengine, all rights reserved.

Query OK, 0 row(s) in set (0.001234s)
```

### 3.3 授权用户

```bash
# 授权 energy 用户访问 energy 数据库
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "GRANT ALL ON energy TO energy;"
```

**预期输出**:
```
Welcome to the TDengine Command Line Interface, Client Version:3.2.0.0
Copyright (c) 2022 by TDengine, all rights reserved.

Query OK, 0 row(s) in set (0.000987s)
```

---

## 四、验证步骤

### 4.1 验证数据库创建

```bash
# 查看所有数据库
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "SHOW DATABASES;"
```

**预期输出**:
```
Welcome to the TDengine Command Line Interface, Client Version:3.2.0.0
Copyright (c) 2022 by TDengine, all rights reserved.

              name              |
=================================
 information_schema             |
 energy                         |
Query OK, 2 row(s) in set (0.001234s)
```

### 4.2 验证用户创建

```bash
# 查看所有用户
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "SHOW USERS;"
```

**预期输出**:
```
Welcome to the TDengine Command Line Interface, Client Version:3.2.0.0
Copyright (c) 2022 by TDengine, all rights reserved.

  username   |
==============
 root        |
 energy      |
Query OK, 2 row(s) in set (0.000876s)
```

### 4.3 验证用户授权

```bash
# 使用 energy 用户登录并访问数据库
docker exec zhurong-ems-tdengine taos -u energy -pdifyai123456 -s "USE energy; SHOW TABLES;"
```

**预期输出**:
```
Welcome to the TDengine Command Line Interface, Client Version:3.2.0.0
Copyright (c) 2022 by TDengine, all rights reserved.

taos> USE energy; SHOW TABLES;
Database changed.

Query OK, 0 row(s) in set (0.002205s)
```

### 4.4 验证后端连接

```bash
# 检查后端服务状态
docker-compose -f docker-compose-54.yml ps zhurong-ems-backend

# 检查后端健康状态
curl http://localhost:1088/autoee-iot-ems/actuator/health

# 查看后端日志中的 TDengine 连接信息
docker-compose -f docker-compose-54.yml logs zhurong-ems-backend | grep -i tdengine
```

---

## 五、完整初始化脚本

```bash
#!/bin/bash
# TDengine 手动初始化脚本

echo "============================================="
echo "TDengine 手动初始化"
echo "============================================="

# 1. 创建数据库
echo "1. 创建 energy 数据库..."
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "CREATE DATABASE IF NOT EXISTS energy;"

# 2. 创建用户
echo "2. 创建 energy 用户..."
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "CREATE USER energy PASS 'difyai123456';"

# 3. 授权
echo "3. 授权用户..."
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "GRANT ALL ON energy TO energy;"

# 4. 验证
echo ""
echo "4. 验证结果..."
echo "--- 数据库列表 ---"
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "SHOW DATABASES;"

echo ""
echo "--- 用户列表 ---"
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "SHOW USERS;"

echo ""
echo "--- 用户登录验证 ---"
docker exec zhurong-ems-tdengine taos -u energy -pdifyai123456 -s "USE energy; SHOW TABLES;"

echo ""
echo "============================================="
echo "TDengine 初始化完成！"
echo "============================================="
```

---

## 六、常见问题处理

### 6.1 认证失败

**错误信息**:
```
failed to connect to server, reason: Authentication failure
```

**原因**: 密码错误或用户不存在

**解决方案**:
```bash
# 检查容器环境变量中的密码
docker exec zhurong-ems-tdengine env | grep TAOS_PASS

# 使用正确的密码登录
docker exec zhurong-ems-tdengine taos -u root -p<正确密码>
```

### 6.2 命令参数错误

**错误信息**:
```
taos: Too many arguments
```

**原因**: `-p` 和密码之间有空格

**错误用法**: `-p difyai123456`  
**正确用法**: `-pdifyai123456`

### 6.3 用户已存在

**错误信息**:
```
DB error: User already exists
```

**原因**: 用户已创建，不需要重复创建

**解决方案**: 跳过创建用户步骤，直接执行授权

### 6.4 数据库已存在

**错误信息**:
```
DB error: Database already exists
```

**原因**: 数据库已创建，不需要重复创建

**解决方案**: 使用 `IF NOT EXISTS` 语法避免报错

---

## 七、命令速查表

| 操作 | 命令 |
|------|------|
| 创建数据库 | `docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "CREATE DATABASE IF NOT EXISTS energy;"` |
| 创建用户 | `docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "CREATE USER energy PASS 'difyai123456';"` |
| 授权用户 | `docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "GRANT ALL ON energy TO energy;"` |
| 查看数据库 | `docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "SHOW DATABASES;"` |
| 查看用户 | `docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "SHOW USERS;"` |
| 测试连接 | `docker exec zhurong-ems-tdengine taos -u energy -pdifyai123456 -s "USE energy;"` |

---

## 八、注意事项

### 8.1 密码安全

- TDengine 默认 root 用户密码为环境变量 `TAOS_PASS` 的值
- 生产环境应使用强密码
- 密码不应包含特殊字符

### 8.2 初始化时机

- 数据库和用户只需创建一次
- 如果数据卷已存在，不会自动重新初始化
- 需要重新初始化时，需先删除数据卷

### 8.3 删除数据卷（重置数据库）

```bash
# 停止容器
docker-compose -f docker-compose-54.yml down

# 删除数据卷
docker volume rm zhurong-ems-tdengine-data

# 重新启动（会自动执行初始化脚本）
docker-compose -f docker-compose-54.yml up -d
```

---

# ./docker/tdengine/init.sql:/docker-entrypoint-initdb.d/init.sql:ro  # 添加这行客户端连接端口 这行，好像没起作用
我来帮您分析一下这个问题。初始化脚本挂载可能没有生效的原因有几个，让我帮您排查：

## 🔍 问题分析

### 1. 检查当前配置

```bash
# 查看 docker-compose.full.yml 中的 TDengine 配置
grep -A 20 "zhurong-ems-tdengine:" docker-compose.full.yml
```

### 2. 检查数据卷状态

**问题原因**: TDengine 的初始化脚本只在**数据卷首次创建时**执行一次。如果数据卷已存在，脚本不会重新执行。

```bash
# 查看数据卷状态
docker volume ls | grep tdengine

# 查看数据卷详情
docker volume inspect zhurong-ems-tdengine-data
```

### 3. 检查脚本内容

```bash
# 查看当前 init.sql 内容
cat docker/tdengine/init.sql
```

---

## ✅ 解决方案

### 方案一：删除数据卷重新初始化

```bash
# 停止服务
docker-compose -f docker-compose.full.yml down

# 删除旧数据卷（这会清除所有数据！）
docker volume rm zhurong-ems-tdengine-data

# 重新启动（会自动执行初始化脚本）
docker-compose -f docker-compose.full.yml up -d

# 等待初始化完成（约10秒）
sleep 15

# 验证
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "SHOW DATABASES;"
```

### 方案二：手动执行初始化（如果不想删除数据）

```bash
# 直接在容器中执行 SQL
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "CREATE DATABASE IF NOT EXISTS energy;"
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "CREATE USER energy PASS 'difyai123456';"
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "GRANT ALL ON energy TO energy;"
```

### 方案三：检查脚本执行日志

```bash
# 查看 TDengine 容器日志
docker-compose -f docker-compose.full.yml logs zhurong-ems-tdengine

# 或查看容器启动日志
docker logs zhurong-ems-tdengine
```

---

## 📋 验证步骤

```bash
# 1. 检查数据库是否创建
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "SHOW DATABASES;"

# 2. 检查用户是否创建
docker exec zhurong-ems-tdengine taos -u root -pdifyai123456 -s "SHOW USERS;"

# 3. 测试用户登录
docker exec zhurong-ems-tdengine taos -u energy -pdifyai123456 -s "USE energy;"
```

---

## 🚀 推荐操作

**如果是首次部署或可以清除数据**，推荐使用 **方案一**，让初始化脚本自动执行。

**如果数据需要保留**，使用 **方案二** 手动执行初始化命令。

请告诉我您想选择哪种方案，或者需要我帮您检查日志？
