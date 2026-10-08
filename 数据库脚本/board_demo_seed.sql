-- =====================================================
-- 数据看板（/data-board）演示数据 - 山东浪潮场景
-- 生成时间: 2026-10-08
-- 说明: 幂等脚本，可重复执行（先清理本脚本涉及的演示数据再插入）
--       数据范围: 区域拓扑 / 设备 / 能耗 / 功率 / 报警
-- 看板依赖接口:
--   /system/itemTopology/topologyTree     -> item_topology
--   /equipment/getAllStatus               -> equipment_info（status 0/1/2）
--   /system/history/list                  -> alarm_history
--   /alarm/getLatestAlarmsByCount         -> realtime_alarm
--   /data/energy/dayTrend                 -> energy_statistics（按小时聚合）
--   /data/energy/getDailyP                -> power_statistics
--   /data/energy/getConsumptionStatistics -> energy_statistics（按日聚合）
-- =====================================================

USE autoee_ems;

-- =====================================================
-- 0. 清理旧演示数据（按唯一编号段隔离，不影响原始数据）
-- =====================================================
DELETE FROM item_topology   WHERE item_id    BETWEEN 8880000000000000001 AND 8880000000000000099;
DELETE FROM equipment_info  WHERE id         BETWEEN 8881000000000000001 AND 8881000000000000099;
DELETE FROM energy_statistics WHERE id      BETWEEN 8882000000000000001 AND 8882000000999999999;
DELETE FROM power_statistics  WHERE id      BETWEEN 8883000000000000001 AND 8883000000999999999;
DELETE FROM alarm_history    WHERE id       BETWEEN 8884000000000000001 AND 8884000000999999999;
DELETE FROM realtime_alarm   WHERE id       BETWEEN 8885000000000000001 AND 8885000000999999999;

-- =====================================================
-- 1. 区域拓扑（浪潮山东园区：济南总部 + 青岛/烟台/威海/潍坊分公司）
-- =====================================================
INSERT INTO item_topology
  (item_id, parent_id, ancestors, item_name, order_num, status, del_flag, item_type, create_by, create_time, update_by, update_time, device_id)
VALUES
  (8880000000000000001, 0, '0', '浪潮集团山东园区', 1, '0', '0', 'building', 'admin', NOW(), 'admin', NOW(), NULL),
  (8880000000000000002, 8880000000000000001, '0,8880000000000000001', '济南总部', 1, '0', '0', 'building', 'admin', NOW(), 'admin', NOW(), NULL),
  (8880000000000000003, 8880000000000000002, '0,8880000000000000001,8880000000000000002', 'A栋-研发楼', 1, '0', '0', 'building', 'admin', NOW(), 'admin', NOW(), 'JN-D-LD-001,JN-D-LD-002,JN-S-LD-001'),
  (8880000000000000004, 8880000000000000002, '0,8880000000000000001,8880000000000000002', 'B栋-数据中心', 2, '0', '0', 'building', 'admin', NOW(), 'admin', NOW(), 'JN-D-DC-001,JN-D-DC-002,JN-S-DC-001'),
  (8880000000000000005, 8880000000000000002, '0,8880000000000000001,8880000000000000002', 'C栋-综合楼', 3, '0', '0', 'building', 'admin', NOW(), 'admin', NOW(), 'JN-D-OF-001,JN-D-OF-002,JN-S-OF-001'),
  (8880000000000000006, 8880000000000000001, '0,8880000000000000001', '青岛研发中心', 2, '0', '0', 'building', 'admin', NOW(), 'admin', NOW(), NULL),
  (8880000000000000007, 8880000000000000006, '0,8880000000000000001,8880000000000000006', '青岛研发楼', 1, '0', '0', 'building', 'admin', NOW(), 'admin', NOW(), 'QD-D-LD-001'),
  (8880000000000000008, 8880000000000000001, '0,8880000000000000001', '烟台分公司', 3, '0', '0', 'building', 'admin', NOW(), 'admin', NOW(), 'YT-D-OF-001,YT-S-OF-001'),
  (8880000000000000009, 8880000000000000001, '0,8880000000000000001', '威海数据中心', 4, '0', '0', 'building', 'admin', NOW(), 'admin', NOW(), 'WH-D-DC-001'),
  (8880000000000000010, 8880000000000000001, '0,8880000000000000001', '潍坊生产基地', 5, '0', '0', 'building', 'admin', NOW(), 'admin', NOW(), 'WF-D-PD-001,WF-S-PD-001');

