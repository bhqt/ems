<template>
  <div class="loading-wrapper" :class="{ 'full-screen': fullScreen }">
    <div v-if="loading" class="loading-mask">
      <a-spin
        :size="size"
        :tip="tip"
        :delay="delay"
        :indicator="indicator"
        :spinning="true"
        class="loading-spin"
      >
        <slot name="indicator" />
      </a-spin>
    </div>
    <div class="loading-content">
      <slot />
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps<{
  /** 加载状态 */
  loading?: boolean
  /** 全屏加载 */
  fullScreen?: boolean
  /** 大小 */
  size?: 'default' | 'small' | 'large'
  /** 提示文本 */
  tip?: string
  /** 延迟显示(ms) */
  delay?: number
  /** 自定义加载图标 */
  indicator?: any
}>()

const emit = defineEmits<{}>()

const containerStyle = computed(() => ({
  position: props.fullScreen ? 'fixed' : 'relative',
  top: props.fullScreen ? 0 : undefined,
  left: props.fullScreen ? 0 : undefined,
  right: props.fullScreen ? 0 : undefined,
  bottom: props.fullScreen ? 0 : undefined,
  zIndex: props.fullScreen ? 9999 : undefined,
}))
</script>

<style scoped>
.loading-wrapper {
  position: relative;
  width: 100%;
}

.loading-wrapper.full-screen {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

.loading-mask {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  backdrop-filter: blur(2px);
}

.dark .loading-mask {
  background: rgba(0, 0, 0, 0.7);
}

.loading-content {
  width: 100%;
}
</style>