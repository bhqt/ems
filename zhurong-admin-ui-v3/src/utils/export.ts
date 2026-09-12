import * as XLSX from 'xlsx'
import { formatDate } from './date'
import { formatNumber } from './format'

/** 导出为 Excel */
export function exportToExcel(data: any[], filename: string, options: {
  sheetName?: string
  headers?: string[]
  headerStyle?: any
  cellStyles?: Record<string, any>
  columnWidths?: number[]
  merges?: Array<{ s: { r: number; c: number }; e: { r: number; c: number } }>
} = {}): void {
  const {
    sheetName = 'Sheet1',
    headers,
    headerStyle,
    cellStyles,
    columnWidths,
    merges,
  } = options

  // 准备数据
  const exportData = data.map(row => {
    const newRow: any = {}
    const keys = headers || Object.keys(row)
    keys.forEach(key => {
      let value = row[key]
      // 格式化日期
      if (value instanceof Date) {
        value = formatDate(value)
      }
      // 格式化数字
      if (typeof value === 'number') {
        value = formatNumber(value)
      }
      newRow[key] = value
    })
    return newRow
  })

  // 创建工作簿
  const wb = XLSX.utils.book_new()
  const ws = XLSX.utils.json_to_sheet(exportData, { header: headers })

  // 设置列宽
  if (columnWidths) {
    ws['!cols'] = columnWidths.map(w => ({ wch: w }))
  }

  // 合并单元格
  if (merges) {
    ws['!merges'] = merges
  }

  // 样式处理 (需要 xlsx-style 或额外处理)
  if (headerStyle) {
    const range = XLSX.utils.decode_range(ws['!ref'] || 'A1')
    for (let c = range.s.c; c <= range.e.c; c++) {
      const cellRef = XLSX.utils.encode_cell({ r: 0, c })
      if (ws[cellRef]) {
        ws[cellRef].s = headerStyle
      }
    }
  }

  XLSX.utils.book_append_sheet(wb, ws, sheetName)
  XLSX.writeFile(wb, `${sheetName}.xlsx`)
}

/** 导出表格数据为 Excel (包含表头、样式) */
export function exportTableToExcel(tableData: any[], columns: any[], filename: string): void {
  const headers = columns.map(col => col.title || col.dataIndex)
  const data = tableData.map(row => {
    const newRow: any = {}
    columns.forEach(col => {
      const key = col.dataIndex
      let value = row[key]
      if (col.formatter) {
        value = col.formatter(value, row)
      }
      newRow[col.title || key] = value
    })
    return newRow
  })

  exportToExcel(data, filename, {
    headers: columns.map(col => col.title || col.dataIndex),
    columnWidths: columns.map(col => col.width ? col.width / 7 : 15),
  })
}

/** 导出为 CSV */
export function exportToCSV(data: any[], filename: string, headers?: string[]): void {
  const keys = headers || (data.length > 0 ? Object.keys(data[0]) : [])
  const csvContent = [
    keys.join(','),
    ...data.map(row => keys.map(key => escapeCSV(row[key])).join(',')),
  ].join('\n')

  const bom = '\uFEFF'
  const blob = new Blob([bom + csvContent], { type: 'text/csv;charset=utf-8' })
  downloadBlob(blob, `${filename}.csv`)
}

function escapeCSV(value: any): string {
  if (value === null || value === undefined) return ''
  const str = String(value)
  if (str.includes(',') || str.includes('"') || str.includes('\n')) {
    return `"${str.replace(/"/g, '""')}"`
  }
  return str
}

/** 导出表格为 CSV */
export function exportTableToCSV(tableData: any[], columns: any[], filename: string): void {
  const headers = columns.map(col => col.title || col.dataIndex)
  const data = tableData.map(row => {
    const newRow: any = {}
    columns.forEach(col => {
      const key = col.dataIndex
      let value = row[key]
      if (col.formatter) {
        value = col.formatter(value, row)
      }
      newRow[col.title || key] = value
    })
    return newRow
  })

  exportToCSV(data, filename, headers)
}

/** 下载 Blob */
function downloadBlob(blob: Blob, filename: string): void {
  const url = URL.createObjectURL(blob)
  const link = document.createElement('a')
  link.href = url
  link.download = filename
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
  URL.revokeObjectURL(url)
}

/** 导出为 JSON */
export function exportToJSON(data: any, filename: string): void {
  const text = JSON.stringify(data, null, 2)
  const blob = new Blob([text], { type: 'application/json' })
  downloadBlob(blob, `${filename}.json`)
}

/** 打印表格 */
export function printTable(tableId: string, title?: string): void {
  const table = document.getElementById(tableId)
  if (!table) return

  const printWindow = window.open('', '_blank')
  if (!printWindow) return

  const html = `
    <html>
      <head>
        <title>${title || '打印'}</title>
        <style>
          body { font-family: Arial, sans-serif; padding: 20px; }
          table { width: 100%; border-collapse: collapse; margin-top: 20px; }
          th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
          th { background-color: #f5f5f5; font-weight: bold; }
          @media print {
            .no-print { display: none; }
          }
        </style>
      </head>
      <body>
        ${title ? `<h1>${title}</h1>` : ''}
        ${table.outerHTML}
      </body>
    </html>
  `

  printWindow.document.write(html)
  printWindow.document.close()
  printWindow.focus()
  printWindow.print()
  printWindow.close()
}

/** 导出图表为图片 */
export function exportChartAsImage(chartId: string, filename: string): void {
  const chart = document.getElementById(chartId)
  if (!chart) return

  const canvas = chart.querySelector('canvas')
  if (!canvas) return

  const link = document.createElement('a')
  link.download = `${filename}.png`
  link.href = canvas.toDataURL('image/png')
  link.click()
}