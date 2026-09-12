<template>
  <div class="top-nav">
    <!-- 左侧：面包屑/页面标题 -->
    <div class="top-nav-left">
      <Breadcrumb v-if="showBreadcrumb" :routes="breadcrumbRoutes" />
      <PageHeader v-else :title="pageTitle" :actions="pageActions" />
    </div>

    <!-- 右侧：操作区 -->
    <div class="top-nav-right">
      <!-- 全屏 -->
      <a-tooltip title="全屏">
        <a-button type="text" @click="toggleFullscreen">
          <template #icon><FullscreenOutlined /></template>
        </a-button>
      </a-tooltip>

      <!-- 主题切换 -->
      <ThemeSwitcher />

      <!-- 国际化 -->
      <LanguageSwitcher />

      <!-- 设置 -->
      <a-dropdown :menu="{ items: settingMenuItems }" placement="bottomRight">
        <a-button type="text">
          <template #icon><SettingOutlined /></template>
        </a-button>
      </a-dropdown>

      <!-- 通知 -->
      <a-dropdown :menu="{ items: notificationMenuItems }" placement="bottomRight">
        <a-button type="text">
          <template #icon><BellOutlined /></template>
          <a-badge :count="notificationCount" />
        </a-button>
      </a-dropdown>

      <!-- 用户菜单 -->
      <a-dropdown :menu="{ items: userMenuItems }" placement="bottomRight" trigger="click">
        <div class="user-avatar-wrapper">
          <a-avatar
            :size="32"
            :src="userAvatar"
            :alt="userName"
            class="user-avatar"
          />
          <span v-show="!collapsed" class="user-name">{{ userName }}</span>
          <DownOutlined class="user-arrow" />
        </div>
      </a-dropdown>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/modules/user'
import { useAppStore } from '@/stores/modules/app'
import { useI18n } from 'vue-i18n'
import {
  FullscreenOutlined,
  SettingOutlined,
  BellOutlined,
  DownOutlined,
  UserOutlined,
  LockOutlined,
  LogoutOutlined,
  DashboardOutlined,
  GlobalOutlined,
} from '@ant-design/icons-vue'
import Breadcrumb from './Breadcrumb.vue'
import PageHeader from './PageHeader.vue'
import ThemeSwitcher from './ThemeSwitcher.vue'
import LanguageSwitcher from './LanguageSwitcher.vue'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const appStore = useAppStore()
const { t } = useI18n()

const props = defineProps<{
  collapsed?: boolean
}>()

const collapsed = computed(() => props.collapsed || appStore.sidebar.collapsed)
const isFullscreen = ref(false)

const userName = computed(() => userStore.userName)
const userAvatar = computed(() => userStore.avatar)

const showBreadcrumb = computed(() => !appStore.sidebar.collapsed)
const pageTitle = computed(() => t(route.meta?.title as string || ''))
const pageActions = computed(() => [])

const breadcrumbRoutes = computed(() => {
  return route.matched
    .filter(m => m.meta?.title && !m.meta?.hideBreadcrumb)
    .map(m => ({
      path: m.path,
      title: t(m.meta?.title as string || ''),
    }))
})

const notificationCount = ref(3)

const settingMenuItems = [
  { label: t('common.theme'), key: 'theme', icon: DashboardOutlined },
  { label: t('common.language'), key: 'language', icon: GlobalOutlined },
  { type: 'divider' },
  { label: t('common.fullscreen'), key: 'fullscreen', icon: FullscreenOutlined },
  { label: t('common.lockScreen'), key: 'lock', icon: LockOutlined },
]

const notificationMenuItems = [
  { label: t('common.noNotifications'), key: 'none', disabled: true },
]

const userMenuItems = [
  { label: t('common.profile'), key: 'profile', icon: UserOutlined },
  { type: 'divider' },
  { label: t('common.lockScreen'), key: 'lock', icon: LockOutlined },
  { label: t('common.logout'), key: 'logout', icon: LogoutOutlined, danger: true },
]

function toggleFullscreen() {
  isFullscreen.value = !isFullscreen.value
  if (isFullscreen.value) {
    document.documentElement.requestFullscreen?.()
  } else {
    document.exitFullscreen?.()
  }
}
</script>

<style scoped>
.top-nav {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  padding: 0 8px;
}

.top-nav-left {
  flex: 1;
  min-width: 0;
  overflow: hidden;
}

.top-nav-right {
  display: flex;
  align-items: center;
  gap: 8px;
}

.user-avatar-wrapper {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 4px 12px 4px 4px;
  border-radius: 20px;
  cursor: pointer;
  transition: background 0.2s;
}

.user-avatar-wrapper:hover {
  background: var(--zhurong-color-bg-hover);
}

.user-name {
  font-size: var(--zhurong-font-size);
  color: var(--zhurong-color-text);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 120px;
}

.user-arrow {
  font-size: 12px;
  color: var(--zhurong-color-text-tertiary);
  margin-left: 4px;
}

@media (max-width: 768px) {
  .user-name {
    display: none;
  }

  .top-nav-left {
    display: none;
  }
}
</style>