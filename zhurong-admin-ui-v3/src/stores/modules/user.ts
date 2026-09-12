import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { UserInfo, LoginParams, LoginResult } from '@/api/modules/system/auth'
import { loginApi, getUserInfoApi, logoutApi } from '@/api/modules/system/auth'
import { setToken, getToken, removeToken } from '@/utils/auth'

export const useUserStore = defineStore('user', () => {
  const token = ref<string>(getToken() || '')
  const userInfo = ref<UserInfo | null>(null)
  const permissions = ref<string[]>([])
  const roles = ref<string[]>([])

  const isLoggedIn = computed(() => !!token.value)
  const userName = computed(() => userInfo.value?.user?.nickName || userInfo.value?.user?.userName || '')
  const avatar = computed(() => userInfo.value?.user?.avatar || '')
  const userId = computed(() => userInfo.value?.user?.userId || 0)

  async function login(loginForm: LoginParams) {
    const res = await loginApi(loginForm)
    const { token: newToken } = res.data

    token.value = newToken
    setToken(newToken)

    await fetchUserInfo()

    return res.data
  }

  async function fetchUserInfo() {
    const res = await getUserInfoApi()
    userInfo.value = res.data

    permissions.value = res.data.permissions || []
    roles.value = res.data.roles || []

    return res.data
  }

  async function logout() {
    try {
      await logoutApi()
    } finally {
      resetAll()
    }
  }

  function setPermissions(perms: string[]) {
    permissions.value = perms
  }

  function setRoles(userRoles: string[]) {
    roles.value = userRoles
  }

  function hasPermission(perm: string | string[]): boolean {
    if (!perm) return true
    const perms = Array.isArray(perm) ? perm : [perm]
    return perms.some(p => permissions.value.includes(p))
  }

  function hasRole(role: string | string[]): boolean {
    if (!role) return true
    const roles_ = Array.isArray(role) ? role : [role]
    return roles_.some(r => roles.value.includes(r))
  }

  function resetAll() {
    token.value = ''
    userInfo.value = null
    permissions.value = []
    roles.value = []
    removeToken()
  }

  return {
    token,
    userInfo,
    permissions,
    roles,
    isLoggedIn,
    userName,
    avatar,
    userId,
    login,
    fetchUserInfo,
    logout,
    setPermissions,
    setRoles,
    hasPermission,
    hasRole,
    resetAll,
  }
}, {
  persist: {
    key: 'zhurong-user',
    paths: ['token'],
  },
})
