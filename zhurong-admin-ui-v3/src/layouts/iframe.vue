<template>
  <a-layout class="iframe-layout">
    <a-layout-header class="iframe-header">
      <TopNav />
    </a-layout-header>
    <a-layout-content class="iframe-content">
      <iframe
        v-if="src"
        :src="src"
        frameborder="0"
        class="iframe"
        @load="onLoad"
      />
      <div v-else class="iframe-loading">
        <a-spin size="large" tip="加载中..." />
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script setup lang="ts">
import { ref, computed, watch, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import TopNav from '@/components/layout/components/TopNav.vue'

const route = useRoute()
const src = ref<string>('')

const iframeSrc = computed(() => {
  const { query } = route
  return (query.src as string) || ''
})

function onLoad() {
  // iframe 加载完成
}

watch(iframeSrc, (newSrc) => {
  src.value = newSrc
}, { immediate: true })
</script>

<style scoped>
.iframe-layout {
  height: 100vh;
  overflow: hidden;
}

.iframe-header {
  height: 60px;
  background: var(--zhurong-color-bg-container);
  border-bottom: 1px solid var(--zhurong-color-border);
  z-index: var(--zhurong-z-index-sticky);
}

.iframe-content {
  height: calc(100vh - 60px);
  overflow: hidden;
  position: relative;
}

.iframe {
  width: 100%;
  height: 100%;
  border: none;
  background: #fff;
}

.iframe-loading {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--zhurong-color-bg-layout);
}
</style>