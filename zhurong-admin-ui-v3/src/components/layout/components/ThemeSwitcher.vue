<template>
  <a-dropdown :menu="{ items: themeMenuItems }" placement="bottomRight" trigger="click">
    <a-button type="text" :title="currentThemeTitle">
      <template #icon>
        <HighlightOutlined v-if="currentTheme === 'light'" />
        <StarOutlined v-else-if="currentTheme === 'dark'" />
        <MedicineBoxOutlined v-else />
      </template>
    </a-button>
  </a-dropdown>
</template>

<script setup lang="ts">
import { computed, h } from 'vue'
import { useAppStore } from '@/stores/modules/app'
import { useI18n } from 'vue-i18n'
import {
  HighlightOutlined,
  StarOutlined,
  MedicineBoxOutlined,
  MonitorOutlined,
} from '@ant-design/icons-vue'

const appStore = useAppStore()
const { t } = useI18n()

const currentTheme = computed(() => appStore.themeMode)

const currentThemeTitle = computed(() => {
  switch (currentTheme.value) {
    case 'dark':
      return t('common.themeDark')
    case 'hospital':
      return t('common.themeHospital')
    default:
      return t('common.themeLight')
  }
})

const themeMenuItems = computed(() => [
  {
    label: () => h('div', { class: 'theme-menu-item' }, [
      h(HighlightOutlined, { class: 'theme-icon' }),
      h('span', null, t('common.themeLight')),
      currentTheme.value === 'light' ? h('span', { class: 'theme-check' }, '✓') : null,
    ]),
    key: 'light',
    onClick: () => appStore.setTheme('light'),
  },
  {
    label: () => h('div', { class: 'theme-menu-item' }, [
      h(StarOutlined, { class: 'theme-icon' }),
      h('span', null, t('common.themeDark')),
      currentTheme.value === 'dark' ? h('span', { class: 'theme-check' }, '✓') : null,
    ]),
    key: 'dark',
    onClick: () => appStore.setTheme('dark'),
  },
  {
    label: () => h('div', { class: 'theme-menu-item' }, [
      h(MedicineBoxOutlined, { class: 'theme-icon' }),
      h('span', null, t('common.themeHospital')),
      currentTheme.value === 'hospital' ? h('span', { class: 'theme-check' }, '✓') : null,
    ]),
    key: 'hospital',
    onClick: () => appStore.setTheme('hospital'),
  },
  {
    type: 'divider',
  },
  {
    label: () => h('div', { class: 'theme-menu-item' }, [
      h(MonitorOutlined, { class: 'theme-icon' }),
      h('span', null, t('common.themeAuto')),
    ]),
    key: 'auto',
    onClick: () => {
      const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches
      appStore.setTheme(prefersDark ? 'dark' : 'light')
    },
  },
])
</script>

<style scoped>
.theme-menu-item {
  display: flex;
  align-items: center;
  gap: 8px;
  width: 100%;
}

.theme-icon {
  font-size: 14px;
}

.theme-check {
  margin-left: auto;
  color: var(--zhurong-color-primary);
  font-weight: bold;
}
</style>