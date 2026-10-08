-- 修复 hospital_device.device_name 乱码
-- 原因：之前导入种子时 MySQL 客户端连接非 utf8mb4，中文被替换为 '?'
-- 这里用 utf8mb4 连接执行，按 device_code 重新写回正确中文
SET NAMES utf8mb4;

UPDATE hospital_device SET device_name = 'CT-001高清螺旋CT'    WHERE device_code = 'CT-001';
UPDATE hospital_device SET device_name = 'CT-002多排CT'          WHERE device_code = 'CT-002';
UPDATE hospital_device SET device_name = 'MRI-001核磁共振'      WHERE device_code = 'MRI-001';
UPDATE hospital_device SET device_name = 'MRI-002核磁共振'      WHERE device_code = 'MRI-002';
UPDATE hospital_device SET device_name = 'DR-001射线摄影'        WHERE device_code = 'DR-001';
UPDATE hospital_device SET device_name = 'DR-002移动DR'          WHERE device_code = 'DR-002';
UPDATE hospital_device SET device_name = 'US-001彩超'            WHERE device_code = 'US-001';
UPDATE hospital_device SET device_name = 'US-002便携彩超'        WHERE device_code = 'US-002';
UPDATE hospital_device SET device_name = 'DSA-001血管造影'      WHERE device_code = 'DSA-001';
UPDATE hospital_device SET device_name = 'LAB-001全自动生化'     WHERE device_code = 'LAB-001';
UPDATE hospital_device SET device_name = 'LAB-002血细胞分析'     WHERE device_code = 'LAB-002';
UPDATE hospital_device SET device_name = 'ECG-001心电监护'       WHERE device_code = 'ECG-001';
UPDATE hospital_device SET device_name = '呼吸机-001'             WHERE device_code = 'VENT-001';
UPDATE hospital_device SET device_name = '中央空调-压缩机组'      WHERE device_code = 'AIR-001';
UPDATE hospital_device SET device_name = '中央空调-冷水机组'      WHERE device_code = 'AIR-002';
UPDATE hospital_device SET device_name = '门诊照明系统'            WHERE device_code = 'LIGHT-001';
UPDATE hospital_device SET device_name = '住院楼照明系统'          WHERE device_code = 'LIGHT-002';
UPDATE hospital_device SET device_name = '电梯动力系统'            WHERE device_code = 'POWER-001';
