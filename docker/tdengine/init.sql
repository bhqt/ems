-- TDengine 初始化脚本
-- 创建 energy 数据库
CREATE DATABASE IF NOT EXISTS energy;

-- 使用 energy 数据库
USE energy;

-- 创建 energy 用户（TDengine 3.x 语法）
CREATE USER IF NOT EXISTS energy WITH PASSWORD 'difyai123456';

-- 授权 energy 用户访问 energy 数据库
GRANT ALL ON energy TO energy;

-- 显示创建的数据库
SHOW DATABASES;