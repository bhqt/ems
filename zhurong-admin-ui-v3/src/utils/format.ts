import { ENERGY_TYPES } from '@/constants'

/** 格式化数字 */
export function formatNumber(num: number | string, options: {
  precision?: number
  thousandSeparator?: boolean
  decimalSeparator?: string
} = {}): string {
  const { precision = 2, thousandSeparator = true, decimalSeparator = '.' } = options
  const number = typeof num === 'string' ? parseFloat(num) : num
  
  if (isNaN(number)) return '—'
  
  let formatted = number.toFixed(precision)
  
  if (thousandSeparator) {
    const parts = formatted.split('.')
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    formatted = parts.join(decimalSeparator)
  }
  
  return formatted
}

/** 格式化货币 */
export function formatCurrency(amount: number | string, currency = '¥', precision = 2): string {
  return `${currency}${formatNumber(amount, { precision })}`
}

/** 格式化百分比 */
export function formatPercent(value: number | string, precision = 2): string {
  const num = typeof value === 'string' ? parseFloat(value) : value
  if (isNaN(num)) return '—'
  return `${(num * 100).toFixed(precision)}%`
}

/** 格式化文件大小 */
export function formatFileSize(bytes: number): string {
  if (bytes === 0) return '0 B'
  const k = 1024
  const sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return `${parseFloat((bytes / Math.pow(k, i)).toFixed(2))} ${sizes[i]}`
}

/** 格式化时长 */
export function formatDuration(ms: number): string {
  if (ms < 1000) return `${ms}ms`
  if (ms < 60000) return `${(ms / 1000).toFixed(1)}s`
  if (ms < 3600000) return `${(ms / 60000).toFixed(1)}m`
  if (ms < 86400000) return `${(ms / 3600000).toFixed(1)}h`
  return `${(ms / 86400000).toFixed(1)}d`
}

/** 格式化数量单位 (k, M, B) */
export function formatCompactNumber(num: number): string {
  if (num < 1000) return num.toString()
  if (num < 1000000) return `${(num / 1000).toFixed(1)}k`
  if (num < 1000000000) return `${(num / 1000000).toFixed(1)}M`
  return `${(num / 1000000000).toFixed(1)}B`
}

/** 格式化能源数值 */
export function formatEnergyValue(value: number | string, energyType?: string, precision = 2): string {
  const num = typeof value === 'string' ? parseFloat(value) : value
  if (isNaN(num)) return '—'
  
  const unit = energyType 
    ? ENERGY_TYPES.find(t => t.value === energyType)?.unit 
    : ''
  
  return `${formatNumber(num, { precision })}${unit ? ` ${unit}` : ''}`
}

/** 格式化电话号码 */
export function formatPhone(phone: string): string {
  if (!phone) return '—'
  const cleaned = phone.replace(/\D/g, '')
  if (cleaned.length === 11) {
    return cleaned.replace(/(\d{3})(\d{4})(\d{4})/, '$1 $2 $3')
  }
  return phone
}

/** 格式化身份证号 */
export function formatIdCard(idCard: string): string {
  if (!idCard) return '—'
  return idCard.replace(/(\d{6})(\d{8})(\d{3}[\dXx])/, '$1 $2 $3')
}

/** 格式化银行卡号 */
export function formatBankCard(card: string): string {
  if (!card) return '—'
  return card.replace(/\s/g, '').replace(/(\d{4})/g, '$1 ').trim()
}

/** 脱敏手机号 */
export function maskPhone(phone: string): string {
  if (!phone) return '—'
  return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
}

/** 脱敏身份证 */
export function maskIdCard(idCard: string): string {
  if (!idCard) return '—'
  return idCard.replace(/(\d{6})\d{8}(\d{3}[\dXx])/, '$1********$2')
}

/** 脱敏邮箱 */
export function maskEmail(email: string): string {
  if (!email) return '—'
  const [local, domain] = email.split('@')
  if (local.length <= 2) return email
  return `${local[0]}${'*'.repeat(local.length - 2)}${local[local.length - 1]}@${domain}`
}

/** 脱敏姓名 */
export function maskName(name: string): string {
  if (!name) return '—'
  if (name.length <= 1) return name
  return `${name[0]}${'*'.repeat(name.length - 1)}`
}

/** 脱敏银行卡 */
export function maskBankCard(card: string): string {
  if (!card) return '—'
  const cleaned = card.replace(/\s/g, '')
  if (cleaned.length < 8) return card
  return `${cleaned.slice(0, 4)}${'*'.repeat(cleaned.length - 8)}${cleaned.slice(-4)}`
}

/** 截断字符串 */
export function truncate(str: string, length: number, suffix = '...'): string {
  if (!str || str.length <= length) return str
  return str.slice(0, length) + suffix
}

/** 首字母大写 */
export function capitalize(str: string): string {
  if (!str) return ''
  return str.charAt(0).toUpperCase() + str.slice(1)
}

/** 驼峰转下划线 */
export function camelToSnake(str: string): string {
  return str.replace(/[A-Z]/g, letter => `_${letter.toLowerCase()}`)
}

/** 下划线转驼峰 */
export function snakeToCamel(str: string): string {
  return str.replace(/_([a-z])/g, (_, letter) => letter.toUpperCase())
}

/** 对象键名转驼峰 */
export function keysToCamel(obj: any): any {
  if (Array.isArray(obj)) {
    return obj.map(keysToCamel)
  }
  if (obj !== null && typeof obj === 'object') {
    const newObj: any = {}
    for (const key in obj) {
      if (Object.prototype.hasOwnProperty.call(obj, key)) {
        newObj[snakeToCamel(key)] = keysToCamel(obj[key])
      }
    }
    return newObj
  }
  return obj
}

/** 对象键名转下划线 */
export function keysToSnake(obj: any): any {
  if (Array.isArray(obj)) {
    return obj.map(keysToSnake)
  }
  if (obj !== null && typeof obj === 'object') {
    const newObj: any = {}
    for (const key in obj) {
      if (Object.prototype.hasOwnProperty.call(obj, key)) {
        newObj[camelToSnake(key)] = keysToSnake(obj[key])
      }
    }
    return newObj
  }
  return obj
}

/** 生成随机字符串 */
export function randomString(length = 8, chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'): string {
  let result = ''
  for (let i = 0; i < length; i++) {
    result += chars.charAt(Math.floor(Math.random() * chars.length))
  }
  return result
}

/** 生成唯一 ID */
export function generateId(prefix = ''): string {
  return `${prefix}${Date.now()}${Math.random().toString(36).slice(2, 9)}`
}