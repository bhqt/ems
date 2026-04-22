# Deep-EMS Docker 构建脚本
# 用于构建所有服务的 Docker 镜像

param(
    [switch]$All,          # 构建所有镜像
    [switch]$Backend,      # 只构建后端
    [switch]$Frontend,     # 只构建前端
    [switch]$XxlJob,       # 只构建 xxl-job
    [switch]$Monitor,       # 只构建 monitor
    [switch]$Clean,        # 清理构建缓存
    [string]$Registry = "zhurong-ems"  # 镜像仓库前缀
)

$ErrorActionPreference = "Stop"
$ProjectRoot = $PSScriptRoot

function Write-Step {
    param([string]$Message)
    Write-Host "`n========================================" -ForegroundColor Cyan
    Write-Host $Message -ForegroundColor Cyan
    Write-Host "========================================`n" -ForegroundColor Cyan
}

function Build-Backend {
    Write-Step "构建后端服务镜像 (zhurong-ems-admin)"

    $BackendDir = Join-Path $ProjectRoot "zhurong-ems-admin"
    Push-Location $BackendDir

    try {
        $Tag = "$Registry/backend:latest"
        docker build -t $Tag .

        if ($LASTEXITCODE -eq 0) {
            Write-Host "后端镜像构建成功: $Tag" -ForegroundColor Green
        } else {
            Write-Host "后端镜像构建失败!" -ForegroundColor Red
            exit 1
        }
    }
    finally {
        Pop-Location
    }
}

function Build-Frontend {
    Write-Step "构建前端服务镜像 (zhurong-admin-ui)"

    $FrontendDir = Join-Path $ProjectRoot "zhurong-admin-ui"
    Push-Location $FrontendDir

    try {
        $Tag = "$Registry/frontend:latest"
        docker build -t $Tag .

        if ($LASTEXITCODE -eq 0) {
            Write-Host "前端镜像构建成功: $Tag" -ForegroundColor Green
        } else {
            Write-Host "前端镜像构建失败!" -ForegroundColor Red
            exit 1
        }
    }
    finally {
        Pop-Location
    }
}

function Build-XxlJob {
    Write-Step "构建 XXL-Job 管理服务镜像"

    $XxlJobDir = Join-Path $ProjectRoot "zhurong-ems-extend\zhurong-ems-xxl-job-admin"
    Push-Location $XxlJobDir

    try {
        $Tag = "$Registry/xxl-job:latest"
        docker build -t $Tag .

        if ($LASTEXITCODE -eq 0) {
            Write-Host "XXL-Job 镜像构建成功: $Tag" -ForegroundColor Green
        } else {
            Write-Host "XXL-Job 镜像构建失败!" -ForegroundColor Red
            exit 1
        }
    }
    finally {
        Pop-Location
    }
}

function Build-Monitor {
    Write-Step "构建 Monitor 监控服务镜像"

    $MonitorDir = Join-Path $ProjectRoot "zhurong-ems-extend\zhurong-ems-monitor-admin"
    Push-Location $MonitorDir

    try {
        $Tag = "$Registry/monitor:latest"
        docker build -t $Tag .

        if ($LASTEXITCODE -eq 0) {
            Write-Host "Monitor 镜像构建成功: $Tag" -ForegroundColor Green
        } else {
            Write-Host "Monitor 镜像构建失败!" -ForegroundColor Red
            exit 1
        }
    }
    finally {
        Pop-Location
    }
}

function Clean-Cache {
    Write-Step "清理 Docker 构建缓存"

    docker builder prune -f
    Write-Host "Docker 构建缓存已清理" -ForegroundColor Green
}

# 主流程
Write-Host "`n========================================" -ForegroundColor Magenta
Write-Host "  Deep-EMS Docker 镜像构建工具" -ForegroundColor Magenta
Write-Host "========================================`n" -ForegroundColor Magenta

# 如果没有指定任何选项，显示帮助
if (-not $All -and -not $Backend -and -not $Frontend -and -not $XxlJob -and -not $Monitor -and -not $Clean) {
    Write-Host "用法:" -ForegroundColor Yellow
    Write-Host "  .\build-docker.ps1 -All          构建所有镜像"
    Write-Host "  .\build-docker.ps1 -Backend      只构建后端"
    Write-Host "  .\build-docker.ps1 -Frontend     只构建前端"
    Write-Host "  .\build-docker.ps1 -XxlJob       只构建 XXL-Job"
    Write-Host "  .\build-docker.ps1 -Monitor       只构建 Monitor"
    Write-Host "  .\build-docker.ps1 -Clean         清理构建缓存"
    Write-Host ""
    Write-Host "示例:" -ForegroundColor Yellow
    Write-Host "  .\build-docker.ps1 -All          构建所有镜像"
    Write-Host "  .\build-docker.ps1 -Backend -Frontend  构建后端和前端"
    exit 0
}

# 清理缓存
if ($Clean) {
    Clean-Cache
}

# 构建镜像
if ($All) {
    Build-Backend
    Build-Frontend
    Build-XxlJob
    Build-Monitor
}
else {
    if ($Backend) { Build-Backend }
    if ($Frontend) { Build-Frontend }
    if ($XxlJob) { Build-XxlJob }
    if ($Monitor) { Build-Monitor }
}

Write-Host "`n========================================" -ForegroundColor Green
Write-Host "  镜像构建完成!" -ForegroundColor Green
Write-Host "========================================`n" -ForegroundColor Green

# 显示构建结果
Write-Host "已构建的镜像:" -ForegroundColor Cyan
docker images | Select-String "$Registry"
