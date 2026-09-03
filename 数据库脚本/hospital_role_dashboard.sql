-- =====================================================
-- 医院智慧能源决策系统 - 菜单（MySQL，FR-17：角色定制看板）
-- 适用库：autoee_ems
-- 说明：加性开发，仅新增菜单；父目录为 hospital_menu.sql 顶级节点 1979000000000000001。
-- 依赖：hospital_init.sql / hospital_menu.sql / hospital_menu_m4.sql 已执行。
-- =====================================================

-- 角色定制看板（FR-17，M4.5 大屏之外的独立看板）
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000104, '角色看板', 1979000000000000001, 13, 'dashboard', 'hospital/dashboard/index', 1, 0, 'C', '0', '0', 'hospital:dashboard:list', 'data-board', 'admin', NOW(), '按角色定制的实时数据看板（FR-17）');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000141, '看板查询', 1979000000000000104, 1, '', NULL, 1, 0, 'F', '0', '0', 'hospital:dashboard:query', '#', 'admin', NOW(), '看板查询');
