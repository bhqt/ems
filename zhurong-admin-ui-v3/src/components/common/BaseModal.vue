<template>
  <a-modal
    v-model:open="open"
    :title="title"
    :width="width"
    :fullscreen="fullscreen"
    :centered="centered"
    :closable="closable"
    :close-icon="closeIcon"
    :mask="mask"
    :mask-closable="maskClosable"
    :keyboard="keyboard"
    :destroy-on-close="destroyOnClose"
    :ok-text="okText"
    :cancel-text="cancelText"
    :ok-button-props="okButtonProps"
    :cancel-button-props="cancelButtonProps"
    :footer="footer"
    :confirm-loading="confirmLoading"
    :modal-render="modalRender"
    :zoom="zoom"
    :wrap-class-name="wrapClassName"
    :z-index="zIndex"
    :body-style="bodyStyle"
    :mask-style="maskStyle"
    @ok="handleOk"
    @cancel="handleCancel"
    @after-open="handleAfterOpen"
    @after-close="handleAfterClose"
  >
    <slot />
  </a-modal>
</template>

<script setup lang="ts">
import type { ModalProps, ModalFuncProps } from 'ant-design-vue'

const props = defineProps<ModalProps & ModalFuncProps & {
  /** 是否打开 */
  open?: boolean
  /** 标题 */
  title?: string
  /** 宽度 */
  width?: string | number
  /** 全屏 */
  fullscreen?: boolean
  /** 居中 */
  centered?: boolean
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
  /** 确认按钮文字 */
  okText?: string
  /** 取消按钮文字 */
  cancelText?: string
  /** 确认按钮属性 */
  okButtonProps?: any
  /** 取消按钮属性 */
  cancelButtonProps?: any
  /** 底部内容 */
  footer?: any
  /** 确认加载状态 */
  confirmLoading?: boolean
  /** 自定义渲染 */
  modalRender?: (originNode: any) => any
  /** 缩放动画 */
  zoom?: boolean
  /** 包装类名 */
  wrapClassName?: string
  /** 层级 */
  zIndex?: number
  /** body样式 */
  bodyStyle?: any
  /** 遮罩样式 */
  maskStyle?: any
}>()

const emit = defineEmits<{
  'update:open': [open: boolean]
  ok: [event: any]
  cancel: [event: any]
  afterOpen: []
  afterClose: []
}>()

function handleOk(event: any) {
  emit('ok', event)
  emit('update:open', false)
}

function handleCancel(event: any) {
  emit('cancel', event)
  emit('update:open', false)
}

function handleAfterOpen() {
  emit('afterOpen')
}

function handleAfterClose() {
  emit('afterClose')
}
</script>