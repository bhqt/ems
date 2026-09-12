<template>
  <div class="column-setting">
    <div class="column-setting-header">
      <span>{{ t('common.columnSetting') }}</span>
      <div class="column-setting-actions">
        <BaseButton type="text" size="small" @click="selectAll">
          {{ t('common.selectAll') }}
        </BaseButton>
        <BaseButton type="text" size="small" @click="deselectAll">
          {{ t('common.deselectAll') }}
        </BaseButton>
      </div>
    </div>
    <div class="column-setting-list">
      <label
        v-for="column in columns"
        :key="column.dataIndex || column.key"
        class="column-setting-item"
      >
        <a-checkbox
          :checked="checkedKeys.includes(String(column.dataIndex || column.key))"
          :disabled="column.fixed || column.hideInColumnSetting"
          @change="(e) => toggleColumn(column, e.target.checked)"
        >
          <span class="column-name">{{ column.title || column.dataIndex }}</span>
          <span v-if="column.fixed" class="column-fixed-badge">{{ t('common.fixed') }}</span>
        </a-checkbox>
      </label>
    </div>
  </div>
</template>

<script setup lang="ts>
import { computed } from 'vue'
import { useI18n } from 'vue-i18n'
import BaseButton from '@/components/common/BaseButton.vue'

const props = defineProps<{
  columns: any[]
  checkedKeys: string[]
}>()

const emit = defineEmits<{
  'update:checkedKeys': [keys: string[]]
}>()

const { t } = useI18n()

const validColumns = computed(() => props.columns.filter((col: any) => col.dataIndex && col.hideInColumnSetting !== true))

function toggleColumn(column: any, checked: boolean) {
  const key = String(column.dataIndex || column.key)
  const index = props.checkedKeys.indexOf(key)
  let newKeys = [...props.checkedKeys]
  
  if (checked && index === -1) {
    newKeys.push(key)
  } else if (!checked && index > -1) {
    newKeys.splice(index, 1)
  }
  
  emit('update:checkedKeys', newKeys)
}

function selectAll() {
  const keys = validColumns.value
    .filter((col: any) => !col.fixed)
    .map((col: any) => String(col.dataIndex || col.key))
  emit('update:checkedKeys', keys)
}

function deselectAll() {
  const fixedKeys = props.columns
    .filter((col: any) => col.fixed)
    .map((col: any) => String(col.dataIndex || col.key))
  emit('update:checkedKeys', fixedKeys)
}
</script>

<style scoped>
.column-setting {
  padding: 8px 0;
  max-height: 400px;
  overflow-y: auto;
}

.column-setting-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-bottom: 12px;
  border-bottom: 1px solid var(--zhurong-color-border-secondary);
  margin-bottom: 12px;
}

.column-setting-actions {
  display: flex;
  gap: 8px;
}

.column-setting-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.column-setting-item {
  display: flex;
  align-items: center;
  padding: 8px 12px;
  border-radius: 6px;
  transition: background 0.2s;
}

.column-setting-item:hover {
  background: var(--zhurong-color-bg-hover);
}

.column-name {
  flex: 1;
  margin-left: 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.column-fixed-badge {
  margin-left: 8px;
  padding: 2px 6px;
  font-size: 10px;
  background: var(--zhurong-color-primary-bg);
  color: var(--zhurong-color-primary);
  border-radius: 4px;
}
</style>