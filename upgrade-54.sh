#!/bin/bash
# =============================================
# Deep-EMS 54 服务器升级脚本
# 日期: 2026-04-30
# =============================================

set -e  # 遇到错误立即退出

echo "============================================="
echo "Deep-EMS 54 服务器环境升级脚本"
echo "============================================="

# 定义颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 定义日志函数
log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 检查是否为 root 用户
if [ "$EUID" -ne 0 ]; then
    log_warn "请使用 sudo 或 root 权限运行此脚本"
fi

# 1. 备份当前配置
log_info "步骤 1: 备份当前配置..."
BACKUP_FILE="docker-compose-54.yml.backup.$(date +%Y%m%d_%H%M%S)"
if [ -f "docker-compose-54.yml" ]; then
    cp docker-compose-54.yml "$BACKUP_FILE"
    log_info "备份已创建: $BACKUP_FILE"
else
    log_error "未找到 docker-compose-54.yml 文件"
    exit 1
fi

# 2. 检查 docker 和 docker-compose
log_info "步骤 2: 检查 Docker 环境..."
if ! command -v docker &> /dev/null; then
    log_error "Docker 未安装"
    exit 1
fi
log_info "Docker 版本: $(docker --version)"

if ! command -v docker-compose &> /dev/null; then
    log_error "docker-compose 未安装"
    exit 1
fi
log_info "docker-compose 版本: $(docker-compose --version)"

# 3. 查看当前服务状态
log_info "步骤 3: 查看当前服务状态..."
docker-compose -f docker-compose-54.yml ps

# 4. 停止当前服务
log_info "步骤 4: 停止当前服务..."
docker-compose -f docker-compose-54.yml down

# 5. 拉取最新镜像
log_info "步骤 5: 拉取最新镜像..."
log_info "注意: 确保镜像已推送到镜像仓库"
# docker-compose -f docker-compose-54.yml pull

# 6. 启动新服务
log_info "步骤 6: 启动新服务..."
docker-compose -f docker-compose-54.yml up -d

# 7. 等待服务启动
log_info "步骤 7: 等待服务启动 (30秒)..."
sleep 30

# 8. 查看服务状态
log_info "步骤 8: 查看服务状态..."
docker-compose -f docker-compose-54.yml ps

# 9. 查看日志
log_info "步骤 9: 查看服务日志 (最近 50 行)..."
docker-compose -f docker-compose-54.yml logs --tail=50

echo ""
echo "============================================="
log_info "升级完成！"
echo "============================================="
echo ""
echo "访问地址:"
echo "  - 前端: http://192.168.8.54:3080"
echo "  - 后端: http://192.168.8.54:1088"
echo "  - EMQX Dashboard: http://192.168.8.54:18083"
echo "  - RabbitMQ: http://192.168.8.54:15672"
echo "  - XXL-Job: http://192.168.8.54:9110"
echo "  - Monitor: http://192.168.8.54:8690"
echo ""
echo "如需查看实时日志，请运行:"
echo "  docker-compose -f docker-compose-54.yml logs -f"
echo ""
