# EMS 与 shared-infra 本地联调部署说明

> 记录日期：2026-09-11  
> 环境：Windows + Java 21 (IntelliJ JBR) + Node.js + Docker Desktop  
> 前提：D:\code\shared-infra 已就绪

---

## 一、架构概览

```
┌──────────────────────────────────────────────────────────────┐
│                     本地联调模式                               │
├──────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌─────────────┐    proxy     ┌────────────────────────┐     │
│  │  前端 (Vue)  │ ──────────→ │  后端 (Spring Boot)    │     │
│  │  :9029      │              │  :8088 /autoee-iot-ems │     │
│  └─────────────┘              └──────┬─────────────────┘     │
│                                      │                       │
│                    ┌─────────────────┼──────────────┐        │
│                    │ shared-infra (Docker Compose)   │        │
│                    │                                 │        │
│                    │  MySQL     :3306  (core)        │        │
│                    │  Redis     :6379  (core)        │        │
│                    │  RabbitMQ  :5672  (coldchain)   │        │
│                    │  EMQX      :1883  (iot)         │        │
│                    │  TDengine  :6041  (iot, 可选)    │        │
│                    └─────────────────────────────────┘        │
└──────────────────────────────────────────────────────────────┘
```

## 二、端口映射总览

| 服务 | 端口 | 所属 profile | 用途 |
|------|------|-------------|------|
| MySQL | 3306 | core | 主数据库 |
| Redis | 6379 | core | 缓存 / session |
| RabbitMQ | 5672 | coldchain | 消息队列 |
| EMQX | 1883 | iot | MQTT |
| TDengine | 6041 | iot | 时序库（本地联调可禁用） |
| 后端 | 8088 | - | Spring Boot |
| 前端 | 9029 | - | Vue devServer |

## 三、快速启动步骤

### 3.1 启动 shared-infra 中间件

```bash
cd D:\code\shared-infra
docker compose --profile core --profile coldchain up -d
```

> **说明**：本地联调一般不需要 TDengine（`--profile iot`），后端已禁用 td 数据源。  
> 如需 IoT 功能，加 `--profile iot`。

### 3.2 验证中间件健康

```bash
# MySQL
docker exec shared-mysql mysql -u root -p123456 -e "SHOW DATABASES;"

# Redis
docker exec shared-redis redis-cli -a difyai123456 ping

# RabbitMQ (管理界面: http://localhost:15672)
docker exec shared-rabbitmq rabbitmqctl status
```

### 3.3 构建后端

**⚠️ 关键要求：必须使用 Java 21 编译，不能用 Java 25**

```bash
# 设置 JAVA_HOME 为 IntelliJ JBR (Java 21)
set JAVA_HOME=C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.1.1\jbr

cd D:\code\gitcp\inspur-ems\deep-ems0
mvn clean package -DskipTests
```

> 项目 target=1.8，但需要 Java 21 的 Lombok 注解处理器。  
> Java 25 (GraalVM) 会导致 Lombok 1.18.36 以下版本崩溃。

### 3.4 启动后端

```bash
cd zhurong-ems-admin\target
java -jar zhurong-ems-admin.jar ^
  --spring.profiles.active=local ^
  --server.port=8088 ^
  --server.servlet.context-path=/autoee-iot-ems
```

验证：
```
curl http://localhost:8088/autoee-iot-ems/actuator/health
# 返回 {"status":"UP"}
```

### 3.5 启动前端

```bash
cd D:\code\gitcp\inspur-ems\deep-ems0\zhurong-admin-ui
set NODE_OPTIONS=--openssl-legacy-provider
npm run dev
```

打开 `http://localhost:9029`，登录 `admin / admin123`

## 四、配置文件清单

### 4.1 后端 application-local.yml

路径：`zhurong-ems-admin/src/main/resources/application-local.yml`

