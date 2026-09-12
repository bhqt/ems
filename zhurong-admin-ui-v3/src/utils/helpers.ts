/** 通用工具函数 */

/** 深拷贝 */
export function deepClone<T>(obj: T): T {
  if (obj === null || typeof obj !== 'object') return obj
  if (obj instanceof Date) return new Date(obj.getTime()) as any
  if (obj instanceof RegExp) return new RegExp(obj) as any
  if (Array.isArray(obj)) return obj.map(deepClone) as any
  
  const cloned: any = {}
  for (const key in obj) {
    if (Object.prototype.hasOwnProperty.call(obj, key)) {
      cloned[key] = deepClone(obj[key])
    }
  }
  return cloned
}

/** 深度合并 */
export function deepMerge<T extends Record<string, any>>(target: T, ...sources: Partial<T>[]): T {
  if (!sources.length) return target
  const source = sources.shift()
  if (!source) return target
  
  for (const key in source) {
    if (Object.prototype.hasOwnProperty.call(source, key)) {
      const value = source[key]
      if (value && typeof value === 'object' && !Array.isArray(value) && value !== null) {
        target[key] = deepMerge(target[key] || {} as any, value as any)
      } else {
        target[key] = value as any
      }
    }
  }
  return deepMerge(target, ...sources)
}

/** 防抖 */
export function debounce<T extends (...args: any[]) => any>(fn: T, delay = 300): T & { cancel: () => void } {
  let timeoutId: ReturnType<typeof setTimeout> | null = null
  
  const debouncedFn = ((...args: Parameters<T>) => {
    if (timeoutId) clearTimeout(timeoutId)
    timeoutId = setTimeout(() => {
      fn(...args)
      timeoutId = null
    }, delay)
  }) as T & { cancel: () => void }
  
  debouncedFn.cancel = () => {
    if (timeoutId) {
      clearTimeout(timeoutId)
      timeoutId = null
    }
  }
  
  return debouncedFn
}

/** 节流 */
export function throttle<T extends (...args: any[]) => any>(fn: T, limit = 300): T & { cancel: () => void } {
  let inThrottle = false
  let lastArgs: Parameters<T> | null = null
  
  const throttledFn = ((...args: Parameters<T>) => {
    if (!inThrottle) {
      fn(...args)
      inThrottle = true
      setTimeout(() => {
        inThrottle = false
        if (lastArgs) {
          fn(...lastArgs)
          lastArgs = null
        }
      }, limit)
    } else {
      lastArgs = args
    }
  }) as T & { cancel: () => void }
  
  throttledFn.cancel = () => {
    inThrottle = false
    lastArgs = null
  }
  
  return throttledFn
}

/** 延迟执行 */
export function delay(ms: number): Promise<void> {
  return new Promise(resolve => setTimeout(resolve, ms))
}

/** 重试函数 */
export async function retry<T>(
  fn: () => Promise<T>,
  options: {
    retries?: number
    delay?: number
    backoff?: number
    onRetry?: (error: Error, attempt: number) => void
  } = {}
): Promise<T> {
  const { retries = 3, delay = 1000, backoff = 2, onRetry } = options
  
  try {
    return await fn()
  } catch (error) {
    if (retries <= 0) throw error
    
    if (onRetry) onRetry(error as Error, retries)
    
    await new Promise(resolve => setTimeout(resolve, delay))
    return retry(fn, { retries: retries - 1, delay: delay * backoff, backoff, onRetry })
  }
}

/** 并发控制 */
export async function parallelLimit<T>(
  tasks: (() => Promise<T>)[],
  limit: number
): Promise<T[]> {
  const results: T[] = []
  const executing: Promise<void>[] = []
  
  for (const task of tasks) {
    const promise = task().then(result => {
      results.push(result)
    })
    
    executing.push(promise)
    
    if (executing.length >= limit) {
      await Promise.race(executing)
      const index = executing.findIndex(p => 
        (p as Promise<any>).then?.(r => r) === Promise.resolve()
      )
      if (index > -1) executing.splice(index, 1)
    }
  }
  
  await Promise.all(executing)
  return results
}

