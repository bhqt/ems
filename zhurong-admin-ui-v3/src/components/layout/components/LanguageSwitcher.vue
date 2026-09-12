<template>
  <a-dropdown :menu="{ items: languageMenuItems }" placement="bottomRight" trigger="click">
    <a-button type="text" :title="currentLanguageTitle">
      <template #icon><GlobalOutlined /></template>
    </a-button>
  </a-dropdown>
</template>

<script setup lang="ts">
import { computed, h } from 'vue'
import { useAppStore } from '@/stores/modules/app'
import { useI18n } from 'vue-i18n'
import {
  GlobalOutlined,
  CheckOutlined,
} from '@ant-design/icons-vue'

const appStore = useAppStore()
const { t } = useI18n()

const currentLocale = computed(() => appStore.locale)

const currentLanguageTitle = computed(() => {
  return currentLocale.value === 'zh-CN' ? '中文' : 'English'
})

const languageMenuItems = computed(() => [
  {
    label: () => h('div', { class: 'language-menu-item' }, [
      h('span', { class: 'language-flag' }, '🇨🇳'),
      h('span', null, '中文'),
      currentLocale.value === 'zh-CN' ? h(CheckOutlined, { class: 'language-check' }) : null,
    ]),
    key: 'zh-CN',
    onClick: () => appStore.setLocale('zh-CN'),
  },
  {
    label: () => h('div', { class: 'language-menu-item' }, [
      h('span', { class: 'language-flag' }, '🇺🇸'),
      h('span', null, 'English'),
      currentLocale.value === 'en-US' ? h(CheckOutlined, { class: 'language-check' }) : null,
    ]),
    key: 'en-US',
    onClick: () => appStore.setLocale('en-US'),
  },
])
</script>

<style scoped>
.language-menu-item {
  display: flex;
  align-items: center;
  gap: 8px;
  width: 100%;
}

.language-flag {
  font-size: 16px;
}

.language-check {
  margin-left: auto;
  color: var(--zhurong-color-primary);
}
</style>