<template>
  <image
    :src="iconUrl"
    :class="className"
    :mode="mode"
    :lazy-load="lazyLoad"
  ></image>
</template>

<script>
import config from '@/config'

export default {
  name: 'iconCommon',
  props: {
    // 图标名称（不含文件扩展名）
   name: {
      type: String,
      required: true
    },
    // CSS类名
    className: {
      type: String,
      default: ''
    },
    // 图片显示模式
    mode: {
      type: String,
      default: 'aspectFill'
    },
    // 是否懒加载
    lazyLoad: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    // 计算完整的图标URL
    iconUrl() {
      return config.iconReqUrl + this.name
    }
  }
}
</script>

<style scoped>
/* 可以在这里添加通用的图标样式 */
</style>
