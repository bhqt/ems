<template>
  <BaseForm
    ref="formRef"
    :model="formModel"
    :rules="mergedRules"
    :layout="layout"
    :label-col="labelCol"
    :wrapper-col="wrapperCol"
    :size="size"
    @finish="handleFinish"
    @values-change="handleValuesChange"
  >
    <template v-for="field in fields" :key="field.name">
      <a-form-item
        v-if="field.type !== 'hidden'"
        :label="field.label"
        :name="field.name"
        :rules="field.rules"
        :required="field.required"
        :label-col="field.labelCol"
        :wrapper-col="field.wrapperCol"
        :colon="field.colon"
        :label-align="field.labelAlign"
        :wrapper-align="field.wrapperAlign"
        :extra="field.extra"
        :help="field.help"
        :has-feedback="field.hasFeedback"
        :validate-status="field.validateStatus"
        :validate-first="field.validateFirst"
      >
        <slot :name="field.name">
          <RenderField :field="field" :form-data="formModel" />
        </slot>
      </a-form-item>
    </template>

    <template v-for="field in fields" :key="field.name">
      <a-form-item v-if="field.type === 'hidden'" :name="field.name">
        <a-input type="hidden" v-model:value="formModel[field.name]" />
      </a-form-item>
    </template>

    <div v-if="showSubmit" class="pro-form-actions">
      <slot name="actions">
        <BaseButton
          type="primary"
          html-type="submit"
          :loading="submitting"
        >
          {{ t('common.submit') }}
        </BaseButton>
        <BaseButton
          v-if="showReset"
          type="default"
          html-type="button"
          @click="resetForm"
        >
          {{ t('common.reset') }}
        </BaseButton>
      </slot>
    </div>
  </BaseForm>
</template>

<script setup lang="ts>
import { ref, computed, reactive, watch, nextTick } from 'vue'
import { useI18n } from 'vue-i18n'
import BaseForm from '@/components/common/BaseForm.vue'
import BaseButton from '@/components/common/BaseButton.vue'
import RenderField from './RenderField.vue'

const props = defineProps<{
  /** 表单 Schema */
  schemas?: any[]
  /** 初始值 */
  initialValues?: Record<string, any>
  /** 布局 */
  layout?: 'horizontal' | 'vertical' | 'inline'
  /** 标签列布局 */
  labelCol?: any
  /** 控件列布局 */
  wrapperCol?: any
  /** 尺寸 */
  size?: 'large' | 'middle' | 'small'
  /** 显示提交按钮 */
  showSubmit?: boolean
  /** 显示重置按钮 */
  showReset?: boolean
  /** 提交函数 */
  submit?: (values: any) => Promise<any>
  /** 重置回调 */
  onReset?: () => void
}>()

const emit = defineEmits<{
  finish: [values: any]
  valuesChange: [changedValues: any, allValues: any]
}>()

const { t } = useI18n()

const formRef = ref()
const formModel = reactive<Record<string, any>>({})
const submitting = ref(false)

const fields = computed(() => {
  return (props.schemas || []).map((schema: any) => ({
    ...schema,
    name: schema.field || schema.name,
    label: schema.label || schema.title,
    type: schema.component || schema.type,
    componentProps: schema.componentProps || schema.props || {},
    rules: schema.rules || schema.validate,
    required: schema.required || false,
    hidden: schema.hidden || false,
    display: schema.display,
    depends: schema.depends,
  }))
})

const mergedRules = computed(() => {
  const rules: Record<string, any[]> = {}
  fields.value.forEach(field => {
    if (field.rules) {
      rules[field.name] = Array.isArray(field.rules) ? field.rules : [field.rules]
    }
    if (field.required) {
      rules[field.name] = rules[field.name] || []
      rules[field.name].push({ required: true, message: `${field.label}不能为空`, trigger: 'blur' })
    }
  })
  return rules
})

watch(
  () => props.initialValues,
  (newValues) => {
    if (newValues) {
      Object.assign(formModel, newValues)
    }
  },
  { immediate: true, deep: true }
)

function handleFinish(values: any) {
  submitting.value = true
  try {
    if (props.submit) {
      return props.submit(values)
    }
    emit('finish', values)
  } finally {
    submitting.value = false
  }
}

function handleValuesChange(changedValues: any, allValues: any) {
  emit('valuesChange', changedValues, allValues)
}

function resetForm() {
  formRef.value?.resetFields()
  if (props.onReset) {
    props.onReset()
  }
  if (props.initialValues) {
    Object.assign(formModel, props.initialValues)
  }
}

defineExpose({
  formRef,
  formModel,
  validate: (names?: string[]) => formRef.value?.validate(names),
  validateFields: (names?: string[]) => formRef.value?.validateFields(names),
  resetFields: (names?: string[]) => formRef.value?.resetFields(names),
  setFieldsValue: (values: Record<string, any>) => {
    Object.assign(formModel, values)
    formRef.value?.setFieldsValue(values)
  },
  getFieldsValue: (names?: string[]) => formRef.value?.getFieldsValue(names),
  scrollToField: (name: string) => formRef.value?.scrollToField(name),
})
</script>

<style scoped>
.pro-form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  padding-top: 24px;
  margin-top: 24px;
  border-top: 1px solid var(--zhurong-color-border-secondary);
}
</style>