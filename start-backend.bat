@echo off
cd /d D:\code\gitcp\inspur-ems\deep-ems0
java -jar zhurong-ems-admin\target\zhurong-ems-admin.jar --spring.profiles.active=local --server.port=8080 --spring.datasource.dynamic.datasource.td.enabled=false
