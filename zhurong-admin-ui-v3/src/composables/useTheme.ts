import { ref, computed, watch, onMounted, onUnmounted } from 'vue'
import { useAppStore } from '@/stores/modules/app'

export default function useTheme() {
  const appStore = useAppStore()

  const currentTheme = computed(() => appStore.themeMode)
  const isDark = computed(() => appStore.isDark)
  const isHospital = computed(() => appStore.isHospital)

  function setTheme(theme: 'light' | 'dark' | 'hospital') {
    appStore.setTheme(theme)
  }

  function toggleDark() {
    setTheme(isDark.value ? 'light' : 'dark')
  }

  function toggleHospital() {
    setTheme(isHospital.value ? 'light' : 'hospital')
  }

  function cycleTheme() {
    const themes: ('light' | 'dark' | 'hospital')[] = ['light', 'dark', 'hospital']
    const currentIndex = themes.indexOf(currentTheme.value)
    const nextIndex = (currentIndex + 1) % themes.length
    setTheme(themes[nextIndex])
  }

  // 监听系统主题变化
  onMounted(() => {
    const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)')
    const handleChange = (e: MediaQueryListEvent) => {
      if (appStore.themeMode === 'light' || appStore.themeMode === 'dark') {
        appStore.setTheme(e.matches ? 'dark' : 'light')
      }
    }
    mediaQuery.addEventListener('change', handleChange)
    onUnmounted(() => mediaQuery.removeEventListener('change', handleChange))
  })

  return {
    currentTheme,
    isDark,
    isHospital,
    setTheme,
    toggleDark,
    toggleHospital,
    cycleTheme,
  }
}