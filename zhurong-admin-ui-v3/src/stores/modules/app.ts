import { defineStore } from 'pinia'
import { ref, computed, watch } from 'vue'
import { theme } from 'ant-design-vue'
import { lightTheme, darkTheme, hospitalTheme } from '@/assets/styles/themes'
import type { ThemeToken, ThemeConfig } from 'ant-design-vue/es/config-provider/context'

export const useAppStore = defineStore('app', () => {
  // ============ State ============
  const sidebar = ref({
    opened: true,
    withoutAnimation: false,
    hide: false,
    collapsed: false,
  })

  const device = ref<'desktop' | 'tablet' | 'mobile'>('desktop')
  const size = ref<'default' | 'small' | 'large'>('default')

  const themeMode = ref<'light' | 'dark' | 'hospital'>('light')
  const locale = ref<'zh-CN' | 'en-US'>('zh-CN')

  const tagsView = ref(true)
  const fixedHeader = ref(true)
  const sidebarLogo = ref(true)
  const watermark = ref(false)
  const greyMode = ref(false)
  const weakMode = ref(false)

  const layout = ref<'default' | 'blank' | 'iframe'>('default')

  // ============ Getters ============
  const isDark = computed(() => themeMode.value === 'dark')
  const isHospital = computed(() => themeMode.value === 'hospital')
  const isMobile = computed(() => device.value === 'mobile')
  const sidebarWidth = computed(() => sidebar.value.collapsed ? 80 : 260)

  const getThemeTokens = computed((): ThemeToken => {
    switch (themeMode.value) {
      case 'dark':
        return darkTheme
      case 'hospital':
        return hospitalTheme
      default:
        return lightTheme
    }
  })

  const getThemeAlgorithm = computed(() => {
    return isDark.value ? [theme.darkAlgorithm] : [theme.defaultAlgorithm]
  })

  const getThemeConfig = computed((): ThemeConfig => ({
    token: getThemeTokens.value,
    algorithm: getThemeAlgorithm.value,
    cssVar: { prefix: 'zhurong' },
  }))

  // ============ Actions ============
  function toggleSidebar(withoutAnimation = false) {
    sidebar.value.opened = !sidebar.value.opened
    sidebar.value.withoutAnimation = withoutAnimation
  }

  function closeSidebar(withoutAnimation = false) {
    sidebar.value.opened = false
    sidebar.value.withoutAnimation = withoutAnimation
  }

  function openSidebar(withoutAnimation = false) {
    sidebar.value.opened = true
    sidebar.value.withoutAnimation = withoutAnimation
  }

  function toggleCollapse() {
    sidebar.value.collapsed = !sidebar.value.collapsed
  }

  function setCollapse(collapsed: boolean) {
    sidebar.value.collapsed = collapsed
  }

  function setDevice(deviceType: 'desktop' | 'tablet' | 'mobile') {
    device.value = deviceType
    if (deviceType === 'mobile') {
      sidebar.value.opened = false
    }
  }

  function setSize(sizeType: 'default' | 'small' | 'large') {
    size.value = sizeType
  }

  function setTheme(mode: 'light' | 'dark' | 'hospital') {
    themeMode.value = mode
    document.documentElement.dataset.theme = mode
    if (mode === 'dark') {
      document.documentElement.classList.add('dark')
    } else {
      document.documentElement.classList.remove('dark')
    }
  }

  function setLocale(lang: 'zh-CN' | 'en-US') {
    locale.value = lang
    document.documentElement.lang = lang === 'zh-CN' ? 'zh' : 'en'
  }

  function toggleTagsView() {
    tagsView.value = !tagsView.value
  }

  function toggleFixedHeader() {
    fixedHeader.value = !fixedHeader.value
  }

  function toggleSidebarLogo() {
    sidebarLogo.value = !sidebarLogo.value
  }

  function toggleWatermark() {
    watermark.value = !watermark.value
  }

  function toggleGreyMode() {
    greyMode.value = !greyMode.value
    if (greyMode.value) {
      document.body.style.filter = 'grayscale(100%)'
    } else {
      document.body.style.filter = 'none'
    }
  }

  function toggleWeakMode() {
    weakMode.value = !weakMode.value
    if (weakMode.value) {
      document.body.style.filter = 'invert(80%)'
    } else if (!greyMode.value) {
      document.body.style.filter = 'none'
    }
  }

  function setLayout(layoutType: 'default' | 'blank' | 'iframe') {
    layout.value = layoutType
  }

  function initTheme() {
    const savedTheme = localStorage.getItem('theme') as 'light' | 'dark' | 'hospital' | null
    if (savedTheme) {
      setTheme(savedTheme)
    } else if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
      setTheme('dark')
    }
  }

  function initLocale() {
    const savedLocale = localStorage.getItem('locale') as 'zh-CN' | 'en-US' | null
    if (savedLocale) {
      setLocale(savedLocale)
    } else if (navigator.language.startsWith('zh')) {
      setLocale('zh-CN')
    }
  }

  function initSettings() {
    initTheme()
    initLocale()

    const settings = localStorage.getItem('app-settings')
    if (settings) {
      try {
        const parsed = JSON.parse(settings)
        tagsView.value = parsed.tagsView ?? true
        fixedHeader.value = parsed.fixedHeader ?? true
        sidebarLogo.value = parsed.sidebarLogo ?? true
        watermark.value = parsed.watermark ?? false
        greyMode.value = parsed.greyMode ?? false
        weakMode.value = parsed.weakMode ?? false
        size.value = parsed.size ?? 'default'

        if (greyMode.value) document.body.style.filter = 'grayscale(100%)'
        if (weakMode.value) document.body.style.filter = 'invert(80%)'
      } catch {
        // ignore
      }
    }
  }

  // ============ Watchers (Persist) ============
  watch(
    themeMode,
    (val) => {
      localStorage.setItem('theme', val)
    },
    { immediate: true }
  )

  watch(
    locale,
    (val) => {
      localStorage.setItem('locale', val)
    },
    { immediate: true }
  )

  watch(
    [tagsView, fixedHeader, sidebarLogo, watermark, greyMode, weakMode, size],
    () => {
      localStorage.setItem('app-settings', JSON.stringify({
        tagsView: tagsView.value,
        fixedHeader: fixedHeader.value,
        sidebarLogo: sidebarLogo.value,
        watermark: watermark.value,
        greyMode: greyMode.value,
        weakMode: weakMode.value,
        size: size.value,
      }))
    },
    { immediate: true }
  )

  return {
    // State
    sidebar,
    device,
    size,
    themeMode,
    locale,
    tagsView,
    fixedHeader,
    sidebarLogo,
    watermark,
    greyMode,
    weakMode,
    layout,

    // Getters
    isDark,
    isHospital,
    isMobile,
    sidebarWidth,
    getThemeTokens,
    getThemeAlgorithm,
    getThemeConfig,

    // Actions
    toggleSidebar,
    closeSidebar,
    openSidebar,
    toggleCollapse,
    setCollapse,
    setDevice,
    setSize,
    setTheme,
    setLocale,
    toggleTagsView,
    toggleFixedHeader,
    toggleSidebarLogo,
    toggleWatermark,
    toggleGreyMode,
    toggleWeakMode,
    setLayout,
    initTheme,
    initLocale,
    initSettings,
  }
}, {
  persist: {
    key: 'zhurong-app',
    paths: ['themeMode', 'locale', 'tagsView', 'fixedHeader', 'sidebarLogo', 'watermark', 'greyMode', 'weakMode', 'size', 'sidebar.collapsed'],
  },
})