/** 生成随机颜色 */
export function randomColor(): string {
  return `#${Math.floor(Math.random() * 0xffffff).toString(16).padStart(6, '0')}`
}

/** 生成渐变色 */
export function generateGradient(startColor: string, endColor: string, steps: number): string[] {
  const start = hexToRgb(startColor)
  const end = hexToRgb(endColor)
  
  if (!start || !end) return []
  
  const colors: string[] = []
  for (let i = 0; i < steps; i++) {
    const ratio = i / (steps - 1)
    const r = Math.round(start.r + (end.r - start.r) * ratio)
    const g = Math.round(start.g + (end.g - start.g) * ratio)
    const b = Math.round(start.b + (end.b - start.b) * ratio)
    colors.push(rgbToHex(r, g, b))
  }
  return colors
}

function hexToRgb(hex: string): { r: number; g: number; b: number } | null {
  const cleanHex = hex.replace('#', '')
  if (cleanHex.length !== 6) return null
  
  return {
    r: parseInt(cleanHex.slice(0, 2), 16),
    g: parseInt(cleanHex.slice(2, 4), 16),
    b: parseInt(cleanHex.slice(4, 6), 16),
  }
}

function rgbToHex(r: number, g: number, b: number): string {
  return `#${[r, g, b].map(v => v.toString(16).padStart(2, '0')).join('')}`
}

/** 获取对比色 (黑/白) */
export function getContrastColor(hex: string): string {
  const rgb = hexToRgb(hex.replace('#', ''))
  if (!rgb) return '#000000'
  
  const luminance = (0.299 * rgb.r + 0.587 * rgb.g + 0.114 * rgb.b) / 255
  return luminance > 0.5 ? '#000000' : '#ffffff'
}

/** 颜色透明度 */
export function colorWithAlpha(hex: string, alpha: number): string {
  const rgb = hexToRgb(hex.replace('#', ''))
  if (!rgb) return hex
  return `rgba(${rgb.r}, ${rgb.g}, ${rgb.b}, ${alpha})`
}

/** 判断是否为空 */
export function isEmpty(value: any): boolean {
  if (value === null || value === undefined) return true
  if (typeof value === 'string') return value.trim() === ''
  if (Array.isArray(value)) return value.length === 0
  if (typeof value === 'object') return Object.keys(value).length === 0
  return false
}

/** 获取嵌套属性 */
export function get(obj: any, path: string, defaultValue?: any): any {
  const keys = path.split('.')
  let result = obj
  for (const key of keys) {
    if (result === null || result === undefined) return defaultValue
    result = result[key]
  }
  return result !== undefined ? result : defaultValue
}

/** 设置嵌套属性 */
export function set(obj: any, path: string, value: any): void {
  const keys = path.split('.')
  let current = obj
  for (let i = 0; i < keys.length - 1; i++) {
    const key = keys[i]
    if (!(key in current) || typeof current[key] !== 'object') {
      current[key] = {}
    }
    current = current[key]
  }
  current[keys[keys.length - 1]] = value
}

/** 删除嵌套属性 */
export function unset(obj: any, path: string): boolean {
  const keys = path.split('.')
  let current = obj
  for (let i = 0; i < keys.length - 1; i++) {
    if (current === null || current === undefined) return false
    current = current[keys[i]]
  }
  return delete current[keys[keys.length - 1]]
}

/** 数组去重 */
export function unique<T>(arr: T[], key?: keyof T | ((item: T) => any)): T[] {
  if (!key) return [...new Set(arr)]
  const seen = new Set()
  return arr.filter(item => {
    const val = typeof key === 'function' ? key(item) : item[key]
    if (seen.has(val)) return false
    seen.add(val)
    return true
  })
}

