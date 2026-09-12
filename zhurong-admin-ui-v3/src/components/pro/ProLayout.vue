<template>
  <div class="pro-layout">
    <div class="pro-layout-header" v-if="title || actions || breadcrumb">
      <div class="pro-layout-header-left">
        <PageHeader
          v-if="title"
          :title="title"
          :subtitle="subtitle"
          :breadcrumb="breadcrumb"
        />
      </div>
      <div class="pro-layout-header-right" v-if="actions && actions.length > 0">
        <slot name="actions">
          <component
            v-for="action in actions"
            :key="action.key"
            :is="action.component"
            v-bind="action.props"
            @click="action.onClick"
            class="pro-layout-action"
          />
        </slot>
      </div>
    </div>

    <div class="pro-layout-content">
      <slot />
    </div>

    <div class="pro-layout-footer" v-if="footer">
      <slot name="footer">
        {{ footer }}
      </slot>
    </div>
  </div>
</template>

<script setup lang="ts">
const props = defineProps<{
  /** 标题 */
  title?: string
  /** 副标题 */
  subtitle?: string
  /** 面包屑 */
  breadcrumb?: any
  /** 操作按钮 */
  actions?: Array<{
    key: string
    component: string
    props?: Record<string, any>
    onClick?: () => void
  }>
  /** 底部内容 */
  footer?: string
}>()
</script>

<style scoped>
.pro-layout {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 24px;
  background: var(--zhurong-color-bg-layout);
}

.pro-layout-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 1px solid var(--zhurong-color-border-secondary);
  flex-wrap: wrap;
  gap: 16px;
}

.pro-layout-header-left {
  flex: 1;
  min-width: 0;
}

.pro-layout-header-right {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-shrink: 0;
}

.pro-layout-action {
  height: 36px;
}

.pro-layout-content {
  flex: 1;
  min-height: 0;
  overflow: auto;
}

.pro-layout-footer {
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid var(--zhurong-color-border-secondary);
  color: var(--zhurong-color-text-secondary);
  text-align: center;
}

@media (max-width: 768px) {
  .pro-layout {
    padding: 16px;
  }

  .pro-layout-header {
    flex-direction: column;
    align-items: flex-start;
  }

  .pro-layout-header-right {
    width: 100%;
    justify-content: flex-end;
  }
}
</style>