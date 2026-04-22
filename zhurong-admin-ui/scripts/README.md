# 国际化改造 - 并行处理工具

## 📖️ 功能说明

本工具用于自动化 Vue 项目的国际化（i18n）改造，支持**多线程/多进程并行处理**，大幅提升大规模文件改造效率。

## ✨ 核心特性

| 特性 | 说明 |
|------|------|
| **并行处理** | 基于 `worker_threads` 的真多线程，充分利用 CPU 多核 |
| **智能识别** | 自动识别 `.vue` 文件中的硬编码中文文本 |
| **上下文感知** | 根据标签类型（label/button/table）自动推断 i18n 命名空间 |
| **批量替换** | 一键替换为 `$t('key')` 调用格式 |
| **安全机制** | 干运行模式、错误隔离、数据一致性保证 |
| **详细报告** | JSON + Markdown 双格式报告 |

## 🚀 使用方式

### 安装依赖
```bash
cd scripts
npm install
```

### 基础命令

```bash
# 1. 扫描并分析（干运行模式）
node i18n-parallel.js src/views --dry-run --report

# 2. 查看报告
cat reports/i18n-report-*.md

# 3. 实际执行修复（慎用！建议先 dry-run 确认无误）
node i18nparallel.js src/views --fix --report
```

## 🔧 工作原理

```
┌─────────────────────────────────────────────┐
│              主进程 (Main Thread)           │
│                                             │
│   1. 递归扫描目标目录                     │
│  2. 过滤包含中文的 .vue 文件               │
│   3. 按并发数分批分配任务                 │
│     ┌────────┐ ┌────────┐ ┌────────┐        │
│     │ Worker1 │ │ Worker2 │ │ WorkerN │        │
│     │ file1   │ │ file5   │ │ ...    │        │
│     │ file2   │ │ file6   │        │        │
│     │ file3   │ │        │        │        │
│     │ file4   │ │        │        │        │
│     └────────┘ └────────┘ └────────┘        │
│                                             │
│   4. 收集结果 & 生成报告                    │
└─────────────────────────────────────────────┘
```

## 📋 处理流程

### Step 1: 文件扫描
- 递归遍历指定目录及其子目录
- 只处理 `.vue` 扩展名的文件
- 快速过滤：检查文件是否包含中文字符

### Step 2: 内容解析
对每个文件进行三部分解析：
```vue
<template>
  <!-- 解析 label="用户名称" placeholder="请输入..." >按钮文本< -->
</template>

<script>
  // 解析 "修改成功" this.$t('...') 已有调用
</script>
```

### Step 3: Key 推断规则

根据文本所在位置自动选择命名空间：

| 位置 | 示例 | 命名空间 | 输出格式 |
|------|------|----------|----------|
| `:label=` | 用户名称 | form.* | `:label="$t('form.userName')"` |
| `:placeholder=` | 请输入 | placeholder.* | `:$t('placeholder.userName')"` |
| `>文本<` | 查询 | button.* | `{{ $t('button.search') }}` |
| `message()` | 成功 | message.* | `this.$t('message.success')` |
| `title=""` | 添加 | message.* | `this.$t('message.addSuccess')` |

### Step 4: 并行执行

使用 Node.js `worker_threads` 模块实现真正的多线程：

```javascript
// 创建工作线程池
const pool = new WorkerPool('./worker.js', {
  min: 4,
  max: os.cpus().length
});

// 分配任务
const results = await Promise.all(
  files.map(file => pool.exec(processFile, { file }))
);
```

### Step 5: 结果汇总

生成两种格式的报告：

#### 1. JSON 报告 (机器可读)
```json
{
  "meta": {
    "totalFilesScanned": 217,
    "totalChineseFiles": 180,
    "duration": 15230,
    "concurrency": 8
  },
  "overview": {
    "totalReplacements": 4560,
    "existingI18n": 89,
    "errors": 0
  },
  "recommendations": [...]
}
```

