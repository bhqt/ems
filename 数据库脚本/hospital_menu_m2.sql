-- =====================================================
-- 医院智慧能源决策系统 - 菜单（MySQL，M2：监测 + 报警）
-- 适用库：autoee_ems
-- 说明：加性开发，仅新增菜单，不修改/删除现有菜单；
--       依赖 hospital_menu.sql 的顶级目录（1979000000000000001）。
-- =====================================================

-- 1. 设备监测
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000006, '设备监测', 1979000000000000001, 5, 'monitor', 'hospital/monitor/index', 1, 0, 'C', '0', '0', 'hospital:monitor:list', 'monitor', 'admin', NOW(), '医院检查检验设备实时监测');

-- 2. 报警规则
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000007, '报警规则', 1979000000000000001, 6, 'alarmRule', 'hospital/alarmRule/index', 1, 0, 'C', '0', '0', 'hospital:alarmRule:list', 'warning', 'admin', NOW(), '医院设备报警规则配置');

-- 3. 报警记录
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000008, '报警记录', 1979000000000000001, 7, 'alarmRecord', 'hospital/alarmRecord/index', 1, 0, 'C', '0', '0', 'hospital:alarmRecord:list', 'alarm', 'admin', NOW(), '医院设备报警记录与处理');

-- 4. 设备监测 - 按钮权限（查询即列表，无增删改）
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000041, '监测查询', 1979000000000000006, 1, '', NULL, 1, 0, 'F', '0', '0', 'hospital:monitor:list', '#', 'admin', NOW(), '设备监测查询');

-- 5. 报警规则 - 按钮权限
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000051, '规则查询', 1979000000000000007, 1, '', NULL, 1, 0, 'F', '0', '0', 'hospital:alarmRule:query', '#', 'admin', NOW(), '报警规则查询'),
       (1979000000000000052, '规则新增', 1979000000000000007, 2, '', NULL, 1, 0, 'F', '0', '0', 'hospital:alarmRule:add', '#', 'admin', NOW(), '报警规则新增'),
       (1979000000000000053, '规则修改', 1979000000000000007, 3, '', NULL, 1, 0, 'F', '0', '0', 'hospital:alarmRule:edit', '#', 'admin', NOW(), '报警规则修改'),
       (1979000000000000054, '规则删除', 1979000000000000007, 4, '', NULL, 1, 0, 'F', '0', '0', 'hospital:alarmRule:remove', '#', 'admin', NOW(), '报警规则删除');

-- 6. 报警记录 - 按钮权限
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000061, '记录查询', 1979000000000000008, 1, '', NULL, 1, 0, 'F', '0', '0', 'hospital:alarmRecord:list', '#', 'admin', NOW(), '报警记录查询'),
       (1979000000000000062, '记录查询详情', 1979000000000000008, 2, '', NULL, 1, 0, 'F', '0', '0', 'hospital:alarmRecord:query', '#', 'admin', NOW(), '报警记录详情'),
       (1979000000000000063, '报警处理', 1979000000000000008, 3, '', NULL, 1, 0, 'F', '0', '0', 'hospital:alarmRecord:handle', '#', 'admin', NOW(), '报警处理/关闭');
