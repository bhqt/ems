-- =====================================================
-- 医院智慧能源决策系统 - 菜单（MySQL，M3：能耗分析 + 能效评估）
-- 适用库：autoee_ems
-- 说明：加性开发，仅新增菜单，不修改/删除现有菜单；
--       依赖 hospital_menu.sql 的顶级目录（1979000000000000001）。
-- =====================================================

-- 1. 能耗分析
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000009, '能耗分析', 1979000000000000001, 8, 'energy', 'hospital/energy/index', 1, 0, 'C', '0', '0', 'hospital:energy:list', 'chart', 'admin', NOW(), '医院能耗概览/趋势/排名分析');

-- 2. 能效评估
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000010, '能效评估', 1979000000000000001, 9, 'efficiency', 'hospital/efficiency/index', 1, 0, 'C', '0', '0', 'hospital:energy:list', 'efficiency', 'admin', NOW(), '医院设备能效评估与节能建议');

-- 3. 能耗分析/能效评估 - 按钮权限
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000071, '分析查询', 1979000000000000009, 1, '', NULL, 1, 0, 'F', '0', '0', 'hospital:energy:list', '#', 'admin', NOW(), '能耗分析查询'),
       (1979000000000000072, '分析查询详情', 1979000000000000010, 1, '', NULL, 1, 0, 'F', '0', '0', 'hospital:energy:query', '#', 'admin', NOW(), '能效评估查询'),
       (1979000000000000073, '报告导出', 1979000000000000010, 2, '', NULL, 1, 0, 'F', '0', '0', 'hospital:energy:export', '#', 'admin', NOW(), '分析报告导出');
