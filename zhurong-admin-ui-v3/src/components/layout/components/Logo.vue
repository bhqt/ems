<template>
  <div class="logo-container" :class="{ collapsed }">
    <router-link to="/dashboard/workbench" class="logo-link">
      <div class="logo" :class="{ collapsed }">
        <component :is="logoIcon" class="logo-icon" />
        <span v-show="!collapsed" class="logo-title">{{ title }}</span>
      </div>
    </router-link>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useAppStore } from '@/stores/modules/app'
import { DashboardOutlined, MedicineBoxOutlined, ThunderboltOutlined } from '@ant-design/icons-vue'
import { useI18n } from 'vue-i18n'

const props = defineProps<{
  collapsed?: boolean
}>()

const appStore = useAppStore()
const { t } = useI18n()

const title = computed(() => t('common.appTitle') || 'Zhurong EMS')

const logoIcon = computed(() => {
  switch (appStore.themeMode) {
    case 'hospital':
      return MedicineBoxOutlined
    case 'dark':
      return ThunderboltOutlined
    default:
      return DashboardOutlined
  }
})
</script>

<style scoped>
.logo-container {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 16px;
  border-bottom: 1px solid var(--zhurong-color-border);
  transition: all 0.2s;
  overflow: hidden;
}

.logo-link {
  display: flex;
  align-items: center;
  text-decoration: none;
  color: inherit;
}

.logo {
  display: flex;
  align-items: center;
  gap: 12px;
  color: var(--zhurong-color-text-heading);
  transition: all 0.2s;
}

.logo-icon {
  font-size: 24px;
  color: var(--zhurong-color-primary);
  flex-shrink: 0;
}

.logo-title {
  font-size: 18px;
  font-weight: 600;
  white-space: nowrap;
  overflow: hidden;
  transition: all 0.2s;
}

.logo.collapsed .logo-title {
  display: none;
}

.logo.collapsed {
  justify-content: center;
}
</style>