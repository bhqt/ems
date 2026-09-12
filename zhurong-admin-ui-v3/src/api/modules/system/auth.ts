import request from '@/utils/request'

/** 登录参数 */
export interface LoginParams {
  username: string
  password: string
  code?: string
  uuid?: string
}

/** 登录结果 */
export interface LoginResult {
  token: string
}

/** 用户信息 */
export interface UserInfo {
  user: {
    userId: number
    userName: string
    nickName: string
    email: string
    phonenumber: string
    sex: string
    avatar: string
    status: string
    deptId: number
    dept?: {
      deptId: number
      deptName: string
    }
    roles?: Array<{
      roleId: number
      roleName: string
      roleKey: string
      admin?: boolean
    }>
    admin?: boolean
  }
  roles: string[]
  permissions: string[]
}

/** 登录 */
export function loginApi(params: LoginParams) {
  return request.post<LoginResult>('/login', params, { headers: { isToken: false } })
}

/** 获取用户信息 */
export function getUserInfoApi() {
  return request.get<UserInfo>('/getInfo')
}

/** 登出 */
export function logoutApi() {
  return request.post('/logout')
}

/** 获取验证码 */
export function getCaptchaApi() {
  return request.get('/captchaImage', { headers: { isToken: false }, timeout: 20000 })
}

/** 获取路由菜单 */
export function getRoutesApi() {
  return request.get('/auth/routes')
}

/** 获取字典数据 */
export function getDictDataApi(type: string) {
  return request.get(`/system/dict/data/type/${type}`)
}
