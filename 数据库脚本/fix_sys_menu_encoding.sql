-- ============================================================
-- 修复 sys_menu 中"医院智慧能源"目录下所有菜单名称乱码
-- 根因：DB 中 menu_name 存在双重编码乱码(GBK/UTF-8/latin1 转换链)
--       通过 --default-character-set=utf8mb4 读取时呈现乱码。
-- 依据：《数据库脚本/hospital_menu.sql、hospital_menu_m2/m3/m4.sql、hospital_role_dashboard.sql》
--       中的正确菜单名(UPDATE 为权威正确值)。
-- 适用库：autoee_ems
-- ============================================================

UPDATE sys_menu SET menu_name = '医院智慧能源' WHERE menu_id = 1979000000000000001;
UPDATE sys_menu SET menu_name = '医院首页',     remark = '医院智慧能源首页'     WHERE menu_id = 1979000000000000002;
UPDATE sys_menu SET menu_name = '设备台账',     remark = '医院检查检验设备台账' WHERE menu_id = 1979000000000000003;
UPDATE sys_menu SET menu_name = '指标定义',     remark = '医院设备指标定义'     WHERE menu_id = 1979000000000000004;
UPDATE sys_menu SET menu_name = '回调日志',     remark = '医院 IOT 回调日志'    WHERE menu_id = 1979000000000000005;
UPDATE sys_menu SET menu_name = '设备监测',     remark = '医院检查检验设备实时监测' WHERE menu_id = 1979000000000000006;
UPDATE sys_menu SET menu_name = '报警规则',     remark = '医院设备报警规则配置' WHERE menu_id = 1979000000000000007;
UPDATE sys_menu SET menu_name = '报警记录',     remark = '医院设备报警记录与处理' WHERE menu_id = 1979000000000000008;
UPDATE sys_menu SET menu_name = '能耗分析',     remark = '医院能耗概览/趋势/排名分析' WHERE menu_id = 1979000000000000009;
UPDATE sys_menu SET menu_name = '能效评估',     remark = '医院设备能效评估与节能建议' WHERE menu_id = 1979000000000000010;

-- 设备台账 - 按钮
UPDATE sys_menu SET menu_name = '设备查询' WHERE menu_id = 1979000000000000011;
UPDATE sys_menu SET menu_name = '设备新增' WHERE menu_id = 1979000000000000012;
UPDATE sys_menu SET menu_name = '设备修改' WHERE menu_id = 1979000000000000013;
UPDATE sys_menu SET menu_name = '设备删除' WHERE menu_id = 1979000000000000014;
UPDATE sys_menu SET menu_name = 'IOT绑定' WHERE menu_id = 1979000000000000015;

-- 指标定义 - 按钮
UPDATE sys_menu SET menu_name = '指标查询' WHERE menu_id = 1979000000000000021;
UPDATE sys_menu SET menu_name = '指标新增' WHERE menu_id = 1979000000000000022;
UPDATE sys_menu SET menu_name = '指标修改' WHERE menu_id = 1979000000000000023;
UPDATE sys_menu SET menu_name = '指标删除' WHERE menu_id = 1979000000000000024;

-- 回调日志 - 按钮
UPDATE sys_menu SET menu_name = '日志查询' WHERE menu_id = 1979000000000000031;

-- 设备监测 - 按钮
UPDATE sys_menu SET menu_name = '监测查询' WHERE menu_id = 1979000000000000041;

-- 报警规则 - 按钮
UPDATE sys_menu SET menu_name = '规则查询'  WHERE menu_id = 1979000000000000051;
UPDATE sys_menu SET menu_name = '规则新增'  WHERE menu_id = 1979000000000000052;
UPDATE sys_menu SET menu_name = '规则修改'  WHERE menu_id = 1979000000000000053;
UPDATE sys_menu SET menu_name = '规则删除'  WHERE menu_id = 1979000000000000054;

-- 报警记录 - 按钮
UPDATE sys_menu SET menu_name = '记录查询'     WHERE menu_id = 1979000000000000061;
UPDATE sys_menu SET menu_name = '记录查询详情' WHERE menu_id = 1979000000000000062;
UPDATE sys_menu SET menu_name = '报警处理'     WHERE menu_id = 1979000000000000063;

-- 能耗分析/能效评估 - 按钮
UPDATE sys_menu SET menu_name = '分析查询'     WHERE menu_id = 1979000000000000071;
UPDATE sys_menu SET menu_name = '分析查询详情' WHERE menu_id = 1979000000000000072;
UPDATE sys_menu SET menu_name = '报告导出'     WHERE menu_id = 1979000000000000073;

-- 院区管理 - 目录 + 按钮 (M4)
UPDATE sys_menu SET menu_name = '院区管理' WHERE menu_id = 1979000000000000101;
UPDATE sys_menu SET menu_name = '院区查询' WHERE menu_id = 1979000000000000111;
UPDATE sys_menu SET menu_name = '院区新增' WHERE menu_id = 1979000000000000112;
UPDATE sys_menu SET menu_name = '院区修改' WHERE menu_id = 1979000000000000113;
UPDATE sys_menu SET menu_name = '院区删除' WHERE menu_id = 1979000000000000114;

-- 工作量管理 - 目录 + 按钮 (M4)
UPDATE sys_menu SET menu_name = '工作量管理' WHERE menu_id = 1979000000000000102;
UPDATE sys_menu SET menu_name = '工作量查询' WHERE menu_id = 1979000000000000121;
UPDATE sys_menu SET menu_name = '工作量新增' WHERE menu_id = 1979000000000000122;
UPDATE sys_menu SET menu_name = '工作量修改' WHERE menu_id = 1979000000000000123;
UPDATE sys_menu SET menu_name = '工作量删除' WHERE menu_id = 1979000000000000124;

-- 医院大屏 / 角色看板 (M4.5 / FR-17)
UPDATE sys_menu SET menu_name = '医院大屏' WHERE menu_id = 1979000000000000103;
UPDATE sys_menu SET menu_name = '角色看板' WHERE menu_id = 1979000000000000104;
UPDATE sys_menu SET menu_name = '看板查询' WHERE menu_id = 1979000000000000141;