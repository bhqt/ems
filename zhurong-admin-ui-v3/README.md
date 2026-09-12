# 智碳能源管理系统 - 现代化重构版 (zhurong-admin-ui-v3)

基于 Vue 3 + Ant Design Vue 4 + Vite + TypeScript + Pinia + UnoCSS 的现代化前端工程。

## 🚀 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| **核心框架** | Vue | 3.4+ |
| **构建工具** | Vite | 5+ |
| **UI 组件库** | Ant Design Vue | 4.x |
| **状态管理** | Pinia | 2.x |
| **路由** | Vue Router | 4.x |
| **CSS 方案** | UnoCSS + CSS Variables | 最新 |
| **国际化** | vue-i18n | 10+ |
| **HTTP 客户端** | Axios | 1.6+ |
| **日期处理** | Dayjs | 1.11+ |
| **图表** | ECharts 5 + @antv/g2 | 最新 |
| **代码规范** | ESLint 9 + Prettier + Stylelint | 最新 |
| **测试** | Vitest + Playwright | 最新 |
| **包管理** | pnpm | 9+ |

## 📦 项目结构

```
zhurong-admin-ui-v3/
├── public/                    # 静态资源
├── src/
│   ├── api/                   # API 接口层
│   │   ├── core/              # 请求核心配置
│   │   ├── modules/           # 业务模块接口
│   │   └── types/             # 类型定义
│   ├── assets/                # 资源文件
│   │   ├── styles/            # 样式文件
│   │   │   ├── tokens/        # 设计令牌
│   │   │   ├── themes/        # 主题预设
│   │   │   ├── uno.css        # UnoCSS 入口
│   │   │   ├── global.scss    # 全局样式
│   │   │   └── antd-overrides.scss
│   │   └── icons/             # SVG 图标
│   ├── components/            # 组件库
│   │   ├── common/            # 基础组件
│   │   ├── pro/               # 业务级组件
│   │   ├── layout/            # 布局组件
│   │   ├── charts/            # 图表封装
│   │   └── hospital/          # 医院专属组件
│   ├── composables/           # 组合式函数
│   ├── directives/            # 自定义指令
│   ├── layouts/               # 路由布局
│   ├── locales/               # 国际化
│   ├── router/                # 路由配置
│   ├── stores/                # Pinia Store
│   ├── utils/                 # 工具函数
│   ├── views/                 # 页面视图
│   ├── App.vue
│   ├── main.ts
│   └── vite-env.d.ts
├── uno.config.ts              # UnoCSS 配置
├── vite.config.ts             # Vite 配置
├── tsconfig.json              # TypeScript 配置
├── .eslintrc.cjs              # ESLint 配置
├── .prettierrc                # Prettier 配置
├── .stylelintrc.cjs           # Stylelint 配置
├── package.json
└── README.md
```

## 🎯 核心特性

### 1. 设计令牌系统
- 基于 CSS Variables 的主题系统
- 支持浅色/深色/医院主题三套主题
- 运行时动态切换，持久化存储

### 2. 业务级组件库
- **ProTable**: 标准列表页（搜索+表格+工具栏+分页+抽屉）
- **ProForm**: 标准表单页（JSON Schema 驱动、依赖字段、异步校验）
- **ProLayout**: 页面级布局
- **ProChart/ProStatistic**: 统计卡片与图表
- **ProSearchForm/ProDetail**: 搜索/详情页

### 3. 完整的权限体系
- 路由守卫（登录态、Token 刷新、菜单权限、按钮权限）
- 动态路由注册
- v-permission 指令

### 4. 国际化支持
- 中英双语，懒加载语言包
- 医学术语专业翻译
- 单位制/时区切换

### 5. 现代化开发体验
- Vite 极速冷启动 (< 3s) 和 HMR (< 100ms)
- TypeScript 严格模式
- ESLint 9 + Prettier + Stylelint 统一代码风格
- Vitest 单测 + Playwright E2E 测试

## 🛠️ 快速开始

### 环境要求
- Node.js >= 18.0.0
- pnpm >= 9.0.0

### 安装依赖
```bash
pnpm install
```

### 启动开发服务器
```bash
pnpm dev
```

### 构建生产版本
```bash
pnpm build:prod
```

### 预览构建结果
```bash
pnpm preview
```

### 代码检查
```bash
# 检查并修复
pnpm lint

# 仅检查
pnpm lint:check

# 格式化代码
pnpm format

# 样式检查
pnpm stylelint
```

### 运行测试
```bash
# 单元测试
pnpm test

# 单元测试（监听模式）
pnpm test:watch

# 覆盖率报告
pnpm test:coverage

# E2E 测试
pnpm test:e2e

# E2E 测试（UI 模式）
pnpm test:e2e:ui
```

### 类型检查
```bash
pnpm typecheck
```

## 🌐 环境变量

