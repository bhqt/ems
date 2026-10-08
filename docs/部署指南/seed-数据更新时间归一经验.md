# Demo Seed「数据更新到最新时间」归一化经验

> 适用范围：`数据库脚本/board_demo_seed.sql` 与 `数据库脚本/board_demo_seed.ps1`
> 沉淀日期：2026-10-09
> 最近相关提交：`740e3c6` `fix(seed): 修正 hospital_device.device_name 中文乱码占位符` 及同批未提交工作区改动
> 适用页面：`/data-board`（综合数据看板）、`/dashboard`、`/hospital/bigScreen`

## 一、问题现象

研发环境跑完演示 seed 数据后，大屏 / 数据看板仍可能表现出「数据没更新到最新时间」，具体可拆为三类：

1. **小时桶缺数据**：电功率曲线（`getDailyP`）、能耗日趋势（`getDayTrend`）的曲线后段（最近 1~3 小时）显示为 `--`，整点附近尤其明显。
2. **整点数据失真**：明明刚写入一条，曲线在小时切换时点跳变 / 错位；功率峰值与时段曲线对不上号。
3. **中文字段全是 `?`**：`equipment_info.device_name`、`hospital_device.device_name`、`item_topology.item_name` 等字段在大屏中显示为问号占位符。

## 二、根因复盘（按链路自上而下）

### 1. 字符集根因：PowerShell 管道导致 UTF-8 失效

`Get-Content $SqlFile | docker exec -i mysql ...` 在 PowerShell 下会按 `[Console]::OutputEncoding`（默认 OEM / GBK）重编码 SQL 文本流，MySQL 客户端即便服务端是 `utf8mb4`，收到的字节仍是 GBK，被服务端按 `utf8mb4` 解码后一切非 ASCII 字符落入 `'?'` 占位符。

**已应用修复**（`board_demo_seed.ps1`）：

```powershell
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
cmd /c "docker exec -i `"$MysqlContainer`" mysql --default-character-set=utf8mb4 -u$MysqlUser -p$MysqlPwd -N -B `"$DbName`" < `"$SqlFile`""
```

两条铁律：
- PowerShell 进程内 `Out-File` 默认仍会切回 OEM，遇到「写到文件但要保留中文」也要 `[Console]::OutputEncoding=UTF8` 后再写入。
- `mysql` 客户端必须显式 `--default-character-set=utf8mb4`，即便 `--B -N` 输出抑制了表头。

### 2. 时间分桶根因：`create_time` 与 `time` 必须落在正确的桶区间

后端 `getDailyP`（`EnergyServiceImpl.java:623`）按 `create_time` 以**开区间 `(h:00, h+1:00)`** 对 24 小时分桶：

```text
e.getCreateTime().after(bucketStart) && e.getCreateTime().before(bucketEnd)
```

而 `getWDayByJob`（即 `getDayTrend` 的内部实现，`EnergyServiceImpl.java:838`）按 `time` 字段以**半开区间 `[h:00, h+1:00)`** 分桶：

```text
e.getTime().before(endHour) && DateUtil.compare(e.getTime(), finalStartTime) >= 0
```

两个接口落地到 seed 脚本时的注意点：

| 字段 | 取值 | 落入的桶接口 | 必须落在 | 推荐写法 |
|------|------|----------------|----------|----------|
| `power_statistics.create_time` | 业务写入时间戳 | `getDailyP`（开区间） | 严格 `(h:00, h+1:00)`，整点会落到下一桶 | `TIMESTAMP(d, SEC_TO_TIME(h * 3600 + 30))` 即 `h:00:30` |
| `power_statistics.time` | 该小时整点 | `getDailyP` 数据库 `between` 过滤（半开） | 任意，`h:00` 即可 | `TIMESTAMP(d, SEC_TO_TIME(h * 3600))` |
| `energy_statistics.time` | 该小时整点 | `getDayTrend` / `getConsumptionStatistics`（半开） | 任意 | `TIMESTAMP(d, SEC_TO_TIME(h * 3600))` |

**绝对不要**把 `create_time` 写成 `NOW()` 或任何「此刻」时刻：

- `NOW()` 与 `d` 不在同一天，曲线会被错位到「脚本执行当天整点那一桶」，导致昨天甚至前天的整点曲线误归最近小时。
- 写入时刻若落在整点 `h:00:00`，在 `getDailyP` 的开区间判定里会被前一个桶（`h-1:00`）吞掉，造成「整点数据丢桶」的观感。

### 3. 时间字段类型根因：种子脚本里 `TIMESTAMP(d, SEC_TO_TIME(h * 3600))`

