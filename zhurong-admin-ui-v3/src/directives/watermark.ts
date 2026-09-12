import type { Directive, DirectiveBinding } from 'vue'

interface WatermarkOptions {
  content?: string
  width?: number
  height?: number
  font?: string
  color?: string
  opacity?: number
  rotate?: number
  zIndex?: number
  gapX?: number
  gapY?: number
}

const defaultOptions: Required<WatermarkOptions> = {
  content: 'Zhurong EMS',
  width: 120,
  height: 64,
  font: '14px PingFang SC, Microsoft YaHei, sans-serif',
  color: '#000000',
  opacity: 0.15,
  rotate: -20,
  zIndex: 999,
  gapX: 40,
  gapY: 40,
}

function createWatermarkCanvas(options: Required<WatermarkOptions>): HTMLCanvasElement {
  const canvas = document.createElement('canvas')
  canvas.width = options.width + options.gapX
  canvas.height = options.height + options.gapY
  const ctx = canvas.getContext('2d')!

  ctx.fillStyle = options.color
  ctx.globalAlpha = options.opacity
  ctx.font = options.font
  ctx.translate(canvas.width / 2, canvas.height / 2)
  ctx.rotate((options.rotate * Math.PI) / 180)
  ctx.fillText(options.content, -ctx.measureText(options.content).width / 2, 0)

  return canvas
}

function createWatermarkElement(canvas: HTMLCanvasElement, zIndex: number): HTMLElement {
  const div = document.createElement('div')
  div.style.cssText = `
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    pointer-events: none;
    z-index: ${zIndex};
    background-image: url(${canvas.toDataURL()});
    background-repeat: repeat;
    background-size: ${canvas.width}px ${canvas.height}px;
  `
  div.className = 'v-watermark'
  return div
}

const watermarkDirective: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    const options: WatermarkOptions = binding.value || {}
    const mergedOptions = { ...defaultOptions, ...options }

    const canvas = createWatermarkCanvas(mergedOptions)
    const watermarkEl = createWatermarkElement(canvas, mergedOptions.zIndex)

    // 如果是绑定在 body 上
    if (el === document.body) {
      document.body.appendChild(watermarkEl)
    } else {
      // 确保父元素有定位
      const computedStyle = window.getComputedStyle(el)
      if (computedStyle.position === 'static') {
        el.style.position = 'relative'
      }
      el.style.overflow = 'hidden'
      el.appendChild(watermarkEl)
    }

    el._watermarkEl = watermarkEl
  },

  updated(el: HTMLElement, binding: DirectiveBinding) {
    const options: WatermarkOptions = binding.value || {}
    const mergedOptions = { ...defaultOptions, ...options }

    if (el._watermarkEl) {
      el._watermarkEl.remove()
    }

    const canvas = createWatermarkCanvas(mergedOptions)
    const watermarkEl = createWatermarkElement(canvas, mergedOptions.zIndex)

    if (el === document.body) {
      document.body.appendChild(watermarkEl)
    } else {
      el.appendChild(watermarkEl)
    }

    el._watermarkEl = watermarkEl
  },

  unmounted(el: HTMLElement) {
    if (el._watermarkEl) {
      el._watermarkEl.remove()
      delete el._watermarkEl
    }
  },
}

export default watermarkDirective