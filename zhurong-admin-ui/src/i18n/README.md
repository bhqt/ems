# 祝融 EMS 国际化方案

## 概述

本项目使用 `vue-i18n@8.x` 实现国际化，支持以下四种语言：

- 🇨🇳 简体中文 (zh-CN)
- 🇺🇸 English (en)
- 🇮🇩 Bahasa Indonesia (id)
- 🇷🇺 Русский (ru)

## 目录结构

```
src/i18n/
├── index.js          # i18n 配置入口
├── helper.js         # 国际化辅助函数（动态菜单翻译）
├── lang/             # 语言包目录
│   ├── zh-CN.js      # 中文
│   ├── en.js         # 英文
│   ├── id.js         # 印度尼西亚语
│   └── ru.js         # 俄语
├── EXAMPLE.md        # 使用示例
└── README.md         # 本文档
```

## 快速开始

### 1. 安装依赖

```bash
npm install vue-i18n@8.28.2 --legacy-peer-deps
```

### 2. 在模板中使用

```vue
<template>
  <div>
    <!-- 简单翻译 -->
    <h1>{{ $t('login.title') }}</h1>

    <!-- 带参数的翻译 -->
    <span>{{ $t('common.total', { total: 100 }) }}</span>

    <!-- Element UI 组件 -->
    <el-button>{{ $t('common.search') }}</el-button>
  </div>
</template>
```

### 3. 在脚本中使用

```javascript
export default {
  methods: {
    handleSubmit() {
      this.$message.success(this.$t('common.success'))
    }
  }
}
```

### 4. 切换语言

点击导航栏的语言切换组件，或在代码中：

```javascript
// 切换为英文
this.$i18n.locale = 'en'
this.$store.dispatch('app/setLanguage', 'en')
```

## 动态菜单国际化

### 方案说明

本项目采用 **前端翻译方案**：后端返回菜单的 `meta.title` 作为国际化 key，前端根据当前语言自动翻译。

### 后端返回格式

```json
{
  "path": "/system/user",
  "meta": {
    "title": "menu.system.user",
    "icon": "user"
  }
}
```

### 语言包配置

```javascript
// src/i18n/lang/zh-CN.js
export default {
  menu: {
    system: {
      title: '系统管理',
      user: '用户管理',
      role: '角色管理'
      // ...
    }
  }
}

// src/i18n/lang/en.js
export default {
  menu: {
    system: {
      title: 'System Management',
      user: 'User Management',
      role: 'Role Management'
      // ...
    }
  }
}
```

### 自动翻译

菜单组件 [SidebarItem.vue](../layout/components/Sidebar/SidebarItem.vue) 已集成自动翻译功能，无需额外处理。

## 语言包结构

```javascript
{
  // 通用
  common: {
    search: '搜索',
    reset: '重置',
    // ...
  },

  // 登录
  login: {
    title: '祝融能源管理系统',
    username: '用户名',
    // ...
  },

  // 菜单（用于动态菜单）
  menu: {
    system: { /* ... */ },
    monitor: { /* ... */ },
    energy: { /* ... */ },
    // ...
  },

  // 导航栏
  navbar: { /* ... */ },

  // 标签页
  tagsView: { /* ... */ },

  // 设置
  settings: { /* ... */ }
}
```

## 添加新语言

1. 在 `src/i18n/lang/` 下创建新的语言文件（如 `ja.js`）
2. 在 `src/i18n/index.js` 中导入并添加到 `messages`
3. 在 `supportLanguages` 数组中添加语言选项

## 添加新翻译

1. 在 `src/i18n/lang/zh-CN.js` 中添加中文翻译
2. 同步更新其他语言文件

## 注意事项

1. **Element UI 组件**：已配置自动跟随语言切换
2. **日期/数字格式**：使用 Vue I18n 提供的 `$d` 和 `$n` 方法
3. **回退机制**：当翻译不存在时，自动回退到中文
4. **Cookie 存储**：语言选择会保存到 Cookie，刷新页面后保持

## 相关文件

- [src/i18n/index.js](./index.js) - i18n 配置
- [src/i18n/helper.js](./helper.js) - 菜单翻译辅助函数
- [src/components/LangSelect/index.vue](../components/LangSelect/index.vue) - 语言切换组件
- [src/layout/components/Sidebar/SidebarItem.vue](../layout/components/Sidebar/SidebarItem.vue) - 菜单组件（已集成翻译）
- [src/store/modules/app.js](../store/modules/app.js) - 语言状态管理
