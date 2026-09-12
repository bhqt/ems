<template>
  <a-drawer
    v-model:open="open"
    :title="title"
    :placement="placement"
    :width="width"
    :height="height"
    :closable="closable"
    :close-icon="closeIcon"
    :mask="mask"
    :mask-closable="maskClosable"
    :keyboard="keyboard"
    :destroy-on-close="destroyOnClose"
    :header-style="headerStyle"
    :body-style="bodyStyle"
    :wrap-class-name="wrapClassName"
    :z-index="zIndex"
    @close="handleClose"
    @after-open="handleAfterOpen"
    @after-close="handleAfterClose"
  >
    <slot />
  </a-drawer>
</template>

<script setup lang="ts">
import type { DrawerProps } from 'ant-design-vue'

const props = defineProps<DrawerProps & {
  /** 是否打开 */
  open?: boolean
  /** 标题 */
  title?: string
  /** 位置 */
  placement?: 'left' | 'right' | 'top' | 'bottom'
  /** 宽度 */
  width?: string | number
  /** 高度 */
  height?: string | number
  /** 显示关闭按钮 */
  closable?: boolean
  /** 自定义关闭图标 */
  closeIcon?: any
  /** 显示遮罩 */
  mask?: boolean
  /** 点击遮罩关闭 */
  maskClosable?: boolean
  /** 键盘关闭 */
  keyboard?: boolean
  /** 关闭时销毁 */
  destroyOnClose?: boolean
  /** 头部样式 */
  headerStyle?: any
  /** 内容样式 */
  bodyStyle?: any
  /** 包装类名 */
  wrapClassName?: string
  /** 层级 */
  zIndex?: number
}>()

const emit = defineEmits<{
  'update:open': [open: boolean]
  close: [event: any]
  afterOpen: []
  afterClose: []
}>()

function handleClose(event: any) {
  emit('close', event)
  emit('update:open', false)
}

function handleAfterOpen() {
  emit('afterOpen')
}

function handleAfterClose() {
  emit('afterClose')
}
</script>