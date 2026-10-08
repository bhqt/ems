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

# 关键修复：不能用 PowerShell 管道把 SQL 喂给 mysql（PS 会按 [Console]::OutputEncoding
# 重编码，默认 OEM 代码页会把中文变成 '?'），改用 cmd 输入重定向，文件字节原样透传；
# 同时显式声明 utf8mb4，保证 mysql 按 UTF-8 解码输入。
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
cmd /c "docker exec -i `"$MysqlContainer`" mysql --default-character-set=utf8mb4 -u$MysqlUser -p$MysqlPwd -N -B `"$DbName`" < `"$SqlFile`""
$exitCode = $LASTEXITCODE

if ($exitCode -eq 0) {
    Write-Host "[OK]  board demo data seeded." -ForegroundColor Green
} else {
    Write-Host "[FAIL] MySQL exit code = $exitCode" -ForegroundColor Red
    exit $exitCode
}
