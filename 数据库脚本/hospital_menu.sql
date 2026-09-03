-- =====================================================
-- 医院智慧能源决策系统 - 菜单（MySQL）
-- 适用库：autoee_ems
-- 说明：加性开发，仅新增菜单，不修改/删除现有菜单；
--       admin(超级管理员) 可见全部菜单，其他角色需在角色管理中分配。
-- =====================================================

-- 1. 顶级目录：医院智慧能源
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000001, '医院智慧能源', 0, 100, 'hospital', NULL, 1, 0, 'M', '0', '0', NULL, 'hospital', 'admin', NOW(), '医院智慧能源决策系统模块');

-- 2. 医院首页
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000002, '医院首页', 1979000000000000001, 1, 'index', 'hospital/index', 1, 0, 'C', '0', '0', 'hospital:index:list', 'home', 'admin', NOW(), '医院智慧能源首页');

-- 3. 设备台账
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000003, '设备台账', 1979000000000000001, 2, 'device', 'hospital/device/index', 1, 0, 'C', '0', '0', 'hospital:device:list', 'equipment', 'admin', NOW(), '医院检查检验设备台账');

-- 4. 指标定义
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000004, '指标定义', 1979000000000000001, 3, 'metric', 'hospital/metric/index', 1, 0, 'C', '0', '0', 'hospital:metric:list', 'chart', 'admin', NOW(), '医院设备指标定义');

-- 5. 回调日志
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000005, '回调日志', 1979000000000000001, 4, 'callbackLog', 'hospital/callbackLog/index', 1, 0, 'C', '0', '0', 'hospital:callbackLog:list', 'log', 'admin', NOW(), '医院 IOT 回调日志');

-- 6. 设备台账 - 按钮权限
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000011, '设备查询', 1979000000000000003, 1, '', NULL, 1, 0, 'F', '0', '0', 'hospital:device:query', '#', 'admin', NOW(), '设备查询'),
       (1979000000000000012, '设备新增', 1979000000000000003, 2, '', NULL, 1, 0, 'F', '0', '0', 'hospital:device:add', '#', 'admin', NOW(), '设备新增'),
       (1979000000000000013, '设备修改', 1979000000000000003, 3, '', NULL, 1, 0, 'F', '0', '0', 'hospital:device:edit', '#', 'admin', NOW(), '设备修改'),
       (1979000000000000014, '设备删除', 1979000000000000003, 4, '', NULL, 1, 0, 'F', '0', '0', 'hospital:device:remove', '#', 'admin', NOW(), '设备删除'),
       (1979000000000000015, 'IOT绑定', 1979000000000000003, 5, '', NULL, 1, 0, 'F', '0', '0', 'hospital:device:bind', '#', 'admin', NOW(), 'IOT 设备绑定/解绑');

-- 7. 指标定义 - 按钮权限
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000021, '指标查询', 1979000000000000004, 1, '', NULL, 1, 0, 'F', '0', '0', 'hospital:metric:query', '#', 'admin', NOW(), '指标查询'),
       (1979000000000000022, '指标新增', 1979000000000000004, 2, '', NULL, 1, 0, 'F', '0', '0', 'hospital:metric:add', '#', 'admin', NOW(), '指标新增'),
       (1979000000000000023, '指标修改', 1979000000000000004, 3, '', NULL, 1, 0, 'F', '0', '0', 'hospital:metric:edit', '#', 'admin', NOW(), '指标修改'),
       (1979000000000000024, '指标删除', 1979000000000000004, 4, '', NULL, 1, 0, 'F', '0', '0', 'hospital:metric:remove', '#', 'admin', NOW(), '指标删除');

-- 8. 回调日志 - 按钮权限
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000031, '日志查询', 1979000000000000005, 1, '', NULL, 1, 0, 'F', '0', '0', 'hospital:callbackLog:list', '#', 'admin', NOW(), '回调日志查询');
