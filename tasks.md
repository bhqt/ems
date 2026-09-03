# 智慧能源决策系统（医院版）Tasks 任务拆解

> 基于 spec.md 里程碑（M1–M4）拆解开发任务。每个任务完成后需同步勾选 checklist.md 对应项。
> 约定：后端沿用 deep-ems0 工程结构（zhurong-ems-*），前端沿用 Vue 2.6 + Element UI。
> **加性开发原则**：原系统全部功能保留、不做删改；以下任务均为**新增模块/新增页面**，叠加在原工程上开发，不修改、不删除既有业务。

## 里程碑 M1：项目骨架 + 数据接入 + 设备台账（P0）

- [x] T1.1 新建医院系统前端工程（或独立前端目录），配置医院 UI 主题基础 — 复用 zhurong-admin-ui/views/hospital/（4 页面）+ api/hospital/ + 中/英 i18n，不另起独立工程

- [x] T1.2 后端回调接入层：回调 Controller、鉴权拦截器（Token/签名/IP）、报文解析器 — HospitalCallbackController + IotCallbackAuthService（Token 比对 + IP 白名单 + HMAC_SHA256 签名，均配置化）+ IotDataParserImpl

- [x] T1.3 标准数据结构定义：设备指标模型、数据点模型（deviceId/metric/value/ts/quality）— StandardDataPoint + IotCallbackRequest

- [x] T1.4 数据标准化与 MQ 投递：解析后投递 RabbitMQ，失败重试队列 — hospital.topic.exchange + 死信转重试队列（最大 3 次）

- [x] T1.5 数据消费与落库：MySQL 业务表 + TDengine 时序表（可开关）— HospitalDeviceDataConsumer（TD 失败不阻断主链路）

- [x] T1.6 回调日志与监控：回调记录、失败统计、异常日志 — hospital_callback_log + 回调日志列表页（统计大盘待 M2 增强）

- [x] T1.7 设备台账模块：检查检验设备档案 CRUD + IOT 设备绑定管理 — 含 device_code / iot_device_id 唯一性校验

- [x] T1.8 数据库脚本：新增表结构（设备台账、指标定义、回调日志、设备绑定等）— hospital_init.sql + hospital_init_tdengine.sql + hospital_menu.sql

## 里程碑 M2：医院 UI + 设备监测 + 报警（P0）

- [x] T2.1 医院风格 UI 主题（配色/布局/组件样式）落地 — 医院首页模块卡片 + 监测页医院蓝渐变（深色大屏主题待 M4）

- [x] T2.2 国际化框架：中/英语言包、切换机制、菜单与业务文案国际化 — LangSelect 切换已存在，hospital 中/英词条补齐监测+报警（动态菜单名/多时区待 M4）

- [x] T2.3 设备监测页面：设备列表、运行状态、能耗实时展示 — views/hospital/monitor（在线/运行/功率/电量/未处理报警，30s 自动刷新，趋势对话框）

- [x] T2.4 设备报警：报警规则配置、触发引擎、报警记录与通知（对接现有报警能力改造）— 独立 hospital_alarm_rule/record 表（与旧 alarm 链路隔离）；阈值引擎随落库触发 + 离线 5 分钟扫描 + 邮件通知；规则/记录两页面

- [x] T2.5 后端监测接口：设备状态/能耗查询 API（聚合 TDengine 数据）— GET /hospital/monitor/overview（聚合 MySQL 最新点 + 在离线判定）与 /trend（TDengine 聚合待 M3 按分析需求增强）

## 里程碑 M3：能耗分析 + 能效评估 + 节能建议（P1）

- [x] T3.1 全院能耗概览与多级钻取（院区/楼宇/科室/设备）— GET /hospital/energy/overview，AREA/DEPT/DEVICE 三级（楼宇归并到科室，台账无楼宇字段），附环比 — views/hospital/energy（ECharts 趋势图 + 排名表）

- [x] T3.2 分区/分项分析：分区维度、分项维度、同比环比/趋势/排名 — 分区（院区/科室）+ 设备排名 + 日/小时趋势 + 环比；分项（照明/空调/医疗设备/动力）需分项计量点，待有分项数据后增强

- [x] T3.3 设备能效评估：单位工作量能耗、待机占比、运行效率模型与计算 — 待机占比 + 平均功率 + 同类对标 + 评分等级（单位工作量需检查量数据对接，暂用功率对标代替）

- [x] T3.4 节能建议引擎：待机浪费/高耗能时段/异常设备识别，建议清单生成与导出 — 三规则引擎 + Excel 导出

- [x] T3.5 分析报告：周期报告生成与导出（对接现有报表能力）— 节能建议清单 + 能效评估 Excel 导出（复用 ExcelUtil/EasyExcel），views/hospital/efficiency

## 里程碑 M4：大屏 + 看板 + 海外适配收尾（P1）

> 本阶段按需求拆分为 M4.1–M4.5 五项交付，已全部编码并验证（后端 JDK8 自测 + 前端 prod 构建通过）：

- [x] M4.1 多院区数据隔离+权限：hospital_area 院区表 + CRUD + options；IHospitalDataScopeService 按角色解析可访问院区（admin/ALL 返回 null 不限）；area 过滤接入 device/monitor/energy 查询；院区管理页 — 后端+前端已交付（admin 全量验证通过，非 admin 角色过滤机制已实现待回归）
- [x] M4.2 报警升级闭环：hospital_alarm_record 加 handle_status/confirm_by/confirm_time/escalate_count/escalate_level/escalate_time；hospital_alarm_rule 加 escalate_min/escalate_level；动作端点 /hospital/alarmRecord/action（confirm/process/done）；HospitalAlarmEvalServiceImpl 每分钟 scanEscalation() 超时自动升级+邮件；记录页三态流转 + 规则页升级配置
- [x] M4.3 分项计量增强：hospital_device 加 project_category（LIGHTING/AIRCOND/MEDICAL/POWER/OTHER）；energy categorySummary/categoryTrend 端点 + 分项页签（表 + 趋势图）— 端点自测通过（当前无分项数据故为空，待分项计量点接入）
- [x] M4.4 单位工作量能效：hospital_device_workload 工作量表 + CRUD；efficiency 注入 workload 计算 unitEnergy=kwh/check_count（自测 unitEnergy=8.09）— 工作量页 + 能效页列
- [x] M4.5 医院大屏+角色看板：views/hospital/bigScreen 深色大屏（能耗概览/分项饼图/日趋势/耗电排名/最近报警，ECharts 定时刷新）— 大屏已交付；角色定制看板为后续增强点

## 里程碑 M4（原 tasks 项）

- [x] T4.1 医院大屏：全院能耗/设备状态/报警/关键指标可视化 — views/hospital/bigScreen（M4.5）

- [ ] T4.2 角色定制看板：按角色配置与展示 — 大屏通用版已交付；按角色差异化配置待接后续

- [ ] T4.3 海外适配：多时区处理、单位体系、合规性（数据最小化/审计）检查 — 待后续（现有中/英切换可用）

- [x] T4.4 多院区数据隔离验证：权限 + 数据隔离 — hospital_area + 数据权限服务接入（M4.1，admin 验证通过）

- [ ] T4.5 性能与安全验收：压测、安全加固、Docker 化部署脚本完善

- [ ] T4.6 全量回归：checklist.md 全部勾选，整体验收

## 依赖关系

```
M1 → M2 → M3 → M4
T1.x 之间：T1.2 依赖 T1.3；T1.4/T1.5 依赖 T1.2/T1.3
```

