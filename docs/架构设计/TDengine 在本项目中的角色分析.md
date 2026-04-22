# TDengine 在本项目中的角色分析

> 作者：AI 辅助分析
> 日期：2026-04-03
> 版本：v1.0

---

## 一、TDengine 在本项目中的角色分析

### 1.1 架构定位：时序数据高速写入与查询

TDengine 在本项目中承担的是**时序数据写入层**，与 MySQL 形成"双写 + 按需读取"的架构：

```
MQTT 设备上报消息
       ↓
  MyAckReceiver (RabbitMQ Consumer)
       ↓
       ├── 始终写入 → MySQL (electricity_u/i/p/w, water_consumption)
       │
       └── 条件写入 → TDengine (electricityw/i/p/u, water)
                     (当 tdDbEnabled = true)
```

| 数据类型 | MySQL 表 | TDengine 子表 | 用途 |
|----------|---------|---------------|------|
| 电能 | `electricity_w` | `electricityw` | 原始数据存储 |
| 电流 | `electricity_i` | `electricityi` | 原始数据存储 |
| 电功率 | `electricity_p` | `electricityp` | 原始数据存储 |
| 电压 | `electricity_u` | `electricityu` | 原始数据存储 |
| 水耗 | `water_consumption` | `water` | 原始数据存储 |

两边的数据**完全相同**——同一条 MQTT 消息同时写入两个数据库。

### 1.2 读取路径：双读分支

`EnergyServiceImpl` 中有 **5 个定时任务方法**（XXL-Job），每个都有如下双读逻辑：

```java
if (tdDbEnabled) {
    // 通过 EnergyMapper (@DS("td")) 查询 TDengine
    // 分页、排序、按时间窗口过滤
} else {
    // 查询 MySQL 的 ElectricityW/I/P/U 表
    // 手动转换为 EnergyVo 格式
}
```

| 方法 | 功能 | 数据源 |
|------|------|--------|
| `statisticsElectricityEnergy()` | 电能统计（按小时） | TD/MySQL |
| `statisticsPowerEnergy()` | 功率统计（按小时） | TD/MySQL |
| `statisticsCurrentEnergy()` | 电流统计（按小时） | TD/MySQL |
| `statisticsVoltageEnergy()` | 电压统计（按小时） | TD/MySQL |
| `queryPowerParameter()` | 电力参数/日原始数据查询 | TD/MySQL |

### 1.3 当前状态总结

| 维度 | 状态 |
|------|------|
| **pom.xml 依赖** | ✅ 已引入 `taos-jdbcdriver 3.2.2` |
| **dev 环境** | ❌ TDengine 配置已注释，tdDbEnabled 读取不到导致启动失败（已修复，加默认值 `false`） |
| **prod 环境** | ✅ 配置存在且 `enabled: true`，但目标地址是 `localhost:6041` |
| **docker-compose** | ⚠️ TDengine 服务在主文件已注释，在部署文档中被激活 |
| **Dockerfile** | ❌ 没有安装 TDengine 客户端库，纯 RESTful 驱动连接无需本地安装 |
| **数据库脚本** | ⚠️ 两处脚本 (`数据库脚本/` 和 `docs/`) schema 不一致，实际运行时用的是 `数据库脚本/` 中的 `energy` 库 |

---

## 二、Schema 不一致问题（需修复）

当前项目存在两套 TDengine 建库脚本，且与 prod 配置不对应：

| 来源 | 数据库名 | 表结构 |
|------|---------|--------|
| `数据库脚本/tdengine-数据库脚本.md` | `energy` | 超级表 `energy` + 5张子表 |
| `docs/第一阶段基础建设详细设计.md` | `deep_ems` | 超级表 `energy_meter` |
| `application-prod.yml` 配置 | `energy` | 与脚本A一致，但 schema 结构未确认 |

建议统一为脚本A的 `energy` 数据库 + 超级表结构。

---

## 三、对接方案

### 方案 A：本地开发环境接入 TDengine（推荐）

**场景**：在本地/测试机上运行 TDengine 服务，供 `dev` profile 使用。

#### Step 1 — 安装 TDengine Server

**Windows 方式：**
下载 TDengine Windows 版 (3.2.x)：https://www.taosdata.com/downloads

**Docker 方式：**
```bash
docker run -d --name tdengine \
  -p 6030:6030 -p 6041:6041 \
  -v D:/tdengine/data:/var/lib/taos \
  -v D:/tdengine/log:/var/log/taos \
  tdengine/tdengine:3.2.0.0
```

#### Step 2 — 修改 `application-dev.yml`

```yaml
td:
  enabled: true
  driverClassName: com.taosdata.jdbc.rs.RestfulDriver
  url: jdbc:TAOS-RS://localhost:6041/energy?user=root&password=taosdata&timezone=Asia/Shanghai
  username: username   # 默认用户名
  password: password    # 默认密码
```

#### Step 3 — 执行建库脚本

在 TDengine CLI 中执行以下 SQL：

