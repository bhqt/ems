# =============================================================================
# 多阶段构建 Dockerfile
# =============================================================================

# ---------- 构建阶段 ----------
FROM node:18-alpine AS builder

# 设置工作目录
WORKDIR /app

# 安装 pnpm
RUN npm install -g pnpm@9

# 复制 package 文件
COPY package.json pnpm-lock.yaml ./

# 安装依赖 (利用缓存)
RUN pnpm install --frozen-lockfile

# 复制源代码
COPY . .

# 构建生产版本
RUN pnpm build:prod

# ---------- 生产阶段 ----------
FROM nginx:alpine AS production

# 复制构建产物
COPY --from=builder /app/dist /usr/share/nginx/html

# 复制 nginx 配置
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 暴露端口
EXPOSE 80

# 启动 nginx
CMD ["nginx", "-g", "daemon off;"]