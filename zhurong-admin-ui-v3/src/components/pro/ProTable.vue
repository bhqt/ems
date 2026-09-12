<template>
  <div class="pro-table">
    <!-- 搜索表单 -->
    <ProSearchForm
      v-if="searchForm && searchForm.length > 0"
      :schemas="searchForm"
      :form-data="searchParams"
      @search="handleSearch"
      @reset="handleReset"
      @collapse="handleCollapse"
    />

    <!-- 工具栏 -->
    <div class="pro-table-toolbar" v-if="toolbar && toolbar.length > 0">
      <div class="toolbar-left">
        <slot name="toolbar-left">
          <template v-for="tool in toolbar" :key="tool.key">
            <component
              :is="tool.component"
              v-bind="tool.props"
              @click="tool.onClick"
              class="toolbar-btn"
            />
          </template>
        </slot>
      </div>
      <div class="toolbar-right">
        <slot name="toolbar-right">
          <BaseButton
            v-if="showColumnSetting"
            type="text"
            icon={<SettingOutlined />}
            @click="showColumnSettingModal = true"
          >
            {{ t('common.columnSetting') }}
          </BaseButton>
          <BaseButton
            v-if="showRefresh"
            type="text"
            icon={<ReloadOutlined />}
            :loading="loading"
            @click="reload"
          >
            {{ t('common.refresh') }}
          </BaseButton>
        </slot>
      </div>
    </div>

    <!-- 表格 -->
    <BaseTable
      ref="tableRef"
      :columns="mergedColumns"
      :data-source="tableData"
      :pagination="pagination"
      :row-key="rowKey"
      :row-selection="rowSelection"
      :loading="loading"
      :scroll="scroll"
      :expandable="expandable"
      :expanded-row-keys="expandedRowKeys"
      @change="handleTableChange"
      @row-selection-change="handleSelectionChange"
    >
      <slot v-for="(slot, name) in $slots" :name="name" :slot="slot" />
    </BaseTable>

    <!-- 列设置弹窗 -->
    <BaseModal
      v-model:open="showColumnSettingModal"
      :title="t('common.columnSetting')"
      :width="480"
      @ok="confirmColumnSetting"
    >
      <ColumnSetting
        :columns="mergedColumns"
        v-model:checked-keys="columnSettingCheckedKeys"
      />
    </BaseModal>

    <!-- 新增/编辑抽屉 -->
    <BaseDrawer
      v-model:open="drawerVisible"
      :title="drawerTitle"
      :width="drawerWidth"
      :placement="drawerPlacement"
      @close="handleDrawerClose"
    >
      <ProForm
        v-if="drawerForm && drawerForm.length > 0"
        ref="drawerFormRef"
        :schemas="drawerForm"
        :initial-values="drawerInitialValues"
        @finish="handleDrawerSubmit"
      />
    </BaseDrawer>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, reactive, watch, nextTick } from 'vue'
import { useI18n } from 'vue-i18n'
import { useTable } from '@/composables/useTable'
import {
  SettingOutlined,
  ReloadOutlined,
  PlusOutlined,
  EditOutlined,
  DeleteOutlined,
  DownloadOutlined,
} from '@ant-design/icons-vue'
import BaseTable from '@/components/common/BaseTable.vue'
import BaseModal from '@/components/common/BaseModal.vue'
import BaseDrawer from '@/components/common/BaseDrawer.vue'
import BaseButton from '@/components/common/BaseButton.vue'
import ProSearchForm from './ProSearchForm.vue'
import ProForm from './ProForm.vue'
import ColumnSetting from './ColumnSetting.vue'

const props = defineProps<{
  /** 列配置 */
  columns: any[]
  /** 请求函数 */
  request: (params: any) => Promise<{ data: any[]; total: number }>
  /** 搜索表单配置 */
  searchForm?: any[]
  /** 工具栏 */
  toolbar?: Array<'add' | 'edit' | 'delete' | 'export' | 'refresh' | 'columnSetting'>
  /** 行键 */
  rowKey?: string
  /** 行选择 */
  rowSelection?: any
  /** 分页配置 */
  pagination?: any
  /** 滚动配置 */
  scroll?: any
  /** 可展开 */
  expandable?: any
  /** 抽屉宽度 */
  drawerWidth?: number | string
  /** 抽屉位置 */
  drawerPlacement?: 'left' | 'right' | 'top' | 'bottom'
  /** 新增/编辑表单配置 */
  drawerForm?: any
  /** 即时请求 */
  immediate?: boolean
  /** 转换数据 */
  transform?: (data: any[]) => any[]
}>()

const emit = defineEmits<{
  add: [record: any]
  edit: [record: any]
  delete: [record: any | any[]]
  export: [data: any[]]
  refresh: []
  search: [params: any]
}>()

const { t } = useI18n()

const tableRef = ref()
const drawerFormRef = ref()

// 合并列配置
const mergedColumns = computed(() => {
  return props.columns.map((col: any) => ({
    ...col,
    ellipsis: col.ellipsis ?? true,
    align: col.align ?? 'center',
    headerAlign: col.headerAlign ?? 'center',
  }))
})