-- =====================================================
-- 2. 设备台账（status 0=正常 1=报警 2=离线）
--    type: 0=电表 1=水表
-- =====================================================
INSERT INTO equipment_info
  (id, name, sn, model, description, img, qr_code, factory, type, status, create_by, create_time, update_by, update_time)
VALUES
  (8881000000000000001, '济南研发楼1#电表',   'JN-D-LD-001', 'DTS634', '济南A栋研发楼主电表',  NULL, NULL, '浪潮能源', '0', '0', 'admin', NOW(), 'admin', NOW()),
  (8881000000000000002, '济南研发楼2#电表',   'JN-D-LD-002', 'DTS634', '济南A栋研发楼副电表',  NULL, NULL, '浪潮能源', '0', '0', 'admin', NOW(), 'admin', NOW()),
  (8881000000000000003, '济南研发楼水表',     'JN-S-LD-001', 'LXSG-50','济南A栋研发楼水表',    NULL, NULL, '浪潮能源', '1', '0', 'admin', NOW(), 'admin', NOW()),
  (8881000000000000004, '济南数据中心1#电表', 'JN-D-DC-001', 'DTS634', '济南B栋数据中心主电表',NULL, NULL, '浪潮能源', '0', '0', 'admin', NOW(), 'admin', NOW()),
  (8881000000000000005, '济南数据中心2#电表', 'JN-D-DC-002', 'DTS634', '济南B栋数据中心UPS电表',NULL,NULL,'浪潮能源', '0', '1', 'admin', NOW(), 'admin', NOW()),
  (8881000000000000006, '济南数据中心水表',   'JN-S-DC-001', 'LXSG-50','济南B栋数据中心水表',  NULL, NULL, '浪潮能源', '1', '0', 'admin', NOW(), 'admin', NOW()),
  (8881000000000000007, '济南综合楼1#电表',   'JN-D-OF-001', 'DTS634', '济南C栋综合楼主电表',  NULL, NULL, '浪潮能源', '0', '0', 'admin', NOW(), 'admin', NOW()),
  (8881000000000000008, '济南综合楼2#电表',   'JN-D-OF-002', 'DTS634', '济南C栋综合楼副电表',  NULL, NULL, '浪潮能源', '0', '2', 'admin', NOW(), 'admin', NOW()),
  (8881000000000000009, '济南综合楼水表',     'JN-S-OF-001', 'LXSG-50','济南C栋综合楼水表',    NULL, NULL, '浪潮能源', '1', '0', 'admin', NOW(), 'admin', NOW()),
  (8881000000000000010, '青岛研发楼电表',     'QD-D-LD-001', 'DTS634', '青岛研发楼主电表',     NULL, NULL, '浪潮能源', '0', '0', 'admin', NOW(), 'admin', NOW()),
  (8881000000000000011, '烟台综合楼电表',     'YT-D-OF-001', 'DTS634', '烟台综合楼主电表',     NULL, NULL, '浪潮能源', '0', '0', 'admin', NOW(), 'admin', NOW()),
  (8881000000000000012, '烟台综合楼水表',     'YT-S-OF-001', 'LXSG-50','烟台综合楼水表',       NULL, NULL, '浪潮能源', '1', '0', 'admin', NOW(), 'admin', NOW()),
  (8881000000000000013, '威海数据中心电表',   'WH-D-DC-001', 'DTS634', '威海数据中心主电表',   NULL, NULL, '浪潮能源', '0', '1', 'admin', NOW(), 'admin', NOW()),
  (8881000000000000014, '潍坊生产基地电表',   'WF-D-PD-001', 'DTS634', '潍坊生产基地总电表',   NULL, NULL, '浪潮能源', '0', '0', 'admin', NOW(), 'admin', NOW()),
  (8881000000000000015, '潍坊生产基地水表',   'WF-S-PD-001', 'LXSG-50','潍坊生产基地总水表',   NULL, NULL, '浪潮能源', '1', '0', 'admin', NOW(), 'admin', NOW());

