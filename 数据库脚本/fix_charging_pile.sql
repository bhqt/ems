-- ============================================================
-- 修复 charging_pile 表结构，使其与 ChargesPile 实体类及前端页面一致
-- 数据库表由"物理设备风格"改为"运营风格"(与 charging_station 保持一致)
-- 旧表备份为 charging_pile_bak，可映射字段迁移，其余新字段置空
--
-- 用法: 在目标库(如 autoee_ems)执行本脚本
-- ============================================================

-- 1. 备份旧表
DROP TABLE IF EXISTS `charging_pile_bak`;
CREATE TABLE `charging_pile_bak` LIKE `charging_pile`;
INSERT INTO `charging_pile_bak` SELECT * FROM `charging_pile`;

-- 2. 删除旧表
DROP TABLE IF EXISTS `charging_pile`;

-- 3. 重建表(运营风格, 匹配 ChargingPile 实体)
CREATE TABLE `charging_pile` (
  `pile_id` bigint NOT NULL AUTO_INCREMENT COMMENT '充电桩ID',
  `encoding` varchar(50) NOT NULL COMMENT '终端编码',
  `type` varchar(10) DEFAULT '1' COMMENT '终端类型(1-直流 2-交流)',
  `name` varchar(100) NOT NULL COMMENT '终端名称',
  `merchant_id` varchar(50) DEFAULT NULL COMMENT '归属商户',
  `merchant_name` varchar(100) DEFAULT NULL COMMENT '归属商户名',
  `station_id` bigint DEFAULT NULL COMMENT '归属电站ID',
  `station_name` varchar(100) DEFAULT NULL COMMENT '归属电站名称',
  `brand` varchar(100) DEFAULT NULL COMMENT '品牌',
  `model` varchar(100) DEFAULT NULL COMMENT '型号',
  `status` varchar(1) DEFAULT '0' COMMENT '电桩状态(0正常 1停用)',
  `work_status` varchar(1) DEFAULT NULL COMMENT '工作状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志(0存在 2删除)',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`pile_id`),
  KEY `idx_station_id` (`station_id`),
  KEY `idx_status` (`status`),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='充电桩信息表';

-- 4. 迁移旧表中可映射的数据(pile_code->encoding, pile_name->name 等)
INSERT INTO `charging_pile`
  (`encoding`, `name`, `station_id`, `status`, `remark`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
SELECT
  `pile_code`,
  `pile_name`,
  `station_id`,
  `status`,
  `remark`,
  `del_flag`,
  `create_by`,
  `create_time`,
  `update_by`,
  `update_time`
FROM `charging_pile_bak`;
