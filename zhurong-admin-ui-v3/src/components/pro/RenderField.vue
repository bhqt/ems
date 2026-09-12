<template>
  <component
    :is="componentMap[field.type] || 'a-input'"
    v-model:value="modelValue"
    v-bind="mergedProps"
    :disabled="field.disabled"
    :readonly="field.readonly"
    @update:value="handleUpdate"
    @change="handleChange"
  />
</template>

<script setup lang="ts>
import { computed } from 'vue'
import {
  BaseInput,
  BaseSelect,
  BaseTree,
  BaseCascader,
  BaseUpload,
  BaseDatePicker,
  BaseTimePicker,
  BaseRadio,
  BaseCheckbox,
  BaseSwitch,
  BaseSlider,
  BaseRate,
  BaseInputNumber,
  BaseTextarea,
  BaseColorPicker,
} from '@/components/common'

const props = defineProps<{
  field: any
  formData: Record<string, any>
}>()

const { field, formData } = props

const componentMap = {
  input: BaseInput,
  text: BaseInput,
  password: BaseInput,
  textarea: BaseTextarea,
  number: BaseInputNumber,
  select: BaseSelect,
  treeSelect: BaseTree,
  cascader: BaseCascader,
  upload: BaseUpload,
  date: BaseDatePicker,
  time: BaseTimePicker,
  datetime: BaseDatePicker,
  radio: BaseRadio,
  checkbox: BaseCheckbox,
  switch: BaseSwitch,
  slider: BaseSlider,
  rate: BaseRate,
  color: BaseColorPicker,
  hidden: 'input',
}

const modelValue = computed({
  get() {
    return formData[field.name]
  },
  set(value) {
    formData[field.name] = value
  },
})

const mergedProps = computed(() => {
  const baseProps = {
    placeholder: field.placeholder || `请输入${field.label}`,
    style: { width: '100%' },
    ...field.componentProps,
  }

  // 特定组件属性处理
  switch (field.type) {
    case 'select':
    case 'treeSelect':
    case 'cascader':
      return {
        ...baseProps,
        options: field.options || field.dataSource,
        allowClear: field.allowClear ?? true,
        showSearch: field.showSearch ?? true,
      }
    case 'date':
    case 'datetime':
      return {
        ...baseProps,
        format: field.format || (field.type === 'datetime' ? 'YYYY-MM-DD HH:mm:ss' : 'YYYY-MM-DD'),
        valueFormat: field.valueFormat || 'YYYY-MM-DD',
        picker: field.type === 'datetime' ? 'datetime' : 'date',
        allowClear: field.allowClear ?? true,
      }
    case 'time':
      return {
        ...baseProps,
        format: field.format || 'HH:mm:ss',
        valueFormat: field.valueFormat || 'HH:mm:ss',
        allowClear: field.allowClear ?? true,
      }
    case 'radio':
    case 'checkbox':
      return {
        ...baseProps,
        options: field.options || [],
        optionType: field.optionType || 'default',
        buttonStyle: field.buttonStyle || 'solid',
      }
    case 'switch':
      return {
        ...baseProps,
        checkedChildren: field.checkedChildren || '开',
        unCheckedChildren: field.unCheckedChildren || '关',
      }
    case 'upload':
      return {
        ...baseProps,
        action: field.action || '/api/upload',
        listType: field.listType || 'picture-card',
        showUploadList: field.showUploadList ?? true,
        multiple: field.multiple ?? false,
        accept: field.accept,
      }
    case 'slider':
      return {
        ...baseProps,
        min: field.min ?? 0,
        max: field.max ?? 100,
        step: field.step ?? 1,
        marks: field.marks,
        dots: field.dots ?? false,
      }
    case 'rate':
      return {
        ...baseProps,
        count: field.count ?? 5,
        allowHalf: field.allowHalf ?? false,
      }
    case 'inputNumber':
      return {
        ...baseProps,
        min: field.min,
        max: field.max,
        step: field.step ?? 1,
        precision: field.precision,
        formatter: field.formatter,
        parser: field.parser,
      }
    default:
      return baseProps
  }
})

function handleUpdate(value: any) {
  formData[field.name] = value
}

function handleChange(value: any) {
  formData[field.name] = value
}
</script>