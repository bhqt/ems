<template>
  <a-transfer
    v-bind="$attrs"
    :data-source="dataSource"
    :titles="titles"
    :operations="operations"
    :list-style="listStyle"
    :one-way="oneWay"
    :show-search="showSearch"
    :filter-option="filterOption"
    :render="render"
    :footer="footer"
    :show-select-all="showSelectAll"
    :select-all-labels="selectAllLabels"
    :pagination="pagination"
    :disabled="disabled"
    :row-key="rowKey"
    @change="handleChange"
    @select-change="handleSelectChange"
    @scroll="handleScroll"
  >
    <slot />
  </a-transfer>
</template>

<script setup lang="ts">
import type { TransferProps, TransferItem } from 'ant-design-vue'

const props = defineProps<TransferProps & {
  /** 数据源 */
  dataSource?: TransferItem[]
  /** 标题集合 */
  titles?: [string, string]
  /** 操作文案 */
  operations?: [string, string]
  /** 列表样式 */
  listStyle?: any
  /** 单向 */
  oneWay?: boolean
  /** 显示搜索 */
  showSearch?: boolean
  /** 筛选函数 */
  filterOption?: (input: string, item: TransferItem) => boolean
  /** 渲染函数 */
  render?: (item: TransferItem) => any
  /** 底部渲染 */
  footer?: (props: { direction: 'left' | 'right'; checkedKeys: string[]; selectedKeys: string[] }) => any
  /** 显示全选 */
  showSelectAll?: boolean
  /** 全选标签 */
  selectAllLabels?: [string, string]
  /** 分页 */
  pagination?: boolean | { pageSize: number }
  /** 禁用 */
  disabled?: boolean
  /** 行键 */
  rowKey?: string
}>()

const emit = defineEmits<{
  change: [targetKeys: string[], direction: string, moveKeys: string[]]
  selectChange: [sourceSelectedKeys: string[], targetSelectedKeys: string[]]
  scroll: [direction: 'left' | 'right', event: Event]
}>()

function handleChange(targetKeys: string[], direction: string, moveKeys: string[]) {
  emit('change', targetKeys, direction, moveKeys)
}

function handleSelectChange(sourceSelectedKeys: string[], targetSelectedKeys: string[]) {
  emit('selectChange', sourceSelectedKeys, targetSelectedKeys)
}

function handleScroll(direction: 'left' | 'right', event: Event) {
  emit('scroll', direction, event)
}
</script>