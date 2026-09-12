import type { Directive, DirectiveBinding } from 'vue'

const loadingDirective: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    if (binding.value) {
      createLoading(el)
    }
  },

  updated(el: HTMLElement, binding: DirectiveBinding) {
    if (binding.value !== binding.oldValue) {
      if (binding.value) {
        createLoading(el)
      } else {
        removeLoading(el)
      }
    }
  },

  unmounted(el: HTMLElement) {
    removeLoading(el)
  },
}

function createLoading(el: HTMLElement) {
  if (el.querySelector('.v-loading-mask')) return

  const mask = document.createElement('div')
  mask.className = 'v-loading-mask'
  mask.innerHTML = `
    <div class="v-loading-spinner">
      <svg viewBox="25 25 50 50" class="circular">
        <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10"/>
      </svg>
    </div>
  `
  mask.style.cssText = `
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(255, 255, 255, 0.9);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
    border-radius: inherit;
  `

  const spinner = mask.querySelector('.v-loading-spinner') as HTMLElement
  if (spinner) {
    spinner.style.cssText = `
      width: 32px;
      height: 32px;
      animation: rotate 1s linear infinite;
    `
  }

  const style = document.createElement('style')
  style.textContent = `
    @keyframes rotate {
      0% { transform: rotate(0deg); }
      100% { transform: rotate(360deg); }
    }
    .circular { animation: rotate 1s linear infinite; }
    .path {
      stroke: #1677ff;
      stroke-linecap: round;
      animation: dash 1.5s ease-in-out infinite;
    }
    @keyframes dash {
      0% { stroke-dasharray: 1, 150; stroke-dashoffset: 0; }
      50% { stroke-dasharray: 90, 150; stroke-dashoffset: -35; }
      100% { stroke-dasharray: 90, 150; stroke-dashoffset: -124; }
    }
  `
  mask.appendChild(style)

  // 确保父元素有定位
  const computedStyle = window.getComputedStyle(el)
  if (computedStyle.position === 'static') {
    el.style.position = 'relative'
  }

  el.appendChild(mask)
}

function removeLoading(el: HTMLElement) {
  const mask = el.querySelector('.v-loading-mask')
  if (mask) {
    mask.remove()
  }
}

export default loadingDirective