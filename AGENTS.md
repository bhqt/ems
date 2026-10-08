# AGENTS.md - 项目开发指南

## 项目概述

- **项目名称**: (智慧能源管理系统)
- **版本**: 4.6.0
- **技术栈**: Spring Boot 2.7.9 + MyBatis-Plus + Vue 2 + Element UI
- **JDK 要求**: JDK 1.8（**强制要求**，不兼容 JDK 17+）
- **本地后端端口**: `18088`（⚠️ 不能使用 8088，见下方"端口保留段"经验）

## 本地开发环境

### 中间件（Docker）

```bash
# 启动所有中间件
docker compose up -d

# 涉及容器：shared-mysql, shared-redis, shared-rabbitmq, shared-emqx, shared-tdengine
# ⚠️ shared-rabbitmq 必须暴露 5672/15672（用户 rabbitmq/rabbitmqpassword，vhost=admin_vhost）
# ⚠️ shared-emqx 必须暴露 1883/8083/8081/18083（账号 admin/public）
```

### 后端启动

```bash
# ⚠️ 重要：如果使用 JDK 17+，必须添加 --add-opens 参数
# 详见 docs/部署指南/打包部署常见问题.md 第十一章
# 已封装脚本：tmp-jar-fix/start_backend_capture.bat（输出日志到 backend_run.log）

java --add-opens java.base/java.lang=ALL-UNNAMED \
     --add-opens java.base/java.lang.reflect=ALL-UNNAMED \
     --add-opens java.base/java.math=ALL-UNNAMED \
     --add-opens java.base/java.util=ALL-UNNAMED \
     --add-opens java.base/java.util.concurrent=ALL-UNNAMED \
     --add-opens java.base/java.net=ALL-UNNAMED \
     --add-opens java.base/java.io=ALL-UNNAMED \
     --add-opens java.base/java.nio=ALL-UNNAMED \
     --add-opens java.base/sun.nio.ch=ALL-UNNAMED \
     --add-opens java.base/sun.security.ssl=ALL-UNNAMED \
     -jar zhurong-ems-admin/target/zhurong-ems-admin.jar \
     --spring.profiles.active=local \
     --server.port=18088 \
     --server.servlet.context-path=/autoee-iot-ems \
     --spring.datasource.dynamic.datasource.td.enabled=false
# 访问 http://localhost:18088/autoee-iot-ems/actuator/health 应返回 {"status":"UP"}
```

### 前端启动

```bash
cd zhurong-admin-ui
npm install
npm run dev
# 访问 http://localhost:9029
```

## 已知问题与经验教训

### 1. JDK 兼容性（高频问题）

**症状**: 所有页面报"系统内部错误"，API 返回 500 + `InaccessibleObjectException`

**原因**: JDK 9+ 模块系统禁止 CGLIB 反射访问 `java.lang.ClassLoader`

**解决**: 启动时添加 `--add-opens` 参数（见上方启动命令）

### 2. 数据库表缺失

**症状**: 特定模块报 500

**排查**: 检查 `.dev/dumps/ems_full.sql` 中是否有对应的建表语句

**解决**: 在 MySQL 中执行缺失表的 DDL

### 3. MyBatis-Plus 逻辑删除

- `del_flag`: `0` = 存在, `2` = 删除
- 实体类中使用 `@TableLogic` 注解

### 4. 主键策略

- 使用雪花算法 (`ASSIGN_ID`)
- 实体类中 `@TableId(value = "xxx_id")`

### 5. Windows 端口保留段（本机发原本地开发）

**症状**: 后端反复报 `Port 8088 was already in use` / `APPLICATION FAILED TO START`，但 `netstat -ano | findstr :8088` 完全无监听；PowerShell 测试绑定报 `An attempt was made to access a socket in a way forbidden by its access permissions`。

**原因**: Hyper-V / Docker Desktop 会把一段 TCP 端口动态保留（本机当前保留段包含 `8061-8160`，8088 恰在其中），导致任何进程都无法绑定该端口。

**排查**: `netsh interface ipv4 show excludedportrange protocol=tcp` 查看保留段；用 PowerShell `TcpListener` 批量测试端口绑定。

**解决**: 本地后端端口改用 `18088`（已实测可绑定），前端 `.env.development` 的 `VUE_APP_BASE_API` 与 `vue.config.js` proxy target 同步改为 `http://localhost:18088/autoee-iot-ems`。生产/容器内 8088 不受影响（容器内不占用宿主保留段）。

### 6. 共享中间件容器必须暴露端口

**症状**: 后端日志日志不断报 `ConnectException: Connection refused`。

**原因**: 经 `docker compose up -d` 拉起的 `shared-*` 容器可能缺少宿主端口映射。

**解决**（Docker Desktop 下重建容器并加 `-p` 映射）:
- `shared-redis`: `-p 6379:6379`，且必须带 `--requirepass difyai123456`（在容器内 `--requirepass` 参数用 `redis-server --requirepass difyai123456` 或 `command` 覆盖）。
- `shared-rabbitmq`: `-p 5672:5672 -p 15672:15672`，用户 `rabbitmq / rabbitmqpassword`，vhost `admin_vhost`（definitions.json 挂载到 `/etc/rabbitmq/definitions.json`，rabbitmq.conf 配 `management.load_definitions`）。绑定挂载源在 Docker Desktop 下不可直接复用宿主绝对路径，需先从原容器 `docker cp` 出配置文件再用正斜杠绝对路径挂载。
- `shared-emqx`: `-p 1883:1883 -p 8083:8083 -p 8081:8081 -p 18083:18083`（账号 admin/public）。

### 7. 启动后端可靠方式

- 直接用改好的 `tmp-jar-fix\start_backend_capture.bat`（已含 `--add-opens` 和 18088 端口）。用 `Start-Process -FilePath "cmd.exe" -ArgumentList "/c <bat路径>" -WindowStyle Hidden` 拉起，日志写入 `tmp-jar-fix\backend_run.log`。
- ⚠️ 不要用 PowerShell `Start-Process -RedirectStandardOutput/-RedirectStandardError` 且无 `-WindowStyle Hidden` 的方式启动长驻 java：可能导致 `ChildProcess.kill` 报错且残留进程占端口，引发后续"端口已被占用"。启动前先 `Get-CimInstance Win32_Process -Filter "Name='java.exe'" | ? {$_.CommandLine -match 'zhurong-ems-admin'}` 清理旧实例。

## 项目结构

```
zhurong-ems-admin/          # Controller 层
zhurong-ems-system/         # Domain / Service / Mapper 层
zhurong-ems-framework/      # 拦截器、异常处理、配置
zhurong-ems-common/         # 公共工具、异常定义
zhurong-admin-ui/           # Vue 2 前端
```

## API 路径约定

- 前缀: `/system/xxx`
- 列表: `GET /system/xxx/list`
- 详情: `GET /system/xxx/{id}`
- 新增: `POST /system/xxx`
- 修改: `PUT /system/xxx`
- 删除: `DELETE /system/xxx/{id}`

## 数据库配置

- 数据库名: `autoee_ems`
- 地址: `localhost:3306`（本地）/ `shared-mysql:3306`（Docker）
- 用户: `root` / 密码: `123456`

## Redis 配置

- 地址: `localhost:6379`
- 密码: `difyai123456`

> 注意: Docker 中的 `shared-redis` 容器必须带 `--requirepass difyai123456` 启动，否则后端认证会报错。

## 登录账号

- 用户名: `admin`
- 密码: `123456`
