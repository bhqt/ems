<template>
  <a-descriptions
    v-bind="$attrs"
    :title="title"
    :extra="extra"
    :column="column"
    :size="size"
    :layout="layout"
    :colon="colon"
    :bordered="bordered"
    :label-style="labelStyle"
    :content-style="contentStyle"
  >
    <slot />
  </a-descriptions>
</template>

<script setup lang="ts">
import type { DescriptionsProps } from 'ant-design-vue'

const props = defineProps<DescriptionsProps & {
  /** 标题 */
  title?: any
  /** 额外内容 */
  extra?: any
  /** 列数 */
  column?: number | { xxl: number; xl: number; lg: number; md: number; sm: number; xs: number }
  /** 尺寸 */
  size?: 'default' | 'middle' | 'small'
  /** 布局 */
  layout?: 'horizontal' | 'vertical'
  /** 显示冒号 */
  colon?: boolean
  /** 带边框 */
  bordered?: boolean
  /** 标签样式 */
  labelStyle?: any
  /** 内容样式 */
  contentStyle?: any
}>()
</script>