#### 2. Markdown 报告 (人类可读)
```markdown
# 国际化改造报告

## 改造进度
| 指标 | 数量 |
|------|------|
| 扫描文件总数 | 217 |
| 包含中文的文件 | 180 |
| 总替换点数 | 4560 |

## 详细文件列表

### src/views/login.vue
- **中文统计**: 15 字
- **需替换**: 12 处
- **已有i18n**: 8 处

## 💡 改进建议
⚠️ [HIGH] **未处理文件**: 50 个文件尚未开始国际化
...
```

## ⚙️ 并发控制策略

### 任务分配策略
```javascript
class TaskQueue {
  constructor(concurrency) {
    this.queue = [];
    this.active = new Set();
    this.maxConcurrency = concurrency;
  }
  
  async run(task) {
    if (this.active.size >= this.maxConcurrency) {
      await this._waitForSlot();
    }
    this.active.add(taskId);
    try {
      return await task();
    } finally {
      this.active.delete(taskId);
      this._next(); // 触发下一个等待的任务
    }
  }
}
```

### 同步机制
1. **文件级隔离**: 每个文件独立处理，无共享状态
2. **原子写入**: 写入前校验内容完整性
3. **错误隔离**: 单个文件失败不影响整体任务
4. **进度回调**: 实时显示处理进度

### 异常处理策略
```javascript
try {
  const result = await processor.parse();
} catch (error) {
  // 记录错误但不中断其他任务
  errors.push({
    file: filePath,
    error: error.message,
    stack: error.stack
  });
  
  // 继续处理下一个文件
  continue;
}
```

## 📊 性能指标

| 场景 | 单线程 | 并行(4核) | 并行(8核) |
|------|--------|-----------|-----------|
| 100 个文件 | ~60s | ~20s | ~12s |
| 200 个文件 | ~120s | ~35s | ~20s |
| 500 个文件 | ~300s | ~80s | ~45s |

**预期提升**: 4-8倍速度提升（取决于 CPU 核心数）

## 🔒 安全保障

### 1. 干跑模式 (`--dry-run`)
```bash
node i18n-parallel.js src/views --dry-run --report
# 只分析不修改，生成报告供审核
```

### 2. 差异对比
```javascript
// 修改前后对比
{
  before: 'label="用户名称"',      // 原始
  after: ':label="$t(\'form.userName\')"', // 修改后
}
```

### 3. 回滚机制
```bash
# 所有原始文件已自动备份到 reports/backup/
# 如需回滚:
cp reports/backup/*.vue src/views/
```

### 4. 校验规则
- 不修改已有 `$t()` 调用
- 不修改 `<style>` 和 `<script>` 中的注释
- 不处理纯 ASCII 字符串
- 保持代码缩进和格式

## 🎯 使用建议

### 首次使用
```bash
# 1. 先小范围测试（如单个模块）
node i18n-parallel.js src/views/system --dry-run --report

# 2. 检查报告确认无误
cat reports/i18n-report-*.md

# 3. 小范围试修复（3-5个文件）
node i18n-parallel.js src/views/system/user --fix --report

# 4. 全量执行
node i18nparallel.js src/views --fix --report
```

### 团队协作
```bash
# 划分模块，多人并行处理
开发者A: node i18n-parallel.js src/views/system --fix
开发者B: node i18n-parallel.js src/views/business --fix
开发者C: node i18n-parallel.js src/views/components --fix

# 合并报告
node i18n-merge-reports.js reports/*-report.json
```

## 🔧 高级配置

### 自定义映射规则
在 `i18n-parallel.js` 中修改 `I18N_MAPPING_RULES`：

```javascript
'I18N_MAPPING_RULES': {
  'custom': {
    examples: {
      '我的特殊文本': 'mySpecialKey'
    }
  }
}
```

### 调整并发数
根据系统资源动态调整：
```bash
# 默认使用所有 CPU 核心
node i18n-parallel.js src/views --concurrency=4

# 在低配机器上减少并发
node i18n-parallel.js src/views --concurrency=2
```
