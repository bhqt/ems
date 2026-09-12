<template>
  <div class="sider-menu-container">
    <!-- Logo -->
    <Logo :collapsed="collapsed" />

    <!-- 菜单 -->
    <a-menu
      v-model:selectedKeys="selectedKeys"
      v-model:openKeys="openKeys"
      :mode="collapsed ? 'vertical' : 'inline'"
      :theme="theme"
      :inline-collapsed="collapsed"
      :inline-indent="24"
      @click="handleMenuClick"
      @open-change="handleOpenChange"
      class="sider-menu"
    >
      <template v-for="route in routes" :key="route.path">
        <SiderMenuItem
          :route="route"
          :collapsed="collapsed"
          :level="1"
        />
      </template>
    </a-menu>

    <!-- 折叠按钮 -->
    <button
      v-show="!collapsed"
      class="collapse-btn"
      @click="$emit('toggle')"
      :aria-label="collapsed ? '展开菜单' : '折叠菜单'"
    >
      <MenuFoldOutlined
        :rotate="collapsed ? 180 : 0"
        class="collapse-icon"
      />
    </button>
  </div>
</template>

<script setup lang="ts">
import { computed, watch, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { usePermissionStore } from '@/stores/modules/permission'
import { useAppStore } from '@/stores/modules/app'
import { MenuUnfoldOutlined, MenuFoldOutlined } from '@ant-design/icons-vue'
import Logo from './Logo.vue'
import SiderMenuItem from './SiderMenuItem.vue'

const props = defineProps<{
  collapsed?: boolean
}>()

const emit = defineEmits<{
  toggle: []
}>()

const router = useRouter()
const route = useRoute()
const permissionStore = usePermissionStore()
const appStore = useAppStore()

const theme = computed(() => appStore.themeMode === 'dark' ? 'dark' : 'light')
const routes = computed(() => permissionStore.sidebarRoutes)

const selectedKeys = ref<string[]>([])
const openKeys = ref<string[]>([])

function handleMenuClick({ key, item }: any) {
  if (item?.props?.route?.children?.length) return

  const targetRoute = item.props.route
  if (targetRoute) {
    router.push(targetRoute.path)
    selectedKeys.value = [key]
  }
}

function handleOpenChange(keys: string[]) {
  openKeys.value = keys
}

function updateSelectedKeys() {
  const matched = route.matched
  const keys: string[] = []

  matched.forEach(m => {
    if (m.path && m.path !== '/') {
      keys.push(m.path)
    }
  })

  selectedKeys.value = keys.length > 0 ? keys : [route.path]
  openKeys.value = keys.slice(0, -1)
}

watch(
  () => route.path,
  updateSelectedKeys,
  { immediate: true }
)

watch(
  () => permissionStore.sidebarRoutes,
  updateSelectedKeys,
  { immediate: true }
)
</script>

<style scoped>
.sider-menu-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  position: relative;
  background: var(--zhurong-color-bg-container);
}

.sider-menu {
  flex: 1;
  overflow-y: auto;
  border-right: none;
  background: transparent;
}

.sider-menu :deep(.ant-menu-item) {
  border-radius: 8px;
  margin: 4px 12px;
  padding: 8px 16px;
  transition: all 0.2s;
}

.sider-menu :deep(.ant-menu-item-selected) {
  background: var(--zhurong-color-primary-bg) !important;
  color: var(--zhurong-color-primary) !important;
}

.sider-menu :deep(.ant-menu-item-selected::after) {
  display: none;
}

.sider-menu :deep(.ant-menu-submenu-title) {
  border-radius: 8px;
  margin: 4px 12px;
  padding: 8px 16px;
}

.sider-menu :deep(.ant-menu-submenu-selected > .ant-menu-submenu-title) {
  background: var(--zhurong-color-primary-bg) !important;
  color: var(--zhurong-color-primary) !important;
}

.sider-menu :deep(.ant-menu-icon) {
  font-size: 16px;
  margin-right: 12px;
}

.sider-menu :deep(.ant-menu-title-content) {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.collapse-btn {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  width: 32px;
  height: 32px;
  border: 1px solid var(--zhurong-color-border);
  border-radius: 50%;
  background: var(--zhurong-color-bg-container);
  color: var(--zhurong-color-text-secondary);
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s;
  z-index: 10;
  box-shadow: var(--zhurong-box-shadow-sm);
}

.collapse-btn:hover {
  background: var(--zhurong-color-bg-hover);
  border-color: var(--zhurong-color-primary);
  color: var(--zhurong-color-primary);
}

.collapse-icon {
  transition: transform 0.2s;
}

/* 滚动条 */
.sider-menu ::-webkit-scrollbar {
  width: 4px;
}

.sider-menu ::-webkit-scrollbar-track {
  background: transparent;
}

.sider-menu ::-webkit-scrollbar-thumb {
  background: var(--zhurong-color-border);
  border-radius: 2px;
}

.sider-menu ::-webkit-scrollbar-thumb:hover {
  background: var(--zhurong-color-border-hover);
}
</style>