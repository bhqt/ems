# 智慧能源决策系统（医院版）Web 端现代化重构规格说明

> 基于 deep-ems0（智慧 EMS 能源管理系统）演进的新一代前端架构规格。
> 本文档为 Spec 驱动开发的总纲，后续所有开发均以本文档 + checklist.md + tasks.md 为准。

***

## 1. 项目背景

现有 `zhurong-admin-ui` 基于 **Vue 2.6 + Element UI 2.15 + Webpack + Vue CLI 4**，技术栈已老旧：
- Vue 2 官方支持已结束 (2023/12)
- Element UI 设计语言陈旧，无暗色模式，组件扩展性弱
- Webpack 构建缓慢，HMR 延迟高，开发体验差
- Options API + Mixins 导致逻辑复用困难，TypeScript 支持弱
- 无设计令牌体系，主题切换、医院专属皮肤难以实现

现需**新建** `zhurong-admin-ui-v3`，采用现代化技术栈全量重构，旧项目保留维护模式，新旧并行灰度发布。

## 2. 项目目标

1. 交付基于 **Vue 3 + Ant Design Vue 4 + Vite + TypeScript** 的现代化前端工程
2. 建立 **设计令牌 + UnoCSS + CSS Variables** 主题系统，支持浅色/深色/医院主题一键切换
3. 封装 **ProComponents (ProTable/ProForm/ProLayout/ProChart...)** 将页面开发效率提升 50%+
4. 全量迁移 120+ 页面，保持功能 100% 兼容，新增医院模块零遗漏
5. 支持中英国际化、多时区、公制/英制单位切换，满足海外医院部署需求
6. 大屏适配 1920×1080 基准，响应式缩放，性能达标 (Lighthouse ≥ 90)

## 3. 技术栈选型

| 层级 | 选型 | 版本 | 备注 |
|------|------|------|------|
| 核心框架 | Vue | 3.4+ | `<script setup>` + Composition API |
| 构建工具 | Vite | 5+ | `vue-ts` 模板，esbuild 极速构建 |
| UI 组件库 | **Ant Design Vue** | **4.x** | 企业级设计语言、强主题系统、TS 原生 |
| 状态管理 | Pinia | 2.x | 模块化、TS 友好、DevTools 支持 |
| 路由 | Vue Router | 4.x | 动态路由、权限守卫、KeepAlive |
| CSS 方案 | **UnoCSS + CSS Variables** | 最新 | 原子化 + 设计令牌，配合 AntD 4 ConfigProvider |
| 图表 | ECharts 5 + @antv/g2 | 最新 | 现有 ECharts 复用，新建大屏用 G2 |
| 国际化 | vue-i18n | 10+ | Composition API、懒加载语言包 |
| 图标 | @ant-design/icons-vue + Iconify | 最新 | AntD 图标 + 按需加载 |
| 日期库 | dayjs | 1.11+ | 替换 moment，包体积减少 ~60KB |
| 代码规范 | ESLint 9 (Flat) + Prettier + Stylelint + @antfu/eslint-config | 最新 | 统一风格 |
| 测试 | Vitest + Vue Test Utils + Playwright | 最新 | 单测 + E2E + 视觉回归 |
| 包管理 | pnpm | 9+ | Workspace 管理共享包 |

## 4. 目录结构

