-- =====================================================
-- 医院智慧能源决策系统 - 报警模块新增表结构（MySQL，M2）
-- 适用库：autoee_ems
-- 说明：加性开发，不影响原有表；与现有 alarm_rule/realtime_alarm
--       链路隔离（医院设备走 hospital_ 前缀表独立闭环）。
-- =====================================================

-- 1. 医院设备报警规则
CREATE TABLE IF NOT EXISTS `hospital_alarm_rule` (
  `id`                 BIGINT         NOT NULL COMMENT '主键',
  `rule_name`          VARCHAR(128)   NOT NULL COMMENT '规则名称',
  `device_id`          BIGINT                  COMMENT '设备ID(hospital_device.id)，为空表示全部设备',
  `device_type`        VARCHAR(32)             COMMENT '设备类型(CT/MRI/DR/US/LAB/DSA/OTHER)，device_id为空时可按类型过滤',
  `metric_code`        VARCHAR(64)             COMMENT '指标编码(THRESHOLD规则必填，如power)',
  `rule_type`          VARCHAR(16)    NOT NULL COMMENT '规则类型(THRESHOLD阈值/OFFLINE离线)',
  `condition`          VARCHAR(8)              COMMENT '比较条件(G大于/E等于/L小于/GE大于等于/LE小于等于)',
  `threshold_value`    DECIMAL(18,4)           COMMENT '阈值(THRESHOLD规则必填)',
  `offline_timeout_min` INT                    COMMENT '离线超时分钟数(OFFLINE规则必填)',
  `level`              CHAR(1)        DEFAULT '0' COMMENT '报警级别(0一般 1严重 2紧急)',
  `status`             CHAR(1)        DEFAULT '0' COMMENT '是否启用(0启用 1停用)',
  `notify_email`       VARCHAR(256)            COMMENT '通知邮箱(多个逗号分隔，为空仅记录不通知)',
  `remark`             VARCHAR(512)            COMMENT '备注',
  `create_by`          VARCHAR(64)             COMMENT '创建者',
  `create_time`        DATETIME                COMMENT '创建时间',
  `update_by`          VARCHAR(64)             COMMENT '更新者',
  `update_time`        DATETIME                COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_rule_type_status` (`rule_type`, `status`),
  KEY `idx_rule_device` (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医院设备报警规则';

-- 2. 医院设备报警记录
CREATE TABLE IF NOT EXISTS `hospital_alarm_record` (
  `id`           BIGINT         NOT NULL COMMENT '主键',
  `rule_id`      BIGINT                  COMMENT '触发规则ID(hospital_alarm_rule.id)',
  `device_id`    BIGINT         NOT NULL COMMENT '设备ID(hospital_device.id)',
  `metric_code`  VARCHAR(64)             COMMENT '指标编码',
  `alarm_type`   VARCHAR(16)    NOT NULL COMMENT '报警类型(OVERLOAD过载/OFFLINE离线)',
  `level`        CHAR(1)        DEFAULT '0' COMMENT '报警级别(0一般 1严重 2紧急)',
  `alarm_val`    DECIMAL(18,4)           COMMENT '触发时的指标值',
  `content`      VARCHAR(512)            COMMENT '报警内容描述',
  `status`       CHAR(1)        DEFAULT '0' COMMENT '处理状态(0待处理 1已结束)',
  `start_time`   DATETIME                COMMENT '报警开始时间',
  `end_time`     DATETIME                COMMENT '报警结束时间',
  `handle_by`    VARCHAR(64)             COMMENT '处理人',
  `handle_time`  DATETIME                COMMENT '处理时间',
  `handle_remark` VARCHAR(512)           COMMENT '处理说明',
  `create_time`  DATETIME                COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_record_device_status` (`device_id`, `status`),
  KEY `idx_record_rule_status` (`rule_id`, `status`),
  KEY `idx_record_start` (`start_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医院设备报警记录';