-- =====================================================
-- 3. 能耗统计（今天 + 昨天，24 小时聚合）
--    简化方案：直接拼接 720 条 INSERT，避开递归 CTE 限制
-- =====================================================
DELIMITER $$
DROP PROCEDURE IF EXISTS seed_energy_statistics $$
CREATE PROCEDURE seed_energy_statistics()
BEGIN
  DECLARE i BIGINT DEFAULT 0;
  DECLARE base_id BIGINT DEFAULT 8882000000000000001;
  DECLARE d DATE;
  DECLARE h INT;
  DECLARE sn_var VARCHAR(40);
  DECLARE etype CHAR(1);
  DECLARE base_val DECIMAL(10,2);
  DECLARE done INT DEFAULT 0;
  -- 15 个设备
  DECLARE cur CURSOR FOR
    SELECT sn, energy_type, base FROM (
      SELECT 'JN-D-LD-001' AS sn, '0' AS energy_type, 420 AS base UNION ALL
      SELECT 'JN-D-LD-002',    '0',           380 UNION ALL
      SELECT 'JN-D-DC-001',    '0',          1850 UNION ALL
      SELECT 'JN-D-DC-002',    '0',          1620 UNION ALL
      SELECT 'JN-D-OF-001',    '0',           520 UNION ALL
      SELECT 'JN-D-OF-002',    '0',           460 UNION ALL
      SELECT 'QD-D-LD-001',    '0',           680 UNION ALL
      SELECT 'YT-D-OF-001',    '0',           340 UNION ALL
      SELECT 'WH-D-DC-001',    '0',          1480 UNION ALL
      SELECT 'WF-D-PD-001',    '0',           780 UNION ALL
      SELECT 'JN-S-LD-001',    '1',            1.2 UNION ALL
      SELECT 'JN-S-DC-001',    '1',            4.8 UNION ALL
      SELECT 'JN-S-OF-001',    '1',            1.5 UNION ALL
      SELECT 'YT-S-OF-001',    '1',            0.9 UNION ALL
      SELECT 'WF-S-PD-001',    '1',            3.6
    ) t;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

  SET i = 0;
  OPEN cur;
  read_loop: LOOP
    FETCH cur INTO sn_var, etype, base_val;
    IF done = 1 THEN LEAVE read_loop; END IF;
    SET @day_offset = 0;
    WHILE @day_offset < 2 DO
      SET d = DATE_SUB(DATE(NOW()), INTERVAL @day_offset DAY);
      SET h = 0;
      WHILE h < 24 DO
        -- 营业曲线
        SET @curve = CASE WHEN etype='0' THEN
            CASE
              WHEN h BETWEEN 0  AND 6  THEN 0.45
              WHEN h BETWEEN 7  AND 9  THEN 0.85
              WHEN h BETWEEN 10 AND 12 THEN 1.15
              WHEN h BETWEEN 13 AND 17 THEN 1.25
              WHEN h BETWEEN 18 AND 22 THEN 0.95
              ELSE 0.55
            END
          ELSE
            CASE
              WHEN h BETWEEN 6  AND 9  THEN 1.30
              WHEN h BETWEEN 10 AND 17 THEN 0.90
              WHEN h BETWEEN 18 AND 22 THEN 1.25
              ELSE 0.30
            END
          END;
        -- 设备随机波动（用 SN 末几位 + 小时 + 日期偏移当 seed）
        SET @seed = CRC32(CONCAT(sn_var, h, @day_offset));
        SET @noise = 0.92 + 0.16 * ((@seed MOD 100) / 100.0);
        SET @val = ROUND(base_val * @curve * @noise, 2);
        SET @id = base_id + i;

        INSERT INTO energy_statistics
          (id, equipment_sn, energy_type, time, statistics, create_by, create_time, update_by, update_time)
        VALUES
          (@id, sn_var, etype, TIMESTAMP(d, SEC_TO_TIME(h * 3600)), @val, 'admin', NOW(), 'admin', NOW());

        SET i = i + 1;
        SET h = h + 1;
      END WHILE;
      SET @day_offset = @day_offset + 1;
    END WHILE;
  END LOOP;
  CLOSE cur;
END $$
DELIMITER ;

CALL seed_energy_statistics();
DROP PROCEDURE seed_energy_statistics;

