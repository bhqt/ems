#!/bin/bash
# =============================================
# Deep-EMS 54 服务器升级验证脚本
# 日期: 2026-04-30
# =============================================

set -e

echo "============================================="
echo "Deep-EMS 54 服务器环境升级验证"
echo "============================================="

# 定义颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

# 计数器
PASS=0
FAIL=0

# 验证函数
check_service() {
    local service=$1
    local port=$2
    local name=$3

    echo -n "检查 $name ($service:$port)... "

    if docker ps | grep -q "$service"; then
        # 检查端口是否监听
        if netstat -tuln 2>/dev/null | grep -q ":$port " || ss -tuln 2>/dev/null | grep -q ":$port "; then
            echo -e "${GREEN}✓ 通过${NC}"
            ((PASS++))
            return 0
        else
            echo -e "${RED}✗ 失败 (端口未监听)${NC}"
            ((FAIL++))
            return 1
        fi
    else
        echo -e "${RED}✗ 失败 (容器未运行)${NC}"
        ((FAIL++))
        return 1
    fi
}

# 1. 验证容器状态
echo ""
echo "1. 验证容器状态"
echo "----------------------------------------------"

check_service "zhurong-ems-rabbitmq" "5672" "RabbitMQ"
check_service "zhurong-ems-emqx" "1883" "EMQX MQTT"
check_service "zhurong-ems-tdengine" "6041" "TDengine"
check_service "zhurong-ems-backend" "1088" "后端服务"
check_service "zhurong-ems-frontend" "3080" "前端服务"
check_service "zhurong-ems-xxl-job" "9110" "XXL-Job"
check_service "zhurong-ems-monitor" "8690" "Monitor"

# 2. 验证服务健康检查
echo ""
echo "2. 验证服务健康检查"
echo "----------------------------------------------"

echo -n "后端服务健康检查... "
if curl -sf http://localhost:1088/autoee-iot-ems/actuator/health > /dev/null 2>&1; then
    echo -e "${GREEN}✓ 通过${NC}"
    ((PASS++))
else
    echo -e "${RED}✗ 失败${NC}"
    ((FAIL++))
fi

echo -n "前端服务健康检查... "
if curl -sf http://localhost:3080 > /dev/null 2>&1; then
    echo -e "${GREEN}✓ 通过${NC}"
    ((PASS++))
else
    echo -e "${RED}✗ 失败${NC}"
    ((FAIL++))
fi

# 3. 验证 TDengine 连接
echo ""
echo "3. 验证 TDengine 配置"
echo "----------------------------------------------"

echo -n "TDengine 容器内连接测试... "
if docker exec zhurong-ems-tdengine taos -u root -s 'show databases;' > /dev/null 2>&1; then
    echo -e "${GREEN}✓ 通过${NC}"
    ((PASS++))
else
    echo -e "${RED}✗ 失败${NC}"
    ((FAIL++))
fi

# 4. 验证 RabbitMQ
echo ""
echo "4. 验证 RabbitMQ"
echo "----------------------------------------------"

echo -n "RabbitMQ 管理界面... "
if curl -sf http://localhost:15672 > /dev/null 2>&1; then
    echo -e "${GREEN}✓ 通过${NC}"
    ((PASS++))
else
    echo -e "${RED}✗ 失败${NC}"
    ((FAIL++))
fi

# 5. 验证 EMQX
echo ""
echo "5. 验证 EMQX"
echo "----------------------------------------------"

echo -n "EMQX Dashboard... "
if curl -sf http://localhost:18083 > /dev/null 2>&1; then
    echo -e "${GREEN}✓ 通过${NC}"
    ((PASS++))
else
    echo -e "${RED}✗ 失败${NC}"
    ((FAIL++))
fi

# 6. 查看日志
echo ""
echo "6. 最近日志"
echo "----------------------------------------------"
docker-compose -f docker-compose-54.yml logs --tail=10

# 7. 汇总结果
echo ""
echo "============================================="
echo "验证结果汇总"
echo "============================================="
echo -e "通过: ${GREEN}$PASS${NC}"
echo -e "失败: ${RED}$FAIL${NC}"
echo ""

if [ $FAIL -eq 0 ]; then
    echo -e "${GREEN}✓ 所有验证通过！升级成功！${NC}"
    exit 0
else
    echo -e "${RED}✗ 有 $FAIL 项验证失败，请检查${NC}"
    exit 1
fi
