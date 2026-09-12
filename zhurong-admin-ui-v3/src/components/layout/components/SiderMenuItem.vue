<template>
  <template v-if="route.children && route.children.length > 0">
    <a-sub-menu :key="route.path">
      <template #title>
        <component
          v-if="route.meta?.icon && getMenuIcon(route.meta.icon)"
          :is="getMenuIcon(route.meta.icon)"
          class="menu-icon"
        />
        <span class="menu-title" v-show="!collapsed || level === 1">
          {{ menuTitle(route) }}
        </span>
      </template>
      <template v-for="child in route.children" :key="child.path">
        <SiderMenuItem
          :route="child"
          :collapsed="collapsed"
          :level="level + 1"
        />
      </template>
    </a-sub-menu>
  </template>

  <template v-else>
    <a-menu-item
      :key="route.path"
      :disabled="route.meta?.disabled"
      :title="menuTitle(route)"
    >
      <template #icon>
        <component
          v-if="route.meta?.icon && getMenuIcon(route.meta.icon)"
          :is="getMenuIcon(route.meta.icon)"
          class="menu-icon"
        />
      </template>
      <span class="menu-title" v-show="!collapsed || level === 1">
        {{ menuTitle(route) }}
      </span>
      <a-badge
        v-if="route.meta?.badge"
        :count="route.meta.badge.count"
        :color="route.meta.badge.color"
        :dot="route.meta.badge.dot"
        :title="route.meta.badge.title"
        class="menu-badge"
      />
    </a-menu-item>
  </template>
</template>

<script setup lang="ts">
import type { RouteRecordRaw } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { getMenuIcon } from '@/utils/setupIcons'

interface SiderMenuItemProps {
  route: RouteRecordRaw
  collapsed: boolean
  level: number
}

defineProps<SiderMenuItemProps>()

const { t } = useI18n()

function menuTitle(route: RouteRecordRaw) {
  return t(route.meta?.title as string || '') || route.name
}
</script>

<style scoped>
.menu-icon {
  font-size: 16px;
}

.menu-title {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.menu-badge {
  margin-left: auto;
  flex-shrink: 0;
}

/* 多级菜单缩进 */
:deep(.ant-menu-inline .ant-menu-submenu-inline .ant-menu-item) {
  padding-left: 56px;
}

:deep(.ant-menu-inline .ant-menu-submenu-inline .ant-menu-submenu-inline .ant-menu-item) {
  padding-left: 80px;
}

:deep(.ant-menu-inline .ant-menu-submenu-inline .ant-menu-submenu-inline .ant-menu-submenu-inline .ant-menu-item) {
  padding-left: 104px;
}
</style>