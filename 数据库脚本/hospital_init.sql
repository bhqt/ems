-- =====================================================
-- 医院智慧能源决策系统 - 新增表结构（MySQL）
-- 适用库：autoee_ems
-- 说明：加性开发，不影响原有表；MyBatis Plus ddl-auto 亦会自动建表，本脚本用于显式初始化与生产部署。
-- =====================================================

-- 1. 医院检查检验设备台账
CREATE TABLE IF NOT EXISTS `hospital_device` (
  `id`            BIGINT       NOT NULL COMMENT '主键',
  `device_name`   VARCHAR(128) NOT NULL COMMENT '设备名称',
  `device_code`   VARCHAR(64)  NOT NULL COMMENT '设备编号',
  `device_type`   VARCHAR(32)  NOT NULL COMMENT '设备类型(CT/MRI/DR/US/LAB/DSA/OTHER)',
  `model`         VARCHAR(128)          COMMENT '设备型号',
  `manufacturer`  VARCHAR(128)          COMMENT '生产厂商',
  `area_id`       VARCHAR(64)           COMMENT '所属院区(sys_dept)',
  `dept_id`       VARCHAR(64)           COMMENT '所属科室(sys_dept)',
  `iot_device_id` VARCHAR(128)          COMMENT 'IOT平台设备ID(回调关联)',
  `status`        CHAR(1)      DEFAULT '0' COMMENT '设备状态(0正常 1停用 2离线)',
  `remark`        VARCHAR(512)          COMMENT '备注',
  `create_by`     VARCHAR(64)           COMMENT '创建者',
  `create_time`   DATETIME              COMMENT '创建时间',
  `update_by`     VARCHAR(64)           COMMENT '更新者',
  `update_time`   DATETIME              COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_device_code` (`device_code`),
  KEY `idx_iot_device_id` (`iot_device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医院检查检验设备台账';

-- 2. 医院设备指标定义
CREATE TABLE IF NOT EXISTS `hospital_metric_def` (
  `id`          BIGINT       NOT NULL COMMENT '主键',
  `metric_code` VARCHAR(64)  NOT NULL COMMENT '指标编码',
  `metric_name` VARCHAR(128) NOT NULL COMMENT '指标名称',
  `unit`        VARCHAR(32)           COMMENT '指标单位',
  `data_type`   VARCHAR(16)  DEFAULT 'number' COMMENT '数据类型(number/status/string)',
  `high_freq`   CHAR(1)      DEFAULT '0' COMMENT '是否高频(1是 0否)',
  `status`      CHAR(1)      DEFAULT '0' COMMENT '是否启用(0正常 1停用)',
  `remark`      VARCHAR(512)          COMMENT '备注',
  `create_by`   VARCHAR(64)           COMMENT '创建者',
  `create_time` DATETIME              COMMENT '创建时间',
  `update_by`   VARCHAR(64)           COMMENT '更新者',
  `update_time` DATETIME              COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_metric_code` (`metric_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医院设备指标定义';

-- 3. 医院设备数据点（MySQL 落库）
CREATE TABLE IF NOT EXISTS `hospital_device_data` (
  `id`           BIGINT      NOT NULL COMMENT '主键',
  `device_id`    BIGINT      NOT NULL COMMENT '设备ID(hospital_device.id)',
  `metric_code`  VARCHAR(64) NOT NULL COMMENT '指标编码',
  `metric_value` DECIMAL(18,4)         COMMENT '指标值(数值型)',
  `metric_str`   VARCHAR(128)          COMMENT '指标值(状态/字符串型)',
  `ts`           DATETIME             COMMENT '采集时间',
  `quality`      TINYINT     DEFAULT 0 COMMENT '数据质量(0正常 1异常)',
  `receive_time` DATETIME             COMMENT '接收时间',
  PRIMARY KEY (`id`),
  KEY `idx_device_metric_ts` (`device_id`, `metric_code`, `ts`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医院设备数据点';

-- 4. 医院 IOT 回调日志
CREATE TABLE IF NOT EXISTS `hospital_callback_log` (
  `id`           BIGINT       NOT NULL COMMENT '主键',
  `request_id`   VARCHAR(128)          COMMENT '请求ID(IOT消息ID)',
  `source_ip`    VARCHAR(64)           COMMENT '来源IP',
  `iot_timestamp` VARCHAR(64)          COMMENT 'IOT消息时间戳',
  `device_count` INT          DEFAULT 0 COMMENT '设备数量',
  `point_count`  INT          DEFAULT 0 COMMENT '数据点数量',
  `status`       VARCHAR(16)           COMMENT '处理状态(success/auth_fail/parse_fail/fail)',
  `error_msg`    VARCHAR(1024)         COMMENT '错误信息',
  `cost_time`    BIGINT                COMMENT '请求耗时(ms)',
  `receive_time` DATETIME              COMMENT '接收时间',
  PRIMARY KEY (`id`),
  KEY `idx_request_id` (`request_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医院IOT回调日志';

-- 5. 初始化常用指标
INSERT INTO `hospital_metric_def` (`id`, `metric_code`, `metric_name`, `unit`, `data_type`, `high_freq`, `status`, `create_time`) VALUES
  (1, 'power',       '实时功率', 'kW',    'number', '1', '0', NOW()),
  (2, 'electricity', '累计电量', 'kWh',   'number', '0', '0', NOW()),
  (3, 'current',     '电流',     'A',     'number', '1', '0', NOW()),
  (4, 'voltage',     '电压',     'V',     'number', '1', '0', NOW()),
  (5, 'temperature', '温度',     '℃',    'number', '0', '0', NOW()),
  (6, 'run_status',  '运行状态', '1-运行/0-待机', 'status', '0', '0', NOW()),
  (7, 'alarm_status','报警状态', '0-正常/1-告警', 'status', '0', '0', NOW())
ON DUPLICATE KEY UPDATE `metric_name` = VALUES(`metric_name`);
