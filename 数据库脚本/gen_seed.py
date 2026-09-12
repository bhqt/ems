# -*- coding: utf-8 -*-
"""生成 hospital_seed_demo.sql：医院大屏演示数据（近 14 天历史 + 今日实时）"""
import datetime
import random

random.seed(20260911)

START = datetime.date(2026, 8, 28)
HIST_DAYS = 14  # 08-28 ~ 09-10 完整历史（08-28~09-03 供环比对比）

# device: (code, name, type, category, area_id)
devices = [
    ("CT-001", "CT-001高清螺旋CT", "CT", "MEDICAL", "东院区"),
    ("CT-002", "CT-002多排CT", "CT", "MEDICAL", "西院区"),
    ("MRI-001", "MRI-001核磁共振", "MRI", "MEDICAL", "东院区"),
    ("MRI-002", "MRI-002核磁共振", "MRI", "MEDICAL", "主院区"),
    ("DR-001", "DR-001射线摄影", "DR", "MEDICAL", "主院区"),
    ("DR-002", "DR-002移动DR", "DR", "MEDICAL", "西院区"),
    ("US-001", "US-001彩超", "US", "MEDICAL", "东院区"),
    ("US-002", "US-002便携彩超", "US", "MEDICAL", "主院区"),
    ("DSA-001", "DSA-001血管造影", "DSA", "MEDICAL", "主院区"),
    ("LAB-001", "LAB-001全自动生化", "LAB", "MEDICAL", "西院区"),
    ("LAB-002", "LAB-002血细胞分析", "LAB", "MEDICAL", "东院区"),
    ("ECG-001", "ECG-001心电监护", "OTHER", "MEDICAL", "主院区"),
    ("VENT-001", "呼吸机-001", "OTHER", "MEDICAL", "西院区"),
    ("AIR-001", "中央空调-压缩机组", "OTHER", "AIRCOND", "主院区"),
    ("AIR-002", "中央空调-冷水机组", "OTHER", "AIRCOND", "西院区"),
    ("LIGHT-001", "门诊照明系统", "OTHER", "LIGHTING", "东院区"),
    ("LIGHT-002", "住院楼照明系统", "OTHER", "LIGHTING", "主院区"),
    ("POWER-001", "电梯动力系统", "OTHER", "POWER", "东院区"),
]

# 各类设备功率范围 (min, max)
POWER_RANGE = {
    "CT": (18, 62),
    "MRI": (12, 40),
    "DR": (5, 22),
    "US": (2, 9),
    "DSA": (15, 45),
    "LAB": (3, 18),
    "OTHER": (1, 12),
}

# 各类型每天用电量基准（影响 electricity 增量）
KWH_BASE = {
    "CT": 95, "MRI": 70, "DR": 28, "US": 12, "DSA": 58, "LAB": 20,
    "OTHER": 10,
}

base_id = 2096000000000000001
data_id = 2096100000000000001

lines = []
lines.append("-- =====================================================")
lines.append("-- 医院智慧能源 - 大屏演示数据（近 14 天历史 + 今日实时）")
lines.append("-- 生成时间: %s" % datetime.datetime.now())
lines.append("-- 说明: 可重复执行（先清空本脚本涉及的数据再插入）")
lines.append("-- =====================================================")
lines.append("")
lines.append("USE autoee_ems;")
lines.append("")

# ---------- 清理 ----------
lines.append("-- 0. 清理旧演示数据（保留 CT-001 台账，清空其旧时间序列）")
lines.append("DELETE FROM hospital_device_data WHERE ts >= '2026-08-28';")
lines.append("DELETE FROM hospital_device_workload WHERE stat_date >= '2026-08-28';")
lines.append("DELETE FROM hospital_alarm_record WHERE start_time >= '2026-08-28';")
lines.append("DELETE FROM hospital_alarm_rule WHERE id >= 2097000000000000001;")
lines.append("")

