<template>
  <ProCard :title="title" :extra="extra" :loading="loading" :bordered="bordered" :size="size">
    <div class="pro-detail">
      <!-- 描述列表 -->
      <BaseDescriptions
        v-if="descriptions.length > 0"
        :column="descriptionsColumn"
        :size="descriptionsSize"
        :layout="descriptionsLayout"
        :bordered="descriptionsBordered"
      >
        <template v-for="item in descriptions" :key="item.key">
          <a-descriptions-item :label="item.label" :span="item.span">
            <slot :name="item.key">
              <template v-if="item.render">
                <component :is="item.render" :value="record[item.key]" :record="record" />
              </template>
              <template v-else>
                {{ formatValue(record[item.key], item.format) }}
              </template>
            </slot>
          </a-descriptions-item>
        </template>
      </BaseDescriptions>

      <!-- 标签页 -->
      <BaseTabs
        v-if="tabs.length > 0"
        :items="tabItems"
        :type="tabsType"
        :size="tabsSize"
      >
        <template v-for="tab in tabs" :key="tab.key">
          <template #tab-key>
            {{ tab.tab }}
          </template>
          <slot :name="tab.key">
            <component
              v-if="tab.component"
              :is="tab.component"
              :record="record"
              :data="tab.data"
            />
            <template v-else>
              {{ tab.content || '暂无内容' }}
            </template>
          </slot>
        </template>
      </BaseTabs>

      <!-- 空状态 -->
      <div v-if="descriptions.length === 0 && tabs.length === 0 && !loading" class="pro-detail-empty">
        <a-empty description="暂无详情数据" />
      </div>
    </div>
  </ProCard>
</template>

<script setup lang="ts>
import { computed } from 'vue'
import ProCard from './ProCard.vue'
import BaseDescriptions from '@/components/common/BaseDescriptions.vue'
import BaseTabs from '@/components/common/BaseTabs.vue'

const props = defineProps<{
  /** 记录数据 */
  record?: Record<string, any>
  /** 标题 */
  title?: string
  /** 额外操作 */
  extra?: any
  /** 描述列表 */
  descriptions?: Array<{
    key: string
    label: string
    span?: number
    format?: 'date' | 'datetime' | 'time' | 'currency' | 'number' | 'boolean' | ((value: any) => string)
    render?: string
  }>
  /** 描述列数 */
  descriptionsColumn?: number
  /** 描述尺寸 */
  descriptionsSize?: 'default' | 'middle' | 'small'
  /** 描述布局 */
  descriptionsLayout?: 'horizontal' | 'vertical'
  /** 描述边框 */
  descriptionsBordered?: boolean
  /** 标签页 */
  tabs?: Array<{
    key: string
    tab: string
    component?: string
    data?: any
    content?: string
  }>
  /** 标签页类型 */
  tabsType?: 'line' | 'card' | 'editable-card'
  /** 标签页尺寸 */
  tabsSize?: 'large' | 'middle' | 'small'
  /** 加载状态 */
  loading?: boolean
  /** 是否有边框 */
  bordered?: boolean
  /** 悬浮浮起 */
  hoverable?: boolean
  /** 尺寸 */
  size?: 'default' | 'small'
}>()

function formatValue(value: any, format?: any): string {
  if (value === undefined || value === null) return '—'
  
  if (typeof format === 'function') {
    return format(value)
  }
  
  switch (format) {
    case 'date':
      return value ? new Date(value).toLocaleDateString() : '—'
    case 'datetime':
      return value ? new Date(value).toLocaleString() : '—'
    case 'time':
      return value ? new Date(value).toLocaleTimeString() : '—'
    case 'currency':
      return typeof value === 'number' ? `¥${value.toLocaleString()}` : value
    case 'number':
      return typeof value === 'number' ? value.toLocaleString() : value
    case 'boolean':
      return value ? '是' : '否'
    default:
      return String(value)
  }
}
</script>

<style scoped>
.pro-detail {
  min-height: 200px;
}

.pro-detail-empty {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 200px;
}
</style>