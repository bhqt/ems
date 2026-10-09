-- ============================================================
-- 修复 charging_price_strategy / charging_price_param 表结构，
-- 使其与 ChargingPriceStrategy / ChargingPriceParam 实体类及
-- 前端页面(zhurong-admin-ui/src/views/charging-station/
-- price-management/charging-strategy/index.vue)一致。
-- 旧表备份为 *_bak，可映射字段迁移，其余新字段置空
--
-- 用法: 在目标库(如 autoee_ems)执行本脚本
-- ============================================================

-- ------------------------------------------------------------
-- 1. charging_price_strategy
--    实体字段: id, strategy_name, station_id, station_name,
--              bill_model, description, status, del_flag, BaseEntity*
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `charging_price_strategy_bak`;
CREATE TABLE `charging_price_strategy_bak` LIKE `charging_price_strategy`;
INSERT INTO `charging_price_strategy_bak` SELECT * FROM `charging_price_strategy`;

DROP TABLE IF EXISTS `charging_price_strategy`;
CREATE TABLE `charging_price_strategy` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '充电价格策略ID',
  `strategy_name` varchar(100) NOT NULL COMMENT '策略名称',
  `station_id` bigint DEFAULT NULL COMMENT '充电站ID',
  `station_name` varchar(100) DEFAULT NULL COMMENT '充电站名称',
  `bill_model` varchar(1) DEFAULT '0' COMMENT '计费模式(0峰平谷模式 1时段模式)',
  `description` varchar(500) DEFAULT NULL COMMENT '充电策略说明',
  `status` varchar(1) DEFAULT '0' COMMENT '策略状态(0未使用 1已使用)',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志(0存在 2删除)',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_strategy_name` (`strategy_name`),
  KEY `idx_station_id` (`station_id`),
  KEY `idx_bill_model` (`bill_model`),
  KEY `idx_status` (`status`),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='充电价格策略表';

-- 迁移可映射数据: strategy_type 1/3 -> bill_model，remark -> description
INSERT INTO `charging_price_strategy`
  (`id`, `strategy_name`, `station_id`, `station_name`, `bill_model`,
   `description`, `status`, `del_flag`, `create_by`, `create_time`,
   `update_by`, `update_time`)
SELECT
  `id`,
  `strategy_name`,
  (SELECT MIN(`station_id`) FROM `charging_station`),
  (SELECT MIN(`name`) FROM `charging_station`),
  CASE `strategy_type` WHEN '1' THEN '0' ELSE '1' END,
  `remark`,
  `status`,
  `del_flag`,
  `create_by`,
  `create_time`,
  `update_by`,
  `update_time`
FROM `charging_price_strategy_bak`;

-- ------------------------------------------------------------
-- 2. charging_price_param
--    实体字段: id, strategy_id, start_time, end_time, mark,
--              elec_price, service_price, del_flag, BaseEntity*
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `charging_price_param_bak`;
CREATE TABLE `charging_price_param_bak` LIKE `charging_price_param`;
INSERT INTO `charging_price_param_bak` SELECT * FROM `charging_price_param`;

DROP TABLE IF EXISTS `charging_price_param`;
CREATE TABLE `charging_price_param` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '充电价格参数ID',
  `strategy_id` bigint DEFAULT NULL COMMENT '充电价格策略ID',
  `start_time` varchar(50) DEFAULT NULL COMMENT '开始时间(HH:mm)',
  `end_time` varchar(50) DEFAULT NULL COMMENT '结束时间(HH:mm)',
  `mark` varchar(1) DEFAULT NULL COMMENT '时段标识(0尖期 1峰期 2平期 3谷期)',
  `elec_price` decimal(10,4) DEFAULT '0.0000' COMMENT '电费单价(元)',
  `service_price` decimal(10,4) DEFAULT '0.0000' COMMENT '服务费单价(元)',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志(0存在 2删除)',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_strategy_id` (`strategy_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_mark` (`mark`),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='充电策略参数表';

-- 迁移可映射数据: start_value/end_value -> start_time/end_time，price/service_fee -> elec_price/service_price
INSERT INTO `charging_price_param`
  (`id`, `strategy_id`, `start_time`, `end_time`, `mark`,
   `elec_price`, `service_price`, `del_flag`, `create_by`, `create_time`,
   `update_by`, `update_time`)
SELECT
  `id`,
  `strategy_id`,
  `start_value`,
  `end_value`,
  `param_type`,
  `price`,
  `service_fee`,
  '0',
  `create_by`,
  `create_time`,
  `update_by`,
  `update_time`
FROM `charging_price_param_bak`;
