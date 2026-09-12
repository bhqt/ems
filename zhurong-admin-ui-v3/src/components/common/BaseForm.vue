<template>
  <a-form
    ref="formRef"
    :model="model"
    :rules="rules"
    :layout="layout"
    :label-col="labelCol"
    :wrapper-col="wrapperCol"
    :colon="colon"
    :label-wrap="labelWrap"
    :size="size"
    :hide-required-mark="hideRequiredMark"
    @finish="handleFinish"
    @finish-failed="handleFinishFailed"
    @values-change="handleValuesChange"
  >
    <slot />
  </a-form>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { FormInstance, FormProps, FormRules } from 'ant-design-vue'

const props = defineProps<FormProps & {
  /** 表单数据模型 */
  model?: Record<string, any>
  /** 校验规则 */
  rules?: FormRules
  /** 布局 */
  layout?: 'horizontal' | 'vertical' | 'inline'
  /** 标签列布局 */
  labelCol?: FormProps['labelCol']
  /** 控件列布局 */
  wrapperCol?: FormProps['wrapperCol']
  /** 是否显示冒号 */
  colon?: boolean
  /** 标签换行 */
  labelWrap?: boolean
  /** 尺寸 */
  size?: 'large' | 'middle' | 'small'
  /** 隐藏必填标记 */
  hideRequiredMark?: boolean
}>()

const emit = defineEmits<{
  finish: [values: Record<string, any>]
  finishFailed: [errorInfo: any]
  valuesChange: [changedValues: any, allValues: any]
}>()

const formRef = ref<FormInstance>()

function handleFinish(values: Record<string, any>) {
  emit('finish', values)
}

function handleFinishFailed(errorInfo: any) {
  emit('finishFailed', errorInfo)
}

function handleValuesChange(changedValues: any, allValues: any) {
  emit('valuesChange', changedValues, allValues)
}

// 暴露方法给父组件
defineExpose({
  formRef,
  validate: (names?: string[]) => formRef.value?.validate(names),
  validateFields: (names?: string[]) => formRef.value?.validateFields(names),
  resetFields: (names?: string[]) => formRef.value?.resetFields(names),
  clearValidate: (names?: string[]) => formRef.value?.clearValidate(names),
  getFieldsValue: (names?: string[]) => formRef.value?.getFieldsValue(names),
  setFieldsValue: (values: Record<string, any>) => formRef.value?.setFieldsValue(values),
  setFieldValue: (name: string, value: any) => formRef.value?.setFieldValue(name, value),
  getFieldValue: (name: string) => formRef.value?.getFieldValue(name),
  getFieldError: (name: string) => formRef.value?.getFieldError(name),
  isFieldTouched: (name: string) => formRef.value?.isFieldTouched(name),
  isFieldValidating: (name: string) => formRef.value?.isFieldValidating(name),
  scrollToField: (name: string) => formRef.value?.scrollToField(name),
})
</script>