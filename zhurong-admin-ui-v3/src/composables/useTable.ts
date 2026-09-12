import { ref, reactive, computed, onMounted, nextTick } from 'vue'
import type { TableProps, PaginationProps } from 'ant-design-vue'
import type { ProColumns } from '@/components/pro/ProTable/types'

export interface UseTableOptions<T = any> {
  /** 请求函数 */
  request?: (params: any) => Promise<{ data: T[]; total: number }>
  /** 即时请求 */
  immediate?: boolean
  /** 默认分页参数 */
  pagination?: Partial<PaginationProps>
  /** 默认排序参数 */
  sorter?: { field: string; order: 'ascend' | 'descend' }
  /** 默认筛选参数 */
  filters?: Record<string, any[]>
  /** 行键 */
  rowKey?: string
  /** 转换数据 */
  transform?: (data: T[]) => T[]
  /** 请求前处理 */
  beforeRequest?: (params: any) => any
  /** 请求后处理 */
  afterResponse?: (data: { data: T[]; total: number }) => { data: T[]; total: number }
  /** 错误处理 */
  onError?: (error: Error) => void
}

export interface UseTableReturn<T = any> {
  /** 表格数据 */
  tableData: T[]
  /** 加载状态 */
  loading: boolean
  /** 总数 */
  total: number
  /** 分页配置 */
  pagination: PaginationProps
  /** 排序配置 */
  sorter: { field: string; order: 'ascend' | 'descend' } | null
  /** 筛选配置 */
  filters: Record<string, any[]>
  /** 选中行 */
  selectedRows: T[]
  /** 选中行键 */
  selectedRowKeys: (string | number)[]
  /** 搜索参数 */
  searchParams: Record<string, any>
  /** 列配置 */
  columns: ProColumns<T>[]
  /** 重新加载 */
  reload: (resetPage?: boolean) => Promise<void>
  /** 刷新当前页 */
  refresh: () => Promise<void>
  /** 改变页码 */
  changePage: (page: number, pageSize?: number) => void
  /** 改变页大小 */
  changePageSize: (pageSize: number) => void
  /** 改变排序 */
  changeSorter: (sorter: { field: string; order: 'ascend' | 'descend' } | null) => void
  /** 改变筛选 */
  changeFilters: (filters: Record<string, any[]>) => void
  /** 设置搜索参数 */
  setSearchParams: (params: Record<string, any>) => void
  /** 重置搜索参数 */
  resetSearchParams: () => void
  /** 选中行变化 */
  onSelectionChange: (keys: (string | number)[], rows: T[]) => void
  /** 清空选择 */
  clearSelection: () => void
  /** 切换行选择 */
  toggleRowSelection: (row: T, selected?: boolean) => void
  /** 全选/全不选 */
  toggleAllSelection: (selected: boolean) => void
  /** 导出数据 */
  exportData: (filename?: string) => void
  /** 获取选中数据 */
  getSelectedData: () => T[]
}

export function useTable<T = any>(options: UseTableOptions<T> = {}): UseTableReturn<T> {
  const {
    request,
    immediate = true,
    pagination: defaultPagination = {},
    sorter: defaultSorter,
    filters: defaultFilters,
    rowKey = 'id',
    transform,
    beforeRequest,
    afterResponse,
    onError,
  } = options

  // ============ State ============
  const tableData = ref<T[]>([])
  const loading = ref(false)
  const total = ref(0)
  const pagination = reactive<PaginationProps>({
    current: 1,
    pageSize: 20,
    total: 0,
    showSizeChanger: true,
    showQuickJumper: true,
    showTotal: (total: number) => `共 ${total} 条`,
    pageSizeOptions: ['10', '20', '50', '100'],
    ...defaultPagination,
  })
  const sorter = ref<{ field: string; order: 'ascend' | 'descend' } | null>(defaultSorter || null)
  const filters = ref<Record<string, any[]>>(defaultFilters || {})
  const selectedRowKeys = ref<(string | number)[]>([])
  const selectedRows = ref<T[]>([])
  const searchParams = ref<Record<string, any>>({})
  const columns = ref<ProColumns<T>[]>([])

  // ============ Computed ============
  const getRowKey = (record: T) => (record as any)[rowKey]

  // ============ Methods ============
  async function fetchData(resetPage = false) {
    if (!request) return

    if (resetPage) {
      pagination.current = 1
    }

    loading.value = true

    try {
      const params = {
        page: pagination.current,
        pageSize: pagination.pageSize,
        ...searchParams.value,
        ...(sorter.value ? { sortField: sorter.value.field, sortOrder: sorter.value.order } : {}),
        ...Object.fromEntries(
          Object.entries(filters.value).map(([key, value]) => [key, value.join(',')])
        ),
      }

      const processedParams = beforeRequest ? beforeRequest(params) : params
      let response = await request(processedParams)

      if (afterResponse) {
        response = afterResponse(response)
      }

      let data = response.data || []
      if (transform) {
        data = transform(data)
      }

      tableData.value = data
      total.value = response.total || 0
      pagination.total = total.value
    } catch (error) {
      console.error('Table fetch error:', error)
      tableData.value = []
      total.value = 0
      pagination.total = 0
      onError?.(error as Error)
    } finally {
      loading.value = false
    }
  }

  function reload(resetPage = false) {
    return fetchData(resetPage)
  }

  function refresh() {
    return fetchData(false)
  }

  function changePage(page: number, pageSize?: number) {
    pagination.current = page
    if (pageSize) {
      pagination.pageSize = pageSize
    }
    fetchData()
  }

  function changePageSize(pageSize: number) {
    pagination.pageSize = pageSize
    pagination.current = 1
    fetchData()
  }

  function changeSorter(newSorter: { field: string; order: 'ascend' | 'descend' } | null) {
    sorter.value = newSorter
    fetchData(true)
  }

  function changeFilters(newFilters: Record<string, any[]>) {
    filters.value = newFilters
    fetchData(true)
  }

  function setSearchParams(params: Record<string, any>) {
    searchParams.value = { ...searchParams.value, ...params }
    fetchData(true)
  }

  function resetSearchParams() {
    searchParams.value = {}
    fetchData(true)
  }

  function onSelectionChange(keys: (string | number)[], rows: T[]) {
    selectedRowKeys.value = keys
    selectedRows.value = rows
  }

  function clearSelection() {
    selectedRowKeys.value = []
    selectedRows.value = []
  }

  function toggleRowSelection(row: T, selected?: boolean) {
    const key = getRowKey(row)
    const index = selectedRowKeys.value.indexOf(key)
    if (selected === undefined) {
      selected = index === -1
    }
    if (selected && index === -1) {
      selectedRowKeys.value.push(key)
      selectedRows.value.push(row)
    } else if (!selected && index > -1) {
      selectedRowKeys.value.splice(index, 1)
      selectedRows.value.splice(index, 1)
    }
  }

  function toggleAllSelection(selected: boolean) {
    if (selected) {
      selectedRowKeys.value = tableData.value.map(getRowKey)
      selectedRows.value = [...tableData.value]
    } else {
      selectedRowKeys.value = []
      selectedRows.value = []
    }
  }

  function exportData(filename = 'export') {
    // 导出逻辑
    console.log('Export data:', { filename, data: tableData.value })
  }

  function getSelectedData() {
    return selectedRows.value
  }

  // ============ Lifecycle ============
  onMounted(() => {
    if (immediate) {
      fetchData()
    }
  })

  return {
    tableData,
    loading,
    total,
    pagination,
    sorter,
    filters,
    selectedRows,
    selectedRowKeys,
    searchParams,
    columns,
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
    getSelectedData,
  }
}