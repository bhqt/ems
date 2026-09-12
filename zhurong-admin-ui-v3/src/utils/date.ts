import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime'
import localizedFormat from 'dayjs/plugin/localizedFormat'
import utc from 'dayjs/plugin/utc'
import timezone from 'dayjs/plugin/timezone'
import 'dayjs/locale/zh-cn'
import 'dayjs/locale/en'

dayjs.extend(relativeTime)
dayjs.extend(localizedFormat)
dayjs.extend(utc)
dayjs.extend(timezone)

export const DATE_FORMATS = {
  DATE: 'YYYY-MM-DD',
  DATETIME: 'YYYY-MM-DD HH:mm:ss',
  TIME: 'HH:mm:ss',
  MONTH: 'YYYY-MM',
  YEAR: 'YYYY',
  WEEK: 'YYYY-[W]WW',
  ISO: 'YYYY-MM-DDTHH:mm:ssZ',
  CHINESE_DATE: 'YYYY年MM月DD日',
  CHINESE_DATETIME: 'YYYY年MM月DD日 HH:mm:ss',
  CHINESE_TIME: 'HH时mm分',
}

/** 格式化日期 */
export function formatDate(date: string | number | Date | dayjs.Dayjs | null | undefined, format = DATE_FORMATS.DATETIME): string {
  if (!date) return '—'
  return dayjs(date).format(format)
}

/** 格式化相对时间 */
export function formatRelativeTime(date: string | number | Date | dayjs.Dayjs | null | undefined): string {
  if (!date) return '—'
  return dayjs(date).fromNow()
}

/** 格式化时长(毫秒转可读) */
export function formatDuration(ms: number): string {
  if (ms < 1000) return `${ms}ms`
  if (ms < 60000) return `${(ms / 1000).toFixed(1)}s`
  if (ms < 3600000) return `${(ms / 60000).toFixed(1)}m`
  if (ms < 86400000) return `${(ms / 3600000).toFixed(1)}h`
  return `${(ms / 86400000).toFixed(1)}d`
}

/** 获取当前时区 */
export function getTimezone(): string {
  return dayjs.tz.guess()
}

/** 转换时区 */
export function convertTimezone(date: string | number | Date | dayjs.Dayjs, targetTz: string): dayjs.Dayjs {
  return dayjs(date).tz(targetTz)
}

/** 获取日期范围 */
export function getDateRange(type: 'today' | 'yesterday' | 'thisWeek' | 'lastWeek' | 'thisMonth' | 'lastMonth' | 'thisQuarter' | 'lastQuarter' | 'thisYear' | 'lastYear'): [string, string] {
  const now = dayjs()
  
  switch (type) {
    case 'today':
      return [now.startOf('day').format(DATE_FORMATS.DATE), now.endOf('day').format(DATE_FORMATS.DATE)]
    case 'yesterday':
      return [now.subtract(1, 'day').startOf('day').format(DATE_FORMATS.DATE), now.subtract(1, 'day').endOf('day').format(DATE_FORMATS.DATE)]
    case 'thisWeek':
      return [now.startOf('week').format(DATE_FORMATS.DATE), now.endOf('week').format(DATE_FORMATS.DATE)]
    case 'lastWeek':
      return [now.subtract(1, 'week').startOf('week').format(DATE_FORMATS.DATE), now.subtract(1, 'week').endOf('week').format(DATE_FORMATS.DATE)]
    case 'thisMonth':
      return [now.startOf('month').format(DATE_FORMATS.DATE), now.endOf('month').format(DATE_FORMATS.DATE)]
    case 'lastMonth':
      return [now.subtract(1, 'month').startOf('month').format(DATE_FORMATS.DATE), now.subtract(1, 'month').endOf('month').format(DATE_FORMATS.DATE)]
    case 'thisQuarter':
      return [now.startOf('quarter').format(DATE_FORMATS.DATE), now.endOf('quarter').format(DATE_FORMATS.DATE)]
    case 'lastQuarter':
      return [now.subtract(1, 'quarter').startOf('quarter').format(DATE_FORMATS.DATE), now.subtract(1, 'quarter').endOf('quarter').format(DATE_FORMATS.DATE)]
    case 'thisYear':
      return [now.startOf('year').format(DATE_FORMATS.DATE), now.endOf('year').format(DATE_FORMATS.DATE)]
    case 'lastYear':
      return [now.subtract(1, 'year').startOf('year').format(DATE_FORMATS.DATE), now.subtract(1, 'year').endOf('year').format(DATE_FORMATS.DATE)]
    default:
      return [now.startOf('day').format(DATE_FORMATS.DATE), now.endOf('day').format(DATE_FORMATS.DATE)]
  }
}

/** 解析日期字符串 */
export function parseDate(dateStr: string, format?: string): dayjs.Dayjs | null {
  try {
    return format ? dayjs(dateStr, format) : dayjs(dateStr)
  } catch {
    return null
  }
}

/** 判断是否同一天 */
export function isSameDay(date1: string | number | Date | dayjs.Dayjs, date2: string | number | Date | dayjs.Dayjs): boolean {
  return dayjs(date1).isSame(date2, 'day')
}

/** 判断是否同一周 */
export function isSameWeek(date1: string | number | Date | dayjs.Dayjs, date2: string | number | Date | dayjs.Dayjs): boolean {
  return dayjs(date1).isSame(date2, 'week')
}

/** 判断是否同一月 */
export function isSameMonth(date1: string | number | Date | dayjs.Dayjs, date2: string | number | Date | dayjs.Dayjs): boolean {
  return dayjs(date1).isSame(date2, 'month')
}

/** 获取周几 */
export function getDayOfWeek(date: string | number | Date | dayjs.Dayjs): number {
  return dayjs(date).day()
}

/** 获取月份天数 */
export function getDaysInMonth(date: string | number | Date | dayjs.Dayjs): number {
  return dayjs(date).daysInMonth()
}

/** 生成日期数组 */
export function generateDateArray(start: string | number | Date | dayjs.Dayjs, end: string | number | Date | dayjs.Dayjs, format = DATE_FORMATS.DATE): string[] {
  const dates: string[] = []
  let current = dayjs(start)
  const endDay = dayjs(end)
  
  while (current.isBefore(endDay) || current.isSame(endDay, 'day')) {
    dates.push(current.format(format))
    current = current.add(1, 'day')
  }
  
  return dates
}

/** 获取时段标签 */
export function getTimeSlotLabel(hour: number): string {
  if (hour < 6) return '凌晨'
  if (hour < 9) return '早晨'
  if (hour < 12) return '上午'
  if (hour < 14) return '中午'
  if (hour < 18) return '下午'
  if (hour < 22) return '晚上'
  return '深夜'
}

export default dayjs