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
          // sidebar / rewrite 各用独立去重集合：
          // 否则 sdata 先走 filterAsyncRouter 会把所有 name 写入共享 set，
          // 接着 rdata 再走 filterAsyncRouter 时几乎全部被 dedup 过滤掉，
          // rewriteRoutes 最终只剩 2~3 个空壳路由（实际表现为所有菜单跳转 404）。
          const sidebarSeen = new Set()
          const rewriteSeen = new Set()
          const sdata = JSON.parse(JSON.stringify(res.data))
          const rdata = JSON.parse(JSON.stringify(res.data))
          const sidebarRoutes = filterAsyncRouter(sdata, sidebarSeen)
          // 医院大屏是独立全屏页面（constantRoutes 中已定义，菜单通过新标签页打开），
          // 需要从注入 Layout 的动态路由中剔除，避免同路径路由被覆盖
          stripFullScreenRoutes(rdata)
          const rewriteRoutes = filterAsyncRouter(rdata, rewriteSeen, false, true)
          const asyncRoutes = filterDynamicRoutes(dynamicRoutes)
          // 关键：catch-all 路由必须放在最后才能兜底（vue-router addRoutes 追加规则）
          rewriteRoutes.push({ path: '*', redirect: '/404', hidden: true })
          // 合并 dynamicRoutes（hidden 的分配角色/分配用户/字典数据等）与业务路由
          // 注意：dynamicRoutes 包含前端写死的 hidden 路由（如 AuthRole），
          //       rewriteRoutes 包含后端返回的所有业务菜单路由。
          // 路由注册顺序：dynamicRoutes 先注册，rewriteRoutes 后注册，
          // catch-all 放在 rewriteRoutes 末尾，可正确兜底所有未匹配路径。
          const allRoutes = asyncRoutes.concat(rewriteRoutes)
          commit('SET_ROUTES', rewriteRoutes)
          commit('SET_SIDEBAR_ROUTERS', constantRoutes.concat(sidebarRoutes))
          commit('SET_DEFAULT_ROUTES', sidebarRoutes)
          commit('SET_TOPBAR_ROUTES', sidebarRoutes)
          // 返回合并后的路由，permission.js 路由守卫会调用 router.addRoutes(accessRoutes)
          resolve(allRoutes)
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
// 这里只按 name 字段去重（vue-router 真正报错的是 name 冲突）
// 历史上曾按 fullPath 去重，但 sys_menu 中存在 parent_id 不同但 path 相同的菜单
// （如 parent_id=0 的 "3D可视化" 与 parent_id=医院菜单的 "角色看板" path 都是 dashboard），
// 按 path 去重会把顶级菜单误删，导致侧边栏看不到菜单、跳转 404。
// path 冲突时由 vue-router 自然覆盖即可（同一 path 的菜单本就是同一份视图），所以不按 path 去重。
//
// 注意：sidebarRoutes 与 rewriteRoutes 走的是同一份菜单数据的两份独立处理，
// vue-router 对 sidebarRouters 不依赖（侧边栏用的是 store.state.permission.sidebarRouters），
// 所以两份去重集合可以相互独立，互不影响。

// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap, seenSet, lastRouter = false, type = false) {
  // 把后端重复的 name 改名为 "name_path"（path 用 _ 替代 /）
  // vue-router 3.4.9 的 matcher 对同名 route 只保留最后一个，会导致 /monitor（系统监控）
  // 被 /quota/monitor（用量监测）覆盖；通过改名让所有 path 都能被路由到。
  // 侧边栏的 SidebarItem 通常按 path 解析（router.resolve({path: basePath + '/' + path})），
  // 因此即便 name 改了也不会影响菜单显示。
  const ensureUniqueName = (route) => {
    if (!route || !route.name) return
    if (seenSet.has(route.name)) {
      const suffix = (route.path || '').replace(/^\//, '').replace(/\//g, '_') || 'x'
      const newName = route.name + '_' + suffix
      route.name = newName
    }
    seenSet.add(route.name)
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
      route.children = filterAsyncRouter(route.children, seenSet, route, type)
    } else {
      delete route['children']
      delete route['redirect']
    }
    ensureUniqueName(route)
    return true
  })
}

// 拼接父子 path，得到一个路由的完整路径（保留以备将来 path 去重场景使用）
// 当前按规范只按 name 去重，因此本函数暂未引用
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
