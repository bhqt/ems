<template>
  <a-steps
    v-bind="$attrs"
    :current="current"
    :direction="direction"
    :size="size"
    :status="status"
    :initial="initial"
    :label-placement="labelPlacement"
    :progress-dot="progressDot"
    :items="items"
    @change="handleChange"
  >
    <slot />
  </a-steps>
</template>

<script setup lang="ts">
import type { StepsProps } from 'ant-design-vue'

const props = defineProps<StepsProps & {
  /** 当前步骤 */
  current?: number
  /** 方向 */
  direction?: 'horizontal' | 'vertical'
  /** 尺寸 */
  size?: 'default' | 'small'
  /** 状态 */
  status?: 'process' | 'finish' | 'wait' | 'error'
  /** 起始步骤 */
  initial?: number
  /** 标签位置 */
  labelPlacement?: 'horizontal' | 'vertical'
  /** 进度点 */
  progressDot?: boolean | ((iconDot: any, info: { index: number; status: string; title: string; description: string; prefixCls: string }) => any)
  /** 步骤项 */
  items?: StepsProps['items']
}>()

const emit = defineEmits<{
  change: [current: number]
}>()

function handleChange(current: number) {
  emit('change', current)
}
</script>