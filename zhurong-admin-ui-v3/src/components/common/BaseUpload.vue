<template>
  <a-upload
    v-bind="$attrs"
    :action="action"
    :method="method"
    :directory="directory"
    :data="data"
    :headers="headers"
    :show-upload-list="showUploadList"
    :list-type="listType"
    :multiple="multiple"
    :accept="accept"
    :before-upload="beforeUpload"
    :custom-request="customRequest"
    :with-credentials="withCredentials"
    :open-file-dialog-on-click="openFileDialogOnClick"
    :disabled="disabled"
    :preview-file="previewFile"
    :download="download"
    :icon-render="iconRender"
    :is-image-url="isImageUrl"
    :progress="progress"
    :max-count="maxCount"
    @change="handleChange"
    @preview="handlePreview"
    @remove="handleRemove"
  >
    <slot />
  </a-upload>
</template>

<script setup lang="ts">
import type { UploadProps, UploadFile, UploadChangeParam } from 'ant-design-vue'

const props = defineProps<UploadProps & {
  /** 上传地址 */
  action?: string
  /** 上传方法 */
  method?: 'post' | 'put' | 'patch'
  /** 上传文件夹 */
  directory?: boolean
  /** 额外数据 */
  data?: Record<string, any> | ((file: UploadFile) => Record<string, any>)
  /** 请求头 */
  headers?: Record<string, string>
  /** 显示上传列表 */
  showUploadList?: boolean | { showPreviewIcon?: boolean; showRemoveIcon?: boolean; showDownloadIcon?: boolean; removeIcon?: any; downloadIcon?: any }
  /** 列表类型 */
  listType?: 'text' | 'picture' | 'picture-card'
  /** 多选 */
  multiple?: boolean
  /** 接受文件类型 */
  accept?: string
  /** 上传前钩子 */
  beforeUpload?: (file: File, fileList: UploadFile[]) => boolean | Promise<boolean>
  /** 自定义请求 */
  customRequest?: (options: any) => void
  /** 携带凭证 */
  withCredentials?: boolean
  /** 点击打开文件对话框 */
  openFileDialogOnClick?: boolean
  /** 禁用 */
  disabled?: boolean
  /** 预览文件 */
  previewFile?: (file: File | Blob) => Promise<string>
  /** 下载文件 */
  download?: (file: UploadFile) => void
  /** 自定义图标 */
  iconRender?: (file: UploadFile, listType: string) => any
  /** 判断是否图片链接 */
  isImageUrl?: (url: string) => boolean
  /** 进度条 */
  progress?: (percent: number, file: UploadFile) => any
  /** 最大数量 */
  maxCount?: number
}>()

const emit = defineEmits<{
  change: [info: UploadChangeParam]
  preview: [file: UploadFile]
  remove: [file: UploadFile]
}>()

function handleChange(info: UploadChangeParam) {
  emit('change', info)
}

function handlePreview(file: UploadFile) {
  emit('preview', file)
}

function handleRemove(file: UploadFile) {
  emit('remove', file)
}
</script>