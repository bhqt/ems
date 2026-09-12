<template>
  <a-table
    v-bind="$attrs"
    :columns="columns"
    :data-source="dataSource"
    :pagination="pagination"
    :row-key="rowKey"
    :row-selection="rowSelection"
    :loading="loading"
    :scroll="scroll"
    :sort-directions="sortDirections"
    :default-sort-order="defaultSortOrder"
    :show-sorter-tooltip="showSorterTooltip"
    :expandable="expandable"
    :expanded-row-keys="expandedRowKeys"
    :expand-icon="expandIcon"
    :expand-icon-column-index="expandIconColumnIndex"
    @change="handleChange"
    @expand="handleExpand"
    @row-selection-change="handleRowSelectionChange"
  >
    <template v-for="(slot, name) in $slots" #[name]="slot">
      <slot :name="name" v-bind="slotProps" />
    </template>
  </a-table>
</template>

<script setup lang="ts">
import type { TableProps, ColumnType } from 'ant-design-vue'

const props = defineProps<TableProps<any> & {
  /** 列配置 */
  columns?: ColumnType<any>[]
  /** 数据源 */
  dataSource?: any[]
  /** 分页配置 */
  pagination?: TableProps<any>['pagination'] | false
  /** 行键 */
  rowKey?: string
  /** 行选择配置 */
  rowSelection?: TableProps<any>['rowSelection']
  /** 加载状态 */
  loading?: boolean
  /** 滚动配置 */
  scroll?: TableProps<any>['scroll']
  /** 排序方向 */
  sortDirections?: TableProps<any>['sortDirections']
  /** 默认排序顺序 */
  defaultSortOrder?: TableProps<any>['defaultSortOrder']
  /** 显示排序提示 */
  showSorterTooltip?: boolean
  /** 可展开行 */
  expandable?: TableProps<any>['expandable']
  /** 展开的行键 */
  expandedRowKeys?: (string | number)[]
  /** 展开图标 */
  expandIcon?: TableProps<any>['expandIcon']
  /** 展开图标列索引 */
  expandIconColumnIndex?: number
}>()

const emit = defineEmits<{
  change: [pagination: any, filters: any, sorter: any, extra: any]
  expand: [expanded: boolean, record: any]
  rowSelectionChange: [keys: (string | number)[], rows: any[]]
}>()

const slotProps = ref({})

function handleChange(pagination: any, filters: any, sorter: any, extra: any) {
  emit('change', pagination, filters, sorter, extra)
}

function handleExpand(expanded: boolean, record: any) {
  emit('expand', expanded, record)
}

function handleRowSelectionChange(keys: (string | number)[], rows: any[]) {
  emit('rowSelectionChange', keys, rows)
}
</script>