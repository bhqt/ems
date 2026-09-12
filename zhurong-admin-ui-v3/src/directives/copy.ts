import type { Directive, DirectiveBinding } from 'vue'
import { message } from 'ant-design-vue'

const copyDirective: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    const value = binding.value || binding.arg || el.innerText

    el.style.cursor = 'copy'
    el.title = '点击复制'

    el.addEventListener('click', handleClick)

    function handleClick() {
      copyToClipboard(value)
    }

    el._copyHandler = handleClick
  },

  updated(el: HTMLElement, binding: DirectiveBinding) {
    const value = binding.value || binding.arg || el.innerText
    // 可以在这里更新存储的值
  },

  unmounted(el: HTMLElement) {
    if (el._copyHandler) {
      el.removeEventListener('click', el._copyHandler)
      delete el._copyHandler
    }
  },
}

async function copyToClipboard(text: string) {
  try {
    await navigator.clipboard.writeText(text)
    message.success('复制成功')
  } catch (error) {
    console.error('Copy failed:', error)
    message.error('复制失败')
  }
}

export default copyDirective