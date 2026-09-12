<template>
  <a-select
    v-model:value="modelValue"
    :options="options"
    :placeholder="placeholder"
    :disabled="disabled"
    :allow-clear="allowClear"
    :show-search="showSearch"
    :filter-option="filterOption"
    :mode="mode"
    :size="size"
    :max-tag-count="maxTagCount"
    :max-tag-placeholder="maxTagPlaceholder"
    :placeholder="placeholder"
    :not-found-content="notFoundContent"
    :dropdown-render="dropdownRender"
    :dropdown-match-select-width="dropdownMatchSelectWidth"
    :dropdown-style="dropdownStyle"
    :dropdown-class-name="dropdownClassName"
    :get-popup-container="getPopupContainer"
    :virtual="virtual"
    :list-height="listHeight"
    :list-item-height="listItemHeight"
    @update:value="handleUpdate"
    @change="handleChange"
    @search="handleSearch"
    @focus="handleFocus"
    @blur="handleBlur"
    @dropdown-visible-change="handleDropdownVisibleChange"
  >
    <slot />
  </a-select>
</template>

<script setup lang="ts">
import type { SelectProps } from 'ant-design-vue'

const props = defineProps<SelectProps & {
  modelValue?: any
  /** 选项数据 */
  options?: Array<{ label: string; value: any; disabled?: boolean; children?: any[] }>
  /** 占位符 */
  placeholder?: string
  /** 禁用状态 */
  disabled?: boolean
  /** 允许清空 */
  allowClear?: boolean
  /** 显示搜索框 */
  showSearch?: boolean
  /** 自定义筛选 */
  filterOption?: boolean | ((input: string, option: any) => boolean)
  /** 模式 */
  mode?: 'multiple' | 'tags' | 'combobox'
  /** 尺寸 */
  size?: 'large' | 'middle' | 'small'
  /** 最大标签数 */
  maxTagCount?: number
  /** 超出标签占位符 */
  maxTagPlaceholder?: (omittedValues: any[]) => any
  /** 未找到内容 */
  notFoundContent?: any
  /** 自定义下拉菜单 */
  dropdownRender?: (menu: any) => any
  /** 下拉菜单宽度跟随选择器 */
  dropdownMatchSelectWidth?: boolean
  /** 下拉菜单样式 */
  dropdownStyle?: any
  /** 下拉菜单类名 */
  dropdownClassName?: string
  /** 下拉菜单挂载容器 */
  getPopupContainer?: (trigger: HTMLElement) => HTMLElement
  /** 虚拟滚动 */
  virtual?: boolean
  /** 列表高度 */
  listHeight?: number
  /** 列表项高度 */
  listItemHeight?: number
}>()

const emit = defineEmits<{
  'update:modelValue': [value: any]
  change: [value: any]
  search: [value: string]
  focus: [event: FocusEvent]
  blur: [event: FocusEvent]
  dropdownVisibleChange: [visible: boolean]
}>()

function handleUpdate(value: any) {
  emit('update:modelValue', value)
}

function handleChange(value: any) {
  emit('change', value)
}

function handleSearch(value: string) {
  emit('search', value)
}

function handleFocus(event: FocusEvent) {
  emit('focus', event)
}

function handleBlur(event: FocusEvent) {
  emit('blur', event)
}

function handleDropdownVisibleChange(visible: boolean) {
  emit('dropdownVisibleChange', visible)
}
</script>