-- =====================================================
-- 4. 功率统计（电表 24h × 2 天，提供给 getDailyP）
-- =====================================================
DELIMITER $$
DROP PROCEDURE IF EXISTS seed_power_statistics $$
CREATE PROCEDURE seed_power_statistics()
BEGIN
  DECLARE i BIGINT DEFAULT 0;
  DECLARE base_id BIGINT DEFAULT 8883000000000000001;
  DECLARE d DATE;
  DECLARE h INT;
  DECLARE sn_var VARCHAR(40);
  DECLARE base_val INT;
  DECLARE done INT DEFAULT 0;
  DECLARE cur CURSOR FOR
    SELECT sn, base FROM (
      SELECT 'JN-D-LD-001' AS sn,  680 AS base UNION ALL
      SELECT 'JN-D-LD-002',  610 UNION ALL
      SELECT 'JN-D-DC-001', 2950 UNION ALL
      SELECT 'JN-D-DC-002', 2640 UNION ALL
      SELECT 'JN-D-OF-001',  820 UNION ALL
      SELECT 'JN-D-OF-002',  740 UNION ALL
      SELECT 'QD-D-LD-001', 1080 UNION ALL
      SELECT 'YT-D-OF-001',  540 UNION ALL
      SELECT 'WH-D-DC-001', 2380 UNION ALL
      SELECT 'WF-D-PD-001', 1260
    ) t;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

  OPEN cur;
  read_loop: LOOP
    FETCH cur INTO sn_var, base_val;
    IF done = 1 THEN LEAVE read_loop; END IF;
    SET @day_offset = 0;
    WHILE @day_offset < 2 DO
      SET d = DATE_SUB(DATE(NOW()), INTERVAL @day_offset DAY);
      SET h = 0;
      WHILE h < 24 DO
        SET @ave_factor = CASE
              WHEN h BETWEEN 0  AND 6  THEN 0.45
              WHEN h BETWEEN 7  AND 9  THEN 0.85
              WHEN h BETWEEN 10 AND 12 THEN 1.15
              WHEN h BETWEEN 13 AND 17 THEN 1.25
              WHEN h BETWEEN 18 AND 22 THEN 0.95
              ELSE 0.55 END;
        SET @id = base_id + i;
        -- create_time 必须落在该小时的桶内：getDailyP 按 create_time 分小时，
        -- 且边界为开区间 (h:00, h+1:00)，故取 h:00:30 而非整点或 NOW()
        INSERT INTO power_statistics
          (id, equipment_sn, energy_type, time, min, ave, max, create_by, create_time, update_by, update_time)
        VALUES
          (@id, sn_var, '0', TIMESTAMP(d, SEC_TO_TIME(h * 3600)),
           ROUND(base_val * @ave_factor * 0.62, 2),
           ROUND(base_val * @ave_factor, 2),
           ROUND(base_val * LEAST(@ave_factor * 1.25, 1.60), 2),
           'admin', TIMESTAMP(d, SEC_TO_TIME(h * 3600 + 30)), 'admin', NOW());
        SET i = i + 1;
        SET h = h + 1;
      END WHILE;
      SET @day_offset = @day_offset + 1;
    END WHILE;
  END LOOP;
  CLOSE cur;
END $$
DELIMITER ;

CALL seed_power_statistics();
DROP PROCEDURE seed_power_statistics;

-- =====================================================
-- 5. 历史报警（今天 + 过去 7 天，每条间隔随机）
-- =====================================================
SET @ar_rn := 0;
INSERT INTO alarm_history
  (id, param_name, alarm_time, alarm_info, alarm_level, area, equipment, alarm_val, end_time, create_by, create_time, update_by, update_time)
SELECT
  8884000000000000000 + @ar_rn := @ar_rn + 1,
  CASE ((@ar_rn) % 3)
    WHEN 0 THEN '电流'
    WHEN 1 THEN '电压'
    ELSE '水压'
  END,
  DATE_SUB(NOW(), INTERVAL (@ar_rn * 5 + ((@ar_rn * 7) MOD 30)) MINUTE),
  CASE ((@ar_rn) % 4)
    WHEN 0 THEN '电流超过阈值 80A'
    WHEN 1 THEN '电压异常波动 220V'
    WHEN 2 THEN '水压过低 0.05MPa'
    ELSE '夜间功率异常攀升'
  END,
  CASE ((@ar_rn) % 3)
    WHEN 0 THEN '0'
    WHEN 1 THEN '1'
    ELSE '2'
  END,
  CASE ((@ar_rn) % 5)
    WHEN 0 THEN '济南总部-A栋研发楼'
    WHEN 1 THEN '济南总部-B栋数据中心'
    WHEN 2 THEN '青岛研发中心'
    WHEN 3 THEN '烟台分公司'
    ELSE '潍坊生产基地'
  END,
  CASE ((@ar_rn) % 6)
    WHEN 0 THEN 'JN-D-LD-001'
    WHEN 1 THEN 'JN-D-DC-002'
    WHEN 2 THEN 'JN-D-OF-002'
    WHEN 3 THEN 'WH-D-DC-001'
    WHEN 4 THEN 'YT-D-OF-001'
    ELSE 'WF-D-PD-001'
  END,
  ROUND(50 + @ar_rn * 7.3, 2),
  CASE WHEN @ar_rn % 3 = 0 THEN DATE_SUB(NOW(), INTERVAL (@ar_rn * 5) MINUTE) ELSE NULL END,
  'admin', NOW(), 'admin', NOW()
