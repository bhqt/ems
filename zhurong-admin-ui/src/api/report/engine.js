import request from '@/utils/request'

// 生成报表
export function generateReport(data) {
  return request({
    url: '/system/report/engine/generate',
    method: 'post',
    data: data
  })
}

// 预览报表
export function previewReport(data) {
  return request({
    url: '/system/report/engine/preview',
    method: 'post',
    data: data
  })
}

// 导出Excel
export function exportExcel(data) {
  return request({
    url: '/system/report/engine/export/excel',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}

// 导出PDF
export function exportPdf(data) {
  return request({
    url: '/system/report/engine/export/pdf',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}

// 生成能耗报表
export function generateEnergyReport(data) {
  return request({
    url: '/system/report/engine/energy',
    method: 'post',
    data: data
  })
}

// 生成费用报表
export function generateExpenseReport(data) {
  return request({
    url: '/system/report/engine/expense',
    method: 'post',
    data: data
  })
}

// 生成损耗报表
export function generateLossReport(data) {
  return request({
    url: '/system/report/engine/loss',
    method: 'post',
    data: data
  })
}

// 生成碳排放报表
export function generateCarbonReport(data) {
  return request({
    url: '/system/report/engine/carbon',
    method: 'post',
    data: data
  })
}