# ---------- 设备台账 ----------
lines.append("-- 1. 设备台账（DELETE + 重新插入，保证幂等，含 CT-001）")
lines.append("DELETE FROM hospital_device WHERE id = 2095489768123383810;")
lines.append("DELETE FROM hospital_device WHERE id > 2095489768123383810;")
lines.append("DELETE FROM hospital_device WHERE device_code LIKE '%-%' AND id <> 2095489768123383810;")
# CT-001 重新插入
lines.append("INSERT INTO hospital_device (id, device_name, device_code, device_type, model, manufacturer, area_id, dept_id, iot_device_id, status, project_category, create_by, create_time, remark) VALUES "
             "(2095489768123383810, 'CT-001高清螺旋CT', 'CT-001', 'CT', 'DEMO-1', '智联医疗', '东院区', NULL, 'iot-ct-001', '0', 'MEDICAL', 'admin', NOW(), '演示设备');")
# 新增其余设备
ids = {}
for i, (code, name, dtype, category, area) in enumerate(devices):
    if code == "CT-001":
        ids[code] = 2095489768123383810
        continue
    new_id = base_id + i
    ids[code] = new_id
    lines.append(
        "INSERT INTO hospital_device (id, device_name, device_code, device_type, model, manufacturer, "
        "area_id, dept_id, iot_device_id, status, project_category, create_by, create_time, remark) VALUES "
        "(%d, '%s', '%s', '%s', 'DEMO-1', '智联医疗', '%s', NULL, 'iot-%s', '0', '%s', 'admin', NOW(), '演示设备');"
        % (new_id, name, code, dtype, area, code.lower(), category)
    )
lines.append("")

# ---------- 时间序列数据 ----------
lines.append("-- 2. 设备时间序列（近 7 天，每天 8 采样点 × 3 指标）")
# 每设备累计电量起点（保证 7 天窗口内 max-min 有增量）
elec_start = {}
for code, name, dtype, category, area in devices:
    elec_start[code] = random.randint(2000, 8000)

now = datetime.datetime.now()
cnt = 0
sql_rows = []
# 各设备累计电量：跨 14 天持续累计，保证窗口内 max-min 合理
device_elec = dict(elec_start)
for d in range(HIST_DAYS):
    day = START + datetime.timedelta(days=d)
    for code, name, dtype, category, area in devices:
        dev_id = ids[code]
        kwh_base = KWH_BASE[dtype]
        pmin, pmax = POWER_RANGE[dtype]
        elec = device_elec[code]
        # 电量跨天累计（window 内 max-min 即周期用电量）
        for slot, hour in enumerate([0, 3, 6, 9, 12, 15, 18, 21]):
            ts = datetime.datetime(day.year, day.month, day.day, hour, random.randint(0, 59))
            # 功率: 午高峰略高，夜间低
            if hour < 6:
                power = round(random.uniform(pmin, pmin + (pmax - pmin) * 0.35), 2)
            elif hour >= 9 and hour <= 18:
                power = round(random.uniform(pmin + (pmax - pmin) * 0.4, pmax), 2)
            else:
                power = round(random.uniform(pmin, pmin + (pmax - pmin) * 0.55), 2)
            # 电量增量 ≈ 平均功率 × 3小时 / 1000kWh? 直接给 kW·h 增量
            inc = round(power * 3 * (0.9 + random.random() * 0.4), 2)
            elec = round(elec + inc, 2)
            run = 1 if (8 <= hour <= 20) else 0
            device_elec[code] = elec
            sql_rows.append(
                "(%d, %d, 'power', %.2f, NULL, '%04d-%02d-%02d %02d:%02d:00', 0, NOW()),"
                % (data_id + cnt, dev_id, power, ts.year, ts.month, ts.day, ts.hour, ts.minute)
            )
            cnt += 1
            sql_rows.append(
                "(%d, %d, 'electricity', %.2f, NULL, '%04d-%02d-%02d %02d:%02d:00', 0, NOW()),"
                % (data_id + cnt, dev_id, elec, ts.year, ts.month, ts.day, ts.hour, ts.minute)
            )
            cnt += 1
            sql_rows.append(
                "(%d, %d, 'run_status', %d, '%.0f', '%04d-%02d-%02d %02d:%02d:00', 0, NOW()),"
                % (data_id + cnt, dev_id, run, run, ts.year, ts.month, ts.day, ts.hour, ts.minute)
            )
            cnt += 1

