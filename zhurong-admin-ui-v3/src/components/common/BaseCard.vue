<template>
  <a-card
    :title="title"
    :extra="extra"
    :bordered="bordered"
    :size="size"
    :loading="loading"
    :hoverable="hoverable"
    :cover="cover"
    :actions="actions"
    :tab-list="tabList"
    :active-tab-key="activeTabKey"
    :type="type"
    @tab-change="handleTabChange"
  >
    <slot />
  </a-card>
</template>

<script setup lang="ts">
import type { CardProps } from 'ant-design-vue'

const props = defineProps<CardProps & {
  /** 标题 */
  title?: string
  /** 右上角操作区 */
  extra?: any
  /** 是否有边框 */
  bordered?: boolean
  /** 尺寸 */
  size?: 'default' | 'small'
  /** 加载状态 */
  loading?: boolean
  /** 悬浮浮起 */
  hoverable?: boolean
  /** 封面 */
  cover?: any
  /** 操作列表 */
  actions?: any[]
  /** 页签列表 */
  tabList?: Array<{ key: string; tab: any }>
  /** 当前激活页签 */
  activeTabKey?: string
  /** 卡片类型 */
  type?: 'inner' | 'meta'
}>()

const emit = defineEmits<{
  tabChange: [key: string]
}>()

function handleTabChange(key: string) {
  emit('tabChange', key)
}
</script>