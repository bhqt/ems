# AGENTS.md - 项目开发指南

## 项目概述

- **项目名称**: (智慧能源管理系统)
- **版本**: 4.6.0
- **技术栈**: Spring Boot 2.7.9 + MyBatis-Plus + Vue 2 + Element UI
- **JDK 要求**: JDK 1.8（**强制要求**，不兼容 JDK 17+）

## 本地开发环境

### 中间件（Docker）

```bash
# 启动所有中间件
docker compose up -d

# 涉及容器：shared-mysql, shared-redis, shared-rabbitmq, shared-emqx, shared-tdengine
```

### 后端启动

```bash
# ⚠️ 重要：如果使用 JDK 17+，必须添加 --add-opens 参数
# 详见 docs/部署指南/打包部署常见问题.md 第十一章

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
     --server.port=8088 \
     --server.servlet.context-path=/autoee-iot-ems \
     --spring.datasource.dynamic.datasource.td.enabled=false
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
