<template>
  <div class="page-header">
    <div class="page-header-content">
      <h1 class="page-title">{{ title }}</h1>
      <p v-if="subtitle" class="page-subtitle">{{ subtitle }}</p>
    </div>
    <div v-if="actions && actions.length > 0" class="page-actions">
      <slot name="actions">
        <component
          v-for="action in actions"
          :key="action.key"
          :is="action.component"
          v-bind="action.props"
          @click="action.onClick"
          class="page-action-btn"
        />
      </slot>
    </div>
  </div>
</template>

<script setup lang="ts">
const props = defineProps<{
  title: string
  subtitle?: string
  actions?: Array<{
    key: string
    component: string
    props?: Record<string, any>
    onClick?: () => void
  }>
}>()
</script>

<style scoped>
.page-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 0;
  margin-bottom: 16px;
  border-bottom: 1px solid var(--zhurong-color-border-secondary);
  flex-wrap: wrap;
  gap: 16px;
}

.page-header-content {
  flex: 1;
  min-width: 0;
}

.page-title {
  font-size: 20px;
  font-weight: 600;
  color: var(--zhurong-color-text-heading);
  margin: 0 0 4px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.page-subtitle {
  font-size: var(--zhurong-font-size);
  color: var(--zhurong-color-text-secondary);
  margin: 0;
}

.page-actions {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-shrink: 0;
}

.page-action-btn {
  height: 36px;
}
</style>