| 变量名 | 说明 | 默认值 |
|--------|------|--------|
| VITE_APP_TITLE | 应用标题 | 智碳能源管理系统 |
| VITE_PUBLIC_PATH | 公共路径 | / |
| VITE_API_BASE_URL | API 基础地址 | http://localhost:8080 |
| VITE_WS_BASE_URL | WebSocket 地址 | ws://localhost:8080 |
| VITE_PORT | 开发服务器端口 | 3000 |
| VITE_USE_MOCK | 启用 Mock | false |
| VITE_BUILD_COMPRESS | 构建压缩 | gzip |
| VITE_DROP_CONSOLE | 移除 console | true |

## 🎨 主题定制

### 设计令牌
在 `src/assets/styles/tokens/` 目录下定义：
- `colors.ts` - 色板（主色、语义色、中性色）
- `spacing.ts` - 间距系统
- `typography.ts` - 字体、行高、字重
- `border.ts` - 圆角、边框
- `shadow.ts` - 阴影层级
- `breakpoints.ts` - 响应式断点
- `motion.ts` - 动画时长、缓动
- `zIndex.ts` - 层级

### 主题预设
在 `src/assets/styles/themes/` 目录下：
- `light.ts` - 浅色主题
- `dark.ts` - 深色主题
- `hospital.ts` - 医院主题

### 运行时切换
```typescript
import { useAppStore } from '@/stores/modules/app'

const appStore = useAppStore()
appStore.setTheme('dark') // 'light' | 'dark' | 'hospital'
```

## 🧩 组件使用示例

### ProTable 标准列表页
```vue
<template>
  <ProTable
    :columns="columns"
    :request="fetchUserList"
    :search-form="searchFormConfig"
    :toolbar="['add', 'delete', 'export', 'columnSetting', 'refresh']"
    :row-selection="true"
    @add="handleAdd"
    @edit="handleEdit"
    @delete="handleDelete"
  />
</template>

<script setup>
import { ref } from 'vue'
import ProTable from '@/components/pro/ProTable.vue'

const columns = [
  { title: '用户名', dataIndex: 'userName', width: 120 },
  { title: '姓名', dataIndex: 'nickName', width: 120 },
  { title: '邮箱', dataIndex: 'email' },
  { title: '状态', dataIndex: 'status', width: 100, slots: { customRender: 'status' } },
]

const searchFormConfig = [
  { field: 'userName', label: '用户名', type: 'input' },
  { field: 'status', label: '状态', type: 'select', options: [] },
]

const fetchUserList = async (params) => {
  const res = await api.system.user.list(params)
  return { data: res.data.rows, total: res.data.total }
}
</script>
```

### ProForm 标准表单页
```vue
<template>
  <ProForm
    :schema="formSchema"
    :initial-values="initialValues"
    @finish="onSubmit"
  />
</template>

<script setup>
import { ref } from 'vue'
import ProForm from '@/components/pro/ProForm.vue'

const formSchema = [
  { field: 'userName', label: '用户名', type: 'input', required: true },
  { field: 'nickName', label: '姓名', type: 'input', required: true },
  { field: 'email', label: '邮箱', type: 'input', rules: [{ type: 'email' }] },
  { field: 'phone', label: '手机号', type: 'input', rules: [{ pattern: /^1[3-9]\d{9}$/ }] },
  { field: 'sex', label: '性别', type: 'radio', options: [{ label: '男', value: '0' }, { label: '女', value: '1' }] },
]

const initialValues = { sex: '0' }

const onSubmit = async (values) => {
  await api.system.user.add(values)
}
</script>
```

## 🔐 权限控制

### 路由权限
```typescript
// 路由元信息配置权限
{
  path: '/system/user',
  meta: {
    title: '用户管理',
    permissions: ['system:user:list'],
    roles: ['admin']
  }
}
```

### 按钮权限
```vue
<template>
  <a-button v-permission="['system:user:add']">新增</a-button>
  <a-button v-permission="['system:user:edit', 'system:user:delete']">编辑/删除</a-button>
</template>
```

### 权限指令值
```typescript
// 字符串 - 需要任一权限
v-permission="'system:user:add'"

// 数组 - 需要所有权限
v-permission="['system:user:add', 'system:user:edit']"

// 对象 - 复杂权限判断
v-permission="{ any: ['system:user:add', 'system:user:edit'] }"
v-permission="{ all: ['system:user:add', 'system:user:edit'] }"
```

## 🌍 国际化使用

### 在组件中使用
```vue
<template>
  <div>
    <h1>{{ $t('common.welcome') }}</h1>
    <p>{{ $t('system.user.title') }}</p>
    <span>{{ $t('hospital.device.mri') }}</span>
  </div>
</template>

<script setup>
import { useI18n } from 'vue-i18n'

const { t } = useI18n()
// 使用 t('key')
const title = t('system.user.title')
</script>
```

### 添加新语言包
1. 在 `src/locales/` 创建新语言目录（如 `ja-JP`）
2. 创建模块语言文件（`common.ts`, `system.ts` 等）
3. 在 `index.ts` 中合并导出
4. 在 `useI18n` 中配置 `availableLocales`

## 📱 响应式断点

