import type { Plugin } from 'vite'
import { createMockServer } from 'vite-plugin-mock'

export async function setupMock() {
  if (import.meta.env.VITE_USE_MOCK !== 'true') return

  const mockModule = await import('./index')
  const mockMethods = mockModule.default

  // 这里可以手动注册 mock 接口
  // 实际使用时建议使用 vite-plugin-mock 插件
  console.log('[Mock] Mock server started', mockMethods.length, 'routes')
}

// 简单的 mock 拦截器（用于开发环境）
export function setupMockInterceptor() {
  if (import.meta.env.VITE_USE_MOCK !== 'true') return

  const originalFetch = window.fetch
  window.fetch = async (input: RequestInfo | URL, init?: RequestInit) => {
    const url = typeof input === 'string' ? input : input.url
    
    // 这里可以拦截请求并返回模拟数据
    // 实际建议使用 vite-plugin-mock
    
    return originalFetch(input, init)
  }
}