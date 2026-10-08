# 数据看板演示数据装载脚本
# 用法（在仓库根目录执行）：pwsh -File .\数据库脚本\board_demo_seed.ps1
param(
    [string]$MysqlContainer = "shared-mysql",
    [string]$MysqlUser = "root",
    [string]$MysqlPwd  = "123456",
    [string]$DbName    = "autoee_ems"
)

$ErrorActionPreference = "Stop"
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
$SqlFile   = Join-Path $ScriptDir "board_demo_seed.sql"

if (-not (Test-Path $SqlFile)) {
    Write-Host "[ERROR] SQL not found: $SqlFile" -ForegroundColor Red
    exit 1
}

Write-Host "[INFO] Loading $SqlFile into $DbName via $MysqlContainer ..." -ForegroundColor Cyan

# 容器内执行 MySQL，输出到当前 shell
Get-Content $SqlFile -Encoding UTF8 | docker exec -i $MysqlContainer mysql -u$MysqlUser -p$MysqlPwd -N -B $DbName 2>&1

if ($LASTEXITCODE -eq 0) {
    Write-Host "[OK]  board demo data seeded." -ForegroundColor Green
} else {
    Write-Host "[FAIL] MySQL exit code = $LASTEXITCODE" -ForegroundColor Red
    exit $LASTEXITCODE
}
