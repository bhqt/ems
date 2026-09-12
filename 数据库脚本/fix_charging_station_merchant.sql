-- ============================================================
-- 修复 charging_station 和 merchant 表缺失问题
-- 
-- 问题描述：充电站平台页面报错"系统内部错误"
-- 原因：数据库中缺少 charging_station 和 merchant 表
-- 
-- 用法: 在目标库(如 autoee_ems)执行本脚本
-- ============================================================

-- 1. 创建 merchant 表（商户表）
CREATE TABLE IF NOT EXISTS `merchant` (
  `merchant_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商户ID',
  `name` varchar(100) NOT NULL COMMENT '商户名称',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `avatar` varchar(255) DEFAULT NULL COMMENT '商户头像',
  `type` varchar(10) DEFAULT '0' COMMENT '商户类型：0-平台商户，1-互联商户',
  `status` varchar(1) DEFAULT '0' COMMENT '状态（0-正常 1-停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`merchant_id`),
  KEY `idx_status` (`status`),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户表';

-- 插入初始商户数据
INSERT INTO `merchant` (`merchant_id`, `name`, `contact`, `type`, `status`, `del_flag`, `create_by`, `create_time`)
VALUES 
  (1, '北京充电服务有限公司', '张三', '0', '0', '0', 'admin', NOW()),
  (2, '上海新能源科技有限公司', '李四', '0', '0', '0', 'admin', NOW())
ON DUPLICATE KEY UPDATE `name`=`name`;

-- 2. 创建 charging_station 表（充电站信息表）
CREATE TABLE IF NOT EXISTS `charging_station` (
  `station_id` bigint NOT NULL AUTO_INCREMENT COMMENT '充电站ID',
  `merchant_id` varchar(50) DEFAULT NULL COMMENT '归属商户',
  `merchant_name` varchar(100) DEFAULT NULL COMMENT '归属商户名',
  `type` varchar(10) DEFAULT '0' COMMENT '充电站类型：0-直流，1-互联',
  `station_code` varchar(50) NOT NULL COMMENT '充电站编号',
  `name` varchar(100) DEFAULT NULL COMMENT '充电站名称',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '电站电价',
  `activity` varchar(500) DEFAULT NULL COMMENT '电站活动',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `longitude` decimal(10,6) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(10,6) DEFAULT NULL COMMENT '纬度',
  `total_piles` int DEFAULT '0' COMMENT '充电桩总数',
  `available_piles` int DEFAULT '0' COMMENT '可用充电桩数',
  `power_capacity` decimal(10,2) DEFAULT '0.00' COMMENT '供电容量(kW)',
  `operator` varchar(100) DEFAULT NULL COMMENT '运营商',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `opening_hours` varchar(100) DEFAULT NULL COMMENT '营业时间',
  `facilities` varchar(500) DEFAULT NULL COMMENT '配套设施',
  `status` varchar(1) DEFAULT '0' COMMENT '状态（0-正常 1-停用 2-维护）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`station_id`),
  UNIQUE KEY `uk_station_code` (`station_code`),
  KEY `idx_status` (`status`),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='充电站信息表';

-- 插入初始充电站数据
INSERT INTO `charging_station` (`station_id`, `merchant_id`, `merchant_name`, `type`, `station_code`, `name`, `price`, `address`, `status`, `del_flag`, `create_by`, `create_time`)
VALUES 
  (1, '1', '北京充电服务有限公司', '0', 'CS001', '中心充电站', 0.00, '北京市朝阳区建国路88号', '0', '0', 'admin', NOW()),
  (2, '1', '北京充电服务有限公司', '0', 'CS002', '东区充电站', 0.00, '北京市朝阳区东三环中路39号', '0', '0', 'admin', NOW()),
  (3, '2', '上海新能源科技有限公司', '0', 'CS003', '西区充电站', 0.00, '北京市海淀区中关村大街1号', '0', '0', 'admin', NOW())
ON DUPLICATE KEY UPDATE `name`=`name`;

-- 完成提示
SELECT '修复完成！已创建 merchant 和 charging_station 表，并插入初始数据。' AS result;
