import axios from 'axios'
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse, AxiosError } from 'axios'
import { message, notification } from 'ant-design-vue'
import { useUserStore } from '@/stores/modules/user'
import { getToken } from './auth'

// 创建 axios 实例
const request: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json;charset=utf-8',
  },
})

// 请求拦截器
request.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    const token = getToken()
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`
    }
    
    // 防止重复请求
    if (config.method === 'get') {
      config.params = {
        ...config.params,
        _t: Date.now(),
      }
    }
    
    return config
  },
  (error: AxiosError) => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  (response: AxiosResponse) => {
    const { data, config } = response
    
    // 文件下载直接返回 blob
    if (config.responseType === 'blob' || config.responseType === 'arraybuffer') {
      return response
    }
    
    const code = data.code ?? 200
    const msg = data.msg ?? data.message ?? '操作成功'
    
    // 成功状态码
    if (code === 200) {
      return data
    }
    
    // 业务错误
    handleError(code, msg)
    return Promise.reject(new Error(msg))
  },
  (error: AxiosError) => {
    const { response } = error
    
    if (response) {
      const { status, data } = response
      
      switch (status) {
        case 400:
          message.error(data?.message || '请求参数错误')
          break
        case 401:
          // Token 过期或无效
          handleUnauthorized()
          break
        case 403:
          message.error('没有权限访问')
          break
        case 404:
          message.error('请求资源不存在')
          break
        case 422:
          message.error(data?.message || '数据验证失败')
          break
        case 500:
          message.error('服务器内部错误')
          break
        case 502:
          message.error('网关错误')
          break
        case 503:
          message.error('服务不可用')
          break
        case 504:
          message.error('网关超时')
          break
        default:
          message.error(data?.message || `请求失败 (${status})`)
      }
    } else {
      // 网络错误
      if (error.code === 'ECONNABORTED') {
        message.error('请求超时')
      } else if (error.message === 'Network Error') {
        message.error('网络错误，请检查网络连接')
      } else {
        message.error('请求失败')
      }
    }
    
    return Promise.reject(error)
  }
)

function handleError(code: number, message: string) {
  notification.error({
    message: '错误',
    description: message,
    duration: 5,
  })
}

function handleUnauthorized() {
  const userStore = useUserStore()
  userStore.resetAll()
  window.location.href = `/login?redirect=${encodeURIComponent(window.location.pathname)}`
}

export default request

// 导出常用方法
export const get = <T = any>(url: string, params?: any, config?: AxiosRequestConfig) => 
  request.get<T>(url, { params, ...config })

export const post = <T = any>(url: string, data?: any, config?: AxiosRequestConfig) => 
  request.post<T>(url, data, config)

export const put = <T = any>(url: string, data?: any, config?: AxiosRequestConfig) => 
  request.put<T>(url, data, config)

export const del = <T = any>(url: string, config?: AxiosRequestConfig) => 
  request.delete<T>(url, config)

export const download = (url: string, params?: any, filename?: string) => 
  request.get(url, { params, responseType: 'blob' }).then((blob: Blob) => {
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = filename || 'download'
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)
  })