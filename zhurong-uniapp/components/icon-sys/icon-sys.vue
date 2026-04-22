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
  name: 'iconMqtt',
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
    // 计算完整的MQTT图标URL，包含mqtt路径前缀
    iconUrl() {
      return config.iconReqUrl + 'sys/' + this.name
    }
  }
}
</script>

<style scoped>
/* 可以在这里添加MQTT图标的特定样式 */
</style>
