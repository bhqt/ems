<template>
  <a-pagination
    v-model:current="current"
    v-model:page-size="pageSize"
    :total="total"
    :page-size-options="pageSizeOptions"
    :show-size-changer="showSizeChanger"
    :show-quick-jumper="showQuickJumper"
    :show-total="showTotal"
    :simple="simple"
    :size="size"
    :responsive="responsive"
    :item-render="itemRender"
    @change="handleChange"
    @show-size-change="handleShowSizeChange"
  />
</template>

<script setup lang="ts">
import type { PaginationProps } from 'ant-design-vue'

const props = defineProps<PaginationProps & {
  /** 当前页码 */
  current?: number
  /** 每页条数 */
  pageSize?: number
  /** 总条数 */
  total?: number
  /** 页码选项 */
  pageSizeOptions?: string[]
  /** 显示页码切换器 */
  showSizeChanger?: boolean
  /** 显示快速跳转 */
  showQuickJumper?: boolean
  /** 显示总数 */
  showTotal?: (total: number, range: [number, number]) => any
  /** 简单模式 */
  simple?: boolean
  /** 尺寸 */
  size?: 'default' | 'small'
  /** 响应式 */
  responsive?: boolean
  /** 自定义项渲染 */
  itemRender?: (current: number, type: 'page' | 'prev' | 'next' | 'jump-prev' | 'jump-next', originalNode: any) => any
}>()

const emit = defineEmits<{
  'update:current': [current: number]
  'update:pageSize': [pageSize: number]
  change: [current: number, pageSize: number]
  showSizeChange: [current: number, pageSize: number]
}>()

function handleChange(current: number, pageSize: number) {
  emit('update:current', current)
  emit('update:pageSize', pageSize)
  emit('change', current, pageSize)
}

function handleShowSizeChange(current: number, pageSize: number) {
  emit('showSizeChange', current, pageSize)
}
</script>