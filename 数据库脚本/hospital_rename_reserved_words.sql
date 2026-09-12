-- =====================================================
-- 修复 MySQL 保留字冲突：condition / level
-- 执行前请备份相关表！
-- 适用库：autoee_ems
-- =====================================================

-- 1. hospital_alarm_rule: condition → rule_condition
ALTER TABLE `hospital_alarm_rule`
  CHANGE COLUMN `condition` `rule_condition` VARCHAR(8) COMMENT '比较条件(G大于/E等于/L小于/GE大于等于/LE小于等于)';

-- 2. hospital_alarm_rule: level → alarm_level
ALTER TABLE `hospital_alarm_rule`
  CHANGE COLUMN `level` `alarm_level` CHAR(1) DEFAULT '0' COMMENT '报警级别(0一般 1严重 2紧急)';

-- 3. hospital_alarm_record: level → alarm_level
ALTER TABLE `hospital_alarm_record`
  CHANGE COLUMN `level` `alarm_level` CHAR(1) DEFAULT '0' COMMENT '报警级别(0一般 1严重 2紧急)';
