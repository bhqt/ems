<template>
  <a-input
    v-model:value="modelValue"
    :placeholder="placeholder"
    :disabled="disabled"
    :readonly="readonly"
    :size="size"
    :max-length="maxLength"
    :show-count="showCount"
    :prefix="prefix"
    :suffix="suffix"
    :allow-clear="allowClear"
    :type="type"
    :rows="rows"
    :autosize="autosize"
    @update:value="handleUpdate"
    @press-enter="handleEnter"
    @change="handleChange"
    @focus="handleFocus"
    @blur="handleBlur"
  />
</template>

<script setup lang="ts">
import type { InputProps, TextAreaProps } from 'ant-design-vue'

const props = defineProps<InputProps & TextAreaProps & {
  modelValue?: string
  /** 占位符 */
  placeholder?: string
  /** 禁用状态 */
  disabled?: boolean
  /** 只读 */
  readonly?: boolean
  /** 尺寸 */
  size?: 'large' | 'middle' | 'small'
  /** 最大长度 */
  maxLength?: number
  /** 显示字数统计 */
  showCount?: boolean
  /** 前缀图标 */
  prefix?: any
  /** 后缀图标 */
  suffix?: any
  /** 允许清空 */
  allowClear?: boolean
  /** 输入类型 */
  type?: 'text' | 'password' | 'number' | 'tel' | 'email' | 'url'
  /** 文本域行数 */
  rows?: number
  /** 自动调整高度 */
  autosize?: boolean | { minRows: number; maxRows: number }
}>()

const emit = defineEmits<{
  'update:modelValue': [value: string]
  enter: [value: string]
  change: [value: string]
  focus: [event: FocusEvent]
  blur: [event: FocusEvent]
}>()

function handleUpdate(value: string) {
  emit('update:modelValue', value)
}

function handleEnter(value: string) {
  emit('enter', value)
}

function handleChange(value: string) {
  emit('change', value)
}

function handleFocus(event: FocusEvent) {
  emit('focus', event)
}

function handleBlur(event: FocusEvent) {
  emit('blur', event)
}
</script>