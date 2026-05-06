@echo off
chcp 65001 >nul
echo ╔══════════════════════════════════════════════════════════╗
echo ║       MQTT 设备模拟工具 - 祝融能源管理系统              ║
echo ╚══════════════════════════════════════════════════════════╝
echo.

REM 检查 Python 是否安装
python --version >nul 2>&1
if errorlevel 1 (
    echo ✗ 错误：未检测到 Python，请先安装 Python 3.8+
    echo 下载地址：https://www.python.org/downloads/
    pause
    exit /b 1
)

echo ✓ Python 已安装
echo.

REM 检查依赖是否安装
echo 正在检查依赖...
pip show paho-mqtt >nul 2>&1
if errorlevel 1 (
    echo 正在安装依赖...
    pip install -r requirements.txt
    if errorlevel 1 (
        echo ✗ 依赖安装失败
        pause
        exit /b 1
    )
) else (
    echo ✓ 依赖已安装
)

echo.
echo ═══════════════════════════════════════════════════════════
echo 可用命令:
echo ═══════════════════════════════════════════════════════════
echo.
echo   python main.py --help          显示帮助信息
echo   python main.py send --help     发送消息帮助
echo   python main.py stress --help   压力测试帮助
echo   python main.py topics          查看支持的主题
echo   python main.py templates       查看消息模板
echo   python main.py ping            测试 MQTT 连接
echo   python main.py validate        验证配置
echo.
echo ═══════════════════════════════════════════════════════════
echo 快速示例:
echo ═══════════════════════════════════════════════════════════
echo.
echo   发送电压数据:
echo   python main.py send -t electric/voltage -cid device_001 -tpl voltage
echo.
echo   轻量压力测试:
echo   python main.py stress -s light
echo.
echo ═══════════════════════════════════════════════════════════
echo.

REM 如果有参数则执行，否则显示帮助
if "%~1"=="" (
    python main.py --help
) else (
    python main.py %*
)

pause