# 批量 INSERT（每 500 行为一批）—— 历史数据
BATCH = 500
for i in range(0, len(sql_rows), BATCH):
    chunk = sql_rows[i:i + BATCH]
    lines.append("INSERT INTO hospital_device_data (id, device_id, metric_code, metric_value, metric_str, ts, quality, receive_time) VALUES")
    lines.append("\n".join(chunk).rstrip(",") + ";")

# 今天(09-11)实时采样：每小时一个点，直到执行当前时刻，保证设备在线
today_rows = []
now = datetime.datetime.now()
today_hours = list(range(0, now.hour + 1))
for code, name, dtype, category, area in devices:
    dev_id = ids[code]
    pmin, pmax = POWER_RANGE[dtype]
    elec = device_elec[code]
    for hour in today_hours:
        ts = now.replace(hour=hour, minute=25, second=0, microsecond=0)
        if hour < 6:
            power = round(random.uniform(pmin, pmin + (pmax - pmin) * 0.35), 2)
        elif 9 <= hour <= 18:
            power = round(random.uniform(pmin + (pmax - pmin) * 0.4, pmax), 2)
        else:
            power = round(random.uniform(pmin, pmin + (pmax - pmin) * 0.55), 2)
        inc = round(power * 1 * (0.9 + random.random() * 0.4), 2)
        elec = round(elec + inc, 2)
        runstr = "1" if (8 <= hour <= 20) else "0"
        run = 1 if (8 <= hour <= 20) else 0
        device_elec[code] = elec
        tstr = ts.strftime("%Y-%m-%d %H:%M:00")
        today_rows.append("(%d, %d, 'power', %.2f, NULL, '%s', 0, NOW())," % (data_id + cnt, dev_id, power, tstr)); cnt += 1
        today_rows.append("(%d, %d, 'electricity', %.2f, NULL, '%s', 0, NOW())," % (data_id + cnt, dev_id, elec, tstr)); cnt += 1
        today_rows.append("(%d, %d, 'run_status', %d, '%s', '%s', 0, NOW())," % (data_id + cnt, dev_id, run, runstr, tstr)); cnt += 1
# 补最后一条 3 分钟前数据（确保最近 30 分钟内有最新点 → 在线）
for code, name, dtype, category, area in devices:
    dev_id = ids[code]
    pmin, pmax = POWER_RANGE[dtype]
    power = round(random.uniform(pmin, pmax), 2)
    tstr = (now - datetime.timedelta(minutes=3)).strftime("%Y-%m-%d %H:%M:00")
    elec = round(device_elec[code] + power * 0.05, 2)
    today_rows.append("(%d, %d, 'power', %.2f, NULL, '%s', 0, NOW())," % (data_id + cnt, dev_id, power, tstr)); cnt += 1
    today_rows.append("(%d, %d, 'electricity', %.2f, NULL, '%s', 0, NOW())," % (data_id + cnt, dev_id, elec, tstr)); cnt += 1
# 追加今天实时采样批次
if today_rows:
    lines.append("INSERT INTO hospital_device_data (id, device_id, metric_code, metric_value, metric_str, ts, quality, receive_time) VALUES")
    lines.append("\n".join(today_rows).rstrip(",") + ";")
lines.append("")