/** 数组分组 */
export function groupBy<T>(arr: T[], key: keyof T | ((item: T) => string)): Record<string, T[]> {
  return arr.reduce((groups, item) => {
    const groupKey = typeof key === 'function' ? key(item) : String(item[key])
    if (!groups[groupKey]) groups[groupKey] = []
    groups[groupKey].push(item)
    return groups
  }, {} as Record<string, T[]>)
}

/** 数组排序 */
export function sortBy<T>(arr: T[], key: keyof T | ((item: T) => any), order: 'asc' | 'desc' = 'asc'): T[] {
  return [...arr].sort((a, b) => {
    const aVal = typeof key === 'function' ? key(a) : a[key]
    const bVal = typeof key === 'function' ? key(b) : b[key]
    if (aVal < bVal) return order === 'asc' ? -1 : 1
    if (aVal > bVal) return order === 'asc' ? 1 : -1
    return 0
  })
}

/** 数组求和 */
export function sum(arr: number[]): number {
  return arr.reduce((acc, val) => acc + val, 0)
}

/** 数组平均值 */
export function average(arr: number[]): number {
  if (!arr.length) return 0
  return sum(arr) / arr.length
}

/** 数组最大值 */
export function max(arr: number[]): number {
  return Math.max(...arr)
}

/** 数组最小值 */
export function min(arr: number[]): number {
  return Math.min(...arr)
}

/** 对象转查询字符串 */
export function toQueryString(params: Record<string, any>): string {
  return Object.entries(params)
    .filter(([, value]) => value !== undefined && value !== null && value !== '')
    .map(([key, value]) => `${encodeURIComponent(key)}=${encodeURIComponent(String(value))}`)
    .join('&')
}

/** 解析查询字符串 */
export function parseQueryString(query: string): Record<string, string> {
  const params: Record<string, string> = {}
  query.replace(/^\?/, '').split('&').forEach(pair => {
    const [key, value] = pair.split('=')
    if (key) params[decodeURIComponent(key)] = decodeURIComponent(value || '')
  })
  return params
}

/** 格式化时间戳为相对时间 */
export function formatRelativeTime(timestamp: number): string {
  const now = Date.now()
  const diff = now - timestamp
  
  if (diff < 1000) return '刚刚'
  if (diff < 60000) return `${Math.floor(diff / 1000)}秒前`
  if (diff < 3600000) return `${Math.floor(diff / 60000)}分钟前`
  if (diff < 86400000) return `${Math.floor(diff / 3600000)}小时前`
  if (diff < 604800000) return `${Math.floor(diff / 86400000)}天前`
  
  return new Date(timestamp).toLocaleDateString()
}

/** 获取文件扩展名 */
export function getFileExtension(filename: string): string {
  return filename.slice(filename.lastIndexOf('.') + 1).toLowerCase()
}

/** 判断是否为图片文件 */
export function isImageFile(filename: string): boolean {
  const imageExtensions = ['jpg', 'jpeg', 'png', 'gif', 'webp', 'bmp', 'svg']
  return imageExtensions.includes(getFileExtension(filename))
}

/** 判断是否为视频文件 */
export function isVideoFile(filename: string): boolean {
  const videoExtensions = ['mp4', 'webm', 'ogg', 'mov', 'avi', 'mkv']
  return videoExtensions.includes(getFileExtension(filename))
}

/** 判断是否为音频文件 */
export function isAudioFile(filename: string): boolean {
  const audioExtensions = ['mp3', 'wav', 'ogg', 'flac', 'aac']
  return audioExtensions.includes(getFileExtension(filename))
}

/** 判断是否为文档文件 */
export function isDocumentFile(filename: string): boolean {
  const docExtensions = ['pdf', 'doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'txt', 'md']
  return docExtensions.includes(getFileExtension(filename))
}