FROM (
  SELECT a.N + b.N * 10 AS n
  FROM (SELECT 0 AS N UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
        UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) a
  CROSS JOIN
       (SELECT 0 AS N UNION ALL SELECT 1) b
  WHERE a.N + b.N * 10 < 18
) seq;
SET @ar_rn := NULL;

-- =====================================================
-- 6. 实时报警（用于 alarmInfo 组件 getLatestAlarmsByCount）
--    生成最近 12 条，每条间隔 3~8 分钟
-- =====================================================
SET @rt_rn := 0;
INSERT INTO realtime_alarm
  (id, param_name, alarm_time, alarm_info, alarm_level, area, equipment, alarm_val, create_by, create_time, update_by, update_time)
SELECT
  8885000000000000000 + @rt_rn := @rt_rn + 1,
  CASE ((@rt_rn) % 3)
    WHEN 0 THEN '电流'
    WHEN 1 THEN '电压'
    ELSE '水压'
  END,
  DATE_SUB(NOW(), INTERVAL (@rt_rn * 4 + ((@rt_rn * 3) MOD 5)) MINUTE),
  CASE ((@rt_rn) % 5)
    WHEN 0 THEN '电流超过阈值 85A，请尽快排查'
    WHEN 1 THEN '电压跌落至 198V'
    WHEN 2 THEN '水压过低 0.04MPa'
    WHEN 3 THEN '设备通讯中断'
    ELSE '功率突增 30%'
  END,
  CASE ((@rt_rn) % 3)
    WHEN 0 THEN '0'
    WHEN 1 THEN '1'
    ELSE '2'
  END,
  CASE ((@rt_rn) % 5)
    WHEN 0 THEN '济南总部-B栋数据中心'
    WHEN 1 THEN '济南总部-A栋研发楼'
    WHEN 2 THEN '青岛研发中心'
    WHEN 3 THEN '威海数据中心'
    ELSE '潍坊生产基地'
  END,
  CASE ((@rt_rn) % 5)
    WHEN 0 THEN 'JN-D-DC-002'
    WHEN 1 THEN 'JN-D-LD-001'
    WHEN 2 THEN 'QD-D-LD-001'
    WHEN 3 THEN 'WH-D-DC-001'
    ELSE 'WF-D-PD-001'
  END,
  ROUND(60 + @rt_rn * 4.7, 2),
  'admin', NOW(), 'admin', NOW()
FROM (
  SELECT a.N + b.N * 10 AS n
  FROM (SELECT 0 AS N UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
        UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) a
  CROSS JOIN
       (SELECT 0 AS N UNION ALL SELECT 1) b
  WHERE a.N + b.N * 10 < 12
) seq;
SET @rt_rn := NULL;

-- =====================================================
-- 7. 校验
-- =====================================================
SELECT 'item_topology'      AS tbl, COUNT(*) AS cnt FROM item_topology      WHERE item_id   BETWEEN 8880000000000000001 AND 8880000000000000099
UNION ALL
SELECT 'equipment_info',  COUNT(*)           FROM equipment_info     WHERE id         BETWEEN 8881000000000000001 AND 8881000000000000099
UNION ALL
SELECT 'energy_statistics',COUNT(*)           FROM energy_statistics WHERE id         BETWEEN 8882000000000000001 AND 8882000000999999999
UNION ALL
SELECT 'power_statistics', COUNT(*)           FROM power_statistics  WHERE id         BETWEEN 8883000000000000001 AND 8883000000999999999
UNION ALL
SELECT 'alarm_history',    COUNT(*)           FROM alarm_history     WHERE id         BETWEEN 8884000000000000001 AND 8884000000999999999
UNION ALL
SELECT 'realtime_alarm',   COUNT(*)           FROM realtime_alarm    WHERE id         BETWEEN 8885000000000000001 AND 8885000000999999999;
