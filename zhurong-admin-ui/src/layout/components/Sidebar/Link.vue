<template>
  <component :is="type" v-bind="linkProps(to)">
    <slot />
  </component>
</template>

<script>
import { isExternal } from '@/utils/validate'

// 需要通过浏览器新标签页打开的站内全屏页面
const NEW_TAB_PATHS = ['/hospital/screen']

export default {
  props: {
    to: {
      type: [String, Object],
      required: true
    }
  },
  computed: {
    linkPath() {
      return typeof this.to === 'string' ? this.to : (this.to.path || '')
    },
    isExternal() {
      return isExternal(this.to)
    },
    isNewTab() {
      return NEW_TAB_PATHS.indexOf(this.linkPath) !== -1
    },
    type() {
      if (this.isExternal || this.isNewTab) {
        return 'a'
      }
      return 'router-link'
    }
  },
  methods: {
    linkProps(to) {
      if (this.isExternal) {
        return {
          href: to,
          target: '_blank',
          rel: 'noopener'
        }
      }
      // 站内全屏页面：用普通 a 链接在新标签页整页打开
      if (this.isNewTab) {
        return {
          href: this.linkPath,
          target: '_blank',
          rel: 'noopener'
        }
      }
      return {
        to: to
      }
    }
  }
}
</script>
