# EMS 接入共享中间件（shared-infra）本地研发联调经验

> 适用范围：将本系统（deep-ems0 / 智碳能源管理系统）的中间件收敛到
> `D:\code\shared-infra` 共享中间件，并以「本地研发 dev 调试」方式运行；
> 原生产部署脚本（docker-compose.full.yml / deployment/docker-compose-54.yml）保持不动。

---

## 一、整体方案

- **中间件统一收口到 shared-infra**（Docker Compose 多 profile），业务系统通过新增
  `application-local` profile 指向本机 `localhost` 映射端口。
- **生产部署脚本一律不改**，避免影响线上。
- **原遗留卷（volume）保留作只读**，导出数据后不删除，保证可回滚。

### shared-infra 连接参数速查（localhost 映射端口）

| 组件 | 地址 | 账号 / 口令 | 库 / 说明 |
|---|---|---|---|
| MySQL | `localhost:3306` | `root` / `123456` | 库 `autoee_ems`、`xxl_job`（已从原卷导出导入） |
| Redis | `localhost:6379` db `13` | **有密码** `difyai123456` | 共享 Redis 实际带密码，勿误判为无密码 |
| RabbitMQ | `localhost:5672` vhost `admin_vhost` | `rabbitmq` / `rabbitmqpassword` | 用户对 `admin_vhost` 全权限（`.*`） |
| EMQX(MQTT) | `tcp://localhost:1883` Dashboard `18083` | `admin` / `public` | |
| TDengine | `localhost:6041`(REST) 库 `energy` | `root` / `taosdata` | `energy` 用户 `Difyai@123456` |

---

## 二、数据迁移（MySQL 存量库）

1. 写临时只读导出 Compose（示例 `.dev/ems-mysql-export.yml`）：

```yaml
services:
  ems-mysql-export:
    image: mysql:8.0
    container_name: zhurong-ems-mysql-export
    restart: "no"
    command: ["--default-authentication-plugin=mysql_native_password"]
    volumes:
      - zhurong-ems-mysql-data:/var/lib/mysql   # 外部卷，external: true
      - ./.dev/dumps:/dumps
volumes:
  zhurong-ems-mysql-data:
    external: true
```

2. 导出容器**不映射宿主机端口**（shared-mysql 已占 3306），用 `docker exec` 进容器操作。
3. 导出到挂载目录（用 `sh -c` 包一层 + 输出到卷目录，避免 PowerShell 重定向/转义问题）：

```bash
docker exec zhurong-ems-mysql-export sh -c \
  "mysqldump -uroot -proot --single-transaction --routines --triggers --databases autoee_ems xxl_job > /dumps/ems_full.sql 2> /dumps/dump.stderr"
```

4. 导入 shared-mysql（dump 含 `CREATE DATABASE`，无需预建库）：

```bash
docker cp .dev/dumps/ems_full.sql shared-mysql:/tmp/
docker exec shared-mysql sh -c "mysql -uroot -p123456 -h127.0.0.1 < /tmp/ems_full.sql"
```

### 避坑
- **bind 相对路径按 compose 文件所在目录解析**：文件放在 `deep-ems0/.dev/` 时 `./.dev/dumps`
  会解析为 `deep-ems0/.dev/.dev/dumps`，产生嵌套目录。尽量用绝对路径或 `-f` 指定文件时核对。
- 验证是否真的导成：`SELECT table_schema,COUNT(*) FROM information_schema.tables WHERE table_schema IN ('autoee_ems','xxl_job') GROUP BY table_schema;`

---

## 三、TDengine 3.3.6.13 的“四连坑”（本轮最大成本）

1. **镜像自带 `entrypoint.sh`** 会自动初始化 `/docker-entrypoint-initdb.d/*.sql` 并自动拉起
   `taosd`，**不要**再用 `command: sh -c "sleep 15 && taos source & taosd"` 叠加启动命令，
   否则与自带 entrypoint 冲突 → `sleep: missing operand` 崩溃。
2. **不支持 `CREATE USER IF NOT EXISTS`**，用标准语法：
   `CREATE USER energy PASS 'difyai123456';` 再 `GRANT ALL ON energy.* TO energy;`
