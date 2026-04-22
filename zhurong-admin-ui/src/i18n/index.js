import Vue from 'vue'
import VueI18n from 'vue-i18n'
import Cookies from 'js-cookie'
import ElementLocale from 'element-ui/lib/locale'

// 导入 Element UI 语言包
import elementZhCN from 'element-ui/lib/locale/lang/zh-CN'
import elementEn from 'element-ui/lib/locale/lang/en'
import elementId from 'element-ui/lib/locale/lang/id'
import elementRu from 'element-ui/lib/locale/lang/ru-RU'

// 导入应用语言包
import zhCN from './lang/zh-CN'
import en from './lang/en'
import id from './lang/id'
import ru from './lang/ru'

Vue.use(VueI18n)

// P3优化：语言包按需加载配置
const loadedLanguages = []

// 合并 Element UI 和应用的语言包
const messages = {
  'zh-CN': {
    ...elementZhCN,
    ...zhCN
  },
  'en': {
    ...elementEn,
    ...en
  },
  'id': {
    ...elementId,
    ...id
  },
  'ru': {
    ...elementRu,
    ...ru
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
  if (cookieLang && messages[cookieLang]) {
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

// 设置语言方法
export function setLanguage(lang) {
  if (!messages[lang]) {
    console.warn(`[i18n] Language ${lang} not supported`)
    return false
  }

  i18n.locale = lang
  Cookies.set('language', lang, { expires: 365 })

  return true
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
