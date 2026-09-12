/** 分页参数 */
export interface PageParams {
  pageNum?: number
  pageSize?: number
}

/** 分页结果 */
export interface PageResult<T> {
  rows: T[]
  total: number
}

/** 列表结果 */
export interface ListResult<T> {
  rows: T[]
  total: number
}

/** 基础实体 */
export interface BaseEntity {
  id: string | number
  createTime?: string
  updateTime?: string
  createBy?: string
  updateBy?: string
  remark?: string
}

/** 树形节点 */
export interface TreeNode<T = any> {
  id: string | number
  parentId?: string | number
  children?: TreeNode[]
  [key: string]: any
}

/** 选项 */
export interface Option {
  label: string
  value: any
  disabled?: boolean
  children?: Option[]
}

/** 键值对 */
export interface KeyValue {
  key: string
  value: any
}

/** 状态选项 */
export interface StatusOption {
  value: string
  label: string
  color?: string
}

/** 文件信息 */
export interface FileInfo {
  name: string
  url: string
  size: number
  type: string
  extension: string
  uploadTime: string
}

/** 导出参数 */
export interface ExportParams {
  fileName: string
  sheetName?: string
  headers?: string[]
  data: any[]
}

/** 打印参数 */
export interface PrintParams {
  title?: string
  tableId?: string
  data?: any[]
  columns?: any[]
}

/** 图表配置 */
export interface ChartConfig {
  type: 'line' | 'bar' | 'pie' | 'radar' | 'scatter' | 'gauge' | 'funnel' | 'heatmap'
  title?: string
  data: any
  options?: any
}