3. **3.3 强制密码复杂度**（需大小写字母 + 数字/特殊字符），`difyai123456`（纯小写+数字）会被拒。
4. 不要用 `TAOS_ROOT_PASSWORD` 把 root 密码改成自定义值 → entrypoint 后续 `taos -f ... -p'xx'`
   登录失败 → **循环重启**。**保持 root 默认 `taosdata`**（也正好是应用期望的账号）。

### 排障手法
- 先 `docker logs` 看 entrypoint 的 `+` 痕迹定位崩溃点（故障常在初始化分支）。
- 出现“健康检查通过后又重启（255）”多为 **volume 残留不一致初始化状态**，直接
  `docker compose ... down --volumes` 清卷重来最干净。
- 每次验证用真实连接确认：`taos` 原生/REST 用应用同款账号口令。
- 前后对镜像行为有疑问，直接 `docker inspect` 看默认 Entrypoint/Cmd，再 `docker run --rm --entrypoint cat` 读脚本。

---

## 四、配置对齐（application-local）

**先确认应用到底连什么，再初始化共享中间件**。依据 `application-prod.yml` 与
`docker-compose.full.yml` 的 `SPRING_*` 覆盖得到：

- MySQL：`root/123456`、库 `autoee_ems`
- Redis：`localhost:6379`、db `13`、**密码 `difyai123456`**
- RabbitMQ：`localhost:5672`、`rabbitmq/rabbitmqpassword`、vhost `admin_vhost`
- MQTT(EMQX)：`tcp://localhost:1883`、`admin/public`
- TDengine：`localhost:6041`、库 `energy`、`root/taosdata`（避免 URL 特殊字符）

业务侧新增 `zhurong-ems-admin/src/main/resources/application-local.yml`，自包含上述连接；
Run Config 设 `--spring.profiles.active=local`（端口 `8088`，context-path `/autoee-iot-ems`）。

### 避坑
- 同一个 `spring.redis` 同时被 Spring 与 Redisson 消费，若 Redis 有密码务必在 local 显式写
  `spring.redis.password`，并建议 `redisson.singleServerConfig` 同步补齐。
- **`spring.config.additional-location` 只能指到单个 `application-local.yml` 文件**；指到
  `src/main/resources` 目录会读到未被 Maven 过滤的 `@xxx@` 占位符 → YAML Scanner 报错。
- **xxl-job 调度中心、SBA 监控不在 shared-infra**，local 里 `enabled: false` 关闭，避免启动即连不上。

---

## 五、前端（Vue 2.6 / Vue CLI 4）

- Node 22 需 `--openssl-legacy-provider`（`package.json` 的 dev 脚本已带），dev 端口 `9029`。
- `.env.development`：`VUE_APP_BASE_API = http://localhost:8088/autoee-iot-ems`（已对齐，无需改）。
- **Vue 2.6 不允许属性内 mustache**：`yName="{{ $t(...) }}"` 会编译失败，须写 `:yName="$t(...)"`。
  这类错误在全量同步导入视图时因单个组件失败会**阻断整个应用编译**。
  排查手法：`grep -r '="{{' src --include=*.vue` 全量找，一次性修完，避免反复长时编译。
- 登录账号：`admin / admin123`。

---

## 六、快速复位清单（照搬即可）

1. shared-infra 全部起来后，逐个探活（mysql/redis/rabbit/mariadb/mongodb/minio/emqx/tdengine）。
2. 存量数据：临时只读容器 `mysqldump` → `docker cp` → 导入 shared-mysql（见第二节）。
3. 业务系统加 `application-local.yml`（localhost 映射 + 账号口令）。
4. 后端 Run Config：`--spring.profiles.active=local`。
5. `/autoee-iot-ems/actuator/health` 校验 db/redis/rabbit UP，日志见 `MQTT 连接成功`。
6. 前端 `.env.development` 的 `VUE_APP_BASE_API` 指向 `localhost:8088`，`npm run dev`。
7. 修 Vue 语法类编译错误（先搜索再修再重编译）。
8. 浏览器打开 `http://localhost:9029/`，`admin / admin123` 登录验证。

---

## 七、核心心得

- **先确认应用真实连接参数，再初始化共享中间件**；每个组件用真实探活验证后才算数。
- 镜像版本行为差异（如 tdengine 3.3 的密码策略、自带 entrypoint）**直接看容器 entrypoint 与清卷重来**，不要靠猜。
- 端口冲突用「临时只读容器 + 不映射端口 + exec 进去操作」规避。
- 生产与本地通过**独立 profile** 隔离，风险最小。