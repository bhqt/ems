# Docker 容器化配置说明

## 配置概述

项目已配置完整的 Docker 容器化方案，包括：

1. **中间件服务**：MySQL、Redis、RabbitMQ（TDengine可选）
2. **后端服务**：Spring Boot 应用
3. **前端服务**：Vue.js 应用（通过 Nginx 提供静态文件服务和 API 代理）

## 核心配置说明

### 1. 前端 Nginx 配置

**文件位置**：`autoee-admin-ui/nginx.conf`

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

**文件位置**：`docker-compose.full.yml`

后端通过环境变量连接容器化的中间件服务：

- **MySQL**：`zhurong-ems-mysql:3306`
- **Redis**：`zhurong-ems-redis:6379`
- **RabbitMQ**：`zhurong-ems-rabbitmq:5672`

**环境变量格式**：
Spring Boot 使用大写+下划线的格式，例如：
- `SPRING_DATASOURCE_DYNAMIC_DATASOURCE_MASTER_URL`
- `SPRING_REDIS_HOST`
- `SPRING_RABBITMQ_HOST`

### 3. 前端构建配置

**文件位置**：`autoee-admin-ui/Dockerfile`

前端构建时通过 ARG 参数设置环境变量：

```dockerfile
ARG VUE_APP_BASE_API=/autoee-iot-ems/
ARG VUE_APP_CONTEXT_PATH=/
ENV VUE_APP_BASE_API=${VUE_APP_BASE_API}
ENV VUE_APP_CONTEXT_PATH=${VUE_APP_CONTEXT_PATH}
```

在 `docker-compose.full.yml` 中通过 build args 传入：

```yaml
build:
  args:
    VUE_APP_BASE_API: /autoee-iot-ems/
    VUE_APP_CONTEXT_PATH: /
```

## 部署方式

### 方式1：仅部署中间件

```bash
docker-compose up -d
```

### 方式2：全量部署（中间件 + 后端 + 前端）

```bash
docker-compose -f docker-compose.full.yml up -d
```

## 服务访问地址

部署后，各服务访问地址：

- **前端页面**：http://localhost
- **后端API**：http://localhost:8088/autoee-iot-ems
- **MySQL**：localhost:3306
- **Redis**：localhost:6379
- **RabbitMQ管理界面**：http://localhost:15672（guest/guest）
- **后端健康检查**：http://localhost:8088/autoee-iot-ems/actuator/health

## 配置修改说明

### 修改前端API路径

如果需要修改前端API路径（例如改为 `/dev-api/`），需要修改以下文件：

1. **docker-compose.full.yml**：
   ```yaml
   build:
     args:
       VUE_APP_BASE_API: /dev-api/  # 修改这里
   ```

2. **autoee-admin-ui/nginx.conf**：
   ```nginx
   location /dev-api/ {  # 添加或修改这个location
       proxy_pass http://zhurong-ems-backend:8088/autoee-iot-ems/;
   }
   ```

3. **重新构建前端镜像**：
   ```bash
   docker-compose -f docker-compose.full.yml build zhurong-ems-frontend
   ```

### 修改后端中间件连接

修改 `docker-compose.full.yml` 中后端服务的环境变量：

```yaml
environment:
  - SPRING_REDIS_HOST=新的redis服务地址
  - SPRING_DATASOURCE_DYNAMIC_DATASOURCE_MASTER_URL=jdbc:mysql://新的mysql地址:3306/...
```

## 注意事项

1. **端口冲突**：确保本地端口 80、8088、3306、6379、5672、15672 未被占用
2. **数据持久化**：所有中间件数据都通过 Docker Volume 持久化
3. **网络配置**：所有服务在同一 Docker 网络 `zhurong-ems-network` 中，可通过服务名互相访问
4. **健康检查**：后端服务会等待中间件服务健康检查通过后才启动
5. **前端构建**：前端构建时会自动使用 Dockerfile 中设置的环境变量

## 故障排查

### 前端无法访问后端

1. 检查 nginx.conf 中的 proxy_pass 地址是否正确
2. 检查前端构建时的 VUE_APP_BASE_API 是否与 nginx 配置一致
3. 检查后端服务是否正常运行：`docker-compose logs zhurong-ems-backend`
4. 检查网络连接：`docker-compose exec zhurong-ems-frontend ping zhurong-ems-backend`

### 后端无法连接中间件

1. 检查环境变量配置是否正确
2. 检查中间件服务是否正常运行：`docker-compose ps`
3. 检查网络连接：`docker-compose exec zhurong-ems-backend ping zhurong-ems-mysql`
4. 查看后端日志：`docker-compose logs zhurong-ems-backend`

