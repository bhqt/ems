import { ref, computed } from 'vue'
import { useUserStore } from '@/stores/modules/user'
import { usePermissionStore } from '@/stores/modules/permission'

export function usePermission() {
  const userStore = useUserStore()
  const permissionStore = usePermissionStore()

  const permissions = computed(() => userStore.permissions)
  const roles = computed(() => userStore.roles)

  function hasPermission(perm: string | string[]): boolean {
    return userStore.hasPermission(perm)
  }

  function hasRole(role: string | string[]): boolean {
    return userStore.hasRole(role)
  }

  function hasAnyPermission(perms: string[]): boolean {
    return perms.some(p => permissions.value.includes(p))
  }

  function hasAllPermissions(perms: string[]): boolean {
    return perms.every(p => permissions.value.includes(p))
  }

  function hasAnyRole(roles_: string[]): boolean {
    return roles_.some(r => roles.value.includes(r))
  }

  function hasAllRoles(roles_: string[]): boolean {
    return roles_.every(r => roles.value.includes(r))
  }

  // v-permission 指令的值可以是字符串或数组
  function checkPermission(value: string | string[]): boolean {
    if (!value) return true
    const perms = Array.isArray(value) ? value : [value]
    return hasPermission(perms)
  }

  function checkRole(value: string | string[]): boolean {
    if (!value) return true
    const roles_ = Array.isArray(value) ? value : [value]
    return hasRole(roles_)
  }

  return {
    permissions,
    roles,
    hasPermission,
    hasRole,
    hasAnyPermission,
    hasAllPermissions,
    hasAnyRole,
    hasAllRoles,
    checkPermission,
    checkRole,
  }
}

// 权限指令
export const permissionDirective = {
  mounted(el: HTMLElement, binding: { value: string | string[] }) {
    const { checkPermission } = usePermission()
    if (!checkPermission(binding.value)) {
      el.style.display = 'none'
      // 或者 el.parentNode?.removeChild(el)
    }
  },
  updated(el: HTMLElement, binding: { value: string | string[] }) {
    const { checkPermission } = usePermission()
    if (!checkPermission(binding.value)) {
      el.style.display = 'none'
    } else {
      el.style.display = ''
    }
  },
}

export default permissionDirective