| 配置项 | 值 | 说明 |
|--------|-----|------|
| MySQL URL | `jdbc:mysql://localhost:3306/autoee_ems` | shared-mysql |
| MySQL 密码 | `123456` | shared-infra .env 中 MYSQL_ROOT_PASSWORD |
| Redis host | `localhost:6379` | db=13, password=difyai123456 |
| RabbitMQ | `localhost:5672` | rabbitmq/rabbitmqpassword, vhost=admin_vhost |
| MQTT | `tcp://localhost:1883` | admin/public |
| TDengine | **已移除** | 本地联调不需要 |
| xxl-job | enabled: false | 本地无调度中心 |
| Spring Boot Admin | enabled: false | 本地无监控中心 |

### 4.2 前端 .env.development

路径：`zhurong-admin-ui/.env.development`

```
VUE_APP_BASE_API = 'http://localhost:8088/autoee-iot-ems'
```

### 4.3 前端 vue.config.js 代理

路径：`zhurong-admin-ui/vue.config.js`

```js
proxy: {
  [process.env.VUE_APP_BASE_API]: {
    target: `http://localhost:8088/autoee-iot-ems`,
    changeOrigin: true,
    pathRewrite: {
      ['^' + process.env.VUE_APP_BASE_API]: ''
    }
  }
}
```

> **注意**：代理 target 必须与后端端口一致（8088），不能是 20080 或其他。

## 五、踩坑记录与解决方案

### 5.1 Lombok 与 Java 21 不兼容

**现象**：`mvn clean package` 报 `Compilation failure`，Maven 不显示具体错误。

**根因**：项目使用 Lombok 1.18.26 + `maven-compiler-plugin` 3.9.0 配合 `<fork>true</fork>`，javac 进程的错误输出被 Maven 静默吞掉。实际错误是：

```
java.lang.NoSuchFieldError: Class com.sun.tools.javac.tree.JCTree$JCImport 
does not have member field 'com.sun.tools.javac.tree.JCTree qualid'
```

Lombok 1.18.26 的注解处理器使用了 Java 内部 API，在 Java 21 中该字段被移除。

**解决**：升级 Lombok 至 1.18.36

```xml
<!-- pom.xml -->
<lombok.version>1.18.36</lombok.version>
```

### 5.2 Maven 编译器错误被吞

**现象**：`maven-compiler-plugin:3.9.0` 报 `Compilation failure` 但不显示具体错误。

**根因**：`<fork>true</fork>` 导致 javac 在子进程执行，错误输出未被 Maven 捕获。

**解决**：临时修改 `pom.xml` 中 `<fork>false</fork>` 可看到实际错误。定位问题后恢复 `<fork>true</fork>`。

### 5.3 TDengine 连接失败导致启动崩溃

**现象**：后端启动时报 `HikariPool$PoolInitializationException: Connect to localhost:6041 failed`。

**根因**：`application-local.yml` 中 td 数据源的 `enabled: false` 不生效——dynamic-datasource 库在 `strict: true` 模式下仍会尝试初始化所有已定义的数据源。

**解决**：直接从 `application-local.yml` 中**删除** td 数据源配置块（不只是设 enabled: false）：

```yaml
# 删除以下整个 td: 块
# td:
#   enabled: false
#   driverClassName: com.taosdata.jdbc.rs.RestfulDriver
#   url: jdbc:TAOS-RS://localhost:6041/energy?...
```

> 如果后续需要 TDengine，启动 shared-infra 的 iot profile 并恢复配置。

### 5.4 UndertowConfig 编译错误

**现象**：`UndertowConfig.java:[31,26] 找不到符号 方法 setDefaultCharset(Charset)`

**根因**：`DeploymentInfo.setDefaultCharset()` 在当前 Undertow 版本（Spring Boot 2.7.9 内嵌）中不存在。

**解决**：删除该调用及未使用的 import。

### 5.5 前端代理端口错误

**现象**：前端页面报 `ERR_CONNECTION_REFUSED` 到 `127.0.0.1:20080`。

**根因**：`vue.config.js` 的 proxy target 指向旧端口 20080。

**解决**：修改 proxy target 为 `http://localhost:8088/autoee-iot-ems`。

