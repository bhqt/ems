#!/bin/sh

# TDengine 容器启动脚本
# 等待 TDengine 服务启动
sleep 10

# 执行初始化脚本
if [ -f /docker-entrypoint-initdb.d/init.sql ]; then
    echo "正在执行 TDengine 初始化脚本..."
    taos -s "source /docker-entrypoint-initdb.d/init.sql"
    echo "TDengine 初始化完成"
fi

# 启动 TDengine 服务
exec /usr/bin/taosd