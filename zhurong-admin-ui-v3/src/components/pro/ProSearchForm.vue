<template>
  <a-form
    ref="formRef"
    :model="formModel"
    :layout="layout"
    :label-col="labelCol"
    :wrapper-col="wrapperCol"
    :size="size"
    @values-change="handleValuesChange"
  >
    <a-row :gutter="16">
      <template v-for="field in visibleFields" :key="field.name">
        <a-col :span="field.span || 8" :key="field.name">
          <a-form-item
            :label="field.label"
            :name="field.name"
            :label-col="{ span: 6 }"
            :wrapper-col="{ span: 18 }"
            :colon="false"
          >
            <RenderField :field="field" :form-data="formModel" />
          </a-form-item>
        </a-col>
      </template>

      <!-- 折叠按钮 -->
      <a-col :span="4" v-if="collapsible && schemas.length > 8" :key="'collapse'">
        <div class="collapse-btn-wrapper">
          <BaseButton
            type="text"
            :icon="collapsed ? <DownOutlined /> : <UpOutlined />"
            @click="toggleCollapse"
          >
            {{ collapsed ? t('common.expand') : t('common.collapse') }}
          </BaseButton>
        </div>
      </a-col>

      <!-- 操作按钮 -->
      <a-col :span="4" :key="'actions'">
        <div class="search-actions">
          <BaseButton type="primary" html-type="submit" :loading="submitting">
            <SearchOutlined /> {{ t('common.search') }}
          </BaseButton>
          <BaseButton html-type="button" @click="resetForm">
            <ReloadOutlined /> {{ t('common.reset') }}
          </BaseButton>
        </div>
      </a-col>
    </a-row>
  </a-form>
</template>

<script setup lang="ts>
import { ref, computed, watch } from 'vue'
import { useI18n } from 'vue-i18n'
import {
  DownOutlined,
  UpOutlined,
  SearchOutlined,
  ReloadOutlined,
} from '@ant-design/icons-vue'
import BaseButton from '@/components/common/BaseButton.vue'
import RenderField from './RenderField.vue'

const props = defineProps<{
  /** 搜索表单配置 */
  schemas?: any[]
  /** 表单数据 */
  formData?: Record<string, any>
  /** 布局 */
  layout?: 'horizontal' | 'vertical' | 'inline'
  /** 标签列布局 */
  labelCol?: any
  /** 控件列布局 */
  wrapperCol?: any
  /** 尺寸 */
  size?: 'large' | 'middle' | 'small'
  /** 可折叠 */
  collapsible?: boolean
  /** 默认折叠 */
  defaultCollapsed?: boolean
}>()

const emit = defineEmits<{
  search: [params: Record<string, any>]
  reset: []
  collapse: [collapsed: boolean]
  valuesChange: [changedValues: any, allValues: any]
}>()

const { t } = useI18n()

const formRef = ref()
const formModel = reactive<Record<string, any>>({})
const collapsed = ref(props.defaultCollapsed ?? false)
const submitting = ref(false)

const fields = computed(() => (props.schemas || []).map((schema: any) => ({
  ...schema,
  name: schema.field || schema.name,
  label: schema.label || schema.title,
  type: schema.component || schema.type,
  componentProps: schema.componentProps || schema.props || {},
  required: schema.required || false,
  span: schema.span || 8,
})))

const visibleFields = computed(() => {
  if (!collapsed.value) return fields.value
  return fields.value.slice(0, 8)
})

watch(
  () => props.formData,
  (newData) => {
    if (newData) {
      Object.assign(formModel, newData)
    }
  },
  { immediate: true, deep: true }
)

function handleValuesChange(changedValues: any, allValues: any) {
  emit('valuesChange', changedValues, allValues)
}

function handleSubmit(e: Event) {
  e.preventDefault()
  submitting.value = true
  try {
    const params = { ...formModel }
    // 移除空值
    Object.keys(params).forEach(key => {
      if (params[key] === '' || params[key] === null || params[key] === undefined) {
        delete params[key]
      }
    })
    emit('search', params)
  } finally {
    submitting.value = false
  }
}

function resetForm() {
  formRef.value?.resetFields()
  Object.keys(formModel).forEach(key => {
    delete formModel[key]
  })
  emit('reset')
}

function toggleCollapse() {
  collapsed.value = !collapsed.value
  emit('collapse', collapsed.value)
}
</script>

<style scoped>
.search-actions {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
}

.collapse-btn-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}
</style>