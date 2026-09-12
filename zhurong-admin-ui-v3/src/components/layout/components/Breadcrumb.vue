<template>
  <a-breadcrumb :items="breadcrumbItems" separator="/" class="breadcrumb">
    <template #item="item">
      <router-link
        v-if="item.path && item.path !== '/dashboard/workbench'"
        :to="item.path"
        class="breadcrumb-link"
      >
        {{ item.title }}
      </router-link>
      <span v-else class="breadcrumb-link">{{ item.title }}</span>
    </template>
  </a-breadcrumb>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useI18n } from 'vue-i18n'

const route = useRoute()
const { t } = useI18n()

const props = defineProps<{
  routes?: Array<{ path: string; title: string }>
}>()

const breadcrumbItems = computed(() => {
  if (props.routes && props.routes.length > 0) {
    return props.routes
  }

  return route.matched
    .filter(m => m.meta?.title && !m.meta?.hideBreadcrumb)
    .map(m => ({
      path: m.path,
      title: t(m.meta?.title as string || ''),
    }))
})
</script>

<style scoped>
.breadcrumb {
  font-size: var(--zhurong-font-size);
}

.breadcrumb-link {
  color: var(--zhurong-color-text-secondary);
  text-decoration: none;
  transition: color 0.2s;
}

.breadcrumb-link:hover {
  color: var(--zhurong-color-primary);
}

:deep(.ant-breadcrumb-separator) {
  color: var(--zhurong-color-text-tertiary);
  margin: 0 8px;
}

:deep(.ant-breadcrumb-last > span) {
  color: var(--zhurong-color-text);
  font-weight: 500;
}
</style>