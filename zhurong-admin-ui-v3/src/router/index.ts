import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/modules/user'
import NProgress from 'nprogress'
import { constantRoutes } from '@/router/routes'

const router = createRouter({
  history: createWebHistory(import.meta.env.VITE_PUBLIC_PATH || '/'),
  routes: constantRoutes,
  scrollBehavior: () => ({ left: 0, top: 0 }),
})

const whiteList = ['/login', '/403', '/404', '/500']

router.beforeEach(async (to, _from, next) => {
  NProgress.start()

  const userStore = useUserStore()

  document.title = `${(to.meta?.title as string) || '页面'} - ${import.meta.env.VITE_APP_TITLE || '智碳能源管理系统'}`

  if (whiteList.includes(to.path)) {
    next()
    return
  }

  if (!userStore.isLoggedIn) {
    next(`/login?redirect=${encodeURIComponent(to.fullPath)}`)
    NProgress.done()
    return
  }

  // 已登录但无用户信息，获取一下
  if (!userStore.userInfo) {
    try {
      await userStore.fetchUserInfo()
    } catch {
      userStore.resetAll()
      next(`/login?redirect=${encodeURIComponent(to.fullPath)}`)
      NProgress.done()
      return
    }
  }

  next()
})

router.afterEach((_to, _from, failure) => {
  NProgress.done()
  if (failure) {
    console.error('路由跳转失败:', failure)
  }
})

export default router