### 5.6 Vue 组件 warning

**现象**：
- `Logo.vue:43` — Property or method "logo" is not defined
- `RightToolbar/index.vue:90` — Property or method "title" is not defined

**解决**：
- `Logo.vue`：启用被注释的 `logo` computed 属性，在 `data()` 中添加 `logoImg` 和 `title`
- `RightToolbar/index.vue`：合并两个 `computed` 块为一个（Vue 2 不支持多个 computed），恢复 `props` 块

## 六、Maven 编译环境要求

| 项目 | 要求 |
|------|------|
| JDK | **Java 21**（IntelliJ JBR 路径：`C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.1.1\jbr`） |
| Lombok | **1.18.36**（已从 1.18.26 升级） |
| Maven | 3.9.x（IntelliJ 内置） |
| source/target | 1.8 |
| Spring Boot | 2.7.9 |

**JAVA_HOME 设置方式**（临时）：

```bash
set "JAVA_HOME=C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.1.1\jbr"
```

**JAVA_HOME 设置方式**（永久，推荐通过 IntelliJ Run Configuration）：

在 IntelliJ IDEA 的 Maven 运行配置中设置 JDK 为 JBR 21。

## 七、启动脚本汇总

### start-local.bat（后端）

```bat
@echo off
cd /d "D:\code\gitcp\inspur-ems\deep-ems0\zhurong-ems-admin\target"
start "zhurong-ems-backend" java -jar zhurong-ems-admin.jar --spring.profiles.active=local --server.port=8088 --server.servlet.context-path=/autoee-iot-ems
```

### start-frontend-dev.bat（前端）

```bat
@echo off
cd /d "D:\code\gitcp\inspur-ems\deep-ems0\zhurong-admin-ui"
set NODE_OPTIONS=--openssl-legacy-provider
npm run dev
```

### build.bat（构建）

```bat
@echo off
set "JAVA_HOME=C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.1.1\jbr"
cd /d "D:\code\gitcp\inspur-ems\deep-ems0"
"C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.1.1\plugins\maven\lib\maven3\bin\mvn.cmd" clean package -DskipTests
```

## 八、故障排查速查

| 症状 | 原因 | 解决 |
|------|------|------|
| Maven 报 `Compilation failure` 无详情 | `<fork>true</fork>` + Lombok 不兼容 | 升级 Lombok 到 1.18.36 |
| `NoSuchFieldError: JCImport` | Lombok 版本太旧 | 升级 Lombok |
| `setDefaultCharset` 找不到 | Undertow API 变更 | 删除该调用 |
| 启动报 TDengine 连接失败 | td 数据源 enabled 不生效 | 删除 td 配置块 |
| 前端报 `ERR_CONNECTION_REFUSED:20080` | proxy target 端口错误 | 改为 8088 |
| 前端报 `系统内部错误` | 后端未启动或端口不对 | 检查后端端口是否 8088 |
| MQTT 连接失败（不影响核心功能） | EMQX 未启动 | `docker compose --profile iot up -d emqx` |

## 九、文件修改清单（本次）

| 文件 | 修改内容 |
|------|---------|
| `pom.xml` | Lombok 1.18.26 → 1.18.36 |
| `zhurong-ems-framework/.../UndertowConfig.java` | 移除 `setDefaultCharset` 调用 |
| `zhurong-ems-admin/.../application-local.yml` | 删除 td 数据源配置块 |
| `zhurong-admin-ui/vue.config.js` | proxy target 20080 → 8088 |
| `zhurong-admin-ui/src/layout/.../Logo.vue` | 启用 logo computed 属性 |
| `zhurong-admin-ui/src/components/RightToolbar/index.vue` | 合并 computed 块，恢复 props |
