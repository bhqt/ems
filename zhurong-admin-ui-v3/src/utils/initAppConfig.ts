/** 初始化应用配置 */

export async function initAppConfig(): Promise<void> {
  // 设置页面标题
  document.title = import.meta.env.VITE_APP_TITLE || '智碳能源管理系统'
  
  // 设置 favicon
  const favicon = document.querySelector('link[rel="icon"]') as HTMLLinkElement
  if (favicon) {
    favicon.href = import.meta.env.VITE_APP_FAVICON || '/favicon.ico'
  }
  
  // 设置语言
  const savedLocale = localStorage.getItem('zhurong_locale') || 'zh-CN'
  document.documentElement.lang = savedLocale === 'zh-CN' ? 'zh' : 'en'
  
  // 设置主题
  const savedTheme = localStorage.getItem('zhurong_theme') as 'light' | 'dark' | 'hospital' | null
  if (savedTheme) {
    document.documentElement.dataset.theme = savedTheme
    if (savedTheme === 'dark') {
      document.documentElement.classList.add('dark')
    }
  } else if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
    document.documentElement.dataset.theme = 'dark'
    document.documentElement.classList.add('dark')
  }
  
  // 防止页面加载闪烁
  document.body.style.visibility = 'visible'
  
  // 注册全局错误处理
  window.addEventListener('error', (event) => {
    console.error('Global error:', event.error)
  })
  
  window.addEventListener('unhandledrejection', (event) => {
    console.error('Unhandled rejection:', event.reason)
  })
}

export default initAppConfig