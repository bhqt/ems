import { ref } from 'vue'
import { message } from 'ant-design-vue'

export function useClipboard() {
  const copied = ref(false)
  const copiedText = ref('')

  async function copy(text: string): Promise<boolean> {
    try {
      await navigator.clipboard.writeText(text)
      copied.value = true
      copiedText.value = text
      message.success('复制成功')
      setTimeout(() => {
        copied.value = false
      }, 2000)
      return true
    } catch (error) {
      console.error('Copy failed:', error)
      message.error('复制失败')
      return false
    }
  }

  async function copyToClipboard(text: string): Promise<boolean> {
    return copy(text)
  }

  return {
    copied,
    copiedText,
    copy,
    copyToClipboard,
  }
}