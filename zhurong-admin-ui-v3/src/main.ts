import { createApp, watch } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import { createI18n } from 'vue-i18n'
import Antd, { theme } from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import 'virtual:uno.css'
import '@/assets/styles/global.scss'
import App from './App.vue'
import router from './router'
import { setupDirectives } from '@/directives'
import { initAppConfig } from '@/utils/initAppConfig'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

import zhCN from 'ant-design-vue/es/locale/zh_CN'
import enUS from 'ant-design-vue/es/locale/en_US'

NProgress.configure({ showSpinner: false })

async function bootstrap() {
  await initAppConfig()

  const app = createApp(App)

  const pinia = createPinia()
  pinia.use(piniaPluginPersistedstate)
  app.use(pinia)

  const { useAppStore } = await import('@/stores/modules/app')
  const appStore = useAppStore()

  const i18n = createI18n({
    legacy: false,
    locale: appStore.locale || 'zh-CN',
    fallbackLocale: 'zh-CN',
    globalInjection: true,
    messages: {},
  })

  const loadLocaleMessages = async (locale: string) => {
    const messages = await import(`@/locales/${locale}/index.ts`)
    i18n.global.setLocaleMessage(locale, messages.default)
  }

  const applyLocale = async (locale: string) => {
    await loadLocaleMessages(locale)
    i18n.global.locale.value = locale
    document.documentElement.lang = locale.startsWith('zh') ? 'zh' : 'en'
  }

  await applyLocale(appStore.locale || 'zh-CN')

  watch(
    () => appStore.locale,
    (lang) => applyLocale(lang)
  )

  app.use(i18n)

  app.use(Antd, {
    theme: {
      token: appStore.getThemeTokens,
      algorithm: appStore.isDark ? [theme.darkAlgorithm] : [theme.defaultAlgorithm],
      cssVar: { prefix: 'zhurong' },
    },
    locale: i18n.global.locale.value === 'zh-CN' ? zhCN : enUS,
  })

  setupDirectives(app)

  app.use(router)

  app.mount('#app')
}

bootstrap()