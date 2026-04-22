# 国际化使用示例

## 1. 模板中使用

```vue
<template>
  <div>
    <!-- 简单使用 -->
    <h1>{{ $t('login.title') }}</h1>

    <!-- 带参数 -->
    <p>{{ $t('common.total', { total: 100 }) }}</p>

    <!-- Element UI 组件 -->
    <el-button>{{ $t('common.search') }}</el-button>
    <el-button>{{ $t('common.reset') }}</el-button>

    <!-- 表单标签 -->
    <el-form>
      <el-form-item :label="$t('login.username')">
        <el-input />
      </el-form-item>
    </el-form>
  </div>
</template>
```

## 2. 脚本中使用

```vue
<script>
export default {
  methods: {
    handleSubmit() {
      // 使用 this.$t
      this.$message.success(this.$t('common.success'))

      // 确认对话框
      this.$confirm(
        this.$t('login.logoutConfirm'),
        this.$t('common.warning'),
        {
          confirmButtonText: this.$t('common.confirm'),
          cancelButtonText: this.$t('common.cancel'),
          type: 'warning'
        }
      )
    }
  }
}
</script>
```

## 3. 动态菜单国际化

动态菜单会自动翻译，只需确保后端返回的菜单 `meta.title` 与语言包中的 key 对应：

```javascript
// 后端返回的菜单数据示例
{
  path: '/system',
  meta: { title: 'menu.system.title', icon: 'system' },
  children: [
    {
      path: 'user',
      meta: { title: 'menu.system.user', icon: 'user' }
    }
  ]
}
```

## 4. 切换语言

```javascript
// 在组件中切换语言
this.$i18n.locale = 'en'
this.$store.dispatch('app/setLanguage', 'en')

// 或使用辅助函数
import { setLanguage } from '@/i18n'
setLanguage('en')
```

## 5. 添加新的翻译

编辑对应的语言文件：

```javascript
// src/i18n/lang/zh-CN.js
export default {
  // ... 其他翻译
  myModule: {
    myKey: '我的翻译'
  }
}

// src/i18n/lang/en.js
export default {
  // ... 其他翻译
  myModule: {
    myKey: 'My Translation'
  }
}
```

## 6. 日期/数字格式化

```vue
<template>
  <div>
    <!-- 日期格式化 -->
    <span>{{ $d(new Date(), 'short') }}</span>

    <!-- 数字格式化 -->
    <span>{{ $n(1000, 'currency') }}</span>
  </div>
</template>
```

## 7. 复数处理

```javascript
// 语言包中定义
messages: {
  en: {
    items: 'no items | one item | {count} items'
  }
}

// 使用
$t('items', 0)  // 'no items'
$t('items', 1)  // 'one item'
$t('items', 10) // '10 items'
```
