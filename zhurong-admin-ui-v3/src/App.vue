<template>
  <a-config-provider :locale="antdLocale">
    <router-view />
  </a-config-provider>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useAppStore } from '@/stores/modules/app'
import zhCN from 'ant-design-vue/es/locale/zh_CN'
import enUS from 'ant-design-vue/es/locale/en_US'

const appStore = useAppStore()

const antdLocale = computed(() => (appStore.locale === 'zh-CN' ? zhCN : enUS))
</script>

<style>
#app {
  width: 100%;
  height: 100%;
}
</style>