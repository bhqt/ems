-- ============================================================
-- energy_storage 表补列：与 EnergyStorageVo / 前端 energyStorage 页面字段对齐
--
-- 背景：
--   前端 zhurong-admin-ui/src/views/newenergy/energyStorage/index.vue
--   表单/详情/表格用到 batteryType、address、commissioningDate 三个字段，
--   但 energy_storage 表无对应列，导致这些列/输入框永远为空、保存后丢数据。
--
-- 处理方式：
--   1) battery_type、address、commissioning_date —— 表里确实没有，直接补列。
--      补完后 EnergyStorage 实体 / EnergyStorageBo / EnergyStorageVo 三者同名字段
--      自动对齐，MyBatis-Plus 自动 SQL 与 EnergyStorageMapper.xml 都能正常映射。
--      注意：grid_date（并网日期）是与 commissioning_date 语义不同的另一个字段
--      （并网 ≠ 投运），保留原列不动，两者各自独立。
--   2) status_name —— 不补列。它是由 status 派生的展示字段
--      （前端用本地 getStatusLabel 映射，并未读取该字段），
--      存库会与 status 产生不一致风险。
--
-- 用法：在目标库（如 autoee_ems）执行本脚本，可重复执行。
--
-- ⚠️ 执行方式：必须以 UTF-8 文件方式导入，不要用 PowerShell 管道
--    （Get-Content | docker exec -i mysql ... 会把中文注释写成 "?"）。
--    推荐：
--      docker cp fix_energy_storage_columns.sql shared-mysql:/tmp/x.sql
--      docker exec shared-mysql mysql -uroot -p123456 --default-character-set=utf8mb4 autoee_ems < /tmp/x.sql
--    或用 Navicat / DBeaver 以 UTF-8 打开执行。
-- ============================================================

USE `autoee_ems`;

-- 1) 补列 battery_type
SET @ddl := IF(
  (SELECT COUNT(*) FROM information_schema.COLUMNS
     WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'energy_storage'
       AND COLUMN_NAME = 'battery_type') > 0,
  'SELECT 1',
  'ALTER TABLE `energy_storage` ADD COLUMN `battery_type` varchar(50) DEFAULT NULL COMMENT ''电池类型'' AFTER `voltage_level`'
);
PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

-- 2) 补列 address
SET @ddl := IF(
  (SELECT COUNT(*) FROM information_schema.COLUMNS
     WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'energy_storage'
       AND COLUMN_NAME = 'address') > 0,
  'SELECT 1',
  'ALTER TABLE `energy_storage` ADD COLUMN `address` varchar(255) DEFAULT NULL COMMENT ''地址'' AFTER `area_id`'
);
PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

-- 3) 补列 commissioning_date
SET @ddl := IF(
  (SELECT COUNT(*) FROM information_schema.COLUMNS
     WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'energy_storage'
       AND COLUMN_NAME = 'commissioning_date') > 0,
  'SELECT 1',
  'ALTER TABLE `energy_storage` ADD COLUMN `commissioning_date` date DEFAULT NULL COMMENT ''投运日期'' AFTER `install_date`'
);
PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

-- 4) 历史数据回填：老数据只有 install_date / grid_date，用 grid_date 初始化投运日期
--    （grid_date 并网日期与投运日期业务上最接近；仅填新列，不改任何已有列的值）
UPDATE `energy_storage`
SET `commissioning_date` = `grid_date`
WHERE `commissioning_date` IS NULL
  AND `grid_date` IS NOT NULL;

UPDATE `energy_storage`
SET `commissioning_date` = `install_date`
WHERE `commissioning_date` IS NULL
  AND `install_date` IS NOT NULL;

-- 5) 校验
SELECT COLUMN_NAME, COLUMN_TYPE, IS_NULLABLE, COLUMN_COMMENT
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 'energy_storage'
  AND COLUMN_NAME IN ('battery_type', 'address', 'commissioning_date')
ORDER BY ORDINAL_POSITION;

SELECT id, storage_name, battery_type, address, commissioning_date FROM `energy_storage`;