<template>
  <component :is="type" v-bind="linkProps(to)">
    <slot />
  </component>
</template>

<script>
import { isExternal } from '@/utils/validate'

export default {
  props: {
    to: {
      type: [String, Object],
      required: true
    }
  },
  computed: {
    isExternal() {
      return isExternal(this.to)
    },
    type() {
      if (this.isExternal) {
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
      const path = typeof to === 'string' ? to : (to.path || '')
      if (path.indexOf('screen') !== -1) {
        return {
          href: 'javascript:void(0)',
          onClick: (e) => {
            e.preventDefault()
            window.open('/hospital/screen', '_blank')
          }
        }
      }
      return {
        to: to
      }
    }
  }
}
</script>