| 断点 | 尺寸 | 适用场景 |
|------|------|----------|
| xs | < 480px | 手机竖屏 |
| sm | 480px - 767px | 手机横屏/小平板 |
| md | 768px - 1023px | 平板 |
| lg | 1024px - 1279px | 笔记本 |
| xl | 1280px - 1535px | 显示器 |
| 2xl | 1536px - 1919px | 大屏显示器 |
| 3xl | >= 1920px | 超大屏/大屏 |

```vue
<template>
  <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
    <div v-for="item in items" :key="item.id" class="p-4 bg-white rounded-lg shadow">
      {{ item.name }}
    </div>
  </div>
</template>
```

## 📊 图表使用

### ECharts
```vue
<template>
  <ProChart
    :chart-type="'echarts'"
    :options="chartOptions"
    :height="'300px'"
  />
</template>

<script setup>
import { ref } from 'vue'
import ProChart from '@/components/pro/ProChart.vue'

const chartOptions = ref({
  tooltip: { trigger: 'axis' },
  xAxis: { type: 'category', data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'] },
  yAxis: { type: 'value' },
  series: [{ data: [120, 132, 101, 134, 90, 230, 210], type: 'line' }],
})
</script>
```

### @antv/g2
```vue
<template>
  <ProChart
    :chart-type="'g2'"
    :options="g2Options"
    :height="'300px'"
  />
</template>

<script setup>
import { ref } from 'vue'
import ProChart from '@/components/pro/ProChart.vue'

const g2Options = ref({
  type: 'interval',
  data: [
    { category: 'A', value: 100 },
    { category: 'B', value: 200 },
  ],
  encoding: {
    x: 'category',
    y: 'value',
    color: 'category',
  },
})
</script>
```

## 🔧 常用工具函数

```typescript
import { 
  formatDate, formatNumber, formatCurrency, formatFileSize,
  debounce, throttle, deepClone, deepMerge,
  downloadBlob, exportToExcel, exportToCSV,
  randomString, generateId,
  get, set, unset
} from '@/utils'

// 日期格式化
formatDate(new Date(), 'YYYY-MM-DD HH:mm:ss')

// 数字格式化
formatNumber(1234567.89, { precision: 2, thousandSeparator: true })

// 防抖
const debouncedSearch = debounce(searchApi, 300)

// 节流
const throttledScroll = throttle(handleScroll, 100)

// 深拷贝
const cloned = deepClone(originalObject)

// 下载
downloadBlob(blob, 'file.xlsx')

// 导出 Excel
exportToExcel(data, '导出文件', { headers: ['姓名', '年龄'] })
```

## 📝 代码规范

### Git 提交规范
```
<type>(<scope>): <subject>

<body>

<footer>
```

Type 类型：
- `feat`: 新功能
- `fix`: 修复 Bug
- `docs`: 文档更新
- `style`: 代码格式调整
- `refactor`: 重构
- `perf`: 性能优化
- `test`: 测试相关
- `chore`: 构建/工具/依赖更新

### 代码风格
- 使用 TypeScript 严格模式
- 组件使用 `<script setup>` 语法
- 优先使用组合式 API
- 组件名使用 PascalCase
- 文件名使用 kebab-case
- 变量/函数使用 camelCase
- 常量使用 UPPER_SNAKE_CASE

## 🧪 测试指南

### 单元测试
```typescript
// components/common/BaseButton.test.ts
import { mount } from '@vue/test-utils'
import { describe, it, expect } from 'vitest'
import BaseButton from '@/components/common/BaseButton.vue'

describe('BaseButton', () => {
  it('renders correctly', () => {
    const wrapper = mount(BaseButton, {
      slots: { default: 'Click me' },
    })
    expect(wrapper.text()).toBe('Click me')
  })
  
  it('emits click event', async () => {
    const wrapper = mount(BaseButton)
    await wrapper.trigger('click')
    expect(wrapper.emitted('click')).toBeTruthy()
  })
})
```

### E2E 测试
```typescript
// tests/e2e/login.spec.ts
import { test, expect } from '@playwright/test'

test('user can login', async ({ page }) => {
  await page.goto('/login')
  await page.fill('input[name="username"]', 'admin')
  await page.fill('input[name="password"]', '123456')
  await page.click('button[type="submit"]')
  await expect(page).toHaveURL('/dashboard/workbench')
})
```

## 📦 部署

### Docker 部署
```dockerfile
# Dockerfile
FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json pnpm-lock.yaml ./
RUN npm install -g pnpm && pnpm install --frozen-lockfile
COPY . .
RUN pnpm build:prod

FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

### Nginx 配置
```nginx
server {
    listen 80;
    server_name localhost;
    root /usr/share/nginx/html;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    location /api/ {
        proxy_pass http://backend:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## 🤝 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
2. 提交更改 (`git commit -m 'feat: Add some AmazingFeature'`)
3. 推送到分支 (`git push origin feature/AmazingFeature`)
4. 创建 Pull Request

## 📄 许可证

本项目基于 MIT 许可证开源 - 详见 [LICENSE](LICENSE) 文件

## 📞 联系我们

- 项目地址: https://github.com/zhurong-ems/zhurong-admin-ui-v3
- 问题反馈: https://github.com/zhurong-ems/zhurong-admin-ui-v3/issues
- 邮箱: support@zhurong-ems.com