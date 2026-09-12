import type { Directive, DirectiveBinding } from 'vue'
import { useUserStore } from '@/stores/modules/user'

function checkPermission(value: string | string[]): boolean {
  const userStore = useUserStore()
  if (!value) return true
  const perms = Array.isArray(value) ? value : [value]
  return userStore.hasPermission(perms)
}

function checkRole(value: string | string[]): boolean {
  const userStore = useUserStore()
  if (!value) return true
  const roles = Array.isArray(value) ? value : [value]
  return userStore.hasRole(roles)
}

function checkAny(value: { permissions?: string[]; roles?: string[] }): boolean {
  const { permissions, roles } = value
  if (permissions && !checkPermission(permissions)) return false
  if (roles && !checkRole(roles)) return false
  return true
}

function checkAll(value: { permissions?: string[]; roles?: string[] }): boolean {
  const { permissions, roles } = value
  if (permissions && !checkPermission(permissions)) return false
  if (roles && !checkRole(roles)) return false
  return true
}

const permissionDirective: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    const { value } = binding
    let hasAuth = false

    if (typeof value === 'string' || Array.isArray(value)) {
      hasAuth = checkPermission(value)
    } else if (value && typeof value === 'object') {
      if (value.any) {
        hasAuth = checkAny(value)
      } else if (value.all) {
        hasAuth = checkAll(value)
      } else if (value.permissions || value.roles) {
        hasAuth = checkAny(value)
      }
    }

    if (!hasAuth) {
      el.style.display = 'none'
      // 或者 el.parentNode?.removeChild(el)
    }
  },

  updated(el: HTMLElement, binding: DirectiveBinding) {
    const { value } = binding
    let hasAuth = false

    if (typeof value === 'string' || Array.isArray(value)) {
      hasAuth = checkPermission(value)
    } else if (value && typeof value === 'object') {
      if (value.any) {
        hasAuth = checkAny(value)
      } else if (value.all) {
        hasAuth = checkAll(value)
      } else if (value.permissions || value.roles) {
        hasAuth = checkAny(value)
      }
    }

    if (!hasAuth) {
      el.style.display = 'none'
    } else {
      el.style.display = ''
    }
  },
}

export default permissionDirective