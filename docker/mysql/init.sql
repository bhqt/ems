-- ==============================================
-- 数据库初始化脚本
-- ==============================================

-- 创建 sys_oss_config 表
CREATE TABLE IF NOT EXISTS `sys_oss_config` (
  `oss_config_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `config_key` VARCHAR(100) DEFAULT NULL COMMENT '配置key',
  `access_key` VARCHAR(200) DEFAULT NULL COMMENT 'accessKey',
  `secret_key` VARCHAR(200) DEFAULT NULL COMMENT '秘钥',
  `bucket_name` VARCHAR(100) DEFAULT NULL COMMENT '桶名称',
  `prefix` VARCHAR(200) DEFAULT NULL COMMENT '前缀',
  `endpoint` VARCHAR(200) DEFAULT NULL COMMENT '访问站点',
  `domain` VARCHAR(200) DEFAULT NULL COMMENT '自定义域名',
  `is_https` VARCHAR(1) DEFAULT '0' COMMENT '是否https（0否 1是）',
  `region` VARCHAR(100) DEFAULT NULL COMMENT '域',
  `status` VARCHAR(1) DEFAULT '0' COMMENT '是否默认（0=是,1=否）',
  `ext1` VARCHAR(200) DEFAULT NULL COMMENT '扩展字段',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `access_policy` VARCHAR(10) DEFAULT '0' COMMENT '桶权限类型(0private 1public 2custom)',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`oss_config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='对象存储配置表';

-- 插入默认数据
INSERT INTO `sys_oss_config` (`oss_config_id`, `config_key`, `access_key`, `secret_key`, `bucket_name`, `prefix`, `endpoint`, `domain`, `is_https`, `region`, `status`, `ext1`, `remark`, `access_policy`, `create_by`, `create_time`, `update_by`, `update_time`) 
VALUES (1, 'local', '', '', 'local', '', '', '', '0', '', '1', '', '本地存储', '0', 'admin', NOW(), 'admin', NOW());

-- 创建 sys_user 表
CREATE TABLE IF NOT EXISTS `sys_user` (
  `user_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` BIGINT(20) DEFAULT NULL COMMENT '部门ID',
  `user_name` VARCHAR(30) NOT NULL COMMENT '用户账号',
  `nick_name` VARCHAR(30) NOT NULL COMMENT '用户昵称',
  `email` VARCHAR(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` VARCHAR(11) DEFAULT '' COMMENT '手机号码',
  `sex` CHAR(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` VARCHAR(100) DEFAULT '' COMMENT '头像地址',
  `password` VARCHAR(100) DEFAULT '' COMMENT '密码',
  `status` CHAR(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` VARCHAR(50) DEFAULT '' COMMENT '最后登录IP',
  `login_date` DATETIME DEFAULT NULL COMMENT '最后登录时间',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';

-- 创建默认管理员用户
INSERT INTO `sys_user` (`user_id`, `user_name`, `nick_name`, `email`, `phonenumber`, `sex`, `avatar`, `password`, `status`, `del_flag`, `create_by`, `create_time`) 
VALUES (1, 'admin', '管理员', 'admin@example.com', '13800138000', '0', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOJ6eYvV2V3v3V8V3v3V8V3v3V8V3', '0', '0', 'admin', NOW()) ON DUPLICATE KEY UPDATE user_name=user_name;

-- 创建 sys_dept 表
CREATE TABLE IF NOT EXISTS `sys_dept` (
  `dept_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `parent_id` BIGINT(20) DEFAULT '0' COMMENT '父部门ID',
  `ancestors` VARCHAR(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` VARCHAR(30) NOT NULL COMMENT '部门名称',
  `order_num` INT(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` VARCHAR(20) DEFAULT NULL COMMENT '负责人',
  `phone` VARCHAR(11) DEFAULT NULL COMMENT '联系电话',
  `email` VARCHAR(50) DEFAULT NULL COMMENT '邮箱',
  `status` CHAR(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门表';

-- 创建默认部门
INSERT INTO `sys_dept` (`dept_id`, `parent_id`, `ancestors`, `dept_name`, `order_num`, `status`, `create_by`, `create_time`) 
VALUES (1, 0, '0', '系统管理部', 1, '0', 'admin', NOW()) ON DUPLICATE KEY UPDATE dept_name=dept_name;

-- 创建 sys_role 表
CREATE TABLE IF NOT EXISTS `sys_role` (
  `role_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` VARCHAR(30) NOT NULL COMMENT '角色名称',
  `role_key` VARCHAR(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` INT(4) DEFAULT '0' COMMENT '显示顺序',
  `data_scope` CHAR(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` CHAR(1) DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `del_flag` CHAR(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色信息表';

-- 创建默认管理员角色
INSERT INTO `sys_role` (`role_id`, `role_name`, `role_key`, `role_sort`, `data_scope`, `status`, `create_by`, `create_time`) 
VALUES (1, '管理员', 'admin', 1, '1', '0', 'admin', NOW()) ON DUPLICATE KEY UPDATE role_name=role_name;

-- 创建 sys_user_role 表
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户和角色关联表';

-- 关联管理员用户和管理员角色
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (1, 1) ON DUPLICATE KEY UPDATE user_id=user_id;

-- 创建 sys_menu 表
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `menu_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` VARCHAR(50) NOT NULL COMMENT '菜单名称',
  `parent_id` BIGINT(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` INT(4) DEFAULT '0' COMMENT '显示顺序',
  `path` VARCHAR(200) DEFAULT '' COMMENT '路由地址',
  `component` VARCHAR(255) DEFAULT '' COMMENT '组件路径',
  `query` VARCHAR(255) DEFAULT '' COMMENT '路由参数',
  `is_frame` CHAR(1) DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` CHAR(1) DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` CHAR(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` CHAR(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` CHAR(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` VARCHAR(100) DEFAULT '' COMMENT '权限标识',
  `icon` VARCHAR(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  `remark` VARCHAR(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单权限表';

-- 创建 sys_role_menu 表
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
  `menu_id` BIGINT(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色和菜单关联表';

-- 创建 sys_dict_type 表
CREATE TABLE IF NOT EXISTS `sys_dict_type` (
  `dict_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` VARCHAR(100) NOT NULL COMMENT '字典名称',
  `dict_type` VARCHAR(100) NOT NULL COMMENT '字典类型',
  `status` CHAR(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典类型表';

-- 创建 sys_dict_data 表
CREATE TABLE IF NOT EXISTS `sys_dict_data` (
  `dict_code` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` INT(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` VARCHAR(100) NOT NULL COMMENT '字典标签',
  `dict_value` VARCHAR(100) NOT NULL COMMENT '字典键值',
  `dict_type` VARCHAR(100) NOT NULL COMMENT '字典类型',
  `css_class` VARCHAR(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` VARCHAR(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` CHAR(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` CHAR(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典数据表';

-- 创建 sys_config 表
CREATE TABLE IF NOT EXISTS `sys_config` (
  `config_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` VARCHAR(100) NOT NULL COMMENT '参数名称',
  `config_key` VARCHAR(100) NOT NULL COMMENT '参数键名',
  `config_value` VARCHAR(500) NOT NULL COMMENT '参数键值',
  `config_type` CHAR(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='参数配置表';

-- 创建 sys_notice 表
CREATE TABLE IF NOT EXISTS `sys_notice` (
  `notice_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` VARCHAR(50) NOT NULL COMMENT '公告标题',
  `notice_type` CHAR(1) DEFAULT '1' COMMENT '公告类型（1通知 2公告）',
  `notice_content` LONGTEXT COMMENT '公告内容',
  `status` CHAR(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='公告表';

-- 创建 sys_logininfor 表
CREATE TABLE IF NOT EXISTS `sys_logininfor` (
  `info_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` VARCHAR(50) DEFAULT '' COMMENT '用户账号',
  `ip_addr` VARCHAR(50) DEFAULT '' COMMENT '登录IP地址',
  `login_location` VARCHAR(255) DEFAULT '' COMMENT '登录地点',
  `browser` VARCHAR(50) DEFAULT '' COMMENT '浏览器类型',
  `os` VARCHAR(50) DEFAULT '' COMMENT '操作系统',
  `status` CHAR(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` VARCHAR(255) DEFAULT '' COMMENT '提示消息',
  `login_time` DATETIME DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统访问记录';

-- 创建 sys_oper_log 表
CREATE TABLE IF NOT EXISTS `sys_oper_log` (
  `oper_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` VARCHAR(50) DEFAULT '' COMMENT '模块标题',
  `business_type` INT(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` VARCHAR(100) DEFAULT '' COMMENT '方法名称',
  `request_method` VARCHAR(10) DEFAULT '' COMMENT '请求方式',
  `operator_type` INT(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` VARCHAR(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` VARCHAR(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` VARCHAR(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` VARCHAR(50) DEFAULT '' COMMENT '主机地址',
  `oper_location` VARCHAR(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` VARCHAR(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` VARCHAR(2000) DEFAULT '' COMMENT '返回参数',
  `status` INT(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` VARCHAR(2000) DEFAULT '' COMMENT '错误信息',
  `oper_time` DATETIME DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志记录';

-- 创建 sys_post 表
CREATE TABLE IF NOT EXISTS `sys_post` (
  `post_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` VARCHAR(64) NOT NULL COMMENT '岗位编码',
  `post_name` VARCHAR(50) NOT NULL COMMENT '岗位名称',
  `post_sort` INT(4) DEFAULT '0' COMMENT '显示顺序',
  `status` CHAR(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='岗位信息表';

-- 创建 sys_user_post 表
CREATE TABLE IF NOT EXISTS `sys_user_post` (
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `post_id` BIGINT(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户和岗位关联表';

-- 创建 sys_job 表
CREATE TABLE IF NOT EXISTS `sys_job` (
  `job_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` VARCHAR(64) NOT NULL COMMENT '任务名称',
  `job_group` VARCHAR(64) DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` VARCHAR(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` VARCHAR(128) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` VARCHAR(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` CHAR(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` CHAR(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='定时任务调度表';

-- 创建 sys_job_log 表
CREATE TABLE IF NOT EXISTS `sys_job_log` (
  `job_log_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` VARCHAR(64) NOT NULL COMMENT '任务名称',
  `job_group` VARCHAR(64) NOT NULL COMMENT '任务组名',
  `invoke_target` VARCHAR(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` VARCHAR(500) DEFAULT '' COMMENT '日志信息',
  `status` CHAR(1) DEFAULT '0' COMMENT '执行状态（0成功 1失败）',
  `error_msg` VARCHAR(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='定时任务调度日志表';

-- 创建 sys_alarm_rule 表
CREATE TABLE IF NOT EXISTS `sys_alarm_rule` (
  `rule_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '告警规则ID',
  `rule_name` VARCHAR(100) NOT NULL COMMENT '规则名称',
  `rule_type` VARCHAR(50) NOT NULL COMMENT '规则类型',
  `threshold` DECIMAL(10,2) DEFAULT NULL COMMENT '阈值',
  `comparison_operator` VARCHAR(10) DEFAULT '>' COMMENT '比较运算符',
  `enabled` CHAR(1) DEFAULT '1' COMMENT '是否启用',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='告警规则表';

-- 创建 sys_alarm_record 表
CREATE TABLE IF NOT EXISTS `sys_alarm_record` (
  `record_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '告警记录ID',
  `rule_id` BIGINT(20) NOT NULL COMMENT '规则ID',
  `device_id` VARCHAR(100) DEFAULT NULL COMMENT '设备ID',
  `alarm_value` DECIMAL(10,2) DEFAULT NULL COMMENT '告警值',
  `alarm_level` VARCHAR(20) DEFAULT 'MEDIUM' COMMENT '告警级别',
  `alarm_time` DATETIME DEFAULT NULL COMMENT '告警时间',
  `status` VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '告警状态',
  `ack_time` DATETIME DEFAULT NULL COMMENT '确认时间',
  `ack_user` VARCHAR(64) DEFAULT '' COMMENT '确认人',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='告警记录表';

-- 创建 sys_device 表
CREATE TABLE IF NOT EXISTS `sys_device` (
  `device_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '设备ID',
  `device_name` VARCHAR(100) NOT NULL COMMENT '设备名称',
  `device_code` VARCHAR(100) NOT NULL COMMENT '设备编码',
  `device_type` VARCHAR(50) DEFAULT NULL COMMENT '设备类型',
  `location` VARCHAR(200) DEFAULT NULL COMMENT '安装位置',
  `status` VARCHAR(20) DEFAULT 'ONLINE' COMMENT '设备状态',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备信息表';

-- 创建 sys_data_collect 表
CREATE TABLE IF NOT EXISTS `sys_data_collect` (
  `collect_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '采集ID',
  `device_id` BIGINT(20) NOT NULL COMMENT '设备ID',
  `data_type` VARCHAR(50) NOT NULL COMMENT '数据类型',
  `data_value` DECIMAL(18,4) DEFAULT NULL COMMENT '数据值',
  `collect_time` DATETIME DEFAULT NULL COMMENT '采集时间',
  PRIMARY KEY (`collect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据采集表';

-- 创建 sys_quota 表
CREATE TABLE IF NOT EXISTS `sys_quota` (
  `quota_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '指标ID',
  `quota_name` VARCHAR(100) NOT NULL COMMENT '指标名称',
  `quota_code` VARCHAR(100) NOT NULL COMMENT '指标编码',
  `unit` VARCHAR(20) DEFAULT '' COMMENT '单位',
  `data_type` VARCHAR(20) DEFAULT 'NUMERIC' COMMENT '数据类型',
  `enabled` CHAR(1) DEFAULT '1' COMMENT '是否启用',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`quota_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='指标配置表';

-- 创建 sys_task 表
CREATE TABLE IF NOT EXISTS `sys_task` (
  `task_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `task_name` VARCHAR(100) NOT NULL COMMENT '任务名称',
  `task_type` VARCHAR(50) DEFAULT NULL COMMENT '任务类型',
  `status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '任务状态',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务表';

-- 创建 contract_info 表
CREATE TABLE IF NOT EXISTS `contract_info` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '合同ID',
  `contract_no` VARCHAR(100) NOT NULL COMMENT '合同编号',
  `contract_name` VARCHAR(200) NOT NULL COMMENT '合同名称',
  `customer_name` VARCHAR(100) DEFAULT NULL COMMENT '客户名称',
  `contract_amount` DECIMAL(18,2) DEFAULT NULL COMMENT '合同金额',
  `sign_date` DATE DEFAULT NULL COMMENT '签订日期',
  `start_date` DATE DEFAULT NULL COMMENT '开始日期',
  `end_date` DATE DEFAULT NULL COMMENT '结束日期',
  `status` VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '合同状态',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同信息表';

-- 创建 goods_info 表
CREATE TABLE IF NOT EXISTS `goods_info` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_code` VARCHAR(100) NOT NULL COMMENT '商品编码',
  `goods_name` VARCHAR(200) NOT NULL COMMENT '商品名称',
  `specification` VARCHAR(200) DEFAULT NULL COMMENT '规格型号',
  `unit` VARCHAR(20) DEFAULT '' COMMENT '单位',
  `price` DECIMAL(10,2) DEFAULT NULL COMMENT '单价',
  `category` VARCHAR(50) DEFAULT NULL COMMENT '商品类别',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品信息表';

-- 创建 goods_inventory 表
CREATE TABLE IF NOT EXISTS `goods_inventory` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '库存ID',
  `goods_id` BIGINT(20) NOT NULL COMMENT '商品ID',
  `warehouse_id` BIGINT(20) DEFAULT NULL COMMENT '仓库ID',
  `quantity` DECIMAL(18,4) DEFAULT '0' COMMENT '库存数量',
  `min_quantity` DECIMAL(18,4) DEFAULT '0' COMMENT '最低库存',
  `max_quantity` DECIMAL(18,4) DEFAULT '0' COMMENT '最高库存',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品库存表';

-- 创建 goods_stock_in 表
CREATE TABLE IF NOT EXISTS `goods_stock_in` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '入库ID',
  `stock_in_no` VARCHAR(100) NOT NULL COMMENT '入库单号',
  `goods_id` BIGINT(20) NOT NULL COMMENT '商品ID',
  `quantity` DECIMAL(18,4) NOT NULL COMMENT '入库数量',
  `unit_price` DECIMAL(10,2) DEFAULT NULL COMMENT '单价',
  `total_amount` DECIMAL(18,2) DEFAULT NULL COMMENT '总金额',
  `stock_in_date` DATETIME DEFAULT NULL COMMENT '入库日期',
  `supplier` VARCHAR(100) DEFAULT NULL COMMENT '供应商',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品入库表';

-- 创建 goods_stock_out 表
CREATE TABLE IF NOT EXISTS `goods_stock_out` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '出库ID',
  `stock_out_no` VARCHAR(100) NOT NULL COMMENT '出库单号',
  `goods_id` BIGINT(20) NOT NULL COMMENT '商品ID',
  `quantity` DECIMAL(18,4) NOT NULL COMMENT '出库数量',
  `unit_price` DECIMAL(10,2) DEFAULT NULL COMMENT '单价',
  `total_amount` DECIMAL(18,2) DEFAULT NULL COMMENT '总金额',
  `stock_out_date` DATETIME DEFAULT NULL COMMENT '出库日期',
  `customer` VARCHAR(100) DEFAULT NULL COMMENT '客户',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品出库表';

-- 创建 danger_goods_info 表
CREATE TABLE IF NOT EXISTS `danger_goods_info` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '危险品ID',
  `goods_code` VARCHAR(100) NOT NULL COMMENT '危险品编码',
  `goods_name` VARCHAR(200) NOT NULL COMMENT '危险品名称',
  `cas_number` VARCHAR(50) DEFAULT NULL COMMENT 'CAS编号',
  `danger_level` VARCHAR(20) DEFAULT NULL COMMENT '危险等级',
  `un_number` VARCHAR(20) DEFAULT NULL COMMENT 'UN编号',
  `packing_group` VARCHAR(20) DEFAULT NULL COMMENT '包装类别',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='危险品信息表';

-- 创建 danger_goods_inventory 表
CREATE TABLE IF NOT EXISTS `danger_goods_inventory` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '危险品库存ID',
  `goods_id` BIGINT(20) NOT NULL COMMENT '危险品ID',
  `warehouse_id` BIGINT(20) DEFAULT NULL COMMENT '仓库ID',
  `quantity` DECIMAL(18,4) DEFAULT '0' COMMENT '库存数量',
  `location` VARCHAR(200) DEFAULT NULL COMMENT '存放位置',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='危险品库存表';

-- 创建 danger_goods_stock_in 表
CREATE TABLE IF NOT EXISTS `danger_goods_stock_in` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '危险品入库ID',
  `stock_in_no` VARCHAR(100) NOT NULL COMMENT '入库单号',
  `goods_id` BIGINT(20) NOT NULL COMMENT '危险品ID',
  `quantity` DECIMAL(18,4) NOT NULL COMMENT '入库数量',
  `unit_price` DECIMAL(10,2) DEFAULT NULL COMMENT '单价',
  `total_amount` DECIMAL(18,2) DEFAULT NULL COMMENT '总金额',
  `stock_in_date` DATETIME DEFAULT NULL COMMENT '入库日期',
  `supplier` VARCHAR(100) DEFAULT NULL COMMENT '供应商',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='危险品入库表';

-- 创建 danger_goods_stock_out 表
CREATE TABLE IF NOT EXISTS `danger_goods_stock_out` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '危险品出库ID',
  `stock_out_no` VARCHAR(100) NOT NULL COMMENT '出库单号',
  `goods_id` BIGINT(20) NOT NULL COMMENT '危险品ID',
  `quantity` DECIMAL(18,4) NOT NULL COMMENT '出库数量',
  `unit_price` DECIMAL(10,2) DEFAULT NULL COMMENT '单价',
  `total_amount` DECIMAL(18,2) DEFAULT NULL COMMENT '总金额',
  `stock_out_date` DATETIME DEFAULT NULL COMMENT '出库日期',
  `customer` VARCHAR(100) DEFAULT NULL COMMENT '客户',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='危险品出库表';

-- 创建 patrol_task 表
CREATE TABLE IF NOT EXISTS `patrol_task` (
  `task_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '巡检任务ID',
  `task_name` VARCHAR(100) NOT NULL COMMENT '任务名称',
  `task_type` VARCHAR(50) DEFAULT 'DAILY' COMMENT '任务类型',
  `device_ids` TEXT DEFAULT NULL COMMENT '设备ID列表（JSON）',
  `status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '任务状态',
  `assigned_to` VARCHAR(64) DEFAULT '' COMMENT '分配人',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='巡检任务表';

-- 创建 patrol_record 表
CREATE TABLE IF NOT EXISTS `patrol_record` (
  `record_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '巡检记录ID',
  `task_id` BIGINT(20) NOT NULL COMMENT '任务ID',
  `device_id` BIGINT(20) NOT NULL COMMENT '设备ID',
  `patrol_time` DATETIME DEFAULT NULL COMMENT '巡检时间',
  `status` VARCHAR(20) DEFAULT 'NORMAL' COMMENT '巡检状态',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `patrol_by` VARCHAR(64) DEFAULT '' COMMENT '巡检人',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='巡检记录表';

-- 创建 report_task 表
CREATE TABLE IF NOT EXISTS `report_task` (
  `task_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '报表任务ID',
  `task_name` VARCHAR(100) NOT NULL COMMENT '任务名称',
  `report_type` VARCHAR(50) NOT NULL COMMENT '报表类型',
  `cron_expression` VARCHAR(128) DEFAULT '' COMMENT '定时表达式',
  `enabled` CHAR(1) DEFAULT '1' COMMENT '是否启用',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报表任务表';

-- 创建 sample_data 表
CREATE TABLE IF NOT EXISTS `sample_data` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '采样数据ID',
  `device_id` VARCHAR(100) NOT NULL COMMENT '设备ID',
  `data_type` VARCHAR(50) NOT NULL COMMENT '数据类型',
  `data_value` DECIMAL(18,4) DEFAULT NULL COMMENT '数据值',
  `sample_time` DATETIME DEFAULT NULL COMMENT '采样时间',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='采样数据表';

-- 创建 statistics_data 表
CREATE TABLE IF NOT EXISTS `statistics_data` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '统计数据ID',
  `device_id` VARCHAR(100) NOT NULL COMMENT '设备ID',
  `data_type` VARCHAR(50) NOT NULL COMMENT '数据类型',
  `statistics_type` VARCHAR(20) DEFAULT 'HOUR' COMMENT '统计类型',
  `start_time` DATETIME NOT NULL COMMENT '开始时间',
  `end_time` DATETIME NOT NULL COMMENT '结束时间',
  `data_value` DECIMAL(18,4) DEFAULT NULL COMMENT '统计值',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='统计数据表';

-- 创建 electricity_data 表
CREATE TABLE IF NOT EXISTS `electricity_data` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用电数据ID',
  `device_id` VARCHAR(100) NOT NULL COMMENT '设备ID',
  `voltage` DECIMAL(10,2) DEFAULT NULL COMMENT '电压',
  `current` DECIMAL(10,2) DEFAULT NULL COMMENT '电流',
  `power` DECIMAL(12,4) DEFAULT NULL COMMENT '功率',
  `energy` DECIMAL(12,4) DEFAULT NULL COMMENT '电能',
  `frequency` DECIMAL(10,2) DEFAULT NULL COMMENT '频率',
  `power_factor` DECIMAL(5,4) DEFAULT NULL COMMENT '功率因数',
  `sample_time` DATETIME DEFAULT NULL COMMENT '采样时间',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用电数据表';

-- 创建 water_data 表
CREATE TABLE IF NOT EXISTS `water_data` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用水数据ID',
  `device_id` VARCHAR(100) NOT NULL COMMENT '设备ID',
  `flow` DECIMAL(10,4) DEFAULT NULL COMMENT '流量',
  `pressure` DECIMAL(10,2) DEFAULT NULL COMMENT '压力',
  `volume` DECIMAL(12,4) DEFAULT NULL COMMENT '水量',
  `sample_time` DATETIME DEFAULT NULL COMMENT '采样时间',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用水数据表';

-- 创建 energy_statistics 表
CREATE TABLE IF NOT EXISTS `energy_statistics` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '能源统计ID',
  `type` VARCHAR(20) DEFAULT 'ELECTRICITY' COMMENT '能源类型',
  `period_type` VARCHAR(20) DEFAULT 'DAILY' COMMENT '周期类型',
  `period_start` DATE NOT NULL COMMENT '周期开始',
  `period_end` DATE NOT NULL COMMENT '周期结束',
  `total_consumption` DECIMAL(18,4) DEFAULT NULL COMMENT '总消耗量',
  `unit` VARCHAR(20) DEFAULT '' COMMENT '单位',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='能源统计表';

-- 创建 camera_config 表
CREATE TABLE IF NOT EXISTS `camera_config` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '摄像头配置ID',
  `camera_name` VARCHAR(100) NOT NULL COMMENT '摄像头名称',
  `ip_address` VARCHAR(50) NOT NULL COMMENT 'IP地址',
  `port` INT(5) DEFAULT '554' COMMENT '端口',
  `username` VARCHAR(50) DEFAULT '' COMMENT '用户名',
  `password` VARCHAR(100) DEFAULT '' COMMENT '密码',
  `channel` INT(3) DEFAULT '1' COMMENT '通道号',
  `status` VARCHAR(20) DEFAULT 'OFFLINE' COMMENT '状态',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='摄像头配置表';

-- 创建 forecast_model_config 表
CREATE TABLE IF NOT EXISTS `forecast_model_config` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '预测模型配置ID',
  `model_name` VARCHAR(100) NOT NULL COMMENT '模型名称',
  `model_type` VARCHAR(50) DEFAULT NULL COMMENT '模型类型',
  `config_params` TEXT DEFAULT NULL COMMENT '配置参数（JSON）',
  `enabled` CHAR(1) DEFAULT '1' COMMENT '是否启用',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预测模型配置表';

-- 创建 integration_config 表
CREATE TABLE IF NOT EXISTS `integration_config` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '集成配置ID',
  `config_name` VARCHAR(100) NOT NULL COMMENT '配置名称',
  `integration_type` VARCHAR(50) NOT NULL COMMENT '集成类型',
  `config_params` TEXT DEFAULT NULL COMMENT '配置参数（JSON）',
  `enabled` CHAR(1) DEFAULT '1' COMMENT '是否启用',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='集成配置表';

-- 创建 interface_config 表
CREATE TABLE IF NOT EXISTS `interface_config` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '接口配置ID',
  `interface_name` VARCHAR(100) NOT NULL COMMENT '接口名称',
  `interface_code` VARCHAR(100) NOT NULL COMMENT '接口编码',
  `api_url` VARCHAR(200) DEFAULT '' COMMENT 'API地址',
  `http_method` VARCHAR(10) DEFAULT 'POST' COMMENT 'HTTP方法',
  `request_params` TEXT DEFAULT NULL COMMENT '请求参数（JSON）',
  `enabled` CHAR(1) DEFAULT '1' COMMENT '是否启用',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='接口配置表';

-- 创建 sys_sms_record 表
CREATE TABLE IF NOT EXISTS `sys_sms_record` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '短信记录ID',
  `phone` VARCHAR(20) NOT NULL COMMENT '手机号码',
  `content` VARCHAR(500) DEFAULT '' COMMENT '短信内容',
  `status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '发送状态',
  `send_time` DATETIME DEFAULT NULL COMMENT '发送时间',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='短信记录表';

-- 创建 sys_mail_record 表
CREATE TABLE IF NOT EXISTS `sys_mail_record` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '邮件记录ID',
  `to_email` VARCHAR(100) NOT NULL COMMENT '收件人邮箱',
  `subject` VARCHAR(200) DEFAULT '' COMMENT '邮件主题',
  `content` TEXT DEFAULT NULL COMMENT '邮件内容',
  `status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '发送状态',
  `send_time` DATETIME DEFAULT NULL COMMENT '发送时间',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='邮件记录表';

-- 创建 sys_file_record 表
CREATE TABLE IF NOT EXISTS `sys_file_record` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '文件记录ID',
  `file_name` VARCHAR(200) NOT NULL COMMENT '文件名',
  `file_path` VARCHAR(500) NOT NULL COMMENT '文件路径',
  `file_size` BIGINT(20) DEFAULT '0' COMMENT '文件大小',
  `file_type` VARCHAR(50) DEFAULT '' COMMENT '文件类型',
  `upload_by` VARCHAR(64) DEFAULT '' COMMENT '上传者',
  `upload_time` DATETIME DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件记录表';

-- 创建 sys_message 表
CREATE TABLE IF NOT EXISTS `sys_message` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `message_type` VARCHAR(20) DEFAULT 'SYSTEM' COMMENT '消息类型',
  `title` VARCHAR(100) DEFAULT '' COMMENT '消息标题',
  `content` VARCHAR(500) DEFAULT '' COMMENT '消息内容',
  `status` VARCHAR(20) DEFAULT 'UNREAD' COMMENT '消息状态',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息表';

-- 创建 sys_online 表
CREATE TABLE IF NOT EXISTS `sys_online` (
  `session_id` VARCHAR(64) NOT NULL COMMENT '会话ID',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
  `user_name` VARCHAR(50) DEFAULT '' COMMENT '用户名称',
  `ip_addr` VARCHAR(50) DEFAULT '' COMMENT '登录IP地址',
  `login_location` VARCHAR(255) DEFAULT '' COMMENT '登录地点',
  `browser` VARCHAR(50) DEFAULT '' COMMENT '浏览器类型',
  `os` VARCHAR(50) DEFAULT '' COMMENT '操作系统',
  `status` VARCHAR(10) DEFAULT 'online' COMMENT '在线状态',
  `start_timestamp` DATETIME DEFAULT NULL COMMENT '会话开始时间',
  `last_access_time` DATETIME DEFAULT NULL COMMENT '最后访问时间',
  `expire_time` DATETIME DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='在线用户表';

-- 创建 sys_log 表
CREATE TABLE IF NOT EXISTS `sys_log` (
  `log_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
  `user_name` VARCHAR(50) DEFAULT '' COMMENT '用户名',
  `operation` VARCHAR(100) DEFAULT '' COMMENT '操作内容',
  `method` VARCHAR(100) DEFAULT '' COMMENT '操作方法',
  `params` VARCHAR(2000) DEFAULT '' COMMENT '请求参数',
  `ip` VARCHAR(50) DEFAULT '' COMMENT 'IP地址',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统日志表';

-- 创建 sys_captcha 表
CREATE TABLE IF NOT EXISTS `sys_captcha` (
  `uuid` VARCHAR(36) NOT NULL COMMENT '唯一标识',
  `code` VARCHAR(10) NOT NULL COMMENT '验证码',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='验证码表';

-- 创建 sys_schedule_log 表
CREATE TABLE IF NOT EXISTS `sys_schedule_log` (
  `log_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `job_id` BIGINT(20) DEFAULT NULL COMMENT '任务ID',
  `job_name` VARCHAR(64) DEFAULT '' COMMENT '任务名称',
  `job_group` VARCHAR(64) DEFAULT '' COMMENT '任务组名',
  `status` VARCHAR(20) DEFAULT 'RUNNING' COMMENT '执行状态',
  `message` VARCHAR(500) DEFAULT '' COMMENT '执行信息',
  `start_time` DATETIME DEFAULT NULL COMMENT '开始时间',
  `end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='调度日志表';

-- 创建 sys_datasource 表
CREATE TABLE IF NOT EXISTS `sys_datasource` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '数据源ID',
  `name` VARCHAR(100) NOT NULL COMMENT '数据源名称',
  `type` VARCHAR(20) DEFAULT 'MYSQL' COMMENT '数据源类型',
  `url` VARCHAR(500) NOT NULL COMMENT '连接URL',
  `username` VARCHAR(50) DEFAULT '' COMMENT '用户名',
  `password` VARCHAR(100) DEFAULT '' COMMENT '密码',
  `driver_class` VARCHAR(100) DEFAULT '' COMMENT '驱动类',
  `enabled` CHAR(1) DEFAULT '1' COMMENT '是否启用',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据源配置表';

-- 创建 sys_cache 表
CREATE TABLE IF NOT EXISTS `sys_cache` (
  `cache_key` VARCHAR(255) NOT NULL COMMENT '缓存键',
  `cache_value` TEXT DEFAULT NULL COMMENT '缓存值',
  `expire_time` DATETIME DEFAULT NULL COMMENT '过期时间',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`cache_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='缓存表';

-- 创建 sys_sequence 表
CREATE TABLE IF NOT EXISTS `sys_sequence` (
  `seq_name` VARCHAR(100) NOT NULL COMMENT '序列名称',
  `current_value` BIGINT(20) DEFAULT '1' COMMENT '当前值',
  `increment` INT(11) DEFAULT '1' COMMENT '增量',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='序列表';

-- 创建 sys_lock 表
CREATE TABLE IF NOT EXISTS `sys_lock` (
  `lock_name` VARCHAR(100) NOT NULL COMMENT '锁名称',
  `lock_value` VARCHAR(100) DEFAULT '' COMMENT '锁值',
  `expire_time` DATETIME DEFAULT NULL COMMENT '过期时间',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分布式锁表';

-- 创建 sys_config_ext 表
CREATE TABLE IF NOT EXISTS `sys_config_ext` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '扩展配置ID',
  `config_key` VARCHAR(100) NOT NULL COMMENT '配置键',
  `config_value` TEXT DEFAULT NULL COMMENT '配置值',
  `config_desc` VARCHAR(500) DEFAULT '' COMMENT '配置描述',
  `config_group` VARCHAR(50) DEFAULT 'DEFAULT' COMMENT '配置分组',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='扩展配置表';

-- 创建 sys_metrics 表
CREATE TABLE IF NOT EXISTS `sys_metrics` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '指标ID',
  `metrics_key` VARCHAR(100) NOT NULL COMMENT '指标键',
  `metrics_value` DECIMAL(18,4) DEFAULT '0' COMMENT '指标值',
  `metrics_desc` VARCHAR(500) DEFAULT '' COMMENT '指标描述',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统指标表';

-- 创建 sys_audit 表
CREATE TABLE IF NOT EXISTS `sys_audit` (
  `audit_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '审计ID',
  `audit_type` VARCHAR(50) NOT NULL COMMENT '审计类型',
  `audit_target` VARCHAR(200) DEFAULT '' COMMENT '审计目标',
  `audit_content` TEXT DEFAULT NULL COMMENT '审计内容',
  `audit_result` VARCHAR(20) DEFAULT 'SUCCESS' COMMENT '审计结果',
  `audit_by` VARCHAR(64) DEFAULT '' COMMENT '审计人',
  `audit_time` DATETIME DEFAULT NULL COMMENT '审计时间',
  PRIMARY KEY (`audit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='审计记录表';

-- 创建 sys_notify 表
CREATE TABLE IF NOT EXISTS `sys_notify` (
  `notify_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `notify_type` VARCHAR(20) DEFAULT 'SYSTEM' COMMENT '通知类型',
  `notify_title` VARCHAR(100) DEFAULT '' COMMENT '通知标题',
  `notify_content` VARCHAR(500) DEFAULT '' COMMENT '通知内容',
  `notify_target` VARCHAR(200) DEFAULT '' COMMENT '通知目标',
  `notify_status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '通知状态',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `send_time` DATETIME DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`notify_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知表';

-- 创建 sys_workflow 表
CREATE TABLE IF NOT EXISTS `sys_workflow` (
  `workflow_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '工作流ID',
  `workflow_name` VARCHAR(100) NOT NULL COMMENT '工作流名称',
  `workflow_key` VARCHAR(100) NOT NULL COMMENT '工作流标识',
  `workflow_desc` VARCHAR(500) DEFAULT '' COMMENT '工作流描述',
  `workflow_status` VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '工作流状态',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`workflow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作流表';

-- 创建 sys_workflow_instance 表
CREATE TABLE IF NOT EXISTS `sys_workflow_instance` (
  `instance_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '流程实例ID',
  `workflow_id` BIGINT(20) NOT NULL COMMENT '工作流ID',
  `business_key` VARCHAR(200) DEFAULT '' COMMENT '业务键',
  `instance_status` VARCHAR(20) DEFAULT 'RUNNING' COMMENT '实例状态',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作流实例表';

-- 创建 sys_workflow_task 表
CREATE TABLE IF NOT EXISTS `sys_workflow_task` (
  `task_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `instance_id` BIGINT(20) NOT NULL COMMENT '流程实例ID',
  `task_name` VARCHAR(100) DEFAULT '' COMMENT '任务名称',
  `task_type` VARCHAR(20) DEFAULT 'APPROVE' COMMENT '任务类型',
  `assignee` VARCHAR(64) DEFAULT '' COMMENT '处理人',
  `task_status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '任务状态',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `complete_time` DATETIME DEFAULT NULL COMMENT '完成时间',
  `comment` VARCHAR(500) DEFAULT '' COMMENT '处理意见',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作流任务表';

-- 创建 sys_api_log 表
CREATE TABLE IF NOT EXISTS `sys_api_log` (
  `log_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'API日志ID',
  `api_path` VARCHAR(200) DEFAULT '' COMMENT 'API路径',
  `http_method` VARCHAR(10) DEFAULT '' COMMENT 'HTTP方法',
  `request_params` TEXT DEFAULT NULL COMMENT '请求参数',
  `response_data` TEXT DEFAULT NULL COMMENT '响应数据',
  `status_code` INT(4) DEFAULT '200' COMMENT '状态码',
  `response_time` BIGINT(20) DEFAULT '0' COMMENT '响应时间（毫秒）',
  `client_ip` VARCHAR(50) DEFAULT '' COMMENT '客户端IP',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='API日志表';

-- 创建 sys_error_log 表
CREATE TABLE IF NOT EXISTS `sys_error_log` (
  `log_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '错误日志ID',
  `error_type` VARCHAR(50) DEFAULT '' COMMENT '错误类型',
  `error_message` TEXT DEFAULT NULL COMMENT '错误信息',
  `error_stack` TEXT DEFAULT NULL COMMENT '错误堆栈',
  `request_url` VARCHAR(200) DEFAULT '' COMMENT '请求URL',
  `request_method` VARCHAR(10) DEFAULT '' COMMENT '请求方法',
  `client_ip` VARCHAR(50) DEFAULT '' COMMENT '客户端IP',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='错误日志表';

-- 创建 sys_permission 表
CREATE TABLE IF NOT EXISTS `sys_permission` (
  `perm_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `perm_name` VARCHAR(100) NOT NULL COMMENT '权限名称',
  `perm_code` VARCHAR(100) NOT NULL COMMENT '权限编码',
  `perm_type` VARCHAR(20) DEFAULT 'MENU' COMMENT '权限类型',
  `parent_id` BIGINT(20) DEFAULT '0' COMMENT '父权限ID',
  `sort_order` INT(4) DEFAULT '0' COMMENT '排序号',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';

-- 创建 sys_role_permission 表
CREATE TABLE IF NOT EXISTS `sys_role_permission` (
  `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
  `perm_id` BIGINT(20) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`, `perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限关联表';

-- 创建 sys_user_dept 表
CREATE TABLE IF NOT EXISTS `sys_user_dept` (
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `dept_id` BIGINT(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`user_id`, `dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户部门关联表';

-- 创建 sys_data_scope 表
CREATE TABLE IF NOT EXISTS `sys_data_scope` (
  `scope_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '数据范围ID',
  `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
  `dept_ids` TEXT DEFAULT NULL COMMENT '部门ID列表',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`scope_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据范围表';

-- 创建 sys_log_archive 表
CREATE TABLE IF NOT EXISTS `sys_log_archive` (
  `archive_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '归档ID',
  `log_type` VARCHAR(20) NOT NULL COMMENT '日志类型',
  `archive_date` DATE NOT NULL COMMENT '归档日期',
  `file_path` VARCHAR(500) DEFAULT '' COMMENT '归档文件路径',
  `record_count` BIGINT(20) DEFAULT '0' COMMENT '记录数',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`archive_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='日志归档表';

-- 创建 sys_maintenance 表
CREATE TABLE IF NOT EXISTS `sys_maintenance` (
  `maintenance_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '维护记录ID',
  `device_id` BIGINT(20) NOT NULL COMMENT '设备ID',
  `maintenance_type` VARCHAR(20) DEFAULT 'ROUTINE' COMMENT '维护类型',
  `maintenance_date` DATETIME DEFAULT NULL COMMENT '维护日期',
  `status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '维护状态',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`maintenance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='维护记录表';

-- 创建 sys_version 表
CREATE TABLE IF NOT EXISTS `sys_version` (
  `version_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '版本ID',
  `version_code` VARCHAR(50) NOT NULL COMMENT '版本号',
  `version_name` VARCHAR(100) DEFAULT '' COMMENT '版本名称',
  `version_desc` VARCHAR(500) DEFAULT '' COMMENT '版本描述',
  `release_date` DATETIME DEFAULT NULL COMMENT '发布日期',
  `is_current` CHAR(1) DEFAULT '1' COMMENT '是否当前版本',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`version_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='版本表';

-- 插入初始版本数据
INSERT INTO `sys_version` (`version_code`, `version_name`, `version_desc`, `release_date`, `is_current`, `create_time`) 
VALUES ('1.0.0', '初始版本', '系统初始版本', NOW(), '1', NOW()) ON DUPLICATE KEY UPDATE version_code=version_code;

-- 创建索引优化查询
CREATE INDEX idx_sys_user_user_name ON sys_user(user_name);
CREATE INDEX idx_sys_user_dept_id ON sys_user(dept_id);
CREATE INDEX idx_sys_role_role_key ON sys_role(role_key);
CREATE INDEX idx_sys_menu_parent_id ON sys_menu(parent_id);
CREATE INDEX idx_sys_menu_menu_type ON sys_menu(menu_type);
CREATE INDEX idx_sys_dict_type_dict_type ON sys_dict_type(dict_type);
CREATE INDEX idx_sys_dict_data_dict_type ON sys_dict_data(dict_type);
CREATE INDEX idx_sys_logininfor_user_name ON sys_logininfor(user_name);
CREATE INDEX idx_sys_oper_log_oper_time ON sys_oper_log(oper_time);
CREATE INDEX idx_sys_job_job_group ON sys_job(job_group);
CREATE INDEX idx_sys_job_status ON sys_job(status);
CREATE INDEX idx_sys_device_device_code ON sys_device(device_code);
CREATE INDEX idx_sys_device_status ON sys_device(status);
CREATE INDEX idx_sample_data_device_id ON sample_data(device_id);
CREATE INDEX idx_sample_data_sample_time ON sample_data(sample_time);
CREATE INDEX idx_statistics_data_device_id ON statistics_data(device_id);
CREATE INDEX idx_statistics_data_statistics_type ON statistics_data(statistics_type);
CREATE INDEX idx_electricity_data_device_id ON electricity_data(device_id);
CREATE INDEX idx_electricity_data_sample_time ON electricity_data(sample_time);
CREATE INDEX idx_water_data_device_id ON water_data(device_id);
CREATE INDEX idx_water_data_sample_time ON water_data(sample_time);
CREATE INDEX idx_energy_statistics_type ON energy_statistics(type);
CREATE INDEX idx_energy_statistics_period_type ON energy_statistics(period_type);
CREATE INDEX idx_sys_message_user_id ON sys_message(user_id);
CREATE INDEX idx_sys_message_status ON sys_message(status);
CREATE INDEX idx_sys_online_user_id ON sys_online(user_id);
CREATE INDEX idx_sys_online_status ON sys_online(status);
CREATE INDEX idx_sys_api_log_api_path ON sys_api_log(api_path);
CREATE INDEX idx_sys_api_log_create_time ON sys_api_log(create_time);
CREATE INDEX idx_sys_error_log_create_time ON sys_error_log(create_time);

COMMIT;