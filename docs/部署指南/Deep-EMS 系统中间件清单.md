# Deep-EMS 系统中间件清单

> 本文档列出 Deep-EMS（祝融能源管理系统）运行所需的全部中间件及其版本、用途、配置信息。

***

## 一、核心中间件（必需）

| 序号 | 中间件                | 版本                     | 用途                | 默认端口                                            | 配置文件                                                                    |
| -- | ------------------ | ---------------------- | ----------------- | ----------------------------------------------- | ----------------------------------------------------------------------- |
| 1  | MySQL              | 8.0.35                 | 关系型数据库，存储业务主数据    | 3306                                            | `application-{env}.yml` → `spring.datasource.dynamic.datasource.master` |
| 2  | Redis              | 7.2.3-alpine           | 缓存、分布式锁、会话管理      | 6379                                            | `application-{env}.yml` → `spring.redis`                                |
| 3  | RabbitMQ           | 3.12-management-alpine | 消息队列，异步消息通信       | 5672(AMQP) / 15672(管理界面)                        | `application.yml` → `spring.rabbitmq`                                   |
| 4  | EMQX (MQTT Broker) | —                      | IoT 设备消息代理，设备数据采集 | 1883(MQTT) / 8083(WebSocket) / 18083(Dashboard) | `application-{env}.yml` → `mqtt`                                        |
| 5  | Nginx              | —                      | 反向代理、前端静态资源服务     | 80                                              | `zhurong-admin-ui/nginx.conf`                                           |

## 二、扩展中间件（推荐）

| 序号 | 中间件               | 版本     | 用途        | 默认端口                   | 配置文件                                                 |
| -- | ----------------- | ------ | --------- | ---------------------- | ---------------------------------------------------- |
| 6  | XXL-Job           | 2.3.1  | 分布式定时任务调度 | 9110(调度中心) / 9101(执行器) | `application-{env}.yml` → `xxl.job`                  |
| 7  | Spring Boot Admin | 2.7.10 | 应用监控、健康检查 | 9090                   | `application-{env}.yml` → `spring.boot.admin.client` |

## 三、可选中间件

| 序号 | 中间件        | 版本      | 用途             | 默认端口                      | 配置文件                                                                |
| -- | ---------- | ------- | -------------- | ------------------------- | ------------------------------------------------------------------- |
| 8  | TDengine   | 3.2.0.0 | 时序数据库，存储能源时序数据 | 6030(原生) / 6041(RESTful)  | `application-{env}.yml` → `spring.datasource.dynamic.datasource.td` |
| 9  | MinIO / S3 | —       | 对象存储，文件/附件上传   | 9000(API) / 9001(Console) | OSS 模块配置                                                            |

## 四、内嵌组件（随应用启动，无需独立部署）

| 序号 | 组件       | 版本                   | 用途               | 说明                                                               |
| -- | -------- | -------------------- | ---------------- | ---------------------------------------------------------------- |
| 1  | Undertow | Spring Boot 2.7.9 内嵌 | Web 服务器          | 替代 Tomcat，配置见 `application.yml` → `server.undertow`              |
| 2  | HikariCP | Spring Boot 2.7.9 内嵌 | 数据库连接池           | 配置见 `application-{env}.yml` → `spring.datasource.dynamic.hikari` |
| 3  | Redisson | 3.20.0               | 分布式 Java 对象、分布式锁 | 基于 Redis，配置见 `application-{env}.yml` → `redisson`                |
| 4  | Lock4j   | 2.2.3                | 分布式锁框架           | 基于 Redisson，配置见 `application.yml` → `lock4j`                     |
| 5  | Sa-Token | 1.34.0               | 权限认证 & JWT       | 配置见 `application.yml` → `sa-token`                               |
| 6  | P6Spy    | 3.9.1                | SQL 性能分析         | 仅开发环境启用，生产环境建议关闭                                                 |

## 五、外部服务（按需配置）

| 序号 | 服务        | 用途        | 配置位置                             |
| -- | --------- | --------- | -------------------------------- |
| 1  | SMTP 邮件服务 | 系统通知、报警邮件 | `application-{env}.yml` → `mail` |
| 2  | 阿里云 SMS   | 短信通知      | `application-{env}.yml` → `sms`  |
| 3  | 腾讯云 SMS   | 短信通知（备选）  | `application-{env}.yml` → `sms`  |

## 六、Docker 部署端口汇总

| 服务       | 容器名                  | 端口映射                   | 说明             |
| -------- | -------------------- | ---------------------- | -------------- |
| MySQL    | zhurong-ems-mysql    | 3306:3306              | 数据库            |
| Redis    | zhurong-ems-redis    | 6379:6379              | 缓存             |
| RabbitMQ | zhurong-ems-rabbitmq | 5672:5672, 15672:15672 | 消息队列           |
| 后端服务     | zhurong-ems-backend  | 1088:8088              | Spring Boot 应用 |
| 前端服务     | zhurong-ems-frontend | 80:80                  | Nginx 静态资源     |

## 七、中间件依赖关系

┌─────────────────────────────────────────────────────┐
│ Deep-EMS 应用 │
├──────────┬──────────┬──────────┬──────────┬─────────┤
│ 业务模块 │ 定时任务 │ 监控中心 │ OSS模块 │ SMS模块 │
├──────────┴──────────┴──────────┴──────────┴─────────┤
│ Spring Boot 2.7.9 (Undertow) │
├──────────┬──────────┬──────────┬────────────────────┤
│ MySQL │ Redis │ RabbitMQ │ EMQX (MQTT) │
│ (主库) │(缓存/锁) │ (消息队列)│ (IoT数据采集) │
├──────────┴──────────┴──────────┴────────────────────┤
│ TDengine (可选，时序数据) │
├─────────────────────────────────────────────────────┤
│ Nginx (反向代理) │
└─────────────────────────────────────────────────────┘

## 八、环境要求

| 项目             | 最低要求            | 推荐配置                              |
| :------------- | :-------------- | :-------------------------------- |
| JDK            | 1.8+            | 1.8                               |
| Maven          | 3.6+            | 3.8+                              |
| Docker         | 20.10+          | 24.0+                             |
| Docker Compose | 2.0+            | 2.20+                             |
| 操作系统           | Windows / Linux | Linux (CentOS 7+ / Ubuntu 20.04+) |
| 内存             | 8GB             | 16GB+                             |
| 磁盘             | 50GB            | 100GB+ SSD                        |

<br />

<br />

清单说明：

通过分析项目的 pom.xml 、 docker-compose.yml 、各环境 application-\*.yml 配置文件以及 nginx.conf ，我梳理出了系统用到的全部中间件，分为以下几类：

1. 核心中间件（5个） ：MySQL、Redis、RabbitMQ、EMQX、Nginx — 这些是系统运行必不可少的
2. 扩展中间件（2个） ：XXL-Job（定时调度）、Spring Boot Admin（应用监控） — 强烈推荐部署
3. 可选中间件（2个） ：TDengine（时序数据）、MinIO/S3（对象存储） — 根据业务需求启用
4. 内嵌组件（6个） ：Undertow、HikariCP、Redisson、Lock4j、Sa-Token、P6Spy — 随应用启动，无需单独部署
5. 外部服务（3个） ：SMTP邮件、阿里云/腾讯云短信 — 按需配置

