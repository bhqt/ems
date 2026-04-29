import Vue from 'vue'
import VueI18n from 'vue-i18n'
import Cookies from 'js-cookie'
import ElementLocale from 'element-ui/lib/locale'

// 导入默认语言包（预加载）
import elementZhCN from 'element-ui/lib/locale/lang/zh-CN'
import zhCN from './lang/zh-CN'

Vue.use(VueI18n)

// 语言包按需加载配置
const loadedLanguages = []

// 初始只包含默认语言
const messages = {
  'zh-CN': {
    ...elementZhCN,
    ...zhCN
  }
}

// 标记默认语言已加载
loadedLanguages.push('zh-CN')

// 支持的语言列表
export const supportLanguages = [
  { code: 'zh-CN', name: '简体中文', flag: '🇨🇳' },
  { code: 'en', name: 'English', flag: '🇺🇸' },
  { code: 'id', name: 'Bahasa Indonesia', flag: '🇮🇩' },
  { code: 'ru', name: 'Русский', flag: '🇷🇺' }
]

// 获取默认语言
export function getDefaultLang() {
  const cookieLang = Cookies.get('language')
  // 支持的语言代码列表
  const supportedLangCodes = supportLanguages.map(lang => lang.code)
  
  if (cookieLang && supportedLangCodes.includes(cookieLang)) {
    return cookieLang
  }

  // 根据浏览器语言自动检测
  const browserLang = navigator.language || navigator.browserLanguage
  if (browserLang) {
    // 处理中文
    if (browserLang.startsWith('zh')) {
      return 'zh-CN'
    }
    // 处理英语
    if (browserLang.startsWith('en')) {
      return 'en'
    }
    // 处理印尼语
    if (browserLang.startsWith('id')) {
      return 'id'
    }
    // 处理俄语
    if (browserLang.startsWith('ru')) {
      return 'ru'
    }
  }

  return 'zh-CN'
}

// 创建 i18n 实例
const i18n = new VueI18n({
  locale: getDefaultLang(),
  fallbackLocale: 'zh-CN',
  messages,
  silentTranslationWarn: true, // 静默翻译警告
  silentFallbackWarn: true // 静默回退警告
})

// 设置 Element UI 的国际化
ElementLocale.i18n((key, value) => i18n.t(key, value))

// 初始化时加载默认语言（如果不是 zh-CN）
const initialLang = getDefaultLang()
if (initialLang !== 'zh-CN') {
  loadLanguageAsync(initialLang).catch(error => {
    console.warn('[i18n] 初始语言加载失败，使用默认语言:', error)
  })
}

// 按需加载语言包
export function loadLanguageAsync(lang) {
  // 如果语言已加载，直接切换
  if (i18n.locale === lang) {
    Cookies.set('language', lang, { expires: 365 })
    return Promise.resolve()
  }

  // 如果语言包已加载，直接切换
  if (loadedLanguages.includes(lang)) {
    i18n.locale = lang
    Cookies.set('language', lang, { expires: 365 })
    return Promise.resolve()
  }

  // 动态导入语言包
  return Promise.all([
    // 导入应用语言包
    import(`./lang/${lang === 'zh-CN' ? 'zh-CN' : lang}`),
    // 导入 Element UI 语言包
    import(`element-ui/lib/locale/lang/${lang === 'zh-CN' ? 'zh-CN' : lang}`)
  ]).then(([appLang, elementLang]) => {
    // 合并语言包
    messages[lang] = {
      ...elementLang.default,
      ...appLang.default
    }

    // 标记语言已加载
    loadedLanguages.push(lang)

    // 切换语言
    i18n.locale = lang
    Cookies.set('language', lang, { expires: 365 })

    return Promise.resolve()
  }).catch(error => {
    console.error(`[i18n] Failed to load language ${lang}:`, error)
    // 加载失败时回退到默认语言
    i18n.locale = 'zh-CN'
    return Promise.reject(error)
  })
}

// 设置语言方法
export function setLanguage(lang) {
  return loadLanguageAsync(lang)
}

// 获取当前语言
export function getCurrentLanguage() {
  return i18n.locale
}

// 翻译函数（用于动态菜单等场景）
export function translate(key, fallback = '') {
  const translated = i18n.t(key)
  // 如果没有找到翻译，返回 fallback 或原 key
  if (translated === key || !translated) {
    return fallback || key
  }
  return translated
}

export default i18n