```sql
CREATE DATABASE energy KEEP 365000;
USE energy;

CREATE STABLE energy (
    ts timestamp,
    client_id varchar(50),
    val float
) TAGS (
    type varchar(50)
);

CREATE TABLE electricityw USING energy TAGS('electricityw');
CREATE TABLE electricityi USING energy TAGS('electricityi');
CREATE TABLE electricityu USING energy TAGS('electricityu');
CREATE TABLE electricityp USING energy TAGS('electricityp');
CREATE TABLE water USING energy TAGS('water');
```

#### Step 4 — 验证接入

重启应用，观察启动日志中是否出现 TDengine 连接成功的信息，`tdDbEnabled` 将变为 `true`，MQTT 消息会同时写入 MySQL 和 TDengine。

---

### 方案 B：远程 TDengine 服务器接入（生产/测试）

**场景**：连接远程服务器上的 TDengine 实例（如内网 IP 或云端）。

只需修改 `application-{env}.yml` 中的 URL：

```yaml
td:
  enabled: true
  url: jdbc:TAOS-RS://192.168.8.x:6041/energy?user=root&password=taosdata&timezone=Asia/Shanghai
```

**注意事项：**
- RESTful 模式下 TDengine 端口固定为 **6041**，不是 6030
- 确保服务器防火墙/安全组开放了 6041 端口
- 建议同时在 Docker Compose 中部署 TDengine 服务作为备份

---

### 方案 C：保持现状（只用 MySQL）

如果当前不需要 TDengine 的高性能时序查询能力，保持 `enabled: false`（但必须是**完全注释掉**整个 `td` 块，而非仅设置 `enabled: false`）：

```yaml
# 完全注释掉 td 数据源块
# td:
#   enabled: false
#   url: jdbc:TAOS-RS://...
#   ...
```

所有数据走 MySQL，定时统计任务也完全从 MySQL 读取，历史数据查询性能可能不如 TDengine，但功能完全正常。

---

## 四、后续优化建议

| 优先级 | 建议 | 说明 |
|--------|------|------|
| **高** | 统一建库脚本 | 合并 `数据库脚本/` 和 `docs/` 中的 TDengine SQL，建议统一用 `energy` 数据库 + 超级表方案 |
| **高** | 在 `application-test.yml` 中加回 td 块（disabled） | 方便开发/测试时快速切换开关，而不是完全删除配置 |
| **中** | 将 `@Value` 默认值统一为 `false` | 已完成两处，剩下的地方注意保持一致 |
| **中** | 添加 TDengine 连接池健康检查 | 当前 RESTful 驱动无连接池，超时会影响启动，建议加 `connectionTimeout` 限制 |
| **低** | 探索 TDengine 原生驱动 (`TSDBDriver`) | 性能优于 RESTful 驱动，需在运行环境中安装 TDengine 客户端库 |
| **低** | 利用 TDengine 流计算替代 XXL-Job | TDengine 原生支持滚动窗口、流计算，可将统计任务下沉到数据库层 |

---

## 五、相关文件索引

| 文件 | 路径 | 说明 |
|------|------|------|
| pom.xml | `zhurong-ems-admin/pom.xml` | TDengine Maven 依赖 |
| application-prod.yml | `zhurong-ems-admin/src/main/resources/application-prod.yml` | 生产环境配置（enabled: true） |
| application-dev.yml | `zhurong-ems-admin/src/main/resources/application-dev.yml` | 开发环境配置（已注释） |
| application-docker.yml | `zhurong-ems-admin/src/main/resources/application-docker.yml` | Docker 环境配置（disabled） |
| EnergyMapper.java | `zhurong-ems-system/src/main/java/com/ruoyi/system/mapper/EnergyMapper.java` | 唯一使用 `@DS("td")` 的 DAO |
| Energy.java | `zhurong-ems-system/src/main/java/com/ruoyi/system/domain/Energy.java` | TDengine 实体类 |
| EnergyVo.java | `zhurong-ems-system/src/main/java/com/ruoyi/system/domain/vo/EnergyVo.java` | TDengine 查询返回 VO |
| EnergyType.java | `zhurong-ems-system/src/main/java/com/ruoyi/system/domain/enums/EnergyType.java` | 能源类型枚举（表名映射） |
| MyAckReceiver.java | `zhurong-ems-system/src/main/java/com/ruoyi/system/config/MyAckReceiver.java` | 写入 TDengine（MQTT Consumer） |
| EnergyServiceImpl.java | `zhurong-ems-system/src/main/java/com/ruoyi/system/service/impl/EnergyServiceImpl.java` | 读取 TDengine（双读逻辑） |
| tdengine-数据库脚本.md | `数据库脚本/tdengine-数据库脚本.md` | 建库 SQL（当前使用） |
| 第一阶段基础建设详细设计.md | `docs/第一阶段基础建设详细设计.md` | 设计文档中的 Schema |
| docker-compose.yml | `docker-compose.yml` | Docker 编排（已注释 TDengine） |
| docker-compose.yml | `docs/部署指南/docker-compose.yml` | 部署文档中的 Docker（TDengine 激活） |
| Deep-EMS 系统中间件清单.md | `docs/部署指南/Deep-EMS 系统中间件清单.md` | 中间件清单 |
