-- =====================================================
-- 医院智慧能源决策系统 - 菜单（MySQL，M4：院区/工作量/大屏等）
-- 适用库：autoee_ems
-- 说明：加性开发，仅新增菜单；父目录为 hospital_menu.sql 顶级节点 1979000000000000001。
-- 依赖：hospital_init.sql / hospital_menu.sql / hospital_alarm.sql 已执行。
-- =====================================================

-- 1. 院区管理（M4.1）
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000101, '院区管理', 1979000000000000001, 10, 'area', 'hospital/area/index', 1, 0, 'C', '0', '0', 'hospital:area:list', 'office-building', 'admin', NOW(), '多院区数据隔离与权限管理');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000111, '院区查询', 1979000000000000101, 1, '', NULL, 1, 0, 'F', '0', '0', 'hospital:area:query', '#', 'admin', NOW(), '院区查询'),
       (1979000000000000112, '院区新增', 1979000000000000101, 2, '', NULL, 1, 0, 'F', '0', '0', 'hospital:area:add', '#', 'admin', NOW(), '院区新增'),
       (1979000000000000113, '院区修改', 1979000000000000101, 3, '', NULL, 1, 0, 'F', '0', '0', 'hospital:area:edit', '#', 'admin', NOW(), '院区修改'),
       (1979000000000000114, '院区删除', 1979000000000000101, 4, '', NULL, 1, 0, 'F', '0', '0', 'hospital:area:remove', '#', 'admin', NOW(), '院区删除');

-- 2. 工作量管理（M4.4）
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000102, '工作量管理', 1979000000000000001, 11, 'workload', 'hospital/workload/index', 1, 0, 'C', '0', '0', 'hospital:workload:list', 'trend-chart', 'admin', NOW(), '设备工作量登记与单位工作量能效');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000121, '工作量查询', 1979000000000000102, 1, '', NULL, 1, 0, 'F', '0', '0', 'hospital:workload:query', '#', 'admin', NOW(), '工作量查询'),
       (1979000000000000122, '工作量新增', 1979000000000000102, 2, '', NULL, 1, 0, 'F', '0', '0', 'hospital:workload:add', '#', 'admin', NOW(), '工作量新增'),
       (1979000000000000123, '工作量修改', 1979000000000000102, 3, '', NULL, 1, 0, 'F', '0', '0', 'hospital:workload:edit', '#', 'admin', NOW(), '工作量修改'),
       (1979000000000000124, '工作量删除', 1979000000000000102, 4, '', NULL, 1, 0, 'F', '0', '0', 'hospital:workload:remove', '#', 'admin', NOW(), '工作量删除');

-- 3. 医院大屏（M4.5）
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES (1979000000000000103, '医院大屏', 1979000000000000001, 12, 'bigScreen', 'hospital/bigScreen/index', 1, 0, 'C', '0', '0', 'hospital:screen:list', 'monitor', 'admin', NOW(), '医院能源智慧大屏/看板');