- `d` 来自 `DATE_SUB(DATE(NOW()), INTERVAL @day_offset DAY)`，是 `DATE`。
- `SEC_TO_TIME(h * 3600)` 返回 `TIME`，对应 `h:00:00`。
- `TIMESTAMP(date, time)` 拼接结果 = 当天 `h:00:00`，正常。
- 需要「错开 30 秒」则是 `SEC_TO_TIME(h * 3600 + 30)`，不要写成 `SEC_TO_TIME(h * 3600 + 0.5)`，MySQL 不吃小数。

### 4. 接口字段选择根因：`getDailyP` 与 `getDayTrend` 用的是不同列

| 看板调用 | SQL 表 | 主要过滤字段 |
|----------|---------|----------------|
| `/data/energy/getDailyP` | `power_statistics` | `equipment_sn IN (...)` + `time BETWEEN yesterday, now`（拉数据） + `create_time`（分小时桶 + 峰值） |
| `/data/energy/dayTrend` | `energy_statistics` | `time >= todayStart` + `time`（分小时桶） |
| `/data/energy/getConsumptionStatistics` | `energy_statistics` | `time BETWEEN today_start, now`（按日聚合） |
| `/equipment/getAllStatus` | `equipment_info` | `status` 字段（不受时间影响，但 `item_topology.device_id` 为空会被前端兜底逻辑跳成 `1`） |

排查顺序：
1. 曲线缺数据 → 检查 `create_time` / `time` 是否落在该小时内。
2. 整点切换错位 → 检查桶边界是开区间还是半开区间。
3. 中文 `?` → 检查导入脚本的字符集链路。
4. 数据整体没出现 → 检查 `item_topology.device_id` 是否为空（前端会跳过空 `deviceId` 的拓扑，导致 `areaId` 取不到）。

### 5. 前端兜底与 `areaId` 选择根因

`zhurong-admin-ui/src/views/dataBoard/index.vue` 调用 `getAreaList()` 时：

- 旧逻辑只用 `topologyTreeSelect`，递归找首个 `deviceId` 非空的节点；拓扑里存的是「建筑」，「浪潮山东园区」根节点 `device_id = NULL`，「A栋-研发楼」的 `device_id` 用逗号分隔串「JN-D-LD-001,JN-D-LD-002,JN-S-LD-001」，匹配失败时直接 fallback `areaId = 1`，导致曲线读不到 A 栋数据。
- 新逻辑（已应用到工作区，**尚未提交**）：
  - 先 `topologyTreeSelect()` 拿首节点 ID 候选；
  - 再 `listItemTopology({})` 拉平列表，过滤 `delFlag !== '2' && deviceId && deviceId.length > 0`，按 `orderNum` 升序排序，取首个；
  - 全部失败兜底 `areaId = 8880000000000000003`（与 seed 中 `A栋-研发楼` 的 `item_id` 对齐）。

关键原则：**`item_topology.device_id` 字段是字符串（多设备逗号拼接），非空即视作该建筑有数据**；前端没必要再深度递归，叶子建筑直接选第一个 `orderNum` 最小且 `deviceId` 非空的即可。

### 6. 大屏 `mixins/chartResize.js`（新增，未提交）

为使 ECharts 容器在 flex 父元素中正确 resize，所有 `AreaCompareChart / CategoryChart / DeviceStatusChart / PowerLineChart / RankChart / TrendChart` 均已 `mixins: [chartResize]`，共享 `mounted/activated/beforeUnmount` 钩子；`getDailyP` 拉到的 `max` 是 `BigDecimal`，前端 `.setScale(2, RoundingMode.HALF_UP)` 后再渲染，否则会带小数尾巴。

## 三、可复用的脚本骨架

### 1. PowerShell 喂 SQL 入 Docker MySQL（中文不乱码）

```powershell
$SqlFile = "数据库脚本\board_demo_seed.sql"
$DbName = "autoee_ems"
$MysqlContainer = "shared-mysql"
$MysqlUser = "root"
$MysqlPwd = "123456"

[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
cmd /c "docker exec -i `"$MysqlContainer`" mysql --default-character-set=utf8mb4 -u$MysqlUser -p$MysqlPwd -N -B `"$DbName`" < `"$SqlFile`""
$exitCode = $LASTEXITCODE
if ($exitCode -eq 0) { "OK" } else { "FAIL $exitCode" ; exit $exitCode }
```

### 2. 「自然时间分桶」SQL 片段（功率统计）

```sql
INSERT INTO power_statistics
  (id, equipment_sn, energy_type, time, min, ave, max, create_by, create_time, update_by, update_time)
VALUES
  (@id, sn_var, '0',
   TIMESTAMP(d, SEC_TO_TIME(h * 3600)),
   ROUND(base_val * @ave_factor * 0.62, 2),
   ROUND(base_val * @ave_factor, 2),
   ROUND(base_val * LEAST(@ave_factor * 1.25, 1.60), 2),
   'admin',
   TIMESTAMP(d, SEC_TO_TIME(h * 3600 + 30)),  -- 关键：错开整点，开区间 (h:00, h+1:00) 容纳
   'admin', NOW());
```

