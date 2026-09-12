import { message } from 'ant-design-vue'

export function useDownload() {
  function downloadBlob(blob: Blob, filename: string) {
    const url = URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = filename
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    URL.revokeObjectURL(url)
  }

  function downloadText(text: string, filename: string, type = 'text/plain') {
    const blob = new Blob([text], { type })
    downloadBlob(blob, filename)
  }

  function downloadJSON(data: any, filename: string) {
    const text = JSON.stringify(data, null, 2)
    downloadText(text, filename, 'application/json')
  }

  function downloadCSV(data: any[], filename: string, headers?: string[]) {
    if (!data.length) {
      message.warning('没有数据可导出')
      return
    }

    const keys = headers || Object.keys(data[0])
    const csvContent = [
      keys.join(','),
      ...data.map(row => keys.map(key => escapeCSV(row[key])).join(',')),
    ].join('\n')

    downloadText(csvContent, filename, 'text/csv;charset=utf-8;')
  }

  function downloadExcel(data: any[], filename: string, sheetName = 'Sheet1') {
    // 需要引入 xlsx 库
    try {
      const XLSX = require('xlsx')
      const ws = XLSX.utils.json_to_sheet(data)
      const wb = XLSX.utils.book_new()
      XLSX.utils.book_append_sheet(wb, ws, sheetName)
      XLSX.writeFile(wb, filename)
    } catch (error) {
      console.error('Export Excel failed:', error)
      message.error('导出 Excel 失败，请确保已安装 xlsx 库')
    }
  }

  function downloadFile(url: string, filename?: string) {
    const link = document.createElement('a')
    link.href = url
    if (filename) {
      link.download = filename
    }
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
  }

  async function downloadFromUrl(url: string, filename?: string) {
    try {
      const response = await fetch(url)
      const blob = await response.blob()
      const finalFilename = filename || url.split('/').pop() || 'download'
      downloadBlob(blob, finalFilename)
    } catch (error) {
      console.error('Download failed:', error)
      message.error('下载失败')
    }
  }

  function escapeCSV(value: any): string {
    if (value === null || value === undefined) return ''
    const str = String(value)
    if (str.includes(',') || str.includes('"') || str.includes('\n')) {
      return `"${str.replace(/"/g, '""')}"`
    }
    return str
  }

  return {
    downloadBlob,
    downloadText,
    downloadJSON,
    downloadCSV,
    downloadExcel,
    downloadFile,
    downloadFromUrl,
  }
}