<template>
  <a-layout class="blank-layout">
    <router-view v-slot="{ Component }">
      <transition name="fade" mode="out-in">
        <component :is="Component" />
      </transition>
    </router-view>
  </a-layout>
</template>

<script setup lang="ts">
import { useAppStore } from '@/stores/modules/app'
import { onMounted, onUnmounted } from 'vue'

const appStore = useAppStore()

onMounted(() => {
  appStore.setLayout('blank')
})

onUnmounted(() => {
  appStore.setLayout('default')
})
</script>

<style scoped>
.blank-layout {
  min-height: 100vh;
  background: var(--zhurong-color-bg-layout);
}
</style>