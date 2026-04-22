import request from '@/utils/request'

export function listCostSaving() {
  return request({ url: '/system/dispatch/evaluation/costSaving/list', method: 'get' })
}

export function getCostSavingTrend(startDate, endDate) {
  return request({ url: '/system/dispatch/evaluation/trend/costSaving', method: 'get', params: { startDate, endDate } })
}

export function getEfficiencyTrend(startDate, endDate) {
  return request({ url: '/system/dispatch/evaluation/trend/efficiency', method: 'get', params: { startDate, endDate } })
}

export function getEmissionTrend(startDate, endDate) {
  return request({ url: '/system/dispatch/evaluation/trend/emission', method: 'get', params: { startDate, endDate } })
}

export function getEvaluationSummary() {
  return request({ url: '/system/dispatch/evaluation/summary', method: 'get' })
}
