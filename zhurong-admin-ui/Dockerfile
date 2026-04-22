# 多阶段构建：构建阶段
# 使用 Node.js 镜像进行前端构建
FROM node:20-bookworm-slim AS build

# 设置工作目录
WORKDIR /build

# 构建参数：API基础路径
# 默认使用后端context-path：/autoee-iot-ems/
# 可以通过 --build-arg VUE_APP_BASE_API=/dev-api/ 来覆盖
ARG VUE_APP_BASE_API=/autoee-iot-ems/
ARG VUE_APP_CONTEXT_PATH=/
ARG VUE_APP_TITLE=智碳能源管理系统

# 设置为环境变量（构建时使用）
ENV VUE_APP_BASE_API=${VUE_APP_BASE_API}
ENV VUE_APP_CONTEXT_PATH=${VUE_APP_CONTEXT_PATH}
ENV VUE_APP_TITLE=${VUE_APP_TITLE}

# 安装依赖（利用Docker缓存，如果package.json未变化，将复用此层）
COPY package*.json ./
RUN npm config set registry https://registry.npmmirror.com \
    && npm install --legacy-peer-deps

# 复制源码
COPY . .

# 构建生产版本
# 构建时会使用上面的环境变量
# 添加 NODE_OPTIONS 环境变量解决 OpenSSL 3.0 兼容性问题
RUN NODE_OPTIONS=--openssl-legacy-provider npm run build:prod

# 运行阶段
# 使用 Nginx 镜像提供静态文件服务
FROM nginx:1.28.3-alpine

# 维护者信息
LABEL maintainer="cp_energy"

# 复制构建产物到nginx的html目录
COPY --from=build /build/dist /usr/share/nginx/html

# 复制nginx配置文件
# 配置文件包含反向代理到后端的设置
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 创建必要的目录
RUN mkdir -p /var/log/nginx

# 暴露端口
EXPOSE 80

# 启动nginx
# nginx在容器中以daemon模式运行，需要使用前台模式
CMD ["nginx", "-g", "daemon off;"]

