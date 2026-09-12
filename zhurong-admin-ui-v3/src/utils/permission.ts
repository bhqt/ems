import type { RouteRecordRaw } from 'vue-router'

/**
 * 过滤异步路由
 * @param routes 异步路由表
 * @param roles 用户角色
 * @param permissions 用户权限
 * @returns 过滤后的路由表
 */
export function filterAsyncRoutes(
  routes: RouteRecordRaw[],
  roles: string[],
  permissions: string[]
): RouteRecordRaw[] {
  const res: RouteRecordRaw[] = []

  routes.forEach(route => {
    const tmp = { ...route }

    // 角色权限检查
    if (tmp.meta?.roles && tmp.meta.roles.length > 0) {
      if (!roles.some(role => tmp.meta!.roles!.includes(role))) {
        return
      }
    }

    // 权限标识检查
    if (tmp.meta?.permissions && tmp.meta.permissions.length > 0) {
      if (!permissions.some(perm => tmp.meta!.permissions!.includes(perm))) {
        return
      }
    }

    // 递归处理子路由
    if (tmp.children && tmp.children.length > 0) {
      tmp.children = filterAsyncRoutes(tmp.children, roles, permissions)
      if (tmp.children.length === 0) {
        return
      }
    }

    res.push(tmp)
  })

  return res
}

/**
 * 判断是否有权限
 * @param permissions 用户权限列表
 * @param requiredPermissions 需要的权限
 */
export function hasPermission(permissions: string[], requiredPermissions: string | string[]): boolean {
  if (!requiredPermissions) return true
  const perms = Array.isArray(requiredPermissions) ? requiredPermissions : [requiredPermissions]
  return perms.some(p => permissions.includes(p))
}

/**
 * 判断是否有角色
 * @param roles 用户角色列表
 * @param requiredRoles 需要的角色
 */
export function hasRole(roles: string[], requiredRoles: string | string[]): boolean {
  if (!requiredRoles) return true
  const roles_ = Array.isArray(requiredRoles) ? requiredRoles : [requiredRoles]
  return roles_.some(r => roles.includes(r))
}

/**
 * 获取面包屑数据
 * @param route 当前路由
 * @returns 面包屑数组
 */
export function getBreadcrumbs(route: any): Array<{ title: string; path?: string }> {
  const breadcrumbs: Array<{ title: string; path?: string }> = []

  let currentRoute = route
  while (currentRoute) {
    if (currentRoute.meta?.title && !currentRoute.meta?.hideBreadcrumb) {
      breadcrumbs.unshift({
        title: currentRoute.meta.title,
        path: currentRoute.path,
      })
    }
    currentRoute = currentRoute.matched[currentRoute.matched.length - 2]
  }

  return breadcrumbs
}

/**
 * 扁平化路由表
 * @param routes 路由表
 * @returns 扁平化后的路由数组
 */
export function flattenRoutes(routes: RouteRecordRaw[]): RouteRecordRaw[] {
  const result: RouteRecordRaw[] = []

  function traverse(routes: RouteRecordRaw[]) {
    routes.forEach(route => {
      result.push(route)
      if (route.children && route.children.length > 0) {
        traverse(route.children)
      }
    })
  }

  traverse(routes)
  return result
}

/**
 * 查找路由
 * @param routes 路由表
 * @param path 路径
 * @returns 匹配的路由
 */
export function findRoute(routes: RouteRecordRaw[], path: string): RouteRecordRaw | undefined {
  for (const route of routes) {
    if (route.path === path) {
      return route
    }
    if (route.children && route.children.length > 0) {
      const found = findRoute(route.children, path)
      if (found) return found
    }
  }
  return undefined
}

/**
 * 获取菜单树
 * @param routes 路由表
 * @returns 菜单树
 */
export function getMenuTree(routes: RouteRecordRaw[]) {
  return routes
    .filter(route => !route.meta?.hideMenu)
    .map(route => ({
      ...route,
      children: route.children?.length ? getMenuTree(route.children) : undefined,
    }))
    .filter(route => !route.children || route.children.length > 0)
}