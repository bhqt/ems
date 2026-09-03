-- =====================================================
-- 医院智慧能源决策系统 - TDengine 时序超表（可选）
-- 适用库：energy（连接串 jdbc:TAOS-RS://.../energy）
-- 说明：用于存储高频设备数据点；不建表时仅 MySQL 落库，不影响主链路。
-- 执行：可在 taos CLI 或 REST /rest/sql 中执行（需有建表权限）。
-- =====================================================

-- 设备数据点超表（device_code / metric_code 为 TAG）
-- 注意：value 为 TDengine 保留字，数值列命名为 metric_value
CREATE STABLE IF NOT EXISTS hospital_device_data (
    ts           TIMESTAMP,
    metric_value DOUBLE,
    quality      INT
) TAGS (
    device_code NCHAR(64),
    metric_code NCHAR(64)
);
