import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { constantRoutes, asyncRoutes } from '@/router/routes'
import type { RouteRecordRaw } from 'vue-router'
import { filterAsyncRoutes } from '@/utils/permission'

export const usePermissionStore = defineStore('permission', () => {
  // ============ State ============
  const routes = ref<RouteRecordRaw[]>([])
  const addRoutes = ref<RouteRecordRaw[]>([])
  const sidebarRoutes = ref<RouteRecordRaw[]>([])
  const isGenerated = ref(false)

  // ============ Getters ============
  const allRoutes = computed(() => [...constantRoutes, ...routes.value])

  // ============ Actions ============
  function generateRoutes(roles: string[], permissions: string[]): Promise<RouteRecordRaw[]> {
    return new Promise((resolve) => {
      let accessedRoutes: RouteRecordRaw[]

      if (roles.includes('SUPER_ADMIN') || roles.includes('admin') || permissions.includes('*:*:*')) {
        accessedRoutes = asyncRoutes
      } else {
        accessedRoutes = filterAsyncRoutes(asyncRoutes, roles, permissions)
      }

      routes.value = accessedRoutes
      addRoutes.value = accessedRoutes.flat()
      sidebarRoutes.value = filterSidebarRoutes(accessedRoutes.flat())
      isGenerated.value = true

      resolve(accessedRoutes)
    })
  }

  function filterSidebarRoutes(routes: RouteRecordRaw[]): RouteRecordRaw[] {
    return routes
      .filter(route => !route.meta?.hideMenu)
      .map(route => {
        if (route.children && route.children.length > 0) {
          return {
            ...route,
            children: filterSidebarRoutes(route.children),
          }
        }
        return route
      })
      .filter(route => !route.children || route.children.length > 0)
  }

  function resetRoutes() {
    routes.value = []
    addRoutes.value = []
    sidebarRoutes.value = []
    isGenerated.value = false
  }

  function setRoutes(newRoutes: RouteRecordRaw[]) {
    routes.value = newRoutes
    addRoutes.value = newRoutes
    sidebarRoutes.value = filterSidebarRoutes(newRoutes)
    isGenerated.value = true
  }

  return {
    // State
    routes,
    addRoutes,
    sidebarRoutes,
    isGenerated,

    // Getters
    allRoutes,

    // Actions
    generateRoutes,
    resetRoutes,
    setRoutes,
  }
})