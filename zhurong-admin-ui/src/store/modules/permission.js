import auth from '@/plugins/auth'
import router, { constantRoutes, dynamicRoutes } from '@/router'
import { getRouters } from '@/api/menu'
import Layout from '@/layout/index'
import ParentView from '@/components/ParentView'
import InnerLink from '@/layout/components/InnerLink'

const permission = {
  state: {
    routes: [],
    addRoutes: [],
    defaultRoutes: [],
    topbarRouters: [],
    sidebarRouters: []
  },
  mutations: {
    SET_ROUTES: (state, routes) => {
      state.addRoutes = routes
      state.routes = constantRoutes.concat(routes)
    },
    SET_DEFAULT_ROUTES: (state, routes) => {
      state.defaultRoutes = constantRoutes.concat(routes)
    },
    SET_TOPBAR_ROUTES: (state, routes) => {
      state.topbarRouters = routes
    },
    SET_SIDEBAR_ROUTERS: (state, routes) => {
      state.sidebarRouters = routes
    },
  },
  actions: {
    // 生成路由
    GenerateRoutes({ commit }) {
      return new Promise(resolve => {
        // 向后端请求路由数据
        getRouters().then(res => {
          // 每次重新生成路由前清空去重集合，避免上一个账号遗留的 name/path 影响当前账号
          seenRouteNames.clear()
          seenRoutePaths.clear()
          const sdata = JSON.parse(JSON.stringify(res.data))
          const rdata = JSON.parse(JSON.stringify(res.data))
          const sidebarRoutes = filterAsyncRouter(sdata)
          // 医院大屏是独立全屏页面（constantRoutes 中已定义，菜单通过新标签页打开），
          // 需要从注入 Layout 的动态路由中剔除，避免同路径路由被覆盖
          stripFullScreenRoutes(rdata)
          const rewriteRoutes = filterAsyncRouter(rdata, false, true)
          const asyncRoutes = filterDynamicRoutes(dynamicRoutes);
          rewriteRoutes.push({ path: '*', redirect: '/404', hidden: true })
          router.addRoutes(asyncRoutes);
          commit('SET_ROUTES', rewriteRoutes)
          commit('SET_SIDEBAR_ROUTERS', constantRoutes.concat(sidebarRoutes))
          commit('SET_DEFAULT_ROUTES', sidebarRoutes)
          commit('SET_TOPBAR_ROUTES', sidebarRoutes)
          resolve(rewriteRoutes)
        })
      })
    }
  }
}

// 独立全屏页面（不套 Layout、不注入动态路由，侧边栏以新标签页打开）
const FULL_SCREEN_PATHS = ['/hospital/screen']

function stripFullScreenRoutes(routes, parentPath = '') {
  for (let i = routes.length - 1; i >= 0; i--) {
    const route = routes[i]
    const fullPath = route.path && route.path.charAt(0) === '/'
      ? route.path
      : parentPath.replace(/\/$/, '') + '/' + route.path
    if (FULL_SCREEN_PATHS.indexOf(fullPath) !== -1) {
      routes.splice(i, 1)
      continue
    }
    if (route.children && route.children.length) {
      stripFullScreenRoutes(route.children, fullPath)
    }
  }
}

// 用于跨递归层级去重：vue-router 会对同名路由抛 Duplicate named routes 警告
// 这里记录已经被加入到路由表中的 name/path，重复出现则丢弃
const seenRouteNames = new Set()
const seenRoutePaths = new Set()

// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap, lastRouter = false, type = false) {
  const dedup = (route) => {
    // 1) 优先按 name 去重（vue-router 对同名路由会抛 Duplicate named routes 警告）
    // 2) 如果没有 name，再按 path 去重
    if (route.name) {
      if (seenRouteNames.has(route.name)) {
        return false
      }
      seenRouteNames.add(route.name)
    } else {
      const fullPath = getFullPath(route, lastRouter)
      if (seenRoutePaths.has(fullPath)) {
        return false
      }
      seenRoutePaths.add(fullPath)
    }
    return true
  }
  return asyncRouterMap.filter(route => {
    if (type && route.children) {
      route.children = filterChildren(route.children)
    }
    if (route.component) {
      // Layout ParentView 组件特殊处理
      if (route.component === 'Layout') {
        route.component = Layout
      } else if (route.component === 'ParentView') {
        route.component = ParentView
      } else if (route.component === 'InnerLink') {
        route.component = InnerLink
      } else {
        route.component = loadView(route.component)
      }
    }
    if (route.children != null && route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children, route, type)
    } else {
      delete route['children']
      delete route['redirect']
    }
    return dedup(route)
  })
}

// 拼接父子 path，得到一个路由的完整路径
function getFullPath(route, lastRouter) {
  if (!route.path) return ''
  if (route.path.charAt(0) === '/') return route.path
  const parentPath = lastRouter && lastRouter.path ? lastRouter.path : ''
  return (parentPath.replace(/\/$/, '') + '/' + route.path).replace(/\/+/g, '/')
}

function filterChildren(childrenMap, lastRouter = false) {
  var children = []
  childrenMap.forEach((el, index) => {
    if (el.children && el.children.length) {
      if (el.component === 'ParentView' && !lastRouter) {
        el.children.forEach(c => {
          c.path = el.path + '/' + c.path
          if (c.children && c.children.length) {
            children = children.concat(filterChildren(c.children, c))
            return
          }
          children.push(c)
        })
        return
      }
    }
    if (lastRouter) {
      el.path = lastRouter.path + '/' + el.path
    }
    children = children.concat(el)
  })
  return children
}

// 动态路由遍历，验证是否具备权限
export function filterDynamicRoutes(routes) {
  const res = []
  routes.forEach(route => {
    if (route.permissions) {
      if (auth.hasPermiOr(route.permissions)) {
        res.push(route)
      }
    } else if (route.roles) {
      if (auth.hasRoleOr(route.roles)) {
        res.push(route)
      }
    }
  })
  return res
}

export const loadView = (view) => {
  // 开发环境使用require方便调试，生产环境使用import实现懒加载
  if (process.env.NODE_ENV === 'development') {
    return (resolve) => require([`@/views/${view}`], resolve)
  } else {
    // 生产环境使用import实现真正的路由懒加载，提升首屏性能
    return () => import(`@/views/${view}`)
  }
}

export default permission
