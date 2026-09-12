<template>
  <a-tag
    v-bind="$attrs"
    :color="color"
    :closable="closable"
    :checked="checked"
    :icon="icon"
    @close="handleClose"
    @change="handleChange"
  >
    <slot />
  </a-tag>
</template>

<script setup lang="ts">
import type { TagProps } from 'ant-design-vue'

const props = defineProps<TagProps & {
  /** 颜色 */
  color?: string
  /** 可关闭 */
  closable?: boolean
  /** 选中状态 */
  checked?: boolean
  /** 图标 */
  icon?: any
}>()

const emit = defineEmits<{
  close: [event: MouseEvent]
  change: [checked: boolean]
}>()

function handleClose(event: MouseEvent) {
  emit('close', event)
}

function handleChange(checked: boolean) {
  emit('change', checked)
}
</script>