### 3. 后端 `areaId` 选择逻辑（前端 dataBoard）

```js
return topologyTreeSelect().then((treeRes) => {
  const firstId = (treeRes.data && treeRes.data[0] && treeRes.data[0].id) || null;
  return listItemTopology({}).then((listRes) => {
    const list = (listRes.data || [])
      .filter(d => d.delFlag !== '2' && d.deviceId && d.deviceId.length > 0);
    if (list.length > 0) {
      list.sort((a, b) => (a.orderNum || 0) - (b.orderNum || 0));
      this.areaId = list[0].itemId;
      return;
    }
    this.areaId = firstId || 8880000000000000003; // seed 中 A栋-研发楼 item_id 兜底
  });
});
```

## 四、调试顺口溜（出问题时按顺序排查）

1. **乱码？** PowerShell + MySQL 客户端字符集双修 → `Out-File` 也要 UTF8。
2. **整点缺数据？** `create_time` 是不是 `NOW()` 或整点？改 `h:00:30`。
3. **昨天 / 前天误归到现在？** `d = DATE_SUB(DATE(NOW()), INTERVAL @day_offset DAY)` 是否在循环里被覆盖（`SET d = ... WHILE ...` 要保证 `d` 在内层不漂移）。
4. **大屏只显示 `--`？** `item_topology.device_id` 是否非空 + 前端 `areaId` 是否选中了那个拓扑。
5. **曲线峰值不对？** `power_statistics.create_time` 是否被旧逻辑写过 `NOW()`；清理后用 seed 脚本重灌。
6. **`getAllStatus` 全是 0？** `equipment_info.status` 是否在 `0/1/2` 之外（如空串，MyBatis-Plus 会被识别为 null 跳过）。
7. **前端 ECharts 高度异常？** 确认已 `mixins: [chartResize]`，且父容器给了 `flex / min-height: 0 / overflow: hidden`（已在 bigScreen/index.vue 中修正）。

## 五、相关文件索引

| 路径 | 角色 |
|------|------|
| `数据库脚本/board_demo_seed.sql` | 演示数据主种子（含 item_topology / equipment / energy / power / alarm） |
| `数据库脚本/board_demo_seed.ps1` | 幂等加载脚本（已修复字符集问题） |
| `数据库脚本/fix_device_name_garbled.sql` | `device_name` 中文乱码兜底回填（提交 `740e3c6`） |
| `zhurong-ems-system/.../service/impl/EnergyServiceImpl.java` | `getDailyP` / `getDayTrend` / `getConsumptionStatistics` 实现 |
| `zhurong-ems-system/.../service/impl/EquipmentInfoServiceImpl.java` | `getAllStatus` 状态分组实现 |
| `zhurong-admin-ui/src/views/dataBoard/index.vue` | 综合数据看板；`getAreaList()` 选择拓扑逻辑已重写 |
| `zhurong-admin-ui/src/views/hospital/bigScreen/index.vue` | 医院版大屏布局；面板 flex / overflow 调整 |
| `zhurong-admin-ui/src/views/hospital/bigScreen/mixins/chartResize.js` | ECharts resize 复用 mixin（新增，未提交） |
| `tmp-jar-fix/backend_run.log` | 本地后端 18088 启动日志（调试期最大来源） |

## 六、踩坑沉淀（直接抄走）

- 不要让 PowerShell 默认编码碰到中文字节流：所有 `Get-Content ... | docker exec` 都要 `[Console]::OutputEncoding=UTF8` 并切 `cmd /c "..." < file`。
- `mysql -N -B < file.sql` 必须加 `--default-character-set=utf8mb4`，否则服务端按 `utf8mb4` 解释 GBK 字节流会全部成 `?`。
- `create_time` 与 `time` 是两套坐标：分桶时一个用 `create_time`（开区间）、一个用 `time`（半开区间）；seed 写值时务必区分。
- 整点（`h:00:00`）在开区间桶判定里属于上一小时；想稳稳当到 `h` 小时，必须写 `h:00:30`。
- `TIMESTAMP(date, time)` 中 `time` 只吃整数秒，**不接受小数**。
- `item_topology.device_id` 用字符串保存多设备 SN（逗号分隔），Python 风格「判断长度 0」在 SQL 里要走 `IS NULL OR device_id = ''`，否则 `LEN()` 在 MySQL 默认会把尾随空格也算长度。
- 前端 `areaId` 兜底值要与 `board_demo_seed.sql` 中插入的「建筑 `item_id`」对齐；当 seed 重新生成时记得同步。
- 大屏面板用 flex 列布局时，子元素给 `flex: 1 1 auto; min-height: 0; overflow: hidden` 三件套，否则 ECharts `100%` 拿不到高度。
