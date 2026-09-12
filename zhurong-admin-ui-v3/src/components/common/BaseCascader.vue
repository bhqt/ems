<template>
  <a-cascader
    v-bind="$attrs"
    :options="options"
    :field-names="fieldNames"
    :placeholder="placeholder"
    :disabled="disabled"
    :allow-clear="allowClear"
    :show-search="showSearch"
    :not-found-content="notFoundContent"
    :expand-trigger="expandTrigger"
    :change-on-select="changeOnSelect"
    :load-data="loadData"
    :dropdown-render="dropdownRender"
    :dropdown-style="dropdownStyle"
    :dropdown-class-name="dropdownClassName"
    :get-popup-container="getPopupContainer"
    :status="status"
    :suffix-icon="suffixIcon"
    @change="handleChange"
    @popup-visible-change="handlePopupVisibleChange"
  />
</template>

<script setup lang="ts">
import type { CascaderProps, CascaderOption } from 'ant-design-vue'

const props = defineProps<CascaderProps & {
  /** 选项数据 */
  options?: CascaderOption[]
  /** 字段名映射 */
  fieldNames?: CascaderProps['fieldNames']
  /** 占位符 */
  placeholder?: string
  /** 禁用 */
  disabled?: boolean
  /** 允许清空 */
  allowClear?: boolean
  /** 显示搜索 */
  showSearch?: boolean | { filter?: (input: string, path: CascaderOption[]) => boolean; limit?: number; matchInputWidth?: boolean }
  /** 未找到内容 */
  notFoundContent?: string
  /** 展开触发方式 */
  expandTrigger?: 'click' | 'hover'
  /** 选中即变更 */
  changeOnSelect?: boolean
  /** 异步加载 */
  loadData?: (options: CascaderOption[]) => Promise<void>
  /** 自定义下拉菜单 */
  dropdownRender?: (menu: any) => any
  /** 下拉菜单样式 */
  dropdownStyle?: any
  /** 下拉菜单类名 */
  dropdownClassName?: string
  /** 挂载容器 */
  getPopupContainer?: (trigger: HTMLElement) => HTMLElement
  /** 状态 */
  status?: 'error' | 'warning'
  /** 后缀图标 */
  suffixIcon?: any
}>()

const emit = defineEmits<{
  change: [value: (string | number)[], option: CascaderOption[]]
  popupVisibleChange: [visible: boolean]
}>()

function handleChange(value: (string | number)[], option: CascaderOption[]) {
  emit('change', value, option)
}

function handlePopupVisibleChange(visible: boolean) {
  emit('popupVisibleChange', visible)
}
</script>