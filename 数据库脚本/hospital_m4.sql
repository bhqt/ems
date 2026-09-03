-- =====================================================
-- 医院智慧能源决策系统 - M4 新增表/字段（MySQL）
-- 适用库：autoee_ems
-- 说明：加性开发，不影响原有表；仅新增表与对既有医院表做增量 ALTER。
-- 覆盖：多院区管理、分项计量、单位工作量能效、报警升级闭环。
-- =====================================================

-- 1. 医院院区管理表（M4.1 多院区数据隔离/权限）
CREATE TABLE IF NOT EXISTS `hospital_area` (
  `id`          BIGINT       NOT NULL COMMENT '主键',
  `area_code`   VARCHAR(64)  NOT NULL COMMENT '院区编码',
  `area_name`   VARCHAR(128) NOT NULL COMMENT '院区名称',
  `area_type`   CHAR(1)      DEFAULT '0' COMMENT '类型(0院区 1楼宇)',
  `parent_id`   BIGINT       DEFAULT 0  COMMENT '上级ID(0为顶级院区)',
  `status`      CHAR(1)      DEFAULT '0' COMMENT '状态(0正常 1停用)',
  `sort`        INT          DEFAULT 0  COMMENT '排序',
  `remark`      VARCHAR(512)          COMMENT '备注',
  `create_by`   VARCHAR(64)           COMMENT '创建者',
  `create_time` DATETIME              COMMENT '创建时间',
  `update_by`   VARCHAR(64)           COMMENT '更新者',
  `update_time` DATETIME              COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_area_code` (`area_code`),
  KEY `idx_area_parent` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医院院区管理';

-- 2. 医院设备分项计量字段（M4.3 分项增强）
--    分项编码：LIGHTING照明 / AIRCOND空调 / MEDICAL医疗设备 / POWER动力 / OTHER其他
ALTER TABLE `hospital_device`
  ADD COLUMN `project_category` VARCHAR(32) DEFAULT NULL COMMENT '分项(LIGHTING/AIRCOND/MEDICAL/POWER/OTHER)' AFTER `device_type`;
ALTER TABLE `hospital_device`
  ADD KEY `idx_device_category` (`project_category`);

-- 3. 医院设备工作量（检查量）表（M4.4 单位工作量能效）
--    workload_count 即该设备在 stat_date 周期内的检查工作量（如检查台次）
CREATE TABLE IF NOT EXISTS `hospital_device_workload` (
  `id`             BIGINT         NOT NULL COMMENT '主键',
  `device_id`      BIGINT         NOT NULL COMMENT '设备ID(hospital_device.id)',
  `workload_count` DECIMAL(12,2)  DEFAULT 0 COMMENT '工作量（检查台次）',
  `stat_date`      DATE           NOT NULL COMMENT '统计日期',
  `create_by`      VARCHAR(64)            COMMENT '创建者',
  `create_time`    DATETIME               COMMENT '创建时间',
  `update_by`      VARCHAR(64)            COMMENT '更新者',
  `update_time`    DATETIME               COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_device_date` (`device_id`, `stat_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医院设备工作量(检查量)';

-- 4. 医院设备报警记录 - 升级闭环字段（M4.2）
ALTER TABLE `hospital_alarm_record`
  ADD COLUMN `handle_status`   CHAR(1)      DEFAULT '0' COMMENT '处理阶段(0待处理 1已确认 2处理中 3已处理)' AFTER `status`;
ALTER TABLE `hospital_alarm_record`
  ADD COLUMN `confirm_by`      VARCHAR(64)  DEFAULT NULL COMMENT '确认人' AFTER `handle_status`;
ALTER TABLE `hospital_alarm_record`
  ADD COLUMN `confirm_time`    DATETIME     DEFAULT NULL COMMENT '确认时间' AFTER `confirm_by`;
ALTER TABLE `hospital_alarm_record`
  ADD COLUMN `escalate_count`  INT          DEFAULT 0 COMMENT '升级次数' AFTER `confirm_time`;
ALTER TABLE `hospital_alarm_record`
  ADD COLUMN `escalate_level`  CHAR(1)      DEFAULT NULL COMMENT '升级后级别(0一般 1严重 2紧急)' AFTER `escalate_count`;
ALTER TABLE `hospital_alarm_record`
  ADD COLUMN `escalate_time`   DATETIME     DEFAULT NULL COMMENT '最近升级时间' AFTER `escalate_level`;

-- 5. 医院设备报警规则 - 升级配置（M4.2）
ALTER TABLE `hospital_alarm_rule`
  ADD COLUMN `escalate_min`   INT       DEFAULT NULL COMMENT '升级超时分钟(该级别仍未处理则升级)' AFTER `notify_email`;
ALTER TABLE `hospital_alarm_rule`
  ADD COLUMN `escalate_level` CHAR(1)   DEFAULT NULL COMMENT '升级目标级别(0一般 1严重 2紧急)' AFTER `escalate_min`;

-- 6. 初始院区数据（示例：总院区 / 东院区 / 西院区）
INSERT INTO `hospital_area` (`id`, `area_code`, `area_name`, `area_type`, `parent_id`, `status`, `sort`, `create_time`) VALUES
  (1, 'HQ',    '总院区', '0', 0, '0', 1, NOW()),
  (2, 'EAST',  '东院区', '0', 0, '0', 2, NOW()),
  (3, 'WEST',  '西院区', '0', 0, '0', 3, NOW())
ON DUPLICATE KEY UPDATE `area_name` = VALUES(`area_name`);
