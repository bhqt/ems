<template>
  <BaseCard
    :title="title"
    :extra="extra"
    :bordered="bordered"
    :loading="loading"
    :hoverable="hoverable"
    :size="size"
    :actions="cardActions"
  >
    <slot />
  </BaseCard>
</template>

<script setup lang="ts>
import BaseCard from '@/components/common/BaseCard.vue'

const props = defineProps<{
  /** 标题 */
  title?: string
  /** 右上角操作 */
  extra?: any
  /** 是否有边框 */
  bordered?: boolean
  /** 加载状态 */
  loading?: boolean
  /** 悬浮浮起 */
  hoverable?: boolean
  /** 尺寸 */
  size?: 'default' | 'small'
  /** 操作按钮 */
  actions?: Array<{
    key: string
    component: string
    props?: Record<string, any>
    onClick?: () => void
  }>
}>()

const cardActions = computed(() => {
  if (!props.actions || !props.actions.length) return undefined
  return props.actions.map(action => (
    <component
      :key="action.key"
      :is="action.component"
      v-bind="action.props"
      @click="action.onClick"
      class="pro-card-action"
    />
  ))
})
</script>

<style scoped>
.pro-card-action {
  margin-left: 8px;
}
</style>