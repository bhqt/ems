import { computed } from 'vue'
import { useI18n as useVueI18n } from 'vue-i18n'
import { useAppStore } from '@/stores/modules/app'

export default function useI18n() {
  const appStore = useAppStore()
  const { t, locale, setLocaleMessage, getLocaleMessage, mergeLocaleMessage } = useVueI18n()

  const currentLocale = computed(() => appStore.locale)
  const availableLocales = computed(() => ['zh-CN', 'en-US'] as const)

  function setLocale(lang: 'zh-CN' | 'en-US') {
    appStore.setLocale(lang)
  }

  function tWithFallback(key: string, fallback?: string) {
    const translation = t(key)
    return translation === key ? (fallback || key) : translation
  }

  function loadLocaleMessages(locale: 'zh-CN' | 'en-US') {
    return import(`@/locales/${locale}/index.ts`).then(module => {
      mergeLocaleMessage(locale, module.default)
      return module.default
    })
  }

  function switchLocale(locale: 'zh-CN' | 'en-US') {
    return loadLocaleMessages(locale).then(() => {
      setLocale(locale)
    })
  }

  return {
    t,
    tWithFallback,
    locale: currentLocale,
    availableLocales,
    setLocale,
    switchLocale,
    loadLocaleMessages,
    getLocaleMessage,
    setLocaleMessage,
    mergeLocaleMessage,
  }
}