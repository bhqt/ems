<template>
  <a-tabs
    v-model:active-key="activeKey"
    :items="items"
    :type="type"
    :size="size"
    :animated="animated"
    :centered="centered"
    :tab-bar-extra-content="tabBarExtraContent"
    :tab-bar-gutter="tabBarGutter"
    :default-active-key="defaultActiveKey"
    :hide-all="hideAll"
    :more-icon="moreIcon"
    :more-transition-name="moreTransitionName"
    :popup-class-name="popupClassName"
    :render-tab-bar="renderTabBar"
    @change="handleChange"
    @edit="handleEdit"
    @tab-click="handleTabClick"
  >
    <slot />
  </a-tabs>
</template>

<script setup lang="ts">
import type { TabsProps, TabPaneProps } from 'ant-design-vue'

const props = defineProps<TabsProps & {
  /** 激活的标签键 */
  activeKey?: string
  /** 标签页列表 */
  items?: Array<TabPaneProps & { key: string }>
  /** 类型 */
  type?: 'line' | 'card' | 'editable-card'
  /** 尺寸 */
  size?: 'large' | 'middle' | 'small'
  /** 动画 */
  animated?: boolean
  /** 居中 */
  centered?: boolean
  /** 标签栏额外内容 */
  tabBarExtraContent?: any
  /** 标签间距 */
  tabBarGutter?: number
  /** 默认激活键 */
  defaultActiveKey?: string
  /** 隐藏所有 */
  hideAll?: boolean
  /** 更多图标 */
  moreIcon?: any
  /** 更多过渡动画 */
  moreTransitionName?: string
  /** 弹出层类名 */
  popupClassName?: string
  /** 自定义渲染标签栏 */
  renderTabBar?: (props: any) => any
}>()

const emit = defineEmits<{
  'update:activeKey': [key: string]
  change: [key: string]
  edit: [action: 'add' | 'remove', key: string]
  tabClick: [key: string]
}>()

function handleChange(key: string) {
  emit('update:activeKey', key)
  emit('change', key)
}

function handleEdit(action: 'add' | 'remove', key: string) {
  emit('edit', action, key)
}

function handleTabClick(key: string) {
  emit('tabClick', key)
}
</script>