/** 下载 Blob */
export function downloadBlob(blob: Blob, filename: string): void {
  const url = URL.createObjectURL(blob)
  const link = document.createElement('a')
  link.href = url
  link.download = filename
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
  URL.revokeObjectURL(url)
}

/** 下载文本 */
export function downloadText(text: string, filename: string, type = 'text/plain'): void {
  const blob = new Blob([text], { type })
  downloadBlob(blob, filename)
}

/** 下载 JSON */
export function downloadJSON(data: any, filename: string): void {
  const text = JSON.stringify(data, null, 2)
  downloadText(text, filename, 'application/json')
}

/** 下载 CSV */
export function downloadCSV(data: any[], filename: string, headers?: string[]): void {
  if (!data.length) return
  
  const keys = headers || Object.keys(data[0])
  const csvContent = [
    keys.join(','),
    ...data.map(row => keys.map(key => escapeCSV(row[key])).join(',')),
  ].join('\n')
  
  // 添加 BOM 以支持 Excel 打开中文
  const bom = '\uFEFF'
  downloadText(bom + csvContent, filename, 'text/csv;charset=utf-8')
}

/** 转义 CSV 字段 */
function escapeCSV(value: any): string {
  if (value === null || value === undefined) return ''
  const str = String(value)
  if (str.includes(',') || str.includes('"') || str.includes('\n')) {
    return `"${str.replace(/"/g, '""')}"`
  }
  return str
}

/** 下载文件 */
export function downloadFile(url: string, filename?: string): void {
  const link = document.createElement('a')
  link.href = url
  if (filename) {
    link.download = filename
  }
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
}

/** 从 URL 下载文件 */
export async function downloadFromUrl(url: string, filename?: string): Promise<void> {
  try {
    const response = await fetch(url)
    const blob = await response.blob()
    const finalFilename = filename || url.split('/').pop() || 'download'
    downloadBlob(blob, finalFilename)
  } catch (error) {
    console.error('Download failed:', error)
    throw error
  }
}

/** 批量下载 */
export async function batchDownload(urls: string[], filenames?: string[]): Promise<void> {
  for (let i = 0; i < urls.length; i++) {
    const filename = filenames?.[i] || urls[i].split('/').pop() || `download_${i}`
    await downloadFromUrl(urls[i], filename)
    // 避免浏览器拦截，稍微延迟
    await new Promise(resolve => setTimeout(resolve, 300))
  }
}