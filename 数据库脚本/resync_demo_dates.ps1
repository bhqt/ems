# hospital_seed_demo.sql date shift script
# Shift the original date range 2026-08-28 ~ 2026-09-11 by +27 days
# Result range: 2026-09-24 ~ 2026-10-08
# Note: comments and output strings are ASCII-only to avoid Windows GBK decoding issues

param(
    [int]$OffsetDays = 27
)

$here = $PSScriptRoot
if (-not $here) { $here = 'D:\code\gitcp\inspur-ems\deep-ems0\db' }
$InputSql  = Join-Path $here 'hospital_seed_demo.sql'
$OutputSql = Join-Path $here 'hospital_seed_demo_resynced.sql'

# Re-encode input properly even if PowerShell host uses GBK
$srcBytes = [System.IO.File]::ReadAllBytes($InputSql)
$src = [System.Text.Encoding]::UTF8.GetString($srcBytes)
# Header marker (CJK may not survive via .Replace on PowerShell host)
$oldHdrBytes  = [byte[]](0xE7,0x94,0x9F,0xE6,0x88,0x90,0xE6,0x97,0xB6,0xE9,0x97,0xB4,0x3A,0x20,0x32,0x30,0x32,0x36,0x2D,0x30,0x39,0x2D,0x31,0x31,0x20,0x31,0x38,0x3A,0x33,0x34,0x3A,0x35,0x33,0x2E,0x39,0x39,0x39,0x37,0x35,0x31)
$oldHdrStr    = [System.Text.Encoding]::UTF8.GetString($oldHdrBytes)
$nowStr       = (Get-Date).ToString('yyyy-MM-dd HH:mm:ss')
$newHdrSuffix = [byte[]](0x28,0xE5,0x8E,0x9F,0xE5,0xA7,0x8B,0x20,0x2B)
$newHdrSuffixStr = [System.Text.Encoding]::UTF8.GetString($newHdrSuffix)
$newHdrStr    = "regen: $nowStr $newHdrSuffixStr $OffsetDays d)"
# We will patch by string replace once UTF-8 of file content is decoded properly;
# to be safe, replace after encoding the literal manually via the bytes string.
$res = $src
$res = $res.Replace($oldHdrStr, $newHdrStr)

function Shift-DateTime([string]$s, [int]$days) {
    $out = $s
    # datetime pattern: '2026-08-28 00:24:00'
    $re = [regex]"'20(\d{2})-(\d{2})-(\d{2}) (\d{2}:\d{2}:\d{2})'"
    $out = $re.Replace($out, {
        param($m)
        $y = 2000 + [int]$m.Groups[1].Value
        $mo = [int]$m.Groups[2].Value
        $d = [int]$m.Groups[3].Value
        $t = $m.Groups[4].Value
        $hh = [int]$t.Substring(0,2)
        $mm = [int]$t.Substring(3,2)
        $ss = [int]$t.Substring(6,2)
        $dt = Get-Date -Year $y -Month $mo -Day $d -Hour $hh -Minute $mm -Second $ss
        $nd = $dt.AddDays($days)
        return [string]::Format("'{0:yyyy-MM-dd HH:mm:ss}'", $nd)
    })

    # pure date pattern: '2026-08-28' (no trailing time)
    $re2 = [regex]"'20(\d{2})-(\d{2})-(\d{2})'"
    $out = $re2.Replace($out, {
        param($m)
        $y = 2000 + [int]$m.Groups[1].Value
        $mo = [int]$m.Groups[2].Value
        $d = [int]$m.Groups[3].Value
        $dt = Get-Date -Year $y -Month $mo -Day $d
        $nd = $dt.AddDays($days)
        return [string]::Format("'{0:yyyy-MM-dd}'", $nd)
    })

    return $out
}

$res = Shift-DateTime $src $OffsetDays

# Replace the header - the original script header contains CJK chars; use UTF-8 byte literal
$oldHeaderStr = [System.Text.Encoding]::UTF8.GetString([byte[]](0xE7,0x94,0x9F,0xE6,0x88,0x90,0xE6,0x97,0xB6,0xE9,0x97,0xB4,0x3A,0x20,0x32,0x30,0x32,0x36,0x2D,0x30,0x39,0x2D,0x31,0x31,0x20,0x31,0x38,0x3A,0x33,0x34,0x3A,0x35,0x33,0x2E,0x39,0x39,0x39,0x37,0x35,0x31))
$nowStr2 = (Get-Date).ToString('yyyy-MM-dd HH:mm:ss')
$newHeaderStr = [System.Text.Encoding]::UTF8.GetString([byte[]](0xE9,0x87,0x8D,0xE7,0x94,0x9F,0xE6,0x88,0x90,0xE6,0x97,0xB6,0xE9,0x97,0xB4,0x3A,0x20)) + " $nowStr2 " + [System.Text.Encoding]::UTF8.GetString([byte[]](0x28,0xE5,0x8E,0x9F,0xE5,0xA7,0x8B,0x20,0x2B)) + "$OffsetDays " + [System.Text.Encoding]::UTF8.GetString([byte[]](0xE5,0xA4,0xA9,0x29))
$res = $res.Replace($oldHeaderStr, $newHeaderStr)

# Replace DELETE boundary dates
$delDate = (Get-Date -Year 2026 -Month 8 -Day 28).AddDays($OffsetDays).ToString('yyyy-MM-dd')
$res = $res.Replace("DELETE FROM hospital_device_data WHERE ts >= '2026-08-28';", "DELETE FROM hospital_device_data WHERE ts >= '$delDate';")
$res = $res.Replace("DELETE FROM hospital_device_workload WHERE stat_date >= '2026-08-28';", "DELETE FROM hospital_device_workload WHERE stat_date >= '$delDate';")
$res = $res.Replace("DELETE FROM hospital_alarm_record WHERE start_time >= '2026-08-28';", "DELETE FROM hospital_alarm_record WHERE start_time >= '$delDate';")

[System.IO.File]::WriteAllText($OutputSql, $res, (New-Object System.Text.UTF8Encoding $false))

# Stats
$arr = @()
$matches = [regex]::Matches($res, "'20\d{2}-\d{2}-\d{2}(?: \d{2}:\d{2}:\d{2})?'")
foreach ($m in $matches) {
    $s = $m.Value.Substring(1, $m.Value.Length - 2)
    $arr += $s
}
$distinct = $arr | Sort-Object -Unique

Write-Host "Input : $InputSql"
Write-Host "Output: $OutputSql"
Write-Host "Offset: +$OffsetDays days"
Write-Host ""
Write-Host "Distinct dates in new file (first 5):"
$distinct | Select-Object -First 5 | ForEach-Object { Write-Host "  $_" }
Write-Host "  ..."
$distinct | Select-Object -Last 5 | ForEach-Object { Write-Host "  $_" }
Write-Host ""
Write-Host "Total date-string occurrences: $($arr.Count)"