```
zhurong-admin-ui-v3/
├── public/
├── src/
│   ├── api/                      # API 层 (OpenAPI 生成)
│   │   ├── core/                 # request 实例、拦截器、错误码
│   │   ├── modules/              # 按业务域分模块
│   │   └── types/                # 后端 DTO/VO 类型
│   ├── assets/
│   │   ├── styles/
│   │   │   ├── tokens/           # 设计令牌 (TS)
│   │   │   ├── themes/           # 主题预设 (light/dark/hospital)
│   │   │   ├── uno.css           # UnoCSS 入口
│   │   │   ├── global.scss       # 全局样式、重置
│   │   │   └── antd-overrides.scss # AntD 组件样式微调
│   │   └── icons/                # SVG 图标
│   ├── components/
│   │   ├── common/               # 基础封装 (二次封装 AntD 组件)
│   │   ├── pro/                  # 业务级组件 (ProTable/ProForm/ProLayout...)
│   │   ├── layout/               # 框架布局 (BasicLayout/SiderMenu/TopNav/TabNav)
│   │   ├── charts/               # 图表封装 (ECharts/G2)
│   │   ├── hospital/             # 医院专属组件
│   │   └── index.ts              # 统一导出、自动注册
│   ├── composables/              # 组合式函数 (useTable/useForm/usePermission...)
│   ├── directives/               # 自定义指令 (permission/loading/copy/watermark)
│   ├── layouts/                  # 路由布局定义 (default/blank/iframe)
│   ├── locales/                  # 国际化 (zh-CN/en-US，按模块拆包)
│   ├── router/                   # 路由配置 (静态/动态、守卫、类型)
│   ├── stores/                   # Pinia stores (app/user/settings/permission/hospital)
│   ├── utils/                    # 工具函数 (request/date/format/tree/validation...)
│   ├── views/                    # 页面视图 (按路由模块组织)
│   │   ├── login/
│   │   ├── system/
│   │   ├── energy/
│   │   ├── maintenance/
│   │   ├── hospital/
│   │   ├── dashboard/
│   │   ├── charging/
│   │   ├── newenergy/
│   │   └── ...
│   ├── App.vue
│   ├── main.ts
│   ├── vite-env.d.ts
│   └── shims-vue.d.ts
├── uno.config.ts
├── vite.config.ts
├── tsconfig.json
├── tsconfig.node.json
├── .eslintrc.cjs
├── .prettierrc
├── .stylelintrc.cjs
├── vitest.config.ts
├── playwright.config.ts
├── package.json
└── README.md
```

## 5. 核心功能需求

### 5.1 基础设施 (P0)

- **FR-1 脚手架初始化**：Vite + Vue 3 + TS + AntD 4 + UnoCSS + Pinia + Router + i18n 零配置跑通
- **FR-2 设计令牌系统**：色板、间距、字体、圆角、阴影、断点、动画、层级 8 大类令牌定义
- **FR-3 主题系统**：浅色/深色/医院主题 3 套，运行时切换，CSS Variables 注入，持久化 localStorage
- **FR-4 布局框架**：BasicLayout (Sider + Header + Content + Footer)、BlankLayout、IframeLayout
- **FR-5 导航系统**：SiderMenu (递归渲染、图标、徽标、内链)、TopNav (用户、通知、全屏、主题、语言)、TabNav (多标签、缓存、右键菜单、拖拽排序)
- **FR-6 权限体系**：路由守卫 (登录态、Token 刷新、菜单权限、按钮权限)、动态路由注册、v-permission 指令
- **FR-7 国际化**：中英双语、懒加载语言包、医学术语表、单位制/时区切换
- **FR-8 请求层**：axios 封装、统一错误码处理、Loading 自动化、Token 自动刷新、取消重复请求

### 5.2 通用组件库 (P0)

| 组件 | 说明 |
|------|------|
| BaseButton/BaseInput/BaseSelect/BaseTable/BaseForm/BaseModal/BaseDrawer/BaseCard/BaseTabs/BaseTree/BaseUpload/BaseBreadcrumb/BasePagination/BaseTag/BaseDescriptions/BaseSteps/BaseTransfer/BaseCascader/LoadingWrapper | 二次封装 AntD 组件，统一 Props/API、内置 loading/空状态、TypeScript 严格类型 |
| ProLayout | 页面级布局：PageHeader(面包屑+标题+操作) + Content + Footer |
| ProTable | 标准列表页：搜索表单自动生成 + 工具栏(新增/删除/导出/列设置/刷新) + 表格(分页/虚拟滚动/行选择/排序/列固定) + 新增/编辑抽屉联动 |
| ProForm | 标准表单页：JSON Schema 驱动、栅格布局、依赖字段显隐、异步校验、草稿自动保存 |
| ProCard | 卡片页容器：标题、操作区、加载/空/错误状态、边框/阴影主题化 |
| ProChart | 图表卡片：ECharts/G2 统一配置、响应式 resize、主题色跟随 CSS Variables、加载/错误骨架屏 |
| ProStatistic | 统计卡片：数值、趋势图、同比环比、单位 |
| ProSearchForm | 通用搜索表单：折叠/展开、重置、查询、URL 参数同步 |
| ProDetail | 详情页：Descriptions + 标签页 + 操作按钮 |