# ---------- 工作负载 ----------
lines.append("-- 3. 设备工作负载（近 14 天每台每天 1 条）")
wl_id = 2096200000000000001
w = 0
for d in range(HIST_DAYS):
    day = START + datetime.timedelta(days=d)
    for code, name, dtype, category, area in devices:
        dev_id = ids[code]
        load = random.randint(3, 48)
        wl = datetime.datetime(day.year, day.month, day.day, 22, 0)
        lines.append(
            "INSERT INTO hospital_device_workload (id, device_id, workload_count, stat_date, create_by, create_time) VALUES "
            "(%d, %d, %d, '%04d-%02d-%02d', 'admin', NOW());"
            % (wl_id + w, dev_id, load, day.year, day.month, day.day)
        )
        w += 1
lines.append("")

# ---------- 报警规则 ----------
lines.append("-- 4. 报警规则（3 条演示）")
lines.append("INSERT INTO hospital_alarm_rule (id, rule_name, device_id, device_type, metric_code, rule_type, rule_condition, threshold_value, offline_timeout_min, alarm_level, status, create_by, create_time) VALUES")
lines.append("(2097000000000000001, 'CT功率过载', NULL, 'CT', 'power', 'THRESHOLD', 'G', 55.0000, NULL, '1', '0', 'admin', NOW()),")
lines.append("(2097000000000000002, 'MRI功率过载', NULL, 'MRI', 'power', 'THRESHOLD', 'G', 38.0000, NULL, '1', '0', 'admin', NOW()),")
lines.append("(2097000000000000003, '设备离线监测', NULL, NULL, NULL, 'OFFLINE', NULL, NULL, 10080, '0', '0', 'admin', NOW());")
lines.append("")

# ---------- 报警记录 ----------
lines.append("-- 5. 报警记录（近 7 天 20 条，模拟待处理/已结束）")
alarm_tpl = [
    ("OVERLOAD", "过载", 1), ("OVERLOAD", "过载", 1), ("OVERLOAD", "过载", 2),
    ("OFFLINE", "离线", 0), ("OFFLINE", "离线", 1),
]
alarm_id = 2096300000000000001
a = 0
rmap = {code: ids[code] for code, _, _, _, _ in devices}
candidates = [c for c in rmap if c != "CT-001"]
for i in range(20):
    code = random.choice(candidates)
    atype, aname, lvl = random.choice(alarm_tpl)
    day = START + datetime.timedelta(days=random.randint(0, HIST_DAYS - 1))
    start = datetime.datetime(day.year, day.month, day.day, random.randint(6, 22), random.randint(0, 59), 0)
    status = '0' if i < 12 else '1'
    level = random.choice(["0", "1", "2"])
    end_time = "NULL" if status == '0' else "'%s'" % (start + datetime.timedelta(minutes=random.randint(10, 180))).strftime("%Y-%m-%d %H:%M:00")
    handle_by = "NULL" if status == '0' else "'张工'"
    handle_time = "NULL" if status == '0' else "NOW()"
    handle_remark = "NULL" if status == '0' else "'已复位确认'"
    lines.append(
        "INSERT INTO hospital_alarm_record (id, rule_id, device_id, metric_code, alarm_type, alarm_level, alarm_val, content, status, start_time, end_time, handle_by, handle_time, handle_remark, create_time) VALUES "
        "(%d, 2097000000000000003, %d, 'power', '%s', '%s', 42.50, '设备[%s]触发%s报警，请及时处理', '%s', '%s', %s, %s, %s, %s, NOW());"
        % (alarm_id + a, rmap[code], atype, level, code, aname, status,
           start.strftime("%Y-%m-%d %H:%M:00"), end_time, handle_by, handle_time, handle_remark)
    )
    a += 1
lines.append("")

lines.append("-- 完成")
lines.append("")

with open("hospital_seed_demo.sql", "w", encoding="utf-8") as f:
    f.write("\n".join(lines))

print("Generated hospital_seed_demo.sql, data rows=%d" % cnt)