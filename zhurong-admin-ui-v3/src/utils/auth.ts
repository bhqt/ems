import { TOKEN_KEY, REFRESH_TOKEN_KEY } from '@/constants'

/** 获取 Token */
export function getToken(): string {
  return localStorage.getItem(TOKEN_KEY) || ''
}

/** 设置 Token */
export function setToken(token: string): void {
  localStorage.setItem(TOKEN_KEY, token)
}

/** 移除 Token */
export function removeToken(): void {
  localStorage.removeItem(TOKEN_KEY)
}

/** 获取 Refresh Token */
export function getRefreshToken(): string {
  return localStorage.getItem(REFRESH_TOKEN_KEY) || ''
}

/** 设置 Refresh Token */
export function setRefreshToken(token: string): void {
  localStorage.setItem(REFRESH_TOKEN_KEY, token)
}

/** 移除 Refresh Token */
export function removeRefreshToken(): void {
  localStorage.removeItem(REFRESH_TOKEN_KEY)
}

/** 检查是否登录 */
export function isAuthenticated(): boolean {
  return !!getToken()
}

/** 清除所有认证信息 */
export function clearAuth(): void {
  removeToken()
  removeRefreshToken()
}

/** 解析 JWT Token (简单实现，不验证签名) */
export function parseJwt(token: string): any {
  try {
    const base64Url = token.split('.')[1]
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    )
    return JSON.parse(jsonPayload)
  } catch {
    return null
  }
}

/** 检查 Token 是否过期 */
export function isTokenExpired(token: string): boolean {
  const payload = parseJwt(token)
  if (!payload || !payload.exp) return true
  return Date.now() >= payload.exp * 1000
}

/** 获取 Token 剩余有效时间(秒) */
export function getTokenRemainingTime(token: string): number {
  const payload = parseJwt(token)
  if (!payload || !payload.exp) return 0
  return Math.max(0, Math.floor((payload.exp * 1000 - Date.now()) / 1000))
}