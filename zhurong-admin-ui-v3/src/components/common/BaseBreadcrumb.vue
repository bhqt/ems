<template>
  <a-breadcrumb
    v-bind="$attrs"
    :items="items"
    :separator="separator"
    :routes="routes"
    @jump="handleJump"
  >
    <template #separator>
      <slot name="separator"><span>/</span></slot>
    </template>
    <template #item="slotProps">
      <router-link
        v-if="slotProps.item.path && slotProps.item.path !== '/dashboard/workbench'"
        :to="slotProps.item.path"
        class="breadcrumb-link"
      >
        {{ slotProps.item.title }}
      </router-link>
      <span v-else class="breadcrumb-link">{{ slotProps.item.title }}</span>
    </template>
  </a-breadcrumb>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useI18n } from 'vue-i18n'

const router = useRouter()
const route = useRoute()
const { t } = useI18n()

const props = defineProps<{
  /** 面包屑项 */
  items?: Array<{ path?: string; title: string }>
  /** 分隔符 */
  separator?: any
  /** 路由数组 */
  routes?: Array<{ path: string; title: string }>
}>()

const emit = defineEmits<{
  jump: [path: string]
}>()

const breadcrumbItems = computed(() => {
  if (props.items && props.items.length > 0) {
    return props.items
  }
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

function handleJump(path: string) {
  emit('jump', path)
}
</script>