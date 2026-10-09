import Vue from 'vue'
import VueI18n from 'vue-i18n'
import Cookies from 'js-cookie'
import ElementLocale from 'element-ui/lib/locale'

// 导入默认语言包（预加载）
import elementZhCN from 'element-ui/lib/locale/lang/zh-CN'
import zhCN from './lang/zh-CN'
import elementEn from 'element-ui/lib/locale/lang/en'
import en from './lang/en'
import id from './lang/id'
import ru from './lang/ru'

Vue.use(VueI18n)

// 语言包按需加载配置
const loadedLanguages = []

/**
 * i18n 文件结构补救：因早期编辑事故，部分子模块（如 occupancyOrder / orderDetail）
 * 在 4 个语言包中被错误提升为顶级键，导致页面通过 `chargingModule.xxx` 路径找不到翻译。
 * 此函数在挂载到 VueI18n 之前，将这些"孤儿"顶级键代理到正确的父模块下，
 * 既不修改 i18n 源文件，也不需要改业务页面代码。
 */
function applyModuleKeyAliases(lang) {
  const aliases = {
    occupancyOrder: 'chargingModule',
    orderDetail: 'chargingModule'
  }
  for (const [orphanKey, parentKey] of Object.entries(aliases)) {
    if (lang[orphanKey] && !lang[parentKey]) {
      lang[parentKey] = { [orphanKey]: lang[orphanKey] }
    } else if (lang[orphanKey] && lang[parentKey] && !lang[parentKey][orphanKey]) {
      lang[parentKey] = { [orphanKey]: lang[orphanKey], ...lang[parentKey] }
    }
  }
}

// 初始包含中英文（预加载）
const messages = {
  'zh-CN': (() => {
    const merged = { ...elementZhCN, ...zhCN }
    applyModuleKeyAliases(merged)
    return merged
  })(),
  'en': (() => {
    const merged = { ...elementEn, ...en }
    applyModuleKeyAliases(merged)
    return merged
  })(),
  // 印尼语和俄语只有应用语言包，没有 Element UI 语言包
  'id': (() => {
    const merged = { ...id }
    applyModuleKeyAliases(merged)
    return merged
  })(),
  'ru': (() => {
    const merged = { ...ru }
    applyModuleKeyAliases(merged)
    return merged
  })()
}

// 标记所有语言已加载
loadedLanguages.push('zh-CN')
loadedLanguages.push('en')
loadedLanguages.push('id')
loadedLanguages.push('ru')

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

  // 默认返回英文
  return 'en'
}

// 创建 i18n 实例
const isDev = process.env.NODE_ENV !== 'production'

const i18n = new VueI18n({
  locale: getDefaultLang(),
  fallbackLocale: 'zh-CN',
  messages,
  // 开发环境打开缺失 key 告警：否则 vue-i18n 会静默回退并直接渲染 key 路径，
  // 导致 "英文硬编码混入中文页" 这类问题只能靠逐页反馈才能发现。
  // 生产环境保持静默，避免控制台噪音。
  silentTranslationWarn: !isDev,
  silentFallbackWarn: !isDev
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

    // 同样的键路径别名补救
    applyModuleKeyAliases(messages[lang])

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
