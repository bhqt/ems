import i18n from './index'

/**
 * 国际化辅助函数
 * 主要用于处理动态菜单的翻译
 */

/**
 * 翻译菜单标题
 * @param {string} title - 菜单标题（可能是国际化 key 或原始文本）
 * @param {string} fallback - 回退文本
 * @returns {string} 翻译后的文本
 *
 * 使用说明：
 * 1. 如果后端返回的菜单 title 是国际化 key（如 "menu.system.user"），直接翻译
 * 2. 如果后端返回的是中文文本，尝试在 menu 配置中查找对应的 key 进行翻译
 * 3. 如果都找不到，返回原始文本
 */
export function translateMenuTitle(title, fallback = '') {
  if (!title) return ''

  // 1. 如果 title 已经是国际化 key 格式（包含点号）
  if (title.includes('.')) {
    const translated = i18n.t(title)
    if (translated !== title) {
      return translated
    }
  }

  // 2. 尝试在 menu 配置中查找（用于后端返回中文的情况）
  // 构建可能的 key 路径
    const possibleKeys = [
      `menu.${title}`,
      `menu.system.${title}`,
      `menu.monitor.${title}`,
      `menu.tool.${title}`,
      `menu.energy.${title}`,
      `menu.equipment.${title}`,
      `menu.alarm.${title}`,
      `menu.camera.${title}`,
      `menu.charging.${title}`,
      `menu.inspection.${title}`,
      `menu.itemized.${title}`,
      `menu.dataQuery.${title}`,
      `menu.profile.${title}`,
      `menu.control.${title}`,
      `menu.metering.${title}`,
      `menu.carbon.${title}`,
      `menu.energyAnalysis.${title}`,
      `menu.digitaltwin.${title}`,
      `menu.inventory.${title}`,
      `menu.managementSystem.${title}`,
      `menu.maintenance.${title}`,
      `menu.quota.${title}`,
      `menu.report.${title}`,
      `menu.newenergy.${title}`,
      `menu.analysisReport.${title}`,
      `menu.dataBoard.${title}`,
      `menu.dashboard.${title}`
    ]

  for (const key of possibleKeys) {
    const translated = i18n.t(key)
    if (translated !== key) {
      return translated
    }
  }

  // 3. 尝试使用 common 中的翻译
  const commonKey = `common.${title}`
  const commonTranslated = i18n.t(commonKey)
  if (commonTranslated !== commonKey) {
    return commonTranslated
  }

  // 4. 返回原始文本或回退文本
  return fallback || title
}

/**
 * 批量翻译菜单列表
 * @param {Array} menus - 菜单列表
 * @returns {Array} 翻译后的菜单列表
 */
export function translateMenus(menus) {
  if (!Array.isArray(menus)) return []

  return menus.map(menu => {
    const translatedMenu = { ...menu }

    // 翻译当前菜单标题
    if (menu.meta && menu.meta.title) {
      translatedMenu.meta = {
        ...menu.meta,
        title: translateMenuTitle(menu.meta.title)
      }
    }

    // 递归翻译子菜单
    if (menu.children && menu.children.length > 0) {
      translatedMenu.children = translateMenus(menu.children)
    }

    return translatedMenu
  })
}

/**
 * 生成菜单的国际化 key
 * 用于前端静态路由配置
 * @param {string} module - 模块名
 * @param {string} name - 菜单名
 * @returns {string} 国际化 key
 */
export function generateMenuKey(module, name) {
  return `menu.${module}.${name}`
}

/**
 * 从路由路径推断菜单 key
 * @param {string} path - 路由路径
 * @returns {string} 可能的国际化 key
 */
export function inferMenuKeyFromPath(path) {
  if (!path) return ''

  // 移除开头的斜杠并按斜杠分割
  const parts = path.replace(/^\//, '').split('/')

  if (parts.length >= 2) {
    return `menu.${parts[0]}.${parts[1]}`
  }

  if (parts.length === 1) {
    return `menu.${parts[0]}.title`
  }

  return ''
}

/**
 * 获取 Element UI 语言包
 * 用于动态切换 Element UI 的语言
 */
export function getElementLocale() {
  const langMap = {
    'zh-CN': () => import('element-ui/lib/locale/lang/zh-CN'),
    'en': () => import('element-ui/lib/locale/lang/en'),
    'id': () => import('element-ui/lib/locale/lang/id'), // Element UI 支持印尼语
    'ru': () => import('element-ui/lib/locale/lang/ru-RU') // Element UI 支持俄语
  }

  const currentLang = i18n.locale
  const loader = langMap[currentLang] || langMap['zh-CN']

  return loader()
}

export default {
  translateMenuTitle,
  translateMenus,
  generateMenuKey,
  inferMenuKeyFromPath,
  getElementLocale
}
