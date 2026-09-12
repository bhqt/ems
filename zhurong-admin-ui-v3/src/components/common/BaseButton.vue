<template>
  <a-button
    :type="type"
    :danger="danger"
    :ghost="ghost"
    :size="size"
    :loading="loading"
    :disabled="disabled"
    :block="block"
    :href="href"
    :target="target"
    :title="title"
    @click="handleClick"
  >
    <slot />
  </a-button>
</template>

<script setup lang="ts">
import type { ButtonProps } from 'ant-design-vue'

const props = defineProps<ButtonProps & {
  /** 是否显示加载状态 */
  loading?: boolean
  /** 按钮类型 */
  type?: 'primary' | 'default' | 'dashed' | 'text' | 'link'
  /** 危险按钮 */
  danger?: boolean
  /** 幽灵按钮 */
  ghost?: boolean
  /** 尺寸 */
  size?: 'large' | 'middle' | 'small'
  /** 禁用状态 */
  disabled?: boolean
  /** 块级按钮 */
  block?: boolean
  /** 链接地址 */
  href?: string
  /** 链接目标 */
  target?: string
  /** 提示文本 */
  title?: string
}>()

const emit = defineEmits<{
  click: [event: MouseEvent]
}>()

function handleClick(event: MouseEvent) {
  if (!props.disabled && !props.loading) {
    emit('click', event)
  }
}
</script>

<style scoped>
/* 样式由 UnoCSS 和 AntD 提供 */
</style>