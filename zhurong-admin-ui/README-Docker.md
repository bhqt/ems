# 前端 Docker 部署说明

## 环境变量配置

前端在构建时需要配置以下环境变量（通过 `.env.production` 文件或构建时传入）：

- `VUE_APP_BASE_API`: API基础路径，例如 `/autoee-iot-ems/` 或 `/dev-api/`
- `VUE_APP_CONTEXT_PATH`: 前端路由base路径，通常为空字符串或 `/`

## nginx.conf 配置说明

`nginx.conf` 文件配置了：

1. **静态文件服务**：提供前端构建后的静态资源
2. **API反向代理**：将 `/autoee-iot-ems/` 路径的请求代理到后端服务 `zhurong-ems-backend:8088`
3. **Vue Router History模式支持**：使用 `try_files` 确保前端路由正常工作

## 自定义API路径

如果前端使用的API路径不是 `/autoee-iot-ems/`（例如 `/dev-api/` 或 `/prod-api/`），需要：

1. 在 `nginx.conf` 中添加对应的 `location` 配置
2. 确保构建时 `VUE_APP_BASE_API` 环境变量与nginx配置一致
3. 代理时路径重写，将前端API路径映射到后端 `context-path`

## 示例配置

### 场景1：前端API路径 = 后端context-path

如果 `VUE_APP_BASE_API=/autoee-iot-ems/`：

```nginx
location /autoee-iot-ems/ {
    proxy_pass http://zhurong-ems-backend:8088;
}
```

### 场景2：前端API路径 ≠ 后端context-path

如果 `VUE_APP_BASE_API=/dev-api/`，后端 `context-path=/autoee-iot-ems`：

```nginx
location /dev-api/ {
    proxy_pass http://zhurong-ems-backend:8088/autoee-iot-ems/;
}
```