// 表格状态管理
const {
  tableData,
  loading,
  total,
  pagination,
  sorter,
  filters,
  selectedRows,
  selectedRowKeys,
  searchParams,
  reload,
  refresh,
  changePage,
  changePageSize,
  changeSorter,
  changeFilters,
  setSearchParams,
  resetSearchParams,
  onSelectionChange,
  clearSelection,
  toggleRowSelection,
  toggleAllSelection,
  exportData,
} = useTable({
  request: props.request,
  immediate: props.immediate ?? true,
  pagination: props.pagination,
  sorter: props.columns.find(c => c.defaultSortOrder) ? { field: c.dataIndex, order: c.defaultSortOrder } : null,
  transform: props.transform,
  beforeRequest: (params) => ({
    ...params,
    ...searchParams.value,
  }),
})

// 抽屉状态
const drawerVisible = ref(false)
const drawerTitle = ref('')
const drawerInitialValues = ref({})
const drawerEditingKey = ref<string | number | null>(null)

const defaultToolbar = ['add', 'edit', 'delete', 'export', 'refresh', 'columnSetting']
const toolbar = computed(() => props.toolbar?.length ? props.toolbar : defaultToolbar)
const showColumnSetting = computed(() => toolbar.value.includes('columnSetting'))
const showRefresh = computed(() => toolbar.value.includes('refresh'))

const showColumnSettingModal = ref(false)
const columnSettingCheckedKeys = ref<string[]>([])

function initColumnSetting() {
  columnSettingCheckedKeys.value = mergedColumns.value
    .filter((col: any) => col.dataIndex && !col.fixed && col.hidden !== true)
    .map((col: any) => String(col.dataIndex))
}

function confirmColumnSetting() {
  mergedColumns.value.forEach((col: any) => {
    if (col.dataIndex) {
      col.hidden = !columnSettingCheckedKeys.value.includes(String(col.dataIndex))
    }
  })
  showColumnSettingModal.value = false
}

// 搜索处理
function handleSearch(params: any) {
  setSearchParams(params)
  emit('search', params)
}

function handleReset() {
  resetSearchParams()
}

function handleCollapse(collapsed: boolean) {
  // 搜索表单折叠/展开
}

// 表格事件
function handleTableChange(pagination: any, filters: any, sorter: any) {
  changePage(pagination.current, pagination.pageSize)
  changeSorter(sorter)
  changeFilters(filters)
}

function handleSelectionChange(keys: (string | number)[], rows: any[]) {
  onSelectionChange(keys, rows)
}

// 工具栏操作
function handleAdd() {
  drawerTitle.value = '新增'
  drawerEditingKey.value = null
  drawerInitialValues.value = {}
  drawerVisible.value = true
}

function handleEdit(record?: any) {
  if (!record && selectedRows.value.length !== 1) {
    return
  }
  const editRecord = record || selectedRows.value[0]
  drawerTitle.value = '编辑'
  drawerEditingKey.value = editRecord[props.rowKey || 'id']
  drawerInitialValues.value = { ...editRecord }
  drawerVisible.value = true
}

function handleDelete(record?: any) {
  const deleteRecords = record ? [record] : selectedRows.value
  if (deleteRecords.length === 0) return
  emit('delete', deleteRecords.length === 1 ? deleteRecords[0] : deleteRecords)
}

function handleExport() {
  exportData('export')
  emit('export', tableData.value)
}

function handleRefresh() {
  reload()
  emit('refresh')
}

// 抽屉处理
async function handleDrawerSubmit(values: any) {
  try {
    if (drawerEditingKey.value) {
      // 编辑
      emit('edit', { ...values, [props.rowKey || 'id']: drawerEditingKey.value })
    } else {
      // 新增
      emit('add', values)
    }
    drawerVisible.value = false
    reload()
  } catch (error) {
    console.error('Drawer submit error:', error)
  }
}

function handleDrawerClose() {
  drawerVisible.value = false
  drawerFormRef.value?.resetFields()
  nextTick(() => {
    drawerInitialValues.value = {}
    drawerEditingKey.value = null
  })
}

// 监听列变化
watch(mergedColumns, initColumnSetting, { immediate: true, deep: true })
</script>

<style scoped>
.pro-table {
  display: flex;
  flex-direction: column;
  height: 100%;
  background: var(--zhurong-color-bg-container);
  border-radius: var(--zhurong-border-radius-lg);
  border: 1px solid var(--zhurong-color-border);
  overflow: hidden;
}

.pro-table-toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 24px;
  border-bottom: 1px solid var(--zhurong-color-border-secondary);
  flex-wrap: wrap;
  gap: 12px;
}

.toolbar-left {
  display: flex;
  align-items: center;
  gap: 8px;
}

.toolbar-right {
  display: flex;
  align-items: center;
  gap: 8px;
}

.toolbar-btn {
  height: 36px;
}
</style>