### 5.3 业务模块迁移 (P0) - 全量 120+ 页面

| 批次 | 模块 | 页面数 | 关键组件 |
|------|------|--------|----------|
| Batch 1 | 系统管理 | ~12 | ProTable/ProForm/ProDetail |
| Batch 2 | 能耗核心 | ~15 | ProTable/ProChart/ProStatistic |
| Batch 3 | 设备/运维 | ~20 | ProTable/TreeTable/ProForm/FlowChart |
| Batch 4 | 充电桩/新能源 | ~15 | ProTable/Map/ProChart/WebSocket |
| Batch 5 | 数据看板/大屏 | ~10 | ProChart/ProStatistic/ScreenContainer/G2 |
| Batch 6 | 医院新增 | ~15 | HospitalDashboard/DeviceWall/EnergyFlow/ProTable |
| Batch 7 | 综合/其他 | ~15 | ProTable/ProForm/ProDetail |

### 5.4 医院专属主题与大屏 (P0)

- **FR-9 医院设计令牌扩展**：医院绿 `#00B96B` 主色调、高对比度中性色、医疗字体栈
- **FR-10 医院大屏组件库**：HospitalDashboard/DeviceWall/EnergyFlow/WardEnergyCard，1920×1080 基准、自适应缩放、全屏轮播
- **FR-11 国际化完善**：医学术语 (Diagnostic Equipment/Campus/Department/Unit Workload Energy Consumption)、公制/英制单位切换、时区适配

### 5.5 性能与体验优化 (P1)

- **FR-12 路由懒加载 + 组件按需引入**：defineAsyncComponent + unplugin-vue-components
- **FR-13 虚拟列表/虚拟表格**：@tanstack/vue-virtual 大数据量场景
- **FR-14 首屏性能**：Preload 关键资源、登录页预渲染、代码分包
- **FR-15 无障碍合规**：WCAG 2.1 AA、ARIA、键盘导航、色盲模式、屏幕阅读器

## 6. 非功能需求

| 类别 | 要求 |
|------|------|
| 性能 | 冷启动 < 3s、HMR < 100ms、构建产物 < 500KB gzip、首屏 FCP < 1.5s、TTI < 3s |
| 可维护性 | TypeScript 严格模式、组件单测覆盖 ≥ 70%、ESLint 0 warning、Storybook 文档全覆盖 |
| 兼容性 | Chrome 90+/Edge 90+/Firefox 88+/Safari 14+、响应式断点 ≥ 1200/992/768/576px |
| 安全 | XSS 防护 (v-html 白名单)、CSP 策略、敏感信息不入日志、HTTPS 强制 |
| 可扩展性 | Micro Frontend 预留 (Module Federation)、插件化 ProComponents、主题扩展机制 |

## 7. 迁移策略

### 7.1 灰度发布
- Nginx 路由：`/admin-v3/` → 新前端，`/admin/` → 旧前端
- 后端菜单表加字段 `frontend_version: 'v2' | 'v3'`
- 登录页统一入口，按用户角色/权重分流
- 共享后端 API，无需改动

### 7.2 分批迁移
见 5.3 批次划分，每批次完成后：
1. 功能回归测试 100% 通过
2. 视觉回归测试 (Playwright + pixelmatch)
3. 性能基线对比 (Lighthouse CI)
4. 灰度 10% 用户 → 50% → 100%

### 7.3 回滚机制
- Nginx 配置热切换，秒级回滚
- 旧项目只读维护，不再新增功能

## 8. 里程碑规划 (12 周)

| 里程碑 | 周次 | 关键产出 | 验收指标 |
|--------|------|----------|----------|
| **M0 脚手架就绪** | 1-2 | 完整工程、设计令牌、主题系统、基础布局、权限路由、CI/CD | `pnpm dev` 启动 < 3s、主题/语言切换生效、菜单权限正确、构建产物 < 500KB gzip |
| **M1 通用组件库 v1.0** | 3 | BaseComponents (18个)、ProComponents (8个)、图表封装、指令、工具函数、Storybook | 组件单测覆盖 ≥ 70%、TS 严格模式通过、无 any |
| **M2 系统管理上线** | 4 | 用户/角色/菜单/部门/字典/参数/日志/监控 12 页面 | 功能回归 100%、列表/表单/详情交互一致 |
| **M3 能耗核心上线** | 5-6 | 分析/定额/报警/报表/碳资产 15 页面 | 图表渲染正确、导出 Excel、大数据量虚拟滚动 |
| **M4 设备/运维上线** | 7 | 台账/巡检/工单/排班/资产 20 页面 | 树形表格、拖拽排序、流程审批联动 |
| **M5 充电/新能源上线** | 8 | 站/桩/订单/价格/光伏/储能/微网 15 页面 | 地图组件、实时数据 WebSocket |
| **M6 看板/大屏上线** | 9 | 仪表盘/实时看板/数字孪生/医院大屏 10 页面 | 1920×1080 无滚动条、自适应缩放、数据轮询 |
| **M7 医院新增模块** | 10-11 | 监测/分析/评估/建议/决策 15 页面 | 新业务零遗漏、国际化完备、单位制切换 |
| **M8 全量收尾** | 12 | 剩余模块、性能优化、a11y、文档、部署 | Lighthouse ≥ 90、无 P0/P1 Bug、旧项目下线 |

## 9. 风险与对策

| 风险 | 等级 | 对策 |
|------|------|------|
| AntD 4 组件 API 与 Element UI 差异大 | 🔴 高 | 编写 Codemod 脚本批量转换 80% 通用模式；复杂组件人工重写 |
| 现有业务逻辑深度耦合 Element UI 实例方法 | 🔴 高 | 封装统一适配层 `useMessage()`/`useModal()`，迁移时仅改 import |
| 日期库 moment → dayjs 格式化 token 不兼容 | 🟡 中 | 统一工具函数 `formatDate()`/`parseDate()` 吸收差异 |
| 120+ 页面全量迁移回归测试压力大 | 🔴 高 | 分批灰度 + Playwright E2E 覆盖核心流程 + 视觉回归测试 |
| 医院大屏高分辨率下性能/字体渲染 | 🟡 中 | `font-size: clamp()`、图层合成 `will-change`、离屏 Canvas |
| 国际化 key 遗漏/冲突 | 🟡 中 | 构建时 `vue-i18n-extract` 强制检查、CI 拦截 |

## 10. 资源配置

| 角色 | 人数 | 职责 |
|------|------|------|
| Tech Lead | 1 | 架构决策、Code Review、疑难攻关、Codemod 编写 |
| Senior Frontend | 2 | ProComponents 开发、核心业务模块、性能优化 |
| Frontend Engineer | 2 | 批量页面迁移、单测编写、Storybook 维护、UI 还原 |
| UI/UX (兼职) | 0.5 | 设计令牌定义、医院主题视觉规范、组件设计稿 |
| QA | 1 | 测试用例设计、回归测试、自动化脚本维护 |

## 11. 术语表

| 术语 | 说明 |
|------|------|
| ProComponents | 业务级组件库，封装“搜索+表格+工具栏+分页+抽屉”标准模式 |
| 设计令牌 | Design Tokens，跨平台的设计决策原子值 (色板/间距/字体/圆角/阴影...) |
| CSS Variables | CSS 自定义属性，运行时动态切换主题的核心机制 |
| UnoCSS | 即时按需原子化 CSS 引擎，零运行时、配置即代码 |
| 灰度发布 | Canary Release，逐步将流量切换到新版本 |
| 视觉回归测试 | Visual Regression Testing，像素级对比截图发现 UI 变更 |